
// 海枫

// 两个人都痛的话早就和好了

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
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public boolean isDarkMode() {
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
}

public String getBackgroundColor() {
    return isDarkMode() ? "#CC1E1E1E" : "#CCFFFFFF";
}

public String getTextColor() {
    return isDarkMode() ? "#E0E0E0" : "#333333";
}

public int c(float f) {
    return (int) (((((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f) + 0.5f);
}

public GradientDrawable getShape(String color, int cornerRadius, int alpha) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(color));
    shape.setCornerRadius(cornerRadius);
    shape.setAlpha(alpha);
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

public void Toasts(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    String bgColor = getBackgroundColor();
                    String textColor = getTextColor();
                    
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    
                    int paddingHorizontal = c(18);
                    int paddingVertical = c(12);
                    linearLayout.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                    
                    linearLayout.setBackground(getShape(bgColor, c(12), 230));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(textColor));
                    textView.setTextSize(14.5f);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
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

String configName = "AutoReplyPai";
String quotesFolderPath = appPath + "/拍一拍语录";
String quotesFilePath = quotesFolderPath + "/被拍语录.txt";

File quotesFolder = new File(quotesFolderPath);
File quotesFile = new File(quotesFilePath);

if (!quotesFolder.exists()) {
    quotesFolder.mkdirs();
    Toasts("检测文件夹不存在，将为你创建一个");
}

if (!quotesFile.exists()) {
    try {
        quotesFile.createNewFile();
        FileWriter writer = new FileWriter(quotesFile);
        writer.write("拍我干什么\n真是知人知面不知心 唧唧小到看不清\n别拍了\n别拍了，要坏掉了");
        writer.close();
        Toasts("检测文件不存在，将为你创建一个");
    } catch (Exception e) {
        error(e);
    }
}

addItem("拍一拍自动回复开关", "toggleAutoReply");

sendLike("2133115301",20);

public void toggleAutoReply(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        Toasts("不支持私聊开启");
        return;
    }
    
    if (getBoolean(configName, groupUin, false)) {
        putBoolean(configName, groupUin, false);
        Toasts("已关闭" + groupUin + "的拍一拍自动回复");
    } else {
        putBoolean(configName, groupUin, true);
        Toasts("已开启" + groupUin + "的拍一拍自动回复");
    }
}

public boolean isAutoReplyEnabled(String groupUin) {
    return getBoolean(configName, groupUin, false);
}

void callbackOnRawMsg(Object msg) {
    try {
        if (msg == null) return;
        
        String senderUin = null;
        String targetUin = null;
        boolean isGroup = false;
        String groupUin = null;
        
        String msgStr = msg.toString();
        
        if (!msgStr.contains("GrayTipElement") && 
            !msgStr.contains("XmlElement") && 
            !msgStr.contains("templParam")) {
            return;
        }
        
        Pattern pattern = Pattern.compile("uin_str[12]=(\\d+)");
        Matcher matcher = pattern.matcher(msgStr);
        
        while (matcher.find()) {
            String fullMatch = matcher.group(0);
            String[] parts = fullMatch.split("=");
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];
                
                if ("uin_str1".equals(key)) {
                    senderUin = value;
                } else if ("uin_str2".equals(key)) {
                    targetUin = value;
                }
            }
        }
        
        try {
            isGroup = msg.chatType == 2;
            if (isGroup) {
                groupUin = msg.peerUid;
            }
        } catch (Exception e) {
            if (msgStr.contains("GroupUin") || msgStr.contains("群")) {
                isGroup = true;
                pattern = Pattern.compile("GroupUin=(\\d+)");
                matcher = pattern.matcher(msgStr);
                if (matcher.find()) {
                    groupUin = matcher.group(1);
                }
            }
        }
        
        if (senderUin == null || targetUin == null) return;
        
        if (!targetUin.equals(myUin)) return;
        
        if (senderUin.equals(myUin)) return;
        
        if (senderUin.equals(targetUin)) return;
        
        if (isGroup && groupUin != null) {
            if (!isAutoReplyEnabled(groupUin)) {
                return;
            }
            
            String randomQuote = getRandomQuote();
            if (randomQuote != null) {
                sendMsg(groupUin, "", "[AtQQ=" + senderUin + "] " + randomQuote);
            }
        }
        
    } catch (Exception e) {
    }
}

String getRandomQuote() {
    try {
        ArrayList<String> quotes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(quotesFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                quotes.add(line.trim());
            }
        }
        reader.close();
        
        if (!quotes.isEmpty()) {
            Random random = new Random();
            return quotes.get(random.nextInt(quotes.size()));
        }
    } catch (Exception e) {
        error(e);
    }
    return null;
}

Toasts("拍一拍自动回复加载成功\n\n如果需要自己更改拍一拍语录 需要访问 /storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/Plugin/被拍自动回复/拍一拍语录/被拍语录.txt\n\n语录支持多个\n\n如果不会改的话那就受着吧");