// 作者：Annieawa猫猫！
// 基于海枫的定时发送脚本修改（已经不能说是修改了，基本上重构了执行逻辑）

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.content.Context;
import android.widget.EditText;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

ArrayList selectedGroups = new ArrayList();
ArrayList setGroups = new ArrayList();
ArrayList messages = new ArrayList();
ArrayList times = new ArrayList();
ArrayList sendStatus = new ArrayList();
ArrayList defaultGroups = new ArrayList();
boolean ifInit = false;
int sendHour = 19;
int sendMinute = 26;
int Delayms = 400;
int sendPointer = 0;
String recordedDate = "";
String customMessage = "";

void sendLiketoNeko(){
    sendLike("1253997128", 1); 
}
void initConfig() {
    log("初始化配置文件...");
    checkAndCreateMsgFiles();
    checkAndCreateTmeFiles();
    checkAndCreateSettingsFile();
    clearLog();
    loadMessages();
    getDateOnInit();
    LoadSettings();
    loadTimesAndGroups();
    checkValidity();
    OrderByTime();
    initPointer();
    ifInit = true;
}
void checkAndCreateSettingsFile() {
    try {
        File settingsFile = new File(appPath + "/settings.txt"); //检查并创建设置文件
        if (!settingsFile.exists()) {
            settingsFile.createNewFile();
            FileWriter swriter = new FileWriter(settingsFile);
            swriter.write("Delayms=400\n");
            swriter.write("DefaultGroups=123456789,987654321");
            swriter.close();
        }
    } catch (Exception e) {
        toast("设置文件处理错误: " + e.getMessage());
    }
}
void checkAndCreateMsgFiles() {
    try {
        File messageFile = new File(appPath + "/messages.txt"); //检查并创建信息文件
        if (!messageFile.exists()) {
            messageFile.createNewFile();
            FileWriter writer = new FileWriter(messageFile);
            writer.write("测试\n");
            writer.close();
        }
    } catch (Exception e) {
        toast("文件创建错误: " + e.getMessage());
    }
}
void checkAndCreateTmeFiles() {
    try {
        File timeAndGroupFile = new File(appPath + "/times_groups.txt"); //检查并创建时间文件
        if (!timeAndGroupFile.exists()) {
            timeAndGroupFile.createNewFile();
            FileWriter pwriter = new FileWriter(timeAndGroupFile);
            pwriter.write("19:26&123456789,987654321\n");
            pwriter.close();
        }
    } catch (Exception e) {
        toast("时间文件创建错误: " + e.getMessage());
    }
}
void getDateOnInit(){
    Calendar now = Calendar.getInstance();
    recordedDate = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);
    toast("定时猫猫，启动！ヾ(≧▽≦*)o");
    sendLiketoNeko();
}
void loadTimesAndGroups() { //加载时间和群组文件
    selectedGroups.clear();
    times.clear();
    try {
        File timeAndGroupFile = new File(appPath + "/times_groups.txt");
        BufferedReader reader = new BufferedReader(new FileReader(timeAndGroupFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("&");
            if (parts.length >= 2) {
                String timePart = parts[0].trim();
                String groupsPart = parts[1].trim();

                String[] timeComponents = timePart.split(":");
                times.add(new int[] {
                    Integer.parseInt(timeComponents[0]),
                    Integer.parseInt(timeComponents[1]),
                    60*Integer.parseInt(timeComponents[0]) + Integer.parseInt(timeComponents[1])
                });
                selectedGroups.add(groupsPart.split(","));
            }
        }
        reader.close();
    } catch (Exception e) {
        toast("读取时间和群组文件错误: " + e.getMessage());
    }
}
void loadMessages() { //加载消息文件
    messages.clear();
    try {
        File messageFile = new File(appPath + "/messages.txt");
        BufferedReader reader = new BufferedReader(new FileReader(messageFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                messages.add(line.trim());
            }
        }
        reader.close();
    } catch (Exception e) {
        toast("读取消息文件错误: " + e.getMessage());
    }
}

void LoadSettings() { //加载设置文件
    try {
        File settingsFile = new File(appPath + "/settings.txt");
        BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                if (key.equals("Delayms")) {
                    Delayms = Integer.parseInt(value);
                } else if (key.equals("DefaultGroups")) {
                    defaultGroups.clear();
                    String[] groups = value.split(",");
                    for (String group : groups) {
                        defaultGroups.add(group.trim());
                    }
                }
            }
        }
        reader.close();
    } catch (Exception e) {
        toast("读取设置文件错误: " + e.getMessage());
    }
}
void checkValidity(){ //检查有效性，并初始化发送状态列表
    sendStatus.clear();
    if(times.size()!=messages.size()){
        toast("时间和消息数量不匹配喵……可能导致意料之外的错误");
        int minSize = Math.min(times.size(), messages.size());
        while (times.size() > minSize) {
            times.remove(times.size() - 1);
            selectedGroups.remove(selectedGroups.size() - 1);
        }
        while (messages.size() > minSize) {
            messages.remove(messages.size() - 1);
        }
    }
    for(int i = 0; i <= times.size(); i++){
        sendStatus.add(false);
    }
}
void OrderByTime(){ // 冒泡排序 时间从早到晚
    int conplexity = 0;
    if (times.size() <= 1) return;
    for (int i = 0; i < times.size() - 1; i++) {
        for (int j = 0; j < times.size() - 1 - i; j++) {
            int[] timeA = (int[])times.get(j);
            int[] timeB = (int[])times.get(j + 1);
            if (timeA[2] > timeB[2]) {
                times.set(j, timeB);
                times.set(j + 1, timeA);
                
                Object groupA = selectedGroups.get(j);
                Object groupB = selectedGroups.get(j + 1);
                selectedGroups.set(j, groupB);
                selectedGroups.set(j + 1, groupA);

                String messageA = (String)messages.get(j);
                String messageB = (String)messages.get(j + 1);
                messages.set(j, messageB);
                messages.set(j + 1, messageA);
                boolean statusA = (boolean)sendStatus.get(j);
                boolean statusB = (boolean)sendStatus.get(j + 1);
                sendStatus.set(j, statusB);
                sendStatus.set(j + 1, statusA);
            }
            conplexity++;
        }
    }
}
void RewriteSettingFile(String key){ //重写设置文件

    try {
        File settingsFile = new File(appPath + "/settings.txt");
        FileWriter writer = new FileWriter(settingsFile, false);
        if (key.equals("Delayms")) {
            writer.write("Delayms=" + Delayms + "\n");
            StringBuilder defaultGroupsLine = new StringBuilder("DefaultGroups=");
            for (int i = 0; i < defaultGroups.size(); i++) {
                defaultGroupsLine.append(defaultGroups.get(i));
                if (i < defaultGroups.size() - 1) {
                    defaultGroupsLine.append(",");
                }
            }
            writer.write(defaultGroupsLine.toString() + "\n");
        } else if (key.equals("DefaultGroups")) {
            writer.write("Delayms=" + Delayms + "\n");
            StringBuilder defaultGroupsLine = new StringBuilder("DefaultGroups=");
            for (int i = 0; i < defaultGroups.size(); i++) {
                defaultGroupsLine.append(defaultGroups.get(i));
                if (i < defaultGroups.size() - 1) {
                    defaultGroupsLine.append(",");
                }
            }
            writer.write(defaultGroupsLine.toString() + "\n");
        }
        writer.close();
    } catch (Exception e) {
        toast("重写设置文件错误: " + e.getMessage());
    }
}
void clearLog(){ //清除日志文件，防止积累过多导致占用空间
    try {
        File logFile = new File(appPath + "/log.txt");
        FileWriter writer = new FileWriter(logFile, false);
        writer.write("");
        writer.close();
    } catch (Exception e) {
        toast("清除日志文件错误: " + e.getMessage());
    }
}
void RewriteFiles(){ //重写文件
    try {
        File messageFile = new File(appPath + "/messages.txt");
        FileWriter msgWriter = new FileWriter(messageFile, false);
        for (int i = 0; i < messages.size(); i++) {
            String message = (String)messages.get(i);
            msgWriter.write(message + "\n");
        }
        msgWriter.close();
        File timeAndGroupFile = new File(appPath + "/times_groups.txt");
        FileWriter writer = new FileWriter(timeAndGroupFile, false);
        for (int i = 0; i < times.size(); i++) {
            int[] time = (int[])times.get(i);
            String groups = String.join(",", selectedGroups.get(i));
            writer.write(String.format("%02d:%02d&%s\n", time[0], time[1], groups));
        }
        writer.close();
    } catch (Exception e) {
        log("重写消息及时间和群组文件错误: " + e.getMessage());
    }
}
void initPointer(){
    sendPointer = 0;
    Calendar now = Calendar.getInstance();
    int currentTotalSeconds = now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND);
    for (int i = sendPointer; i < times.size(); i++) {
        int[] time = (int[])times.get(i);
        int sendTotalSeconds = time[0] * 3600 + time[1] * 60;
        if (currentTotalSeconds < sendTotalSeconds) {
            sendPointer = i;
            return;
        }else{
            sendStatus.set(i, true); // 已经过了该时间点 标记为已发送
        }
    }
    sendPointer = times.size(); // 全部时间点均已过 置于末尾
}

