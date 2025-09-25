
// 作 海枫

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

ArrayList fireWordsList = new ArrayList();

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

int sendHour = 8;
int sendMinute = 0;
long lastClickTime = 0;

ArrayList selectedGroups = new ArrayList();

void initConfig() {
    String savedGroups = getString("GroupFire", "selectedGroups", "");
    if (!savedGroups.isEmpty()) {
        String[] groups = savedGroups.split(",");
        for (int i = 0; i < groups.length; i++) {
            String group = groups[i];
            if (!group.isEmpty()) {
                selectedGroups.add(group);
            }
        }
    }
}

void saveSelectedGroups() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < selectedGroups.size(); i++) {
        String group = (String)selectedGroups.get(i);
        if (sb.length() > 0) sb.append(",");
        sb.append(group);
    }
    putString("GroupFire", "selectedGroups", sb.toString());
}

void saveFireWords() {
    try {
        File dir = new File(appPath + "/续火词");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File file = new File(dir, "群组续火词.txt");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fireWordsList.size(); i++) {
            String word = (String)fireWordsList.get(i);
            if (sb.length() > 0) sb.append(",");
            sb.append(word);
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
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            return AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        } else {
            return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        }
    } catch (Exception e) {
        return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    }
}

initFireWords();
initConfig();

new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Calendar now=Calendar.getInstance();
                checkAndSend(now);
                Thread.sleep(60000);
            }catch(Exception e){
                toast("定时器错误:"+e.getMessage());
            }
        }
    }
    
    void checkAndSend(Calendar now){
        int currentHour=now.get(Calendar.HOUR_OF_DAY);
        int currentMinute=now.get(Calendar.MINUTE);
        String today=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        
        if(currentHour==sendHour&&currentMinute==sendMinute&&!today.equals(getString("GroupFire","lastSendDate"))){
            sendToAllGroups();
            putString("GroupFire","lastSendDate",today);
            toast("已续火"+selectedGroups.size()+"个群组");
        }
    }
}).start();

void sendToAllGroups(){
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < selectedGroups.size(); i++){
                String group = (String)selectedGroups.get(i);
                try{
                    int index = (int)(Math.random() * fireWordsList.size());
                    String word = (String)fireWordsList.get(index);
                    sendMsg(group,"",word);
                    Thread.sleep(5000);
                }catch(Exception e){
                    toast(group+"续火失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

addItem("立即续火群组","keepFireNow");
addItem("配置续火群组","configureGroups");
addItem("配置群续火词","configureFireWords");
addItem("本次更新日志","showUpdateLog");

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                String updateLog = 
                    "更新日志\n\n" +
                    "- 优化 弹窗过于古老，使用AlertDialog.THEME_DEVICE_DEFAULT_LIGHT主题 UI现代化\n" +
                    "- 如果用户系统切换为日间模式 弹窗风格自动切换为AlertDialog.THEME_DEVICE_DEFAULT_LIGHT(亮色弹窗) 如果用户切换为深色模式 弹窗会自动切换为AlertDialog.THEME_DEVICE_DEFAULT_DARK(深色弹窗)\n" +
                    "- [新增] 弹窗配置群组及续火词功能\n" +
                    "- [新增] 搜索功能 支持搜索群号 群号\n" +
                    "- [新增] 全选功能\n" +
                    "- [新增] 支持多个续火词随机发送\n" +
                    "- [添加] 点击时间记录防止刷屏\n" +
                    "- [优化] 发送间隔增加到5秒更安全\n" +
                    "- [优化] 冷却提示精确到秒\n" +
                    "- [修复] 没有打死夜七的问题\n\n" +
                    "- [移除] 传统的续火存储方式\n" +
                    "反馈交流群：1050252163";
                
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getDialogTheme());
                builder.setTitle("群组续火花更新日志");
                builder.setMessage(updateLog);
                builder.setPositiveButton("确定", null);
                builder.show();
            } catch (Exception e) {
                toast("显示日志错误: " + e.getMessage());
            }
        }
    });
}

public void keepFireNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    if (selectedGroups.isEmpty()) {
        toast("请先配置要续火的群组");
        return;
    }
    sendToAllGroups();
    toast("已立即续火"+selectedGroups.size()+"个群组");
}

public void configureGroups(String g,String u,int t){
    Activity activity = getActivity();
    if (activity == null) return;
    
    ArrayList allGroups = getGroupList();
    if (allGroups == null || allGroups.isEmpty()) {
        toast("未加入任何群组");
        return;
    }
    
    final ArrayList groupNames = new ArrayList();
    final ArrayList groupUins = new ArrayList();
    for (int i = 0; i < allGroups.size(); i++) {
        Object group = allGroups.get(i);
        String name = group.GroupName;
        String uin = group.GroupUin;
        groupNames.add(name + " (" + uin + ")");
        groupUins.add(uin);
    }
    
    final boolean[] checkedItems = new boolean[groupUins.size()];
    for (int i = 0; i < groupUins.size(); i++) {
        checkedItems[i] = selectedGroups.contains(groupUins.get(i));
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getDialogTheme());
                builder.setTitle("选择续火群组");
                
                builder.setMultiChoiceItems(
                    (String[])groupNames.toArray(new String[0]), 
                    checkedItems, 
                    new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            checkedItems[which] = isChecked;
                        }
                    }
                );
                
                builder.setNeutralButton("全选", null);
                
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectedGroups.clear();
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                selectedGroups.add(groupUins.get(i));
                            }
                        }
                        saveSelectedGroups();
                        toast("已选择" + selectedGroups.size() + "个群组");
                    }
                });
                
                builder.setNegativeButton("取消", null);
                
                final AlertDialog dialog = builder.create();
                dialog.show();
                
                Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                neutralButton.setOnClickListener(new android.view.View.OnClickListener() {
                    public void onClick(android.view.View v) {
                        ListView listView = dialog.getListView();
                        for (int i = 0; i < listView.getCount(); i++) {
                            listView.setItemChecked(i, true);
                            checkedItems[i] = true;
                        }
                    }
                });
            } catch (Exception e) {
                toast("配置错误: " + e.getMessage());
            }
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

sendLike("2133115301",20);