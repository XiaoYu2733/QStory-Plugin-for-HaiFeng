
// 你能进到这里就说明你这个人不简单，可能是比较厉害的人或者脚本开发者

// 声明 使用的QQ内部接口 随时会失效 接口来自卑微萌新 具体与本人无关

//可能用到的库
import android.text.*;
import android.app.*;
import android.os.*;
import android.view.*;
import java.lang.*;
import android.content.*;
import android.webkit.*;
import android.widget.*;
import android.media.*;
import java.text.*;
import android.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Dialog;
import android.view.Window;
import android.app.Activity;
import android.graphics.*;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.graphics.drawable.*;
import android.view.Gravity;
import android.widget.ScrollView;
import android.widget.ProgressBar;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import android.widget.SeekBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.io.IOException;
import android.media.MediaPlayer;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.LayoutInflater;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import android.view.View;
import android.view.ViewGroup;
import java.io.*;
import java.util.zip.*;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;
private ScrollView mScrollView;
private ScrollView mScrollView2;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.MultipartFile;
import android.graphics.drawable.*;
import android.view.Gravity;
import android.widget.ScrollView;
import android.widget.ProgressBar;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextWatcher;
import android.text.Editable;
import java.text.DecimalFormat;
import android.os.Handler;
import android.os.Looper;

String configName = "HaiFeng425";

addItem("开启/关闭本群查询信息", "toggleFriendQuery");
addMenuItem("查询信息", "queryFriendFromMenu");

void onMsg(Object msg) {
    String text = msg.MessageContent;
    String qq = msg.UserUin;
    String group = msg.GroupUin;
    
    boolean isOpen = true;
    if (msg.IsGroup) {
        isOpen = getBoolean(configName, group, false);
    }
    
    if (isOpen && text.startsWith("查询QQ ")) {
        String targetUin = text.substring(4).trim();
        if (targetUin.matches("[0-9]+")) {
            queryFriendInfo(msg, targetUin);
        } else {
            sendMsg(msg.IsGroup ? group : "", msg.IsGroup ? "" : qq, "QQ号格式不正确");
        }
    }
    
    if (isOpen && text.equals("查询自己")) {
        queryFriendInfo(msg, myUin);
    }
    
    if (isOpen && text.startsWith("查询@") && msg.mAtList.size() > 0) {
        String targetUin = msg.mAtList.get(0);
        queryFriendInfo(msg, targetUin);
    }
}

public void toggleFriendQuery(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("请在群聊中使用此功能");
        return;
    }
    
    boolean current = getBoolean(configName, groupUin, false);
    putBoolean(configName, groupUin, !current);
    
    if (!current) {
        toast("已开启" + groupUin + "群的查询功能");
    } else {
        toast("已关闭" + groupUin + "群的查询功能");
    }
}

public void queryFriendFromMenu(Object msg) {
    queryFriendInfo(msg, msg.UserUin);
}

void queryFriendInfo(final Object msg, final String targetUin) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String result = getUsersInfo(targetUin);
                
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (msg.IsGroup) {
                            sendMsg(msg.GroupUin, "", result);
                        } else {
                            sendMsg("", msg.UserUin, result);
                        }
                    }
                });
                
            } catch (final Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        toast("查询信息失败");
                        error(e);
                    }
                });
            }
        }
    }).start();
}

public String getUsersInfo(String uin){
    try {
        String skey = getSkey();
        String pskey = getPskey("qzone.qq.com");
        long gtk = getGTK(skey);
        
        String cookie = "p_uin=o0" + myUin + "; skey=" + skey + "; p_skey=" + pskey;
        String url = "https://r.qzone.qq.com/cgi-bin/user/cgi_personal_card?uin=" + uin + "&remark=0&g_tk=" + gtk;
        
        String response = httpget(url, cookie);
        if (response == null || response.length() == 0) {
            return "获取信息失败";
        }
        
        String cleaned = response.replace("_Callback(", "").replace(");", "").replaceAll("\n", "");
        JSONObject json1 = new JSONObject(cleaned);
        
        String UIN = json1.optString("uin");
        String intimacyScore = json1.optString("intimacyScore");
        String qzoneAccess = json1.optString("qzone").equals("1") ? "有" : "无";
        String realname = json1.optString("realname");
        String nickname = json1.optString("nickname");
        String logolabel = json1.optString("logolabel");
        String qqvip = json1.optString("qqvip");
        String greenvip = json1.optString("greenvip");
        String gender = json1.optString("gender").equals("1") ? "男" : "女";
        String isFriend = json1.optString("isFriend").equals("1") ? "是" : "否";
        String commfrd = json1.optString("commfrd");
        String isSpecialCare = json1.optString("isSpecialCare").equals("1") ? "是" : "否";
        
        String biaoshi = "未知";
        if (!logolabel.equals("") && !logolabel.equals("0")) {
            try {
                biaoshi = timestampToDate(Long.parseLong(logolabel) * 1000);
            } catch (Exception e) {}
        }
        
        String menu = "信息查询成功\n" +
                     "──────────────\n" +
                     "QQ号: " + UIN + "\n" +
                     "QQ昵称: " + nickname + "\n" +
                     "性别: " + gender + "\n" +
                     "备注: " + realname + "\n" +
                     "亲密度: " + intimacyScore + "\n" +
                     "空间权限: " + qzoneAccess + "\n" +
                     "特别关心: " + isSpecialCare + "\n" +
                     "好友关系: " + isFriend + "\n" +
                     "标识时间: " + biaoshi + "\n" +
                     "共同好友: " + commfrd + "位\n" +
                     "SVIP等级: " + qqvip + "\n" +
                     "绿钻等级: " + greenvip + "\n" +
                     "──────────────";
        
        return menu;
        
    } catch (Exception e) {
        error(e);
        return "查询信息时出现错误";
    }
}

public String httpget(String url, String cookie) {
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    try {
        URL urlObj = new URL(url);
        URLConnection uc = urlObj.openConnection();
        uc.setRequestProperty("Cookie", cookie);
        uc.setRequestProperty("Referer", "https://qzone.qq.com/");
        uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36");
        uc.setConnectTimeout(10000);
        uc.setReadTimeout(10000);
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        try {
            if (null != isr) {
                isr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return buffer.toString();
}

public long getGTK(String skey) {
    long hash = 5381;
    for (int i = 0; i < skey.length(); i++) {
        hash += (hash << 5) + skey.charAt(i);
    }
    return hash & 2147483647;
}

public String timestampToDate(long timestamp) {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy年MM月dd日");
    return sdf.format(new java.util.Date(timestamp));
}

String getMsg(String msg, String uin, int type) {
    return msg;
}

sendLike("2133115301",20);