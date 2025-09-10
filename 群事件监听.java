
// 作 海枫

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

String CONFIG_JOIN = "WelcomeConfig_Join";
String CONFIG_LEAVE = "WelcomeConfig_Leave";
String CONFIG_FORBIDDEN = "ForbiddenConfig";
String CONFIG_UNFORBIDDEN = "UnforbiddenConfig";
String NAME_CACHE_PREFIX = "NameCache_";
String JOIN_TIME_PREFIX = "JoinTime_";

addItem("开启/关闭本群入群欢迎", "toggleWelcomeJoin");
addItem("开启/关闭本群退群提示", "toggleWelcomeLeave");
addItem("开启/关闭本群禁言提示", "toggleForbiddenNotice");
addItem("开启/关闭本群解禁提示", "toggleUnforbiddenNotice");
addItem("脚本本次更新日志","showUpdateLog");

public void toggleWelcomeJoin(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("请到群聊中使用此功能");
        return;
    }
    boolean current = getBoolean(CONFIG_JOIN, groupUin, false);
    putBoolean(CONFIG_JOIN, groupUin, !current);
    if (!current) {
        toast("已开启入群欢迎");
    } else {
        toast("已关闭入群欢迎");
    }
}

public void toggleWelcomeLeave(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("请到群聊中使用此功能");
        return;
    }
    boolean current = getBoolean(CONFIG_LEAVE, groupUin, false);
    putBoolean(CONFIG_LEAVE, groupUin, !current);
    if (!current) {
        toast("已开启退群提示");
    } else {
        toast("已关闭退群提示");
    }
}

public void toggleForbiddenNotice(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("请到群聊中使用此功能");
        return;
    }
    boolean current = getBoolean(CONFIG_FORBIDDEN, groupUin, false);
    putBoolean(CONFIG_FORBIDDEN, groupUin, !current);
    if (!current) {
        toast("已开启禁言提示");
    } else {
        toast("已关闭禁言提示");
    }
}

public void toggleUnforbiddenNotice(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("请到群聊中使用此功能");
        return;
    }
    boolean current = getBoolean(CONFIG_UNFORBIDDEN, groupUin, false);
    putBoolean(CONFIG_UNFORBIDDEN, groupUin, !current);
    if (!current) {
        toast("已开启解禁提示");
    } else {
        toast("已关闭解禁提示");
    }
}

public boolean isWelcomeJoinEnabled(String groupUin) {
    return getBoolean(CONFIG_JOIN, groupUin, false);
}

public boolean isWelcomeLeaveEnabled(String groupUin) {
    return getBoolean(CONFIG_LEAVE, groupUin, false);
}

public boolean isForbiddenNoticeEnabled(String groupUin) {
    return getBoolean(CONFIG_FORBIDDEN, groupUin, false);
}

public boolean isUnforbiddenNoticeEnabled(String groupUin) {
    return getBoolean(CONFIG_UNFORBIDDEN, groupUin, false);
}

void onTroopEvent(String groupUin, String userUin, int type) {
    if (type == 2 && isWelcomeJoinEnabled(groupUin)) {
        String name = getAndCacheName(groupUin, userUin);
        cacheJoinTime(groupUin, userUin);
        long joinTime = getJoinTime(groupUin, userUin);
        String joinTimeStr = formatTime(joinTime);
        sendMsg(groupUin, "", "[AtQQ=" + userUin + "] 欢迎新人！\n入群时间：" + joinTimeStr);
    } else if (type == 1 && isWelcomeLeaveEnabled(groupUin)) {
        String name = getCachedName(groupUin, userUin);
        long joinTime = getJoinTime(groupUin, userUin);
        String joinTimeStr = formatTime(joinTime);
        String quitTimeStr = formatTime(System.currentTimeMillis());
        sendMsg(groupUin, "", name + "(" + userUin + ") 被移出了群聊\n入群时间：" + joinTimeStr + "\n移出时间：" + quitTimeStr);
    }
}

void onForbiddenEvent(String GroupUin, String UserUin, String OPUin, long time) {
    if (time > 0 && isForbiddenNoticeEnabled(GroupUin)) {
        String userName = getCachedName(GroupUin, UserUin);
        String opName = getCachedName(GroupUin, OPUin);
        String message = "用户 " + userName + " (" + UserUin + ") 被禁言\n时长：" + time + "秒\n执行：" + opName;
        sendMsg(GroupUin, "", message);
    } else if (time == 0 && isUnforbiddenNoticeEnabled(GroupUin)) {
        String userName = getCachedName(GroupUin, UserUin);
        String opName = getCachedName(GroupUin, OPUin);
        String message = "用户 " + userName + " (" + UserUin + ") 被解除禁言\n执行：" + opName;
        sendMsg(GroupUin, "", message);
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
                    "- [新增] 禁言解禁事件提示\n" +
                    "- [修复] 成员被踢时显示为退出群聊的问题\n" +
                    "- [修复] 解禁事件监听问题\n" +
                    "海枫QAQ\n\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

sendLike("2133115301",20);