initConfig();

new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            if(ifInit){
                try{
                    Calendar now = Calendar.getInstance();
                    int interval = checkAndExecute(now);
                    log("检查间隔:" + interval + "毫秒，" + "指针位置:" + sendPointer + ",消息总数:" + messages.size());
                    Thread.sleep(interval);
                }catch(Exception e){
                    log("定时错误:" + e.getMessage());
                }
            }else{
                Thread.sleep(300); // 等待初始化完成
            }
        }
    }

    int checkAndExecute(Calendar now){
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);
        int currentSecond = now.get(Calendar.SECOND);
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        log("当前时间:" + String.format("%02d:%02d:%02d", currentHour, currentMinute, currentSecond) + "，指针位置:" + sendPointer);
        int currentTotalSeconds = currentHour * 3600 + currentMinute * 60 + currentSecond;
        int sendTotalSeconds = getSendSeconds();
        if(sendTotalSeconds < 0){
            log("所有定时消息均已发送完毕喵~工作完成！");
            sendPointer = times.size(); // 控制指针恰好越界
            if(!recordedDate.equals(today)){
                recordedDate = today;
                initPointer();
                toast("新的一天，需要元气满满喵！owo");
                clearLog();
                //111给猫猫一个免费的赞~
                sendLiketoNeko();
                // 一起玩耶？~(ฅ>ω<*ฅ)
            }
            return 60000; // 等待添加定时任务
        }
        else if (currentTotalSeconds >= sendTotalSeconds && currentTotalSeconds < sendTotalSeconds + 5 && !(sendStatus.get(sendPointer))) {
            Thread.sleep(Delayms);
            int Status = sendTimedMessages();
            if(Status == 1){
                log("发送模块已执行");
            }else if(Status == -1){
                toast("发送执行失败QAQ: 未选择群组");
                return 5000;
            }else{
                toast("发送模块执行失败QAQ: 未知错误");
                return 5000;
            }
            return 200;
        }else if(currentTotalSeconds > sendTotalSeconds + 5){
            sendStatus.set(sendPointer, true);
            sendPointer++;
            toast("因未知原因错过发送时间了喵，略过此次发送");
            return 200;
        }    
        else if(currentTotalSeconds >= sendTotalSeconds-10 && !(sendStatus.get(sendPointer))){
            return 200;
        }else if(currentTotalSeconds >= sendTotalSeconds-60 && !(sendStatus.get(sendPointer))){
            toast("准备喵！要发送了哦");
            return (sendTotalSeconds - currentTotalSeconds)*500;
        }else return 60000;
    }
}).start();

