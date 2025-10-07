// 海枫

// 时间真的是解药吗 为什么我越来越痛

import android.widget.TextView;
import android.widget.ScrollView;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Looper;
import android.os.Handler;
import android.app.Activity;
import java.util.ArrayList;

// 等太阳升起就把昨天忘掉
// 我想以朋友身份在你身边待很久很久

addMenuItem("自由复制", "freeCopy");

// 你根本不懂 你走后我生了一辈子的疑心病

void freeCopy(Object msg) {
    String content = "";
    
    if (msg.MessageType == 4) {
        content = "语音消息\n";
        content += "MD5: " + msg.MessageContent + "\n";
        if (msg.LocalPath != null && !msg.LocalPath.equals("")) {
            content += "本地路径: " + msg.LocalPath + "\n";
        }
        content += "语音时长等信息需要额外解析";
    } else if (msg.MessageType == 1 && msg.PicUrlList != null && msg.PicUrlList.size() > 0) {
        content = "图片/表情消息\n";
        content += "图片/表情链接:\n";
        ArrayList<String> picList = new ArrayList<String>(msg.PicUrlList);
        for (int i = 0; i < picList.size(); i++) {
            content += (i + 1) + ". " + picList.get(i) + "\n";
        }
    } else {
        content = msg.MessageContent;
    }
    
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    int theme = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
        theme = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
    }
    
    // 想不想看花海盛开 想不想看燕子归来
    
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
                    .setTitle("消息内容 - 长按可自由复制")
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

// 但我们之间 连可能都没有 谈如何可以

sendLike("2133115301",20);

// 他明明对谁都那样 为什么只有你以为自己很特别
// 任何人投入进来 都像是一个小孩