import android.os.Environment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Math;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 插件更新时状态管理
 * 预留未来可能出现的选择性更新功能
 */
private ConcurrentHashMap canUpdate = new ConcurrentHashMap();
class PluginsUpdatingStatus {
    public HashMap pluginMap = new HashMap(); // 插件信息
    public String name = null; // 插件名字
    public boolean isSelected = true; // 选择更新
    public boolean isUpdated = false; // 已更新
    public boolean isFailed = false; // 更新失败
    public String msg = null; // 存放一段信息
    PluginsUpdatingStatus(HashMap pluginMap){
        this.pluginMap = pluginMap;
        this.name = pluginMap.get("pluginName");
    }
    public void setSuccess(){
        this.isUpdated = true;
        this.isFailed = false;
    }
    public void setFailed(String msg){
        this.isUpdated = false;
        this.isFailed = true;
        this.msg = msg;
    }
}


public void pluginsCheckAndUpdate(){
    File dir = new File(
        Environment.getExternalStorageDirectory(),
        "/Android/data/com.tencent.mobileqq/QStory/Plugin/"
    );
    boolean runLock = runtimeLock(dir, (byte)1);
    if (!runLock) {
        toast("文件锁正由其他进程持有，无法继续操作");
        return;
    }
    toast("开始更新已安装的插件");
    String path = dir.getAbsolutePath();
    List dirlist = new ArrayList();
    if (dir.exists()) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    dirlist.add(f.getName());
                }
            }
        }
    }
    HashMap plugins = getPluginLists();
    if (plugins != null) {
        for (String key : plugins.keySet()) {
            if (!dirlist.contains(key)) continue;
            HashMap pluginMap = plugins.get(key);
            File PluginPath = new File(dir, key);
            Integer checkResult = versionCheck(pluginMap, PluginPath);
            if (checkResult > 0) {
                canUpdate.put(key, new PluginsUpdatingStatus(pluginMap));
            }
        }
    }
    if (canUpdate.isEmpty()) {
        runtimeLock(dir, (byte) 0);
        toast("所有插件均为最新版本");
        return;
    }
    toast("发现 " + canUpdate.size() + " 个可更新插件, 开始更新");
    int poolSize = canUpdate.size(); // 创建和待更新插件数相同的线程池, 坑: 更新任务正常结束后线程池不会自动终止, 只能依赖强制结束
    ExecutorService updatePool = Executors.newFixedThreadPool(poolSize);
    List keyList = new ArrayList(canUpdate.keySet());
    CountDownLatch realTaskLatch = new CountDownLatch(canUpdate.size()); // 用于等待所有任务完成, 坑: 更新任务正常结束后线程池不会自动终止, 只能依赖强制结束, 通过检查此值将为0或超时强制结束线程池
    AtomicInteger counter = new AtomicInteger(0); // 用于线程内自增索引找到一个待更新插件, 坑: 这里不能传递hashmap的k/v,无论如何, 此环境只能传递指针而非值, 循环会快于线程执行导致只能读取到最后一个k/v
    Runnable updateTask = new Runnable() {
        public void run() {
            while(true) {
                int index = counter.getAndIncrement();
                if (index >= keyList.size()) return;
                try{
                    final String k = keyList.get(index);
                    final PluginsUpdatingStatus pus = canUpdate.get(k);
                    PluginsUpdatingStatus status = pluginUpdate(pus, dir);
                    canUpdate.put(k, status);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    realTaskLatch.countDown();
                }
            }
        }
    };
    for (int i = 0; i < poolSize; i++) {
        updatePool.submit(updateTask);
    }
    boolean taskTimeout = false;
    try {
        boolean taskDone = realTaskLatch.await(1, TimeUnit.MINUTES);
        if (!taskDone) {
            taskTimeout = true;
            toast("部分任务未在1分钟内完成, 已强制结束并关闭线程池");
            updatePool.shutdownNow();
        }
    } catch (InterruptedException e) {
        updatePool.shutdownNow();
    }
    if (!taskTimeout) {
        updatePool.shutdown(); // 虽然是冗余的, 实践实际上执行不到这里
    }
    // 此处本该等待线程池自然终止, 坑: 如上, 经实践此环境即便线程正常退出队列为空也永远等不到线程池自然终止

    StringBuilder updateResult = new StringBuilder();
    int[] count = {dirlist.size(), canUpdate.size(), 0, 0};
    for (String k : canUpdate.keySet()) {
        PluginsUpdatingStatus pus = canUpdate.get(k);
        if (!pus.isSelected) continue;
        if (pus.isFailed){
            updateResult.append("插件 ").append(pus.pluginMap.get("pluginName")).append(" 更新失败").append("\n");
        } else {
            updateResult.append("插件 ").append(pus.pluginMap.get("pluginName")).append(" 更新成功").append("\n");
            count[3]++;
        }
        count[2]++;
        
    }
    runtimeLock(dir, (byte)0);
    String countStr = "已检查本地 " + count[0] + " 个插件, 发现 " + count[1] + " 个可更新, 选择" + count[2] + "个进行更新,成功更新 " + count[3] + " 个";
    toast(countStr);
}


