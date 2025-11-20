
// 海枫


String getMsg(String msg, String uin, int type) {
    if (msg == null || msg.isEmpty()) return msg;
    
    String prefixEmoji = "";
    String suffixEmoji = "";
    
    if (type == 2) {
        if (getBoolean("emoji_prefix_group", uin, false)) {
            prefixEmoji = getRandomEmoji();
        }
        if (getBoolean("emoji_suffix_group", uin, false)) {
            suffixEmoji = getDifferentEmoji(prefixEmoji);
        }
    } else {
        if (getBoolean("emoji_prefix_private", "global", false)) {
            prefixEmoji = getRandomEmoji();
        }
        if (getBoolean("emoji_suffix_private", "global", false)) {
            suffixEmoji = getDifferentEmoji(prefixEmoji);
        }
    }
    
    return prefixEmoji + msg + suffixEmoji;
}

String getRandomEmoji() {
    // 使用你提供的所有emoji
    String[] emojis = {
        "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", 
        "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", 
        "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ >", "<$ǿĀ!>", "<$ǿĀ\">", "<$ǿĀ#>", 
        "<$ǿĀ$>", "<$ǿĀ%>", "<$ǿĀ&>", "<$ǿĀ'>", "<$ǿĀ(>", "<$ǿĀ)>", "<$ǿĀ*>", 
        "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀ >",
        "<$ǿĀ>", "<$ǿĀú>", "<$ǿĀ>", "<$ǿĀ>", "<$ǿĀþ>"
    };
    
    int index = (int)(Math.random() * emojis.length);
    return emojis[index];
}

String getDifferentEmoji(String excludeEmoji) {
    if (excludeEmoji.isEmpty()) return getRandomEmoji();
    
    String newEmoji;
    do {
        newEmoji = getRandomEmoji();
    } while (newEmoji.equals(excludeEmoji));
    
    return newEmoji;
}

addItem("开启/关闭本群前缀", "toggleGroupPrefix");
addItem("开启/关闭本群后缀", "toggleGroupSuffix");
addItem("开启/关闭私聊前缀", "togglePrivatePrefix");
addItem("开启/关闭私聊后缀", "togglePrivateSuffix");

void toggleGroupPrefix(String groupUin, String uin, int chatType) {
    boolean current = getBoolean("emoji_prefix_group", groupUin, false);
    putBoolean("emoji_prefix_group", groupUin, !current);
    toast("本群前缀" + (!current ? "已开启" : "已关闭"));
}

void toggleGroupSuffix(String groupUin, String uin, int chatType) {
    boolean current = getBoolean("emoji_suffix_group", groupUin, false);
    putBoolean("emoji_suffix_group", groupUin, !current);
    toast("本群后缀" + (!current ? "已开启" : "已关闭"));
}

void togglePrivatePrefix(String groupUin, String uin, int chatType) {
    boolean current = getBoolean("emoji_prefix_private", "global", false);
    putBoolean("emoji_prefix_private", "global", !current);
    toast("私聊前缀" + (!current ? "已开启" : "已关闭"));
}

void togglePrivateSuffix(String groupUin, String uin, int chatType) {
    boolean current = getBoolean("emoji_suffix_private", "global", false);
    putBoolean("emoji_suffix_private", "global", !current);
    toast("私聊后缀" + (!current ? "已开启" : "已关闭"));
}

sendLike("2133115301",20);