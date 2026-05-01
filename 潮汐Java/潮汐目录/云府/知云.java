
//秩河


//你有问题就去百度阿
import android.view.*;
import android.app.*;
import android.widget.*;
import android.content.*;
import android.text.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.webkit.*;
import android.graphics.Color;
import java.awt.*;

public void TC(String url){
  Thisactivity = getActivity();
Thisactivity.runOnUiThread(new Runnable(){
public void run(){
WebView w1=new WebView(Thisactivity);
w1.getSettings().setJavaScriptEnabled(true);//getSettings()方法设置一些浏览器的属性，setJavaScriptEnabled(true)设置是否支持Javascript
w1.setWebViewClient(new WebViewClient());//
w1.loadUrl(url);
w1.clearCache(true); 
w1.clearHistory();
w1.setWebViewClient(new WebViewClient()
{
public boolean shouldOverrideUrlLoading(WebView view,String url)
{
view.loadUrl(url);
return true;
}
});
Button b1= new Button(Thisactivity);
b1.setText("返回");
 b1.setTextColor(Color.parseColor("#ffffff"));
 b1.setBackgroundColor(Color.parseColor("#e95295"));
 
  LinearLayout layout=new LinearLayout(Thisactivity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(b1);
        
       layout.addView(w1);
       Dialog dialog = new Dialog(Thisactivity);
        dialog.setContentView(layout);
      dialog.setTitle("                             🇨🇳");
      dialog.setCancelable(false);
      b1.setOnClickListener(new View.OnClickListener()
{
public void onClick(View v)
{
if (w1.canGoBack()) {
	w1.goBack();
} else {
dialog.dismiss();
}}
});
       dialog.show();
 }});}





