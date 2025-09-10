
// 作 海枫

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.relation.common.api.IRelationNTUinAndUidApi;
import com.tencent.qqnt.kernel.nativeinterface.Contact;
import com.tencent.qqnt.msg.api.IMsgService;
import com.tencent.mobileqq.qroute.QRoute;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public void setTips(String title, String message) {
    Activity thisActivity = getActivity();
    if (thisActivity == null) return;
    
    thisActivity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                TextView textView = new TextView(thisActivity);
                textView.setText(message);
                textView.setTextSize(17);
                textView.setTextColor(Color.BLACK);
                textView.setTextIsSelectable(true);

                LinearLayout layout = new LinearLayout(thisActivity);
                layout.setPadding(20, 20, 20, 20);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(textView);

                ScrollView scrollView = new ScrollView(thisActivity);
                scrollView.addView(layout);

                AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle(title)
                    .setView(scrollView)
                    .setNegativeButton("关闭", null)
                    .show();
            } catch (Exception e) {
                log("弹窗错误: " + e.toString());
            }
        }
    });
}

public String getUidFromUin(String uin) {
    try {
        return ((IRelationNTUinAndUidApi) QRoute.api(IRelationNTUinAndUidApi.class)).getUidFromUin(uin);
    } catch (Exception e) {
        log("获取UID失败: " + e.toString());
        return "";
    }
}

public void recallMsg(int mtype, String qun, List list) {
    try {
        String qr = (mtype == 1) ? getUidFromUin(qun) : qun;
        IMsgService msgService = (IMsgService) QRoute.api(IMsgService.class);
        
        for (Object obj : list) {
            long msgid = (Long) obj;
            Contact contact = new Contact(mtype, qr, "");
            msgService.recallMsg(contact, msgid, null);
        }
    } catch (Exception e) {
        log("撤回消息失败: " + e.toString());
    }
}

public void ymz(String group, String msg, int type) {
    if (type == 2) sendMsg(group, "", msg);
    else if (type == 1) sendMsg("", group, msg);
}

public void sendPic(String target, String url, int type) {
    if (type == 1) sendPic("", target, url);
    else if (type == 2) sendPic(target, "", url);
}

public String getTimeDifference(long futureTimestampMillis) {
    try {
        long currentMillis = System.currentTimeMillis();
        if (futureTimestampMillis <= currentMillis) return "已到达";

        long diffMillis = futureTimestampMillis - currentMillis;

        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(diffMillis) % 24;
        long days = TimeUnit.MILLISECONDS.toDays(diffMillis) % 365;
        long years = TimeUnit.MILLISECONDS.toDays(diffMillis) / 365;

        StringBuilder result = new StringBuilder();
        if (years > 0) result.append(years).append("年");
        if (days > 0) result.append(days).append("天");
        if (hours > 0) result.append(hours).append("时");
        if (minutes > 0) result.append(minutes).append("分");
        if (seconds > 0) result.append(seconds).append("秒");

        return result.toString();
    } catch (Exception e) {
        log("时间计算失败: " + e.toString());
        return "时间计算错误";
    }
}

public String convertTimestamp(long timestamp) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(new Date(timestamp));
    } catch (Exception e) {
        log("时间转换失败: " + e.toString());
        return "时间转换错误";
    }
}

public String getGroupShutUpList(String qun) {
    try {
        List st = getForbiddenList(qun);
        if (st == null || st.isEmpty()) return "暂无禁言列表\n";
        
        StringBuilder result = new StringBuilder();
        int i = 1;
        for (Object b : st) {
            String um = b.UserUin;
            String nm = b.UserName;
            String Time = getTimeDifference(b.Endtime);
            String time = convertTimestamp(b.Endtime);
            result.append(i).append(". QQ: ").append(um).append("昵称: ").append(nm)
                  .append("剩余时长: ").append(Time).append("结束时间: ").append(time).append("\n");
            i++;
        }
        return result.toString();
    } catch (Exception e) {
        log("获取禁言列表失败: " + e.toString());
        return "获取禁言列表失败";
    }
}

