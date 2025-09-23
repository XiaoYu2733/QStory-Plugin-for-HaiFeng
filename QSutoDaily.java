
// 作 海枫(៸៸᳐⦁⩊⦁៸៸᳐ )੭ 

// 你会遇到比我更好的人 因为你从未觉得我好

// QStory精选脚本系列 请勿二改上传 会拉黑上传权限(៸៸᳐⦁⩊⦁៸៸᳐ )੭ 

// 部分接口 卑微萌新



























































































// 所有代码不建议动 容易坏哦qwq
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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

ArrayList likeFriends = new ArrayList();
String lastLikeDate = "";
String likeTime = "00:00";

ArrayList fireFriends = new ArrayList();
ArrayList friendFireWords = new ArrayList();
String lastFriendFireDate = "";
String friendFireTime = "08:00";

ArrayList fireGroups = new ArrayList();
ArrayList groupFireWords = new ArrayList();
String lastGroupFireDate = "";
String groupFireTime = "08:00";

long lastLikeClickTime = 0;
long lastFriendFireClickTime = 0;
long lastGroupFireClickTime = 0;

String friendFireWordsPath = appPath + "/续火词/好友续火词.txt";
String groupFireWordsPath = appPath + "/续火词/群组续火词.txt";

ArrayList readWordsFromFile(String path) {
    ArrayList words = new ArrayList();
    try {
        File file = new File(path);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    words.add(line);
                }
            }
            reader.close();
        }
    } catch (Exception e) {
        toast("读取文件失败: " + e.getMessage());
    }
    return words;
}

void writeWordsToFile(String path, ArrayList words) {
    try {
        File dir = new File(appPath + "/续火词");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileWriter writer = new FileWriter(path);
        for (int i = 0; i < words.size(); i++) {
            writer.write((String)words.get(i) + "\n");
        }
        writer.close();
    } catch (Exception e) {
        toast("写入文件失败: " + e.getMessage());
    }
}

void executeSendLikes(){
    new Thread(new Runnable(){
    public void run(){
        for(int i=0; i < likeFriends.size(); i++){
            String friend = (String)likeFriends.get(i);
            try{
                sendLike(friend, 20);
                Thread.sleep(3000);
            }catch(Exception e){
                toast(friend + "点赞失败:" + e.getMessage());
            }
        }
    }
}).start();
}

void sendToAllFriends(){
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < fireFriends.size(); i++){
                String friend = (String)fireFriends.get(i);
                try{
                    int index = (int)(Math.random() * friendFireWords.size());
                    String word = (String)friendFireWords.get(index);
                    sendMsg("", friend, word);
                    Thread.sleep(5000);
                }catch(Exception e){
                    toast(friend + "续火失败:" + e.getMessage());
                }
            }
        }
    }).start();
}

void sendToAllGroups(){
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < fireGroups.size(); i++){
                String group = (String)fireGroups.get(i);
                try{
                    int index = (int)(Math.random() * groupFireWords.size());
                    String word = (String)groupFireWords.get(index);
                    sendMsg(group, "", word);
                    Thread.sleep(5000);
                }catch(Exception e){
                    toast(group + "续火失败:" + e.getMessage());
                }
            }
        }
    }).start();
}

void checkMissedTasks() {
    Calendar now = Calendar.getInstance();
    String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);
    int currentHour = now.get(Calendar.HOUR_OF_DAY);
    int currentMinute = now.get(Calendar.MINUTE);
    
    int[] likeTimeParts = parseTime(likeTime);
    int likeHour = likeTimeParts[0];
    int likeMinute = likeTimeParts[1];
    
    int[] friendFireTimeParts = parseTime(friendFireTime);
    int friendFireHour = friendFireTimeParts[0];
    int friendFireMinute = friendFireTimeParts[1];
    
    int[] groupFireTimeParts = parseTime(groupFireTime);
    int groupFireHour = groupFireTimeParts[0];
    int groupFireMinute = groupFireTimeParts[1];
    
    if (!today.equals(lastLikeDate) && isTimePassed(currentHour, currentMinute, likeHour, likeMinute)) {
        executeSendLikes();
        lastLikeDate = today;
        putString("DailyLike", "lastLikeDate", today);
        toast("检测到错过点赞任务，已立即执行");
    }
    
    if (!today.equals(lastFriendFireDate) && isTimePassed(currentHour, currentMinute, friendFireHour, friendFireMinute)) {
        sendToAllFriends();
        lastFriendFireDate = today;
        putString("KeepFire", "lastSendDate", today);
        toast("检测到错过好友续火任务，已立即执行");
    }
    
    if (!today.equals(lastGroupFireDate) && isTimePassed(currentHour, currentMinute, groupFireHour, groupFireMinute)) {
        sendToAllGroups();
        lastGroupFireDate = today;
        putString("GroupFire", "lastSendDate", today);
        toast("检测到错过群组续火任务，已立即执行");
    }
}

