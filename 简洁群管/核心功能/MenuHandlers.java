
// 你应该像欣赏花一样 欣赏自己

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;
import android.view.Gravity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

private Map groupInfoCache = new ConcurrentHashMap();

{
    try {
        ArrayList groupList = getGroupList();
        if (groupList != null) {
            ArrayList groupListCopy = safeCopyList(groupList);
            for (Object groupInfo : groupListCopy) {
                if (groupInfo != null && groupInfo.GroupUin != null) {
                    groupInfoCache.put(groupInfo.GroupUin, groupInfo);
                }
            }
        }
    } catch (Exception e) {
    }
}

void onCreateMenu(Object msg) {
    try {
        if (msg != null && msg.IsGroup) {
            String groupUin = msg.GroupUin;
            String targetUin = msg.UserUin;
            
            if (targetUin != null && targetUin.equals(myUin)) {
                return;
            }
            
            Object groupInfo = groupInfoCache.get(groupUin);
            if (groupInfo == null) {
                groupInfo = getGroupInfo(groupUin);
                if (groupInfo != null) {
                    groupInfoCache.put(groupUin, groupInfo);
                } else {
                    return;
                }
            }
            
            Object myInfo = getMemberInfo(groupUin, myUin);
            if (myInfo == null) return;
            
            if (myInfo.IsOwner || myInfo.IsAdmin) {
                addMenuItem("快捷群管", "haifeng520");
            }
            
        }
    } catch (Exception e) {
    }
}

void onClickFloatingWindow(int type, String uin) {
    try {
        if (type == 2) {
            addTemporaryItem("开启/关闭艾特禁言", "开关艾特禁言方法");
            addTemporaryItem("开启/关闭退群拉黑", "退群拉黑开关方法");
            addTemporaryItem("开启/关闭自助头衔", "开关自助头衔方法");
            addTemporaryItem("开启/关闭自动解禁代管", "自动解禁代管方法");
            addTemporaryItem("设置艾特禁言时间", "设置艾特禁言时间方法");
            addTemporaryItem("代管管理功能", "代管管理弹窗");
            addTemporaryItem("群黑名单管理", "黑名单管理弹窗");
            addTemporaryItem("检测群黑名单", "检测黑名单方法");
            addTemporaryItem("查看更新日志", "showUpdateLog");
            addTemporaryItem("查看群管功能", "群管功能弹窗");
        }
    } catch (Exception e) {
    }
}

