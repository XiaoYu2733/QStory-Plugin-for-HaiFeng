
//朝雨念音

import java.lang.*;
import com.tencent.mobileqq.activity.shortvideo.SendVideoTask;
import com.tencent.mobileqq.app.BaseActivity;
import android.content.Intent;

public void sendVideo(String qun,String filepath,boolean raw)
{
Intent intent = new Intent();
         intent.putExtra("file_send_path",filepath);
         intent.putExtra("uin",qun);
         intent.putExtra("uintype", 1);
    // intent.putExtra("short_video_msg_tail_type", 1);
    intent.putExtra("send_in_background",true);
         if(raw) intent.putExtra("PhotoConst.SEND_SIZE_SPEC", 0);
         else intent.putExtra("PhotoConst.SEND_SIZE_SPEC", 2);
        
    SendVideoTask gg=new SendVideoTask(BaseActivity.sTopActivity,intent);
        gg.execute(new Void[0]);
}
public String 随()
{
if("4".equals(getString("样式","菜单样式")))
{
return 次空.get(new Random().nextInt(次空.size()));
}
if("5".equals(getString("样式","菜单样式")))
{
return 雨华.get(new Random().nextInt(雨华.size()));
}

return 雾迷.get(new Random().nextInt(雾迷.size()));
}

public int 等级int(String uin){
try{
Object card=GetCard(uin);
Thread.sleep(100);
if(card.iQQLevel!=null){
return card.iQQLevel;}
}catch(Exception e){
try{
Object card=GetCard(uin);
Thread.sleep(100);
if(card.iQQLevel!=null){
return card.iQQLevel;}
}catch(Exception e){
try{
Object card=GetCard(uin);
Thread.sleep(100);
if(card.iQQLevel!=null){
if(card.iQQLevel==0)
{Object card=GetCard(uin);
return card.iQQLevel;}
return card.iQQLevel;}
}catch(Exception e){
return 999;
}}}}



/*
public int 等级int(String uin){
try{Object card=GetCard(uin);
return card.iQQLevel;}catch(Exception e){try{Thread.sleep(100);Object card=GetCard(uin);return card.iQQLevel;}catch(e){try{return 等级int(uin);}catch(e){return 等级int(uin);}}}}
*/
HashMap GroupMap = new HashMap();
public String 朝云(String 南)
{
if(GroupMap.containsKey(南)) return GroupMap.get(南);
else{
for(Object group:getGroupList())
	{
    GroupMap.put(group.GroupUin,group.GroupName);
	}
if(GroupMap.containsKey(南)) return GroupMap.get(南);
else return "群名获取失败";
}
}
SimpleDateFormat sjgs=new SimpleDateFormat("yy年M月d日H:mm:ss");
public int 群人数(String qun)
{
	int tmp = 0;
for (Object target : getGroupMemberList(qun))
{
	++tmp;
}
return tmp;
}
import java.lang.*;
import java.text.*;
import java.util.*;
import java.io.*;


public String 群昵称(String qun,String q){//获取群昵称
return getMember(qun,q);
}
public String wgpd(String a,List list){
String jj="";
for(int i=0;i<list.size();i++){
if(a.contains(list.get(i))){
jj=list.get(i); 
break;}
}
return jj;
}

public int 取数(String a)
{
int i=Integer.parseInt(a);
return i;
}

public int 组(Object 汐){
	int datu =汐.消息.lastIndexOf(" ");
	String date=汐.消息.substring(datu +1);
	date=date.trim();
	String t="";
	if(date != null && !"".equals(date)){
		for(int i=0;i<date.length();i++){
			if(date.charAt(i)>=48 && date.charAt(i)<=57){
				t +=date.charAt(i);
			}
		}
	}	
	int time=Integer.parseInt(t);  
	/*	if(date.contains("10")){
		return 10*60*60*24;
	}*/
	if(date.contains("天")){
		return time*60*60*24;
	}
	else if(date.contains("时") || date.contains("小时") ){
	 	return 60*60*time;
	}
	else if(date.contains("分") || date.contains("分钟") ){
		return 60*time;}	
		return time;
}

public int 居_组(int yu){
	String date=汐.消息.substring(yu);
	date=date.trim();
	String t="";
	if(date != null && !"".equals(date)){
		for(int i=0;i<date.length();i++){
			if(date.charAt(i)>=48 && date.charAt(i)<=57){
				t +=date.charAt(i);
			}
		}
	}	
	int time=Integer.parseInt(t);  
		
	if(date.contains("天")){
		return time*60*60*24;
	}
	else if(date.contains("时") || date.contains("小时") ){
	 	return 60*60*time;
	}
	else if(date.contains("分") || date.contains("分钟") ){
		return 60*time;}	
		return time;
}

