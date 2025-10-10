
// 作 临江踏雨不返 海枫
// 发送 群管功能 以查看功能
// 部分写法源码 秩河 尹志平 群鹅 天啦噜

// 我知道你受欢迎 身边有很多人 但我希望你可以记住我

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 被丢弃一次的小朋友就会质疑所有人的爱

public void forbiddenOperation(String groupUin, String targetUin, int time) {
    try {
        forbidden(groupUin, targetUin, time);
    } catch (Throwable e) {
        error(e);
    }
}

// 没有结果的两个人为什么要安排他们相遇 为什么

public void kickOperation(String groupUin, String targetUin, boolean isBlack) {
    try {
        kick(groupUin, targetUin, isBlack);
    } catch (Throwable e) {
        error(e);
    }
}

// 性别让我侥幸接近你 性别让我没资格爱你.

public int getCurrentTheme() {
    try {
        int uiMode = context.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (uiMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            return AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        } else {
            return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        }
    } catch (Exception e) {
        return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    }
}

// 在错位的时空里，我们会不会相遇
void onCreateMenu(Object msg) {
    if (msg.IsGroup) {
        try {
            addMenuItem("踢", "kickMenuItem");
            addMenuItem("踢黑", "kickBlackMenuItem"); 
            addMenuItem("禁言", "forbiddenMenuItem");
        } catch (Exception e) {
        }
    }
}

public void kickMenuItem(Object msg) {
    if (!msg.IsGroup) {
        toast("只能在群聊中使用");
        return;
    }
    
    String groupUin = msg.GroupUin;
    String targetUin = msg.UserUin;
    String operatorUin = myUin;
    
    if (!isAdmin(groupUin, operatorUin)) {
        toast("需要管理员权限");
        return;
    }
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("确认踢出");
                builder.setMessage("确定要踢出 " + targetUin + " 吗？");
                
                builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        kickOperation(groupUin, targetUin, false);
                        toast("踢出成功");
                    }
                });
                
                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        toast("已取消");
                    }
                });
                
                builder.show();
            } catch (Exception e) {
                toast("显示对话框失败: " + e.toString());
            }
        }
    });
}

public void kickBlackMenuItem(Object msg) {
    if (!msg.IsGroup) {
        toast("只能在群聊中使用");
        return;
    }
    
    String groupUin = msg.GroupUin;
    String targetUin = msg.UserUin;
    String operatorUin = myUin;
    
    if (!isAdmin(groupUin, operatorUin)) {
        toast("需要管理员权限");
        return;
    }
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("确认踢黑");
                builder.setMessage("确定要踢出并拉黑 " + targetUin + " 吗？");
                
                builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        kickOperation(groupUin, targetUin, true);
                        toast("踢黑成功");
                    }
                });
                
                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        toast("已取消");
                    }
                });
                
                builder.show();
            } catch (Exception e) {
                toast("显示对话框失败: " + e.toString());
            }
        }
    });
}

private Map digitMap = new HashMap();
{
    digitMap.put('零', 0);
    digitMap.put('一', 1);
    digitMap.put('二', 2);
    digitMap.put('三', 3);
    digitMap.put('四', 4);
    digitMap.put('五', 5);
    digitMap.put('六', 6);
    digitMap.put('七', 7);
    digitMap.put('八', 8);
    digitMap.put('九', 9);
    digitMap.put('十', 10);
}

private Map unitMap = new HashMap();
{
    unitMap.put('十', 10);
    unitMap.put('百', 100);
    unitMap.put('千', 1000);
    unitMap.put('万', 10000);
}

private Pattern numberPattern = Pattern.compile("[零一二三四五六七八九十]?[十百千万]?");

public Integer chineseToInteger(String chineseNumber) {
    if (chineseNumber == null) return 0;
    Integer result = 0;
    Matcher matcher = numberPattern.matcher(chineseNumber);
    while (matcher.find()) {
        String match = matcher.group(0);
        if (match.length() == 2) {
            Integer digit = (Integer)digitMap.get(match.charAt(0));
            Integer unit = (Integer)unitMap.get(match.charAt(1));
            if (digit != null && unit != null) {
                result += digit * unit;
            }
        } else if (match.length() == 1) {
            if (unitMap.containsKey(match.charAt(0))) {
                Integer unit = (Integer)unitMap.get(match.charAt(0));
                if (unit != null) {
                    result *= unit;
                }
            } else if (digitMap.containsKey(match.charAt(0))) {
                Integer digit = (Integer)digitMap.get(match.charAt(0));
                if (digit != null) {
                    result += digit;
                }
            }
        }
    }
    if(chineseNumber.startsWith("十")){
        return 10+result;
    }
    return result;
}

