
// 作 海枫

// 时间真的是解药吗 为什么我越来越痛

import android.widget.TextView;
import android.widget.ScrollView;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Looper;
import android.os.Handler;
import android.app.Activity;

addMenuItem("自由复制", "freeCopy");

void freeCopy(Object msg) {
    String content = msg.MessageContent;
    
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    int theme = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
        theme = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
    }
    
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                Activity activity = getActivity();
                if (activity == null || activity.isFinishing()) {
                    toast("请先打开QQ聊天界面");
                    return;
                }
                
                TextView textView = new TextView(activity);
                textView.setText(content);
                textView.setTextIsSelectable(true);
                textView.setTextSize(16);
                
                ScrollView scrollView = new ScrollView(activity);
                scrollView.addView(textView);
                
                AlertDialog dialog = new AlertDialog.Builder(activity, theme)
                    .setTitle("消息内容")
                    .setView(scrollView)
                    .setPositiveButton("关闭", null)
                    .create();
                
                if (!activity.isFinishing()) {
                    dialog.show();
                }
            } catch (Exception e) {
                error(e);
                toast("显示弹窗失败: " + e.getMessage());
            }
        }
    });
}

sendLike("2133115301",20);