
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
import java.util.Collections;
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
import android.widget.Toast;
import android.content.res.Configuration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

public ArrayList unifiedGetForbiddenList(String groupUin) {
    try {
        return getForbiddenList(groupUin);
    } catch (Throwable e) {
        return getProhibitList(groupUin);
    }
}

public ArrayList 禁言组(String groupUin) {
    ArrayList list = unifiedGetForbiddenList(groupUin);
    if (list == null || list.size() == 0) {
        return new ArrayList();
    }
    ArrayList uinList = new ArrayList();
    for (Object item : list) {
        uinList.add(item.UserUin);
    }
    return uinList;
}

public String 禁言组文本(String groupUin) {
    ArrayList list = 禁言组(groupUin);
    if (list.size() == 0) {
        return "当前没有人被禁言";
    }
    StringBuilder sb = new StringBuilder("禁言列表:\n");
    for (int i = 0; i < list.size(); i++) {
        String uin = (String) list.get(i);
        sb.append(i + 1).append(". ").append(名(uin)).append("(").append(uin).append(")\n");
    }
    return sb.toString();
}

addItem("开启/关闭艾特禁言","开关艾特禁言方法");
addItem("开启/关闭退群拉黑", "退群拉黑开关方法");
addItem("开启/关闭自助头衔", "开关自助头衔方法");
addItem("设置艾特禁言时间", "设置艾特禁言时间方法");
addItem("查看群管功能", "群管功能弹窗");
addItem("代管管理功能", "代管管理弹窗");
addItem("群黑名单管理", "黑名单管理弹窗");
addItem("检测群黑名单", "检测黑名单方法");
addItem("查看更新日志","showUpdateLog");

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

public int getTextColorForTheme() {
    try {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    } catch (Exception e) {
        return Color.BLACK;
    }
}

