
// 海枫

// 有的歌只是前奏好听，有的人也只是表面真心

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
import android.view.ViewGroup.LayoutParams;

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

Handler mainHandler;

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
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(linearLayout);
                    toast.show();
                } else {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            } catch(Exception e) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        }
    });
}

ArrayList loadListFromFile(String filePath) {
    ArrayList list = new ArrayList();
    try {
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    list.add(line);
                }
            }
            reader.close();
        }
    } catch (Exception e) {
    }
    return list;
}

void saveListToFile(String filePath, ArrayList list) {
    try {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        FileWriter writer = new FileWriter(filePath);
        for (int i = 0; i < list.size(); i++) {
            writer.write((String)list.get(i) + "\n");
        }
        writer.close();
    } catch (Exception e) {
    }
}

String loadTimeFromFile(String filePath) {
    try {
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String time = reader.readLine();
            reader.close();
            if (time != null && time.trim().matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                return time.trim();
            }
        }
    } catch (Exception e) {
    }
    return null;
}

void saveTimeToFile(String filePath, String time) {
    try {
        File dir = new File(timeConfigPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileWriter writer = new FileWriter(filePath);
        writer.write(time);
        writer.close();
    } catch (Exception e) {
    }
}

void initTimeConfig() {
    try {
        File dir = new File(timeConfigPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String likeTimeFile = timeConfigPath + "/好友点赞时间.txt";
        String friendFireTimeFile = timeConfigPath + "/好友续火时间.txt";
        String groupFireTimeFile = timeConfigPath + "/群组续火时间.txt";
        
        if (!new File(likeTimeFile).exists()) {
            saveTimeToFile(likeTimeFile, "00:00");
        }
        if (!new File(friendFireTimeFile).exists()) {
            saveTimeToFile(friendFireTimeFile, "00:00");
        }
        if (!new File(groupFireTimeFile).exists()) {
            saveTimeToFile(groupFireTimeFile, "00:00");
        }
    } catch (Exception e) {
    }
}

void executeLikeTask() {
    if (selectedFriendsForLike.isEmpty()) return;
    
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < selectedFriendsForLike.size(); i++){
                String friendUin = (String)selectedFriendsForLike.get(i);
                try{
                    sendLike(friendUin, 20);
                }catch(Exception e){
                }
            }
            Toasts("点赞任务完成，共点赞" + selectedFriendsForLike.size() + "位好友");
        }
    }).start();
}

void executeFriendFireTask(){
    if (selectedFriendsForFire.isEmpty()) return;
    
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < selectedFriendsForFire.size(); i++){
                String friendUin = (String)selectedFriendsForFire.get(i);
                try{
                    int randomIndex = (int)(Math.random() * friendFireWords.size());
                    String fireWord = (String)friendFireWords.get(randomIndex);
                    sendMsg("", friendUin, fireWord);
                }catch(Exception e){
                }
            }
            Toasts("好友续火任务完成，共续火" + selectedFriendsForFire.size() + "位好友");
        }
    }).start();
}

void executeGroupFireTask(){
    if (selectedGroupsForFire.isEmpty()) return;
    
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < selectedGroupsForFire.size(); i++){
                String groupUin = (String)selectedGroupsForFire.get(i);
                try{
                    int randomIndex = (int)(Math.random() * groupFireWords.size());
                    String fireWord = (String)groupFireWords.get(randomIndex);
                    sendMsg(groupUin, "", fireWord);
                }catch(Exception e){
                }
            }
            Toasts("群组续火任务完成，共续火" + selectedGroupsForFire.size() + "个群组");
        }
    }).start();
}

public String getCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    return String.format("%04d-%02d-%02d", 
        calendar.get(Calendar.YEAR), 
        calendar.get(Calendar.MONTH) + 1, 
        calendar.get(Calendar.DAY_OF_MONTH));
}

public String getCurrentTime() {
    Calendar calendar = Calendar.getInstance();
    return String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
}

