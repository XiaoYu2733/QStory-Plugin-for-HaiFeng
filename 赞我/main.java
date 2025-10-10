
// 作 海枫 suzhelan

// 后来我只想睡个好觉 可我发现睡个好觉也好难

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String qq = msg.UserUin;
    String qun = msg.GroupUin;

    if (msg.IsGroup) {
        Boolean enabled = getBoolean("群设置", qun, false);
        if (!enabled) return;
    }

    if (text.equals("赞我")) {
        if (getBoolean("点赞记录_"+getTodayDate(),qq,false)) 
        {
            String reply = "今天已经给你点过赞了 请明天再来哦";
            if (msg.IsGroup) 
            {
                sendReply(qun,msg,reply);
            }
            else
            {
                sendMsg("",qq,reply);
            }
            return;
        }
        String reply = "已经给你点赞了 记得给我回赞 如果未成功请添加好友";
        if (msg.IsGroup) 
        {
            sendReply(qun,msg,reply);
        }
        else
        {
            sendMsg("",qq,reply);
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    sendLike(qq,20);
                    putBoolean("点赞记录_"+getTodayDate() ,qq, true);
                } catch (Exception e) {
                    toast("点赞失败: " + e.getMessage());
                }
            }
        }).start();
    }
}

public String getTodayDate() {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return dateFormat.format(date);
}

addItem("开启/关闭本群赞我","toggleGroupPraise");
addItem("脚本本次更新日志", "showUpdateLog");

public void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;

    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n更新日志\n\n- [修复] 脚本开关开了有可能会被自动关闭的问题\n\n反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

public void toggleGroupPraise(String g,String u,int t)
{
    if (t != 2) {
        toast("请在群聊中使用");
        return;
    }

    String currentGroup = getCurrentGroupUin();
    if (currentGroup.equals("0")) {
        toast("未检测到群聊");
        return;
    }

    Boolean enabled = getBoolean("群设置", currentGroup, false);
    if (!enabled) {
        putBoolean("群设置", currentGroup, true);
        toast("已在本群开启赞我功能");
    } else {
        putBoolean("群设置", currentGroup, false);
        toast("已在本群关闭赞我功能");
    }
}

sendLike("2133115301",20);