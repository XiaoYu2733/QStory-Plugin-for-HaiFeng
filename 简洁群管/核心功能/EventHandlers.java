
// 如果我们不能在一起 那就喜欢到不喜欢你为止

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import android.app.Activity;
import android.app.AlertDialog;
import android.widget.EditText;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.graphics.drawable.GradientDrawable;

Object msgLock = new Object();

public String 获取群名(String groupUin) {
    if (groupUin == null || groupUin.isEmpty()) return "未知群";
    try {
        Object groupInfo = getGroupInfo(groupUin);
        if (groupInfo != null && groupInfo.GroupName != null) {
            return groupInfo.GroupName;
        }
    } catch (Exception e) {
    }
    return groupUin;
}

public void onMsg(Object msg) {
    if (msg == null) return;

    synchronized (msgLock) {
        try {
            String msgContent = msg.MessageContent;
            String userUin = msg.UserUin;
            String groupUin = msg.GroupUin;
            if (msgContent == null) return;

            ArrayList mAtListCopy = (msg.mAtList != null) ? safeCopyList(msg.mAtList) : new ArrayList();

            if (msgContent.startsWith("我要头衔") && "开".equals(getString(groupUin, "自助头衔"))) {
                setTitle(groupUin, userUin, msgContent.substring(4));
                return;
            }

            int 艾特禁言时间 = getInt("艾特禁言时间配置", "时间", 2592000);
            if ("开".equals(getString(groupUin, "艾特禁言")) && atMe(msg)) {
                unifiedForbidden(groupUin, userUin, 艾特禁言时间);
                return;
            }

            boolean isMyUin = userUin != null && userUin.equals(myUin);
            boolean isDelegate = 是代管(groupUin, userUin);
            
            if (!msg.IsGroup) return;

            if (msgContent.equals("群管功能")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                String qqVersion = "QQVersion未知";
                try {
                    qqVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                } catch (Exception e) {}
                
                String menu = "群管功能(QQVersion：" + qqVersion + "):\n" +
                        "禁@ 禁言@ 头衔@\n" +
                        "@+时间+天|分|秒\n" +
                        "解@ 踢@ 踢黑@\n" +
                        "禁/解(全体禁言/解禁) \n" +
                        "查看禁言列表\n" +
                        "全解(解所有人禁言)\n" +
                        "添加代管@ 删除代管@\n" +
                        "查看/清空 代管\n" +
                        "显示/隐藏 头衔|等级|标识\n" +
                        "开启/关闭退群拉黑\n" +
                        "查看/移除黑名单@\n" +
                        "开启/关闭自动解禁代管\n" +
                        "开启/关闭自助头衔" + (isGN(groupUin, "自助头衔") ? "✅" : "❌");
                sendMsg(groupUin, "", menu);
                return;
            }

            if (msgContent.equals("显示标识")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String result = SetTroopShowHonour(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 1);
                            sendMsg(groupUin, "", result);
                        } catch (Exception e) {}
                    }
                }).start();
                return;
            } else if (msgContent.equals("隐藏标识")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String result = SetTroopShowHonour(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 0);
                            sendMsg(groupUin, "", result);
                        } catch (Exception e) {}
                    }
                }).start();
                return;
            } else if (msgContent.equals("显示等级")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String result = SetTroopShowLevel(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 1);
                            sendMsg(groupUin, "", result);
                        } catch (Exception e) {}
                    }
                }).start();
                return;
            } else if (msgContent.equals("隐藏等级")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String result = SetTroopShowLevel(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 0);
                            sendMsg(groupUin, "", result);
                        } catch (Exception e) {}
                    }
                }).start();
                return;
            } else if (msgContent.equals("显示头衔")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String result = SetTroopShowTitle(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 1);
                            sendMsg(groupUin, "", result);
                        } catch (Exception e) {}
                    }
                }).start();
                return;
            } else if (msgContent.equals("隐藏头衔")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String result = SetTroopShowTitle(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 0);
                            sendMsg(groupUin, "", result);
                        } catch (Exception e) {}
                    }
                }).start();
                return;
            }

            if (msgContent.equals("开启自动解禁代管")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                if ("开".equals(getString("自动解禁代管配置", "开关"))) {
                    sendMsg(groupUin, "", "已经打开了");
                } else {
                    putString("自动解禁代管配置", "开关", "开");
                    sendMsg(groupUin, "", "已开启自动解禁代管");
                }
                return;
            } else if (msgContent.equals("关闭自动解禁代管")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                if ("开".equals(getString("自动解禁代管配置", "开关"))) {
                    putString("自动解禁代管配置", "开关", null);
                    sendMsg(groupUin, "", "已关闭自动解禁代管");
                } else {
                    sendMsg(groupUin, "", "未开启无法关闭");
                }
                return;
            }

            if (msgContent.equals("开启自助头衔")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                putString(groupUin, "自助头衔", "开");
                sendMsg(groupUin, "", "自助头衔已开启 大家可以发送 我要头衔xxx来获取头衔");
                return;
            } else if (msgContent.equals("关闭自助头衔")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                if ("开".equals(getString(groupUin, "自助头衔"))) {
                    putString(groupUin, "自助头衔", null);
                    sendMsg(groupUin, "", "自助头衔已关闭");
                } else {
                    sendMsg(groupUin, "", "未开启无法关闭");
                }
                return;
            }

            if (msgContent.equals("开启退群拉黑")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                putString(groupUin, "退群拉黑", "开");
                sendMsg(groupUin, "", "退群拉黑已开启");
                return;
            } else if (msgContent.equals("关闭退群拉黑")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                putString(groupUin, "退群拉黑", null);
                sendMsg(groupUin, "", "退群拉黑已关闭");
                return;
            }

            if (msgContent.equals("查看黑名单")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                ArrayList blackList = 获取黑名单列表(groupUin);
                if (blackList.isEmpty()) {
                    sendMsg(groupUin, "", "本群黑名单为空");
                } else {
                    StringBuilder sb = new StringBuilder("本群黑名单:\n");
                    for (int i = 0; i < blackList.size(); i++) {
                        Object item = blackList.get(i);
                        if (item == null) continue;
                        String u = item.toString();
                        sb.append(i + 1).append(". ").append(名(u)).append("(").append(u).append(")\n");
                    }
                    sendMsg(groupUin, "", sb.toString());
                }
                return;
            }

            if (msgContent.startsWith("移除黑名单@") && !mAtListCopy.isEmpty()) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                for (Object uin : mAtListCopy) {
                    if (uin != null) {
                        移除黑名单(groupUin, (String) uin);
                    }
                }
                sendMsg(groupUin, "", "已删黑该用户");
                return;
            }

            if (msgContent.equals("查看代管")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                File f = 获取代管文件();
                if (!f.exists()) sendMsg(groupUin, "", "当前没有代管");
                else {
                    String text = 组名(简取(f)).replace("]", "").replace("[", " ");
                    sendMsg(groupUin, "", "当前的代管如下:\n" + text);
                }
                return;
            }

            if (msg.MessageType == 6) { 
                String replyTo = msg.ReplyTo;
                if (replyTo != null && !replyTo.isEmpty()) {
                    if ("解".equals(msgContent) || "解禁".equals(msgContent)) {
                        if (!isMyUin && !是代管(groupUin, userUin)) return;
                        unifiedForbidden(groupUin, replyTo, 0);
                        return;
                    }

                    if (msgContent.startsWith("/dban") || msgContent.startsWith("!dban") || msgContent.startsWith("/ban") || msgContent.startsWith("!ban")) {
                        if (!isMyUin && !是代管(groupUin, userUin)) return;
                        if (检查代管保护(groupUin, replyTo, "踢黑")) return;
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            unifiedKick(groupUin, replyTo, true);
                            sendMsg(groupUin, "", "已踢出" + replyTo + "不会再收到该用户入群申请\n权限使用人：" + 名(userUin));
                        }
                        return;
                    }

                    if (msgContent.startsWith("/kick") || msgContent.startsWith("!kick")) {
                        if (!isMyUin && !是代管(groupUin, userUin)) return;
                        if (检查代管保护(groupUin, replyTo, "踢出")) return;
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            unifiedKick(groupUin, replyTo, false);
                            sendMsg(groupUin, "", "已踢出" + replyTo + "，此用户还可再次申请入群\n权限使用人：" + 名(userUin));
                        }
                        return;
                    }

                    if (msgContent.startsWith("/fban") || msgContent.startsWith("!fban")) {
                        if (!isMyUin && !是代管(groupUin, userUin)) return;
                        if (检查代管保护(groupUin, replyTo, "联盟封禁")) return;
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            String reason = null;
                            String[] parts = msgContent.split(" ", 2);
                            if (parts.length > 1 && !parts[1].trim().isEmpty()) reason = parts[1].trim();
                            
                            if (是封禁用户(replyTo)) {
                                sendReply(groupUin, msg, "该用户已经被封禁，请勿再次封禁！");
                                return;
                            }
                            
                            ArrayList members = getGroupMemberList(groupUin);
                            if (members != null) {
                                for (Object member : safeCopyList(members)) {
                                    try {
                                        java.lang.reflect.Field uinField = member.getClass().getDeclaredField("UserUin"); 
                                        uinField.setAccessible(true);
                                        Object uinObj = uinField.get(member);
                                        if (uinObj != null && uinObj.equals(replyTo)) {
                                            unifiedKick(groupUin, replyTo, true);
                                            break;
                                        }
                                    } catch (Exception e) {}
                                }
                            }
                            添加封禁用户(replyTo, reason);
                            sendReply(groupUin, msg, "新联盟封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(replyTo) + "\n用户 ID：" + replyTo + (reason != null ? "\n理由：" + reason : ""));
                        } else {
                            sendReply(groupUin, msg, "你没有权限操作该用户");
                        }
                        return;
                    }

                    if (msgContent.startsWith("/unfban") || msgContent.startsWith("!unfban")) {
                        if (!isMyUin && !是代管(groupUin, userUin)) return;
                        if (!是封禁用户(replyTo)) {
                            sendReply(groupUin, msg, "该用户未被联盟封禁");
                            return;
                        }
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            String reason = null;
                            String[] parts = msgContent.split(" ", 2);
                            if (parts.length > 1 && !parts[1].trim().isEmpty()) reason = parts[1].trim();
                            
                            移除封禁用户(replyTo);
                            sendReply(groupUin, msg, "新联盟解除封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(replyTo) + "\n用户 ID：" + replyTo + (reason != null ? "\n原因：" + reason : ""));
                        } else {
                            sendReply(groupUin, msg, "你没有权限操作该用户");
                        }
                        return;
                    }

                    boolean isBanCmd = msgContent.startsWith("禁") || msgContent.startsWith("禁言");
                    if (isBanCmd) {
                        if (!isMyUin && !是代管(groupUin, userUin)) return;
                        if (检查代管保护(groupUin, replyTo, "禁言")) return;
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            int banTime = 0;
                            String cause = "";
                            String cleanContent = msgContent;
                            
                            if (msgContent.contains(" ")) {
                                int lastIdx = msgContent.lastIndexOf(" ");
                                int firstIdx = msgContent.indexOf(" ");
                                if (lastIdx != firstIdx) {
                                    cause = "\n原因 : " + msgContent.substring(lastIdx + 1);
                                    cleanContent = msgContent.substring(0, lastIdx);
                                }
                            }
                            
                            if (cleanContent.matches(".*[零一二三四五六七八九十].*")) {
                                String timePart = cleanContent.substring(cleanContent.indexOf(" ") + 1);
                                String text = timePart.replaceAll("[^零一二三四五六七八九十百千万]", "");
                                banTime = get_time_int(cleanContent, CN_zh_int(text));
                            } else {
                                banTime = get_time(cleanContent);
                            }

                            if (banTime > 2592000) {
                                sendMsg(groupUin, "", "请控制在30天以内");
                            } else if (banTime > 0) {
                                unifiedForbidden(groupUin, replyTo, banTime);
                                if (!cause.isEmpty() || msgContent.startsWith("禁 ")) {
                                    sendMsg(groupUin, "", "已禁言 时长" + banTime + cause + "\n权限使用人：" + 名(userUin));
                                }
                            }
                        }
                        return;
                    }
                }
            }

            if (!mAtListCopy.isEmpty()) {
                if (msgContent.startsWith("解")) {
                    if (!isMyUin && !是代管(groupUin, userUin)) return;
                    for (Object uin : mAtListCopy) {
                        if (uin != null) {
                            unifiedForbidden(groupUin, (String) uin, 0);
                        }
                    }
                    return;
                }

                if (msgContent.startsWith("踢黑")) {
                    if (!isMyUin && !是代管(groupUin, userUin)) return;
                    boolean kicked = false;
                    for (Object uin : mAtListCopy) {
                        String u = (String) uin;
                        if (u == null) continue;
                        if (检查代管保护(groupUin, u, "踢黑")) continue;
                        if (有权限操作(groupUin, userUin, u)) {
                            unifiedKick(groupUin, u, true);
                            kicked = true;
                        }
                    }
                    if (kicked) sendMsg(groupUin, "", "已踢出，不会再收到该用户入群申请\n权限使用人：" + 名(userUin));
                    return;
                }

                if (msgContent.startsWith("踢")) {
                    if (!isMyUin && !是代管(groupUin, userUin)) return;
                    boolean kicked = false;
                    for (Object uin : mAtListCopy) {
                        String u = (String) uin;
                        if (u == null) continue;
                        if (检查代管保护(groupUin, u, "踢出")) continue;
                        if (有权限操作(groupUin, userUin, u)) {
                            unifiedKick(groupUin, u, false);
                            kicked = true;
                        }
                    }
                    if (kicked) sendMsg(groupUin, "", "踢出成功\n权限使用人：" + 名(userUin));
                    return;
                }

                if (msgContent.startsWith("头衔@")) {
                    if (!isMyUin && !是代管(groupUin, userUin)) return;
                    String title = msgContent.substring(msgContent.lastIndexOf(" ") + 1);
                    for (Object uin : mAtListCopy) {
                        if (uin != null) {
                            setTitle(groupUin, (String) uin, title);
                        }
                    }
                    return;
                }
                
                boolean isBan = msgContent.startsWith("禁");
                boolean isBanYan = msgContent.startsWith("禁言");
                if (isBan || isBanYan || msgContent.matches("^@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)+$") || msgContent.matches("^@?[\\s\\S]+[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒)+$")) {
                    if (!isMyUin && !是代管(groupUin, userUin)) return;
                    int banTime = 0;
                    if (msgContent.matches(".*[零一二三四五六七八九十].*")) {
                        String str = msgContent.substring(msgContent.lastIndexOf(" ") + 1);
                        String text = str.replaceAll("[天分时小时分钟秒]", "");
                        if (!text.isEmpty()) {
                            banTime = get_time_int(msgContent, CN_zh_int(text));
                        }
                    } else if (msgContent.matches(".*[0-9].*")) {
                         if (!Character.isDigit(msgContent.charAt(msgContent.length() - 1)) && !msgContent.endsWith("秒") && !msgContent.endsWith("分") && !msgContent.endsWith("时") && !msgContent.endsWith("天")) {
                             banTime = 2592000; 
                         } else {
                            String tStr = msgContent.substring(msgContent.lastIndexOf(" ") + 1);
                             if (tStr.matches("^[0-9]+$")) {
                                 banTime = Integer.parseInt(tStr) * 60;
                             } else {
                                 banTime = get_time(msgContent);
                             }
                         }
                    } else {
                         banTime = isBanYan ? 86400 : 2592000; 
                    }

                    if (banTime > 2592000) {
                        sendMsg(groupUin, "", "请控制在30天以内");
                    } else if (banTime > 0) {
                        for (Object uin : mAtListCopy) {
                            String u = (String) uin;
                            if (u == null) continue;
                            if (检查代管保护(groupUin, u, "禁言")) continue;
                            unifiedForbidden(groupUin, u, banTime);
                        }
                    }
                    return;
                }

                if (isMyUin) {
                    if (msgContent.startsWith("添加代管") || msgContent.startsWith("添加管理员") || msgContent.startsWith("设置代管") || msgContent.startsWith("添加老婆")) {
                        File f = 获取代管文件();
                        if (!f.exists()) {
                            try {
                                f.createNewFile();
                            } catch (Exception e) {}
                        }
                        ArrayList current = 简取(f);
                        StringBuilder sb = new StringBuilder();
                        for (Object uin : mAtListCopy) {
                            String u = (String) uin;
                            if (u == null) continue;
                            if (!jiandu(u, current)) {
                                简写(f, u);
                                sb.append(u).append(" ");
                            }
                        }
                        if (sb.length() > 0) sendMsg(groupUin, "", "已添加代管:\n" + sb.toString());
                        else sendMsg(groupUin, "", "以上代管已经添加过了");
                        return;
                    }
                    if (msgContent.startsWith("删除代管@") || msgContent.startsWith("删除管理员@")) {
                        File f = 获取代管文件();
                        if (f.exists()) {
                            StringBuilder sb = new StringBuilder();
                            ArrayList current = 简取(f);
                            for (Object uin : mAtListCopy) {
                                String u = (String) uin;
                                if (u == null) continue;
                                if (jiandu(u, current)) {
                                    简弃(f, u);
                                    sb.append(u).append(" ");
                                }
                            }
                            sendMsg(groupUin, "", "已删除管理员:\n" + sb.toString());
                        }
                        return;
                    }
                }
                
                if (是联盟群组(groupUin)) {
                     if (msgContent.startsWith("/fban") || msgContent.startsWith("!fban")) {
                        if (mAtListCopy.isEmpty()) return;
                        Object uinObj = mAtListCopy.get(0);
                        if (uinObj == null) return;
                        String uin = uinObj.toString();
                        if (!isMyUin && !是代管(groupUin, userUin)) return;
                        if (检查代管保护(groupUin, uin, "联盟封禁")) return;
                        if (是封禁用户(uin)) {
                            sendReply(groupUin, msg, "该用户已经被封禁，请勿再次封禁！");
                            return;
                        }
                        if (有权限操作(groupUin, userUin, uin)) {
                            String reason = null;
                            String[] parts = msgContent.split(" ", 2);
                            if (parts.length > 1 && !parts[1].trim().isEmpty()) reason = parts[1].trim();
                            
                            unifiedKick(groupUin, uin, true);
                            添加封禁用户(uin, reason);
                            sendReply(groupUin, msg, "新联盟封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(uin) + "\n用户 ID：" + uin + (reason != null ? "\n理由：" + reason : ""));
                        } else {
                             sendReply(groupUin, msg, "你没有权限操作该用户");
                        }
                        return;
                     }
                     if (msgContent.startsWith("/unfban") || msgContent.startsWith("!unfban")) {
                        if (mAtListCopy.isEmpty()) return;
                        Object uinObj = mAtListCopy.get(0);
                        if (uinObj == null) return;
                        String uin = uinObj.toString();
                         if (!是封禁用户(uin)) {
                             sendReply(groupUin, msg, "该用户未被联盟封禁");
                             return;
                         }
                        if (!isMyUin && !是代管(groupUin, userUin)) return;
                        if (有权限操作(groupUin, userUin, uin)) {
                            String reason = null;
                            String[] parts = msgContent.split(" ", 2);
                            if (parts.length > 1 && !parts[1].trim().isEmpty()) reason = parts[1].trim();
                            
                            移除封禁用户(uin);
                            sendReply(groupUin, msg, "新联盟解除封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(uin) + "\n用户 ID：" + uin + (reason != null ? "\n原因：" + reason : ""));
                        } else {
                             sendReply(groupUin, msg, "你没有权限操作该用户");
                        }
                        return;
                     }
                }
            }

            if ("禁".equals(msgContent) && mAtListCopy.isEmpty()) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                unifiedForbidden(groupUin, "", 1);
                return;
            }
            if (msg.MessageType == 1 && "解".equals(msgContent) && mAtListCopy.isEmpty()) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                unifiedForbidden(groupUin, "", 0);
                return;
            }

            if ("查看禁言列表".equals(msgContent)) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                ArrayList list = 禁言组(groupUin);
                sendReply(groupUin, msg, list.isEmpty() ? "当前没有人被禁言" : 禁言组文本(groupUin));
                return;
            }

            if (msgContent.matches("^解禁? ?[1-9][0-9]*$")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                int index = Integer.parseInt(msgContent.replaceAll(" |解|禁", "")) - 1;
                ArrayList list = 禁言组(groupUin);
                if (index >= 0 && index < list.size()) {
                    Object item = list.get(index);
                    if (item != null) {
                        unifiedForbidden(groupUin, item.toString(), 0);
                    }
                }
                return;
            }
            if (msgContent.matches("^解禁? ?[0-9]{5,11}$")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                unifiedForbidden(groupUin, msgContent.replaceAll(" |解|禁", ""), 0);
                return;
            }

            if (msgContent.matches("^(踢|踢黑) ?[1-9][0-9]*$")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                boolean isBlack = msgContent.contains("黑");
                int index = Integer.parseInt(msgContent.replaceAll(" |踢|黑", "")) - 1;
                ArrayList list = 禁言组(groupUin);
                if (index >= 0 && index < list.size()) {
                    Object item = list.get(index);
                    if (item == null) return;
                    String target = item.toString();
                    if (!检查代管保护(groupUin, target, isBlack ? "踢黑" : "踢出") && 有权限操作(groupUin, userUin, target)) {
                        unifiedKick(groupUin, target, isBlack);
                        sendMsg(groupUin, "", "已" + (isBlack ? "踢出并拉黑" : "踢出") + target + "\n权限使用人：" + 名(userUin));
                    }
                }
                return;
            }

            if ("#踢禁言".equals(msgContent)) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                unifiedForbidden(groupUin, "", 0);
                ArrayList list = unifiedGetForbiddenList(groupUin);
                if (list != null && !list.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (Object item : safeCopyList(list)) {
                        try {
                            java.lang.reflect.Field f = item.getClass().getDeclaredField("UserUin");
                            f.setAccessible(true);
                            Object uinObj = f.get(item);
                            if (uinObj == null) continue;
                            String u = uinObj.toString();
                            if (!检查代管保护(groupUin, u, "踢出") && 有权限操作(groupUin, userUin, u)) {
                                sb.append("\n").append(u);
                                unifiedKick(groupUin, u, false);
                            }
                        } catch (Exception e) {}
                    }
                    sendMsg(groupUin, "", "已踢出禁言列表:" + sb.toString());
                } else {
                    sendMsg(groupUin, "", "当前没有人被禁言");
                }
                return;
            }

            if ("全禁".equals(msgContent)) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                unifiedForbidden(groupUin, "", 0);
                ArrayList list = unifiedGetForbiddenList(groupUin);
                if (list != null && !list.isEmpty()) {
                    for (Object item : safeCopyList(list)) {
                        try {
                            java.lang.reflect.Field f = item.getClass().getDeclaredField("UserUin");
                            f.setAccessible(true);
                            Object uinObj = f.get(item);
                            if (uinObj == null) continue;
                            String u = uinObj.toString();
                            if (!检查代管保护(groupUin, u, "禁言") && 有权限操作(groupUin, userUin, u)) {
                                unifiedForbidden(groupUin, u, 2592000);
                            }
                        } catch (Exception e) {}
                    }
                    sendReply(groupUin, msg, "禁言列表已加倍禁言");
                } else {
                    sendMsg(groupUin, "", "当前没有人被禁言");
                }
                return;
            }

            if ("全解".equals(msgContent)) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                unifiedForbidden(groupUin, "", 0);
                ArrayList list = unifiedGetForbiddenList(groupUin);
                if (list != null && !list.isEmpty()) {
                    for (Object item : safeCopyList(list)) {
                        try {
                            java.lang.reflect.Field f = item.getClass().getDeclaredField("UserUin");
                            f.setAccessible(true);
                            Object uinObj = f.get(item);
                            if (uinObj != null) {
                                unifiedForbidden(groupUin, uinObj.toString(), 0);
                            }
                        } catch (Exception e) {}
                    }
                    sendReply(groupUin, msg, "已解禁禁言列表用户");
                } else {
                    sendMsg(groupUin, "", "当前没有人被禁言");
                }
                return;
            }

            if (msgContent.startsWith("删除代管") || msgContent.startsWith("删除管理员")) {
                if (!isMyUin) return;
                String text = msgContent.substring(4).replaceAll("[^0-9]", "");
                File f = 获取代管文件();
                if (f.exists() && text.matches("[0-9]+")) {
                    if (jiandu(text, 简取(f))) {
                        简弃(f, text);
                        sendMsg(groupUin, "", "已删除管理员:\n" + text);
                    } else {
                        sendReply(groupUin, msg, "此人并不是代管");
                    }
                } else {
                    sendReply(groupUin, msg, "代管列表为空或格式错误");
                }
                return;
            }

            if ("清空代管".equals(msgContent)) {
                if (!isMyUin) return;
                全弃(获取代管文件());
                sendReply(groupUin, msg, "代管列表已清空");
                return;
            }

            if (msgContent.startsWith("/addgroup") || msgContent.startsWith("!addgroup")) {
                if (!isMyUin) return;
                添加联盟群组(groupUin);
                sendReply(groupUin, msg, "已添加该群组为联盟\n联盟：简洁群管\n联盟创建者：" + myUin + "\n群组：" + groupUin);
                return;
            }
            if (msgContent.startsWith("/ungroup") || msgContent.startsWith("!ungroup")) {
                if (!isMyUin) return;
                移除联盟群组(groupUin);
                sendReply(groupUin, msg, "已取消该群组为联盟\n联盟：简洁群管\n联盟创建者：" + myUin + "\n群组：" + groupUin);
                return;
            }

            if (是联盟群组(groupUin)) {
                 if (msgContent.startsWith("/fban") || msgContent.startsWith("!fban")) {
                    if (!isMyUin && !是代管(groupUin, userUin)) return;
                    String[] parts = msgContent.split("\\s+", 3);
                    if (parts.length >= 2) {
                        String target = parts[1];
                        String reason = parts.length > 2 ? parts[2] : null;
                        if (target.matches("[0-9]{4,11}") && !是封禁用户(target) && !检查代管保护(groupUin, target, "联盟封禁") && 有权限操作(groupUin, userUin, target)) {
                             unifiedKick(groupUin, target, true);
                             添加封禁用户(target, reason);
                             sendReply(groupUin, msg, "新联盟封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(target) + "\n用户 ID：" + target + (reason != null ? "\n理由：" + reason : ""));
                        } else if (是封禁用户(target)) {
                            sendReply(groupUin, msg, "该用户已经被封禁，请勿再次封禁！");
                        }
                    }
                    return;
                 }
                 if (msgContent.startsWith("/unfban") || msgContent.startsWith("!unfban")) {
                    if (!isMyUin && !是代管(groupUin, userUin)) return;
                    String[] parts = msgContent.split("\\s+", 3);
                    if (parts.length >= 2) {
                        String target = parts[1];
                        String reason = parts.length > 2 ? parts[2] : null;
                        if (target.matches("[0-9]{4,11}") && 是封禁用户(target) && 有权限操作(groupUin, userUin, target)) {
                             移除封禁用户(target);
                             sendReply(groupUin, msg, "新联盟解除封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(target) + "\n用户 ID：" + target + (reason != null ? "\n原因：" + reason : ""));
                        }
                    }
                    return;
                 }
            }

        } catch (Throwable e) {
            error(e);
        }
    }
}