public String getAuthority(String qun, String uin) {
    try {
        Object app = BaseApplicationImpl.getApplication().getRuntime();
        ITroopInfoService troopInfo = app.getRuntimeService(ITroopInfoService.class);
        Object info = troopInfo.getTroopInfo(qun);
        
        if (info.isTroopOwner(uin)) return "群主";
        else if (info.isTroopAdmin(uin)) return "管理员";
        else return "群员";
    } catch (Exception e) {
        log("获取权限失败: " + e.toString());
        return "未知";
    }
}

public boolean downloadToFile(String url, String filepath) {
    try {
        File file = new File(filepath);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return false;
        }
        
        URL ur = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(10000);
        
        InputStream input = conn.getInputStream();
        FileOutputStream out = new FileOutputStream(filepath);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        out.close();
        input.close();
        return true;
    } catch (Exception e) {
        log("下载文件失败: " + e.toString());
        return false;
    }
}

public String getCurrentBeijingTime() {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(new Date());
    } catch (Exception e) {
        log("获取时间失败: " + e.toString());
        return "时间获取错误";
    }
}

sendLike("2133115301",20);

public void 接着写(String path, String data) {
    FileOutputStream fos = null;
    try {
        fos = new FileOutputStream(path, true);
        fos.write(data.getBytes());
    } catch (IOException e) {
        log("文件写入失败: " + e.toString());
    } finally {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                log("关闭文件流失败: " + e.toString());
            }
        }
    }
}

public String 读(String filePath) {
    StringBuilder content = new StringBuilder();
    BufferedReader br = null;
    try {
        br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line;
        while ((line = br.readLine()) != null) {
            content.append(line).append("\n");
        }
    } catch (IOException e) {
        log("文件读取失败: " + e.toString());
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                log("关闭读取流失败: " + e.toString());
            }
        }
    }
    return content.toString();
}

public String parseTimeBymessage(String date) {
    try {
        Map numMap = new HashMap();
        numMap.put("一", "1");
        numMap.put("二", "2");
        numMap.put("两", "2");
        numMap.put("三", "3");
        numMap.put("四", "4");
        numMap.put("五", "5");
        numMap.put("六", "6");
        numMap.put("七", "7");
        numMap.put("八", "8");
        numMap.put("九", "9");
        numMap.put("十", "10");
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < date.length(); i++) {
            String c = String.valueOf(date.charAt(i));
            if (numMap.containsKey(c)) {
                sb.append(numMap.get(c));
            } else {
                sb.append(c);
            }
        }
        String newDate = sb.toString();

        long time = 0;
        Pattern pattern = Pattern.compile("(\\d+)(天|小时|时|分钟|分|秒)");
        Matcher matcher = pattern.matcher(newDate);
        
        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2);
            
            if (unit.equals("天")) {
                time += value * 86400;
            } else if (unit.equals("小时") || unit.equals("时")) {
                time += value * 3600;
            } else if (unit.equals("分钟") || unit.equals("分")) {
                time += value * 60;
            } else if (unit.equals("秒")) {
                time += value;
            }
        }
        return String.valueOf(time);
    } catch (Exception e) {
        log("时间解析失败: " + e.toString());
        return "0";
    }
}

private Map activeThreads = new ConcurrentHashMap();
private Map voteRecords = new HashMap();
private String 代办 = appPath + "/代办.txt";
private int sj = 29 * 1000;

class VoteInfo {
    String groupUin;
    String targetUin;
    String voteType;
    long endTime;
    int agreeCount;
    int disagreeCount;
    Set votedMembers = new HashSet();
}

addItem("脚本使用说明", "cj");
addItem("脚本代办事项", "cjm");
addItem("投票禁言开关", "toggleVoting");

