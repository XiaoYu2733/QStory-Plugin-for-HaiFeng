
// 你会遇到比我更好的人 因为你从未觉得我好

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.content.Context;
import android.content.res.Configuration;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.app.Activity;

String MD3_PRIMARY = "#6750A4";
String MD3_ON_PRIMARY = "#FFFFFF";
String MD3_SURFACE = "#FEF7FF";
String MD3_ON_SURFACE = "#1C1B1F";
String MD3_SURFACE_VARIANT = "#E7E0EC";
String MD3_ON_SURFACE_VARIANT = "#49454F";
String MD3_DARK_SURFACE = "#1C1B1F";
String MD3_DARK_ON_SURFACE = "#F4EFF4";
String MD3_DARK_SURFACE_VARIANT = "#49454F";
String MD3_DARK_ON_SURFACE_VARIANT = "#CAC4D0";

public void unifiedForbidden(String groupUin, String userUin, int time) {
    try {
        forbidden(groupUin, userUin, time);
    } catch (Throwable e) {
        try {
            shutUp(groupUin, userUin, time);
        } catch (Throwable e2) {
        }
    }
}

public void unifiedKick(String groupUin, String userUin, boolean isBlack) {
    try {
        kick(groupUin, userUin, isBlack);
    } catch (Throwable e) {
        try {
            kickGroup(groupUin, userUin, isBlack);
        } catch (Throwable e2) {
        }
    }
}

public ArrayList safeCopyList(ArrayList original) {
    if (original == null) {
        return new ArrayList();
    }
    try {
        return new ArrayList(original);
    } catch (Exception e) {
        return new ArrayList();
    }
}

public ArrayList unifiedGetForbiddenList(String groupUin) {
    try {
        ArrayList result = getForbiddenList(groupUin);
        return safeCopyList(result);
    } catch (Throwable e) {
        try {
            ArrayList result = getProhibitList(groupUin);
            return safeCopyList(result);
        } catch (Throwable e2) {
            return new ArrayList();
        }
    }
}

public ArrayList 禁言组(String groupUin) {
    ArrayList list = unifiedGetForbiddenList(groupUin);
    if (list == null || list.size() == 0) {
        return new ArrayList();
    }
    
    ArrayList uinList = new ArrayList();
    ArrayList listCopy = safeCopyList(list);
    for (int i = 0; i < listCopy.size(); i++) {
        Object item = listCopy.get(i);
        if (item != null) {
            try {
                java.lang.reflect.Field userUinField = item.getClass().getDeclaredField("UserUin");
                userUinField.setAccessible(true);
                Object uin = userUinField.get(item);
                if (uin != null) {
                    uinList.add(uin.toString());
                }
            } catch (Exception e) {
            }
        }
    }
    return uinList;
}

public String 禁言组文本(String qun) {
    ArrayList st = unifiedGetForbiddenList(qun);
    ArrayList t = new ArrayList();
    int i = 1;
    
    if (st != null && st.size() > 0) {
        ArrayList stListCopy = safeCopyList(st);
        for (Object b : stListCopy) {
            try {
                t.add(i + "." + b.UserName + "(" + b.UserUin + ")");
                i++;
            } catch (Exception e) {
            }
        }
    }
    
    String r = t.toString();
    String s = r.replace(" ", "");
    String g = s.replace(",", "\n");
    String k = g.replace("[", "");
    String y = k.replace("]", "");
    return y + "\n输入 解禁+序号快速解禁\n输入 踢/踢黑+序号 可快速踢出\n输入全禁可禁言30天\n输入#踢禁言 可踢出上述所有人";
}