boolean isTimePassed(int currentHour, int currentMinute, int targetHour, int targetMinute) {
    if (currentHour > targetHour) return true;
    if (currentHour == targetHour && currentMinute >= targetMinute) return true;
    return false;
}

int[] parseTime(String timeStr) {
    int[] result = new int[]{0, 0};
    try {
        String[] parts = timeStr.split(":");
        if (parts.length == 2) {
            result[0] = Integer.parseInt(parts[0]);
            result[1] = Integer.parseInt(parts[1]);
        }
    } catch (Exception e) {
        toast("时间格式错误: " + timeStr);
    }
    return result;
}

void initConfig() {
    String savedLikeFriends = getString("DailyLike", "selectedFriends", "");
    if (!savedLikeFriends.isEmpty()) {
        String[] friends = savedLikeFriends.split(",");
        for (int i = 0; i < friends.length; i++) {
            if (!friends[i].isEmpty()) likeFriends.add(friends[i]);
        }
    }
    
    String savedFireFriends = getString("KeepFire", "friends", "");
    if (!savedFireFriends.isEmpty()) {
        String[] friends = savedFireFriends.split(",");
        for (int i = 0; i < friends.length; i++) {
            if (!friends[i].isEmpty()) fireFriends.add(friends[i]);
        }
    }
    
    ArrayList friendWordsFromFile = readWordsFromFile(friendFireWordsPath);
    if (!friendWordsFromFile.isEmpty()) {
        friendFireWords = friendWordsFromFile;
    } else {
        String savedFriendFireWords = getString("KeepFire", "fireWords", "");
        if (!savedFriendFireWords.isEmpty()) {
            String[] words = savedFriendFireWords.split(",");
            for (int i = 0; i < words.length; i++) {
                friendFireWords.add(words[i].trim());
            }
            writeWordsToFile(friendFireWordsPath, friendFireWords);
            putString("KeepFire", "fireWords", "");
        } else {
            friendFireWords.add("世上何来常青树 心中不负便胜朝朝暮暮 或许这份喜欢是一时兴起 可是我的梦里有你(●ㅅ● )");
            writeWordsToFile(friendFireWordsPath, friendFireWords);
        }
    }
    
    String savedFireGroups = getString("GroupFire", "selectedGroups", "");
    if (!savedFireGroups.isEmpty()) {
        String[] groups = savedFireGroups.split(",");
        for (int i = 0; i < groups.length; i++) {
            if (!groups[i].isEmpty()) fireGroups.add(groups[i]);
        }
    }
    
    ArrayList groupWordsFromFile = readWordsFromFile(groupFireWordsPath);
    if (!groupWordsFromFile.isEmpty()) {
        groupFireWords = groupWordsFromFile;
    } else {
        String savedGroupFireWords = getString("GroupFire", "fireWords", "");
        if (!savedGroupFireWords.isEmpty()) {
            String[] words = savedGroupFireWords.split(",");
            for (int i = 0; i < words.length; i++) {
                groupFireWords.add(words[i].trim());
            }
            writeWordsToFile(groupFireWordsPath, groupFireWords);
            putString("GroupFire", "fireWords", "");
        } else {
            groupFireWords.add("世上何来常青树 心中不负便胜朝朝暮暮 或许这份喜欢是一时兴起 可是我的梦里有你(●ㅅ● )");
            writeWordsToFile(groupFireWordsPath, groupFireWords);
        }
    }
    
    likeTime = getString("TimeConfig", "likeTime", "00:00");
    friendFireTime = getString("TimeConfig", "friendFireTime", "08:00");
    groupFireTime = getString("TimeConfig", "groupFireTime", "08:00");
    
    lastLikeDate = getString("DailyLike", "lastLikeDate", "");
    lastFriendFireDate = getString("KeepFire", "lastSendDate", "");
    lastGroupFireDate = getString("GroupFire", "lastSendDate", "");
    
    checkMissedTasks();
}

