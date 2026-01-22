
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

String MD3_PRIMARY = "#6750A4";
String MD3_ON_PRIMARY = "#FFFFFF";
String MD3_SECONDARY = "#625B71";
String MD3_ON_SECONDARY = "#FFFFFF";
String MD3_TERTIARY = "#7D5260";
String MD3_ON_TERTIARY = "#FFFFFF";
String MD3_SURFACE = "#FEF7FF";
String MD3_ON_SURFACE = "#1C1B1F";
String MD3_SURFACE_VARIANT = "#E7E0EC";
String MD3_ON_SURFACE_VARIANT = "#49454F";
String MD3_DARK_SURFACE = "#1C1B1F";
String MD3_DARK_ON_SURFACE = "#F4EFF4";
String MD3_DARK_SURFACE_VARIANT = "#49454F";
String MD3_DARK_ON_SURFACE_VARIANT = "#CAC4D0";

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
        return MD3_DARK_SURFACE;
    } else {
        return MD3_SURFACE;
    }
}

public String getTextColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return MD3_DARK_ON_SURFACE;
    } else {
        return MD3_ON_SURFACE;
    }
}

public String getSubTextColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return MD3_DARK_ON_SURFACE_VARIANT;
    } else {
        return MD3_ON_SURFACE_VARIANT;
    }
}

public String getCardColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return MD3_DARK_SURFACE_VARIANT;
    } else {
        return "#FFFFFF";
    }
}

public String getAccentColor() {
    return MD3_PRIMARY;
}

public String getAccentColorDark() {
    return "#5A4C8C";
}

public String getSurfaceColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return MD3_DARK_SURFACE_VARIANT;
    } else {
        return MD3_SURFACE_VARIANT;
    }
}

