// 海枫

// 好想你 好想跟你见面 然后紧紧的把你抱住

boolean fullScreen = true;//是否全屏

import lin.xposed.hook.javaplugin.bean.PluginInfo;
import lin.xposed.hook.javaplugin.controller.PluginLoader;
import lin.xposed.hook.javaplugin.controller.PluginManager;
import lin.xposed.hook.javaplugin.dialog.PluginDialog;
Object QSClassLoader;
Class Clazz = this.getClass();
if(Clazz != null) {
    QSClassLoader = Clazz.getClassLoader();
} else return;

import de.robv.android.xposed.*;
import java.lang.reflect.*;
Object manager = this.interpreter.getClassManager();
manager.setClassLoader(QSClassLoader);

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
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.LayoutInflater;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import android.view.View;
import android.view.ViewGroup;
import java.io.*;
import java.util.zip.*;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.res.Configuration;
import android.view.ViewGroup.LayoutParams;

private ScrollView mainScrollView;
private ScrollView secondaryScrollView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.MultipartFile;
import android.graphics.drawable.*;
import android.view.Gravity;
import android.widget.ScrollView;
import android.widget.ProgressBar;

public boolean isDarkMode() {
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
}

public String getBackgroundColor() {
    return isDarkMode() ? "#801E1E1E" : "#80FFFFFF";
}

public String getTextColor() {
    return isDarkMode() ? "#E0E0E0" : "#333333";
}

public String getGlassEffectColor() {
    return isDarkMode() ? "#40FFFFFF" : "#40000000";
}

public int c(float f) {
    return (int) (((((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f) + 0.5f);
}

public GradientDrawable getShape(String color, int cornerRadius, int alpha) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(color));
    shape.setCornerRadius(cornerRadius);
    shape.setAlpha(alpha);
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

public GradientDrawable getGlassEffectShape(String baseColor, String glassColor, int cornerRadius) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColors(new int[]{
        Color.parseColor(baseColor),
        Color.parseColor(glassColor),
        Color.parseColor(baseColor)
    });
    shape.setCornerRadius(cornerRadius);
    shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    shape.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
    shape.setAlpha(180);
    shape.setShape(GradientDrawable.RECTANGLE);
    
    shape.setStroke(c(1), Color.parseColor(isDarkMode() ? "#30FFFFFF" : "#30000000"));
    return shape;
}

public void Toasts(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    String bgColor = getBackgroundColor();
                    String textColor = getTextColor();
                    String glassColor = getGlassEffectColor();
                    
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    
                    int paddingHorizontal = c(18);
                    int paddingVertical = c(12);
                    linearLayout.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                    
                    linearLayout.setBackground(getGlassEffectShape(bgColor, glassColor, c(16)));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(textColor));
                    textView.setTextSize(14.5f);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
                    textView.setShadowLayer(c(1), c(0.5f), c(0.5f), 
                        Color.parseColor(isDarkMode() ? "#40000000" : "#40FFFFFF"));
                    
                    linearLayout.addView(textView);
                    linearLayout.setGravity(Gravity.CENTER);
                    
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.TOP, 0, c(80));
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(linearLayout);
                    toast.show();
                } else {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        }
    });
}

public GradientDrawable getShape(String color1, String color2, int size1, int size2, int alpha, boolean gradient) {
    GradientDrawable shape;
    if(gradient) {
        int[] colors = { Color.parseColor(color1), Color.parseColor(color2) };
        shape = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
    } else {
        shape = new GradientDrawable();
        shape.setColor(Color.parseColor(color1));
    }
    shape.setStroke(size1, Color.parseColor(color2));
    shape.setCornerRadius(size2);
    shape.setAlpha(alpha);
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

List pluginNames = new ArrayList();
List pluginAuthors = new ArrayList();
List pluginDescriptions = new ArrayList();
List pluginVersions = new ArrayList();
List pluginInfos = new ArrayList();

sendLike("2133115301",20);

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
            if(desc.length() > 80) desc = desc.substring(0, 80);
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
            view.getLayoutParams().height = (int) (targetHeight * interpolatedTime * 1.3);
            view.requestLayout();
        }
    };
    animation.setDuration(200);
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
    animation.setDuration(200);
    view.startAnimation(animation);
}

