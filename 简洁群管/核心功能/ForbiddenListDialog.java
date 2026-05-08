
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
        String groupName = 获取群名(groupUin);
        String cardColor = getCardColor();
        String textColor = getTextColor();
        String subTextColor = getSubTextColor();
        String accentColor = getAccentColor();
        String surfaceColor = getSurfaceColor();

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

        final LinearLayout listContainer = new LinearLayout(activity);
        listContainer.setOrientation(LinearLayout.VERTICAL);

        final Runnable refreshList = new Runnable() {
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        listContainer.removeAllViews();
                        ArrayList<Object> currentList = getForbiddenList(groupUin);
                        
                        if (currentList == null || currentList.isEmpty()) {
                            TextView emptyHint = new TextView(activity);
                            emptyHint.setText("当前没有被禁言成员");
                            emptyHint.setTextColor(Color.parseColor(subTextColor));
                            emptyHint.setTextSize(14);
                            emptyHint.setGravity(Gravity.CENTER);
                            emptyHint.setPadding(0, dp2px(32), 0, dp2px(32));
                            listContainer.addView(emptyHint);
                            return;
                        }

                        long now = System.currentTimeMillis();
                        for (Object info : currentList) {
                            if (info == null) continue;
                            try {
                                java.lang.reflect.Field uinField = info.getClass().getDeclaredField("UserUin");
                                uinField.setAccessible(true);
                                String userUin = (String) uinField.get(info);
                                
                                java.lang.reflect.Field nameField = info.getClass().getDeclaredField("UserName");
                                nameField.setAccessible(true);
                                String userName = (String) nameField.get(info);
                                
                                java.lang.reflect.Field endField = info.getClass().getDeclaredField("Endtime");
                                endField.setAccessible(true);
                                long endTimeSec = endField.getLong(info);
                                
                                long endTimeMillis = (endTimeSec > 1000000000000L) ? endTimeSec : endTimeSec * 1000;
                                long remaining = endTimeMillis - now;
                                
                                if (remaining <= 0) continue;

                                String timeDisplay;
                                long minutes = remaining / (60 * 1000);
                                if (minutes > 60 * 24) {
                                    timeDisplay = (minutes / (60 * 24)) + "天";
                                } else if (minutes > 60) {
                                    timeDisplay = (minutes / 60) + "小时";
                                } else {
                                    timeDisplay = minutes + "分";
                                }

                                LinearLayout itemRow = new LinearLayout(activity);
                                itemRow.setOrientation(LinearLayout.HORIZONTAL);
                                itemRow.setGravity(Gravity.CENTER_VERTICAL);
                                itemRow.setPadding(dp2px(12), dp2px(10), dp2px(12), dp2px(10));
                                itemRow.setBackground(getWebShape(surfaceColor, dp2px(10)));
                                LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(-1, -2);
                                rowParams.setMargins(0, 0, 0, dp2px(8));
                                itemRow.setLayoutParams(rowParams);

                                LinearLayout infoLayout = new LinearLayout(activity);
                                infoLayout.setOrientation(LinearLayout.VERTICAL);
                                infoLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));

                                TextView nameText = new TextView(activity);
                                nameText.setText((userName == null ? "" : userName) + " (" + userUin + ")");
                                nameText.setTextSize(14);
                                nameText.setTextColor(Color.parseColor(textColor));
                                infoLayout.addView(nameText);

                                TextView timeText = new TextView(activity);
                                timeText.setText("剩余约 " + timeDisplay);
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
                                        forbidden(groupUin, finalUin, 0);
                                        toast("已发送解禁请求: " + finalUin);
                                        listContainer.postDelayed(refreshList, 200);
                                    }
                                });
                                itemRow.addView(unbanBtn);
                                listContainer.addView(itemRow);
                            } catch (Exception e) {}
                        }
                    }
                });
            }
        };

        refreshList.run();
        scrollView.addView(listContainer);
        contentLayout.addView(scrollView);
        builder.setView(contentLayout);
        
        builder.setNegativeButton("关闭", null);
        builder.setNeutralButton("踢出禁言列表", null);
        builder.setPositiveButton("一键解禁", null);

        final AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(getShape(cardColor, dp2px(20)));
        }
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<Object> list = getForbiddenList(groupUin);
                if (list != null) {
                    for (Object info : list) {
                        try {
                            java.lang.reflect.Field f = info.getClass().getDeclaredField("UserUin");
                            f.setAccessible(true);
                            String u = (String) f.get(info);
                            if (u != null) kick(groupUin, u, false);
                        } catch (Exception e) {}
                    }
                }
                toast("正在踢出所有禁言成员...");
                listContainer.postDelayed(refreshList, 300);
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<Object> list = getForbiddenList(groupUin);
                if (list != null) {
                    for (Object info : list) {
                        try {
                            java.lang.reflect.Field f = info.getClass().getDeclaredField("UserUin");
                            f.setAccessible(true);
                            String u = (String) f.get(info);
                            if (u != null) forbidden(groupUin, u, 0);
                        } catch (Exception e) {}
                    }
                }
                toast("正在一键解禁...");
                listContainer.postDelayed(refreshList, 300);
            }
        });
    } catch (Exception e) {
        toast("操作失败: " + e.getMessage());
    }
}