public void 设置艾特禁言时间方法(String groupUin, String uin, int chatType) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(() -> {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
            builder.setTitle("设置艾特禁言时间");
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 30, 30, 30);
            
            TextView hint = new TextView(activity);
            hint.setText("当前艾特禁言时间: " + 艾特禁言时间 + "秒 (" + (艾特禁言时间/86400) + "天)");
            hint.setTextColor(getTextColorForTheme());
            layout.addView(hint);
            
            EditText inputEditText = new EditText(activity);
            inputEditText.setHint("请输入禁言时间(秒)");
            inputEditText.setText(String.valueOf(艾特禁言时间));
            inputEditText.setHintTextColor(Color.GRAY);
            inputEditText.setTextColor(getTextColorForTheme());
            inputEditText.setBackgroundResource(android.R.drawable.edit_text);
            layout.addView(inputEditText);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定", (dialog, which) -> {
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
            });
            
            builder.setNegativeButton("取消", null);
            builder.show();
        } catch (Exception e) {
            toast("设置艾特禁言时间弹窗错误: " + e.toString());
        }
    });
}

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(() -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
        builder.setTitle("简洁群管更新日志");
        
        TextView textView = new TextView(activity);
        textView.setText("QStory精选脚本系列\n\n以下是简洁群管的部分更新日志 14.0以前的更新内容已丢失 因为以前的版本是临江在维护 并非海枫 找不到 并且部分更新内容我自己也不记得了\n\n简洁群管_14.0_更新日志\n- [新增] 退群拉黑\n————————\n简洁群管_15.0_更新日志\n- [新增] 脚本悬浮窗可打开自助头衔\n————————\n简洁群管_16.0_更新日志\n- [修复] 艾特禁言不生效的问题\n————————\n简洁群管_17.0_更新日志\n- [修复] 脚本悬浮窗所有指令Toast提示失败的问题\n————————\n简洁群管_18.0_更新日志\n- [修复] 撤回功能\n————————\n简洁群管_19.0_更新日志\n- [移除] 彩蛋开关\n- [移除] 彩蛋开关相关代码\n- [移除] 爱腾讯爱生活\n————————\n简洁群管_20.0_更新日志\n- [其他] 尝试修复QQ9.1.0以下版本 没有使用隐藏标识也不显示的问题\n————————\n简洁群管_21.0_更新日志\n- [修复] 空指针异常的问题\n————————\n简洁群管_22.0_更新日志\n- [修复] 退群拉黑用户无法正确写入的问题\n————————\n简洁群管_23.0_更新日志\n- [优化] 群管功能显示逻辑\n————————\n简洁群管_24..0_更新日志\n- [添加] 查看黑名单指令 只有自己和代管才可以触发\n————————\n简洁群管_25.0_更新日志\n- [优化] 退群拉黑逻辑 现在更方便\n————————\n简洁群管_26.0_更新日志\n- [修复] 代管可能会被清空\n————————\n简洁群管_27.0_更新日志\n- [优化] 代管逻辑，现在 只有在发送添加代管指令的时候才会创建代管文件 防止更新简洁群管代管凭空消失\n————————\n简洁群管_28.0_更新日志\n- [优化] 再次优化代管存储逻辑 以防更新简洁群管的时候 代管被清空\n————————\n简洁群管_29.0_更新日志\n- [新增] 回复消息撤回 以及给踢人指令加了撤回\n————————\n简洁群管_30.0_更新日志\n_ [新增] 代管保护功能 代管不会被禁言、踢黑等\n————————\n简洁群管_31.0_更新日志\n- [新增] 弹窗显示群管功能 更方便的知道指令如何使用\n————————\n简洁群管_32.0_更新日志\n- [拓展] 继续移除撤回消息代码\n————————\n简洁群管=33.0_更新日志\n- [修复] 查看禁言列表指令失效\n————————\n简洁群管_34.0_更新日志\n- [删除] 废弃的部分功能 可能导致脚本卡顿\n————————\n简洁群管_35.0_更新日志\n- [新增] 不知道新增啥了\n————————\n简洁群管_36.0_更新日志\n- [更改] 脚本悬浮窗弹窗主题 更美观\n- [修复] 打不死夜七的问题\n————————\n简洁群管_37.0_更新日志\n- [新增] 脚本悬浮窗代管管理 可以通过弹窗来添加移除代管\n- [修复] 打不死夜七的问题\n- [修复] 用户提出的问题\n- [修复] 提出问题的用户\n- [拓展] 更新版本号\n- [优化] 代码逻辑\n————————\n简洁群管_38.0_更新日志\n- [适配] QStory_1.9.0+的脚本写法\n————————\n简洁群管_39.0更新日志\n- [移除] 踢人指令撤回\n- [新增] 部分指令显示权限使用人\n- [新增] 脚本悬浮窗代管管理 可以通过弹窗来添加移除代管\n- [新增] 脚本悬浮窗查看更新日志 如果你看到这个弹窗，那么就是此更新的内容之一\n————————\n简洁群管_40.0_更新日志\n- [优化] 部分代码\n————————\n简洁群管_41.0_更新日志\n- [修复] 退群拉黑相关指令失效的问题\n————————\n简洁群管_42.0_更新日志\n- [拓展] 被九月做局 更新了版本号\n————————\n简洁群管_43.0_更新日志\n- [新增] 脚本悬浮窗可以操作艾特禁言时间了\n- [其他] 现在艾特禁言任务不需要在main.java里面修改了 现在更换了艾特禁言的储存方式 艾特禁言时间需要重新配置 默认2562000秒\n- [其他] 简洁群管目前就没有什么需要添加的东西了 如果有建议的话加入海枫的群聊反馈哦(៸៸᳐⦁⩊⦁៸៸᳐ )੭ \n————————\n简洁群管_44.0_更新日志\n- [移除] 退群拉黑开启后 提示无管理员权限无法踢出\n- [修复] 可能由于后台问题 导致有管理员权限 简洁群管识别到退群拉黑用户进群提示无管理员权限无法踢出的问题\n- [其他] 现在不管有没有管理员权限 都可以写入黑名单以及每次入群时都会尝试执行踢黑退群拉黑用户\n————————\n简洁群管_45.0_更新日志\n- [新增] 脚本弹窗可以立即检测黑名单用户是否在群内 如果在群内则踢黑 没有考虑使用定时线程来检测黑名单 这样会极其耗电 脚本目前只会监听入群事件来识别黑名单用户\n- [其他] 优化了代码逻辑以及保留了简洁群管老旧版本的版权信息\n————————\n简洁群管_46.0_更新日志\n- [修复] 弹窗在暗色模式中 渲染显示异常的问题\n————————\n简洁群管_47.0_更新日志\n- [移除] Android 主题 (Theme.Material.Light.Dialog.Alert) 因为在旧版本 QQ sdk 不同导致弹窗显示风格较老\n- [修复] 全选弹窗消失的问题\n- [更改] 继续使用 THEME_DEVICE_DEFAULT_LIGHT); 主题\n- [其他] 顺便也修了一些存在的问题\n————————\n简洁群管_48.0_更新日志\n- [新增] 如果用户系统使用浅色模式 弹窗自动切换为AlertDialog.THEME_DEVICE_DEFAULT_LIGHT(亮色窗口) 如果用户切换为深色模式 会自动切换为AlertDialog.THEME_DEVICE_DEFAULT_DARK(深色窗口)，此版本更新是为了保护好用户和开发者的眼睛 避免在深夜中查看弹窗时被太亮的弹窗闪到\n————————\n简洁群管_49.0_更新日志\n- [修复] 隐藏 显示 标识 头衔等功能在历代版本失效的问题\n————————\n简洁群管_50.0_更新日志\n- [移除] 撤回功能代码 之前没有发现没有删干净，现在已经删除\n————————\n简洁群管_51.0_更新日志\n- [修复] 部分存在的问题\n————————\n简洁群管_52.0_更新日志\n- [修复] bsh.BlockNameSpace.getInstance方法空指针异常\n————————\n简洁群管_53.0_更新日志\n- [新增] addMenuItem菜单踢/踢黑，管理群聊更方便\n- [优化] 大量代码\n\n临江、海枫 岁岁平安 >_<");
        textView.setTextColor(getTextColorForTheme());
        textView.setTextSize(14);
        textView.setPadding(50, 30, 50, 30);
        textView.setTextIsSelectable(true);

        ScrollView scrollView = new ScrollView(activity);
        scrollView.addView(textView);

        builder.setView(scrollView);
        builder.setPositiveButton("确定", null);
        builder.show();
    });
}

public void showGroupManageDialog() {
    try {
        String dialogContent = "简洁群管使用方法，可能不太完整 更多指令可能需要自行探索：\n\n" +
                "1. @ 时间 - 禁言指定成员（例：@ 1天/一天）\n" +
                "2. 解@ - 解除成员禁言\n" +
                "3. 踢@ - 踢出该用户\n" +
                "4. 踢黑@ - 踢出该用户并拉黑\n" +
                "5. 禁 - 开启全体禁言\n" +
                "6. 解 - 关闭全体禁言\n" +
                "7. 查看禁言列表 - 显示当前禁言用户\n" +
                "8. 全解 - 解禁所有用户\n" +
                "9. 全禁 - 禁言列表加倍禁言\n" +
                "10. 添加代管@ - 添加该代管\n" +
                "11. 删除代管@ - 删除该代管\n" +
                "12. 查看代管 - 显示代管列表\n" +
                "13. 清空代管 - 清空代管\n" +
                "14. 显示/隐藏头衔 - 切换头衔显示\n" +
                "15. 显示/隐藏等级 - 切换等级显示\n" +
                "16. 显示/隐藏标识 - 切换互动标识\n" +
                "17. 开启/关闭自助头衔 - 切换自助头衔功能\n" +
                "18. 开启/关闭退群拉黑 - 切换退群拉黑功能\n" +
                "19. 查看黑名单 - 显示退群被拉黑的用户\n" +
                "20. 移除黑名单@成员 - 移除退群被拉黑的用户\n\n" +
                "回复操作：\n" +
                "• 回复消息 /ban ban - 踢黑\n" +
                "• 回复消息 /kick kick - 普通踢出\n\n" +
                "临江：634941583\n" +
                "海枫：https://t.me/XiaoYu_Chat";

        Activity activity = getActivity();
        if (activity == null) return;
        
        activity.runOnUiThread(() -> {
            try {
                TextView textView = new TextView(activity);
                textView.setText(dialogContent);
                textView.setTextSize(16);
                textView.setTextColor(getTextColorForTheme());
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
                log("弹窗错误: " + e.toString());
            }
        });
    } catch (Exception e) {
        log("显示弹窗失败: " + e.toString());
    }
}

