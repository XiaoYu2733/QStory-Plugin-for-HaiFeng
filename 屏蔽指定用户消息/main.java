
// 海枫

// 自行配置指定屏蔽用户的QQ号
String[] targetUsers = {"123453","234567","345678"};

void onMsg(Object msg) {
    String senderUin = msg.UserUin;
    
    for (String targetUser : targetUsers) {
        if (targetUser.equals(senderUin)) {
            deleteMsg(msg);
            break;
        }
    }
}

sendLike("2133115301",20);