public void haifeng520(final Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                Object myInfo = getMemberInfo(groupUin, myUin);
                if (myInfo == null) return;
                
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
                builder.setTitle("快捷群管 —— " + 名(targetUin) + "(" + targetUin + ")");
                
                LinearLayout dialogLayout = new LinearLayout(getActivity());
                dialogLayout.setOrientation(LinearLayout.VERTICAL);
                dialogLayout.setPadding(dp2px(20), dp2px(15), dp2px(20), dp2px(15));
                
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(Color.argb(230, 255, 255, 255));
                bg.setCornerRadius(dp2px(18));
                int textColor = Color.BLACK;
                if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                    bg.setColor(Color.argb(220, 40, 40, 40));
                    textColor = Color.WHITE;
                }
                dialogLayout.setBackground(bg);
                
                boolean isOwner = false;
                boolean isAdmin = false;
                try {
                    isOwner = myInfo.IsOwner;
                    isAdmin = myInfo.IsAdmin;
                } catch (Exception e) {
                }
                
                List<TextView> buttonList = new ArrayList<>();
                
                if (isOwner || isAdmin) {
                    boolean canOperateTarget = true;
                    
                    try {
                        Object targetInfo = getMemberInfo(groupUin, targetUin);
                        if (targetInfo != null) {
                            boolean targetIsOwner = false;
                            boolean targetIsAdmin = false;
                            try {
                                targetIsOwner = targetInfo.IsOwner;
                                targetIsAdmin = targetInfo.IsAdmin;
                            } catch (Exception e) {
                            }
                            
                            if (isAdmin && (targetIsOwner || targetIsAdmin)) {
                                canOperateTarget = false;
                            }
                        }
                    } catch (Exception e) {
                    }
                    
                    if (canOperateTarget) {
                        TextView banBtn = new TextView(getActivity());
                        banBtn.setText("禁言");
                        banBtn.setTextSize(18);
                        banBtn.setTextColor(textColor);
                        banBtn.setPadding(dp2px(15), dp2px(20), dp2px(15), dp2px(20));
                        banBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable banBg = new GradientDrawable();
                        banBg.setColor(Color.argb(30, 0, 0, 0));
                        banBg.setCornerRadius(dp2px(12));
                        if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                            banBg.setColor(Color.argb(50, 255, 255, 255));
                        }
                        banBtn.setBackground(banBg);
                        
                        banBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                forbiddenMenuItem(msg);
                            }
                        });
                        
                        buttonList.add(banBtn);
                        
                        TextView revokeBtn = new TextView(getActivity());
                        revokeBtn.setText("撤回");
                        revokeBtn.setTextSize(18);
                        revokeBtn.setTextColor(textColor);
                        revokeBtn.setPadding(dp2px(15), dp2px(20), dp2px(15), dp2px(20));
                        revokeBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable revokeBg = new GradientDrawable();
                        revokeBg.setColor(Color.argb(30, 0, 0, 0));
                        revokeBg.setCornerRadius(dp2px(12));
                        if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                            revokeBg.setColor(Color.argb(50, 255, 255, 255));
                        }
                        revokeBtn.setBackground(revokeBg);
                        
                        revokeBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                Activity activity = getActivity();
                                if (activity == null) return;
                                
                                activity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
                                        builder.setTitle("确认撤回");
                                        
                                        LinearLayout layout = new LinearLayout(getActivity());
                                        layout.setOrientation(LinearLayout.VERTICAL);
                                        layout.setPadding(dp2px(25), dp2px(20), dp2px(25), dp2px(20));
                                        
                                        GradientDrawable bg = new GradientDrawable();
                                        bg.setColor(Color.argb(230, 255, 255, 255));
                                        bg.setCornerRadius(dp2px(18));
                                        int textColor = Color.BLACK;
                                        if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                                            bg.setColor(Color.argb(220, 40, 40, 40));
                                            textColor = Color.WHITE;
                                        }
                                        layout.setBackground(bg);
                                        
                                        TextView message = new TextView(getActivity());
                                        message.setText("确定要撤回这条消息吗？");
                                        message.setTextSize(16);
                                        message.setTextColor(textColor);
                                        message.setPadding(0, 0, 0, dp2px(20));
                                        layout.addView(message);
                                        
                                        builder.setView(layout);
                                        
                                        builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                                            public void onClick(android.content.DialogInterface dialog, int which) {
                                                try {
                                                    revokeMsg(msg);
                                                    toast("已撤回消息");
                                                } catch (Exception e) {
                                                    toast("撤回失败: " + e.getMessage());
                                                }
                                            }
                                        });
                                        
                                        builder.setNegativeButton("取消", null);
                                        
                                        AlertDialog dialog = builder.create();
                                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        dialog.show();
                                    }
                                });
                            }
                        });
                        
                        buttonList.add(revokeBtn);
                        
                        TextView kickBtn = new TextView(getActivity());
                        kickBtn.setText("踢出");
                        kickBtn.setTextSize(18);
                        kickBtn.setTextColor(textColor);
                        kickBtn.setPadding(dp2px(15), dp2px(20), dp2px(15), dp2px(20));
                        kickBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable kickBg = new GradientDrawable();
                        kickBg.setColor(Color.argb(30, 0, 0, 0));
                        kickBg.setCornerRadius(dp2px(12));
                        if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                            kickBg.setColor(Color.argb(50, 255, 255, 255));
                        }
                        kickBtn.setBackground(kickBg);
                        
                        kickBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                kickMenuItem(msg);
                            }
                        });
                        
                        buttonList.add(kickBtn);
                        
                        TextView kickBlackBtn = new TextView(getActivity());
                        kickBlackBtn.setText("踢黑");
                        kickBlackBtn.setTextSize(18);
                        kickBlackBtn.setTextColor(textColor);
                        kickBlackBtn.setPadding(dp2px(15), dp2px(20), dp2px(15), dp2px(20));
                        kickBlackBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable kickBlackBg = new GradientDrawable();
                        kickBlackBg.setColor(Color.argb(30, 0, 0, 0));
                        kickBlackBg.setCornerRadius(dp2px(12));
                        if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                            kickBlackBg.setColor(Color.argb(50, 255, 255, 255));
                        }
                        kickBlackBtn.setBackground(kickBlackBg);
                        
                        kickBlackBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                kickBlackMenuItem(msg);
                            }
                        });
                        
                        buttonList.add(kickBlackBtn);
                        
                        TextView blacklistBtn = new TextView(getActivity());
                        blacklistBtn.setText("加入黑名单");
                        blacklistBtn.setTextSize(18);
                        blacklistBtn.setTextColor(textColor);
                        blacklistBtn.setPadding(dp2px(15), dp2px(20), dp2px(15), dp2px(20));
                        blacklistBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable blacklistBg = new GradientDrawable();
                        blacklistBg.setColor(Color.argb(30, 0, 0, 0));
                        blacklistBg.setCornerRadius(dp2px(12));
                        if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                            blacklistBg.setColor(Color.argb(50, 255, 255, 255));
                        }
                        blacklistBtn.setBackground(blacklistBg);
                        
                        blacklistBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                addToBlacklistMenuItem(msg);
                            }
                        });
                        
                        buttonList.add(blacklistBtn);
                        
                        if ((isOwner || isAdmin) && 是联盟群组(groupUin)) {
                            TextView allianceBanBtn = new TextView(getActivity());
                            allianceBanBtn.setText("联盟封禁");
                            allianceBanBtn.setTextSize(18);
                            allianceBanBtn.setTextColor(textColor);
                            allianceBanBtn.setPadding(dp2px(15), dp2px(20), dp2px(15), dp2px(20));
                            allianceBanBtn.setGravity(Gravity.CENTER);
                            
                            GradientDrawable allianceBanBg = new GradientDrawable();
                            allianceBanBg.setColor(Color.argb(30, 0, 0, 0));
                            allianceBanBg.setCornerRadius(dp2px(12));
                            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                                allianceBanBg.setColor(Color.argb(50, 255, 255, 255));
                            }
                            allianceBanBtn.setBackground(allianceBanBg);
                            
                            allianceBanBtn.setOnClickListener(new android.view.View.OnClickListener() {
                                public void onClick(android.view.View v) {
                                    allianceBanMenuItem(msg);
                                }
                            });
                            
                            buttonList.add(allianceBanBtn);
                        }
                    }
                }
                
                if (isOwner) {
                    TextView titleBtn = new TextView(getActivity());
                    titleBtn.setText("设置头衔");
                    titleBtn.setTextSize(18);
                    titleBtn.setTextColor(textColor);
                    titleBtn.setPadding(dp2px(15), dp2px(20), dp2px(15), dp2px(20));
                    titleBtn.setGravity(Gravity.CENTER);
                    
                    GradientDrawable titleBg = new GradientDrawable();
                    titleBg.setColor(Color.argb(30, 0, 0, 0));
                    titleBg.setCornerRadius(dp2px(12));
                    if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                        titleBg.setColor(Color.argb(50, 255, 255, 255));
                    }
                    titleBtn.setBackground(titleBg);
                    
                    titleBtn.setOnClickListener(new android.view.View.OnClickListener() {
                        public void onClick(android.view.View v) {
                            setTitleMenuItem(msg);
                        }
                    });
                    
                    buttonList.add(titleBtn);
                }
                
                if (buttonList.isEmpty()) {
                    TextView noPermissionText = new TextView(getActivity());
                    noPermissionText.setText("没有权限操作该用户……");
                    noPermissionText.setTextSize(18);
                    noPermissionText.setTextColor(textColor);
                    noPermissionText.setGravity(Gravity.CENTER);
                    noPermissionText.setPadding(dp2px(15), dp2px(30), dp2px(15), dp2px(30));
                    
                    dialogLayout.addView(noPermissionText);
                } else {
                    for (int i = 0; i < buttonList.size(); i += 2) {
                        LinearLayout rowLayout = new LinearLayout(getActivity());
                        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        
                        TextView btn1 = buttonList.get(i);
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1.0f
                        );
                        params1.setMargins(0, 0, dp2px(5), dp2px(5));
                        btn1.setLayoutParams(params1);
                        rowLayout.addView(btn1);
                        
                        if (i + 1 < buttonList.size()) {
                            TextView btn2 = buttonList.get(i + 1);
                            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                            );
                            params2.setMargins(dp2px(5), 0, 0, dp2px(5));
                            btn2.setLayoutParams(params2);
                            rowLayout.addView(btn2);
                        } else {
                            btn1.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ));
                        }
                        
                        dialogLayout.addView(rowLayout);
                    }
                }
                
                ScrollView scrollView = new ScrollView(getActivity());
                scrollView.addView(dialogLayout);
                
                builder.setView(scrollView);
                builder.setNegativeButton("取消", null);
                
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                
            } catch (Exception e) {
                toast("打开快捷群管失败: " + e.getMessage());
            }
        }
    });
}

