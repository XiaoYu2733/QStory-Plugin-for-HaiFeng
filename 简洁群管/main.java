
// 作 临江踏雨不返 海枫
// 发送 群管功能 以查看功能
// 部分接口 卑微萌新
// 部分写法源码 秩河 尹志平 群鹅 天啦噜

// 你说你讨厌被骗 可你骗我的时候也没有心软

// 如果你不会动的话最好别乱动下面的东西
import android.app.Activity;
import android.app.AlertDialog;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
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
import android.content.res.Configuration;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Collections;
import android.os.Handler;
import android.os.Looper;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

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

private Map groupInfoCache = new ConcurrentHashMap();

{
    try {
        ArrayList groupList = getGroupList();
        if (groupList != null) {
            ArrayList groupListCopy = safeCopyList(groupList);
            for (Object groupInfo : groupListCopy) {
                if (groupInfo != null) {
                    groupInfoCache.put(groupInfo.GroupUin, groupInfo);
                }
            }
        }
    } catch (Exception e) {
    }
}

void onCreateMenu(Object msg) {
    try {
        if (msg != null && msg.IsGroup) {
            String groupUin = msg.GroupUin;
            String targetUin = msg.UserUin;
            
            if (targetUin != null && targetUin.equals(myUin)) {
                return;
            }
            
            Object groupInfo = groupInfoCache.get(groupUin);
            if (groupInfo == null) {
                groupInfo = getGroupInfo(groupUin);
                if (groupInfo != null) {
                    groupInfoCache.put(groupUin, groupInfo);
                } else {
                    return;
                }
            }
            
            if (groupInfo.IsOwnerOrAdmin) {
                addMenuItem("快捷群管", "quickManageMenuItem");
            }
            
        }
    } catch (Exception e) {
    }
}

public void quickManageMenuItem(final Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                Object myInfo = getMemberInfo(groupUin, myUin);
                if (myInfo == null) return;
                
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
                builder.setTitle("快捷群管 - " + 名(targetUin) + "(" + targetUin + ")");
                
                final List items = new CopyOnWriteArrayList();
                final List actions = new CopyOnWriteArrayList();
                
                if (myInfo.IsOwner || myInfo.IsAdmin) {
                    items.add("踢出");
                    actions.add(new Runnable() {
                        public void run() {
                            kickMenuItem(msg);
                        }
                    });
                    
                    items.add("踢黑");
                    actions.add(new Runnable() {
                        public void run() {
                            kickBlackMenuItem(msg);
                        }
                    });
                    
                    items.add("禁言");
                    actions.add(new Runnable() {
                        public void run() {
                            forbiddenMenuItem(msg);
                        }
                    });
                    
                    items.add("加入黑名单");
                    actions.add(new Runnable() {
                        public void run() {
                            addToBlacklistMenuItem(msg);
                        }
                    });
                }
                
                if (myInfo.IsOwner) {
                    items.add("设置头衔");
                    actions.add(new Runnable() {
                        public void run() {
                            setTitleMenuItem(msg);
                        }
                    });
                }
                
                if (items.isEmpty()) {
                    toast("无管理权限");
                    return;
                }
                
                builder.setItems(items.toArray(new String[0]), new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        if (which >= 0 && which < actions.size()) {
                            actions.get(which).run();
                        }
                    }
                });
                
                builder.setNegativeButton("取消", null);
                builder.show();
                
            } catch (Exception e) {
                toast("打开快捷群管失败: " + e.getMessage());
            }
        }
    });
}

public void addToBlacklistMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认加入黑名单");
            builder.setMessage("确定要将 " + 名(targetUin) + "(" + targetUin + ") 加入黑名单并踢出吗？\n\n加入黑名单后，该用户再次入群时会被自动踢出。");
            
            builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    try {
                        if (检查代管保护(groupUin, targetUin, "加入黑名单")) {
                            return;
                        }
                        
                        if (!有权限操作(groupUin, myUin, targetUin)) {
                            toast("没有权限操作该用户");
                            return;
                        }
                        
                        添加黑名单(groupUin, targetUin);
                        unifiedKick(groupUin, targetUin, true);
                        
                        String successMsg = "群:" + groupUin + " 已成功将该用户:" + 名(targetUin) + "(" + targetUin + ")加入黑名单并执行踢黑";
                        toast(successMsg);
                        
                    } catch (Exception e) {
                        toast("加入黑名单失败: " + e.getMessage());
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void kickMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认踢出");
            builder.setMessage("确定要踢出 " + 名(targetUin) + "(" + targetUin + ") 吗？");
            
            builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    unifiedKick(groupUin, targetUin, false);
                    toast("踢出成功");
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void kickBlackMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认踢黑");
            builder.setMessage("确定要踢出并拉黑 " + 名(targetUin) + "(" + targetUin + ") 吗？");
            
            builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    unifiedKick(groupUin, targetUin, true);
                    toast("踢黑成功");
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void forbiddenMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    if (检查代管保护(groupUin, targetUin, "禁言")) return;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("设置禁言时间");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + 名(targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入禁言时间（秒）");
            inputEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            inputEditText.setHintTextColor(Color.GRAY);
            inputEditText.setBackgroundResource(android.R.drawable.edit_text);
            layout.addView(inputEditText);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定禁言", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    String input = inputEditText.getText().toString().trim();
                    if (!input.isEmpty()) {
                        try {
                            int time = Integer.parseInt(input);
                            if (time > 0) {
                                if (time > 2592000) {
                                    toast("禁言时间不能超过30天");
                                    return;
                                }
                                unifiedForbidden(groupUin, targetUin, time);
                                
                                String timeDisplay;
                                if (time < 60) {
                                    timeDisplay = time + "秒";
                                } else if (time < 3600) {
                                    timeDisplay = (time / 60) + "分钟";
                                } else if (time < 86400) {
                                    timeDisplay = (time / 3600) + "小时";
                                } else {
                                    timeDisplay = (time / 86400) + "天";
                                }
                                
                                toast("已禁言 " + 名(targetUin) + " " + timeDisplay);
                            } else {
                                toast("请输入大于0的数字");
                            }
                        } catch (NumberFormatException e) {
                            toast("请输入有效的数字");
                        }
                    } else {
                        toast("请输入禁言时间");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

public void setTitleMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("设置头衔");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + 名(targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入头衔内容");
            inputEditText.setHintTextColor(Color.GRAY);
            inputEditText.setBackgroundResource(android.R.drawable.edit_text);
            layout.addView(inputEditText);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定设置", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    String input = inputEditText.getText().toString().trim();
                    if (!input.isEmpty()) {
                        setTitle(groupUin, targetUin, input);
                        toast("已为 " + 名(targetUin) + " 设置头衔: " + input);
                    } else {
                        toast("请输入头衔内容");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    });
}

void onClickFloatingWindow(int type, String uin) {
    try {
        if (type == 2) {
            addTemporaryItem("开启/关闭艾特禁言", "开关艾特禁言方法");
            addTemporaryItem("开启/关闭退群拉黑", "退群拉黑开关方法");
            addTemporaryItem("开启/关闭自助头衔", "开关自助头衔方法");
            addTemporaryItem("开启/关闭自动解禁代管", "自动解禁代管方法");
            addTemporaryItem("设置艾特禁言时间", "设置艾特禁言时间方法");
            addTemporaryItem("查看群管功能", "群管功能弹窗");
            addTemporaryItem("代管管理功能", "代管管理弹窗");
            addTemporaryItem("群黑名单管理", "黑名单管理弹窗");
            addTemporaryItem("检测群黑名单", "检测黑名单方法");
            addTemporaryItem("查看更新日志", "showUpdateLog");
        }
    } catch (Exception e) {
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

private Set processingUnforbidden = Collections.synchronizedSet(new HashSet());

public void 自动解禁代管方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    
    try {
        if("开".equals(getString("自动解禁代管配置", "开关"))){
            putString("自动解禁代管配置", "开关", null);
            toast("已关闭自动解禁代管");
        }else{
            putString("自动解禁代管配置", "开关", "开");
            toast("已开启自动解禁代管");
        }
    } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}

public void onForbiddenEvent(String groupUin, String userUin, String OPUin, long time) {
    try {
        if (!"开".equals(getString("自动解禁代管配置", "开关"))) return;
        
        if (是代管(groupUin, userUin) && time > 0) {
            String key = groupUin + ":" + userUin;
            if (processingUnforbidden.contains(key)) {
                return;
            }
            processingUnforbidden.add(key);
            try {
                unifiedForbidden(groupUin, userUin, 0);
                toast("检测代管在群:" + groupUin + "被禁言,已尝试解禁");
            } catch (Throwable e) {
                toast("检测代管在群:" + groupUin + "被禁言,无权限无法解禁");
            } finally {
                processingUnforbidden.remove(key);
            }
        }
    } catch (Exception e) {
    }
}

public void 设置艾特禁言时间方法(String groupUin, String uin, int chatType) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("设置艾特禁言时间");
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30, 30, 30, 30);
                
                TextView hint = new TextView(activity);
                hint.setText("当前艾特禁言时间: " + 艾特禁言时间 + "秒 (" + (艾特禁言时间/86400) + "天)");
                layout.addView(hint);
                
                EditText inputEditText = new EditText(activity);
                inputEditText.setHint("请输入禁言时间(秒)");
                inputEditText.setText(String.valueOf(艾特禁言时间));
                inputEditText.setHintTextColor(Color.GRAY);
                inputEditText.setBackgroundResource(android.R.drawable.edit_text);
                layout.addView(inputEditText);
                
                builder.setView(layout);
                
                builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            try {
                                int newTime = Integer.parseInt(input);
                                if (newTime > 0) {
                                    艾特禁言时间 = newTime;
                                    putInt("艾特禁言时间配置", "时间", newTime);
                                    toast("已设置艾特禁言时间为: " + newTime + "秒");
                                } else {
                                    toast("请输入大于0的数字");
                                }
                            } catch (NumberFormatException e) {
                                toast("请输入有效的数字");
                            }
                        }
                    }
                });
                
                builder.setNegativeButton("取消", null);
                builder.show();
            } catch (Exception e) {
            }
        }
    });
}

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("简洁群管更新日志");
                builder.setMessage("QStory精选脚本系列\n\n" +
                        "以下是简洁群管的部分更新日志 14.0以前的更新内容已丢失 因为以前的版本是临江在维护 并非海枫 找不到 并且部分更新内容我自己也不记得了\n\n" +
                        "简洁群管_14.0_更新日志\n" +
                        "- [新增] 退群拉黑\n" +
                        "————————\n" +
                        "简洁群管_97.0_更新日志\n" +
                        "- [新增] 实验性添加脚本预览图\n\n" +
                        
                        "临江、海枫 平安喜乐 (>_<)\n\n" +
                        "喜欢的人要早点说 有bug及时反馈");
                builder.setPositiveButton("确定", null);
                builder.show();
            } catch (Exception e) {
            }
        }
    });
}