public String 残花(int a)
{
return 汐.消息.substring(a);
}

public String 残花(int a,int b)
{
return 汐.消息.substring(a,b);
}

public String 论(String u,String a,String b)
{
return u.replace(a,b);
}
public static int 度(String a){
return a.length();
}

// 继承QQ复读机的atMe函数
public static boolean atMe(Object 汐)
{
	if (null == 汐.msg.AtList || 0 == 汐.msg.AtList.length)
		return false;
	for (String to_at : 汐.msg.AtList)
		if (to_at.equals(myUin))
			return true;
	return false;
}
// 继承改进QQ复读机的是否群主函数
public static boolean isGroupOwner(String GroupUin, String UserUin)
{
	ArrayList groupList = getGroupList();
	for (Object groupInfo : groupList)
		if (groupInfo.GroupUin.equals(GroupUin))
return groupInfo.GroupOwner.equals(UserUin);
	return false;
}
public static boolean 是否群主(String GroupUin) { return isGroupOwner(GroupUin, myUin); }
public static void 缓冲(int a)
{
Thread.sleep(a*10);
}
public void 存(String a,String b,String c)
{
putString(a,b,c);
}
public String 读(String a,String b)
{
return getString(a,b);
}

public boolean 读开关(String a,String d)
{
if(a.equals(getString(汐.群,d)))return true;
else return false;
}

public boolean 读关开(String d)
{
if(读(汐.群,d)==null)return true;
else return false;
}
public String 解析(String 接口,String 内容)
{
JSONObject a = new JSONObject(访问(接口));
return a.getString(内容);

}




public void 提示(String a)
{
	toast(a);
}
public boolean 故(String a){
	if(汐.消息.equals(a))return true;
	else return false;}
	
public boolean 故始(String a){
	if(汐.消息.startsWith(a))return true;
	else return false;}	
public boolean 故含(String a)	{
    return 汐.消息.contains(a);
}
public boolean 含(String b,String a){
	if(b.contains(a))return true;
	else return false;}	
	
public boolean 故里(int a){
	if(汐.消息.length()<a)return true;
	else return false;}
	
	
public boolean 秋(String a){
	if(汐.用户.equals(a))return true;
	else return false;}
	
public boolean 南(String a){
	if(汐.群.equals(a))return true;
	else return false;}


import java.util.*;
import java.util.regex.Pattern;
public static int 舞(a)
{
return a.size();
}

public static boolean 正则(String a,String b)
{
if(a.matches(b))return true;
else return false;
}

public static boolean 正则(String b)
{
if(正则(汐.消息,b))return true;
else return false;
}
	
public void 语(String a)
{
sendMsg(汐.群,"",a);
}
public void 景(String a){
sendPic(汐.群,"",a);}	
public void 纸(String a){
sendCard(汐.群,"",a);
}
public void 语景(String b,String a)
{
sendMsg(汐.群,"",b+"[PicUrl="+a+"]");
}
public void 回(String a){
sendReply(汐.群,汐.msg,a);
}

public void 禁言(String 千,int 时)
{
forbidden(汐.群,千,时);
}
public void 踢(String a)
{
kick(汐.群,a,false);
}
public void 踢黑(String a)
{
kick(汐.群,a,true);
}
public static void 全体禁言()
{	
forbidden(汐.群,"",1);
}
public static void 全体解禁()
{		
forbidden(汐.群,"",0);
}
public void 头衔(String qq,String kk)
{
setTitle(汐.群,qq,kk);
}
public static void 撤回()
{
revokeMsg(汐.msg);
}
//是否在数组中
public static boolean isInList(String targetValue, String[] arr)
{
	if (arr.length <= 0)
		return false;
	for (String s : arr)
		if (s.equals(targetValue))
			return true;
	return false;
}
//继承并改进QQ复读机的判断是否管理函数
public static boolean isAdmin(String GroupUin, String UserUin)
{
	ArrayList groupList = getGroupList();
	for (Object groupInfo : groupList)
if (groupInfo.GroupUin.equals(GroupUin))
			return groupInfo.GroupOwner.equals(UserUin) || isInList(UserUin, groupInfo.AdminList);
	return false;
}
public static boolean 是否管理(String GroupUin) { return isAdmin(GroupUin, myUin); }


List 是否文字=Arrays.asList(new String[]{"是","否"});
List 是否表情=Arrays.asList(new String[]{"✅","❌"});
public String 是管理(List l)
{
if(是否管理(汐.群))return l.a[0];
else return l.a[1];
}

