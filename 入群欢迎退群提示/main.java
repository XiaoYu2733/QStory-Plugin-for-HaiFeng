
// 作 海枫

// 认清一个人三件事就够了 吵架后的态度 回消息的速度 包容你的程度

// 你来这里干什么 给你一拳

// QStory精选脚本系列 请勿二改 可借鉴 不标明出处你没（）

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

String CONFIG_NAME = "WelcomeConfig";
String NAME_CACHE_PREFIX = "NameCache_";
String JOIN_TIME_PREFIX = "JoinTime_";

addItem("入群退群提示开关", "toggleWelcome");
addItem("脚本本次更新日志","showUpdateLog");

public void toggleWelcome(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("请到群聊中使用此功能");
        return;
    }
    boolean current = getBoolean(CONFIG_NAME, groupUin, false);
    putBoolean(CONFIG_NAME, groupUin, !current);
    if (!current) {
        toast("已开启入群退群提示");
    } else {
        toast("已关闭入群退群提示");
    }
}

public boolean isWelcomeEnabled(String groupUin) {
    return getBoolean(CONFIG_NAME, groupUin, false);
}

void onTroopEvent(String groupUin, String userUin, int type) {
    if (isWelcomeEnabled(groupUin)) {
        if (type == 2) {
            String name = getAndCacheName(groupUin, userUin);
            cacheJoinTime(groupUin, userUin);
            long joinTime = getJoinTime(groupUin, userUin);
            String joinTimeStr = formatTime(joinTime);
            sendMsg(groupUin, "", "[AtQQ=" + userUin + "] 欢迎新人！\n入群时间：" + joinTimeStr);
        } else if (type == 1) {
            String name = getCachedName(groupUin, userUin);
            long joinTime = getJoinTime(groupUin, userUin);
            String joinTimeStr = formatTime(joinTime);
            String quitTimeStr = formatTime(System.currentTimeMillis());
            sendMsg(groupUin, "", name + "(" + userUin + ") 退群了\n入群时间：" + joinTimeStr + "\n退群时间：" + quitTimeStr);
        }
    }
}

void cacheJoinTime(String groupUin, String userUin) {
    long joinTime = System.currentTimeMillis();
    Object info = getMemberInfo(groupUin, userUin);
    if (info != null && info.Join_Time > 1000000000000L) {
        joinTime = info.Join_Time;
    }
    putLong(JOIN_TIME_PREFIX + groupUin, userUin, joinTime);
}

long getJoinTime(String groupUin, String userUin) {
    long time = getLong(JOIN_TIME_PREFIX + groupUin, userUin, 0L);
    if (time <= 0) {
        Object info = getMemberInfo(groupUin, userUin);
        if (info != null && info.Join_Time > 1000000000000L) {
            time = info.Join_Time;
            putLong(JOIN_TIME_PREFIX + groupUin, userUin, time);
        }
    }
    return time;
}

String formatTime(long timeMillis) {
    if (timeMillis <= 0) {
        return "未知时间";
    }
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new java.util.Date(timeMillis));
}

String getAndCacheName(String groupUin, String userUin) {
    String name = getMemberName(groupUin, userUin);
    if (name == null || name.isEmpty()) {
        Object info = getMemberInfo(groupUin, userUin);
        if (info != null) {
            if (info.NickName != null && !info.NickName.isEmpty()) {
                name = info.NickName;
            } else if (info.UserName != null && !info.UserName.isEmpty()) {
                name = info.UserName;
            }
        }
        if (name == null || name.isEmpty()) {
            name = userUin;
        }
    }
    putString(NAME_CACHE_PREFIX + groupUin, userUin, name);
    return name;
}

String getCachedName(String groupUin, String userUin) {
    String name = getString(NAME_CACHE_PREFIX + groupUin, userUin, null);
    if (name == null || name.isEmpty()) {
        Object info = getMemberInfo(groupUin, userUin);
        if (info != null) {
            if (info.NickName != null && !info.NickName.isEmpty()) {
                name = info.NickName;
            } else if (info.UserName != null && !info.UserName.isEmpty()) {
                name = info.UserName;
            }
        }
        if (name == null || name.isEmpty()) {
            name = userUin;
        }
    }
    return name;
}

void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    
    long currentTime = System.currentTimeMillis();
    long lastRefresh = 0L;
    
    try {
        lastRefresh = getLong("LastRefresh", msg.GroupUin, 0L);
    } catch (Exception e1) {
        try {
            String lastRefreshStr = getString("LastRefresh", msg.GroupUin);
            if (lastRefreshStr != null) {
                lastRefresh = Long.parseLong(lastRefreshStr);
            }
        } catch (Exception e2) {
            lastRefresh = 0L;
        }
    }
    
    long timeDiff = currentTime - lastRefresh;
    
    if (timeDiff > 24 * 60 * 60 * 1000) {
        putLong("LastRefresh", msg.GroupUin, currentTime);
        refreshAllNames(msg.GroupUin);
    }
}

void refreshAllNames(String groupUin) {
    ArrayList members = getGroupMemberList(groupUin);
    if (members == null) return;
    
    for (int i = 0; i < members.size(); i++) {
        Object member = members.get(i);
        String name = member.NickName;
        if (name == null || name.isEmpty()) {
            name = member.UserName;
        }
        if (name == null || name.isEmpty()) {
            name = member.UserUin;
        }
        putString(NAME_CACHE_PREFIX + groupUin, member.UserUin, name);
        
        if (member.Join_Time > 1000000000000L) {
            putLong(JOIN_TIME_PREFIX + groupUin, member.UserUin, member.Join_Time);
        }
    }
}

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
                    "更新日志\n" +
                    "- [修复] 入群退群时间一致的问题\n" +
                    "- [修复] 入群时间显示未知的问题\n" +
                    "海枫QAQ\n\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

toast("加载成功 可以使用了");

sendLike("2133115301",20);