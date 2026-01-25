
// 作 海枫 临江踏雨不返

// 希望有人懂你的言外之意 更懂你的欲言又止

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
String 叶花落叶叶落花叶 = 花飘言子 + "/点赞好友.txt";
String 飘飘叶花飘落飘落 = 花飘言子 + "/续火好友.txt";
String 子叶言飘子言花言花叶 = 花飘言子 + "/续火群组.txt";

String 落叶花花飘言子子飘花 = appPath + "/续火语录/好友续火语录.txt";
String 子叶花花花飘 = appPath + "/续火语录/群组续火语录.txt";
String 花叶落飘落 = appPath + "/执行时间";

Handler 叶落飘花 = new Handler(Looper.getMainLooper());

load(appPath + "/核心功能/ConfigManager.java");
load(appPath + "/核心功能/TaskExecutor.java");
load(appPath + "/核心功能/UIManager.java");
load(appPath + "/核心功能/ScheduleManager.java");

loadConfig();

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

try {
    File 花飘言子 = new File(appPath + "/error.txt");
    if (花飘言子.exists()) {
        花飘言子.delete();
    }
} catch (Exception 落叶叶子叶落子飘) {
}