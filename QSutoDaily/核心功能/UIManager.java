
// 你当然不会难过 你身边有很多人可以代替我 而我没有

// 核心ui类 借鉴或搬运自己的脚本请标注原作者名称

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.os.Handler;
import android.os.Looper;
import java.util.List;
import android.view.LayoutInflater;

public Object getFieldValue(Object object, String fieldName) {
    try {
        Field field = object.getClass().getField(fieldName);
        return field.get(object);
    } catch (Exception e) {
        return null;
    }
}

public int getCurrentTheme() {
    try {
        Context context = getActivity();
        if (context == null) return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        int nightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
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
        return "#1E1E1E";
    } else {
        return "#F8F9FA";
    }
}

public String getTextColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#E9ECEF";
    } else {
        return "#212529";
    }
}

public String getSubTextColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#E9ECEF";
    } else {
        return "#6C757D";
    }
}

public String getCardColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#2D2D2D";
    } else {
        return "#FFFFFF";
    }
}

public String getAccentColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#5A9EFF";
    } else {
        return "#4285F4";
    }
}

public String getSurfaceColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#3C3C3C";
    } else {
        return "#E9ECEF";
    }
}

public int c(float value) {
    return dp2px(value);
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

public GradientDrawable getShape(String color, int radius) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(color));
    shape.setCornerRadius(radius);
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

public StateListDrawable getButtonBackground(String normalColor, String pressedColor, int radius) {
    StateListDrawable stateList = new StateListDrawable();
    GradientDrawable normalShape = new GradientDrawable();
    normalShape.setColor(Color.parseColor(normalColor));
    normalShape.setCornerRadius(radius);
    GradientDrawable pressedShape = new GradientDrawable();
    pressedShape.setColor(Color.parseColor(pressedColor));
    pressedShape.setCornerRadius(radius);
    stateList.addState(new int[]{android.R.attr.state_pressed}, pressedShape);
    stateList.addState(new int[]{}, normalShape);
    return stateList;
}

public GradientDrawable getWebShape(String baseColor, int radius) {
    GradientDrawable shape = new GradientDrawable();
    shape.setColor(Color.parseColor(baseColor));
    shape.setCornerRadius(radius);
    shape.setStroke(dp2px(1), Color.parseColor(getBorderColor()));
    shape.setShape(GradientDrawable.RECTANGLE);
    return shape;
}

public String getBorderColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#404040";
    } else {
        return "#DEE2E6";
    }
}

public void Toasts(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    int theme = getCurrentTheme();
                    String bgColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#CC2D2D2D" : "#CC000000";
                    String textColor = "#FFFFFF";
                    
                    LinearLayout layout = new LinearLayout(context);
                    layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    layout.setOrientation(LinearLayout.VERTICAL);
                    
                    int horizontalPadding = dp2px(16);
                    int verticalPadding = dp2px(12);
                    layout.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
                    
                    layout.setBackground(getShape(bgColor, dp2px(8)));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(textColor));
                    textView.setTextSize(14);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
                    layout.addView(textView);
                    layout.setGravity(Gravity.CENTER);
                    
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
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

class CustomArrayAdapter extends ArrayAdapter {
    private List<String> selectedList;
    private String textColor;
    private List<String> idList;
    
    public CustomArrayAdapter(Context context, List<String> objects, List<String> idList, List<String> selectedList, String textColor) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.selectedList = selectedList;
        this.textColor = textColor;
        this.idList = idList;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            textView = (TextView) convertView;
        }
        String item = (String) getItem(position);
        textView.setText(item);
        textView.setTextColor(Color.parseColor(textColor));
        textView.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
        String id = idList.get(position);
        if (selectedList.contains(id)) {
            textView.setBackgroundColor(Color.parseColor("#33007AFF"));
        } else {
            textView.setBackgroundColor(Color.TRANSPARENT);
        }
        return textView;
    }
}

