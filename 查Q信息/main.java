
// 海枫

// 就算今天我把话说得再绝 明天我还是会喜欢你

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

String configName = "qq_query";

void onMsg(Object msg) {
    String text = msg.MessageContent;
    String qq = msg.UserUin;
    String group = msg.GroupUin;
    
    if (text.startsWith("查等级") && msg.IsGroup && getBoolean("switch_level", group, false)) {
        String targetQq = extractQQ(text, "查等级", qq);
        if (targetQq != null) {
            queryLevel(group, targetQq);
        }
    }
    
    else if ((text.startsWith("查注册") || text.startsWith("查信息") || text.startsWith("查q信息")) && 
             msg.IsGroup && getBoolean("switch_register", group, false)) {
        String targetQq = extractQQ(text, text.split(" ")[0], qq);
        if (targetQq != null) {
            queryRegister(group, targetQq);
        }
    }
    
    else if (text.startsWith("查靓号") && msg.IsGroup && getBoolean("switch_lianghao", group, false)) {
        String targetQq = extractQQ(text, "查靓号", qq);
        if (targetQq != null) {
            queryLianghao(group, targetQq);
        }
    }
    
    else if ((text.startsWith("查业务") || text.startsWith("查会员")) && 
             msg.IsGroup && getBoolean("switch_vip", group, false)) {
        String targetQq = extractQQ(text, text.split(" ")[0], qq);
        if (targetQq != null) {
            queryVip(group, targetQq);
        }
    }
    
    else if (text.startsWith("他的线索") && msg.IsGroup && getBoolean("switch_clue", group, false)) {
        if (msg.mAtList != null && !msg.mAtList.isEmpty()) {
            for (String atQq : msg.mAtList) {
                sendClue(group, atQq);
            }
        } else {
            sendMsg(group, "", "请@要查询的用户");
        }
    }
}

void onCreateMenu(Object msg) {
    addMenuItem("查询该用户", "showQueryMenu");
}

void showQueryMenu(Object msg) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            String[] menuItems = {"查询等级", "查询注册信息", "查询靓号", "查询会员", "查询线索"};
            final String targetQq = msg.UserUin;
            
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getCurrentTheme());
            builder.setTitle("查询用户: " + targetQq);
            
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    new Thread(new Runnable() {
                        public void run() {
                            switch (which) {
                                case 0:
                                    queryLevelToast(targetQq);
                                    break;
                                case 1:
                                    queryRegisterToast(targetQq);
                                    break;
                                case 2:
                                    queryLianghaoToast(targetQq);
                                    break;
                                case 3:
                                    queryVipToast(targetQq);
                                    break;
                                case 4:
                                    sendClueToast(targetQq);
                                    break;
                            }
                        }
                    }).start();
                }
            };
            
            builder.setItems(menuItems, listener);
            builder.show();
        }
    });
}

void queryLevelToast(String qq) {
    try {
        String url = "https://api.s01s.cn/API/wddj1/?qq=" + qq;
        String response = httpGet(url);
        
        if (response != null && !response.isEmpty()) {
            String[] lines = response.split("\n");
            StringBuilder result = new StringBuilder();
            result.append("QQ等级查询 - ").append(qq).append("\n");
            
            for (String line : lines) {
                if (line.contains("：")) {
                    String[] parts = line.split("：", 2);
                    if (parts.length == 2) {
                        result.append(parts[0].trim()).append("：").append(parts[1].trim()).append("\n");
                    }
                }
            }
            
            customToast(result.toString());
        } else {
            customToast("查询失败，请稍后重试");
        }
    } catch (Exception e) {
        error(e);
        customToast("查询出错");
    }
}

void queryRegisterToast(String qq) {
    try {
        String url = "https://api.s01s.cn/API/zcsj/?qq=" + qq + "&key=2CF26951C676294E28FE136326A29942";
        String response = httpGet(url);
        
        if (response != null && !response.isEmpty()) {
            String[] lines = response.split("\n");
            StringBuilder result = new StringBuilder();
            result.append("QQ注册信息 - ").append(qq).append("\n");
            
            for (String line : lines) {
                if (line.contains("：")) {
                    String[] parts = line.split("：", 2);
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        if (!value.isEmpty() && !value.equals("未知")) {
                            result.append(key).append("：").append(value).append("\n");
                        }
                    }
                }
            }
            
            customToast(result.toString());
        } else {
            customToast("查询失败，请稍后重试");
        }
    } catch (Exception e) {
        error(e);
        customToast("查询出错");
    }
}

void queryLianghaoToast(String qq) {
    try {
        String url = "https://api.s01s.cn/API/clh/?uin=" + qq;
        String response = httpGet(url);
        
        if (response != null && !response.isEmpty()) {
            customToast("靓号查询 - " + qq + "\n" + response);
        } else {
            customToast("查询失败，请稍后重试");
        }
    } catch (Exception e) {
        error(e);
        customToast("查询出错");
    }
}

