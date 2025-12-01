
// 海枫

// 睡觉可能是唯一轻松的事

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import android.text.Editable;
import android.text.TextWatcher;
import java.lang.reflect.Field;

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

ArrayList loadWordsFromFile(String filePath) {
    ArrayList wordsList = new ArrayList();
    File file = new File(filePath);
    if (file.exists()) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    wordsList.add(line);
                }
            }
        } catch (Exception e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {}
            }
        }
    }
    return wordsList;
}

void saveWordsToFile(String filePath, ArrayList wordsList) {
    FileWriter writer = null;
    try {
        File dir = new File(appPath + "/续火词");
        if (!dir.exists()) dir.mkdirs();
        writer = new FileWriter(filePath);
        for (int i = 0; i < wordsList.size(); i++) {
            writer.write((String)wordsList.get(i) + "\n");
        }
    } catch (Exception e) {
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }
}

String loadTimeFromFile(String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String time = reader.readLine();
            if (time != null && isValidTimeFormat(time.trim())) {
                return time.trim();
            }
        } catch (Exception e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {}
            }
        }
    }
    return null;
}

void saveTimeToFile(String filePath, String time) {
    FileWriter writer = null;
    try {
        File dir = new File(timeConfigPath);
        if (!dir.exists()) dir.mkdirs();
        writer = new FileWriter(filePath);
        writer.write(time);
    } catch (Exception e) {
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }
}

void initTimeConfig() {
    try {
        File dir = new File(timeConfigPath);
        if (!dir.exists()) dir.mkdirs();
        
        String likeTimeFile = timeConfigPath + "/好友点赞时间.txt";
        String friendFireTimeFile = timeConfigPath + "/好友续火时间.txt";
        String groupFireTimeFile = timeConfigPath + "/群组续火时间.txt";
        
        if (!new File(likeTimeFile).exists()) saveTimeToFile(likeTimeFile, "00:00");
        if (!new File(friendFireTimeFile).exists()) saveTimeToFile(friendFireTimeFile, "00:00");
        if (!new File(groupFireTimeFile).exists()) saveTimeToFile(groupFireTimeFile, "00:00");
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
                    Thread.sleep(100); 
                }catch(Exception e){}
            }
        }
    }).start();
}

void executeFriendFireTask(){
    new Thread(new Runnable(){
        public void run(){
            if(friendFireWords.isEmpty()) return;
            for(int i = 0; i < selectedFriendsForFire.size(); i++){
                String friendUin = (String)selectedFriendsForFire.get(i);
                try{
                    int randomIndex = (int)(Math.random() * friendFireWords.size());
                    String fireWord = (String)friendFireWords.get(randomIndex);
                    sendMsg("", friendUin, fireWord);
                    Thread.sleep(500);
                }catch(Exception e){}
            }
        }
    }).start();
}

void executeGroupFireTask(){
    new Thread(new Runnable(){
        public void run(){
            if(groupFireWords.isEmpty()) return;
            for(int i = 0; i < selectedGroupsForFire.size(); i++){
                String groupUin = (String)selectedGroupsForFire.get(i);
                try{
                    int randomIndex = (int)(Math.random() * groupFireWords.size());
                    String fireWord = (String)groupFireWords.get(randomIndex);
                    sendMsg(groupUin, "", fireWord);
                    Thread.sleep(500);
                }catch(Exception e){}
            }
        }
    }).start();
}

void checkMissedTasks() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String currentDate = sdf.format(calendar.getTime());
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    int currentMinute = calendar.get(Calendar.MINUTE);
    int currentTimeInMinutes = currentHour * 60 + currentMinute;
    
    int[] likeTimeArray = parseTime(likeTime);
    int likeTimeInMinutes = likeTimeArray[0] * 60 + likeTimeArray[1];
    
    int[] friendFireTimeArray = parseTime(friendFireTime);
    int friendFireTimeInMinutes = friendFireTimeArray[0] * 60 + friendFireTimeArray[1];
    
    int[] groupFireTimeArray = parseTime(groupFireTime);
    int groupFireTimeInMinutes = groupFireTimeArray[0] * 60 + groupFireTimeArray[1];
    
    if (lastLikeDate.equals("")) {
        lastLikeDate = currentDate;
        putString("DailyLike", "lastLikeDate", currentDate);
    } else if (!currentDate.equals(lastLikeDate) && currentTimeInMinutes >= likeTimeInMinutes) {
        executeLikeTask();
        lastLikeDate = currentDate;
        putString("DailyLike", "lastLikeDate", currentDate);
        toast("检测到错过点赞任务，已立即执行");
    }
    
    if (lastFriendFireDate.equals("")) {
        lastFriendFireDate = currentDate;
        putString("KeepFire", "lastSendDate", currentDate);
    } else if (!currentDate.equals(lastFriendFireDate) && currentTimeInMinutes >= friendFireTimeInMinutes) {
        executeFriendFireTask();
        lastFriendFireDate = currentDate;
        putString("KeepFire", "lastSendDate", currentDate);
        toast("检测到错过好友续火任务，已立即执行");
    }
    
    if (lastGroupFireDate.equals("")) {
        lastGroupFireDate = currentDate;
        putString("GroupFire", "lastSendDate", currentDate);
    } else if (!currentDate.equals(lastGroupFireDate) && currentTimeInMinutes >= groupFireTimeInMinutes) {
        executeGroupFireTask();
        lastGroupFireDate = currentDate;
        putString("GroupFire", "lastSendDate", currentDate);
        toast("检测到错过群续火任务，已立即执行");
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
    } catch (Exception e) {}
    return timeArray;
}

