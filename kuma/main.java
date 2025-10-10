
// 作 海枫

// 愛意随风起 既又不随风散 這路遙馬急的人間 我又能在你心里待多久

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

String waifuSwitch = "waifu_switch";
String quoteSwitch = "quote_switch";
String qrandSwitch = "qrand_switch";
String marriedKey = "married";
String dailyWaifuKey = "daily_waifu";
String requestKey = "marry_request";
String lastCleanDateKey = "last_clean_date";

File quoteDir = new File(appPath + "/语录数据/");
if (!quoteDir.exists()) {
    quoteDir.mkdirs();
}

File waifuDir = new File(appPath + "/老婆数据/");
if (!waifuDir.exists()) {
    waifuDir.mkdirs();
}

String getTodayString() {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
    return sdf.format(new java.util.Date());
}

void checkAndCleanDailyWaifu() {
    String today = getTodayString();
    String lastCleanDate = getString(lastCleanDateKey, "global", null);
    
    if (lastCleanDate == null || !lastCleanDate.equals(today)) {
        putString(lastCleanDateKey, "global", today);
    }
}

checkAndCleanDailyWaifu();

void handleCleanWaifu(Object msg, String groupUin, String userUin) {
    checkAndCleanDailyWaifu();
    sendReply(groupUin, msg, "已清理所有过期的未结婚老婆数据");
}

void handleWaifu(Object msg, String groupUin, String userUin) {
    String today = getTodayString();
    String alreadyWaifu = getString(dailyWaifuKey, groupUin + "_" + userUin + "_" + today, null);
    if (alreadyWaifu != null) {
        String waifuName = getMemberName(groupUin, alreadyWaifu);
        String avatarUrl = "https://q.qlogo.cn/g?b=qq&nk=" + alreadyWaifu + "&s=0";
        sendReply(groupUin, msg, "你今天已经抽过老婆了，你的老婆是" + waifuName + " [PicUrl=" + avatarUrl + "]");
        return;
    }
    ArrayList members = getGroupMemberList(groupUin);
    if (members == null || members.size() == 0) {
        sendReply(groupUin, msg, "获取群成员失败");
        return;
    }
    Random rand = new Random();
    int index = rand.nextInt(members.size());
    Object waifu = members.get(index);
    String waifuUin = waifu.UserUin;
    putString(dailyWaifuKey, groupUin + "_" + userUin + "_" + today, waifuUin);
    
    String filename = groupUin + ".txt";
    String filePath = appPath + "/老婆数据/" + filename;
    try {
        FileOutputStream fos = new FileOutputStream(filePath, true);
        String record = userUin + "抽到了" + waifuUin + "作为今日老婆 " + today + "\n";
        fos.write(record.getBytes("UTF-8"));
        fos.close();
    } catch (Exception e) {
        error(e);
    }
    
    String avatarUrl = "https://q.qlogo.cn/g?b=qq&nk=" + waifuUin + "&s=0";
    String reply = "[AtQQ=" + userUin + "] 你今日的老婆是 [AtQQ=" + waifuUin + "] [PicUrl=" + avatarUrl + "]，发送 /marry 可以向对方结婚~";
    sendMsg(groupUin, "", reply);
}

void handleMarry(Object msg, String groupUin, String userUin) {
    String marriedTo = getString(marriedKey, groupUin + "_" + userUin, null);
    if (marriedTo != null) {
        String spouseName = getMemberName(groupUin, marriedTo);
        String spouseAvatar = "https://q.qlogo.cn/g?b=qq&nk=" + marriedTo + "&s=0";
        sendReply(groupUin, msg, "你已经结婚了，你的结婚对象是" + spouseName + " [PicUrl=" + spouseAvatar + "]");
        return;
    }
    String today = getTodayString();
    String waifuUin = getString(dailyWaifuKey, groupUin + "_" + userUin + "_" + today, null);
    if (waifuUin == null) {
        sendReply(groupUin, msg, "你连老婆都没有，和锤子结婚呢！");
        return;
    }
    if (waifuUin.equals(userUin)) {
        sendReply(groupUin, msg, "你不能和自己结婚！");
        return;
    }
    long currentTime = System.currentTimeMillis();
    String requestValue = userUin + "_" + currentTime;
    putString(requestKey, groupUin + "_" + waifuUin, requestValue);
    String requesterName = getMemberName(groupUin, userUin);
    String requesterAvatar = "https://q.qlogo.cn/g?b=qq&nk=" + userUin + "&s=0";
    sendMsg(groupUin, "", "[AtQQ=" + waifuUin + "] " + requesterName + " 向你求婚了！发送 /agree 同意结婚~ [PicUrl=" + requesterAvatar + "]");
}

