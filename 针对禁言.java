
// 作 海枫

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

ConcurrentHashMap<String, Boolean> bannedUsersMap = new ConcurrentHashMap<String, Boolean>();
Object fileLock = new Object();

String configName = "Group";

addItem("开启/关闭本群针对禁言", "switchFunction");
addItem("配置禁言用户", "showConfigDialog");
addItem("脚本使用方法", "showUsageDialog");

java.io.File targetDir = new java.io.File(appPath + "/针对禁言");
synchronized(fileLock) {
    if (!targetDir.exists()) {
        targetDir.mkdirs();
    }
}

java.io.File qqFile = new java.io.File(targetDir, "针对禁言QQ号.txt");
synchronized(fileLock) {
    if (!qqFile.exists()) {
        try {
            qqFile.createNewFile();
        } catch (Exception e) {
            error(e);
        }
    } else {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(qqFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String qq = line.trim();
                if (!qq.isEmpty()) {
                    bannedUsersMap.put(qq, true);
                }
            }
            reader.close();
        } catch (Exception e) {
            error(e);
        }
    }
}

public int getCurrentTheme() {
    try {
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

public void showUsageDialog(String groupUin, String uin, int chatType) {
    try {
        String dialogContent = "需要先把对应群聊开关打开\n添加禁言用户在群内发送添加/删除禁言用户@用户\n时间需要自行配置，代码里面也有说明";

        Activity activity = getActivity();
        if (activity == null) {
            toast("无法获取弹窗");
            return;
        }
        
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    TextView textView = new TextView(activity);
                    textView.setText(dialogContent);
                    textView.setTextSize(14);
                    textView.setPadding(50, 30, 50, 30);
                    textView.setTextIsSelectable(true);

                    ScrollView scrollView = new ScrollView(activity);
                    scrollView.addView(textView);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                    builder.setTitle("脚本使用方法")
                        .setView(scrollView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                } catch (Exception e) {
                    log("弹窗错误: " + e.toString());
                    toast("弹窗显示失败");
                }
            }
        });
    } catch (Exception e) {
        log("显示弹窗失败: " + e.toString());
        toast("弹窗初始化失败");
    }
}

public void showConfigDialog(String groupUin, String uin, int chatType) {
    Activity activity = getActivity();
    if (activity == null) {
        toast("无法获取弹窗");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("配置禁言用户");
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30, 30, 30, 30);
                
                EditText inputEditText = new EditText(activity);
                inputEditText.setHint("输入QQ号，多个用逗号分隔");
                inputEditText.setHintTextColor(Color.GRAY);
                layout.addView(inputEditText);
                
                final ArrayList<String> qqList = new ArrayList<String>(bannedUsersMap.keySet());
                final ListView listView = new ListView(activity);
                ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, qqList);
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                layout.addView(listView);
                
                builder.setView(layout);
                
                builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            String[] qqs = input.split("[,\\s]+");
                            for (String qq : qqs) {
                                if (!qq.isEmpty()) {
                                    addQQToFile(qq);
                                }
                            }
                        }
                    }
                });
                
                builder.setNegativeButton("删除选中", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < listView.getCount(); i++) {
                            if (listView.isItemChecked(i)) {
                                String qq = (String)qqList.get(i);
                                removeQQFromFile(qq);
                            }
                        }
                    }
                });
                
                builder.setNeutralButton("清空列表", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        synchronized(fileLock) {
                            try {
                                bannedUsersMap.clear();
                                new Thread(new Runnable() {
                                    public void run() {
                                        synchronized(fileLock) {
                                            try {
                                                java.io.FileWriter writer = new java.io.FileWriter(qqFile);
                                                writer.write("");
                                                writer.close();
                                            } catch (Exception e) {
                                                error(e);
                                            }
                                        }
                                    }
                                }).start();
                                toast("已清空禁言列表");
                            } catch (Exception e) {
                                error(e);
                                toast("清空失败");
                            }
                        }
                    }
                });
                
                builder.show();
            } catch (Exception e) {
                log("配置弹窗错误: " + e.toString());
                toast("配置弹窗显示失败");
            }
        }
    });
}