void loadConfig() {
    File configDirFile = new File(configDir);
    if (!configDirFile.exists()) {
        configDirFile.mkdirs();
    }

    selectedFriendsForLike = loadListFromFile(likeFriendsPath);
    selectedFriendsForFire = loadListFromFile(friendFirePath);
    selectedGroupsForFire = loadListFromFile(groupFirePath);
    
    ArrayList loadedFriendWords = loadListFromFile(friendFireWordsPath);
    if (!loadedFriendWords.isEmpty()) {
        friendFireWords = loadedFriendWords;
    } else {
        friendFireWords.add("Ciallo～(∠・ω ＜)⌒☆");
        saveListToFile(friendFireWordsPath, friendFireWords);
    }
    
    ArrayList loadedGroupWords = loadListFromFile(groupFireWordsPath);
    if (!loadedGroupWords.isEmpty()) {
        groupFireWords = loadedGroupWords;
    } else {
        groupFireWords.add("Ciallo～(∠・ω ＜)⌒☆");
        saveListToFile(groupFireWordsPath, groupFireWords);
    }
    
    initTimeConfig();
    
    String likeTimeFile = timeConfigPath + "/好友点赞时间.txt";
    String friendFireTimeFile = timeConfigPath + "/好友续火时间.txt";
    String groupFireTimeFile = timeConfigPath + "/群组续火时间.txt";
    
    String loadedLikeTime = loadTimeFromFile(likeTimeFile);
    if (loadedLikeTime != null) {
        likeTime = loadedLikeTime;
    } else {
        likeTime = "00:00";
    }
    
    String loadedFriendFireTime = loadTimeFromFile(friendFireTimeFile);
    if (loadedFriendFireTime != null) {
        friendFireTime = loadedFriendFireTime;
    } else {
        friendFireTime = "00:00";
    }
    
    String loadedGroupFireTime = loadTimeFromFile(groupFireTimeFile);
    if (loadedGroupFireTime != null) {
        groupFireTime = loadedGroupFireTime;
    } else {
        groupFireTime = "00:00";
    }
    
    lastLikeDate = getString("DailyLike", "lastLikeDate", "");
    lastFriendFireDate = getString("KeepFire", "lastSendDate", "");
    lastGroupFireDate = getString("GroupFire", "lastSendDate", "");
}

void saveLikeFriends() {
    saveListToFile(likeFriendsPath, selectedFriendsForLike);
}

void saveFireFriends() {
    saveListToFile(friendFirePath, selectedFriendsForFire);
}

void saveFireGroups() {
    saveListToFile(groupFirePath, selectedGroupsForFire);
}

void saveTimeConfig() {
    String likeTimeFile = timeConfigPath + "/好友点赞时间.txt";
    String friendFireTimeFile = timeConfigPath + "/好友续火时间.txt";
    String groupFireTimeFile = timeConfigPath + "/群组续火时间.txt";
    
    saveTimeToFile(likeTimeFile, likeTime);
    saveTimeToFile(friendFireTimeFile, friendFireTime);
    saveTimeToFile(groupFireTimeFile, groupFireTime);
}

loadConfig();

mainHandler = new Handler(Looper.getMainLooper());

new Thread(new Runnable() {
    public void run() {
        while(true) {
            try {
                String currentDate = getCurrentDate();
                String currentTime = getCurrentTime();
                
                if (!currentDate.equals(lastLikeDate) && currentTime.equals(likeTime) && !selectedFriendsForLike.isEmpty()) {
                    executeLikeTask();
                    lastLikeDate = currentDate;
                    putString("DailyLike", "lastLikeDate", currentDate);
                    Toasts("已执行好友点赞");
                }
                
                if (!currentDate.equals(lastFriendFireDate) && currentTime.equals(friendFireTime) && !selectedFriendsForFire.isEmpty()) {
                    executeFriendFireTask();
                    lastFriendFireDate = currentDate;
                    putString("KeepFire", "lastSendDate", currentDate);
                    Toasts("已续火" + selectedFriendsForFire.size() + "位好友");
                }
                
                if (!currentDate.equals(lastGroupFireDate) && currentTime.equals(groupFireTime) && !selectedGroupsForFire.isEmpty()) {
                    executeGroupFireTask();
                    lastGroupFireDate = currentDate;
                    putString("GroupFire", "lastSendDate", currentDate);
                    Toasts("已续火" + selectedGroupsForFire.size() + "个群组");
                }
                
                Thread.sleep(60000);
            } catch (Exception e) {
                Thread.sleep(60000);
            }
        }
    }
}).start();

