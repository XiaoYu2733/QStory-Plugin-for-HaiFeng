
// 大家都很普通，却都很想让自己成为别人心里特别的存在

// 自定义骰子方法 可能会在以后版本移除该功能 仅供娱乐 可能以后会添加更多趣味功能

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;
import org.json.JSONObject;

String MD3_PRIMARY = "#6750A4";
String MD3_ON_PRIMARY = "#FFFFFF";
String MD3_SURFACE = "#FEF7FF";
String MD3_ON_SURFACE = "#1C1B1F";
String MD3_SURFACE_VARIANT = "#E7E0EC";
String MD3_ON_SURFACE_VARIANT = "#49454F";
String MD3_DARK_SURFACE = "#1C1B1F";
String MD3_DARK_ON_SURFACE = "#F4EFF4";
String MD3_DARK_SURFACE_VARIANT = "#49454F";
String MD3_DARK_ON_SURFACE_VARIANT = "#CAC4D0";

public void 自定义骰子方法(String groupUin, String userUin, int chatType) {
    if (chatType != 2) {
        toast("不支持私聊");
        return;
    }

    final Object act = getActivity();
    if (act == null) return;

    act.runOnUiThread(new Runnable() {
        public void run() {
            int theme = getCurrentTheme();
            boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            
            String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
            String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
            String surfaceVariantColor = isDark ? MD3_DARK_SURFACE_VARIANT : MD3_SURFACE_VARIANT;
            String onSurfaceVariantColor = isDark ? MD3_DARK_ON_SURFACE_VARIANT : MD3_ON_SURFACE_VARIANT;
            
            LinearLayout root = new LinearLayout(act);
            root.setOrientation(LinearLayout.VERTICAL);
            root.setPadding(c(24), c(24), c(24), c(20));
            root.setBackgroundColor(Color.parseColor(surfaceColor));
            
            TextView title = new TextView(act);
            title.setText("设定骰子点数");
            title.setTextSize(20);
            title.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            title.setTextColor(Color.parseColor(onSurfaceColor));
            title.setPadding(0, 0, 0, c(16));
            root.addView(title);

            final EditText input = new EditText(act);
            input.setHint("1-6");
            input.setHintTextColor(Color.parseColor(onSurfaceVariantColor));
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setGravity(Gravity.CENTER);
            input.setTextColor(Color.parseColor(onSurfaceColor));
            input.setTextSize(16);
            input.setPadding(c(16), c(14), c(16), c(14));
            
            GradientDrawable inputBg = new GradientDrawable();
            inputBg.setColor(Color.parseColor(surfaceVariantColor));
            inputBg.setCornerRadius(c(12));
            input.setBackgroundDrawable(inputBg);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
            lp.setMargins(0, 0, 0, c(24));
            root.addView(input, lp);

            LinearLayout buttonLayout = new LinearLayout(act);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setGravity(Gravity.END);
            
            TextView cancelBtn = new TextView(act);
            cancelBtn.setText("取消");
            cancelBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
            cancelBtn.setTextSize(14);
            cancelBtn.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            cancelBtn.setGravity(Gravity.CENTER);
            cancelBtn.setPadding(c(12), c(8), c(12), c(8));
            cancelBtn.setBackgroundResource(android.R.color.transparent);
            
            TextView confirmBtn = new TextView(act);
            confirmBtn.setText("确定发送");
            confirmBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
            confirmBtn.setTextSize(14);
            confirmBtn.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            confirmBtn.setGravity(Gravity.CENTER);
            confirmBtn.setPadding(c(12), c(8), c(12), c(8));
            confirmBtn.setBackgroundResource(android.R.color.transparent);
            
            buttonLayout.addView(cancelBtn);
            buttonLayout.addView(confirmBtn);
            root.addView(buttonLayout);

            final AlertDialog ad = new AlertDialog.Builder(act, theme).create();
            
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ad.dismiss();
                }
            });
            
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String p = input.getText().toString().trim();
                    if (!p.equals("")) {
                        executeDice(p);
                        ad.dismiss();
                    }
                }
            });

            ad.show();
            Window win = ad.getWindow();
            if (win != null) {
                win.setContentView(root);
                
                win.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                
                GradientDrawable winBg = new GradientDrawable();
                winBg.setColor(Color.parseColor(surfaceColor));
                winBg.setCornerRadius(c(28));
                win.setBackgroundDrawable(winBg);
                
                WindowManager.LayoutParams params = win.getAttributes();
                params.width = (int) (act.getResources().getDisplayMetrics().widthPixels * 0.85);
                win.setAttributes(params);
            }
            
            input.requestFocus();
        }
    });
}

public void executeDice(String point) {
    try {
        String gUin = getCurrentGroupUin();
        JSONObject root = new JSONObject();
        
        JSONObject f1 = new JSONObject();
        JSONObject sub1 = new JSONObject();
        sub1.put("1", Long.parseLong(gUin));
        f1.put("2", sub1);
        root.put("1", f1);

        JSONObject f2 = new JSONObject();
        f2.put("1", 1); f2.put("2", 0); f2.put("3", 0);
        root.put("2", f2);

        JSONObject dice = new JSONObject();
        dice.put("1", "1"); dice.put("2", "33"); dice.put("3", 358);
        dice.put("4", 1); dice.put("5", 2); dice.put("6", point);
        dice.put("7", "/骰子"); dice.put("9", 1);

        JSONObject e53 = new JSONObject();
        e53.put("1", 37); e53.put("2", dice); e53.put("3", 20);
        JSONObject s53 = new JSONObject();
        s53.put("53", e53);
        JSONObject f32 = new JSONObject();
        f32.put("2", s53);
        JSONObject f3 = new JSONObject();
        f3.put("1", f32);
        root.put("3", f3);

        Random r = new Random();
        root.put("4", 10000000L + r.nextInt(90000000));
        root.put("5", 10000000L + r.nextInt(90000000));

        sendProto("MessageSvc.PbSendMsg", root.toString());
        toast("已发送点数: " + point);
    } catch (Exception e) {
        log(e.toString());
    }
}