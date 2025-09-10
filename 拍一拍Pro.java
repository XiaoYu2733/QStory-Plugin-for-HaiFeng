
// 作 海枫

// 我不懂什么叫挽留 我只知道爱我的人不会离开我

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

String atPaiConfig = "AtPai";

// 配置指定人戳一戳 支持私聊和群聊（不是艾特回戳） 配置用户后该用户发一条消息就会戳一次 范围是群聊和私聊 和艾特回戳可能冲突
String[] targetUins = {"123456","1234567","12345678"};
// 黑名单 指定戳一戳用户不戳 范围群聊和私聊
String[] blacklistUins = {"123456","1234567","12345678"};

addItem("脚本本次更新日志", "showUpdateLog");

void onMsg(Object msg) {
    String senderUin = msg.UserUin;
    if (senderUin.equals(myUin)) return;
    
    for (String blackUin : blacklistUins) {
        if (senderUin.equals(blackUin)) {
            return;
        }
    }
    
    if (msg.IsGroup) {
        String groupUin = msg.GroupUin;
        
        if (msg.mAtList != null && msg.mAtList.contains(myUin)) {
            sendPai(groupUin, senderUin);
        }
        
        for (String targetUin : targetUins) {
            if (senderUin.equals(targetUin)) {
                sendPai(groupUin, senderUin);
                break;
            }
        }
    } else {
        for (String targetUin : targetUins) {
            if (senderUin.equals(targetUin)) {
                sendPai("", senderUin);
                break;
            }
        }
    }
}

public void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
            "更新日志\n\n" +
            "- [新增] 指定戳一戳用户 指定用户说一下就拍一下 支持多个QQ号\n" +
            "- [新增] 私聊戳一戳 好友需要自己在代码配置 配置后该好友如果有共同群 在群内发一条消息也会戳一次\n" +
            "- [新增] 黑名单 指定用户艾特你 不会戳\n\n" +
            "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

sendLike("2133115301",20);