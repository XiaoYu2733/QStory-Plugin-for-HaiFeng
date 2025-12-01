
// 海枫

// 睡觉可能是唯一轻松的事

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
import java.text.SimpleDateFormat;
import java.util.Date;

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

String friendFireWordsPath = appPath + "/续火词/好友续火词.txt";
String groupFireWordsPath = appPath + "/续火词/群组续火词.txt";
String timeConfigPath = appPath + "/执行时间";

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

ArrayList loadWordsFromFile(String filePath) {
    ArrayList wordsList = new ArrayList();
    try {
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    wordsList.add(line);
                }
            }
            reader.close();
        }
    } catch (Exception e) {
    }
    return wordsList;
}

void saveWordsToFile(String filePath, ArrayList wordsList) {
    try {
        File dir = new File(appPath + "/续火词");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileWriter writer = new FileWriter(filePath);
        for (int i = 0; i < wordsList.size(); i++) {
            writer.write((String)wordsList.get(i) + "\n");
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
            if (time != null && isValidTimeFormat(time.trim())) {
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
    new Thread(new Runnable(){
    public void run(){
        for(int i = 0; i < selectedFriendsForLike.size(); i++){
            String friendUin = (String)selectedFriendsForLike.get(i);
            try{
                sendLike(friendUin, 20);
            }catch(Exception e){
            }
        }
    }
}).start();
}

void executeFriendFireTask(){
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
        }
    }).start();
}

void executeGroupFireTask(){
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
        }
    }).start();
}

void checkMissedTasks() {
    Calendar calendar = Calendar.getInstance();
    String currentDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    int currentMinute = calendar.get(Calendar.MINUTE);
    int currentTimeInMinutes = currentHour * 60 + currentMinute;
    
    int[] likeTimeArray = parseTime(likeTime);
    int likeHour = likeTimeArray[0];
    int likeMinute = likeTimeArray[1];
    int likeTimeInMinutes = likeHour * 60 + likeMinute;
    
    int[] friendFireTimeArray = parseTime(friendFireTime);
    int friendFireHour = friendFireTimeArray[0];
    int friendFireMinute = friendFireTimeArray[1];
    int friendFireTimeInMinutes = friendFireHour * 60 + friendFireMinute;
    
    int[] groupFireTimeArray = parseTime(groupFireTime);
    int groupFireHour = groupFireTimeArray[0];
    int groupFireMinute = groupFireTimeArray[1];
    int groupFireTimeInMinutes = groupFireHour * 60 + groupFireMinute;
    
    if (!currentDate.equals(lastLikeDate) && currentTimeInMinutes >= likeTimeInMinutes) {
        executeLikeTask();
        lastLikeDate = currentDate;
        putString("DailyLike", "lastLikeDate", currentDate);
        Toasts("检测到错过点赞任务，已立即执行");
    }
    
    if (!currentDate.equals(lastFriendFireDate) && currentTimeInMinutes >= friendFireTimeInMinutes) {
        executeFriendFireTask();
        lastFriendFireDate = currentDate;
        putString("KeepFire", "lastSendDate", currentDate);
        Toasts("检测到错过好友续火任务，已立即执行");
    }
    
    if (!currentDate.equals(lastGroupFireDate) && currentTimeInMinutes >= groupFireTimeInMinutes) {
        executeGroupFireTask();
        lastGroupFireDate = currentDate;
        putString("GroupFire", "lastSendDate", currentDate);
        Toasts("检测到错过群续火任务，已立即执行");
    }
}

int[] parseTime(String timeStr) {
    int[] timeArray = new int[]{0, 0};
    try {
        String[] timeParts = timeStr.split(":");
        if (timeParts.length == 2) {
            timeArray[0] = Integer.parseInt(timeParts[0]);
            timeArray[1] = Integer.parseInt(timeParts[1]);
        }
    } catch (Exception e) {
    }
    return timeArray;
}

public String TIME(int t) {  
    SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm:ss");
    if(t==2) df = new SimpleDateFormat("HH:mm");
    if(t==5) df = new SimpleDateFormat("yyyyMMdd");
    if(t==7) df = new SimpleDateFormat("yyyyMM");
    if(t==8) df = new SimpleDateFormat("HH");
    Calendar calendar = Calendar.getInstance();
    String time = df.format(calendar.getTime());
    try {      
        return time;
    } catch (Throwable e) {}
    return "";
}

