import org.json.JSONObject;
import java.net.*;
import java.io.*;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.profilecard.api.IProfileDataService;
import com.tencent.mobileqq.profilecard.api.IProfileProtocolService;

/*
该接口由卑微萌新(QQ779412117)开发，使用请保留版权。接口内容全部来自QQ内部，部分参数不准确与本人无关
*/
/*接口说明 

显示群互动标识 SetTroopShowHonour(qun,myUin,getSkey(),getPskey("clt.qq.com"),1);
显示群聊等级 SetTroopShowLevel(qun,myUin,getSkey(),getPskey("clt.qq.com"),1);
显示群员头衔 SetTroopShowTitle(qun,myUin,getSkey(),getey("clt.qq.com"),1);

隐藏就是最后1改成0

*/

public long GetBkn(String skey){
    long hash = 5381;
    for (int i = 0, len = skey.length(); i < len; i++) {
        hash += (hash << 5) + (long)skey.charAt(i);
    }
    return hash & 2147483647L;
}

public String httppost(String urlPath, String cookie,String data){
    StringBuffer buffer = new StringBuffer();
    InputStreamReader isr = null;
    CookieManager cookieManager = new CookieManager();
    CookieHandler.setDefault(cookieManager);
    try {
        URL url = new URL(urlPath);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setDoInput(true);
        uc.setDoOutput(true);
        uc.setConnectTimeout(20000);
        uc.setReadTimeout(20000);
        uc.setRequestMethod("POST");
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        uc.setRequestProperty("Cookie",cookie);
        uc.getOutputStream().write(data.getBytes("UTF-8"));
        uc.getOutputStream().flush();
        uc.getOutputStream().close();
        isr = new InputStreamReader(uc.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line).append("\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "错误: " + e.toString();
    } finally {
        try {
            if (null != isr) {
                isr.close();
            }
        } catch (IOException e) {
        }
    }
    if(buffer.length()==0)return buffer.toString();
    buffer.delete(buffer.length()-1,buffer.length());
    return buffer.toString();
}

public String SetTroopShowHonour(String qun,String myQQ,String skey,String pskey,int type){
    try{
        String cookie="p_uin=o0"+myQQ+";uin=o0"+myQQ+";skey="+skey+";p_skey="+pskey;
        String put="gc="+qun+"&flag="+type+"&bkn="+GetBkn(skey);
        String response = httppost("https://qinfo.clt.qq.com/cgi-bin/qun_info/set_honour_flag",cookie,put);
        try {
            JSONObject json = new JSONObject(response);
            int ec=json.getInt("ec");
            String em=json.getString("em");
            if(ec==0) return "设置成功";
            else return "设置失败，原因:"+em;
        } catch (Exception e) {
            return "设置失败，返回非JSON数据:"+response;
        }
    }
    catch(Exception e){
        return "设置失败，原因:"+e;
    } 
}

public String SetTroopShowLevel(String qun,String myQQ,String skey,String pskey,int type){
    return SetTroopShowInfo(qun,myQQ,skey,pskey,"levelnewflag",type);
}

public String SetTroopShowTitle(String qun,String myQQ,String skey,String pskey,int type){
    return SetTroopShowInfo(qun,myQQ,skey,pskey,"levelflag",type);
}

public String SetTroopShowInfo(String qun,String myQQ,String skey,String pskey,String flagType,int type){
    try{
        String cookie="p_uin=o0"+myQQ+";uin=o0"+myQQ+";skey="+skey+";p_skey="+pskey;
        String put="gc="+qun+"&"+flagType+"="+type+"&bkn="+GetBkn(skey);
        String response = httppost("https://qinfo.clt.qq.com/cgi-bin/qun_info/set_group_setting",cookie,put);
        try {
            JSONObject json = new JSONObject(response);
            int ec=json.getInt("ec");
            String em=json.getString("em");
            if(ec==0) return "设置成功";
            else return "设置失败，原因:"+em;
        } catch (Exception e) {
            return "设置失败，返回非JSON数据:"+response;
        }
    }
    catch(Exception e){
        return "设置失败，原因:"+e;
    } 
}

Object app=BaseApplicationImpl.getApplication().getRuntime();
IProfileDataService ProfileData=app.getRuntimeService(IProfileDataService.class);
IProfileProtocolService ProtocolService=app.getRuntimeService(IProfileProtocolService.class);

public Object GetCard(String uin){
    try {
        ProfileData.onCreate(app);
        Object card=ProfileData.getProfileCard(uin,false);
        if(card==null){
            Bundle bundle =new Bundle();
            bundle.putLong("selfUin",Long.parseLong(myUin));
            bundle.putLong("targetUin",Long.parseLong(uin));
            bundle.putInt("comeFromType",12);
            ProtocolService.requestProfileCard(bundle);
            return null;
        }else return card;
    } catch (Exception e) {
        return null;
    }
}

ITroopInfoService TroopInfo=app.getRuntimeService(ITroopInfoService.class);

public void SetTroopAdmin(Object qun,Object qq,int type){
    try {
        Intent intent=new Intent();
        intent.putExtra("command",0);
        intent.putExtra("operation", (byte) type);
        intent.putExtra("troop_code",""+qun);
        intent.putExtra("troop_member_uin",""+qq);
        Class TroopServlet = Class.forName("com.tencent.mobileqq.troop.api.TroopServlet");
        Object tr = TroopServlet.newInstance();
        java.lang.reflect.Method initMethod = TroopServlet.getMethod("init", Object.class, Object.class);
        java.lang.reflect.Method serviceMethod = TroopServlet.getMethod("service", Intent.class);
        initMethod.invoke(tr, app, null);
        serviceMethod.invoke(tr, intent);
    } catch (Exception e) {
    }
}