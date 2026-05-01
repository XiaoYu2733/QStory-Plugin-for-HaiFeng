//当你真正被爱的时候  不用那么漂亮
package com.thunisoft.cail.utils;
import com.sun.istack.internal.NotNull;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
private static final Map Arab2Chinese = new HashMap() {
{
put('零', 0);put('一', 1);
put('二', 2);put('三', 3);
put('四', 4);put('五', 5);
put('六', 6);put('七', 7);
put('八', 8);put('九', 9);
put('十', 10);
}
};
private static final Map UnitMap = new HashMap() {
{
put('十', 10);put('百', 100);
put('千', 1000);put('万', 10000);
}
};
private static Pattern pattern = Pattern.compile("[零一二三四五六七八九十]?[十百千万]?");
public static Integer 久恋(String chinese) {
Objects.requireNonNull(chinese);
Integer result = 0;
Matcher matcher = pattern.matcher(chinese);
while (matcher.find()) {
String res = matcher.group(0);
if (res.length() == 2) {
result += Arab2Chinese.get(res.charAt(0)) * UnitMap.get(res.charAt(1));
} else if (res.length() == 1) {
if (UnitMap.containsKey(res.charAt(0))) {
result *= UnitMap.get(res.charAt(0));
} else if (Arab2Chinese.containsKey(res.charAt(0))) {
result += Arab2Chinese.get(res.charAt(0));
}
}
}
 if(chinese.startsWith("十"))
 {
 return 10+result;
 }
return result;
}

String 已=myUin;
import android.app.*;
import android.app.Dialog;
import android.widget.*;
import android.content.*;
import android.text.*;


public class 潮汐
{
	String 消息;
	String 源;
	int type;
	long texttime;
	String 用户;
	String 群;
	ArrayList 艾特列表;
	boolean 远景;
	boolean isqun;
	String[] atList;
	String name;
	Object msg;
	}

潮汐 汐 = new 潮汐();
package com.accord.demo;
 
import java.security.Key;
 
import javax.crypto.Cipher;
 
public class EncrypDES {
	
	
	private static String strDefaultKey = "临江踏雨不返";
 
	private Cipher encryptCipher = null;
 

	private Cipher decryptCipher = null;
 
	
	public EncrypDES() throws Exception {
		this(strDefaultKey);
	}
 

 
	public EncrypDES(String strKey) throws Exception {
 
	
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}
 
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
	
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
		
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}
 
	
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
	
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}
 

	public String CL(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}
 

	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}
 

	public String CX(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}
 

	private Key getKey(byte[] arrBTmp) throws Exception {
	
		byte[] arrB = new byte[8];
		
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
	
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}
 }
 EncrypDES t = new EncrypDES();
 public void tip(){
   ThisActivity = getActivity();
	ThisActivity.runOnUiThread(new Runnable()
	{
		public void run()
		{
			TextView textView = new TextView(ThisActivity);
	
			textView.setText(t.CX("8489b5b68617701a253ffd8664ddc5d11ded54a136896da8683d325a11759e5c1ad9dfcee138ea0f605013b0dd9b5a21"));
			textView.setTextSize(25);
			textView.setTextColor(android.graphics.Color.RED);
			TextView textView1 = new TextView(ThisActivity);
		
		textView1.setText(t.CX("36a61a81b4e0931e310a3d23cd03748792f44359c935b4be95129cd4b9b69336a0c4d49ca7f17f0b271015591402b48a0d8c950dcc1e0b70fbb593ea31fcae3ed644c10738562913a7239803762984bc63a011dcb7851bac"));
			textView1.setTextColor(android.graphics.Color.GREEN);
		textView1.setTextSize(20);
			LinearLayout layout=new LinearLayout(ThisActivity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        layout.addView(textView1);
 
		AlertDialog.Builder dialog1=	new AlertDialog.Builder(ThisActivity, AlertDialog.THEME_HOLO_LIGHT);
		dialog1.setView(layout);
		dialog1.setPositiveButton(t.CX("c9a2bf38519ffb0644d35bb1eefd4022"), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialogInterface, int i)
				{
			tip();
				}
			});
			dialog1.setNegativeButton(t.CX("fc08c1eca9c53352"), new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialogInterface, int i)
				{
					tip();
				}
			});
			dialog1.setCancelable(false);							
			dialog1.show();
		}
	});			
}

