
// 海枫

// 一想到和你没结果了 以后的日子都没有你了 就感觉心口一阵一阵的疼 留住你和放下你 我一个也做不到

String getMsg(String msg, String uin, int type) {
    if (msg == null || msg.isEmpty()) return msg;
    
    String prefixEmoji = getRandomEmoji();
    String suffixEmoji = getDifferentEmoji(prefixEmoji);
    
    return prefixEmoji + msg + suffixEmoji;
}

String getRandomEmoji() {
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

sendLike("2133115301",20);