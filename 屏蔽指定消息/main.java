
// 海枫

// 

import java.io.File;
import java.util.ArrayList;

// 配置群聊
String[] targetGroups = {"123756","87654321"};

void onMsg(Object msg) {
    if (!msg.IsGroup) {
        return;
    }
    
    String groupUin = msg.GroupUin;
    String messageContent = msg.MessageContent;
    String senderUin = msg.UserUin;
    
    boolean isTargetGroup = false;
    for (String targetGroup : targetGroups) {
        if (targetGroup.equals(groupUin)) {
            isTargetGroup = true;
            break;
        }
    }
    
    if (!isTargetGroup) {
        return;
    }
    
    if (!senderUin.equals(myUin)) {
        String trimmedContent = messageContent.trim();
        ArrayList<String> keywords = loadKeywords(groupUin);
        
        for (String keyword : keywords) {
            if (trimmedContent.equals(keyword)) {
                deleteMsg(msg);
                break;
            }
        }
        return;
    }
    
    if (messageContent.startsWith("添加关键词")) {
        if (messageContent.length() <= 5) {
            sendMsg(groupUin, "", "你的关键词呢？");
            return;
        }
        
        String keyword = messageContent.substring(5).trim();
        if (keyword.isEmpty()) {
            sendMsg(groupUin, "", "你的关键词呢？");
            return;
        }
        
        ArrayList<String> keywords = loadKeywords(groupUin);
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
            saveKeywords(groupUin, keywords);
        }
        sendMsg(groupUin, "", "已添加关键词：" + keyword);
    } else if (messageContent.startsWith("删除关键词")) {
        if (messageContent.length() <= 5) {
            sendMsg(groupUin, "", "你的关键词呢？");
            return;
        }
        
        String keyword = messageContent.substring(5).trim();
        if (keyword.isEmpty()) {
            sendMsg(groupUin, "", "你的关键词呢？");
            return;
        }
        
        ArrayList<String> keywords = loadKeywords(groupUin);
        if (keywords.remove(keyword)) {
            saveKeywords(groupUin, keywords);
            sendMsg(groupUin, "", "已删除关键词：" + keyword);
        } else {
            sendMsg(groupUin, "", "关键词不存在：" + keyword);
        }
    } else {
        String trimmedContent = messageContent.trim();
        ArrayList<String> keywords = loadKeywords(groupUin);
        
        for (String keyword : keywords) {
            if (trimmedContent.equals(keyword)) {
                deleteMsg(msg);
                break;
            }
        }
    }
}

ArrayList<String> loadKeywords(String groupUin) {
    ArrayList<String> keywords = new ArrayList<>();
    try {
        String dirPath = appPath + "/群聊关键词/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String filePath = dirPath + groupUin + ".txt";
        File file = new File(filePath);
        if (!file.exists()) {
            return keywords;
        }
        
        String content = readFileText(filePath);
        if (content != null && !content.isEmpty()) {
            String[] lines = content.split("\n");
            for (String line : lines) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    keywords.add(trimmedLine);
                }
            }
        }
    } catch (Exception e) {
        error(e);
    }
    return keywords;
}

void saveKeywords(String groupUin, ArrayList<String> keywords) {
    try {
        String dirPath = appPath + "/群聊关键词/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String filePath = dirPath + groupUin + ".txt";
        StringBuilder content = new StringBuilder();
        for (String keyword : keywords) {
            content.append(keyword).append("\n");
        }
        writeTextToFile(filePath, content.toString());
    } catch (Exception e) {
        error(e);
    }
}

sendLike("2133115301",20);