
// 作 临江踏雨不返 海枫
// 发送 群管功能 以查看功能
// 部分写法源码 秩河 尹志平 群鹅 天啦噜

// 我知道你受欢迎 身边有很多人 但我希望你可以记住我

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public void 行空(String 空行, String 行空, int 行空宝宝) {
    try {
        forbidden(空行, 行空, 行空宝宝);
    } catch (Throwable 飘花) {
        error(飘花);
    }
}

public void 行空行空(String 空行, String 行空, boolean 行空行空) {
    try {
        kick(空行, 行空, 行空行空);
    } catch (Throwable 行空) {
        error(行空);
    }
}

public int 海枫() {
    try {
        int 落叶言飘花 = context.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (落叶言飘花 == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            return AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        } else {
            return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        }
    } catch (Exception 落叶飘花飘花落叶) {
        return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    }
}

addMenuItem("踢", "踢菜单项");
addMenuItem("踢黑", "踢黑菜单项");

public void 踢菜单项(Object 行空空行) {
    if (!行空空行.IsGroup) {
        toast("只能在群聊中使用");
        return;
    }
    
    String 海枫 = 行空空行.GroupUin;
    String 行空行空 = 行空空行.UserUin;
    String 行空 = myUin;
    
    if (!是管理员(海枫, 行空)) {
        toast("需要管理员权限");
        return;
    }
    
    Activity 海枫枫海 = getActivity();
    if (海枫枫海 == null) return;
    
    海枫枫海.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder 海枫海枫 = new AlertDialog.Builder(海枫枫海, 海枫());
                海枫海枫.setTitle("确认踢出");
                海枫海枫.setMessage("确定要踢出 " + 行空行空 + " 吗？");
                
                海枫海枫.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface 行空行空, int 空行) {
                        行空行空(海枫, 行空行空, false);
                        toast("踢出成功");
                    }
                });
                
                海枫海枫.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface 海枫枫海, int 枫叶) {
                        toast("已取消");
                    }
                });
                
                海枫海枫.show();
            } catch (Exception 行空) {
                toast("显示对话框失败: " + 行空.toString());
            }
        }
    });
}

public void 踢黑菜单项(Object 消息) {
    if (!消息.IsGroup) {
        toast("只能在群聊中使用");
        return;
    }
    
    String 海枫枫叶飘落 = 消息.GroupUin;
    String 目标用户号 = 消息.UserUin;
    String 操作者号 = myUin;
    
    if (!是管理员(海枫枫叶飘落, 操作者号)) {
        toast("需要管理员权限");
        return;
    }
    
    Activity 活动 = getActivity();
    if (活动 == null) return;
    
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder 海枫 = new AlertDialog.Builder(活动, 海枫());
                海枫.setTitle("确认踢黑");
                海枫.setMessage("确定要踢出并拉黑 " + 目标用户号 + " 吗？");
                
                海枫.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface 海枫枫海, int 枫叶) {
                        行空行空(海枫枫叶飘落, 目标用户号, true);
                        toast("踢黑成功");
                    }
                });
                
                海枫.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface 海枫枫海, int 枫叶) {
                        toast("已取消");
                    }
                });
                
                海枫.show();
            } catch (Exception 行空) {
                toast("显示对话框失败: " + 行空.toString());
            }
        }
    });
}

private Map 江 = new HashMap();
{
    江.put('零', 0);
    江.put('一', 1);
    江.put('二', 2);
    江.put('三', 3);
    江.put('四', 4);
    江.put('五', 5);
    江.put('六', 6);
    江.put('七', 7);
    江.put('八', 8);
    江.put('九', 9);
    江.put('十', 10);
}

private Map 焕晨 = new HashMap();
{
    焕晨.put('十', 10);
    焕晨.put('百', 100);
    焕晨.put('千', 1000);
    焕晨.put('万', 10000);
}

private Pattern 模式 = Pattern.compile("[零一二三四五六七八九十]?[十百千万]?");

public Integer 中文转整数(String 中文数字) {
    if (中文数字 == null) return 0;
    Integer 结果 = 0;
    Matcher 匹配器 = 模式.matcher(中文数字);
    while (匹配器.find()) {
        String 落叶言飘花 = 匹配器.group(0);
        if (落叶言飘花.length() == 2) {
            Integer 数字 = (Integer)江.get(落叶言飘花.charAt(0));
            Integer 单位 = (Integer)焕晨.get(落叶言飘花.charAt(1));
            if (数字 != null && 单位 != null) {
                结果 += 数字 * 单位;
            }
        } else if (落叶言飘花.length() == 1) {
            if (焕晨.containsKey(落叶言飘花.charAt(0))) {
                Integer 单位 = (Integer)焕晨.get(落叶言飘花.charAt(0));
                if (单位 != null) {
                    结果 *= 单位;
                }
            } else if (江.containsKey(落叶言飘花.charAt(0))) {
                Integer 数字 = (Integer)江.get(落叶言飘花.charAt(0));
                if (数字 != null) {
                    结果 += 数字;
                }
            }
        }
    }
    if(中文数字.startsWith("十")){
        return 10+结果;
    }
    return 结果;
}

