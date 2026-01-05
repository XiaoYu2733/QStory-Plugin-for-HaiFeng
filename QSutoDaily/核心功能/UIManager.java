
// 你当然不会难过 你身边有很多人可以代替我 而我没有

public Object getFieldValue(Object obj, String fieldName) {
    try {
        Field field = obj.getClass().getField(fieldName);
        return field.get(obj);
    } catch (Exception e) {
        return null;
    }
}

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
        return "#ADB5BD";
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
    return "#4285F4";
}

public String getAccentColorDark() {
    return "#3367D6";
}

public String getSurfaceColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#3C3C3C";
    } else {
        return "#E9ECEF";
    }
}

public String getBorderColor() {
    int theme = getCurrentTheme();
    if (theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
        return "#404040";
    } else {
        return "#DEE2E6";
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

public void Toasts(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    int theme = getCurrentTheme();
                    String bgColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? "#2D2D2D" : "#4285F4";
                    String textColor = "#FFFFFF";
                    
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    
                    int paddingHorizontal = dp2px(16);
                    int paddingVertical = dp2px(12);
                    linearLayout.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                    
                    linearLayout.setBackground(getShape(bgColor, dp2px(8)));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(textColor));
                    textView.setTextSize(14);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
                    linearLayout.addView(textView);
                    linearLayout.setGravity(Gravity.CENTER);
                    
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.TOP, 0, dp2px(80));
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
                
                layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView hint = new TextView(activity);
                hint.setText("当前配置:\n点赞好友: " + selectedFriendsForLike.size() + " 人\n续火好友: " + selectedFriendsForFire.size() + " 人\n续火群组: " + selectedGroupsForFire.size() + " 个");
                hint.setTextSize(14);
                hint.setTextColor(Color.parseColor(getTextColor()));
                hint.setPadding(0, 0, 0, dp2px(16));
                layout.addView(hint);
                
                View spacer = new View(activity);
                spacer.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(16)
                ));
                layout.addView(spacer);
                
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(44)
                );
                buttonParams.setMargins(0, 0, 0, dp2px(8));
                
                String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                Button btnLikeFriends = new Button(activity);
                btnLikeFriends.setText("配置点赞好友");
                btnLikeFriends.setTextColor(Color.WHITE);
                btnLikeFriends.setBackground(getShape(accentColor, dp2px(6)));
                btnLikeFriends.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnLikeFriends.setLayoutParams(buttonParams);
                btnLikeFriends.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configLikeFriends("", "", 0);
                    }
                });
                
                Button btnFireFriends = new Button(activity);
                btnFireFriends.setText("配置续火好友");
                btnFireFriends.setTextColor(Color.WHITE);
                btnFireFriends.setBackground(getShape(accentColor, dp2px(6)));
                btnFireFriends.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnFireFriends.setLayoutParams(buttonParams);
                btnFireFriends.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFireFriends("", "", 0);
                    }
                });
                
                Button btnFireGroups = new Button(activity);
                btnFireGroups.setText("配置续火群组");
                btnFireGroups.setTextColor(Color.WHITE);
                btnFireGroups.setBackground(getShape(accentColor, dp2px(6)));
                btnFireGroups.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnFireGroups.setLayoutParams(buttonParams);
                btnFireGroups.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFireGroups("", "", 0);
                    }
                });
                
                layout.addView(btnLikeFriends);
                layout.addView(btnFireFriends);
                layout.addView(btnFireGroups);
                
                View spacer2 = new View(activity);
                spacer2.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    dp2px(16)
                ));
                layout.addView(spacer2);
                
                TextView quickTitle = new TextView(activity);
                quickTitle.setText("快速操作");
                quickTitle.setTextSize(15);
                quickTitle.setTextColor(Color.parseColor(getTextColor()));
                quickTitle.setTypeface(null, Typeface.BOLD);
                quickTitle.setPadding(0, 0, 0, dp2px(8));
                layout.addView(quickTitle);
                
                LinearLayout.LayoutParams quickButtonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(40)
                );
                quickButtonParams.setMargins(0, 0, 0, dp2px(6));
                
                Button btnViewConfig = new Button(activity);
                btnViewConfig.setText("查看配置详情");
                btnViewConfig.setTextColor(Color.WHITE);
                btnViewConfig.setBackground(getShape(accentColor, dp2px(6)));
                btnViewConfig.setPadding(dp2px(12), dp2px(8), dp2px(12), dp2px(8));
                btnViewConfig.setLayoutParams(quickButtonParams);
                btnViewConfig.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showConfigDetails(activity);
                    }
                });
                
                Button btnImportConfig = new Button(activity);
                btnImportConfig.setText("导入配置文件");
                btnImportConfig.setTextColor(Color.WHITE);
                btnImportConfig.setBackground(getShape(accentColor, dp2px(6)));
                btnImportConfig.setPadding(dp2px(12), dp2px(8), dp2px(12), dp2px(8));
                btnImportConfig.setLayoutParams(quickButtonParams);
                btnImportConfig.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        importConfig(activity);
                    }
                });
                
                Button btnExportConfig = new Button(activity);
                btnExportConfig.setText("导出配置文件");
                btnExportConfig.setTextColor(Color.WHITE);
                btnExportConfig.setBackground(getShape(accentColor, dp2px(6)));
                btnExportConfig.setPadding(dp2px(12), dp2px(8), dp2px(12), dp2px(8));
                btnExportConfig.setLayoutParams(quickButtonParams);
                btnExportConfig.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        exportConfig(activity);
                    }
                });
                
                layout.addView(btnViewConfig);
                layout.addView(btnImportConfig);
                layout.addView(btnExportConfig);
                
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
    layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
    
    TextView titleView = new TextView(activity);
    titleView.setText("配置详情");
    titleView.setTextSize(17);
    titleView.setTextColor(Color.parseColor(getTextColor()));
    titleView.setGravity(Gravity.CENTER);
    titleView.setPadding(0, 0, 0, dp2px(16));
    layout.addView(titleView);
    
    ScrollView scrollView = new ScrollView(activity);
    LinearLayout contentLayout = new LinearLayout(activity);
    contentLayout.setOrientation(LinearLayout.VERTICAL);
    
    TextView likeTitle = new TextView(activity);
    likeTitle.setText("点赞好友 (" + selectedFriendsForLike.size() + "人):");
    likeTitle.setTextSize(15);
    likeTitle.setTextColor(Color.parseColor(getTextColor()));
    likeTitle.setPadding(0, dp2px(4), 0, dp2px(4));
    likeTitle.setTypeface(null, Typeface.BOLD);
    contentLayout.addView(likeTitle);
    
    if (selectedFriendsForLike.isEmpty()) {
        TextView emptyLike = new TextView(activity);
        emptyLike.setText("未配置点赞好友");
        emptyLike.setTextSize(14);
        emptyLike.setTextColor(Color.parseColor(getSubTextColor()));
        emptyLike.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        contentLayout.addView(emptyLike);
    } else {
        for (int i = 0; i < selectedFriendsForLike.size(); i++) {
            TextView likeItem = new TextView(activity);
            likeItem.setText((i + 1) + ". " + selectedFriendsForLike.get(i));
            likeItem.setTextSize(14);
            likeItem.setTextColor(Color.parseColor(getTextColor()));
            likeItem.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            contentLayout.addView(likeItem);
        }
    }
    
    TextView fireTitle = new TextView(activity);
    fireTitle.setText("续火好友 (" + selectedFriendsForFire.size() + "人):");
    fireTitle.setTextSize(15);
    fireTitle.setTextColor(Color.parseColor(getTextColor()));
    fireTitle.setPadding(0, dp2px(12), 0, dp2px(4));
    fireTitle.setTypeface(null, Typeface.BOLD);
    contentLayout.addView(fireTitle);
    
    if (selectedFriendsForFire.isEmpty()) {
        TextView emptyFire = new TextView(activity);
        emptyFire.setText("未配置续火好友");
        emptyFire.setTextSize(14);
        emptyFire.setTextColor(Color.parseColor(getSubTextColor()));
        emptyFire.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        contentLayout.addView(emptyFire);
    } else {
        for (int i = 0; i < selectedFriendsForFire.size(); i++) {
            TextView fireItem = new TextView(activity);
            fireItem.setText((i + 1) + ". " + selectedFriendsForFire.get(i));
            fireItem.setTextSize(14);
            fireItem.setTextColor(Color.parseColor(getTextColor()));
            fireItem.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            contentLayout.addView(fireItem);
        }
    }
    
    TextView groupTitle = new TextView(activity);
    groupTitle.setText("续火群组 (" + selectedGroupsForFire.size() + "个):");
    groupTitle.setTextSize(15);
    groupTitle.setTextColor(Color.parseColor(getTextColor()));
    groupTitle.setPadding(0, dp2px(12), 0, dp2px(4));
    groupTitle.setTypeface(null, Typeface.BOLD);
    contentLayout.addView(groupTitle);
    
    if (selectedGroupsForFire.isEmpty()) {
        TextView emptyGroup = new TextView(activity);
        emptyGroup.setText("未配置续火群组");
        emptyGroup.setTextSize(14);
        emptyGroup.setTextColor(Color.parseColor(getSubTextColor()));
        emptyGroup.setPadding(dp2px(8), dp2px(2), 0, dp2px(12));
        contentLayout.addView(emptyGroup);
    } else {
        for (int i = 0; i < selectedGroupsForFire.size(); i++) {
            TextView groupItem = new TextView(activity);
            groupItem.setText((i + 1) + ". " + selectedGroupsForFire.get(i));
            groupItem.setTextSize(14);
            groupItem.setTextColor(Color.parseColor(getTextColor()));
            groupItem.setPadding(dp2px(8), dp2px(2), 0, dp2px(2));
            contentLayout.addView(groupItem);
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
    lp.height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
    dialog.getWindow().setAttributes(lp);
}

void importConfig(final Activity activity) {
    Toasts("导入功能开发中...");
}

void exportConfig(final Activity activity) {
    Toasts("导出功能开发中...");
}

void showWordsMenu(final Activity activity) {
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int theme = getCurrentTheme();
                String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置续火语录");
                titleView.setTextSize(17);
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(44)
                );
                buttonParams.setMargins(0, 0, 0, dp2px(8));
                
                Button btnFriendWords = new Button(activity);
                btnFriendWords.setText("配置好友续火语录");
                btnFriendWords.setTextColor(Color.WHITE);
                btnFriendWords.setBackground(getShape(accentColor, dp2px(6)));
                btnFriendWords.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnFriendWords.setLayoutParams(buttonParams);
                
                btnFriendWords.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFriendFireWords("", "", 0);
                    }
                });
                
                Button btnGroupWords = new Button(activity);
                btnGroupWords.setText("配置群组续火语录");
                btnGroupWords.setTextColor(Color.WHITE);
                btnGroupWords.setBackground(getShape(accentColor, dp2px(6)));
                btnGroupWords.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnGroupWords.setLayoutParams(buttonParams);
                
                btnGroupWords.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configGroupFireWords("", "", 0);
                    }
                });
                
                layout.addView(btnFriendWords);
                layout.addView(btnGroupWords);
                
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
                
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (negativeButton != null) {
                    negativeButton.setTextColor(Color.parseColor(accentColor));
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
                String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置执行时间");
                titleView.setTextSize(17);
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(44)
                );
                buttonParams.setMargins(0, 0, 0, dp2px(8));
                
                Button btnLikeTime = new Button(activity);
                btnLikeTime.setText("配置好友点赞时间");
                btnLikeTime.setTextColor(Color.WHITE);
                btnLikeTime.setBackground(getShape(accentColor, dp2px(6)));
                btnLikeTime.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnLikeTime.setLayoutParams(buttonParams);
                
                btnLikeTime.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configLikeTime("", "", 0);
                    }
                });
                
                Button btnFriendFireTime = new Button(activity);
                btnFriendFireTime.setText("配置好友续火时间");
                btnFriendFireTime.setTextColor(Color.WHITE);
                btnFriendFireTime.setBackground(getShape(accentColor, dp2px(6)));
                btnFriendFireTime.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnFriendFireTime.setLayoutParams(buttonParams);
                
                btnFriendFireTime.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configFriendFireTime("", "", 0);
                    }
                });
                
                Button btnGroupFireTime = new Button(activity);
                btnGroupFireTime.setText("配置群组续火时间");
                btnGroupFireTime.setTextColor(Color.WHITE);
                btnGroupFireTime.setBackground(getShape(accentColor, dp2px(6)));
                btnGroupFireTime.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnGroupFireTime.setLayoutParams(buttonParams);
                
                btnGroupFireTime.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        configGroupFireTime("", "", 0);
                    }
                });
                
                layout.addView(btnLikeTime);
                layout.addView(btnFriendFireTime);
                layout.addView(btnGroupFireTime);
                
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
                
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (negativeButton != null) {
                    negativeButton.setTextColor(Color.parseColor(accentColor));
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
                String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("立即执行任务");
                titleView.setTextSize(17);
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(44)
                );
                buttonParams.setMargins(0, 0, 0, dp2px(8));
                
                Button btnLike = new Button(activity);
                btnLike.setText("立即点赞好友");
                btnLike.setTextColor(Color.WHITE);
                btnLike.setBackground(getShape(accentColor, dp2px(6)));
                btnLike.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnLike.setLayoutParams(buttonParams);
                
                btnLike.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateLike("", "", 0);
                    }
                });
                
                Button btnFriendFire = new Button(activity);
                btnFriendFire.setText("立即续火好友");
                btnFriendFire.setTextColor(Color.WHITE);
                btnFriendFire.setBackground(getShape(accentColor, dp2px(6)));
                btnFriendFire.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnFriendFire.setLayoutParams(buttonParams);
                
                btnFriendFire.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateFriendFire("", "", 0);
                    }
                });
                
                Button btnGroupFire = new Button(activity);
                btnGroupFire.setText("立即续火群组");
                btnGroupFire.setTextColor(Color.WHITE);
                btnGroupFire.setBackground(getShape(accentColor, dp2px(6)));
                btnGroupFire.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnGroupFire.setLayoutParams(buttonParams);
                
                btnGroupFire.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        immediateGroupFire("", "", 0);
                    }
                });
                
                Button btnAll = new Button(activity);
                btnAll.setText("执行全部任务");
                btnAll.setTextColor(Color.WHITE);
                btnAll.setBackground(getShape(accentColor, dp2px(6)));
                btnAll.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
                btnAll.setLayoutParams(buttonParams);
                
                btnAll.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        executeAllTasks();
                    }
                });
                
                layout.addView(btnLike);
                layout.addView(btnFriendFire);
                layout.addView(btnGroupFire);
                layout.addView(btnAll);
                
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
                
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (negativeButton != null) {
                    negativeButton.setTextColor(Color.parseColor(accentColor));
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
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList friendList = getFriendList();
                if (friendList == null || friendList.isEmpty()) {
                    Toasts("未添加任何好友");
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList uinList = new ArrayList();
                
                for (int i = 0; i < friendList.size(); i++) {
                    Object friend = friendList.get(i);
                    String uin = "";
                    String name = "";
                    String remark = "";
                    
                    try {
                        Object uinObj = getFieldValue(friend, "uin");
                        Object nameObj = getFieldValue(friend, "name");
                        Object remarkObj = getFieldValue(friend, "remark");
                        
                        if (uinObj != null) uin = uinObj.toString();
                        if (nameObj != null) name = nameObj.toString();
                        if (remarkObj != null) remark = remarkObj.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
                    displayList.add(displayName);
                    uinList.add(uin);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        showFriendSelectionDialog(activity, displayList, uinList, selectedFriendsForLike, "点赞", "like");
                    }
                });
            } catch (Exception e) {
                Toasts("获取好友列表失败");
            }
        }
    }).start();
}

