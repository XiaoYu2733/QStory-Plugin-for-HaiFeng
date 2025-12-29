
// 作 临江踏雨不返 海枫
// 发送 群管功能 以查看功能
// 部分接口 卑微萌新
// 部分写法源码 秩河 尹志平 群鹅 天啦噜

// 你说你讨厌被骗 可你骗我的时候也没有心软

import android.app.Activity;
import android.widget.Toast;
import java.io.File;

String 退群拉黑目录;
String 联盟目录;
File 联盟群组文件;
File 封禁列表文件;

public void onLoad() {
    load(appPath + "/核心功能/Utils.java");
    load(appPath + "/核心功能/DialogUtils.java");
    load(appPath + "/核心功能/MenuHandlers.java");
    load(appPath + "/核心功能/EventHandlers.java");
    load(appPath + "/核心功能/AllianceManager.java");
    load(appPath + "/核心功能/FileOperations.java");
    load(appPath + "/核心功能/QQInterface.java");
    
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
}

public void onUnLoad() {
    Toast.makeText(context, "简洁群管已卸载", Toast.LENGTH_SHORT).show();
}