void loadConfig() {
    String likeFriends = getString("DailyLike", "selectedFriends", "");
    if (!likeFriends.isEmpty()) {
        String[] friendsArray = likeFriends.split(",");
        for (int i = 0; i < friendsArray.length; i++) {
            if (!friendsArray[i].isEmpty()) selectedFriendsForLike.add(friendsArray[i]);
        }
    }
    
    String fireFriends = getString("KeepFire", "friends", "");
    if (!fireFriends.isEmpty()) {
        String[] friendsArray = fireFriends.split(",");
        for (int i = 0; i < friendsArray.length; i++) {
            if (!friendsArray[i].isEmpty()) selectedFriendsForFire.add(friendsArray[i]);
        }
    }
    
    ArrayList loadedFriendWords = loadWordsFromFile(friendFireWordsPath);
    if (!loadedFriendWords.isEmpty()) {
        friendFireWords = loadedFriendWords;
    } else {
        String savedFriendWords = getString("KeepFire", "fireWords", "");
        if (!savedFriendWords.isEmpty()) {
            String[] wordsArray = savedFriendWords.split(",");
            for (int i = 0; i < wordsArray.length; i++) {
                friendFireWords.add(wordsArray[i].trim());
            }
            saveWordsToFile(friendFireWordsPath, friendFireWords);
            putString("KeepFire", "fireWords", "");
        } else {
            friendFireWords.add("世上何来常青树 心中不负便胜朝朝暮暮 也许这份喜欢是一时兴起 可是我的梦里有你");
            saveWordsToFile(friendFireWordsPath, friendFireWords);
        }
    }
    
    String fireGroups = getString("GroupFire", "selectedGroups", "");
    if (!fireGroups.isEmpty()) {
        String[] groupsArray = fireGroups.split(",");
        for (int i = 0; i < groupsArray.length; i++) {
            if (!groupsArray[i].isEmpty()) selectedGroupsForFire.add(groupsArray[i]);
        }
    }
    
    ArrayList loadedGroupWords = loadWordsFromFile(groupFireWordsPath);
    if (!loadedGroupWords.isEmpty()) {
        groupFireWords = loadedGroupWords;
    } else {
        String savedGroupWords = getString("GroupFire", "fireWords", "");
        if (!savedGroupWords.isEmpty()) {
            String[] wordsArray = savedGroupWords.split(",");
            for (int i = 0; i < wordsArray.length; i++) {
                groupFireWords.add(wordsArray[i].trim());
            }
            saveWordsToFile(groupFireWordsPath, groupFireWords);
            putString("GroupFire", "fireWords", "");
        } else {
            groupFireWords.add("世上何来常青树 心中不负便胜朝朝暮暮 也许这份喜欢是一时兴起 可是我的梦里有你");
            saveWordsToFile(groupFireWordsPath, groupFireWords);
        }
    }
    
    initTimeConfig();
    
    String likeTimeFile = timeConfigPath + "/好友点赞时间.txt";
    String friendFireTimeFile = timeConfigPath + "/好友续火时间.txt";
    String groupFireTimeFile = timeConfigPath + "/群组续火时间.txt";
    
    String loadedLikeTime = loadTimeFromFile(likeTimeFile);
    if (loadedLikeTime != null) likeTime = loadedLikeTime;
    
    String loadedFriendFireTime = loadTimeFromFile(friendFireTimeFile);
    if (loadedFriendFireTime != null) friendFireTime = loadedFriendFireTime;
    
    String loadedGroupFireTime = loadTimeFromFile(groupFireTimeFile);
    if (loadedGroupFireTime != null) groupFireTime = loadedGroupFireTime;
    
    lastLikeDate = getString("DailyLike", "lastLikeDate", "");
    lastFriendFireDate = getString("KeepFire", "lastSendDate", "");
    lastGroupFireDate = getString("GroupFire", "lastSendDate", "");
    
    checkMissedTasks();
}

void saveLikeFriends() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < selectedFriendsForLike.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append((String)selectedFriendsForLike.get(i));
    }
    putString("DailyLike", "selectedFriends", sb.toString());
}

