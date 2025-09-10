
// 作 海枫

String 退群拉黑目录 = appPath + "/退群拉黑/";
File 退群拉黑文件夹 = new File(退群拉黑目录);

if (!退群拉黑文件夹.exists()) {
    退群拉黑文件夹.mkdirs();
    toast("已创建退群拉黑文件夹");
}

addItem("退群拉黑开关", "退群拉黑开关方法", pluginID);

public void 退群拉黑开关方法(String qun)
{
    if("开".equals(getString(qun,"退群拉黑")))
    {
        putString(qun,"退群拉黑",null);
        toast("已关闭退群拉黑");
    }
    else{
        putString(qun,"退群拉黑","开");
        toast("已开启退群拉黑");
    }
}

public static void 简写(File ff, String a) {
    try {
        FileWriter f = new FileWriter(ff, true);
        BufferedWriter f1 = new BufferedWriter(f);
        f1.append(a);
        f1.newLine();
        f1.close();
        f.close();
    } catch (Exception e) {
        toast("文件写入失败: " + e);
    }
}

public static ArrayList 简取(File ff) {
    ArrayList list1 = new ArrayList();
    if (!ff.exists()) {
        try {
            ff.createNewFile();
        } catch (Exception e) {
            toast("创建文件失败: " + e);
        }
        return list1;
    }
    
    try {
        FileReader fr = new FileReader(ff);
        BufferedReader f2 = new BufferedReader(fr);
        String a;
        while ((a = f2.readLine()) != null) {
            list1.add(a);
        }
        fr.close();
        f2.close();
    } catch (Exception e) {
        toast("文件读取失败: " + e);
    }
    return list1;
}

public static void 简弃(File ff, String a) {
    ArrayList l1 = new ArrayList();
    l1.addAll(简取(ff));
    if (l1.contains(a)) {
        l1.remove(a);
    }
    
    try {
        FileWriter f = new FileWriter(ff);
        BufferedWriter f1 = new BufferedWriter(f);
        f1.write("");
        f1.close();
        f.close();
    } catch (Exception e) {
        toast("文件清空失败: " + e);
    }
    
    for (int i = 0; i < l1.size(); i++) {
        简写(ff, l1.get(i).toString());
    }
}

public File 获取黑名单文件(String 群号) {
    if (群号 == null || 群号.isEmpty()) {
        return null;
    }
    
    File 文件 = new File(退群拉黑目录 + 群号 + ".txt");
    if (!文件.exists()) {
        try {
            文件.createNewFile();
        } catch (Exception e) {
            toast("创建黑名单文件失败: " + e);
        }
    }
    return 文件;
}

public void 添加黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) return;
    
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 == null) return;
    
    ArrayList 当前名单 = 简取(黑名单文件);
    if (!当前名单.contains(QQ号)) {
        简写(黑名单文件, QQ号);
    }
}

public void 移除黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) return;
    
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 != null && 黑名单文件.exists()) {
        简弃(黑名单文件, QQ号);
    }
}

public boolean 检查黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) return false;
    
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 == null || !黑名单文件.exists()) return false;
    
    return 简取(黑名单文件).contains(QQ号);
}

public ArrayList 获取黑名单列表(String 群号) {
    if (群号 == null || 群号.isEmpty()) {
        return new ArrayList();
    }
    return 简取(获取黑名单文件(群号));
}

public boolean isAdmin(String GroupUin, String UserUin) {
    ArrayList groupList = getGroupList();
    for (int i = 0; i < groupList.size(); i++) {
        Object groupInfo = groupList.get(i);
        if (groupInfo.GroupUin.equals(GroupUin)) {
            return groupInfo.GroupOwner.equals(UserUin) || 
                   (groupInfo.AdminList != null && groupInfo.AdminList.contains(UserUin));
        }
    }
    return false;
}

public String getMemberName(String group, String uin) {
    return getGroupMemberNick(group, uin);
}

public void checkGroupMembersPeriodically() {
    new java.util.Timer().scheduleAtFixedRate(
        new java.util.TimerTask() {
            public void run() {
                checkAllGroups();
            }
        },
        0,
        60 * 1000 // 每1分钟读取一次群员列表，检测到黑名单用户执行踢黑，时间可自行更改
    );
}

public void checkAllGroups() {
    ArrayList groupList = getGroupList();
    for (int i = 0; i < groupList.size(); i++) {
        Object groupInfo = groupList.get(i);
        String groupUin = groupInfo.GroupUin;
        
        String switchState = getString(groupUin, "退群拉黑");
        if (switchState == null || !"开".equals(switchState)) {
            continue;
        }
        
        ArrayList blackList = 获取黑名单列表(groupUin);
        if (blackList.isEmpty()) {
            continue;
        }
        
        ArrayList memberList = getGroupMemberList(groupUin);
        
        boolean found = false;
        StringBuilder kickedUsers = new StringBuilder();
        for (int j = 0; j < memberList.size(); j++) {
            Object member = memberList.get(j);
            String uin = member.UserUin;
            if (blackList.contains(uin)) {
                kick(groupUin, uin, true);
                kickedUsers.append("\n").append(getMemberName(groupUin, uin)).append("(").append(uin).append(")");
                found = true;
            }
        }
        
        if (found) {
            toast("检测黑户已执行踢黑，QQ号:" + kickedUsers.toString());
        }
    }
}

public void onTroopEvent(String groupUin, String userUin, int type) {
    if (groupUin == null || groupUin.isEmpty()) return;
    
    String switchState = getString(groupUin, "退群拉黑");
    if (switchState == null || !"开".equals(switchState)) return;
    
    if (type == 1) {
        if (userUin.equals(myUin)) return;
        
        if (!isAdmin(groupUin, myUin)) {
            toast("无管理员权限，无法添加黑名单");
            return;
        }
        
        添加黑名单(groupUin, userUin);
        String log = "[" + getMemberName(groupUin, userUin) + "] " + userUin + " 退群，已加入黑名单";
        toast(log);
    }
}

public void onMsg(Object msg) {
    String qun = msg.GroupUin;
    String qq = msg.UserUin;

    if (msg.MessageContent.equals("开启退群拉黑")) {
        putString(qun, "退群拉黑", "开");
        sendMsg(qun, "", "退群拉黑已开启");
        return;
    }

    if (msg.MessageContent.equals("关闭退群拉黑")) {
        putString(qun, "退群拉黑", null);
        sendMsg(qun, "", "退群拉黑已关闭");
        return;
    }

    if (msg.MessageContent.equals("查看黑名单")) {
        ArrayList 名单 = 获取黑名单列表(qun);
        if (名单.isEmpty()) {
            sendMsg(qun, "", "本群黑名单为空");
        } else {
            String 名单文本 = "本群黑名单:\n";
            for (int i = 0; i < 名单.size(); i++) {
                String uin = 名单.get(i).toString();
                名单文本 += (i + 1) + ". " + getMemberName(qun, uin) + "(" + uin + ")\n";
            }
            sendMsg(qun, "", 名单文本);
        }
        return;
    }

    if (msg.MessageContent.startsWith("移除黑名单@") && msg.mAtList != null && msg.mAtList.size() > 0) {
        for (String uin : msg.mAtList) {
            移除黑名单(qun, uin);
        }
        sendMsg(qun, "", "已移除黑名单用户");
        return;
    }
}

sendLike("2133115301",20);

toast("加载成功 欢迎使用");