public void configFireFriends(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList friendList = getFriendList();
                if (friendList == null || friendList.isEmpty()) {
                    Toasts("未添加任何好友");
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList uinList = new ArrayList();
                
                for (int i = 0; i < friendList.size(); i++) {
                    Object friend = friendList.get(i);
                    String uin = "";
                    String name = "";
                    String remark = "";
                    
                    try {
                        Object uinObj = getFieldValue(friend, "uin");
                        Object nameObj = getFieldValue(friend, "name");
                        Object remarkObj = getFieldValue(friend, "remark");
                        
                        if (uinObj != null) uin = uinObj.toString();
                        if (nameObj != null) name = nameObj.toString();
                        if (remarkObj != null) remark = remarkObj.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = (!remark.isEmpty() ? remark : name) + " (" + uin + ")";
                    displayList.add(displayName);
                    uinList.add(uin);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        showFriendSelectionDialog(activity, displayList, uinList, selectedFriendsForFire, "续火", "fire");
                    }
                });
            } catch (Exception e) {
                Toasts("获取好友列表失败");
            }
        }
    }).start();
}

private void showFriendSelectionDialog(Activity activity, ArrayList displayList, ArrayList uinList, 
                                     ArrayList selectedList, String taskName, String configType) {
    
    if (activity == null || activity.isFinishing()) {
        Toasts("无法获取有效的Activity");
        return;
    }
    
    try {
        int theme = getCurrentTheme();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
        
        LinearLayout mainLayout = new LinearLayout(activity);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(dp2px(4), dp2px(4), dp2px(4), dp2px(4));
        
        final ArrayList currentSessionSelected = new ArrayList(selectedList);

        LinearLayout contentLayout = new LinearLayout(activity);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
        contentLayout.setBackground(getWebShape(getCardColor(), dp2px(8)));
        
        TextView titleView = new TextView(activity);
        titleView.setText("选择" + taskName + "好友");
        titleView.setTextColor(Color.parseColor(getTextColor()));
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        titleView.setGravity(Gravity.CENTER);
        titleView.setPadding(0, 0, 0, dp2px(16));
        contentLayout.addView(titleView);
        
        final EditText searchEditText = new EditText(activity);
        searchEditText.setHint("搜索好友QQ号、好友名、备注");
        searchEditText.setTextColor(Color.parseColor(getTextColor()));
        searchEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
        searchEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
        searchEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
        contentLayout.addView(searchEditText);
        
        int themeValue = getCurrentTheme();
        String accentColor = themeValue == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
        
        Button selectAllButton = new Button(activity);
        selectAllButton.setText("全选(当前显示)");
        selectAllButton.setTextColor(Color.WHITE);
        selectAllButton.setBackground(getShape(accentColor, dp2px(6)));
        selectAllButton.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.END;
        params.setMargins(0, dp2px(12), 0, dp2px(12));
        selectAllButton.setLayoutParams(params);
        contentLayout.addView(selectAllButton);
        
        final ListView listView = new ListView(activity);
        listView.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
        listView.setDividerHeight(dp2px(1));
        LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        );
        listParams.weight = 1;
        listView.setLayoutParams(listParams);
        contentLayout.addView(listView);
        
        mainLayout.addView(contentLayout);
        
        final ArrayList filteredDisplayList = new ArrayList(displayList);
        final ArrayList filteredUinList = new ArrayList(uinList);
        
        final CustomArrayAdapter adapter = new CustomArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList, getTextColor());
        
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        for (int i = 0; i < filteredUinList.size(); i++) {
            String uin = (String)filteredUinList.get(i);
            listView.setItemChecked(i, currentSessionSelected.contains(uin));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String uin = (String) filteredUinList.get(position);
                boolean isChecked = listView.isItemChecked(position);
                if (isChecked) {
                    if (!currentSessionSelected.contains(uin)) {
                        currentSessionSelected.add(uin);
                    }
                } else {
                    currentSessionSelected.remove(uin);
                }
            }
        });
        
        searchEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();
                filteredDisplayList.clear();
                filteredUinList.clear();
                
                if (searchText.isEmpty()) {
                    filteredDisplayList.addAll(displayList);
                    filteredUinList.addAll(uinList);
                } else {
                    for (int i = 0; i < displayList.size(); i++) {
                        String displayName = ((String)displayList.get(i)).toLowerCase();
                        String uin = (String)uinList.get(i);
                        
                        if (displayName.contains(searchText) || uin.contains(searchText)) {
                            filteredDisplayList.add(displayList.get(i));
                            filteredUinList.add(uinList.get(i));
                        }
                    }
                }
                
                adapter.notifyDataSetChanged();
                
                for (int i = 0; i < filteredUinList.size(); i++) {
                    String uin = (String)filteredUinList.get(i);
                    listView.setItemChecked(i, currentSessionSelected.contains(uin));
                }
            }
        });
        
        selectAllButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < filteredUinList.size(); i++) {
                    listView.setItemChecked(i, true);
                    String uin = (String) filteredUinList.get(i);
                    if (!currentSessionSelected.contains(uin)) {
                        currentSessionSelected.add(uin);
                    }
                }
            }
        });
        
        dialogBuilder.setView(mainLayout);
        
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                selectedList.clear();
                selectedList.addAll(currentSessionSelected);
                
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
        
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setTextColor(Color.parseColor(accentColor));
        }
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (negativeButton != null) {
            negativeButton.setTextColor(Color.parseColor(accentColor));
        }
        
    } catch (Exception e) {
        Toasts("打开选择对话框失败: " + e.getMessage());
    }
}