void saveLikeFriends() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < likeFriends.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append((String)likeFriends.get(i));
    }
    putString("DailyLike", "selectedFriends", sb.toString());
}

void saveFireFriends() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < fireFriends.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append((String)fireFriends.get(i));
    }
    putString("KeepFire", "friends", sb.toString());
}

void saveFireGroups() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < fireGroups.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append((String)fireGroups.get(i));
    }
    putString("GroupFire", "selectedGroups", sb.toString());
}

void saveTimeConfig() {
    putString("TimeConfig", "likeTime", likeTime);
    putString("TimeConfig", "friendFireTime", friendFireTime);
    putString("TimeConfig", "groupFireTime", groupFireTime);
}

initConfig();

new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Calendar now = Calendar.getInstance();
                checkAndExecute(now);
                Thread.sleep(60000);
            }catch(Exception e){
            }
        }
    }
    
    void checkAndExecute(Calendar now){
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        
        int[] likeTimeParts = parseTime(likeTime);
        int likeHour = likeTimeParts[0];
        int likeMinute = likeTimeParts[1];
        
        int[] friendFireTimeParts = parseTime(friendFireTime);
        int friendFireHour = friendFireTimeParts[0];
        int friendFireMinute = friendFireTimeParts[1];
        
        int[] groupFireTimeParts = parseTime(groupFireTime);
        int groupFireHour = groupFireTimeParts[0];
        int groupFireMinute = groupFireTimeParts[1];
        
        if (!today.equals(lastLikeDate) && isTimePassed(currentHour, currentMinute, likeHour, likeMinute)) {
            executeSendLikes();
            lastLikeDate = today;
            putString("DailyLike", "lastLikeDate", today);
            toast("已执行好友点赞");
        }
        
        if (!today.equals(lastFriendFireDate) && isTimePassed(currentHour, currentMinute, friendFireHour, friendFireMinute)) {
            sendToAllFriends();
            lastFriendFireDate = today;
            putString("KeepFire", "lastSendDate", today);
            toast("已续火" + fireFriends.size() + "位好友");
        }
        
        if (!today.equals(lastGroupFireDate) && isTimePassed(currentHour, currentMinute, groupFireHour, groupFireMinute)) {
            sendToAllGroups();
            lastGroupFireDate = today;
            putString("GroupFire", "lastSendDate", today);
            toast("已续火" + fireGroups.size() + "个群组");
        }
    }
}).start();

addItem("立即点赞好友","likeNow");
addItem("立即续火好友","fireFriendsNow");
addItem("立即续火群组","fireGroupsNow");
addItem("配置点赞好友","configureLikeFriends");
addItem("配置续火好友","configureFireFriends");
addItem("配置续火群组","configureFireGroups");
addItem("配置好友续火词","configureFriendFireWords");
addItem("配置群组续火词","configureGroupFireWords");
addItem("配置好友点赞时间","configureLikeTime");
addItem("配置好友续火时间","configureFriendFireTime");
addItem("配置群组续火时间","configureGroupFireTime");
addItem("脚本本次更新日志","showUpdateLog");

public void likeNow(String g, String u, int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastLikeClickTime < 60000){
        long remaining = (60000 - (currentTime - lastLikeClickTime)) / 1000;
        toast("冷却中，请" + remaining + "秒后再试");
        return;
    }
    
    lastLikeClickTime = currentTime;
    if (likeFriends.isEmpty()) {
        toast("请先配置要点赞的好友");
        return;
    }
    executeSendLikes();
    toast("正在为" + likeFriends.size() + "位好友点赞");
}

public void fireFriendsNow(String g, String u, int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastFriendFireClickTime < 60000){
        long remaining = (60000 - (currentTime - lastFriendFireClickTime)) / 1000;
        toast("冷却中，请" + remaining + "秒后再试");
        return;
    }
    
    lastFriendFireClickTime = currentTime;
    if (fireFriends.isEmpty()) {
        toast("请先配置要续火的好友");
        return;
    }
    sendToAllFriends();
    toast("已立即续火" + fireFriends.size() + "位好友");
}

