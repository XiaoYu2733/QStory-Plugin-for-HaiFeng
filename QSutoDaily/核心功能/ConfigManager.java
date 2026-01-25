
// 我的世界生病了 连崩溃都要考虑后果

ArrayList loadListFromFile(String 文件路径) {
    ArrayList 列表 = new ArrayList();
    try {
        File 文件 = new File(文件路径);
        if (文件.exists()) {
            BufferedReader 读取器 = new BufferedReader(new FileReader(文件));
            String 行;
            while ((行 = 读取器.readLine()) != null) {
                行 = 行.trim();
                if (!行.isEmpty()) {
                    列表.add(行);
                }
            }
            读取器.close();
        }
    } catch (Exception e) {
    }
    return 列表;
}

void saveListToFile(String 文件路径, ArrayList 列表) {
    try {
        File 文件 = new File(文件路径);
        File 父目录 = 文件.getParentFile();
        if (!父目录.exists()) {
            父目录.mkdirs();
        }
        FileWriter 写入器 = new FileWriter(文件路径);
        for (int i = 0; i < 列表.size(); i++) {
            写入器.write((String)列表.get(i) + "\n");
        }
        写入器.close();
    } catch (Exception e) {
    }
}

String loadTimeFromFile(String 文件路径) {
    try {
        File 文件 = new File(文件路径);
        if (文件.exists()) {
            BufferedReader 读取器 = new BufferedReader(new FileReader(文件));
            String 时间 = 读取器.readLine();
            读取器.close();
            if (时间 != null && 时间.trim().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                return 时间.trim();
            }
        }
    } catch (Exception e) {
    }
    return null;
}

void saveTimeToFile(String 文件路径, String 时间) {
    try {
        File 目录 = new File(文件路径).getParentFile();
        if (!目录.exists()) {
            目录.mkdirs();
        }
        FileWriter 写入器 = new FileWriter(文件路径);
        写入器.write(时间);
        写入器.close();
    } catch (Exception e) {
    }
}

void initTimeConfig() {
    try {
        File 目录 = new File(花叶落飘落);
        if (!目录.exists()) {
            目录.mkdirs();
        }
        
        String 点赞时间文件 = 花叶落飘落 + "/好友点赞时间.txt";
        String 好友续火时间文件 = 花叶落飘落 + "/好友续火时间.txt";
        String 群组续火时间文件 = 花叶落飘落 + "/群组续火时间.txt";
        
        if (!new File(点赞时间文件).exists()) {
            saveTimeToFile(点赞时间文件, "00:00");
        }
        if (!new File(好友续火时间文件).exists()) {
            saveTimeToFile(好友续火时间文件, "00:00");
        }
        if (!new File(群组续火时间文件).exists()) {
            saveTimeToFile(群组续火时间文件, "00:00");
        }
    } catch (Exception e) {
    }
}

public String getCurrentDate() {
    Calendar 日历 = Calendar.getInstance();
    return String.format("%04d-%02d-%02d", 
        日历.get(Calendar.YEAR), 
        日历.get(Calendar.MONTH) + 1, 
        日历.get(Calendar.DAY_OF_MONTH));
}

public String getCurrentTime() {
    Calendar 日历 = Calendar.getInstance();
    return String.format("%02d:%02d", 日历.get(Calendar.HOUR_OF_DAY), 日历.get(Calendar.MINUTE));
}

void loadConfig() {
    File 配置目录 = new File(花飘言子);
    if (!配置目录.exists()) {
        配置目录.mkdirs();
    }

    落叶叶子叶落子飘 = loadListFromFile(叶花落叶叶落花叶);
    落言花飘言落言 = loadListFromFile(飘飘叶花飘落飘落);
    飘飘花言飘飘 = loadListFromFile(子叶言飘子言花言花叶);
    
    ArrayList 加载的好友语录 = loadListFromFile(落叶花花飘言子子飘花);
    if (!加载的好友语录.isEmpty()) {
        飘飘叶飘 = 加载的好友语录;
    } else {
        飘飘叶飘.add("Ciallo～(∠・ω ＜)⌒☆");
        saveListToFile(落叶花花飘言子子飘花, 飘飘叶飘);
    }
    
    ArrayList 加载的群组语录 = loadListFromFile(子叶花花花飘);
    if (!加载的群组语录.isEmpty()) {
        叶落花落 = 加载的群组语录;
    } else {
        叶落花落.add("Ciallo～(∠・ω ＜)⌒☆");
        saveListToFile(子叶花花花飘, 叶落花落);
    }
    
    initTimeConfig();
    
    String 点赞时间文件 = 花叶落飘落 + "/好友点赞时间.txt";
    String 好友续火时间文件 = 花叶落飘落 + "/好友续火时间.txt";
    String 群组续火时间文件 = 花叶落飘落 + "/群组续火时间.txt";
    
    String 加载的点赞时间 = loadTimeFromFile(点赞时间文件);
    if (加载的点赞时间 != null) {
        叶飘叶落言叶子叶落子 = 加载的点赞时间;
    } else {
        叶飘叶落言叶子叶落子 = "00:00";
    }
    
    String 加载的好友续火时间 = loadTimeFromFile(好友续火时间文件);
    if (加载的好友续火时间 != null) {
        飘飘花花 = 加载的好友续火时间;
    } else {
        飘飘花花 = "00:00";
    }
    
    String 加载的群组续火时间 = loadTimeFromFile(群组续火时间文件);
    if (加载的群组续火时间 != null) {
        子言花言飘叶落飘 = 加载的群组续火时间;
    } else {
        子言花言飘叶落飘 = "00:00";
    }
    
    飘花叶言飘花 = getString("DailyLike", "lastLikeDate", "");
    言子言叶花子落 = getString("KeepFire", "lastSendDate", "");
    落叶子子子叶 = getString("GroupFire", "lastSendDate", "");
}

void saveLikeFriends() {
    saveListToFile(叶花落叶叶落花叶, 落叶叶子叶落子飘);
}

void saveFireFriends() {
    saveListToFile(飘飘叶花飘落飘落, 落言花飘言落言);
}

void saveFireGroups() {
    saveListToFile(子叶言飘子言花言花叶, 飘飘花言飘飘);
}

void saveTimeConfig() {
    String 点赞时间文件 = 花叶落飘落 + "/好友点赞时间.txt";
    String 好友续火时间文件 = 花叶落飘落 + "/好友续火时间.txt";
    String 群组续火时间文件 = 花叶落飘落 + "/群组续火时间.txt";
    
    saveTimeToFile(点赞时间文件, 叶飘叶落言叶子叶落子);
    saveTimeToFile(好友续火时间文件, 飘飘花花);
    saveTimeToFile(群组续火时间文件, 子言花言飘叶落飘);
}

// 求求你不要出现在我的梦里 不然我醒来会哭的