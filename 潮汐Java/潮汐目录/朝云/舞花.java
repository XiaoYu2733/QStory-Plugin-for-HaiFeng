public void 圆舞(Object 汐)
{
if(故("开启调试功能"))
{
存(汐.群,"调试功能","开");
语("调试功能已打开，请发送菜单查看");
}

if(故("关闭调试功能"))
{
存(汐.群,"调试功能",null);
语("调试功能已关闭");
}



if(读开关("开","调试功能"))
{

if(故("调试功能"))
{
String CD=
 "*此功能并不是不稳定*\n"
+"*需要时测试使用*\n" 
+"*请不要没必要的好奇*\n"
+"#rm  (重置大部分开关状态,开关机黑名单等)\n"
+"#tc   (再播放一次初始化弹窗,会进入初始化模式)\n"
+"#qun (当前群人数)\n"
+"#qunUin (列出当前群聊所有QQ号)\n"
+"#hmd (黑名单高频扫描,#hmd k是开,#hmd g是关)\n"
+"#rmw (删除目录所有空文件夹)";
语(CD);
}

if(故("#tc")){
存("初次使用","是否完成",null);
语("已改变相应设置,可能需要再加载脚本才能看到弹窗");}                
//getGroupList
if(故("#rm"))
{
语("开始重置");
	
	全弃(菜单开关);
	全弃(图片开关);
	全弃(违禁词);
	全弃(代管);
	全弃(黑名单);
	全弃(管理操作);
	全弃(一言);
	存(汐.群,"临时保险",null);
	存(汐.群,"隐藏艾特",null);
	存(汐.群,"涩涩限制",null);
	存(汐.群,"退群拉黑",null);
	存("艾特回","开关",null);
	存(汐.群,"秩",null);
	存(汐.群,"自助头衔",null);
	存(汐.群,"防止封群",null);
	存(汐.群,"防止炸群",null);
	存(汐.群,"调试功能",null);
	存("初次使用","是否完成",null);
	
语("重置完成，包括开关机与本功能，部分功能需要重载脚本才会生效");
存(汐.群,"调试功能",null);
	全弃(开关机);
}


if(故("#hmd k"))
{
存("黑名单扫描","开关","开");
语("已开启黑名单高频扫描,踢出的日志将会存到"+日志);
}
if(故("#hmd g"))
{
存("黑名单扫描","开关","关");
语("黑名单扫描已关闭");
}






 if (故("#qun"))
		{
			String groupUin = 汐.群;
			try
			{
				groupUin = 汐.消息.split("#qun ")[1];
			}
			catch (Throwable e)
			{
				groupUin = 汐.群;
			}
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
		String UinText = "GroupUin = " + 汐.群 + "\n" + "UserUin = " + 汐.用户 +  "\n\n\n";
						int tmp = 0;
						for (Object target : getGroupMemberList(groupUin))
							++tmp;
						UinText += "经检测，该群（" + groupUin + "）共有 " + tmp + " 名群成员啊喵~";
						回(UinText);
					}
					catch (Throwable e)
					{
						回("发生错误，本 QQ 可能不在群 " + groupUin + " 中！");
					}
				}
			}).start();
			return;
		}

if (故("#qunUin"))
		{
			String groupUin = 汐.群;
		
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						String UinText = "GroupUin = " + 汐.群 + "\n" + "UserUin = " + 汐.用户 +  "\n";
						int tmp = 0;
						for (Object target : getGroupMemberList(groupUin))
						{
							UinText += "\n" + target.UserUin;
							++tmp;
						}
						UinText += "\n\n\n经检测，该群（" + groupUin + "）共有 " + tmp + " 名群成员啊喵~";
						回( UinText);
					}
					catch (Throwable e)
					{
						回("发生错误，本 QQ 可能不在群 " + groupUin + " 中！");
					}
				}
			}).start();
			return;
		}



}

}