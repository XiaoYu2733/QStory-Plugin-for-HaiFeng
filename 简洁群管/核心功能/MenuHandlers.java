
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
import android.widget.EditText;

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

Map groupInfoCache = new ConcurrentHashMap();

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
            addTemporaryItem("自定骰子功能", "自定义骰子方法");
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
                int theme = getCurrentTheme();
                boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
                Object myInfo = getMemberInfo(groupUin, myUin);
                if (myInfo == null) return;
                
                String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
                String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
                String surfaceVariantColor = isDark ? MD3_DARK_SURFACE_VARIANT : MD3_SURFACE_VARIANT;
                String onSurfaceVariantColor = isDark ? MD3_DARK_ON_SURFACE_VARIANT : MD3_ON_SURFACE_VARIANT;
                
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
                builder.setTitle("快捷群管 —— " + 名(targetUin) + "(" + targetUin + ")");
                
                LinearLayout dialogLayout = new LinearLayout(getActivity());
                dialogLayout.setOrientation(LinearLayout.VERTICAL);
                dialogLayout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
                
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(Color.parseColor(surfaceColor));
                bg.setCornerRadius(dp2px(12));
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
                        TextView batchRevokeBtn = new TextView(getActivity());
                        batchRevokeBtn.setText("批量撤回");
                        batchRevokeBtn.setTextSize(14);
                        batchRevokeBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                        batchRevokeBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                        batchRevokeBtn.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                        batchRevokeBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable batchRevokeBg = new GradientDrawable();
                        batchRevokeBg.setColor(Color.parseColor(surfaceVariantColor));
                        batchRevokeBg.setCornerRadius(dp2px(20));
                        batchRevokeBtn.setBackground(batchRevokeBg);
                        
                        batchRevokeBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                showBatchRevokeDialog(msg);
                            }
                        });
                        
                        buttonList.add(batchRevokeBtn);
                        
                        TextView banBtn = new TextView(getActivity());
                        banBtn.setText("禁言");
                        banBtn.setTextSize(14);
                        banBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                        banBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                        banBtn.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                        banBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable banBg = new GradientDrawable();
                        banBg.setColor(Color.parseColor(surfaceVariantColor));
                        banBg.setCornerRadius(dp2px(20));
                        banBtn.setBackground(banBg);
                        
                        banBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                forbiddenMenuItem(msg);
                            }
                        });
                        
                        buttonList.add(banBtn);
                        
                        TextView revokeBtn = new TextView(getActivity());
                        revokeBtn.setText("撤回");
                        revokeBtn.setTextSize(14);
                        revokeBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                        revokeBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                        revokeBtn.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                        revokeBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable revokeBg = new GradientDrawable();
                        revokeBg.setColor(Color.parseColor(surfaceVariantColor));
                        revokeBg.setCornerRadius(dp2px(20));
                        revokeBtn.setBackground(revokeBg);
                        
                        revokeBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                Activity activity = getActivity();
                                if (activity == null) return;
                                
                                activity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        int theme = getCurrentTheme();
                                        boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
                                        builder.setTitle("确认撤回");
                                        
                                        String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
                                        String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
                                        String surfaceVariantColor = isDark ? MD3_DARK_SURFACE_VARIANT : MD3_SURFACE_VARIANT;
                                        
                                        LinearLayout layout = new LinearLayout(getActivity());
                                        layout.setOrientation(LinearLayout.VERTICAL);
                                        layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
                                        
                                        GradientDrawable bg = new GradientDrawable();
                                        bg.setColor(Color.parseColor(surfaceColor));
                                        bg.setCornerRadius(dp2px(12));
                                        layout.setBackground(bg);
                                        
                                        TextView message = new TextView(getActivity());
                                        message.setText("确定要撤回这条消息吗？");
                                        message.setTextSize(16);
                                        message.setTextColor(Color.parseColor(onSurfaceColor));
                                        message.setPadding(0, 0, 0, dp2px(16));
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
                        kickBtn.setTextSize(14);
                        kickBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                        kickBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                        kickBtn.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                        kickBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable kickBg = new GradientDrawable();
                        kickBg.setColor(Color.parseColor(surfaceVariantColor));
                        kickBg.setCornerRadius(dp2px(20));
                        kickBtn.setBackground(kickBg);
                        
                        kickBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                kickMenuItem(msg);
                            }
                        });
                        
                        buttonList.add(kickBtn);
                        
                        TextView kickBlackBtn = new TextView(getActivity());
                        kickBlackBtn.setText("踢黑");
                        kickBlackBtn.setTextSize(14);
                        kickBlackBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                        kickBlackBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                        kickBlackBtn.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                        kickBlackBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable kickBlackBg = new GradientDrawable();
                        kickBlackBg.setColor(Color.parseColor(surfaceVariantColor));
                        kickBlackBg.setCornerRadius(dp2px(20));
                        kickBlackBtn.setBackground(kickBlackBg);
                        
                        kickBlackBtn.setOnClickListener(new android.view.View.OnClickListener() {
                            public void onClick(android.view.View v) {
                                kickBlackMenuItem(msg);
                            }
                        });
                        
                        buttonList.add(kickBlackBtn);
                        
                        TextView blacklistBtn = new TextView(getActivity());
                        blacklistBtn.setText("加入黑名单");
                        blacklistBtn.setTextSize(14);
                        blacklistBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                        blacklistBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                        blacklistBtn.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                        blacklistBtn.setGravity(Gravity.CENTER);
                        
                        GradientDrawable blacklistBg = new GradientDrawable();
                        blacklistBg.setColor(Color.parseColor(surfaceVariantColor));
                        blacklistBg.setCornerRadius(dp2px(20));
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
                            allianceBanBtn.setTextSize(14);
                            allianceBanBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                            allianceBanBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                            allianceBanBtn.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                            allianceBanBtn.setGravity(Gravity.CENTER);
                            
                            GradientDrawable allianceBanBg = new GradientDrawable();
                            allianceBanBg.setColor(Color.parseColor(surfaceVariantColor));
                            allianceBanBg.setCornerRadius(dp2px(20));
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
                    titleBtn.setTextSize(14);
                    titleBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                    titleBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                    titleBtn.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                    titleBtn.setGravity(Gravity.CENTER);
                    
                    GradientDrawable titleBg = new GradientDrawable();
                    titleBg.setColor(Color.parseColor(surfaceVariantColor));
                    titleBg.setCornerRadius(dp2px(20));
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
                    noPermissionText.setTextSize(16);
                    noPermissionText.setTextColor(Color.parseColor(onSurfaceColor));
                    noPermissionText.setGravity(Gravity.CENTER);
                    noPermissionText.setPadding(dp2px(16), dp2px(24), dp2px(16), dp2px(24));
                    
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
                        params1.setMargins(0, 0, dp2px(6), dp2px(6));
                        btn1.setLayoutParams(params1);
                        rowLayout.addView(btn1);
                        
                        if (i + 1 < buttonList.size()) {
                            TextView btn2 = buttonList.get(i + 1);
                            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                            );
                            params2.setMargins(dp2px(6), 0, 0, dp2px(6));
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

