
//一份爱的价格是298日元

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

String CONFIG_PREFIX = "ForbiddenTracker_";
String STORAGE_FOLDER = "禁言追踪";

void onLoad() {
    createTrackerFolder();
    cleanExpiredRecords();
    toast("禁言追踪已加载");
}

void onUnLoad() {
    toast("禁言追踪已卸载");
}

void createTrackerFolder() {
    File folder = new File(appPath, STORAGE_FOLDER);
    if (!folder.exists()) {
        folder.mkdirs();
    }
}

String getGroupTxtPath(String groupUin) {
    return STORAGE_FOLDER + "/" + groupUin + ".txt";
}

Map<String, Record> readGroupTracker(String groupUin) {
    String path = getGroupTxtPath(groupUin);
    File file = new File(appPath, path);
    if (!file.exists()) {
        return new HashMap<>();
    }
    String content = readFileText(path);
    if (content == null || content.isEmpty()) {
        return new HashMap<>();
    }
    Map<String, Record> map = new HashMap<>();
    String[] lines = content.split("\n");
    for (String line : lines) {
        line = line.trim();
        if (line.isEmpty()) continue;
        String[] parts = line.split("_");
        if (parts.length == 3) {
            String uin = parts[0];
            int minutes = Integer.parseInt(parts[1]);
            long endTime = parseEndTimeStr(parts[2]);
            if (endTime > 0) {
                map.put(uin, new Record(minutes, endTime));
            }
        }
    }
    return map;
}

void writeGroupTracker(String groupUin, Map<String, Record> map) {
    String path = getGroupTxtPath(groupUin);
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Record> entry : map.entrySet()) {
        String uin = entry.getKey();
        Record rec = entry.getValue();
        String endTimeStr = formatTimestampToStr(rec.endTime);
        sb.append(uin).append("_").append(rec.minutes).append("_").append(endTimeStr).append("\n");
    }
    String fullPath = appPath + "/" + path;
    File file = new File(fullPath);
    File parent = file.getParentFile();
    if (parent != null && !parent.exists()) {
        parent.mkdirs();
    }
    writeTextToFile(path, sb.toString());
}

String formatTimestampToStr(long timestamp) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    return sdf.format(new Date(timestamp));
}

long parseEndTimeStr(String str) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        return sdf.parse(str).getTime();
    } catch (Exception e) {
        return 0;
    }
}

void cleanExpiredRecords() {
    File folder = new File(appPath, STORAGE_FOLDER);
    if (!folder.exists()) return;
    File[] files = folder.listFiles();
    if (files == null) return;
    int totalRemoved = 0;
    for (File file : files) {
        String name = file.getName();
        if (!name.endsWith(".txt")) continue;
        String groupUin = name.substring(0, name.length() - 4);
        Map<String, Record> map = readGroupTracker(groupUin);
        if (map.isEmpty()) continue;
        long now = System.currentTimeMillis();
        int before = map.size();
        map.entrySet().removeIf(entry -> entry.getValue().endTime <= now);
        int after = map.size();
        if (after < before) {
            totalRemoved += (before - after);
            writeGroupTracker(groupUin, map);
        }
    }
    if (totalRemoved > 0) {
        toast("已清理 " + totalRemoved + " 条过期禁言记录");
    }
}

boolean isTrackerEnabled(String groupUin) {
    return getBoolean(CONFIG_PREFIX, groupUin, false);
}

void setTrackerEnabled(String groupUin, boolean enabled) {
    putBoolean(CONFIG_PREFIX, groupUin, enabled);
}

void toggleTracker(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("请在群聊中使用此开关");
        return;
    }
    boolean current = isTrackerEnabled(groupUin);
    if (current) {
        setTrackerEnabled(groupUin, false);
        toast("已关闭本群禁言追踪");
    } else {
        setTrackerEnabled(groupUin, true);
        toast("已开启本群禁言追踪");
        refreshGroupForbiddenList(groupUin);
    }
}

void refreshGroupForbiddenList(String groupUin) {
    if (!isTrackerEnabled(groupUin)) return;
    try {
        ArrayList<Object> forbiddenList = getForbiddenList(groupUin);
        Map<String, Record> map = new HashMap<>();
        long now = System.currentTimeMillis();
        for (Object info : forbiddenList) {
            String userUin = getFieldString(info, "UserUin");
            long endTime = normalizeTime(getFieldLong(info, "Endtime")) * 1000;
            if (userUin == null || userUin.isEmpty()) continue;
            if (是代管(groupUin, userUin)) continue;
            long remaining = endTime - now;
            if (remaining >= 5 * 60 * 1000) {
                int minutes = (int)(remaining / 60000);
                map.put(userUin, new Record(minutes, endTime));
            }
        }
        writeGroupTracker(groupUin, map);
    } catch (Exception e) {
        error(e);
    }
}