void saveFireFriends() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < selectedFriendsForFire.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append((String)selectedFriendsForFire.get(i));
    }
    putString("KeepFire", "friends", sb.toString());
}

void saveFireGroups() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < selectedGroupsForFire.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append((String)selectedGroupsForFire.get(i));
    }
    putString("GroupFire", "selectedGroups", sb.toString());
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

new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                String currentTime = TIME(2);
                String currentDate = TIME(5);
                
                if (!currentDate.equals(lastLikeDate) && currentTime.equals(likeTime)) {
                    executeLikeTask();
                    lastLikeDate = currentDate;
                    putString("DailyLike", "lastLikeDate", currentDate);
                    Toasts("已执行好友点赞");
                }
                
                if (!currentDate.equals(lastFriendFireDate) && currentTime.equals(friendFireTime)) {
                    executeFriendFireTask();
                    lastFriendFireDate = currentDate;
                    putString("KeepFire", "lastSendDate", currentDate);
                    Toasts("已续火" + selectedFriendsForFire.size() + "位好友");
                }
                
                if (!currentDate.equals(lastGroupFireDate) && currentTime.equals(groupFireTime)) {
                    executeGroupFireTask();
                    lastGroupFireDate = currentDate;
                    putString("GroupFire", "lastSendDate", currentDate);
                    Toasts("已续火" + selectedGroupsForFire.size() + "个群组");
                }
                
                Thread.sleep(60000);
            }catch(Exception e){
            }
        }
    }
}).start();

addItem("立即点赞好友","immediateLike");
addItem("立即续火好友","immediateFriendFire");
addItem("立即续火群组","immediateGroupFire");
addItem("配置点赞好友","configLikeFriends");
addItem("配置续火好友","configFireFriends");
addItem("配置续火群组","configFireGroups");
addItem("配置好友续火词","configFriendFireWords");
addItem("配置群组续火词","configGroupFireWords");
addItem("配置好友点赞时间","configLikeTime");
addItem("配置好友续火时间","configFriendFireTime");
addItem("配置群组续火时间","configGroupFireTime");

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
    
    ArrayList friendList = getFriendList();
    if (friendList == null || friendList.isEmpty()) {
        Toasts("未添加任何好友");
        return;
    }
    
    final ArrayList displayList = new ArrayList();
    final ArrayList uinList = new ArrayList();
    for (int i = 0; i < friendList.size(); i++) {
        Object friend = friendList.get(i);
        String remark = "";
        String name = "";
        String uin = "";
        try {
            Class friendClass = friend.getClass();
            java.lang.reflect.Field remarkField = friendClass.getDeclaredField("remark");
            remarkField.setAccessible(true);
            java.lang.reflect.Field nameField = friendClass.getDeclaredField("name");
            nameField.setAccessible(true);
            java.lang.reflect.Field uinField = friendClass.getDeclaredField("uin");
            uinField.setAccessible(true);
            
            remark = (String)remarkField.get(friend);
            name = (String)nameField.get(friend);
            uin = (String)uinField.get(friend);
        } catch (Exception e) {
        }
        
        String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
        displayList.add(displayName);
        uinList.add(uin);
    }
    
    final ArrayList filteredDisplayList = new ArrayList(displayList);
    final ArrayList filteredUinList = new ArrayList(uinList);
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("选择点赞好友");
            dialogBuilder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 10, 20, 10);
            
            final EditText searchEditText = new EditText(activity);
            searchEditText.setHint("搜索好友QQ号、好友名、备注");
            searchEditText.setTextColor(Color.BLACK);
            searchEditText.setHintTextColor(Color.GRAY);
            layout.addView(searchEditText);
            
            Button selectAllButton = new Button(activity);
            selectAllButton.setText("全选");
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
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < filteredUinList.size(); i++) {
                String uin = (String)filteredUinList.get(i);
                listView.setItemChecked(i, selectedFriendsForLike.contains(uin));
            }
            
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
                        listView.setItemChecked(i, selectedFriendsForLike.contains(uin));
                    }
                }
            });
            
            selectAllButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, true);
                    }
                }
            });
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList tempSelected = new ArrayList();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            tempSelected.add(filteredUinList.get(i));
                        }
                    }
                    
                    for (int i = 0; i < uinList.size(); i++) {
                        String uin = (String)uinList.get(i);
                        if (filteredUinList.contains(uin)) {
                            if (tempSelected.contains(uin)) {
                                if (!selectedFriendsForLike.contains(uin)) {
                                    selectedFriendsForLike.add(uin);
                                }
                            } else {
                                selectedFriendsForLike.remove(uin);
                            }
                        }
                    }
                    
                    saveLikeFriends();
                    Toasts("已选择" + selectedFriendsForLike.size() + "位点赞好友");
                }
            });
            
            dialogBuilder.setNegativeButton("取消", null);
            
            dialogBuilder.show();
        }
    });
}

