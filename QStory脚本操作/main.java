
// 海枫

// 好想你 好想跟你见面 然后紧紧的把你抱住

// 官方脚本，未经允许二改会追究责任

import lin.xposed.hook.javaplugin.bean.PluginInfo;
import lin.xposed.hook.javaplugin.controller.PluginLoader;
import lin.xposed.hook.javaplugin.controller.PluginManager;
import lin.xposed.hook.javaplugin.dialog.PluginDialog;
import de.robv.android.xposed.*;
import java.lang.reflect.*;
import android.app.*;
import android.os.*;
import android.view.*;
import java.lang.*;
import android.content.*;
import android.webkit.*;
import android.widget.*;
import android.media.*;
import java.text.*;
import android.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Dialog;
import android.view.Window;
import android.app.Activity;
import android.graphics.*;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.graphics.drawable.*;
import android.view.Gravity;
import android.widget.ScrollView;
import android.widget.ProgressBar;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import android.widget.SeekBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.io.IOException;
import android.media.MediaPlayer;
import java.util.Date;
import android.view.LayoutInflater;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import android.view.View;
import android.view.ViewGroup;
import java.util.zip.*;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.content.res.Configuration;
import android.view.ViewGroup.LayoutParams;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.MultipartFile;
import com.tencent.mobileqq.qroute.QRoute;
import android.content.ComponentName;
import java.lang.reflect.Field;

boolean fullScreen = true;

Object QSClassLoader;
Class Clazz = this.getClass();
if(Clazz != null) {
    QSClassLoader = Clazz.getClassLoader();
} else return;

Object manager = this.interpreter.getClassManager();
manager.setClassLoader(QSClassLoader);

private ScrollView mainScrollView;
private Dialog pluginDialog;
private LinearLayout pluginContainer;
private Activity currentActivity;

List pluginNames = new ArrayList();
List pluginAuthors = new ArrayList();
List pluginDescriptions = new ArrayList();
List pluginVersions = new ArrayList();
List pluginInfos = new ArrayList();

public boolean isDarkMode() {
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
}

public String getThemeColor(String type) {
    boolean dark = isDarkMode();
    if (type.equals("background")) return dark ? "#111315" : "#F8F9FA";
    if (type.equals("surface")) return dark ? "#1E2124" : "#FFFFFF";
    if (type.equals("surfaceVariant")) return dark ? "#2D3135" : "#E9ECEF";
    if (type.equals("primary")) return "#4285F4"; 
    if (type.equals("textPrimary")) return dark ? "#E3E3E3" : "#212529";
    if (type.equals("textSecondary")) return dark ? "#A8C7FA" : "#5F6368";
    if (type.equals("border")) return dark ? "#444746" : "#DEE2E6";
    if (type.equals("buttonBg")) return dark ? "#304285F4" : "#E8F0FE"; 
    if (type.equals("buttonText")) return "#4285F4";
    if (type.equals("dangerBg")) return dark ? "#3C181A" : "#FEE2E2";
    if (type.equals("dangerText")) return dark ? "#FFB4AB" : "#DC2626";
    return "#000000";
}

public int c(float f) {
    return (int) (((((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f) + 0.5f);
}

public GradientDrawable getMaterialShape(String color, int radius) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(color));
    shape.setCornerRadius(c(radius));
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

public GradientDrawable getMaterialCardShape(String bgColor, String borderColor, int radius) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(bgColor));
    shape.setCornerRadius(c(radius));
    shape.setStroke(c(1), Color.parseColor(borderColor));
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

public void Toasts(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    
                    int paddingH = c(24);
                    int paddingV = c(12);
                    linearLayout.setPadding(paddingH, paddingV, paddingH, paddingV);
                    
                    String bg = isDarkMode() ? "#333333" : "#323232";
                    String tx = "#FFFFFF";
                    
                    linearLayout.setBackground(getMaterialShape(bg, 28));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(tx));
                    textView.setTextSize(14f);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
                    linearLayout.addView(textView);
                    
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, c(100));
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(linearLayout);
                    toast.show();
                } else {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            } catch(Exception e) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        }
    });
}

