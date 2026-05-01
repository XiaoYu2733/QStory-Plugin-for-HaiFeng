
//百般花落

int 抢劫时间=2;
String 抢劫金额="200";

int 小号收费等级=20;

File 违禁词=new File(路径+"朝花/违禁词.txt");
File 代管=new File(路径+"朝花/代管.txt");
File 开关机=new File(路径+"朝花/开关机.txt");
File 菜单开关=new File(路径+"朝花/菜单开关.txt");
File 管理操作=new File(路径+"朝花/管理操作提示.txt");
File 图片开关=new File(路径+"朝花/图片开关.txt");
File 一言=new File(路径+"朝花/一言.txt");
File 黑名单=new File(路径+"朝花/黑名单.txt");
File 查询功能=new File(路径+"朝花/查询开关.txt");
File 日志=new File(路径+"朝花/日志/黑名单踢出日志.txt");
File 菜单限制=new File(路径+"朝花/菜单限制.txt");
File xglj=new File(路径+"朝花/日志/黑名单/");
if (!xglj.getParentFile().exists()){
  日志.getParentFile().mkdirs();
日志.createNewFile();}
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
FileWriter f=null;
BufferedWriter f1=null;
FileReader  fr  =  null;
BufferedReader f2=null;
public  static  void 简写(File ff, String a){
f=new FileWriter(ff,true);
f1=new BufferedWriter(f);
f1.append(a);
f1.newLine();
f1.close();
f.close();
}
public static ArrayList 简取(File ff){
if(!ff.exists()){
ff.createNewFile();}
String a;
FileReader reader = new FileReader(ff);
BufferedReader bReader = new BufferedReader(reader);
ArrayList list1 = new ArrayList();
while ((a = bReader.readLine()) != null) {
            list1.add(a);}
            bReader.close();
            reader.close();
            return list1;
}




public boolean 简读(String a,ArrayList l1){
boolean x=false;
for(int i=0;i<l1.size();i++){
if(a.contains(l1.get(i).toString())){
x=true; break;}
}
return x;}

public boolean 读违禁(ArrayList l)
{
return l.toString().contains(汐.消息);
}


public static void 简弃(File ff,String a){
ArrayList l1= new ArrayList();
l1.addAll(简取(ff));
if(l1.contains(a)){
l1.remove(a);
}
f=new FileWriter(ff);
f1=new BufferedWriter(f);
f1.write("");
f1.close();
f.close();
for(int i=0;i<l1.size();i++){
简写(ff,l1.get(i));
}
}
public static void 全弃(File ff){
f=new FileWriter(ff);
f1=new BufferedWriter(f);
f1.write("");
f1.close();
f.close();
}
import java.io.*;

//获取文件内字节
public int srlu(String a){
  int size;
 InputStream fh = new FileInputStream(a);
        size = fh.available();
        return size;
    fh.close();
    }

public boolean 简读文本(File ff,String 内容)
{
if(简取(ff).contains(内容))return true;
else return false;
}
public boolean 简读开关(File ff)
{
if(简取(ff).contains(汐.群))return true;
else return false;
}
public boolean 简读开关(File ff,String a)
{
if(简取(ff).contains(a))return true;
else return false;
}
public boolean 简读用户(File ff)
{
if(简取(ff).contains(汐.用户))return true;
else return false;
}
public boolean 简取用户(File ff,String a)
{
if(简取(ff).contains(a))return true;
else return false;
}

public boolean 用户简取(File a,String b)
{
if(b.contains(a))return true;
else return false;
}

public String 简读取(File file)
{
	FileInputStream inputStream = new FileInputStream(file);
	byte[] bytes = new byte[inputStream.available()];
	inputStream.read(bytes);
	inputStream.close();
	String sRet = new String(bytes, StandardCharsets.UTF_8);
	return sRet;
}
//获取文件中的所有内容 参数一是File的路径QQ 原搭配查黑+QQ使用 返回为字符
public String 取(String a)
{
File file = new File(路径+"朝花/日志/黑名单/"+a+".txt");

if (!file.exists()&&简取(黑名单).contains(a))
{
return "此人是黑名单但没有拉黑日志";
}

if (!file.exists())
{
return "此人并不是黑名单";
}

 FileReader reader = new FileReader(file);
 BufferedReader bReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String s = "";
 while ((s =bReader.readLine()) != null) {
 sb.append(s+"\n");
        }
        bReader.close();
        sb.delete(sb.length()-1,sb.length());//删除末尾一字
        String str = sb.toString();
      return str;
}      
public String 曲华(File file)
{
 FileReader reader = new FileReader(file);
 BufferedReader bReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String s = "";
 while ((s =bReader.readLine()) != null) {
 sb.append(s);
        }
        bReader.close();
        String str = sb.toString();
      return str;
}      
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
//删除文件夹 参数为路径
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
//删除文件
private static boolean deleteFile(String sPath) {  
		boolean flag = false;  
	    File file = new File(sPath);  
	
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}
//路径文件与文件夹一起删除,顺序为A-Z
	private static boolean deleteAZ(String sPath) {  
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
	
//路径文件文件夹一起删除,顺序为Z-A 
		private static boolean deleteZA(String sPath) {  
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

//创建文件夹(文件)写入文件 参数一文件名 参数二内容
public static void 写如(String b, String t) {

File file = new File(路径+"朝花/日志/黑名单/"+b+".txt");
if (!file.getParentFile().exists()){
 file.getParentFile().mkdirs();
file.createNewFile();}
        FileWriter fw = null;
       
            fw = new FileWriter(file);
            fw.write(t);
        
            if (fw != null) {
                fw.close();
                fw = null;
            }
    }

public static boolean 删(String a)
{
String sPath=路径+"朝花/日志/黑名单/"+a+".txt";
if(deleteFile(sPath))return true;
return false;
}

public static void 全删()
{
String sPath=路径+"朝花/日志/黑名单";
delez(sPath);
}


File 文案 = new File(路径+"诗秋-作语/诗落.txt");
if (!文案.getParentFile().exists()){
 文案.getParentFile().mkdirs();
文案.createNewFile();
}
//返回文件中随机一行的内容
public static String 进文(File f) 
{
	     String result = null;
	     Random rand = new Random();
	     int n = 0;
for(Scanner sc = new Scanner(f); sc.hasNext();)
	     {
	        ++n;
	        String line = sc.nextLine();
	        //循环输出文件每行内容
	        //toast("行数 "+n+" 内容 "+line);
	        if(rand.nextInt(n) == 0)
	           result = line;         
	     }
	     return result;      
}

//循环到a不是空为止 返回不是空的那条(行)文案
public static String 进语(File t)
{
if(曲华(文案).equals(""))return "文案文件获取失败 请前往官方下载渠道更新文案配置文件";
String a=进文(t);
int d=0;
//if(a.equals(""))
while(a.equals("")||a.contains("//"))
{
a=进文(t);
}
//if(!a.equals(""))
return a;
}