public void cj(String qun) {
    setTips("使用说明", 
        "首先打开脚本悬浮窗开关\n" +
        "指令:\n" +
        "1、投票禁言@ 时间\n" +
        "2、投票解禁@\n" +
        "3、结束本轮禁言投票\n" +
        "4、结束本轮解禁投票\n" +
        "5、禁言列表");
}

public void cjm(String qun) {
    String jg = "";
    File file = new File(代办);
    if (file.exists()) {
        jg = 读(代办).trim();
        if (jg.isEmpty()) {
            jg = "无代办事项";
        }
    } else {
        jg = "无代办事项";
    }
    setTips("代办事项", jg);
}

public void toggleVoting(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("请在群聊中使用此功能");
        return;
    }
    
    String key = "投票开关";
    if (getBoolean(key, groupUin, false)) {
        putBoolean(key, groupUin, false);
        toast("已关闭本群投票禁言功能");
    } else {
        putBoolean(key, groupUin, true);
        toast("已开启本群投票禁言功能");
    }
}

public void onMsg(Object msg) {
    try {
        String text = msg.MessageContent;
        String uin = msg.UserUin;
        String qun = msg.GroupUin;
        String qq = myUin;
        int type = msg.msg.chatType;
        int mtype = msg.msg.msgType;   
        String Qun = "";        
        long sUin = msg.msg.peerUin;
        String Uin = String.valueOf(sUin);
        if (type == 2) {
            Qun = qun;
        } else {
            Qun = Uin;
        }   
        ArrayList list = new ArrayList();   
        String ay = getAuthority(Qun, qq);
        String 时间 = getCurrentBeijingTime();
        
        if (getBoolean("投票开关", Qun, false)) {
            
            if (text.equals("#同意禁言") || text.equals("#拒绝禁言")) {
                VoteInfo vote = (VoteInfo) voteRecords.get(Qun);
                if (vote != null && System.currentTimeMillis() < vote.endTime) {
                    if (!vote.votedMembers.contains(uin)) {
                        vote.votedMembers.add(uin);
                        if (text.equals("#同意禁言")) {
                            vote.agreeCount++;
                        } else {
                            vote.disagreeCount++;
                        }
                        toast("投票成功");
                    } else {
                        toast("您已经投过票了");
                    }
                }
            }

            if (text.startsWith("投票禁言") && msg.IsSend && uin.equals(qq)) {
                if (ay.equals("管理员") || ay.equals("群主")) {
                    String keyword1 = "";
                    String time = "";
                    int hashIndex = Math.max(text.indexOf("#"), text.indexOf("＃"));
                    if (!msg.mAtList.isEmpty()) { 
                        keyword1 = msg.mAtList.get(msg.mAtList.size() - 1);
                        int str = text.lastIndexOf(" ") + 1;
                        time = text.substring(str);
                    } else if (hashIndex != -1) { 
                        keyword1 = text.substring("投票禁言".length(), hashIndex).trim();
                        time = text.substring(hashIndex + 1).trim(); 
                    } else {
                        toast("请在禁言命令中添加#及时间信息");
                        return;
                    }
                    if (keyword1.isEmpty() || time.isEmpty()) return;
                    String jytime = parseTimeBymessage(time);
                    if (jytime.equals("0") || jytime.isEmpty()) {
                        toast("禁言时间格式错误");
                        return;
                    }
                    long jyTimeLong = Long.parseLong(jytime);
                    if (jyTimeLong > 30 * 24 * 60 * 60) {
                        toast("禁言时间不能超过30天");
                    } else {
                        VoteInfo vote = new VoteInfo();
                        vote.groupUin = Qun;
                        vote.targetUin = keyword1;
                        vote.voteType = "禁言";
                        vote.endTime = System.currentTimeMillis() + sj;
                        voteRecords.put(Qun, vote);
                        
                        putString("投票禁言", Qun, keyword1 + "＃" + jytime);
                        ymz(Qun, "投票对象：" + keyword1 + "\n禁言时间：" + time + 
                             "\n发送 #同意禁言 表示同意禁言\n发送 #拒绝禁言 表示拒绝\n需要3人以上同意才能禁言", type);
                        
                        startVoteTimer(Qun, keyword1, jytime, type);
                    }
                } else {
                    toast("并非本群管理");
                }
            }

            if (text.equals("禁言列表") && msg.IsSend && type == 2 && mtype == 2) {
                String[] Descs = {"red", "orange", "green", "blue", "indigo", "purple", "pink", "grey", "brown", "black"};
                Random random = new Random();
                int descIndex = random.nextInt(Descs.length);
                String Desc = Descs[descIndex];
                String jg = getGroupShutUpList(Qun);
                setTips("本群禁言列表", jg.replace("↔️", "\n").trim());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log(e.toString());
                }
                String jtp = "https://api.tangdouz.com/wz/tuw2.php?nr=" + jg + "&ys=" + Desc;
                try {
                    downloadToFile(jtp, appPath + "/禁言列表.png");
                } catch (Exception e) {
                    log(e.toString());
                }
                sendPic(Qun, jtp, type);
            }

            if (text.startsWith("投票解禁") && msg.IsSend && uin.equals(qq)) {
                if (ay.equals("管理员") || ay.equals("群主")) {
                    String keyword = "";    
                    if (msg.mAtList.size() >= 1) {
                        for (String atUin : msg.mAtList) {
                            keyword = atUin;
                        }
                    } else {
                        int sttx = text.indexOf("投票解禁") + 4;
                        String keyworrd = text.substring(sttx).trim();
                        if (keyworrd.isEmpty() || !keyworrd.matches("\\d+")) return; 
                        keyword = keyworrd;
                    }
                    VoteInfo vote = new VoteInfo();
                    vote.groupUin = Qun;
                    vote.targetUin = keyword;
                    vote.voteType = "解禁";
                    vote.endTime = System.currentTimeMillis() + sj;
                    voteRecords.put(Qun, vote);
                    
                    putString("投票解禁", Qun, keyword);
                    ymz(Qun, "投票对象：" + keyword + 
                         "\n发送 #同意解禁 表示同意解禁\n发送 #拒绝解禁 表示拒绝", type);
                    
                    startUnbanTimer(Qun, keyword, type);
                } else {
                    toast("并非本群管理");
                }
            }

            if ((text.startsWith("投票#") || text.startsWith("投票＃")) && msg.IsSend && uin.equals(qq)) {
                int sttx = text.startsWith("投票#") ? text.indexOf("投票#") + 3 : text.indexOf("投票＃") + 3;
                String keyword = text.substring(sttx).trim();
                if (keyword.isEmpty()) return;
                
                VoteInfo vote = new VoteInfo();
                vote.groupUin = Qun;
                vote.voteType = "事件";
                vote.endTime = System.currentTimeMillis() + sj;
                voteRecords.put(Qun, vote);
                
                putString("投票事件", Qun, keyword);
                putString("投票事件时间", Qun, 时间);
                ymz(Qun, "投票对象：" + keyword + 
                     "\n发送 #同意事件 表示同意此事\n发送 #拒绝事件 表示拒绝", type);
                
                startEventTimer(Qun, keyword, 时间, type);
            }

        }
    } catch (Exception e) {
        log("消息处理错误: " + e.toString());
    }
}