public void showBatchRevokeDialog(final Object msg) {
    if (msg == null || !msg.IsGroup) return;
    
    final String groupUin = msg.GroupUin;
    final long baseSeq = Long.parseLong("" + msg.msg.msgSeq);
    
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            try {
                int theme = getCurrentTheme();
                boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
                builder.setTitle("批量撤回");
                
                String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
                String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
                String surfaceVariantColor = isDark ? MD3_DARK_SURFACE_VARIANT : MD3_SURFACE_VARIANT;
                String onSurfaceVariantColor = isDark ? MD3_DARK_ON_SURFACE_VARIANT : MD3_ON_SURFACE_VARIANT;
                
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
                
                GradientDrawable bg = new GradientDrawable();
                bg.setColor(Color.parseColor(surfaceColor));
                bg.setCornerRadius(dp2px(12));
                layout.setBackground(bg);
                
                TextView hint = new TextView(getActivity());
                hint.setText("从当前消息开始，批量撤回多条消息");
                hint.setTextSize(14);
                hint.setTextColor(Color.parseColor(onSurfaceColor));
                hint.setPadding(0, 0, 0, dp2px(16));
                layout.addView(hint);
                
                final EditText inputEditText = new EditText(getActivity());
                inputEditText.setHint("请输入撤回数量");
                inputEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                inputEditText.setText("5");
                inputEditText.setHintTextColor(Color.parseColor(onSurfaceVariantColor));
                inputEditText.setTextColor(Color.parseColor(onSurfaceColor));
                inputEditText.setTextSize(14);
                
                GradientDrawable etBg = new GradientDrawable();
                etBg.setColor(Color.parseColor(surfaceVariantColor));
                etBg.setCornerRadius(dp2px(8));
                inputEditText.setBackground(etBg);
                inputEditText.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
                
                layout.addView(inputEditText);
                
                LinearLayout buttonLayout = new LinearLayout(getActivity());
                buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
                buttonLayout.setGravity(Gravity.END);
                buttonLayout.setPadding(0, dp2px(16), 0, 0);
                
                TextView cancelBtn = new TextView(getActivity());
                cancelBtn.setText("取消");
                cancelBtn.setTextSize(14);
                cancelBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                cancelBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                cancelBtn.setGravity(Gravity.CENTER);
                cancelBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
                cancelBtn.setBackgroundResource(android.R.color.transparent);
                
                TextView prevBtn = new TextView(getActivity());
                prevBtn.setText("撤回前面");
                prevBtn.setTextSize(14);
                prevBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                prevBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                prevBtn.setGravity(Gravity.CENTER);
                prevBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
                prevBtn.setBackgroundResource(android.R.color.transparent);
                
                TextView nextBtn = new TextView(getActivity());
                nextBtn.setText("撤回后面");
                nextBtn.setTextSize(14);
                nextBtn.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
                nextBtn.setTextColor(Color.parseColor(MD3_PRIMARY));
                nextBtn.setGravity(Gravity.CENTER);
                nextBtn.setPadding(dp2px(16), dp2px(8), dp2px(16), dp2px(8));
                nextBtn.setBackgroundResource(android.R.color.transparent);
                
                buttonLayout.addView(cancelBtn);
                buttonLayout.addView(prevBtn);
                buttonLayout.addView(nextBtn);
                layout.addView(buttonLayout);
                
                builder.setView(layout);
                
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                
                cancelBtn.setOnClickListener(new android.view.View.OnClickListener() {
                    public void onClick(android.view.View v) {
                        dialog.dismiss();
                    }
                });
                
                prevBtn.setOnClickListener(new android.view.View.OnClickListener() {
                    public void onClick(android.view.View v) {
                        try {
                            int count = Integer.parseInt(inputEditText.getText().toString());
                            dialog.dismiss();
                            executeBatchRevoke(groupUin, baseSeq, count, -1);
                        } catch (Exception e) {
                            toast("请输入有效数字");
                        }
                    }
                });
                
                nextBtn.setOnClickListener(new android.view.View.OnClickListener() {
                    public void onClick(android.view.View v) {
                        try {
                            int count = Integer.parseInt(inputEditText.getText().toString());
                            dialog.dismiss();
                            executeBatchRevoke(groupUin, baseSeq, count, 1);
                        } catch (Exception e) {
                            toast("请输入有效数字");
                        }
                    }
                });
                
            } catch (Exception e) {
                toast("打开批量撤回失败");
            }
        }
    });
}

