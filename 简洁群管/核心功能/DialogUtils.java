
// 是我想要的太多 又或是从未得到过

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
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;
import android.view.Gravity;
import android.view.View;
import android.content.DialogInterface;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.Context;
import android.content.res.Configuration;
import android.util.TypedValue;
import android.widget.AdapterView;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import java.lang.reflect.Field;

public int getCurrentTheme() {
    try {
        Context context = getActivity();
        if (context == null) return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
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

public String getBackgroundColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#121212";
    } else {
        return "#F8F9FA";
    }
}

public String getTextColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#E4E6EB";
    } else {
        return "#1A1A1A";
    }
}

public String getSubTextColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#9A9DA3";
    } else {
        return "#666666";
    }
}

public String getCardColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#1E1E1E";
    } else {
        return "#FFFFFF";
    }
}

public String getAccentColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#BB86FC";
    } else {
        return "#6750A4";
    }
}

public String getAccentColorDark() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#9B69E3";
    } else {
        return "#553E87";
    }
}

public String getSurfaceColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#2D2D2D";
    } else {
        return "#F2F2F7";
    }
}

public String getBorderColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#3A3A3C";
    } else {
        return "#E5E5EA";
    }
}

public int c(float f) {
    return dp2px(f);
}

public int dp2px(float dp) {
    try {
        DisplayMetrics metrics = new DisplayMetrics();
        Activity activity = getActivity();
        if (activity != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            return (int) (dp * metrics.density + 0.5f);
        }
    } catch (Exception e) {}
    return (int) (dp * 3 + 0.5f);
}

public GradientDrawable getShape(String color, int cornerRadius) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(color));
    shape.setCornerRadius(cornerRadius);
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

public GradientDrawable getWebShape(String baseColor, int cornerRadius) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(baseColor));
    shape.setCornerRadius(cornerRadius);
    shape.setStroke(dp2px(1), Color.parseColor(getBorderColor()));
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

class CustomArrayAdapter extends ArrayAdapter {
    private String textColor;
    
