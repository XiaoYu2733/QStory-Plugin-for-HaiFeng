
// 海枫

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

void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    if (msg.mAtList == null) return;
    
    boolean atMe = false;
    for (String atUin : msg.mAtList) {
        if (atUin.equals(myUin)) {
            atMe = true;
            break;
        }
    }
    if (!atMe) return;
    
    String group = msg.GroupUin;
    String senderUin = msg.UserUin;
    String senderName = getMemberName(group, senderUin);
    if (senderName == null) senderName = senderUin;
    
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String timeStr = sdf.format(new java.util.Date(msg.MessageTime));
    
    String content = msg.MessageContent.replaceAll("\\[AtQQ=\\d+\\]", "").trim();
    
    Toasts("在群" + group + "被" + senderName + "艾特了");
    
    String privateMsg = "群聊：" + group + "\n" +
                        "艾特者：" + senderName + "(" + senderUin + ")\n" +
                        "艾特内容：" + content + "\n" +
                        "艾特时间：" + timeStr;
    sendMsg("", myUin, privateMsg);
}

public boolean isDarkMode() {
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
}

public String getBackgroundColor() {
    return isDarkMode() ? "#801E1E1E" : "#80FFFFFF";
}

public String getTextColor() {
    return isDarkMode() ? "#E0E0E0" : "#333333";
}

public String getGlassEffectColor() {
    return isDarkMode() ? "#40FFFFFF" : "#40000000";
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

public GradientDrawable getGlassEffectShape(String baseColor, String glassColor, int cornerRadius) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColors(new int[]{
        Color.parseColor(baseColor),
        Color.parseColor(glassColor),
        Color.parseColor(baseColor)
    });
    shape.setCornerRadius(cornerRadius);
    shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    shape.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
    shape.setAlpha(180);
    shape.setShape(GradientDrawable.RECTANGLE);
    
    shape.setStroke(c(1), Color.parseColor(isDarkMode() ? "#30FFFFFF" : "#30000000"));
    return shape;
}

public void Toasts(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    String bgColor = getBackgroundColor();
                    String textColor = getTextColor();
                    String glassColor = getGlassEffectColor();
                    
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    
                    int paddingHorizontal = c(18);
                    int paddingVertical = c(12);
                    linearLayout.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                    
                    linearLayout.setBackground(getGlassEffectShape(bgColor, glassColor, c(16)));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(textColor));
                    textView.setTextSize(14.5f);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
                    textView.setShadowLayer(c(1), c(0.5f), c(0.5f), 
                        Color.parseColor(isDarkMode() ? "#40000000" : "#40FFFFFF"));
                    
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

Toasts("加载成功，所有群已生效，被艾特提醒并私聊自己");