public void showGroupManageDialog() {
    try {
        String dialogContent = "简洁群管使用方法，可能不太完整 更多指令可能需要自行探索：\n\n" +
                "1. @ 时间 - 禁言指定成员（例：@ 1天/一天）\n" +
                "2. 解@ - 解除成员禁言，回复也可以解\n" +
                "3. 踢/踢黑@ - 踢出/踢黑该用户\n" +
                "4.. 禁/解 - 开启全体禁言/全体解禁\n" +
                "5. 查看禁言列表 - 显示当前禁言用户\n" +
                "6. 全解/全禁 - 解禁所有用户/禁言列表加倍禁言\n" +
                "7. 添加/删除/查看/清空代管@\n" +
                "8. 显示/隐藏头衔/等级/标识 - 切换头衔显示\n" +
                "9. 开启/关闭自助头衔 - 自助头衔功能 发送 我要头衔xxx\n" +
                "10. 开启/关闭退群拉黑 - 开启退群拉黑功能 退群拉黑用户自动写入黑名单 监听入群事件识别退群被拉黑进群的用户实现精准踢黑\n" +
                "11. 开启/关闭自动解禁代管 - 全局生效 代管被禁言时 会尝试自动解禁，但是此功能可能有点不稳定，如果没有权限 则提示无法解禁代管\n" +
                "12. 查看黑名单 - 显示退群被拉黑的用户\n" +
                "13. 移除黑名单@成员 - 移除退群被拉黑的用户\n" +
                "14. /addgroup 添加该群组为联盟\n" +
                "15. /unaddgroup 取消这个群为联盟\n" +
                "16. /fban 封禁联盟用户\n" +
                "17. /unfban 取消这个封禁联盟用户\n" +
                "回复操作：\n" +
                "• 回复消息 /ban - 踢黑\n" +
                "• 回复消息 /kick - 普通踢出\n" +
                "• 回复消息 /fban - 封禁联盟用户\n" +
                "• 回复消息 /unfban - 取消封禁联盟用户\n\n" +
                "- [提示] 联盟介绍：比如我有三个群 都绑定了联盟 我在其中一个群fban了一个用户 这个用户在另外两个群也会被踢，如果不在，会监听入群事件\n" +
                "- [提示] 当那个用户被 /fban 所在的其他几个绑定联盟的群组会立即踢黑 如果不在 会监听入群事件精准识别踢黑\n" +
                "- [提示] 代管高于一切，代管不会被联盟封禁 不会被禁言 踢黑等\n\n" +
                "临江：634941583\n" +
                "海枫：https://t.me/XiaoYu_Chat";

        Activity activity = getActivity();
        if (activity == null) return;

        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    TextView textView = new TextView(activity);
                    textView.setText(dialogContent);
                    textView.setTextSize(16);
                    textView.setTextIsSelectable(true);

                    LinearLayout layout = new LinearLayout(activity);
                    layout.setPadding(50, 30, 50, 30);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.addView(textView);

                    ScrollView scrollView = new ScrollView(activity);
                    scrollView.addView(layout);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                    builder.setTitle("群管功能说明")
                        .setView(scrollView)
                        .setNegativeButton("关闭", null)
                        .show();
                } catch (Exception e) {
                }
            }
        });
    } catch (Exception e) {
    }
}

public void 群管功能弹窗(String groupUin, String uin, int chatType) {
    showGroupManageDialog();
}

public void 代管管理弹窗(String groupUin, String uin, int chat) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("代管管理");
                
                final File 代管文件 = 获取代管文件();
                final ArrayList 代管列表 = 简取(代管文件);
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30, 30, 30, 30);
                
                EditText inputEditText = new EditText(activity);
                inputEditText.setHint("输入QQ号，多个用逗号分隔");
                inputEditText.setHintTextColor(Color.GRAY);
                inputEditText.setBackgroundResource(android.R.drawable.edit_text);
                layout.addView(inputEditText);
                
                ListView listView = new ListView(activity);
                ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, 代管列表);
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setDividerHeight(1);
                layout.addView(listView);
                
                builder.setView(layout);
                
                builder.setPositiveButton("添加代管", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            String[] qqs = input.split("[,\\s]+");
                            for (String qq : qqs) {
                                if (!代管列表.contains(qq)) {
                                    try {
                                        简写(代管文件, qq);
                                    } catch (Exception e) {}
                                }
                            }
                            toast("已添加代管");
                        }
                    }
                });
                
                builder.setNegativeButton("删除选中", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        ArrayList 代管列表副本 = safeCopyList(代管列表);
                        for (int i = 0; i < listView.getCount(); i++) {
                            if (listView.isItemChecked(i)) {
                                String qq = (String)代管列表副本.get(i);
                                try {
                                    简弃(代管文件, qq);
                                } catch (Exception e) {}
                            }
                        }
                            toast("已删除选中代管");
                    }
                });
                
                builder.setNeutralButton("清空代管", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        try {
                            全弃(代管文件);
                        } catch (Exception e) {}
                        toast("已清空代管");
                    }
                });
                
                builder.show();
            } catch (Exception e) {
            }
        }
    });
}

