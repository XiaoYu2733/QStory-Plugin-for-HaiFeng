
// 作 海枫

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
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

ArrayList targetFriends = new ArrayList();
ArrayList fireWordsList = new ArrayList();

int sendHour = 8;
int sendMinute = 0;
long lastClickTime = 0;

void initFireWords() {
    try {
        File dir = new File(appPath + "/续火词");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File file = new File(dir, "群组续火词.txt");
        if (!file.exists()) {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("我是真的讨厌异地恋 也是真的喜欢你");
            writer.close();
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        
        String savedWords = content.toString().trim();
        if (!savedWords.isEmpty()) {
            String[] words = savedWords.split(",");
            for (int i = 0; i < words.length; i++) {
                fireWordsList.add(words[i].trim());
            }
        } else {
            FileWriter writer = new FileWriter(file);
            writer.write("我是真的讨厌异地恋 也是真的喜欢你");
            writer.close();
            fireWordsList.add("我是真的讨厌异地恋 也是真的喜欢你");
        }
    } catch (Exception e) {
        fireWordsList.add("我是真的讨厌异地恋 也是真的喜欢你");
    }
}

void initConfig() {
    String savedFriends = getString("KeepFire", "friends", "");
    if (!savedFriends.isEmpty()) {
        String[] friends = savedFriends.split(",");
        for (String friend : friends) {
            if (!friend.isEmpty()) {
                targetFriends.add(friend);
            }
        }
    }
    
    initFireWords();
}

void saveFriends() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < targetFriends.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append(targetFriends.get(i));
    }
    putString("KeepFire", "friends", sb.toString());
}

void saveFireWords() {
    try {
        File dir = new File(appPath + "/续火词");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File file = new File(dir, "好友续火词.txt");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fireWordsList.size(); i++) {
            if (sb.length() > 0) sb.append(",");
            sb.append(fireWordsList.get(i));
        }
        
        FileWriter writer = new FileWriter(file);
        writer.write(sb.toString());
        writer.close();
    } catch (Exception e) {
        toast("保存续火词文件错误:" + e.getMessage());
    }
}

int getDialogTheme() {
    try {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            return AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        } else {
            return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        }
    } catch (Exception e) {
        return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    }
}

initConfig();

new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Calendar now = Calendar.getInstance();
                checkAndSend(now);
                Thread.sleep(60000);
            }catch(Exception e){
                toast("定时器错误:" + e.getMessage());
            }
        }
    }
    
    void checkAndSend(Calendar now){
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        
        if(currentHour == sendHour && currentMinute == sendMinute && !today.equals(getString("KeepFire","lastSendDate",""))){
            sendToAllFriends();
            putString("KeepFire","lastSendDate",today);
            toast("已续火" + targetFriends.size() + "位好友");
        }
    }
}).start();

void sendToAllFriends(){
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < targetFriends.size(); i++){
                String friend = (String)targetFriends.get(i);
                try{
                    int index = (int)(Math.random() * fireWordsList.size());
                    String word = (String)fireWordsList.get(index);
                    sendMsg("",friend,word);
                    Thread.sleep(5000);
                }catch(Exception e){
                    toast(friend + "续火失败:" + e.getMessage());
                }
            }
        }
    }).start();
}

addItem("立即续火","keepFireNow");
addItem("配置好友","configureFriends");
addItem("配置续火词","configureFireWords");
addItem("更新日志","showUpdateLog");

public void keepFireNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        toast("冷却中，请" + remaining + "秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    if (targetFriends.isEmpty()) {
        toast("请先配置要续火的好友");
        return;
    }
    sendToAllFriends();
    toast("已立即续火" + targetFriends.size() + "位好友");
}

public void configureFriends(String g, String u, int t){
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
            java.lang.reflect.Field remarkField = friend.getClass().getDeclaredField("remark");
            remarkField.setAccessible(true);
            java.lang.reflect.Field nameField = friend.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            java.lang.reflect.Field uinField = friend.getClass().getDeclaredField("uin");
            uinField.setAccessible(true);
            
            remark = (String)remarkField.get(friend);
            name = (String)nameField.get(friend);
            uin = (String)uinField.get(friend);
        } catch (Exception e) {
        }
        
        String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
        originalFriendNames.add(displayName);
        originalFriendUins.add(uin);
    }
    
    final ArrayList displayedFriendNames = new ArrayList(originalFriendNames);
    final ArrayList displayedFriendUins = new ArrayList(originalFriendUins);
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, getDialogTheme());
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
                listView.setItemChecked(i, targetFriends.contains(uin));
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
                        listView.setItemChecked(i, targetFriends.contains(uin));
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
                    targetFriends.clear();
                    for (int i = 0; i < displayedFriendUins.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            targetFriends.add(displayedFriendUins.get(i));
                        }
                    }
                    saveFriends();
                    toast("已选择" + targetFriends.size() + "位续火好友");
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            builder.show();
        }
    });
}

public void configureFireWords(String g,String u,int t){
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder currentWords = new StringBuilder();
                for (int i = 0; i < fireWordsList.size(); i++) {
                    if (currentWords.length() > 0) currentWords.append(",");
                    currentWords.append(fireWordsList.get(i));
                }
                
                final EditText input = new EditText(activity);
                input.setText(currentWords.toString());
                input.setHint("请输入续火词，用逗号分隔");
                
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getDialogTheme());
                builder.setTitle("配置续火词");
                builder.setView(input);
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String words = input.getText().toString().trim();
                        if (words.isEmpty()) {
                            toast("续火词不能为空");
                            return;
                        }
                        
                        fireWordsList.clear();
                        String[] wordsArray = words.split(",");
                        for (int i = 0; i < wordsArray.length; i++) {
                            String word = wordsArray[i].trim();
                            if (!word.isEmpty()) {
                                fireWordsList.add(word);
                            }
                        }
                        
                        if (fireWordsList.isEmpty()) {
                            toast("未添加有效的续火词");
                            return;
                        }
                        
                        saveFireWords();
                        toast("已保存 " + fireWordsList.size() + " 个续火词");
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            } catch (Exception e) {
                toast("配置错误: " + e.getMessage());
            }
        }
    });
}

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getDialogTheme());
                builder.setTitle("续火脚本更新日志");
                builder.setMessage("更新日志\n" +
                        "- [优化] 弹窗过于古老，使用AlertDialog.THEME_DEVICE_DEFAULT_LIGHT主题 UI现代化\n" +
                    "- [其他] 如果用户系统切换为日间模式 弹窗风格自动切换为AlertDialog.THEME_DEVICE_DEFAULT_LIGHT(亮色弹窗) 如果用户切换为深色模式 弹窗会自动切换为AlertDialog.THEME_DEVICE_DEFAULT_DARK(深色弹窗)\n" +
                    "- [新增] 弹窗配置好友及续火词功能\n" +
                    "- [新增] 搜索功能 支持搜索好友名字 qq号\n" +
                    "- [新增] 全选功能\n" +
                    "- [新增] 支持多个续火词随机发送\n" +
                    "- [添加] 点击时间记录防止刷屏\n" +
                    "- [优化] 发送间隔增加到5秒更安全\n" +
                    "- [优化] 冷却提示精确到秒\n" +
                    "- [修复] 没有打死夜七的问题\n\n" +
                    "- [移除] 传统的续火存储方式\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
                builder.setPositiveButton("确定", null);
                builder.show();
            } catch (Exception e) {
                toast("显示日志错误: " + e.getMessage());
            }
        }
    });
}

sendLike("2133115301",20);