public void allianceBanMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    if (检查代管保护(groupUin, targetUin, "联盟封禁")) {
        return;
    }
    
    if (!有权限操作(groupUin, myUin, targetUin)) {
        toast("没有权限操作该用户");
        return;
    }
    
    if (是封禁用户(targetUin)) {
        toast("该用户已经被封禁，请勿再次封禁");
        return;
    }
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("联盟封禁 - " + 名(targetUin) + "(" + targetUin + ")");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(25), dp2px(20), dp2px(25), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.argb(230, 255, 255, 255));
            bg.setCornerRadius(dp2px(18));
            int textColor = Color.BLACK;
            int hintTextColor = Color.GRAY;
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                bg.setColor(Color.argb(220, 40, 40, 40));
                textColor = Color.WHITE;
                hintTextColor = Color.LTGRAY;
            }
            layout.setBackground(bg);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + 名(targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            hint.setTextColor(textColor);
            hint.setPadding(0, 0, 0, dp2px(15));
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入封禁原因，可填可不填");
            inputEditText.setHintTextColor(hintTextColor);
            inputEditText.setTextColor(textColor);
            
            GradientDrawable etBg = new GradientDrawable();
            etBg.setColor(Color.argb(50, 0, 0, 0));
            etBg.setCornerRadius(dp2px(10));
            etBg.setStroke(dp2px(1), Color.argb(80, 0, 0, 0));
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                etBg.setColor(Color.argb(30, 255, 255, 255));
                etBg.setStroke(dp2px(1), Color.argb(60, 255, 255, 255));
            }
            inputEditText.setBackground(etBg);
            inputEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            
            layout.addView(inputEditText);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定封禁", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    final String reason = inputEditText.getText().toString().trim();
                    toast("正在执行联盟封禁...");
                    
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                添加封禁用户(targetUin, reason);
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        toast("已联盟封禁 " + 名(targetUin) + "(" + targetUin + ")");
                                    }
                                });
                            } catch (Exception e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        toast("联盟封禁失败: " + e.getMessage());
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    });
}

