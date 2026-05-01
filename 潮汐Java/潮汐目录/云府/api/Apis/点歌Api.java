import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
//二改自绿逗以及刺痛
public static String SearchForQQMusicInfoResult(String url,int yinzhi)//来自绿逗以及刺痛
{
    if(url != null && !url.isEmpty())
    {
       JSONObject jsonReturn = new JSONObject();
       if(!url.startsWith("http")) url="https://y.qq.com/n/ryqq/songDetail/"+url;
       int index1 =-1;
        int index2=-1;
        String ImageCallBack = http.get(url);
        index1 = ImageCallBack.indexOf("window.__ssrFirstPageData__ =");
        index2=ImageCallBack.indexOf("window.__INITIAL_DATA__ =");
       // toast("11"+index1);
        if(index1 != -1&& index2==-1)
        {
            index2 = ImageCallBack.indexOf("</script><script", index1 + 10);
        
       // toast("12"+index2);
        if(index1 != -1 && index2 != -1)
        {
            JSONObject json = new JSONObject(ImageCallBack.substring(index1 + "window.__ssrFirstPageData__ =".length(), index2));
            String mMid=json.get("songList").get(0).get("mid");
            ImageCallBack = http.get("https://y.qq.com/n/ryqq/songDetail/"+mMid);
            }
            }
        index1 = ImageCallBack.indexOf("window.__INITIAL_DATA__ =");
        int index2=-1;
        if(index1 != -1)
        {
            index2 = ImageCallBack.indexOf("</script><script", index1 + 10);
        }
        if(index1 != -1 && index2 != -1)
        {
            JSONObject json = new JSONObject(ImageCallBack.substring(index1 + "window.__INITIAL_DATA__ =".length(), index2));
            
            JSONObject json2=json.get("detail");
            //toast((json2+""));
            jsonReturn.put("name", json2.getString("title"));
            jsonReturn.put("pic", "https:"+json2.getString("picurl").replace("T002R300x300","T002R150x150").replace("_1.jpg",".jpg"));
            JSONArray array=json2.getJSONArray("singer");
            String singer="";
            for(int h=0;h<array.length();h++)
{
if(h==0) singer = array.getJSONObject(h).getString("name");
else singer = singer+"/"+array.getJSONObject(h).getString("name");
}
            jsonReturn.put("singer", singer);
            jsonReturn.put("album", json2.getString("albumName"));
            String filename=json.get("songList").get(0).get("file").get("media_mid");
            String mMid=json.get("songList").get(0).get("mid");
            jsonReturn.put("playurl", GetQQMusicSongUrl(mMid,filename,yinzhi));
            jsonReturn.put("mid", mMid);
            //toast(jsonReturn+"");
        }
        return(jsonReturn.toString());
    }
    else
    {
    toast("hzh"+url);
        return("mid?"+url);
    }
}

public String GetQQMusicSongUrl(String SongMid,String name,int type)
{
String filename="";
if(type==0) filename="RS01"+name+".flac";
else if(type==1) filename="F000"+name+".flac";
else if(type==2) filename="M800"+name+".mp3";
else if(type==3) filename="M500"+name+".mp3";
else if(type==4) filename="C400"+name+".m4a";
else return GetQQMusicSongUrl(SongMid);
	try
	{
		String cookie="p_uin=o0"+myUin+";skey="+getSkey()+";p_skey="+getPskey("y.qq.com");
		String text = http.getWithAgentCookie("http://u6.y.qq.com/cgi-bin/musicu.fcg?data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22filename%22%3A%5B%22"+filename+"%22%5D%2C%22songmid%22%3A%5B%22"+SongMid+"%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%22"+myUin+"%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%7D","Mozilla/5.0 (Linux; U; Android 8.1.0; zh-cn; BLA-AL00 Build/HUAWEIBLA-AL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 Mobile Safari/537.36",cookie);
		JSONObject json = new JSONObject(text);
            
            String purl=json.get("req_0").get("data").get("midurlinfo").get(0).get("purl");
		String SongUrl = "http://isure.stream.qqmusic.qq.com/"+purl;
		if(!SongUrl.contains("vkey")){
		 return GetQQMusicSongUrl(SongMid,name,type+1);
		 }
		return SongUrl;
	}
	catch(Exception e)
	{
	toast("QQ音乐"+filename+"直链1获取失败\n"+e);
		return GetQQMusicSongUrl(SongMid,name,type+1);
	}
	toast("QQ音乐"+filename+"直链1获取失败");
	return GetQQMusicSongUrl(SongMid,name,type+1);
}

