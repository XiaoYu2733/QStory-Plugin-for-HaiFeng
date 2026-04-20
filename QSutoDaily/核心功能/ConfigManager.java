
// 再见面时 我们拥抱吧 在冬天 在下雪天

ArrayList loadListFromFile(String filePath) {
    ArrayList list = new ArrayList();
    try {
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    list.add(line);
                }
            }
            reader.close();
        }
    } catch (Exception e) {
    }
    return list;
}

void saveListToFile(String filePath, ArrayList list) {
    try {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        FileWriter writer = new FileWriter(filePath);
        for (int i = 0; i < list.size(); i++) {
            writer.write((String) list.get(i) + "\n");
        }
        writer.close();
    } catch (Exception e) {
    }
}

String loadTimeFromFile(String filePath) {
    try {
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String time = reader.readLine();
            reader.close();
            if (time != null && time.trim().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                return time.trim();
            }
        }
    } catch (Exception e) {
    }
    return null;
}

void saveTimeToFile(String filePath, String time) {
    try {
        File dir = new File(filePath).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileWriter writer = new FileWriter(filePath);
        writer.write(time);
        writer.close();
    } catch (Exception e) {
    }
}

void initTimeConfig() {
    try {
        File dir = new File(timeConfigDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String likeTimeFile = timeConfigDir + "/好友点赞时间.txt";
        String friendFireTimeFile = timeConfigDir + "/好友续火时间.txt";
        String groupFireTimeFile = timeConfigDir + "/群组续火时间.txt";
        
        if (!new File(likeTimeFile).exists()) {
            saveTimeToFile(likeTimeFile, "00:00");
        }
        if (!new File(friendFireTimeFile).exists()) {
            saveTimeToFile(friendFireTimeFile, "00:00");
        }
        if (!new File(groupFireTimeFile).exists()) {
            saveTimeToFile(groupFireTimeFile, "00:00");
        }
    } catch (Exception e) {
    }
}

public String getCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    return String.format("%04d-%02d-%02d", 
        calendar.get(Calendar.YEAR), 
        calendar.get(Calendar.MONTH) + 1, 
        calendar.get(Calendar.DAY_OF_MONTH));
}

public String getCurrentTime() {
    Calendar calendar = Calendar.getInstance();
    return String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
}

void loadConfig() {
    File configDir = new File(configRoot);
    if (!configDir.exists()) {
        configDir.mkdirs();
    }

    File accountDir = new File(currentAccountDir);
    if (!accountDir.exists()) {
        accountDir.mkdirs();
        toast("检测到新账号，已创建配置文件夹，请自行配置任务");
    }

    likeFriendList = loadListFromFile(likeFriendsFile);
    fireFriendList = loadListFromFile(fireFriendsFile);
    fireGroupList = loadListFromFile(fireGroupsFile);
    
    ArrayList loadedFriendWords = loadListFromFile(friendFireWordsFile);
    if (!loadedFriendWords.isEmpty()) {
        friendFireWords = loadedFriendWords;
    } else {
        friendFireWords.add("Ciallo～(∠・ω ＜)⌒☆");
        saveListToFile(friendFireWordsFile, friendFireWords);
    }
    
    ArrayList loadedGroupWords = loadListFromFile(groupFireWordsFile);
    if (!loadedGroupWords.isEmpty()) {
        groupFireWords = loadedGroupWords;
    } else {
        groupFireWords.add("Ciallo～(∠・ω ＜)⌒☆");
        saveListToFile(groupFireWordsFile, groupFireWords);
    }
    
    initTimeConfig();
    
    String likeTimeFile = timeConfigDir + "/好友点赞时间.txt";
    String friendFireTimeFile = timeConfigDir + "/好友续火时间.txt";
    String groupFireTimeFile = timeConfigDir + "/群组续火时间.txt";
    
    String loadedLikeTime = loadTimeFromFile(likeTimeFile);
    if (loadedLikeTime != null) {
        likeTime = loadedLikeTime;
    } else {
        likeTime = "00:00";
    }
    
    String loadedFriendFireTime = loadTimeFromFile(friendFireTimeFile);
    if (loadedFriendFireTime != null) {
        fireFriendTime = loadedFriendFireTime;
    } else {
        fireFriendTime = "00:00";
    }
    
    String loadedGroupFireTime = loadTimeFromFile(groupFireTimeFile);
    if (loadedGroupFireTime != null) {
        fireGroupTime = loadedGroupFireTime;
    } else {
        fireGroupTime = "00:00";
    }
    
    lastLikeDate = getString("DailyLike", "lastLikeDate", "");
    lastFireFriendDate = getString("KeepFire", "lastSendDate", "");
    lastFireGroupDate = getString("GroupFire", "lastSendDate", "");
}

void saveLikeFriends() {
    saveListToFile(likeFriendsFile, likeFriendList);
}

void saveFireFriends() {
    saveListToFile(fireFriendsFile, fireFriendList);
}

void saveFireGroups() {
    saveListToFile(fireGroupsFile, fireGroupList);
}

void saveTimeConfig() {
    String likeTimeFile = timeConfigDir + "/好友点赞时间.txt";
    String friendFireTimeFile = timeConfigDir + "/好友续火时间.txt";
    String groupFireTimeFile = timeConfigDir + "/群组续火时间.txt";
    
    saveTimeToFile(likeTimeFile, likeTime);
    saveTimeToFile(friendFireTimeFile, fireFriendTime);
    saveTimeToFile(groupFireTimeFile, fireGroupTime);
}