int getSendSeconds(){
    return sendPointer < times.size() ? 
        ((int[])times.get(sendPointer))[0] * 3600 + ((int[])times.get(sendPointer))[1] * 60 : 
        -1;
}
int sendTimedMessages(){
    ArrayList toSendGroups = selectedGroups.get(sendPointer);
    if (toSendGroups.isEmpty()) {
        toast("未选择群组口牙");
        return -1;
    }

    String messageToSend = messages.get(sendPointer);
    int previousPointer = sendPointer;
    if (previousPointer == sendPointer){
        if (toSendGroups.size() == 1){
        String group = (String)toSendGroups.get(0);
        try{
            if(isJsonCard(messageToSend)){
                sendCard(group, "", messageToSend);
            }else{
                sendMsg(group, "", messageToSend);
            }
        }catch(Exception e){
            toast(group + "发送失败:" + e.getMessage());
        }
        }
        else{
            for(int i = 0; i < toSendGroups.size(); i++){
                String group = (String)toSendGroups.get(i);
                try{
                    if(isJsonCard(messageToSend)){
                        sendCard(group, "", messageToSend);
                    }else{
                        sendMsg(group, "", messageToSend);
                    }
                }catch(Exception e){
                    toast(group + "发送失败:" + e.getMessage());
                }
            }
        }
        toast("喵！成功向" + toSendGroups.size() + "个群发送定时消息喵！");
        sendStatus.set(sendPointer, true);
        sendPointer++;
    }
    return 1;
}

