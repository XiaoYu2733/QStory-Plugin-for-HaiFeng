
// 作 海枫

// 眼泪是人最纯真的东西 流尽了人就变得冷漠了

String CONFIG_NAME = "AutoReplyConfig";
String FORBIDDEN_CONFIG_NAME = "AutoForbiddenConfig";
String REPLY_CONTENT = "艾特我干嘛"; // 艾特回复内容 自己改

addItem("开启/关闭本群艾特自动回复", "toggleAutoReply");
addItem("开启/关闭本群艾特自动禁言", "toggleAutoForbidden");

public void toggleAutoReply(String groupUin, String userUin, int chatType) {
    if (chatType == 2) {
        boolean current = getBoolean(CONFIG_NAME, groupUin, false);
        putBoolean(CONFIG_NAME, groupUin, !current);
        toast(groupUin + (!current ? " 已开启艾特自动回复" : " 已关闭艾特自动回复"));
    }
}

// 原来你和谁聊天都那么开心 我恨你

public void toggleAutoForbidden(String groupUin, String userUin, int chatType) {
    if (chatType == 2) {
        boolean current = getBoolean(FORBIDDEN_CONFIG_NAME, groupUin, false);
        putBoolean(FORBIDDEN_CONFIG_NAME, groupUin, !current);
        toast(groupUin + (!current ? " 已开启艾特自动禁言" : " 已关闭艾特自动禁言"));
    }
}

// 直到后来我看见你爱别人的样子 才发现我从未真正走进你心里

void onMsg(Object msg) {
    if (shouldForbid(msg)) {
        forbidden(msg.GroupUin, msg.UserUin, 60); // 艾特禁言时间 单位秒
    }
    
    if (shouldReply(msg)) {
        sendMsg(msg.GroupUin, "", REPLY_CONTENT);
    }
}

// 是你先接近我的 也是你先不喜欢我的

boolean shouldReply(Object msg) {
    return msg.IsGroup &&
          !msg.IsSend &&
          msg.mAtList.contains(myUin) &&
          getBoolean(CONFIG_NAME, msg.GroupUin, false);
}

boolean shouldForbid(Object msg) {
    return msg.IsGroup &&
          !msg.IsSend &&
          msg.mAtList.contains(myUin) &&
          getBoolean(FORBIDDEN_CONFIG_NAME, msg.GroupUin, false);
}

sendLike("2133115301",20);

toast("艾特自动回复加载成功\n艾特自动禁言加载成功");