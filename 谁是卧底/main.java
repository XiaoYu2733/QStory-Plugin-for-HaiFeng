//练手作 临江踏雨不返 (2376738596) 使用与更新请保留版权
//群 634941583
//少年 带我走吧,一起去远方,离开这个喧嚣的地方

String 路径 = appPath + "/奏舞/";
/*
load(appPath+"/Log/Log.java");
load(路径 + "夜星.java");
load(路径 + "暮光.java");
load(路径 + "鉴舞.java");
*/
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public static final String[] Civilians = { "平民" , "侦探" };
public static final String[] Killer = { "卧底" };
//时间数据类型中心
public class TimeType {
    public static final String night = "黑夜";
    public static final String voting = "投票";
    public static final String daytime = "白天";
    public static final int maxTime = 60*20;//最长游戏时间
    public static final int daytimeTime = 60;//白天时间
    public static final int votingTime = 60;//白天投票时间
    public static final int nightTime = 60;//黑夜时间
    public static final long sleepTime = 500;//防止过快双线程启动导致冲突而休眠线程 暂时没啥用
}
public static final boolean 是否隐藏QQ号信息 = true;
public static final int 线程池任务数量 = 20;
boolean run = false;
//K GroupUin V GroupInfo;
Map ongoingGroup = new HashMap();//正在游戏的群聊Data
Map preparation = new HashMap();//准备进行游戏的群聊<String Group,ArrayList<String>>
public String at(String qq) {
    return "[AtQQ="+qq+"]";
}


