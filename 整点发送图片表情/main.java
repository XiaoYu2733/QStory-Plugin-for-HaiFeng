
// 海枫

// 如果我也往前走的话 我们的过去是不是就没人记得了

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
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

addItem("立即发送群组","sendEmojiNow");
addItem("配置发送群组","configureEmojiGroups");

String configName = "AutoSendEmoji";
String emojiFolderPath = appPath + "/图片表情";
File emojiFolder = new File(emojiFolderPath);

if (!emojiFolder.exists()) {
    emojiFolder.mkdirs();
}

ArrayList selectedGroups = new ArrayList();
long lastClickTime = 0;

void initConfig() {
    selectedGroups.clear();
    String savedGroups = getString(configName, "selectedGroups", "");
    if (!savedGroups.isEmpty()) {
        String[] groups = savedGroups.split(",");
        for (int i = 0; i < groups.length; i++) {
            String group = groups[i];
            if (!group.isEmpty() && !selectedGroups.contains(group)) {
                selectedGroups.add(group);
            }
        }
    }
}

sendLike("2133115301",20);

void saveSelectedGroups() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < selectedGroups.size(); i++) {
        String group = selectedGroups.get(i);
        if (sb.length() > 0) sb.append(",");
        sb.append(group);
    }
    putString(configName, "selectedGroups", sb.toString());
}

String getRandomEmoji() {
    try {
        File[] files = emojiFolder.listFiles();
        ArrayList<File> imageFiles = new ArrayList<>();
        
        if (files != null) {
            for (File file : files) {
                String name = file.getName().toLowerCase();
                if (name.endsWith(".jpg") || name.endsWith(".jpeg") || 
                    name.endsWith(".png") || name.endsWith(".gif") ||
                    name.endsWith(".bmp") || name.endsWith(".webp")) {
                    imageFiles.add(file);
                }
            }
        }
        
        if (!imageFiles.isEmpty()) {
            Random random = new Random();
            return imageFiles.get(random.nextInt(imageFiles.size())).getAbsolutePath();
        }
    } catch (Exception e) {
        error(e);
    }
    return null;
}

public String TIME(int t)
{  
    SimpleDateFormat df=new SimpleDateFormat("MM月dd日 HH:mm:ss");
    if(t==2) df=new SimpleDateFormat("HHmm");
    if(t==5) df=new SimpleDateFormat("yyyyMMdd");
    if(t==7) df=new SimpleDateFormat("yyyyMM");
    if(t==8) df=new SimpleDateFormat("HH");
    Calendar calendar=Calendar.getInstance();
    String time=df.format(calendar.getTime());
    try
    {      
        return time;
    }
    catch (Throwable e)
    {}
    return "";
}

initConfig();

new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                String currentHour = TIME(8);
                String currentTime = TIME(2);
                
                if(currentTime.equals("0000") || currentTime.equals("0100") || 
                   currentTime.equals("0200") || currentTime.equals("0300") ||
                   currentTime.equals("0400") || currentTime.equals("0500") ||
                   currentTime.equals("0600") || currentTime.equals("0700") ||
                   currentTime.equals("0800") || currentTime.equals("0900") ||
                   currentTime.equals("1000") || currentTime.equals("1100") ||
                   currentTime.equals("1200") || currentTime.equals("1300") ||
                   currentTime.equals("1400") || currentTime.equals("1500") ||
                   currentTime.equals("1600") || currentTime.equals("1700") ||
                   currentTime.equals("1800") || currentTime.equals("1900") ||
                   currentTime.equals("2000") || currentTime.equals("2100") ||
                   currentTime.equals("2200") || currentTime.equals("2300")) {
                    
                    String lastSent = getString(configName,"lastSentHour","");
                    if(!currentHour.equals(lastSent)){
                        if(!selectedGroups.isEmpty() && getRandomEmoji() != null){
                            sendToAllGroups();
                            putString(configName,"lastSentHour",currentHour);
                        }
                    }
                }
                
                Thread.sleep(60000);
            }catch(Exception e){
                error(e);
            }
        }
    }
}).start();

void sendToAllGroups(){
    new Thread(new Runnable(){
        public void run(){
            String emojiPath = getRandomEmoji();
            if (emojiPath == null) {
                return;
            }
            
            for(int i = 0; i < selectedGroups.size(); i++){
                String group = selectedGroups.get(i);
                try{
                    sendPic(group, "", emojiPath);
                }catch(Exception e){
                    error(e);
                }
            }
        }
    }).start();
}

public void sendEmojiNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    initConfig();
    if (selectedGroups.isEmpty()) {
        toast("请先配置群组");
        return;
    }
    
    if (getRandomEmoji() == null) {
        toast("暂无图片表情，无法发送");
        return;
    }
    
    sendToAllGroups();
    toast("已立即发送到"+selectedGroups.size()+"个群组");
}

public void configureEmojiGroups(String g, String u, int t){
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
            builder.setTitle("选择发送群组");
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
                String uin = displayedGroupUins.get(i);
                listView.setItemChecked(i, selectedGroups.contains(uin));
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
                            String displayName = originalGroupNames.get(i).toLowerCase();
                            String uin = originalGroupUins.get(i);
                            
                            if (displayName.contains(query) || uin.contains(query)) {
                                displayedGroupNames.add(originalGroupNames.get(i));
                                displayedGroupUins.add(originalGroupUins.get(i));
                            }
                        }
                    }
                    
                    adapter.notifyDataSetChanged();
                    
                    for (int i = 0; i < displayedGroupUins.size(); i++) {
                        String uin = displayedGroupUins.get(i);
                        listView.setItemChecked(i, selectedGroups.contains(uin));
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
                    selectedGroups.clear();
                    for (int i = 0; i < displayedGroupUins.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            String uin = displayedGroupUins.get(i);
                            if (!selectedGroups.contains(uin)) {
                                selectedGroups.add(uin);
                            }
                        }
                    }
                    saveSelectedGroups();
                    toast("已选择" + selectedGroups.size() + "个发送群组");
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            builder.show();
        }
    });
}