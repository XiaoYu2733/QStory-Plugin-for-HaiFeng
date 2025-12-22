
// 作 海枫

import java.util.ArrayList;
import java.util.Random;

String[] replyUsers = {"指定QQ1","指定QQ2"};
boolean replySelf = true; // 回应自己 自己发信息会回应自己 false关闭 true开启
boolean replyOthers = false; // 回应他人 需要先设置指定QQ false关闭 true开启
boolean replyWhenAt = false; // 被艾特时回应 被他人艾特时会回应对方 false关闭 true开启
String[] emojiIds = {"66","277","318","319"}; // 这里填写表情id 爱心66 汪汪277 崇拜318 比心319 其他表情id自己通过特殊手段获取即可

// 版本新增 使用replyWithRandomOrder方法之后将你配置的emojiIds打乱顺序并全部发送
void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    
    String senderUin = msg.UserUin;
    boolean isMe = senderUin.equals(myUin);
    
    boolean isAtMe = false;
    if (msg.mAtList != null) {
        for (String atUin : msg.mAtList) {
            if (atUin.equals(myUin)) {
                isAtMe = true;
                break;
            }
        }
    }
    
    if (isMe && replySelf) {
        replyWithRandomOrder(msg);
        return;
    }
    
    if (replyWhenAt && isAtMe) {
        replyWithRandomOrder(msg);
        return;
    }
    
    if (replyOthers) {
        for (String user : replyUsers) {
            if (senderUin.equals(user)) {
                replyWithRandomOrder(msg);
                return;
            }
        }
    }
}

// 这里是将表情ID列表打乱顺序全部回应
void replyWithRandomOrder(Object msg) {
    if (emojiIds == null || emojiIds.length == 0) return;

    // 将数组转为列表
    java.util.List<String> list = new java.util.ArrayList<>(java.util.Arrays.asList(emojiIds));
    
    // 打乱表情包排序
    java.util.Collections.shuffle(list);
    
    // 遍历打乱后的列表，依次回应你配置的emojiIds
    for (String id : list) {
        if (id != null && id.matches("\\d+")) {
            replyEmoji(msg, id);
        }
    }
}

// 长按消息菜单回应 灵感来源自ouo模块
void onCreateMenu(Object msg) {
    if (msg.IsGroup) {
        addMenuItem("回应", "respondWithEmojis");
    }
}

void respondWithEmojis(Object msg) {
    String[] longPressEmojiIds = new String[200];
    for (int i = 0; i < 200; i++) {
        longPressEmojiIds[i] = String.valueOf(i + 1);
    }
    
    java.util.ArrayList<String> selectedEmojis = new java.util.ArrayList<>();
    java.util.ArrayList<Integer> usedIndexes = new java.util.ArrayList<>();
    
    for (int i = 0; i < 20; i++) {
        int randomIndex;
        do {
            randomIndex = (int)(Math.random() * longPressEmojiIds.length);
        } while (usedIndexes.contains(randomIndex));
        
        usedIndexes.add(randomIndex);
        selectedEmojis.add(longPressEmojiIds[randomIndex]);
    }
    
    for (String emojiId : selectedEmojis) {
        if (emojiId != null && emojiId.matches("\\d+")) {
            replyEmoji(msg, emojiId);
        }
    }
}