addItem("配置执行任务(先配置这个，选择在哪些地方开启)", "showTargetConfigMenu");
addItem("配置续火语录(也可以不用配置，脚本自带六百个语录，可以自行更改)", "showWordConfigMenu");
addItem("配置执行时间(不配置时间就是默认00:00)", "showTimeConfigMenu");
addItem("立即执行任务(一键执行当前所有任务)", "showExecuteMenu");

public void showExecuteMenu(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;

    activity.runOnUiThread(new Runnable() {
        public void run() {
            String[] items = {"立即点赞好友", "立即续火好友", "立即续火群组", "执行全部任务"};
            
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("立即执行任务");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) confirmAndRun(activity, "立即点赞好友", "确定要立即执行好友点赞任务吗？", new Runnable() { public void run() { immediateLike(groupUin, userUin, chatType); }});
                    else if (which == 1) confirmAndRun(activity, "立即续火好友", "确定要立即执行好友续火任务吗？", new Runnable() { public void run() { immediateFriendFire(groupUin, userUin, chatType); }});
                    else if (which == 2) confirmAndRun(activity, "立即续火群组", "确定要立即执行群组续火任务吗？", new Runnable() { public void run() { immediateGroupFire(groupUin, userUin, chatType); }});
                    else if (which == 3) confirmAndRun(activity, "执行全部任务", "确定要立即执行所有任务吗？", new Runnable() { public void run() { executeAllTasks(); }});
                }
            });
            builder.show();
        }
    });
}

public void showTargetConfigMenu(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;

    activity.runOnUiThread(new Runnable() {
        public void run() {
            String[] items = {"配置点赞好友", "配置续火好友", "配置续火群组"};
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("配置执行任务");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) configLikeFriends(groupUin, userUin, chatType);
                    else if (which == 1) configFireFriends(groupUin, userUin, chatType);
                    else if (which == 2) configFireGroups(groupUin, userUin, chatType);
                }
            });
            builder.show();
        }
    });
}

public void showWordConfigMenu(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;

    activity.runOnUiThread(new Runnable() {
        public void run() {
            String[] items = {"配置好友续火语录", "配置群组续火语录"};
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("配置续火语录");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) configFriendFireWords(groupUin, userUin, chatType);
                    else if (which == 1) configGroupFireWords(groupUin, userUin, chatType);
                }
            });
            builder.show();
        }
    });
}

public void showTimeConfigMenu(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;

    activity.runOnUiThread(new Runnable() {
        public void run() {
            String[] items = {"配置好友点赞时间", "配置好友续火时间", "配置群组续火时间"};
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("配置执行时间");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) configLikeTime(groupUin, userUin, chatType);
                    else if (which == 1) configFriendFireTime(groupUin, userUin, chatType);
                    else if (which == 2) configGroupFireTime(groupUin, userUin, chatType);
                }
            });
            builder.show();
        }
    });
}

private void confirmAndRun(Activity activity, String title, String message, final Runnable action) {
    int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
    int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    
    new AlertDialog.Builder(activity, theme)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                action.run();
            }
        })
        .setNegativeButton("取消", null)
        .show();
}

public void executeAllTasks() {
    long currentTime = System.currentTimeMillis();
    
    if (selectedFriendsForLike.isEmpty() && selectedFriendsForFire.isEmpty() && selectedGroupsForFire.isEmpty()) {
        Toasts("未配置任何任务");
        return;
    }

    if (!selectedFriendsForLike.isEmpty()) {
        executeLikeTask();
        lastLikeClickTime = currentTime;
    }
    
    if (!selectedFriendsForFire.isEmpty()) {
        executeFriendFireTask();
        lastFriendFireClickTime = currentTime;
    }
    
    if (!selectedGroupsForFire.isEmpty()) {
        executeGroupFireTask();
        lastGroupFireClickTime = currentTime;
    }
    
    Toasts("已开始执行所有已配置的任务");
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
    Toasts("正在为" + selectedFriendsForLike.size() + "位好友点赞");
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
    Toasts("已立即续火" + selectedFriendsForFire.size() + "位好友");
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
    Toasts("已立即续火" + selectedGroupsForFire.size() + "个群组");
}

