

public void 阅景(Object 汐)
{
if(简读开关(图片开关)&&!秋(myUin)&&!简读用户(代管))return;

if(简读开关(菜单限制)&&!秋(myUin)&&!简读用户(代管))return;

if(等("开",读("全局","菜单限制"))&&!秋(myUin)&&!简读用户(代管))return;

if(故("壁纸"))
{
String 图=访问("http://xiaobai.klizi.cn/API/img/bizhi.php");
缓冲(50);
景(图);
}
if(故("随机表情"))
{
String 好=访问("http://api.tangdouz.com/zzz/face.php");
景(好);
}

if(故("随机风景"))
{//提示("p");
String 图=访问("http://apii.gq/api/fengjingtu.php");
缓冲(50);
//提示("y");
语景("",图);
//提示("g");
}

if(故("随机二次元"))
{
String 图="http://apii.gq/api/erciyuan.php";
缓冲(50);
景(图);
}

if(故("风景"))
{
String 图="https://api.vvhan.com/api/view";
缓冲(50);//21 6 30
景(图);
}

if(故("原神图"))
{
String 图 =访问("http://ovooa.com/API/yuan/api?type=text");
景(图);
}

if(故("动漫图"))
{
String a=访问("http://lkaa.top/API/Lovely/api?type=text");
景(a);
}
if(故("横屏动漫图"))
{
String 嗯="http://ovooa.com/API/dmt/api.php";
String 文=解析(嗯,"text");
景(文);
}


/*
if(故始("赞@"))
{
String 图="http://xiaobai.klizi.cn/API/ce/zan.php?qq=";
String 不好=汐.艾特列表[0];
String 好=图+不好;
景(好);
}
*/
/*
if(故("随机图"))
{
String 好="http://xiaobai.klizi.cn/API/img/miyou.php";
String 图=解析(好,"img");
景(图);
}
*/
if(汐.用户.equals(myUin)||简读用户(代管)||读开关("开","涩涩限制"))
{
if(故("妹子图"))
{
String 千="http://lkaa.top/API/meizi/api.php";
String 图 = 解析(千,"text");
景(图);
return;
}

if(故("白丝")) {
//获取返回
String 原文 = 访问("http://api.xn--7gqa009h.top/api/bshs?msg=白丝&type=text");
if(原文.equals(""))语("未知原因没有获取到图片");
//创建原文json对象
JSONObject json = new JSONObject(原文);
//将json对象更新为 原本的data内容 此时依然是json对象
json = new JSONObject(json.getString("data"));
//迭代器 这个可能比较高级
String pic="";
Iterator it = json.keySet().iterator();
while (it.hasNext()/*还有内容没有读取的话就继续循环*/) {
    String key = it.next();//json的本质是 {"key" : "值" ,"key2" : "值" }
    pic += "[PicUrl="+json.get(key)+"]";
    //+=代表直接在原本的基础上加
        }
语(pic);
}
if(故("cos图集"))
{
String qun = 汐.群;//提前定义变量,不然会有概率产生bug
try{

String url = 访问("http://ovooa.com/API/cosplay/api.php");
JSONObject json = new JSONObject(url);
String url2 = json.getString("data");
String Title = new JSONObject(url2).getString("Title").replace("\"","");
String st = new JSONObject(url2).getString("data").replace("\"","");
st = st.replace("[","");
st = st.replace("]","");
String[] sp = st.split(",");


if(sp.length==0)sendMsg(qun,"","未知原因没有得到cos图集 数量0");
else{
sendMsg(qun,"","标题: "+Title+"\n\n数量 "+sp.length + "\n正在发送 请稍等哦\n图片多可能消耗长时间");
new Thread(new Runnable(){
public void run()
{
try{
StringBuilder sb = new StringBuilder();//创建字符串可写区
ArrayList list_1 = new ArrayList();//创建盒子
    for(int i=0;i<=sp.length-1;i++)
   {
        sb.append("[PicUrl="+sp[i]+"]");//往可写区添加
        if(i !=0 &&( i%20==0 || i== sp.length-1 ) )//判断当前循环次数是否是20的倍数且 或 当前循环次数是最后一次
       {
        list_1.add(sb.toString());
        sb.delete(0, sb.length());//删除可写区所有内容
       }
   }
   //循环发送盒子内的内容
   for(String PicMsg : list_1)
   {
   try{
   sendMsg(qun,"",PicMsg);
   }catch(Exception e){sendMsg(qun,"","缓存错误");break;}
   }
   
}catch(Exception e){
sendMsg(qun,"","发送图片错误");
}}
}).start();
}

}catch(Exception e){sendMsg(qun,"","获取cos图集失败");return; }

}

if(故("妹子"))
{
String 图="https://api.linhun.vip/api/Littlesister?type=json";
String 访 = 解析(图,"img");
景(访);
}
 if(故("看腿"))
{
String 图=访问("http://api.tangdouz.com/mt.php");
景(图);
}
if(故("美腿"))
{
String 图=访问("http://api.tangdouz.com/mt1.php");
景(图);
}
if(故("美女图"))
{
String 图=访问("http://xiaobai.klizi.cn/API/img/beauty.php?data=&");
景(图);
}
if(故("美女"))
{
String 图=访问("http://ovooa.com/API/meinv/api.php?type=text");
景(图);
}
if(故("美女壁纸"))
{
String 图=访问("http://ovooa.com/API/guang/api?n=1&type=text");
景(图);
}



}
}