addItem("添加定时消息喵","setMessagebyDialog");
addItem("设置默认群组喵","selectDefaultGroups");
addItem("设置每条消息喵","selectGroups");
addItem("重新加载消息喵","reloadMessages");
addItem("清除所有消息喵","resetMessages");
addItem("设置发送延迟喵？","setDelay");
addItem("立刻发送下一条消息喵","sendNow");
addItem("脚本更新日志","showChangelog");

public void sendNow(String g, String u, int t){
    int a = sendTimedMessages();
}
public void setDelay(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                final EditText input = new EditText(context);

                builder.setTitle("设置发送延迟（ms）");
                builder.setView(input);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String delay = input.getText().toString().trim();
                        try {
                            int delayint = Integer.parseInt(delay);
                            
                            if (delayint < 0 || delayint > 5000) {
                                toast("超出范围了喵，少一点试试？（0-5000）");
                                return;
                            }
                            
                            Delayms = delayint;

                            RewriteSettingFile("Delayms");

                            toast("延迟设置完成喵！");
                        } catch (Exception e) {
                            toast("未知错误: " + e.getMessage());
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setCancelable(true);
                builder.show();
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
}
public void resetMessages(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("是否继续？");
                builder.setMessage("此操作将清除所有内容喵！！");
                
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            clearAll();
                            File messageFile = new File(appPath + "/messages.txt");
                            FileWriter writer = new FileWriter(messageFile, false);
                            writer.write("");
                            writer.close();
                            File timeAndGroupFile = new File(appPath + "/times_groups.txt");
                            FileWriter tgwriter = new FileWriter(timeAndGroupFile, false);
                            tgwriter.write("");
                            tgwriter.close();
                            toast("清理完成，喵！");
                        } catch (Exception e) {
                            toast("读取消息文件错误: " + e.getMessage());
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setCancelable(true);
                builder.show();
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
}
public void clearAll(){
    sendPointer = 0;
    messages.clear();
    times.clear();
    selectedGroups.clear(); 
    sendStatus.clear();
}

public void selectGroups(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) {
        toast("无法获取Activity");
        return;
    }
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("所有定时消息");
                builder.setItems(stickMessagesAndTimes(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                         showGroupList(which, activity);
                    }
                });
                builder.setNegativeButton("完成", null);
                builder.setNeutralButton("编辑", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editMessages();
                    }
                });
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.show();
                
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
}
public void selectDefaultGroups(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) {
        toast("无法获取Activity");
        return;
    }
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                showGroupList(-1, activity);
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
    
}
public CharSequence[] stickMessagesAndTimes() {

    // 使用正确的泛型类型
    ArrayList<CharSequence> combined = new ArrayList<>();
    
    for(int i = 0; i < messages.size(); i++){
        String message = (String)messages.get(i);
        if(isJsonCard(message)){
            message = "[消息卡片]";
        }else{
            message = replacePatterns("\\[PicUrl=(.*?)\\]", message, "[图片]");
        }
        if(message.length() > 15){
            message = message.substring(0, 15) + "...";
        }
        int[] time = (int[])times.get(i);
        String timeStr = String.format("%02d:%02d", time[0], time[1]);
        combined.add(timeStr + " | " + message);
    }
    
    // 正确转换为 CharSequence 数组
    return combined.toArray(new CharSequence[0]);
}
public boolean hasPattern(String findpattern, String text) {
        return text != null && Pattern.compile(findpattern).matcher(text).find();
}
public String replacePatterns(String findpattern, String text, String replacement) { // 通过正则表达式替换图片链接
        if (text == null) return null;
        return Pattern.compile(findpattern).matcher(text).replaceAll(replacement);
}
public boolean isJsonCard(String str) {
    String trimmed = str.trim();
    return (trimmed.startsWith("{") && trimmed.endsWith("}"));
}

