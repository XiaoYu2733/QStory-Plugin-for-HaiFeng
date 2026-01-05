
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

ArrayList selectedFriendsForLike = new ArrayList();
String lastLikeDate = "";
String likeTime = "00:00";

ArrayList selectedFriendsForFire = new ArrayList();
ArrayList friendFireWords = new ArrayList();
String lastFriendFireDate = "";
String friendFireTime = "00:00";

ArrayList selectedGroupsForFire = new ArrayList();
ArrayList groupFireWords = new ArrayList();
String lastGroupFireDate = "";
String groupFireTime = "00:00";

long lastLikeClickTime = 0;
long lastFriendFireClickTime = 0;
long lastGroupFireClickTime = 0;

String configDir = appPath + "/配置文件";
String likeFriendsPath = configDir + "/点赞好友.txt";
String friendFirePath = configDir + "/续火好友.txt";
String groupFirePath = configDir + "/续火群组.txt";

String friendFireWordsPath = appPath + "/续火语录/好友续火语录.txt";
String groupFireWordsPath = appPath + "/续火语录/群组续火语录.txt";
String timeConfigPath = appPath + "/执行时间";

Handler mainHandler = new Handler(Looper.getMainLooper());

load(appPath + "/核心功能/ConfigManager.java");
load(appPath + "/核心功能/TaskExecutor.java");
load(appPath + "/核心功能/UIManager.java");
load(appPath + "/核心功能/ScheduleManager.java");

loadConfig();

Runnable timerRunnable = new Runnable() {
    public void run() {
        try {
            String currentDate = getCurrentDate();
            String currentTime = getCurrentTime();
            
            if (!currentDate.equals(lastLikeDate) && currentTime.equals(likeTime) && !selectedFriendsForLike.isEmpty()) {
                lastLikeDate = currentDate;
                putString("DailyLike", "lastLikeDate", currentDate);
                executeLikeTask();
            }
            
            if (!currentDate.equals(lastFriendFireDate) && currentTime.equals(friendFireTime) && !selectedFriendsForFire.isEmpty()) {
                lastFriendFireDate = currentDate;
                putString("KeepFire", "lastSendDate", currentDate);
                executeFriendFireTask();
            }
            
            if (!currentDate.equals(lastGroupFireDate) && currentTime.equals(groupFireTime) && !selectedGroupsForFire.isEmpty()) {
                lastGroupFireDate = currentDate;
                putString("GroupFire", "lastSendDate", currentDate);
                executeGroupFireTask();
            }
        } catch (Exception e) {}
        
        mainHandler.postDelayed(this, 10000);
    }
};

mainHandler.post(timerRunnable);

addItem("配置执行任务", "menu1");
addItem("配置续火语录", "menu2");
addItem("配置执行时间", "menu3");
addItem("立即执行任务", "menu4");

public void menu1(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showMainMenu(a);
}

public void menu2(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showWordsMenu(a);
}

public void menu3(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showTimeMenu(a);
}

public void menu4(String g, String u, int t) {
    Activity a = getActivity();
    if (a == null) {
        Toasts("请在前台打开QQ");
        return;
    }
    showExecuteMenu(a);
}

public void immediateLike(String groupUin, String userUin, int chatType){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastLikeClickTime < 60000){
        long remainingTime = (60000 - (currentTime - lastLikeClickTime)) / 1000;
        Toasts("冷却中，请" + remainingTime + "秒后再试");
        return;
    }
    lastLikeClickTime = currentTime;
    if (selectedFriendsForLike.isEmpty()) {
        Toasts("请先配置要点赞的好友");
        return;
    }
    executeLikeTask();
}

public void immediateFriendFire(String groupUin, String userUin, int chatType){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastFriendFireClickTime < 60000){
        long remainingTime = (60000 - (currentTime - lastFriendFireClickTime)) / 1000;
        Toasts("冷却中，请" + remainingTime + "秒后再试");
        return;
    }
    lastFriendFireClickTime = currentTime;
    if (selectedFriendsForFire.isEmpty()) {
        Toasts("请先配置要续火的好友");
        return;
    }
    executeFriendFireTask();
}

public void immediateGroupFire(String groupUin, String userUin, int chatType){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastGroupFireClickTime < 60000){
        long remainingTime = (60000 - (currentTime - lastGroupFireClickTime)) / 1000;
        Toasts("冷却中，请" + remainingTime + "秒后再试");
        return;
    }
    lastGroupFireClickTime = currentTime;
    if (selectedGroupsForFire.isEmpty()) {
        Toasts("请先配置要续火的群组");
        return;
    }
    executeGroupFireTask();
}