public String GetQQMusicSongUrl(String SongMid)
{
	try
	{
	String cookie="p_uin=o0"+myUin+";skey="+getSkey()+";p_skey="+getPskey("y.qq.com");
		String text = http.getWithAgentCookie("http://u6.y.qq.com/cgi-bin/musicu.fcg?data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22songmid%22%3A%5B%22"+SongMid+"%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%22"+myUin+"%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%7D","Mozilla/5.0 (Linux; U; Android 8.1.0; zh-cn; BLA-AL00 Build/HUAWEIBLA-AL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 Mobile Safari/537.36",cookie);
		JSONObject json = new JSONObject(text);
            String purl=json.get("req_0").get("data").get("midurlinfo").get(0).get("purl");
		String SongUrl = "http://isure.stream.qqmusic.qq.com/"+purl;
		if(!SongUrl.contains("vkey")){
		return GetQQMusicSongUrl2(SongMid);
		}
		return SongUrl;
	}
		catch(Throwable o)
		{
		toast("QQ音乐"+SongMid+"直链2获取失败\n"+o);
			return GetQQMusicSongUrl2(SongMid);
		}
	toast("QQ音乐"+SongMid+"直链2获取失败");
	return "";
}

public String GetQQMusicSongUrl2(String SongMid)//来自绿逗以及刺痛
{
try{
String cookie="p_uin=o0"+myUin+";skey="+getSkey()+";p_skey="+getPskey("y.qq.com");
			String text = http.getWithAgentCookie("https://i.y.qq.com/v8/playsong.html?songmid=" + SongMid,"Mozilla/5.0 (Linux; U; Android 8.1.0; zh-cn; BLA-AL00 Build/HUAWEIBLA-AL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 Mobile Safari/537.36",cookie);
		int index = text.indexOf("\"m4aUrl\":\"");
		int index2 = text.indexOf("\",", index + 11);
		String SongUrl = text.substring(index + 10, index2);
		if(!SongUrl.startsWith("http")) {
		toast("QQ音乐"+SongMid+"直链3获取失败");
		return "";
		}
		return SongUrl;
			}
			catch(Throwable e)
		{
			toast("QQ音乐"+SongMid+"直链3获取失败\n"+e);
	        return ""+e;
		}
}
public static String SearchQQMusicList(String searchName)
{
    if(searchName != null && !searchName.isEmpty())
    {
        try
        {
            String jsonStr = http.get("http://u.y.qq.com/cgi-bin/musicu.fcg?data=%7B%22comm%22%3A%7B%22ct%22%3A%221%22%2C%22cv%22%3A%2210180005%22%2C%22vG%22%3A%2210180005%22%7D%2C%22req%22%3A%7B%22method%22%3A%22DoSearchForQQMusicMobile%22%2C%22module%22%3A%22music.search.SearchBrokerCgiServer%22%2C%22param%22%3A%7B%22search_type%22%3A0%2C%22query%22%3A%22" + searchName + "%22%2C%22page_num%22%3A1%2C%22num_per_page%22%3A"+listnum+"%2C%22grp%22%3A0%7D%7D%7D");
            JSONObject json = new JSONObject(jsonStr);
            json = json.get("req").getJSONObject("data").get("body");
            JSONArray array = json.getJSONArray("item_song");
            JSONArray mJson = new JSONArray();
            for(int i = 0; i < array.length(); i++)
            {
                JSONObject TempJson = new JSONObject();
                JSONObject mRawJson = array.getJSONObject(i);
                try
                {
                    TempJson.put("name", mRawJson.getString("title").replaceAll("<.*?>",""));
                    TempJson.put("mid", mRawJson.getString("mid"));
                   // TempJson.put("filename", mRawJson.get("file").get("media_mid"));
                    JSONArray array=mRawJson.getJSONArray("singer");
                    String singer="";
            for(int h=0;h<array.length();h++)
{	
if(h==0) singer = array.getJSONObject(h).getString("name");
else singer = singer+"/"+array.getJSONObject(h).getString("name");
}
            TempJson.put("singer", singer);
                    
                    TempJson.put("albumname", mRawJson.getJSONObject("album").getString("name")/*+mRawJson.get("lyric")*/);
                    mJson.put(TempJson);
                }
                catch(Exception w)
                {
                    continue;
                }
            }
            JSONObject returnObj = new JSONObject();
            returnObj.put("data", mJson);
            return returnObj.toString();
        }
        catch(Exception e)
        {
        toast(""+e);
            return("Error 500\n" + e.toString());
        }
    }
    else
    {
        return("name?");
    }
}


