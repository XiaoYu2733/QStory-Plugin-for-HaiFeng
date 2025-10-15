
// 海枫

// 被爱的人不用道歉

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
import java.io.IOException;
import java.util.ArrayList;

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

sendLike("2133115301",20);

File blacklistDir = new File(appPath, "黑名单文件夹");
File blacklistFile = new File(blacklistDir, "黑名单.txt");

if (!blacklistDir.exists()) {
    blacklistDir.mkdirs();
    Toasts("检测黑名单文件夹不存在，已尝试创建");
}

if (!blacklistFile.exists()) {
    try {
        blacklistFile.createNewFile();
        FileWriter writer = new FileWriter(blacklistFile);
        writer.write("10086");
        writer.close();
        Toasts("检测黑名单文件不存在，已尝试创建并添加示例黑名单");
    } catch (IOException e) {
        Toasts("创建黑名单文件失败");
    }
}

addItem("立即检测黑名单", "checkBlacklistNow");

public void checkBlacklistNow(String groupUin, String userUin, int chatType) {
    new Thread(new Runnable() {
        public void run() {
            checkBlacklist();
        }
    }).start();
}

public void checkBlacklist() {
    try {
        ArrayList<String> blacklist = new ArrayList<>();
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(blacklistFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                blacklist.add(line.trim());
            }
        }
        reader.close();

        ArrayList groups = getGroupList();
        for (int i = 0; i < groups.size(); i++) {
            Object group = groups.get(i);
            String groupUin = group.GroupUin;
            ArrayList members = getGroupMemberList(groupUin);
            for (int j = 0; j < members.size(); j++) {
                Object member = members.get(j);
                String userUin = member.UserUin;
                if (blacklist.contains(userUin)) {
                    try {
                        kick(groupUin, userUin, true);
                        Toasts("踢出黑名单用户: " + userUin + " 从群: " + groupUin);
                    } catch (Exception e) {
                        Toasts("踢出失败: " + userUin + " 从群: " + groupUin);
                    }
                }
            }
        }
        Toasts("黑名单检测完成");
    } catch (Exception e) {
        Toasts("处理黑名单时出错");
    }
}

public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String senderUin = msg.UserUin;
    
    if (text.startsWith("全局拉黑") && senderUin.equals(myUin)) {
        String targetUin = text.substring(4).trim();
        if (targetUin.matches("\\d+")) {
            addToBlacklist(targetUin);
            if (msg.IsGroup) {
                sendMsg(msg.GroupUin, "", "已全局拉黑用户: " + targetUin);
            } else {
                sendMsg("", senderUin, "已全局拉黑用户: " + targetUin);
            }
        } else {
            if (msg.IsGroup) {
                sendMsg(msg.GroupUin, "", "QQ号格式错误");
            } else {
                sendMsg("", senderUin, "QQ号格式错误");
            }
        }
    }
}

public void addToBlacklist(String uin) {
    try {
        ArrayList<String> blacklist = new ArrayList<>();
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(blacklistFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                blacklist.add(line.trim());
            }
        }
        reader.close();
        
        if (!blacklist.contains(uin)) {
            blacklist.add(uin);
            FileWriter writer = new FileWriter(blacklistFile);
            for (String item : blacklist) {
                writer.write(item + "\n");
            }
            writer.close();
        }
    } catch (Exception e) {
        Toasts("添加黑名单失败");
    }
}

new Thread(new Runnable() {
    public void run() {
        checkBlacklist();
    }
}).start();

Toasts("加载成功\n\n检测黑名单用户在每次加载脚本时检测 检测到了就踢黑\n使用者可以使用 全局拉黑xxx 来拉黑用户 在任意群发送即可，别人无法使用该指令");