public String TIME(int t) {  
    SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH:mm:ss");
    if(t==2) df = new SimpleDateFormat("HH:mm");
    if(t==5) df = new SimpleDateFormat("yyyyMMdd");
    return df.format(new Date());
}

void loadConfig() {
    String likeFriends = getString("DailyLike", "selectedFriends", "");
    if (likeFriends != null && !likeFriends.isEmpty()) {
        String[] friendsArray = likeFriends.split(",");
        for (int i=0; i<friendsArray.length; i++) {
            String f = friendsArray[i];
            if (!f.isEmpty()) selectedFriendsForLike.add(f);
        }
    }
    
    String fireFriends = getString("KeepFire", "friends", "");
    if (fireFriends != null && !fireFriends.isEmpty()) {
        String[] friendsArray = fireFriends.split(",");
        for (int i=0; i<friendsArray.length; i++) {
            String f = friendsArray[i];
            if (!f.isEmpty()) selectedFriendsForFire.add(f);
        }
    }
    
    ArrayList loadedFriendWords = loadWordsFromFile(friendFireWordsPath);
    if (!loadedFriendWords.isEmpty()) {
        friendFireWords = loadedFriendWords;
    } else {
        String savedFriendWords = getString("KeepFire", "fireWords", "");
        if (savedFriendWords != null && !savedFriendWords.isEmpty()) {
            String[] wordsArray = savedFriendWords.split(",");
            for (int i=0; i<wordsArray.length; i++) {
                String w = wordsArray[i];
                friendFireWords.add(w.trim());
            }
            saveWordsToFile(friendFireWordsPath, friendFireWords);
            putString("KeepFire", "fireWords", "");
        } else {
            friendFireWords.add("世上何来常青树 心中不负便胜朝朝暮暮 也许这份喜欢是一时兴起 可是我的梦里有你");
            saveWordsToFile(friendFireWordsPath, friendFireWords);
        }
    }
    
    String fireGroups = getString("GroupFire", "selectedGroups", "");
    if (fireGroups != null && !fireGroups.isEmpty()) {
        String[] groupsArray = fireGroups.split(",");
        for (int i=0; i<groupsArray.length; i++) {
            String g = groupsArray[i];
            if (!g.isEmpty()) selectedGroupsForFire.add(g);
        }
    }
    
    ArrayList loadedGroupWords = loadWordsFromFile(groupFireWordsPath);
    if (!loadedGroupWords.isEmpty()) {
        groupFireWords = loadedGroupWords;
    } else {
        String savedGroupWords = getString("GroupFire", "fireWords", "");
        if (savedGroupWords != null && !savedGroupWords.isEmpty()) {
            String[] wordsArray = savedGroupWords.split(",");
            for (int i=0; i<wordsArray.length; i++) {
                String w = wordsArray[i];
                groupFireWords.add(w.trim());
            }
            saveWordsToFile(groupFireWordsPath, groupFireWords);
            putString("GroupFire", "fireWords", "");
        } else {
            groupFireWords.add("世上何来常青树 心中不负便胜朝朝暮暮 也许这份喜欢是一时兴起 可是我的梦里有你");
            saveWordsToFile(groupFireWordsPath, groupFireWords);
        }
    }
    
    initTimeConfig();
    
    String loadedLikeTime = loadTimeFromFile(timeConfigPath + "/好友点赞时间.txt");
    if (loadedLikeTime != null) likeTime = loadedLikeTime;
    
    String loadedFriendFireTime = loadTimeFromFile(timeConfigPath + "/好友续火时间.txt");
    if (loadedFriendFireTime != null) friendFireTime = loadedFriendFireTime;
    
    String loadedGroupFireTime = loadTimeFromFile(timeConfigPath + "/群组续火时间.txt");
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
    saveTimeToFile(timeConfigPath + "/好友点赞时间.txt", likeTime);
    saveTimeToFile(timeConfigPath + "/好友续火时间.txt", friendFireTime);
    saveTimeToFile(timeConfigPath + "/群组续火时间.txt", groupFireTime);
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
                    toast("已执行好友点赞");
                }
                
                if (!currentDate.equals(lastFriendFireDate) && currentTime.equals(friendFireTime)) {
                    executeFriendFireTask();
                    lastFriendFireDate = currentDate;
                    putString("KeepFire", "lastSendDate", currentDate);
                    toast("已续火" + selectedFriendsForFire.size() + "位好友");
                }
                
                if (!currentDate.equals(lastGroupFireDate) && currentTime.equals(groupFireTime)) {
                    executeGroupFireTask();
                    lastGroupFireDate = currentDate;
                    putString("GroupFire", "lastSendDate", currentDate);
                    toast("已续火" + selectedGroupsForFire.size() + "个群组");
                }
                
                Thread.sleep(30000);
            }catch(Exception e){}
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
        toast("冷却中，请稍后再试");
        return;
    }
    lastLikeClickTime = currentTime;
    if (selectedFriendsForLike.isEmpty()) {
        toast("请先配置要点赞的好友");
        return;
    }
    executeLikeTask();
    toast("正在为" + selectedFriendsForLike.size() + "位好友点赞");
}