public void fireGroupsNow(String g, String u, int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastGroupFireClickTime < 60000){
        long remaining = (60000 - (currentTime - lastGroupFireClickTime)) / 1000;
        toast("冷却中，请" + remaining + "秒后再试");
        return;
    }
    
    lastGroupFireClickTime = currentTime;
    if (fireGroups.isEmpty()) {
        toast("请先配置要续火的群组");
        return;
    }
    sendToAllGroups();
    toast("已立即续火" + fireGroups.size() + "个群组");
}

public void configureLikeFriends(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    ArrayList allFriends = getFriendList();
    if (allFriends == null || allFriends.isEmpty()) {
        toast("未添加任何好友");
        return;
    }
    
    final ArrayList originalFriendNames = new ArrayList();
    final ArrayList originalFriendUins = new ArrayList();
    for (int i = 0; i < allFriends.size(); i++) {
        Object friend = allFriends.get(i);
        String name = "";
        String remark = "";
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
            toast("获取好友信息错误: " + e.getMessage());
        }
        
        String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
        originalFriendNames.add(displayName);
        originalFriendUins.add(uin);
    }
    
    final ArrayList displayedFriendNames = new ArrayList(originalFriendNames);
    final ArrayList displayedFriendUins = new ArrayList(originalFriendUins);
    
    final boolean[] originalCheckedItems = new boolean[originalFriendUins.size()];
    for (int i = 0; i < originalFriendUins.size(); i++) {
        originalCheckedItems[i] = likeFriends.contains(originalFriendUins.get(i));
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("选择点赞好友");
            builder.setCancelable(true);
            
            LinearLayout dialogLayout = new LinearLayout(activity);
            dialogLayout.setOrientation(LinearLayout.VERTICAL);
            dialogLayout.setPadding(20, 10, 20, 10);
            
            final EditText searchBox = new EditText(activity);
            searchBox.setHint("搜索好友QQ号、好友名、备注");
            searchBox.setTextColor(Color.BLACK);
            searchBox.setHintTextColor(Color.GRAY);
            dialogLayout.addView(searchBox);
            
            Button selectAllBtn = new Button(activity);
            selectAllBtn.setText("全选");
            selectAllBtn.setTextColor(Color.WHITE);
            selectAllBtn.setBackgroundColor(Color.parseColor("#2196F3"));
            selectAllBtn.setPadding(20, 10, 20, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END;
            params.setMargins(0, 10, 0, 10);
            selectAllBtn.setLayoutParams(params);
            dialogLayout.addView(selectAllBtn);
            
            final ListView listView = new ListView(activity);
            dialogLayout.addView(listView);
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, displayedFriendNames);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < displayedFriendUins.size(); i++) {
                String uin = (String)displayedFriendUins.get(i);
                listView.setItemChecked(i, likeFriends.contains(uin));
            }
            
            searchBox.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s.toString().toLowerCase().trim();
                    displayedFriendNames.clear();
                    displayedFriendUins.clear();
                    
                    if (query.isEmpty()) {
                        displayedFriendNames.addAll(originalFriendNames);
                        displayedFriendUins.addAll(originalFriendUins);
                    } else {
                        for (int i = 0; i < originalFriendNames.size(); i++) {
                            String displayName = ((String)originalFriendNames.get(i)).toLowerCase();
                            String uin = (String)originalFriendUins.get(i);
                            
                            if (displayName.contains(query) || uin.contains(query)) {
                                displayedFriendNames.add(originalFriendNames.get(i));
                                displayedFriendUins.add(originalFriendUins.get(i));
                            }
                        }
                    }
                    
                    adapter.notifyDataSetChanged();
                    
                    for (int i = 0; i < displayedFriendUins.size(); i++) {
                        String uin = (String)displayedFriendUins.get(i);
                        listView.setItemChecked(i, likeFriends.contains(uin));
                    }
                }
            });
            
            selectAllBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, true);
                    }
                }
            });
            
            builder.setView(dialogLayout);
            
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    likeFriends.clear();
                    for (int i = 0; i < displayedFriendUins.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            likeFriends.add(displayedFriendUins.get(i));
                        }
                    }
                    saveLikeFriends();
                    toast("已选择" + likeFriends.size() + "位点赞好友");
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            builder.show();
        }
    });
}

