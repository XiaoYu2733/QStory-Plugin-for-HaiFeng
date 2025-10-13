
// 海枫

// 等太阳升起时就把昨天忘掉

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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// 支持多个用户 请用 , 和 "" 隔开
String atPaiConfig = "AtPai";
// 配置指定人戳一戳 支持私聊和群聊（不是艾特回戳） 配置用户后该用户发一条消息就会戳一次 范围是群聊和私聊 和艾特回戳可能冲突
String[] targetUins = {"123456","107464738","12345678"};
// 黑名单 指定戳一戳用户不戳 范围群聊和私聊
String[] blacklistUins = {"1633946103","951691255","2190951350","2479437177"};

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

// 既然有人陪你了 那我就悄悄退场了
public void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n更新日志\n\n- [新增] 指定戳一戳用户 指定用户说一下就拍一下 支持多个QQ号\n- [新增] 私聊戳一戳 好友需要自己在代码配置 配置后该好友如果有共同群 在群内发一条消息也会戳一次\n- [新增] 黑名单 指定用户艾特你 不会戳\n- [新增] 自动回拍 当有人拍你时自动回拍对方\n\n反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

void callbackOnRawMsg(Object msg) {
    try {
        if (msg == null) return;
        
        String senderUin = null;
        String targetUin = null;
        boolean isGroup = false;
        String groupUin = null;
        
        String msgStr = msg.toString();
        
        if (!msgStr.contains("GrayTipElement") && 
            !msgStr.contains("XmlElement") && 
            !msgStr.contains("templParam")) {
            return;
        }
        
        Pattern pattern = Pattern.compile("uin_str[12]=(\\d+)");
        Matcher matcher = pattern.matcher(msgStr);
        
        while (matcher.find()) {
            String fullMatch = matcher.group(0);
            String[] parts = fullMatch.split("=");
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];
                
                if ("uin_str1".equals(key)) {
                    senderUin = value;
                } else if ("uin_str2".equals(key)) {
                    targetUin = value;
                }
            }
        }
        
        try {
            isGroup = msg.chatType == 2;
            if (isGroup) {
                groupUin = msg.peerUid;
            }
        } catch (Exception e) {
            if (msgStr.contains("GroupUin") || msgStr.contains("群")) {
                isGroup = true;
                pattern = Pattern.compile("GroupUin=(\\d+)");
                matcher = pattern.matcher(msgStr);
                if (matcher.find()) {
                    groupUin = matcher.group(1);
                }
            }
        }
        
        if (senderUin == null || targetUin == null) return;
        
        if (!targetUin.equals(myUin)) return;
        
        if (senderUin.equals(myUin)) return;
        
        if (senderUin.equals(targetUin)) return;
        
        for (String blackUin : blacklistUins) {
            if (senderUin.equals(blackUin)) {
                return;
            }
        }
        
        if (isGroup && groupUin != null) {
            sendPai(groupUin, senderUin);
        } else {
            sendPai("", senderUin);
        }
        
    } catch (Exception e) {
    }
}

sendLike("2133115301",20);

// 但我们之间 连可能都没有 谈如何可以
// 从等你消息变成了等你的访客记录.