    public CustomArrayAdapter(Context context, int resource, ArrayList<String> objects, String textColor) {
        super(context, resource, objects);
        this.textColor = textColor;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setTextColor(Color.parseColor(textColor));
        textView.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
        return view;
    }
}

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                builder.setTitle("简洁群管更新日志");
                
                TextView textView = new TextView(activity);
                textView.setText("QStory精选脚本系列\n\n" +
                        "以下是简洁群管的部分更新日志 14.0以前的更新内容已丢失 因为以前的版本是临江在维护 并非海枫 找不到 并且部分更新内容我自己也不记得了\n\n" +
                        "简洁群管_14.0_更新日志\n" +
                        "- [添加] 退群拉黑\n" +
                        "——————————\n" +
                        "简洁群管_15.0_更新日志\n" +
                        "- [添加] 脚本悬浮窗可打开自助头衔\n" +
                        "——————————\n" +
                        "简洁群管_16.0_更新日志\n" +
                        "- [修复] 艾特禁言不生效的问题\n" +
                        "——————————\n" +
                        "简洁群管_17.0_更新日志\n" +
                        "- [修复] 脚本悬浮窗所有指令Toast提示失败的问题\n" +
                        "——————————\n" +
                        "简洁群管_18.0_更新日志\n" +
                        "- [修复] 撤回功能\n" +
                        "——————————\n" +
                        "简洁群管_19.0_更新日志\n" +
                        "- [移除] 彩蛋开关\n" +
                        "- [移除] 彩蛋开关相关代码\n" +
                        "- [移除] 爱腾讯爱生活\n" +
                        "——————————\n" +
                        "简洁群管_20.0_更新日志\n" +
                        "- [其他] 尝试修复QQ9.1.0以下版本 没有使用隐藏标识也不显示的问题\n" +
                        "——————————\n" +
                        "简洁群管_21.0_更新日志\n" +
                        "- [修复] 空指针异常的问题\n" +
                        "——————————\n" +
                        "简洁群管_22.0_更新日志\n" +
                        "- [修复] 退群拉黑用户无法正确写入的问题\n" +
                        "——————————\n" +
                        "简洁群管_23.0_更新日志\n" +
                        "- [优化] 群管功能显示逻辑\n" +
                        "——————————\n" +
                        "简洁群管_24..0_更新日志\n" +
                        "- [添加] 查看黑名单指令 只有自己和代管才可以触发\n" +
                        "——————————\n" +
                        "简洁群管_25.0_更新日志\n" +
                        "- [优化] 退群拉黑逻辑 现在更方便\n" +
                        "——————————\n" +
                        "简洁群管_26.0_更新日志\n" +
                        "- [修复] 代管可能会被清空\n" +
                        "——————————\n" +
                        "简洁群管_27.0_更新日志\n" +
                        "- [优化] 代管逻辑，现在 只有在发送添加代管指令的时候才会创建代管文件 防止更新简洁群管代管凭空消失\n" +
                        "——————————\n" +
                        "简洁群管_28.0_更新日志\n" +
                        "- [优化] 再次优化代管存储逻辑 以防更新简洁群管的时候 代管被清空\n" +
                        "——————————\n" +
                        "简洁群管_29.0_更新日志\n" +
                        "- [添加] 回复消息撤回 以及给踢人指令加了撤回\n" +
                        "——————————\n" +
                        "简洁群管_30.0_更新日志\n" +
                        "_ [添加] 代管保护功能 代管不会被禁言、踢黑等\n" +
                        "——————————\n" +
                        "简洁群管_31.0_更新日志\n" +
                        "- [添加] 弹窗显示群管功能 更方便的知道指令如何使用\n" +
                        "——————————\n" +
                        "简洁群管_32.0_更新日志\n" +
                        "- [拓展] 继续移除撤回消息代码\n" +
                        "——————————\n" +
                        "简洁群管=33.0_更新日志\n" +
                        "- [修复] 查看禁言列表指令失效\n" +
                        "——————————\n" +
                        "简洁群管_34.0_更新日志\n" +
                        "- [删除] 废弃的部分功能 可能导致脚本卡顿\n" +
                        "——————————\n" +
                        "简洁群管_35.0_更新日志\n" +
                        "- [添加] 不知道添加啥了\n" +
                        "——————————\n" +
                        "简洁群管_36.0_更新日志\n" +
                        "- [更改] 脚本悬浮窗弹窗主题 更美观\n" +
                        "- [修复] 打不死夜七的问题\n" +
                        "——————————\n" +
                        "简洁群管_37.0_更新日志\n" +
                        "- [添加] 脚本悬浮窗代管管理 可以通过弹窗来添加移除代管\n" +
                        "- [修复] 打不死夜七的问题\n" +
                        "- [修复] 用户提出的问题\n" +
                        "- [修复] 提出问题的用户\n" +
                        "- [拓展] 更新版本号\n" +
                        "- [优化] 代码逻辑\n" +
                        "——————————\n" +
                        "简洁群管_38.0_更新日志\n" +
                        "- [适配] QStory_1.9.0+的脚本写法\n" +
                        "——————————\n" +
                        "简洁群管_39.0更新日志\n" +
                        "- [移除] 踢人指令撤回\n" +
                        "- [添加] 部分指令显示权限使用人\n" +
                        "- [添加] 脚本悬浮窗代管管理 可以通过弹窗来添加移除代管\n" +
                        "- [添加] 脚本悬浮窗查看更新日志 如果你看到这个弹窗，那么就是此更新的内容之一\n" +
                        "——————————\n" +
                        "简洁群管_40.0_更新日志\n" +
                        "- [优化] 部分代码\n" +
                        "——————————\n" +
                        "简洁群管_41.0_更新日志\n" +
                        "- [修复] 退群拉黑相关指令失效的问题\n" +
                        "——————————\n" +
                        "简洁群管_42.0_更新日志\n" +
                        "- [拓展] 被九月做局 更新了版本号\n" +
                        "——————————\n" +
                        "简洁群管_43.0_更新日志\n" +
                        "- [添加] 脚本悬浮窗可以操作艾特禁言时间了\n" +
                        "- [其他] 现在艾特禁言任务不需要在main.java里面修改了 现在更换了艾特禁言的储存方式 艾特禁言时间需要重新配置 默认2562000秒\n" +
                        "- [其他] 简洁群管目前就没有什么需要添加的东西了 如果有建议的话加入海枫的群聊反馈哦(៸៸᳐⦁⩊⦁៸៸᳐ )੭ \n" +
                        "——————————\n" +
                        "简洁群管_44.0_更新日志\n" +
                        "- [移除] 退群拉黑开启后 提示无管理员权限无法踢出\n" +
                        "- [修复] 可能由于后台问题 导致有管理员权限 简洁群管识别到退群拉黑用户进群提示无管理员权限无法踢出的问题\n" +
                        "- [其他] 现在不管有没有管理员权限 都可以写入黑名单以及每次入群时都会尝试执行踢黑退群拉黑用户\n" +
                        "——————————\n" +
                        "简洁群管_45.0_更新日志\n" +
                        "- [添加] 脚本弹窗可以立即检测黑名单用户是否在群内 如果在群内则踢黑 没有考虑使用定时线程来检测黑名单 这样会极其耗电 脚本目前只会监听入群事件来识别黑名单用户\n" +
                        "- [其他] 优化了代码逻辑以及保留了简洁群管老旧版本的版权信息\n" +
                        "——————————\n" +
                        "简洁群管_46.0_更新日志\n" +
                        "- [修复] 弹窗在暗色模式中 渲染显示异常的问题\n" +
                        "——————————\n" +
                        "简洁群管_47.0_更新日志\n" +
                        "- [移除] Android 主题 (Theme.Material.Light.Dialog.Alert) 因为在旧版本 QQ sdk 不同导致弹窗显示风格较老\n" +
                        "- [修复] 全选弹窗消失的问题\n" +
                        "- [更改] 继续使用 THEME_DEVICE_DEFAULT_LIGHT); 主题\n" +
                        "- [其他] 顺便也修了一些存在的问题\n" +
                        "——————————\n" +
                        "简洁群管_48.0_更新日志\n" +
                        "- [添加] 如果用户系统使用浅色模式 弹窗自动切换为AlertDialog.THEME_DEVICE_DEFAULT_LIGHT(亮色窗口) 如果用户切换为深色模式 会自动切换为AlertDialog.THEME_DEVICE_DEFAULT_DARK(深色窗口)，此版本更新是为了保护好用户和开发者的眼睛 避免在深夜中查看弹窗时被太亮的弹窗闪到\n" +
                        "——————————\n" +
                        "简洁群管_49.0_更新日志\n" +
                        "- [修复] 隐藏 显示 标识 头衔等功能在历代版本失效的问题\n" +
                        "——————————\n" +
                        "简洁群管_50.0_更新日志\n" +
                        "- [移除] 撤回功能代码 之前没有发现没有删干净，现在已经删除\n" +
                        "——————————\n" +
                        "简洁群管_51.0_更新日志\n" +
                        "- [修复] 部分存在的问题\n" +
                        "——————————\n" +
                        "简洁群管_52.0_更新日志\n" +
                        "- [修复] bsh.BlockNameSpace.getInstance方法空指针异常\n" +
                        "——————————\n" +
                        "简洁群管_53.0_更新日志\n" +
                        "- [添加] addMenuItem菜单踢/踢黑，管理群聊更方便\n" +
                        "- [优化] 大量代码\n" +
                        "——————————\n" +
                        "简洁群管_54.0_更新日志\n" +
                        "- [添加] addMenuItem菜单踢/踢黑添加二次确认选项 避免误触导致滥权\n" +
                        "- [优化] 使用了部分lambda表达式以简化代码\n" +
                        "——————————\n" +
                        "简洁群管_55.0_更新日志\n" +
                        "- [修复] unifiedGetForbiddenList(groupUin) 返回的 ArrayList 可能在遍历过程中被其他线程修改\n" +
                        "- [优化] 弹窗显示效果 更炫丽\n" +
                        "——————————\n" +
                        "简洁群管_56.0_更新日志\n" +
                        "- [优化] 禁言列表\n" +
                        "——————————\n" +
                        "简洁群管_57.0_更新日志\n" +
                        "- [修复] 打不死hd的问题\n" +
                        "——————————\n" +
                        "简洁群管_58.0_更新日志\n" +
                        "- [优化] 代码逻辑\n" +
                        "——————————\n" +
                        "简洁群管_59.0_更新日志\n" +
                        "- [修复] 又又又修复禁言列表的问题以及更改禁言列表文本\n" +
                        "——————————\n" +
                        "简洁群管_60.0_更新日志\n" +
                        "- [添加] addMenuItem弹窗禁言，单位秒\n" +
                        "- [优化] 脚本大部分代码\n" +
                        "- [修复] 部分存在的问题，并打死了hd\n" +
                        "——————————\n" +
                        "简洁群管_61.0_更新日志\n" +
                        "- [修复] 被WAuxiliary脚本引擎做局导致出现的部分问题并打死了hd\n" +
                        "——————————\n" +
                        "简洁群管_62.0_更新日志\n" +
                        "- [优化] 底层代码，修复了部分泛型和lambda表达式\n" +
                        "——————————\n" +
                        "简洁群管_63.0_更新日志\n" +
                        "- [修复] 禁言列表不太正常的问题\n" +
                        "——————————\n" +
                        "简洁群管_64.0_更新日志\n" +
                        "- [修复] 部分存在报错的问题\n" +
                        "- [移除] lambda表达式\n" +
                        "——————————\n" +
                        "简洁群管_65.0_更新日志\n" +
                        "- [修复] qs2.0.3的祖传脚本引擎限制导致无法使用final的问题\n" +
                        "——————————\n" +
                        "简洁群管_66.0_更新日志\n" +
                        "- [移除] 快捷菜单项，如需使用，请使用简洁群管-Plus\n" +
                        "——————————\n" +
                        "简洁群管_67.0_更新日志\n" +
                        "- [修复] 部分不太正常的变量\n" +
                        "——————————\n" +
                        "简洁群管_68.0_更新日志\n" +
                        "- [修复] 部分存在报错的问题\n" +
                        "- [修复] 部分不太正常的变量\n" +
                        "- [修复] 代管自动解禁开关和指令存储不同的问题\n" +
                        "- [修复] 代管发送自动解禁代管指令无法控制的问题\n" +
                        "- [添加] 开启/关闭自动解禁代管 全局生效 代管被禁言时 会尝试自动解禁，但是此功能可能有点不稳定，如果没有权限 则提示无法解禁代管\n" +
                        "- [添加] addMenuItem快捷菜单，支持禁言、踢、踢黑 长按信息即可显示\n" +
                        "- [添加] addMenuItem快捷菜单只在群聊显示，如需使用请更新QStory至2.0.4+\n" +
                        "- [添加] 使用了部分泛型以及尝试实用性使用lambda表达式\n" +
                        "- [更改] 部分文本显示，更简洁\n" +
                        "——————————\n" +
                        "简洁群管_69.0_更新日志\n" +
                        "- [更改] addItem为addTemporaryItem\n" +
                        "- [更改] 简洁群管菜单只能在群聊显示，而不是私聊\n" +
                        "- [移除] toast只能在群聊中使用的代码\n" +
                        "——————————\n" +
                        "简洁群管_70.0_更新日志\n" +
                        "- [修复] 遍历的同时修改导致出现部分问题\n" +
                        "——————————\n" +
                        "简洁群管_71.0_更新日志\n" +
                        "- [修复] 部分问题导致的空指针异常以及错误\n" +
                        "——————————\n" +
                        "简洁群管_72.0_更新日志\n" +
                        "- [添加] addMenuItem设置头衔快捷菜单 如果我们是群主 则显示快捷菜单 如果不是群主 则不显示，管理员/私聊不显示快捷菜单\n" +
                        "- [添加] addMenuItem快捷菜单 如果我们是群主/管理 则显示快捷菜单 如果不是 则不显示\n" +
                        "- [更改] 长按自己的消息不显示踢 踢黑 禁言 设置头衔除外\n" +
                        "——————————\n" +
                        "简洁群管_73.0_更新日志\n" +
                        "- [修复] bsh.NameSpace.getMethods\n" +
                        "- [更改] 弹窗风格 更美观\n" +
                        "- [其他] 打死hd\n" +
                        "——————————\n" +
                        "简洁群管_74.0_更新日志\n" +
                        "- [适配] toast弹窗支持深色模式，如果用户系统是浅色模式，则是白色弹窗，深色模式同理\n" +
                        "- [更改] 由于自定义弹窗限制只能在前台显示，后台不显示 更改弹窗逻辑，如果QQ在前台时则显示自定义弹窗，在后台则使用默认Android弹窗\n" +
                        "——————————\n" +
                        "简洁群管_75.0_更新日志\n" +
                        "- [更改] addMenuItem菜单太多导致群待办,设置精华没办法正常显示，在已经将这些功能整合在一个addMenuItem通过弹窗调用\n" +
                        "——————————\n" +
                        "简洁群管_76.0_更新日志\n" +
                        "- [更改] addMenuItem菜单，如果你是群主，可以利用addMenuItem来权限任何用户，如果你是管理员，addMenuItem菜单只在长按普通群员才能显示，现在管理员长按管理员/群主信息不会显示这个addMenuItem菜单了，不影响群主长按管理员显示addMenuItem菜单\n" +
                        "——————————\n" +
                        "简洁群管_77.0_更新日志\n" +
                        "- [更改] addMenuItem菜单原始的使用 getMemberInfo 获取群信息，这样会导致盯帧每次使用会慢100ms 使用 getGroupInfo 来获取群信息\n" +
                        "- [移除] 部分log输出日志代码 看着难受\n" +
                        "——————————\n" +
                        "简洁群管_78.0_更新日志\n" +
                        "- [更新] 版本号\n" +
                        "——————————\n" +
                        "简洁群管_79.0_更新日志\n" +
                        "- [优化] addMenuItem获取权限方式以优化性能\n" +
                        "——————————\n" +
                        "简洁群管_80.0_更新日志\n" +
                        "- [修复] 空指针异常在onMsg添加空指针检查，确保msg不为null\n" +
                        "- [修复] 代管保护逻辑，在极端情况下代管也会被踢出的问题\n" +
                        "- [优化] 线程，在遍历ArrayList时使用safeCopyList方法创建副本，避免并发修改异常\n" +
                        "- [其他] 增强错误处理，在可能为空的地方添加了额外的空值检查\n" +
                        "——————————\n" +
                        "简洁群管_81.0_更新日志\n" +
                        "- [添加] 在 isGN 方法添加了空值检查\n" +
                        "- [添加] 在所有访问 msg.MessageContent 的地方都添加了空值检查\n" +
                        "- [更改] 将 groupInfoCache 从 HashMap 改为 ConcurrentHashMap 以避免并发访问问题\n" +
                        "- [更改] Map改为ConcurrentHashMap：groupInfoCache,Arab2Chinese,UnitMap\n" +
                        "- [更改] List改为CopyOnWriteArrayList：在快捷群管方法中的items和actions\n" +
                        "- [添加] 在 onMsg 方法中为 mAtList 添加了同步块，确保线程安全\n" +
                        "- [添加] import java.util.concurrent.ConcurrentHashMap;\n" +
                        "- [添加] 在初始化代码中也使用safeCopyList确保线程安全\n" +
                        "- [添加] 快捷群管加入黑名单功能，触发后，该用户会被立即踢黑并加入黑名单无法再次入群\n" +
                        "- [更改] 为onMsg方法添加了专门的锁对象msgLock，确保消息处理的线程安全\n" +
                        "- [其他] 保持所有原有的同步方法和同步块\n" +
                        "- [修复] 可能导致并发修改异常和空指针异常的地方\n" +
                        "——————————\n" +
                        "简洁群管_82.0_更新日志\n" +
                        "- [更改] 将 groupInfoCache 从 HashMap 改为 ConcurrentHashMap 以避免并发访问问题\n" +
                        "- [更改] Map改为ConcurrentHashMap：groupInfoCache,Arab2Chinese,UnitMap\n" +
                        "- [更改] List改为CopyOnWriteArrayList：在快捷群管方法中的items和actions\n" +
                        "- [添加] 在 onMsg 方法中为 mAtList 添加了同步块，确保线程安全\n" +
                        "- [添加] import java.util.concurrent.ConcurrentHashMap;\n" +
                        "- [添加] 在初始化代码中也使用safeCopyList确保线程安全\n" +
                        "- [添加] 快捷群管加入黑名单功能，触发后，该用户会被立即踢黑并加入黑名单无法再次入群\n" +
                        "- [更改] 为onMsg方法添加了专门的锁对象msgLock，确保消息处理的线程安全\n" +
                        "- [其他] 保持所有原有的同步方法和同步块\n" +
                        "——————————\n" +
                        "简洁群管_83.0_更新日志\n" +
                        "- [更改] 在null的地方添加了检查\n" +
                        "- [更改] 在onMsg方法中对msg.MessageContent的使用\n" +
                        "- [更改] GroupInfo变Object\n" +
                        "- [添加] 使用safeCopyList方法创建ArrayList，避免在遍历时修改\n" +
                        "- [添加] 在一些方法中添加try-catch以防止报错\n" +
                        "- [其他] 82.0更新日志忘加了，已添加\n" +
                        "——————————\n" +
                        "简洁群管_84.0_更新日志\n" +
                        "- [优化] 部分代码防止wa引擎导致的报错\n" +
                        "——————————\n" +
                        "简洁群管_85.0_更新日志\n" +
                        "- [移除] 脚本的自定义toast弹窗，使用qs传统弹窗\n" +
                        "——————————\n" +
                        "简洁群管_86.0_更新日志\n" +
                        "- [修复] 部分写法错误以及报错\n" +
                        "——————————\n" +
                        "简洁群管_87.0_更新日志\n" +
                        "- [优化] 全解 全禁触发逻辑\n" +
                        "- [优化] @用户 时间 的准确性 例如之前：@用户 一行白鹭上青天 该用户会被禁言一天 现已优化\n" +
                        "——————————\n" +
                        "简洁群管_88.0_更新日志\n" +
                        "- [变更] 继续使用旧版简洁群管禁言逻辑，因为87.0的禁言过于严格导致无法多个禁言，有的时候，最简单的方法是最可靠的\n" +
                        "——————————\n" +
                        "简洁群管_89.0_更新日志\n" +
                        "- [其他] 版本号\n" +
                        "——————————\n" +
                        "简洁群管_90.0_更新日志\n" +
                        "- [修复] 指令失效\n" +
                        "——————————\n" +
                        "简洁群管_91.0_更新日志\n" +
                        "- [添加] 封禁联盟，只有简洁群管所有者可以添加\n" +
                        "- [添加] 联盟指令，自己去简洁群管使用方法看\n" +
                        "- [其他] 联盟管理员就是代管，当封禁了联盟用户之后，其绑定的联盟会进行封禁他\n" +
                        "- [修复] 退群拉黑失效的问题\n" +
                        "- [更改] 封禁联盟如果进入联盟群聊，会被踢黑，与退群拉黑原理相同\n" +
                        "- [修复] 封禁联盟绑定的群聊没有踢出的问题\n" +
                        "- [更改] 现在脚本会在每次加载的时候检测退群拉黑和封禁联盟\n" +
                        "——————————\n" +
                        "简洁群管_92.0_更新日志\n" +
                        "- [修复] /unfban指令无效的问题\n" +
                        "- [添加] /fban和/unfan可以使用回复进行 怎么用看群管功能使用方法\n" +
                        "- [更改] 移除每次发送/fban的冷却\n" +
                        "- [添加] 群管功能的指令\n" +
                        "- [优化] onMsg(Object msg){方法\n" +
                        "- [提示] 联盟介绍：比如我有三个群 都绑定了联盟 我在其中一个群fban了一个用户 这个用户在另外两个群也会被踢，如果不在，会监听入群事件\n" +
                        "——————————\n" +
                        "简洁群管_92.0_更新日志\n" +
                        "- [修复] 可能导致QQ闪退的问题\n" +
                        "——————————\n" +
                        "简洁群管_93.0_更新日志\n" +
                        "- [修复] 继续修复闪退的问题\n" +
                        "- [修复] /fban和/unfban指令失效的问题\n" +
                        "——————————\n" +
                        "简洁群管_94.0_更新日志\n" +
                        "- [修复] 隐藏显示头衔 标识 等级 失效的问题\n" +
                        "——————————\n" +
                        "简洁群管_95.0_更新日志\n" +
                        "- [添加] 联盟更多介绍\n" +
                        "——————————\n" +
                        "简洁群管_96.0_更新日志\n" +
                        "- [适配] 最新版QStory\n" +
                        "——————————\n" +
                        "简洁群管_97.0_更新日志\n" +
                        "- [添加] 实验性添加脚本预览图\n" +
                        "——————————\n" +
                        "简洁群管_98.0_更新日志\n" +
                        "- [修复] 网络请求导致的闪退 (NetworkOnMainThreadException)：原脚本在 onMsg 中直接调用了 SetTroopShowHonour 等包含网络请求(httppost)的方法。如果脚本运行在主线程，这会导致立马闪退。已将这些操作放入子线程执行。\n" +
                        "- [移除] onMsg 的全局同步锁 (msgLock)：原脚本对整个消息处理过程加了锁，这会导致高并发（如群里消息多）时消息处理阻塞，甚至导致 ANR（应用无响应）从而闪退。已移除该锁，提高并发性能\n" +
                        "- [优化] mAtList 的线程安全：移除了对 msg.mAtList 的强制同步锁（这可能导致死锁或空指针），改用更安全的 safeCopyList 方式获取副本\n" +
                        "- [优化] 启动时的联检测线程：原逻辑是多重循环嵌套（群x成员x封禁列表），效率极低，容易造成卡顿。优化了逻辑，先缓存封禁名单，大幅减少循环次数，并增加了空指针保护\n" +
                        "- [添加] 空指针防护：在 getGroupMemberList 等可能返回 null 的接口处增加了严谨的判空处理，防止遍历时出现空指针异常导致脚本崩溃\n" +
                        "——————————\n" +
                        "简洁群管_99.0_更新日志\n" +
                        "- [移除] 导致空指针异常的全局锁，改用局部同步和线程安全的集合\n" +
                        "- [优化] 启动时联盟检测线程，增加了空指针检查和异常处理\n" +
                        "- [更改] 将网络请求操作改为异步线程执行，避免主线程阻塞\n" +
                        "- [添加] 更多的空指针检查，特别是在文件操作和集合遍历时\n" +
                        "- [更改]使用了线程安全的集合如 ConcurrentHashMap 和 CopyOnWriteArrayList\n" +
                        "- [修复] 反射操作中的异常处理，避免因反射失败导致崩溃\n" +
                        "- [优化] 线程睡眠时间，减少资源占用\n" +
                        "- [添加] 错误处理，在可能出现异常的地方添加了 try-catch\n" +
                        "——————————\n" +
                        "简洁群管_100.0_更新日志\n" +
                        "- [添加] 快捷菜单封禁联盟 前提是该群组属于联盟群组\n" +
                        "- [添加] 如果 这个用户已经被封禁，再次封禁会提示\n" +
                        "- [修复] bsh.BlockNameSpace.getInstance\n" +
                        "——————————\n" +
                        "简洁群管_101.0_更新日志\n" +
                        "- [优化] 脚本所有dialog弹窗的显示效果也使现在浅色模式部分功能弹窗也能正常显示了\n" +
                        "- [优化] 快捷群管封禁联盟的弹窗效果，已防止过于卡顿\n" +
                        "- [添加] 在执行联盟封禁时，显示 正在执行联盟封禁... 的提示\n" +
                        "——————————\n" +
                        "简洁群管_102.0_更新日志\n" +
                        "- [优化] 快捷群管的按钮，现在更好看了\n" +
                        "- [修复] 快捷群管按钮错乱的问题\n" +
                        "——————————\n" +
                        "简洁群管_103.0_更新日志\n" +
                        "- [更改] quickManageMenuItem为haifeng520\n" +
                        "- [更改] 部分文本\n" +
                        "- [添加] 群管功能以及弹窗添加版本号\n" +
                        "- [修复] 退群拉黑只写入黑名单不监听入群事件踢出\n" +
                        "- [修复] 一些已知问题\n" +
                        "- [修复] 夜七不是猫娘\n" +
                        "- [修复] 打不死夜七\n" +
                        "- [修复] 夜七不听话\n" +
                        "——————————\n" +
                        "简洁群管_104.0_更新日志\n" +
                        "- [添加] 为快捷群管添加更多ui，当你在没有权限的群聊，长按快捷群管会显示没有权限操作该用户而不是空白\n" +
                        "- [添加] 快捷群管添加撤回功能，画个大饼：以后可能会支持设置精华，设置群待办等\n" +
                        "- [优化] 代管管理弹窗 黑名单管理弹窗\n" +
                        "——————————\n" +
                        "简洁群管_105.0_更新日志_2026 Happy New Year！\n" +
                        "- [添加] 为快捷群管添加没有权限操作该用户 触发条件是你必须是管理操作群主 管理操作管理 没有群管权限\n" +
                        "- [添加] 为快捷群管撤回功能添加二次确认\n" +
                        "- [修复] 设置艾特禁言时间失效\n" +
                        "- [修复] 无论是否代管都可以触发指令\n" +
                        "- [调整] 现在代管不可以清空代管了 只可以使用者清空\n" +
                        "- [更改] 脚本代码被拆开为好几个Java，具体自己看\n" +
                        "——————————\n" +
                        "简洁群管_106.0_更新日志\n" +
                        "- [更改] 退群拉黑提示文本\n" +
                        "- [修复] Toast .LENGTH_SHORT\n" +
                                                "——————————\n" +
                        "简洁群管_107.0_更新日志\n" +
                        "- [优化] 代码底层逻辑\n" +
                        "——————————\n" +
                        "简洁群管_108.0_更新日志\n" +
                        "- [优化] 使用Material Design 3.0风格弹窗主题优化显示效果\n" +
                        "——————————\n" +
                        "简洁群管_109.0_更新日志\n" +
                        "- [新增] 某个神秘功能\n" +
                        "——————————\n" +
                        "简洁群管_110.0_更新日志\n" +
                        "- [修复] 艾特用户禁言无法使用阿拉伯数字的问题\n" +
                        "——————————\n" +
                        "简洁群管_111.0_更新日志\n" +
                        "- [修复] 脚本文件初始化异常\n" +
                        "——————————\n" +
                        "简洁群管_112.0_更新日志\n" +
                        "- [新增] 检测error.txt文件是否存在，存在则删除\n" +
                        "- [优化] 代管文件创建逻辑\n" +
                        "- [修复] 艾特禁言用户有点不太正常的问题\n" +
                        "——————————\n" +
                        "简洁群管_113.0_更新日志\n" +
                        "- [新增] 快捷群管菜单添加批量撤回\n" +
                        "——————————\n" +
                        "简洁群管_114.0_更新日志\n" +
                        "- [修复] 退群拉黑开启后无法关闭的问题\n" +
                        "——————————\n" +
                        "简洁群管_115.0_更新日志\n" +
                        "- [移除] 部分无用的更新日志\n" +
                        "——————————\n" +
                        "简洁群管_116.0_更新日志\n" +
                        "- [调整] 回滚旧版本ui\n" +
                        "——————————\n" +
                        "简洁群管_117.0_更新日志\n" +
                        "- [优化] 优化了脚本主题\n" +
                        "——————————\n" +
                        "简洁群管_118.0_更新日志\n" +
                        "- [修复] 一些存在的问题\n" +
                        "——————————\n" +
                        "简洁群管_119.0_更新日志\n" +
                        "- [添加] 快捷群管以及设置艾特禁言时间方法添加禁言添加显示时间\n\n" +
                        "喜欢的人要早点说 有bug及时反馈\n\n交流群:https://t.me/XiaoYu_Chat");
                textView.setTextSize(14);
                textView.setLineSpacing(dp2px(4), 1);
                textView.setTextIsSelectable(true);
                
                int textColor = Color.parseColor(getTextColor());
                textView.setTextColor(textColor);
                textView.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
                
                ScrollView scrollView = new ScrollView(activity);
                scrollView.addView(textView);
                
                LinearLayout container = new LinearLayout(activity);
                container.setOrientation(LinearLayout.VERTICAL);
                container.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
                
                GradientDrawable bg = getWebShape(getCardColor(), dp2px(16));
                scrollView.setBackground(bg);
                
                container.addView(scrollView);
                
                builder.setView(container);
                builder.setPositiveButton("确定", null);
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                
                Window window = dialog.getWindow();
                if (window != null) {
                    GradientDrawable windowBg = getShape(getCardColor(), dp2px(20));
                    window.setBackgroundDrawable(windowBg);
                }
            } catch (Exception e) {
            }
        }
    });
}

