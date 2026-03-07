
// 作 临江踏雨不返 海枫
// 发送 群管功能 以查看功能
// 部分接口 卑微萌新
// 部分写法源码 秩河 尹志平 群鹅 天啦噜

// 你说你讨厌被骗 可你骗我的时候也没有心软

// 其实 我的心也想离你近一点

// 此脚本存在绝大多数中文变量 如果你没有Java基础请勿随意修改 可能造成无法加载或导致QQ频繁闪退

import android.app.Activity;
import android.widget.Toast;
import java.io.File;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.data.troop.TroopInfo;
import com.tencent.mobileqq.app.ITroopInfoService;
import android.view.*;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.regex.*;
import java.lang.reflect.*;
import android.os.Bundle;
import android.os.Environment;
import android.content.Context;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.view.WindowManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.graphics.PixelFormat;
import android.widget.SeekBar;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.renderscript.*;
import android.text.TextWatcher;
import android.text.Editable;
import android.telephony.TelephonyManager;
import android.net.wifi.WifiManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.app.ActivityManager;
import android.text.format.Formatter;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;
import com.tencent.mobileqq.app.BaseActivity;
import com.tencent.mobileqq.transfile.TransferRequest;
import com.tencent.mobileqq.friend.api.IFriendDataService;
import com.tencent.mobileqq.data.Friends;
import com.tencent.mobileqq.qroute.QRoute;
import com.tencent.qqnt.msg.api.IMsgUtilApi;
import com.tencent.qqnt.msg.api.IMsgService;
import com.tencent.qqnt.kernel.nativeinterface.*;
import mqq.app.AppService;
import mqq.manager.TicketManager;
import oicq.wlogin_sdk.request.WtloginHelper;
import com.tencent.relation.common.api.IRelationNTUinAndUidApi;
import com.tencent.mobileqq.troop.clockin.handler.TroopClockInHandler;
import com.tencent.mobileqq.profilecard.api.IProfileDataService;
import com.tencent.mobileqq.profilecard.api.IProfileProtocolService;
import com.tencent.mobileqq.data.Card;
import com.tencent.mobileqq.forward.ForwardSDKB77Sender;
import com.tencent.mobileqq.structmsg.StructMsgForAudioShare;
import com.tencent.mobileqq.structmsg.AbsShareMsg;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.zip.*;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import dalvik.system.DexClassLoader;
import com.tencent.aio.data.AIOParam;
import lin.xposed.hook.HookEnv;

String 退群拉黑目录;
String 联盟目录;
File 联盟群组文件;
File 封禁列表文件;
String 当前脚本版本 = "v121.0";

public void onLoad() {
    退群拉黑目录 = appPath + "/退群拉黑/";
    File 退群拉黑文件夹 = new File(退群拉黑目录);
    if (!退群拉黑文件夹.exists()) {
        退群拉黑文件夹.mkdirs();
    }
    联盟目录 = appPath + "/封禁联盟/";
    File 联盟文件夹 = new File(联盟目录);
    if (!联盟文件夹.exists()) {
        联盟文件夹.mkdirs();
    }
    联盟群组文件 = new File(联盟目录, "联盟群组.txt");
    封禁列表文件 = new File(联盟目录, "封禁联盟.txt");
    int 艾特禁言时间 = getInt("艾特禁言时间配置", "时间", 2592000);
    load(appPath + "/核心功能/Utils.java");
    load(appPath + "/核心功能/DialogUtils.java");
    load(appPath + "/核心功能/MenuHandlers.java");
    load(appPath + "/核心功能/EventHandlers.java");
    load(appPath + "/核心功能/AllianceManager.java");
    load(appPath + "/核心功能/FileOperations.java");
    load(appPath + "/核心功能/QQInterface.java");
    initEventHandlers();

    String 上次显示版本 = getString("UpdateLog", "lastShownVersion", "");
    if (!当前脚本版本.equals(上次显示版本)) {
        final int[] 尝试次数 = {0};
        final int 最大尝试 = 5;
        Handler 重试处理器 = new Handler(Looper.getMainLooper());
        Runnable 尝试显示 = new Runnable() {
            public void run() {
                Activity 活动 = getActivity();
                if (活动 != null) {
                    showUpdateLogDialog(活动);
                } else {
                    尝试次数[0]++;
                    if (尝试次数[0] < 最大尝试) {
                        重试处理器.postDelayed(this, 1000);
                    } else {
                        toast("妹妹吗");
                    }
                }
            }
        };
        重试处理器.post(尝试显示);
    }
}

