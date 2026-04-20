
// 作 海枫

// 部分帮助来自临江

// 不支持二改 可借鉴 但是必须标注原作者名

// 必须用到的库 不要动
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

ArrayList likeFriendList = new ArrayList();
String lastLikeDate = "";
String likeTime = "00:00";

ArrayList fireFriendList = new ArrayList();
ArrayList friendFireWords = new ArrayList();
String lastFireFriendDate = "";
String fireFriendTime = "00:00";

ArrayList fireGroupList = new ArrayList();
ArrayList groupFireWords = new ArrayList();
String lastFireGroupDate = "";
String fireGroupTime = "00:00";

long likeCooldown = 0;
long friendFireCooldown = 0;
long groupFireCooldown = 0;

String configRoot = appPath + "/配置文件";
String currentAccountDir = configRoot + "/" + myUin;

String likeFriendsFile = currentAccountDir + "/点赞好友.txt";
String fireFriendsFile = currentAccountDir + "/续火好友.txt";
String fireGroupsFile = currentAccountDir + "/续火群组.txt";

String friendFireWordsFile = appPath + "/续火语录/好友续火语录.txt";
String groupFireWordsFile = appPath + "/续火语录/群组续火语录.txt";
String timeConfigDir = appPath + "/执行时间";

String scriptVersion = "v51.0";

String TARGET_GROUP_NAME = "冷月霜";
String TARGET_GROUP_UIN = "593047854";
String DIALOG_TITLE = "欢迎使用本脚本";
String DIALOG_CONTENT = "为了获取更好的体验和脚本的最新更新，请加入我们的官方交流群！";

int COLOR_BG = Color.parseColor("#F5F3FF");
int COLOR_PRIMARY = Color.parseColor("#DDD6FE");
int COLOR_SURFACE = Color.parseColor("#FFFFFF");
int COLOR_TEXT_PRIMARY = Color.parseColor("#4C1D95");
int COLOR_TEXT_SECONDARY = Color.parseColor("#5B21B6");
int COLOR_DISABLE = Color.parseColor("#EDE9FE");

Handler mainHandler = new Handler(Looper.getMainLooper());

load(appPath + "/核心功能/ConfigManager.java");
load(appPath + "/核心功能/TaskExecutor.java");
load(appPath + "/核心功能/UIManager.java");
load(appPath + "/核心功能/ScheduleManager.java");

loadConfig();

final int[] retryCount = {0};
final int maxRetry = 5;
Handler groupHandler = new Handler(Looper.getMainLooper());
Runnable checkJoinGroup = new Runnable() {
    public void run() {
        Activity activity = getActivity();
        if (activity == null) {
            retryCount[0]++;
            if (retryCount[0] < maxRetry) {
                groupHandler.postDelayed(this, 1000);
            }
            return;
        }

        ArrayList groupList = getGroupList();
        if (groupList == null || groupList.isEmpty()) {
            retryCount[0]++;
            if (retryCount[0] < maxRetry) {
                groupHandler.postDelayed(this, 1000);
            }
            return;
        }

        boolean joined = false;
        for (Object groupInfo : groupList) {
            String groupUin = String.valueOf(groupInfo.GroupUin);
            if (TARGET_GROUP_UIN.equals(groupUin)) {
                joined = true;
                break;
            }
        }

        if (!joined) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    showMd3Dialog(activity);
                }
            });
        }
    }
};
groupHandler.postDelayed(checkJoinGroup, 1000);

Runnable scheduleRunnable = new Runnable() {
    public void run() {
        try {
            String currentDate = getCurrentDate();
            String currentTime = getCurrentTime();
            
            if (!currentDate.equals(lastLikeDate) && currentTime.equals(likeTime) && !likeFriendList.isEmpty()) {
                lastLikeDate = currentDate;
                putString("DailyLike", "lastLikeDate", currentDate);
                executeLikeTask();
            }
            
            if (!currentDate.equals(lastFireFriendDate) && currentTime.equals(fireFriendTime) && !fireFriendList.isEmpty()) {
                lastFireFriendDate = currentDate;
                putString("KeepFire", "lastSendDate", currentDate);
                executeFriendFireTask();
            }
            
            if (!currentDate.equals(lastFireGroupDate) && currentTime.equals(fireGroupTime) && !fireGroupList.isEmpty()) {
                lastFireGroupDate = currentDate;
                putString("GroupFire", "lastSendDate", currentDate);
                executeGroupFireTask();
            }
        } catch (Exception e) {}
        
        mainHandler.postDelayed(this, 10000);
    }
};

mainHandler.post(scheduleRunnable);

addItem("配置执行任务", "configTaskMethod");
addItem("配置续火语录", "configWordsMethod");
addItem("配置执行时间", "configTimeMethod");
addItem("立即执行任务", "executeNowMethod");

public void configTaskMethod(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showMainMenu(a);
}

public void configWordsMethod(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showWordsMenu(a);
}

public void configTimeMethod(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showTimeMenu(a);
}

public void executeNowMethod(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showExecuteMenu(a);
}

public void immediateLike(String groupUin, String userUin, int chatType){
    long now = System.currentTimeMillis();
    if(now - likeCooldown < 60000){
        long remaining = (60000 - (now - likeCooldown)) / 1000;
        Toasts("冷却中，请" + remaining + "秒后再试");
        return;
    }
    likeCooldown = now;
    if (likeFriendList.isEmpty()) {
        Toasts("请先配置要点赞的好友");
        return;
    }
    executeLikeTask();
}