public static String SearchQQMvList(String searchName)//来自绿逗以及刺痛
{
    if(searchName != null && !searchName.isEmpty())
    {
        try
        {
            String jsonStr = http.get("http://u6.y.qq.com/cgi-bin/musicu.fcg?data=%7B%22req%22%3A%7B%22method%22%3A%22DoSearchForQQMusicMobile%22%2C%22module%22%3A%22music.search.SearchBrokerCgiServer%22%2C%22param%22%3A%7B%22search_type%22%3A4%2C%22query%22%3A%22"+searchName+"%22%2C%22page_num%22%3A1%2C%22num_per_page%22%3A"+listnum+"%2C%22grp%22%3A0%7D%7D%7D%0A%20%20%20%20%20");
            JSONObject json = new JSONObject(jsonStr);
            json = json.getJSONObject("data").getJSONObject("body");
            JSONArray array = json.getJSONArray("item_mv");
            JSONArray mJson = new JSONArray();
            for(int i = 0; i < array.length(); i++)
            {
                JSONObject TempJson = new JSONObject();
                JSONObject mRawJson = array.getJSONObject(i);
                try
                {
                    TempJson.put("name", mRawJson.getString("mvname").replaceAll("<.*？>",""));
                    TempJson.put("singer", mRawJson.getString("singername"));
                    TempJson.put("vid", mRawJson.getString("vid"));
                    mJson.put(TempJson);
                }
                catch(Exception w)
                {
                    continue;
                }
            }
            JSONObject returnObj = new JSONObject();
            returnObj.put("data", mJson);
            return returnObj.toString();
        }
        catch(Exception e)
        {
            return("Error 500\n" + e.toString());
        }
    }
    else
    {
        return("name?");
    }
}