public void showGroupManageDialog() {
    try {
        String qqVersion = "QQVersion未知";
        try {
            qqVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
        
        }
        
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
                "17. /unfban 取消这个封禁联盟用户\n\n" +
                "回复 禁言+分钟+理由\n" +
                "回复 /ban - 踢黑\n" +
                "回复 /kick - 普通踢出\n" +
                "回复 /fban - 封禁联盟用户\n" +
                "回复 /unfban - 取消封禁联盟用户\n\n" +
                "- [提示] 联盟介绍：比如我有三个群 都绑定了联盟 我在其中一个群fban了一个用户 这个用户在另外两个群也会被踢，如果不在，会监听入群事件\n" +
                "- [提示] 当那个用户被 /fban 所在的其他几个绑定联盟的群组会立即踢黑 如果不在 会监听入群事件精准识别踢黑\n" +
                "- [提示] 代管不会被联盟封禁 不会被禁言 踢黑等\n\n" +
                "临江：634941583\n" +
                "海枫：https://t.me/XiaoYu_Chat";

        Activity activity = getActivity();
        if (activity == null) return;

        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    TextView textView = new TextView(activity);
                    textView.setText(dialogContent);
                    textView.setTextSize(14);
                    textView.setTextIsSelectable(true);
                    textView.setLineSpacing(dp2px(4), 1);
                    int textColor = Color.parseColor(getTextColor());
                    textView.setTextColor(textColor);
                    textView.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));

                    ScrollView scrollView = new ScrollView(activity);
                    scrollView.addView(textView);
                    
                    LinearLayout container = new LinearLayout(activity);
                    container.setOrientation(LinearLayout.VERTICAL);
                    container.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
                    
                    GradientDrawable bg = getWebShape(getCardColor(), dp2px(16));
                    scrollView.setBackground(bg);
                    
                    container.addView(scrollView);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                    builder.setTitle("群管功能(QQVersion：" + qqVersion + ")")
                        .setView(container)
                        .setNegativeButton("关闭", null);
                    
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    
                    Window window = dialog.getWindow();
                    if (window != null) {
                        GradientDrawable windowBg = getShape(getCardColor(), dp2px(20));
                        window.setBackgroundDrawable(windowBg);
                    }
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
                
                LinearLayout mainLayout = new LinearLayout(activity);
                mainLayout.setOrientation(LinearLayout.VERTICAL);
                mainLayout.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
                mainLayout.setBackgroundColor(Color.parseColor(getBackgroundColor()));
                
                LinearLayout headerLayout = new LinearLayout(activity);
                headerLayout.setOrientation(LinearLayout.HORIZONTAL);
                headerLayout.setGravity(Gravity.CENTER_VERTICAL);
                headerLayout.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                headerLayout.setBackground(getWebShape(getCardColor(), dp2px(12)));
                
                LinearLayout.LayoutParams headerParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                );
                headerParams.setMargins(0, 0, 0, dp2px(12));
                headerLayout.setLayoutParams(headerParams);
                
                TextView title = new TextView(activity);
                title.setText("黑名单管理");
                title.setTextSize(16);
                title.setTypeface(Typeface.DEFAULT_BOLD);
                title.setTextColor(Color.parseColor(getTextColor()));
                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                title.setLayoutParams(titleParams);
                headerLayout.addView(title);
                
                TextView countText = new TextView(activity);
                countText.setText("数量: " + 黑名单列表.size());
                countText.setTextSize(14);
                countText.setTextColor(Color.parseColor(getTextColor()));
                headerLayout.addView(countText);
                
                mainLayout.addView(headerLayout);
                
                LinearLayout inputLayout = new LinearLayout(activity);
                inputLayout.setOrientation(LinearLayout.HORIZONTAL);
                inputLayout.setGravity(Gravity.CENTER_VERTICAL);
                inputLayout.setPadding(0, 0, 0, dp2px(12));
                
                final EditText inputEditText = new EditText(activity);
                inputEditText.setHint("输入QQ号");
                inputEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
                inputEditText.setTextColor(Color.parseColor(getTextColor()));
                inputEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(10)));
                inputEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                inputEditText.setTextSize(14);
                
                LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                inputParams.setMargins(0, 0, dp2px(8), 0);
                inputEditText.setLayoutParams(inputParams);
                inputLayout.addView(inputEditText);
                
                Button addBtn = new Button(activity);
                addBtn.setText("添加");
                addBtn.setTextSize(14);
                addBtn.setTextColor(Color.WHITE);
                addBtn.setGravity(Gravity.CENTER);
                addBtn.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                addBtn.setAllCaps(false);
                
                GradientDrawable addBg = getShape(getAccentColor(), dp2px(10));
                addBtn.setBackground(addBg);
                
                LinearLayout.LayoutParams addBtnParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                );
                addBtn.setLayoutParams(addBtnParams);
                inputLayout.addView(addBtn);
                
                mainLayout.addView(inputLayout);
                
                LinearLayout buttonLayout = new LinearLayout(activity);
                buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
                buttonLayout.setGravity(Gravity.CENTER);
                buttonLayout.setPadding(0, 0, 0, dp2px(12));
                
                String successColor = "#34C759";
                String errorColor = "#FF3B30";
                
                Button removeBtn = new Button(activity);
                removeBtn.setText("删除选中");
                removeBtn.setTextSize(14);
                removeBtn.setTextColor(Color.WHITE);
                removeBtn.setGravity(Gravity.CENTER);
                removeBtn.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
                removeBtn.setAllCaps(false);
                
                GradientDrawable removeBg = getShape(successColor, dp2px(10));
                removeBtn.setBackground(removeBg);
                
                LinearLayout.LayoutParams removeParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                removeParams.setMargins(0, 0, dp2px(8), 0);
                removeBtn.setLayoutParams(removeParams);
                
                Button clearBtn = new Button(activity);
                clearBtn.setText("清空全部");
                clearBtn.setTextSize(14);
                clearBtn.setTextColor(Color.WHITE);
                clearBtn.setGravity(Gravity.CENTER);
                clearBtn.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
                clearBtn.setAllCaps(false);
                
                GradientDrawable clearBg = getShape(errorColor, dp2px(10));
                clearBtn.setBackground(clearBg);
                
                LinearLayout.LayoutParams clearParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                clearParams.setMargins(dp2px(8), 0, 0, 0);
                clearBtn.setLayoutParams(clearParams);
                
                buttonLayout.addView(removeBtn);
                buttonLayout.addView(clearBtn);
                
                mainLayout.addView(buttonLayout);
                
                final ListView listView = new ListView(activity);
                final CustomArrayAdapter adapter = new CustomArrayAdapter(activity, 
                    android.R.layout.simple_list_item_multiple_choice, 
                    黑名单列表,
                    getTextColor()
                );
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setDividerHeight(0);
                
                LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(180)
                );
                listView.setLayoutParams(listParams);
                
                GradientDrawable listBg = getWebShape(getCardColor(), dp2px(12));
                listView.setBackground(listBg);
                
                mainLayout.addView(listView);
                
                addBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            String[] qqs = input.split("[,，\\s]+");
                            int addedCount = 0;
                            for (String qq : qqs) {
                                if (qq.matches("[0-9]{4,11}")) {
                                    if (!黑名单列表.contains(qq)) {
                                        try {
                                            简写(黑名单文件, qq);
                                            addedCount++;
                                        } catch (Exception e) {}
                                    }
                                }
                            }
                            if (addedCount > 0) {
                                toast("已添加 " + addedCount + " 个黑名单");
                                
                                黑名单列表.clear();
                                黑名单列表.addAll(简取(黑名单文件));
                                adapter.clear();
                                adapter.addAll(黑名单列表);
                                adapter.notifyDataSetChanged();
                                
                                countText.setText("数量: " + 黑名单列表.size());
                                inputEditText.setText("");
                            } else {
                                toast("没有添加新的黑名单");
                            }
                        } else {
                            toast("请输入QQ号");
                        }
                    }
                });
                
                removeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int removedCount = 0;
                        ArrayList<String> 黑名单列表副本 = safeCopyList(黑名单列表);
                        for (int i = 0; i < listView.getCount(); i++) {
                            if (listView.isItemChecked(i)) {
                                String qq = (String)黑名单列表副本.get(i);
                                try {
                                    简弃(黑名单文件, qq);
                                    removedCount++;
                                } catch (Exception e) {}
                            }
                        }
                        if (removedCount > 0) {
                            toast("已删除 " + removedCount + " 个黑名单");
                            
                            黑名单列表.clear();
                            黑名单列表.addAll(简取(黑名单文件));
                            adapter.clear();
                            adapter.addAll(黑名单列表);
                            adapter.notifyDataSetChanged();
                            
                            countText.setText("数量: " + 黑名单列表.size());
                            
                            for (int i = 0; i < listView.getCount(); i++) {
                                listView.setItemChecked(i, false);
                            }
                        } else {
                            toast("请先选择要删除的黑名单");
                        }
                    }
                });
                
                clearBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            全弃(黑名单文件);
                            toast("已清空所有黑名单");
                            
                            黑名单列表.clear();
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                            
                            countText.setText("数量: " + 黑名单列表.size());
                        } catch (Exception e) {
                            toast("清空失败: " + e.getMessage());
                        }
                    }
                });
                
                builder.setView(mainLayout);
                builder.setNegativeButton("关闭", null);
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                
                Window window = dialog.getWindow();
                if (window != null) {
                    GradientDrawable windowBg = getShape(getCardColor(), dp2px(20));
                    window.setBackgroundDrawable(windowBg);
                }
                
                dialog.show();
                
            } catch (Exception e) {
                toast("打开黑名单管理失败: " + e.getMessage());
            }
        }
    });
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
                final ArrayList 代管列表;
                
                if (!代管文件.exists()) {
                    代管列表 = new ArrayList();
                } else {
                    代管列表 = 简取(代管文件);
                }
                
                LinearLayout mainLayout = new LinearLayout(activity);
                mainLayout.setOrientation(LinearLayout.VERTICAL);
                mainLayout.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
                mainLayout.setBackgroundColor(Color.parseColor(getBackgroundColor()));
                
                LinearLayout headerLayout = new LinearLayout(activity);
                headerLayout.setOrientation(LinearLayout.HORIZONTAL);
                headerLayout.setGravity(Gravity.CENTER_VERTICAL);
                headerLayout.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                headerLayout.setBackground(getWebShape(getCardColor(), dp2px(12)));
                
                LinearLayout.LayoutParams headerParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                );
                headerParams.setMargins(0, 0, 0, dp2px(12));
                headerLayout.setLayoutParams(headerParams);
                
                TextView title = new TextView(activity);
                title.setText("代管管理");
                title.setTextSize(16);
                title.setTypeface(Typeface.DEFAULT_BOLD);
                title.setTextColor(Color.parseColor(getTextColor()));
                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                title.setLayoutParams(titleParams);
                headerLayout.addView(title);
                
                TextView countText = new TextView(activity);
                countText.setText("数量: " + 代管列表.size());
                countText.setTextSize(14);
                countText.setTextColor(Color.parseColor(getTextColor()));
                headerLayout.addView(countText);
                
                mainLayout.addView(headerLayout);
                
                LinearLayout inputLayout = new LinearLayout(activity);
                inputLayout.setOrientation(LinearLayout.HORIZONTAL);
                inputLayout.setGravity(Gravity.CENTER_VERTICAL);
                inputLayout.setPadding(0, 0, 0, dp2px(12));
                
                final EditText inputEditText = new EditText(activity);
                inputEditText.setHint("输入QQ号");
                inputEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
                inputEditText.setTextColor(Color.parseColor(getTextColor()));
                inputEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(10)));
                inputEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                inputEditText.setTextSize(14);
                
                LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                inputParams.setMargins(0, 0, dp2px(8), 0);
                inputEditText.setLayoutParams(inputParams);
                inputLayout.addView(inputEditText);
                
                Button addBtn = new Button(activity);
                addBtn.setText("添加");
                addBtn.setTextSize(14);
                addBtn.setTextColor(Color.WHITE);
                addBtn.setGravity(Gravity.CENTER);
                addBtn.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                addBtn.setAllCaps(false);
                
                GradientDrawable addBg = getShape(getAccentColor(), dp2px(10));
                addBtn.setBackground(addBg);
                
                LinearLayout.LayoutParams addBtnParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                );
                addBtn.setLayoutParams(addBtnParams);
                inputLayout.addView(addBtn);
                
                mainLayout.addView(inputLayout);
                
                LinearLayout buttonLayout = new LinearLayout(activity);
                buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
                buttonLayout.setGravity(Gravity.CENTER);
                buttonLayout.setPadding(0, 0, 0, dp2px(12));
                
                String successColor = "#34C759";
                String errorColor = "#FF3B30";
                
                Button removeBtn = new Button(activity);
                removeBtn.setText("删除选中");
                removeBtn.setTextSize(14);
                removeBtn.setTextColor(Color.WHITE);
                removeBtn.setGravity(Gravity.CENTER);
                removeBtn.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
                removeBtn.setAllCaps(false);
                
                GradientDrawable removeBg = getShape(successColor, dp2px(10));
                removeBtn.setBackground(removeBg);
                
                LinearLayout.LayoutParams removeParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                removeParams.setMargins(0, 0, dp2px(8), 0);
                removeBtn.setLayoutParams(removeParams);
                
                Button clearBtn = new Button(activity);
                clearBtn.setText("清空全部");
                clearBtn.setTextSize(14);
                clearBtn.setTextColor(Color.WHITE);
                clearBtn.setGravity(Gravity.CENTER);
                clearBtn.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
                clearBtn.setAllCaps(false);
                
                GradientDrawable clearBg = getShape(errorColor, dp2px(10));
                clearBtn.setBackground(clearBg);
                
                LinearLayout.LayoutParams clearParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                clearParams.setMargins(dp2px(8), 0, 0, 0);
                clearBtn.setLayoutParams(clearParams);
                
                buttonLayout.addView(removeBtn);
                buttonLayout.addView(clearBtn);
                
                mainLayout.addView(buttonLayout);
                
                final ListView listView = new ListView(activity);
                final CustomArrayAdapter adapter = new CustomArrayAdapter(activity, 
                    android.R.layout.simple_list_item_multiple_choice, 
                    代管列表,
                    getTextColor()
                );
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setDividerHeight(0);
                
                LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(180)
                );
                listView.setLayoutParams(listParams);
                
                GradientDrawable listBg = getWebShape(getCardColor(), dp2px(12));
                listView.setBackground(listBg);
                
                mainLayout.addView(listView);
                
                addBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            String[] qqs = input.split("[,，\\s]+");
                            int addedCount = 0;
                            for (String qq : qqs) {
                                if (qq.matches("[0-9]{4,11}")) {
                                    if (!代管列表.contains(qq)) {
                                        try {
                                            File f = 创建代管文件();
                                            简写(f, qq);
                                            addedCount++;
                                        } catch (Exception e) {}
                                    }
                                }
                            }
                            if (addedCount > 0) {
                                toast("已添加 " + addedCount + " 个代管");
                                
                                代管列表.clear();
                                代管列表.addAll(简取(代管文件));
                                adapter.clear();
                                adapter.addAll(代管列表);
                                adapter.notifyDataSetChanged();
                                
                                countText.setText("数量: " + 代管列表.size());
                                inputEditText.setText("");
                            } else {
                                toast("没有添加新的代管");
                            }
                        } else {
                            toast("请输入QQ号");
                        }
                    }
                });
                
                removeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int removedCount = 0;
                        ArrayList<String> 代管列表副本 = safeCopyList(代管列表);
                        for (int i = 0; i < listView.getCount(); i++) {
                            if (listView.isItemChecked(i)) {
                                String qq = (String)代管列表副本.get(i);
                                try {
                                    简弃(代管文件, qq);
                                    removedCount++;
                                } catch (Exception e) {}
                            }
                        }
                        if (removedCount > 0) {
                            toast("已删除 " + removedCount + " 个代管");
                            
                            代管列表.clear();
                            代管列表.addAll(简取(代管文件));
                            adapter.clear();
                            adapter.addAll(代管列表);
                            adapter.notifyDataSetChanged();
                            
                            countText.setText("数量: " + 代管列表.size());
                            
                            for (int i = 0; i < listView.getCount(); i++) {
                                listView.setItemChecked(i, false);
                            }
                        } else {
                            toast("请先选择要删除的代管");
                        }
                    }
                });
                
                clearBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            全弃(代管文件);
                            toast("已清空所有代管");
                            
                            代管列表.clear();
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                            
                            countText.setText("数量: " + 代管列表.size());
                        } catch (Exception e) {
                            toast("清空失败: " + e.getMessage());
                        }
                    }
                });
                
                builder.setView(mainLayout);
                builder.setNegativeButton("关闭", null);
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                
                Window window = dialog.getWindow();
                if (window != null) {
                    GradientDrawable windowBg = getShape(getCardColor(), dp2px(20));
                    window.setBackgroundDrawable(windowBg);
                }
                
                dialog.show();
                
            } catch (Exception e) {
                toast("打开代管管理失败: " + e.getMessage());
            }
        }
    });
}