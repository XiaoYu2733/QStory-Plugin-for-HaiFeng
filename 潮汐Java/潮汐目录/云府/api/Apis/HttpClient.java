import java.net.*;
import java.io.*;
public class HttpApi
{
	public String get(String url)
	{
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
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
	public String get2(String url, int timeOut)
	{
		StringBuffer buffer = new StringBuffer();

            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
                uc.setConnectTimeout(timeOut);
                uc.setReadTimeout(timeOut);
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
	public boolean DownloadToFile(String url,String filepath,int type) throws Exception
    {
File file = new File(filepath.replace("*-","1-"));
if(!file.getParentFile().exists())    
       {    
        file.getParentFile().mkdirs(); 
       }
if(file.exists()) {
//toast(file.length()+"\n"+http.getFileLength(url));
if(type==2&&file.length()==http.getFileLength(url)) return true;
else if(file.length()>7000)return true;
}
        InputStream is = null;
        try {
            URL urlsssss = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) urlsssss.openConnection();
            //urlConn.setConnectTimeout(10000);
            //urlConn.setReadTimeout(60000);
            is = urlConn.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            int len2=0;
            int i=1;
            FileOutputStream out = new FileOutputStream(filepath.replace("*-","1-"), false);
    if(type==1)
    {  //带开头信息第一段
    while ((len = is.read(bs)) != -1&&len2<10485760-1024) 
 	 {                                  
 	  out.write(bs, 0, len);
 	  len2=len+len2;                        
     } 
     }
     else if(type==2)
    {  //完整下载
    bs = new byte[20480];
    while ((len = is.read(bs)) != -1) 
 	 {                                  
 	  out.write(bs, 0, len);     
 	  //len2=len+len2;                
     } 
     }
    else if(type==3)
    {  //mp3去开头信息分段
    len = is.read(bs);
    out.write(bs, 0, len);
    out=new FileOutputStream(filepath.replace("*","1"),false);
    Object size=urlConn.getContentLength()-len;
 	  int cut=Math.ceil(size/10484736f);
 	  int cutsize=(size/cut);
 	 // if(cutsize>1048576) cutsize=1048576;
 	  bs = new byte[1024];
 	  len=0;
 	//  toast(size+"\n"+cut+"\n"+cutsize);
    while ((len = is.read(bs)) != -1)
 	 {
 	  out.write(bs, 0, len);
 	  len2=len+len2;  
 	 if(len2>=cutsize&&i<cut) 
 	    {
 	      i=i+1;
 	      len2=0;
 	      out=new FileOutputStream(filepath.replace("*-",i+"-"),false);
 	    }
     } 
     }
            out.close();
            is.close();
            //toast("下载完成1");
        } catch (IOException e) {
        toast("下载失败"+e);
            return false;
        }
        finally {
            try {
                is.close();
            } catch (IOException e) {
                toast("下载失败"+e);
            }
        }
        return true;
    }
	public String post(String url,String params) {
        try {
            URL urlObjUrl=new URL(url);
            URLConnection connection =urlObjUrl.openConnection();
            connection.setDoOutput(true);
            OutputStream os=connection.getOutputStream();
            os.write(params.getBytes());
            os.close();
            InputStream iStream=connection.getInputStream();
            byte[] b=new byte[1024];
            int len;
            StringBuilder sb=new StringBuilder();
            while ((len=iStream.read(b))!=-1) {
                sb.append(new String(b,0,len));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	public String getWithAgent(String url,String Agent) {
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
				uc.setRequestProperty("User-agent",Agent);
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
	public String getWithAgent(String url,String Agent,String Referer) {
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
				uc.setRequestProperty("User-agent",Agent);
				uc.setRequestProperty("referer",Referer);
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
	public String getWithAgentCookie(String url,String Agent,String Cookie) {
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
				uc.setRequestProperty("User-agent",Agent);
				uc.setRequestProperty("Cookie",Cookie);
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
	public String get(String url,String Cookies,String referer) {
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
				uc.setRequestProperty("referer",referer);
				uc.setRequestProperty("cookie",Cookies);
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
	public static String Post(String Url,String PostData,String Referer)
    {
        try {
            URL urlObjUrl=new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) urlObjUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Referer",Referer);
            conn.setRequestProperty("Origin","https://music.liuzhijin.cn");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
            conn.setRequestProperty("X-Requested-With","XMLHttpRequest");
            OutputStream os=conn.getOutputStream();
            os.write(PostData.getBytes());
            os.flush();
            os.close();
            InputStream iStream=conn.getInputStream();
            byte[] b=new byte[1024];
            int len;
            StringBuilder sb=new StringBuilder();
            while ((len=iStream.read(b))!=-1) {
                sb.append(new String(b,0,len));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
public long getFileLength(String downloadUrl) 
{
long lg;
		  if(downloadUrl == null || "".equals(downloadUrl)){
			  lg= 0; 
		  }
		  else{
	      URL url = new URL(downloadUrl);
	      HttpURLConnection conn = null;
	      try {
	          conn = (HttpURLConnection) url.openConnection();
	          lg=  conn.getContentLength();
	      } catch (IOException e) {
	          lg= 0;
	      } finally {
	          conn.disconnect();
	    }
	    }
	    return lg;
     }
}
HttpApi http = new HttpApi();