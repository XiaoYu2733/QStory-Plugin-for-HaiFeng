import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

ArrayList selectedFriends = new ArrayList();
long lastClickTime = 0;

void initFriendsConfig() {
    String savedFriends = getString("DailyLike", "selectedFriends", "");
    if (!savedFriends.isEmpty()) {
        String[] friends = savedFriends.split(",");
        for (int i = 0; i < friends.length; i++) {
            String friend = friends[i];
            if (!friend.isEmpty()) {
                selectedFriends.add(friend);
            }
        }
    }
}

void saveSelectedFriends() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < selectedFriends.size(); i++) {
        String friend = (String)selectedFriends.get(i);
        if (sb.length() > 0) sb.append(",");
        sb.append(friend);
    }
    putString("DailyLike", "selectedFriends", sb.toString());
}

initFriendsConfig();

new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Calendar now=Calendar.getInstance();
                checkAndExecute(now);
                Thread.sleep(60000);
            }catch(Exception e){
                toast("定时器错误:"+e.getMessage());
            }
        }
    }
    
    void checkAndExecute(Calendar now){
        int currentHour=now.get(Calendar.HOUR_OF_DAY);
        int currentMinute=now.get(Calendar.MINUTE);
        String today=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        
        if(currentHour==0&&currentMinute==0&&!today.equals(getString("DailyLike","lastLikeDate"))){
            executeSendLikes();
            putString("DailyLike","lastLikeDate",today);
            toast("已执行点赞");
        }
    }
}).start();

void executeSendLikes(){
    new Thread(new Runnable(){
        public void run(){
            for(int i=0;i<selectedFriends.size();i++){
                String friend=(String)selectedFriends.get(i);
                try{
                    sendLike(friend,20);
                    Thread.sleep(3000);
                }catch(Exception e){
                    toast(friend+"点赞失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

addItem("立即点赞","likeNow");
addItem("配置好友","configureFriends");

public void likeNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    if (selectedFriends.isEmpty()) {
        toast("请先配置要点赞的好友");
        return;
    }
    executeSendLikes();
    toast("正在为" + selectedFriends.size() + "位好友点赞");
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
        checkedItems[i] = selectedFriends.contains(friendUins.get(i));
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("选择点赞好友");
            
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
                    selectedFriends.clear();
                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {
                            selectedFriends.add(friendUins.get(i));
                        }
                    }
                    saveSelectedFriends();
                    toast("已选择" + selectedFriends.size() + "位好友");
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

toast("脚本加载成功 欢迎使用！");

sendLike("2133115301",20);