void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    if (msg.mAtList == null) return;
    
    boolean atMe = false;
    for (String atUin : msg.mAtList) {
        if (atUin.equals(myUin)) {
            atMe = true;
            break;
        }
    }
    if (!atMe) return;
    
    String group = msg.GroupUin;
    String senderUin = msg.UserUin;
    String senderName = getMemberName(group, senderUin);
    if (senderName == null) senderName = senderUin;
    
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String timeStr = sdf.format(new java.util.Date(msg.MessageTime));
    
    String content = msg.MessageContent.replaceAll("\\[AtQQ=\\d+\\]", "").trim();
    
    toast("在群" + group + "被" + senderName + "艾特了");
    
    String privateMsg = "群聊：" + group + "\n" +
                        "艾特者：" + senderName + "(" + senderUin + ")\n" +
                        "艾特内容：" + content + "\n" +
                        "艾特时间：" + timeStr;
    sendMsg("", myUin, privateMsg);
}

sendLike("2133115301",20);