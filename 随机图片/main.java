
// 作 海枫

// 认清一个人三件事就够了 吵架后的态度 回消息的速度 包容你的程度

// 如果把爱寄托在别人身上 会很痛

import java.net.HttpURLConnection;
import java.net.URL;
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
import java.util.HashMap;
import java.util.Map;

public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String qq = msg.UserUin;
    String qun = msg.GroupUin;
    boolean isGroup = msg.IsGroup;
    
    if (isGroup) {
        boolean otherTrigger = getBoolean("other_trigger_switch", qun, false);
        if (!qq.equals(myUin) && !otherTrigger) {
            return;
        }
    }
    
    Map<String, String[]> commandMap = new HashMap<>();
    commandMap.put("随机二次元图片", new String[]{"random_pic_switch", "https://www.loliapi.com/bg/"});
    commandMap.put("随机二次元头像", new String[]{"avatar_switch", "https://www.loliapi.com/acg/pp/"});
    commandMap.put("手机端二次元壁纸", new String[]{"pe_wallpaper_switch", "https://www.loliapi.com/acg/pe/"});
    commandMap.put("电脑端二次元壁纸", new String[]{"pc_wallpaper_switch", "https://www.loliapi.com/acg/pc/"});
    commandMap.put("随机图片", new String[]{"random_image_switch", "https://www.loliapi.com/acg/"});
    commandMap.put("随机涩图", new String[]{"setu_switch", "https://api.anosu.top/api/?sort=setu"});
    commandMap.put("随机r18", new String[]{"r18_switch", "https://moe.jitsu.top/api/?sort=r18"});
    commandMap.put("随机星空", new String[]{"starry_switch", "https://api.anosu.top/api?sort=starry"});
    commandMap.put("随机风景", new String[]{"scenery_switch", "https://tuapi.eees.cc/api.php?category=fengjing&px=pc&type=302"});
    commandMap.put("随机电脑壁纸", new String[]{"pc_wallpaper2_switch", "https://api.anosu.top/api/?sort=pc"});
    commandMap.put("随机手机壁纸", new String[]{"mp_wallpaper_switch", "https://api.anosu.top/api/?sort=mp"});
    commandMap.put("随机1080p", new String[]{"1080p_switch", "https://api.anosu.top/api/?sort=1080p"});
    commandMap.put("随机兽耳", new String[]{"furry_switch", "https://moe.jitsu.top/api/?sort=furry"});
    commandMap.put("随机三次元", new String[]{"realistic_switch", "https://tuapi.eees.cc/api.php?category=meinv&px=pc&type=302"});

    if (commandMap.containsKey(text) && getBoolean(commandMap.get(text)[0], qun, false)) {
        String url = commandMap.get(text)[1];
        if (isGroup) {
            sendMsg(qun, "", "[PicUrl=" + location(url) + "]");
        } else {
            sendMsg("", qq, "[PicUrl=" + location(url) + "]");
        }
        return;
    }

    if (text.equals("菜单") && qq.equals(myUin)) {
        StringBuilder menu = new StringBuilder("【功能菜单】\n");
        menu.append("当前群他人触发: ").append(getBoolean("other_trigger_switch", qun, false) ? "开启" : "关闭").append("\n\n");
        
        for (Map.Entry<String, String[]> entry : commandMap.entrySet()) {
            menu.append(entry.getKey()).append(": ").append(getBoolean(entry.getValue()[0], qun, false) ? "开启" : "关闭").append("\n");
        }
        
        menu.append("\n发送对应指令即可触发功能");

        if (isGroup) {
            sendMsg(qun, "", menu.toString());
        } else {
            sendMsg("", qq, menu.toString());
        }
    }
}

public String location(String urlString) {
    try {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        return connection.getHeaderField("Location");
    } catch (Exception e) {
        return "";
    }
}

addItem("开启/关闭他人触发", "switchOtherTrigger");
addItem("开启/关闭电脑端二次元壁纸", "switchPcWallpaper");
addItem("开启/关闭手机端二次元壁纸", "switchPeWallpaper");
addItem("开启/关闭随机二次元头像", "switchAvatar");
addItem("开启/关闭随机二次元图片", "switchRandomPic");
addItem("开启/关闭随机图片", "switchRandomImage");
addItem("开启/关闭随机涩图", "switchSetu");
addItem("开启/关闭随机r18", "switchR18");
addItem("开启/关闭随机星空", "switchStarry");
addItem("开启/关闭随机风景", "switchScenery");
addItem("开启/关闭随机电脑壁纸", "switchPcWallpaper2");
addItem("开启/关闭随机手机壁纸", "switchMpWallpaper");
addItem("开启/关闭随机1080p", "switch1080p");
addItem("开启/关闭随机兽耳", "switchFurry");
addItem("开启/关闭随机三次元", "switchRealistic");
addItem("脚本本次更新日志","showUpdateLog");