public void configFireGroups(String groupUin, String userUin, int chatType){
    final Activity activity = getActivity();
    if (activity == null) return;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                ArrayList groupList = getGroupList();
                if (groupList == null || groupList.isEmpty()) {
                    Toasts("未加入任何群组");
                    return;
                }
                
                final ArrayList displayList = new ArrayList();
                final ArrayList uinList = new ArrayList();
                for (int i = 0; i < groupList.size(); i++) {
                    Object group = groupList.get(i);
                    String groupName = "";
                    String groupUinStr = "";
                    try {
                        Object nameObj = getFieldValue(group, "GroupName");
                        Object uinObj = getFieldValue(group, "GroupUin");
                        
                        if (nameObj != null) groupName = nameObj.toString();
                        if (uinObj != null) groupUinStr = uinObj.toString();
                    } catch (Exception e) {
                        continue;
                    }
                    
                    String displayName = groupName + " (" + groupUinStr + ")";
                    displayList.add(displayName);
                    uinList.add(groupUinStr);
                }
                
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        showGroupSelectionDialog(activity, displayList, uinList);
                    }
                });
            } catch (Exception e) {
                Toasts("获取群组列表失败");
            }
        }
    }).start();
}

private void showGroupSelectionDialog(Activity activity, ArrayList displayList, ArrayList uinList) {
    if (activity == null || activity.isFinishing()) {
        Toasts("无法获取有效的Activity");
        return;
    }
    
    try {
        int theme = getCurrentTheme();
        String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
        
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
        
        LinearLayout mainLayout = new LinearLayout(activity);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(dp2px(4), dp2px(4), dp2px(4), dp2px(4));
        
        final ArrayList currentSessionSelected = new ArrayList(selectedGroupsForFire);
        
        LinearLayout contentLayout = new LinearLayout(activity);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
        contentLayout.setBackground(getWebShape(getCardColor(), dp2px(8)));
        
        TextView titleView = new TextView(activity);
        titleView.setText("选择续火群组");
        titleView.setTextColor(Color.parseColor(getTextColor()));
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        titleView.setGravity(Gravity.CENTER);
        titleView.setPadding(0, 0, 0, dp2px(16));
        contentLayout.addView(titleView);
        
        final EditText searchEditText = new EditText(activity);
        searchEditText.setHint("搜索群号、群名");
        searchEditText.setTextColor(Color.parseColor(getTextColor()));
        searchEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
        searchEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
        searchEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
        contentLayout.addView(searchEditText);
        
        Button selectAllButton = new Button(activity);
        selectAllButton.setText("全选(当前显示)");
        selectAllButton.setTextColor(Color.WHITE);
        selectAllButton.setBackground(getShape(accentColor, dp2px(6)));
        selectAllButton.setPadding(dp2px(16), dp2px(10), dp2px(16), dp2px(10));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.END;
        params.setMargins(0, dp2px(12), 0, dp2px(12));
        selectAllButton.setLayoutParams(params);
        contentLayout.addView(selectAllButton);
        
        final ListView listView = new ListView(activity);
        listView.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
        listView.setDividerHeight(dp2px(1));
        LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        );
        listParams.weight = 1;
        listView.setLayoutParams(listParams);
        contentLayout.addView(listView);
        
        mainLayout.addView(contentLayout);
        
        final ArrayList filteredDisplayList = new ArrayList(displayList);
        final ArrayList filteredUinList = new ArrayList(uinList);
        
        final CustomArrayAdapter adapter = new CustomArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, filteredDisplayList, getTextColor());
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        for (int i = 0; i < filteredUinList.size(); i++) {
            String uin = (String)filteredUinList.get(i);
            listView.setItemChecked(i, currentSessionSelected.contains(uin));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String uin = (String) filteredUinList.get(position);
                boolean isChecked = listView.isItemChecked(position);
                if (isChecked) {
                    if (!currentSessionSelected.contains(uin)) {
                        currentSessionSelected.add(uin);
                    }
                } else {
                    currentSessionSelected.remove(uin);
                }
            }
        });
        
        searchEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();
                filteredDisplayList.clear();
                filteredUinList.clear();
                
                if (searchText.isEmpty()) {
                    filteredDisplayList.addAll(displayList);
                    filteredUinList.addAll(uinList);
                } else {
                    for (int i = 0; i < displayList.size(); i++) {
                        String displayName = ((String)displayList.get(i)).toLowerCase();
                        String uin = (String)uinList.get(i);
                        
                        if (displayName.contains(searchText) || uin.contains(searchText)) {
                            filteredDisplayList.add(displayList.get(i));
                            filteredUinList.add(uinList.get(i));
                        }
                    }
                }
                
                adapter.notifyDataSetChanged();
                
                for (int i = 0; i < filteredUinList.size(); i++) {
                    String uin = (String)filteredUinList.get(i);
                    listView.setItemChecked(i, currentSessionSelected.contains(uin));
                }
            }
        });
        
        selectAllButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < filteredUinList.size(); i++) {
                    listView.setItemChecked(i, true);
                    String uin = (String) filteredUinList.get(i);
                    if (!currentSessionSelected.contains(uin)) {
                        currentSessionSelected.add(uin);
                    }
                }
            }
        });
        
        dialogBuilder.setView(mainLayout);
        
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                selectedGroupsForFire.clear();
                selectedGroupsForFire.addAll(currentSessionSelected);
                
                saveFireGroups();
                Toasts("已选择" + selectedGroupsForFire.size() + "个续火群组");
                
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
        
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setTextColor(Color.parseColor(accentColor));
        }
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (negativeButton != null) {
            negativeButton.setTextColor(Color.parseColor(accentColor));
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
                    wordsBuilder.append((String)friendFireWords.get(i));
                }
                
                int theme = getCurrentTheme();
                String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置好友续火语录");
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                final EditText wordsEditText = new EditText(activity);
                wordsEditText.setText(wordsBuilder.toString());
                wordsEditText.setHint("输入好友续火语录，每行一个");
                wordsEditText.setTextColor(Color.parseColor(getTextColor()));
                wordsEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                wordsEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
                wordsEditText.setMinLines(6);
                wordsEditText.setMaxLines(20);
                wordsEditText.setGravity(Gravity.TOP);
                wordsEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
                wordsEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                
                int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
                int editTextHeight = (int)(screenHeight * 0.35);
                LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    editTextHeight
                );
                editTextParams.setMargins(0, 0, 0, dp2px(16));
                wordsEditText.setLayoutParams(editTextParams);
                
                layout.addView(wordsEditText);
                
                TextView hintView = new TextView(activity);
                hintView.setText("注意：输入多个续火语录时，每行一个");
                hintView.setTextColor(Color.parseColor(getSubTextColor()));
                hintView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                hintView.setPadding(0, 0, 0, 0);
                layout.addView(hintView);
                
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
                dialogBuilder.setView(layout);
                dialogBuilder.setCancelable(true);
                
                dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String wordsText = wordsEditText.getText().toString().trim();
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
                        
                        saveListToFile(friendFireWordsPath, friendFireWords);
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
                
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
                
                Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if (positiveButton != null) {
                    positiveButton.setTextColor(Color.parseColor(accentColor));
                }
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (negativeButton != null) {
                    negativeButton.setTextColor(Color.parseColor(accentColor));
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
                    wordsBuilder.append((String)groupFireWords.get(i));
                }
                
                int theme = getCurrentTheme();
                String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
                
                LinearLayout layout = new LinearLayout(activity);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
                layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
                
                TextView titleView = new TextView(activity);
                titleView.setText("配置群组续火语录");
                titleView.setTextColor(Color.parseColor(getTextColor()));
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                titleView.setGravity(Gravity.CENTER);
                titleView.setPadding(0, 0, 0, dp2px(16));
                layout.addView(titleView);
                
                final EditText wordsEditText = new EditText(activity);
                wordsEditText.setText(wordsBuilder.toString());
                wordsEditText.setHint("输入群组续火语录，每行一个");
                wordsEditText.setTextColor(Color.parseColor(getTextColor()));
                wordsEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                wordsEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
                wordsEditText.setMinLines(6);
                wordsEditText.setMaxLines(20);
                wordsEditText.setGravity(Gravity.TOP);
                wordsEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
                wordsEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                
                int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
                int editTextHeight = (int)(screenHeight * 0.35);
                LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    editTextHeight
                );
                editTextParams.setMargins(0, 0, 0, dp2px(16));
                wordsEditText.setLayoutParams(editTextParams);
                
                layout.addView(wordsEditText);
                
                TextView hintView = new TextView(activity);
                hintView.setText("注意：输入多个续火语录时，每行一个");
                hintView.setTextColor(Color.parseColor(getSubTextColor()));
                hintView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                hintView.setPadding(0, 0, 0, 0);
                layout.addView(hintView);
                
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
                dialogBuilder.setView(layout);
                dialogBuilder.setCancelable(true);
                
                dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String wordsText = wordsEditText.getText().toString().trim();
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
                        
                        saveListToFile(groupFireWordsPath, groupFireWords);
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
                
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    
                Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if (positiveButton != null) {
                    positiveButton.setTextColor(Color.parseColor(accentColor));
                }
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (negativeButton != null) {
                    negativeButton.setTextColor(Color.parseColor(accentColor));
                }
            } catch (Exception e) {
            }
        }
    });
}