public void configLikeFriends(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList friendList = getFriendList();
                if (friendList == null || friendList.isEmpty()) {
                    Toasts("未添加任何好友");
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList uinList = new ArrayList();
                
                for (int i = 0; i < friendList.size(); i++) {
                    Object friend = friendList.get(i);
                    String uin = "";
                    String name = "";
                    String remark = "";
                    
                    try {
                        uin = friend.uin;
                        name = friend.name;
                        remark = friend.remark;
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
                    displayList.add(displayName);
                    uinList.add(uin);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        showFriendSelectionDialog(activity, displayList, uinList, selectedFriendsForLike, "点赞", "like");
                    }
                });
            } catch (Exception e) {
                Toasts("获取好友列表失败");
            }
        }
    }).start();
}

public void configFireFriends(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList friendList = getFriendList();
                if (friendList == null || friendList.isEmpty()) {
                    Toasts("未添加任何好友");
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList uinList = new ArrayList();
                
                for (int i = 0; i < friendList.size(); i++) {
                    Object friend = friendList.get(i);
                    String uin = "";
                    String name = "";
                    String remark = "";
                    
                    try {
                        uin = friend.uin;
                        name = friend.name;
                        remark = friend.remark;
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
                    displayList.add(displayName);
                    uinList.add(uin);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        showFriendSelectionDialog(activity, displayList, uinList, selectedFriendsForFire, "续火", "fire");
                    }
                });
            } catch (Exception e) {
                Toasts("获取好友列表失败");
            }
        }
    }).start();
}

private void showFriendSelectionDialog(Activity activity, ArrayList displayList, ArrayList uinList, 
                                     ArrayList selectedList, String taskName, String configType) {
    int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
    int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
    dialogBuilder.setTitle("选择" + taskName + "好友");
    dialogBuilder.setCancelable(true);
    
    final ArrayList currentSessionSelected = new ArrayList(selectedList);

    LinearLayout layout = new LinearLayout(activity);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(20, 10, 20, 10);
    
    final EditText searchEditText = new EditText(activity);
    searchEditText.setHint("搜索好友QQ号、好友名、备注");
    searchEditText.setTextColor(Color.BLACK);
    searchEditText.setHintTextColor(Color.GRAY);
    layout.addView(searchEditText);
    
    Button selectAllButton = new Button(activity);
    selectAllButton.setText("全选(当前显示)");
    selectAllButton.setTextColor(Color.WHITE);
    selectAllButton.setBackgroundColor(Color.parseColor("#2196F3"));
    selectAllButton.setPadding(20, 10, 20, 10);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    );
    params.gravity = Gravity.END;
    params.setMargins(0, 10, 0, 10);
    selectAllButton.setLayoutParams(params);
    layout.addView(selectAllButton);
    
    final ListView listView = new ListView(activity);
    layout.addView(listView);
    
    final ArrayList filteredDisplayList = new ArrayList(displayList);
    final ArrayList filteredUinList = new ArrayList(uinList);
    
    final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList);
    listView.setAdapter(adapter);
    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    
    for (int i = 0; i < filteredUinList.size(); i++) {
        String uin = (String)filteredUinList.get(i);
        listView.setItemChecked(i, currentSessionSelected.contains(uin));
    }

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            String uin = (String) filteredUinList.get(position);
            boolean isChecked = listView.isItemChecked(position);
            if (isChecked) {
                if (!currentSessionSelected.contains(uin)) {
                    currentSessionSelected.add(uin);
                }
            } else {
                currentSessionSelected.remove(uin);
            }
        }
    });
    
    searchEditText.addTextChangedListener(new TextWatcher() {
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchText = s.toString().toLowerCase().trim();
            filteredDisplayList.clear();
            filteredUinList.clear();
            
            if (searchText.isEmpty()) {
                filteredDisplayList.addAll(displayList);
                filteredUinList.addAll(uinList);
            } else {
                for (int i = 0; i < displayList.size(); i++) {
                    String displayName = ((String)displayList.get(i)).toLowerCase();
                    String uin = (String)uinList.get(i);
                    
                    if (displayName.contains(searchText) || uin.contains(searchText)) {
                        filteredDisplayList.add(displayList.get(i));
                        filteredUinList.add(uinList.get(i));
                    }
                }
            }
            
            adapter.notifyDataSetChanged();
            
            for (int i = 0; i < filteredUinList.size(); i++) {
                String uin = (String)filteredUinList.get(i);
                listView.setItemChecked(i, currentSessionSelected.contains(uin));
            }
        }
    });
    
    selectAllButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            for (int i = 0; i < filteredUinList.size(); i++) {
                listView.setItemChecked(i, true);
                String uin = (String) filteredUinList.get(i);
                if (!currentSessionSelected.contains(uin)) {
                    currentSessionSelected.add(uin);
                }
            }
        }
    });
    
    dialogBuilder.setView(layout);
    
    dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            selectedList.clear();
            selectedList.addAll(currentSessionSelected);
            
            if (configType.equals("like")) {
                saveLikeFriends();
                Toasts("已选择" + selectedList.size() + "位点赞好友");
            } else if (configType.equals("fire")) {
                saveFireFriends();
                Toasts("已选择" + selectedList.size() + "位续火好友");
            }
        }
    });
    
    dialogBuilder.setNegativeButton("取消", null);
    dialogBuilder.show();
}