public void forbiddenTraceOnForbiddenEvent(String groupUin, String userUin, String opUin, long time) {
    if (!isTrackerEnabled(groupUin)) return;
    if (是代管(groupUin, userUin)) {
        try {
            Map<String, Record> map = readGroupTracker(groupUin);
            if (map.containsKey(userUin)) {
                map.remove(userUin);
                writeGroupTracker(groupUin, map);
            }
        } catch (Exception e) {
            error(e);
        }
        return;
    }
    if (time < 300) return;
    try {
        Map<String, Record> map = readGroupTracker(groupUin);
        long endTime = System.currentTimeMillis() + time * 1000;
        int minutes = (int)(time / 60);
        map.put(userUin, new Record(minutes, endTime));
        writeGroupTracker(groupUin, map);
    } catch (Exception e) {
        error(e);
    }
}

public void forbiddenTraceOnTroopEvent(String groupUin, String userUin, int type) {
    if (!isTrackerEnabled(groupUin)) return;
    try {
        if (type == 2) {
            if (是代管(groupUin, userUin)) {
                Map<String, Record> map = readGroupTracker(groupUin);
                if (map.containsKey(userUin)) {
                    map.remove(userUin);
                    writeGroupTracker(groupUin, map);
                }
                return;
            }
            Map<String, Record> map = readGroupTracker(groupUin);
            Record rec = map.get(userUin);
            if (rec != null && rec.endTime > System.currentTimeMillis()) {
                int seconds = rec.minutes * 60;
                forbidden(groupUin, userUin, seconds);
                toast("用户 " + userUin + " 重新进群，已自动禁言 " + rec.minutes + " 分钟");
                map.remove(userUin);
                writeGroupTracker(groupUin, map);
            } else if (rec != null) {
                map.remove(userUin);
                writeGroupTracker(groupUin, map);
            }
        }
    } catch (Exception e) {
        error(e);
    }
}

public void forbiddenTraceOnMsg(Object msg) {
    if (!msg.IsGroup) return;
    String groupUin = msg.GroupUin;
    if (!isTrackerEnabled(groupUin)) return;

    String content = msg.MessageContent;
    String senderUin = msg.UserUin;
    boolean isSendByMe = msg.IsSend;
    ArrayList<String> atList = msg.mAtList;

    if (content.startsWith("删除禁言记录") && atList != null && !atList.isEmpty()) {
        if (!是主人(senderUin) && !是代管(groupUin, senderUin)) {
            return;
        }
        Map<String, Record> map = readGroupTracker(groupUin);
        boolean changed = false;
        for (String atUin : atList) {
            if (map.containsKey(atUin)) {
                map.remove(atUin);
                changed = true;
                sendMsg(groupUin, "", "已删除 " + atUin + " 的禁言记录");
            }
        }
        if (changed) {
            writeGroupTracker(groupUin, map);
        }
        return;
    }

    Map<String, Record> map = readGroupTracker(groupUin);
    Record rec = map.get(senderUin);
    if (rec == null) return;

    if (是代管(groupUin, senderUin)) {
        map.remove(senderUin);
        writeGroupTracker(groupUin, map);
        return;
    }

    long now = System.currentTimeMillis();
    if (now >= rec.endTime) {
        map.remove(senderUin);
        writeGroupTracker(groupUin, map);
    } else {
        int seconds = rec.minutes * 60;
        forbidden(groupUin, senderUin, seconds);
        revokeMsg(msg);
    }
}

String getFieldString(Object obj, String field) {
    try {
        java.lang.reflect.Field f = obj.getClass().getDeclaredField(field);
        f.setAccessible(true);
        return (String) f.get(obj);
    } catch (Exception e) {
        return null;
    }
}

long getFieldLong(Object obj, String field) {
    try {
        java.lang.reflect.Field f = obj.getClass().getDeclaredField(field);
        f.setAccessible(true);
        return f.getLong(obj);
    } catch (Exception e) {
        return 0;
    }
}

long normalizeTime(long time) {
    if (time > 1000000000000L) {
        return time / 1000;
    }
    return time;
}

class Record {
    int minutes;
    long endTime;
    Record(int minutes, long endTime) {
        this.minutes = minutes;
        this.endTime = endTime;
    }
}