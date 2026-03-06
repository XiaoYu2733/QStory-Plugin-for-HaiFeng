
// 你当然不会难过 你身边有很多人可以代替我 而我没有

public Object getFieldValue(Object 对象, String 字段名) {
    try {
        Field 字段 = 对象.getClass().getField(字段名);
        return 字段.get(对象);
    } catch (Exception e) {
        return null;
    }
}

public int getCurrentTheme() {
    try {
        Context 上下文 = getActivity();
        if (上下文 == null) return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        int 夜间模式 = 上下文.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (夜间模式 == Configuration.UI_MODE_NIGHT_YES) {
            return AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        } else {
            return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        }
    } catch (Exception e) {
        return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    }
}

public String getBackgroundColor() {
    int 主题 = getCurrentTheme();
    if (主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#1E1E1E";
    } else {
        return "#F8F9FA";
    }
}

public String getTextColor() {
    int 主题 = getCurrentTheme();
    if (主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#E9ECEF";
    } else {
        return "#212529";
    }
}

public String getSubTextColor() {
    int 主题 = getCurrentTheme();
    if (主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#ADB5BD";
    } else {
        return "#6C757D";
    }
}

public String getCardColor() {
    int 主题 = getCurrentTheme();
    if (主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#2D2D2D";
    } else {
        return "#FFFFFF";
    }
}

public String getAccentColor() {
    return "#4285F4";
}

public String getAccentColorDark() {
    return "#3367D6";
}

public String getSurfaceColor() {
    int 主题 = getCurrentTheme();
    if (主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#3C3C3C";
    } else {
        return "#E9ECEF";
    }
}

public String getBorderColor() {
    int 主题 = getCurrentTheme();
    if (主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#404040";
    } else {
        return "#DEE2E6";
    }
}

public int c(float 值) {
    return dp2px(值);
}

public int dp2px(float dp) {
    try {
        DisplayMetrics 指标 = new DisplayMetrics();
        Activity 活动 = getActivity();
        if (活动 != null) {
            活动.getWindowManager().getDefaultDisplay().getMetrics(指标);
            return (int) (dp * 指标.density + 0.5f);
        }
    } catch (Exception e) {}
    return (int) (dp * 3 + 0.5f);
}

public GradientDrawable getShape(String 颜色, int 圆角) {
    GradientDrawable 形状 = new GradientDrawable();
    形状.setColor(Color.parseColor(颜色));
    形状.setCornerRadius(圆角);
    形状.setShape(GradientDrawable.RECTANGLE);
    return 形状;
}

public GradientDrawable getWebShape(String 基色, int 圆角) {
    GradientDrawable 形状 = new GradientDrawable();
    形状.setColor(Color.parseColor(基色));
    形状.setCornerRadius(圆角);
    形状.setStroke(dp2px(1), Color.parseColor(getBorderColor()));
    形状.setShape(GradientDrawable.RECTANGLE);
    return 形状;
}

public void Toasts(String 文本) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    int 主题 = getCurrentTheme();
                    String 背景色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#2D2D2D" : "#4285F4";
                    String 文本色 = "#FFFFFF";
                    
                    LinearLayout 线性布局 = new LinearLayout(context);
                    线性布局.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    线性布局.setOrientation(LinearLayout.VERTICAL);
                    
                    int 水平内边距 = dp2px(16);
                    int 垂直内边距 = dp2px(12);
                    线性布局.setPadding(水平内边距, 垂直内边距, 水平内边距, 垂直内边距);
                    
                    线性布局.setBackground(getShape(背景色, dp2px(8)));
                    
                    TextView 文本视图 = new TextView(context);
                    文本视图.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    文本视图.setTextColor(Color.parseColor(文本色));
                    文本视图.setTextSize(14);
                    文本视图.setText(文本);
                    文本视图.setGravity(Gravity.CENTER);
                    
                    线性布局.addView(文本视图);
                    线性布局.setGravity(Gravity.CENTER);
                    
                    Toast 提示 = new Toast(context);
                    提示.setGravity(Gravity.TOP, 0, dp2px(80));
                    提示.setDuration(Toast.LENGTH_LONG);
                    提示.setView(线性布局);
                    提示.show();
                } else {
                    Toast.makeText(context, 文本, Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                Toast.makeText(context, 文本, Toast.LENGTH_LONG).show();
            }
        }
    });
}

class CustomArrayAdapter extends ArrayAdapter {
    private String 文本颜色;
    
    public CustomArrayAdapter(Context 上下文, int 资源, ArrayList<String> 对象, String 文本颜色) {
        super(上下文, 资源, 对象);
        this.文本颜色 = 文本颜色;
    }
    
    public View getView(int 位置, View 转换视图, ViewGroup 父视图) {
        View 视图 = super.getView(位置, 转换视图, 父视图);
        TextView 文本视图 = (TextView) 视图.findViewById(android.R.id.text1);
        文本视图.setTextColor(Color.parseColor(文本颜色));
        return 视图;
    }
}

void showMainMenu(final Activity 活动) {
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int 主题 = getCurrentTheme();
                AlertDialog.Builder 构建器 = new AlertDialog.Builder(活动, 主题);
                构建器.setTitle("配置执行任务");
                
                LinearLayout 布局 = new LinearLayout(活动);
                布局.setOrientation(LinearLayout.VERTICAL);
                布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                
                布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView 提示文本 = new TextView(活动);
                提示文本.setText("当前配置:\n点赞好友: " + 落叶叶子叶落子飘.size() + " 人\n续火好友: " + 落言花飘言落言.size() + " 人\n续火群组: " + 飘飘花言飘飘.size() + " 个");
                提示文本.setTextSize(14);
                提示文本.setTextColor(Color.parseColor(getTextColor()));
                提示文本.setPadding(0, 0, 0, dp2px(16));
                布局.addView(提示文本);
                
                View 间隔 = new View(活动);
                间隔.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(16)
                ));
                布局.addView(间隔);
                
                LinearLayout.LayoutParams 按钮参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(44)
                );
                按钮参数.setMargins(0, 0, 0, dp2px(8));
                
                String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                Button 点赞好友按钮 = new Button(活动);
                点赞好友按钮.setText("配置点赞好友");
                点赞好友按钮.setTextColor(Color.WHITE);
                点赞好友按钮.setBackground(getShape(强调色, dp2px(6)));
                点赞好友按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                点赞好友按钮.setLayoutParams(按钮参数);
                点赞好友按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configLikeFriends("", "", 0);
                    }
                });
                
                Button 续火好友按钮 = new Button(活动);
                续火好友按钮.setText("配置续火好友");
                续火好友按钮.setTextColor(Color.WHITE);
                续火好友按钮.setBackground(getShape(强调色, dp2px(6)));
                续火好友按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                续火好友按钮.setLayoutParams(按钮参数);
                续火好友按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFireFriends("", "", 0);
                    }
                });
                
                Button 续火群组按钮 = new Button(活动);
                续火群组按钮.setText("配置续火群组");
                续火群组按钮.setTextColor(Color.WHITE);
                续火群组按钮.setBackground(getShape(强调色, dp2px(6)));
                续火群组按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                续火群组按钮.setLayoutParams(按钮参数);
                续火群组按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFireGroups("", "", 0);
                    }
                });
                
                布局.addView(点赞好友按钮);
                布局.addView(续火好友按钮);
                布局.addView(续火群组按钮);
                
                View 间隔2 = new View(活动);
                间隔2.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(16)
                ));
                布局.addView(间隔2);
                
                TextView 快速标题 = new TextView(活动);
                快速标题.setText("快速操作");
                快速标题.setTextSize(15);
                快速标题.setTextColor(Color.parseColor(getTextColor()));
                快速标题.setTypeface(null, Typeface.BOLD);
                快速标题.setPadding(0, 0, 0, dp2px(8));
                布局.addView(快速标题);
                
                LinearLayout.LayoutParams 快速按钮参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(40)
                );
                快速按钮参数.setMargins(0, 0, 0, dp2px(6));
                
                Button 查看配置按钮 = new Button(活动);
                查看配置按钮.setText("查看配置详情");
                查看配置按钮.setTextColor(Color.WHITE);
                查看配置按钮.setBackground(getShape(强调色, dp2px(6)));
                查看配置按钮.setPadding(dp2px(12), dp2px(8), dp2px(12), dp2px(8));
                查看配置按钮.setLayoutParams(快速按钮参数);
                查看配置按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showConfigDetails(活动);
                    }
                });
                
                Button 导入配置按钮 = new Button(活动);
                导入配置按钮.setText("导入配置文件");
                导入配置按钮.setTextColor(Color.WHITE);
                导入配置按钮.setBackground(getShape(强调色, dp2px(6)));
                导入配置按钮.setPadding(dp2px(12), dp2px(8), dp2px(12), dp2px(8));
                导入配置按钮.setLayoutParams(快速按钮参数);
                导入配置按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        importConfig(活动);
                    }
                });
                
                Button 导出配置按钮 = new Button(活动);
                导出配置按钮.setText("导出配置文件");
                导出配置按钮.setTextColor(Color.WHITE);
                导出配置按钮.setBackground(getShape(强调色, dp2px(6)));
                导出配置按钮.setPadding(dp2px(12), dp2px(8), dp2px(12), dp2px(8));
                导出配置按钮.setLayoutParams(快速按钮参数);
                导出配置按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        exportConfig(活动);
                    }
                });
                
                布局.addView(查看配置按钮);
                布局.addView(导入配置按钮);
                布局.addView(导出配置按钮);
                
                ScrollView 滚动视图 = new ScrollView(活动);
                滚动视图.addView(布局);
                
                构建器.setView(滚动视图);
                构建器.setNegativeButton("关闭", null);
                
                AlertDialog 对话框 = 构建器.create();
                对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                对话框.show();
                
            } catch (Exception e) {
                Toasts("显示配置菜单失败: " + e.getMessage());
            }
        }
    });
}