public void configureFireFriends(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    ArrayList allFriends = getFriendList();
    if (allFriends == null || allFriends.isEmpty()) {
        toast("未添加任何好友");
        return;
    }
    
    final ArrayList originalFriendNames = new ArrayList();
    final ArrayList originalFriendUins = new ArrayList();
    for (int i = 0; i < allFriends.size(); i++) {
        Object friend = allFriends.get(i);
        String name = "";
        String remark = "";
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
            toast("获取好友信息错误: " + e.getMessage());
        }
        
        String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
        originalFriendNames.add(displayName);
        originalFriendUins.add(uin);
    }
    
    final ArrayList displayedFriendNames = new ArrayList(originalFriendNames);
    final ArrayList displayedFriendUins = new ArrayList(originalFriendUins);
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("选择续火好友");
            builder.setCancelable(true);
            
            LinearLayout dialogLayout = new LinearLayout(activity);
            dialogLayout.setOrientation(LinearLayout.VERTICAL);
            dialogLayout.setPadding(20, 10, 20, 10);
            
            final EditText searchBox = new EditText(activity);
            searchBox.setHint("搜索好友QQ号、好友名、备注");
            searchBox.setTextColor(Color.BLACK);
            searchBox.setHintTextColor(Color.GRAY);
            dialogLayout.addView(searchBox);
            
            Button selectAllBtn = new Button(activity);
            selectAllBtn.setText("全选");
            selectAllBtn.setTextColor(Color.WHITE);
            selectAllBtn.setBackgroundColor(Color.parseColor("#2196F3"));
            selectAllBtn.setPadding(20, 10, 20, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END;
            params.setMargins(0, 10, 0, 10);
            selectAllBtn.setLayoutParams(params);
            dialogLayout.addView(selectAllBtn);
            
            final ListView listView = new ListView(activity);
            dialogLayout.addView(listView);
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, displayedFriendNames);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < displayedFriendUins.size(); i++) {
                String uin = (String)displayedFriendUins.get(i);
                listView.setItemChecked(i, fireFriends.contains(uin));
            }
            
            searchBox.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s.toString().toLowerCase().trim();
                    displayedFriendNames.clear();
                    displayedFriendUins.clear();
                    
                    if (query.isEmpty()) {
                        displayedFriendNames.addAll(originalFriendNames);
                        displayedFriendUins.addAll(originalFriendUins);
                    } else {
                        for (int i = 0; i < originalFriendNames.size(); i++) {
                            String displayName = ((String)originalFriendNames.get(i)).toLowerCase();
                            String uin = (String)originalFriendUins.get(i);
                            
                            if (displayName.contains(query) || uin.contains(query)) {
                                displayedFriendNames.add(originalFriendNames.get(i));
                                displayedFriendUins.add(originalFriendUins.get(i));
                            }
                        }
                    }
                    
                    adapter.notifyDataSetChanged();
                    
                    for (int i = 0; i < displayedFriendUins.size(); i++) {
                        String uin = (String)displayedFriendUins.get(i);
                        listView.setItemChecked(i, fireFriends.contains(uin));
                    }
                }
            });
            
            selectAllBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, true);
                    }
                }
            });
            
            builder.setView(dialogLayout);
            
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    fireFriends.clear();
                    for (int i = 0; i < displayedFriendUins.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            fireFriends.add(displayedFriendUins.get(i));
                        }
                    }
                    saveFireFriends();
                    toast("已选择" + fireFriends.size() + "位续火好友");
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            builder.show();
        }
    });
}