void showMainMenu(final Activity activity) {
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int theme = getCurrentTheme();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
                builder.setTitle("配置执行任务");
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(16)));
                
                TextView hintText = new TextView(activity);
                hintText.setText("当前配置:\n点赞好友: " + likeFriendList.size() + " 人\n续火好友: " + fireFriendList.size() + " 人\n续火群组: " + fireGroupList.size() + " 个");
                hintText.setTextSize(14);
                hintText.setTextColor(Color.parseColor(getTextColor()));
                hintText.setPadding(0, 0, 0, dp2px(16));
                layout.addView(hintText);
                
                View spacer = new View(activity);
                spacer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(12)));
                layout.addView(spacer);
                
                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(44));
                btnParams.setMargins(0, 0, 0, dp2px(8));
                
                String accentColor = getAccentColor();
                String pressedColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#3A6CD9" : "#3367D6";
                
                Button likeFriendsBtn = new Button(activity);
                likeFriendsBtn.setText("配置点赞好友");
                likeFriendsBtn.setTextColor(Color.WHITE);
                likeFriendsBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                likeFriendsBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                likeFriendsBtn.setLayoutParams(btnParams);
                likeFriendsBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configLikeFriends("", "", 0);
                    }
                });
                
                Button fireFriendsBtn = new Button(activity);
                fireFriendsBtn.setText("配置续火好友");
                fireFriendsBtn.setTextColor(Color.WHITE);
                fireFriendsBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                fireFriendsBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                fireFriendsBtn.setLayoutParams(btnParams);
                fireFriendsBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFireFriends("", "", 0);
                    }
                });
                
                Button fireGroupsBtn = new Button(activity);
                fireGroupsBtn.setText("配置续火群组");
                fireGroupsBtn.setTextColor(Color.WHITE);
                fireGroupsBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                fireGroupsBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                fireGroupsBtn.setLayoutParams(btnParams);
                fireGroupsBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFireGroups("", "", 0);
                    }
                });
                
                layout.addView(likeFriendsBtn);
                layout.addView(fireFriendsBtn);
                layout.addView(fireGroupsBtn);
                
                View spacer2 = new View(activity);
                spacer2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(16)));
                layout.addView(spacer2);
                
                TextView quickTitle = new TextView(activity);
                quickTitle.setText("快速操作");
                quickTitle.setTextSize(16);
                quickTitle.setTextColor(Color.parseColor(getTextColor()));
                quickTitle.setTypeface(null, Typeface.BOLD);
                quickTitle.setPadding(0, 0, 0, dp2px(8));
                layout.addView(quickTitle);
                
                LinearLayout.LayoutParams quickBtnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(40));
                quickBtnParams.setMargins(0, 0, 0, dp2px(6));
                
                Button viewConfigBtn = new Button(activity);
                viewConfigBtn.setText("查看配置详情");
                viewConfigBtn.setTextColor(Color.WHITE);
                viewConfigBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                viewConfigBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
                viewConfigBtn.setLayoutParams(quickBtnParams);
                viewConfigBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showConfigDetails(activity);
                    }
                });
                
                Button importConfigBtn = new Button(activity);
                importConfigBtn.setText("导入配置文件");
                importConfigBtn.setTextColor(Color.WHITE);
                importConfigBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                importConfigBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
                importConfigBtn.setLayoutParams(quickBtnParams);
                importConfigBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        importConfig(activity);
                    }
                });
                
                Button exportConfigBtn = new Button(activity);
                exportConfigBtn.setText("导出配置文件");
                exportConfigBtn.setTextColor(Color.WHITE);
                exportConfigBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                exportConfigBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
                exportConfigBtn.setLayoutParams(quickBtnParams);
                exportConfigBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        exportConfig(activity);
                    }
                });
                
                layout.addView(viewConfigBtn);
                layout.addView(importConfigBtn);
                layout.addView(exportConfigBtn);
                
                ScrollView scrollView = new ScrollView(activity);
                scrollView.addView(layout);
                
                builder.setView(scrollView);
                builder.setNegativeButton("关闭", null);
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                
            } catch (Exception e) {
                Toasts("显示配置菜单失败: " + e.getMessage());
            }
        }
    });
}

void showConfigDetails(final Activity activity) {
    int theme = getCurrentTheme();
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
    
    LinearLayout layout = new LinearLayout(activity);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
    layout.setBackground(getWebShape(getCardColor(), dp2px(16)));
    
    TextView titleView = new TextView(activity);
    titleView.setText("配置详情");
    titleView.setTextSize(18);
    titleView.setTextColor(Color.parseColor(getTextColor()));
    titleView.setGravity(Gravity.CENTER);
    titleView.setPadding(0, 0, 0, dp2px(16));
    layout.addView(titleView);
    
    ScrollView scrollView = new ScrollView(activity);
    LinearLayout contentLayout = new LinearLayout(activity);
    contentLayout.setOrientation(LinearLayout.VERTICAL);
    
    TextView likeTitle = new TextView(activity);
    likeTitle.setText("点赞好友 (" + likeFriendList.size() + "人):");
    likeTitle.setTextSize(16);
    likeTitle.setTextColor(Color.parseColor(getTextColor()));
    likeTitle.setPadding(0, dp2px(4), 0, dp2px(4));
    likeTitle.setTypeface(null, Typeface.BOLD);
    contentLayout.addView(likeTitle);
    
    if (likeFriendList.isEmpty()) {
        TextView emptyLike = new TextView(activity);
        emptyLike.setText("未配置点赞好友");
        emptyLike.setTextSize(14);
        emptyLike.setTextColor(Color.parseColor(getSubTextColor()));
        emptyLike.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        contentLayout.addView(emptyLike);
    } else {
        for (int i = 0; i < likeFriendList.size(); i++) {
            TextView item = new TextView(activity);
            item.setText((i + 1) + ". " + likeFriendList.get(i));
            item.setTextSize(14);
            item.setTextColor(Color.parseColor(getTextColor()));
            item.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            contentLayout.addView(item);
        }
    }
    
    TextView fireFriendTitle = new TextView(activity);
    fireFriendTitle.setText("续火好友 (" + fireFriendList.size() + "人):");
    fireFriendTitle.setTextSize(16);
    fireFriendTitle.setTextColor(Color.parseColor(getTextColor()));
    fireFriendTitle.setPadding(0, dp2px(12), 0, dp2px(4));
    fireFriendTitle.setTypeface(null, Typeface.BOLD);
    contentLayout.addView(fireFriendTitle);
    
    if (fireFriendList.isEmpty()) {
        TextView emptyFire = new TextView(activity);
        emptyFire.setText("未配置续火好友");
        emptyFire.setTextSize(14);
        emptyFire.setTextColor(Color.parseColor(getSubTextColor()));
        emptyFire.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        contentLayout.addView(emptyFire);
    } else {
        for (int i = 0; i < fireFriendList.size(); i++) {
            TextView item = new TextView(activity);
            item.setText((i + 1) + ". " + fireFriendList.get(i));
            item.setTextSize(14);
            item.setTextColor(Color.parseColor(getTextColor()));
            item.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            contentLayout.addView(item);
        }
    }
    
    TextView fireGroupTitle = new TextView(activity);
    fireGroupTitle.setText("续火群组 (" + fireGroupList.size() + "个):");
    fireGroupTitle.setTextSize(16);
    fireGroupTitle.setTextColor(Color.parseColor(getTextColor()));
    fireGroupTitle.setPadding(0, dp2px(12), 0, dp2px(4));
    fireGroupTitle.setTypeface(null, Typeface.BOLD);
    contentLayout.addView(fireGroupTitle);
    
    if (fireGroupList.isEmpty()) {
        TextView emptyGroup = new TextView(activity);
        emptyGroup.setText("未配置续火群组");
        emptyGroup.setTextSize(14);
        emptyGroup.setTextColor(Color.parseColor(getSubTextColor()));
        emptyGroup.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        contentLayout.addView(emptyGroup);
    } else {
        for (int i = 0; i < fireGroupList.size(); i++) {
            TextView item = new TextView(activity);
            item.setText((i + 1) + ". " + fireGroupList.get(i));
            item.setTextSize(14);
            item.setTextColor(Color.parseColor(getTextColor()));
            item.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            contentLayout.addView(item);
        }
    }
    
    scrollView.addView(contentLayout);
    layout.addView(scrollView);
    
    dialogBuilder.setView(layout);
    dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    
    AlertDialog dialog = dialogBuilder.create();
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    dialog.show();
    
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    lp.height = Math.min(lp.height, (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.8));
    dialog.getWindow().setAttributes(lp);
}

