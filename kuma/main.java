
// 海枫

// 一个人会犯多少错 两个人又会有多少承诺

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
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
import java.util.ArrayList;
import java.util.Calendar;

// 指定用户结婚 如果用户在该群就会指定 不在就不会使用自定义指定
String[] specifiedWaifuList = {"1987836456","123453"};

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
        
        File waifuDataDir = new File(appPath + "/老婆数据/");
        if (waifuDataDir.exists() && waifuDataDir.isDirectory()) {
            File[] files = waifuDataDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt") && !file.getName().contains("_married")) {
                        try {
                            file.delete();
                        } catch (Exception e) {
                            error(e);
                        }
                    }
                }
            }
        }
    }
}

checkAndCleanDailyWaifu();

void handleCleanWaifu(Object msg, String groupUin, String userUin) {
    checkAndCleanDailyWaifu();
    sendReply(groupUin, msg, "已清理所有过期的未结婚老婆数据");
}

void handleWaifu(Object msg, String groupUin, String userUin) {
    String marriedTo = getString(marriedKey, userUin, null);
    if (marriedTo != null) {
        String spouseName = getMemberName(groupUin, marriedTo);
        String spouseAvatar = "https://q.qlogo.cn/g?b=qq&nk=" + marriedTo + "&s=0";
        sendReply(groupUin, msg, "你已经结婚了，你的结婚老婆是" + spouseName + " [PicUrl=" + spouseAvatar + "]");
        return;
    }
    
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
    
    ArrayList availableSpecified = new ArrayList();
    
    if (specifiedWaifuList != null && specifiedWaifuList.length > 0) {
        for (String specifiedUin : specifiedWaifuList) {
            for (Object member : members) {
                if (member.UserUin.equals(specifiedUin.trim())) {
                    availableSpecified.add(member);
                    break;
                }
            }
        }
    }
    
    Random rand = new Random();
    Object waifu;
    
    if (availableSpecified.size() > 0) {
        int index = rand.nextInt(availableSpecified.size());
        waifu = availableSpecified.get(index);
    } else {
        int index = rand.nextInt(members.size());
        waifu = members.get(index);
    }
    
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
    String reply = "[AtQQ=" + userUin + "] 你今日的老婆是 [AtQQ=" + waifuUin + "] [PicUrl=" + avatarUrl + "]发送 /marry 可以向对方结婚~";
    sendMsg(groupUin, "", reply);
}

void handleMarry(Object msg, String groupUin, String userUin) {
    String marriedTo = getString(marriedKey, userUin, null);
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
    putString(marriedKey, requesterUin, userUin);
    putString(marriedKey, userUin, requesterUin);
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
    sendMsg(groupUin, "", "恭喜 " + requesterName + " 和 " + spouseName + " 结婚啦！ [PicUrl=" + requesterAvatar + "] [PicUrl=" + spouseAvatar + "]");
}

void handleDivorce(Object msg, String groupUin, String userUin) {
    String marriedTo = getString(marriedKey, userUin, null);
    if (marriedTo == null) {
        sendReply(groupUin, msg, "你还没有结婚！");
        return;
    }
    
    putString(marriedKey, userUin, null);
    putString(marriedKey, marriedTo, null);
    
    String spouseName = getMemberName(groupUin, marriedTo);
    sendMsg(groupUin, "", "[AtQQ=" + userUin + "] 已经和 " + spouseName + " 离婚了");
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
    String filePath = appPath + "/语录数据/" + filename;
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
    String filePath = appPath + "/语录数据/" + filename;
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
        } else if (text.equals("/divorce") && waifuOn) {
            handleDivorce(msg, groupUin, userUin);
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
        MonetToasts("仅群聊可用");
        return;
    }
    boolean current = getBoolean(waifuSwitch, groupUin, false);
    putBoolean(waifuSwitch, groupUin, !current);
    MonetToasts("本群每日老婆功能已" + (!current ? "开启" : "关闭"));
}

