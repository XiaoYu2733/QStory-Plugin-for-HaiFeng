
// 作 海枫

// 人道洛阳花似锦 偏我来时不逢春

import java.io.*;
import java.util.HashSet;
import java.util.Set;

String globalStatus;
String currentOperation;

void initializeScript() {
    try {
        
        File whitelistDir = new File(appPath + "/whitelist");
        if (!whitelistDir.exists()) {
            whitelistDir.mkdirs();
        }
        
        String status = getString("global_status", "status");
        globalStatus = (status != null) ? status : "off";
        currentOperation = "";
        
        addItem("开启/关闭白名单解禁", "toggleGlobalSwitch");
        addItem("添加白名单用户", "prepareAddMember");
        addItem("删除白名单用户", "prepareRemoveMember");
        addItem("查看白名单用户", "viewWhiteList");
        addItem("开启/关闭自动解禁", "toggleAutoUnban");
        
        sendLike("2133115301", 20);
        
        String statusMsg = "脚本初始化完成\n全局状态: " + (globalStatus.equals("on") ? "开启" : "关闭");
        toast(statusMsg);
        
    } catch (Exception e) {
        toast("初始化失败: " + e.getMessage());
    }
}

initializeScript();
toast("白名单自动解禁Java加载成功");

boolean isGlobalEnabled() {
    return globalStatus != null && globalStatus.equals("on");
}

void toggleGlobalSwitch(String groupUin, String uin, int chatType) {
    globalStatus = globalStatus.equals("on") ? "off" : "on";
    putString("global_status", "status", globalStatus);
    toast("白名单解禁功能已" + (globalStatus.equals("on") ? "开启" : "关闭"));
}

void prepareAddMember(String groupUin, String uin, int chatType) {
    if (!isGlobalEnabled()) {
        toast("请先开启白名单解禁");
        return;
    }
    currentOperation = "add";
    toast("请在群聊中@要添加的成员");
}

void prepareRemoveMember(String groupUin, String uin, int chatType) {
    if (!isGlobalEnabled()) {
        toast("请先开启白名单解禁");
        return;
    }
    currentOperation = "remove";
    toast("请在群聊中@要移除的成员");
}

void viewWhiteList(String groupUin, String uin, int chatType) {
    if (!isGlobalEnabled()) {
        toast("请先开启白名单解禁");
        return;
    }
    
    File whitelistFile = new File(appPath + "/whitelist/" + groupUin + ".txt");
    if (!whitelistFile.exists()) {
        sendMsg(groupUin, "", "本群白名单为空");
        return;
    }
    
    try {
        BufferedReader reader = new BufferedReader(new FileReader(whitelistFile));
        StringBuilder message = new StringBuilder("本群白名单成员：\n");
        String member;
        while ((member = reader.readLine()) != null) {
            if (!member.isEmpty()) {
                String name = getMemberName(groupUin, member);
                message.append(name != null ? name : member).append(" (").append(member).append(")\n");
            }
        }
        reader.close();
        
        if (message.toString().equals("本群白名单成员：\n")) {
            sendMsg(groupUin, "", "本群白名单为空");
        } else {
            sendMsg(groupUin, "", message.toString());
        }
    } catch (IOException e) {
        sendMsg(groupUin, "", "读取白名单失败: " + e.getMessage());
    }
}

void toggleAutoUnban(String groupUin, String uin, int chatType) {
    if (!isGlobalEnabled()) {
        toast("请先开启白名单解禁");
        return;
    }
    
    boolean enabled = !isAutoUnbanEnabled(groupUin);
    setAutoUnbanStatus(groupUin, enabled);
    toast("自动解禁已" + (enabled ? "开启" : "关闭"));
}