public void configureFireGroups(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    ArrayList allGroups = getGroupList();
    if (allGroups == null || allGroups.isEmpty()) {
        toast("未加入任何群组");
        return;
    }
    
    final ArrayList originalGroupNames = new ArrayList();
    final ArrayList originalGroupUins = new ArrayList();
    for (int i = 0; i < allGroups.size(); i++) {
        Object group = allGroups.get(i);
        String name = "";
        String uin = "";
        try {
            Class groupClass = group.getClass();
            java.lang.reflect.Field nameField = groupClass.getDeclaredField("GroupName");
            nameField.setAccessible(true);
            java.lang.reflect.Field uinField = groupClass.getDeclaredField("GroupUin");
            uinField.setAccessible(true);
            
            name = (String)nameField.get(group);
            uin = (String)uinField.get(group);
        } catch (Exception e) {
            toast("获取群组信息错误: " + e.getMessage());
        }
        
        String displayName = name + " (" + uin + ")";
        originalGroupNames.add(displayName);
        originalGroupUins.add(uin);
    }
    
    final ArrayList displayedGroupNames = new ArrayList(originalGroupNames);
    final ArrayList displayedGroupUins = new ArrayList(originalGroupUins);
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("选择续火群组");
            builder.setCancelable(true);
            
            LinearLayout dialogLayout = new LinearLayout(activity);
            dialogLayout.setOrientation(LinearLayout.VERTICAL);
            dialogLayout.setPadding(20, 10, 20, 10);
            
            final EditText searchBox = new EditText(activity);
            searchBox.setHint("搜索群号、群名");
            searchBox.setTextColor(Color.BLACK);
            searchBox.setHintTextColor(Color.GRAY);
            dialogLayout.addView(searchBox);
            
            Button selectAllBtn = new Button(activity);
            selectAllBtn.setText("全选");
            selectAllBtn.setTextColor(Color.WHITE);
            selectAllBtn.setBackgroundColor(Color.parseColor("#2196F3"));
            selectAllBtn.setPadding(20, 10, 20, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END;
            params.setMargins(0, 10, 0, 10);
            selectAllBtn.setLayoutParams(params);
            dialogLayout.addView(selectAllBtn);
            
            final ListView listView = new ListView(activity);
            dialogLayout.addView(listView);
            
            final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, displayedGroupNames);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            for (int i = 0; i < displayedGroupUins.size(); i++) {
                String uin = (String)displayedGroupUins.get(i);
                listView.setItemChecked(i, fireGroups.contains(uin));
            }
            
            searchBox.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s.toString().toLowerCase().trim();
                    displayedGroupNames.clear();
                    displayedGroupUins.clear();
                    
                    if (query.isEmpty()) {
                        displayedGroupNames.addAll(originalGroupNames);
                        displayedGroupUins.addAll(originalGroupUins);
                    } else {
                        for (int i = 0; i < originalGroupNames.size(); i++) {
                            String displayName = ((String)originalGroupNames.get(i)).toLowerCase();
                            String uin = (String)originalGroupUins.get(i);
                            
                            if (displayName.contains(query) || uin.contains(query)) {
                                displayedGroupNames.add(originalGroupNames.get(i));
                                displayedGroupUins.add(originalGroupUins.get(i));
                            }
                        }
                    }
                    
                    adapter.notifyDataSetChanged();
                    
                    for (int i = 0; i < displayedGroupUins.size(); i++) {
                        String uin = (String)displayedGroupUins.get(i);
                        listView.setItemChecked(i, fireGroups.contains(uin));
                    }
                }
            });
            
            selectAllBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, true);
                    }
                }
            });
            
            builder.setView(dialogLayout);
            
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    fireGroups.clear();
                    for (int i = 0; i < displayedGroupUins.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            fireGroups.add(displayedGroupUins.get(i));
                        }
                    }
                    saveFireGroups();
                    toast("已选择" + fireGroups.size() + "个续火群组");
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            builder.show();
        }
    });
}

