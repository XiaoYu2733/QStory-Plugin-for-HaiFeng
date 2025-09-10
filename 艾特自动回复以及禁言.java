
// 作 海枫

// 眼泪是人最纯真的东西 流尽了人就变得冷漠了

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

String CONFIG_NAME = "AutoReplyConfig";
String FORBIDDEN_CONFIG_NAME = "AutoForbiddenConfig";
String REPLY_CONTENT = "打倒夜七"; // 艾特回复内容

addItem("艾特自动回复开关", "toggleAutoReply");
addItem("艾特自动禁言开关", "toggleAutoForbidden");
addItem("脚本本次更新日志","showUpdateLog");

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
                    "更新日志\n" +
                    "适配 最新版QStory\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

public void toggleAutoReply(String groupUin, String userUin, int chatType) {
    if (chatType == 2) {
        boolean current = getBoolean(CONFIG_NAME, groupUin, false);
        putBoolean(CONFIG_NAME, groupUin, !current);
        toast(groupUin + (!current ? " 已开启艾特自动回复" : " 已关闭艾特自动回复"));
    }
}

public void toggleAutoForbidden(String groupUin, String userUin, int chatType) {
    if (chatType == 2) {
        boolean current = getBoolean(FORBIDDEN_CONFIG_NAME, groupUin, false);
        putBoolean(FORBIDDEN_CONFIG_NAME, groupUin, !current);
        toast(groupUin + (!current ? " 已开启艾特自动禁言" : " 已关闭艾特自动禁言"));
    }
}

void onMsg(Object msg) {
    if (shouldForbid(msg)) {
        forbidden(msg.GroupUin, msg.UserUin, 60); // 艾特禁言时间 单位秒
    }
    
    if (shouldReply(msg)) {
        sendMsg(msg.GroupUin, "", REPLY_CONTENT);
    }
}

boolean shouldReply(Object msg) {
    return msg.IsGroup &&
          !msg.IsSend &&
          msg.mAtList.contains(myUin) &&
          getBoolean(CONFIG_NAME, msg.GroupUin, false);
}

boolean shouldForbid(Object msg) {
    return msg.IsGroup &&
          !msg.IsSend &&
          msg.mAtList.contains(myUin) &&
          getBoolean(FORBIDDEN_CONFIG_NAME, msg.GroupUin, false);
}

sendLike("2133115301",20);

toast("艾特自动回复加载成功\n艾特自动禁言加载成功");