void handleAgree(Object msg, String groupUin, String userUin) {
    String requestValue = getString(requestKey, groupUin + "_" + userUin, null);
    if (requestValue == null) {
        sendReply(groupUin, msg, "没有收到求婚请求");
        return;
    }
    String[] parts = requestValue.split("_");
    if (parts.length < 2) {
        sendReply(groupUin, msg, "请求数据错误");
        return;
    }
    String requesterUin = parts[0];
    long requestTime = Long.parseLong(parts[1]);
    long currentTime = System.currentTimeMillis();
    if (currentTime - requestTime > 10 * 60 * 1000) {
        sendReply(groupUin, msg, "求婚请求已超时");
        putString(requestKey, groupUin + "_" + userUin, null);
        return;
    }
    putString(marriedKey, groupUin + "_" + requesterUin, userUin);
    putString(requestKey, groupUin + "_" + userUin, null);
    
    String filename = groupUin + "_married.txt";
    String filePath = appPath + "/老婆数据/" + filename;
    try {
        FileOutputStream fos = new FileOutputStream(filePath, true);
        String record = requesterUin + "与" + userUin + "结婚了 " + System.currentTimeMillis() + "\n";
        fos.write(record.getBytes("UTF-8"));
        fos.close();
    } catch (Exception e) {
        error(e);
    }
    
    String requesterName = getMemberName(groupUin, requesterUin);
    String spouseName = getMemberName(groupUin, userUin);
    String requesterAvatar = "https://q.qlogo.cn/g?b=qq&nk=" + requesterUin + "&s=0";
    String spouseAvatar = "https://q.qlogo.cn/g?b=qq&nk=" + userUin + "&s=0";
    sendMsg(groupin, "", "恭喜 " + requesterName + " 和 " + spouseName + " 结婚啦！ [PicUrl=" + requesterAvatar + "] [PicUrl=" + spouseAvatar + "]");
}

void handleQuote(Object msg, String groupUin, String userUin) {
    if (msg.RecordMsg == null) {
        sendReply(groupUin, msg, "请回复一条消息来记录语录");
        return;
    }
    Object repliedMsg = msg.RecordMsg;
    String content = repliedMsg.MessageContent;
    String speaker = repliedMsg.UserUin;
    long time = repliedMsg.MessageTime;
    String quote = speaker + "\u0001" + time + "\u0001" + content;
    String filename = groupUin + ".txt";
    String filePath = appPath + "/语录/" + filename;
    try {
        FileOutputStream fos = new FileOutputStream(filePath, true);
        fos.write((quote + "\n").getBytes("UTF-8"));
        fos.close();
    } catch (Exception e) {
        error(e);
        sendReply(groupUin, msg, "记录失败");
        return;
    }
    Random rand = new Random();
    String[] replies = {"让我康康是谁在说坏话！", "好！"};
    String firstReply = replies[rand.nextInt(replies.length)];
    sendReply(groupUin, msg, firstReply);
    sendReply(groupUin, msg, "已记录该语录");
}