void showWordsMenu(final Activity activity) {
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int theme = getCurrentTheme();
                String accentColor = getAccentColor();
                String pressedColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#3A6CD9" : "#3367D6";
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(16)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置续火语录");
                titleView.setTextSize(18);
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(44));
                btnParams.setMargins(0, 0, 0, dp2px(8));
                
                Button friendWordsBtn = new Button(activity);
                friendWordsBtn.setText("配置好友续火语录");
                friendWordsBtn.setTextColor(Color.WHITE);
                friendWordsBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                friendWordsBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                friendWordsBtn.setLayoutParams(btnParams);
                friendWordsBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFriendFireWords("", "", 0);
                    }
                });
                
                Button groupWordsBtn = new Button(activity);
                groupWordsBtn.setText("配置群组续火语录");
                groupWordsBtn.setTextColor(Color.WHITE);
                groupWordsBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                groupWordsBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                groupWordsBtn.setLayoutParams(btnParams);
                groupWordsBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configGroupFireWords("", "", 0);
                    }
                });
                
                layout.addView(friendWordsBtn);
                layout.addView(groupWordsBtn);
                
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
                builder.setView(layout);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                
                Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (cancelBtn != null) {
                    cancelBtn.setTextColor(Color.parseColor(accentColor));
                }
            } catch (Exception e) {
                Toasts("显示语录配置菜单失败: " + e.getMessage());
            }
        }
    });
}

void showTimeMenu(final Activity activity) {
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int theme = getCurrentTheme();
                String accentColor = getAccentColor();
                String pressedColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#3A6CD9" : "#3367D6";
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(16)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置执行时间");
                titleView.setTextSize(18);
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(44));
                btnParams.setMargins(0, 0, 0, dp2px(8));
                
                Button likeTimeBtn = new Button(activity);
                likeTimeBtn.setText("配置好友点赞时间");
                likeTimeBtn.setTextColor(Color.WHITE);
                likeTimeBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                likeTimeBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                likeTimeBtn.setLayoutParams(btnParams);
                likeTimeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configLikeTime("", "", 0);
                    }
                });
                
                Button friendFireTimeBtn = new Button(activity);
                friendFireTimeBtn.setText("配置好友续火时间");
                friendFireTimeBtn.setTextColor(Color.WHITE);
                friendFireTimeBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                friendFireTimeBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                friendFireTimeBtn.setLayoutParams(btnParams);
                friendFireTimeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFriendFireTime("", "", 0);
                    }
                });
                
                Button groupFireTimeBtn = new Button(activity);
                groupFireTimeBtn.setText("配置群组续火时间");
                groupFireTimeBtn.setTextColor(Color.WHITE);
                groupFireTimeBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                groupFireTimeBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                groupFireTimeBtn.setLayoutParams(btnParams);
                groupFireTimeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configGroupFireTime("", "", 0);
                    }
                });
                
                layout.addView(likeTimeBtn);
                layout.addView(friendFireTimeBtn);
                layout.addView(groupFireTimeBtn);
                
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
                builder.setView(layout);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                
                Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (cancelBtn != null) {
                    cancelBtn.setTextColor(Color.parseColor(accentColor));
                }
            } catch (Exception e) {
                Toasts("显示时间配置菜单失败: " + e.getMessage());
            }
        }
    });
}

void showExecuteMenu(final Activity activity) {
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int theme = getCurrentTheme();
                String accentColor = getAccentColor();
                String pressedColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#3A6CD9" : "#3367D6";
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(16)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("立即执行任务");
                titleView.setTextSize(18);
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(44));
                btnParams.setMargins(0, 0, 0, dp2px(8));
                
                Button likeBtn = new Button(activity);
                likeBtn.setText("立即点赞好友");
                likeBtn.setTextColor(Color.WHITE);
                likeBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                likeBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                likeBtn.setLayoutParams(btnParams);
                likeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateLike("", "", 0);
                    }
                });
                
                Button friendFireBtn = new Button(activity);
                friendFireBtn.setText("立即续火好友");
                friendFireBtn.setTextColor(Color.WHITE);
                friendFireBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                friendFireBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                friendFireBtn.setLayoutParams(btnParams);
                friendFireBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateFriendFire("", "", 0);
                    }
                });
                
                Button groupFireBtn = new Button(activity);
                groupFireBtn.setText("立即续火群组");
                groupFireBtn.setTextColor(Color.WHITE);
                groupFireBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                groupFireBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                groupFireBtn.setLayoutParams(btnParams);
                groupFireBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateGroupFire("", "", 0);
                    }
                });
                
                Button allBtn = new Button(activity);
                allBtn.setText("执行全部任务");
                allBtn.setTextColor(Color.WHITE);
                allBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
                allBtn.setPadding(dp2px(24), dp2px(12), dp2px(24), dp2px(12));
                allBtn.setLayoutParams(btnParams);
                allBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        executeAllTasks();
                    }
                });
                
                layout.addView(likeBtn);
                layout.addView(friendFireBtn);
                layout.addView(groupFireBtn);
                layout.addView(allBtn);
                
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
                builder.setView(layout);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                
                Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (cancelBtn != null) {
                    cancelBtn.setTextColor(Color.parseColor(accentColor));
                }
            } catch (Exception e) {
                Toasts("显示执行菜单失败: " + e.getMessage());
            }
        }
    });
}