void queryVipToast(String qq) {
    try {
        String url = "https://api.s01s.cn/API/chy/?qq=" + qq;
        String response = httpGet(url);
        
        if (response != null && !response.isEmpty()) {
            JSONObject json = new JSONObject(response);
            StringBuilder result = new StringBuilder();
            result.append("会员业务查询 - ").append(qq).append("\n");
            
            result.append("会员状态：").append(json.optString("vip", "未知")).append("\n");
            result.append("会员到期：").append(json.optString("vipe", "未知")).append("\n");
            result.append("超会状态：").append(json.optString("svip", "未知")).append("\n");
            result.append("超会到期：").append(json.optString("svipe", "未知")).append("\n");
            result.append("会员等级：").append(json.optString("hydj", "未知")).append("\n");
            result.append("活跃天数：").append(json.optString("hyts", "未知")).append("\n");
            
            customToast(result.toString());
        } else {
            customToast("查询失败，请稍后重试");
        }
    } catch (Exception e) {
        error(e);
        customToast("查询出错");
    }
}

void sendClueToast(String qq) {
    try {
        String url = "https://ti.qq.com/friends/recall?uin=" + qq;
        customToast("线索查询 - " + qq + "\n请复制链接查看：\n" + url);
    } catch (Exception e) {
        error(e);
        customToast("生成链接失败");
    }
}

