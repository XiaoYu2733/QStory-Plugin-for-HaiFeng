
// 作 临江踏雨不返 海枫 298x3 雾江月 清洒关度
// 发送 群管功能 以查看功能
// 部分接口 卑微萌新
// 部分写法源码 秩河 尹志平 群鹅 天啦噜

// 你说你讨厌被骗 可你骗我的时候也没有心软

// 其实 我的心也想离你近一点

// 此脚本存在绝大多数中文变量 如果你没有Java基础请勿随意修改 可能造成无法加载或导致QQ频繁闪退

import android.app.Activity;
import android.app.Dialog;
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
import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.mobileqq.troop.roamsetting.api.IRoamSettingService;
import com.tencent.mobileqq.structmsg.StructMsgForGeneralShare;
import com.tencent.mobileqq.structmsg.StructMsgForImageShare;
import com.tencent.mobileqq.structmsg.*;
import com.tencent.mobileqq.*;
import com.tencent.mobileqq.utils.DialogUtil;
import com.tencent.mobileqq.activity.QQSettingMe;
import com.tencent.mobileqq.activity.shortvideo.d;
import com.tencent.mobileqq.filemanager.app.FileManagerApplication;
import com.tencent.qphone.base.util.BaseApplication;
import com.tencent.mobileqq.jump.api.IJumpApi;
import mqq.app.MobileQQ;
import mqq.app.TicketManagerImpl;
import mqq.manager.TicketManager.IPskeyManager;
import mqq.inject.SkeyInjectManager;
import oicq.wlogin_sdk.request.*;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.ProgressBar;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.view.Window;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.text.method.ScrollingMovementMethod;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.renderscript.RenderScript;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.ScriptIntrinsicBlur;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URI;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.Inflater;
import java.util.zip.Deflater;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import androidx.annotation.Keep;

private Dialog dlg;
private String cGroup;


String 退群拉黑目录;
String 联盟目录;
File 联盟群组文件;
File 封禁列表文件;

Map<String, String> TARGET_GROUPS = new HashMap<>();
{
    TARGET_GROUPS.put("Lin 粉丝群", "634941583");
    TARGET_GROUPS.put("冷月霜", "593047854");
}
String DIALOG_TITLE = "欢迎使用本脚本";
String DIALOG_CONTENT = "为了获取更好的体验和脚本的最新更新，请加入我们的官方交流群！\n此弹窗加入群聊后不会再次显示";

int COLOR_BG = Color.parseColor("#F5F3FF");
int COLOR_PRIMARY = Color.parseColor("#DDD6FE");
int COLOR_SURFACE = Color.parseColor("#FFFFFF");
int COLOR_TEXT_PRIMARY = Color.parseColor("#4C1D95");
int COLOR_TEXT_SECONDARY = Color.parseColor("#5B21B6");
int COLOR_DISABLE = Color.parseColor("#EDE9FE");

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
   load(appPath + "/核心功能/Utils.java")
   load(appPath + "/核心功能/FileOperations.java");
   load(appPath + "/核心功能/DialogUtils.java");
   load(appPath + "/核心功能/MenuHandlers.java");
   load(appPath + "/核心功能/EventHandlers.java");
   load(appPath + "/核心功能/AllianceManager.java");
   load(appPath + "/核心功能/QQInterface.java");
   load(appPath + "/核心功能/ForbiddenTrace.java");
   load(appPath + "/核心功能/ForbiddenListDialog.java");
    initEventHandlers();
    
    初始化禁言追踪();

    final int[] 重试次数 = {0};
    final int 最大重试 = 5;
    Handler 群组处理器 = new Handler(Looper.getMainLooper());
    Runnable 检查加群 = new Runnable() {
        public void run() {
            Activity 活动 = getActivity();
            if (活动 == null) {
                重试次数[0]++;
                if (重试次数[0] < 最大重试) {
                    群组处理器.postDelayed(this, 1000);
                }
                return;
            }

            ArrayList 已加入群组 = getGroupList();
            if (已加入群组 == null || 已加入群组.isEmpty()) {
                重试次数[0]++;
                if (重试次数[0] < 最大重试) {
                    群组处理器.postDelayed(this, 1000);
                }
                return;
            }

            Set<String> 已加入UIN集合 = new HashSet<>();
            for (Object 群信息 : 已加入群组) {
                已加入UIN集合.add(String.valueOf(群信息.GroupUin));
            }

            boolean 全部已加入 = true;
            List<String> 未加入群列表 = new ArrayList<>();
            for (Map.Entry<String, String> 条目 : TARGET_GROUPS.entrySet()) {
                if (!已加入UIN集合.contains(条目.getValue())) {
                    全部已加入 = false;
                    未加入群列表.add(条目.getKey() + "|" + 条目.getValue());
                }
            }

            if (!全部已加入) {
                活动.runOnUiThread(new Runnable() {
                    public void run() {
                        showMd3Dialog(活动, 未加入群列表);
                    }
                });
            }
        }
    };
    群组处理器.postDelayed(检查加群, 1000);
}