public void configLikeTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int theme = getCurrentTheme();
            String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
            
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
            layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
            
            TextView titleView = new TextView(activity);
            titleView.setText("设置点赞时间 (HH:mm)");
            titleView.setTextColor(Color.parseColor(getTextColor()));
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            titleView.setGravity(Gravity.CENTER);
            titleView.setPadding(0, 0, 0, dp2px(16));
            layout.addView(titleView);
            
            final EditText timeEditText = new EditText(activity);
            timeEditText.setText(likeTime);
            timeEditText.setHint("例如: 00:00");
            timeEditText.setTextColor(Color.parseColor(getTextColor()));
            timeEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
            timeEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
            timeEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            layout.addView(timeEditText);
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String timeText = timeEditText.getText().toString().trim();
                    if (timeText.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        likeTime = timeText;
                        saveTimeConfig();
                        Toasts("已设置点赞时间: " + likeTime);
                        checkAndExecuteTasks();
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
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
            
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            if (positiveButton != null) {
                positiveButton.setTextColor(Color.parseColor(accentColor));
            }
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (negativeButton != null) {
                negativeButton.setTextColor(Color.parseColor(accentColor));
            }
        }
    });
}

public void configFriendFireTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int theme = getCurrentTheme();
            String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
            
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
            layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
            
            TextView titleView = new TextView(activity);
            titleView.setText("设置好友续火时间 (HH:mm)");
            titleView.setTextColor(Color.parseColor(getTextColor()));
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            titleView.setGravity(Gravity.CENTER);
            titleView.setPadding(0, 0, 0, dp2px(16));
            layout.addView(titleView);
            
            final EditText timeEditText = new EditText(activity);
            timeEditText.setText(friendFireTime);
            timeEditText.setHint("例如: 00:00");
            timeEditText.setTextColor(Color.parseColor(getTextColor()));
            timeEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
            timeEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
            timeEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            layout.addView(timeEditText);
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String timeText = timeEditText.getText().toString().trim();
                    if (timeText.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        friendFireTime = timeText;
                        saveTimeConfig();
                        Toasts("已设置好友续火时间: " + friendFireTime);
                        checkAndExecuteTasks();
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
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
            
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            if (positiveButton != null) {
                positiveButton.setTextColor(Color.parseColor(accentColor));
            }
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (negativeButton != null) {
                negativeButton.setTextColor(Color.parseColor(accentColor));
            }
        }
    });
}

