
// 我也知道跟你没有结果 但想想能陪你一点时光也不错

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public void 添加联盟群组(String groupUin) {
    try {
        if (groupUin == null || groupUin.isEmpty()) return;
        ArrayList 当前群组 = 简取(联盟群组文件);
        if (!当前群组.contains(groupUin)) {
            简写(联盟群组文件, groupUin);
        }
    } catch (Exception e) {}
}

public void 移除联盟群组(String groupUin) {
    try {
        if (groupUin == null || groupUin.isEmpty()) return;
        简弃(联盟群组文件, groupUin);
    } catch (Exception e) {}
}

public boolean 是联盟群组(String groupUin) {
    try {
        if (groupUin == null || groupUin.isEmpty()) return false;
        if (联盟群组文件 == null || !联盟群组文件.exists()) return false;
        ArrayList 联盟群组 = 简取(联盟群组文件);
        return 联盟群组.contains(groupUin);
    } catch (Exception e) {
        return false;
    }
}

public void 添加封禁用户(String userUin, String reason) {
    new Thread(new Runnable() {
        public void run() {
            try {
                if (userUin == null || userUin.isEmpty()) return;
                String 封禁记录 = userUin + "|" + (reason == null ? "" : reason);
                ArrayList 当前封禁 = 简取(封禁列表文件);
                boolean 已存在 = false;
                for (int i = 0; i < 当前封禁.size(); i++) {
                    String 记录 = (String)当前封禁.get(i);
                    if (记录 != null && 记录.startsWith(userUin + "|")) {
                        当前封禁.set(i, 封禁记录);
                        已存在 = true;
                        break;
                    }
                }
                if (!已存在) {
                    简写(封禁列表文件, 封禁记录);
                } else {
                    全弃(封禁列表文件);
                    for (int i = 0; i < 当前封禁.size(); i++) {
                        Object item = 当前封禁.get(i);
                        if (item != null) {
                            简写(封禁列表文件, item.toString());
                        }
                    }
                }
                
                String 当前群组 = getCurrentGroupUin();
                if (当前群组 != null && !当前群组.isEmpty()) {
                    ArrayList 成员列表 = getGroupMemberList(当前群组);
                    if (成员列表 != null) {
                        ArrayList 成员列表副本 = safeCopyList(成员列表);
                        for (int j = 0; j < 成员列表副本.size(); j++) {
                            Object 成员 = 成员列表副本.get(j);
                            if (成员 != null && 成员.UserUin != null && 成员.UserUin.equals(userUin)) {
                                unifiedKick(当前群组, userUin, true);
                                break;
                            }
                        }
                    }
                }
                
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            ArrayList 联盟群组列表 = 简取(联盟群组文件);
                            if (联盟群组列表 == null || 联盟群组列表.isEmpty()) return;
                            
                            ArrayList 联盟群组列表副本 = safeCopyList(联盟群组列表);
                            for (int i = 0; i < 联盟群组列表副本.size(); i++) {
                                String 群号 = (String)联盟群组列表副本.get(i);
                                if (群号 == null || 群号.isEmpty()) continue;
                                
                                ArrayList 成员列表 = getGroupMemberList(群号);
                                if (成员列表 != null) {
                                    ArrayList 成员列表副本 = safeCopyList(成员列表);
                                    for (int j = 0; j < 成员列表副本.size(); j++) {
                                        Object 成员 = 成员列表副本.get(j);
                                        if (成员 != null && 成员.UserUin != null && 成员.UserUin.equals(userUin)) {
                                            unifiedKick(群号, userUin, true);
                                            Thread.sleep(100);
                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            
                        }
                    }
                }).start();
                
            } catch (Exception e) {}
        }
    }).start();
}

public void 移除封禁用户(String userUin) {
    try {
        if (userUin == null || userUin.isEmpty()) return;
        ArrayList 当前封禁 = 简取(封禁列表文件);
        ArrayList 新列表 = new ArrayList();
        for (int i = 0; i < 当前封禁.size(); i++) {
            String 记录 = (String)当前封禁.get(i);
            if (记录 != null && !记录.startsWith(userUin + "|")) {
                新列表.add(记录);
            }
        }
        全弃(封禁列表文件);
        for (int i = 0; i < 新列表.size(); i++) {
            Object item = 新列表.get(i);
            if (item != null) {
                简写(封禁列表文件, item.toString());
            }
        }
    } catch (Exception e) {}
}