public void configLikeFriends(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    final ProgressBar progressBar = new ProgressBar(activity);
    
    new Thread(new Runnable() {
        public void run() {
            try {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                
                List friendList = getNewFriendList();
                if (friendList == null || friendList.isEmpty()) {
                    Toasts("未添加任何好友");
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList qqList = new ArrayList();
                
                for (int i = 0; i < friendList.size(); i++) {
                    Object friend = friendList.get(i);
                    String qq = "";
                    String nickname = "";
                    String remark = "";
                    
                    try {
                        Object qqObj = getFieldValue(friend, "uin");
                        Object nicknameObj = getFieldValue(friend, "nickname");
                        Object remarkObj = getFieldValue(friend, "remark");
                        
                        if (qqObj != null) qq = qqObj.toString();
                        if (nicknameObj != null) nickname = nicknameObj.toString();
                        if (remarkObj != null) remark = remarkObj.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = (!remark.isEmpty() ? remark : nickname) + " (" + qq + ")";
                    displayList.add(displayName);
                    qqList.add(qq);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        showFriendSelectionDialog(activity, displayList, qqList, likeFriendList, "点赞", "like");
                    }
                });
            } catch (Exception e) {
                Toasts("获取好友列表失败");
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }).start();
}

public void configFireFriends(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    final ProgressBar progressBar = new ProgressBar(activity);
    
    new Thread(new Runnable() {
        public void run() {
            try {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                
                List friendList = getNewFriendList();
                if (friendList == null || friendList.isEmpty()) {
                    Toasts("未添加任何好友");
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList qqList = new ArrayList();
                
                for (int i = 0; i < friendList.size(); i++) {
                    Object friend = friendList.get(i);
                    String qq = "";
                    String nickname = "";
                    String remark = "";
                    
                    try {
                        Object qqObj = getFieldValue(friend, "uin");
                        Object nicknameObj = getFieldValue(friend, "nickname");
                        Object remarkObj = getFieldValue(friend, "remark");
                        
                        if (qqObj != null) qq = qqObj.toString();
                        if (nicknameObj != null) nickname = nicknameObj.toString();
                        if (remarkObj != null) remark = remarkObj.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = (!remark.isEmpty() ? remark : nickname) + " (" + qq + ")";
                    displayList.add(displayName);
                    qqList.add(qq);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        showFriendSelectionDialog(activity, displayList, qqList, fireFriendList, "续火", "fire");
                    }
                });
            } catch (Exception e) {
                Toasts("获取好友列表失败");
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }).start();
}

private void showFriendSelectionDialog(Activity activity, ArrayList displayList, ArrayList qqList, 
                                     ArrayList selectedList, String taskName, String configType) {
    
    if (activity == null || activity.isFinishing()) {
        Toasts("无法获取有效的Activity");
        return;
    }
    
    try {
        int theme = getCurrentTheme();
        String accentColor = getAccentColor();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
        
        LinearLayout mainLayout = new LinearLayout(activity);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(dp2px(4), dp2px(4), dp2px(4), dp2px(4));
        
        final ArrayList currentSelected = new ArrayList(selectedList);

        LinearLayout contentLayout = new LinearLayout(activity);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
        contentLayout.setBackground(getWebShape(getCardColor(), dp2px(16)));
        
        TextView titleView = new TextView(activity);
        titleView.setText("选择" + taskName + "好友");
        titleView.setTextColor(Color.parseColor(getTextColor()));
        titleView.setTextSize(18);
        titleView.setGravity(Gravity.CENTER);
        titleView.setPadding(0, 0, 0, dp2px(16));
        contentLayout.addView(titleView);
        
        final EditText searchBox = new EditText(activity);
        searchBox.setHint("搜索好友QQ号、好友名、备注");
        searchBox.setTextColor(Color.parseColor(getTextColor()));
        searchBox.setHintTextColor(Color.parseColor(getSubTextColor()));
        searchBox.setBackground(getWebShape(getSurfaceColor(), dp2px(8)));
        searchBox.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
        contentLayout.addView(searchBox);
        
        LinearLayout buttonLayout = new LinearLayout(activity);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonLayoutParams.setMargins(0, dp2px(12), 0, dp2px(12));
        buttonLayout.setLayoutParams(buttonLayoutParams);
        
        String pressedColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#3A6CD9" : "#3367D6";
        
        Button selectAllBtn = new Button(activity);
        selectAllBtn.setText("全选");
        selectAllBtn.setTextColor(Color.WHITE);
        selectAllBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
        selectAllBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
        LinearLayout.LayoutParams selectAllParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        selectAllParams.setMargins(dp2px(2), 0, dp2px(2), 0);
        selectAllBtn.setLayoutParams(selectAllParams);
        
        Button clearAllBtn = new Button(activity);
        clearAllBtn.setText("全不选");
        clearAllBtn.setTextColor(Color.WHITE);
        clearAllBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
        clearAllBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
        LinearLayout.LayoutParams clearAllParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        clearAllParams.setMargins(dp2px(2), 0, dp2px(2), 0);
        clearAllBtn.setLayoutParams(clearAllParams);
        
        Button invertBtn = new Button(activity);
        invertBtn.setText("反选");
        invertBtn.setTextColor(Color.WHITE);
        invertBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
        invertBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
        LinearLayout.LayoutParams invertParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        invertParams.setMargins(dp2px(2), 0, dp2px(2), 0);
        invertBtn.setLayoutParams(invertParams);
        
        buttonLayout.addView(selectAllBtn);
        buttonLayout.addView(clearAllBtn);
        buttonLayout.addView(invertBtn);
        contentLayout.addView(buttonLayout);
        
        final ListView listView = new ListView(activity);
        listView.setBackground(getWebShape(getSurfaceColor(), dp2px(8)));
        listView.setDividerHeight(dp2px(1));
        LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        listParams.weight = 1;
        listView.setLayoutParams(listParams);
        contentLayout.addView(listView);
        
        mainLayout.addView(contentLayout);
        
        final ArrayList filteredDisplayList = new ArrayList(displayList);
        final ArrayList filteredQQList = new ArrayList(qqList);
        
        final CustomArrayAdapter adapter = new CustomArrayAdapter(activity, filteredDisplayList, filteredQQList, currentSelected, getTextColor());
        
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String qq = (String) filteredQQList.get(position);
                if (currentSelected.contains(qq)) {
                    currentSelected.remove(qq);
                } else {
                    currentSelected.add(qq);
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        searchBox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();
                filteredDisplayList.clear();
                filteredQQList.clear();
                
                if (searchText.isEmpty()) {
                    filteredDisplayList.addAll(displayList);
                    filteredQQList.addAll(qqList);
                } else {
                    for (int i = 0; i < displayList.size(); i++) {
                        String displayName = ((String) displayList.get(i)).toLowerCase();
                        String qq = (String) qqList.get(i);
                        
                        if (displayName.contains(searchText) || qq.contains(searchText)) {
                            filteredDisplayList.add(displayList.get(i));
                            filteredQQList.add(qqList.get(i));
                        }
                    }
                }
                
                adapter.notifyDataSetChanged();
            }
        });
        
        selectAllBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < filteredQQList.size(); i++) {
                    String qq = (String) filteredQQList.get(i);
                    if (!currentSelected.contains(qq)) {
                        currentSelected.add(qq);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        clearAllBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < filteredQQList.size(); i++) {
                    String qq = (String) filteredQQList.get(i);
                    currentSelected.remove(qq);
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        invertBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < filteredQQList.size(); i++) {
                    String qq = (String) filteredQQList.get(i);
                    if (currentSelected.contains(qq)) {
                        currentSelected.remove(qq);
                    } else {
                        currentSelected.add(qq);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        dialogBuilder.setView(mainLayout);
        
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                selectedList.clear();
                selectedList.addAll(currentSelected);
                
                if (configType.equals("like")) {
                    saveLikeFriends();
                    Toasts("已选择" + selectedList.size() + "位点赞好友");
                } else if (configType.equals("fire")) {
                    saveFireFriends();
                    Toasts("已选择" + selectedList.size() + "位续火好友");
                }
                
                checkAndExecuteTasks();
            }
        });
        
        dialogBuilder.setNegativeButton("取消", null);
        
        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
        lp.height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.8);
        dialog.getWindow().setAttributes(lp);
        
        Button confirmBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (confirmBtn != null) {
            confirmBtn.setTextColor(Color.parseColor(accentColor));
        }
        Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (cancelBtn != null) {
            cancelBtn.setTextColor(Color.parseColor(accentColor));
        }
        
    } catch (Exception e) {
        Toasts("打开选择对话框失败: " + e.getMessage());
    }
}