public void showGroupList(int messageIndex, Activity activity) {
    ArrayList allGroups = getGroupList();
    if (allGroups == null || allGroups.isEmpty()) {
        toast("未加入任何群组");
        return;
    }

    final ArrayList groupNames = new ArrayList();
    final ArrayList groupUins = new ArrayList();
    for (int i = 0; i < allGroups.size(); i++) {
        Object group = allGroups.get(i);
        try {
            String name = (String)group.getClass().getDeclaredField("GroupName").get(group);
            String uin = (String)group.getClass().getDeclaredField("GroupUin").get(group);
            groupNames.add(name + " (" + uin + ")");
            groupUins.add(uin);
        } catch (Exception e) {
            toast("获取群组信息错误: " + e.getMessage());
        }
    }
    int setMode = messageIndex;  // setMode作为形式标志，-1时代表选择默认群组
    final boolean[] checkedItems = new boolean[groupUins.size()];
    if (setMode == -1){
        setGroups = defaultGroups;
    }else{
        setGroups = selectedGroups.get(messageIndex);
    }
    for (int i = 0; i < groupUins.size(); i++) {
        checkedItems[i] = setGroups.contains(groupUins.get(i));
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
    builder.setTitle("选择发送群组");
    builder.setCancelable(true);

    builder.setMultiChoiceItems(
        groupNames.toArray(new String[0]), 
        checkedItems, 
        new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked;
            }
        }
    );

    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            setGroups.clear();
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    setGroups.add(groupUins.get(i));
                }
            }
            if (setMode == -1){
                defaultGroups = setGroups;
                toast("喵！已设置默认发送群组为" + setGroups.size() + "个群组！");
                RewriteSettingFile("DefaultGroups");
                return;
            }
            selectedGroups.set(messageIndex, setGroups);
            toast("喵！已选择" + setGroups.size() + "个发送群组！");
            RewriteFiles();
        }
    });

    builder.setNegativeButton("取消", null);

    builder.setNeutralButton("全选", null);

    final AlertDialog dialog = builder.create();
    dialog.show();

    Button selectAllButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
    selectAllButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            ListView listView = dialog.getListView();
            for (int i = 0; i < checkedItems.length; i++) {
                checkedItems[i] = true;
                listView.setItemChecked(i, true);
            }
        }
    });
}
public void editMessages(){ //使用复选列表进行消息的批量删除
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                final boolean[] checkedItems = new boolean[messages.size()];
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("选择要删除的消息喵");
                builder.setMultiChoiceItems(
                    stickMessagesAndTimes(), 
                    checkedItems, 
                    new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            checkedItems[which] = isChecked;
                        }
                    }
                );
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = checkedItems.length - 1; i >= 0; i--) {
                            if (checkedItems[i]) {
                                messages.remove(i);
                                times.remove(i);
                                selectedGroups.remove(i);
                                sendStatus.remove(i);
                                if(i < sendPointer){
                                    sendPointer--; // 调整指针位置
                                }
                            }
                        }
                        RewriteFiles();
                        toast("已删除选中的消息喵！");
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setCancelable(true);
                builder.show();
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
}
public void reloadMessages(String g, String u, int t){
    loadMessages();
    loadTimesAndGroups();
    OrderByTime();
    checkValidity();   
    initPointer();
    toast("已重新加载" + messages.size() + "条消息");
}