public void toggleQuote(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        MonetToasts("仅群聊可用");
        return;
    }
    boolean current = getBoolean(quoteSwitch, groupUin, false);
    putBoolean(quoteSwitch, groupUin, !current);
    MonetToasts("本群记录语录功能已" + (!current ? "开启" : "关闭"));
}

public void toggleQrand(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        MonetToasts("仅群聊可用");
        return;
    }
    boolean current = getBoolean(qrandSwitch, groupUin, false);
    putBoolean(qrandSwitch, groupUin, !current);
    MonetToasts("本群随机语录功能已" + (!current ? "开启" : "关闭"));
}

public void cleanWaifuData(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        MonetToasts("仅群聊可用");
        return;
    }
    checkAndCleanDailyWaifu();
    MonetToasts("已清理所有未结婚的老婆数据");
}

public void showUsage(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本使用方法");
            builder.setMessage("/waifu 是抽取每日老婆\n/marry 是求婚 需要对方同意\n/divorce 是离婚\n/q 是记录语录 回复信息\n/qrand 是随机语录 需要先记录语录");
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
            builder.setMessage("- [新增] 指定老婆功能，可设置特定用户作为老婆\n- [新增] 离婚指令 /divorce\n- [更改] 结婚关系改为全局，跨群有效\n- [修复] 抽取到但是未结婚的老婆在其他群也会显示一致的问题\n- [更改] 抽取老婆逻辑 每天定时刷新\n- [更改] Tosat弹窗样式 使用莫奈弹窗\n\n反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

public boolean isDarkMode() {
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
}

public String[] getMonetDarkColors() {
    return new String[] {
        "#FF1E2B3C",
        "#FF2D3A4B",
        "#FF3C495A",
        "#FF4B5869",
        "#FF5A6778"
    };
}

public String[] getMonetLightColors() {
    return new String[] {
        "#FFE8F4F8",
        "#FFD9E8ED",
        "#FFCADCE2",
        "#FFBBD0D7",
        "#FFACC4CC"
    };
}

public String getMonetBackgroundColor() {
    String[] colors = isDarkMode() ? getMonetDarkColors() : getMonetLightColors();
    return colors[0];
}

public String getMonetTextColor() {
    return isDarkMode() ? "#FFE8F4F8" : "#FF1E2B3C";
}

public String getMonetAccentColor() {
    String[] colors = isDarkMode() ? getMonetDarkColors() : getMonetLightColors();
    return colors[2];
}

public int c(float f) {
    return (int) (((((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f) + 0.5f);
}

public GradientDrawable getMonetShape(String baseColor, String accentColor, int cornerRadius) {
    GradientDrawable shape = new GradientDrawable();
    
    int[] colors = new int[] {
        Color.parseColor(baseColor),
        Color.parseColor(accentColor),
        Color.parseColor(baseColor)
    };
    
    shape.setColors(colors);
    shape.setCornerRadius(cornerRadius);
    shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    shape.setOrientation(GradientDrawable.Orientation.TL_BR);
    shape.setAlpha(220);
    shape.setShape(GradientDrawable.RECTANGLE);
    
    shape.setStroke(c(0.8f), Color.parseColor(isDarkMode() ? "#20FFFFFF" : "#20000000"));
    
    return shape;
}

public void MonetToasts(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    String bgColor = getMonetBackgroundColor();
                    String textColor = getMonetTextColor();
                    String accentColor = getMonetAccentColor();
                    
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    
                    int paddingHorizontal = c(20);
                    int paddingVertical = c(14);
                    linearLayout.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                    
                    linearLayout.setBackground(getMonetShape(bgColor, accentColor, c(18)));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(textColor));
                    textView.setTextSize(14.5f);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
                    textView.setShadowLayer(c(1.2f), c(0.8f), c(0.8f), 
                        Color.parseColor(isDarkMode() ? "#40000000" : "#20FFFFFF"));
                    
                    textView.setTypeface(textView.getTypeface(), android.graphics.Typeface.NORMAL);
                    
                    linearLayout.addView(textView);
                    linearLayout.setGravity(Gravity.CENTER);
                    
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.TOP, 0, c(80));
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(linearLayout);
                    toast.show();
                } else {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        }
    });
}

sendLike("2133115301",20);