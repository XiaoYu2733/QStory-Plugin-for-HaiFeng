
// 我的世界生病了 连崩溃都要考虑后果

import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Map;

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
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        FileWriter writer = new FileWriter(filePath);
        for (int i = 0; i < list.size(); i++) {
            writer.write((String)list.get(i) + "\n");
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
        File dir = new File(timeConfigPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String likeTimeFile = timeConfigPath + "/好友点赞时间.txt";
        String friendFireTimeFile = timeConfigPath + "/好友续火时间.txt";
        String groupFireTimeFile = timeConfigPath + "/群组续火时间.txt";
        
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

ArrayList getWordsFromNetwork(String url) {
    ArrayList wordList = new ArrayList();
    try {
        String content = httpGet(url);
        if (content != null && !content.trim().isEmpty()) {
            String[] lines = content.split("\\r?\\n");
            for (String line : lines) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    wordList.add(line);
                }
            }
        }
    } catch (Exception e) {
    }
    return wordList;
}

void initWordsFile(String filePath) {
    try {
        File file = new File(filePath);
        if (!file.exists()) {
            ArrayList wordList = getWordsFromNetwork("https://qstory.suzhelan.top/qzone_content.txt");
            if (!wordList.isEmpty()) {
                saveListToFile(filePath, wordList);
            }
        }
    } catch (Exception e) {
    }
}

void loadConfig() {
    File configDirFile = new File(configDir);
    if (!configDirFile.exists()) {
        configDirFile.mkdirs();
    }

    selectedFriendsForLike = loadListFromFile(likeFriendsPath);
    selectedFriendsForFire = loadListFromFile(friendFirePath);
    selectedGroupsForFire = loadListFromFile(groupFirePath);
    
    initWordsFile(friendFireWordsPath);
    ArrayList loadedFriendWords = loadListFromFile(friendFireWordsPath);
    if (!loadedFriendWords.isEmpty()) {
        friendFireWords = loadedFriendWords;
    }
    
    initWordsFile(groupFireWordsPath);
    ArrayList loadedGroupWords = loadListFromFile(groupFireWordsPath);
    if (!loadedGroupWords.isEmpty()) {
        groupFireWords = loadedGroupWords;
    }
    
    initTimeConfig();
    
    String likeTimeFile = timeConfigPath + "/好友点赞时间.txt";
    String friendFireTimeFile = timeConfigPath + "/好友续火时间.txt";
    String groupFireTimeFile = timeConfigPath + "/群组续火时间.txt";
    
    String loadedLikeTime = loadTimeFromFile(likeTimeFile);
    if (loadedLikeTime != null) {
        likeTime = loadedLikeTime;
    } else {
        likeTime = "00:00";
    }
    
    String loadedFriendFireTime = loadTimeFromFile(friendFireTimeFile);
    if (loadedFriendFireTime != null) {
        friendFireTime = loadedFriendFireTime;
    } else {
        friendFireTime = "00:00";
    }
    
    String loadedGroupFireTime = loadTimeFromFile(groupFireTimeFile);
    if (loadedGroupFireTime != null) {
        groupFireTime = loadedGroupFireTime;
    } else {
        groupFireTime = "00:00";
    }
    
    lastLikeDate = getString("DailyLike", "lastLikeDate", "");
    lastFriendFireDate = getString("KeepFire", "lastSendDate", "");
    lastGroupFireDate = getString("GroupFire", "lastSendDate", "");
}

void saveLikeFriends() {
    saveListToFile(likeFriendsPath, selectedFriendsForLike);
}

void saveFireFriends() {
    saveListToFile(friendFirePath, selectedFriendsForFire);
}

void saveFireGroups() {
    saveListToFile(groupFirePath, selectedGroupsForFire);
}

void saveTimeConfig() {
    String likeTimeFile = timeConfigPath + "/好友点赞时间.txt";
    String friendFireTimeFile = timeConfigPath + "/好友续火时间.txt";
    String groupFireTimeFile = timeConfigPath + "/群组续火时间.txt";
    
    saveTimeToFile(likeTimeFile, likeTime);
    saveTimeToFile(friendFireTimeFile, friendFireTime);
    saveTimeToFile(groupFireTimeFile, groupFireTime);
}

// 求求你不要出现在我的梦里 不然我醒来会哭的