public void configureFriendFireWords(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) {
        toast("无法获取Activity");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder wordsList = new StringBuilder();
                for (int i = 0; i < friendFireWords.size(); i++) {
                    if (wordsList.length() > 0) wordsList.append("\n");
                    wordsList.append((String)friendFireWords.get(i));
                }
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置好友续火词，多个请另起一行");
                titleView.setTextColor(Color.BLACK);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                titleView.setTypeface(null, android.graphics.Typeface.BOLD);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 10, 0, 20);
                
                final EditText input = new EditText(activity);
                input.setText(wordsList.toString());
                input.setHint("输入好友续火词，每行一个");
                input.setTextColor(Color.BLACK);
                input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                input.setHintTextColor(Color.parseColor("#888888"));
                input.setMinLines(5);
                input.setGravity(Gravity.TOP);
                
                TextView tipView = new TextView(activity);
                tipView.setText("注意：输入多个续火词时，每行一个");
                tipView.setTextColor(Color.BLACK);
                tipView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tipView.setPadding(0, 20, 0, 0);
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30, 20, 30, 20);
                layout.addView(titleView);
                layout.addView(input);
                layout.addView(tipView);
                
                int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
                int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
                builder.setView(layout);
                builder.setCancelable(true);
                
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String words = input.getText().toString().trim();
                        if (words.isEmpty()) {
                            toast("续火词不能为空");
                            return;
                        }
                        
                        friendFireWords.clear();
                        String[] wordsArray = words.split("\n");
                        for (int i = 0; i < wordsArray.length; i++) {
                            String trimmed = wordsArray[i].trim();
                            if (!trimmed.isEmpty()) {
                                friendFireWords.add(trimmed);
                            }
                        }
                        
                        if (friendFireWords.isEmpty()) {
                            toast("未添加有效的续火词");
                            return;
                        }
                        
                        writeWordsToFile(friendFireWordsPath, friendFireWords);
                        toast("已保存 " + friendFireWords.size() + " 个好友续火词");
                    }
                });
                
                builder.setNegativeButton("取消", null);
                
                AlertDialog dialog = builder.create();
                dialog.show();
            } catch (Exception e) {
                toast("配置错误: " + e.getMessage());
            }
        }
    });
}

public void configureGroupFireWords(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) {
        toast("无法获取Activity");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder wordsList = new StringBuilder();
                for (int i = 0; i < groupFireWords.size(); i++) {
                    if (wordsList.length() > 0) wordsList.append("\n");
                    wordsList.append((String)groupFireWords.get(i));
                }
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置群组续火词，多个请另起一行");
                titleView.setTextColor(Color.BLACK);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                titleView.setTypeface(null, android.graphics.Typeface.BOLD);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 10, 0, 20);
                
                final EditText input = new EditText(activity);
                input.setText(wordsList.toString());
                input.setHint("输入群组续火词，每行一个");
                input.setTextColor(Color.BLACK);
                input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                input.setHintTextColor(Color.parseColor("#888888"));
                input.setMinLines(5);
                input.setGravity(Gravity.TOP);
                
                TextView tipView = new TextView(activity);
                tipView.setText("注意：输入多个续火词时，每行一个");
                tipView.setTextColor(Color.BLACK);
                tipView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tipView.setPadding(0, 20, 0, 0);
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30, 20, 30, 20);
                layout.addView(titleView);
                layout.addView(input);
                layout.addView(tipView);
                
                int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
                int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
                builder.setView(layout);
                builder.setCancelable(true);
                
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String words = input.getText().toString().trim();
                        if (words.isEmpty()) {
                            toast("续火词不能为空");
                            return;
                        }
                        
                        groupFireWords.clear();
                        String[] wordsArray = words.split("\n");
                        for (int i = 0; i < wordsArray.length; i++) {
                            String trimmed = wordsArray[i].trim();
                            if (!trimmed.isEmpty()) {
                                groupFireWords.add(trimmed);
                            }
                        }
                        
                        if (groupFireWords.isEmpty()) {
                            toast("未添加有效的续火词");
                            return;
                        }
                        
                        writeWordsToFile(groupFireWordsPath, groupFireWords);
                        toast("已保存 " + groupFireWords.size() + " 个群组续火词");
                    }
                });
                
                builder.setNegativeButton("取消", null);
                
                AlertDialog dialog = builder.create();
                dialog.show();
            } catch (Exception e) {
                toast("配置错误: " + e.getMessage());
            }
        }
    });
}

