import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

String configPrefix = "prefix";
String configSuffix = "suffix";
String filePath = appPath + "/消息文案/消息文案.txt";
ArrayList contents = new ArrayList();
String defaultContent = "在失去的所有人中 我最怀念自己";
String separator = "——————————————";

void init() {
    try {
        File dir = new File(appPath + "/消息文案");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(defaultContent);
            writer.close();
            contents.add(defaultContent);
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    contents.add(line);
                }
            }
            reader.close();
            
            if (contents.isEmpty()) {
                contents.add(defaultContent);
            }
        }
    } catch (Exception e) {
        error(e);
    }
}

init();

public String getMsg(String text, String uin, int chatType) {
    if (contents.isEmpty()) return text;
    
    String key = (chatType == 2) ? uin : "private_" + uin;
    
    Random random = new Random();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTime = sdf.format(new Date());
    
    if (getBoolean(configPrefix, key, false)) {
        String randomContent = (String) contents.get(random.nextInt(contents.size()));
        text = randomContent + "\n" + separator + "\n" + text;
    }
    
    if (getBoolean(configSuffix, key, false)) {
        text = text + "\n" + separator + "\n" + currentTime;
    }
    
    return text;
}

addItem("开启/关闭当前聊天前缀文案", "togglePrefix");
addItem("开启/关闭当前聊天后缀时间", "toggleSuffix");

public void togglePrefix(String group, String uin, int chatType) {
    String key = (chatType == 2) ? group : "private_" + uin;
    boolean current = getBoolean(configPrefix, key, false);
    putBoolean(configPrefix, key, !current);
    toast("前缀文案已" + (!current ? "开启" : "关闭"));
}

public void toggleSuffix(String group, String uin, int chatType) {
    String key = (chatType == 2) ? group : "private_" + uin;
    boolean current = getBoolean(configSuffix, key, false);
    putBoolean(configSuffix, key, !current);
    toast("后缀时间已" + (!current ? "开启" : "关闭"));
}

public void unLoadPlugin() {
    toast("加载成功 欢迎使用");
}


sendLike("2133115301",20);