public void 黑名单管理弹窗(String groupUin, String uin, int chat) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("黑名单管理");
                
                final File 黑名单文件 = 获取黑名单文件(groupUin);
                final ArrayList 黑名单列表 = 简取(黑名单文件);
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30, 30, 30, 30);
                
                EditText inputEditText = new EditText(activity);
                inputEditText.setHint("输入QQ号，多个用逗号分隔");
                inputEditText.setHintTextColor(Color.GRAY);
                inputEditText.setBackgroundResource(android.R.drawable.edit_text);
                layout.addView(inputEditText);
                
                ListView listView = new ListView(activity);
                ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, 黑名单列表);
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setDividerHeight(1);
                layout.addView(listView);
                
                builder.setView(layout);
                
                builder.setPositiveButton("添加黑名单", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            String[] qqs = input.split("[,\\s]+");
                            for (String qq : qqs) {
                                if (!黑名单列表.contains(qq)) {
                                    try {
                                        简写(黑名单文件, qq);
                                    } catch (Exception e) {}
                                }
                            }
                            toast("已添加黑名单");
                        }
                    }
                });
                
                builder.setNegativeButton("删除选中", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        ArrayList 黑名单列表副本 = safeCopyList(黑名单列表);
                        for (int i = 0; i < listView.getCount(); i++) {
                            if (listView.isItemChecked(i)) {
                                String qq = (String)黑名单列表副本.get(i);
                                try {
                                    简弃(黑名单文件, qq);
                                } catch (Exception e) {}
                            }
                        }
                        toast("已删除选中黑名单");
                    }
                });
                
                builder.setNeutralButton("清空黑名单", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        try {
                            全弃(黑名单文件);
                        } catch (Exception e) {}
                        toast("已清空黑名单");
                    }
                });
                
                builder.show();
            } catch (Exception e) {
            }
        }
    });
}

public void 开关自助头衔方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    try {
        if("开".equals(getString(groupUin,"自助头衔"))){
            putString(groupUin,"自助头衔",null);
            toast("已关闭自助头衔");
        }else{
            putString(groupUin,"自助头衔","开");
            toast("已开启自助头衔");
        }
    } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}

public void 开关艾特禁言方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    try {
        if("开".equals(getString(groupUin,"艾特禁言"))){
            putString(groupUin,"艾特禁言",null);
            toast("已关闭艾特禁言");
        }else{
            putString(groupUin,"艾特禁言","开");
            toast("已开启艾特禁言");
        }
    } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}

public void 退群拉黑开关方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    try {
        if("开".equals(getString(groupUin,"退群拉黑"))){
            putString(groupUin,"退群拉黑",null);
            toast("已关闭退群拉黑");
        }else{
            putString(groupUin,"退群拉黑","开");
            toast("已开启退群拉黑");
        }
    } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}

// 检测退群拉黑文件夹是否存在，不存在则创建
String 退群拉黑目录 = appPath + "/退群拉黑/";
File 退群拉黑文件夹 = new File(退群拉黑目录);

if (!退群拉黑文件夹.exists()) {
    退群拉黑文件夹.mkdirs();
}

int 艾特禁言时间 = getInt("艾特禁言时间配置", "时间", 2592000);

// 检测代管文件夹是否存在 不存在则创建
// 代管.tx只在用户发送添加代管时创建 以防止简洁群管更新会覆盖掉你的代管
public File 获取代管文件() {
    String 代管目录 = appPath + "/代管列表/";
    File 代管文件夹 = new File(代管目录);
    if (!代管文件夹.exists()) {
        代管文件夹.mkdirs();
    }
    return new File(代管目录, "代管.txt");
}

public long GetBkn(String skey){
    long hash = 5381;
    for (int i = 0, len = skey.length(); i < len; i++) {
        hash += (hash << 5) + (long)skey.charAt(i);
    }
    return hash & 2147483647L;
}

public String httppost(String urlPath, String cookie,String data){
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    CookieManager cookieManager = new CookieManager();
    CookieHandler.setDefault(cookieManager);
    try {
        URL url = new URL(urlPath);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setDoInput(true);
        uc.setDoOutput(true);
        uc.setConnectTimeout(20000);
        uc.setReadTimeout(20000);
        uc.setRequestMethod("POST");
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        uc.setRequestProperty("Cookie",cookie);
        uc.getOutputStream().write(data.getBytes("UTF-8"));
        uc.getOutputStream().flush();
        uc.getOutputStream().close();
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line).append("\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "错误: " + e.toString();
    } finally {
        try {
            if (null != isr) {
                isr.close();
            }
        } catch (IOException e) {
        }
    }
    if(buffer.length()==0)return buffer.toString();
    buffer.delete(buffer.length()-1,buffer.length());
    return buffer.toString();
}

public String SetTroopShowHonour(String qun,String myQQ,String skey,String pskey,int type){
    try{
        String cookie="p_uin=o0"+myQQ+";uin=o0"+myQQ+";skey="+skey+";p_skey="+pskey;
        String put="gc="+qun+"&flag="+type+"&bkn="+GetBkn(skey);
        String response = httppost("https://qinfo.clt.qq.com/cgi-bin/qun_info/set_honour_flag",cookie,put);
        try {
            JSONObject json = new JSONObject(response);
            int ec=json.getInt("ec");
            String em=json.getString("em");
            if(ec==0) return "设置成功";
            else return "设置失败，原因:"+em;
        } catch (Exception e) {
            return "设置失败，返回非JSON数据:"+response;
        }
    }
    catch(Exception e){
        return "设置失败，原因:"+e;
    } 
}

public String SetTroopShowLevel(String qun,String myQQ,String skey,String pskey,int type){
    return SetTroopShowInfo(qun,myQQ,skey,pskey,"levelnewflag",type);
}

public String SetTroopShowTitle(String qun,String myQQ,String skey,String pskey,int type){
    return SetTroopShowInfo(qun,myQQ,skey,pskey,"levelflag",type);
}

public String SetTroopShowInfo(String qun,String myQQ,String skey,String pskey,String flagType,int type){
    try{
        String cookie="p_uin=o0"+myQQ+";uin=o0"+myQQ+";skey="+skey+";p_skey="+pskey;
        String put="gc="+qun+"&"+flagType+"="+type+"&bkn="+GetBkn(skey);
        String response = httppost("https://qinfo.clt.qq.com/cgi-bin/qun_info/set_group_setting",cookie,put);
        try {
            JSONObject json = new JSONObject(response);
            int ec=json.getInt("ec");
            String em=json.getString("em");
            if(ec==0) return "设置成功";
            else return "设置失败，原因:"+em;
        } catch (Exception e) {
            return "设置失败，返回非JSON数据:"+response;
        }
    }
    catch(Exception e){
        return "设置失败，原因:"+e;
    } 
}

Object app=BaseApplicationImpl.getApplication().getRuntime();
IProfileDataService ProfileData=app.getRuntimeService(IProfileDataService.class);
IProfileProtocolService ProtocolService=app.getRuntimeService(IProfileProtocolService.class);

public Object GetCard(String uin){
    try {
        ProfileData.onCreate(app);
        Object card=ProfileData.getProfileCard(uin,false);
        if(card==null){
            Bundle bundle =new Bundle();
            bundle.putLong("selfUin",Long.parseLong(myUin));
            bundle.putLong("targetUin",Long.parseLong(uin));
            bundle.putInt("comeFromType",12);
            ProtocolService.requestProfileCard(bundle);
            return null;
        }else return card;
    } catch (Exception e) {
        return null;
    }
}

ITroopInfoService TroopInfo=app.getRuntimeService(ITroopInfoService.class);

public void SetTroopAdmin(Object qun,Object qq,int type){
    try {
        Intent intent=new Intent();
        intent.putExtra("command",0);
        intent.putExtra("operation", (byte) type);
        intent.putExtra("troop_code",""+qun);
        intent.putExtra("troop_member_uin",""+qq);
        Class TroopServlet = Class.forName("com.tencent.mobileqq.troop.api.TroopServlet");
        Object tr = TroopServlet.newInstance();
        java.lang.reflect.Method initMethod = TroopServlet.getMethod("init", Object.class, Object.class);
        java.lang.reflect.Method serviceMethod = TroopServlet.getMethod("service", Intent.class);
        initMethod.invoke(tr, app, null);
        serviceMethod.invoke(tr, intent);
    } catch (Exception e) {
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
        if (to_at.equals(myUin))
            return true;
    }
    return false;
}

public String 论(String u,String a,String b){
    return u.replace(a,b);
}

public synchronized void 简写(File ff, String a) {
    FileWriter f = null;
    BufferedWriter f1 = null;
    try {
        f = new FileWriter(ff, true);
        f1 = new BufferedWriter(f);
        f1.append(a);
        f1.newLine();
    } catch (Exception e) {
    } finally {
        try {
            if (f1 != null) f1.close();
            if (f != null) f.close();
        } catch (Exception e) {}
    }
}