public void onForbiddenEvent(String groupUin, String userUin, String OPUin, long time) {
    try {
        if (!"开".equals(getString("自动解禁代管配置", "开关"))) return;
        
        if (是代管(groupUin, userUin) && time > 0) {
            String key = groupUin + ":" + userUin;
            if (processingUnforbidden.contains(key)) {
                return;
            }
            processingUnforbidden.add(key);
            try {
                unifiedForbidden(groupUin, userUin, 0);
                toast("检测代管在群:" + groupUin + "被禁言,已尝试解禁");
            } catch (Throwable e) {
                toast("检测代管在群:" + groupUin + "被禁言,无权限无法解禁");
            } finally {
                processingUnforbidden.remove(key);
            }
        }
    } catch (Exception e) {
    }
}

private Set processingUnforbidden = Collections.synchronizedSet(new HashSet());

public void onTroopEvent(String groupUin, String userUin, int type) {
    try {
        if (groupUin == null || groupUin.isEmpty()) {
            return;
        }
        
        String switchState = getString(groupUin, "退群拉黑");
        if (switchState != null && "开".equals(switchState)) {
            if (type == 1) {
                if (userUin.equals(myUin)) return;
                if (!检查黑名单(groupUin, userUin)) {
                    添加黑名单(groupUin, userUin);
                    String groupName = 获取群名(groupUin);
                    String message = "用户" + userUin + "在" + groupName + "退群，已加入黑名单";
                    toast(message);
                }
            } else if (type == 2) {
                if (检查黑名单(groupUin, userUin)) {
                    unifiedKick(groupUin, userUin, true);
                    String groupName = 获取群名(groupUin);
                    String message = "在" + groupName + "检测到用户" + userUin + "是退群拉黑用户，已踢黑";
                    toast(message);
                }
            }
        }
        
        if (type == 2 && 是联盟群组(groupUin) && 是封禁用户(userUin)) {
            unifiedKick(groupUin, userUin, true);
            String groupName = 获取群名(groupUin);
            toast("检测到联盟封禁用户 " + userUin + " 入群，在" + groupName + "已踢出");
        }
    } catch (Exception e) {
    }
}

