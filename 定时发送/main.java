
// 作 海枫

// 当你意识到生命只有一次的时候，你的第二次生命就开始了

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

ArrayList selectedGroups = new ArrayList();
ArrayList messages = new ArrayList();
int sendHour = 19;
int sendMinute = 26;
String lastSendDate = "";
String customMessage = "";

void initConfig() {
    String savedGroups = getString("TimedMessage", "selectedGroups", "");
    if (!savedGroups.isEmpty()) {
        String[] groups = savedGroups.split(",");
        for (int i = 0; i < groups.length; i++) {
            if (!groups[i].isEmpty()) selectedGroups.add(groups[i]);
        }
    }

    lastSendDate = getString("TimedMessage", "lastSendDate", "");
    sendHour = getInt("TimedMessage", "sendHour", 19);
    sendMinute = getInt("TimedMessage", "sendMinute", 26);
    customMessage = getString("TimedMessage", "customMessage", "");

    checkAndCreateFiles();
    loadMessages();
}

void checkAndCreateFiles() {
    try {
        File messageFile = new File(appPath + "/messages.txt");
        if (!messageFile.exists()) {
            messageFile.createNewFile();
            FileWriter writer = new FileWriter(messageFile);
            writer.write("测试");
            writer.close();
        }
    } catch (Exception e) {
        toast("文件创建错误: " + e.getMessage());
    }
}

void loadMessages() {
    messages.clear();

    try {
        File messageFile = new File(appPath + "/messages.txt");
        BufferedReader reader = new BufferedReader(new FileReader(messageFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                messages.add(line.trim());
            }
        }
        reader.close();
    } catch (Exception e) {
        toast("读取消息文件错误: " + e.getMessage());
    }
}

void saveSelectedGroups() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < selectedGroups.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append(selectedGroups.get(i));
    }
    putString("TimedMessage", "selectedGroups", sb.toString());
}

void onMsg(Object msg) {
    if (!msg.UserUin.equals(myUin)) return;
    
    String text = msg.MessageContent;
    
    if (text.startsWith("设置定时消息时间")) {
        String timeStr = text.replace("设置定时消息时间", "").trim();
        try {
            String[] parts = timeStr.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                sendMsg(msg.GroupUin, "", "时间格式错误，请使用HH:MM格式");
                return;
            }
            
            sendHour = hour;
            sendMinute = minute;
            putInt("TimedMessage", "sendHour", hour);
            putInt("TimedMessage", "sendMinute", minute);
            
            sendMsg(msg.GroupUin, "", "设置成功，定时消息时间为: " + timeStr);
        } catch (Exception e) {
            sendMsg(msg.GroupUin, "", "时间格式错误，请使用HH:MM格式");
        }
    } else if (text.startsWith("设置定时消息")) {
        customMessage = text.replace("设置定时消息", "").trim();
        putString("TimedMessage", "customMessage", customMessage);
        try {
            File messageFile = new File(appPath + "/messages.txt");
            FileWriter writer = new FileWriter(messageFile, true);
            writer.write(customMessage + "\n");
            writer.close();
            loadMessages();
        } catch (Exception e) {
            toast("写入消息文件错误: " + e.getMessage());
        }
        sendMsg(msg.GroupUin, "", "设置成功，定时消息为: " + customMessage);
    }
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
                log("定时错误:" + e.getMessage());
            }
        }
    }

    void checkAndExecute(Calendar now){
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);

        int currentTotalMinutes = currentHour * 60 + currentMinute;
        int sendTotalMinutes = sendHour * 60 + sendMinute;

        if (currentTotalMinutes >= sendTotalMinutes && !today.equals(lastSendDate)) {
            sendTimedMessages();
            lastSendDate = today;
            putString("TimedMessage", "lastSendDate", today);
        }
    }
}).start();