void showConfigDetails(final Activity 活动) {
    int 主题 = getCurrentTheme();
    AlertDialog.Builder 对话框构建器 = new AlertDialog.Builder(活动, 主题);
    
    LinearLayout 布局 = new LinearLayout(活动);
    布局.setOrientation(LinearLayout.VERTICAL);
    布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
    布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
    
    TextView 标题视图 = new TextView(活动);
    标题视图.setText("配置详情");
    标题视图.setTextSize(17);
    标题视图.setTextColor(Color.parseColor(getTextColor()));
    标题视图.setGravity(Gravity.CENTER);
    标题视图.setPadding(0, 0, 0, dp2px(16));
    布局.addView(标题视图);
    
    ScrollView 滚动视图 = new ScrollView(活动);
    LinearLayout 内容布局 = new LinearLayout(活动);
    内容布局.setOrientation(LinearLayout.VERTICAL);
    
    TextView 点赞标题 = new TextView(活动);
    点赞标题.setText("点赞好友 (" + 落叶叶子叶落子飘.size() + "人):");
    点赞标题.setTextSize(15);
    点赞标题.setTextColor(Color.parseColor(getTextColor()));
    点赞标题.setPadding(0, dp2px(4), 0, dp2px(4));
    点赞标题.setTypeface(null, Typeface.BOLD);
    内容布局.addView(点赞标题);
    
    if (落叶叶子叶落子飘.isEmpty()) {
        TextView 空点赞 = new TextView(活动);
        空点赞.setText("未配置点赞好友");
        空点赞.setTextSize(14);
        空点赞.setTextColor(Color.parseColor(getSubTextColor()));
        空点赞.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        内容布局.addView(空点赞);
    } else {
        for (int i = 0; i < 落叶叶子叶落子飘.size(); i++) {
            TextView 点赞项 = new TextView(活动);
            点赞项.setText((i + 1) + ". " + 落叶叶子叶落子飘.get(i));
            点赞项.setTextSize(14);
            点赞项.setTextColor(Color.parseColor(getTextColor()));
            点赞项.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            内容布局.addView(点赞项);
        }
    }
    
    TextView 续火标题 = new TextView(活动);
    续火标题.setText("续火好友 (" + 落言花飘言落言.size() + "人):");
    续火标题.setTextSize(15);
    续火标题.setTextColor(Color.parseColor(getTextColor()));
    续火标题.setPadding(0, dp2px(12), 0, dp2px(4));
    续火标题.setTypeface(null, Typeface.BOLD);
    内容布局.addView(续火标题);
    
    if (落言花飘言落言.isEmpty()) {
        TextView 空续火 = new TextView(活动);
        空续火.setText("未配置续火好友");
        空续火.setTextSize(14);
        空续火.setTextColor(Color.parseColor(getSubTextColor()));
        空续火.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        内容布局.addView(空续火);
    } else {
        for (int i = 0; i < 落言花飘言落言.size(); i++) {
            TextView 续火项 = new TextView(活动);
            续火项.setText((i + 1) + ". " + 落言花飘言落言.get(i));
            续火项.setTextSize(14);
            续火项.setTextColor(Color.parseColor(getTextColor()));
            续火项.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            内容布局.addView(续火项);
        }
    }
    
    TextView 群组标题 = new TextView(活动);
    群组标题.setText("续火群组 (" + 飘飘花言飘飘.size() + "个):");
    群组标题.setTextSize(15);
    群组标题.setTextColor(Color.parseColor(getTextColor()));
    群组标题.setPadding(0, dp2px(12), 0, dp2px(4));
    群组标题.setTypeface(null, Typeface.BOLD);
    内容布局.addView(群组标题);
    
    if (飘飘花言飘飘.isEmpty()) {
        TextView 空群组 = new TextView(活动);
        空群组.setText("未配置续火群组");
        空群组.setTextSize(14);
        空群组.setTextColor(Color.parseColor(getSubTextColor()));
        空群组.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        内容布局.addView(空群组);
    } else {
        for (int i = 0; i < 飘飘花言飘飘.size(); i++) {
            TextView 群组项 = new TextView(活动);
            群组项.setText((i + 1) + ". " + 飘飘花言飘飘.get(i));
            群组项.setTextSize(14);
            群组项.setTextColor(Color.parseColor(getTextColor()));
            群组项.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            内容布局.addView(群组项);
        }
    }
    
    滚动视图.addView(内容布局);
    布局.addView(滚动视图);
    
    对话框构建器.setView(布局);
    对话框构建器.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface 对话框, int 选项) {
            对话框.dismiss();
        }
    });
    
    AlertDialog 对话框 = 对话框构建器.create();
    对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    对话框.show();
    
    WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
    布局参数.copyFrom(对话框.getWindow().getAttributes());
    布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
    布局参数.height = (int) (活动.getResources().getDisplayMetrics().heightPixels * 0.7);
    对话框.getWindow().setAttributes(布局参数);
}