//自研简单磁悬浮列猫组加密-喵呜~
String help = "喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵喵呜呜呜呜~喵喵喵呜呜呜~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜喵~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵呜呜~喵呜呜呜呜呜喵~喵喵呜喵喵喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵喵喵呜呜喵~喵喵呜喵呜喵~喵喵呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜喵呜~喵呜呜呜呜喵喵~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜喵~喵呜呜呜喵呜呜~喵喵喵呜呜呜~喵喵呜呜呜呜~喵喵呜呜喵呜~喵喵喵呜呜喵~喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵喵喵呜呜呜~喵喵呜喵呜呜~喵喵喵呜呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜喵~喵呜呜呜呜喵喵~喵呜呜呜呜呜喵~喵呜呜呜呜喵喵~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜喵~喵呜呜呜呜呜喵~喵喵喵呜呜呜~喵喵呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜喵~喵喵呜喵呜呜~喵呜呜呜呜呜喵~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜呜呜喵~喵喵喵呜呜喵~喵喵呜呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵喵喵呜呜喵~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵喵呜呜喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵喵喵呜呜呜~喵喵呜呜呜喵~喵喵呜呜喵呜~喵喵呜呜呜呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵呜呜呜喵呜喵~喵喵喵呜呜喵~喵喵喵呜呜呜~喵喵呜呜喵呜~喵喵喵呜呜呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜呜喵呜~喵喵呜喵喵喵~喵喵呜喵呜呜~喵喵呜呜呜呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵喵呜喵呜喵~喵呜呜呜喵呜喵~喵喵喵呜呜喵~喵喵喵呜呜呜~喵喵呜呜喵呜~喵喵喵呜呜呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜呜喵呜~喵喵呜喵喵喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵呜呜~喵喵喵呜呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵呜呜呜呜呜喵~喵喵喵呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵呜呜呜呜喵呜~喵喵呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜呜呜喵~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜喵~喵呜呜呜呜喵喵~喵呜呜呜呜呜喵~喵呜呜呜呜喵喵~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵呜呜呜呜喵呜~喵呜呜呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜呜喵呜~喵喵喵呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵喵呜呜呜喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵喵呜呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜呜~喵喵喵呜呜呜~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵呜呜呜呜喵喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵喵喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵喵喵呜呜喵~喵喵喵呜呜呜~喵呜呜呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵喵呜喵呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵喵喵呜呜呜~喵喵喵呜呜呜~喵喵呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵呜呜~喵喵喵呜呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵呜呜呜呜呜喵~喵呜呜呜呜喵喵~喵呜呜呜呜呜喵~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵喵呜呜喵~喵喵喵呜呜呜~喵喵呜呜喵呜~喵喵喵呜呜呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜呜喵呜~喵喵呜喵喵喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵呜呜~喵喵喵呜呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵呜呜呜呜呜喵~喵喵喵呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵呜呜呜呜喵呜~喵喵呜喵喵呜~喵喵呜呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵喵喵呜呜喵~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵喵呜呜喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵喵喵呜呜呜~喵喵呜呜呜喵~喵喵呜呜喵呜~喵喵呜呜呜呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵呜呜呜呜喵喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵喵喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵喵呜喵呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵呜呜呜呜喵喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵喵喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜喵~喵呜呜呜呜喵喵~喵呜呜呜呜呜喵~喵呜呜呜呜喵喵~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵呜呜呜呜喵呜~喵呜呜呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜喵~喵呜呜呜呜呜喵~喵喵喵呜呜呜~喵喵呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜呜~喵喵喵呜呜呜~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵喵呜呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵喵喵呜呜喵~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵喵呜呜喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵喵喵呜呜呜~喵喵呜呜呜喵~喵喵呜呜喵呜~喵喵呜呜呜呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵喵呜~喵呜呜呜呜呜喵~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜呜~喵喵呜呜呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵喵呜呜喵呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜呜呜喵~喵喵呜喵呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜喵~喵呜呜呜呜喵喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵呜呜呜喵呜呜~喵喵喵呜呜喵~喵喵呜呜喵喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵喵喵呜呜喵~喵喵喵呜呜呜~喵呜呜呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵喵呜~喵呜呜呜呜呜喵~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜呜~喵喵呜呜呜喵~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵喵呜呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵喵呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜喵呜~喵喵喵呜呜喵~喵喵呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵喵喵呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵喵喵呜呜呜~喵喵呜呜喵喵~喵呜呜呜呜喵呜~喵呜呜呜喵呜呜~喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜呜喵呜~喵喵呜喵喵喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵喵喵呜呜呜~喵喵呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜呜~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵喵喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵呜呜呜呜喵喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵喵喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜喵~喵喵呜呜呜呜~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵喵喵呜呜喵~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵喵呜呜喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵喵喵呜呜呜~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵喵喵呜呜喵~喵呜呜呜呜呜喵~喵喵喵呜呜呜~喵喵呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵喵喵呜呜喵~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵呜呜呜呜呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵喵呜呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵呜呜~喵喵喵呜呜喵~喵呜呜呜呜喵喵~喵喵呜呜喵呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵喵呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵呜呜呜呜喵呜~喵喵呜呜呜喵~喵喵喵呜呜喵~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵喵呜呜喵~喵喵喵呜呜喵~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵喵喵呜呜呜~喵喵喵呜呜呜~喵呜呜呜喵呜呜~喵呜呜呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵喵呜~喵喵喵呜呜喵~喵喵呜喵呜呜~喵喵呜呜喵呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜呜~喵喵喵呜呜喵~喵喵喵呜呜呜~喵喵呜呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜喵呜~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜呜~喵喵喵呜呜喵~喵呜呜呜呜喵呜~喵喵呜呜呜呜~喵喵呜呜呜呜~喵呜呜呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵呜呜~喵呜呜呜呜喵呜~喵呜呜呜呜呜喵~喵呜呜呜呜呜喵~喵喵呜喵呜呜~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵呜呜呜呜喵呜~喵喵呜喵呜喵~喵喵喵呜呜呜~喵喵呜呜呜喵~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵呜呜呜呜喵呜~喵呜呜呜喵呜喵~喵呜呜呜呜呜喵~喵喵呜喵呜呜~喵喵呜呜喵呜~喵喵喵呜呜呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵呜呜呜呜喵呜~喵喵呜呜呜呜~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵呜呜呜喵呜喵~喵喵呜喵呜喵~喵喵喵呜呜呜~喵呜呜呜喵喵呜~喵呜呜呜呜喵呜~喵喵呜喵喵喵~喵呜呜呜喵呜喵~喵喵呜喵喵呜~喵喵喵呜呜呜~喵呜呜呜呜喵呜~喵喵喵呜呜喵~喵喵呜呜喵呜~喵呜呜呜喵呜喵~喵喵呜喵喵喵~喵呜呜呜呜喵呜~喵呜呜呜呜喵呜~喵喵喵呜呜喵~喵呜呜呜喵呜呜~喵喵呜呜喵呜~喵喵喵呜呜喵~喵喵呜呜喵呜~喵喵呜呜呜呜~喵喵呜呜喵喵~喵呜呜呜呜呜喵~喵喵呜呜喵呜~喵喵呜呜呜呜~喵喵呜呜喵喵~喵喵呜喵喵呜~喵喵呜呜喵喵~喵喵呜呜喵喵~喵喵呜呜喵喵~喵喵呜喵呜呜~喵喵呜呜喵喵~喵喵喵呜呜喵~喵喵呜呜喵喵~喵喵呜喵呜呜~喵喵呜呜喵喵~喵喵呜呜呜喵~喵喵呜呜喵喵~喵喵呜喵呜喵~喵喵呜呜喵喵~喵喵喵呜呜呜~喵喵呜呜喵喵~喵喵呜呜喵喵~喵喵呜呜呜呜~喵呜呜呜呜呜喵~";

