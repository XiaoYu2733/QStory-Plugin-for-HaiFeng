/**
 * 作 临江踏雨不返 海枫 298x3 雾江月 清洒关度
 * 发送 群管功能 以查看功能
 * 部分接口 卑微萌新
 * 部分写法源码 秩河 尹志平 群鹅 天啦噜
 * 
 * 你说你讨厌被骗 可你骗我的时候也没有心软
 * 
 * 此脚本存在绝大多数中文变量 如果你没有Java基础请勿随意修改 可能造成无法加载或导致QQ频繁闪退
**/

/**
 * import com.tencent.common.app.BaseApplicationImpl;
 * Object app = BaseApplicationImpl.getApplication().getRuntime();
 * 
 * import android.content.pm.PackageManager;
 * import android.content.pm.ApplicationInfo;
 * 
 * PackageManager pm = context.getPackageManager();
 * ApplicationInfo sAppInfo = pm.getApplicationInfo("com.tencent.mobileqq", PackageManager.GET_META_DATA);
 * String UUID = sAppInfo.metaData.getString("com.tencent.rdm.uuid");
 * String Version_Code = UUID.substring(0, UUID.indexOf("_"));
 * int QQ_version = Integer.parseInt(Version_Code);
 * 
 * import com.tencent.mobileqq.app.BaseActivity;
 * BaseActivity activity;
 * while (activity == null) {
 *     activity = BaseActivity.sTopActivity;
 * }
 * 
 * import com.tencent.mobileqq.troop.api.ITroopInfoService;
 * ITroopInfoService TroopInfo = app.getRuntimeService(ITroopInfoService.class);
 * 
 * public String quncode(String qun) {
 *     String code = TroopInfo.getTroopCodeByTroopUin(qun);
 *     if (code == null || code.equals("")) code = qun;
 *     return code;
 * }
 * 
 * public String qunuin(String code) {
 *     String qun = TroopInfo.getTroopUinByTroopCode(code);
 *     if (qun == null || qun.equals("")) qun = code;
 *     return qun;
 * }
 */

private void loadAllScripts() {
    load(appPath + "/import.java");
    load(appPath + "/核心功能/Utils.java");
    load(appPath + "/核心功能/FileOperations.java");
    load(appPath + "/核心功能/DialogUtils.java");
    load(appPath + "/核心功能/MenuHandlers.java");
    load(appPath + "/核心功能/EventHandlers.java");
    load(appPath + "/核心功能/AllianceManager.java");
    load(appPath + "/核心功能/QQInterface.java");
    load(appPath + "/核心功能/ForbiddenTrace.java");
    load(appPath + "/核心功能/ForbiddenListDialog.java");
}

import android.app.Activity;
import android.app.Dialog;
import android.widget.Toast;
import java.io.File;

String 退群拉黑目录;
String 联盟目录;
File 联盟群组文件;
File 封禁列表文件;

/**
 *public void Callback_OnRawMsg(Object msg){
 *Toast(msg);}
 */

public void onLoad() {
    退群拉黑目录 = appPath + "/退群拉黑/";
    File 退群拉黑文件夹 = new File(退群拉黑目录);
    if (!退群拉黑文件夹.exists()) {
        退群拉黑文件夹.mkdirs();
    }
    联盟目录 = appPath + "/封禁联盟/";
    File 联盟文件夹 = new File(联盟目录);
    if (!联盟文件夹.exists()) {
        联盟文件夹.mkdirs();
    }
    联盟群组文件 = new File(联盟目录, "联盟群组.txt");
    封禁列表文件 = new File(联盟目录, "封禁联盟.txt");
    int 艾特禁言时间 = getInt("艾特禁言时间配置", "时间", 2592000);

    loadAllScripts();
    
    initEventHandlers();
    
    初始化禁言追踪();
}

public void onUnLoad() {
    卸载禁言追踪();
}

public void initEventHandlers() {
    new Thread(new Runnable() {
        public void run() {
            try {
                Thread.sleep(5000);
                ArrayList 联盟群组列表 = null;
                ArrayList 封禁列表 = null;
                try {
                    if (联盟群组文件 != null && 联盟群组文件.exists()) {
                        联盟群组列表 = 简取(联盟群组文件);
                    }
                    if (封禁列表文件 != null && 封禁列表文件.exists()) {
                        封禁列表 = 简取(封禁列表文件);
                    }
                } catch (Exception e) {
                    return;
                }
                if (联盟群组列表 == null || 联盟群组列表.isEmpty() || 封禁列表 == null || 封禁列表.isEmpty()) {
                    return;
                }
                Set 封禁UIN集合 = new HashSet();
                ArrayList 封禁列表副本 = safeCopyList(封禁列表);
                for (int k = 0; k < 封禁列表副本.size(); k++) {
                    String 记录 = (String) 封禁列表副本.get(k);
                    if (记录 != null && 记录.contains("|")) {
                        String[] parts = 记录.split("\\|");
                        if (parts.length > 0 && parts[0] != null) {
                            封禁UIN集合.add(parts[0].trim());
                        }
                    }
                }
                if (封禁UIN集合.isEmpty()) {
                    return;
                }
                ArrayList 联盟群组列表副本 = safeCopyList(联盟群组列表);
                for (int i = 0; i < 联盟群组列表副本.size(); i++) {
                    String 群号 = (String)联盟群组列表副本.get(i);
                    if (群号 == null || 群号.isEmpty()) continue;
                    try {
                        ArrayList 成员列表 = getGroupMemberList(群号);
                        if (成员列表 != null && !成员列表.isEmpty()) {
                            ArrayList 成员列表副本 = safeCopyList(成员列表);
                            for (int j = 0; j < 成员列表副本.size(); j++) {
                                Object 成员 = 成员列表副本.get(j);
                                if (成员 != null && 成员.UserUin != null) {
                                    if (封禁UIN集合.contains(成员.UserUin)) {
                                        unifiedKick(群号, 成员.UserUin, true);
                                        Thread.sleep(300);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        }
    }).start();
}

if (!"False".equals(getString("Toast", "Yes"))) {
    toast("简洁群管加载成功\n眼泪是人最纯真的东西 流尽了 人就变得冷漠了");
    putString("Toast", "Yes", "False");
}

try {
    File errorFile = new File(appPath + "/error.txt");
    if (errorFile.exists() && errorFile.length() > 1024 * 1024) {
        errorFile.delete();
        toast("检测error.txt过大 已清理 如有问题可优先加群发送日志");
    }
} catch (Exception e) {
}

// 希望有人懂你的言外之意 更懂你的欲言又止