public void addToBlacklistMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认加入黑名单");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(25), dp2px(20), dp2px(25), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.argb(230, 255, 255, 255));
            bg.setCornerRadius(dp2px(18));
            int textColor = Color.BLACK;
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                bg.setColor(Color.argb(220, 40, 40, 40));
                textColor = Color.WHITE;
            }
            layout.setBackground(bg);
            
            TextView message = new TextView(getActivity());
            message.setText("确定要将 " + 名(targetUin) + "(" + targetUin + ") 加入黑名单并踢出吗？\n\n加入黑名单后，该用户再次入群时会被自动踢出。");
            message.setTextSize(15);
            message.setTextColor(textColor);
            message.setLineSpacing(dp2px(4), 1);
            message.setPadding(0, 0, 0, dp2px(20));
            layout.addView(message);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    try {
                        if (检查代管保护(groupUin, targetUin, "加入黑名单")) {
                            return;
                        }
                        
                        if (!有权限操作(groupUin, myUin, targetUin)) {
                            toast("没有权限操作该用户");
                            return;
                        }
                        
                        添加黑名单(groupUin, targetUin);
                        unifiedKick(groupUin, targetUin, true);
                        
                        String successMsg = "群:" + groupUin + " 已成功将该用户:" + 名(targetUin) + "(" + targetUin + ")加入黑名单并执行踢黑";
                        toast(successMsg);
                        
                    } catch (Exception e) {
                        toast("加入黑名单失败: " + e.getMessage());
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    });
}

public void kickMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认踢出");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(25), dp2px(20), dp2px(25), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.argb(230, 255, 255, 255));
            bg.setCornerRadius(dp2px(18));
            int textColor = Color.BLACK;
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                bg.setColor(Color.argb(220, 40, 40, 40));
                textColor = Color.WHITE;
            }
            layout.setBackground(bg);
            
            TextView message = new TextView(getActivity());
            message.setText("真的确定要踢出 " + 名(targetUin) + "(" + targetUin + ") 吗？");
            message.setTextSize(16);
            message.setTextColor(textColor);
            message.setPadding(0, 0, 0, dp2px(20));
            layout.addView(message);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    unifiedKick(groupUin, targetUin, false);
                    toast("踢出成功");
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    });
}

public void kickBlackMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认踢黑");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(25), dp2px(20), dp2px(25), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.argb(230, 255, 255, 255));
            bg.setCornerRadius(dp2px(18));
            int textColor = Color.BLACK;
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                bg.setColor(Color.argb(220, 40, 40, 40));
                textColor = Color.WHITE;
            }
            layout.setBackground(bg);
            
            TextView message = new TextView(getActivity());
            message.setText("真的确定要踢出并拉黑 " + 名(targetUin) + "(" + targetUin + ") 吗？\n\n确定后，该用户无法再次加入该群聊");
            message.setTextSize(16);
            message.setTextColor(textColor);
            message.setPadding(0, 0, 0, dp2px(20));
            layout.addView(message);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    unifiedKick(groupUin, targetUin, true);
                    toast("踢黑成功，该用户不会再次入群");
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    });
}

