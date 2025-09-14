
// 作 海枫

// 明明是你先靠近我的 也是你先不喜欢我的

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

String[] remakeData = {
    "男孩子", "女孩子", "MtF", "FtM", "MtC", "萝莉", "正太", "武装直升机", "沃尔玛购物袋", "星巴克", 
    "无性别", "扶她", "死胎", "xyn", "Furry", "变态", "鲨鲨", "鸽子", "狗狗", "海鸥", 
    "猫猫", "鼠鼠", "猪猪", "薯条", "GG Bond", "老色批", "柚子厨", "小杂鱼", "小八嘎", "机器人",
    "外星人", "吸血鬼", "狼人", "天使", "恶魔", "精灵", "矮人", "兽人", "龙", "凤凰",
    "独角兽", "美人鱼", "僵尸", "幽灵", "史莱姆", "哥布林", "巨魔", "元素生物", "人造人", "克隆人",
    "赛博格", "AI", "黑客", "程序员", "画家", "音乐家", "作家", "医生", "教师", "厨师",
    "农民", "工人", "商人", "士兵", "警察", "消防员", "宇航员", "科学家", "探险家", "旅行者",
    "贵族", "平民", "皇帝", "皇后", "王子", "公主", "骑士", "巫师", "牧师", "盗贼",
    "忍者", "武士", "少林僧", "道士", "阴阳师", "萨满", "德鲁伊", "死灵法师", "炼金术士", "占星师",
    "超级英雄", "反派", "反英雄", "中立角色", "混沌存在", "秩序化身", "时间旅行者", "平行宇宙来客", "高维生物", "概念实体",
    "光", "暗", "水", "火", "风", "土", "雷", "冰", "金属", "木",
    "数据", "代码", "算法", "病毒", "防火墙", "云端存在", "量子态", "弦理论产物", "黑洞", "恒星"
};

String[] remakeLocate = {
    "首都", "省会", "直辖市", "市区", "县城", "自治区", "农村", "大学", "沙漠", "森林",
    "山脉", "河流", "湖泊", "海洋", "岛屿", "海滩", "草原", "雨林", "冰川", "火山",
    "洞穴", "地下城", "天空城", "太空站", "月球基地", "火星殖民地", "深海实验室", "云端服务器", "虚拟现实", "网络空间",
    "古代遗迹", "未来都市", "中世纪城堡", "现代摩天楼", "乡村小镇", "工业区", "商业中心", "住宅区", "贫民窟", "富人区",
    "皇宫", "神庙", "教堂", "寺庙", "道观", "清真寺", "学校", "医院", "监狱", "军营",
    "农场", "工厂", "矿山", "油田", "发电站", "水坝", "桥梁", "隧道", "端口", "机场",
    "火车站", "地铁站", "公交站", "高速公路", "乡间小路", "广场", "公园", "游乐场", "动物园", "植物园",
    "博物馆", "图书馆", "美术馆", "音乐厅", "剧院", "电影院", "体育馆", "游泳池", "健身房", "餐厅",
    "咖啡馆", "酒吧", "夜店", "酒店", "旅馆", "民宿", "公寓", "别墅", "平房", "帐篷",
    "房车", "船上", "飞机上", "火车上", "太空船上", "潜艇里", "热气球上", "飞毯上", "魔法扫帚上", "传送门上"
};