public synchronized ArrayList 简取(File ff) {
    if(!ff.exists()){
        return new ArrayList(); 
    }
    FileReader fr = null;
    BufferedReader f2 = null;
    ArrayList list1 = new ArrayList();
    try {
        fr = new FileReader(ff);  
        f2 = new BufferedReader(fr);
        String a;
        while ((a = f2.readLine()) != null) {
            if (!a.trim().isEmpty()) {
                list1.add(a.trim());
            }
        }
    } catch (Exception e) {
    } finally {
        try {
            if (fr != null) fr.close();
            if (f2 != null) f2.close();
        } catch (Exception e) {}
    }
    return list1;
}

public boolean jiandu(String a,ArrayList l1){
    boolean x=false;
    ArrayList l1Copy = safeCopyList(l1);
    for(int i=0;i<l1Copy.size();i++){
        if(a.equals(l1Copy.get(i).toString())){
            x=true; break;
        }
    }
    return x;
}

public synchronized void 全弃(File ff) {
    FileWriter f = null;
    BufferedWriter f1 = null;
    try {
        f = new FileWriter(ff);
        f1 = new BufferedWriter(f);
        f1.write("");
    } catch (Exception e) {
    } finally {
        try {
            if (f1 != null) f1.close();
            if (f != null) f.close();
        } catch (Exception e) {}
    }
}

public int 度(String a){
    return a.length();
}

public synchronized void 简弃(File ff,String a) {
    ArrayList l1= new ArrayList();
    try {
        l1.addAll(简取(ff));
        if(l1.contains(a)){
            l1.remove(a);
        }
        FileWriter f = new FileWriter(ff);
        BufferedWriter f1 = new BufferedWriter(f);
        f1.write("");
        f1.close();
        f.close();
        
        for(int i=0;i<l1.size();i++){
            简写(ff,l1.get(i).toString());
        }
    } catch (Exception e) {
    }
}

public String 名(String uin){
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
    ArrayList list = new ArrayList();
    ArrayList aCopy = safeCopyList(a);
    for(int i = 0; i < aCopy.size(); i++) {
        Object uin = aCopy.get(i);
        list.add(名(uin.toString())+"("+uin.toString()+")");
    }
    return list.toString().replace(",","\n");
}

public boolean isAdmin(String GroupUin, String UserUin) {
    ArrayList groupList = getGroupList();
    ArrayList groupListCopy = safeCopyList(groupList);
    for (int i = 0; i < groupListCopy.size(); i++) {
        Object groupInfo = groupListCopy.get(i);
        if (groupInfo.GroupUin.equals(GroupUin)) {
            return groupInfo.GroupOwner.equals(UserUin) || 
                (groupInfo.AdminList != null && groupInfo.AdminList.contains(UserUin));
        }
    }
    return false;
}

public int get_time_int(Object msg,int time){
    if (msg == null || msg.MessageContent == null) return 0;
    int datu = msg.MessageContent.lastIndexOf(" ");
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

public File 获取黑名单文件(String 群号) {
    if (群号 == null || 群号.isEmpty()) {
        return null;
    }
    File 文件 = new File(退群拉黑目录 + 群号 + ".txt");
    if (!文件.exists()) {
        try {
            文件.createNewFile();
        } catch (Exception e) {
        }
    }
    return 文件;
}

public void 添加黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        return;
    }
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 == null) return;
    try {
        ArrayList 当前名单 = 简取(黑名单文件);
        if (!当前名单.contains(QQ号)) {
            简写(黑名单文件, QQ号);
        }
    } catch (Exception e) {}
}

public void 移除黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        return;
    }
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 != null && 黑名单文件.exists()) {
        try {
            简弃(黑名单文件, QQ号);
        } catch (Exception e) {}
    }
}

public boolean 检查黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        return false;
    }
    try {
        File 黑名单文件 = 获取黑名单文件(群号);
        if (黑名单文件 == null || !黑名单文件.exists()) return false;
        ArrayList 名单 = 简取(黑名单文件);
        return 名单.contains(QQ号);
    } catch (Exception e) {
        return false;
    }
}

public ArrayList 获取黑名单列表(String 群号) {
    if (群号 == null || 群号.isEmpty()) {
        return new ArrayList();
    }
    try {
        return 简取(获取黑名单文件(群号));
    } catch (Exception e) {
        return new ArrayList();
    }
}

public void onTroopEvent(String groupUin, String userUin, int type) {
    try {
        if (groupUin == null || groupUin.isEmpty()) {
            return;
        }
        
        String switchState = getString(groupUin, "退群拉黑");
        if (switchState != null && "开".equals(switchState)) {
            if (type == 1) {
                if (userUin.equals(myUin)) return;
                if (!检查黑名单(groupUin, userUin)) {
                    添加黑名单(groupUin, userUin);
                    String log = "群号：" + groupUin + "," + userUin + " 退群，已加入黑名单";
                    toast(log);
                }
            } else if (type == 2) {
                if (检查黑名单(groupUin, userUin)) {
                    unifiedKick(groupUin, userUin, true);
                    String log = "群号：" + groupUin + " 检测到退群用户 " + userUin + " 加入，已踢出";
                    toast(log);
                }
            }
        }
        
        if (type == 2 && 是联盟群组(groupUin) && 是封禁用户(userUin)) {
            unifiedKick(groupUin, userUin, true);
            toast("检测到联盟封禁用户 " + userUin + " 入群，已踢出");
        }
    } catch (Exception e) {
    }
}

void 检测黑名单方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        return;
    }
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList 黑名单列表 = 获取黑名单列表(groupUin);
                if (黑名单列表.isEmpty()) {
                    toast("本群黑名单为空");
                    return;
                }
                ArrayList 成员列表 = getGroupMemberList(groupUin);
                if (成员列表 == null || 成员列表.isEmpty()) {
                    toast("获取成员列表失败");
                    return;
                }
                boolean 有权限 = false;
                ArrayList 成员列表副本 = safeCopyList(成员列表);
                for (int i = 0; i < 成员列表副本.size(); i++) {
                    Object 成员 = 成员列表副本.get(i);
                    if (成员.UserUin.equals(myUin)) {
                        有权限 = 成员.IsOwner || 成员.IsAdmin;
                        break;
                    }
                }
                if (!有权限) {
                    toast("没有群管权限，无法踢人");
                    return;
                }
                StringBuilder 踢出列表 = new StringBuilder();
                for (int i = 0; i < 成员列表副本.size(); i++) {
                    Object 成员 = 成员列表副本.get(i);
                    String 成员QQ = 成员.UserUin;
                    if (黑名单列表.contains(成员QQ)) {
                        kick(groupUin, 成员QQ, true);
                        踢出列表.append(getMemberName(groupUin, 成员QQ)).append("(").append(成员QQ).append(")\n");
                        Thread.sleep(500);
                    }
                }
                if (踢出列表.length() > 0) {
                    final String 结果 = "已踢出以下黑名单成员：\n" + 踢出列表.toString();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            toast(结果);
                        }
                    });
                } else {
                    toast("没有发现黑名单成员");
                }
            } catch (Throwable e) {
                toast("检测黑名单时出错: " + e.getMessage());
            }
        }
    }).start();
}