public void configFireFriends(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    ArrayList friendList = getFriendList();
    if (friendList == null || friendList.isEmpty()) {
        Toasts("未添加任何好友");
        return;
    }
    
    final ArrayList displayList = new ArrayList();
    final ArrayList uinList = new ArrayList();
    for (int i = 0; i < friendList.size(); i++) {
        Object friend = friendList.get(i);
        String remark = "";
        String name = "";
        String uin = "";
        try {
            Class friendClass = friend.getClass();
            java.lang.reflect.Field remarkField = friendClass.getDeclaredField("remark");
            remarkField.setAccessible(true);
            java.lang.reflect.Field nameField = friendClass.getDeclaredField("name");
            nameField.setAccessible(true);
            java.lang.reflect.Field uinField = friendClass.getDeclaredField("uin");
            uinField.setAccessible(true);
            
            remark = (String)remarkField.get(friend);
            name = (String)nameField.get(friend);
            uin = (String)uinField.get(friend);
        } catch (Exception e) {
        }
        
        String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
        displayList.add(displayName);
        uinList.add(uin);
    }
    
    final ArrayList filteredDisplayList = new ArrayList(displayList);
    final ArrayList filteredUinList = new ArrayList(uinList);
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("选择续火好友");
            dialogBuilder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 10, 20, 10);
            
            final EditText searchEditText = new EditText(activity);
            searchEditText.setHint("搜索好友QQ号、好友名、备注");
            searchEditText.setTextColor(Color.BLACK);
            searchEditText.setHintTextColor(Color.GRAY);
            layout.addView(searchEditText);
            
            Button selectAllButton = new Button(activity);
            selectAllButton.setText("全选");
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
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < filteredUinList.size(); i++) {
                String uin = (String)filteredUinList.get(i);
                listView.setItemChecked(i, selectedFriendsForFire.contains(uin));
            }
            
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
                        listView.setItemChecked(i, selectedFriendsForFire.contains(uin));
                    }
                }
            });
            
            selectAllButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, true);
                    }
                }
            });
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList tempSelected = new ArrayList();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            tempSelected.add(filteredUinList.get(i));
                        }
                    }
                    
                    for (int i = 0; i < uinList.size(); i++) {
                        String uin = (String)uinList.get(i);
                        if (filteredUinList.contains(uin)) {
                            if (tempSelected.contains(uin)) {
                                if (!selectedFriendsForFire.contains(uin)) {
                                    selectedFriendsForFire.add(uin);
                                }
                            } else {
                                selectedFriendsForFire.remove(uin);
                            }
                        }
                    }
                    
                    saveFireFriends();
                    Toasts("已选择" + selectedFriendsForFire.size() + "位续火好友");
                }
            });
            
            dialogBuilder.setNegativeButton("取消", null);
            
            dialogBuilder.show();
        }
    });
}

sendLike("2133115301",20);

