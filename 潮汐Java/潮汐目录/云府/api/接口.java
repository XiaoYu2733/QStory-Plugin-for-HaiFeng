//卑微萌新 

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*; 
import java.net.*;

public long GetBkn(String skey)
{
    int hash = 5381;
    for (int i = 0, len = skey.length(); i < len; i++) 
        hash += (hash << 5) + (int)(char)skey.charAt(i);
    return hash & 2147483647;
}


	
public String httppost(String urlPath, String cookie,String data)
	{
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            CookieManager cookieManager = new CookieManager();
CookieHandler.setDefault(cookieManager);
            try {
                URL url = new URL(urlPath);
			uc = (HttpURLConnection) url.openConnection();
			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.setConnectTimeout(2000000);// 设置连接主机超时（单位：毫秒）
			uc.setReadTimeout(2000000);// 设置从主机读取数据超时（单位：毫秒）
			uc.setRequestMethod("POST");
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uc.setRequestProperty("Cookie",cookie);
	uc.getOutputStream().write(data.getBytes("UTF-8"));
			uc.getOutputStream().flush();
			uc.getOutputStream().close();
                isr = new InputStreamReader(uc.getInputStream(), "utf-8");
                BufferedReader reader = new BufferedReader(isr); //缓冲
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != isr) {
                        isr.close();
                    }
                } catch (IOException e) {
                    toast("错误:\n"+e);
                }
            }
        if(buffer.length()==0)return buffer.toString();
        buffer.delete(buffer.length()-1,buffer.length());
        return buffer.toString();
}



public String SetTroopShowHonour(String qun,String myQQ,String skey,String pskey,int type)
{
try{
String cookie="p_uin=o0"+myQQ+";uin=o0"+myQQ+";skey="+skey+";p_skey="+pskey;
String put="gc="+qun+"&flag="+type+"&bkn="+GetBkn(skey);
		JSONObject json = new JSONObject(httppost("https://qinfo.clt.qq.com/cgi-bin/qun_info/set_honour_flag",cookie,put));
		int ec=json.get("ec");
		String em=json.get("em");
		if(ec==0) return "设置成功";
		//else if(ec==13)	return "设置失败，管理位置已满";
		else return "设置失败，原因:\n"+em;
		}
catch(Exception e)
            {
                return "设置失败，原因:\n"+e;
            } 
}



public String SetTroopShowLevel(String qun,String myQQ,String skey,String pskey,int type)
{
return SetTroopShowInfo(qun,myQQ,skey,pskey,"&levelnewflag="+type);
}

public String SetTroopShowTitle(String qun,String myQQ,String skey,String pskey,int type)
{
return SetTroopShowInfo(qun,myQQ,skey,pskey,"&levelflag="+type);
}

public String SetTroopShowInfo(String qun,String myQQ,String skey,String pskey,String type)
{
try{
String cookie="p_uin=o0"+myQQ+";uin=o0"+myQQ+";skey="+skey+";p_skey="+pskey;
String put="gc="+qun+type+"&bkn="+GetBkn(skey);
		JSONObject json = new JSONObject(httppost("https://qinfo.clt.qq.com/cgi-bin/qun_info/set_group_setting",cookie,put));
		int ec=json.get("ec");
		String em=json.get("em");
		if(ec==0) return "设置成功";
		//else if(ec==13)	return "设置失败，管理位置已满";
		else return "设置失败，原因:\n"+em;
		}
catch(Exception e)
            {
                return "设置失败，原因:\n"+e;
            } 
}



import com.tencent.mobileqq.app.TroopServlet;

//import com.tencent.mobileqq.app.ef;

import android.content.Intent;

import com.tencent.common.app.BaseApplicationImpl;

Object app=BaseApplicationImpl.getApplication().getRuntime();

