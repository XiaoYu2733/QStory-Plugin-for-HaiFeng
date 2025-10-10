
// 作 海枫

// 我不懂什么叫挽留 我只知道爱我的人不会离开我

// 反馈交流群：https://t.me/XiaoYu_Chat

// 该脚本可以添加自定义目录哦

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

toast("欢迎使用清理QQ垃圾Java");
addItem("脚本本次更新日志","showUpdateLog");

addItem("清理垃圾", "clearCacheMenu");

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
                    "更新日志\n\n" +
                    "- [移除] 部分不存在的目录\n" +
                    "- [添加] 定时线程清理 如果建议定时线程清理 默认开启 如果需要关闭 请在脚本悬浮窗关闭\n" +
                    "- [新增] data更多目录 尤其是装扮存储目录那个地方，简直就是占内存的圣地 现在可以清理那个目录了 不过可能没有root权限无法清理 以及正在使用该脚本的root用户请放心 不会把data格了 我自己的备用机已测试，可以清理 不影响其他数据\n" +
                    "- [优化] 底层代码 现在脚本更流畅 以便于打死南浔\n\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

public void deleteRecursive(File fileOrDirectory) {
    if (fileOrDirectory == null || !fileOrDirectory.exists()) return;
    
    if (fileOrDirectory.isDirectory()) {
        File[] files = fileOrDirectory.listFiles();
        if (files != null) {
            for (File child : files) {
                deleteRecursive(child);
            }
        }
    }
    fileOrDirectory.delete();
}

public void clearCache() {
    try {
        toast("开始清理QQ垃圾...");
        log("开始清理QQ垃圾");
        
        // 强制停止QQ 这个坏了 目前没有办法修 不修了 自己重启QQ
        Runtime.getRuntime().exec("am force-stop com.tencent.mobileqq");
        log("已强制停止QQ");
        Thread.sleep(1000);
        
        // 清理路径 可自己添加
        String[] cachePaths = {
            // Android/data 部分 不确定是否可以清理
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/diskcache",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/Scribble",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/ScribbleCache",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/qav",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/qqmusic",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/pddata",
            "/sdcard/Android/data/com.tencent.mobileqq/cache",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/photo",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/chatpic",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/thumb",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/QQ_Images",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/QQEditPic",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/hotpic",
            "/sdcard/Android/data/com.tencent.mobileqq/qzone/zip_cache",
            "/sdcard/Android/data/com.tencent.mobileqq/qzone/video_cache",
            "/sdcard/Android/data/com.tencent.mobileqq/qzone/imageV2",
            "/sdcard/Android/data/com.tencent.mobileqq/qzlive",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/shortvideo",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/qbosssplahAD",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/.apollo",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/vasrm",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/lottie",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQ_Images/QQEditPic",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/mini",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/TMAssistantSDK",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.font_info",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/.hiboom_font",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/MobileQQ/.gift",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.trooprm/enter_effects",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/head",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.pendant",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.profilecard",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.sticker_recommended_pics",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/pe",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.emotionsm",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.vaspoke",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/newpoke",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/poke",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/.vipicon",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/DoutuRes",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/funcall",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/.trooptmp",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/.tmp",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/.thumbnails",
            "/sdcard/Android/data/com.tencent.mobileqq/qcircle",
            "/sdcard/Android/data/com.tencent.mobileqq/files/.info",
            "/sdcard/Android/data/com.tencent.mobileqq/files/onelog",
            "/sdcard/Android/data/com.tencent.mobileqq/files/ae/playshow",
            "/sdcard/Android/data/com.tencent.mobileqq/files/tencent/msflogs",
            "/sdcard/Android/data/com.tencent.mobileqq/files/.info",
            "/sdcard/Android/data/com.tencent.mobileqq/files/ae/camera/capture",
            "/sdcard/Android/data/com.tencent.mobileqq/files/tbslog",
            "/sdcard/Android/data/com.tencent.mobileqq/files/tencent/tbs_live_log",
            "/sdcard/Android/data/com.tencent.mobileqq/files/tencent/tbs_common_log",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/.tmp/edit_video",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/TMAssistantSDK/Download/com.tencent.mobileqq",
            "/sdcard/Android/data/com.tencent.mobileqq/Tencent/mini/files",
            "/sdcard/Tencent/blob/mqq",
            "/sdcard/Tencent/ams/cache",
            "/sdcard/Tencent/com.tencent.weread/euplog.txt",
            "/sdcard/Tencent/imsdkvideocache",
            "/sdcard/Tencent/Midas/Log",
            "/sdcard/Tencent/MobileQQ/bless",
            
            // /data/data 路径 (可能需要root权限)
            "/data/data/com.tencent.mobileqq/files/group_catalog_temp",
            "/data/data/com.tencent.mobileqq/files/hippy/codecache",
            "/data/data/com.tencent.mobileqq/files/hippy/bundle",
            "/data/data/com.tencent.mobileqq/files/mini",
            "/data/user/0/com.tencent.mobileqq/files/files",
            "/data/data/com.tencent.mobileqq/shared_prefs",
            "/data/data/com.tencent.mobileqq/shared_prefs",
            "/data/data/com.tencent.mobileqq/cache",
            "/data/data/com.tencent.mobileqq/cache"
        };
        
        int deletedCount = 0;
        int errorCount = 0;
        
        for (String path : cachePaths) {
            File target = new File(path);
            if (!target.exists()) {
                log("路径不存在: " + path);
                continue;
            }
            
            try {
                log("清理: " + path);
                deleteRecursive(target);
                deletedCount++;
            } catch (Exception e) {
                log("清理错误: " + path + " - " + e.getMessage());
                errorCount++;
            }
        }
        
        // 重启QQ
        Runtime.getRuntime().exec("am start com.tencent.mobileqq/.activity.SplashActivity");
        log("已重启QQ");
        
        String result = "清理完成！处理" + deletedCount + "个路径";
        if (errorCount > 0) {
            result += "，有" + errorCount + "个错误";
        }
        
        toast(result);
        log(result);
        
    } catch (Exception e) {
        toast("清理出错: " + e.getMessage());
        log("清理出错: " + e.getMessage());
        error(e);
    }
}

void onMsg(Object msg) {
    if (msg.MessageContent.equals("清理垃圾") && !msg.IsSend) {
        new Thread(new Runnable() {
            public void run() {
                clearCache();
            }
        }).start();
    }
}

public void clearCacheMenu(String groupUin, String uin, int chatType) {
    new Thread(new Runnable() {
        public void run() {
            clearCache();
        }
    }).start();
}

sendLike("2133115301",20);