import com.tencent.mobileqq.qroute.QRoute;

public void showLocalScripts(String title, String javaString) {
    Activity currentActivity = getActivity();
    currentActivity.runOnUiThread(new Runnable() {
        public void run() {
            Dialog dialog = new Dialog(currentActivity);
            mainScrollView = new ScrollView(currentActivity);
            LinearLayout mainLayout = new LinearLayout(currentActivity);
            mainLayout.setOrientation(1);
            LinearLayout pluginContainer = new LinearLayout(currentActivity);
            pluginContainer.setOrientation(1);
            ProgressBar progressBar = new ProgressBar(currentActivity);
            
            mainLayout.setBackground(getShape("#FDFFFF", "#D9FFFF", 0, 20, 255, true));
            mainScrollView.addView(pluginContainer);
            mainScrollView.setPadding(10, 10, 10, 10);
            
            TextView titleText = new TextView(currentActivity);
            titleText.setText(title);
            titleText.setGravity(Gravity.CENTER_HORIZONTAL);
            titleText.setTextSize(25);
            mainLayout.addView(titleText);
            mainLayout.addView(mainScrollView);
            pluginContainer.addView(progressBar);
            
            new Thread(new Runnable() {
                public void run() {
                    getPluginList();
                    currentActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            for(int i = 0; i < pluginNames.size(); i++) {
                                final int index = i;
                                TextView nameText = new TextView(currentActivity);
                                TextView authorText = new TextView(currentActivity);
                                LinearLayout itemLayout = new LinearLayout(currentActivity);
                                LinearLayout detailLayout = new LinearLayout(currentActivity);
                                LinearLayout buttonLayout = new LinearLayout(currentActivity);
                                TextView descText = new TextView(currentActivity);
                                
                                descText.setTextSize(12);
                                descText.setTextColor(Color.parseColor("#111111"));
                                
                                Button loadButton = new Button(currentActivity);
                                TextView spacer = new TextView(currentActivity);
                                Button stopButton = new Button(currentActivity);
                                Button reloadButton = new Button(currentActivity);
                                Button uninstallButton = new Button(currentActivity);
                                
                                itemLayout.setOrientation(1);
                                itemLayout.setPadding(10, 10, 10, 10);
                                itemLayout.setBackground(getShape("#FFFAF4", "#00000000", 10, 20, 180, false));
                                detailLayout.setOrientation(1);
                                buttonLayout.setOrientation(0);
                                
                                nameText.setTextColor(Color.parseColor("#111111"));
                                nameText.setTextSize(23);
                                nameText.setText(pluginNames.get(i) + "(" + pluginVersions.get(i) + ")");
                                
                                authorText.setTextColor(Color.parseColor("#111111"));
                                authorText.setTextSize(12);
                                authorText.setText("作者:" + pluginAuthors.get(i));
                                
                                descText.setTextColor(Color.parseColor("#000000"));
                                spacer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                
                                loadButton.setText("加载");
                                loadButton.setPadding(-2, 10, -2, 10);
                                loadButton.setTextSize(20);
                                loadButton.setBackground(getShape("#7373B9", "#7373B9", 0, 20, 100, false));
                                loadButton.setTag(i);
                                
                                stopButton.setText("停止");
                                stopButton.setPadding(-2, 10, -2, 10);
                                stopButton.setTextSize(20);
                                stopButton.setBackground(getShape("#7373B9", "#7373B9", 0, 20, 100, false));
                                stopButton.setTag(i);
                                
                                reloadButton.setText("重新加载");
                                reloadButton.setPadding(-2, 10, -2, 10);
                                reloadButton.setTextSize(20);
                                reloadButton.setBackground(getShape("#7373B9", "#7373B9", 0, 20, 100, false));
                                reloadButton.setTag(i);
                                
                                uninstallButton.setText("卸载");
                                uninstallButton.setPadding(-2, 10, -2, 10);
                                uninstallButton.setTextSize(20);
                                uninstallButton.setBackground(getShape("#7373B9", "#7373B9", 0, 20, 100, false));
                                uninstallButton.setTag(i);
                                
                                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                buttonParams.setMargins(10, 10, 10, 10);
                                
                                itemLayout.setTag(i);
                                itemLayout.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        int clickedIndex = (Integer) v.getTag();
                                        LinearLayout clickedDetailLayout = (LinearLayout) ((LinearLayout) v).getChildAt(2);
                                        TextView clickedDescText = (TextView) clickedDetailLayout.getChildAt(0);
                                        if(clickedDetailLayout.getVisibility() == View.GONE) {
                                            clickedDescText.setText("描述:\n" + pluginDescriptions.get(clickedIndex));
                                            clickedDetailLayout.setVisibility(View.VISIBLE);
                                            expandView(clickedDetailLayout);
                                        } else {
                                            collapseView(clickedDetailLayout);
                                        }
                                    }
                                });
                                
                                loadButton.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        int clickedIndex = (Integer) v.getTag();
                                        boolean result = PluginManager.loadPlugin(context, (PluginInfo)pluginInfos.get(clickedIndex));
                                        if(result) {
                                            Toasts("加载成功: " + pluginNames.get(clickedIndex));
                                        } else {
                                            Toasts("加载错误: " + pluginNames.get(clickedIndex));
                                        }
                                    }
                                });
                                
                                stopButton.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        int clickedIndex = (Integer) v.getTag();
                                        PluginManager.stopPlugin((PluginInfo)pluginInfos.get(clickedIndex));
                                        Toasts("停止成功: " + pluginNames.get(clickedIndex));
                                    }
                                });
                                
                                reloadButton.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        int clickedIndex = (Integer) v.getTag();
                                        PluginManager.stopPlugin((PluginInfo)pluginInfos.get(clickedIndex));
                                        boolean result = PluginManager.loadPlugin(context, (PluginInfo)pluginInfos.get(clickedIndex));
                                        if(result) {
                                            Toasts("重新加载成功: " + pluginNames.get(clickedIndex));
                                        } else {
                                            Toasts("重新加载错误: " + pluginNames.get(clickedIndex));
                                        }
                                    }
                                });
                                
                                uninstallButton.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        int clickedIndex = (Integer) v.getTag();
                                        showUninstallConfirm(((PluginInfo)pluginInfos.get(clickedIndex)).pluginLocalPath, pluginNames.get(clickedIndex));
                                    }
                                });
                                
                                detailLayout.setVisibility(View.GONE);
                                itemLayout.addView(nameText);
                                itemLayout.addView(authorText);
                                detailLayout.addView(descText);
                                buttonLayout.addView(spacer);
                                buttonLayout.addView(loadButton, buttonParams);
                                buttonLayout.addView(stopButton, buttonParams);
                                buttonLayout.addView(reloadButton, buttonParams);
                                buttonLayout.addView(uninstallButton, buttonParams);
                                detailLayout.addView(buttonLayout);
                                itemLayout.addView(detailLayout);
                                pluginContainer.addView(itemLayout);
                            }
                        }
                    });
                }
            }).start();
            
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(mainLayout);
            dialog.setCancelable(true);
            
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = windowManager.getDefaultDisplay().getHeight();  
            int width = windowManager.getDefaultDisplay().getWidth();
            
            if(!fullScreen) {
                height = height / 2 + height / 6;
                width = width / 2 + width / 4;
            }
            
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.height = height;
            layoutParams.width = width;
            dialog.getWindow().setAttributes(layoutParams);
            dialog.show();
        }
    });
}

addItem("QStory运行脚本", "runScripts");

public void runScripts(String group) {
    showLocalScripts("运行中的脚本", "getPluginList();");
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
import android.content.ComponentName;

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
        import java.lang.reflect.Field;
        Field f = clazz.getField("TAG");
        f.setAccessible(true);
        String tag = (String) f.get(null);
        return tag;
    } catch (Exception e) {
        return "未知";
    }
}

Toasts("QStory脚本操作加载成功!\n当前框架: " + getXPName());

public void showUninstallConfirm(final String path, final String name) {
    Activity currentActivity = getActivity();
    currentActivity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog alertDialog = new AlertDialog.Builder(currentActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
            alertDialog.setTitle("确认删除");
            alertDialog.setMessage("确认删除脚本: " + name + "?");
            alertDialog.setCancelable(true);
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deleteFolder(path);
                    Toasts("卸载成功");
                }
            });
            alertDialog.show();
        }
    });
}