void showWordsMenu(final Activity 活动) {
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int 主题 = getCurrentTheme();
                String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout 布局 = new LinearLayout(活动);
                布局.setOrientation(LinearLayout.VERTICAL);
                布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView 标题视图 = new TextView(活动);
                标题视图.setText("配置续火语录");
                标题视图.setTextSize(17);
                标题视图.setTextColor(Color.parseColor(getTextColor()));
                标题视图.setGravity(Gravity.CENTER);
                标题视图.setPadding(0, 0, 0, dp2px(16));
                布局.addView(标题视图);
                
                LinearLayout.LayoutParams 按钮参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(44)
                );
                按钮参数.setMargins(0, 0, 0, dp2px(8));
                
                Button 好友语录按钮 = new Button(活动);
                好友语录按钮.setText("配置好友续火语录");
                好友语录按钮.setTextColor(Color.WHITE);
                好友语录按钮.setBackground(getShape(强调色, dp2px(6)));
                好友语录按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                好友语录按钮.setLayoutParams(按钮参数);
                
                好友语录按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFriendFireWords("", "", 0);
                    }
                });
                
                Button 群组语录按钮 = new Button(活动);
                群组语录按钮.setText("配置群组续火语录");
                群组语录按钮.setTextColor(Color.WHITE);
                群组语录按钮.setBackground(getShape(强调色, dp2px(6)));
                群组语录按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                群组语录按钮.setLayoutParams(按钮参数);
                
                群组语录按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configGroupFireWords("", "", 0);
                    }
                });
                
                布局.addView(好友语录按钮);
                布局.addView(群组语录按钮);
                
                AlertDialog.Builder 构建器 = new AlertDialog.Builder(活动, 主题);
                构建器.setView(布局);
                构建器.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface 对话框, int 选项) {
                        对话框.dismiss();
                    }
                });
                
                AlertDialog 对话框 = 构建器.create();
                对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                对话框.show();
                
                WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
                布局参数.copyFrom(对话框.getWindow().getAttributes());
                布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
                布局参数.height = WindowManager.LayoutParams.WRAP_CONTENT;
                对话框.getWindow().setAttributes(布局参数);
                
                Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (取消按钮 != null) {
                    取消按钮.setTextColor(Color.parseColor(强调色));
                }
            } catch (Exception e) {
                Toasts("显示语录配置菜单失败: " + e.getMessage());
            }
        }
    });
}

void showTimeMenu(final Activity 活动) {
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int 主题 = getCurrentTheme();
                String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout 布局 = new LinearLayout(活动);
                布局.setOrientation(LinearLayout.VERTICAL);
                布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView 标题视图 = new TextView(活动);
                标题视图.setText("配置执行时间");
                标题视图.setTextSize(17);
                标题视图.setTextColor(Color.parseColor(getTextColor()));
                标题视图.setGravity(Gravity.CENTER);
                标题视图.setPadding(0, 0, 0, dp2px(16));
                布局.addView(标题视图);
                
                LinearLayout.LayoutParams 按钮参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(44)
                );
                按钮参数.setMargins(0, 0, 0, dp2px(8));
                
                Button 点赞时间按钮 = new Button(活动);
                点赞时间按钮.setText("配置好友点赞时间");
                点赞时间按钮.setTextColor(Color.WHITE);
                点赞时间按钮.setBackground(getShape(强调色, dp2px(6)));
                点赞时间按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                点赞时间按钮.setLayoutParams(按钮参数);
                
                点赞时间按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configLikeTime("", "", 0);
                    }
                });
                
                Button 好友续火时间按钮 = new Button(活动);
                好友续火时间按钮.setText("配置好友续火时间");
                好友续火时间按钮.setTextColor(Color.WHITE);
                好友续火时间按钮.setBackground(getShape(强调色, dp2px(6)));
                好友续火时间按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                好友续火时间按钮.setLayoutParams(按钮参数);
                
                好友续火时间按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFriendFireTime("", "", 0);
                    }
                });
                
                Button 群组续火时间按钮 = new Button(活动);
                群组续火时间按钮.setText("配置群组续火时间");
                群组续火时间按钮.setTextColor(Color.WHITE);
                群组续火时间按钮.setBackground(getShape(强调色, dp2px(6)));
                群组续火时间按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                群组续火时间按钮.setLayoutParams(按钮参数);
                
                群组续火时间按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configGroupFireTime("", "", 0);
                    }
                });
                
                布局.addView(点赞时间按钮);
                布局.addView(好友续火时间按钮);
                布局.addView(群组续火时间按钮);
                
                AlertDialog.Builder 构建器 = new AlertDialog.Builder(活动, 主题);
                构建器.setView(布局);
                构建器.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface 对话框, int 选项) {
                        对话框.dismiss();
                    }
                });
                
                AlertDialog 对话框 = 构建器.create();
                对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                对话框.show();
                
                WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
                布局参数.copyFrom(对话框.getWindow().getAttributes());
                布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
                布局参数.height = WindowManager.LayoutParams.WRAP_CONTENT;
                对话框.getWindow().setAttributes(布局参数);
                
                Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (取消按钮 != null) {
                    取消按钮.setTextColor(Color.parseColor(强调色));
                }
            } catch (Exception e) {
                Toasts("显示时间配置菜单失败: " + e.getMessage());
            }
        }
    });
}

void showExecuteMenu(final Activity 活动) {
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int 主题 = getCurrentTheme();
                String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout 布局 = new LinearLayout(活动);
                布局.setOrientation(LinearLayout.VERTICAL);
                布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView 标题视图 = new TextView(活动);
                标题视图.setText("立即执行任务");
                标题视图.setTextSize(17);
                标题视图.setTextColor(Color.parseColor(getTextColor()));
                标题视图.setGravity(Gravity.CENTER);
                标题视图.setPadding(0, 0, 0, dp2px(16));
                布局.addView(标题视图);
                
                LinearLayout.LayoutParams 按钮参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(44)
                );
                按钮参数.setMargins(0, 0, 0, dp2px(8));
                
                Button 点赞按钮 = new Button(活动);
                点赞按钮.setText("立即点赞好友");
                点赞按钮.setTextColor(Color.WHITE);
                点赞按钮.setBackground(getShape(强调色, dp2px(6)));
                点赞按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                点赞按钮.setLayoutParams(按钮参数);
                
                点赞按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateLike("", "", 0);
                    }
                });
                
                Button 好友续火按钮 = new Button(活动);
                好友续火按钮.setText("立即续火好友");
                好友续火按钮.setTextColor(Color.WHITE);
                好友续火按钮.setBackground(getShape(强调色, dp2px(6)));
                好友续火按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                好友续火按钮.setLayoutParams(按钮参数);
                
                好友续火按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateFriendFire("", "", 0);
                    }
                });
                
                Button 群组续火按钮 = new Button(活动);
                群组续火按钮.setText("立即续火群组");
                群组续火按钮.setTextColor(Color.WHITE);
                群组续火按钮.setBackground(getShape(强调色, dp2px(6)));
                群组续火按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                群组续火按钮.setLayoutParams(按钮参数);
                
                群组续火按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateGroupFire("", "", 0);
                    }
                });
                
                Button 全部按钮 = new Button(活动);
                全部按钮.setText("执行全部任务");
                全部按钮.setTextColor(Color.WHITE);
                全部按钮.setBackground(getShape(强调色, dp2px(6)));
                全部按钮.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                全部按钮.setLayoutParams(按钮参数);
                
                全部按钮.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        executeAllTasks();
                    }
                });
                
                布局.addView(点赞按钮);
                布局.addView(好友续火按钮);
                布局.addView(群组续火按钮);
                布局.addView(全部按钮);
                
                AlertDialog.Builder 构建器 = new AlertDialog.Builder(活动, 主题);
                构建器.setView(布局);
                构建器.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface 对话框, int 选项) {
                        对话框.dismiss();
                    }
                });
                
                AlertDialog 对话框 = 构建器.create();
                对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                对话框.show();
                
                WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
                布局参数.copyFrom(对话框.getWindow().getAttributes());
                布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
                布局参数.height = WindowManager.LayoutParams.WRAP_CONTENT;
                对话框.getWindow().setAttributes(布局参数);
    
                Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (取消按钮 != null) {
                    取消按钮.setTextColor(Color.parseColor(强调色));
                }
            } catch (Exception e) {
                Toasts("显示执行菜单失败: " + e.getMessage());
            }
        }
    });
}