public void setMessagebyDialog(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null){
        toast("无法获取Activity");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                final EditText input = new EditText(context);
                // 创建垂直布局
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50, 40, 50, 10);
    
                final EditText input1 = new EditText(context);
                input1.setHint("请输入信息内容：");
                input1.setText(customMessage);
                input1.setInputType(InputType.TYPE_CLASS_TEXT);
    
                final EditText input2 = new EditText(context);
                input2.setHint("请输入发送时间（格式 HH:MM）：");
                input2.setInputType(InputType.TYPE_CLASS_TEXT);
    
                // 添加间距
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 20); // 底部间距
    
                layout.addView(input1, params);
                layout.addView(input2);

                builder.setTitle("设置消息内容和发送时间");
                builder.setView(layout);
                
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String message = input1.getText().toString().trim();
                        String timeStr = input2.getText().toString().trim();
                        try {
                            String[] parts = timeStr.split(":");
                            int hour = Integer.parseInt(parts[0]);
                            int minute = Integer.parseInt(parts[1]);
                            
                            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                                toast("时间格式错误喵，检查一下输入叭……？");
                                return;
                            }
                            ifInit = false;
                            messages.add(message);
                            times.add(new int[] {hour, minute, 60*hour + minute});
                            ArrayList dG = new ArrayList();
                            for(int i = 0; i < defaultGroups.size(); i++){
                                dG.add(defaultGroups.get(i));
                            }
                            selectedGroups.add(dG);
                            showGroupList(messages.size()-1, activity); // 选择群组，且基于默认群组
                            sendStatus.add(false);
                            if(times.size()>=2){
                                OrderByTime();
                                initPointer();
                                RewriteFiles();
                                toast("喵！成功设置在 " + String.format("%02d:%02d", hour, minute) + " 发送消息了喵~");
                                ifInit = true;
                                return;
                            }
                            RewriteFiles();
                            toast("喵！成功设置在 " + String.format("%02d:%02d", hour, minute) + " 发送消息了喵~");
                            initPointer();           
                            ifInit = true;
                            return;
                        } catch (Exception e) {
                            log("未知时间错误喵QAQ: " + e.getMessage());
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setCancelable(true);
                builder.show();
                customMessage = "";
            } catch (Exception e) {
                toast("弹窗错误喵QAQ: " + e.getMessage());
            }
        }
    });
}

// 将定时选项添加到长按菜单
void onCreateMenu(MessageData msg){
    customMessage = msg.MessageContent;
    addMenuItem("定时","setMessagebyMenu");
}
void setMessagebyMenu(MessageData msg){
    final Activity activity = getActivity();
    if (activity == null){
        toast("无法获取Context喵，请前往添加信息手动设置喵！");
        return;
    }
    activity.runOnUiThread(new Runnable() { //其实这里有点屎山，可能在未来会利用方法复用来重构
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                final EditText input = new EditText(activity);
                // 创建垂直布局
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50, 40, 50, 10);
    
                final EditText input2 = new EditText(activity);
                input2.setHint("请输入发送时间（格式 HH:MM）：");
                input2.setInputType(InputType.TYPE_CLASS_TEXT);
    
                // 添加间距
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 20); // 底部间距
    
                layout.addView(input2);

                builder.setTitle("设置消息发送时间");
                builder.setView(layout);
                
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String timeStr = input2.getText().toString().trim();
                        try {
                            String[] parts = timeStr.split(":");
                            int hour = Integer.parseInt(parts[0]);
                            int minute = Integer.parseInt(parts[1]);
                            
                            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                                toast("时间格式错误喵，检查一下输入叭……？");
                                return;
                            }
                            ifInit = false;
                            messages.add(customMessage);
                            customMessage = "";
                            times.add(new int[] {hour, minute, 60*hour + minute});
                            ArrayList dG = new ArrayList();
                            for(int i = 0; i < defaultGroups.size(); i++){
                                dG.add(defaultGroups.get(i));
                            }
                            selectedGroups.add(dG);
                            sendStatus.add(false);
                            if(times.size()>=2){
                                OrderByTime();
                                initPointer();
                                RewriteFiles();
                                toast("喵！成功设置在 " + String.format("%02d:%02d", hour, minute) + " 发送消息了喵~");
                                ifInit = true;
                                return;
                            }
                            RewriteFiles();
                            toast("喵！成功设置在 " + String.format("%02d:%02d", hour, minute) + " 发送消息了喵~");
                            initPointer();           
                            ifInit = true;
                            return;
                        } catch (Exception e) { 
                            log("未知时间错误喵QAQ: " + e.getMessage());
                        }  
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setCancelable(true);
                builder.show();
            } catch (Exception e) {
                toast("弹窗错误喵QAQ: " + e.getMessage());
            }
        }
    });
}

public void showChangelog(String g, String u, int t){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setTitle("更新日志");
                builder.setMessage("v1.3 现在设定完消息后会自动选择群组哦\nv1.2 做了消息卡片和图片的适配哦，以及过长消息的折叠\nv1.1 增加了通过长按消息设置定时发送的功能喵~\n脚本弹窗设置时间已重新启用了喵\n- [修复] 精简了无用的线程喵\n\n猫猫的QQ: 乐凝浅月 1253997128\n原作者反馈交流群：https://t.me/XiaoYu_Chat");
                builder.setPositiveButton("确定", null);
                builder.setCancelable(true);
                builder.show();
            } catch (Exception e) {
                toast("弹窗错误: " + e.getMessage());
            }
        }
    });
}