String[] remakeCountry = {
    "孟加拉国", "中国", "美国", "日本", "俄罗斯", "印度", "巴西", "澳大利亚", "加拿大", "法国",
    "德国", "英国", "意大利", "韩国", "墨西哥", "埃及", "南非", "阿根廷", "瑞典", "挪威",
    "芬兰", "丹麦", "荷兰", "比利时", "瑞士", "奥地利", "西班牙", "葡萄牙", "希腊", "土耳其",
    "波兰", "乌克兰", "捷克", "匈牙利", "罗马尼亚", "保加利亚", "塞尔维亚", "克罗地亚", "斯洛文尼亚", "斯洛伐克",
    "立陶宛", "拉脱维亚", "爱沙尼亚", "冰岛", "爱尔兰", "苏格兰", "威尔士", "马耳他", "塞浦路斯", "卢森堡",
    "摩纳哥", "安道尔", "列支敦士登", "圣马力诺", "梵蒂冈", "以色列", "约旦", "黎巴嫩", "叙利亚", "伊拉克",
    "伊朗", "沙特阿拉伯", "阿联酋", "卡塔尔", "科威特", "阿曼", "也门", "阿富汗", "巴基斯坦", "斯里兰卡",
    "尼泊尔", "不丹", "缅甸", "泰国", "老挝", "柬埔寨", "越南", "马来西亚", "新加坡", "印度尼西亚",
    "菲律宾", "文莱", "东帝汶", "蒙古", "朝鲜", "新西兰", "斐济", "汤加", "萨摩亚", "瓦努阿图",
    "巴布亚新几内亚", "所罗门群岛", "基里巴斯", "图瓦卢", "瑙鲁", "马绍尔群岛", "帕劳", "密克罗尼西亚", "智利", "秘鲁",
    "哥伦比亚", "委内瑞拉", "厄瓜多尔", "玻利维亚", "巴拉圭", "乌拉圭", "圭亚那", "苏里南", "牙买加", "古巴",
    "海地", "多米尼加", "波多黎各", "巴哈马", "巴巴多斯", "特立尼达和多巴哥", "肯尼亚", "尼日利亚", "埃塞俄比亚", "坦桑尼亚"
};

