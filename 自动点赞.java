
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
import android.widget.ArrayAdapter;
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

addItem("立即点赞好友","likeNow");
addItem("配置点赞好友","configureFriends");

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
                listView.setItemChecked(i, selectedFriends.contains(uin));
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
                        listView.setItemChecked(i, selectedFriends.contains(uin));
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
                    selectedFriends.clear();
                    for (int i = 0; i < displayedFriendUins.size(); i++) {
                        if (listView.isItemChecked(i)) {
                            selectedFriends.add(displayedFriendUins.get(i));
                        }
                    }
                    saveSelectedFriends();
                    toast("已选择" + selectedFriends.size() + "位点赞好友");
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            builder.show();
        }
    });
}

sendLike("2133115301",20);