
// 作 临江踏雨不返 海枫
// 发送 群管功能 以查看功能
// 部分接口 卑微萌新
// 部分写法源码 秩河 尹志平 群鹅 天啦噜

// 你说你讨厌被骗 可你骗我的时候也没有心软

// 其实 我的心也想离你近一点

// 此脚本存在绝大多数中文变量 如果你没有Java基础请勿随意修改 可能造成无法加载或导致QQ频繁闪退

import android.app.Activity;
import android.widget.Toast;
import java.io.File;

String 退群拉黑目录;
String 联盟目录;
File 联盟群组文件;
File 封禁列表文件;

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

    load(appPath + "/核心功能/Utils.java");
    load(appPath + "/核心功能/DialogUtils.java");
    load(appPath + "/核心功能/MenuHandlers.java");
    load(appPath + "/核心功能/EventHandlers.java");
    load(appPath + "/核心功能/AllianceManager.java");
    load(appPath + "/核心功能/FileOperations.java");
    load(appPath + "/核心功能/QQInterface.java");
    load(appPath + "/核心功能/CustomDice.java");

    initEventHandlers();
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

public void onUnLoad() {
    toast("简洁群管已卸载");
}

try {
    File errorFile = new File(appPath + "/error.txt");
    if (errorFile.exists()) {
        errorFile.delete();
    }
} catch (Exception e) {
}

// 希望有人懂你的言外之意 更懂你的欲言又止