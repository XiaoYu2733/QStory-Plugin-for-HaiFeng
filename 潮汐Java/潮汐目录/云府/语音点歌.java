//卑微萌新(779412117)
import android.os.Environment;
import java.util.*;
ArrayList diangekeyword= new ArrayList();

diangekeyword.add("语音点歌");

int listnum=5;

int autoset=1;



int qunsendtype=2;

int channelsendtype=0;

int sendtype=1;
boolean wenjian=false;
int delFile=1;
//文件模式上传音乐文件后是否自动删除文件。0不删，1删

int delPtt=1;
//发送语音后是否自动删除语音文件。0不，1删


int yuyin=0;
//发送模式为语音且文件为mp3时语音会自动分割(其它格式只会有第一个)。0代表全部发出，1第一个，2第二个以此类推。超过分割数量时会自动发最后一个。(建议第2个)

int qingxidu=2;
//非语音时尝试获取的歌最大音质，0为Hi-Res品质flac,1为SQ品质flac,2高品质mp3,3中品质mp3,4试听m4a(只是尝试获取。不保证一定是。没绿钻或者音乐包的就直接3就行了)

String RootPath = appPath+"/潮汐目录/云府/api/";


HashMap MusicMap = new HashMap();
HashMap FileMap = new HashMap();
//{链接} {图标}
String MusicXml = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"2\" templateID=\"1\" action=\"web\" brief=\"[QQ音乐] {标题} - {作者}\" sourceMsgId=\"0\" url=\"{歌曲}\" flag=\"0\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"2\"><audio cover=\"{图标}\" src=\"{歌曲}\" /><title>{标题}</title><summary>{作者}</summary></item><source name=\"QQ音乐\" icon=\"ICONUrl\" url=\"\" action=\"plugin\" a_actionData=\"mqqapi://card/show_pslcard?src_type=internal&amp;source=sharecard&amp;version=1&amp;uin="+myUin+"\" i_actionData=\"mqqapi://card/show_pslcard?src_type=internal&amp;source=sharecard&amp;version=1&amp;uin="+myUin+"\" appid=\"1101079856\" /></msg>";

if(listnum>=99) listnum=99;
if(listnum<=1) listnum=1;
if(listnum<autoset) autoset=listnum;
if(channelsendtype!=3) channelsendtype=0;
public class CheckMusic
{
	String MusicName;
	String[] MusicMid = new String[listnum];
//	String[] FileName = new String[listnum];
	long time;
	int MusicType;
	Object SongDataSave;
}

load(RootPath+"Apis/HttpClient.java");
load(RootPath+"Apis/点歌Api.java");
load(RootPath+"Apis/upload.java");

public void Music(String qun)
{
if(getString(qun,"QQ点歌")==null)
	{
	putString(qun, "QQ点歌", "1");
      toast("本群语音点歌已关闭");
	}
	else{
	putString(qun,"QQ点歌",null);
	toast("本群语音点歌已开启");
	}
}