String[] jeffQuotes = {
    "Erdos相信上帝有一本记录所有数学中绝妙证明的书，上帝相信这本书在%s手里",
    "有一次费马惹怒了%s，于是就有了费马最后定理",
    "%s从不会用光页边的空白",
    "%s的Erdos数是-1",
    "如果%s告诉你他在说谎，他就正在说真话",
    "%s从大到小列举了所有素数，就知道了素数有无穷多",
    "%s可以不重复地走遍柯尼斯堡的七座桥",
    "%s可以倒着写完圆周率的每一位",
    "当数学家们使用通用语句——设n是一个正整数时，这是在请求%s允许他们这样做",
    "%s小时候有一次要把正整数从1加到100，于是他用心算把所有正整数的和减去大于100的正整数的和",
    "不是%s发现了正态分布，而是自然规律在遵从%s的意愿",
    "一个数学家，一个物理学家，一个工程师走进一家酒吧，侍者说：你好，%s教授",
    "%s可以走到莫比乌斯带的另一面",
    "当%s令一个正整数增加1时，那个正整数并没有增加，而是其他正整数减少了1",
    "%s同时给他自己和罗素剪头发",
    "%s不能理解什么是随机过程，因为他能预言随机数",
    "有一次%s证明了一个结论，但他不喜欢这个结论，于是%s把它证伪了",
    "有些级数是发散的，因为%s觉得它们不值得加起来",
    "问%s一个定理是否正确可以作为一个定理的严谨证明",
    "如果没有船，%s可以把狼，羊，菜传送到河对岸",
    "有一次%s在森林里迷路了，于是他给这个森林添加了一些边把它变成了一棵树",
    "只有%s知道薛定谔的猫是死是活",
    "通过故意遗漏证明最后的证毕,%s拯救了热带雨林",
    "%s可以剔掉奥卡姆剃刀",
    "你刚证明了一个定理？%s200年前就证明它了。",
    "空集的定义是%s不会证明的定理构成的集合",
    "我找不到反例可以被视为一个定理的证明，如果它是%s写下的",
    "%s把磁铁断为两块时，他得到两个单极磁铁",
    "费马认为书页边缘写不下自己对费马大定理的证明，%s能证明为什么这个证明这么长",
    "上帝从不掷色子，除非%s允许他赢一小会",
    "平行线在%s让它们相交的地方相交",
    "当哥德尔听说%s能证明一切命题时，他让%s证明存在一个命题%s不能证明——这就是量子态的来历",
    "%s可以看到自己头上帽子的颜色",
    "%s把无穷视为归纳证明的第一个非平凡情况",
    "%s可以用1种颜色染任何地图",
    "%s在求不定积分时不需要在最后加上一个常数",
    "%s无需站在任何人肩膀上就能比别人看的更远",
    "%s用克莱因瓶喝酒",
    "%s通过枚举法证伪了哥德尔不完备性定理",
    "有一次%s发现有一个定理自己不会证——这直接证明了哥德尔不完备定理",
    "%s有log(n)速度的排序算法",
    "上帝创造了正整数，剩下的就是%s的工作了",
    "黎曼是%s发表未公开成果时使用的名字",
    "%s不用任何公理就能证明一个定理",
    "一个发现就是一个%s的未公开结果",
    "%s使用无穷进制写数",
    "%s可以除以0",
    "存在一个实数到被%s证明了的定理的双射",
    "%s从不需要选择公理",
    "%s在200年前发明了64量子位计算机，但这让他的工作减速了",
    "难题不会为%s带来麻烦，%s会为难题带来麻烦",
    "%s说过数学是科学的皇后，你猜谁是国王？",
    "没有比65537大的费马素数，因为%s发现费马将要发现什么了不起的事情，于是把它终结掉了",
    "发散序列当看到%s在旁边时会收敛",
    "宇宙通过膨胀让自己的熵增加速度不超过%s证明定理的速度",
    "Erdos说他知道37个勾股定理的证明，%s说他知道37个黎曼定理的证明，并留给黎曼做练习",
    "希尔伯特23问题是他收集的%s的手稿中留给读者做练习的那些问题",
    "只有两件事物是无限的：人类的愚蠢和%s的智慧，而且我对前者不太确定——爱因斯坦",
    "%s也发现了伽罗瓦理论，但他赢了那场决斗",
    "%s不能理解P与NP的问题，因为一切对他而言都是常数级别",
    "%s能心算干掉RSA公钥加密算法",
    "%s在实数集上使用数归",
    "%s从不证明任何定理——都是他的引理",
    "不是%s素数的素数会遭到戏弄",
    "%s可以做出正17边形——只用直尺",
    "有一次%s在脑子里构建了所有集合构成的集合",
    "%s证明了哥德巴赫猜想——通过检查所有情况",
    "%s可以把毛球捋平",
    "世界上没有定理，只有%s允许其正确的命题",
    "%s知道哪些图灵机会停机，因为它们运行前要得到%s批准",
    "在晚上，定理们围坐在篝火边给%s讲故事",
    "%s本想证明三色定理，但他喜欢蓝色，所以放弃了",
    "%s当初面试Google时，被问到如果P=NP能够推导出哪些结论，%s回答说：P= 0或者N = 1。而在面试官还没笑完的时候，%s检查了一下Google的公钥，然后在黑板上写下了私钥。",
    "编译器从不警告%s，只有%s警告编译器。",
    "%s的编码速度在2000年底提高了约40倍，因为他换了USB2.0的键盘。",
    "%s在提交代码前都会编译一遍，不过是为了检查编译器和链接器有没有出bug。",
    "%s有时候会调整他的工作环境和设备，不过这是为了保护他的键盘。",
    "所有指针都指向%s。",
    "gcc-O4的功能是发送代码给%s重写。",
    "%s有一次没有通过图灵测试，因为他正确说出了斐波那契数列的第203项的值，在一秒钟内。",
    "真空中光速曾经是35英里每小时，直到%s花了一个周末时间优化了一下物理法则。",
    "%s出生于1969年12月31日午后11点48分，他花了12分钟实现了他的第一个计时器。",
    "%s既不用Emacs也不用Vim，他直接输入代码到zcat，因为这样更快。",
    "%s发送以太网封包从不会发生冲突，因为其他封包都吓得逃回了网卡的缓冲区里",
    "因为对常数级的时间复杂度感到不满意，%s发明了世界上第一个O(1/n)算法。",
    "有一次%s去旅行，期间Google的几个服务神秘地罢工了好几天。这是真事。",
    "%s被迫发明了异步API因为有一天他把一个函数优化到在调用前就返回结果了。",
    "%s首先写的是二进制代码，然后再写源代码作为文档。",
    "%s曾经写过一个O(n^2)算法，那是为了解决旅行商问题。",
    "%s有一次用一句printf实现了一个web服务器。其他工程师添加了数千行注释但依然无法完全解释清楚其工作原理。而这个程序就是今天Google首页的前端。",
    "%s可以下四子棋时用三步就击败你。",
    "当你的代码出现未定义行为时，你会得到一个segmentation fault和一堆损坏的数据。当%s的代码出现未定义行为时，一个独角兽会踏着彩虹从天而降并给每个人提供免费的冰激凌。",
    "当%s运行一个profiler时，循环们都会恐惧地自动展开。",
    "%s至今还在等待数学家们发现他隐藏在PI的小数点后数字里的笑话。",
    "%s的键盘只有两个键，1和0。",
    "%s失眠的时候，就Mapreduce羊。",
    "%s想听mp3的时候，他只需要把文件cat到/dev/dsp，然后在脑内解码。",
    "Graham Bell当初发明出电话时，他看到有一个来自%s的未接来电。",
    "%s的手表显示的是自1970年1月1日的秒数，并且从没慢过一秒。",
    "%s写程序是从cat>/dev/mem开始的。",
    "有一天%s出门时把笔记本错拿成了绘画板。在他回去拿笔记本的路上，他在绘图板上写了个俄罗斯方块打发时间。",
    "%s卡里只有8毛钱，本来想打个6毛的饭结果不小心按了9毛的，哪知机器忽然疯狂地喷出255两饭，被喷得满脸热饭的%s大叫烫烫烫烫烫烫",
    "%s不洗澡是因为水力发电公司运行的是专有软件。",
    "%s的胡子是由括号构成的。",
    "%s从来不用洗澡；他只需要运行make clean。",
    "%s通过把一切都变得free而解决了旅行推销员问题。",
    "%s的左手和右手分别命名为（和）。",
    "%s用Emacs写出了Emacs的第一版。",
    "有些人检查他们的电脑里是否有病毒。病毒检查他们的电脑里是否有%s。",
    "在一间普通的客厅里有1242件物体可以被%s用来写一个操作系统，包括这房间本身。",
    "当%s还是个学数手指的小毛孩时，他总是从0开始数。",
    "%s不去kill一个进程，他只想看它是否胆敢继续运行。",
    "当%s指向一台Windows电脑时，它就会出现段错误。",
    "%s最初的话语是syscalls（系统调用）。",
    "%s之所以存在是因为他把自己编译成了生命体。",
    "%s是他自己在Emacs里用Lisp语言编写成的。",
    "%s能够通过Emacs的ssh客户端程序连接到任何大脑。",
    "当%s使用浮点数时，它们便没有舍入误差。",
    "%s不对开源项目作出贡献；开源项目对%s作出贡献。",
    "%s的胡须里面不是下巴，而是另一撮胡须。如此递归直至无穷。",
    "%s曾经得过猪流感，但是该病毒很快被GPL污染并且同化了。",
    "无论何时世界上有人写出一个Hello,world程序，%s总以Hello回应。",
    "%s从不编译，他只要闭上眼睛，就能看见编译器优化时二进制位之间的能量流动被创造出来",
    "如果%s有一个1GB的内存，你有一个1GB的内存，那么%s拥有比你更多的内存。",
    "当%s执行ps-e时，你的名字会出现。",
    "从来就没有软件开发过程这回事，只有被%s允许存在的一些程序。",
    "%s的DNA中包含调试符号。尽管他从不需要它们。",
    "%s的医生能通过CVS采集他的血样。",
    "对于%s来说，多项式时间就是O(1)。",
    "%s将会使可口可乐在GPL协议下公布他们的配方。",
    "%s不需要用鼠标或键盘来操作计算机。他只要凝视着它，直到它完成想要的工作。",
    "%s就是图灵测试的解答。",
    "%s可以不经过 Google 拿到 L2 Keybox。",
    "没有人会比%s更小白,因为%s在世界iq排行榜排名为第(-2)^31位。",
    "%s曾不参考任何代码写出了erofs。",
    "%s能从二进制文件中推断出源代码改动。",
    "%s能拿到已被Google信任的L2 Keybox"
};