public void SetTroopAdmin(Object qun,Object qq,int type)
{
Intent intent=new Intent();
intent.putExtra("command",0);
intent.putExtra("operation", (byte) type);
intent.putExtra("troop_code",""+qun);
intent.putExtra("troop_member_uin",""+qq);

TroopServlet tr=new TroopServlet();
tr.init(app,null);
tr.service(intent);
}


import com.tencent.mobileqq.troop.api.ITroopInfoService;
import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.profilecard.api.IProfileDataService;
import com.tencent.mobileqq.profilecard.api.IProfileProtocolService;
import android.os.Bundle;
Object app=BaseApplicationImpl.getApplication().getRuntime();
IProfileDataService ProfileData=app.getRuntimeService(IProfileDataService.class);
IProfileProtocolService ProtocolService=app.getRuntimeService(IProfileProtocolService.class);

public Object GetCard(String uin){
ProfileData.onCreate(app);
Object card=ProfileData.getProfileCard(uin,false);
if(card==null||card.iQQLevel==null)
{
Bundle bundle =new Bundle();
bundle.putLong("selfUin",Long.parseLong(myUin));
bundle.putLong("targetUin",Long.parseLong(uin));
bundle.putInt("comeFromType",12);
ProtocolService.requestProfileCard(bundle);
return null;
}
else return card;
}
ITroopInfoService TroopInfo=app.getRuntimeService(ITroopInfoService.class);
/*
由卑微萌新(QQ779412117)开发，使用请保留版权

QQ上传群文件接口

upload(String qun,String filepath); 参数: 群号,文件路径

返回boolean true/false
*/

import com.tencent.mobileqq.filemanager.app.FileManagerEngine;
FileManagerEngine file=null;
public boolean upload(String qun,String filepath)
{
if(file==null) file=new FileManagerEngine(BaseApplicationImpl.sApplication.getAppRuntime(myUin));
    return file.a(filepath,qun,1,1);
}

import com.tencent.common.app.BaseApplicationImpl;
import com.tencent.mobileqq.app.BusinessHandlerFactory;

Object app=BaseApplicationImpl.getApplication().getRuntime();
Object FriendList=app.getBusinessHandler(BusinessHandlerFactory.FRIENDLIST_HANDLER);

//FriendList.delFriend("好友QQ号", (byte) 2);


//陌然
import org.json.*;
import org.json.JSONObject;

import java.io.*; 
import java.net.*;
import java.util.zip.GZIPInputStream;

public String zipget(String url,String cookie)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CookieManager cookieManager = new CookieManager();
CookieHandler.setDefault(cookieManager);
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
                uc.setRequestProperty("Accept-Encoding","gzip, deflate, br");
                uc.setRequestProperty("Cookie",cookie);
                uc.setConnectTimeout(10000);
                uc.setReadTimeout(10000);
                InputStream in = uc.getInputStream();
in = new BufferedInputStream(in); // 缓冲
in = new GZIPInputStream(in); // 解压数据
      byte[] buffer = new byte[256];
int n;
while ((n = in.read(buffer)) >= 0) {
out.write(buffer, 0, n);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != out) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return out.toString();
	}

public Object getDrago(String QQ,String skey,String pskey,String qun)
{
try{
String cookie= "p_skey="+pskey+"; uin=o"+QQ+"; skey="+skey+"; p_uin=o"+QQ+";";
String result=zipget("https://qun.qq.com/interactive/honorlist?gc="+qun+"&type=1&_wv=3&_wwv=129",cookie);
int index = result.lastIndexOf("window.__INITIAL_STATE__=");
if(index!=-1){
String text = result.substring(index + 25);
int rd = text.indexOf("}<");
String re = text.substring(0,rd+1);
JSONObject json=new JSONObject(re);
		if((json+"").contains("currentTalkative")) {
        return re;
		}
	    else "获取失败,原因:\n"+re;
	    }
	    return "获取失败,原因:\n未获取到Json";
        }
catch(Exception e)
            {
                return "获取失败,原因:\n"+e;
            } 
}