void onMsg(Object msg) {
    if (!msg.IsGroup || !msg.UserUin.equals(myUin) || currentOperation.isEmpty()) return;
    
    if (msg.mAtList == null || msg.mAtList.size() == 0) {
        toast("请@要操作的用户");
        return;
    }
    
    for (int i = 0; i < msg.mAtList.size(); i++) {
        String atUin = (String) msg.mAtList.get(i);
        if (currentOperation.equals("add")) {
            addToWhitelist(msg.GroupUin, atUin);
            String name = getMemberName(msg.GroupUin, atUin);
            toast("已添加白名单: " + (name != null ? name : atUin));
        } else if (currentOperation.equals("remove")) {
            removeFromWhitelist(msg.GroupUin, atUin);
            String name = getMemberName(msg.GroupUin, atUin);
            toast("已移除白名单: " + (name != null ? name : atUin));
        }
    }
    currentOperation = "";
}

void addToWhitelist(String groupUin, String uin) {
    File whitelistFile = new File(appPath + "/whitelist/" + groupUin + ".txt");
    try {
        Set members = new HashSet();
        if (whitelistFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(whitelistFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    members.add(line);
                }
            }
            reader.close();
        }
        
        members.add(uin);
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(whitelistFile));
        for (Object member : members) {
            writer.write(member.toString() + "\n");
        }
        writer.close();
    } catch (IOException e) {
        toast("添加白名单失败: " + e.getMessage());
    }
}

void removeFromWhitelist(String groupUin, String uin) {
    File whitelistFile = new File(appPath + "/whitelist/" + groupUin + ".txt");
    if (!whitelistFile.exists()) return;
    
    try {
        Set members = new HashSet();
        BufferedReader reader = new BufferedReader(new FileReader(whitelistFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty() && !line.equals(uin)) {
                members.add(line);
            }
        }
        reader.close();
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(whitelistFile));
        for (Object member : members) {
            writer.write(member.toString() + "\n");
        }
        writer.close();
    } catch (IOException e) {
        toast("移除白名单失败: " + e.getMessage());
    }
}

void setAutoUnbanStatus(String groupUin, boolean enabled) {
    File statusFile = new File(appPath + "/whitelist/status.txt");
    try {
        Set enabledGroups = new HashSet();
        if (statusFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(statusFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    enabledGroups.add(line);
                }
            }
            reader.close();
        }
        
        if (enabled) {
            enabledGroups.add(groupUin);
        } else {
            enabledGroups.remove(groupUin);
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(statusFile));
        for (Object group : enabledGroups) {
            writer.write(group.toString() + "\n");
        }
        writer.close();
    } catch (IOException e) {
        toast("设置自动解禁状态失败: " + e.getMessage());
    }
}

void onForbiddenEvent(String GroupUin, String UserUin, String OPUin, long time) {

    toast("监听到禁言事件");
    
    if (!isGlobalEnabled()) {
        toast("全局开关未开启");
        return;
    }
    
    if (!isAutoUnbanEnabled(GroupUin)) {
        toast("自动解禁未开启");
        return;
    }
    
    if (time <= 0) {
        toast("禁言时间无效");
        return;
    }
    
    File whitelistFile = new File(appPath + "/whitelist/" + GroupUin + ".txt");
    if (!whitelistFile.exists()) {
        toast("白名单文件不存在");
        return;
    }
    
    try {
        Set whitelist = new HashSet();
        BufferedReader reader = new BufferedReader(new FileReader(whitelistFile));
        String member;
        while ((member = reader.readLine()) != null) {
            if (!member.trim().isEmpty()) {
                whitelist.add(member.trim());
            }
        }
        reader.close();
        
        if (whitelist.contains(UserUin)) {
            forbidden(GroupUin, UserUin, 0);
            toast("已自动解禁白名单成员");
        } else {
            toast("用户不在白名单中");
        }
    } catch (IOException e) {
        toast("检查白名单失败: " + e.getMessage());
    }
}

boolean isAutoUnbanEnabled(String groupUin) {
    File statusFile = new File(appPath + "/whitelist/status.txt");
    if (!statusFile.exists()) return false;
    
    try {
        BufferedReader reader = new BufferedReader(new FileReader(statusFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals(groupUin)) {
                reader.close();
                return true;
            }
        }
        reader.close();
    } catch (IOException e) {
    
    }
    return false;
}

// 如果心里面有满分的人 看谁都会差点意思