List 雾迷=Arrays.asList(new String[]{"http://gchat.qpic.cn/gchatpic_new/0/0-0-3A1CDF443B235D30289C6273CE7A33A1/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-281C2961B43294B16D530F77E4C7F48D/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-9CA6C14958CDEF4BCA58C9243D57B1F5/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-873B9A1CEBB0D4BB7DE4D3FE18ADDD27/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-7911C902E3BEF74F2BA9E0EAC4F30A7F/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-97E03F3DA9B9E131A3D1B1E814272EC0/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-54DE85E2666D3BB40A94C7291AAE9DBC/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-1429E51CC5555A017976F99AFAABCE1C/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-09CA4A9134061D1C10D11850A05A5FEA/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-A6E102546FEA84DBF7E269B619324364/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-7537AF7A8AEAE5945B696C16AFF4AC51/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-6954D4D55B84AFC9927210EBFF860248/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-C285C117F3731C86DF308FADC8CF5E8E/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-145B27831E6497C32FBF0F96CEF4EC71/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-EDADA1C7FA3D1C9B9AB7A48127B85474/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-EA634B08D01B26AD3ADB94AEF3D041B0/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-A6B10724F7269FFD2C8A5BD0C1F13E77/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-3C197B2DA05A2BBFA459FBCF2FE268BD/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-D8459C14BFDEE9BB7FACBA7ED393A29F/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-63E9E845F6B054DBEB1C14625B7B9590/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-6D50AE9A5C44667A528A9BE121D14A96/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-27B75959596A83027C7C4B8A51DA6132/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-3A1CDF443B235D30289C6273CE7A33A1/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-9856FB7285B9069176D9FC2EA1014FE0/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-573FC0B32182D3CFFB94054C2678D618/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-D7BE2725AB4231E4A1C4C8C261665631/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-B185A9A4A5B11EBAABF45377DC65BE16/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-B9EF05DCCE16FAFBBE7DF1C3FF53E54B/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-BF2066AE9292D23D5C60C4B82ECB6242/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-CF731F3AD1D07D580D791FB621D8257D/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-CF731F3AD1D07D580D791FB621D8257D/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-95310C2997193565F8B49987DF1F3331/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-857B702991A6403120311FE2CE28994C/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-8C9CEC823F040EEA12A49FAE656DC6E6/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-0794D386714FD11F0816468A5DA41592/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-C268530961F951C3B9B0D73C920731FD/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-C268530961F951C3B9B0D73C920731FD/0?term=2","http://gchat.qpic.cn/gchatpic_new/2635294982/951366660-3032942149-EFCCF0154A6818D2F7515778DA6A2884/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-9CA576B3A098C3A633706CD51DD48C33/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-D80A98EE91D77B1BCC939839BCD65E2A/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-44B92D02C4787937CDABE6AE92F63FAF/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-8268E1E15F880107D61CD48A4A3B1742/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-1178DFA784F4C2E23EA90CEB9B10751A/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-BD515C00F21BBCB350F906463A29B130/0?term=2","http://gchat.qpic.cn/gchatpic_new/0/0-0-6D2DB0E595A28E362EC47760B5B67ED5/0?term=2"});

import java.util.Random;
public String scolor(){
        String red; 
        String green;
        String blue;
        Random random = new Random();  
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        green = Integer.toHexString(random.nextInt(256)).toUpperCase(); 
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();  
        red = red.length()==1 ? "0" + red : red ;  
        green = green.length()==1 ? "0" + green : green ; 
        blue = blue.length()==1 ? "0" + blue : blue ;
        String color = "#"+red+green+blue;
         return color;
       }
public int  n(){
Random r=new Random();
int s=r.nextInt(10);
return s;
}

