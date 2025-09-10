import android.app.Activity;
import android.app.AlertDialog;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.LinearLayout;

addItem("警告功能开关", "toggleWarnFunction");
addItem("加入群聊", "joinGroup");
addItem("使用方法", "showUsageDialog");
toast("警告功能加载成功！");

public void toggleWarnFunction(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("请在群聊中使用此功能");
        return;
    }
    boolean currentStatus = getBoolean("WarnEnable", groupUin, false);
    putBoolean("WarnEnable", groupUin, !currentStatus);
    toast("警告功能已" + (!currentStatus ? "开启" : "关闭"));
}

public void joinGroup(String groupUin, String uin, int chatType) {
    String groupLink = "https://qm.qq.com/q/B6gzp4dxBe";
    String message = "点击加入交流群: " + groupLink;
    if (chatType == 2) {
        sendMsg(groupUin, "", message);
    } else {
        sendMsg("", uin, message);
    }
}

public void showUsageDialog(String groupUin, String uin, int chatType) {
    try {
        Activity activity = getActivity();
        if (activity == null) {
            String usage = "使用方法：\n1. /warn @用户 原因 - 警告用户\n2. 警告 @用户 原因 - 警告用户\n3. /check @用户 - 查看警告次数\n4. /reset @用户 - 重置警告次数\n5. /ban @用户 原因 - 直接踢黑\n6. 回复消息输入「警告 原因」\n\n注意：\n- 仅管理员可用\n- 无法警告管理层\n- 警告两次禁言一天\n- 三次踢黑";
            if (chatType == 2) {
                sendMsg(groupUin, "", usage);
            } else {
                sendMsg("", uin, usage);
            }
            return;
        }
        
        String dialogContent = "警告功能使用方法：\n\n" +
                "1. /warn @用户 原因 - 警告用户\n" +
                "2. 警告 @用户 原因 - 警告用户\n" +
                "3. /check @用户 - 查看警告次数\n" +
                "4. /reset @用户 - 重置警告次数\n" +
                "5. /ban @用户 原因 - 直接踢黑\n" +
                "6. 回复消息输入「警告 原因」\n\n" +
                "注意：\n" +
                "- 仅管理员可用\n" +
                "- 无法警告管理层\n" +
                "- 警告两次禁言一天\n" +
                "- 三次踢黑";
        
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    TextView textView = new TextView(activity);
                    textView.setText(dialogContent);
                    textView.setTextSize(16);
                    textView.setTextColor(Color.parseColor("#000080"));
                    textView.setTextIsSelectable(true);

                    LinearLayout layout = new LinearLayout(activity);
                    layout.setPadding(50, 30, 50, 30);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    layout.addView(textView);

                    ScrollView scrollView = new ScrollView(activity);
                    scrollView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    scrollView.addView(layout);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    builder.setTitle("警告功能使用说明")
                        .setView(scrollView)
                        .setNegativeButton("关闭", null)
                        .show();
                } catch (Exception e) {
                    log("弹窗错误: " + e.toString());
                }
            }
        });
    } catch (Exception e) {
        log("显示使用方法弹窗失败: " + e.toString());
    }
}

public boolean isAdmin(String groupUin, String uin) {
    Object groupInfo = getGroupInfo(groupUin);
    if (groupInfo == null) return false;
    if (uin.equals(groupInfo.GroupOwner)) return true;
    if (groupInfo.AdminList != null) {
        for (String admin : groupInfo.AdminList) {
            if (uin.equals(admin)) return true;
        }
    }
    return false;
}