public void configLikeFriends(String 群号, String 用户, int 类型){
    final Activity 活动 = getActivity();
    if (活动 == null) return;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList 好友列表 = getFriendList();
                if (好友列表 == null || 好友列表.isEmpty()) {
                    Toasts("未添加任何好友");
                    return;
                }
                
                final ArrayList 显示列表 = new ArrayList();
                final ArrayList QQ列表 = new ArrayList();
                
                for (int i = 0; i < 好友列表.size(); i++) {
                    Object 好友 = 好友列表.get(i);
                    String QQ = "";
                    String 昵称 = "";
                    String 备注 = "";
                    
                    try {
                        Object QQ对象 = getFieldValue(好友, "uin");
                        Object 昵称对象 = getFieldValue(好友, "name");
                        Object 备注对象 = getFieldValue(好友, "remark");
                        
                        if (QQ对象 != null) QQ = QQ对象.toString();
                        if (昵称对象 != null) 昵称 = 昵称对象.toString();
                        if (备注对象 != null) 备注 = 备注对象.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String 显示名 = (!备注.isEmpty() ? 备注 : 昵称) + " (" + QQ + ")";
                    显示列表.add(显示名);
                    QQ列表.add(QQ);
                }
                
                活动.runOnUiThread(new Runnable() {
                    public void run() {
                        showFriendSelectionDialog(活动, 显示列表, QQ列表, 落叶叶子叶落子飘, "点赞", "like");
                    }
                });
            } catch (Exception e) {
                Toasts("获取好友列表失败");
            }
        }
    }).start();
}

public void configFireFriends(String 群号, String 用户, int 类型){
    final Activity 活动 = getActivity();
    if (活动 == null) return;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList 好友列表 = getFriendList();
                if (好友列表 == null || 好友列表.isEmpty()) {
                    Toasts("未添加任何好友");
                    return;
                }
                
                final ArrayList 显示列表 = new ArrayList();
                final ArrayList QQ列表 = new ArrayList();
                
                for (int i = 0; i < 好友列表.size(); i++) {
                    Object 好友 = 好友列表.get(i);
                    String QQ = "";
                    String 昵称 = "";
                    String 备注 = "";
                    
                    try {
                        Object QQ对象 = getFieldValue(好友, "uin");
                        Object 昵称对象 = getFieldValue(好友, "name");
                        Object 备注对象 = getFieldValue(好友, "remark");
                        
                        if (QQ对象 != null) QQ = QQ对象.toString();
                        if (昵称对象 != null) 昵称 = 昵称对象.toString();
                        if (备注对象 != null) 备注 = 备注对象.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String 显示名 = (!备注.isEmpty() ? 备注 : 昵称) + " (" + QQ + ")";
                    显示列表.add(显示名);
                    QQ列表.add(QQ);
                }
                
                活动.runOnUiThread(new Runnable() {
                    public void run() {
                        showFriendSelectionDialog(活动, 显示列表, QQ列表, 落言花飘言落言, "续火", "fire");
                    }
                });
            } catch (Exception e) {
                Toasts("获取好友列表失败");
            }
        }
    }).start();
}

