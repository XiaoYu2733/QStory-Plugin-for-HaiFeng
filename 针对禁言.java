
// 作 海枫

String configName = "Group";

addItem("开启/关闭本群针对禁言", "switchFunction");

java.io.File targetDir = new java.io.File(appPath + "/针对禁言");
if (!targetDir.exists()) {
    targetDir.mkdirs();
}

java.io.File qqFile = new java.io.File(targetDir, "针对禁言QQ号.txt");
if (!qqFile.exists()) {
    try {
        qqFile.createNewFile();
    } catch (Exception e) {
        error(e);
    }
}

public void switchFunction(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("仅群聊可用");
        return;
    }
    boolean isOpen = getBoolean(configName, groupUin, false);
    putBoolean(configName, groupUin, !isOpen);
    toast("已" + (!isOpen ? "开启本群" : "关闭") + "针对禁言");
}

public boolean isFunctionOpen(String groupUin) {
    return getBoolean(configName, groupUin, false);
}

void onMsg(Object msg) {
    if (!msg.IsGroup || msg.IsSend) return;
    
    String text = msg.MessageContent;
    String senderQQ = msg.UserUin;
    String groupUin = msg.GroupUin;
    
    if (text.startsWith("添加禁言用户") && senderQQ.equals(myUin) && msg.mAtList.size() > 0) {
        for (String targetQQ : msg.mAtList) {
            addQQToFile(targetQQ);
        }
        toast("已添加禁言用户");
        return;
    }
    
    if (text.startsWith("删除禁言用户") && senderQQ.equals(myUin) && msg.mAtList.size() > 0) {
        for (String targetQQ : msg.mAtList) {
            removeQQFromFile(targetQQ);
        }
        toast("已删除禁言用户");
        return;
    }
    
    if (!isFunctionOpen(groupUin)) return;
    
    String targetQQ = msg.UserUin;
    boolean shouldBan = isQQInFile(targetQQ);
    
    if (shouldBan) {
        int banTime = getInt(configName, "禁言时间", 60);
        forbidden(groupUin, targetQQ, banTime);
        toast("已禁言" + targetQQ + " " + banTime + "秒");
    }
}

public boolean isQQInFile(String qq) {
    try {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(qqFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().equals(qq)) {
                reader.close();
                return true;
            }
        }
        reader.close();
    } catch (Exception e) {
        error(e);
    }
    return false;
}

public void addQQToFile(String qq) {
    if (isQQInFile(qq)) return;
    
    try {
        java.io.FileWriter writer = new java.io.FileWriter(qqFile, true);
        writer.write(qq + "\n");
        writer.close();
    } catch (Exception e) {
        error(e);
    }
}

public void removeQQFromFile(String qq) {
    try {
        java.util.ArrayList<String> qqList = new java.util.ArrayList<String>();
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(qqFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().equals(qq)) {
                qqList.add(line);
            }
        }
        reader.close();
        
        java.io.FileWriter writer = new java.io.FileWriter(qqFile);
        for (String savedQQ : qqList) {
            writer.write(savedQQ + "\n");
        }
        writer.close();
    } catch (Exception e) {
        error(e);
    }
}

sendLike("2133115301",20);