public void 群管功能弹窗(String groupUin, String uin, int chatType) {
    showGroupManageDialog();
}

public void 代管管理弹窗(String groupUin, String uin, int chat) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(() -> {
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
            inputEditText.setTextColor(getTextColorForTheme());
            inputEditText.setBackgroundResource(android.R.drawable.edit_text);
            layout.addView(inputEditText);
            
            ListView listView = new ListView(activity);
            ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, 代管列表);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setDividerHeight(1);
            layout.addView(listView);
            
            builder.setView(layout);
            
            builder.setPositiveButton("添加代管", (dialog, which) -> {
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
            });
            
            builder.setNegativeButton("删除选中", (dialog, which) -> {
                for (int i = 0; i < listView.getCount(); i++) {
                    if (listView.isItemChecked(i)) {
                        String qq = (String)代管列表.get(i);
                        try {
                            简弃(代管文件, qq);
                        } catch (Exception e) {}
                    }
                }
                    toast("已删除选中代管");
            });
            
            builder.setNeutralButton("清空代管", (dialog, which) -> {
                try {
                    全弃(代管文件);
                } catch (Exception e) {}
                toast("已清空代管");
            });
            
            builder.show();
        } catch (Exception e) {
            toast("代管管理弹窗错误: " + e.toString());
        }
    });
}

public void 黑名单管理弹窗(String groupUin, String uin, int chat) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(() -> {
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
            inputEditText.setTextColor(getTextColorForTheme());
            inputEditText.setBackgroundResource(android.R.drawable.edit_text);
            layout.addView(inputEditText);
            
            ListView listView = new ListView(activity);
            ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, 黑名单列表);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setDividerHeight(1);
            layout.addView(listView);
            
            builder.setView(layout);
            
            builder.setPositiveButton("添加黑名单", (dialog, which) -> {
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
            });
            
            builder.setNegativeButton("删除选中", (dialog, which) -> {
                for (int i = 0; i < listView.getCount(); i++) {
                    if (listView.isItemChecked(i)) {
                        String qq = (String)黑名单列表.get(i);
                        try {
                            简弃(黑名单文件, qq);
                        } catch (Exception e) {}
                    }
                }
                toast("已删除选中黑名单");
            });
            
            builder.setNeutralButton("清空黑名单", (dialog, which) -> {
                try {
                    全弃(黑名单文件);
                } catch (Exception e) {}
                toast("已清空黑名单");
            });
            
            builder.show();
        } catch (Exception e) {
            toast("黑名单管理弹窗错误: " + e.toString());
        }
    });
}

public void 开关自助头衔方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    if("开".equals(getString(groupUin,"自助头衔"))){
        putString(groupUin,"自助头衔",null);
        toast("已关闭自助头衔");
    }else{
        putString(groupUin,"自助头衔","开");
        toast("已开启自助头衔");
    }
}

public void 开关艾特禁言方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    if("开".equals(getString(groupUin,"艾特禁言"))){
        putString(groupUin,"艾特禁言",null);
        toast("已关闭艾特禁言");
    }else{
        putString(groupUin,"艾特禁言","开");
        toast("已开启艾特禁言");
    }
}

public void 退群拉黑开关方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    if("开".equals(getString(groupUin,"退群拉黑"))){
        putString(groupUin,"退群拉黑",null);
        toast("已关闭退群拉黑");
    }else{
        putString(groupUin,"退群拉黑","开");
        toast("已开启退群拉黑");
    }
}

String 退群拉黑目录 = appPath + "/退群拉黑/";
File 退群拉黑文件夹 = new File(退群拉黑目录);

if (!退群拉黑文件夹.exists()) {
    退群拉黑文件夹.mkdirs();
}

int 艾特禁言时间 = getInt("艾特禁言时间配置", "时间", 2592000);

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
            toast("错误:"+e);
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
        toast("设置管理员错误: " + e);
    }
}

private final Map Arab2Chinese = Collections.synchronizedMap(new HashMap());
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

private final Map UnitMap = Collections.synchronizedMap(new HashMap());
{
    UnitMap.put('十', 10);
    UnitMap.put('百', 100);
    UnitMap.put('千', 1000);
    UnitMap.put('万', 10000);
}

private Pattern pattern = Pattern.compile("[零一二三四五六七八九十]?[十百千万]?");

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

public boolean atMe(Object msg){
    if (msg.mAtList == null || msg.mAtList.size() == 0)
        return false;
    for (String to_at : msg.mAtList)
        if (to_at.equals(myUin))
            return true;
    return false;
}

public String 论(String u,String a,String b){
    return u.replace(a,b);
}

FileWriter f=null;
BufferedWriter f1=null;
FileReader  fr  =  null;
BufferedReader f2=null;

public synchronized void 简写(File ff, String a) throws IOException {
    f=new FileWriter(ff,true);
    f1=new BufferedWriter(f);
    f1.append(a);
    f1.newLine();
    f1.close();
    f.close();
}

public synchronized ArrayList 简取(File ff) throws IOException {
    if(!ff.exists()){
        return new ArrayList(); 
    }
    fr  =  new  FileReader(ff);  
    f2=new BufferedReader(fr);
    ArrayList list1 = new ArrayList();
    String a;
    while ((a = f2.readLine()) != null) {
        if (!a.trim().isEmpty()) {
            list1.add(a.trim());
        }
    }
    fr.close();
    f2.close();
    return list1;
}

public boolean jiandu(String a,ArrayList l1){
    boolean x=false;
    for(int i=0;i<l1.size();i++){
        if(a.equals(l1.get(i).toString())){
            x=true; break;
        }
    }
    return x;
}

