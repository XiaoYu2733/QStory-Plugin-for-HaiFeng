
// 海枫

// 世界好吵 我只想在沉默里被拥抱着

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.EditText;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import android.graphics.drawable.GradientDrawable;
import android.content.res.Configuration;

public void unifiedForbidden(String groupUin, String userUin, int time) {
    try {
        forbidden(groupUin, userUin, time);
    } catch (Throwable e) {
        shutUp(groupUin, userUin, time);
    }
}

public void unifiedKick(String groupUin, String userUin, boolean isBlack) {
    try {
        kick(groupUin, userUin, isBlack);
    } catch (Throwable e) {
        kickGroup(groupUin, userUin, isBlack);
    }
}

void onCreateMenu(Object msg) {
    if (msg.IsGroup) {
        try {
            String groupUin = msg.GroupUin;
            String targetUin = msg.UserUin;
            
            if (targetUin.equals(myUin)) {
                return;
            }
            
            GroupInfo groupInfo = getGroupInfo(groupUin);
            if (groupInfo != null && groupInfo.IsOwnerOrAdmin) {
                addMenuItem("快捷群管", "quickManageMenuItem");
            }
            
        } catch (Exception e) {
        }
    }
}

public void quickManageMenuItem(final Object msg) {
    if (!msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                GroupMemberInfo myInfo = getMemberInfo(groupUin, myUin);
                if (myInfo == null) return;
                
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("快捷群管 - " + getMemberName(groupUin, targetUin) + "(" + targetUin + ")");
                
                final List<String> items = new CopyOnWriteArrayList<>();
                final List<Runnable> actions = new CopyOnWriteArrayList<>();
                
                if (myInfo.IsOwner || myInfo.IsAdmin) {
                    items.add("禁言");
                    actions.add(new Runnable() {
                        public void run() {
                            forbiddenMenuItem(msg);
                        }
                    });
                    
                    items.add("踢出");
                    actions.add(new Runnable() {
                        public void run() {
                            kickMenuItem(msg);
                        }
                    });
                    
                    items.add("踢黑");
                    actions.add(new Runnable() {
                        public void run() {
                            kickBlackMenuItem(msg);
                        }
                    });
                }
                
                if (myInfo.IsOwner) {
                    items.add("设置头衔");
                    actions.add(new Runnable() {
                        public void run() {
                            setTitleMenuItem(msg);
                        }
                    });
                }
                
                if (items.isEmpty()) {
                    toast("没有可用的操作权限");
                    return;
                }
                
                builder.setItems(items.toArray(new String[0]), new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        if (which >= 0 && which < actions.size()) {
                            actions.get(which).run();
                        }
                    }
                });
                
                builder.setNegativeButton("取消", null);
                builder.show();
                
            } catch (Exception e) {
                toast("打开快捷群管失败: " + e.getMessage());
            }
        }
    });
}