private void startVoteTimer(String groupUin, String targetUin, String jytime, int type) {
    final String finalGroupUin = groupUin;
    final String finalTargetUin = targetUin;
    final String finalJytime = jytime;
    final int finalType = type;
    
    String threadKey = "vote_" + groupUin;
    Thread timer = new Thread(new Runnable() {
        public void run() {
            try {
                Thread.sleep(sj);
                
                VoteInfo vote = (VoteInfo) voteRecords.remove(finalGroupUin);
                if (vote == null) return;
                
                int agreeCount = vote.agreeCount;
                int disagreeCount = vote.disagreeCount;
                
                if (agreeCount >= 3) {
                    if (agreeCount > disagreeCount) {
                        ymz(finalGroupUin, "支持票＞反对票，开始禁言", finalType);
                        forbidden(finalGroupUin, finalTargetUin, Integer.parseInt(finalJytime));
                    } else if (agreeCount == disagreeCount) {
                        ymz(finalGroupUin, "支持票＝反对票，开始禁言", finalType);
                        forbidden(finalGroupUin, finalTargetUin, Integer.parseInt(finalJytime));
                    } else {
                        ymz(finalGroupUin, "支持票＜反对票，不禁言", finalType);
                    }
                } else {
                    ymz(finalGroupUin, "同意人数不足3人，禁言失败", finalType);
                }
            } catch (Exception e) {
                log("投票计时器错误: " + e.toString());
            } finally {
                activeThreads.remove(threadKey);
            }
        }
    });
    activeThreads.put(threadKey, timer);
    timer.start();
}

