
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
                        "- [修复] 一些存在的问题\n" +
                        "- [移除] 以前的更新日志堆积成山没有保留的必要，已进行删除从而保持代码的简洁性\n" +
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