public int getTimeFromMessage(Object msg){
    String content = msg.MessageContent;
    int lastSpaceIndex = content.lastIndexOf(" ");
    String timeString;
    if (lastSpaceIndex == -1) {
        timeString = content;
    } else {
        timeString = content.substring(lastSpaceIndex + 1);
    }
    timeString=timeString.trim();
    String digitPart="";
    if(timeString != null && !"".equals(timeString)){
        for(int i=0;i<timeString.length();i++){
            if(timeString.charAt(i)>=48 && timeString.charAt(i)<=57){
                digitPart +=timeString.charAt(i);
            }
        }
    }
    if (digitPart.isEmpty()) return 0;
    int time=Integer.parseInt(digitPart);  
    if(timeString.contains("天")){
        return time*60*60*24;
    }
    else if(timeString.contains("时") || timeString.contains("小时") ){
        return 60*60*time;
    }
    else if(timeString.contains("分") || timeString.contains("分钟") ){
        return 60*time;
    }
    return time;
}

public int getTimeInteger(Object msg,int time){
    String content = msg.MessageContent;
    int lastSpaceIndex = content.lastIndexOf(" ");
    String timeString;
    if (lastSpaceIndex == -1) {
        timeString = content;
    } else {
        timeString = content.substring(lastSpaceIndex + 1);
    }
    if(timeString.contains("天")){
        return time*60*60*24;
    }
    else if(timeString.contains("时") || timeString.contains("小时") ){
        return 60*60*time;
    }
    else if(timeString.contains("分") || timeString.contains("分钟") ){
        return 60*time;
    }	
    return time;
}

public boolean isAdmin(String groupUin, String userUin) {
    ArrayList groupList = getGroupList();
    for (Object groupInfo : groupList) {
        if (groupInfo.GroupUin.equals(groupUin)) {
            if (groupInfo.GroupOwner.equals(userUin)) {
                return true;
            }
            if (groupInfo.AdminList != null) {
                for (Object admin : groupInfo.AdminList) {
                    if (admin.equals(userUin)) {
                        return true;
                    }
                }
            }
        }
    }
    return false;
}

public void onMsg(Object msg){
    String content=msg.MessageContent;
    String senderQQ=msg.UserUin;
    String groupUin = msg.GroupUin;
    
    if(!msg.IsGroup) return;
    
    if(content.equals("群管功能")){
        String helpInfo = "群管功能菜单：\n" +
                        "禁言@某人 时间 - 禁言指定成员\n" +
                        "支持数字和中文时间单位\n" +
                        "长按消息可使用踢人菜单";
        sendMsg(groupUin, "", helpInfo);
        return;
    }
    
    if(!isAdmin(groupUin, senderQQ)) return;
    
    if(msg.mAtList != null && msg.mAtList.size() > 0){
        if(content.matches(".*[0-9]+(天|分|时|小时|分钟|秒).*")){
            int forbiddenTime = getTimeFromMessage(msg);
            if(forbiddenTime > 2592000){
                sendMsg(groupUin,"","请控制在30天以内");
                return;
            }else if(forbiddenTime > 0){
                for(String user:msg.mAtList){
                    forbiddenOperation(groupUin,user,forbiddenTime);
                }
                toast("禁言成功");
                return;
            }
        }
        
        if(content.matches(".*[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒).*")){
            int spaceIndex = content.lastIndexOf(" ");
            String timePart;
            if (spaceIndex == -1) {
                timePart = content;
            } else {
                timePart = content.substring(spaceIndex + 1);
            }
            String digitText=timePart.replaceAll("[天分时小时分钟秒]","");
            int time=chineseToInteger(digitText);
            int forbiddenTime = getTimeInteger(msg,time);
            if(forbiddenTime > 2592000){
                sendMsg(groupUin,"","禁言时间太长无法禁言");
                return;
            }else if(forbiddenTime > 0){
                for(String user:msg.mAtList){
                    forbiddenOperation(groupUin,user,forbiddenTime);
                }
                toast("禁言成功");
                return;
            }
        }
        
        if(content.matches(".*[零一二三四五六七八九十百千万]+.*")){
            int spaceIndex = content.lastIndexOf(" ");
            String timePart;
            if (spaceIndex == -1) {
                timePart = content;
            } else {
                timePart = content.substring(spaceIndex + 1);
            }
            String digitText = timePart.replaceAll("[^零一二三四五六七八九十百千万]", "");
            int time=chineseToInteger(digitText);
            if(time > 0){
                for(String user:msg.mAtList){
                    forbiddenOperation(groupUin,user,time*60);
                }
                toast("禁言成功");
                return;
            }
        }
    }
}

// 电影里总是仁慈  让错过的人再次相遇

public boolean checkProtection(String groupUin, String targetUin, String operation) {
    return false;
}

public String getMemberDisplayName(String groupUin, String userUin) {
    return getMemberName(groupUin, userUin);
}

public void forbiddenMenuItem(Object msg) {
    if (!msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    if (checkProtection(groupUin, targetUin, "禁言")) return;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("设置禁言时间");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + getMemberDisplayName(groupUin, targetUin) + "(" + targetUin + ")");
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
                                forbiddenOperation(groupUin, targetUin, time);
                                
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
                                
                                toast("已禁言 " + getMemberDisplayName(groupUin, targetUin) + " " + timeDisplay);
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

// 兜起这阵风 赴千千万万个春