import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;

ArrayList selectedGroups = new ArrayList();
int forbidHour = 23;
int forbidMinute = 57;
int unforbidHour = 8;
int unforbidMinute = 0;
String lastForbidDate = "";
String lastUnforbidDate = "";

void initConfig() {
    String savedGroups = getString("ForbiddenTask", "selectedGroups", "");
    if (!savedGroups.isEmpty()) {
        String[] groups = savedGroups.split(",");
        for (int i = 0; i < groups.length; i++) {
            if (!groups[i].isEmpty()) selectedGroups.add(groups[i]);
        }
    }

    lastForbidDate = getString("ForbiddenTask", "lastForbidDate", "");
    lastUnforbidDate = getString("ForbiddenTask", "lastUnforbidDate", "");
    forbidHour = getInt("ForbiddenTask", "forbidHour", 23);
    forbidMinute = getInt("ForbiddenTask", "forbidMinute", 57);
    unforbidHour = getInt("ForbiddenTask", "unforbidHour", 8);
    unforbidMinute = getInt("ForbiddenTask", "unforbidMinute", 0);
}

void saveSelectedGroups() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < selectedGroups.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append(selectedGroups.get(i));
    }
    putString("ForbiddenTask", "selectedGroups", sb.toString());
}

void onMsg(Object msg) {
    String text = msg.MessageContent;
    String groupUin = msg.GroupUin;
    
    if (!selectedGroups.contains(groupUin)) return;
    
    if (text.startsWith("设置定时禁言时间")) {
        String timeStr = text.replace("设置定时禁言时间", "").trim();
        try {
            String[] parts = timeStr.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                sendMsg(groupUin, "", "时间格式错误，请使用HH:MM格式");
                return;
            }
            
            forbidHour = hour;
            forbidMinute = minute;
            putInt("ForbiddenTask", "forbidHour", hour);
            putInt("ForbiddenTask", "forbidMinute", minute);
            
            sendMsg(groupUin, "", "已设置定时禁言时间为: " + timeStr);
        } catch (Exception e) {
            sendMsg(groupUin, "", "时间格式错误，请使用HH:MM格式");
        }
    } else if (text.startsWith("设置定时解禁时间")) {
        String timeStr = text.replace("设置定时解禁时间", "").trim();
        try {
            String[] parts = timeStr.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                sendMsg(groupUin, "", "时间格式错误，请使用HH:MM格式");
                return;
            }
            
            unforbidHour = hour;
            unforbidMinute = minute;
            putInt("ForbiddenTask", "unforbidHour", hour);
            putInt("ForbiddenTask", "unforbidMinute", minute);
            
            sendMsg(groupUin, "", "已设置定时解禁时间为: " + timeStr);
        } catch (Exception e) {
            sendMsg(groupUin, "", "时间格式错误，请使用HH:MM格式");
        }
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
                toast("定时器错误:"+e.getMessage());
            }
        }
    }
    
    void checkAndExecute(Calendar now){
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        
        if (selectedGroups.isEmpty()) return;
        
        if(currentHour == forbidHour && currentMinute == forbidMinute && !today.equals(lastForbidDate)){
            executeForbiddenAll();
            lastForbidDate = today;
            putString("ForbiddenTask", "lastForbidDate", today);
            toast("已执行定时禁言");
        }
        
        if(currentHour == unforbidHour && currentMinute == unforbidMinute && !today.equals(lastUnforbidDate)){
            executeUnforbiddenAll();
            lastUnforbidDate = today;
            putString("ForbiddenTask", "lastUnforbidDate", today);
            toast("已执行定时解禁");
        }
    }
}).start();

void executeForbiddenAll(){
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < selectedGroups.size(); i++){
                String group = (String)selectedGroups.get(i);
                try{
                    forbidden(group, "", 31536000);
                    Thread.sleep(3000);
                }catch(Exception e){
                    toast(group+"禁言失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

void executeUnforbiddenAll(){
    new Thread(new Runnable(){
        public void run(){
            for(int i = 0; i < selectedGroups.size(); i++){
                String group = (String)selectedGroups.get(i);
                try{
                    forbidden(group, "", 0);
                    Thread.sleep(3000);
                }catch(Exception e){
                    toast(group+"解禁失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

long lastClickTime = 0;

addItem("立即全员禁言","forbidNow");
addItem("立即全员解禁","unforbidNow");
addItem("配置禁言群组","selectGroups");
addItem("脚本更新日志","showChangelog");

public void forbidNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    executeForbiddenAll();
    toast("正在执行全员禁言");
}

public void unforbidNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    executeUnforbiddenAll();
    toast("正在执行全员解禁");
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
                builder.setTitle("选择群组");
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
                        toast("已选择" + selectedGroups.size() + "个群组");
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

public void showChangelog(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("更新日志");
                builder.setMessage("- [新增] 自己勾选群聊 如果不勾选 部分指令不能用\n- [新增] 指令设置定时禁言时间 设置定时解禁时间\n- [其他] 如果勾选了群聊没有勾选对应群聊 则使用脚本默认的定时禁言时间23:57和定时解禁08:00\n\n反馈交流群：https://t.me/XiaoYu_Chat");
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