public void configFireGroups(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    final ProgressBar progressBar = new ProgressBar(activity);
    
    new Thread(new Runnable() {
        public void run() {
            try {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                
                ArrayList groupList = getGroupList();
                if (groupList == null || groupList.isEmpty()) {
                    Toasts("未加入任何群组");
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList qqList = new ArrayList();
                for (int i = 0; i < groupList.size(); i++) {
                    Object group = groupList.get(i);
                    String groupName = "";
                    String groupUin = "";
                    try {
                        Object nameObj = getFieldValue(group, "GroupName");
                        Object uinObj = getFieldValue(group, "GroupUin");
                        
                        if (nameObj != null) groupName = nameObj.toString();
                        if (uinObj != null) groupUin = uinObj.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = groupName + " (" + groupUin + ")";
                    displayList.add(displayName);
                    qqList.add(groupUin);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        showGroupSelectionDialog(activity, displayList, qqList);
                    }
                });
            } catch (Exception e) {
                Toasts("获取群组列表失败");
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }).start();
}

private void showGroupSelectionDialog(Activity activity, ArrayList displayList, ArrayList qqList) {
    if (activity == null || activity.isFinishing()) {
        Toasts("无法获取有效的Activity");
        return;
    }
    
    try {
        int theme = getCurrentTheme();
        String accentColor = getAccentColor();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
        
        LinearLayout mainLayout = new LinearLayout(activity);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(dp2px(4), dp2px(4), dp2px(4), dp2px(4));
        
        final ArrayList currentSelected = new ArrayList(fireGroupList);
        
        LinearLayout contentLayout = new LinearLayout(activity);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
        contentLayout.setBackground(getWebShape(getCardColor(), dp2px(16)));
        
        TextView titleView = new TextView(activity);
        titleView.setText("选择续火群组");
        titleView.setTextColor(Color.parseColor(getTextColor()));
        titleView.setTextSize(18);
        titleView.setGravity(Gravity.CENTER);
        titleView.setPadding(0, 0, 0, dp2px(16));
        contentLayout.addView(titleView);
        
        final EditText searchBox = new EditText(activity);
        searchBox.setHint("搜索群号、群名");
        searchBox.setTextColor(Color.parseColor(getTextColor()));
        searchBox.setHintTextColor(Color.parseColor(getSubTextColor()));
        searchBox.setBackground(getWebShape(getSurfaceColor(), dp2px(8)));
        searchBox.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
        contentLayout.addView(searchBox);
        
        LinearLayout buttonLayout = new LinearLayout(activity);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, dp2px(12), 0, dp2px(12));
        buttonLayout.setLayoutParams(buttonLayoutParams);
        
        String pressedColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#3A6CD9" : "#3367D6";
        
        Button selectAllBtn = new Button(activity);
        selectAllBtn.setText("全选");
        selectAllBtn.setTextColor(Color.WHITE);
        selectAllBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
        selectAllBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
        LinearLayout.LayoutParams selectAllParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        selectAllParams.setMargins(dp2px(2), 0, dp2px(2), 0);
        selectAllBtn.setLayoutParams(selectAllParams);
        
        Button clearAllBtn = new Button(activity);
        clearAllBtn.setText("全不选");
        clearAllBtn.setTextColor(Color.WHITE);
        clearAllBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
        clearAllBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
        LinearLayout.LayoutParams clearAllParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        clearAllParams.setMargins(dp2px(2), 0, dp2px(2), 0);
        clearAllBtn.setLayoutParams(clearAllParams);
        
        Button invertBtn = new Button(activity);
        invertBtn.setText("反选");
        invertBtn.setTextColor(Color.WHITE);
        invertBtn.setBackground(getButtonBackground(accentColor, pressedColor, dp2px(8)));
        invertBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
        LinearLayout.LayoutParams invertParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        invertParams.setMargins(dp2px(2), 0, dp2px(2), 0);
        invertBtn.setLayoutParams(invertParams);
        
        buttonLayout.addView(selectAllBtn);
        buttonLayout.addView(clearAllBtn);
        buttonLayout.addView(invertBtn);
        contentLayout.addView(buttonLayout);
        
        final ListView listView = new ListView(activity);
        listView.setBackground(getWebShape(getSurfaceColor(), dp2px(8)));
        listView.setDividerHeight(dp2px(1));
        LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        listParams.weight = 1;
        listView.setLayoutParams(listParams);
        contentLayout.addView(listView);
        
        mainLayout.addView(contentLayout);
        
        final ArrayList filteredDisplayList = new ArrayList(displayList);
        final ArrayList filteredQQList = new ArrayList(qqList);
        
        final CustomArrayAdapter adapter = new CustomArrayAdapter(activity, filteredDisplayList, filteredQQList, currentSelected, getTextColor());
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String qq = (String) filteredQQList.get(position);
                if (currentSelected.contains(qq)) {
                    currentSelected.remove(qq);
                } else {
                    currentSelected.add(qq);
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        searchBox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();
                filteredDisplayList.clear();
                filteredQQList.clear();
                
                if (searchText.isEmpty()) {
                    filteredDisplayList.addAll(displayList);
                    filteredQQList.addAll(qqList);
                } else {
                    for (int i = 0; i < displayList.size(); i++) {
                        String displayName = ((String) displayList.get(i)).toLowerCase();
                        String qq = (String) qqList.get(i);
                        
                        if (displayName.contains(searchText) || qq.contains(searchText)) {
                            filteredDisplayList.add(displayList.get(i));
                            filteredQQList.add(qqList.get(i));
                        }
                    }
                }
                
                adapter.notifyDataSetChanged();
            }
        });
        
        selectAllBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < filteredQQList.size(); i++) {
                    String qq = (String) filteredQQList.get(i);
                    if (!currentSelected.contains(qq)) {
                        currentSelected.add(qq);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        clearAllBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < filteredQQList.size(); i++) {
                    String qq = (String) filteredQQList.get(i);
                    currentSelected.remove(qq);
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        invertBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < filteredQQList.size(); i++) {
                    String qq = (String) filteredQQList.get(i);
                    if (currentSelected.contains(qq)) {
                        currentSelected.remove(qq);
                    } else {
                        currentSelected.add(qq);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        dialogBuilder.setView(mainLayout);
        
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                fireGroupList.clear();
                fireGroupList.addAll(currentSelected);
                
                saveFireGroups();
                Toasts("已选择" + fireGroupList.size() + "个续火群组");
                
                checkAndExecuteTasks();
            }
        });
        
        dialogBuilder.setNegativeButton("取消", null);
        
        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
        lp.height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.8);
        dialog.getWindow().setAttributes(lp);
        
        Button confirmBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (confirmBtn != null) {
            confirmBtn.setTextColor(Color.parseColor(accentColor));
        }
        Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (cancelBtn != null) {
            cancelBtn.setTextColor(Color.parseColor(accentColor));
        }
        
    } catch (Exception e) {
        Toasts("打开选择对话框失败: " + e.getMessage());
    }
}