public void immediateFriendFire(String groupUin, String userUin, int chatType){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastFriendFireClickTime < 60000){
        toast("冷却中，请稍后再试");
        return;
    }
    lastFriendFireClickTime = currentTime;
    if (selectedFriendsForFire.isEmpty()) {
        toast("请先配置要续火的好友");
        return;
    }
    executeFriendFireTask();
    toast("已立即续火" + selectedFriendsForFire.size() + "位好友");
}

public void immediateGroupFire(String groupUin, String userUin, int chatType){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastGroupFireClickTime < 60000){
        toast("冷却中，请稍后再试");
        return;
    }
    lastGroupFireClickTime = currentTime;
    if (selectedGroupsForFire.isEmpty()) {
        toast("请先配置要续火的群组");
        return;
    }
    executeGroupFireTask();
    toast("已立即续火" + selectedGroupsForFire.size() + "个群组");
}

String getFieldValue(Object obj, String fieldName) {
    try {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(obj);
        return value != null ? value.toString() : "";
    } catch (Exception e) {
        return "";
    }
}

public void configLikeFriends(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    final ArrayList friendList = getFriendList();
    if (friendList == null || friendList.isEmpty()) {
        toast("未添加任何好友");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final ArrayList displayList = new ArrayList();
            final ArrayList uinList = new ArrayList();
            
            for (int i=0; i<friendList.size(); i++) {
                Object friend = friendList.get(i);
                String remark = getFieldValue(friend, "remark");
                String name = getFieldValue(friend, "name");
                String uin = getFieldValue(friend, "uin");
                
                String showName = remark.isEmpty() ? name : remark;
                displayList.add(showName + " (" + uin + ")");
                uinList.add(uin);
            }
            
            final ArrayList filteredDisplayList = new ArrayList(displayList);
            final ArrayList filteredUinList = new ArrayList(uinList);
            
            int uiMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            int theme = Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("选择点赞好友");
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 10, 20, 10);
            
            final EditText searchEditText = new EditText(activity);
            searchEditText.setHint("搜索...");
            searchEditText.setTextColor(isDarkMode() ? Color.WHITE : Color.BLACK);
            layout.addView(searchEditText);
            
            Button selectAllButton = new Button(activity);
            selectAllButton.setText("全选");
            layout.addView(selectAllButton);
            
            final ListView listView = new ListView(activity);
            layout.addView(listView);
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < filteredUinList.size(); i++) {
                listView.setItemChecked(i, selectedFriendsForLike.contains(filteredUinList.get(i)));
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
                            if (((String)displayList.get(i)).toLowerCase().contains(searchText)) {
                                filteredDisplayList.add(displayList.get(i));
                                filteredUinList.add(uinList.get(i));
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        listView.setItemChecked(i, selectedFriendsForLike.contains(filteredUinList.get(i)));
                    }
                }
            });
            
            selectAllButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) listView.setItemChecked(i, true);
                }
            });
            
            dialogBuilder.setView(layout);
            dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList tempSelected = new ArrayList();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        if (listView.isItemChecked(i)) tempSelected.add(filteredUinList.get(i));
                    }
                    for (int i=0; i < uinList.size(); i++) {
                        String uin = (String)uinList.get(i);
                        if (filteredUinList.contains(uin)) {
                            if (tempSelected.contains(uin)) {
                                if (!selectedFriendsForLike.contains(uin)) selectedFriendsForLike.add(uin);
                            } else {
                                selectedFriendsForLike.remove(uin);
                            }
                        }
                    }
                    saveLikeFriends();
                    toast("操作完成");
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
    
    final ArrayList friendList = getFriendList();
    if (friendList == null || friendList.isEmpty()) {
        toast("未添加任何好友");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final ArrayList displayList = new ArrayList();
            final ArrayList uinList = new ArrayList();
            
            for (int i=0; i<friendList.size(); i++) {
                Object friend = friendList.get(i);
                String remark = getFieldValue(friend, "remark");
                String name = getFieldValue(friend, "name");
                String uin = getFieldValue(friend, "uin");
                
                String showName = remark.isEmpty() ? name : remark;
                displayList.add(showName + " (" + uin + ")");
                uinList.add(uin);
            }
            
            final ArrayList filteredDisplayList = new ArrayList(displayList);
            final ArrayList filteredUinList = new ArrayList(uinList);
            
            int uiMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            int theme = Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("选择续火好友");
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 10, 20, 10);
            
            final EditText searchEditText = new EditText(activity);
            searchEditText.setHint("搜索...");
            searchEditText.setTextColor(isDarkMode() ? Color.WHITE : Color.BLACK);
            layout.addView(searchEditText);
            
            Button selectAllButton = new Button(activity);
            selectAllButton.setText("全选");
            layout.addView(selectAllButton);
            
            final ListView listView = new ListView(activity);
            layout.addView(listView);
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < filteredUinList.size(); i++) {
                listView.setItemChecked(i, selectedFriendsForFire.contains(filteredUinList.get(i)));
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
                            if (((String)displayList.get(i)).toLowerCase().contains(searchText)) {
                                filteredDisplayList.add(displayList.get(i));
                                filteredUinList.add(uinList.get(i));
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        listView.setItemChecked(i, selectedFriendsForFire.contains(filteredUinList.get(i)));
                    }
                }
            });
            
            selectAllButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) listView.setItemChecked(i, true);
                }
            });
            
            dialogBuilder.setView(layout);
            dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList tempSelected = new ArrayList();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        if (listView.isItemChecked(i)) tempSelected.add(filteredUinList.get(i));
                    }
                    for (int i=0; i < uinList.size(); i++) {
                        String uin = (String)uinList.get(i);
                        if (filteredUinList.contains(uin)) {
                            if (tempSelected.contains(uin)) {
                                if (!selectedFriendsForFire.contains(uin)) selectedFriendsForFire.add(uin);
                            } else {
                                selectedFriendsForFire.remove(uin);
                            }
                        }
                    }
                    saveFireFriends();
                    toast("操作完成");
                }
            });
            dialogBuilder.setNegativeButton("取消", null);
            dialogBuilder.show();
        }
    });
}

