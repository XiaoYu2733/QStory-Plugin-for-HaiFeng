List 龙王表情=Arrays.asList(new String[]{
"http://gchat.qpic.cn/gchatpic_new/0/0-0-7035BC367DC769BDF75631F243CACE1A/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-D67C3049689FEA4BA331BB59D212320D/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-08CBB10FDDACEC7EE36EEB1841E9D7E3/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-A3382D35177E48669937C6AD5D4C9155/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-0FC007E5ABF59D6DBB4808C02C88BDCE/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-BFA0EB6C67F1B4DCF5C7B18EDA95229F/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-5C7E76DD64A9A4FDD24BB4F0980B3558/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-11A9570ECA4D23F16708F751A9B48AB0/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-4B1E742D02B51415BBBCD56F0D544459/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-99D7566718906BF3E7397D66E4341480/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-4E7D0A330531FA66525DF9D22E9FA003/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-FCDC82D2D8408FC790546E263C110153/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-8820141273A0C7C32493A7ACD67ECC1A/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-38F0487820A6AE1E44DBE03F0AD47575/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-DF7E8B6E0D764ACAEEA6F5A34DB1FDE9/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-35A3A8D73453205FDFB087AD7BE6C341/0?term=2",
"http://gchat.qpic.cn/gchatpic_new/0/0-0-F16E9BCFFE1408A46425AAE8651FD52A/0?term=2",
});


ArrayList listy = new ArrayList();
public void 知年(Object 汐)
{

if(简读开关(查询功能)&&!秋(myUin)&&!简读用户(代管))return;

if(简读开关(菜单限制)&&!秋(myUin)&&!简读用户(代管))return;

if(等("开",读("全局","菜单限制"))&&!秋(myUin)&&!简读用户(代管))return;


if(故("查龙王")||故("本群龙王")||故("今日龙王")||故("龙王"))
{
String 表情=龙王表情.get(new Random().nextInt(龙王表情.size()));
JSONObject a = new JSONObject(getDrago(myUin,getSkey(),getPskey("qun.qq.com"),汐.群));
String b=a.getString("currentTalkative");
String 龙王QQ= new JSONObject(b).getString("uin");
String 头像="http://q2.qlogo.cn/headimg_dl?dst_uin="+龙王QQ+"&spec=100";
String 龙王名字=群昵称(汐.群,龙王QQ);
语("今日龙王是 :\n[PicUrl="+头像+"]"+龙王名字+"("+龙王QQ+")[PicUrl="+表情+"]");
}

if(故("获取本群信息")||故("查看本群信息"))
{
String 群名="本群昵称 : "+群名(汐.群)+"\n";
String 人数="群聊人数 :"+群人数(汐.群)+"\n";
String 创建="创建时间 :"+群创建时间(汐.群)+"\n";
String 群主="群主:\n"+名(群主账号(汐.群))+"("+群主账号(汐.群)+")\n";
String 管理组1="管理员 :\n"+论(管理组名(汐.群).replace("]",""),"[","")+"\n";
String 管理组2=论(管理组1,"]","");
语(群名+人数+创建+群主+管理组2);
}


if(故("当前时间"))
{
语(getTime());
}

if(故始("搜图"))
{
if(汐.消息.contains("扑克")||汐.消息.contains("老虎机")||汐.消息.contains("棋牌")||汐.消息.contains("快乐飞艇")||汐.消息.contains("斗地主")||汐.消息.contains("大法伦")||汐.消息.contains("一分"))
{
禁言(汐.用户,60*60*24*10);
撤回();
回("不合规的违法内容,已拒绝该请求(作 : 不好意思小傻瓜，我已经料到你的想法了哦)");
return;
}

缓冲(50);
String 啊这=残花(2);
语("正在搜索，请稍等");


if(啊这.equals(""))
{
语("您未输入搜索内容");
return;
}
else{
String 图=访问("http://101.34.216.55:90/api/st.php?id=360&msg="+啊这);
语景("搜索结果:",图);
}
}


if(故始("查业务"))
{
if(汐.消息.contains("@"))
{
if(舞(汐.艾特列表)==0)
{
回("你艾特的人呢？");
return;}
else{
String qq=汐.msg.AtList[0];
String url = 访问("http://lkaa.top/API/QQ_info/?uin="+qq+"&QQ=&Skey=&Pskey=&type=text");
语(url);return;}
}	
String text = 论(残花(3)," ","");
	if(正则(text,"[1-9][0-9]{4,10}"))
{
	String url = 访问("http://lkaa.top/API/QQ_info/?uin="+text+"&QQ=&Skey=&Pskey=&type=text");
	    语(url);return;
	    }
	else
回("查询失败,可能存在不合法字符,应是 查业务@ 或正确Q号");
}
	    
	    




}