public void forbiddenMenuItem(Object msg) {
    if (!msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("设置禁言时间");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + getMemberName(groupUin, targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入禁言时间（秒）");
            inputEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            inputEditText.setHintTextColor(Color.GRAY);
            inputEditText.setBackgroundResource(android.R.drawable.edit_text);
            layout.addView(inputEditText);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定禁言", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    String input = inputEditText.getText().toString().trim();
                    if (!input.isEmpty()) {
                        try {
                            int time = Integer.parseInt(input);
                            if (time > 0) {
                                if (time > 2592000) {
                                    toast("禁言时间不能超过30天");
                                    return;
                                }
                                unifiedForbidden(groupUin, targetUin, time);
                                
                                String timeDisplay;
                                if (time < 60) {
                                    timeDisplay = time + "秒";
                                } else if (time < 3600) {
                                    timeDisplay = (time / 60) + "分钟";
                                } else if (time < 86400) {
                                    timeDisplay = (time / 3600) + "小时";
                                } else {
                                    timeDisplay = (time / 86400) + "天";
                                }
                                
                                toast("已禁言 " + getMemberName(groupUin, targetUin) + " " + timeDisplay);
                            } else {
                                toast("请输入大于0的数字");
                            }
                        } catch (NumberFormatException e) {
                            toast("请输入有效的数字");
                        }
                    } else {
                        toast("请输入禁言时间");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

sendLike("2133115301",20);

public void kickMenuItem(Object msg) {
    if (!msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("确认踢出");
            builder.setMessage("确定要踢出 " + getMemberName(groupUin, targetUin) + "(" + targetUin + ") 吗？");
            
            builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    unifiedKick(groupUin, targetUin, false);
                    toast("踢出成功");
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void kickBlackMenuItem(Object msg) {
    if (!msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("确认踢黑");
            builder.setMessage("确定要踢出并拉黑 " + getMemberName(groupUin, targetUin) + "(" + targetUin + ") 吗？");
            
            builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    unifiedKick(groupUin, targetUin, true);
                    toast("踢黑成功");
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void setTitleMenuItem(Object msg) {
    if (!msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("设置头衔");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + getMemberName(groupUin, targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入头衔内容");
            inputEditText.setHintTextColor(Color.GRAY);
            inputEditText.setBackgroundResource(android.R.drawable.edit_text);
            layout.addView(inputEditText);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定设置", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    String input = inputEditText.getText().toString().trim();
                    if (!input.isEmpty()) {
                        setTitle(groupUin, targetUin, input);
                        toast("已为 " + getMemberName(groupUin, targetUin) + " 设置头衔: " + input);
                    } else {
                        toast("请输入头衔内容");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public int get_time(Object msg){
    String content = msg.MessageContent;
    int lastSpace = content.lastIndexOf(" ");
    String date = content.substring(lastSpace + 1);
    date = date.trim();
    
    String t = "";
    for(int i = 0; i < date.length(); i++){
        if(date.charAt(i) >= '0' && date.charAt(i) <= '9'){
            t += date.charAt(i);
        }
    }
    
    if(t.isEmpty()) return 0;
    
    int time = Integer.parseInt(t);
    if(date.contains("天")){
        return time * 86400;
    } else if(date.contains("小时") || date.contains("时")){
        return time * 3600;
    } else if(date.contains("分钟") || date.contains("分")){
        return time * 60;
    }
    return time;
}

public boolean isAdmin(String groupUin, String userUin) {
    try {
        GroupMemberInfo memberInfo = getMemberInfo(groupUin, userUin);
        return memberInfo != null && (memberInfo.IsOwner || memberInfo.IsAdmin);
    } catch (Exception e) {
        return false;
    }
}

public void onMsg(Object msg){
    if (msg == null) return;
    
    String content = msg.MessageContent;
    String qq = msg.UserUin;
    String groupUin = msg.GroupUin;
    
    ArrayList mAtListCopy = new ArrayList();
    if (msg.mAtList != null) {
        mAtListCopy.addAll(msg.mAtList);
    }
    
    boolean isAdminUser = isAdmin(groupUin, qq);
    
    if(msg.UserUin.equals(myUin) || isAdminUser){
        if(content.matches("^禁言 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)$") && mAtListCopy.size() >= 1){
            int banTime = get_time(msg);
            if(banTime > 2592000){
                sendReply(groupUin, msg, "时间太长无法禁言");
                return;
            } else if(banTime > 0){
                for(int i = 0; i < mAtListCopy.size(); i++){
                    String u = (String) mAtListCopy.get(i);
                    unifiedForbidden(groupUin, u, banTime);
                }
                return;
            }
        }
        
        if(content.matches("^禁 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)$") && mAtListCopy.size() >= 1){
            int banTime = get_time(msg);
            if(banTime > 2592000){
                sendReply(groupUin, msg, "时间太长无法禁言");
                return;
            } else if(banTime > 0){
                for(int i = 0; i < mAtListCopy.size(); i++){
                    String u = (String) mAtListCopy.get(i);
                    unifiedForbidden(groupUin, u, banTime);
                }
                return;
            }
        }
        
        if((content.equals("禁言") || content.equals("禁")) && mAtListCopy.size() >= 1){
            int time = 60;
            for(int i = 0; i < mAtListCopy.size(); i++){
                String u = (String) mAtListCopy.get(i);
                unifiedForbidden(groupUin, u, time);
            }
            return;
        }
        
        if(content.equals("解禁") && mAtListCopy.size() >= 1){
            for(int i = 0; i < mAtListCopy.size(); i++){
                String u = (String) mAtListCopy.get(i);
                unifiedForbidden(groupUin, u, 0);
            }
            return;
        }
        
        if(content.equals("全员禁言")){
            unifiedForbidden(groupUin, "", 1);
            return;
        }
        
        if(content.equals("解除全员禁言")){
            unifiedForbidden(groupUin, "", 0);
            return;
        }
        
        if(content.equals("踢出") && mAtListCopy.size() >= 1){
            for(int i = 0; i < mAtListCopy.size(); i++){
                String u = (String) mAtListCopy.get(i);
                unifiedKick(groupUin, u, false);
            }
            sendMsg(groupUin, "", "踢出成功");
            return;
        }
        
        if(content.equals("踢黑") && mAtListCopy.size() >= 1){
            for(int i = 0; i < mAtListCopy.size(); i++){
                String u = (String) mAtListCopy.get(i);
                unifiedKick(groupUin, u, true);
            }
            sendMsg(groupUin, "", "已踢黑");
            return;
        }
    }
}