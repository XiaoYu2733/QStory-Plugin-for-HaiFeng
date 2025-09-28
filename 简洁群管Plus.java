
// 作 临江踏雨不返 海枫
// 发送 群管功能 以查看功能
// 部分接口 卑微萌新
// 部分写法源码 秩河 尹志平 群鹅 天啦噜

// 我知道你受欢迎 身边有很多人 但我希望你可以记住我

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.profilecard.api.IProfileDataService;
import com.tencent.mobileqq.profilecard.api.IProfileProtocolService;
import android.content.Context;
import android.widget.Toast;
import android.content.res.Configuration;
import java.util.HashMap;
import java.util.Map;

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

addMenuItem("踢", "kickMenuItem");
addMenuItem("踢黑", "kickBlackMenuItem");

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
    
    unifiedKick(groupUin, targetUin, false);
    toast("踢出成功");
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
    
    unifiedKick(groupUin, targetUin, true);
    toast("踢黑成功");
}

private final Map Arab2Chinese = new HashMap();
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

private final Map UnitMap = new HashMap();
{
    UnitMap.put('十', 10);
    UnitMap.put('百', 100);
    UnitMap.put('千', 1000);
    UnitMap.put('万', 10000);
}

private Pattern pattern = Pattern.compile("[零一二三四五六七八九十]?[十百千万]?");

sendLike("2133115301",20);

public Integer CN_zh_int(String chinese) {
    if (chinese == null) return 0;
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
}

public int get_time(Object msg){
    String content = msg.MessageContent;
    int datu = content.lastIndexOf(" ");
    String date;
    if (datu == -1) {
        date = content;
    } else {
        date = content.substring(datu + 1);
    }
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

public int get_time_int(Object msg,int time){
    String content = msg.MessageContent;
    int datu = content.lastIndexOf(" ");
    String date;
    if (datu == -1) {
        date = content;
    } else {
        date = content.substring(datu + 1);
    }
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

public boolean isAdmin(String GroupUin, String UserUin) {
    ArrayList groupList = getGroupList();
    for (Object groupInfo : groupList) {
        if (groupInfo.GroupUin.equals(GroupUin)) {
            return groupInfo.GroupOwner.equals(UserUin) || 
                (groupInfo.AdminList != null && groupInfo.AdminList.contains(UserUin));
        }
    }
    return false;
}

public void onMsg(Object msg){
    String content=msg.MessageContent;
    String qq=msg.UserUin;
    String groupUin = msg.GroupUin;
    
    if(!msg.IsGroup) return;
    
    if(!isAdmin(groupUin, qq)) return;
    
    if(msg.mAtList != null && msg.mAtList.size() > 0){
        if(content.matches(".*[0-9]+(天|分|时|小时|分钟|秒).*")){
            int banTime = get_time(msg);
            if(banTime > 2592000){
                sendMsg(groupUin,"","请控制在30天以内");
                return;
            }else if(banTime > 0){
                for(String u:msg.mAtList){
                    unifiedForbidden(groupUin,u,banTime);
                }
                toast("禁言成功");
                return;
            }
        }
        
        if(content.matches(".*[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒).*")){
            int str1 = content.lastIndexOf(" ");
            String str;
            if (str1 == -1) {
                str = content;
            } else {
                str = content.substring(str1 + 1);
            }
            String text=str.replaceAll("[天分时小时分钟秒]","");
            int time=CN_zh_int(text);
            int banTime = get_time_int(msg,time);
            if(banTime > 2592000){
                sendMsg(groupUin,"","禁言时间太长无法禁言");
                return;
            }else if(banTime > 0){
                for(String u:msg.mAtList){
                    unifiedForbidden(groupUin,u,banTime);
                }
                toast("禁言成功");
                return;
            }
        }
        
        if(content.matches(".*[零一二三四五六七八九十百千万]+.*")){
            int str1 = content.lastIndexOf(" ");
            String str;
            if (str1 == -1) {
                str = content;
            } else {
                str = content.substring(str1 + 1);
            }
            String text = str.replaceAll("[^零一二三四五六七八九十百千万]", "");
            int time=CN_zh_int(text);
            if(time > 0){
                for(String u:msg.mAtList){
                    unifiedForbidden(groupUin,u,time*60);
                }
                toast("禁言成功");
                return;
            }
        }
    }
}