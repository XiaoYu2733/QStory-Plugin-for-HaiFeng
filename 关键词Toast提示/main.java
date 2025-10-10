
// 作 海枫

// 暖味期太甜了 聊天太频繁了 突然的断联让我没有身份问个明白……
String keywordDir = appPath + "/关键词/";
File dir = new File(keywordDir);
if (!dir.exists()) {
    dir.mkdirs();
}

// 怀念的其实不是你 而是没有你的时候
addItem("关键词提醒开关", "toggleKeywordReminder");

// 如果缘分够的话 我想陪你到最后 如果缘分太浅 那陪你走一段路也是好的
public void toggleKeywordReminder(String groupUin, String uin, int chatType) {
    if (chatType == 2) {
        boolean currentStatus = getBoolean("KeywordSwitch", groupUin, false);
        putBoolean("KeywordSwitch", groupUin, !currentStatus);
        toast("关键词提醒已" + (!currentStatus ? "开启" : "关闭"));
    } else {
        toast("请在群聊中使用");
    }
}

// 爱就像止痛药 短暂幸福后就是延迟的痛苦
public void onMsg(Object msg) {
    if (msg.IsGroup && !msg.IsSend) {
        String groupUin = msg.GroupUin;
        if (getBoolean("KeywordSwitch", groupUin, false)) {
            java.util.List keywords = getKeywords(groupUin);
            String content = msg.MessageContent;
            
            for (int i = 0; i < keywords.size(); i++) {
                String keyword = (String) keywords.get(i);
                if (content.contains(keyword)) {
                    toast("检测关键词: " + keyword + "\n发送者: " + msg.SenderNickName + 
                          "(" + msg.UserUin + ")\n群号: " + groupUin);
                    break;
                }
            }
        }
    }
    
    if (msg.IsSend && msg.IsGroup) {
        String content = msg.MessageContent;
        String groupUin = msg.GroupUin;
        
        if (content.startsWith("添加关键词 ")) {
            String keyword = content.substring(6).trim();
            if (!keyword.isEmpty()) {
                if (addKeyword(groupUin, keyword)) {
                    sendMsg(groupUin, "", "已添加关键词: " + keyword);
                } else {
                    sendMsg(groupUin, "", "添加失败，请勿重复添加");
                }
            }
        }
        
        else if (content.startsWith("删除关键词 ")) {
            String keyword = content.substring(6).trim();
            if (!keyword.isEmpty()) {
                if (removeKeyword(groupUin, keyword)) {
                    sendMsg(groupUin, "", "已删除关键词: " + keyword);
                } else {
                    sendMsg(groupUin, "", "删除失败，根本没有这个关键词");
                }
            }
        }
        // 清空关键词
        else if (content.equals("清空关键词")) {
            if (clearKeywords(groupUin)) {
                sendMsg(groupUin, "", "已清空本群关键词");
            } else {
                sendMsg(groupUin, "", "暂无本群关键词");
            }
        }
        
        else if (content.equals("查看关键词")) {
            java.util.List keywords = getKeywords(groupUin);
            if (keywords.isEmpty()) {
                sendMsg(groupUin, "", "暂无本群关键词");
            } else {
                StringBuilder list = new StringBuilder("当前关键词:\n");
                for (int i = 0; i < keywords.size(); i++) {
                    list.append("- ").append(keywords.get(i)).append("\n");
                }
                sendMsg(groupUin, "", list.toString());
            }
        }
    }
}

// 不知道永远是多远 只要你愿意我就陪你
private java.util.List getKeywords(String groupUin) {
    java.util.List keywords = new java.util.ArrayList();
    try {
        File file = new File(keywordDir + groupUin + ".txt");
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    keywords.add(trimmed);
                }
            }
            reader.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return keywords;
}

// 时间只会过得越来越快，我们的距离也越来越远
private boolean addKeyword(String groupUin, String keyword) {
    try {
        File file = new File(keywordDir + groupUin + ".txt");
        java.util.List keywords = getKeywords(groupUin);
        
        if (keywords.contains(keyword)) {
            return false;
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(keyword);
        writer.newLine();
        writer.close();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// 我也还没成年，难过跟谁说，委屈跟谁说
private boolean removeKeyword(String groupUin, String keyword) {
    try {
        File file = new File(keywordDir + groupUin + ".txt");
        if (!file.exists()) return false;
        
        java.util.List keywords = getKeywords(groupUin);
        if (!keywords.contains(keyword)) return false;
        
        keywords.remove(keyword);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < keywords.size(); i++) {
            writer.write((String) keywords.get(i));
            writer.newLine();
        }
        writer.close();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// 想要留住雪花 可在掌心里 只会融化的更快
private boolean clearKeywords(String groupUin) {
    try {
        File file = new File(keywordDir + groupUin + ".txt");
        if (file.exists()) {
            return file.delete();
        }
        return false;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// 山有木兮木有枝 心悦君兮君不知
sendLike("2133115301",20);

// 人道洛阳花似锦 偏我来时不逢春
toast("关键词Toast提示Java加载成功");