public void 点歌(Object 汐)
{
if("1".equals(getString(汐.群,"QQ点歌"))&&!秋(myUin)&&!简读用户(代管))return;

if(简读开关(菜单限制)&&!秋(myUin)&&!简读用户(代管))return;

if(等("开",读("全局","菜单限制"))&&!秋(myUin)&&!简读用户(代管))return;
//String nick=汐.msg.SenderNickName;
/*if(getString(汐.群,"QQ点歌")==null)
{*/
String quntext=汐.msg.MessageContent;
String qun=汐.msg.GroupUin;
String qq="";
String at="";
int type=汐.msg.MessageType;

int ii=quntext.indexOf("https://c.y.qq.com/base/fcgi-bin/u?__");
if(ii!=-1){
String url=quntext.substring(ii,ii+50);
JSONObject json2 = new JSONObject(SearchForQQMusicInfoResult(url,2));
          SendMusic(汐.msg,json2,1);
          return;
}
quntext=quntext.toUpperCase();
String diangekeyword2="";
for(String key:diangekeyword)
{
if(quntext.startsWith(key)){
diangekeyword2=key;
break;
}
}
//toast(diangekeyword2);
if(汐.msg.IsGroup||汐.msg.IsChannel){at="[AtQQ="+汐.msg.UserUin+"]\n";}
else qq=汐.msg.msg.frienduin;
if(!diangekeyword2.equals(""))
{
//if(diangekeyword2.equals("")) return;
String name = quntext.substring(diangekeyword2.length());
if(name.equals(""))
{
回("未输入点歌内容\n示例:语音点歌xx");
			return ;
}
if(汐.消息.contains("扑克")||汐.消息.contains("老虎机")||汐.消息.contains("棋牌")||汐.消息.contains("快乐飞艇")||汐.消息.contains("斗地主")||汐.消息.contains("大法伦")||汐.消息.contains("一分"))
{
禁言(汐.用户,60*60*24*10);
撤回();
回("不合规的违法内容,已拒绝该请求(作 : 不好意思小傻瓜，我已经料到你的想法了哦)");
return;
}

		JSONObject json = new JSONObject(SearchQQMusicList(name));
		CheckMusic TempFlag = new CheckMusic();
		String text = "当前搜索到的歌曲列表如下:\n\n";
		String RawXmlCard = "";
		int size=json.getJSONArray("data").length();
		if(size==0) {
		sendMsg(qun,qq,at+"未搜到");
		return;
		}
		if(autoset>size) autoset=size;
		for(int i = 0; i < listnum; i++)
		{
			try
			{
				TempFlag.MusicMid[i] = json.getJSONArray("data").getJSONObject(i).getString("mid");
			//	TempFlag.FileName[i] = json.getJSONArray("data").getJSONObject(i).getString("filename");
				text = text + (i+1) + "." + json.getJSONArray("data").getJSONObject(i).getString("name") + "--" + json.getJSONArray("data").getJSONObject(i).getString("singer") + "[" + json.getJSONArray("data").getJSONObject(i).getString("albumname") + "]\n";
			}
			catch(e)
			{
				break;
			}
		}
		TempFlag.MusicName = name;
		TempFlag.time = 汐.msg.MessageTime;
		TempFlag.MusicType = 0;
		MusicMap.put(汐.msg.UserUin, TempFlag);
		if(autoset==0)
		{
		String moshi="链接";
		int send_type=qunsendtype;
		if(汐.msg.IsChannel) send_type=channelsendtype;
		if(!汐.msg.IsChannel&&!汐.msg.IsGroup) send_type=sendtype;
		if(send_type==1) moshi="卡片";
		else if(send_type==2) moshi="语音";
		else if(send_type==3) moshi="文件";
		text = text + "\n"+ at +"请发送序号来进行点歌("+moshi+"模式)";
		if(!汐.msg.IsChannel) text+=",或者发送链接/卡片/语音";
		else text+=",或者发送链接";
		if(汐.msg.IsSend||wenjian) text+="/文件";
		text+="+序号指定模式\n一分钟内有效";
		sendMsg(qun,qq,text);
		return ;
		}
	}
if(!diangekeyword2.equals("")&&autoset!=0||(quntext.matches("^[0-9]*$")))
	{//([1-9]|))$
	try{
		int Con=0;
		 if(diangekeyword2.equals(""))
		 {
		if(MusicMap==null||!MusicMap.containsKey(汐.msg.UserUin))
		{
			return ;
		}
		if(Integer.parseInt(quntext)>listnum) return;
		 Con= Integer.parseInt(quntext)-1;
		 }
		 else Con=autoset-1;
		CheckMusic MusicInfo = MusicMap.get(汐.msg.UserUin);
		if(MusicInfo.MusicMid[Con]==null||MusicInfo.MusicMid[Con].equals(""))
		{
		//toast("没那么多");
		return;
		}
		//MusicMap.remove(汐.msg.UserUin);
		if(汐.msg.MessageTime - MusicInfo.time > 60)
		{
		MusicMap.remove(汐.msg.UserUin);
			return ;
		}
		if(MusicInfo.MusicType == 0)
		{
		int yinzhi=qingxidu;
		int send_type=qunsendtype;
		if(汐.msg.IsChannel) send_type=channelsendtype;
		if(!汐.msg.IsChannel&&!汐.msg.IsGroup) send_type=sendtype;
		if(send_type==2) yinzhi=3;
		JSONObject json = new JSONObject(SearchForQQMusicInfoResult(MusicInfo.MusicMid[Con],yinzhi));
		SendMusic(汐.msg,json,send_type);
		}
			}
	catch(e)
			{
				return;
			}
}

if(type==5)
{
if(FileMap!=null&&FileMap.containsKey(汐.msg.FileName))
{
String url=FileMap.get(汐.msg.FileName);
FileMap.remove(汐.msg.FileName);
new java.lang.Thread(new Runnable()
{
public void run()
{
for(int i=0;i<100;i++)
       {
       Thread.sleep(500);
       if(汐.msg.msg.extraflag!=0) continue;
       else{
       toast(汐.msg.FileName+"发送成功");
       String path=RootPath+"音乐/"+汐.msg.FileName;
       if(delFile==1) delAllFile(new File(path),0);
        return;
        }
       }
sendMsg(qun,"","发送可能失败请自行下载\n"+url);
}
}).start();
}
}
//}
}

if(getSkey().equals("")) toast("未授权将不能获取QQ音乐会员身份，请重新加载授权");

