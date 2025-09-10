// 所有代码不建议动 容易坏哦qwq
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;

String atPaiConfig = "AtPai";
String paiBackConfig = "PaiBack";

addItem("开启/关闭本群艾特回拍", "toggleAtPai");
addItem("开启/关闭本群回拍他人", "togglePaiBack");
addItem("脚本本次更新日志","showUpdateLog");

public void toggleAtPai(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("仅群聊可用");
        return;
    }
    boolean current = getBoolean(atPaiConfig, groupUin, false);
    putBoolean(atPaiConfig, groupUin, !current);
    toast("本群艾特回拍" + (!current ? "开启" : "关闭"));
}

public void togglePaiBack(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("仅群聊可用");
        return;
    }
    boolean current = getBoolean(paiBackConfig, groupUin, false);
    putBoolean(paiBackConfig, groupUin, !current);
    toast("本群回拍他人" + (!current ? "开启" : "关闭"));
}

void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    String groupUin = msg.GroupUin;
    String senderUin = msg.UserUin;
    if (senderUin.equals(myUin)) return;

    if (getBoolean(atPaiConfig, groupUin, false) && msg.mAtList != null && msg.mAtList.contains(myUin)) {
        sendPai(groupUin, senderUin);
    }

    if (getBoolean(paiBackConfig, groupUin, false) && msg.MessageContent != null && msg.MessageContent.contains("拍拍了") && msg.MessageContent.contains(myUin)) {
        sendPai(groupUin, senderUin);
    }
}

sendLike("2133115301",20);

public void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
            "更新日志\n\n" +
            "- [其他] 目前别人拍你无法回拍 因为有点复杂 等待n+版本添加 目前没空\n" +
            "- [优化] 代码逻辑\n\n" +
            "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}