public void getPluginList() {
    pluginNames.clear();
    pluginAuthors.clear();
    pluginDescriptions.clear();
    pluginVersions.clear();
    pluginInfos.clear();
    try {
        for(PluginLoader pluginLoader : PluginManager.getAllRunningPluginLoader()) {
            PluginInfo pluginInfo = pluginLoader.getJavaPluginInfo();
            String desc = pluginInfo.pluginDesc;
            if(desc.length() > 200) desc = desc.substring(0, 200) + "...";
            pluginNames.add(pluginInfo.pluginName);
            pluginAuthors.add(pluginInfo.pluginAuthor);
            pluginDescriptions.add(desc);
            pluginVersions.add(pluginInfo.pluginVersion);
            pluginInfos.add(pluginInfo);
        }
    } catch (Exception e) {
        Toasts("获取失败: " + e.toString());
    }
}

public void expandView(View view) {
    view.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    final int targetHeight = view.getMeasuredHeight();
    view.getLayoutParams().height = 0;
    view.setVisibility(View.VISIBLE);
    Animation animation = new Animation() {
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            view.getLayoutParams().height = (int) (targetHeight * interpolatedTime);
            view.requestLayout();
        }
    };
    animation.setDuration(250); 
    view.startAnimation(animation);
}

public void collapseView(View view) {
    final int initialHeight = view.getMeasuredHeight();
    Animation animation = new Animation() {
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (interpolatedTime == 1) {
                view.setVisibility(View.GONE);
            } else {
                view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                view.requestLayout();
            }
        }
    };
    animation.setDuration(250);
    view.startAnimation(animation);
}

public void showLocalScripts(String title, String javaString) {
    currentActivity = getActivity();
    if (currentActivity == null) return;
    
    currentActivity.runOnUiThread(new Runnable() {
        public void run() {
            if (pluginDialog != null && pluginDialog.isShowing()) {
                pluginDialog.dismiss();
            }
            
            pluginDialog = new Dialog(currentActivity);
            mainScrollView = new ScrollView(currentActivity);
            LinearLayout mainLayout = new LinearLayout(currentActivity);
            mainLayout.setOrientation(1);
            pluginContainer = new LinearLayout(currentActivity);
            pluginContainer.setOrientation(1);
            ProgressBar progressBar = new ProgressBar(currentActivity);
            
            mainLayout.setBackground(getMaterialShape(getThemeColor("background"), 16));
            mainScrollView.addView(pluginContainer);
            mainScrollView.setVerticalScrollBarEnabled(false);
            
            LinearLayout headerLayout = new LinearLayout(currentActivity);
            headerLayout.setOrientation(1);
            headerLayout.setPadding(c(24), c(24), c(24), c(12));

            TextView titleText = new TextView(currentActivity);
            titleText.setText(title);
            titleText.setTextColor(Color.parseColor(getThemeColor("textPrimary")));
            titleText.setTextSize(22);
            titleText.setTypeface(null, Typeface.BOLD);
            
            headerLayout.addView(titleText);
            mainLayout.addView(headerLayout);
            mainLayout.addView(mainScrollView);
            
            LinearLayout progressLayout = new LinearLayout(currentActivity);
            progressLayout.setGravity(Gravity.CENTER);
            progressLayout.setPadding(0, c(20), 0, c(20));
            progressLayout.addView(progressBar);
            pluginContainer.addView(progressLayout);
            
            new Thread(new Runnable() {
                public void run() {
                    getPluginList();
                    currentActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressLayout.setVisibility(View.GONE);
                            refreshPluginContainer();
                        }
                    });
                }
            }).start();
            
            pluginDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            pluginDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            pluginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pluginDialog.setContentView(mainLayout);
            pluginDialog.setCancelable(true);
            
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = windowManager.getDefaultDisplay().getHeight();  
            int width = windowManager.getDefaultDisplay().getWidth();
            
            if(!fullScreen) {
                height = (int)(height * 0.70); 
                width = (int)(width * 0.92);
            }
            
            WindowManager.LayoutParams layoutParams = pluginDialog.getWindow().getAttributes();
            layoutParams.height = height;
            layoutParams.width = width;
            layoutParams.dimAmount = 0.5f;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            
            pluginDialog.getWindow().setAttributes(layoutParams);
            pluginDialog.show();
        }
    });
}