public boolean 是封禁用户(String userUin) {
    try {
        if (userUin == null || userUin.isEmpty()) return false;
        if (封禁列表文件 == null || !封禁列表文件.exists()) return false;
        ArrayList 封禁列表 = 简取(封禁列表文件);
        for (int i = 0; i < 封禁列表.size(); i++) {
            String 记录 = (String)封禁列表.get(i);
            if (记录 != null && 记录.startsWith(userUin + "|")) {
                return true;
            }
        }
        return false;
    } catch (Exception e) {
        return false;
    }
}

public String 获取封禁理由(String userUin) {
    try {
        if (userUin == null || userUin.isEmpty()) return null;
        if (封禁列表文件 == null || !封禁列表文件.exists()) return null;
        ArrayList 封禁列表 = 简取(封禁列表文件);
        for (int i = 0; i < 封禁列表.size(); i++) {
            String 记录 = (String)封禁列表.get(i);
            if (记录 != null && 记录.startsWith(userUin + "|")) {
                String[] 部分 = 记录.split("\\|", 2);
                if (部分.length > 1 && !部分[1].isEmpty()) {
                    return 部分[1];
                }
                break;
            }
        }
        return null;
    } catch (Exception e) {
        return null;
    }
}

public void 处理联盟指令(Object msg) {
    try {
        if (msg == null || msg.MessageContent == null) return;
        
        String 故 = msg.MessageContent;
        String qq = msg.UserUin;
        String groupUin = msg.GroupUin;
        
        if (!msg.IsGroup) return;
        
        if ((故.startsWith("/addgroup") || 故.startsWith("!addgroup")) && qq.equals(myUin)) {
            添加联盟群组(groupUin);
            String 回复 = "已添加该群组为联盟\n联盟：简洁群管\n联盟创建者：" + myUin + "\n群组：" + groupUin;
            sendReply(groupUin, msg, 回复);
            return;
        }
        
        if ((故.startsWith("/ungroup") || 故.startsWith("!ungroup")) && qq.equals(myUin)) {
            移除联盟群组(groupUin);
            String 回复 = "已取消该群组为联盟\n联盟：简洁群管\n联盟创建者：" + myUin + "\n群组：" + groupUin;
            sendReply(groupUin, msg, 回复);
            return;
        }
        
        if (!是联盟群组(groupUin)) return;
        
        if (!有权限操作(groupUin, qq, "")) return;
        
        if (故.startsWith("/fban") || 故.startsWith("!fban")) {
            String[] 部分 = 故.split(" ", 3);
            if (部分.length < 2) {
                sendReply(groupUin, msg, "使用方法：/fban QQ号 [理由]");
                return;
            }
            
            String 目标QQ = 部分[1];
            String 理由 = 部分.length > 2 ? 部分[2] : null;
            
            if (!目标QQ.matches("[0-9]{4,10}")) {
                sendReply(groupUin, msg, "QQ号格式错误");
                return;
            }
            
            if (是封禁用户(目标QQ)) {
                sendReply(groupUin, msg, "该用户已经被封禁，请勿再次封禁！");
                return;
            }
            
            添加封禁用户(目标QQ, 理由);
            
            String 回复 = "新联盟封禁\n联盟：简洁群管\n联盟管理员：" + 名(qq) + "\n用户：" + 名(目标QQ) + "\n用户 ID：" + 目标QQ;
            if (理由 != null && !理由.isEmpty()) {
                回复 += "\n理由：" + 理由;
            }
            
            sendReply(groupUin, msg, 回复);
            return;
        }
        
        if (故.startsWith("/unfban") || 故.startsWith("!unfban")) {
            String[] 部分 = 故.split(" ", 3);
            if (部分.length < 2) {
                sendReply(groupUin, msg, "使用方法：/unfban QQ号 [原因]");
                return;
            }
            
            String 目标QQ = 部分[1];
            String 原因 = 部分.length > 2 ? 部分[2] : null;
            
            if (!是封禁用户(目标QQ)) {
                sendReply(groupUin, msg, "该用户未被联盟封禁");
                return;
            }
            
            移除封禁用户(目标QQ);
            
            String 回复 = "新联盟解除封禁\n联盟：简洁群管\n联盟管理员：" + 名(qq) + "\n用户：" + 名(目标QQ) + "\n用户 ID：" + 目标QQ;
            if (原因 != null && !原因.isEmpty()) {
                回复 += "\n原因：" + 原因;
            }
            
            sendReply(groupUin, msg, 回复);
            return;
        }
    } catch (Exception e) {
        error(e);
    }
}