public String getBorderColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#5A5760";
    } else {
        return "#CAC4D0";
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
                        "以下是简洁群管的部分更新日志\n" +
                        "——————————\n" +
                        "简洁群管_114.0_更新日志\n" +
                        "- [调整] 优化脚本主题，以后会继续优化\n" +
                        "- [移除] 大量的更新日志\n\n" +
                        "喜欢的人要早点说 有bug及时反馈");
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
                container.setPadding(dp2px(0), dp2px(0), dp2px(0), dp2px(0));

                GradientDrawable bg = new GradientDrawable();
                bg.setColor(Color.parseColor(getCardColor()));
                bg.setCornerRadius(dp2px(12));
                scrollView.setBackground(bg);

                container.addView(scrollView);

                builder.setView(container);
                builder.setPositiveButton("确定", null);

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
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
                    container.setPadding(dp2px(0), dp2px(0), dp2px(0), dp2px(0));

                    GradientDrawable bg = new GradientDrawable();
                    bg.setColor(Color.parseColor(getCardColor()));
                    bg.setCornerRadius(dp2px(12));
                    scrollView.setBackground(bg);

                    container.addView(scrollView);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
                    builder.setTitle("群管功能(QQVersion：" + qqVersion + ")")
                        .setView(container)
                        .setNegativeButton("关闭", null);

                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
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
                mainLayout.setPadding(dp2px(0), dp2px(0), dp2px(0), dp2px(0));

                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
                layout.setBackgroundColor(Color.parseColor(getCardColor()));

                int theme = getCurrentTheme();
                String accentColor = getAccentColor();

                TextView hint = new TextView(activity);
                hint.setText("当前黑名单数量: " + 黑名单列表.size() + " 人");
                hint.setTextSize(14);
                hint.setTextColor(Color.parseColor(getTextColor()));
                hint.setPadding(0, 0, 0, dp2px(16));
                layout.addView(hint);

                EditText inputEditText = new EditText(activity);
                inputEditText.setHint("输入QQ号添加，多个用逗号分开");
                inputEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
                inputEditText.setTextColor(Color.parseColor(getTextColor()));
                inputEditText.setTextSize(14);

                GradientDrawable etBg = new GradientDrawable();
                etBg.setColor(Color.parseColor(getSurfaceColor()));
                etBg.setCornerRadius(dp2px(8));
                inputEditText.setBackground(etBg);
                inputEditText.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                inputEditText.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                layout.addView(inputEditText);

                View spacer = new View(activity);
                spacer.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(16)
                ));
                layout.addView(spacer);

                LinearLayout buttonLayout = new LinearLayout(activity);
                buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
                buttonLayout.setGravity(Gravity.CENTER);
                buttonLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                Button addBtn = new Button(activity);
                addBtn.setText("添加用户");
                addBtn.setTextSize(14);
                addBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                addBtn.setTextColor(Color.WHITE);
                addBtn.setGravity(Gravity.CENTER);
                addBtn.setPadding(dp2px(20), dp2px(12), dp2px(20), dp2px(12));

                GradientDrawable addBg = getShape(accentColor, dp2px(20));
                addBtn.setBackground(addBg);

                addBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            String[] qqs = input.split("[,\\s]+");
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
                            } else {
                                toast("没有添加新的黑名单");
                            }
                        } else {
                            toast("请输入QQ号");
                        }
                    }
                });

                Button removeSelectedBtn = new Button(activity);
                removeSelectedBtn.setText("删除选中");
                removeSelectedBtn.setTextSize(14);
                removeSelectedBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                removeSelectedBtn.setTextColor(Color.WHITE);
                removeSelectedBtn.setGravity(Gravity.CENTER);
                removeSelectedBtn.setPadding(dp2px(20), dp2px(12), dp2px(20), dp2px(12));

                GradientDrawable removeBg = getShape(accentColor, dp2px(20));
                removeSelectedBtn.setBackground(removeBg);

                removeSelectedBtn.setOnClickListener(new View.OnClickListener() {
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

                            for (int i = 0; i < listView.getCount(); i++) {
                                listView.setItemChecked(i, false);
                            }
                        } else {
                            toast("请先选择要删除的黑名单");
                        }
                    }
                });

                Button clearAllBtn = new Button(activity);
                clearAllBtn.setText("清空全部");
                clearAllBtn.setTextSize(14);
                clearAllBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                clearAllBtn.setTextColor(Color.WHITE);
                clearAllBtn.setGravity(Gravity.CENTER);
                clearAllBtn.setPadding(dp2px(20), dp2px(12), dp2px(20), dp2px(12));

                GradientDrawable clearBg = getShape("#F44336", dp2px(20));
                clearAllBtn.setBackground(clearBg);

                clearAllBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            全弃(黑名单文件);
                            toast("已清空所有黑名单");

                            黑名单列表.clear();
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            toast("清空失败: " + e.getMessage());
                        }
                    }
                });

                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
                );
                buttonParams.setMargins(dp2px(4), 0, dp2px(4), 0);

                addBtn.setLayoutParams(buttonParams);
                removeSelectedBtn.setLayoutParams(buttonParams);
                clearAllBtn.setLayoutParams(buttonParams);

                buttonLayout.addView(addBtn);
                buttonLayout.addView(removeSelectedBtn);
                buttonLayout.addView(clearAllBtn);

                layout.addView(buttonLayout);

                View spacer2 = new View(activity);
                spacer2.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(16)
                ));
                layout.addView(spacer2);

                final ListView listView = new ListView(activity);
                final CustomArrayAdapter adapter = new CustomArrayAdapter(activity, 
                    android.R.layout.simple_list_item_multiple_choice, 
                    黑名单列表,
                    getTextColor()
                );
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setDividerHeight(0);
                listView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(200)
                ));

                GradientDrawable listBg = new GradientDrawable();
                listBg.setColor(Color.parseColor(getSurfaceColor()));
                listBg.setCornerRadius(dp2px(8));
                listView.setBackground(listBg);

                layout.addView(listView);

                TextView bottomHint = new TextView(activity);
                bottomHint.setText("共 " + 黑名单列表.size() + " 个黑名单用户");
                bottomHint.setTextSize(12);
                bottomHint.setTextColor(Color.parseColor(getSubTextColor()));
                bottomHint.setGravity(Gravity.CENTER);
                bottomHint.setPadding(0, dp2px(12), 0, 0);
                layout.addView(bottomHint);

                mainLayout.addView(layout);

                builder.setView(mainLayout);
                builder.setNegativeButton("关闭", null);

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                mainLayout.setPadding(dp2px(0), dp2px(0), dp2px(0), dp2px(0));

                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
                layout.setBackgroundColor(Color.parseColor(getCardColor()));

                int theme = getCurrentTheme();
                String accentColor = getAccentColor();

                TextView hint = new TextView(activity);
                hint.setText("当代管数量: " + 代管列表.size() + " 人");
                hint.setTextSize(14);
                hint.setTextColor(Color.parseColor(getTextColor()));
                hint.setPadding(0, 0, 0, dp2px(16));
                layout.addView(hint);

                EditText inputEditText = new EditText(activity);
                inputEditText.setHint("输入QQ号添加，多个用逗号分开");
                inputEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
                inputEditText.setTextColor(Color.parseColor(getTextColor()));
                inputEditText.setTextSize(14);

                GradientDrawable etBg = new GradientDrawable();
                etBg.setColor(Color.parseColor(getSurfaceColor()));
                etBg.setCornerRadius(dp2px(8));
                inputEditText.setBackground(etBg);
                inputEditText.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                inputEditText.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                layout.addView(inputEditText);

                View spacer = new View(activity);
                spacer.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(16)
                ));
                layout.addView(spacer);

                LinearLayout buttonLayout = new LinearLayout(activity);
                buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
                buttonLayout.setGravity(Gravity.CENTER);
                buttonLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                Button addBtn = new Button(activity);
                addBtn.setText("添加用户");
                addBtn.setTextSize(14);
                addBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                addBtn.setTextColor(Color.WHITE);
                addBtn.setGravity(Gravity.CENTER);
                addBtn.setPadding(dp2px(20), dp2px(12), dp2px(20), dp2px(12));

                GradientDrawable addBg = getShape(accentColor, dp2px(20));
                addBtn.setBackground(addBg);

                addBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String input = inputEditText.getText().toString().trim();
                        if (!input.isEmpty()) {
                            String[] qqs = input.split("[,\\s]+");
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
                            } else {
                                toast("没有添加新的代管");
                            }
                        } else {
                            toast("请输入QQ号");
                        }
                    }
                });

                Button removeSelectedBtn = new Button(activity);
                removeSelectedBtn.setText("删除选中");
                removeSelectedBtn.setTextSize(14);
                removeSelectedBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                removeSelectedBtn.setTextColor(Color.WHITE);
                removeSelectedBtn.setGravity(Gravity.CENTER);
                removeSelectedBtn.setPadding(dp2px(20), dp2px(12), dp2px(20), dp2px(12));

                GradientDrawable removeBg = getShape(accentColor, dp2px(20));
                removeSelectedBtn.setBackground(removeBg);

                removeSelectedBtn.setOnClickListener(new View.OnClickListener() {
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

                            for (int i = 0; i < listView.getCount(); i++) {
                                listView.setItemChecked(i, false);
                            }
                        } else {
                            toast("请先选择要删除的代管");
                        }
                    }
                });

                Button clearAllBtn = new Button(activity);
                clearAllBtn.setText("清空全部");
                clearAllBtn.setTextSize(14);
                clearAllBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                clearAllBtn.setTextColor(Color.WHITE);
                clearAllBtn.setGravity(Gravity.CENTER);
                clearAllBtn.setPadding(dp2px(20), dp2px(12), dp2px(20), dp2px(12));

                GradientDrawable clearBg = getShape("#F44336", dp2px(20));
                clearAllBtn.setBackground(clearBg);

                clearAllBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            全弃(代管文件);
                            toast("已清空所有代管");

                            代管列表.clear();
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            toast("清空失败: " + e.getMessage());
                        }
                    }
                });

                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
                );
                buttonParams.setMargins(dp2px(4), 0, dp2px(4), 0);

                addBtn.setLayoutParams(buttonParams);
                removeSelectedBtn.setLayoutParams(buttonParams);
                clearAllBtn.setLayoutParams(buttonParams);

                buttonLayout.addView(addBtn);
                buttonLayout.addView(removeSelectedBtn);
                buttonLayout.addView(clearAllBtn);

                layout.addView(buttonLayout);

                View spacer2 = new View(activity);
                spacer2.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(16)
                ));
                layout.addView(spacer2);

                final ListView listView = new ListView(activity);
                final CustomArrayAdapter adapter = new CustomArrayAdapter(activity, 
                    android.R.layout.simple_list_item_multiple_choice, 
                    代管列表,
                    getTextColor()
                );
                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setDividerHeight(0);
                listView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(200)
                ));

                GradientDrawable listBg = new GradientDrawable();
                listBg.setColor(Color.parseColor(getSurfaceColor()));
                listBg.setCornerRadius(dp2px(8));
                listView.setBackground(listBg);

                layout.addView(listView);

                TextView bottomHint = new TextView(activity);
                bottomHint.setText("共 " + 代管列表.size() + " 个代管");
                bottomHint.setTextSize(12);
                bottomHint.setTextColor(Color.parseColor(getSubTextColor()));
                bottomHint.setGravity(Gravity.CENTER);
                bottomHint.setPadding(0, dp2px(12), 0, 0);
                layout.addView(bottomHint);

                mainLayout.addView(layout);

                builder.setView(mainLayout);
                builder.setNegativeButton("关闭", null);

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

            } catch (Exception e) {
                toast("打开代管管理失败: " + e.getMessage());
            }
        }
    });
}