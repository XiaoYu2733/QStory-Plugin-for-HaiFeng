
import java.util.*;
import java.lang.*;
public String 转1(String str) { 
        char[] strChar = str.toCharArray(); 
        String result = ""; 
        for (int i = 0; i < strChar.length; i++) { 
            result += Integer.toBinaryString(strChar[i]) + ""; } 
        return result;  } 
public String 转2(String a){
char[] ac=a.toCharArray();
String result="";
for (int i = 0; i < ac.length; i++) {
if(Integer.parseInt(String.valueOf(ac[i]))==1){result=result+"\u200b";}
if(Integer.parseInt(String.valueOf(ac[i]))==0){result=result+"\u200c";} } 
return result; 
}



public String 转3(String x){
return 转2(转1(x));
}

public String getMsg(String msg,String qun,int i){

String a=msg;

if(getChatType()==1&&msg.contains("@")&&"开".equals(getString(getCurrentGroupUin(),"隐藏艾特")))  {
String text=msg.split("@")[0];
a=text+转3(msg.replace(text,""));}
return a;
}