private void showFriendSelectionDialog(Activity 活动, ArrayList 显示列表, ArrayList QQ列表, 
                                     ArrayList 选中列表, String 任务名, String 配置类型) {
    
    if (活动 == null || 活动.isFinishing()) {
        Toasts("无法获取有效的Activity");
        return;
    }
    
    try {
        int 主题 = getCurrentTheme();
        AlertDialog.Builder 对话框构建器 = new AlertDialog.Builder(活动, 主题);
        
        LinearLayout 主布局 = new LinearLayout(活动);
        主布局.setOrientation(LinearLayout.VERTICAL);
        主布局.setPadding(dp2px(4), dp2px(4), dp2px(4), dp2px(4));
        
        final ArrayList 当前选中 = new ArrayList(选中列表);

        LinearLayout 内容布局 = new LinearLayout(活动);
        内容布局.setOrientation(LinearLayout.VERTICAL);
        内容布局.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
        内容布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
        
        TextView 标题视图 = new TextView(活动);
        标题视图.setText("选择" + 任务名 + "好友");
        标题视图.setTextColor(Color.parseColor(getTextColor()));
        标题视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        标题视图.setGravity(Gravity.CENTER);
        标题视图.setPadding(0, 0, 0, dp2px(16));
        内容布局.addView(标题视图);
        
        final EditText 搜索框 = new EditText(活动);
        搜索框.setHint("搜索好友QQ号、好友名、备注");
        搜索框.setTextColor(Color.parseColor(getTextColor()));
        搜索框.setHintTextColor(Color.parseColor(getSubTextColor()));
        搜索框.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
        搜索框.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
        内容布局.addView(搜索框);
        
        int 主题值 = getCurrentTheme();
        String 强调色 = 主题值 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
        
        LinearLayout 按钮布局 = new LinearLayout(活动);
        按钮布局.setOrientation(LinearLayout.HORIZONTAL);
        按钮布局.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams 按钮布局参数 = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        按钮布局参数.setMargins(0, dp2px(12), 0, dp2px(12));
        按钮布局.setLayoutParams(按钮布局参数);
        
        Button 全选按钮 = new Button(活动);
        全选按钮.setText("全选");
        全选按钮.setTextColor(Color.WHITE);
        全选按钮.setBackground(getShape(强调色, dp2px(6)));
        全选按钮.setPadding(dp2px(10), dp2px(8), dp2px(10), dp2px(8));
        LinearLayout.LayoutParams 全选参数 = new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        );
        全选参数.setMargins(dp2px(2), 0, dp2px(2), 0);
        全选按钮.setLayoutParams(全选参数);
        
        Button 全不选按钮 = new Button(活动);
        全不选按钮.setText("全不选");
        全不选按钮.setTextColor(Color.WHITE);
        全不选按钮.setBackground(getShape(强调色, dp2px(6)));
        全不选按钮.setPadding(dp2px(10), dp2px(8), dp2px(10), dp2px(8));
        LinearLayout.LayoutParams 全不选参数 = new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        );
        全不选参数.setMargins(dp2px(2), 0, dp2px(2), 0);
        全不选按钮.setLayoutParams(全不选参数);
        
        Button 反选按钮 = new Button(活动);
        反选按钮.setText("反选");
        反选按钮.setTextColor(Color.WHITE);
        反选按钮.setBackground(getShape(强调色, dp2px(6)));
        反选按钮.setPadding(dp2px(10), dp2px(8), dp2px(10), dp2px(8));
        LinearLayout.LayoutParams 反选参数 = new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        );
        反选参数.setMargins(dp2px(2), 0, dp2px(2), 0);
        反选按钮.setLayoutParams(反选参数);
        
        按钮布局.addView(全选按钮);
        按钮布局.addView(全不选按钮);
        按钮布局.addView(反选按钮);
        内容布局.addView(按钮布局);
        
        final ListView 列表视图 = new ListView(活动);
        列表视图.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
        列表视图.setDividerHeight(dp2px(1));
        LinearLayout.LayoutParams 列表参数 = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        );
        列表参数.weight = 1;
        列表视图.setLayoutParams(列表参数);
        内容布局.addView(列表视图);
        
        主布局.addView(内容布局);
        
        final ArrayList 过滤显示列表 = new ArrayList(显示列表);
        final ArrayList 过滤QQ列表 = new ArrayList(QQ列表);
        
        final CustomArrayAdapter 适配器 = new CustomArrayAdapter(活动, android.R.layout.simple_list_item_multiple_choice, 过滤显示列表, getTextColor());
        
        列表视图.setAdapter(适配器);
        列表视图.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        for (int i = 0; i < 过滤QQ列表.size(); i++) {
            String QQ = (String)过滤QQ列表.get(i);
            列表视图.setItemChecked(i, 当前选中.contains(QQ));
        }

        列表视图.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView 父视图, View 视图, int 位置, long id) {
                String QQ = (String) 过滤QQ列表.get(位置);
                boolean 选中状态 = 列表视图.isItemChecked(位置);
                if (选中状态) {
                    if (!当前选中.contains(QQ)) {
                        当前选中.add(QQ);
                    }
                } else {
                    当前选中.remove(QQ);
                }
            }
        });
        
        搜索框.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int 开始, int 数量, int 后) {}
            public void onTextChanged(CharSequence s, int 开始, int 前, int 数量) {
                String 搜索文本 = s.toString().toLowerCase().trim();
                过滤显示列表.clear();
                过滤QQ列表.clear();
                
                if (搜索文本.isEmpty()) {
                    过滤显示列表.addAll(显示列表);
                    过滤QQ列表.addAll(QQ列表);
                } else {
                    for (int i = 0; i < 显示列表.size(); i++) {
                        String 显示名 = ((String)显示列表.get(i)).toLowerCase();
                        String QQ = (String)QQ列表.get(i);
                        
                        if (显示名.contains(搜索文本) || QQ.contains(搜索文本)) {
                            过滤显示列表.add(显示列表.get(i));
                            过滤QQ列表.add(QQ列表.get(i));
                        }
                    }
                }
                
                适配器.notifyDataSetChanged();
                
                for (int i = 0; i < 过滤QQ列表.size(); i++) {
                    String QQ = (String)过滤QQ列表.get(i);
                    列表视图.setItemChecked(i, 当前选中.contains(QQ));
                }
            }
        });
        
        全选按钮.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < 过滤QQ列表.size(); i++) {
                    列表视图.setItemChecked(i, true);
                    String QQ = (String) 过滤QQ列表.get(i);
                    if (!当前选中.contains(QQ)) {
                        当前选中.add(QQ);
                    }
                }
            }
        });
        
        全不选按钮.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < 过滤QQ列表.size(); i++) {
                    列表视图.setItemChecked(i, false);
                    String QQ = (String) 过滤QQ列表.get(i);
                    当前选中.remove(QQ);
                }
            }
        });
        
        反选按钮.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < 过滤QQ列表.size(); i++) {
                    String QQ = (String) 过滤QQ列表.get(i);
                    boolean 当前选中状态 = 当前选中.contains(QQ);
                    boolean 新选中状态 = !当前选中状态;
                    列表视图.setItemChecked(i, 新选中状态);
                    if (新选中状态) {
                        if (!当前选中.contains(QQ)) {
                            当前选中.add(QQ);
                        }
                    } else {
                        当前选中.remove(QQ);
                    }
                }
            }
        });
        
        对话框构建器.setView(主布局);
        
        对话框构建器.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface 对话框, int 选项) {
                选中列表.clear();
                选中列表.addAll(当前选中);
                
                if (配置类型.equals("like")) {
                    saveLikeFriends();
                    Toasts("已选择" + 选中列表.size() + "位点赞好友");
                } else if (配置类型.equals("fire")) {
                    saveFireFriends();
                    Toasts("已选择" + 选中列表.size() + "位续火好友");
                }
                
                checkAndExecuteTasks();
            }
        });
        
        对话框构建器.setNegativeButton("取消", null);
        
        AlertDialog 对话框 = 对话框构建器.create();
        对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        对话框.show();
        
        WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
        布局参数.copyFrom(对话框.getWindow().getAttributes());
        布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
        布局参数.height = (int) (活动.getResources().getDisplayMetrics().heightPixels * 0.8);
        对话框.getWindow().setAttributes(布局参数);
        
        Button 确定按钮 = 对话框.getButton(DialogInterface.BUTTON_POSITIVE);
        if (确定按钮 != null) {
            确定按钮.setTextColor(Color.parseColor(强调色));
        }
        Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (取消按钮 != null) {
            取消按钮.setTextColor(Color.parseColor(强调色));
        }
        
    } catch (Exception e) {
        Toasts("打开选择对话框失败: " + e.getMessage());
    }
}

public void configFireGroups(String 群号, String 用户, int 类型){
    final Activity 活动 = getActivity();
    if (活动 == null) return;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList 群组列表 = getGroupList();
                if (群组列表 == null || 群组列表.isEmpty()) {
                    Toasts("未加入任何群组");
                    return;
                }
                
                final ArrayList 显示列表 = new ArrayList();
                final ArrayList QQ列表 = new ArrayList();
                for (int i = 0; i < 群组列表.size(); i++) {
                    Object 群组 = 群组列表.get(i);
                    String 群名 = "";
                    String 群号 = "";
                    try {
                        Object 群名对象 = getFieldValue(群组, "GroupName");
                        Object 群号对象 = getFieldValue(群组, "GroupUin");
                        
                        if (群名对象 != null) 群名 = 群名对象.toString();
                        if (群号对象 != null) 群号 = 群号对象.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String 显示名 = 群名 + " (" + 群号 + ")";
                    显示列表.add(显示名);
                    QQ列表.add(群号);
                }
                
                活动.runOnUiThread(new Runnable() {
                    public void run() {
                        showGroupSelectionDialog(活动, 显示列表, QQ列表);
                    }
                });
            } catch (Exception e) {
                Toasts("获取群组列表失败");
            }
        }
    }).start();
}

