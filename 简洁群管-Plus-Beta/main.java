
// 海枫

// 原来心情不好真的说不出来话 就像现在这样 只能发呆

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

public boolean isDarkMode() {
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
}

public String getBackgroundColor() {
    return isDarkMode() ? "#CC1E1E1E" : "#CCFFFFFF";
}

public String getTextColor() {
    return isDarkMode() ? "#E0E0E0" : "#333333";
}

public int c(float f) {
    return (int) (((((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f) + 0.5f);
}

public GradientDrawable getShape(String color, int cornerRadius, int alpha) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(color));
    shape.setCornerRadius(cornerRadius);
    shape.setAlpha(alpha);
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

public void Toasts(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    String bgColor = getBackgroundColor();
                    String textColor = getTextColor();
                    
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    
                    int paddingHorizontal = c(18);
                    int paddingVertical = c(12);
                    linearLayout.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                    
                    linearLayout.setBackground(getShape(bgColor, c(12), 230));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(textColor));
                    textView.setTextSize(14.5f);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
                    linearLayout.addView(textView);
                    linearLayout.setGravity(Gravity.CENTER);
                    
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.TOP, 0, c(80));
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(linearLayout);
                    toast.show();
                } else {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        }
    });
}

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

// 哪有什么心脉受损 就是能力不够 解决不了问题 接受不了结果

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

// 别人再好与我无关 你再不好我都喜欢

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
                    Toasts("没有可用的操作权限");
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
                Toasts("打开快捷群管失败: " + e.getMessage());
            }
        }
    });
}

// 我只要你 我不要别人 只想和你一直在一起

// 喜欢你 跟你在一起 是一件很幸福的事情 我们要一直在一起好不好

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
                                    Toasts("禁言时间不能超过30天");
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
                                
                                Toasts("已禁言 " + getMemberName(groupUin, targetUin) + " " + timeDisplay);
                            } else {
                                Toasts("请输入大于0的数字");
                            }
                        } catch (NumberFormatException e) {
                            Toasts("请输入有效的数字");
                        }
                    } else {
                        Toasts("请输入禁言时间");
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
                    Toasts("踢出成功");
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
                    Toasts("踢黑成功");
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
                        Toasts("已为 " + getMemberName(groupUin, targetUin) + " 设置头衔: " + input);
                    } else {
                        Toasts("请输入头衔内容");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
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
        if(content.matches("^@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)+$") && mAtListCopy.size() >= 1){
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
        
        if((content.startsWith("禁") || content.startsWith("禁言")) && mAtListCopy.size() >= 1){
            int time = 60;
            if(content.contains("天")) time = 86400;
            else if(content.contains("小时") || content.contains("时")) time = 3600;
            else if(content.contains("分钟") || content.contains("分")) time = 60;
            
            for(int i = 0; i < mAtListCopy.size(); i++){
                String u = (String) mAtListCopy.get(i);
                unifiedForbidden(groupUin, u, time);
            }
            return;
        }
        
        if(content.startsWith("解") && mAtListCopy.size() >= 1){
            for(int i = 0; i < mAtListCopy.size(); i++){
                String u = (String) mAtListCopy.get(i);
                unifiedForbidden(groupUin, u, 0);
            }
            return;
        }
        
        if(content.equals("禁") && mAtListCopy.size() == 0){
            unifiedForbidden(groupUin, "", 1);
            return;
        }
        
        if(content.equals("解") && mAtListCopy.size() == 0){
            unifiedForbidden(groupUin, "", 0);
            return;
        }
        
        // 踢出
        if(content.startsWith("踢") && mAtListCopy.size() >= 1 && !content.startsWith("踢黑")){
            for(int i = 0; i < mAtListCopy.size(); i++){
                String u = (String) mAtListCopy.get(i);
                unifiedKick(groupUin, u, false);
            }
            sendMsg(groupUin, "", "踢出成功");
            return;
        }
        
        if(content.startsWith("踢黑") && mAtListCopy.size() >= 1){
            for(int i = 0; i < mAtListCopy.size(); i++){
                String u = (String) mAtListCopy.get(i);
                unifiedKick(groupUin, u, true);
            }
            sendMsg(groupUin, "", "已踢黑");
            return;
        }
    }
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

// 你不必担忧 我定会用最初的心 陪你走最远的路