public void configFireGroups(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
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
        String groupUin = "";
        try {
            Class groupClass = group.getClass();
            java.lang.reflect.Field nameField = groupClass.getDeclaredField("GroupName");
            nameField.setAccessible(true);
            java.lang.reflect.Field uinField = groupClass.getDeclaredField("GroupUin");
            uinField.setAccessible(true);
            
            groupName = (String)nameField.get(group);
            groupUin = (String)uinField.get(group);
        } catch (Exception e) {
        }
        
        String displayName = groupName + " (" + groupUin + ")";
        displayList.add(displayName);
        uinList.add(groupUin);
    }
    
    final ArrayList filteredDisplayList = new ArrayList(displayList);
    final ArrayList filteredUinList = new ArrayList(uinList);
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int uiMode = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("选择续火群组");
            dialogBuilder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 10, 20, 10);
            
            final EditText searchEditText = new EditText(activity);
            searchEditText.setHint("搜索群号、群名");
            searchEditText.setTextColor(Color.BLACK);
            searchEditText.setHintTextColor(Color.GRAY);
            layout.addView(searchEditText);
            
            Button selectAllButton = new Button(activity);
            selectAllButton.setText("全选");
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
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < filteredUinList.size(); i++) {
                String uin = (String)filteredUinList.get(i);
                listView.setItemChecked(i, selectedGroupsForFire.contains(uin));
            }
            
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
                        listView.setItemChecked(i, selectedGroupsForFire.contains(uin));
                    }
                }
            });
            
            selectAllButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, true);
                    }
                }
            });
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList tempSelected = new ArrayList();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            tempSelected.add(filteredUinList.get(i));
                        }
                    }
                    
                    for (int i = 0; i < uinList.size(); i++) {
                        String uin = (String)uinList.get(i);
                        if (filteredUinList.contains(uin)) {
                            if (tempSelected.contains(uin)) {
                                if (!selectedGroupsForFire.contains(uin)) {
                                    selectedGroupsForFire.add(uin);
                                }
                            } else {
                                selectedGroupsForFire.remove(uin);
                            }
                        }
                    }
                    
                    saveFireGroups();
                    Toasts("已选择" + selectedGroupsForFire.size() + "个续火群组");
                }
            });
            
            dialogBuilder.setNegativeButton("取消", null);
            
            dialogBuilder.show();
        }
    });
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
                titleView.setText("配置好友续火词，多个请另起一行");
                titleView.setTextColor(Color.BLACK);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                titleView.setTypeface(null, android.graphics.Typeface.BOLD);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 10, 0, 20);
                
                final EditText wordsEditText = new EditText(activity);
                wordsEditText.setText(wordsBuilder.toString());
                wordsEditText.setHint("输入好友续火词，每行一个");
                wordsEditText.setTextColor(Color.BLACK);
                wordsEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                wordsEditText.setHintTextColor(Color.parseColor("#888888"));
                wordsEditText.setMinLines(5);
                wordsEditText.setGravity(Gravity.TOP);
                
                TextView hintView = new TextView(activity);
                hintView.setText("注意：输入多个续火词时，每行一个");
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
                            Toasts("续火词不能为空");
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
                            Toasts("未添加有效的续火词");
                            return;
                        }
                        
                        saveWordsToFile(friendFireWordsPath, friendFireWords);
                        Toasts("已保存 " + friendFireWords.size() + " 个好友续火词");
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
                titleView.setText("配置群组续火词，多个请另起一行");
                titleView.setTextColor(Color.BLACK);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                titleView.setTypeface(null, android.graphics.Typeface.BOLD);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 10, 0, 20);
                
                final EditText wordsEditText = new EditText(activity);
                wordsEditText.setText(wordsBuilder.toString());
                wordsEditText.setHint("输入群组续火词，每行一个");
                wordsEditText.setTextColor(Color.BLACK);
                wordsEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                wordsEditText.setHintTextColor(Color.parseColor("#888888"));
                wordsEditText.setMinLines(5);
                wordsEditText.setGravity(Gravity.TOP);
                
                TextView hintView = new TextView(activity);
                hintView.setText("注意：输入多个续火词时，每行一个");
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
                            Toasts("续火词不能为空");
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
                            Toasts("未添加有效的续火词");
                            return;
                        }
                        
                        saveWordsToFile(groupFireWordsPath, groupFireWords);
                        Toasts("已保存 " + groupFireWords.size() + " 个群组续火词");
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
                    if (isValidTimeFormat(timeText)) {
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
                    if (isValidTimeFormat(timeText)) {
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
                    if (isValidTimeFormat(timeText)) {
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

boolean isValidTimeFormat(String timeStr) {
    try {
        String[] timeParts = timeStr.split(":");
        if (timeParts.length != 2) return false;
        
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        
        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    } catch (Exception e) {
        return false;
    }
}