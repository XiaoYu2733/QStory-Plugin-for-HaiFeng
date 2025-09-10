
// 作 海枫

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

ArrayList targetFriends = new ArrayList();
ArrayList fireWordsList = new ArrayList();

int sendHour = 8;
int sendMinute = 0;
long lastClickTime = 0;

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
    
    String savedWords = getString("KeepFire", "fireWords", "");
    if (!savedWords.isEmpty()) {
        String[] words = savedWords.split(",");
        for (String word : words) {
            fireWordsList.add(word);
        }
    } else {
        fireWordsList.add("🔥");
        fireWordsList.add("续火");
        fireWordsList.add("火苗");
        fireWordsList.add("保持火花");
        fireWordsList.add("火火火");
    }
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
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < fireWordsList.size(); i++) {
        if (i > 0) sb.append(",");
        sb.append(fireWordsList.get(i));
    }
    putString("KeepFire", "fireWords", sb.toString());
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
        
        if(currentHour == sendHour && currentMinute == sendMinute && !today.equals(getString("KeepFire","lastSendDate"))){
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

public void configureFriends(String g,String u,int t){
    Activity activity = getActivity();
    if (activity == null) return;
    
    ArrayList allFriends = getFriendList();
    if (allFriends == null || allFriends.isEmpty()) {
        toast("未添加任何好友");
        return;
    }
    
    final ArrayList friendNames = new ArrayList();
    final ArrayList friendUins = new ArrayList();
    for (int i = 0; i < allFriends.size(); i++) {
        Object friend = allFriends.get(i);
        String name = friend.remark.isEmpty() ? friend.name : friend.remark;
        String uin = friend.uin;
        friendNames.add(name + " (" + uin + ")");
        friendUins.add(uin);
    }
    
    final boolean[] checkedItems = new boolean[friendUins.size()];
    for (int i = 0; i < friendUins.size(); i++) {
        checkedItems[i] = targetFriends.contains(friendUins.get(i));
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("选择续火好友");
            
            builder.setMultiChoiceItems(
                (String[])friendNames.toArray(new String[0]), 
                checkedItems, 
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                    }
                }
            );
            
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    targetFriends.clear();
                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {
                            targetFriends.add(friendUins.get(i));
                        }
                    }
                    saveFriends();
                    toast("已选择" + targetFriends.size() + "位好友");
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
            StringBuilder wordsList = new StringBuilder();
            for (int i = 0; i < fireWordsList.size(); i++) {
                if (wordsList.length() > 0) wordsList.append(",");
                wordsList.append(fireWordsList.get(i));
            }
            
            final EditText input = new EditText(activity);
            input.setText(wordsList.toString());
            input.setHint("输入续火词，用逗号分隔");
            
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
                    for (String word : wordsArray) {
                        String trimmed = word.trim();
                        if (!trimmed.isEmpty()) {
                            fireWordsList.add(trimmed);
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
        }
    });
}

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("续火脚本更新日志");
            builder.setMessage("更新日志\n" +
                    "- 更改 弹窗勾选好友\n" +
                    "- 新增 好友选择对话框，可视化选择好友\n" +
                    "- 优化 界面使用现代化主题\n" +
                    "- 新增 支持多个续火词随机发送\n" +
                    "- 添加 防刷屏机制（1分钟冷却时间）\n" +
                    "- 优化 发送间隔增加到5秒更安全\n" +
                    "- 优化 冷却提示精确到秒\n\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

toast("好友续火花Java加载成功");
toast("每天" + sendHour + ":" + (sendMinute < 10 ? "0" + sendMinute : sendMinute) + "自动续火");
toast("当前好友数: " + targetFriends.size());
toast("当前续火词数: " + fireWordsList.size());

sendLike("2133115301",20);