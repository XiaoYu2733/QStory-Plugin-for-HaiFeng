
// 我将玫瑰藏于身后 也将喜欢藏于心底 从此 玫瑰与你不可提及

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.content.DialogInterface;
import java.util.ArrayList;

public void 禁言列表弹窗(String groupUin, String uin, int chatType) {
    Activity activity = getActivity();
    if (activity == null) return;
    activity.runOnUiThread(new Runnable() {
        public void run() {
            showForbiddenListDialog(activity, groupUin);
        }
    });
}

private void showForbiddenListDialog(Activity activity, String groupUin) {
    try {
        ArrayList<Object> forbiddenList = unifiedGetForbiddenList(groupUin);
        String groupName = 获取群名(groupUin);
        boolean isDark = getCurrentTheme() == AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        String cardColor = getCardColor();
        String textColor = getTextColor();
        String subTextColor = getSubTextColor();
        String accentColor = getAccentColor();
        String surfaceColor = getSurfaceColor();
        String borderColor = getBorderColor();
        String errorColor = "#FF3B30";

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, getCurrentTheme());
        builder.setTitle("禁言列表");

        LinearLayout contentLayout = new LinearLayout(activity);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(dp2px(16), dp2px(12), dp2px(16), dp2px(0));

        TextView groupInfo = new TextView(activity);
        groupInfo.setText("群：" + groupName + " (" + groupUin + ")");
        groupInfo.setTextSize(13);
        groupInfo.setTextColor(Color.parseColor(subTextColor));
        groupInfo.setPadding(0, 0, 0, dp2px(12));
        contentLayout.addView(groupInfo);

        ScrollView scrollView = new ScrollView(activity);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp2px(500)));

        LinearLayout listContainer = new LinearLayout(activity);
        listContainer.setOrientation(LinearLayout.VERTICAL);

        if (forbiddenList == null || forbiddenList.isEmpty()) {
            TextView emptyHint = new TextView(activity);
            emptyHint.setText("当前没有被禁言成员");
            emptyHint.setTextColor(Color.parseColor(subTextColor));
            emptyHint.setTextSize(14);
            emptyHint.setGravity(Gravity.CENTER);
            emptyHint.setPadding(0, dp2px(32), 0, dp2px(32));
            listContainer.addView(emptyHint);
        } else {
            long now = System.currentTimeMillis();
            ArrayList<Object> listCopy = safeCopyList(forbiddenList);
            for (int i = 0; i < listCopy.size(); i++) {
                Object info = listCopy.get(i);
                if (info == null) continue;
                try {
                    java.lang.reflect.Field uinField = info.getClass().getDeclaredField("UserUin");
                    uinField.setAccessible(true);
                    String userUin = (String) uinField.get(info);
                    if (userUin == null) continue;
                    java.lang.reflect.Field nameField = info.getClass().getDeclaredField("UserName");
                    nameField.setAccessible(true);
                    String userName = (String) nameField.get(info);
                    if (userName == null) userName = "";
                    java.lang.reflect.Field endField = info.getClass().getDeclaredField("Endtime");
                    endField.setAccessible(true);
                    long endTimeSec = endField.getLong(info);
                    if (endTimeSec > 1000000000000L) {
                        endTimeSec = endTimeSec / 1000;
                    }
                    long endTimeMillis = endTimeSec * 1000;
                    long remaining = endTimeMillis - now;
                    String timeDisplay;
                    if (remaining <= 0) {
                        timeDisplay = "已过期";
                    } else {
                        long days = remaining / (24 * 60 * 60 * 1000);
                        long hours = (remaining % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
                        long minutes = (remaining % (60 * 60 * 1000)) / (60 * 1000);
                        if (days > 0) {
                            timeDisplay = days + "天" + hours + "小时";
                        } else if (hours > 0) {
                            timeDisplay = hours + "小时" + minutes + "分";
                        } else {
                            timeDisplay = minutes + "分";
                        }
                    }

                    LinearLayout itemRow = new LinearLayout(activity);
                    itemRow.setOrientation(LinearLayout.HORIZONTAL);
                    itemRow.setGravity(Gravity.CENTER_VERTICAL);
                    itemRow.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                    GradientDrawable itemBg = getWebShape(surfaceColor, dp2px(10));
                    itemRow.setBackground(itemBg);
                    LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    rowParams.setMargins(0, 0, 0, dp2px(8));
                    itemRow.setLayoutParams(rowParams);

                    LinearLayout infoLayout = new LinearLayout(activity);
                    infoLayout.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                    infoLayout.setLayoutParams(infoParams);

                    TextView nameText = new TextView(activity);
                    nameText.setText(userName + " (" + userUin + ")");
                    nameText.setTextSize(14);
                    nameText.setTextColor(Color.parseColor(textColor));
                    infoLayout.addView(nameText);

                    TextView timeText = new TextView(activity);
                    timeText.setText("剩余 " + timeDisplay);
                    timeText.setTextSize(12);
                    timeText.setTextColor(Color.parseColor(subTextColor));
                    infoLayout.addView(timeText);

                    itemRow.addView(infoLayout);

                    Button unbanBtn = new Button(activity);
                    unbanBtn.setText("解禁");
                    unbanBtn.setTextSize(12);
                    unbanBtn.setTextColor(Color.WHITE);
                    unbanBtn.setGravity(Gravity.CENTER);
                    unbanBtn.setPadding(dp2px(12), dp2px(6), dp2px(12), dp2px(6));
                    unbanBtn.setAllCaps(false);
                    GradientDrawable unbanBg = getShape(accentColor, dp2px(8));
                    unbanBtn.setBackground(unbanBg);
                    LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    btnParams.setMargins(dp2px(8), 0, 0, 0);
                    unbanBtn.setLayoutParams(btnParams);

                    final String finalUin = userUin;
                    unbanBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            unifiedForbidden(groupUin, finalUin, 0);
                            toast("已解禁 " + finalUin);
                        }
                    });
                    itemRow.addView(unbanBtn);
                    listContainer.addView(itemRow);
                } catch (Exception e) {
                    continue;
                }
            }
        }

        scrollView.addView(listContainer);
        contentLayout.addView(scrollView);

        builder.setView(contentLayout);
        
        builder.setNegativeButton("关闭", null);
        builder.setNeutralButton("踢出禁言列表", null);
        builder.setPositiveButton("一键解禁", null);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        if (window != null) {
            GradientDrawable windowBg = getShape(cardColor, dp2px(20));
            window.setBackgroundDrawable(windowBg);
        }
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(activity, getCurrentTheme())
                    .setTitle("确认操作")
                    .setMessage("确定要踢出所有被禁言的成员吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int w) {
                            ArrayList<Object> list = unifiedGetForbiddenList(groupUin);
                            if (list != null) {
                                for (Object info : safeCopyList(list)) {
                                    try {
                                        java.lang.reflect.Field f = info.getClass().getDeclaredField("UserUin");
                                        f.setAccessible(true);
                                        String u = (String) f.get(info);
                                        if (u != null) unifiedKick(groupUin, u, false);
                                    } catch (Exception e) {}
                                }
                            }
                            toast("已踢出全部禁言成员");
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(activity, getCurrentTheme())
                    .setTitle("确认操作")
                    .setMessage("确定要解禁所有被禁言的成员吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int w) {
                            ArrayList<Object> list = unifiedGetForbiddenList(groupUin);
                            if (list != null) {
                                for (Object info : safeCopyList(list)) {
                                    try {
                                        java.lang.reflect.Field f = info.getClass().getDeclaredField("UserUin");
                                        f.setAccessible(true);
                                        String u = (String) f.get(info);
                                        if (u != null) unifiedForbidden(groupUin, u, 0);
                                    } catch (Exception e) {}
                                }
                            }
                            toast("已解禁全部成员");
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
            }
        });
    } catch (Exception e) {
        toast("打开禁言列表失败: " + e.getMessage());
    }
}