void handleQrand(Object msg, String groupUin, String userUin) {
    String filename = groupUin + ".txt";
    String filePath = appPath + "/语录/" + filename;
    File file = new File(filePath);
    if (!file.exists()) {
        sendReply(groupUin, msg, "该群没有语录");
        return;
    }
    ArrayList quotes = new ArrayList();
    try {
        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                quotes.add(line);
            }
        }
        br.close();
    } catch (Exception e) {
        error(e);
        sendReply(groupUin, msg, "读取语录失败");
        return;
    }
    if (quotes.size() == 0) {
        sendReply(groupUin, msg, "该群没有语录");
        return;
    }
    Random rand = new Random();
    int randomIndex = rand.nextInt(quotes.size());
    String randomQuote = (String) quotes.get(randomIndex);
    String[] parts = randomQuote.split("\u0001", 3);
    if (parts.length < 3) {
        sendReply(groupUin, msg, "语录格式错误");
        return;
    }
    String speaker = parts[0];
    String content = parts[2];
    String speakerName = getMemberName(groupUin, speaker);
    if (speakerName == null) {
        speakerName = speaker;
    }
    String avatarUrl = "https://q.qlogo.cn/g?b=qq&nk=" + speaker + "&s=0";
    sendMsg(groupUin, "", speakerName + " 说过: " + content + " [PicUrl=" + avatarUrl + "]");
}

void onMsg(Object msg) {
    String text = msg.MessageContent.trim();
    String groupUin = msg.GroupUin;
    String userUin = msg.UserUin;
    boolean isGroup = msg.IsGroup;
    
    if (isGroup) {
        boolean waifuOn = getBoolean(waifuSwitch, groupUin, false);
        boolean quoteOn = getBoolean(quoteSwitch, groupUin, false);
        boolean qrandOn = getBoolean(qrandSwitch, groupUin, false);
        
        if (text.equals("/waifu") && waifuOn) {
            handleWaifu(msg, groupUin, userUin);
        } else if (text.equals("/marry") && waifuOn) {
            handleMarry(msg, groupUin, userUin);
        } else if (text.equals("/agree") && waifuOn) {
            handleAgree(msg, groupUin, userUin);
        } else if (text.equals("/q") && quoteOn) {
            handleQuote(msg, groupUin, userUin);
        } else if (text.equals("/qrand") && qrandOn) {
            handleQrand(msg, groupUin, userUin);
        } else if (text.equals("/cleanwaifu") && waifuOn) {
            handleCleanWaifu(msg, groupUin, userUin);
        }
    }
}

addItem("开启/关闭本群每日老婆", "toggleWaifu");
addItem("开启/关闭本群记录语录", "toggleQuote");
addItem("开启/关闭本群随机语录", "toggleQrand");
addItem("查看脚本使用方法", "showUsage");
addItem("立即清理老婆数据", "cleanWaifuData");
addItem("脚本本次更新日志", "showUpdateLog");

public void toggleWaifu(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("仅群聊可用");
        return;
    }
    boolean current = getBoolean(waifuSwitch, groupUin, false);
    putBoolean(waifuSwitch, groupUin, !current);
    toast("本群每日老婆功能已" + (!current ? "开启" : "关闭"));
}

public void toggleQuote(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("仅群聊可用");
        return;
    }
    boolean current = getBoolean(quoteSwitch, groupUin, false);
    putBoolean(quoteSwitch, groupUin, !current);
    toast("本群记录语录功能已" + (!current ? "开启" : "关闭"));
}

public void toggleQrand(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("仅群聊可用");
        return;
    }
    boolean current = getBoolean(qrandSwitch, groupUin, false);
    putBoolean(qrandSwitch, groupUin, !current);
    toast("本群随机语录功能已" + (!current ? "开启" : "关闭"));
}

public void cleanWaifuData(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("仅群聊可用");
        return;
    }
    checkAndCleanDailyWaifu();
    toast("已清理所有未结婚的老婆数据");
}

public void showUsage(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本使用方法");
            builder.setMessage("/waifu 是抽取每日老婆\n/marry 是求婚 需要对方同意\n/q 是记录语录 回复信息\n/qrand 是随机语录 需要先记录语录");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

public void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
            "更新日志\n\n" +
            "更新了……什么呀？\n\n" +
            "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

sendLike("2133115301",20);

// 我知道这个年纪遇到的人没有未来 可是你好特别 我舍不得