void sendTimedMessages(){
    if (selectedGroups.isEmpty()) {
        toast("未选择群组");
        return;
    }

    String messageToSend = customMessage.isEmpty() ? 
        (messages.isEmpty() ? "测试" : (String)messages.get(getInt("TimedMessage", "messageIndex", 0) % messages.size())) : 
        customMessage;

    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < selectedGroups.size(); i++){
                String group = (String)selectedGroups.get(i);
                try{
                    sendMsg(group, "", messageToSend);
                    Thread.sleep(3000);
                }catch(Exception e){
                    toast(group + "发送失败:" + e.getMessage());
                }
            }

            if (customMessage.isEmpty() && !messages.isEmpty()) {
                int messageIndex = getInt("TimedMessage", "messageIndex", 0);
                putInt("TimedMessage", "messageIndex", messageIndex + 1);
            }
            
            toast("已向" + selectedGroups.size() + "个群发送定时消息");
        }
    }).start();
}

addItem("立即发送消息","sendNow");
addItem("选择发送群组","selectGroups");
addItem("重新加载消息","reloadMessages");
addItem("脚本使用方法","showUsage");
addItem("脚本更新日志","showChangelog");

public void sendNow(String g, String u, int t){
    Calendar now = Calendar.getInstance();
    String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);
    lastSendDate = today;
    putString("TimedMessage", "lastSendDate", today);
    sendTimedMessages();
}

public void selectGroups(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) {
        toast("无法获取Activity");
        return;
    }

    ArrayList allGroups = getGroupList();
    if (allGroups == null || allGroups.isEmpty()) {
        toast("未加入任何群组");
        return;
    }

    final ArrayList groupNames = new ArrayList();
    final ArrayList groupUins = new ArrayList();
    for (int i = 0; i < allGroups.size(); i++) {
        Object group = allGroups.get(i);
        try {
            String name = (String)group.getClass().getDeclaredField("GroupName").get(group);
            String uin = (String)group.getClass().getDeclaredField("GroupUin").get(group);
            groupNames.add(name + " (" + uin + ")");
            groupUins.add(uin);
        } catch (Exception e) {
            toast("获取群组信息错误: " + e.getMessage());
        }
    }

    final boolean[] checkedItems = new boolean[groupUins.size()];
    for (int i = 0; i < groupUins.size(); i++) {
        checkedItems[i] = selectedGroups.contains(groupUins.get(i));
    }

    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("选择发送群组");
                builder.setCancelable(true);

                builder.setMultiChoiceItems(
                    groupNames.toArray(new String[0]), 
                    checkedItems, 
                    new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            checkedItems[which] = isChecked;
                        }
                    }
                );

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectedGroups.clear();
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                selectedGroups.add(groupUins.get(i));
                            }
                        }
                        saveSelectedGroups();
                        toast("已选择" + selectedGroups.size() + "个发送群组");
                    }
                });

                builder.setNegativeButton("取消", null);

                builder.setNeutralButton("全选", null);

                final AlertDialog dialog = builder.create();
                dialog.show();

                Button selectAllButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                selectAllButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ListView listView = dialog.getListView();
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = true;
                            listView.setItemChecked(i, true);
                        }
                    }
                });
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
}

public void reloadMessages(String g, String u, int t){
    loadMessages();
    toast("已重新加载" + messages.size() + "条消息");
}

public void showUsage(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("使用方法");
                builder.setMessage("设置定时消息 早上好 早上好是示例\n设置定时消息时间 10:00 10:00是示列");
                builder.setPositiveButton("确定", null);
                builder.show();
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
}

public void showChangelog(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("更新日志");
                builder.setMessage("- [新增] 支持手动设置时间和消息\n- [移除] 脚本弹窗设置时间 已废弃\n- [优化] 现在手动发送消息 计入每日发送次数 如果手动发送了 则定时消息将不会自动发送\n- [修复] 定时线程的问题 可能导致无法发送定时消息\n\n反馈交流群：https://t.me/XiaoYu_Chat");
                builder.setPositiveButton("确定", null);
                builder.setCancelable(true);
                builder.show();
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
}

sendLike("2133115301",20);