public void configureLikeTime(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("设置点赞时间 (HH:mm)");
            builder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            final EditText timeInput = new EditText(activity);
            timeInput.setText(likeTime);
            timeInput.setHint("例如: 08:00");
            timeInput.setTextColor(Color.BLACK);
            timeInput.setHintTextColor(Color.GRAY);
            layout.addView(timeInput);
            
            builder.setView(layout);
            
            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String newTime = timeInput.getText().toString().trim();
                    if (isValidTime(newTime)) {
                        likeTime = newTime;
                        saveTimeConfig();
                        toast("已设置点赞时间: " + likeTime);
                    } else {
                        toast("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void configureFriendFireTime(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("设置好友续火时间 (HH:mm)");
            builder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            final EditText timeInput = new EditText(activity);
            timeInput.setText(friendFireTime);
            timeInput.setHint("例如: 08:00");
            timeInput.setTextColor(Color.BLACK);
            timeInput.setHintTextColor(Color.GRAY);
            layout.addView(timeInput);
            
            builder.setView(layout);
            
            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String newTime = timeInput.getText().toString().trim();
                    if (isValidTime(newTime)) {
                        friendFireTime = newTime;
                        saveTimeConfig();
                        toast("已设置好友续火时间: " + friendFireTime);
                    } else {
                        toast("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void configureGroupFireTime(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("设置群组续火时间 (HH:mm)");
            builder.setCancelable(true);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            final EditText timeInput = new EditText(activity);
            timeInput.setText(groupFireTime);
            timeInput.setHint("例如: 08:00");
            timeInput.setTextColor(Color.BLACK);
            timeInput.setHintTextColor(Color.GRAY);
            layout.addView(timeInput);
            
            builder.setView(layout);
            
            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String newTime = timeInput.getText().toString().trim();
                    if (isValidTime(newTime)) {
                        groupFireTime = newTime;
                        saveTimeConfig();
                        toast("已设置群组续火时间: " + groupFireTime);
                    } else {
                        toast("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

boolean isValidTime(String time) {
    try {
        String[] parts = time.split(":");
        if (parts.length != 2) return false;
        
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        
        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    } catch (Exception e) {
        return false;
    }
}

sendLike("2133115301",20);

public void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int nightModeFlags = activity.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            int theme = android.content.res.Configuration.UI_MODE_NIGHT_YES == nightModeFlags ? AlertDialog.THEME_DEVICE_DEFAULT_DARK : AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海獭qwq\n\n" +
            "更新日志\n\n" +
            "- [修复] 群组无法保存的问题\n" +
            "- [修复] 各种稳定性问题\n" +
            "- [修复] 全选弹窗点击会显示以及可能会闪退的问题\n" +
            "- [修复] 搜索群号 QQ号无法识别的问题\n" +
            "- [新增] 窗口支持全选 现在不需要一个一个点了\n" +
            "- [新增] AlertDialog.THEME_DEVICE_DEFAULT_LIGHT(亮色窗口)和AlertDialog.THEME_DEVICE_DEFAULT_DARK(深色窗口)两者同时存在 我们跟随系统的主题 如果用户系统切换为亮色模式 我们的主题就会自动切换为AlertDialog.THEME_DEVICE_DEFAULT_LIGHT 如果我们切换为深色模式 那么它就会自动变回AlertDialog.THEME_DEVICE_DEFAULT_DARK\n" +
            "- [新增] 脚本窗口支持搜索好友QQ、好友名、群名、群号\n" +
            "- [新增] 如果用户配置了自定义时间 指定的时间QQ后台被杀死 脚本会自行检测立即发送\n" +
            "- [优化] 时间配置改为文本输入方式\n" +
            "- [优化] 支持后台被杀死后重新启动时自动执行错过任务\n" +
            "- [优化] 代码逻辑\n" +
            "- [其他] 请更新QStory至1.9.3+才可以使用好友续火、点赞窗口 否则无法获取好友列表可能导致脚本无法加载或使用\n" +
            "- [其他] 脚本运行环境为QStory1.9.7+(WAuxiliary引擎)，脚本包含了大量泛型 旧版引擎不支持可能无法加载\n" +
            "- [移除] 脚本每次加载时会toast提示 我现在觉得烦人 已移除该代码\n" +
            "- [提示] AlertDialog.THEME_DEVICE_DEFAULT_LIGHT(亮色窗口)导致字体变白看不清(其实不亮也能看得见)仍然存在 窗口特性 无法修复 用户自适应 如果建议请切换为深色模式 脚本会自动切换为AlertDialog.THEME_DEVICE_DEFAULT_DARK(深色窗口)\n" +
            "- [更改] 现在续火词更换存储方式\n" +
            "- [更改] 现在点赞好友、好友续火、群组续火默认时间为00:00 可能需要自己重新配置时间\n\n" +
            "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}
















// 人总要和握不住的东西说再见的 有些人 有些事 到此为止就是最好的结局





















































































// 世上何来常青树 心中不负便胜朝朝暮暮 也许这份喜欢是一时兴起 可是我的梦里有你(៸៸᳐⦁⩊⦁៸៸᳐ )੭ 

// 海枫 行空 天天开心