
// 作 海枫

// 你对我的感情 到底是朋友还是恋人般的喜欢呢

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

addItem("使用方法", "showUpdateLog");

public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String qq = msg.UserUin;
    String qun = msg.GroupUin;
    
    if(text.startsWith("code ")) {
        int a = text.indexOf("\n");
        String lang = text.substring(0, a).substring(5).toLowerCase();
        String command = text.substring(a + 1).replaceAll("\\\\", "\\\\\\\\").replaceAll("\n", "\\\\n").replaceAll("\"", "\\\\\"");
        
        String FileName = "";
        switch(lang) {
            case "bash":
            case "sh":
                lang = "bash";
                FileName = "sh";
                break;
            case "py":
            case "python":
                lang = "python";
                FileName = "py";
                break;
            case "c":
                lang = "c";
                FileName = "c";
                break;
            case "c++":
            case "cpp":
                lang = "cpp";
                FileName = "cpp";
                break;
            case "js":
            case "javascript":
                lang = "javascript";
                FileName = "js";
                break;
            case "kt":
            case "kotlin":
                lang = "kotlin";
                FileName = "kt";
                break;
            case "lua":
                lang = "lua";
                FileName = "lua";
                break;
            case "go":
            case "golang":
                lang = "go";
                FileName = "go";
                break;
            case "c#":
                lang = "csharp";
                FileName = "cs";
                break;
            case "java":
                lang = "java";
                FileName = "java";
                break;
            case "php":
                lang = "php";
                FileName = "php";
                break;
            case "groovy":
                lang = "groovy";
                FileName = "groovy";
                break;
            case "rust":
                lang = "rust";
                FileName = "rs";
                break;
            case "typescript":
            case "ts":
                lang = "typescript";
                FileName = "ts";
                break;
            case "perl":
                lang = "perl";
                FileName = "pl";
                break;
            case "nix":
                lang = "nix";
                FileName = "nix";
                break;
            default:
                sendMsg(qun, "", "未知语言" + lang);
                return;
        }
        
        String postData = httppost("https://glot.io/run/" + lang + "?version=latest", "", "{\"files\":[{\"name\":\"main." + FileName + "\",\"content\":\"" + command + "\"}],\"stdin\":\"\",\"command\":\"\"}");
        
        JSONObject jsonData = new JSONObject(postData);
        if(!jsonData.optString("message").equals("")) {
            sendMsg(qun, "", jsonData.getString("message"));
            return;
        }
        
        String out = jsonData.getString("stdout") + jsonData.getString("stderr") + jsonData.getString("error");
        if(out.equals("")) out = "执行成功，但是没有输出";
        sendMsg(qun, "", out.replaceAll("\\\\n", "\n"));
    }
}

public String httppost(String urlPath, String cookie, String data) {
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    HttpURLConnection uc = null;
    
    try {
        URL url = new URL(urlPath);
        uc = (HttpURLConnection) url.openConnection();
        uc.setDoInput(true);
        uc.setDoOutput(true);
        uc.setConnectTimeout(10000);
        uc.setReadTimeout(20000);
        uc.setRequestMethod("POST");
        uc.setRequestProperty("Content-Type", "application/json");
        uc.setRequestProperty("Cookie", cookie);
        uc.getOutputStream().write(data.getBytes("UTF-8"));
        uc.getOutputStream().flush();
        uc.getOutputStream().close();
        
        if(uc.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
            isr = new InputStreamReader(uc.getErrorStream(), "utf-8");
        } else {
            isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        }
        
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if(isr != null) {
                isr.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        if(uc != null) {
            uc.disconnect();
        }
    }
    
    if(buffer.length() == 0) return buffer.toString();
    buffer.delete(buffer.length() - 1, buffer.length());
    return buffer.toString();
}

public void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("使用方法");
            builder.setMessage("海枫qwq\n\n使用方法\n\n模拟运行各种语言\n指令：\ncode 语言\n要执行的语句\n比如：\ncode python\nprint(\"114514\")\n目前支持的语言：bash python c c++ javascript kotlin lua go c# java php groovy rust typescript perl nix\n\n反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

sendLike("2133115301",20);