
// 作 海枫

// 部分帮助来自临江

// 不支持二改 可借鉴 但是必须标注原作者名

// 必须用到的库 不要动
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import android.graphics.Typeface;
import android.widget.ScrollView;
import android.util.DisplayMetrics;

ArrayList 落叶叶子叶落子飘 = new ArrayList();
String 飘花叶言飘花 = "";
String 叶飘叶落言叶子叶落子 = "00:00";

ArrayList 落言花飘言落言 = new ArrayList();
ArrayList 飘飘叶飘 = new ArrayList();
String 言子言叶花子落 = "";
String 飘飘花花 = "00:00";

ArrayList 飘飘花言飘飘 = new ArrayList();
ArrayList 叶落花落 = new ArrayList();
String 落叶子子子叶 = "";
String 子言花言飘叶落飘 = "00:00";

long 叶言飘言花飘叶叶花 = 0;
long 言落子子子 = 0;
long 子花子飘 = 0;

String 花飘言子 = appPath + "/配置文件";
String 当前账号目录 = 花飘言子 + "/" + myUin;

String 叶花落叶叶落花叶 = 当前账号目录 + "/点赞好友.txt";
String 飘飘叶花飘落飘落 = 当前账号目录 + "/续火好友.txt";
String 子叶言飘子言花言花叶 = 当前账号目录 + "/续火群组.txt";

String 落叶花花飘言子子飘花 = appPath + "/续火语录/好友续火语录.txt";
String 子叶花花花飘 = appPath + "/续火语录/群组续火语录.txt";
String 花叶落飘落 = appPath + "/执行时间";

String 当前脚本版本 = "v49.0";

Handler 叶落飘花 = new Handler(Looper.getMainLooper());

load(appPath + "/核心功能/ConfigManager.java");
load(appPath + "/核心功能/TaskExecutor.java");
load(appPath + "/核心功能/UIManager.java");
load(appPath + "/核心功能/ScheduleManager.java");

loadConfig();

String 上次显示版本 = getString("UpdateLog", "lastShownVersion", "");
if (!当前脚本版本.equals(上次显示版本)) {
    final int[] 尝试次数 = {0};
    final int 最大尝试 = 5;
    Handler 重试处理器 = new Handler(Looper.getMainLooper());
    Runnable 尝试显示 = new Runnable() {
        public void run() {
            Activity 活动 = getActivity();
            if (活动 != null) {
                showUpdateLogDialog(活动);
            } else {
                尝试次数[0]++;
                if (尝试次数[0] < 最大尝试) {
                    重试处理器.postDelayed(this, 1000);
                } else {
                    Toasts("妹妹吗");
                }
            }
        }
    };
    重试处理器.post(尝试显示);
}

Runnable 子言子花花子 = new Runnable() {
    public void run() {
        try {
            String 当前日期 = getCurrentDate();
            String 当前时间 = getCurrentTime();
            
            if (!当前日期.equals(飘花叶言飘花) && 当前时间.equals(叶飘叶落言叶子叶落子) && !落叶叶子叶落子飘.isEmpty()) {
                飘花叶言飘花 = 当前日期;
                putString("DailyLike", "lastLikeDate", 当前日期);
                executeLikeTask();
            }
            
            if (!当前日期.equals(言子言叶花子落) && 当前时间.equals(飘飘花花) && !落言花飘言落言.isEmpty()) {
                言子言叶花子落 = 当前日期;
                putString("KeepFire", "lastSendDate", 当前日期);
                executeFriendFireTask();
            }
            
            if (!当前日期.equals(落叶子子子叶) && 当前时间.equals(子言花言飘叶落飘) && !飘飘花言飘飘.isEmpty()) {
                落叶子子子叶 = 当前日期;
                putString("GroupFire", "lastSendDate", 当前日期);
                executeGroupFireTask();
            }
        } catch (Exception e) {}
        
        叶落飘花.postDelayed(this, 10000);
    }
};

叶落飘花.post(子言子花花子);

addItem("配置执行任务", "配置执行任务方法");
addItem("配置续火语录", "配置续火语录方法");
addItem("配置执行时间", "配置执行时间方法");
addItem("立即执行任务", "立即执行任务方法");

public void 配置执行任务方法(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showMainMenu(a);
}

public void 配置续火语录方法(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showWordsMenu(a);
}

public void 配置执行时间方法(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showTimeMenu(a);
}

public void 立即执行任务方法(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showExecuteMenu(a);
}

public void immediateLike(String 群号, String 用户, int 类型){
    long 当前时间 = System.currentTimeMillis();
    if(当前时间 - 叶言飘言花飘叶叶花 < 60000){
        long 剩余时间 = (60000 - (当前时间 - 叶言飘言花飘叶叶花)) / 1000;
        Toasts("冷却中，请" + 剩余时间 + "秒后再试");
        return;
    }
    叶言飘言花飘叶叶花 = 当前时间;
    if (落叶叶子叶落子飘.isEmpty()) {
        Toasts("请先配置要点赞的好友");
        return;
    }
    executeLikeTask();
}