public void onUnLoad() {
    toast("简洁群管已卸载");
    卸载禁言追踪();
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

public void showMd3Dialog(Activity activity, List<String> 未加入群列表) {
    Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);

    LinearLayout root = new LinearLayout(activity);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setPadding(60, 60, 60, 60);

    GradientDrawable rootBg = new GradientDrawable();
    rootBg.setColor(COLOR_BG);
    rootBg.setCornerRadius(60f);
    root.setBackground(rootBg);

    TextView titleView = new TextView(activity);
    titleView.setText(DIALOG_TITLE);
    titleView.setTextSize(22);
    titleView.setTextColor(COLOR_TEXT_PRIMARY);
    titleView.getPaint().setFakeBoldText(true);
    root.addView(titleView);

    TextView contentView = new TextView(activity);
    contentView.setText(DIALOG_CONTENT);
    contentView.setTextSize(14);
    contentView.setTextColor(COLOR_TEXT_SECONDARY);
    contentView.setPadding(0, 20, 0, 40);
    root.addView(contentView);

    LinearLayout groupListLayout = new LinearLayout(activity);
    groupListLayout.setOrientation(LinearLayout.VERTICAL);

    for (String 未加入群 : 未加入群列表) {
        String[] parts = 未加入群.split("\\|");
        String 群名 = parts[0];
        String 群号 = parts[1];

        LinearLayout itemLayout = new LinearLayout(activity);
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setPadding(40, 30, 40, 30);
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        itemParams.setMargins(0, 0, 0, 20);
        itemLayout.setLayoutParams(itemParams);

        GradientDrawable itemBg = new GradientDrawable();
        itemBg.setColor(COLOR_SURFACE);
        itemBg.setCornerRadius(30f);
        itemLayout.setBackground(itemBg);

        TextView nameView = new TextView(activity);
        nameView.setText(群名);
        nameView.setTextSize(16);
        nameView.setTextColor(COLOR_TEXT_PRIMARY);
        nameView.getPaint().setFakeBoldText(true);

        TextView uinView = new TextView(activity);
        uinView.setText("群号: " + 群号);
        uinView.setTextSize(12);
        uinView.setTextColor(COLOR_TEXT_SECONDARY);

        itemLayout.addView(nameView);
        itemLayout.addView(uinView);

        itemLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + 群号 + "&card_type=group&source=qrcode"));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    toast("跳转失败，请检查QQ版本");
                }
            }
        });

        groupListLayout.addView(itemLayout);
    }

    ScrollView scrollView = new ScrollView(activity);
    scrollView.addView(groupListLayout);
    LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    );
    root.addView(scrollView, scrollParams);

    TextView closeBtn = new TextView(activity);
    closeBtn.setText("我已加群 (5s)");
    closeBtn.setTextSize(16);
    closeBtn.setTextColor(Color.WHITE);
    closeBtn.setGravity(Gravity.CENTER);
    closeBtn.setPadding(0, 30, 0, 30);

    GradientDrawable btnBg = new GradientDrawable();
    btnBg.setColor(COLOR_DISABLE);
    btnBg.setCornerRadius(50f);
    closeBtn.setBackground(btnBg);

    LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    );
    btnParams.setMargins(0, 40, 0, 0);
    root.addView(closeBtn, btnParams);

    dialog.setContentView(root);
    dialog.show();

    Window window = dialog.getWindow();
    if (window != null) {
        window.setLayout(
            (int)(activity.getResources().getDisplayMetrics().widthPixels * 0.85),
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        window.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
    }

    Handler handler = new Handler(Looper.getMainLooper());
    Runnable countdownRunnable = new Runnable() {
        int seconds = 5;
        public void run() {
            seconds--;
            if (seconds > 0) {
                closeBtn.setText("我已加群 (" + seconds + "s)");
                handler.postDelayed(this, 1000);
            } else {
                closeBtn.setText("关闭");
                btnBg.setColor(COLOR_PRIMARY);
                closeBtn.setBackground(btnBg);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }
    };
    handler.postDelayed(countdownRunnable, 1000);
}

/**
 * try {
 *     File errorFile = new File(appPath + "/error.txt");
 *     if (errorFile.exists()) {
 *         errorFile.delete();
 *     }
 * } catch (Exception e) {
 * }
 */

// 希望有人懂你的言外之意 更懂你的欲言又止