public void configFriendFireWords(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) {
        Toasts("无法获取Activity");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder wordsBuilder = new StringBuilder();
                for (int i = 0; i < friendFireWords.size(); i++) {
                    if (wordsBuilder.length() > 0) wordsBuilder.append("\n");
                    wordsBuilder.append((String) friendFireWords.get(i));
                }
                
                int theme = getCurrentTheme();
                String accentColor = getAccentColor();
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(16)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置好友续火语录");
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setTextSize(18);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                final EditText wordsEdit = new EditText(activity);
                wordsEdit.setText(wordsBuilder.toString());
                wordsEdit.setHint("输入好友续火语录，每行一个");
                wordsEdit.setTextColor(Color.parseColor(getTextColor()));
                wordsEdit.setTextSize(15);
                wordsEdit.setHintTextColor(Color.parseColor(getSubTextColor()));
                wordsEdit.setMinLines(6);
                wordsEdit.setMaxLines(15);
                wordsEdit.setGravity(Gravity.TOP);
                wordsEdit.setBackground(getWebShape(getSurfaceColor(), dp2px(8)));
                wordsEdit.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                
                LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                editParams.setMargins(0, 0, 0, dp2px(16));
                wordsEdit.setLayoutParams(editParams);
                
                layout.addView(wordsEdit);
                
                TextView hintView = new TextView(activity);
                hintView.setText("注意：输入多个续火语录时，每行一个");
                hintView.setTextColor(Color.parseColor(getSubTextColor()));
                hintView.setTextSize(14);
                hintView.setPadding(0, 0, 0, 0);
                layout.addView(hintView);
                
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
                dialogBuilder.setView(layout);
                dialogBuilder.setCancelable(true);
                
                dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String wordsText = wordsEdit.getText().toString().trim();
                        if (wordsText.isEmpty()) {
                            Toasts("续火语录不能为空");
                            return;
                        }
                        
                        friendFireWords.clear();
                        String[] wordsArray = wordsText.split("\n");
                        for (int i = 0; i < wordsArray.length; i++) {
                            String word = wordsArray[i].trim();
                            if (!word.isEmpty()) {
                                friendFireWords.add(word);
                            }
                        }
                        
                        if (friendFireWords.isEmpty()) {
                            Toasts("未添加有效的续火语录");
                            return;
                        }
                        
                        saveListToFile(friendFireWordsFile, friendFireWords);
                        Toasts("已保存 " + friendFireWords.size() + " 个好友续火语录");
                    }
                });
                
                dialogBuilder.setNegativeButton("取消", null);
                
                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                
                Button saveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if (saveBtn != null) {
                    saveBtn.setTextColor(Color.parseColor(accentColor));
                }
                Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (cancelBtn != null) {
                    cancelBtn.setTextColor(Color.parseColor(accentColor));
                }
            } catch (Exception e) {
            }
        }
    });
}