public void 自动解禁代管方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    
    try {
        if("开".equals(getString("自动解禁代管配置", "开关"))){
            putString("自动解禁代管配置", "开关", null);
            toast("已关闭自动解禁代管");
        }else{
            putString("自动解禁代管配置", "开关", "开");
            toast("已开启自动解禁代管");
        }
    } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}

public void 设置艾特禁言时间方法(String groupUin, String uin, int chatType) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                boolean isDark = getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
                int 当前艾特禁言时间 = getInt("艾特禁言时间配置", "时间", 2592000);
                
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("设置艾特禁言时间");
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(25), dp2px(20), dp2px(25), dp2px(20));
                
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(isDark ? Color.parseColor("#1E1E1E") : Color.parseColor("#F8F9FA"));
                bg.setCornerRadius(dp2px(8));
                bg.setStroke(dp2px(1), isDark ? Color.parseColor("#343A40") : Color.parseColor("#DEE2E6"));
                int textColor = isDark ? Color.parseColor("#E9ECEF") : Color.parseColor("#212529");
                int hintTextColor = isDark ? Color.parseColor("#ADB5BD") : Color.parseColor("#6C757D");
                layout.setBackground(bg);
                
                TextView hint = new TextView(activity);
                hint.setText("当前艾特禁言时间: " + 当前艾特禁言时间 + "秒 (" + (当前艾特禁言时间/86400) + "天)");
                hint.setTextColor(textColor);
                hint.setPadding(0, 0, 0, dp2px(15));
                layout.addView(hint);
                
                EditText inputEditText = new EditText(activity);
                inputEditText.setHint("请输入禁言时间(秒)");
                inputEditText.setText(String.valueOf(当前艾特禁言时间));
                inputEditText.setHintTextColor(hintTextColor);
                inputEditText.setTextColor(textColor);
                
                GradientDrawable etBg = new GradientDrawable();
                etBg.setColor(isDark ? Color.parseColor("#2D2D2D") : Color.parseColor("#FFFFFF"));
                etBg.setCornerRadius(dp2px(6));
                etBg.setStroke(dp2px(1), isDark ? Color.parseColor("#495057") : Color.parseColor("#CED4DA"));
                inputEditText.setBackground(etBg);
                inputEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                
                layout.addView(inputEditText);
                
                builder.setView(layout);
                
                builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            try {
                                int newTime = Integer.parseInt(input);
                                if (newTime > 0) {
                                    putInt("艾特禁言时间配置", "时间", newTime);
                                    toast("已设置艾特禁言时间为: " + newTime + "秒");
                                } else {
                                    toast("请输入大于0的数字");
                                }
                            } catch (NumberFormatException e) {
                                toast("请输入有效的数字");
                            }
                        }
                    }
                });
                
                builder.setNegativeButton("取消", null);
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            } catch (Exception e) {
            }
        }
    });
}