public void configFireGroups(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList groupList = getGroupList();
                if (groupList == null || groupList.isEmpty()) {
                    Toasts("未加入任何群组");
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList uinList = new ArrayList();
                for (int i = 0; i < groupList.size(); i++) {
                    Object group = groupList.get(i);
                    String groupName = "";
                    String groupUinStr = "";
                    try {
                        groupName = group.GroupName;
                        groupUinStr = group.GroupUin;
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = groupName + " (" + groupUinStr + ")";
                    displayList.add(displayName);
                    uinList.add(groupUinStr);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        showGroupSelectionDialog(activity, displayList, uinList);
                    }
                });
            } catch (Exception e) {
                Toasts("获取群组列表失败");
            }
        }
    }).start();
}

private void showGroupSelectionDialog(Activity activity, ArrayList displayList, ArrayList uinList) {
    int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
    int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
    dialogBuilder.setTitle("选择续火群组");
    dialogBuilder.setCancelable(true);

    final ArrayList currentSessionSelected = new ArrayList(selectedGroupsForFire);
    
    LinearLayout layout = new LinearLayout(activity);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(20, 10, 20, 10);
    
    final EditText searchEditText = new EditText(activity);
    searchEditText.setHint("搜索群号、群名");
    searchEditText.setTextColor(Color.BLACK);
    searchEditText.setHintTextColor(Color.GRAY);
    layout.addView(searchEditText);
    
    Button selectAllButton = new Button(activity);
    selectAllButton.setText("全选(当前显示)");
    selectAllButton.setTextColor(Color.WHITE);
    selectAllButton.setBackgroundColor(Color.parseColor("#2196F3"));
    selectAllButton.setPadding(20, 10, 20, 10);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    );
    params.gravity = Gravity.END;
    params.setMargins(0, 10, 0, 10);
    selectAllButton.setLayoutParams(params);
    layout.addView(selectAllButton);
    
    final ListView listView = new ListView(activity);
    layout.addView(listView);
    
    final ArrayList filteredDisplayList = new ArrayList(displayList);
    final ArrayList filteredUinList = new ArrayList(uinList);
    
    final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList);
    listView.setAdapter(adapter);
    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    
    for (int i = 0; i < filteredUinList.size(); i++) {
        String uin = (String)filteredUinList.get(i);
        listView.setItemChecked(i, currentSessionSelected.contains(uin));
    }

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            String uin = (String) filteredUinList.get(position);
            boolean isChecked = listView.isItemChecked(position);
            if (isChecked) {
                if (!currentSessionSelected.contains(uin)) {
                    currentSessionSelected.add(uin);
                }
            } else {
                currentSessionSelected.remove(uin);
            }
        }
    });
    
    searchEditText.addTextChangedListener(new TextWatcher() {
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchText = s.toString().toLowerCase().trim();
            filteredDisplayList.clear();
            filteredUinList.clear();
            
            if (searchText.isEmpty()) {
                filteredDisplayList.addAll(displayList);
                filteredUinList.addAll(uinList);
            } else {
                for (int i = 0; i < displayList.size(); i++) {
                    String displayName = ((String)displayList.get(i)).toLowerCase();
                    String uin = (String)uinList.get(i);
                    
                    if (displayName.contains(searchText) || uin.contains(searchText)) {
                        filteredDisplayList.add(displayList.get(i));
                        filteredUinList.add(uinList.get(i));
                    }
                }
            }
            
            adapter.notifyDataSetChanged();
            
            for (int i = 0; i < filteredUinList.size(); i++) {
                String uin = (String)filteredUinList.get(i);
                listView.setItemChecked(i, currentSessionSelected.contains(uin));
            }
        }
    });
    
    selectAllButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            for (int i = 0; i < filteredUinList.size(); i++) {
                listView.setItemChecked(i, true);
                String uin = (String) filteredUinList.get(i);
                if (!currentSessionSelected.contains(uin)) {
                    currentSessionSelected.add(uin);
                }
            }
        }
    });
    
    dialogBuilder.setView(layout);
    
    dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            selectedGroupsForFire.clear();
            selectedGroupsForFire.addAll(currentSessionSelected);
            
            saveFireGroups();
            Toasts("已选择" + selectedGroupsForFire.size() + "个续火群组");
        }
    });
    
    dialogBuilder.setNegativeButton("取消", null);
    
    dialogBuilder.show();
}