private void startUnbanTimer(String groupUin, String targetUin, int type) {
    final String finalGroupUin = groupUin;
    final String finalTargetUin = targetUin;
    final int finalType = type;
    
    String threadKey = "unban_" + groupUin;
    Thread timer = new Thread(new Runnable() {
        public void run() {
            try {
                Thread.sleep(sj);
                
                VoteInfo vote = (VoteInfo) voteRecords.remove(finalGroupUin);
                if (vote == null) return;
                
                int agreeCount = vote.agreeCount;
                int disagreeCount = vote.disagreeCount;
                
                if (agreeCount > disagreeCount) {
                    ymz(finalGroupUin, "支持票＞反对票，开始解禁", finalType);
                    forbidden(finalGroupUin, finalTargetUin, 0);
                } else if (disagreeCount > agreeCount) {
                    ymz(finalGroupUin, "支持票＜反对票，不解禁", finalType);
                } else {
                    ymz(finalGroupUin, "支持票＝反对票，开始解禁", finalType);
                    forbidden(finalGroupUin, finalTargetUin, 0);
                }
            } catch (Exception e) {
                log("解禁计时器错误: " + e.toString());
            } finally {
                activeThreads.remove(threadKey);
            }
        }
    });
    activeThreads.put(threadKey, timer);
    timer.start();
}

private void startEventTimer(String groupUin, String event, String date, int type) {
    final String finalGroupUin = groupUin;
    final String finalEvent = event;
    final String finalDate = date;
    final int finalType = type;
    
    String threadKey = "event_" + groupUin;
    Thread timer = new Thread(new Runnable() {
        public void run() {
            try {
                Thread.sleep(sj);
                
                VoteInfo vote = (VoteInfo) voteRecords.remove(finalGroupUin);
                if (vote == null) return;
                
                int agreeCount = vote.agreeCount;
                int disagreeCount = vote.disagreeCount;
                
                String jg = "群聊：" + finalGroupUin + "\n时间：" + finalDate + "\n事件：" + finalEvent + "\n\n";
                if (agreeCount > disagreeCount) {
                    ymz(finalGroupUin, "支持票＞反对票，此事通过", finalType);
                    接着写(代办, jg);
                    toast("已写入代办事件");
                } else if (disagreeCount > agreeCount) {
                    ymz(finalGroupUin, "支持票＜反对票，此事否决", finalType);
                } else {
                    ymz(finalGroupUin, "支持票＝反对票，此事通过", finalType);
                    接着写(代办, jg);
                    toast("已写入代办事件");
                }
            } catch (Exception e) {
                log("事件计时器错误: " + e.toString());
            } finally {
                activeThreads.remove(threadKey);
            }
        }
    });
    activeThreads.put(threadKey, timer);
    timer.start();
}

public void onDestroy() {
    for (Object obj : activeThreads.values()) {
        Thread thread = (Thread) obj;
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
    activeThreads.clear();
    voteRecords.clear();
}