public void 开关自助头衔方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    try {
        if("开".equals(getString(groupUin,"自助头衔"))){
            putString(groupUin,"自助头衔",null);
            toast("已关闭自助头衔");
        }else{
            putString(groupUin,"自助头衔","开");
            toast("已开启自助头衔");
        }
    } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}

public void 开关艾特禁言方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    try {
        if("开".equals(getString(groupUin,"艾特禁言"))){
            putString(groupUin,"艾特禁言",null);
            toast("已关闭艾特禁言");
        }else{
            putString(groupUin,"艾特禁言","开");
            toast("已开启艾特禁言");
        }
    } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}

public void 退群拉黑开关方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    try {
        if("开".equals(getString(groupUin,"退群拉黑"))){
            putString(groupUin,"退群拉黑",null);
            toast("已关闭退群拉黑");
        }else{
            putString(groupUin,"退群拉黑","开");
            toast("已开启退群拉黑");
        }
   } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}

public void 检测黑名单方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        return;
    }
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList 黑名单列表 = 获取黑名单列表(groupUin);
                if (黑名单列表.isEmpty()) {
                    toast("本群黑名单为空");
                    return;
                }
                ArrayList 成员列表 = getGroupMemberList(groupUin);
                if (成员列表 == null || 成员列表.isEmpty()) {
                    toast("获取成员列表失败");
                    return;
                }
                boolean 有权限 = false;
                ArrayList 成员列表副本 = safeCopyList(成员列表);
                for (int i = 0; i < 成员列表副本.size(); i++) {
                    Object 成员 = 成员列表副本.get(i);
                    if (成员 != null && 成员.UserUin != null && 成员.UserUin.equals(myUin)) {
                        有权限 = 成员.IsOwner || 成员.IsAdmin;
                        break;
                    }
                }
                if (!有权限) {
                    toast("没有群管权限，无法踢人");
                    return;
                }
                StringBuilder 踢出列表 = new StringBuilder();
                for (int i = 0; i < 成员列表副本.size(); i++) {
                    Object 成员 = 成员列表副本.get(i);
                    if (成员 == null || 成员.UserUin == null) continue;
                    String 成员QQ = 成员.UserUin;
                    if (黑名单列表.contains(成员QQ)) {
                        kick(groupUin, 成员QQ, true);
                        踢出列表.append(getMemberName(groupUin, 成员QQ)).append("(").append(成员QQ).append(")\n");
                        Thread.sleep(500);
                    }
                }
                if (踢出列表.length() > 0) {
                    final String 结果 = "已踢出以下黑名单成员：\n" + 踢出列表.toString();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            toast(结果);
                        }
                    });
                } else {
                    toast("没有发现黑名单成员");
                }
            } catch (Throwable e) {
                toast("检测黑名单时出错: " + e.getMessage());
            }
        }
    }).start();
}