public String 是否开启(File a,List l)
{
if(简读开关(a))return l.a[1];
else return l.a[0];
}
public String 是否开启2(File a,List l)
{
if(简读开关(a))return l.a[0];
else return l.a[1];
}
public String 是否功能(String a,String d,List l)
{
if(读开关(a,d))return l.a[0];
else return l.a[1];
}

public String 是否功能2(String a,String d,List l)
{
if(!读开关(a,d))return l.a[0];
else return l.a[1];
}

public String 是否功能3(String d,List l)
{
if(读关开(d))return l.a[0];
else return l.a[1];
}

public String 是否功能4(String a,String b,String c,List l)
{
if(等(a,读(b,c)))return l.a[0];
else return l.a[1];
}

public String 是否功能5(String a,String b,String c,List l)
{
if(!等(a,读(b,c)))return l.a[0];
else return l.a[1];
}
//时间转换
public static String transTime(long dShut)
{
	String sShut = "";
	int[] delimsTime = {2592000, 86400, 3600, 60, 1};
String[] delimsText = {"月", "天", "小时", "分钟", "秒"};
	for (int i = 1; i < delimsTime.length; ++i)
	{
		int tmp = (int)((dShut % delimsTime[i - 1]) / delimsTime[i]);
		if (tmp > 0)
			sShut += tmp + delimsText[i];
	}
	return sShut;
}

public static String 时(int dShut) { return transTime((long)dShut); }


public String 群名(String qun){
Object info=TroopInfo.getTroopInfo(qun);
String na=info.getNewTroopNameOrTroopName();
return na;
}

public String 群创建时间(String qun){
Object info=TroopInfo.getTroopInfo(qun);
long tt=info.troopCreateTime;
Date t1 = new Date(tt*1000);
String t3=sjgs.format(t1);
return t3;
}


public static String getTime()
{
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日"), df2 = new SimpleDateFormat("E", Locale.CHINA), df3 = new SimpleDateFormat("HH:mm:ss");
	Calendar calendar = Calendar.getInstance();
	String TimeMsg1 = df1.format(calendar.getTime()), TimeMsg2 = df2.format(calendar.getTime()), TimeMsg3 = df3.format(calendar.getTime());
	if (TimeMsg1.contains("年0"))//去掉多余的 0
		TimeMsg1 = TimeMsg1.replace("年0", "年");
	if (TimeMsg1.contains("月0"))
		TimeMsg1 = TimeMsg1.replace("月0", "月");
	if (TimeMsg2.contains("周"))
		TimeMsg2 = TimeMsg2.replace("周", "星期");//转换为星期
	return TimeMsg1 + TimeMsg2 + TimeMsg3;
}




public static String transString(String ori_string)//转换字串符
{
	return "\"" + ori_string.replaceAll("\"", "\\\"").replaceAll("\\\\", "\\\\") + "\"";
}
public static boolean isAllE(String isAllC)//判断是否全为英文字母
{
	return isAllC.replaceAll("[A-Za-z]", "").length() == 0;
}

public static boolean isAllP(String isAllC)//判断是否全为标点和数字
{
	return isAllC.replaceAll("\\p{P}", "").length() == 0 || isAllC.replaceAll("\\d+", "").length() == 0  || isAllC.replaceAll("\\p{P}", "").replaceAll("\\d+", "").length() == 0;
}
//判断是否仅包含汉字 数字 英文
public static boolean isLetterDigitOrChinese(String str) {
  String regex = "^[a-z0-9A-Z\\p{P}\u4e00-\u9fa5]+$";
  return str.matches(regex);
 }
 
public static boolean isChinese(char c)
{
	return c >= 0x4E00 &&  c <= 0x9FA5;//根据字节码判断
}

public static boolean isContainsZH(String str)//判断是否含有中文字符
{
	if (null == str)
		return false;
	for (char c : str.toCharArray())
		if (isChinese(c))
			return true;// 有一个中文字符就返回
	return false;
}

public static boolean isSameChar(String msg)//是否同一字符
{
	char tmp = msg.charAt(0);
	for (int i = 1; i < msg.length(); ++i)
		if (msg.charAt(i) != tmp)
			return false;
	return true;
}


//判断字符串中是否含有Emoji表情 
    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

public String 观(a)
{
return a.toString();
}



EncrypDES L = new EncrypDES();

public String CL(String l)
{
return L.CL(l);
}
public String CR(String l)
{
return L.CX(l);
}