private void showGroupSelectionDialog(Activity 活动, ArrayList 显示列表, ArrayList QQ列表) {
    if (活动 == null || 活动.isFinishing()) {
        Toasts("无法获取有效的Activity");
        return;
    }
    
    try {
        int 主题 = getCurrentTheme();
        String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
        
        AlertDialog.Builder 对话框构建器 = new AlertDialog.Builder(活动, 主题);
        
        LinearLayout 主布局 = new LinearLayout(活动);
        主布局.setOrientation(LinearLayout.VERTICAL);
        主布局.setPadding(dp2px(4), dp2px(4), dp2px(4), dp2px(4));
        
        final ArrayList 当前选中 = new ArrayList(飘飘花言飘飘);
        
        LinearLayout 内容布局 = new LinearLayout(活动);
        内容布局.setOrientation(LinearLayout.VERTICAL);
        内容布局.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
        内容布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
        
        TextView 标题视图 = new TextView(活动);
        标题视图.setText("选择续火群组");
        标题视图.setTextColor(Color.parseColor(getTextColor()));
        标题视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        标题视图.setGravity(Gravity.CENTER);
        标题视图.setPadding(0, 0, 0, dp2px(16));
        内容布局.addView(标题视图);
        
        final EditText 搜索框 = new EditText(活动);
        搜索框.setHint("搜索群号、群名");
        搜索框.setTextColor(Color.parseColor(getTextColor()));
        搜索框.setHintTextColor(Color.parseColor(getSubTextColor()));
        搜索框.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
        搜索框.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
        内容布局.addView(搜索框);
        
        LinearLayout 按钮布局 = new LinearLayout(活动);
        按钮布局.setOrientation(LinearLayout.HORIZONTAL);
        按钮布局.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams 按钮布局参数 = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        按钮布局参数.setMargins(0, dp2px(12), 0, dp2px(12));
        按钮布局.setLayoutParams(按钮布局参数);
        
        Button 全选按钮 = new Button(活动);
        全选按钮.setText("全选");
        全选按钮.setTextColor(Color.WHITE);
        全选按钮.setBackground(getShape(强调色, dp2px(6)));
        全选按钮.setPadding(dp2px(10), dp2px(8), dp2px(10), dp2px(8));
        LinearLayout.LayoutParams 全选参数 = new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        );
        全选参数.setMargins(dp2px(2), 0, dp2px(2), 0);
        全选按钮.setLayoutParams(全选参数);
        
        Button 全不选按钮 = new Button(活动);
        全不选按钮.setText("全不选");
        全不选按钮.setTextColor(Color.WHITE);
        全不选按钮.setBackground(getShape(强调色, dp2px(6)));
        全不选按钮.setPadding(dp2px(10), dp2px(8), dp2px(10), dp2px(8));
        LinearLayout.LayoutParams 全不选参数 = new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        );
        全不选参数.setMargins(dp2px(2), 0, dp2px(2), 0);
        全不选按钮.setLayoutParams(全不选参数);
        
        Button 反选按钮 = new Button(活动);
        反选按钮.setText("反选");
        反选按钮.setTextColor(Color.WHITE);
        反选按钮.setBackground(getShape(强调色, dp2px(6)));
        反选按钮.setPadding(dp2px(10), dp2px(8), dp2px(10), dp2px(8));
        LinearLayout.LayoutParams 反选参数 = new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        );
        反选参数.setMargins(dp2px(2), 0, dp2px(2), 0);
        反选按钮.setLayoutParams(反选参数);
        
        按钮布局.addView(全选按钮);
        按钮布局.addView(全不选按钮);
        按钮布局.addView(反选按钮);
        内容布局.addView(按钮布局);
        
        final ListView 列表视图 = new ListView(活动);
        列表视图.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
        列表视图.setDividerHeight(dp2px(1));
        LinearLayout.LayoutParams 列表参数 = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        );
        列表参数.weight = 1;
        列表视图.setLayoutParams(列表参数);
        内容布局.addView(列表视图);
        
        主布局.addView(内容布局);
        
        final ArrayList 过滤显示列表 = new ArrayList(显示列表);
        final ArrayList 过滤QQ列表 = new ArrayList(QQ列表);
        
        final CustomArrayAdapter 适配器 = new CustomArrayAdapter(活动, android.R.layout.simple_list_item_multiple_choice, 过滤显示列表, getTextColor());
        列表视图.setAdapter(适配器);
        列表视图.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        for (int i = 0; i < 过滤QQ列表.size(); i++) {
            String QQ = (String)过滤QQ列表.get(i);
            列表视图.setItemChecked(i, 当前选中.contains(QQ));
        }

        列表视图.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView 父视图, View 视图, int 位置, long id) {
                String QQ = (String) 过滤QQ列表.get(位置);
                boolean 选中状态 = 列表视图.isItemChecked(位置);
                if (选中状态) {
                    if (!当前选中.contains(QQ)) {
                        当前选中.add(QQ);
                    }
                } else {
                    当前选中.remove(QQ);
                }
            }
        });
        
        搜索框.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int 开始, int 数量, int 后) {}
            public void onTextChanged(CharSequence s, int 开始, int 前, int 数量) {
                String 搜索文本 = s.toString().toLowerCase().trim();
                过滤显示列表.clear();
                过滤QQ列表.clear();
                
                if (搜索文本.isEmpty()) {
                    过滤显示列表.addAll(显示列表);
                    过滤QQ列表.addAll(QQ列表);
                } else {
                    for (int i = 0; i < 显示列表.size(); i++) {
                        String 显示名 = ((String)显示列表.get(i)).toLowerCase();
                        String QQ = (String)QQ列表.get(i);
                        
                        if (显示名.contains(搜索文本) || QQ.contains(搜索文本)) {
                            过滤显示列表.add(显示列表.get(i));
                            过滤QQ列表.add(QQ列表.get(i));
                        }
                    }
                }
                
                适配器.notifyDataSetChanged();
                
                for (int i = 0; i < 过滤QQ列表.size(); i++) {
                    String QQ = (String)过滤QQ列表.get(i);
                    列表视图.setItemChecked(i, 当前选中.contains(QQ));
                }
            }
        });
        
        全选按钮.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < 过滤QQ列表.size(); i++) {
                    列表视图.setItemChecked(i, true);
                    String QQ = (String) 过滤QQ列表.get(i);
                    if (!当前选中.contains(QQ)) {
                        当前选中.add(QQ);
                    }
                }
            }
        });
        
        全不选按钮.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < 过滤QQ列表.size(); i++) {
                    列表视图.setItemChecked(i, false);
                    String QQ = (String) 过滤QQ列表.get(i);
                    当前选中.remove(QQ);
                }
            }
        });
        
        反选按钮.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < 过滤QQ列表.size(); i++) {
                    String QQ = (String) 过滤QQ列表.get(i);
                    boolean 当前选中状态 = 当前选中.contains(QQ);
                    boolean 新选中状态 = !当前选中状态;
                    列表视图.setItemChecked(i, 新选中状态);
                    if (新选中状态) {
                        if (!当前选中.contains(QQ)) {
                            当前选中.add(QQ);
                        }
                    } else {
                        当前选中.remove(QQ);
                    }
                }
            }
        });
        
        对话框构建器.setView(主布局);
        
        对话框构建器.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface 对话框, int 选项) {
                飘飘花言飘飘.clear();
                飘飘花言飘飘.addAll(当前选中);
                
                saveFireGroups();
                Toasts("已选择" + 飘飘花言飘飘.size() + "个续火群组");
                
                checkAndExecuteTasks();
            }
        });
        
        对话框构建器.setNegativeButton("取消", null);
        
        AlertDialog 对话框 = 对话框构建器.create();
        对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        对话框.show();
        
        WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
        布局参数.copyFrom(对话框.getWindow().getAttributes());
        布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
        布局参数.height = (int) (活动.getResources().getDisplayMetrics().heightPixels * 0.8);
        对话框.getWindow().setAttributes(布局参数);
        
        Button 确定按钮 = 对话框.getButton(DialogInterface.BUTTON_POSITIVE);
        if (确定按钮 != null) {
            确定按钮.setTextColor(Color.parseColor(强调色));
        }
        Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (取消按钮 != null) {
            取消按钮.setTextColor(Color.parseColor(强调色));
        }
        
    } catch (Exception e) {
        Toasts("打开选择对话框失败: " + e.getMessage());
    }
}

