/*
由卑微萌新(QQ779412117)开发，使用请保留版权

接口说明 

①发起群收款，需要收款QQ号对应的skey,"tenpay.com"的pskey

String sendpay(String MyQQ,String qun,String QQ,String title,String pay,String skey,String pskey);
 参数 收款QQ号,群号,被收款QQ号,收款标题,收款金额(分),收款QQ号对应skey,收款QQ号对应tenpay.com的pskey;

返回collection_no(用于判断收款情况)或者错误文本;

------------------------------

②判断支付结果，需要收款时用QQ号对应的skey,"tenpay.com"的pskey以及collection_no

Object getpay(String MyQQ,String collection,String skey,String pskey)

参数 收款时用QQ号,收款时返回的collection_no,收款时用QQ号对应skey,收款时用QQ号对应tenpay.com的pskey;

返回boolean true/false 或者错误信息

------------------------------

③取消收款，需要收款时用QQ号对应的skey,"tenpay.com"的pskey以及collection_no

String cancelpay(String MyQQ,String collection,String skey,String pskey)

参数 收款时用QQ号,收款时返回的collection_no,收款时用QQ号对应skey,收款时用QQ号对应tenpay.com的pskey;

返回文本是否成功
--------------------------

示例 在群204523116里自己对779412117收款0.1元

String code=sendpay(myUin,"204523116",myUin,"测试","10",getSkey(),getPskey("tenpay.com"));

sendMsg("204523116","",pay);

//获得上述收款情况(在code正确的情况下)
if(!code.contains("失败"))
{
sendMsg(qun,"",""+getpay(myUin,code,getSkey(),getPskey("tenpay.com")));//true为收款成功
}

//取消上述收款
if(!code.contains("失败"))
{
sendMsg(qun,"",""+cancelpay(myUin,code,getSkey(),getPskey("tenpay.com")));
}

*/

import org.json.*;
import org.json.JSONObject;

import java.io.*; 
import java.net.*;


public String httpget(String url,String cookie)
	{
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
                uc.setRequestProperty("Cookie",cookie);
                uc.setConnectTimeout(10000);
                uc.setReadTimeout(10000);
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
                    e.printStackTrace();
                }
            }
        if(buffer.length()==0)return buffer.toString();
        buffer.delete(buffer.length()-1,buffer.length());
        return buffer.toString();
	}




public String sendpay(String MyQQ,String qun,String QQ,String title,String pay,String skey,String pskey)
{
try{

String result=httpget("https://mqq.tenpay.com/cgi-bin/qcollect/qpay_collect_create.cgi?type=2&memo="+java.net.URLEncoder.encode(title)+"&amount="+pay+"&payer_list=%5B%7B%22uin%22%3A%22"+QQ+"%22%2C%22amount%22%3A%22"+pay+"%22%7D%5D&num=1&recv_type=1&group_id="+qun+"&uin="+MyQQ+"&pskey="+pskey+"&skey="+skey,"");

//return result;

		JSONObject json = new JSONObject(result);
		String retcode=json.get("retcode");
		String retmsg=json.get("retmsg");
		//
		if(retcode.equals("0")) {
		if((json+"").contains("collection_no")) {
        return json.get("collection_no");
		}
		else return "提交失败,原因:\n"+retmsg;
		}
		else return "提交失败,原因:\n"+retmsg;
		}
catch(Exception e)
            {
                return "提交失败,原因:\n"+e;
            } 
}

public String sendpay(String MyQQ,String QQ,String title,String pay,String skey,String pskey)
{
try{

String result=httpget("https://mqq.tenpay.com/cgi-bin/qcollect/qpay_collect_create.cgi?type=2&memo="+java.net.URLEncoder.encode(title)+"&amount="+pay+"&payer_list=%5B%7B%22uin%22%3A%22"+QQ+"%22%2C%22amount%22%3A%22"+pay+"%22%7D%5D&num=1&recv_type=0&group_id="+QQ+"&uin="+MyQQ+"&pskey="+pskey+"&skey="+skey,"");

//return result;

		JSONObject json = new JSONObject(result);
		String retcode=json.get("retcode");
		String retmsg=json.get("retmsg");
		//
		if(retcode.equals("0")) {
		if((json+"").contains("collection_no")) {
        return json.get("collection_no");
		}
		else return "提交失败,原因:\n"+retmsg;
		}
		else return "提交失败,原因:\n"+retmsg;
		}
catch(Exception e)
            {
                return "提交失败,原因:\n"+e;
            } 
}


public Object getpay(String MyQQ,String collection,String skey,String pskey)
{
try{
String cookie="p_uin=o0"+QQ+";skey="+skey+";p_skey="+pskey;
String result=httpget("https://mqq.tenpay.com/cgi-bin/qcollect/qpay_collect_detail.cgi?collection_no="+collection+"&uin="+MyQQ+"&pskey="+pskey+"&skey="+skey,cookie);

//return result;

		JSONObject json = new JSONObject(result);
		String retcode=json.get("retcode");
		String retmsg=json.get("retmsg");
		
		if(retcode.equals("0")) {
		if((json+"").contains("payer_list")) {
		//String pay=json.get("amount");
		String get=json.get("payer_list").get(0).get("state");
		if(!get.equals("0")) return true;
        else return false;
		}
		else return "获取失败,原因:\n"+retmsg;
		}
		else return "获取失败,原因:\n"+retmsg;
		}
catch(Exception e)
            {
                return "获取失败,原因:\n"+e;
            } 
}


public String cancelpay(String MyQQ,String collection,String skey,String pskey)
{
try{
String cookie="p_uin=o0"+QQ+";skey="+skey+";p_skey="+pskey;
String result=httpget("https://mqq.tenpay.com/cgi-bin/qcollect/qpay_collect_close.cgi?collection_no="+collection+"&uin="+MyQQ+"&pskey="+pskey+"&skey="+skey,cookie);

//return result;

		JSONObject json = new JSONObject(result);
		String retcode=json.get("retcode");
		String retmsg=json.get("retmsg");
		
		if(retcode.equals("0")) {
		return "取消成功";
		}
		else return "取消失败,原因:\n"+retmsg;
		}
catch(Exception e)
            {
                return "取消失败,原因:\n"+e;
            } 
}