public void configFriendFireWords(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) {
        Toasts("无法获取Activity");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder wordsBuilder = new StringBuilder();
                for (int i = 0; i < friendFireWords.size(); i++) {
                    if (wordsBuilder.length() > 0) wordsBuilder.append("\n");
                    wordsBuilder.append((String)friendFireWords.get(i));
                }
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置好友续火语录，多个请另起一行");
                titleView.setTextColor(Color.BLACK);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                titleView.setTypeface(null, android.graphics.Typeface.BOLD);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 10, 0, 20);
                
                final EditText wordsEditText = new EditText(activity);
                wordsEditText.setText(wordsBuilder.toString());
                wordsEditText.setHint("输入好友续火语录，每行一个");
                wordsEditText.setTextColor(Color.BLACK);
                wordsEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                wordsEditText.setHintTextColor(Color.parseColor("#888888"));
                wordsEditText.setMinLines(5);
                wordsEditText.setGravity(Gravity.TOP);
                
                TextView hintView = new TextView(activity);
                hintView.setText("注意：输入多个续火语录时，每行一个");
                hintView.setTextColor(Color.BLACK);
                hintView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                hintView.setPadding(0, 20, 0, 0);
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30, 20, 30, 20);
                layout.addView(titleView);
                layout.addView(wordsEditText);
                layout.addView(hintView);
                
                int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
                int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
                dialogBuilder.setView(layout);
                dialogBuilder.setCancelable(true);
                
                dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String wordsText = wordsEditText.getText().toString().trim();
                        if (wordsText.isEmpty()) {
                            Toasts("续火语录不能为空");
                            return;
                        }
                        
                        friendFireWords.clear();
                        String[] wordsArray = wordsText.split("\n");
                        for (int i = 0; i < wordsArray.length; i++) {
                            String word = wordsArray[i].trim();
                            if (!word.isEmpty()) {
                                friendFireWords.add(word);
                            }
                        }
                        
                        if (friendFireWords.isEmpty()) {
                            Toasts("未添加有效的续火语录");
                            return;
                        }
                        
                        saveListToFile(friendFireWordsPath, friendFireWords);
                        Toasts("已保存 " + friendFireWords.size() + " 个好友续火语录");
                    }
                });
                
                dialogBuilder.setNegativeButton("取消", null);
                
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            } catch (Exception e) {
            }
        }
    });
}

sendLike("2133115301",20);