public int getCurrentTheme() {
    try {
        Context context = getActivity();
        if (context == null) return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
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

private Map Arab2Chinese = new ConcurrentHashMap();
{
    Arab2Chinese.put('零', 0);
    Arab2Chinese.put('一', 1);
    Arab2Chinese.put('二', 2);
    Arab2Chinese.put('三', 3);
    Arab2Chinese.put('四', 4);
    Arab2Chinese.put('五', 5);
    Arab2Chinese.put('六', 6);
    Arab2Chinese.put('七', 7);
    Arab2Chinese.put('八', 8);
    Arab2Chinese.put('九', 9);
    Arab2Chinese.put('十', 10);
}

private Map UnitMap = new ConcurrentHashMap();
{
    UnitMap.put('十', 10);
    UnitMap.put('百', 100);
    UnitMap.put('千', 1000);
    UnitMap.put('万', 10000);
}

private Pattern pattern = Pattern.compile("[零一二三四五六七八九十]?[十百千万]?");

public Integer CN_zh_int(String chinese) {
    if (chinese == null) return 0;
    try {
        Integer result = 0;
        Matcher matcher = pattern.matcher(chinese);
        while (matcher.find()) {
            String res = matcher.group(0);
            if (res.length() == 2) {
                Integer num = (Integer)Arab2Chinese.get(res.charAt(0));
                Integer unit = (Integer)UnitMap.get(res.charAt(1));
                if (num != null && unit != null) {
                    result += num * unit;
                }
            } else if (res.length() == 1) {
                if (UnitMap.containsKey(res.charAt(0))) {
                    Integer unit = (Integer)UnitMap.get(res.charAt(0));
                    if (unit != null) {
                        result *= unit;
                    }
                } else if (Arab2Chinese.containsKey(res.charAt(0))) {
                    Integer num = (Integer)Arab2Chinese.get(res.charAt(0));
                    if (num != null) {
                        result += num;
                    }
                }
            }
        }
        if(chinese.startsWith("十")){
            return 10+result;
        }
        return result;
    } catch (Exception e) {
        return 0;
    }
}

public boolean atMe(Object msg){
    if (msg == null || msg.mAtList == null || msg.mAtList.size() == 0)
        return false;
    ArrayList atListCopy = safeCopyList(msg.mAtList);
    for (int i = 0; i < atListCopy.size(); i++) {
        String to_at = (String) atListCopy.get(i);
        if (to_at != null && to_at.equals(myUin))
            return true;
    }
    return false;
}

public String 论(String u,String a,String b){
    if (u == null) return "";
    return u.replace(a,b);
}

public String 名(String uin){
    if (uin == null) return "未知";
    try{
        Object card=GetCard(uin);
        if(card != null && card.strNick != null){
            return card.strNick;
        }
        else{
            return getMemberName("", uin);
        }
    }catch(Exception e){
        return "未知";
    }
}

public String 组名(ArrayList a){
    if (a == null) return "";
    ArrayList list = new ArrayList();
    ArrayList aCopy = safeCopyList(a);
    for(int i = 0; i < aCopy.size(); i++) {
        Object uin = aCopy.get(i);
        if (uin != null) {
            list.add(名(uin.toString())+"("+uin.toString()+")");
        }
    }
    return list.toString().replace(",","\n");
}

public boolean isAdmin(String GroupUin, String UserUin) {
    if (GroupUin == null || UserUin == null) return false;
    ArrayList groupList = getGroupList();
    ArrayList groupListCopy = safeCopyList(groupList);
    for (int i = 0; i < groupListCopy.size(); i++) {
        Object groupInfo = groupListCopy.get(i);
        if (groupInfo != null && groupInfo.GroupUin != null && groupInfo.GroupUin.equals(GroupUin)) {
            return groupInfo.GroupOwner != null && groupInfo.GroupOwner.equals(UserUin) || 
                (groupInfo.AdminList != null && groupInfo.AdminList.contains(UserUin));
        }
    }
    return false;
}

public int get_time_int(Object msg,int time){
    if (msg == null || msg.MessageContent == null) return 0;
    int datu = msg.MessageContent.lastIndexOf(" ");
    if (datu == -1) return 0;
    String date=msg.MessageContent.substring(datu +1); 
    if(date.contains("天")){
        return time*60*60*24;
    }
    else if(date.contains("时") || date.contains("小时") ){
        return 60*60*time;
    }
    else if(date.contains("分") || date.contains("分钟") ){
        return 60*time;
    }	
    return time;
}

public int get_time_int(String msg,int time){
    if (msg == null) return 0;
    int datu = msg.lastIndexOf(" ");
    if (datu == -1) return 0;
    String date = msg.substring(datu +1); 
    if(date.contains("天")){
        return time*60*60*24;
    }
    else if(date.contains("时") || date.contains("小时") ){
        return 60*60*time;
    }
    else if(date.contains("分") || date.contains("分钟") ){
        return 60*time;
    }	
    return time;
}

public int get_time(String msg){
    if (msg == null) return 0;
    int datu = msg.lastIndexOf(" ");
    if (datu == -1) return 0;
    String date=msg.substring(datu +1);
    date=date.trim();
    String t="";
    if(date != null && !"".equals(date)){
        for(int i=0;i<date.length();i++){
            if(date.charAt(i)>=48 && date.charAt(i)<=57){
                t +=date.charAt(i);
            }
        }
    }
    if (t.isEmpty()) return 0;
    int time=Integer.parseInt(t);  
    if(date.contains("天")){
        return time*60*60*24;
    }
    else if(date.contains("时") || date.contains("小时") ){
        return 60*60*time;
    }
    else if(date.contains("分") || date.contains("分钟") ){
        return 60*time;
    }	
    return time;
}

public int get_time(Object msg){
    if (msg == null || msg.MessageContent == null) return 0;
    int datu = msg.MessageContent.lastIndexOf(" ");
    if (datu == -1) return 0;
    String date=msg.MessageContent.substring(datu +1);
    date=date.trim();
    String t="";
    if(date != null && !"".equals(date)){
        for(int i=0;i<date.length();i++){
            if(date.charAt(i)>=48 && date.charAt(i)<=57){
                t +=date.charAt(i);
            }
        }
    }
    if (t.isEmpty()) return 0;
    int time=Integer.parseInt(t);  
    if(date.contains("天")){
        return time*60*60*24;
    }
    else if(date.contains("时") || date.contains("小时") ){
        return 60*60*time;
    }
    else if(date.contains("分") || date.contains("分钟") ){
        return 60*time;
    }
    return time;
}

public int parseBanTime(String timeStr) {
    if (timeStr == null || timeStr.isEmpty()) return 0;
    
    timeStr = timeStr.trim().toLowerCase();
    
    Pattern arabicPattern = Pattern.compile("(\\d+)\\s*(天|时|小时|分|分钟|秒)");
    Matcher arabicMatcher = arabicPattern.matcher(timeStr);
    
    if (arabicMatcher.find()) {
        try {
            int num = Integer.parseInt(arabicMatcher.group(1));
            String unit = arabicMatcher.group(2);
            
            switch (unit) {
                case "天":
                    return num * 24 * 60 * 60;
                case "时":
                case "小时":
                    return num * 60 * 60;
                case "分":
                case "分钟":
                    return num * 60;
                case "秒":
                    return num;
                default:
                    return num * 60;
            }
        } catch (Exception e) {
            return 0;
        }
    }
    
    return get_time_int("禁言 " + timeStr, CN_zh_int(timeStr.replaceAll("[天分时小时分钟秒]", "")));
}

public int dp2px(float dp) {
    try {
        DisplayMetrics metrics = new DisplayMetrics();
        Activity activity = getActivity();
        if (activity != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            return (int) (dp * metrics.density + 0.5f);
        }
    } catch (Exception e) {}
    return (int) (dp * 3 + 0.5f);
}

public boolean isGN(String groupUin, String key) {
    try {
        if (groupUin == null || key == null) return false;
        if("开".equals(getString(groupUin, key))) return true;
        else return false;
    } catch (Exception e) {
        return false;
    }
}