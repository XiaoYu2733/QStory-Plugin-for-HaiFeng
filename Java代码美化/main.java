// 作 海枫

// 近况不与旧人讲 过往不该新人知

import java.util.HashMap;
import java.util.regex.*;
import android.app.*;
import android.widget.*;
import android.content.*;
import android.text.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public void beautiful(String groupUin, String uin, int chatType) {
    Activity activity = getActivity();
    if (activity == null) {
        toast("请在前台使用");
        return;
    }
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final EditText code = new EditText(activity);
            new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("输入需要美化的代码").setView(code).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                String t = ("" + code.getText()).replaceAll("^[\\s]*", "").replace("\\\\", "[反斜杠]").replace("\\\"", "[双引号]").replace("\\'", "[单引号]");
                                HashMap hs = new HashMap();
                                HashMap hs2 = new HashMap();
                                HashMap hs3 = new HashMap();
                                while (t.matches("[\\s\\S]*\"[^\"]*\"[\\s\\S]*")) {
                                    String gets = get(t, "\"[^\"]*\"");
                                    String core = "%%" + hs.size() + "%%";
                                    hs.put(core, gets.replace("[反斜杠]", "\\\\").replace("[双引号]", "\\\"").replace("[单引号]", "\\'"));
                                    t = replace(t, gets, core, 1);
                                }
                                while (t.matches("[\\s\\S]*'[^']*'[\\s\\S]*")) {
                                    String gets = get(t, "'[^']*'");
                                    String core = "%%" + hs.size() + "%%";
                                    hs.put(core, gets.replace("[反斜杠]", "\\\\").replace("[双引号]", "\\\"").replace("[单引号]", "\\'"));
                                    t = replace(t, gets, core, 1);
                                }
                                while (t.matches("[\\s\\S]*//[^\\n]*[\\s\\S]*")) {
                                    String gets = get(t, "//[^\\n]*");
                                    String core = "%^" + hs2.size() + "^%;";
                                    hs2.put(core, gets);
                                    t = replace(t, gets, core, 1);
                                }
                                while (t.matches("[\\s\\S]*for[\\s]*\\([^\\)]*\\)[\\s\\S]*")) {
                                    String gets = get(t, "for[\\s]*\\([^\\)]*\\)");
                                    String core = "^%" + hs3.size() + "%^";
                                    hs3.put(core, gets.replace("\n", "").replaceAll("[\\s]*;[\\s]*", ";").replaceAll("\\([\\s]*", "(").replaceAll("[\\s]*\\)", ")").replaceAll("( )+", " "));
                                    t = replace(t, gets, core, 1);
                                }
                                t = t.replace("*/", "*/;").replaceAll("\\{[\\s]*\\}", "%%-1%%;").replaceAll("\\([\\s]*", "(").replaceAll("[\\s]*\\)", ")").replaceAll("[\\s]*,[\\s]*", ",").replaceAll("[\\s]*\\n", "\n").replaceAll("\\n[\\s]*", " ").replaceAll("\\}[\\s]*", "}").replaceAll(";[\\s]*", ";").replaceAll("\\{[\\s]*", "{").replaceAll("[\\s]*\\{", "{") + "\n";
                                String s;
                                while ((s = get(t, "[\\S][^\\n]*( ){2,}[^\\n]*\\n")) != null) {
                                    t = replace(t, s, s.replaceAll("( ){2,}", " "), 1);
                                }
                                int tabs = 0;
                                for (int i = 0; i < t.length(); i++) {
                                    if (t.toCharArray()[i] == ';') {
                                        StringBuilder sb = new StringBuilder(t);
                                        sb.insert(i + 1, "\n" + ns(" ", tabs));
                                        t = sb.toString();
                                    } else if (t.toCharArray()[i] == '{') {
                                        boolean f = false;
                                        for (int j = i - 1; j >= 0; j--) {
                                            if (t.toCharArray()[j] == ']' || t.toCharArray()[j] == '=') {
                                                f = true;
                                                i = findRight(t, i) + 1;
                                                break;
                                            }
                                            if (!("" + t.toCharArray()[j]).matches("[\\s]")) {
                                                break;
                                            }
                                        }
                                        if (f) continue;
                                        tabs += 4;
                                        StringBuilder sb = new StringBuilder(t);
                                        sb.insert(i + 1, "\n" + ns(" ", tabs));
                                        t = sb.toString();
                                    } else if (t.toCharArray()[i] == '}') {
                                        StringBuilder sb = new StringBuilder(t);
                                        if (t.toCharArray()[i - 1] == ' ') {
                                            tabs -= 4;
                                            sb.delete(i - 4, i);
                                            i -= 4;
                                        } else {
                                            sb.insert(i, "\n" + ns(" ", tabs));
                                            i += 4;
                                        }
                                        t = sb.toString();
                                    }
                                }
                                t = t.replace("*/;", "*/").replace("%%-1%%;", "{}");
                                for (int i = 0; i < hs3.size(); i++) {
                                    t = t.replace("^%" + i + "%^", (String) hs3.get("^%" + i + "%^"));
                                }
                                for (int i = 0; i < hs2.size(); i++) {
                                    t = t.replace("%^" + i + "^%;", (String) hs2.get("%^" + i + "^%;"));
                                }
                                for (int i = 0; i < hs.size(); i++) {
                                    t = t.replace("%%" + i + "%%", (String) hs.get("%%" + i + "%%"));
                                }
                                final String result = t;
                                Activity activity = getActivity();
                                if (activity == null) return;
                                activity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        EditText et = new EditText(activity);
                                        et.setText(result.toCharArray(), 0, result.length());
                                        new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("美化结果").setView(et).setPositiveButton("确定", null).show();
                                    }
                                });
                            } catch (Exception e) {
                                Activity activity = getActivity();
                                if (activity != null) {
                                    activity.runOnUiThread(new Runnable() {
                                        public void run() {
                                            toast("处理失败，代码可能过长或格式异常");
                                        }
                                    });
                                }
                            }
                        }
                    }).start();
                }
            }).setNegativeButton("取消", null).show();
        }
    });
}

public int findRight(String s, int l) {
    int left = 0;
    char right;
    switch (s.toCharArray()[l]) {
        case '{':
            right = '}';
            break;
        case '[':
            right = ']';
            break;
        case '(':
            right = ')';
            break;
        default:
            return -2;
    }
    for (int i = l + 1; i < s.length(); i++) {
        if (s.toCharArray()[i] == s.toCharArray()[l]) {
            left++;
        } else if (s.toCharArray()[i] == right) {
            if (left == 0) return i;
            left--;
        }
    }
    return -1;
}

public String ns(String s, int n) {
    String t = "";
    for (int i = 0; i < n; i++) {
        t += s;
    }
    return t;
}

public String get(String s, String rex, int group) {
    Pattern p = Pattern.compile(rex);
    Matcher m = p.matcher(s);
    if (m.find()) return m.group(group);
    return null;
}

public String get(String s, String rex) {
    return get(s, rex, 0);
}

public String replace(String s, String from, String to, int n) {
    int last = 0;
    for (int i = 0; i < n; i++) {
        last = s.indexOf(from);
        s = s.substring(0, last) + to + s.substring(last + from.length());
    }
    return s;
}

public void showUpdateLog(String g, String u, int t) {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("退群拉黑更新日志");
            builder.setMessage(" " +
                    "- [修复] 过长的代码可能会导致崩溃 现在修复了这个问题，但是 过长的代码 执行较慢\n\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

sendLike("2133115301",20);

addItem("点击美化代码", "beautiful");
addItem("本次更新日志","showUpdateLog");