public void configFriendFireWords(String 群号, String 用户, int 类型){
    final Activity 活动 = getActivity();
    if (活动 == null) {
        Toasts("无法获取Activity");
        return;
    }
    
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder 语录构建器 = new StringBuilder();
                for (int i = 0; i < 飘飘叶飘.size(); i++) {
                    if (语录构建器.length() > 0) 语录构建器.append("\n");
                    语录构建器.append((String)飘飘叶飘.get(i));
                }
                
                int 主题 = getCurrentTheme();
                String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout 布局 = new LinearLayout(活动);
                布局.setOrientation(LinearLayout.VERTICAL);
                布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView 标题视图 = new TextView(活动);
                标题视图.setText("配置好友续火语录");
                标题视图.setTextColor(Color.parseColor(getTextColor()));
                标题视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                标题视图.setGravity(Gravity.CENTER);
                标题视图.setPadding(0, 0, 0, dp2px(16));
                布局.addView(标题视图);
                
                final EditText 语录编辑框 = new EditText(活动);
                语录编辑框.setText(语录构建器.toString());
                语录编辑框.setHint("输入好友续火语录，每行一个");
                语录编辑框.setTextColor(Color.parseColor(getTextColor()));
                语录编辑框.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                语录编辑框.setHintTextColor(Color.parseColor(getSubTextColor()));
                语录编辑框.setMinLines(6);
                语录编辑框.setMaxLines(20);
                语录编辑框.setGravity(Gravity.TOP);
                语录编辑框.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
                语录编辑框.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                
                int 屏幕高度 = 活动.getResources().getDisplayMetrics().heightPixels;
                int 编辑框高度 = (int)(屏幕高度 * 0.35);
                LinearLayout.LayoutParams 编辑框参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    编辑框高度
                );
                编辑框参数.setMargins(0, 0, 0, dp2px(16));
                语录编辑框.setLayoutParams(编辑框参数);
                
                布局.addView(语录编辑框);
                
                TextView 提示视图 = new TextView(活动);
                提示视图.setText("注意：输入多个续火语录时，每行一个");
                提示视图.setTextColor(Color.parseColor(getSubTextColor()));
                提示视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                提示视图.setPadding(0, 0, 0, 0);
                布局.addView(提示视图);
                
                AlertDialog.Builder 对话框构建器 = new AlertDialog.Builder(活动, 主题);
                对话框构建器.setView(布局);
                对话框构建器.setCancelable(true);
                
                对话框构建器.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface 对话框, int 选项) {
                        String 语录文本 = 语录编辑框.getText().toString().trim();
                        if (语录文本.isEmpty()) {
                            Toasts("续火语录不能为空");
                            return;
                        }
                        
                        飘飘叶飘.clear();
                        String[] 语录数组 = 语录文本.split("\n");
                        for (int i = 0; i < 语录数组.length; i++) {
                            String 语录 = 语录数组[i].trim();
                            if (!语录.isEmpty()) {
                                飘飘叶飘.add(语录);
                            }
                        }
                        
                        if (飘飘叶飘.isEmpty()) {
                            Toasts("未添加有效的续火语录");
                            return;
                        }
                        
                        saveListToFile(落叶花花飘言子子飘花, 飘飘叶飘);
                        Toasts("已保存 " + 飘飘叶飘.size() + " 个好友续火语录");
                    }
                });
                
                对话框构建器.setNegativeButton("取消", null);
                
                AlertDialog 对话框 = 对话框构建器.create();
                对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                对话框.show();
                
                WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
                布局参数.copyFrom(对话框.getWindow().getAttributes());
                布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
                布局参数.height = WindowManager.LayoutParams.WRAP_CONTENT;
                对话框.getWindow().setAttributes(布局参数);
                
                对话框.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
                
                Button 保存按钮 = 对话框.getButton(DialogInterface.BUTTON_POSITIVE);
                if (保存按钮 != null) {
                    保存按钮.setTextColor(Color.parseColor(强调色));
                }
                Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (取消按钮 != null) {
                    取消按钮.setTextColor(Color.parseColor(强调色));
                }
            } catch (Exception e) {
            }
        }
    });
}

public void configGroupFireWords(String 群号, String 用户, int 类型){
    final Activity 活动 = getActivity();
    if (活动 == null) {
        Toasts("无法获取Activity");
        return;
    }
    
    活动.runOnUiThread(new Runnable() {
        public void run() {
            try {
                StringBuilder 语录构建器 = new StringBuilder();
                for (int i = 0; i < 叶落花落.size(); i++) {
                    if (语录构建器.length() > 0) 语录构建器.append("\n");
                    语录构建器.append((String)叶落花落.get(i));
                }
                
                int 主题 = getCurrentTheme();
                String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout 布局 = new LinearLayout(活动);
                布局.setOrientation(LinearLayout.VERTICAL);
                布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView 标题视图 = new TextView(活动);
                标题视图.setText("配置群组续火语录");
                标题视图.setTextColor(Color.parseColor(getTextColor()));
                标题视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                标题视图.setGravity(Gravity.CENTER);
                标题视图.setPadding(0, 0, 0, dp2px(16));
                布局.addView(标题视图);
                
                final EditText 语录编辑框 = new EditText(活动);
                语录编辑框.setText(语录构建器.toString());
                语录编辑框.setHint("输入群组续火语录，每行一个");
                语录编辑框.setTextColor(Color.parseColor(getTextColor()));
                语录编辑框.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                语录编辑框.setHintTextColor(Color.parseColor(getSubTextColor()));
                语录编辑框.setMinLines(6);
                语录编辑框.setMaxLines(20);
                语录编辑框.setGravity(Gravity.TOP);
                语录编辑框.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
                语录编辑框.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                
                int 屏幕高度 = 活动.getResources().getDisplayMetrics().heightPixels;
                int 编辑框高度 = (int)(屏幕高度 * 0.35);
                LinearLayout.LayoutParams 编辑框参数 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    编辑框高度
                );
                编辑框参数.setMargins(0, 0, 0, dp2px(16));
                语录编辑框.setLayoutParams(编辑框参数);
                
                布局.addView(语录编辑框);
                
                TextView 提示视图 = new TextView(活动);
                提示视图.setText("注意：输入多个续火语录时，每行一个");
                提示视图.setTextColor(Color.parseColor(getSubTextColor()));
                提示视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                提示视图.setPadding(0, 0, 0, 0);
                布局.addView(提示视图);
                
                AlertDialog.Builder 对话框构建器 = new AlertDialog.Builder(活动, 主题);
                对话框构建器.setView(布局);
                对话框构建器.setCancelable(true);
                
                对话框构建器.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface 对话框, int 选项) {
                        String 语录文本 = 语录编辑框.getText().toString().trim();
                        if (语录文本.isEmpty()) {
                            Toasts("续火语录不能为空");
                            return;
                        }
                        
                        叶落花落.clear();
                        String[] 语录数组 = 语录文本.split("\n");
                        for (int i = 0; i < 语录数组.length; i++) {
                            String 语录 = 语录数组[i].trim();
                            if (!语录.isEmpty()) {
                                叶落花落.add(语录);
                            }
                        }
                        
                        if (叶落花落.isEmpty()) {
                            Toasts("未添加有效的续火语录");
                            return;
                        }
                        
                        saveListToFile(子叶花花花飘, 叶落花落);
                        Toasts("已保存 " + 叶落花落.size() + " 个群组续火语录");
                    }
                });
                
                对话框构建器.setNegativeButton("取消", null);
                
                AlertDialog 对话框 = 对话框构建器.create();
                对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                对话框.show();
    
                WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
                布局参数.copyFrom(对话框.getWindow().getAttributes());
                布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
                布局参数.height = WindowManager.LayoutParams.WRAP_CONTENT;
                对话框.getWindow().setAttributes(布局参数);
                
                对话框.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    
                Button 保存按钮 = 对话框.getButton(DialogInterface.BUTTON_POSITIVE);
                if (保存按钮 != null) {
                    保存按钮.setTextColor(Color.parseColor(强调色));
                }
                Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (取消按钮 != null) {
                    取消按钮.setTextColor(Color.parseColor(强调色));
                }
            } catch (Exception e) {
            }
        }
    });
}