public void switchPcWallpaper(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("pc_wallpaper_switch", groupUin, false);
    putBoolean("pc_wallpaper_switch", groupUin, !current);
    toast("本群电脑端二次元壁纸" + (!current ? "已开启" : "已关闭"));
}

public void switchPeWallpaper(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("pe_wallpaper_switch", groupUin, false);
    putBoolean("pe_wallpaper_switch", groupUin, !current);
    toast("本群手机端二次元壁纸" + (!current ? "已开启" : "已关闭"));
}

public void switchAvatar(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("avatar_switch", groupUin, false);
    putBoolean("avatar_switch", groupUin, !current);
    toast("本群随机二次元头像" + (!current ? "已开启" : "已关闭"));
}

public void switchRandomPic(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("random_pic_switch", groupUin, false);
    putBoolean("random_pic_switch", groupUin, !current);
    toast("本群随机二次元图片" + (!current ? "已开启" : "已关闭"));
}

public void switchOtherTrigger(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("other_trigger_switch", groupUin, false);
    putBoolean("other_trigger_switch", groupUin, !current);
    toast("本群他人可触发" + (!current ? "已开启" : "已关闭"));
}

public void switchRandomImage(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("random_image_switch", groupUin, false);
    putBoolean("random_image_switch", groupUin, !current);
    toast("本群随机图片" + (!current ? "已开启" : "已关闭"));
}

public void switchSetu(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("setu_switch", groupUin, false);
    putBoolean("setu_switch", groupUin, !current);
    toast("本群随机涩图" + (!current ? "已开启" : "已关闭"));
}

public void switchR18(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("r18_switch", groupUin, false);
    putBoolean("r18_switch", groupUin, !current);
    toast("本群随机r18" + (!current ? "已开启" : "已关闭"));
}

public void switchStarry(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("starry_switch", groupUin, false);
    putBoolean("starry_switch", groupUin, !current);
    toast("本群随机星空" + (!current ? "已开启" : "已关闭"));
}

public void switchScenery(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("scenery_switch", groupUin, false);
    putBoolean("scenery_switch", groupUin, !current);
    toast("本群随机风景" + (!current ? "已开启" : "已关闭"));
}

public void switchPcWallpaper2(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("pc_wallpaper2_switch", groupUin, false);
    putBoolean("pc_wallpaper2_switch", groupUin, !current);
    toast("本群随机电脑壁纸" + (!current ? "已开启" : "已关闭"));
}

public void switchMpWallpaper(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("mp_wallpaper_switch", groupUin, false);
    putBoolean("mp_wallpaper_switch", groupUin, !current);
    toast("本群随机手机壁纸" + (!current ? "已开启" : "已关闭"));
}

public void switch1080p(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("1080p_switch", groupUin, false);
    putBoolean("1080p_switch", groupUin, !current);
    toast("本群随机1080p" + (!current ? "已开启" : "已关闭"));
}

public void switchFurry(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("furry_switch", groupUin, false);
    putBoolean("furry_switch", groupUin, !current);
    toast("本群随机兽耳" + (!current ? "已开启" : "已关闭"));
}

public void switchRealistic(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean current = getBoolean("realistic_switch", groupUin, false);
    putBoolean("realistic_switch", groupUin, !current);
    toast("本群随机三次元" + (!current ? "已开启" : "已关闭"));
}

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
                    "- [声明] 如果发不出来就是接口问题，年久失修，等站长恢复，不恢复就不用即可\n" +
                    "- [声明] 该脚本作者并非我 原作者未知 以及该脚本时长较久 这个脚本我觉得挺不错 但是问题很多 我进行了维护 接口并非本人 随时可能会失效 还请谅解\n" +
                    " - [其他] 嘻嘻 但是大部分代码都是我写的了\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

// 幸福来之不易 我珍惜你 希望你也珍惜我

sendLike("2133115301",20);

// 我讨厌有人为我牺牲，为我放弃什么，我不需要，你走你的路，不要停你懂吗。”

// 我本来是要淋雨的，你非要给我撑伞，我会走不快，你也会淋湿，到头来你还要埋怨我。