public void immediateFriendFire(String groupUin, String userUin, int chatType){
    long now = System.currentTimeMillis();
    if(now - friendFireCooldown < 60000){
        long remaining = (60000 - (now - friendFireCooldown)) / 1000;
        Toasts("冷却中，请" + remaining + "秒后再试");
        return;
    }
    friendFireCooldown = now;
    if (fireFriendList.isEmpty()) {
        Toasts("请先配置要续火的好友");
        return;
    }
    executeFriendFireTask();
}

public void immediateGroupFire(String groupUin, String userUin, int chatType){
    long now = System.currentTimeMillis();
    if(now - groupFireCooldown < 60000){
        long remaining = (60000 - (now - groupFireCooldown)) / 1000;
        Toasts("冷却中，请" + remaining + "秒后再试");
        return;
    }
    groupFireCooldown = now;
    if (fireGroupList.isEmpty()) {
        Toasts("请先配置要续火的群组");
        return;
    }
    executeGroupFireTask();
}

// ================= 加群弹窗 UI =================
public void showMd3Dialog(Activity activity) {
    Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);

    LinearLayout root = new LinearLayout(activity);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setPadding(60, 60, 60, 60);

    GradientDrawable rootBg = new GradientDrawable();
    rootBg.setColor(COLOR_BG);
    rootBg.setCornerRadius(60f);
    root.setBackground(rootBg);

    TextView titleView = new TextView(activity);
    titleView.setText(DIALOG_TITLE);
    titleView.setTextSize(22);
    titleView.setTextColor(COLOR_TEXT_PRIMARY);
    titleView.getPaint().setFakeBoldText(true);
    root.addView(titleView);

    TextView contentView = new TextView(activity);
    contentView.setText(DIALOG_CONTENT);
    contentView.setTextSize(14);
    contentView.setTextColor(COLOR_TEXT_SECONDARY);
    contentView.setPadding(0, 20, 0, 40);
    root.addView(contentView);

    LinearLayout itemLayout = new LinearLayout(activity);
    itemLayout.setOrientation(LinearLayout.VERTICAL);
    itemLayout.setPadding(40, 30, 40, 30);
    LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    );
    itemParams.setMargins(0, 0, 0, 20);
    itemLayout.setLayoutParams(itemParams);

    GradientDrawable itemBg = new GradientDrawable();
    itemBg.setColor(COLOR_SURFACE);
    itemBg.setCornerRadius(30f);
    itemLayout.setBackground(itemBg);

    TextView nameView = new TextView(activity);
    nameView.setText(TARGET_GROUP_NAME);
    nameView.setTextSize(16);
    nameView.setTextColor(COLOR_TEXT_PRIMARY);
    nameView.getPaint().setFakeBoldText(true);

    TextView uinView = new TextView(activity);
    uinView.setText("群号: " + TARGET_GROUP_UIN);
    uinView.setTextSize(12);
    uinView.setTextColor(COLOR_TEXT_SECONDARY);

    itemLayout.addView(nameView);
    itemLayout.addView(uinView);

    itemLayout.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + TARGET_GROUP_UIN + "&card_type=group&source=qrcode"));
                activity.startActivity(intent);
            } catch (Exception e) {
                Toasts("跳转失败，请检查QQ版本");
            }
        }
    });

    LinearLayout groupListLayout = new LinearLayout(activity);
    groupListLayout.setOrientation(LinearLayout.VERTICAL);
    groupListLayout.addView(itemLayout);

    ScrollView scrollView = new ScrollView(activity);
    scrollView.addView(groupListLayout);
    LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    );
    root.addView(scrollView, scrollParams);

    TextView closeBtn = new TextView(activity);
    closeBtn.setText("我已加群 (5s)");
    closeBtn.setTextSize(16);
    closeBtn.setTextColor(Color.WHITE);
    closeBtn.setGravity(Gravity.CENTER);
    closeBtn.setPadding(0, 30, 0, 30);

    GradientDrawable btnBg = new GradientDrawable();
    btnBg.setColor(COLOR_DISABLE);
    btnBg.setCornerRadius(50f);
    closeBtn.setBackground(btnBg);

    LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    );
    btnParams.setMargins(0, 40, 0, 0);
    root.addView(closeBtn, btnParams);

    dialog.setContentView(root);
    dialog.show();

    Window window = dialog.getWindow();
    if (window != null) {
        window.setLayout(
            (int)(activity.getResources().getDisplayMetrics().widthPixels * 0.85),
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        window.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
    }

    Handler handler = new Handler(Looper.getMainLooper());
    Runnable countdownRunnable = new Runnable() {
        int seconds = 5;
        public void run() {
            seconds--;
            if (seconds > 0) {
                closeBtn.setText("我已加群 (" + seconds + "s)");
                handler.postDelayed(this, 1000);
            } else {
                closeBtn.setText("关闭");
                btnBg.setColor(COLOR_PRIMARY);
                closeBtn.setBackground(btnBg);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }
    };
    handler.postDelayed(countdownRunnable, 1000);
}

/*
try {
    File errorFile = new File(appPath + "/error.txt");
    if (errorFile.exists()) {
        errorFile.delete();
    }
} catch (Exception e) {
}
*/

Toasts("自动点赞续火脚本加载成功 当前QQ:" + myUin);