public void immediateFriendFire(String 群号, String 用户, int 类型){
    long 当前时间 = System.currentTimeMillis();
    if(当前时间 - 言落子子子 < 60000){
        long 剩余时间 = (60000 - (当前时间 - 言落子子子)) / 1000;
        Toasts("冷却中，请" + 剩余时间 + "秒后再试");
        return;
    }
    言落子子子 = 当前时间;
    if (落言花飘言落言.isEmpty()) {
        Toasts("请先配置要续火的好友");
        return;
    }
    executeFriendFireTask();
}

public void immediateGroupFire(String 群号, String 用户, int 类型){
    long 当前时间 = System.currentTimeMillis();
    if(当前时间 - 子花子飘 < 60000){
        long 剩余时间 = (60000 - (当前时间 - 子花子飘)) / 1000;
        Toasts("冷却中，请" + 剩余时间 + "秒后再试");
        return;
    }
    子花子飘 = 当前时间;
    if (飘飘花言飘飘.isEmpty()) {
        Toasts("请先配置要续火的群组");
        return;
    }
    executeGroupFireTask();
}

public void showUpdateLogDialog(Activity 活动) {
    if (活动 == null) return;
    
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int 主题 = getCurrentTheme();
                String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                String 卡片背景色 = getCardColor();
                String 文本颜色 = getTextColor();
                String 次要文本颜色 = getSubTextColor();
                String 边框颜色 = getBorderColor();
                
                LinearLayout 根布局 = new LinearLayout(活动);
                根布局.setOrientation(LinearLayout.VERTICAL);
                根布局.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
                根布局.setBackground(getWebShape(卡片背景色, dp2px(20)));
                
                TextView 标题 = new TextView(活动);
                标题.setText("自动点赞+续火更新日志");
                标题.setTextColor(Color.parseColor(文本颜色));
                标题.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                标题.setTypeface(null, Typeface.BOLD);
                标题.setGravity(Gravity.CENTER);
                标题.setPadding(0, 0, 0, dp2px(12));
                根布局.addView(标题);
                
                TextView 内容 = new TextView(活动);
                内容.setText("· [优化] 切换账号时QS自动关闭脚本，重开即可；配置按账号隔离，加载时自动获取QQ号\n· [优化] 续火间隔5-30秒，降低风控概率\n\nTG交流群：https://t.me/XiaoYu_Chat");
                内容.setTextColor(Color.parseColor(次要文本颜色));
                内容.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                内容.setLineSpacing(dp2px(4), 1.2f);
                内容.setPadding(dp2px(4), dp2px(8), dp2px(4), dp2px(16));
                根布局.addView(内容);
                
                LinearLayout 按钮栏 = new LinearLayout(活动);
                按钮栏.setOrientation(LinearLayout.HORIZONTAL);
                按钮栏.setGravity(Gravity.END);
                LinearLayout.LayoutParams 按钮栏参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                按钮栏.setLayoutParams(按钮栏参数);
                
                Button 不同意按钮 = new Button(活动);
                不同意按钮.setText("朕不同意");
                不同意按钮.setTextColor(Color.parseColor(强调色));
                不同意按钮.setBackground(null);
                不同意按钮.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
                不同意按钮.setTypeface(null, Typeface.BOLD);
                不同意按钮.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                LinearLayout.LayoutParams 不同意参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                不同意参数.setMargins(0, 0, dp2px(8), 0);
                不同意按钮.setLayoutParams(不同意参数);
                不同意按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        System.exit(0);
                    }
                });
                按钮栏.addView(不同意按钮);
                
                Button 确定按钮 = new Button(活动);
                确定按钮.setText("朕知道了");
                确定按钮.setTextColor(Color.WHITE);
                确定按钮.setBackground(getShape(强调色, dp2px(20)));
                确定按钮.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
                确定按钮.setTypeface(null, Typeface.BOLD);
                确定按钮.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                LinearLayout.LayoutParams 确定参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                确定按钮.setLayoutParams(确定参数);
                按钮栏.addView(确定按钮);
                
                根布局.addView(按钮栏);
                
                AlertDialog.Builder 构建器 = new AlertDialog.Builder(活动, 主题);
                构建器.setView(根布局);
                
                AlertDialog 对话框 = 构建器.create();
                对话框.setCancelable(false);
                对话框.setCanceledOnTouchOutside(false);
                对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                
                final AlertDialog[] dialogHolder = new AlertDialog[1];
                dialogHolder[0] = 对话框;
                
                确定按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        putString("UpdateLog", "lastShownVersion", 当前脚本版本);
                        if (dialogHolder[0] != null) {
                            dialogHolder[0].dismiss();
                        }
                    }
                });
                
                对话框.show();
                根布局.setTag(对话框);
                
            } catch (Exception e) {
                Toasts("显示更新日志失败");
            }
        }
    });
}

try {
    File 花飘言子 = new File(appPath + "/error.txt");
    if (花飘言子.exists()) {
        花飘言子.delete();
    }
} catch (Exception 落叶叶子叶落子飘) {
}

Toasts("自动点赞续火脚本加载成功 当前QQ:" + myUin);