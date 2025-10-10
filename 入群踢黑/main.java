
// 海枫

// 科学解释不了的事情 那就交给爱吧

String configName = "入群踢黑开关";
addItem("开启/关闭本群入群踢黑", "toggleKickBlack");

public void toggleKickBlack(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("不支持私聊开启");
        return;
    }
    if (getBoolean(configName, groupUin, false)) {
        putBoolean(configName, groupUin, false);
        toast("已关闭群聊：" + groupUin + "，的入群踢黑");
    } else {
        putBoolean(configName, groupUin, true);
        toast("已开启群聊：" + groupUin + "，的入群踢黑");
    }
}

public boolean isKickBlackOpen(String groupUin) {
    return getBoolean(configName, groupUin, false);
}

void onTroopEvent(String groupUin, String userUin, int type) {
    if (type == 2 && isKickBlackOpen(groupUin)) {
        kick(groupUin, userUin, true);
    }
}

sendLike("2133115301",20);

// 树不会再议掉落的树叶 旧物必有新代替