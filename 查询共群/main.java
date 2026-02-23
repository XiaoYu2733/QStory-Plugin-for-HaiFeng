
// 海枫

// 看落日永远是一件浪漫的事情，如果恰巧你们也在，那就是double

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

String configName = "查询共群开关";
addItem("开启/关闭查询共群功能", "toggleQueryCommonGroup");

public void toggleQueryCommonGroup(String groupUin, String uin, int chatType) {
    if (getBoolean(configName, "switch", false)) {
        putBoolean(configName, "switch", false);
        Toasts("已关闭查询共群功能");
    } else {
        putBoolean(configName, "switch", true);
        Toasts("已开启查询共群功能");
    }
}

void onCreateMenu(Object msg){
    if(msg.IsGroup && !msg.IsSend){
        addMenuItem("查询共群", "queryCommonGroupMenu");
    }
}

void queryCommonGroupMenu(Object msg){
    String targetUin = msg.UserUin;
    int i = 0;
    String result = "";
    
    new Thread(new Runnable() {
        public void run() {
            Object groupList = getGroupList();
            for (Object group : groupList) {
                String groupUin = group.GroupUin;
                Object memberList = getGroupMemberList(groupUin);
                for (Object member : memberList) {
                    String memberUin = member.UserUin;
                    if (memberUin.equals(targetUin)) {
                        Object groupInfo = getGroupInfo(groupUin);
                        String groupName = groupInfo.GroupName;
                        result += (i + 1) + "、" + groupUin + "(" + groupName + ")\n";
                        i++;
                        break;
                    }
                }
            }
            
            final String finalResult = result;
            final int finalCount = i;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (finalCount > 0) {
                        Toasts("与" + targetUin + "有" + finalCount + "个共同群:\n" + finalResult);
                    } else {
                        Toasts("与" + targetUin + "没有共同群");
                    }
                }
            });
        }
    }).start();
}

sendLike("2133115301",20);

public void onMsg(Object msg) {
    if (!msg.UserUin.equals(myUin)) return;
    if (!getBoolean(configName, "switch", false)) return;
    
    String text = msg.MessageContent;
    String qun = msg.GroupUin;
    
    if (text.matches("查询共群[0-9]+")) {
        String qr = text.substring(4);
        int i = 0;
        String result = "";
        sendMsg(qun, "", "查询中，请稍等...");
        
        Object st1 = getGroupList();
        for (Object c : st1) {
            String Qun = c.GroupUin;
            Object st = getGroupMemberList(Qun);
            for (Object b : st) {
                String qrr = b.UserUin;
                if (qrr.equals(qr)) {
                    Object groupInfo = getGroupInfo(Qun);
                    String groupName = groupInfo.GroupName;
                    result += (i + 1) + "、" + Qun + "(" + groupName + ")\n";
                    i++;
                }
            }
        }
        sendMsg(qun, "", "你与" + qr + "的共同群如下:\n\n" + result);
    }
}