public void forbiddenMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    if (检查代管保护(groupUin, targetUin, "禁言")) return;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("设置禁言时间");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(25), dp2px(20), dp2px(25), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.argb(230, 255, 255, 255));
            bg.setCornerRadius(dp2px(18));
            int textColor = Color.BLACK;
            int hintTextColor = Color.GRAY;
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                bg.setColor(Color.argb(220, 40, 40, 40));
                textColor = Color.WHITE;
                hintTextColor = Color.LTGRAY;
            }
            layout.setBackground(bg);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + 名(targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            hint.setTextColor(textColor);
            hint.setPadding(0, 0, 0, dp2px(15));
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入禁言时间（秒）");
            inputEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            inputEditText.setHintTextColor(hintTextColor);
            inputEditText.setTextColor(textColor);
            
            GradientDrawable etBg = new GradientDrawable();
            etBg.setColor(Color.argb(50, 0, 0, 0));
            etBg.setCornerRadius(dp2px(10));
            etBg.setStroke(dp2px(1), Color.argb(80, 0, 0, 0));
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                etBg.setColor(Color.argb(30, 255, 255, 255));
                etBg.setStroke(dp2px(1), Color.argb(60, 255, 255, 255));
            }
            inputEditText.setBackground(etBg);
            inputEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            
            layout.addView(inputEditText);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定禁言", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    String input = inputEditText.getText().toString().trim();
                    if (!input.isEmpty()) {
                        try {
                            int time = Integer.parseInt(input);
                            if (time > 0) {
                                if (time > 2592000) {
                                    toast("禁言时间不能超过30天");
                                    return;
                                }
                                unifiedForbidden(groupUin, targetUin, time);
                                
                                String timeDisplay;
                                if (time < 60) {
                                    timeDisplay = time + "秒";
                                } else if (time < 3600) {
                                    timeDisplay = (time / 60) + "分钟";
                                } else if (time < 86400) {
                                    timeDisplay = (time / 3600) + "小时";
                                } else {
                                    timeDisplay = (time / 86400) + "天";
                                }
                                
                                toast("已禁言 " + 名(targetUin) + " " + timeDisplay);
                            } else {
                                toast("请输入大于0的数字");
                            }
                        } catch (NumberFormatException e) {
                            toast("请输入有效的数字");
                        }
                    } else {
                        toast("请输入禁言时间");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    });
}

public void setTitleMenuItem(Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final String targetUin = msg.UserUin;
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("设置头衔");
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(25), dp2px(20), dp2px(25), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.argb(230, 255, 255, 255));
            bg.setCornerRadius(dp2px(18));
            int textColor = Color.BLACK;
            int hintTextColor = Color.GRAY;
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                bg.setColor(Color.argb(220, 40, 40, 40));
                textColor = Color.WHITE;
                hintTextColor = Color.LTGRAY;
            }
            layout.setBackground(bg);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + 名(targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            hint.setTextColor(textColor);
            hint.setPadding(0, 0, 0, dp2px(15));
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入头衔内容");
            inputEditText.setHintTextColor(hintTextColor);
            inputEditText.setTextColor(textColor);
            
            GradientDrawable etBg = new GradientDrawable();
            etBg.setColor(Color.argb(50, 0, 0, 0));
            etBg.setCornerRadius(dp2px(10));
            etBg.setStroke(dp2px(1), Color.argb(80, 0, 0, 0));
            if (getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK) {
                etBg.setColor(Color.argb(30, 255, 255, 255));
                etBg.setStroke(dp2px(1), Color.argb(60, 255, 255, 255));
            }
            inputEditText.setBackground(etBg);
            inputEditText.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
            
            layout.addView(inputEditText);
            
            builder.setView(layout);
            
            builder.setPositiveButton("确定设置", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int which) {
                    String input = inputEditText.getText().toString().trim();
                    if (!input.isEmpty()) {
                        setTitle(groupUin, targetUin, input);
                        toast("已经为 " + 名(targetUin) + " 设置了头衔: " + input);
                    } else {
                        toast("请输入头衔内容");
                    }
                }
            });
            
            builder.setNegativeButton("取消", null);
            
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    });
}