public void configFireGroups(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    final ArrayList groupList = getGroupList();
    if (groupList == null || groupList.isEmpty()) {
        toast("未加入任何群组");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final ArrayList displayList = new ArrayList();
            final ArrayList uinList = new ArrayList();
            
            for (int i=0; i<groupList.size(); i++) {
                Object group = groupList.get(i);
                String name = getFieldValue(group, "GroupName");
                String uin = getFieldValue(group, "GroupUin");
                displayList.add(name + " (" + uin + ")");
                uinList.add(uin);
            }
            
            final ArrayList filteredDisplayList = new ArrayList(displayList);
            final ArrayList filteredUinList = new ArrayList(uinList);
            
            int uiMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            int theme = Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            dialogBuilder.setTitle("选择续火群组");
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 10, 20, 10);
            
            final EditText searchEditText = new EditText(activity);
            searchEditText.setHint("搜索...");
            searchEditText.setTextColor(isDarkMode() ? Color.WHITE : Color.BLACK);
            layout.addView(searchEditText);
            
            Button selectAllButton = new Button(activity);
            selectAllButton.setText("全选");
            layout.addView(selectAllButton);
            
            final ListView listView = new ListView(activity);
            layout.addView(listView);
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < filteredUinList.size(); i++) {
                listView.setItemChecked(i, selectedGroupsForFire.contains(filteredUinList.get(i)));
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
                            if (((String)displayList.get(i)).toLowerCase().contains(searchText)) {
                                filteredDisplayList.add(displayList.get(i));
                                filteredUinList.add(uinList.get(i));
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        listView.setItemChecked(i, selectedGroupsForFire.contains(filteredUinList.get(i)));
                    }
                }
            });
            
            selectAllButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) listView.setItemChecked(i, true);
                }
            });
            
            dialogBuilder.setView(layout);
            dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList tempSelected = new ArrayList();
                    for (int i = 0; i < filteredUinList.size(); i++) {
                        if (listView.isItemChecked(i)) tempSelected.add(filteredUinList.get(i));
                    }
                    for (int i=0; i<uinList.size(); i++) {
                        String uin = (String)uinList.get(i);
                        if (filteredUinList.contains(uin)) {
                            if (tempSelected.contains(uin)) {
                                if (!selectedGroupsForFire.contains(uin)) selectedGroupsForFire.add(uin);
                            } else {
                                selectedGroupsForFire.remove(uin);
                            }
                        }
                    }
                    saveFireGroups();
                    toast("已选择" + selectedGroupsForFire.size() + "个群组");
                }
            });
            dialogBuilder.setNegativeButton("取消", null);
            dialogBuilder.show();
        }
    });
}