public void executeBatchRevoke(String gUin, long startSeq, int count, int dir) {
    getActivity().runOnUiThread(new Runnable() {
        public void run() {
            int theme = getCurrentTheme();
            boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            
            String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
            String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
            String surfaceVariantColor = isDark ? MD3_DARK_SURFACE_VARIANT : MD3_SURFACE_VARIANT;

            final android.app.Dialog progressDialog = new android.app.Dialog(getActivity(), getCurrentTheme());
            progressDialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
            progressDialog.setCancelable(false);

            LinearLayout root = new LinearLayout(getActivity());
            root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            root.setOrientation(LinearLayout.VERTICAL);
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.parseColor(surfaceColor));
            bg.setCornerRadius(dp2px(16));
            root.setBackground(bg);
            
            root.setPadding(dp2px(24), dp2px(24), dp2px(24), dp2px(24));

            TextView title = new TextView(getActivity());
            title.setText("正在撤回...");
            title.setTextSize(18);
            title.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
            title.setTextColor(Color.parseColor(onSurfaceColor));
            title.setPadding(0, 0, 0, dp2px(16));

            LinearLayout progressContainer = new LinearLayout(getActivity());
            progressContainer.setOrientation(LinearLayout.VERTICAL);
            
            LinearLayout progressBg = new LinearLayout(getActivity());
            progressBg.setBackgroundColor(Color.parseColor(surfaceVariantColor));
            progressBg.setPadding(1, 1, 1, 1);
            LinearLayout.LayoutParams bgParams = new LinearLayout.LayoutParams(-1, dp2px(8));
            bgParams.setMargins(0, 0, 0, dp2px(8));
            progressBg.setLayoutParams(bgParams);
            
            final android.view.View progressFill = new android.view.View(getActivity());
            progressFill.setBackgroundColor(Color.parseColor(MD3_PRIMARY));
            LinearLayout.LayoutParams fillParams = new LinearLayout.LayoutParams(0, -1, 0.0f);
            progressFill.setMinimumHeight(dp2px(6));
            progressFill.setLayoutParams(fillParams);
            
            progressBg.addView(progressFill);
            progressContainer.addView(progressBg);
            
            final TextView progressText = new TextView(getActivity());
            progressText.setText("0/" + count);
            progressText.setTextSize(14);
            progressText.setTextColor(Color.parseColor(onSurfaceColor));
            progressText.setGravity(Gravity.CENTER);
            progressText.setPadding(0, dp2px(4), 0, 0);
            
            root.addView(title);
            root.addView(progressContainer);
            root.addView(progressText);

            progressDialog.setContentView(root);
            
            android.view.Window window = progressDialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                android.view.WindowManager.LayoutParams params = window.getAttributes();
                params.width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.75);
                params.height = -2;
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
            }

            progressDialog.show();

            new Thread(new Runnable() {
                public void run() {
                    try {
                        long group = Long.parseLong(gUin);
                        for (int i = 0; i < count; i++) {
                            long seq = startSeq + (i * dir);
                            
                            org.json.JSONObject body = new org.json.JSONObject();
                            body.put("1", 1);
                            body.put("2", group);
                            org.json.JSONObject msgInfo = new org.json.JSONObject();
                            msgInfo.put("1", seq);
                            msgInfo.put("2", 0);
                            msgInfo.put("3", 0);
                            body.put("3", msgInfo);
                            body.put("4", new org.json.JSONObject().put("1", 0));

                            sendProto("trpc.msg.msg_svc.MsgService.SsoGroupRecallMsg", body.toString());

                            final float progress = (float)(i + 1) / count;
                            final int current = i + 1;
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) progressFill.getLayoutParams();
                                    params.weight = progress;
                                    progressFill.setLayoutParams(params);
                                    progressText.setText(current + "/" + count);
                                }
                            });
                            
                            Thread.sleep(1000);
                        }
                        
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                toast("批量撤回完成");
                            }
                        });
                        
                    } catch (Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                toast("撤回过程中出现错误");
                            }
                        });
                    }
                }
            }).start();
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
            int theme = getCurrentTheme();
            boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("联盟封禁 - " + 名(targetUin) + "(" + targetUin + ")");
            
            String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
            String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
            String surfaceVariantColor = isDark ? MD3_DARK_SURFACE_VARIANT : MD3_SURFACE_VARIANT;
            String onSurfaceVariantColor = isDark ? MD3_DARK_ON_SURFACE_VARIANT : MD3_ON_SURFACE_VARIANT;
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.parseColor(surfaceColor));
            bg.setCornerRadius(dp2px(12));
            layout.setBackground(bg);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + 名(targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            hint.setTextColor(Color.parseColor(onSurfaceColor));
            hint.setPadding(0, 0, 0, dp2px(16));
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入封禁原因，可填可不填");
            inputEditText.setHintTextColor(Color.parseColor(onSurfaceVariantColor));
            inputEditText.setTextColor(Color.parseColor(onSurfaceColor));
            inputEditText.setTextSize(14);
            
            GradientDrawable etBg = new GradientDrawable();
            etBg.setColor(Color.parseColor(surfaceVariantColor));
            etBg.setCornerRadius(dp2px(8));
            inputEditText.setBackground(etBg);
            inputEditText.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
            
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
            int theme = getCurrentTheme();
            boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认加入黑名单");
            
            String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
            String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.parseColor(surfaceColor));
            bg.setCornerRadius(dp2px(12));
            layout.setBackground(bg);
            
            TextView message = new TextView(getActivity());
            message.setText("确定要将 " + 名(targetUin) + "(" + targetUin + ") 加入黑名单并踢出吗？\n\n加入黑名单后，该用户再次入群时会被自动踢出。");
            message.setTextSize(15);
            message.setTextColor(Color.parseColor(onSurfaceColor));
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
            int theme = getCurrentTheme();
            boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认踢出");
            
            String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
            String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.parseColor(surfaceColor));
            bg.setCornerRadius(dp2px(12));
            layout.setBackground(bg);
            
            TextView message = new TextView(getActivity());
            message.setText("真的确定要踢出 " + 名(targetUin) + "(" + targetUin + ") 吗？");
            message.setTextSize(16);
            message.setTextColor(Color.parseColor(onSurfaceColor));
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
            int theme = getCurrentTheme();
            boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("确认踢黑");
            
            String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
            String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.parseColor(surfaceColor));
            bg.setCornerRadius(dp2px(12));
            layout.setBackground(bg);
            
            TextView message = new TextView(getActivity());
            message.setText("真的确定要踢出并拉黑 " + 名(targetUin) + "(" + targetUin + ") 吗？\n\n确定后，该用户无法再次加入该群聊");
            message.setTextSize(16);
            message.setTextColor(Color.parseColor(onSurfaceColor));
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
            int theme = getCurrentTheme();
            boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("设置禁言时间");
            
            String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
            String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
            String surfaceVariantColor = isDark ? MD3_DARK_SURFACE_VARIANT : MD3_SURFACE_VARIANT;
            String onSurfaceVariantColor = isDark ? MD3_DARK_ON_SURFACE_VARIANT : MD3_ON_SURFACE_VARIANT;
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.parseColor(surfaceColor));
            bg.setCornerRadius(dp2px(12));
            layout.setBackground(bg);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + 名(targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            hint.setTextColor(Color.parseColor(onSurfaceColor));
            hint.setPadding(0, 0, 0, dp2px(16));
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入禁言时间（秒）");
            inputEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            inputEditText.setHintTextColor(Color.parseColor(onSurfaceVariantColor));
            inputEditText.setTextColor(Color.parseColor(onSurfaceColor));
            inputEditText.setTextSize(14);
            
            GradientDrawable etBg = new GradientDrawable();
            etBg.setColor(Color.parseColor(surfaceVariantColor));
            etBg.setCornerRadius(dp2px(8));
            inputEditText.setBackground(etBg);
            inputEditText.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
            
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
            int theme = getCurrentTheme();
            boolean isDark = theme == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("设置头衔");
            
            String surfaceColor = isDark ? MD3_DARK_SURFACE : MD3_SURFACE;
            String onSurfaceColor = isDark ? MD3_DARK_ON_SURFACE : MD3_ON_SURFACE;
            String surfaceVariantColor = isDark ? MD3_DARK_SURFACE_VARIANT : MD3_SURFACE_VARIANT;
            String onSurfaceVariantColor = isDark ? MD3_DARK_ON_SURFACE_VARIANT : MD3_ON_SURFACE_VARIANT;
            
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(dp2px(24), dp2px(20), dp2px(24), dp2px(20));
            
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(Color.parseColor(surfaceColor));
            bg.setCornerRadius(dp2px(12));
            layout.setBackground(bg);
            
            TextView hint = new TextView(getActivity());
            hint.setText("目标用户: " + 名(targetUin) + "(" + targetUin + ")");
            hint.setTextSize(16);
            hint.setTextColor(Color.parseColor(onSurfaceColor));
            hint.setPadding(0, 0, 0, dp2px(16));
            layout.addView(hint);
            
            final EditText inputEditText = new EditText(getActivity());
            inputEditText.setHint("请输入头衔内容");
            inputEditText.setHintTextColor(Color.parseColor(onSurfaceVariantColor));
            inputEditText.setTextColor(Color.parseColor(onSurfaceColor));
            inputEditText.setTextSize(14);
            
            GradientDrawable etBg = new GradientDrawable();
            etBg.setColor(Color.parseColor(surfaceVariantColor));
            etBg.setCornerRadius(dp2px(8));
            inputEditText.setBackground(etBg);
            inputEditText.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(12));
            
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