{
    new Thread(new Runnable() {
        public void run() {
            try {
                Thread.sleep(5000);
                
                ArrayList 联盟群组列表 = null;
                ArrayList 封禁列表 = null;
                
                try {
                    if (联盟群组文件 != null && 联盟群组文件.exists()) {
                        联盟群组列表 = 简取(联盟群组文件);
                    }
                    if (封禁列表文件 != null && 封禁列表文件.exists()) {
                        封禁列表 = 简取(封禁列表文件);
                    }
                } catch (Exception e) {
                    return;
                }
                
                if (联盟群组列表 == null || 联盟群组列表.isEmpty() || 封禁列表 == null || 封禁列表.isEmpty()) {
                    return;
                }
                
                Set 封禁UIN集合 = new HashSet();
                ArrayList 封禁列表副本 = safeCopyList(封禁列表);
                for (int k = 0; k < 封禁列表副本.size(); k++) {
                    String 记录 = (String) 封禁列表副本.get(k);
                    if (记录 != null && 记录.contains("|")) {
                        String[] parts = 记录.split("\\|");
                        if (parts.length > 0 && parts[0] != null) {
                            封禁UIN集合.add(parts[0].trim());
                        }
                    }
                }
                
                if (封禁UIN集合.isEmpty()) {
                    return;
                }
                
                ArrayList 联盟群组列表副本 = safeCopyList(联盟群组列表);
                for (int i = 0; i < 联盟群组列表副本.size(); i++) {
                    String 群号 = (String)联盟群组列表副本.get(i);
                    if (群号 == null || 群号.isEmpty()) continue;
                    
                    try {
                        ArrayList 成员列表 = getGroupMemberList(群号);
                        if (成员列表 != null && !成员列表.isEmpty()) {
                            ArrayList 成员列表副本 = safeCopyList(成员列表);
                            for (int j = 0; j < 成员列表副本.size(); j++) {
                                Object 成员 = 成员列表副本.get(j);
                                if (成员 != null && 成员.UserUin != null) {
                                    if (封禁UIN集合.contains(成员.UserUin)) {
                                        unifiedKick(群号, 成员.UserUin, true);
                                        Thread.sleep(300);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    
                    }
                }
            } catch (Exception e) {
                
            }
        }
    }).start();
}