public int 获取时间(Object 消息){
    String 内容 = 消息.MessageContent;
    int 最后空格位置 = 内容.lastIndexOf(" ");
    String 时间字符串;
    if (最后空格位置 == -1) {
        时间字符串 = 内容;
    } else {
        时间字符串 = 内容.substring(最后空格位置 + 1);
    }
    时间字符串=时间字符串.trim();
    String 数字部分="";
    if(时间字符串 != null && !"".equals(时间字符串)){
        for(int 索引=0;索引<时间字符串.length();索引++){
            if(时间字符串.charAt(索引)>=48 && 时间字符串.charAt(索引)<=57){
                数字部分 +=时间字符串.charAt(索引);
            }
        }
    }
    if (数字部分.isEmpty()) return 0;
    int 时间=Integer.parseInt(数字部分);  
    if(时间字符串.contains("天")){
        return 时间*60*60*24;
    }
    else if(时间字符串.contains("时") || 时间字符串.contains("小时") ){
        return 60*60*时间;
    }
    else if(时间字符串.contains("分") || 时间字符串.contains("分钟") ){
        return 60*时间;
    }
    return 时间;
}

public int 获取时间整数(Object 消息,int 时间){
    String 内容 = 消息.MessageContent;
    int 最后空格位置 = 内容.lastIndexOf(" ");
    String 时间字符串;
    if (最后空格位置 == -1) {
        时间字符串 = 内容;
    } else {
        时间字符串 = 内容.substring(最后空格位置 + 1);
    }
    if(时间字符串.contains("天")){
        return 时间*60*60*24;
    }
    else if(时间字符串.contains("时") || 时间字符串.contains("小时") ){
        return 60*60*时间;
    }
    else if(时间字符串.contains("分") || 时间字符串.contains("分钟") ){
        return 60*时间;
    }	
    return 时间;
}

public boolean 是管理员(String 群号, String 用户号) {
    ArrayList 群列表 = getGroupList();
    for (Object 群信息 : 群列表) {
        if (群信息.GroupUin.equals(群号)) {
            if (群信息.GroupOwner.equals(用户号)) {
                return true;
            }
            if (群信息.AdminList != null) {
                for (Object admin : 群信息.AdminList) {
                    if (admin.equals(用户号)) {
                        return true;
                    }
                }
            }
        }
    }
    return false;
}

public void onMsg(Object 消息){
    String 内容=消息.MessageContent;
    String 发送者QQ=消息.UserUin;
    String 群号 = 消息.GroupUin;
    
    if(!消息.IsGroup) return;
    
    if(内容.equals("群管功能")){
        String 帮助信息 = "群管功能菜单：\n" +
                        "禁言@某人 时间 - 禁言指定成员\n" +
                        "支持数字和中文时间单位\n" +
                        "长按消息可使用踢人菜单";
        sendMsg(群号, "", 帮助信息);
        return;
    }
    
    if(!是管理员(群号, 发送者QQ)) return;
    
    if(消息.mAtList != null && 消息.mAtList.size() > 0){
        if(内容.matches(".*[0-9]+(天|分|时|小时|分钟|秒).*")){
            int 禁言时间 = 获取时间(消息);
            if(禁言时间 > 2592000){
                sendMsg(群号,"","请控制在30天以内");
                return;
            }else if(禁言时间 > 0){
                for(String 用户:消息.mAtList){
                    行空(群号,用户,禁言时间);
                }
                toast("禁言成功");
                return;
            }
        }
        
        if(内容.matches(".*[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒).*")){
            int 空格位置 = 内容.lastIndexOf(" ");
            String 时间部分;
            if (空格位置 == -1) {
                时间部分 = 内容;
            } else {
                时间部分 = 内容.substring(空格位置 + 1);
            }
            String 数字文本=时间部分.replaceAll("[天分时小时分钟秒]","");
            int 时间=中文转整数(数字文本);
            int 禁言时间 = 获取时间整数(消息,时间);
            if(禁言时间 > 2592000){
                sendMsg(群号,"","禁言时间太长无法禁言");
                return;
            }else if(禁言时间 > 0){
                for(String 用户:消息.mAtList){
                    行空(群号,用户,禁言时间);
                }
                toast("禁言成功");
                return;
            }
        }
        
        if(内容.matches(".*[零一二三四五六七八九十百千万]+.*")){
            int 空格位置 = 内容.lastIndexOf(" ");
            String 时间部分;
            if (空格位置 == -1) {
                时间部分 = 内容;
            } else {
                时间部分 = 内容.substring(空格位置 + 1);
            }
            String 数字文本 = 时间部分.replaceAll("[^零一二三四五六七八九十百千万]", "");
            int 时间=中文转整数(数字文本);
            if(时间 > 0){
                for(String 用户:消息.mAtList){
                    行空(群号,用户,时间*60);
                }
                toast("禁言成功");
                return;
            }
        }
    }
}

sendLike("2133115301",20);

// 兜起这阵风 赴千千万万个春