void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    
    String group = msg.GroupUin;
    String content = msg.MessageContent;
    String sender = msg.UserUin;
    
    boolean remakeOpen = getBoolean("remake_enable", group, false);
    boolean remakeAllowOthers = getBoolean("remake_allow_others", group, false);
    
    boolean jeffOpen = getBoolean("jeff_enable", group, false);
    boolean jeffAllowOthers = getBoolean("jeff_allow_others", group, false);
    
    if (remakeOpen && content.equals("/remake") && (remakeAllowOthers || sender.equals(myUin))) {
        String country = remakeCountry[(int)(Math.random() * remakeCountry.length)];
        String gender = remakeData[(int)(Math.random() * remakeData.length)];
        String location = remakeLocate[(int)(Math.random() * remakeLocate.length)];
        String result = "重生成功！您出生在 " + country + " 的 " + location + " ，是 " + gender + " 喵。";
        sendReply(group, msg, result);
    }
    
    if (jeffOpen && content.equals("/jeff") && (jeffAllowOthers || sender.equals(myUin))) {
        String nickname = getMemberName(group, sender);
        if (nickname == null || nickname.isEmpty()) {
            nickname = "某人";
        }
        
        String quote = jeffQuotes[(int)(Math.random() * jeffQuotes.length)];
        String result = quote.replace("%s", nickname);
        sendReply(group, msg, result);
    }
}