public class DaytimeTask implements Runnable {
    private String qun;
    private List list;
    public TimerTask timerTask;
    public DaytimeTask(String GroupUin,List list1) {
        this.qun = GroupUin;
        this.list = list1;
        timerTask = new TimerTask() {
            public void run() {
                //天亮时间结束 开始投票时间

                DataList.ReplacementTime(TimeType.voting,qun);
            }
        };        
    }
    public void run() {
        //天亮时间开始
        //啥也不做
    }
}

//任务类 放了投票开始和结束的执行程序
public class VoteTask implements Runnable {
    private String qun;
    private List list;
    public TimerTask timerTask;
    public VoteTask(String GroupUin,List list1) {
        this.qun = GroupUin;
        this.list = list1;
        timerTask = new TimerTask() {
            public void run() {
                //计算投票
                String vote = DataList.CountingVotes(qun,TimeType.daytime);
                sendMsg(qun,"",vote);
                if (DataList.isEnd(qun)[0]) 
                    DataList.ReplacementTime(TimeType.night,qun);
                else {
                    DataList.gameOver(qun,DataList.isEnd(qun)[1]);
                }
            }
        };        
    }
    public void run() {
        for (Object obj :  (List)ongoingGroup.get(qun).GameInformationList ) {
            //put可投票名单 条件 存活
            if (obj.getSurvivalState()) {
                list.add(obj.getUin());
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("通知\n-------我是分割线qwq--------\n可以开始投票了 允许投票的玩家如下\n");
        for(String str : list) {
            sb.append(at(str))
            .append("(")
            .append(getQQ(str))
            .append(")");
            sb.append("\n");
        }
        sb.append("发送 投@ 即可投票");
        sendMsg(qun,"",sb.toString());
        DataList.allowVoteNameList.put(qun,list);
    }    
}

//任务类 放了黑夜开始和结束的执行程序
public class NightTask implements Runnable {
    private String qun;
    private List list;
    public TimerTask timerTask;
    public NightTask(String GroupUin,List list1) {
        this.qun = GroupUin;
        this.list = list1;
        timerTask = new TimerTask() {
            public void run() { 
                ongoingGroup.get(qun).IsNight = false;
                for (String uin : list) {
                    sendMsg(qun,uin,"黑夜时间已结束");
                }
                forbidden(qun,"",0);
                String vote = DataList.CountingVotes(qun,TimeType.night);
                sendMsg(qun,"","天亮了 "+vote);
                if (DataList.isEnd(qun)[0]) {
                    DataList.ReplacementTime(TimeType.daytime,qun);
                } else {
                    DataList.gameOver(qun,DataList.isEnd(qun)[1]);
                }
            }
        };        
    }
    public void run() {
        sendMsg(qun,"","黑夜已降临");
        ongoingGroup.get(qun).IsNight = true;
        for (Object obj :  (List)ongoingGroup.get(qun).GameInformationList ) {
            if (obj.name.equals("侦探") && obj.survivalState) {
                ongoingGroup.get(qun).IsDetectiveTime[1]=true;
                sendMsg(qun,obj.uin,"你是侦探 已经到了黑夜 可以开始投票了 发送 查看+QQ号(如：看2376738)即可获取对方身份");
            }
            //是卧底且存活
            else if (obj.name.equals("卧底") && obj.survivalState) {
                forbidden(qun,"",1);
                list.add(obj.uin);
                sendMsg(qun,obj.uin,"你是卧底 已经到了黑夜 可以开始投票了 发送 投+QQ号(如：投2376738)即可投票");
            }
        }
        DataList.allowVoteNameList.put(qun,list);
    }    
}

public String getQQ(String qq) {
        StringBuilder qq1 = new StringBuilder(qq);
            if (qq1.length()>8&&是否隐藏QQ号信息)
                qq1.replace(3,6,"***");
            if (qq1.length()<=6&&是否隐藏QQ号信息)
                qq1.replace(2,4,"***");
       return qq1.toString();
}
public void onMsg(Object msg) {
    String qun=msg.GroupUin;
    String qq=msg.UserUin;
    String text=msg.MessageContent;
    
    if (text.startsWith("邀请@")&&qq.equals(myUin)) {
        //确保当前群聊不在游戏中
        if (ongoingGroup.containsKey(qun)) {
            sendMsg(qun,"","当前群聊游戏已经开启 为了遵守游戏规则请勿中途邀请 如果执意想让玩家加入对局请发送中止对局");
            return;
        }
        List Uinlist;
        if (preparation.containsKey(qun)) {
            Uinlist = (ArrayList)preparation.get(qun);
        } else {
            Uinlist = new ArrayList();
            preparation.put(qun,Uinlist);
        }
        //核查该成员是否已经开始游戏
        for (Map.Entry entry : ongoingGroup.entrySet()) {
            List gamePlayerData = entry.getValue().GameInformationList;
            for(Playerdata pd : gamePlayerData) {
                for(String atUin : msg.mAtList) {
                    if (pd.getUin().equals(atUin)) {
                        sendMsg(qun,"",at(atUin)+"\n正在其他游戏中 无法进行邀请");
                        continue;
                    }
               }
            }
        }
        for(String atUin : msg.mAtList) {
            if (preparation.get(qun).contains(atUin)) {
                sendMsg(qun,"",at(atUin)+"\n已在当前群聊等待游戏队列 无需重复邀请");
                continue;
            } else {
                Uinlist.add(atUin);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("等待队列如下 : \n");
        int i = 1;
        for (String uin : Uinlist) {
            sb.append(i).append(".").append(uin+"\n");
            i++;
        }
        sb.append("当前参与人数为 : "+Uinlist.size());
        sb.append("\n发送\"同意\" | \"拒绝\" 可同意和拒绝邀请");
        preparation.put(qun,Uinlist);
        sendMsg(qun,"",sb.toString());
    }
    
    if (preparation.containsKey(qun)&&(text.equals("同意")||text.equals("拒绝"))) {
        sendReply(qun,msg,"欢迎你加入游戏");
    }
    
    if ((text.equals("开始游戏")||text.equals("开始"))&&qq.equals(myUin)) {
        if (ongoingGroup.containsKey(qun)) {
            sendMsg(qun,"","当前群聊游戏正在进行");
            return;
        }
        if (!preparation.containsKey(qun)) {
            sendMsg(qun,"","没有邀请玩家玩你🐴 给老娘爬");return;
        }
        int yxrs = preparation.get(qun).size();
        //三个人以下不能开始游戏
        if (yxrs<3) { 
            sendMsg(qun,"","就"+yxrs+"个人玩你🐴 给老娘爬");return;
        }
        //卧底人数=游戏人数的三分之一
        int wdrs = yxrs / 3;
        ArrayList gameUserList = new ArrayList();
        Playerdata gdata;
        //分发身份
        for (int i=0;i<=yxrs-1;i++) {
            int ra = new Random().nextInt(preparation.get(qun).size());
            String uin =(String)preparation.get(qun).get(ra);
            preparation.get(qun).remove(ra);//上面定义好了就可以在准备游戏玩家数组中删除该账号了 避免账号重复
            if (i==0) {
                gdata = new Playerdata(uin,Civilians[1]);
            } else if (wdrs != 0) {
                gdata = new Playerdata(uin,Killer[0]);
                wdrs--;
            } else {
                gdata = new Playerdata(uin,Civilians[0]);
            }
            gameUserList.add(gdata);
        }
        GroupInfo groupInfo = new GroupInfo(qun,gameUserList);
        ongoingGroup.put(qun,groupInfo);
        preparation.remove(qun);//清除本群的所有准备数据
        游戏对局 yxdj = new 游戏对局(msg,(ArrayList)ongoingGroup.get(qun).GameInformationList);
        Thread th = new Thread(yxdj);
        th.start();
    }
    if (text.equals("信息")&&ongoingGroup.containsKey(qun)) {
        GroupInfo groupInfo = ongoingGroup.get(qun);
        List list = groupInfo.GameInformationList;
        StringBuilder sb = new StringBuilder();
        sb.append("本群参与玩家数据 : \n");
        for (Playerdata obj : list) {
            sb.append(at(obj.uin)+"("+getQQ(obj.uin)+")");
            {
                if (obj.survivalState)sb.append("未知");
                else sb.append(obj.name);
            }
            if (obj.survivalState) {
                sb.append(" ✅存活");
            }
            else if (!obj.survivalState) {
                sb.append(" ❌淘汰");
            }
            else {
                sb.append(" 不知道");
            }
            sb.append("\n");
        }
        if (!DataList.allowVoteNameList.containsKey(qun))
            sb.append("现在不在投票时间");
        if (DataList.allowVoteNameList.containsKey(qun)) {
            Map map = groupInfo.VotingData;//投票数据
        Map maps = new HashMap();//Map<String qq,int vote>
        for (Map.Entry entry : map.entrySet()) {
            String qq = entry.getValue();
            if (maps.containsKey(qq)) {
                int num = (int)maps.get(qq);
                maps.put(qq,++num);
            }
            else {
                maps.put(qq,1);
            }
        }
        List list2 = new ArrayList(maps.entrySet());
		Collections.sort(list2,new Comparator() {
			public int compare(Map.Entry o1, Map.Entry o2) {
				return o2.getValue()-o1.getValue();//倒序
			}
		});
		int i=1;
	    sb.append("被投票排行榜 : ");
		for (Map.Entry e: list2) {
		    sb.append("\n");
		    sb.append(i);
		    sb.append(".");
		    sb.append(at(e.getKey())).append("票数 : ").append(e.getValue());
		    i++;
		}
        }
        if(msg.IsGroup)
            sendMsg(qun,"",sb.toString());
    }
    //白天群内投票
    if (text.startsWith("投@")||text.startsWith("投票@")) {
        if (!msg.IsGroup)return;
        if (ongoingGroup.get(qun).IsNight)return;
        List array;
        if (DataList.allowVoteNameList.containsKey(qun) ) {
            if (msg.mAtList==null || msg.mAtList.size()==0) {
                sendMsg(qun,"","你艾特的人呢？");
                return;
            }
            array = DataList.allowVoteNameList.get(qun);
            if (array.contains(qq) ) {
                String tp = (String)msg.mAtList.get(0);//Object->String
                String vote = DataList.Vote(TimeType.voting,qun,qq,tp);//提交结果
                sendMsg(qun,"",vote);
            }
            else {
                sendReply(qun,msg,"你没有资格投票-不在游戏或是黑夜时间或已淘汰");
            }
        }
    }
    /*
    *黑夜私聊处理
    *记得考虑有好友的情况 有好友 群号参数是空的
    *所以通过QQ号查找对局
    */
    if (!msg.IsGroup) {
        //if (!ongoingGroup.get(qun).IsNight)return;//有好友该行报Null 懂的都懂
        if (text.matches("^[查看投票]{1,2} ?[0-9]+$")) {
            for (Map.Entry entry : ongoingGroup.entrySet()) {
                List gamePlayerData = entry.getValue().GameInformationList;
                for(Playerdata pd : gamePlayerData) {
                    if (pd.getUin().equals(qq)&&pd.getName().equals(Civilians[0])){
                        sendMsg(entry.getKey(),qq,"你是平民 没有任何行动");
                        return;
                    }
                    else if (pd.getUin().equals(qq)&&!entry.getValue().IsNight) {
                        sendMsg(entry.getKey(),qq,"当前不是黑夜时间 无法进行相关操作");
                        return;
                    }
                }
            }
        }
        if (text.matches("^[查看]{1,2} ?[0-9]+$")) {
            for (Map.Entry entry : ongoingGroup.entrySet()) {
                if (!entry.getValue().IsNight)return;
                Object[] en = entry.getValue().IsDetectiveTime;
                if( en[0].equals(qq)) {
                    String tpqq = text.replaceAll("[\\s\\S]*[^0-9]","");
                    String identity = entry.getValue().getIdentity(tpqq);
                    sendMsg(entry.getKey(),qq,identity);
                }
            }
        }
        if (text.matches("^(投|投票) ?[0-9]+$")) {            
            //遍历可投票的群
            er : for(Map.Entry entry : DataList.allowVoteNameList.entrySet()){
                if (!ongoingGroup.get(entry.getKey()).IsNight)break;
                //查找遍历黑夜列表的QQ号从Map<String qun,List UinArray>
                for(String uin : (List)entry.getValue()) {
                    if (uin.equals(qq)) {//遍历包含当前私聊自己的QQ号
                        String tpqq = text.replaceAll("[投票\\s]*","");
                        //提交投票申请得到结果
                        String jg = DataList.Vote(TimeType.night,entry.getKey(),uin,tpqq);
                        sendMsg(entry.getKey(),uin,jg);
                        break er;
                    }
                }
            }
        }
    }

    if (text.startsWith("帮助")&&qq.equals(myUin)) {
        String  het=BinaryStringConverteUtil.toString(help),
        he=BinaryStringConverteUtil.hexStr2Str(het);
        sendMsg(qun,"",he);
    }
}

String timef = "游戏时长\n" 
        + "白天时间 " + TimeType.daytimeTime + " 秒 ->\n"
        + "投票时间 " + TimeType.votingTime + " 秒 ->\n"
        + "黑夜时间 " + TimeType.nightTime + " 秒 ->\n"
        + "发送\"帮助\"可以查看相关的帮助";
//数据操作处理中心
public class DataList {
    //定时类线程池
    public static ExecutorService executorService = Executors.newScheduledThreadPool(线程池任务数量);
    //允许某些成员在某个群投票
    //未投票Map<String qun,List<String Uin>> 
    public static Map allowVoteNameList = new HashMap();    
    //提交线程池任务 对内存处理单元不会造成阻塞 任务创建放入线程池里在堆中不会被第一时间销毁
    public static void ReplacementTime(String type,String qun) {
    if (run==void || !ongoingGroup.containsKey(qun))return;//脚本停止后强制停止提交线程任务 不然会闪退
        ArrayList list = new ArrayList();
        TimerTask task;//暂时没用 懒得改
        long time;//暂时没用
        switch (type) {
            case TimeType.night ://如果黑夜
                //把群的黑夜可投票名单放到list
                NightTask night = new NightTask(qun,list);
                new Thread(night).start();
                //线程池延时任务 黑夜时间后执行
                executorService.schedule(night.timerTask,(long)TimeType.nightTime,TimeUnit.SECONDS);
                break;//停止穿透
            case TimeType.daytime :
                DaytimeTask daytime = new DaytimeTask(qun,list);
                new Thread(daytime).start();
                executorService.schedule(daytime.timerTask,(long)TimeType.daytimeTime,TimeUnit.SECONDS);
                break;
            case TimeType.voting :
                VoteTask voteTask = new VoteTask(qun,list);
                new Thread(voteTask).start();
                executorService.schedule(voteTask.timerTask,(long)TimeType.votingTime,TimeUnit.SECONDS);
                break;
        }
    }
    //判断是否可以结束游戏了 true为否 false是
    public static Object[] isEnd(String qun) {
        int KillerNum = 0;
        int CiviliansNum = 0;
        if (!ongoingGroup.containsKey(qun))return new Object[]{false,"NULL"};
        GroupInfo groupInfo = ongoingGroup.get(qun);
        List gamePlayerData = groupInfo.GameInformationList;
        for(Playerdata pd : gamePlayerData) {
            if (!pd.getSurvivalState())continue;//不存活直接略过
            if (pd.getName().equals(Killer[0])) {
                KillerNum++;
            }
            if ( pd.getName().equals(Civilians[0]) 
            || pd.getName().equals(Civilians[1]) ) {
                CiviliansNum++;
            }
        }
        Object[] obj = new Object[2];
        //杀手比平民多或者数量相等 杀手获胜
        if (CiviliansNum<=KillerNum) {
            obj[0] = false;
            obj[1] = "Killer";
        }
        //杀手全灭
        else if (KillerNum==0) {
            obj[0] = false;
            obj[1] = "Civilians";
        } else {
            obj[0] = true;
            obj[1] = "InProgress";
        }
        return obj;
    }
    //统计投票结果 禁言某玩家 返回投票排行榜
    public static String CountingVotes(String qun,String type) {
        GroupInfo groupInfo = ongoingGroup.get(qun);
        Map map = groupInfo.VotingData;//投票数据
        if (map.isEmpty()&&type.equals(TimeType.night)) {
            allowVoteNameList.remove(qun);
            return "黑夜时间没有卧底投票 本回合没有人出局";
        }
        if (map.isEmpty()) {
            allowVoteNameList.remove(qun);
            return "没有人投票 本回合没有人出局";
        }
        // Log.r("参与投票的map数据"+map.toString());
        Map maps = new HashMap();//Map<String qq,int vote>
        for (Map.Entry entry : map.entrySet()) {
            //获得每个Key的被投QQ
            String qq = entry.getValue();
            //叠加票数
            if (maps.containsKey(qq)) {
                int num = (int)maps.get(qq);
                maps.put(qq,++num);
            }
            else {
                maps.put(qq,1);
            }
        }
        map.clear();//清理原地址投票数据 maps是方法临时数据会被自动清理
        allowVoteNameList.remove(qun);//清理可参与投票的玩家
        /*
        Log.r("筛选结束 maps数据 "+maps.toString()
        +" 清理数据校准 原投票数据 "+ongoingGroup.get(qun).VotingData.toString()
        +" 可参与投票的玩家数据"+allowVoteNameList.containsKey(qun).toString()
        +" 后两项不正常说明正常");
        */
        List list = new ArrayList(maps.entrySet());
        //排序 根据Value倒序
		Collections.sort(list,new Comparator() {
			public int compare(Map.Entry o1, Map.Entry o2) {
				return o2.getValue()-o1.getValue();//倒序
			}
		});
		//此时是有序倒序Array 也可以转成Map
		int i=1;
		StringBuilder sb = new StringBuilder("被投排行榜 : ");
		for (Map.Entry e: list) {
		    sb.append("\n");
		    sb.append(i);
		    sb.append(".");
		    sb.append(at(e.getKey())).append("票数 : ").append(e.getValue());
		    i++;
		}
	    sb.append("\n");
	    int size;
	    if (list.size()>=2) {
	        if (list.get(0).getValue()==list.get(1).getValue()) {
	            sb.append("第一名第二名投票数量相同 本回合投票作废");
	            return sb.toString();
	        }
	    }
	    sb.append("根据投票结果淘汰的玩家是");
	    sb.append(at(list.get(0).getKey())+" 乖乖闭嘴吧大笨蛋～");
	    if (type.equals(TimeType.night)) {
            sb.delete(0,sb.length());
            sb.append("黑夜被卧底淘汰的玩家是"+at(list.get(0).getKey()));
        }
	    //更新玩家存活状态为false
	    List gamePlayerData = groupInfo.GameInformationList;
        for(Playerdata pd : gamePlayerData) {
            if (pd.getUin().equals(list.get(0).getKey())) {
                pd.survivalState = false;
                break;
            }   
        }
	    return sb.toString();
    }
    
    //执行成员对成员的投票 返回投票结果 当前时间 群号 qq 被投qq
    public static String Vote(String type,String qun,String qq,String Uin) {
        String consequence;
        GroupInfo groupInfo = ongoingGroup.get(qun);
        Map map = groupInfo.VotingData;
        List list = groupInfo.GameInformationList;
        boolean exist = false;
        //核对身份
        for (Playerdata obj : list) {
            if (!obj.uin.equals(Uin))continue;
            if (type.equals(TimeType.night)) {
                if (obj.name.equals(Killer[0])) {
                    return "对方也是卧底 无法投票";
                    break;
                }
            }
            if (!obj.survivalState) {
                return "对方已不是存活状态 无法投票";
                break;
            } 
            if (obj.uin.equals(Uin)) {
                exist = true;
            }
        }
        if (!exist) {
            return "该玩家不在本对局中 请核查投票对象是否有误";
        }
        if (map.containsKey(qq)) {
            String V_QQ = map.get(qq);
            map.put(qq,Uin);
            if (type.equals(TimeType.night))
                consequence = "你已经对" + V_QQ + "进行投票过了\n已更新本次投票目标为" + Uin;
            else {
                consequence = "你已经对" + at(V_QQ) + "进行投票过了\n已更新本次投票目标为" + at(Uin);
            }
        } else {
            //没投票过 存数据
            map.put(qq,Uin);
            if (type.equals(TimeType.night)) {
                consequence = "成功对" + Uin + "进行投票";
            } else {
                consequence = "成功对" + at(Uin) + "进行投票";
            }
        }
        //更新数据
        groupInfo.updateVotingData(map);
        ongoingGroup.put(qun,groupInfo);//这行可没可无 毫无存在的意义
        // Log.r(type+ "时间 投票人"+qq+" 被投票人"+Uin+" map :"+map.toString());
        return consequence;
    }
    //结束游戏 统计,发送,删除,停止线程
    public static void gameOver(String qun,String t) {
        StringBuilder sb = new StringBuilder();
        sb.append("游戏结束 胜者 : ");
        if (t.equals("Civilians")) {
            sb.append("平民");
            sb.append("\n胜利原因 : 卧底全灭");
        } else if (t.equals("Killer")) {
            sb.append("卧底");
            sb.append("\n胜利原因 : 卧底人数大于等于平民");
        } else { 
            sb.append("不知道"); 
        }
        sb.append("\n--------------------------------------\n")
        .append("玩家数据 : \n");
        GroupInfo groupInfo = ongoingGroup.get(qun);
        List list = groupInfo.GameInformationList;
        for (Playerdata obj : list) {
            sb.append(at(obj.uin)+"("+getQQ(obj.uin)+")"+obj.name);
            if (obj.survivalState) {
                sb.append(" ✅存活");
            }
            else if (!obj.survivalState) {
                sb.append(" ❌淘汰");
            }
            else {
                sb.append(" 不知道");
            }
            sb.append("\n");
        }
        sb.delete(sb.length()-1,sb.length());
        sendMsg(qun,"",sb.toString());
        ongoingGroup.remove(qun);
        //  Log.r("对局结束");
    }

}


//群聊数据 群号 是否黑夜 一堆玩家数据
class GroupInfo {
    String GroupUin;
    Object[] IsDetectiveTime;//侦探QQ,本回合是否允许查看别人身份
    boolean IsNight;
    Map VotingData = new HashMap();//{"投票人QQ","投票QQ"}
    List GameInformationList = new ArrayList();//玩家列表<Playerdata>
    public GroupInfo(String g,List list) {
        this.GroupUin = g;
        this.GameInformationList = list;
        this.IsNight = false;
        for (Playerdata pd : this.GameInformationList) {
            if (pd.getName().equals(Civilians[1])) {
                IsDetectiveTime = new Object[2];
                IsDetectiveTime[0]=pd.getUin();
                IsDetectiveTime[1]=false;
            }
        }
    }
    public String getIdentity(String uin) {
    if (! IsDetectiveTime[1])return "你今夜的查看TA人身份次数已用光";
        for (Object pd : GameInformationList) {
            if (pd.getUin().equals(uin)) {
                this.IsDetectiveTime[1] = false;
                return "该玩家的身份是 "+pd.getName();
                break;
            }
        }
        return "没有找到该玩家"+uin;
    }
    public Object[] getDetectiveTime() {
        return IsDetectiveTime;
    }
    public void updateVotingData(Map map) { 
        this.VotingData = map;
    }
    public void updatePlayerdata(List list) {
        this.GameInformationList = list;
    }
}
//玩家数据 qq号 身份 存活状态
class Playerdata {
    String uin;
    String name;
    //还可以存一些投票记录什么的
    boolean survivalState=true;
    Playerdata(String u,String n) {
        this.uin = u;
        this.name = n;
    }
    public String getUin(){
        return uin;
    }
    public String getName(){
        return name;
    }
    public boolean getSurvivalState() {
        return survivalState;
    }
    //重写类的toString方法
    public String toString() {
        return "{"
              +"uin="+uin
              +"name="+name
              +"survivalState="+survivalState.toString()
              +"}";
    }
}


//该线程不受玩家干预 由时长决定开启结束的方法
public class 游戏对局 implements Runnable {
    private int times = TimeType.maxTime;//游戏进行中的时间 这个别动
    private int countdown = 0;
    private Object msg;
    String group;
    private List uinList;
    private Timer timer;
    private TimerTask task;
    //有参构造类-传入的参数(消息本身,参与本局游戏所有人QQ号)
    public 游戏对局(Object data,List uinlist) {
        this.msg = data;
        this.group = data.GroupUin;
        this.uinList = uinlist;
        for (Object obj : (ArrayList)ongoingGroup.get(this.group).GameInformationList ) {
            sendMsg(this.group,obj.uin,"你的身份是 : "+obj.name);
        }
        
        sendMsg(this.group,"","身份分发完毕\n已通过私聊通知各位的身份注意查收\n请准备开始游戏\n"+timef);
        run = true;
    }
    //线程里要做什么
    public void run() {
        /*
        *==void的话就是脚本已经停止运行了
        *因为脚本也属于线程 不是进程 主线程和子线程互不影响
        *(主进程被结束后所有子线程也都会强制中止 但主线程不会)
        *脚本关闭相当于主线程被关闭后子线程依然在进行运行循环
        *boolean run==void则直接false && 第一个表达式否定后这个表达式就不会继续判断 
        *就不会引起错误 (使用非法未定义的变量) Crash unexpectedly: illegal use of undefined variable, class, or 'void' literal
        *听懂扣1
        */
        while ( times>0&& (run!=void&&run) ) {
            times--;
            long hh = times / 60 / 60 % 60;
            long mm = times / 60 % 60;
            long ss = times % 60;
            //System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
           // if (times%30==0) sendMsg(this.group,"","计时线程正常存活 剩余 "+mm+" 分钟 "+ss+" 秒");
            countdown++;
            //投票时间
            if (countdown==TimeType.daytimeTime) {
                DataList.ReplacementTime(TimeType.voting,this.group);
            }
            if (!ongoingGroup.containsKey(group)) {
                toast("游戏结束");
                break;
            }
            if (times==TimeType.maxTime) {
                sendMsg(this.group,"","时间到");
                ongoingGroup.remove(this.group);
                break;
            }
            if (times==TimeType.maxTime/2)
                sendMsg(this.group,"","游戏时间已过半\n剩余时间" + mm + "分" + ss + "秒");

            try {
                Thread.sleep(1000);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}

/**
 * 二进制字符串转换工具类
 */
public class BinaryStringConverteUtil {
    private static final String BIN_SEPARATOR = " ";
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
    public static String toBinaryString(String str) {
        if (str == null) return null;
        StringBuffer sb = new StringBuffer();
        byte[] bytes = str.getBytes();
        for (byte aByte : bytes) {
            sb.append(Integer.toBinaryString(aByte) + BIN_SEPARATOR);
        }
        return sb.toString()
        .replace(hexStr2Str("31"),hexStr2Str("E596B5"))
        .replace(hexStr2Str("30"),hexStr2Str("E5919C"))
        .replace(hexStr2Str("20"),hexStr2Str("7E"));
    }

    public static String toString(String binaryStr) {
        if (binaryStr == null) return null;
        binaryStr = binaryStr
        .replace(hexStr2Str("E596B5"),hexStr2Str("31"))
        .replace(hexStr2Str("E5919C"),hexStr2Str("30"))
        .replace(hexStr2Str("7E"),hexStr2Str("20"));
        String[] binArrays = binaryStr.split(BIN_SEPARATOR);
        StringBuffer sb = new StringBuffer();
        for (String binStr : binArrays) {
            char c = binstrToChar(binStr);
            sb.append(c);
        }
        return sb.toString();
    }
    private static int[] binstrToIntArray(String binStr) {
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }
    private static char binstrToChar(String binStr){
        int[] temp=binstrToIntArray(binStr);
        int sum=0;
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }
        return (char)sum;
    }
}

toast("发送 \"邀请@\" 和 \"开始\" 可进行游戏 \n发送 \"帮助\" 可以查看帮助或联系开发者");