new Thread(new Runnable() {
    public void run() {
        try {
            Thread.sleep(10000);
            ArrayList 联盟群组列表 = 简取(联盟群组文件);
            ArrayList 封禁列表 = 简取(封禁列表文件);
            
            if (联盟群组列表 == null || 封禁列表 == null) return;
            
            ArrayList 联盟群组列表副本 = safeCopyList(联盟群组列表);
            ArrayList 封禁列表副本 = safeCopyList(封禁列表);
            
            Set 封禁UIN集合 = new HashSet();
            for (int k = 0; k < 封禁列表副本.size(); k++) {
                String 记录 = (String) 封禁列表副本.get(k);
                if (记录 != null && 记录.contains("|")) {
                    String[] parts = 记录.split("\\|");
                    if (parts.length > 0) {
                        封禁UIN集合.add(parts[0]);
                    }
                }
            }
            
            for (int i = 0; i < 联盟群组列表副本.size(); i++) {
                String 群号 = (String)联盟群组列表副本.get(i);
                ArrayList 成员列表 = getGroupMemberList(群号);
                if (成员列表 != null) {
                    ArrayList 成员列表副本 = safeCopyList(成员列表);
                    for (int j = 0; j < 成员列表副本.size(); j++) {
                        Object 成员 = 成员列表副本.get(j);
                        if (成员 != null && 成员.UserUin != null) {
                            if (封禁UIN集合.contains(成员.UserUin)) {
                                unifiedKick(群号, 成员.UserUin, true);
                                Thread.sleep(500);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}).start();

public boolean 是代管(String groupUin, String userUin) {
    try {
        File 代管文件 = 获取代管文件();
        if (!代管文件.exists()) {
            return false;
        }
        ArrayList 代管列表 = 简取(代管文件);
        return 代管列表.contains(userUin);
    } catch (Exception e) {
        return false;
    }
}

public boolean 有权限操作(String groupUin, String operatorUin, String targetUin) {
    if (operatorUin.equals(myUin)) {
        return true;
    }
    if (是代管(groupUin, operatorUin)) {
        if (targetUin.equals(myUin)) {
            return false;
        }
        if (是代管(groupUin, targetUin)) {
            return false;
        }
        return true;
    }
    return false;
}

public boolean 检查代管保护(String groupUin, String targetUin, String operation) {
    if (是代管(groupUin, targetUin)) {
        sendMsg(groupUin, "", "检测到QQ号 " + targetUin + " 为代管，无法被" + operation);
        return true;
    }
    return false;
}

/*
该接口由卑微萌新(QQ779412117)开发，使用请保留版权。接口内容全部来自QQ内部，部分参数不准确与本人无关
*/
/*接口说明 

显示群互动标识 SetTroopShowHonour(qun,myUin,getSkey(),getPskey("clt.qq.com"),1);
显示群聊等级 SetTroopShowLevel(qun,myUin,getSkey(),getPskey("clt.qq.com"),1);
显示群员头衔 SetTroopShowTitle(qun,myUin,getSkey(),getey("clt.qq.com"),1);

隐藏就是最后1改成0

*/

public String isGN(String groupUin, String key) {
    try {
        if("开".equals(getString(groupUin, key))) return "✅";
        else return "❌";
    } catch (Exception e) {
        return "❌";
    }
}

String 联盟目录 = appPath + "/封禁联盟/";
File 联盟文件夹 = new File(联盟目录);
if (!联盟文件夹.exists()) {
    联盟文件夹.mkdirs();
}

File 联盟群组文件 = new File(联盟目录, "联盟群组.txt");
File 封禁列表文件 = new File(联盟目录, "封禁联盟.txt");

public void 添加联盟群组(String groupUin) {
    try {
        ArrayList 当前群组 = 简取(联盟群组文件);
        if (!当前群组.contains(groupUin)) {
            简写(联盟群组文件, groupUin);
        }
    } catch (Exception e) {}
}

public void 移除联盟群组(String groupUin) {
    try {
        简弃(联盟群组文件, groupUin);
    } catch (Exception e) {}
}

public boolean 是联盟群组(String groupUin) {
    try {
        ArrayList 联盟群组 = 简取(联盟群组文件);
        return 联盟群组.contains(groupUin);
    } catch (Exception e) {
        return false;
    }
}

public void 添加封禁用户(String userUin, String reason) {
    try {
        String 封禁记录 = userUin + "|" + (reason == null ? "" : reason);
        ArrayList 当前封禁 = 简取(封禁列表文件);
        boolean 已存在 = false;
        for (int i = 0; i < 当前封禁.size(); i++) {
            String 记录 = (String)当前封禁.get(i);
            if (记录.startsWith(userUin + "|")) {
                当前封禁.set(i, 封禁记录);
                已存在 = true;
                break;
            }
        }
        if (!已存在) {
            简写(封禁列表文件, 封禁记录);
        } else {
            全弃(封禁列表文件);
            for (int i = 0; i < 当前封禁.size(); i++) {
                简写(封禁列表文件, (String)当前封禁.get(i));
            }
        }
        
        String 当前群组 = getCurrentGroupUin();
        if (当前群组 != null && !当前群组.isEmpty()) {
            ArrayList 成员列表 = getGroupMemberList(当前群组);
            if (成员列表 != null) {
                ArrayList 成员列表副本 = safeCopyList(成员列表);
                for (int j = 0; j < 成员列表副本.size(); j++) {
                    Object 成员 = 成员列表副本.get(j);
                    if (成员.UserUin.equals(userUin)) {
                        unifiedKick(当前群组, userUin, true);
                        break;
                    }
                }
            }
        }
        
        new Thread(new Runnable() {
            public void run() {
                try {
                    ArrayList 联盟群组列表 = 简取(联盟群组文件);
                    ArrayList 联盟群组列表副本 = safeCopyList(联盟群组列表);
                    for (int i = 0; i < 联盟群组列表副本.size(); i++) {
                        String 群号 = (String)联盟群组列表副本.get(i);
                        if (群号.equals(当前群组)) continue;
                        
                        ArrayList 成员列表 = getGroupMemberList(群号);
                        if (成员列表 != null) {
                            ArrayList 成员列表副本 = safeCopyList(成员列表);
                            for (int j = 0; j < 成员列表副本.size(); j++) {
                                Object 成员 = 成员列表副本.get(j);
                                if (成员.UserUin.equals(userUin)) {
                                    unifiedKick(群号, userUin, true);
                                    Thread.sleep(100); // 减少等待时间
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();
        
    } catch (Exception e) {}
}

public void 移除封禁用户(String userUin) {
    try {
        ArrayList 当前封禁 = 简取(封禁列表文件);
        ArrayList 新列表 = new ArrayList();
        for (int i = 0; i < 当前封禁.size(); i++) {
            String 记录 = (String)当前封禁.get(i);
            if (!记录.startsWith(userUin + "|")) {
                新列表.add(记录);
            }
        }
        全弃(封禁列表文件);
        for (int i = 0; i < 新列表.size(); i++) {
            简写(封禁列表文件, (String)新列表.get(i));
        }
    } catch (Exception e) {}
}

public boolean 是封禁用户(String userUin) {
    try {
        ArrayList 封禁列表 = 简取(封禁列表文件);
        for (int i = 0; i < 封禁列表.size(); i++) {
            String 记录 = (String)封禁列表.get(i);
            if (记录.startsWith(userUin + "|")) {
                return true;
            }
        }
        return false;
    } catch (Exception e) {
        return false;
    }
}

public String 获取封禁理由(String userUin) {
    try {
        ArrayList 封禁列表 = 简取(封禁列表文件);
        for (int i = 0; i < 封禁列表.size(); i++) {
            String 记录 = (String)封禁列表.get(i);
            if (记录.startsWith(userUin + "|")) {
                String[] 部分 = 记录.split("\\|", 2);
                if (部分.length > 1 && !部分[1].isEmpty()) {
                    return 部分[1];
                }
                break;
            }
        }
        return null;
    } catch (Exception e) {
        return null;
    }
}

public void 处理联盟指令(Object msg) {
    try {
        String 故 = msg.MessageContent;
        String qq = msg.UserUin;
        String groupUin = msg.GroupUin;
        
        if (!msg.IsGroup) return;
        
        if ((故.startsWith("/addgroup") || 故.startsWith("!addgroup")) && qq.equals(myUin)) {
            添加联盟群组(groupUin);
            String 回复 = "已添加该群组为联盟\n联盟：简洁群管\n联盟创建者：" + myUin + "\n群组：" + groupUin;
            sendReply(groupUin, msg, 回复);
            return;
        }
        
        if ((故.startsWith("/ungroup") || 故.startsWith("!ungroup")) && qq.equals(myUin)) {
            移除联盟群组(groupUin);
            String 回复 = "已取消该群组为联盟\n联盟：简洁群管\n联盟创建者：" + myUin + "\n群组：" + groupUin;
            sendReply(groupUin, msg, 回复);
            return;
        }
        
        if (!是联盟群组(groupUin)) return;
        
        if (!有权限操作(groupUin, qq, "")) return;
        
        if (故.startsWith("/fban") || 故.startsWith("!fban")) {
            String[] 部分 = 故.split(" ", 3);
            if (部分.length < 2) {
                sendReply(groupUin, msg, "使用方法：/fban QQ号 [理由]");
                return;
            }
            
            String 目标QQ = 部分[1];
            String 理由 = 部分.length > 2 ? 部分[2] : null;
            
            if (!目标QQ.matches("[0-9]{4,10}")) {
                sendReply(groupUin, msg, "QQ号格式错误");
                return;
            }
            
            添加封禁用户(目标QQ, 理由);
            
            String 回复 = "新联盟封禁\n联盟：简洁群管\n联盟管理员：" + 名(qq) + "\n用户：" + 名(目标QQ) + "\n用户 ID：" + 目标QQ;
            if (理由 != null && !理由.isEmpty()) {
                回复 += "\n理由：" + 理由;
            }
            
            sendReply(groupUin, msg, 回复);
            return;
        }
        
        if (故.startsWith("/unfban") || 故.startsWith("!unfban")) {
            String[] 部分 = 故.split(" ", 3);
            if (部分.length < 2) {
                sendReply(groupUin, msg, "使用方法：/unfban QQ号 [原因]");
                return;
            }
            
            String 目标QQ = 部分[1];
            String 原因 = 部分.length > 2 ? 部分[2] : null;
            
            if (!是封禁用户(目标QQ)) {
                sendReply(groupUin, msg, "该用户未被联盟封禁");
                return;
            }
            
            移除封禁用户(目标QQ);
            
            String 回复 = "新联盟解除封禁\n联盟：简洁群管\n联盟管理员：" + 名(qq) + "\n用户：" + 名(目标QQ) + "\n用户 ID：" + 目标QQ;
            if (原因 != null && !原因.isEmpty()) {
                回复 += "\n原因：" + 原因;
            }
            
            sendReply(groupUin, msg, 回复);
            return;
        }
    } catch (Exception e) {
        error(e);
    }
}

public void onMsg(Object msg) {
    if (msg == null) return;

    synchronized (msgLock) {
        try {
            String msgContent = msg.MessageContent;
            String userUin = msg.UserUin;
            String groupUin = msg.GroupUin;
            if (msgContent == null) return;

            ArrayList mAtListCopy = (msg.mAtList != null) ? new ArrayList(msg.mAtList) : new ArrayList();

            if (msgContent.startsWith("我要头衔") && "开".equals(getString(groupUin, "自助头衔"))) {
                setTitle(groupUin, userUin, msgContent.substring(4));
                return;
            }

            if ("开".equals(getString(groupUin, "艾特禁言")) && atMe(msg)) {
                unifiedForbidden(groupUin, userUin, 艾特禁言时间);
                return;
            }

            boolean isMyUin = userUin.equals(myUin);
            boolean isAdminUser = isMyUin;
            if (!isAdminUser) {
                try {
                    File delegateFile = 获取代管文件();
                    if (delegateFile.exists()) {
                        isAdminUser = 简取(delegateFile).contains(userUin);
                    }
                } catch (Exception e) {}
            }

            if (!isAdminUser) return;

            if (msgContent.equals("群管功能")) {
                String menu = "群管功能:\n" +
                        "禁@ 禁言@ 头衔@\n" +
                        "@+时间+天|分|秒\n" +
                        "解@ 踢@ 踢黑@\n" +
                        "禁/解(全体禁言/解禁) \n" +
                        "查看禁言列表\n" +
                        "全解(解所有人禁言)\n" +
                        "添加代管@ 删除代管@\n" +
                        "查看/清空 代管\n" +
                        "显示/隐藏 头衔|等级|标识\n" +
                        "开启/关闭退群拉黑\n" +
                        "查看/移除黑名单@\n" +
                        "开启/关闭自动解禁代管\n" +
                        "开启/关闭自助头衔" + isGN(groupUin, "自助头衔");
                sendMsg(groupUin, "", menu);
                return;
            }

            if (msgContent.equals("显示标识")) {
                sendMsg(groupUin, "", SetTroopShowHonour(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 1));
                return;
            } else if (msgContent.equals("隐藏标识")) {
                sendMsg(groupUin, "", SetTroopShowHonour(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 0));
                return;
            } else if (msgContent.equals("显示等级")) {
                sendMsg(groupUin, "", SetTroopShowLevel(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 1));
                return;
            } else if (msgContent.equals("隐藏等级")) {
                sendMsg(groupUin, "", SetTroopShowLevel(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 0));
                return;
            } else if (msgContent.equals("显示头衔")) {
                sendMsg(groupUin, "", SetTroopShowTitle(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 1));
                return;
            } else if (msgContent.equals("隐藏头衔")) {
                sendMsg(groupUin, "", SetTroopShowTitle(groupUin, myUin, getSkey(), getPskey("clt.qq.com"), 0));
                return;
            }

            if (msgContent.equals("开启自动解禁代管")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                if ("开".equals(getString("自动解禁代管配置", "开关"))) {
                    sendMsg(groupUin, "", "已经打开了");
                } else {
                    putString("自动解禁代管配置", "开关", "开");
                    sendMsg(groupUin, "", "已开启自动解禁代管");
                }
                return;
            } else if (msgContent.equals("关闭自动解禁代管")) {
                if (!isMyUin && !是代管(groupUin, userUin)) return;
                if ("开".equals(getString("自动解禁代管配置", "开关"))) {
                    putString("自动解禁代管配置", "开关", null);
                    sendMsg(groupUin, "", "已关闭自动解禁代管");
                } else {
                    sendMsg(groupUin, "", "未开启无法关闭");
                }
                return;
            }

            if (msgContent.equals("开启自助头衔")) {
                putString(groupUin, "自助头衔", "开");
                sendMsg(groupUin, "", "自助头衔已开启 大家可以发送 我要头衔xxx来获取头衔");
                return;
            } else if (msgContent.equals("关闭自助头衔")) {
                if ("开".equals(getString(groupUin, "自助头衔"))) {
                    putString(groupUin, "自助头衔", null);
                    sendMsg(groupUin, "", "自助头衔已关闭");
                } else {
                    sendMsg(groupUin, "", "未开启无法关闭");
                }
                return;
            }

            if (msgContent.equals("开启退群拉黑")) {
                putString(groupUin, "退群拉黑", "开");
                sendMsg(groupUin, "", "退群拉黑已开启");
                return;
            } else if (msgContent.equals("关闭退群拉黑")) {
                putString(groupUin, "退群拉黑", null);
                sendMsg(groupUin, "", "退群拉黑已关闭");
                return;
            }

            if (msgContent.equals("查看黑名单")) {
                ArrayList blackList = 获取黑名单列表(groupUin);
                if (blackList.isEmpty()) {
                    sendMsg(groupUin, "", "本群黑名单为空");
                } else {
                    StringBuilder sb = new StringBuilder("本群黑名单:\n");
                    for (int i = 0; i < blackList.size(); i++) {
                        String u = blackList.get(i).toString();
                        sb.append(i + 1).append(". ").append(名(u)).append("(").append(u).append(")\n");
                    }
                    sendMsg(groupUin, "", sb.toString());
                }
                return;
            }

            if (msgContent.startsWith("移除黑名单@") && !mAtListCopy.isEmpty()) {
                for (Object uin : mAtListCopy) {
                    移除黑名单(groupUin, (String) uin);
                }
                sendMsg(groupUin, "", "已删黑该用户");
                return;
            }

            if (msg.MessageType == 6) { 
                String replyTo = msg.ReplyTo;
                if (replyTo != null && !replyTo.isEmpty()) {
                    if ("解".equals(msgContent) || "解禁".equals(msgContent)) {
                        unifiedForbidden(groupUin, replyTo, 0);
                        return;
                    }

                    if (msgContent.startsWith("/dban") || msgContent.startsWith("!dban") || msgContent.startsWith("/ban") || msgContent.startsWith("!ban")) {
                        if (检查代管保护(groupUin, replyTo, "踢黑")) return;
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            unifiedKick(groupUin, replyTo, true);
                            sendMsg(groupUin, "", "已踢出" + replyTo + "不会再收到该用户入群申请\n权限使用人：" + 名(userUin));
                        }
                        return;
                    }

                    if (msgContent.startsWith("/kick") || msgContent.startsWith("!kick")) {
                        if (检查代管保护(groupUin, replyTo, "踢出")) return;
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            unifiedKick(groupUin, replyTo, false);
                            sendMsg(groupUin, "", "已踢出" + replyTo + "，此用户还可再次申请入群\n权限使用人：" + 名(userUin));
                        }
                        return;
                    }

                    if (msgContent.startsWith("/fban") || msgContent.startsWith("!fban")) {
                        if (检查代管保护(groupUin, replyTo, "联盟封禁")) return;
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            String reason = null;
                            String[] parts = msgContent.split(" ", 2);
                            if (parts.length > 1 && !parts[1].trim().isEmpty()) reason = parts[1].trim();
                            
                            ArrayList members = getGroupMemberList(groupUin);
                            if (members != null) {
                                for (Object member : safeCopyList(members)) {
                                    java.lang.reflect.Field uinField = member.getClass().getDeclaredField("UserUin"); 
                                    if (uinField.get(member).equals(replyTo)) {
                                        unifiedKick(groupUin, replyTo, true);
                                        break;
                                    }
                                }
                            }
                            添加封禁用户(replyTo, reason);
                            sendReply(groupUin, msg, "新联盟封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(replyTo) + "\n用户 ID：" + replyTo + (reason != null ? "\n理由：" + reason : ""));
                        } else {
                            sendReply(groupUin, msg, "你没有权限操作该用户");
                        }
                        return;
                    }

                    if (msgContent.startsWith("/unfban") || msgContent.startsWith("!unfban")) {
                        if (!是封禁用户(replyTo)) {
                            sendReply(groupUin, msg, "该用户未被联盟封禁");
                            return;
                        }
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            String reason = null;
                            String[] parts = msgContent.split(" ", 2);
                            if (parts.length > 1 && !parts[1].trim().isEmpty()) reason = parts[1].trim();
                            
                            移除封禁用户(replyTo);
                            sendReply(groupUin, msg, "新联盟解除封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(replyTo) + "\n用户 ID：" + replyTo + (reason != null ? "\n原因：" + reason : ""));
                        } else {
                            sendReply(groupUin, msg, "你没有权限操作该用户");
                        }
                        return;
                    }

                    boolean isBanCmd = msgContent.startsWith("禁") || msgContent.startsWith("禁言");
                    if (isBanCmd) {
                        if (检查代管保护(groupUin, replyTo, "禁言")) return;
                        if (有权限操作(groupUin, userUin, replyTo)) {
                            int banTime = 0;
                            String cause = "";
                            String cleanContent = msgContent;
                            
                            if (msgContent.contains(" ")) {
                                int lastIdx = msgContent.lastIndexOf(" ");
                                int firstIdx = msgContent.indexOf(" ");
                                if (lastIdx != firstIdx) {
                                    cause = "\n原因 : " + msgContent.substring(lastIdx + 1);
                                    cleanContent = msgContent.substring(0, lastIdx);
                                }
                            }
                            
                            if (cleanContent.matches(".*[零一二三四五六七八九十].*")) {
                                String timePart = cleanContent.substring(cleanContent.indexOf(" ") + 1);
                                String text = timePart.replaceAll("[^零一二三四五六七八九十百千万]", "");
                                banTime = get_time_int(cleanContent, CN_zh_int(text));
                            } else {
                                banTime = get_time(cleanContent);
                            }

                            if (banTime > 2592000) {
                                sendMsg(groupUin, "", "请控制在30天以内");
                            } else if (banTime > 0) {
                                unifiedForbidden(groupUin, replyTo, banTime);
                                if (!cause.isEmpty() || msgContent.startsWith("禁 ")) {
                                    sendMsg(groupUin, "", "已禁言 时长" + banTime + cause + "\n权限使用人：" + 名(userUin));
                                }
                            }
                        }
                        return;
                    }
                }
            }

            if (!mAtListCopy.isEmpty()) {
                if (msgContent.startsWith("解")) {
                    for (Object uin : mAtListCopy) unifiedForbidden(groupUin, (String) uin, 0);
                    return;
                }

                if (msgContent.startsWith("踢黑")) {
                    boolean kicked = false;
                    for (Object uin : mAtListCopy) {
                        String u = (String) uin;
                        if (检查代管保护(groupUin, u, "踢黑")) continue;
                        if (有权限操作(groupUin, userUin, u)) {
                            unifiedKick(groupUin, u, true);
                            kicked = true;
                        }
                    }
                    if (kicked) sendMsg(groupUin, "", "已踢出，不会再收到该用户入群申请\n权限使用人：" + 名(userUin));
                    return;
                }

                if (msgContent.startsWith("踢")) {
                    boolean kicked = false;
                    for (Object uin : mAtListCopy) {
                        String u = (String) uin;
                        if (检查代管保护(groupUin, u, "踢出")) continue;
                        if (有权限操作(groupUin, userUin, u)) {
                            unifiedKick(groupUin, u, false);
                            kicked = true;
                        }
                    }
                    if (kicked) sendMsg(groupUin, "", "踢出成功\n权限使用人：" + 名(userUin));
                    return;
                }

                if (msgContent.startsWith("头衔@")) {
                    String title = msgContent.substring(msgContent.lastIndexOf(" ") + 1);
                    for (Object uin : mAtListCopy) setTitle(groupUin, (String) uin, title);
                    return;
                }
                
                boolean isBan = msgContent.startsWith("禁");
                boolean isBanYan = msgContent.startsWith("禁言");
                if (isBan || isBanYan || msgContent.matches("^@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)+$") || msgContent.matches("^@?[\\s\\S]+[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒)+$")) {
                    int banTime = 0;
                    if (msgContent.matches(".*[零一二三四五六七八九十].*")) {
                        String str = msgContent.substring(msgContent.lastIndexOf(" ") + 1);
                        String text = str.replaceAll("[天分时小时分钟秒]", "");
                        if (!text.isEmpty()) {
                            banTime = get_time_int(msgContent, CN_zh_int(text));
                        }
                    } else if (msgContent.matches(".*[0-9].*")) {
                         if (!Character.isDigit(msgContent.charAt(msgContent.length() - 1)) && !msgContent.endsWith("秒") && !msgContent.endsWith("分") && !msgContent.endsWith("时") && !msgContent.endsWith("天")) {
                             banTime = 2592000; 
                         } else {
                            String tStr = msgContent.substring(msgContent.lastIndexOf(" ") + 1);
                             if (tStr.matches("^[0-9]+$")) {
                                 banTime = Integer.parseInt(tStr) * 60;
                             } else {
                                 banTime = get_time(msgContent);
                             }
                         }
                    } else {
                         banTime = isBanYan ? 86400 : 2592000; 
                    }

                    if (banTime > 2592000) {
                        sendMsg(groupUin, "", "请控制在30天以内");
                    } else if (banTime > 0) {
                        for (Object uin : mAtListCopy) {
                            String u = (String) uin;
                            if (检查代管保护(groupUin, u, "禁言")) continue;
                            unifiedForbidden(groupUin, u, banTime);
                        }
                    }
                    return;
                }

                if (isMyUin) {
                    if (msgContent.startsWith("添加代管") || msgContent.startsWith("添加管理员") || msgContent.startsWith("设置代管") || msgContent.startsWith("添加老婆")) {
                        File f = 获取代管文件();
                        if (!f.exists()) f.createNewFile();
                        ArrayList current = 简取(f);
                        StringBuilder sb = new StringBuilder();
                        for (Object uin : mAtListCopy) {
                            String u = (String) uin;
                            if (!jiandu(u, current)) {
                                简写(f, u);
                                sb.append(u).append(" ");
                            }
                        }
                        if (sb.length() > 0) sendMsg(groupUin, "", "已添加代管:\n" + sb.toString());
                        else sendMsg(groupUin, "", "以上代管已经添加过了");
                        return;
                    }
                    if (msgContent.startsWith("删除代管@") || msgContent.startsWith("删除管理员@")) {
                        File f = 获取代管文件();
                        if (f.exists()) {
                            StringBuilder sb = new StringBuilder();
                            ArrayList current = 简取(f);
                            for (Object uin : mAtListCopy) {
                                String u = (String) uin;
                                if (jiandu(u, current)) {
                                    简弃(f, u);
                                    sb.append(u).append(" ");
                                }
                            }
                            sendMsg(groupUin, "", "已删除管理员:\n" + sb.toString());
                        }
                        return;
                    }
                }
                
                // Alliance logic for At
                if (是联盟群组(groupUin)) {
                     if (msgContent.startsWith("/fban") || msgContent.startsWith("!fban")) {
                        String uin = (String)mAtListCopy.get(0);
                        if (检查代管保护(groupUin, uin, "联盟封禁")) return;
                        if (有权限操作(groupUin, userUin, uin)) {
                            String reason = null;
                            String[] parts = msgContent.split(" ", 2);
                            if (parts.length > 1 && !parts[1].trim().isEmpty()) reason = parts[1].trim();
                            
                            unifiedKick(groupUin, uin, true);
                            添加封禁用户(uin, reason);
                            sendReply(groupUin, msg, "新联盟封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(uin) + "\n用户 ID：" + uin + (reason != null ? "\n理由：" + reason : ""));
                        } else {
                             sendReply(groupUin, msg, "你没有权限操作该用户");
                        }
                        return;
                     }
                     if (msgContent.startsWith("/unfban") || msgContent.startsWith("!unfban")) {
                        String uin = (String)mAtListCopy.get(0);
                         if (!是封禁用户(uin)) {
                             sendReply(groupUin, msg, "该用户未被联盟封禁");
                             return;
                         }
                        if (有权限操作(groupUin, userUin, uin)) {
                            String reason = null;
                            String[] parts = msgContent.split(" ", 2);
                            if (parts.length > 1 && !parts[1].trim().isEmpty()) reason = parts[1].trim();
                            
                            移除封禁用户(uin);
                            sendReply(groupUin, msg, "新联盟解除封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(uin) + "\n用户 ID：" + uin + (reason != null ? "\n原因：" + reason : ""));
                        } else {
                             sendReply(groupUin, msg, "你没有权限操作该用户");
                        }
                        return;
                     }
                }
            }

            if ("禁".equals(msgContent) && mAtListCopy.isEmpty()) {
                unifiedForbidden(groupUin, "", 1);
                return;
            }
            if (msg.MessageType == 1 && "解".equals(msgContent) && mAtListCopy.isEmpty()) {
                unifiedForbidden(groupUin, "", 0);
                return;
            }

            if ("查看禁言列表".equals(msgContent)) {
                ArrayList list = 禁言组(groupUin);
                sendReply(groupUin, msg, list.isEmpty() ? "当前没有人被禁言" : 禁言组文本(groupUin));
                return;
            }

            if (msgContent.matches("^解禁? ?[1-9][0-9]*$")) {
                int index = Integer.parseInt(msgContent.replaceAll(" |解|禁", "")) - 1;
                ArrayList list = 禁言组(groupUin);
                if (index >= 0 && index < list.size()) unifiedForbidden(groupUin, (String) list.get(index), 0);
                return;
            }
            if (msgContent.matches("^解禁? ?[0-9]{5,11}$")) {
                unifiedForbidden(groupUin, msgContent.replaceAll(" |解|禁", ""), 0);
                return;
            }

            if (msgContent.matches("^(踢|踢黑) ?[1-9][0-9]*$")) {
                boolean isBlack = msgContent.contains("黑");
                int index = Integer.parseInt(msgContent.replaceAll(" |踢|黑", "")) - 1;
                ArrayList list = 禁言组(groupUin);
                if (index >= 0 && index < list.size()) {
                    String target = (String) list.get(index);
                    if (!检查代管保护(groupUin, target, isBlack ? "踢黑" : "踢出") && 有权限操作(groupUin, userUin, target)) {
                        unifiedKick(groupUin, target, isBlack);
                        sendMsg(groupUin, "", "已" + (isBlack ? "踢出并拉黑" : "踢出") + target + "\n权限使用人：" + 名(userUin));
                    }
                }
                return;
            }

            if ("#踢禁言".equals(msgContent)) {
                unifiedForbidden(groupUin, "", 0);
                ArrayList list = unifiedGetForbiddenList(groupUin);
                if (list != null && !list.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (Object item : safeCopyList(list)) {
                        java.lang.reflect.Field f = item.getClass().getDeclaredField("UserUin");
                        f.setAccessible(true);
                        String u = f.get(item).toString();
                        if (!检查代管保护(groupUin, u, "踢出") && 有权限操作(groupUin, userUin, u)) {
                            sb.append("\n").append(u);
                            unifiedKick(groupUin, u, false);
                        }
                    }
                    sendMsg(groupUin, "", "已踢出禁言列表:" + sb.toString());
                } else {
                    sendMsg(groupUin, "", "当前没有人被禁言");
                }
                return;
            }

            if ("全禁".equals(msgContent)) {
                unifiedForbidden(groupUin, "", 0);
                ArrayList list = unifiedGetForbiddenList(groupUin);
                if (list != null && !list.isEmpty()) {
                    for (Object item : safeCopyList(list)) {
                        java.lang.reflect.Field f = item.getClass().getDeclaredField("UserUin");
                        f.setAccessible(true);
                        String u = f.get(item).toString();
                        if (!检查代管保护(groupUin, u, "禁言") && 有权限操作(groupUin, userUin, u)) {
                            unifiedForbidden(groupUin, u, 2592000);
                        }
                    }
                    sendReply(groupUin, msg, "禁言列表已加倍禁言");
                } else {
                    sendMsg(groupUin, "", "当前没有人被禁言");
                }
                return;
            }

            if ("全解".equals(msgContent)) {
                unifiedForbidden(groupUin, "", 0);
                ArrayList list = unifiedGetForbiddenList(groupUin);
                if (list != null && !list.isEmpty()) {
                    for (Object item : safeCopyList(list)) {
                        java.lang.reflect.Field f = item.getClass().getDeclaredField("UserUin");
                        f.setAccessible(true);
                        unifiedForbidden(groupUin, f.get(item).toString(), 0);
                    }
                    sendReply(groupUin, msg, "禁言列表已解禁");
                } else {
                    sendMsg(groupUin, "", "当前没有人被禁言");
                }
                return;
            }

            if (isMyUin) {
                 if (msgContent.startsWith("删除代管") || msgContent.startsWith("删除管理员")) {
                     String text = msgContent.substring(4).replaceAll("[^0-9]", "");
                     File f = 获取代管文件();
                     if (f.exists() && text.matches("[0-9]+")) {
                         if (jiandu(text, 简取(f))) {
                             简弃(f, text);
                             sendMsg(groupUin, "", "已删除管理员:\n" + text);
                         } else {
                             sendReply(groupUin, msg, "此人并不是代管");
                         }
                     } else {
                         sendReply(groupUin, msg, "代管列表为空或格式错误");
                     }
                     return;
                 }
                 if ("查看代管".equals(msgContent)) {
                     File f = 获取代管文件();
                     if (!f.exists()) sendMsg(groupUin, "", "当前没有代管");
                     else {
                         String text = 组名(简取(f)).replace("]", "").replace("[", " ");
                         sendMsg(groupUin, "", "当前的代管如下:\n" + text);
                     }
                     return;
                 }
                 if ("清空代管".equals(msgContent)) {
                     全弃(获取代管文件());
                     sendReply(groupUin, msg, "代管列表已清空");
                     return;
                 }
                 if (msgContent.startsWith("/addgroup") || msgContent.startsWith("!addgroup")) {
                    添加联盟群组(groupUin);
                    sendReply(groupUin, msg, "已添加该群组为联盟\n联盟：简洁群管\n联盟创建者：" + myUin + "\n群组：" + groupUin);
                    return;
                }
                if (msgContent.startsWith("/ungroup") || msgContent.startsWith("!ungroup")) {
                    移除联盟群组(groupUin);
                    sendReply(groupUin, msg, "已取消该群组为联盟\n联盟：简洁群管\n联盟创建者：" + myUin + "\n群组：" + groupUin);
                    return;
                }
            }

            if (是联盟群组(groupUin)) {
                 if (msgContent.startsWith("/fban") || msgContent.startsWith("!fban")) {
                    String[] parts = msgContent.split("\\s+", 3);
                    if (parts.length >= 2) {
                        String target = parts[1];
                        String reason = parts.length > 2 ? parts[2] : null;
                        if (target.matches("[0-9]{4,11}") && !检查代管保护(groupUin, target, "联盟封禁") && 有权限操作(groupUin, userUin, target)) {
                             unifiedKick(groupUin, target, true);
                             添加封禁用户(target, reason);
                             sendReply(groupUin, msg, "新联盟封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(target) + "\n用户 ID：" + target + (reason != null ? "\n理由：" + reason : ""));
                        }
                    }
                    return;
                 }
                 if (msgContent.startsWith("/unfban") || msgContent.startsWith("!unfban")) {
                    String[] parts = msgContent.split("\\s+", 3);
                    if (parts.length >= 2) {
                        String target = parts[1];
                        String reason = parts.length > 2 ? parts[2] : null;
                        if (target.matches("[0-9]{4,11}") && 是封禁用户(target) && 有权限操作(groupUin, userUin, target)) {
                             移除封禁用户(target);
                             sendReply(groupUin, msg, "新联盟解除封禁\n联盟：简洁群管\n联盟管理员：" + 名(userUin) + "\n用户：" + 名(target) + "\n用户 ID：" + target + (reason != null ? "\n原因：" + reason : ""));
                        }
                    }
                    return;
                 }
            }

        } catch (Throwable e) {
            error(e);
        }
    }
}