public void SendMusic(Object data,JSONObject json,int type)
{
String quntext=data.MessageContent;
String qun=data.GroupUin;
String qq="";
String at="";
if(data.IsGroup||data.IsChannel){at="[AtQQ="+data.UserUin+"]\n";}
else  qq=data.msg.frienduin;
		String filename=json.getString("name")+"-"+json.getString("singer").replace("/",",");
		String url=json.getString("playurl");
		int ii=url.indexOf("?");
		if(ii==-1) {sendMsg(qun,qq,"歌曲"+filename+"直链获取失败!\n可能存在版权问题或者单独收费\n"+"https://i.y.qq.com/v8/playsong.html?songmid="+json.get("mid"));
		return;}
		String name=url.substring(ii-6,ii);
		name=name.substring(name.indexOf("."));
		
		if(type==3){
		String path=RootPath+"音乐/"+filename+name;
		sendMsg(qun,qq,at+"开始下载音乐并上传\n♪"+filename+name+"♪\n请稍后。。");
		boolean dow=http.DownloadToFile(url,path,2);
		
		if(!dow) {sendMsg(qun,qq,at+"发送失败请自行下载\n"+url);return;}
		upload(qun,qq,path);
		FileMap.put(filename+name,url);
	/*	new java.lang.Thread(new Runnable()
{
public void run()
{
Thread.sleep(12000);
if(FileMap!=null&&FileMap.containsKey(filename+name)) FileMap.remove(filename+name);
if(delFile==1) delAllFile(new File(path),0);
}
}).start();*/
}
else if(type==2){
		sendMsg(qun,"","开始下载音乐并发送语音\n♪"+filename+name+"♪\n请稍后。。");
		sendMsg("",myUin,""+url);
		if(name.contains(".mp3")){
		String path=Environment.getExternalStorageDirectory().getAbsoluteFile()+"/LD_Module/Voice/QQ点歌/"+filename+"/*-"+filename+name;
		 boolean dow=http.DownloadToFile(url,path,3);
		if(!dow) {sendMsg(qun,qq,at+"发送失败请自行下载\n"+url);return;}
		File file = new File(path);
		String[] files = file.getParentFile().list();
		if(yuyin==0)
		{
		for(int i=1;i<=files.length;i++){
		sendVoice(qun,qq,path.replace("*-",i+"-"));
		}
		}
		else if(yuyin>files.length) sendVoice(qun,qq,path.replace("*-",files.length+"-"));
		else sendVoice(qun,qq,path.replace("*-",yuyin+"-"));
		if(delPtt==1) delAllFile(file.getParentFile(),0);
		//toast("下载完成2");
		 /*
		 for(File pt:file.getParentFile().listFiles()){
		 //toast(pt.getAbsolutePath());
         sendVoice(qun,qq,pt.getAbsolutePath());
		 }*/
		 }
		else{
		String path=Environment.getExternalStorageDirectory().getAbsoluteFile()+"/LD_Module/Voice/QQ点歌/"+filename+name;
		boolean dow=http.DownloadToFile(url,path,1);
		if(!dow) {sendMsg(qun,qq,at+"发送失败请自行下载\n"+url);return;}
		sendVoice(qun,qq,path);
		if(delPtt==1) delAllFile(new File(path),0);
		}
		}
		else if(type==0) sendMsg(qun,qq,"♪"+filename+"♪下载地址\n"+url);
		else{
			String JsonXml = MusicXml;
			JsonXml = JsonXml.replace("{标题}", json.getString("name")).replace("{作者}", json.getString("singer")).replace("{图标}", json.getString("pic")).replace("{链接}", json.getString("playurl").replace("&","&amp;"));
			JsonXml = JsonXml.replace("{歌曲}", (json.getString("playurl").replace("&","&amp;")));
			JsonXml = JsonXml.replace("ICONUrl", "https://y.qq.com/favicon.ico");
			sendCard(qun,qq, JsonXml);
			}
}


public static void delAllFile(File directory,int type){
if(!directory.exists()) return;
String text="删除";
    if (!directory.isDirectory()){
    text+="\n文件"+directory.getAbsolutePath();
        directory.delete();
    } else{
        File [] files = directory.listFiles();

        // 空文件夹
        if (type==0&&files.length==0){
           directory.delete();
          text+="\n空文件夹"+directory.getAbsolutePath();
            return;
        }

        // 删除子文件夹和子文件
        for (File file : files){
            if (file.isDirectory()){
                delAllFile(file,type);
            } else {
                file.delete();
                text+="\n文件"+file.getAbsolutePath();
            }
        }

        // 删除文件夹本身
        if(type==0){
        directory.delete();
      text+="\n文件夹" + directory.getAbsolutePath();
      }
    }
   // if(!text.equals("删除")) toast(text);
}

/*
if(srlu(路径+"朝云/若年.java")!=23438)
{
tip();
//LINZ();
}
*/