public void configLikeTime(String 群号, String 用户, int 类型) {
    final Activity 活动 = getActivity();
    if (活动 == null) return;
    
    活动.runOnUiThread(new Runnable() {
        public void run() {
            int 主题 = getCurrentTheme();
            String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
            
            AlertDialog.Builder 对话框构建器 = new AlertDialog.Builder(活动, 主题);
            
            LinearLayout 布局 = new LinearLayout(活动);
            布局.setOrientation(LinearLayout.VERTICAL);
            布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
            布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
            
            TextView 标题视图 = new TextView(活动);
            标题视图.setText("设置点赞时间 (HH:mm)");
            标题视图.setTextColor(Color.parseColor(getTextColor()));
            标题视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            标题视图.setGravity(Gravity.CENTER);
            标题视图.setPadding(0, 0, 0, dp2px(16));
            布局.addView(标题视图);
            
            final EditText 时间编辑框 = new EditText(活动);
            时间编辑框.setText(叶飘叶落言叶子叶落子);
            时间编辑框.setHint("例如: 00:00");
            时间编辑框.setTextColor(Color.parseColor(getTextColor()));
            时间编辑框.setHintTextColor(Color.parseColor(getSubTextColor()));
            时间编辑框.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
            时间编辑框.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            布局.addView(时间编辑框);
            
            对话框构建器.setView(布局);
            
            对话框构建器.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface 对话框, int 选项) {
                    String 时间文本 = 时间编辑框.getText().toString().trim();
                    if (时间文本.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        叶飘叶落言叶子叶落子 = 时间文本;
                        saveTimeConfig();
                        Toasts("已设置点赞时间: " + 叶飘叶落言叶子叶落子);
                        checkAndExecuteTasks();
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            对话框构建器.setNegativeButton("取消", null);
            
            AlertDialog 对话框 = 对话框构建器.create();
            对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            对话框.show();
            
            WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
            布局参数.copyFrom(对话框.getWindow().getAttributes());
            布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
            布局参数.height = WindowManager.LayoutParams.WRAP_CONTENT;
            对话框.getWindow().setAttributes(布局参数);
            
            对话框.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            
            Button 保存按钮 = 对话框.getButton(DialogInterface.BUTTON_POSITIVE);
            if (保存按钮 != null) {
                保存按钮.setTextColor(Color.parseColor(强调色));
            }
            Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (取消按钮 != null) {
                取消按钮.setTextColor(Color.parseColor(强调色));
            }
        }
    });
}

public void configFriendFireTime(String 群号, String 用户, int 类型) {
    final Activity 活动 = getActivity();
    if (活动 == null) return;
    
    活动.runOnUiThread(new Runnable() {
        public void run() {
            int 主题 = getCurrentTheme();
            String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
            
            AlertDialog.Builder 对话框构建器 = new AlertDialog.Builder(活动, 主题);
            
            LinearLayout 布局 = new LinearLayout(活动);
            布局.setOrientation(LinearLayout.VERTICAL);
            布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
            布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
            
            TextView 标题视图 = new TextView(活动);
            标题视图.setText("设置好友续火时间 (HH:mm)");
            标题视图.setTextColor(Color.parseColor(getTextColor()));
            标题视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            标题视图.setGravity(Gravity.CENTER);
            标题视图.setPadding(0, 0, 0, dp2px(16));
            布局.addView(标题视图);
            
            final EditText 时间编辑框 = new EditText(活动);
            时间编辑框.setText(飘飘花花);
            时间编辑框.setHint("例如: 00:00");
            时间编辑框.setTextColor(Color.parseColor(getTextColor()));
            时间编辑框.setHintTextColor(Color.parseColor(getSubTextColor()));
            时间编辑框.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
            时间编辑框.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            布局.addView(时间编辑框);
            
            对话框构建器.setView(布局);
            
            对话框构建器.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface 对话框, int 选项) {
                    String 时间文本 = 时间编辑框.getText().toString().trim();
                    if (时间文本.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        飘飘花花 = 时间文本;
                        saveTimeConfig();
                        Toasts("已设置好友续火时间: " + 飘飘花花);
                        checkAndExecuteTasks();
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            对话框构建器.setNegativeButton("取消", null);
    
            AlertDialog 对话框 = 对话框构建器.create();
            对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            对话框.show();
            
            WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
            布局参数.copyFrom(对话框.getWindow().getAttributes());
            布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
            布局参数.height = WindowManager.LayoutParams.WRAP_CONTENT;
            对话框.getWindow().setAttributes(布局参数);
            
            对话框.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            
            Button 保存按钮 = 对话框.getButton(DialogInterface.BUTTON_POSITIVE);
            if (保存按钮 != null) {
                保存按钮.setTextColor(Color.parseColor(强调色));
            }
            Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (取消按钮 != null) {
                取消按钮.setTextColor(Color.parseColor(强调色));
            }
        }
    });
}

public void configGroupFireTime(String 群号, String 用户, int 类型) {
    final Activity 活动 = getActivity();
    if (活动 == null) return;
    
    活动.runOnUiThread(new Runnable() {
        public void run() {
            int 主题 = getCurrentTheme();
            String 强调色 = 主题 == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
            
            AlertDialog.Builder 对话框构建器 = new AlertDialog.Builder(活动, 主题);
            
            LinearLayout 布局 = new LinearLayout(活动);
            布局.setOrientation(LinearLayout.VERTICAL);
            布局.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
            布局.setBackground(getWebShape(getCardColor(), dp2px(8)));
            
            TextView 标题视图 = new TextView(活动);
            标题视图.setText("设置群组续火时间 (HH:mm)");
            标题视图.setTextColor(Color.parseColor(getTextColor()));
            标题视图.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            标题视图.setGravity(Gravity.CENTER);
            标题视图.setPadding(0, 0, 0, dp2px(16));
            布局.addView(标题视图);
            
            final EditText 时间编辑框 = new EditText(活动);
            时间编辑框.setText(子言花言飘叶落飘);
            时间编辑框.setHint("例如: 00:00");
            时间编辑框.setTextColor(Color.parseColor(getTextColor()));
            时间编辑框.setHintTextColor(Color.parseColor(getSubTextColor()));
            时间编辑框.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
            时间编辑框.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            布局.addView(时间编辑框);
            
            对话框构建器.setView(布局);
            
            对话框构建器.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface 对话框, int 选项) {
                    String 时间文本 = 时间编辑框.getText().toString().trim();
                    if (时间文本.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        子言花言飘叶落飘 = 时间文本;
                        saveTimeConfig();
                        Toasts("已设置群组续火时间: " + 子言花言飘叶落飘);
                        checkAndExecuteTasks();
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
                }
            });
            
            对话框构建器.setNegativeButton("取消", null);
            
            AlertDialog 对话框 = 对话框构建器.create();
            对话框.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            对话框.show();
            
            WindowManager.LayoutParams 布局参数 = new WindowManager.LayoutParams();
            布局参数.copyFrom(对话框.getWindow().getAttributes());
            布局参数.width = (int) (活动.getResources().getDisplayMetrics().widthPixels * 0.9);
            布局参数.height = WindowManager.LayoutParams.WRAP_CONTENT;
            对话框.getWindow().setAttributes(布局参数);
            
            对话框.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            
            Button 保存按钮 = 对话框.getButton(DialogInterface.BUTTON_POSITIVE);
            if (保存按钮 != null) {
                保存按钮.setTextColor(Color.parseColor(强调色));
            }
            Button 取消按钮 = 对话框.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (取消按钮 != null) {
                取消按钮.setTextColor(Color.parseColor(强调色));
            }
        }
    });
}