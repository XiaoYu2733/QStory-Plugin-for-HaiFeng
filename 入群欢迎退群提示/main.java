
// 海枫

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
import java.text.SimpleDateFormat;
import java.util.Date;

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
            long joinTime = System.currentTimeMillis();
            
            Object info = getMemberInfo(groupUin, userUin);
            if (info != null && info.Join_Time > 0) {
                long t = info.Join_Time;
                if (t < 1000000000000L) {
                    t = t * 1000;
                }
                joinTime = t;
            }
            
            putLong(JOIN_TIME_PREFIX + groupUin, userUin, joinTime);
            
            String joinTimeStr = formatTime(joinTime);
            sendMsg(groupUin, "", "[AtQQ=" + userUin + "] 欢迎新人！\n入群时间：" + joinTimeStr);
        } else if (type == 1) {
            String name = getString(NAME_CACHE_PREFIX + groupUin, userUin, userUin);
            long joinTime = getLong(JOIN_TIME_PREFIX + groupUin, userUin, 0L);
            String joinTimeStr = formatTime(joinTime);
            String quitTimeStr = formatTime(System.currentTimeMillis());
            sendMsg(groupUin, "", name + "(" + userUin + ") 退群了\n入群时间：" + joinTimeStr + "\n退群时间：" + quitTimeStr);
        }
    }
}

String formatTime(long timeMillis) {
    if (timeMillis <= 0) {
        return "未知时间";
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new Date(timeMillis));
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
        if (member == null) continue;
        
        String name = member.NickName;
        if (name == null || name.isEmpty()) {
            name = member.UserName;
        }
        if (name == null || name.isEmpty()) {
            name = member.UserUin;
        }
        if (name != null && member.UserUin != null) {
            putString(NAME_CACHE_PREFIX + groupUin, member.UserUin, name);
        }
        
        if (member.Join_Time > 0) {
            long t = member.Join_Time;
            if (t < 1000000000000L) {
                t = t * 1000;
            }
            if (member.UserUin != null) {
                putLong(JOIN_TIME_PREFIX + groupUin, member.UserUin, t);
            }
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
            builder.setMessage("修复入群退群时间逻辑");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

sendLike("2133115301",20);

// 但我们之间 连可能都没有 谈如何可以
// 从等你消息变成了等你的访客记录.