public View createMaterialButton(String text, String bgColor, String textColor, Object tag, View.OnClickListener listener) {
    TextView btn = new TextView(currentActivity);
    btn.setText(text);
    btn.setTextColor(Color.parseColor(textColor));
    btn.setTextSize(14);
    btn.setTypeface(null, Typeface.BOLD);
    btn.setGravity(Gravity.CENTER);
    btn.setPadding(c(16), c(8), c(16), c(8));
    btn.setBackground(getMaterialShape(bgColor, 20)); 
    btn.setTag(tag);
    btn.setOnClickListener(listener);
    return btn;
}

public void refreshPluginContainer() {
    if (pluginContainer == null || currentActivity == null) return;
    
    pluginContainer.removeAllViews();
    pluginContainer.setPadding(c(16), 0, c(16), c(16));
    
    for(int i = 0; i < pluginNames.size(); i++) {
        final int index = i;
        
        LinearLayout cardLayout = new LinearLayout(currentActivity);
        cardLayout.setOrientation(1);
        cardLayout.setBackground(getMaterialCardShape(getThemeColor("surface"), getThemeColor("border"), 12));
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, 0, 0, c(12));
        cardLayout.setLayoutParams(cardParams);
        cardLayout.setPadding(c(16), c(16), c(16), c(16));
        
        TextView nameText = new TextView(currentActivity);
        nameText.setTextColor(Color.parseColor(getThemeColor("textPrimary")));
        nameText.setTextSize(16);
        nameText.setTypeface(null, Typeface.BOLD);
        nameText.setText(pluginNames.get(i) + "  " + pluginVersions.get(i));
        
        TextView authorText = new TextView(currentActivity);
        authorText.setTextColor(Color.parseColor(getThemeColor("textSecondary")));
        authorText.setTextSize(12);
        authorText.setPadding(0, c(4), 0, c(8));
        authorText.setText("Designed by " + pluginAuthors.get(i));
        
        LinearLayout expandLayout = new LinearLayout(currentActivity);
        expandLayout.setOrientation(1);
        expandLayout.setVisibility(View.GONE);
        
        TextView descText = new TextView(currentActivity);
        descText.setTextSize(13);
        descText.setTextColor(Color.parseColor(getThemeColor("textPrimary")));
        descText.setLineSpacing(c(3), 1.1f);
        descText.setPadding(0, 0, 0, c(16));
        descText.setText((String)pluginDescriptions.get(i));
        
        HorizontalScrollView btnScrollView = new HorizontalScrollView(currentActivity);
        btnScrollView.setHorizontalScrollBarEnabled(false);
        btnScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        
        LinearLayout buttonContainer = new LinearLayout(currentActivity);
        buttonContainer.setOrientation(0);
        
        View.OnClickListener loadL = new View.OnClickListener() {
            public void onClick(View v) {
                int clickedIndex = (Integer) v.getTag();
                try {
                    PluginInfo pluginInfo = (PluginInfo)pluginInfos.get(clickedIndex);
                    PluginManager.loadPlugin(null, pluginInfo);
                    Toasts("已加载: " + pluginNames.get(clickedIndex));
                    refreshPluginContainer();
                } catch (Exception e) {
                    Toasts("加载错误: " + e.getMessage());
                }
            }
        };
        View.OnClickListener stopL = new View.OnClickListener() {
            public void onClick(View v) {
                int clickedIndex = (Integer) v.getTag();
                try {
                    PluginInfo pluginInfo = (PluginInfo)pluginInfos.get(clickedIndex);
                    PluginManager.stopPlugin(pluginInfo);
                    Toasts("已停止: " + pluginNames.get(clickedIndex));
                    refreshPluginContainer();
                } catch (Exception e) {
                    Toasts("停止错误: " + e.getMessage());
                }
            }
        };
        View.OnClickListener reloadL = new View.OnClickListener() {
            public void onClick(View v) {
                int clickedIndex = (Integer) v.getTag();
                try {
                    PluginInfo pluginInfo = (PluginInfo)pluginInfos.get(clickedIndex);
                    PluginManager.stopPlugin(pluginInfo);
                    PluginManager.loadPlugin(null, pluginInfo);
                    Toasts("已重载: " + pluginNames.get(clickedIndex));
                    refreshPluginContainer();
                } catch (Exception e) {
                    Toasts("重载错误: " + e.getMessage());
                }
            }
        };
        View.OnClickListener uninstallL = new View.OnClickListener() {
            public void onClick(View v) {
                int clickedIndex = (Integer) v.getTag();
                showUninstallConfirm(((PluginInfo)pluginInfos.get(clickedIndex)).pluginLocalPath, (String)pluginNames.get(clickedIndex));
            }
        };

        View loadBtn = createMaterialButton("加载", getThemeColor("buttonBg"), getThemeColor("buttonText"), i, loadL);
        View stopBtn = createMaterialButton("停止", getThemeColor("surfaceVariant"), getThemeColor("textPrimary"), i, stopL);
        View reloadBtn = createMaterialButton("重载", getThemeColor("surfaceVariant"), getThemeColor("textPrimary"), i, reloadL);
        View uninstallBtn = createMaterialButton("卸载", getThemeColor("dangerBg"), getThemeColor("dangerText"), i, uninstallL);

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btnParams.setMargins(0, 0, c(8), 0);

        buttonContainer.addView(loadBtn, btnParams);
        buttonContainer.addView(stopBtn, btnParams);
        buttonContainer.addView(reloadBtn, btnParams);
        buttonContainer.addView(uninstallBtn, btnParams);
        
        btnScrollView.addView(buttonContainer);
        
        expandLayout.addView(descText);
        expandLayout.addView(btnScrollView);
        
        cardLayout.setTag(i);
        cardLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LinearLayout container = (LinearLayout) v;
                View details = container.getChildAt(2);
                
                if(details.getVisibility() == View.GONE) {
                    expandView(details);
                } else {
                    collapseView(details);
                }
            }
        });

        cardLayout.addView(nameText);
        cardLayout.addView(authorText);
        cardLayout.addView(expandLayout);
        
        pluginContainer.addView(cardLayout);
    }
}