public synchronized void 全弃(File ff) throws IOException {
    f=new FileWriter(ff);
    f1=new BufferedWriter(f);
    f1.write("");
    f1.close();
    f.close();
}

public int 度(String a){
    return a.length();
}

public synchronized void 简弃(File ff,String a) throws IOException {
    ArrayList l1= new ArrayList();
    l1.addAll(简取(ff));
    if(l1.contains(a)){
        l1.remove(a);
    }
    f=new FileWriter(ff);
    f1=new BufferedWriter(f);
    f1.write("");
    f1.close();
    f.close();
    for(int i=0;i<l1.size();i++){
        简写(ff,l1.get(i).toString());
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
    for(Object uin : a) {
        list.add(名(uin.toString())+"("+uin.toString()+")");
    }
    return list.toString().replace(",","\n");
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

public int get_time_int(Object msg,int time){
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
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 == null || !黑名单文件.exists()) return false;
    try {
        return 简取(黑名单文件).contains(QQ号);
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
    if (groupUin == null || groupUin.isEmpty()) {
        return;
    }
    String switchState = getString(groupUin, "退群拉黑");
    if (switchState == null || !"开".equals(switchState)) {
        return;
    }
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

void 检测黑名单方法(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        toast("只能在群聊中使用此功能");
        return;
    }
    new Thread(() -> {
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
            for (int i = 0; i < 成员列表.size(); i++) {
                Object 成员 = 成员列表.get(i);
                if (成员.UserUin.equals(myUin)) {
                    有权限 = 成员.IsOwner || 成员.IsAdmin;
                    break;
                }
            }
            if (!有权限) {
                toast("没有管理员权限，无法踢人");
                return;
            }
            StringBuilder 踢出列表 = new StringBuilder();
            for (int i = 0; i < 成员列表.size(); i++) {
                Object 成员 = 成员列表.get(i);
                String 成员QQ = 成员.UserUin;
                if (黑名单列表.contains(成员QQ)) {
                    kick(groupUin, 成员QQ, true);
                    踢出列表.append(getMemberName(groupUin, 成员QQ)).append("(").append(成员QQ).append(")\n");
                    Thread.sleep(500);
                }
            }
            if (踢出列表.length() > 0) {
                final String 结果 = "已踢出以下黑名单成员：\n" + 踢出列表.toString();
                getActivity().runOnUiThread(() -> toast(结果));
            } else {
                toast("没有发现黑名单成员");
            }
        } catch (Throwable e) {
            toast("检测黑名单时出错: " + e.getMessage());
        }
    }).start();
}

public boolean 是代管(String groupUin, String userUin) {
    File 代管文件 = 获取代管文件();
    if (!代管文件.exists()) {
        return false;
    }
    try {
        return jiandu(userUin, 简取(代管文件));
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
            sendMsg(groupUin, "", "不能权限简洁群管使用者");
            return false;
        }
        if (是代管(groupUin, targetUin)) {
            sendMsg(groupUin, "", "不能权限简洁群管代管: " + targetUin);
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

public String isGN(String groupUin, String key) {
    if("开".equals(getString(groupUin, key))) return "✅";
    else return "❌";
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

public void onMsg(Object msg){
    String 故=msg.MessageContent;
    String qq=msg.UserUin;
    String groupUin = msg.GroupUin;
    
    if(msg.MessageContent.startsWith("我要头衔")&&"开".equals(getString(groupUin,"自助头衔"))){
        String a=msg.MessageContent.substring(4);
        setTitle(groupUin,qq,a);
    }
    
    if("开".equals(getString(groupUin,"艾特禁言"))){
        if(atMe(msg)){
            unifiedForbidden(groupUin,qq,艾特禁言时间);
        }
    }
    
    boolean isAdminUser = false;
    try {
        File 代管文件 = 获取代管文件();
        if (代管文件.exists()) {
            ArrayList 代管列表 = 简取(代管文件);
            isAdminUser = 代管列表.contains(qq);
        }
    } catch (Exception e) {}
    
    if(msg.UserUin.equals(myUin)||isAdminUser){
        if(msg.MessageContent.equals("显示标识")){
            String result = SetTroopShowHonour(groupUin,myUin,getSkey(),getPskey("clt.qq.com"),1);
            sendMsg(groupUin,"",result);
        }
        if(msg.MessageContent.equals("隐藏标识")){
            String result = SetTroopShowHonour(groupUin,myUin,getSkey(),getPskey("clt.qq.com"),0);
            sendMsg(groupUin,"",result);
        }
        if(msg.MessageContent.equals("显示等级")){
            String result = SetTroopShowLevel(groupUin,myUin,getSkey(),getPskey("clt.qq.com"),1);
            sendMsg(groupUin,"",result);
        }
        if(msg.MessageContent.equals("隐藏等级")){
            String result = SetTroopShowLevel(groupUin,myUin,getSkey(),getPskey("clt.qq.com"),0);
            sendMsg(groupUin,"",result);
        }
        if(msg.MessageContent.equals("显示头衔")){
            String result = SetTroopShowTitle(groupUin,myUin,getSkey(),getPskey("clt.qq.com"),1);
            sendMsg(groupUin,"",result);
        }
        if(msg.MessageContent.equals("隐藏头衔")){
            String result = SetTroopShowTitle(groupUin,myUin,getSkey(),getPskey("clt.qq.com"),0);
            sendMsg(groupUin,"",result);
        }
        if(msg.MessageContent.equals("群管功能")){
            String a=
                "群管功能:\n"
                +"禁@ 禁言@ 头衔@\n"
                +"@+时间+天|分|秒\n"
                +"解@ 踢@ 踢黑@\n"
                +"禁/解(全体禁言/解禁) \n"
                +"查看禁言列表\n"
                +"全解(解所有人禁言)\n"
                +"添加代管@ 删除代管@\n"
                +"查看/清空 代管\n"
                +"显示/隐藏 头衔|等级|标识\n"
                +"开启/关闭退群拉黑\n"
                +"查看/移除黑名单@\n"
                +"开启/关闭 自助头衔"+isGN(groupUin,"自助头衔");
            sendMsg(groupUin,"",a);
        }
        if(msg.MessageContent.equals("开启自助头衔")){
            putString(groupUin,"自助头衔","开");
            sendMsg(groupUin,"","自助头衔已开启 大家可以发送 我要头衔xxx来获取头衔");
            return;
        }
        if(msg.MessageContent.equals("关闭自助头衔")){
            if("开".equals(getString(groupUin,"自助头衔"))){
                putString(groupUin,"自助头衔",null);
                sendMsg(groupUin,"","自助头衔已关闭 你们不要再发我要头衔了!");
                return;
            }else sendMsg(groupUin,"","未开启无法关闭");
        }
        if (msg.MessageContent.equals("开启退群拉黑")) {
            putString(groupUin, "退群拉黑", "开");
            sendMsg(groupUin, "", "退群拉黑已开启");
            return;
        }
        if (msg.MessageContent.equals("关闭退群拉黑")) {
            putString(groupUin, "退群拉黑", null);
            sendMsg(groupUin, "", "退群拉黑已关闭");
            return;
        }
        if (msg.MessageContent.equals("查看黑名单")) {
            ArrayList 黑名单列表 = 获取黑名单列表(groupUin);
            if (黑名单列表.isEmpty()) {
                sendMsg(groupUin, "", "本群黑名单为空");
            } else {
                String 黑名单文本 = "本群黑名单:\n";
                for (int i = 0; i < 黑名单列表.size(); i++) {
                    黑名单文本 += (i + 1) + ". " + 名(黑名单列表.get(i).toString()) + "(" + 黑名单列表.get(i) + ")\n";
                }
                sendMsg(groupUin, "", 黑名单文本);
            }
            return;
        }
        if (msg.MessageContent.startsWith("移除黑名单@") && msg.mAtList.size() > 0) {
            for (String uin : msg.mAtList) {
                移除黑名单(groupUin, uin);
            }
            sendMsg(groupUin, "", "已删黑该用户");
            return;
        }
        if(!msg.MessageContent.startsWith("禁言")&&msg.MessageContent.startsWith("禁")&&msg.mAtList.size()>=1){   			
            if(msg.MessageContent.matches("禁 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")){
                int banTime = get_time(msg);
                if(banTime > 2592000){
                    sendMsg(groupUin,"","请控制在30天以内");
                    return;
                }else if(banTime > 0){
                    for(String u:msg.mAtList){
                        if (检查代管保护(groupUin, u, "禁言")) continue;
                        unifiedForbidden(groupUin,u,banTime);
                    }
                    return;
                }
            }
            if(msg.MessageContent.matches("禁 ?@[\\s\\S]+[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒)")){
                int str1 = msg.MessageContent.lastIndexOf(" ");
                String str =msg.MessageContent.substring(str1 + 1);
                String text=str.replaceAll("[天分时小时分钟秒]","");
                int time=CN_zh_int(text);
                int banTime = get_time_int(msg,time);
                if(banTime > 2592000){
                    sendReply(groupUin,msg,"禁言时间太长无法禁言");return;
                }else if(banTime > 0){
                    for(String u:msg.mAtList){
                        if (检查代管保护(groupUin, u, "禁言")) continue;
                        unifiedForbidden(groupUin,u,banTime);
                    }
                    return;
                }
            }
            if(!Character.isDigit(msg.MessageContent.charAt(msg.MessageContent.length() - 1))){
                for(String u:msg.mAtList){
                    if (检查代管保护(groupUin, u, "禁言")) continue;
                    unifiedForbidden(groupUin,u,2592000);
                }
                return;
            }else{
                int  time2= msg.MessageContent.lastIndexOf(" ");
                String time1 = msg.MessageContent.substring(time2 + 1); 
                int time=Integer.parseInt(time1);  
                for(String u:msg.mAtList){  
                    if (检查代管保护(groupUin, u, "禁言")) continue;
                    unifiedForbidden(groupUin,u,time*60);       
                } 
                return; 
            }
        }    
        if(msg.MessageContent.startsWith("禁言")&&msg.mAtList.size()>=1){ 
            if(msg.MessageContent.matches("禁言 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")){
                int banTime = get_time(msg);
                if(banTime > 2592000){
                    sendMsg(groupUin,"","请控制在30天以内");
                    return;
                }else if(banTime > 0){
                    for(String u:msg.mAtList){
                        if (检查代管保护(groupUin, u, "禁言")) continue;
                        unifiedForbidden(groupUin,u,banTime);
                    }
                    return;
                }
            }
            if(msg.MessageContent.matches("禁言 ?@[\\s\\S]+[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒)")){
                int str1 = msg.MessageContent.lastIndexOf(" ");
                String str =msg.MessageContent.substring(str1 + 1);
                String text= str.replaceAll("[天分时小时分钟秒]","");
                int time=CN_zh_int(text);
                int banTime = get_time_int(msg,time);
                if(banTime > 2592000){
                    sendReply(groupUin,msg,"禁言时间太长无法禁言");return;
                }else if(banTime > 0){
                    for(String u:msg.mAtList){
                        if (检查代管保护(groupUin, u, "禁言")) continue;
                        unifiedForbidden(groupUin,u,banTime);
                    }
                    return;
                }
            }  
            if(!Character.isDigit(msg.MessageContent.charAt(msg.MessageContent.length() - 1))){
                for(String u:msg.mAtList){
                    if (检查代管保护(groupUin, u, "禁言")) continue;
                    unifiedForbidden(groupUin,u,86400);
                }
                return;
            }else{
                int time2 = msg.MessageContent.lastIndexOf(" ");
                String time1 = msg.MessageContent.substring(time2 + 1); 
                int time=Integer.parseInt(time1);  
                for(String u:msg.mAtList){  
                    if (检查代管保护(groupUin, u, "禁言")) continue;
                    unifiedForbidden(groupUin,u,time);       
                } 
                return; 
            }   
        }
        if(msg.MessageContent.startsWith("解")&&msg.mAtList.size()>=1){    	
            for(String 千:msg.mAtList){  
                unifiedForbidden(groupUin,千,0);
            } 
            return; 
        }
        if(msg.MessageType == 6 &&( msg.MessageContent.equals("解") || msg.MessageContent.equals("解禁"))) {
            unifiedForbidden(groupUin,msg.ReplyTo,0);
        }
        if(msg.MessageType == 6 && (msg.MessageContent.startsWith("/dban")||msg.MessageContent.startsWith("dban"))) {
            if (检查代管保护(groupUin, msg.ReplyTo, "踢黑")) return;
            if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
            unifiedKick(groupUin,msg.ReplyTo,true);
            sendMsg(groupUin,"","已踢出"+msg.ReplyTo+"不会再收到该用户入群申请\n权限使用人："+名(qq));
        }
        if(msg.MessageType == 6 && (msg.MessageContent.startsWith("/ban")||msg.MessageContent.startsWith("ban"))) {
            if (检查代管保护(groupUin, msg.ReplyTo, "踢黑")) return;
            if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
            unifiedKick(groupUin,msg.ReplyTo,true);
            sendMsg(groupUin,"","已踢出"+msg.ReplyTo+"不会再收到该用户入群申请\n权限使用人："+名(qq));
        }
        if(msg.MessageType == 6 && (msg.MessageContent.startsWith("/kick")||msg.MessageContent.startsWith("kick"))) {
            if (检查代管保护(groupUin, msg.ReplyTo, "踢出")) return;
            if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
            unifiedKick(groupUin,msg.ReplyTo,false);
            sendMsg(groupUin,"","已踢出"+msg.ReplyTo+"，此用户还可再次申请入群\n权限使用人："+名(qq));
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁言 ?[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")) {
            if (检查代管保护(groupUin, msg.ReplyTo, "禁言")) return;
            int banTime = get_time(msg);
            if(banTime > 2592000) {
                sendMsg(groupUin,"","请控制在30天以内");
                return;
            } else if(banTime > 0){
                if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
                unifiedForbidden(groupUin,msg.ReplyTo,banTime);
            }
            return;
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁言 [0-9]+(天|分|时|小时|分钟|秒) ?(.+)?")) {
            int index = msg.MessageContent.indexOf(" ");
            String 原因 = "";
            int lastIndex = msg.MessageContent.lastIndexOf(" ");
            boolean hasCause = lastIndex != index;
            if (hasCause) {
                原因 = "\n原因 : "+ msg.MessageContent.substring(lastIndex + 1);
            }
            String timeText = msg.MessageContent;
            if (hasCause) timeText = msg.MessageContent.substring(index , lastIndex);
            int time = get_time(timeText);
            if (检查代管保护(groupUin, msg.ReplyTo, "禁言")) return;
            if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
            unifiedForbidden(groupUin,msg.ReplyTo,time);
            sendMsg(groupUin,"","已禁言 时长"+time + 原因 + "\n权限使用人："+名(qq));
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁言 [零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒) ?(.+)?")) {
            int index = msg.MessageContent.indexOf(" ");
            String str =msg.MessageContent.substring(index + 1);
            String text=str.replaceAll("[^零一二三四五六七八九十百千万]","");
            String 原因 = "";
            int lastIndex = msg.MessageContent.lastIndexOf(" ");
            boolean hasCause = lastIndex != index;
            if (hasCause) {
                原因 = "\n原因 : "+ msg.MessageContent.substring(lastIndex + 1);
            }
            int time = CN_zh_int(text);
            String timeText = msg.MessageContent;
            if (hasCause) timeText = msg.MessageContent.substring(index , lastIndex);
            int banTime = get_time_int(timeText,time);
            if (检查代管保护(groupUin, msg.ReplyTo, "禁言")) return;
            if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
            unifiedForbidden(groupUin,msg.ReplyTo,banTime);
            sendMsg(groupUin,"","已禁言 时长"+banTime + 原因 + "\n权限使用人："+名(qq));
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁 ?[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)")) {
            if (检查代管保护(groupUin, msg.ReplyTo, "禁言")) return;
            int banTime = get_time(msg);
            if(banTime > 2592000) {
                sendMsg(groupUin,"","请控制在30天以内");
                return;
            } else if(banTime > 0){
                if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
                unifiedForbidden(groupUin,msg.ReplyTo,banTime);
            }
            return;
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁 [0-9]+(天|分|时|小时|分钟|秒) ?(.+)?")) {
            int index = msg.MessageContent.indexOf(" ");
            String 原因 = "";
            int lastIndex = msg.MessageContent.lastIndexOf(" ");
            boolean hasCause = lastIndex != index;
            if (hasCause) {
                原因 = "\n原因 : "+ msg.MessageContent.substring(lastIndex + 1);
            }
            String timeText = msg.MessageContent;
            if (hasCause) timeText = msg.MessageContent.substring(index , lastIndex);
            int time = get_time(timeText);
            if (检查代管保护(groupUin, msg.ReplyTo, "禁言")) return;
            if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
            unifiedForbidden(groupUin,msg.ReplyTo,time);
            sendMsg(groupUin,"","已禁言 时长"+time + 原因 + "\n权限使用人："+名(qq));
        }
        if(msg.MessageType == 6 && msg.MessageContent.matches("禁 [零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒) ?(.+)?")) {
            int index = msg.MessageContent.indexOf(" ");
            String str =msg.MessageContent.substring(index + 1);
            String text=str.replaceAll("[^零一二三四五六七八九十百千万]","");
            String 原因 = "";
            int lastIndex = msg.MessageContent.lastIndexOf(" ");
            boolean hasCause = lastIndex != index;
            if (hasCause) {
                原因 = "\n原因 : "+ msg.MessageContent.substring(lastIndex + 1);
            }
            int time = CN_zh_int(text);
            String timeText = msg.MessageContent;
            if (hasCause) timeText = msg.MessageContent.substring(index , lastIndex);
            int banTime = get_time_int(timeText,time);
            if (检查代管保护(groupUin, msg.ReplyTo, "禁言")) return;
            if (!有权限操作(groupUin, qq, msg.ReplyTo)) return;
            unifiedForbidden(groupUin,msg.ReplyTo,banTime);
            sendMsg(groupUin,"","已禁言 时长"+banTime + 原因 + "\n权限使用人："+名(qq));
        }
        if(!msg.MessageContent.startsWith("踢黑")&&msg.MessageContent.startsWith("踢")&&msg.mAtList.size()>=1){
            for(String u:msg.mAtList){
                if (检查代管保护(groupUin, u, "踢出")) continue;
                if (!有权限操作(groupUin, qq, u)) continue;
                unifiedKick(groupUin,u,false);
            }
            sendMsg(groupUin,"","踢出成功\n权限使用人："+名(qq));
            return;
        }
        if(msg.MessageContent.startsWith("踢黑")&&msg.mAtList.size()>=1){
            for(String 千:msg.mAtList){
                if (检查代管保护(groupUin, 千, "踢黑")) continue;
                if (!有权限操作(groupUin, qq, 千)) continue;
                unifiedKick(groupUin,千,true);
            }
            sendMsg(groupUin,"","已踢出，不会再收到该用户入群申请\n权限使用人："+名(qq));
        }
        if(msg.MessageContent.equals("禁")&&msg.mAtList.size()==0){	  
            unifiedForbidden(groupUin,"",1);return;
        }
        if(msg.MessageType == 1 && msg.MessageContent.equals("解")&&msg.mAtList.size()==0){		    
            unifiedForbidden(groupUin,"",0);return;
        }
        if(msg.MessageContent.startsWith("头衔@")){    	
            int str = msg.MessageContent.lastIndexOf(" ")+1;
            String text = msg.MessageContent.substring(str);   
            for(String u:msg.mAtList){
                setTitle(groupUin,u,text);
            }
        }
        if(msg.MessageContent.equals("查看禁言列表")) {
            if(禁言组(groupUin).size() == 0) {
                sendReply(groupUin,msg,"当前没有人被禁言");
            } else {
                sendReply(groupUin,msg,禁言组文本(groupUin));
            }
        }
        if (msg.MessageContent.matches("^解禁? ?[1-9]{0,2}+$") && msg.MessageContent.length() >= 2){
            String indexStr = msg.MessageContent.replaceAll(" |解","");
            int index = Integer.parseInt(indexStr) - 1;
            ArrayList 禁言列表 = 禁言组(groupUin);
            if (index >= 0 && index < 禁言列表.size()) {
                unifiedForbidden(groupUin, (String)禁言列表.get(index), 0);
            }
        }
        if (msg.MessageContent.matches("^踢 ?[1-9]{0,2}+$") && msg.MessageContent.length() >= 2){
            String indexStr = msg.MessageContent.replaceAll(" |踢","");
            int index = Integer.parseInt(indexStr) - 1;
            ArrayList 禁言列表 = 禁言组(groupUin);
            if (index >= 0 && index < 禁言列表.size()) {
                String targetUin = (String)禁言列表.get(index);
                if (检查代管保护(groupUin, targetUin, "踢出")) return;
                if (!有权限操作(groupUin, qq, targetUin)) return;
                unifiedKick(groupUin, targetUin, false);
                sendMsg(groupUin,"","已踢出"+targetUin+"\n权限使用人："+名(qq));
            }
        }
        if (msg.MessageContent.matches("^踢黑 ?[1-9]{0,2}+$") && msg.MessageContent.length() >= 2){
            String indexStr = msg.MessageContent.replaceAll(" |踢|黑","");
            int index = Integer.parseInt(indexStr) - 1;
            ArrayList 禁言列表 = 禁言组(groupUin);
            if (index >= 0 && index < 禁言列表.size()) {
                String targetUin = (String)禁言列表.get(index);
                if (检查代管保护(groupUin, targetUin, "踢黑")) return;
                if (!有权限操作(groupUin, qq, targetUin)) return;
                unifiedKick(groupUin, targetUin, true);
                sendMsg(groupUin,"","已踢出"+targetUin+"并且不会再收到该成员申请\n权限使用人："+名(qq));
            }
        }
        if (msg.MessageContent.matches("^解禁? ?[0-9]{4,10}+$") && msg.MessageContent.length() >= 6){
            String indexStr = msg.MessageContent.replaceAll(" |解","");
            String uin = indexStr;
            unifiedForbidden(groupUin, uin, 0);
        }
        if(msg.MessageContent.equals("#踢禁言")) {
            unifiedForbidden(groupUin, "", 0);
            Object list=unifiedGetForbiddenList(groupUin);
            if(list==null||((ArrayList)list).size()==0) 
                sendMsg(groupUin,"", "当前没有人被禁言");
            else{
                String kickListStr = "";
                for(Object ForbiddenList : (ArrayList)list){   
                    String u = ForbiddenList.UserUin;
                    if (检查代管保护(groupUin, u, "踢出")) continue;
                    if (!有权限操作(groupUin, qq, u)) continue;
                    kickListStr+="\n"+u;
                    unifiedKick(groupUin, u, false);
                }
                sendMsg(groupUin,"", "已踢出禁言列表:"+kickListStr);
            }
        }
        if(msg.MessageContent.equals("全禁")){
            unifiedForbidden(groupUin, "", 0);
            Object list=unifiedGetForbiddenList(groupUin);
            if(list==null||((ArrayList)list).size()==0) 
                sendMsg(groupUin,"", "当前没有人被禁言");
            else{
                for(Object ForbiddenList : (ArrayList)list){
                    String u = ForbiddenList.UserUin+"";
                    if (检查代管保护(groupUin, u, "禁言")) continue;
                    if (!有权限操作(groupUin, qq, u)) continue;
                    unifiedForbidden(groupUin, u, 2592000);
                }
                sendReply(groupUin,msg, "禁言列表已加倍禁言");
            }
        }
        if(msg.MessageContent.equals("全解")){
            unifiedForbidden(groupUin, "", 0);
            Object list=unifiedGetForbiddenList(groupUin);
            if(list==null||((ArrayList)list).size() == 0) 
                sendMsg(groupUin,"", "当前没有人被禁言");
            else{
                for(Object ForbiddenList : (ArrayList)list){
                    unifiedForbidden(groupUin, ForbiddenList.UserUin+"", 0);
                }
                sendReply(groupUin,msg, "禁言列表已解禁");
            }
        }
        if(qq.equals(myUin)){
            if(msg.MessageContent.startsWith("添加代管")||msg.MessageContent.startsWith("添加管理员")||msg.MessageContent.startsWith("设置代管")||msg.MessageContent.startsWith("添加老婆")){
                String QQUin = "";
                if(msg.mAtList.size()==0){
                    sendReply(groupUin,msg,"你艾特的人呢？");
                    return;
                }
                for(String u:msg.mAtList){
                    File 代管文件 = 获取代管文件();
                    if(!代管文件.exists()){
                        try {
                            代管文件.createNewFile();
                        } catch (Exception e) {}
                    }
                    try {
                        if(jiandu(u, 简取(代管文件))){
                            sendMsg(groupUin,"","列表内的"+u+"已经是代管了 已自动略过");
                            continue;
                        }else {
                            简写(代管文件,u);
                        }
                        QQUin = QQUin + u + " ";
                    } catch (Exception e) {}
                }
                if(QQUin.replace(" ","").equals("")){
                    sendMsg(groupUin,"","以上代管已经添加过了");
                }else{ sendMsg(groupUin,"","已添加代管:\n"+QQUin);}
            }
            if(msg.MessageContent.startsWith("删除代管@")||msg.MessageContent.startsWith("删除管理员@")){
                String QQUin="";
                if(msg.mAtList.size()==0){
                    sendReply(groupUin,msg,"你艾特的人呢？");
                    return;
                }
                for(String 千:msg.mAtList){
                    File 代管文件 = 获取代管文件();
                    if(!代管文件.exists()) continue;
                    try {
                        if(jiandu(千, 简取(代管文件))){
                            简弃(代管文件,千);
                            QQUin = QQUin + 千 + " ";
                        }else sendMsg(groupUin,"","QQ "+千+"并不是代管");
                    } catch (Exception e) {}
                }
                sendMsg(groupUin,"","已删除管理员:\n"+QQUin);
                return;
            }
            if(msg.MessageContent.startsWith("删除代管")||msg.MessageContent.startsWith("删除管理员")){
                String QQUin="";
                String Stext=msg.MessageContent.substring(4).replace(" ","");
                String text=Stext.replaceAll("[\u4e00-\u9fa5]","");
                {
                    if(!text.matches("[0-9]+")){
                        sendReply(groupUin,msg,"正确方式 : 删除代管+Q号，请不要输入别的字符");
                        return;
                    }
                    File 代管文件 = 获取代管文件();
                    if(!代管文件.exists()) {
                        sendReply(groupUin,msg,"代管列表为空");
                        return;
                    }
                    try {
                        if(!jiandu(text, 简取(代管文件))){
                            sendReply(groupUin,msg,"此人并不是代管");
                            return;
                        } else {
                            简弃(代管文件,text);
                        }
                    } catch (Exception e) {}
                    QQUin = QQUin + text + " ";
                }
                sendMsg(groupUin,"","已删除管理员:\n"+QQUin);
                return;
            }      
        }
        if(msg.MessageContent.equals("查看代管")){
            File 代管文件 = 获取代管文件();
            if (!代管文件.exists()) {
                sendMsg(groupUin,"","当前没有代管");
                return;
            }
            try {
                String 代=组名(简取(代管文件));
                String 代管文本=论(代,"]","");
                代管文本=论(代管文本,"["," ");
                sendMsg(groupUin,"","当前的代管如下:\n"+代管文本);
            } catch (Exception e) {}
        }                   
        if(msg.MessageContent.equals("清空代管")){
            File 代管文件 = 获取代管文件();
            if (代管文件.exists()) {
                try {
                    全弃(代管文件);
                } catch (Exception e) {}
            }
            sendReply(groupUin,msg,"代管列表已清空");
        }
        if(msg.MessageContent.matches("^@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)+$")&&msg.mAtList.size()>=1){
            int banTime = get_time(msg);
            if(banTime > 2592000){
                sendReply(groupUin,msg,"时间太长无法禁言");
                return;
            }else if(banTime > 0){
                for(String u:msg.mAtList){
                    if (检查代管保护(groupUin, u, "禁言")) continue;
                    unifiedForbidden(groupUin,u,banTime);
                }
                return;
            }
        }
        if(msg.MessageContent.matches("^@?[\\s\\S]+[零一二三四五六七八九十]?[十百千万]?(天|分|时|小时|分钟|秒)+$")&&msg.mAtList.size()>=1){
            int str1 = msg.MessageContent.lastIndexOf(" ");
            String str =msg.MessageContent.substring(str1 + 1);
            String text=str.replaceAll("[天分时小时分钟秒]","");
            int time=CN_zh_int(text);
            int banTime = get_time_int(msg,time);
            if(banTime > 2592000){
                sendReply(groupUin,msg,"禁言时间太长无法禁言");return;
            }else if(banTime > 0){
                for(String u:msg.mAtList){
                    if (检查代管保护(groupUin, u, "禁言")) continue;
                    unifiedForbidden(groupUin,u,banTime);
                }
                return;
            }
        }
        if(msg.MessageContent.matches("^@?[\\s\\S]+([零一二三四五六七八九十]?[十百千万])+$")&&msg.mAtList.size()>=1){  
            int str = msg.MessageContent.lastIndexOf(" ");
            String text =msg.MessageContent.substring(str + 1);
            int time=CN_zh_int(text);
            for(String u:msg.mAtList){
                if (检查代管保护(groupUin, u, "禁言")) continue;
                unifiedForbidden(groupUin,u,time*60);
                return;
            }
        }                          
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

// 接下来的故事慢慢听我诉说