public void switchFunction(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("仅群聊可用");
        return;
    }
    boolean isOpen = getBoolean(configName, groupUin, false);
    putBoolean(configName, groupUin, !isOpen);
    toast("已" + (!isOpen ? "开启" : "关闭") + "本群针对禁言功能");
}

public boolean isFunctionOpen(String groupUin) {
    return getBoolean(configName, groupUin, false);
}

void onMsg(Object msg) {
    if (!msg.IsGroup || msg.IsSend) return;
    
    String text = msg.MessageContent;
    String senderQQ = msg.UserUin;
    String groupUin = msg.GroupUin;
    
    if (text.startsWith("添加禁言用户")) {
        if (msg.mAtList != null && msg.mAtList.size() > 0) {
            for (String targetQQ : msg.mAtList) {
                addQQToFile(targetQQ);
            }
            toast("已添加禁言用户");
        } else {
            toast("请@要添加的用户");
        }
        return;
    }
    
    if (text.startsWith("删除禁言用户")) {
        if (msg.mAtList != null && msg.mAtList.size() > 0) {
            for (String targetQQ : msg.mAtList) {
                removeQQFromFile(targetQQ);
            }
            toast("已删除禁言用户");
        } else {
            toast("请@要删除的用户");
        }
        return;
    }
    
    if (!isFunctionOpen(groupUin)) return;
    
    String targetQQ = msg.UserUin;
    boolean shouldBan = isQQInFile(targetQQ);
    
    if (shouldBan) {
        int banTime = getInt(configName, "禁言时间", 60); // 禁言时间 单位秒 自行更改 最高2592000秒
        forbidden(groupUin, targetQQ, banTime);
        toast("已禁言" + targetQQ + " " + banTime + "秒");
    }
}

public boolean isQQInFile(String qq) {
    return bannedUsersMap.containsKey(qq);
}

public void addQQToFile(String qq) {
    synchronized(fileLock) {
        if (isQQInFile(qq)) {
            toast("用户 " + qq + " 已在禁言列表中");
            return;
        }
        
        try {
            bannedUsersMap.put(qq, true);
            
            new Thread(new Runnable() {
                public void run() {
                    synchronized(fileLock) {
                        try {
                            java.io.FileWriter writer = new java.io.FileWriter(qqFile, true);
                            writer.write(qq + "\n");
                            writer.close();
                        } catch (Exception e) {
                            error(e);
                        }
                    }
                }
            }).start();
            
            toast("已添加用户 " + qq + " 到禁言列表");
        } catch (Exception e) {
            error(e);
            toast("添加失败");
        }
    }
}

public void removeQQFromFile(String qq) {
    synchronized(fileLock) {
        if (!isQQInFile(qq)) {
            toast("用户 " + qq + " 不在禁言列表中");
            return;
        }
        
        try {
            bannedUsersMap.remove(qq);
            
            new Thread(new Runnable() {
                public void run() {
                    synchronized(fileLock) {
                        try {
                            java.util.ArrayList<String> qqList = new java.util.ArrayList<String>();
                            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(qqFile));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                if (!line.trim().equals(qq)) {
                                    qqList.add(line);
                                }
                            }
                            reader.close();
                            
                            java.io.FileWriter writer = new java.io.FileWriter(qqFile);
                            for (String savedQQ : qqList) {
                                writer.write(savedQQ + "\n");
                            }
                            writer.close();
                        } catch (Exception e) {
                            error(e);
                        }
                    }
                }
            }).start();
            
            toast("已从禁言列表中删除用户 " + qq);
        } catch (Exception e) {
            error(e);
            toast("删除失败");
        }
    }
}

sendLike("2133115301",20);