public static boolean 等(String a,String b)
{
if(a.equals(b))return true;
else return false;
}



public int 组(Object 汐,int time){

int datu =汐.消息.lastIndexOf(" ");
String date=汐.消息.substring(datu +1); 
	if(date.contains("天")){
		return time*60*60*24;
	}
	else if(date.contains("时") || date.contains("小时") ){
	 	return 60*60*time;
	}
	else if(date.contains("分") || date.contains("分钟") ){
		return 60*time;}	
    return time;
}
public static String 残舞(String a,String b)
{
return a.replaceAll(b,"");
}

public List 禁言组(String qun)
{
Object st=getForbiddenList(qun);
ArrayList t = new ArrayList();
for(Object b:st)
{
t.add(b.UserUin);
}
return t;
}


public String R_禁言组(String qun)
{
Object st=getForbiddenList(qun);
ArrayList t = new ArrayList();
int i=1;
for(Object b:st)
{
t.add(i+"."+b.UserName+"("+b.UserUin+")");
i++;
}
String r=t.toString();
String s=r.replace(" ","");
String g=s.replace(",","\n");
String k=g.replace("[","");
String y=k.replace("]","");
return y + "\n输入 解+序号快速解禁\n输入全禁可禁言30天\n输入#踢禁言 可踢出上述所有人";
}



public List 管理组(String qun){
for(Object c:getGroupList()){
if(c.GroupUin.equals(qun)){
List list=Arrays.asList(c.AdminList);
return list;
break;
}}
}

public String 名(String uin){
try{
Object card=GetCard(uin);
if(card.strNick!=null){
return card.strNick;}
else{return "未知";}
}catch(Exception e){toast("异常 请再次尝试获取"+e);return "未知";}
}


public String 管理组名(String qun){
ArrayList list = new ArrayList();
for(Object c:getGroupList()){
if(c.GroupUin.equals(qun)){
for(String uin:c.AdminList){
list.add(群昵称(qun,uin)+"("+uin+")");
}
return list.toString().replace(",","\n");
break;
}}
}

public String 群主账号(String qun){
for(Object c:getGroupList()){
if(c.GroupUin.equals(qun)){
return c.GroupOwner;
break;
}}
}

public String 组名(ArrayList a)
{
ArrayList list = new ArrayList();
for(String uin:a ) {
list.add(名(uin)+"("+uin+")");
}
return list.toString().replace(",","\n");
break;
}

public String 群组名(ArrayList a)
{
ArrayList list = new ArrayList();
for(String uin:a ) {
list.add(朝云(uin)+"("+uin+")");
}
return list.toString().replace(",","\n");
break;
}


public static int 残(String b,String a)
{
return b.lastIndexOf(a);
}
public static int 残(String a)
{
return 汐.消息.lastIndexOf(a);
}

public void 开关机方法(String GroupUin ) {

if(简读开关(开关机,GroupUin))
{
简弃(开关机,GroupUin);
提示("当前群聊已关闭");
return;
}
else
{
 简写(开关机,GroupUin);
提示("当前群聊已开启");
}
}
public void 更新公告(String s)
{
更新内容();
}

public static String 获取黑名单()
{
class 黑名单组{
int 黑名单数量;
int 退群数量;
int 拉黑数量;
int 其他;
String 黑名="";
String 退群="";
String 拉黑="";
String 例外="";
}
黑名单组 落 = new 黑名单组();
List li = 简取(黑名单);

for(String s : li)
{
if(取(s).contains("退出群聊")||取(s).contains("退群"))
{
落.退群数量++;
落.黑名单数量++;
落.退群 = 落.退群 + "，" + s;
continue;
}

if(取(s).contains("被 "))
{
落.拉黑数量++;
落.黑名单数量++;
落.拉黑 =落.拉黑+ "，" + s;
continue;
}else{
落.黑名单数量++;
落.其他++;
落.例外= 落.例外+"，"+s;
    }

}

if(落.退群.length() > 1)
落.退群 = 落.退群.substring(1);

if(落.拉黑.length() > 1)
落.拉黑 = 落.拉黑.substring(1);

if(落.例外.length() > 1)
落.例外 = 落.例外.substring(1);

String a = 
"黑名单总人数为 : "+落.黑名单数量+"\n"+

"退群拉黑数量为 : "+落.退群数量+"\n"+
"名单 : \n"+落.退群+"\n"+

"管理拉黑数量为 : "+落.拉黑数量+"\n"+
"名单 : \n"+落.拉黑+"\n"+

"其他数量为 : "+落.其他+"\n"+
"名单 : \n"+落.例外;

return a;
}