public int getCurrentTheme() {
    try {
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

public boolean isDarkMode() {
    int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
}

public String getBackgroundColor() {
    return isDarkMode() ? "#CC1E1E1E" : "#CCFFFFFF";
}

public String getTextColor() {
    return isDarkMode() ? "#E0E0E0" : "#333333";
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

public void customToast(String text) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() {
            try {
                if (getActivity() != null) {
                    String bgColor = getBackgroundColor();
                    String textColor = getTextColor();
                    
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    
                    int paddingHorizontal = c(18);
                    int paddingVertical = c(12);
                    linearLayout.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                    
                    linearLayout.setBackground(getShape(bgColor, c(12), 230));
                    
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(Color.parseColor(textColor));
                    textView.setTextSize(14.5f);
                    textView.setText(text);
                    textView.setGravity(Gravity.CENTER);
                    
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

String extractQQ(String text, String command, String defaultQq) {
    String content = text.replace(command, "").trim();
    if (content.isEmpty()) {
        return defaultQq;
    }
    
    String qq = content.replaceAll("[^0-9]", "");
    if (qq.length() >= 5 && qq.length() <= 15) {
        return qq;
    }
    
    return defaultQq;
}

void queryLevel(String group, String qq) {
    try {
        String url = "https://api.s01s.cn/API/wddj1/?qq=" + qq;
        String response = httpGet(url);
        
        if (response != null && !response.isEmpty()) {
            String[] lines = response.split("\n");
            StringBuilder result = new StringBuilder();
            result.append("=== QQ等级查询 ===\n");
            
            for (String line : lines) {
                if (line.contains("：")) {
                    String[] parts = line.split("：", 2);
                    if (parts.length == 2) {
                        result.append(parts[0].trim()).append("：").append(parts[1].trim()).append("\n");
                    }
                }
            }
            
            sendMsg(group, "", "[pic=https://q.qlogo.cn/g?b=qq&s=0&nk=" + qq + "]\n" + result.toString());
        } else {
            sendMsg(group, "", "查询失败，请稍后重试");
        }
    } catch (Exception e) {
        error(e);
        sendMsg(group, "", "查询出错");
    }
}

void queryRegister(String group, String qq) {
    try {
        String url = "https://api.s01s.cn/API/zcsj/?qq=" + qq + "&key=2CF26951C676294E28FE136326A29942";
        String response = httpGet(url);
        
        if (response != null && !response.isEmpty()) {
            String[] lines = response.split("\n");
            StringBuilder result = new StringBuilder();
            result.append("=== QQ注册信息 ===\n");
            
            for (String line : lines) {
                if (line.contains("：")) {
                    String[] parts = line.split("：", 2);
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        if (!value.isEmpty() && !value.equals("未知")) {
                            result.append(key).append("：").append(value).append("\n");
                        }
                    }
                }
            }
            
            sendMsg(group, "", result.toString());
        } else {
            sendMsg(group, "", "查询失败，请稍后重试");
        }
    } catch (Exception e) {
        error(e);
        sendMsg(group, "", "查询出错");
    }
}

void queryLianghao(String group, String qq) {
    try {
        String url = "https://api.s01s.cn/API/clh/?uin=" + qq;
        String response = httpGet(url);
        
        if (response != null && !response.isEmpty()) {
            sendMsg(group, "", "=== 靓号查询 ===\n" + response);
        } else {
            sendMsg(group, "", "查询失败，请稍后重试");
        }
    } catch (Exception e) {
        error(e);
        sendMsg(group, "", "查询出错");
    }
}

void queryVip(String group, String qq) {
    try {
        String url = "https://api.s01s.cn/API/chy/?qq=" + qq;
        String response = httpGet(url);
        
        if (response != null && !response.isEmpty()) {
            JSONObject json = new JSONObject(response);
            StringBuilder result = new StringBuilder();
            result.append("=== 会员业务查询 ===\n");
            
            result.append("大会状态：").append(json.optString("dhy", "未知")).append("\n");
            result.append("总成长值：").append(json.optString("dhycz", "未知")).append("\n");
            result.append("年大状态：").append(json.optString("ndhy", "未知")).append("\n");
            result.append("活跃天数：").append(json.optString("hyts", "未知")).append("\n");
            result.append("会员等级：").append(json.optString("hydj", "未知")).append("\n");
            result.append("会员状态：").append(json.optString("vip", "未知")).append("\n");
            result.append("会员到期：").append(json.optString("vipe", "未知")).append("\n");
            result.append("超会状态：").append(json.optString("svip", "未知")).append("\n");
            result.append("超会到期：").append(json.optString("svipe", "未知")).append("\n");
            result.append("年会状态：").append(json.optString("nvip", "未知")).append("\n");
            result.append("年会到期：").append(json.optString("nsvipe", "未知")).append("\n");
            result.append("总成长值：").append(json.optString("zccz", "未知")).append("\n");
            result.append("总魅力值：").append(json.optString("mccz", "未知")).append("\n");
            
            sendMsg(group, "", "[pic=https://q.qlogo.cn/g?b=qq&s=0&nk=" + qq + "]\n" + result.toString());
        } else {
            sendMsg(group, "", "查询失败，请稍后重试");
        }
    } catch (Exception e) {
        error(e);
        sendMsg(group, "", "查询出错");
    }
}

void sendClue(String group, String qq) {
    try {
        String url = "https://ti.qq.com/friends/recall?uin=" + qq;
        String qrcodeUrl = "https://api.suyanw.cn/api/qrcode.php?text=" + url + "&size=180";
        
        sendMsg(group, "", "请扫码查看" + qq + "的线索：[pic=" + qrcodeUrl + "]");
    } catch (Exception e) {
        error(e);
        sendMsg(group, "", "生成二维码失败");
    }
}

sendLike("2133115301",20);

String httpGet(String urlStr) {
    StringBuilder response = new StringBuilder();
    HttpURLConnection connection = null;
    try {
        URL url = new URL(urlStr);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append("\n");
            }
            in.close();
        }
    } catch (Exception e) {
        error(e);
    } finally {
        if (connection != null) {
            connection.disconnect();
        }
    }
    return response.toString().trim();
}

addItem("开启查等级", "toggleLevel");
addItem("开启查注册", "toggleRegister");
addItem("开启查靓号", "toggleLianghao");
addItem("开启查会员", "toggleVip");
addItem("开启查线索", "toggleClue");

public void toggleLevel(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        customToast("仅支持群聊");
        return;
    }
    boolean current = getBoolean("switch_level", groupUin, false);
    putBoolean("switch_level", groupUin, !current);
    customToast(!current ? "已开启查等级功能" : "已关闭查等级功能");
}

public void toggleRegister(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        customToast("仅支持群聊");
        return;
    }
    boolean current = getBoolean("switch_register", groupUin, false);
    putBoolean("switch_register", groupUin, !current);
    customToast(!current ? "已开启查注册功能" : "已关闭查注册功能");
}

public void toggleLianghao(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        customToast("仅支持群聊");
        return;
    }
    boolean current = getBoolean("switch_lianghao", groupUin, false);
    putBoolean("switch_lianghao", groupUin, !current);
    customToast(!current ? "已开启查靓号功能" : "已关闭查靓号功能");
}

public void toggleVip(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        customToast("仅支持群聊");
        return;
    }
    boolean current = getBoolean("switch_vip", groupUin, false);
    putBoolean("switch_vip", groupUin, !current);
    customToast(!current ? "已开启查会员功能" : "已关闭查会员功能");
}

public void toggleClue(String groupUin, String uin, int chatType) {
    if (chatType != 2) {
        customToast("仅支持群聊");
        return;
    }
    boolean current = getBoolean("switch_clue", groupUin, false);
    putBoolean("switch_clue", groupUin, !current);
    customToast(!current ? "已开启查线索功能" : "已关闭查线索功能");
}

customToast("QQ信息查询脚本加载成功！使用菜单开启各项功能");