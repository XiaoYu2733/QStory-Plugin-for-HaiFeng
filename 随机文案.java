
// 作 海枫

// QQ交流群：1050252163

// 请勿二改 二改者会拉黑

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

String folderPath = appPath + "/随机文案";
String filePath = folderPath + "/随机文案.txt";
ArrayList quotesList = new ArrayList();
String configName = "RandomQuotes";

java.io.File folder = new java.io.File(folderPath);
if (!folder.exists()) {
    folder.mkdirs();
    toast("创建文件夹: " + folderPath);
}

java.io.File file = new java.io.File(filePath);
if (!file.exists()) {
    try {
        file.createNewFile();
        java.io.FileWriter writer = new java.io.FileWriter(file);
        writer.write("眼泪是人最纯真的东西 流尽了人就变得冷漠了\n若是单思栀子花 庭中怎有三千树");
        writer.close();
        toast("创建默认文案文件");
    } catch (Exception e) {
        toast("创建文件失败: " + e);
    }
}

if (file.exists()) {
    try {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) quotesList.add(line);
        }
        reader.close();
        if (quotesList.isEmpty()) {
            quotesList.add("默认语录: 请编辑随机文案.txt添加更多内容");
            toast("文案文件为空，使用默认语录");
        }
    } catch (Exception e) {
        toast("读取失败: " + e);
    }
}

addItem("随机文案开关", "toggleSwitch");
addItem("脚本更新日志","showUpdateLog");

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
                    "- [适配] 最新版QStory\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

public void toggleSwitch(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("仅支持群聊");
        return;
    }
    boolean current = getBoolean(configName, groupUin, false);
    putBoolean(configName, groupUin, !current);
    toast(groupUin + (current ? "已关闭" : "已开启"));
}

void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    String group = msg.GroupUin;
    String content = msg.MessageContent.trim();
    
    if (!getBoolean(configName, group, false)) return;
    
    if ("随机文案".equals(content)) {
        if (quotesList.isEmpty()) {
            sendMsg(group, "", "文案库为空，请添加内容");
            return;
        }
        int index = (int)(Math.random() * quotesList.size());
        sendMsg(group, "", (String)quotesList.get(index));
        return;
    }
    
    if (Math.random() > 0.05) return;
    if (quotesList.isEmpty()) return;
    int index = (int)(Math.random() * quotesList.size());
    sendMsg(group, "", (String)quotesList.get(index));
}

sendLike("2133115301",20);
sendLike("107464738",20);