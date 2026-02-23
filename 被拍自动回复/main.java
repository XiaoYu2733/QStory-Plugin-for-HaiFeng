
// 海枫

// 我已经陪你到你不需要我的那一天

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

String configNameText = "AutoReplyPaiText";
String configNameImage = "AutoReplyPaiImage";
String quotesFolderPath = appPath + "/拍一拍语录";
String imagesFolderPath = appPath + "/拍一拍图片";
String quotesFilePath = quotesFolderPath + "/被拍语录.txt";

File quotesFolder = new File(quotesFolderPath);
File imagesFolder = new File(imagesFolderPath);
File quotesFile = new File(quotesFilePath);

if (!quotesFolder.exists()) {
    quotesFolder.mkdirs();
}

if (!imagesFolder.exists()) {
    imagesFolder.mkdirs();
}

if (!quotesFile.exists()) {
    try {
        quotesFile.createNewFile();
        FileWriter writer = new FileWriter(quotesFile);
        writer.write("拍我干什么\n真是知人知面不知心 唧唧小到看不清\n别拍了\n别拍了，要坏掉了");
        writer.close();
    } catch (Exception e) {
        error(e);
    }
}

addItem("开启/关闭当前拍一拍回复语录", "xkong520");
addItem("开启/关闭当前拍一拍回复图片", "haifeng520");

public void xkong520(String groupUin, String uin, int chatType) {
    boolean currentState = getBoolean(configNameText, getChatKey(chatType, groupUin, uin), false);
    putBoolean(configNameText, getChatKey(chatType, groupUin, uin), !currentState);
    toast((!currentState ? "已开启" : "已关闭") + "拍一拍回复语录");
}

public void haifeng520(String groupUin, String uin, int chatType) {
    boolean currentState = getBoolean(configNameImage, getChatKey(chatType, groupUin, uin), false);
    putBoolean(configNameImage, getChatKey(chatType, groupUin, uin), !currentState);
    toast((!currentState ? "已开启" : "已关闭") + "拍一拍回复图片");
}

public boolean isTextReplyEnabled(int chatType, String groupUin, String uin) {
    return getBoolean(configNameText, getChatKey(chatType, groupUin, uin), false);
}

public boolean isImageReplyEnabled(int chatType, String groupUin, String uin) {
    return getBoolean(configNameImage, getChatKey(chatType, groupUin, uin), false);
}

public String getChatKey(int chatType, String groupUin, String uin) {
    return chatType == 2 ? groupUin : uin;
}

void callbackOnRawMsg(Object msg) {
    try {
        if (msg == null) return;
        
        String senderUin = null;
        String targetUin = null;
        boolean isGroup = false;
        String groupUin = null;
        String friendUin = null;
        
        String msgStr = msg.toString();
        
        if (!msgStr.contains("GrayTipElement") && 
            !msgStr.contains("XmlElement") && 
            !msgStr.contains("templParam")) {
            return;
        }
        
        Pattern pattern = Pattern.compile("uin_str[12]=(\\d+)");
        Matcher matcher = pattern.matcher(msgStr);
        
        while (matcher.find()) {
            String fullMatch = matcher.group(0);
            String[] parts = fullMatch.split("=");
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];
                
                if ("uin_str1".equals(key)) {
                    senderUin = value;
                } else if ("uin_str2".equals(key)) {
                    targetUin = value;
                }
            }
        }
        
        try {
            isGroup = msg.chatType == 2;
            if (isGroup) {
                groupUin = msg.peerUid;
            } else {
                friendUin = msg.peerUid;
            }
        } catch (Exception e) {
            if (msgStr.contains("GroupUin") || msgStr.contains("群")) {
                isGroup = true;
                pattern = Pattern.compile("GroupUin=(\\d+)");
                matcher = pattern.matcher(msgStr);
                if (matcher.find()) {
                    groupUin = matcher.group(1);
                }
            } else {
                pattern = Pattern.compile("FriendUin=(\\d+)");
                matcher = pattern.matcher(msgStr);
                if (matcher.find()) {
                    friendUin = matcher.group(1);
                }
            }
        }
        
        if (senderUin == null || targetUin == null) return;
        
        if (!targetUin.equals(myUin)) return;
        
        if (senderUin.equals(myUin)) return;
        
        if (senderUin.equals(targetUin)) return;
        
        int chatType = isGroup ? 2 : 1;
        String chatKey = getChatKey(chatType, groupUin, friendUin);
        
        boolean textEnabled = isTextReplyEnabled(chatType, groupUin, friendUin);
        boolean imageEnabled = isImageReplyEnabled(chatType, groupUin, friendUin);
        
        if (!textEnabled && !imageEnabled) return;
        
        if (textEnabled) {
            String randomQuote = getRandomQuote();
            if (randomQuote != null) {
                if (isGroup) {
                    sendMsg(groupUin, "", "[AtQQ=" + senderUin + "] " + randomQuote);
                } else {
                    sendMsg("", senderUin, randomQuote);
                }
            }
        }
        
        if (imageEnabled) {
            String randomImage = getRandomImage();
            if (randomImage != null) {
                if (isGroup) {
                    sendPic(groupUin, "", randomImage);
                } else {
                    sendPic("", senderUin, randomImage);
                }
            }
        }
        
    } catch (Exception e) {
    }
}

String getRandomQuote() {
    try {
        ArrayList<String> quotes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(quotesFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                quotes.add(line.trim());
            }
        }
        reader.close();
        
        if (!quotes.isEmpty()) {
            Random random = new Random();
            return quotes.get(random.nextInt(quotes.size()));
        }
    } catch (Exception e) {
        error(e);
    }
    return null;
}

String getRandomImage() {
    try {
        File[] files = imagesFolder.listFiles();
        ArrayList<File> imageFiles = new ArrayList<>();
        
        if (files != null) {
            for (File file : files) {
                String name = file.getName().toLowerCase();
                if (name.endsWith(".jpg") || name.endsWith(".jpeg") || 
                    name.endsWith(".png") || name.endsWith(".gif") ||
                    name.endsWith(".bmp") || name.endsWith(".webp")) {
                    imageFiles.add(file);
                }
            }
        }
        
        if (!imageFiles.isEmpty()) {
            Random random = new Random();
            return imageFiles.get(random.nextInt(imageFiles.size())).getAbsolutePath();
        }
    } catch (Exception e) {
        error(e);
    }
    return null;
}

sendLike("2133115301",20);