public void handleWarn(String groupUin, String targetUin, String reason) {
    String warnKey = groupUin + "_" + targetUin;
    int warnCount = getInt("WarnRecords", warnKey, 0) + 1;
    putInt("WarnRecords", warnKey, warnCount);
    
    String targetName = getMemberName(groupUin, targetUin);
    if (targetName == null) targetName = targetUin;
    
    if (warnCount == 2) {
        forbidden(groupUin, targetUin, 86400);
        sendMsg(groupUin, "", "[AtQQ=" + targetUin + "] 警告(2/3)\n原因:" + reason + "\n已禁言一天");
    } else if (warnCount >= 3) {
        kick(groupUin, targetUin, true);
        sendMsg(groupUin, "", "[AtQQ=" + targetUin + "] 已被踢黑");
        putInt("WarnRecords", warnKey, 0);
    } else {
        sendMsg(groupUin, "", "[AtQQ=" + targetUin + "] 警告(" + warnCount + "/3)\n原因:" + reason);
    }
}

public void onMsg(Object msg) {
    if (!msg.IsGroup || msg.IsChannel) return;
    
    String content = msg.MessageContent.trim();
    String groupUin = msg.GroupUin;
    String senderUin = msg.UserUin;
    
    if (content.equals("/admins")) {
        Object groupInfo = getGroupInfo(groupUin);
        if (groupInfo == null) return;
        
        StringBuilder admins = new StringBuilder("群主: ");
        admins.append(getMemberName(groupUin, groupInfo.GroupOwner));
        admins.append("\n管理员:\n");
        
        if (groupInfo.AdminList != null) {
            for (String admin : groupInfo.AdminList) {
                admins.append("• ").append(getMemberName(groupUin, admin)).append("\n");
            }
        }
        sendMsg(groupUin, "", admins.toString());
        return;
    }
    
    boolean isWarnCommand = 
        (msg.MessageType == 6 && content.startsWith("警告")) ||
        (content.startsWith("警告") && msg.mAtList != null) ||
        content.startsWith("/warn");
    
    if (isWarnCommand) {
        if (!getBoolean("WarnEnable", groupUin, false)) return;
        if (!isAdmin(groupUin, senderUin)) {
            toast("需要管理员权限");
            return;
        }
        
        String reason = "违反群规";
        if (msg.MessageType == 6) {
            reason = content.substring(2).trim();
            if (reason.isEmpty()) reason = "违反群规";
            handleWarn(groupUin, msg.ReplyTo, reason);
        } else {
            reason = content.replaceFirst("(警告|/warn)", "").replaceAll("\\[@.*?\\]", "").trim();
            if (reason.isEmpty()) reason = "违反群规";
            for (String targetUin : msg.mAtList) {
                if (targetUin.equals(myUin) || isAdmin(groupUin, targetUin)) continue;
                handleWarn(groupUin, targetUin, reason);
            }
        }
        return;
    }
    
    if (content.startsWith("/ban")) {
        if (!isAdmin(groupUin, senderUin)) {
            toast("需要管理员权限");
            return;
        }
        if (msg.mAtList == null) return;
        
        String reason = content.replaceFirst("/ban", "").trim();
        if (reason.isEmpty()) reason = "违反群规";
        
        for (String targetUin : msg.mAtList) {
            if (targetUin.equals(myUin) || isAdmin(groupUin, targetUin)) continue;
            kick(groupUin, targetUin, true);
            sendMsg(groupUin, "", "[AtQQ=" + targetUin + "] 已被踢黑\n原因:" + reason);
            putInt("WarnRecords", groupUin + "_" + targetUin, 0);
        }
        return;
    }
    
    if (content.startsWith("/check") || content.startsWith("/reset")) {
        if (!isAdmin(groupUin, senderUin)) {
            toast("需要管理员权限");
            return;
        }
        if (msg.mAtList == null) return;
        
        for (String targetUin : msg.mAtList) {
            String warnKey = groupUin + "_" + targetUin;
            if (content.startsWith("/check")) {
                int count = getInt("WarnRecords", warnKey, 0);
                sendMsg(groupUin, "", "[AtQQ=" + targetUin + "] 警告次数:" + count);
            } else {
                putInt("WarnRecords", warnKey, 0);
                sendMsg(groupUin, "", "[AtQQ=" + targetUin + "] 警告已重置");
            }
        }
    }
}

sendLike("2133115301",20);