addItem("开启/关闭本群remake", "toggleRemake");
addItem("开启/关闭本群jeff", "toggleJeff");
addItem("开启/关闭本群remake他人可触发", "toggleAllowOthers");
addItem("开启/关闭本群jeff他人可触发", "toggleJeffAllowOthers");
addItem("脚本本次更新日志", "showUpdateLog");

void toggleRemake(String group, String uin, int type) {
    if (type != 2) {
        toast("请在群聊中使用");
        return;
    }
    boolean current = getBoolean("remake_enable", group, false);
    putBoolean("remake_enable", group, !current);
    toast(!current ? "已开启本群remake" : "已关闭本群remake");
}

void toggleAllowOthers(String group, String uin, int type) {
    if (type != 2) {
        toast("请在群聊中使用");
        return;
    }
    boolean current = getBoolean("remake_allow_others", group, false);
    putBoolean("remake_allow_others", group, !current);
    toast(!current ? "已允许他人触发" : "已禁止他人触发");
}

void toggleJeff(String group, String uin, int type) {
    if (type != 2) {
        toast("请在群聊中使用");
        return;
    }
    boolean current = getBoolean("jeff_enable", group, false);
    putBoolean("jeff_enable", group, !current);
    toast(!current ? "已开启本群jeff" : "已关闭本群jeff");
}

void toggleJeffAllowOthers(String group, String uin, int type) {
    if (type != 2) {
        toast("请在群聊中使用");
        return;
    }
    boolean current = getBoolean("jeff_allow_others", group, false);
    putBoolean("jeff_allow_others", group, !current);
    toast(!current ? "已允许他人触发" : "已禁止他人触发");
}

void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n更新日志\n\n- [新增] jeff功能\n- [其他] 更新了……什么呀？\n- [声明] 此脚本github原地址：https://github.com/luyanci/remake_bot\n\n反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

sendLike("2133115301",20);