public void configGroupFireWords(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) {
        Toasts("无法获取Activity");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder wordsBuilder = new StringBuilder();
                for (int i = 0; i < groupFireWords.size(); i++) {
                    if (wordsBuilder.length() > 0) wordsBuilder.append("\n");
                    wordsBuilder.append((String)groupFireWords.get(i));
                }
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置群组续火语录，多个请另起一行");
                titleView.setTextColor(Color.BLACK);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                titleView.setTypeface(null, android.graphics.Typeface.BOLD);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 10, 0, 20);
                
                final EditText wordsEditText = new EditText(activity);
                wordsEditText.setText(wordsBuilder.toString());
                wordsEditText.setHint("输入群组续火语录，每行一个");
                wordsEditText.setTextColor(Color.BLACK);
                wordsEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                wordsEditText.setHintTextColor(Color.parseColor("#888888"));
                wordsEditText.setMinLines(5);
                wordsEditText.setGravity(Gravity.TOP);
                
                TextView hintView = new TextView(activity);
                hintView.setText("注意：输入多个续火语录时，每行一个");
                hintView.setTextColor(Color.BLACK);
                hintView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                hintView.setPadding(0, 20, 0, 0);
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30, 20, 30, 20);
                layout.addView(titleView);
                layout.addView(wordsEditText);
                layout.addView(hintView);
                
                int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
                int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
                dialogBuilder.setView(layout);
                dialogBuilder.setCancelable(true);
                
                dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String wordsText = wordsEditText.getText().toString().trim();
                        if (wordsText.isEmpty()) {
                            Toasts("续火语录不能为空");
                            return;
                        }
                        
                        groupFireWords.clear();
                        String[] wordsArray = wordsText.split("\n");
                        for (int i = 0; i < wordsArray.length; i++) {
                            String word = wordsArray[i].trim();
                            if (!word.isEmpty()) {
                                groupFireWords.add(word);
                            }
                        }
                        
                        if (groupFireWords.isEmpty()) {
                            Toasts("未添加有效的续火语录");
                            return;
                        }
                        
                        saveListToFile(groupFireWordsPath, groupFireWords);
                        Toasts("已保存 " + groupFireWords.size() + " 个群组续火语录");
                    }
                });
                
                dialogBuilder.setNegativeButton("取消", null);
                
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            } catch (Exception e) {
            }
        }
    });
}

public void configLikeTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("设置点赞时间 (HH:mm)");
            dialogBuilder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            final EditText timeEditText = new EditText(activity);
            timeEditText.setText(likeTime);
            timeEditText.setHint("例如: 00:00");
            timeEditText.setTextColor(Color.BLACK);
            timeEditText.setHintTextColor(Color.GRAY);
            layout.addView(timeEditText);
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String timeText = timeEditText.getText().toString().trim();
                    if (timeText.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        likeTime = timeText;
                        saveTimeConfig();
                        Toasts("已设置点赞时间: " + likeTime);
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            dialogBuilder.setNegativeButton("取消", null);
            dialogBuilder.show();
        }
    });
}

public void configFriendFireTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("设置好友续火时间 (HH:mm)");
            dialogBuilder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            final EditText timeEditText = new EditText(activity);
            timeEditText.setText(friendFireTime);
            timeEditText.setHint("例如: 00:00");
            timeEditText.setTextColor(Color.BLACK);
            timeEditText.setHintTextColor(Color.GRAY);
            layout.addView(timeEditText);
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String timeText = timeEditText.getText().toString().trim();
                    if (timeText.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        friendFireTime = timeText;
                        saveTimeConfig();
                        Toasts("已设置好友续火时间: " + friendFireTime);
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            dialogBuilder.setNegativeButton("取消", null);
            dialogBuilder.show();
        }
    });
}

public void configGroupFireTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("设置群组续火时间 (HH:mm)");
            dialogBuilder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            final EditText timeEditText = new EditText(activity);
            timeEditText.setText(groupFireTime);
            timeEditText.setHint("例如: 00:00");
            timeEditText.setTextColor(Color.BLACK);
            timeEditText.setHintTextColor(Color.GRAY);
            layout.addView(timeEditText);
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String timeText = timeEditText.getText().toString().trim();
                    if (timeText.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        groupFireTime = timeText;
                        saveTimeConfig();
                        Toasts("已设置群组续火时间: " + groupFireTime);
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            dialogBuilder.setNegativeButton("取消", null);
            dialogBuilder.show();
        }
    });
}