public void configGroupFireWords(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) {
        Toasts("无法获取Activity");
        return;
    }
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder wordsBuilder = new StringBuilder();
                for (int i = 0; i < groupFireWords.size(); i++) {
                    if (wordsBuilder.length() > 0) wordsBuilder.append("\n");
                    wordsBuilder.append((String) groupFireWords.get(i));
                }
                
                int theme = getCurrentTheme();
                String accentColor = getAccentColor();
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(16)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置群组续火语录");
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setTextSize(18);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                final EditText wordsEdit = new EditText(activity);
                wordsEdit.setText(wordsBuilder.toString());
                wordsEdit.setHint("输入群组续火语录，每行一个");
                wordsEdit.setTextColor(Color.parseColor(getTextColor()));
                wordsEdit.setTextSize(15);
                wordsEdit.setHintTextColor(Color.parseColor(getSubTextColor()));
                wordsEdit.setMinLines(6);
                wordsEdit.setMaxLines(15);
                wordsEdit.setGravity(Gravity.TOP);
                wordsEdit.setBackground(getWebShape(getSurfaceColor(), dp2px(8)));
                wordsEdit.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                
                LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                editParams.setMargins(0, 0, 0, dp2px(16));
                wordsEdit.setLayoutParams(editParams);
                
                layout.addView(wordsEdit);
                
                TextView hintView = new TextView(activity);
                hintView.setText("注意：输入多个续火语录时，每行一个");
                hintView.setTextColor(Color.parseColor(getSubTextColor()));
                hintView.setTextSize(14);
                hintView.setPadding(0, 0, 0, 0);
                layout.addView(hintView);
                
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
                dialogBuilder.setView(layout);
                dialogBuilder.setCancelable(true);
                
                dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String wordsText = wordsEdit.getText().toString().trim();
                        if (wordsText.isEmpty()) {
                            Toasts("续火语录不能为空");
                            return;
                        }
                        
                        groupFireWords.clear();
                        String[] wordsArray = wordsText.split("\n");
                        for (int i = 0; i < wordsArray.length; i++) {
                            String word = wordsArray[i].trim();
                            if (!word.isEmpty()) {
                                groupFireWords.add(word);
                            }
                        }
                        
                        if (groupFireWords.isEmpty()) {
                            Toasts("未添加有效的续火语录");
                            return;
                        }
                        
                        saveListToFile(groupFireWordsFile, groupFireWords);
                        Toasts("已保存 " + groupFireWords.size() + " 个群组续火语录");
                    }
                });
                
                dialogBuilder.setNegativeButton("取消", null);
                
                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
    
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
    
                Button saveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if (saveBtn != null) {
                    saveBtn.setTextColor(Color.parseColor(accentColor));
                }
                Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (cancelBtn != null) {
                    cancelBtn.setTextColor(Color.parseColor(accentColor));
                }
            } catch (Exception e) {
            }
        }
    });
}

public void configLikeTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    int theme = getCurrentTheme();
    String accentColor = getAccentColor();
    String cardColor = getCardColor();
    String textColor = getTextColor();
    String subTextColor = getSubTextColor();
    String surfaceColor = getSurfaceColor();
    
    LinearLayout layout = new LinearLayout(activity);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
    layout.setBackground(getWebShape(cardColor, dp2px(16)));
    
    TextView titleView = new TextView(activity);
    titleView.setText("设置点赞时间");
    titleView.setTextColor(Color.parseColor(textColor));
    titleView.setTextSize(18);
    titleView.setGravity(Gravity.CENTER);
    titleView.setPadding(0, 0, 0, dp2px(16));
    layout.addView(titleView);
    
    TextView hintView = new TextView(activity);
    hintView.setText("当前时间: " + likeTime);
    hintView.setTextColor(Color.parseColor(subTextColor));
    hintView.setTextSize(14);
    hintView.setPadding(0, 0, 0, dp2px(8));
    layout.addView(hintView);
    
    final EditText timeEdit = new EditText(activity);
    timeEdit.setText(likeTime);
    timeEdit.setHint("格式 HH:MM (例如 08:30)");
    timeEdit.setTextColor(Color.parseColor(textColor));
    timeEdit.setHintTextColor(Color.parseColor(subTextColor));
    timeEdit.setBackground(getWebShape(surfaceColor, dp2px(8)));
    timeEdit.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
    layout.addView(timeEdit);
    
    AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
    builder.setView(layout);
    builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String newTime = timeEdit.getText().toString().trim();
            if (newTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                likeTime = newTime;
                saveTimeConfig();
                Toasts("已设置点赞时间: " + likeTime);
                checkAndExecuteTasks();
            } else {
                Toasts("时间格式错误，请使用 HH:MM 格式 (如 08:30)");
            }
        }
    });
    builder.setNegativeButton("取消", null);
    
    AlertDialog dialog = builder.create();
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    dialog.show();
    
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    dialog.getWindow().setAttributes(lp);
    
    Button saveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
    if (saveBtn != null) {
        saveBtn.setTextColor(Color.parseColor(accentColor));
    }
    Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
    if (cancelBtn != null) {
        cancelBtn.setTextColor(Color.parseColor(accentColor));
    }
}