/**
 * 文件锁
 * 0: 解锁
 * 1: 加锁
 */
public boolean runtimeLock(File directory, byte operation) {
    File lockFile = new File(directory, ".lock");
    switch (operation) {
        case (byte)0: 
            if (lockFile.exists()) {
                return lockFile.delete();
            } else {
                return true;
            }
        case (byte)1:
            try {
                return lockFile.createNewFile();
            } catch (IOException e) {
                return false;
            }
        default:
            return false;
    }
}

public PluginsUpdatingStatus pluginUpdate(PluginsUpdatingStatus pluginStatus, File basePath) {
    HashMap pluginMap = pluginStatus.pluginMap;
    String downloadUrl = "https://plugin.sacz.top/plugin/downloadPlugin?id=" + pluginMap.get("cloudId").toString();
    String pluginName = pluginStatus.name;
    File zipFile = new File(basePath, pluginName + ".zip");
    try {
        URL url = new URL(downloadUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(zipFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        connection.disconnect();
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            File outFile = new File(basePath, entry.getName());
            if (entry.isDirectory()) {
                outFile.mkdirs();
            } else {
                outFile.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buf = new byte[4096];
                int len;
                while ((len = zipInputStream.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fos.flush();
                fos.getFD().sync();
                fos.close();
            }
            zipInputStream.closeEntry();
        }
        zipInputStream.close();
        zipFile.delete();
        pluginStatus.setSuccess();
    } catch (IOException e) {
        e.printStackTrace();
        pluginStatus.setFailed("无法下载解压压缩包");
    }
    return pluginStatus;
}

public Integer versionCheck(HashMap onlinePluginMap, File PluginPath) {
    File versionFile = new File(PluginPath, "info.prop");
    if (!versionFile.exists()) return null;
    String localVersion_Pre = null;
    String localTime_Pre = null;
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(versionFile), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("version")) localVersion_Pre = line.split("=")[1].trim();
            if (line.startsWith("time")) localTime_Pre = line.split("=")[1].trim();
        }
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    if (localVersion_Pre == null || localTime_Pre == null) return null;
    String onlineVersion_Pre = onlinePluginMap.get("pluginVersion").trim();
    boolean isOnlyNumberAndDot_local = localVersion_Pre.matches("^[0-9.]+$");
    boolean isOnlyNumberAndDot_online = onlineVersion_Pre.matches("^[0-9.]+$");
    String localVersionFixed = localVersion_Pre;
    String onlineVersionFixed = onlineVersion_Pre;
    byte versionType = 0; // 0: 正常版本号 1: 时间版本号
    if (!isOnlyNumberAndDot_local || !isOnlyNumberAndDot_online) {
        localVersionFixed = localTime_Pre;
        onlineVersionFixed = onlinePluginMap.get("pluginTime").trim();
        versionType = 1;
    }
    return compareVersions(onlineVersionFixed, localVersionFixed, versionType);
}

/**
 * 四种比较结果
 * <0 本地结果比在线版本高
 * =0 本地结果与在线版本相同
 * >0 本地结果比在线版本低
 * null 检查版本失败
 */
public Integer compareVersions(String onlineVersion, String localVersion, byte versionType) {
    switch (versionType) {
        case (byte)1: // 时间版本号 (2025-9-6)
            onlineVersion = onlineVersion.replace("-", ".").replace(":", ".").replace(" ", "");
            localVersion = localVersion.replace("-", ".").replace(":", ".").replace(" ", "");
            return compareVersions(onlineVersion, localVersion, (byte)0); // :)
        case (byte)0: // 正常版本号 (1.0.0)
            int[] onlineParts = parseVersion(onlineVersion);
            int[] localParts = parseVersion(localVersion);
            if (onlineParts.length == 0 && localParts.length == 0) return null;
            int maxLength = Math.max(localParts.length, onlineParts.length);
            for (int i = 0; i < maxLength; i++) {
                int onlinePart = i < onlineParts.length ? onlineParts[i] : 0;
                int localPart = i < localParts.length ? localParts[i] : 0;
                if (onlinePart != localPart) {
                    return onlinePart - localPart;
                }
            }
            return 0;
        default:
            return null;
    }
}

/**
 * 安全转换版本号
 * "..1" -> [0, 0, 1]
 * "A.." -> [0, 0, 0]
 */
public static int[] parseVersion(String version) {
    if (version == null || version.isEmpty()) return new int[0];
    String[] parts = version.split("\\.");
    int[] result = new int[parts.length];
    for (int i = 0; i < parts.length; i++) {
        if (parts[i].isEmpty()) {
            result[i] = 0;
        } else {
            try {
                result[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                result[i] = 0;
            }
        }
    }
    return result;
}

/**
 * 获取服务器插件列表
 * @return HashMap 插件列表
 *  <插件名, 插件信息HashMap>
 */
public HashMap getPluginLists(){
    URL pluginUrl = new URL("https://plugin.sacz.top/plugin/get-online-plugin-list/qq");
    HttpURLConnection connection = (HttpURLConnection) pluginUrl.openConnection();
    connection.setRequestMethod("GET");
    connection.setConnectTimeout(10000);
    connection.setReadTimeout(10000);
    connection.connect();
    InputStream inputStream = connection.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder response = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        response.append(line);
    }
    reader.close();
    connection.disconnect();
    String jsonResponse = response.toString();
    HashMap pluginList = new HashMap();
    try {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (jsonObject.getInt("code") == 200) {
            JSONArray dataArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject pluginObject = dataArray.getJSONObject(i);
                JSONObject pluginInfo = pluginObject.getJSONObject("pluginInfo");
                Iterator keys = pluginInfo.keys();
                Map pluginMap = new HashMap();
                pluginMap.put("app", pluginObject.getString("app"));
                pluginMap.put("cloudId", pluginObject.getString("cloudId"));
                pluginMap.put("createTime", pluginObject.getString("createTime"));
                pluginMap.put("downloadCount", pluginObject.getString("downloadCount"));
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    pluginMap.put(key, pluginInfo.optString(key, ""));
                }
                pluginList.put(pluginInfo.getString("pluginName"), pluginMap);
            }
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return pluginList;
}


//添加脚本悬浮窗菜单项
addItem("一键检查更新已安装脚本", "CheckAndUpdateAll");
addItem("强制解除文件锁", "ForceUnlock");

public void CheckAndUpdateAll(String s) {
    new Thread(new Runnable() {
        public void run() {
            pluginsCheckAndUpdate();
        }
    }).start();
}

public void ForceUnlock(String s) {
    File dir = new File(
        Environment.getExternalStorageDirectory(),
        "/Android/data/com.tencent.mobileqq/QStory/Plugin/"
    );
    boolean unlockStatus = runtimeLock(dir, (byte)0);
    toast(unlockStatus ? "文件锁解除成功" : "文件锁解除失败");
}