public void configFriendFireWords(String groupUin, String userUin, int chatType){
    showWordConfigDialog("好友续火词", friendFireWords, friendFireWordsPath);
}

public void configGroupFireWords(String groupUin, String userUin, int chatType){
    showWordConfigDialog("群组续火词", groupFireWords, groupFireWordsPath);
}

void showWordConfigDialog(String title, final ArrayList wordsList, final String savePath) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    final String dialogTitle = title;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            StringBuilder wordsBuilder = new StringBuilder();
            for (int i = 0; i < wordsList.size(); i++) {
                if (wordsBuilder.length() > 0) wordsBuilder.append("\n");
                wordsBuilder.append((String)wordsList.get(i));
            }
            
            final EditText wordsEditText = new EditText(activity);
            wordsEditText.setText(wordsBuilder.toString());
            wordsEditText.setHint("每行一个...");
            wordsEditText.setTextColor(isDarkMode() ? Color.WHITE : Color.BLACK);
            wordsEditText.setMinLines(5);
            wordsEditText.setGravity(Gravity.TOP);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 20, 30, 20);
            layout.addView(wordsEditText);
            
            int uiMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            int theme = Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle(dialogTitle);
            builder.setView(layout);
            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String wordsText = wordsEditText.getText().toString().trim();
                    wordsList.clear();
                    if (!wordsText.isEmpty()) {
                        String[] wordsArray = wordsText.split("\n");
                        for (int i=0; i<wordsArray.length; i++) {
                            String word = wordsArray[i];
                            if (!word.trim().isEmpty()) wordsList.add(word.trim());
                        }
                    }
                    saveWordsToFile(savePath, wordsList);
                    toast("保存成功");
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void configLikeTime(String groupUin, String userUin, int chatType) {
    showTimeConfigDialog("设置点赞时间 (HH:mm)", likeTime, 1);
}

public void configFriendFireTime(String groupUin, String userUin, int chatType) {
    showTimeConfigDialog("设置好友续火时间 (HH:mm)", friendFireTime, 2);
}

public void configGroupFireTime(String groupUin, String userUin, int chatType) {
    showTimeConfigDialog("设置群组续火时间 (HH:mm)", groupFireTime, 3);
}

void showTimeConfigDialog(String title, String currentTime, final int type) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    final String defaultTime = currentTime;
    final String dialogTitle = title;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int uiMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            int theme = Configuration.UI_MODE_NIGHT_YES == uiMode ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final EditText timeEditText = new EditText(activity);
            timeEditText.setText(defaultTime);
            timeEditText.setTextColor(isDarkMode() ? Color.WHITE : Color.BLACK);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setPadding(30, 30, 30, 30);
            layout.addView(timeEditText);
            
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle(dialogTitle);
            builder.setView(layout);
            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String t = timeEditText.getText().toString().trim();
                    if (isValidTimeFormat(t)) {
                        if (type == 1) {
                            likeTime = t;
                        } else if (type == 2) {
                            friendFireTime = t;
                        } else if (type == 3) {
                            groupFireTime = t;
                        }
                        saveTimeConfig();
                        toast("设置成功: " + t);
                    } else {
                        toast("时间格式错误");
                    }
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

boolean isValidTimeFormat(String timeStr) {
    try {
        String[] parts = timeStr.split(":");
        if (parts.length != 2) return false;
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        return h >= 0 && h <= 23 && m >= 0 && m <= 59;
    } catch (Exception e) {
        return false;
    }
}