public void configGroupFireTime(String groupUin, String userUin, int chatType) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            int theme = getCurrentTheme();
            String accentColor = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK ? getAccentColorDark() : getAccentColor();
            
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, theme);
            
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(20), dp2px(16), dp2px(20), dp2px(16));
            layout.setBackground(getWebShape(getCardColor(), dp2px(8)));
            
            TextView titleView = new TextView(activity);
            titleView.setText("设置群组续火时间 (HH:mm)");
            titleView.setTextColor(Color.parseColor(getTextColor()));
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            titleView.setGravity(Gravity.CENTER);
            titleView.setPadding(0, 0, 0, dp2px(16));
            layout.addView(titleView);
            
            final EditText timeEditText = new EditText(activity);
            timeEditText.setText(groupFireTime);
            timeEditText.setHint("例如: 00:00");
            timeEditText.setTextColor(Color.parseColor(getTextColor()));
            timeEditText.setHintTextColor(Color.parseColor(getSubTextColor()));
            timeEditText.setBackground(getWebShape(getSurfaceColor(), dp2px(6)));
            timeEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            layout.addView(timeEditText);
            
            dialogBuilder.setView(layout);
            
            dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String timeText = timeEditText.getText().toString().trim();
                    if (timeText.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                        groupFireTime = timeText;
                        saveTimeConfig();
                        Toasts("已设置群组续火时间: " + groupFireTime);
                        checkAndExecuteTasks();
                    } else {
                        Toasts("时间格式错误，请使用 HH:mm 格式");
                    }
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
            
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            if (positiveButton != null) {
                positiveButton.setTextColor(Color.parseColor(accentColor));
            }
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (negativeButton != null) {
                negativeButton.setTextColor(Color.parseColor(accentColor));
            }
        }
    });
}