addItem("QStory运行脚本", "runScripts");

public void runScripts(String group) {
    showLocalScripts("Local Scripts", "getPluginList();");
}

public void deleteFolder(String path) {
    File folder = new File(path);
    if (folder.isDirectory()) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolders(file);
                } else {
                    file.delete();
                }
            }
        }
    }
    folder.delete();
}

public void deleteFolders(File folder) {
    if (folder.isDirectory()) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolders(file);
                } else {
                    file.delete();
                }
            }
        }
    }
    folder.delete();
}

Activity activity = getActivity();

public void startComponentName(String packageName, String className) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_MAIN);
    ComponentName componentName = new ComponentName(packageName, className);
    intent.setComponent(componentName);
    activity.startActivity(intent);
}

addItem("打开QStory设置", "openSettings");

public void openSettings(String group) {
    startComponentName("com.tencent.mobileqq", "lin.xposed.hook.view.main.MainSettingActivity");
}

public String getXPName() {
    try {
        Object cl = this.getClass().getClassLoader();
        Class clazz = cl.loadClass("de.robv.android.xposed.XposedBridge");
        Field f = clazz.getField("TAG");
        f.setAccessible(true);
        String tag = (String) f.get(null);
        return tag;
    } catch (Exception e) {
        return "未知";
    }
}

Toasts("QStory Script Engine Loaded\nFramework: " + getXPName());

public void showUninstallConfirm(final String path, final String name) {
    Activity currentActivity = getActivity();
    if (currentActivity == null) return;
    
    currentActivity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("确认卸载");
            builder.setMessage("您确定要删除脚本 \"" + name + "\" 吗？此操作无法撤销。");
            
            builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deleteFolder(path);
                    Toasts("已卸载: " + name);
                    if (pluginDialog != null && pluginDialog.isShowing()) {
                        pluginDialog.dismiss();
                    }
                    showLocalScripts("Local Scripts", "getPluginList();");
                }
            });
            
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            
            AlertDialog dialog = builder.create();
            dialog.show();
            
            Button nBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            if(nBtn != null) nBtn.setTextColor(Color.parseColor("#DC3545"));
            
            Button pBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if(pBtn != null) pBtn.setTextColor(Color.parseColor("#4285F4"));
        }
    });
}