public void configFriendFireTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    int theme = getCurrentTheme();
    String accentColor = getAccentColor();
    String cardColor = getCardColor();
    String textColor = getTextColor();
    String subTextColor = getSubTextColor();
    String surfaceColor = getSurfaceColor();
    
    LinearLayout layout = new LinearLayout(activity);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
    layout.setBackground(getWebShape(cardColor, dp2px(16)));
    
    TextView titleView = new TextView(activity);
    titleView.setText("设置好友续火时间");
    titleView.setTextColor(Color.parseColor(textColor));
    titleView.setTextSize(18);
    titleView.setGravity(Gravity.CENTER);
    titleView.setPadding(0, 0, 0, dp2px(16));
    layout.addView(titleView);
    
    TextView hintView = new TextView(activity);
    hintView.setText("当前时间: " + fireFriendTime);
    hintView.setTextColor(Color.parseColor(subTextColor));
    hintView.setTextSize(14);
    hintView.setPadding(0, 0, 0, dp2px(8));
    layout.addView(hintView);
    
    final EditText timeEdit = new EditText(activity);
    timeEdit.setText(fireFriendTime);
    timeEdit.setHint("格式 HH:MM (例如 08:30)");
    timeEdit.setTextColor(Color.parseColor(textColor));
    timeEdit.setHintTextColor(Color.parseColor(subTextColor));
    timeEdit.setBackground(getWebShape(surfaceColor, dp2px(8)));
    timeEdit.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
    layout.addView(timeEdit);
    
    AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
    builder.setView(layout);
    builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String newTime = timeEdit.getText().toString().trim();
            if (newTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                fireFriendTime = newTime;
                saveTimeConfig();
                Toasts("已设置好友续火时间: " + fireFriendTime);
                checkAndExecuteTasks();
            } else {
                Toasts("时间格式错误，请使用 HH:MM 格式 (如 08:30)");
            }
        }
    });
    builder.setNegativeButton("取消", null);
    
    AlertDialog dialog = builder.create();
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    dialog.show();
    
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    dialog.getWindow().setAttributes(lp);
    
    Button saveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
    if (saveBtn != null) {
        saveBtn.setTextColor(Color.parseColor(accentColor));
    }
    Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
    if (cancelBtn != null) {
        cancelBtn.setTextColor(Color.parseColor(accentColor));
    }
}

public void configGroupFireTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    int theme = getCurrentTheme();
    String accentColor = getAccentColor();
    String cardColor = getCardColor();
    String textColor = getTextColor();
    String subTextColor = getSubTextColor();
    String surfaceColor = getSurfaceColor();
    
    LinearLayout layout = new LinearLayout(activity);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
    layout.setBackground(getWebShape(cardColor, dp2px(16)));
    
    TextView titleView = new TextView(activity);
    titleView.setText("设置群组续火时间");
    titleView.setTextColor(Color.parseColor(textColor));
    titleView.setTextSize(18);
    titleView.setGravity(Gravity.CENTER);
    titleView.setPadding(0, 0, 0, dp2px(16));
    layout.addView(titleView);
    
    TextView hintView = new TextView(activity);
    hintView.setText("当前时间: " + fireGroupTime);
    hintView.setTextColor(Color.parseColor(subTextColor));
    hintView.setTextSize(14);
    hintView.setPadding(0, 0, 0, dp2px(8));
    layout.addView(hintView);
    
    final EditText timeEdit = new EditText(activity);
    timeEdit.setText(fireGroupTime);
    timeEdit.setHint("格式 HH:MM (例如 08:30)");
    timeEdit.setTextColor(Color.parseColor(textColor));
    timeEdit.setHintTextColor(Color.parseColor(subTextColor));
    timeEdit.setBackground(getWebShape(surfaceColor, dp2px(8)));
    timeEdit.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
    layout.addView(timeEdit);
    
    AlertDialog.Builder builder = new AlertDialog.Builder(activity, theme);
    builder.setView(layout);
    builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String newTime = timeEdit.getText().toString().trim();
            if (newTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                fireGroupTime = newTime;
                saveTimeConfig();
                Toasts("已设置群组续火时间: " + fireGroupTime);
                checkAndExecuteTasks();
            } else {
                Toasts("时间格式错误，请使用 HH:MM 格式 (如 08:30)");
            }
        }
    });
    builder.setNegativeButton("取消", null);
    
    AlertDialog dialog = builder.create();
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    dialog.show();
    
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    dialog.getWindow().setAttributes(lp);
    
    Button saveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
    if (saveBtn != null) {
        saveBtn.setTextColor(Color.parseColor(accentColor));
    }
    Button cancelBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
    if (cancelBtn != null) {
        cancelBtn.setTextColor(Color.parseColor(accentColor));
    }
}

// 那天我们聊到深夜 心比手机烫 我以为那是永远