
// 作 海枫

// 我羡慕被人幸福 因为我没有 所以我不抢

import android.app.*;
import android.widget.*;
import android.content.*;
import android.text.*;
import java.util.regex.*;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

load(appPath + "/Convert.java");

addItem("切换样式", "item");

public void item(String groupUin, String userUin, int type){
    Activity activity = getActivity();
    activity.setTheme(android.R.style.Theme_Material_NoActionBar_Fullscreen);
    activity.runOnUiThread(new Runnable() {
        public void run() {
            final String[] choiceItems = new String[] {"衬线粗体", "衬线斜体", "衬线粗斜体", "手写体", "手写粗体", "哥特体", "双线体", "哥特粗体", "无衬线体", "无衬线粗体", "无衬线斜体", "无衬线粗斜体", "等宽字体", "默认"};
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(choiceItems, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int index) {
                    putString("AutoStyle", "style", choiceItems[index]);
                }
            });
            builder.show();
        }
    });
}

private Pattern pattern = Pattern.compile("\\[PicUrl=\\S*?\\]");

public String getMsg(String msg, String uin, int type) {
    StringBuilder sb = new StringBuilder();
    Matcher matcher = pattern.matcher(msg);
    int index = 0;
    while(matcher.find()) {
        sb.append(convert(msg.substring(index, matcher.start())));
        sb.append(matcher.group(0));
        index = matcher.end();
    }
    sb.append(convert(msg.substring(index)));
    return sb.toString();
}

private String convert(String source) {
    String style = getString("AutoStyle", "style");
    style = style == null ? "" : style;
    switch(style) {
        case "衬线粗体":
            source = Convert.replaceMap(source, Convert.衬线粗体);
            break;
        case "衬线斜体":
            source = Convert.replaceMap(source, Convert.衬线斜体);
            break;
        case "衬线粗斜体":
            source = Convert.replaceMap(source, Convert.衬线粗斜体);
            break;
        case "手写体":
            source = Convert.replaceMap(source, Convert.手写体);
            break;
        case "手写粗体":
            source = Convert.replaceMap(source, Convert.手写粗体);
            break;
        case "哥特体":
            source = Convert.replaceMap(source, Convert.哥特体);
            break;
        case "双线体":
            source = Convert.replaceMap(source, Convert.双线体);
            break;
        case "哥特粗体":
            source = Convert.replaceMap(source, Convert.哥特粗体);
            break;
        case "无衬线体":
            source = Convert.replaceMap(source, Convert.无衬线体);
            break;
        case "无衬线粗体":
            source = Convert.replaceMap(source, Convert.无衬线粗体);
            break;
        case "无衬线斜体":
            source = Convert.replaceMap(source, Convert.无衬线斜体);
            break;
        case "无衬线粗斜体":
            source = Convert.replaceMap(source, Convert.无衬线粗斜体);
            break;
        case "等宽字体":
            source = Convert.replaceMap(source, Convert.等宽字体);
            break;
        default:
            break;
    }
    return source;
}

sendLike("2133115301",20);