public void initEventHandlers() {
    new Thread(new Runnable() {
        public void run() {
            try {
                Thread.sleep(5000);
                ArrayList 联盟群组列表 = null;
                ArrayList 封禁列表 = null;
                try {
                    if (联盟群组文件 != null && 联盟群组文件.exists()) {
                        联盟群组列表 = 简取(联盟群组文件);
                    }
                    if (封禁列表文件 != null && 封禁列表文件.exists()) {
                        封禁列表 = 简取(封禁列表文件);
                    }
                } catch (Exception e) {
                    return;
                }
                if (联盟群组列表 == null || 联盟群组列表.isEmpty() || 封禁列表 == null || 封禁列表.isEmpty()) {
                    return;
                }
                Set 封禁UIN集合 = new HashSet();
                ArrayList 封禁列表副本 = safeCopyList(封禁列表);
                for (int k = 0; k < 封禁列表副本.size(); k++) {
                    String 记录 = (String) 封禁列表副本.get(k);
                    if (记录 != null && 记录.contains("|")) {
                        String[] parts = 记录.split("\\|");
                        if (parts.length > 0 && parts[0] != null) {
                            封禁UIN集合.add(parts[0].trim());
                        }
                    }
                }
                if (封禁UIN集合.isEmpty()) {
                    return;
                }
                ArrayList 联盟群组列表副本 = safeCopyList(联盟群组列表);
                for (int i = 0; i < 联盟群组列表副本.size(); i++) {
                    String 群号 = (String)联盟群组列表副本.get(i);
                    if (群号 == null || 群号.isEmpty()) continue;
                    try {
                        ArrayList 成员列表 = getGroupMemberList(群号);
                        if (成员列表 != null && !成员列表.isEmpty()) {
                            ArrayList 成员列表副本 = safeCopyList(成员列表);
                            for (int j = 0; j < 成员列表副本.size(); j++) {
                                Object 成员 = 成员列表副本.get(j);
                                if (成员 != null && 成员.UserUin != null) {
                                    if (封禁UIN集合.contains(成员.UserUin)) {
                                        unifiedKick(群号, 成员.UserUin, true);
                                        Thread.sleep(300);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        }
    }).start();
}

public void onUnLoad() {
    toast("简洁群管已卸载");
}

public void showUpdateLogDialog(Activity 活动) {
    if (活动 == null) return;
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int 主题 = getCurrentTheme();
                String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                String 卡片背景色 = getCardColor();
                String 文本颜色 = getTextColor();
                String 次要文本颜色 = getSubTextColor();
                String 边框颜色 = getBorderColor();
                LinearLayout 根布局 = new LinearLayout(活动);
                根布局.setOrientation(LinearLayout.VERTICAL);
                根布局.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
                根布局.setBackground(getWebShape(卡片背景色, dp2px(20)));
                TextView 标题 = new TextView(活动);
                标题.setText("简洁群管更新日志");
                标题.setTextColor(Color.parseColor(文本颜色));
                标题.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                标题.setTypeface(null, Typeface.BOLD);
                标题.setGravity(Gravity.CENTER);
                标题.setPadding(0, 0, 0, dp2px(12));
                根布局.addView(标题);
                TextView 内容 = new TextView(活动);
                内容.setText("- [修复] 一些存在的问题\n- [移除] 以前的更新日志堆积成山没有保留的必要，已进行删除从而保持代码的简洁性\n- [优化] 大幅提升了艾特禁言的速度与判断方法\n- [添加] 快捷群管以及设置艾特禁言时间方法添加禁言添加显示时间\n\n喜欢的人要早点说 有bug及时反馈\n\n交流群:https://t.me/XiaoYu_Chat");
                内容.setTextColor(Color.parseColor(次要文本颜色));
                内容.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                内容.setLineSpacing(dp2px(4), 1.2f);
                内容.setPadding(dp2px(4), dp2px(8), dp2px(4), dp2px(16));
                根布局.addView(内容);
                LinearLayout 按钮栏 = new LinearLayout(活动);
                按钮栏.setOrientation(LinearLayout.HORIZONTAL);
                按钮栏.setGravity(Gravity.END);
                LinearLayout.LayoutParams 按钮栏参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                按钮栏.setLayoutParams(按钮栏参数);
                Button 不同意按钮 = new Button(活动);
                不同意按钮.setText("朕不同意");
                不同意按钮.setTextColor(Color.parseColor(强调色));
                不同意按钮.setBackground(null);
                不同意按钮.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
                不同意按钮.setTypeface(null, Typeface.BOLD);
                不同意按钮.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                LinearLayout.LayoutParams 不同意参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                不同意参数.setMargins(0, 0, dp2px(8), 0);
                不同意按钮.setLayoutParams(不同意参数);
                不同意按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        System.exit(0);
                    }
                });
                按钮栏.addView(不同意按钮);
                Button 确定按钮 = new Button(活动);
                确定按钮.setText("朕知道了");
                确定按钮.setTextColor(Color.WHITE);
                确定按钮.setBackground(getShape(强调色, dp2px(20)));
                确定按钮.setPadding(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
                确定按钮.setTypeface(null, Typeface.BOLD);
                确定按钮.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                LinearLayout.LayoutParams 确定参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                确定按钮.setLayoutParams(确定参数);
                按钮栏.addView(确定按钮);
                根布局.addView(按钮栏);
                AlertDialog.Builder 构建器 = new AlertDialog.Builder(活动, 主题);
                构建器.setView(根布局);
                AlertDialog 对话框 = 构建器.create();
                对话框.setCancelable(false);
                对话框.setCanceledOnTouchOutside(false);
                对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                对话框.show();
                根布局.setTag(对话框);
                
                确定按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        putString("UpdateLog", "lastShownVersion", 当前脚本版本);
                        // 通过根布局的 tag 获取对话框并关闭
                        View root = (View) v.getParent().getParent(); // 按钮栏的父布局就是根布局
                        AlertDialog dialog = (AlertDialog) root.getTag();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
            } catch (Exception e) {
                toast("显示更新日志失败");
            }
        }
    });
}

try {
    File errorFile = new File(appPath + "/error.txt");
    if (errorFile.exists()) {
        errorFile.delete();
    }
} catch (Exception e) {
}

// 希望有人懂你的言外之意 更懂你的欲言又止