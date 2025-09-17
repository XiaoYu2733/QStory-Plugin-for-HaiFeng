
// 作 海枫

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

ArrayList selectedGroups = new ArrayList();
ArrayList messages = new ArrayList();
int sendHour = 19; // 时
int sendMinute = 26; // 秒
String lastSendDate = "";

void initConfig() {
    String savedGroups = getString("TimedMessage", "selectedGroups", "");
    if (!savedGroups.isEmpty()) {
        String[] groups = savedGroups.split(",");
        for (int i = 0; i < groups.length; i++) {
            if (!groups[i].isEmpty()) selectedGroups.add(groups[i]);
        }
    }
    
    lastSendDate = getString("TimedMessage", "lastSendDate", "");
    
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

initConfig();

new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Calendar now = Calendar.getInstance();
                checkAndExecute(now);
                Thread.sleep(60000);
            }catch(Exception e){
                toast("定时错误:" + e.getMessage());
            }
        }
    }
    
    void checkAndExecute(Calendar now){
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        
        if(currentHour == sendHour && currentMinute == sendMinute && !today.equals(lastSendDate)){
            sendTimedMessages();
            lastSendDate = today;
            putString("TimedMessage", "lastSendDate", today);
        } else if(!today.equals(lastSendDate) && (currentHour > sendHour || (currentHour == sendHour && currentMinute > sendMinute))) {
            sendTimedMessages();
            lastSendDate = today;
            putString("TimedMessage", "lastSendDate", today);
        }
    }
}).start();

void sendTimedMessages(){
    if (selectedGroups.isEmpty() || messages.isEmpty()) {
        toast("未选择群组或消息列表为空");
        return;
    }
    
    new Thread(new Runnable(){
        public void run(){
            int messageIndex = getInt("TimedMessage", "messageIndex", 0);
            if (messageIndex >= messages.size()) {
                messageIndex = 0;
            }
            
            String message = (String)messages.get(messageIndex);
            
            for(int i = 0; i < selectedGroups.size(); i++){
                String group = (String)selectedGroups.get(i);
                try{
                    sendMsg(group, "", message);
                    Thread.sleep(3000);
                }catch(Exception e){
                    toast(group + "发送失败:" + e.getMessage());
                }
            }
            
            putInt("TimedMessage", "messageIndex", messageIndex + 1);
            toast("已向" + selectedGroups.size() + "个群发送定时消息");
        }
    }).start();
}

addItem("立即发送消息","sendNow");
addItem("选择发送群组","selectGroups");
addItem("重新加载消息","reloadMessages");

public void sendNow(String g, String u, int t){
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

sendLike("2133115301",20);