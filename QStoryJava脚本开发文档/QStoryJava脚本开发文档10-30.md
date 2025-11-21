# QStory  Java脚本开发文档

### 有bug 维护方面的问题 请前往有我的地方向我反馈

最近一次文档更新日期 2025-11-15

适配新版本QStory指南

[QStory脚本变更](https://www.notion.so/QStory-2414a5bbff2380dc8604e2add0bf2e95?pvs=21)

---

## **注意**

**1.在脚本环境中,注解是不可用的,写了会直接加载失败。**

**2.脚本环境可以使用Java标准类库，以及Android标准类库，例如org.json.JSONObject,TextView等(需import)**

**3.脚本中的实体对象，例如GroupInfo和GroupMemberInfo等直接使用Object指代即可，脚本可以直接访问字段**

**4.脚本的Java运行环境为JDK9,不支持较新的API**

## 运行脚本时需要的文件

### main.java // 文件在点击加载时加载
desc.txt // 脚本描述文件，用于在列表中显示
info.prop // 脚本信息，采用 key = value 格式，需要的 key 如下：

<aside>
💡

name = 脚本名称
type = 1 (直接写入即可)
version = 1.0 (版本号)
author = 作者名
id = 脚本ID（确保唯一性)

time = 2025-8-1(格式2025-8-1) 以确保脚本的更新时间没有很落后,太旧的脚本无法加载

</aside>

---

## 全局变量 直接引用即可

`String myUin; 当前用户的QQ号`

`Context context; QQ全局上下文对象(android.content.Context)`

`String appPath;脚本运行时的相对目录`

`ClassLoader loader; QQ的类加载器`

`String pluginID； 当前脚本ID`

例如 想提示当前QQ 则直接在文件根写即可

```java
toast("当前QQ"+myUin);
```

---

## 回调方法

直接在文件根定义即可 无需定义在类里面 当相关内容触发时会调用定义在文件根的回调方法

例如

```java
//监听收到消息
void onMsg(Object msg) {
    toast("消息内容:"+msg.MessageContent);
}
```

### 回调方法需要自行重写 频道的任何相关内容都没有维护

### `void onMsg(MessageData msg)` //收到消息时调用，msg 成员如下：

`String MessageContent;` // 消息内容（文本、图片下载地址、语音MD5、卡片代码）
`String GroupUin;` // 群号（仅在群消息、私聊消息和频道消息时有效）
`String PeerUin` //在私聊可以使用此参数 总是为对方的QQ号
`String UserUin;` // 发送者QQ号
`int MessageType;` // 消息类型（1: 文字/图片；2: 卡片；3: 图文；4: 语音；5: 文件；6: 回复）)
`boolean IsGroup;` // 是否群组消息（仅在群聊消息和频道消息时为 true）
`boolean IsChannel;` // 是否频道消息（仅在频道消息时为 true）
`String SenderNickName;` // 发送者昵称
`long MessageTime;` // 消息时间戳（单位：毫秒）
`ArrayList<String> mAtList;` // 艾特列表
`boolean IsSend;` // 是否为自己发送的消息
`String FileName;` // 文件名（仅在群文件消息时有效）
`long FileSize;` // 文件大小（仅在群文件消息时有效）
`String LocalPath;` // 本地文件路径（仅在语音文件消息时有效）
`String ReplyTo;` // 回复的用户账号（仅在回复消息时有效）
`MessageData RecordMsg;` //回复的消息
`String GuildID;` // 频道ID（仅在频道消息时有效）
`String ChannelID;` // 子频道ID（仅在频道消息时有效）

`String[] PicList;` //消息中存在的图片MD5列表

`ArrayList<String> PicUrlList;` //消息中存在的图片链接列表

`Object msg;` //未经过解析的原消息

### `void onForbiddenEvent(String GroupUin, String UserUin, String OPUin, long time)` // 成员被禁言时调用

参数1为群 参数2为被禁言的用户QQ 参数3执行禁言的管理员 参数4为禁言时间 单位秒

### `void onTroopEvent(String GroupUin, String UserUin, int type)` //发生进群和退群时调用

参数1为群 参数2为用户QQ 参数3在进群时为2 退群时为1

### `void onClickFloatingWindow(int type,String uin)`  //在脚本悬浮窗打开时会调用 参数一为聊天类型 私聊为1 群聊为2 参数二在群聊为群号 私聊为QQ号

通常配合`void addTemporaryItem(String ItemName, String CallbackName)` 使用

### `String getMsg(String msg, String GroupUin or FriendUin, int)` // 点击发送按钮发送消息时调用，支持纯文本消息，传递发送消息的内容，返回文本会修改为文本内容。参数1为将要发送的消息，参数2为好友号码或群号，参数3为类型（2: 群组；1和100代表私聊）

### `void onCreateMenu(MessageData msg)`// 长按消息创建菜单时调用，msg对象等同于`onMsg(MessageData msg)`中的`msg`，用于使用`addMenuItem`创建一次性消息长按菜单

### `void callbackOnRawMsg(Object msg)` //收到未解析解析的消息时调用 包括灰字 文本等，需要自己解析，对应QQ的com.tencent.qqnt.kernel.nativeinterface.MsgRecord类

---

# API方法

---

## 发送消息方法 全局方法 在任何地方直接调用即可

### 参数1为群号 参数2为QQ号 参数3为内容 QQ号为空时发送群消息 群号为空时发送私聊消息

### `sendMsg(String GroupUin, String UserUin, String msg)` // 发送文本、图片或图文消息，图文消息写[PicUrl=图片本地或网络地址]，艾特写[AtQQ=QQ号]

`sendPic(String GroupUin, String UserUin, String Path)` // 发送单张图片，参数3为图片本地或网络地址

`sendCard(String GroupUin, String UserUin, String card)` // 发送 JSON ~~或 XML 卡片代码~~，参数3为卡片代码

`sendReply(String GroupUin, Object msg, String msg)` // 发送回复消息，仅支持群聊，参数1为群号，参数2为回复的消息对象，参数3为显示的回复文本

### `sendFile(String GroupUin, String UserUin, String Path)`  //发文件 参数三为路径

### `sendVoice(String GroupUin, String UserUin, String Path)` //发语音 参数三为语音路径

### `sendVideo(String group, String userUin, String path)` //发视频

### `sendLike(String UserUin, int count)` //点赞 参数一为QQ，次数二为点赞数

### `sendPai(String group, String uin)` //拍一拍对方 参数一为群号 参数二为对方QQ  私聊戳一戳参数一留空

---

## 群聊操作方法

### `setCard(String GroupUin, String UserUin, String Name)` // 设置群名字，仅管理员可用 (尚未维护）

`setTitle(String GroupUin, String UserUin, String title)` // 设置头衔，仅群主可用

`revokeMsg(Object msg)` // 撤回一条消息，msg 为消息对象，仅能撤回自己或管理员撤回群员的消息

### `deleteMsg(Object msg)` //删除一条消息

`forbidden(String GroupUin, String UserUin, int time)` // 禁言，仅管理员可用，时间单位为秒，全体禁言不写用户账号即可。如果是全体禁言时间结束后会变成假全体禁言，显示全体禁言但能发消息

`kick(String GroupUin, String UserUin, boolean isBlack)` // 踢出，仅管理员可用，参数3表示是否禁止再次申请

---

# 获取信息接口(获取的数据量越多  耗时越多)

直接使用Object指代即可,无需强转类型

### `getMemberName(String group, String uin)` 获取群内成员名称 参数一为群号 参数二为群员QQ号

---

### `ArrayList<GroupInfo> getGroupList()` 获取群信息列表

### `GroupInfo getGroupInfo(GroupUin)`   获取指定群信息

### `GroupInfo`成员包括：

`String GroupUin`(群号)；

`String GroupName`(群名)；

`String GroupOwner`(群主账号)；

`String[] AdminList`(管理员列表 包括群主,不一定总是最新,通常30分钟刷新一次)
`boolean IsOwnerOrAdmin` 我在此群是否是群主或者管理

`Object sourceInfo`(原对象 对应com.tencent.mobileqq.data.troop.TroopMemberInfo)

---

### `ArrayList<GroupMemberInfo> getGroupMemberList(String GroupUin)` 获取群成员信息列表
`GroupMemberInfo getMemberInfo(String group, String uin)` 指定群指定成员信息

### `GroupMemberInfo` 成员包括 :

`String UserUin`(成员账号)；
`public String NickName` (群内昵称)
`String UserName`(成员名字(好友备注))；
`int UserLevel`(成员群聊等级)；
`long Join_Time`(成员加群时间)；
`long Last_AvtivityTime`(最后发言时间，不一定能实时刷新)
`Object sourceInfo`(原对象 对应com.tencent.mobileqq.data.troop.TroopInfo)

`boolean IsOwner;` 是否群主

`boolean IsAdmin;` 是否管理

---

### `ArrayList<ForbiddenInfo> getForbiddenList(String GroupUin)` // 获取一个群聊被禁言的成员列表

`ForbiddenInfo` 成员包括：

`String UserUin`(成员号码)；

`String UserName`(成员名字)；

`long Endtime`(禁言结束的时间戳)

---

### `ArrayList<FriendInfo> getFriendList()` //获取好友列表

### `FriendInfo` 包括:

`String uin;` QQ号

`String name;` QQ昵称

`String remark;` 备注

`boolean isVip = false;` 是否会员

`int vipLevel = 0;` 会员等级

额外方法

`boolean isFriend(String uin)` //判断是否是好友

---

## 简单数据存储方法

`void putString(String ConfigName, String key, String value)`; // 存储文本数据

`String getString(String ConfigName, String key);` // 读取文本数据

`String getString(String ConfigName, String key,def);` 
`void putInt(String ConfigName, String key, int value);` // 存储整数数据
`int getInt(String ConfigName, String key, int def) ;`// 读取整数数据，参数3为未获取到时的默认数据
`void putLong(String ConfigName, String key, long value);` // 存储长整数数据
`long getLong(String ConfigName, String key, long def);` // 读取长整数数据，参数3为未获取到时的默认数据
`void putBoolean(String ConfigName, String key, boolean value);` // 存储布尔数据
`boolean getBoolean(String ConfigName, String key, boolean def);` // 读取布尔数据，参数3为未获取到时的默认数值
`float getFloat(String ConfigName, String key, float def);`
`void putFloat(String ConfigName, String key, float value);`
`void putDouble(String ConfigName, String key, double value);`
`double getDouble(String ConfigName, String key, double def);`

---

## Skey类方法

 `String getGroupRKey()` //获取群聊rkey
 `String getFriendRKey()` ///获取私聊rkey
`String getSkey()` //获取标准skey
`String getRealSkey()` //获取可能是真实的skey
`String getPskey(String url)` //懂的都懂
`String getPT4Token(String str)`  //我也不懂
`String getGTK(String str)` //好像是什么
`long getBKN(String pskey)` //有用的人自然懂

---

## 其他方法

`Activity getActivity()` // 获取当前 QQ 顶层活动，如果 QQ 在后台返回 null
`toast(Object content)` // 弹出 toast 提示
`load(String Path)` // 在当前脚本环境再加载一个 java，路径为相对路径或绝对路径（推荐相对路径）
`loadJar(String JarPath)` //加载Jar

`eval(String code)`   直接热加载一段java代码
`error(Throwable throwable)`  //打印异常到脚本目录
`log(Object content)` //输出日志到脚本目录下

`String httpGet(String url);`  //内置http get请求方法 可以获取能在浏览器中打开的链接内容
`String httpGet(String url, Map<String, String> headers);`  同上 可以获取请求头

`String httpPost(String,Map<String,String> data);` //内置http post请求方法 可以用来发送post表单请求 
`String httpPost(String url, Map<String, String> headers, Map<String, String> data)` 同上 可携带请求头

`String httpPostJson(String url, String data)` //内置http postJSON请求方法 可以用来发送post`application/json; charset=utf-8`请求 
`String httpPostJson(String url, Map<String, String> headers, String data)` 同上 可携带请求头

 `httpDownload(String url, String path);`  //内置http 下载文件方法 参数一为文件链接,参数二为路径,必须是脚本内的相对路径 其他路径QQ可能没有权限读写
`httpDownload(String url, String path, Map<String, String> headers)` 同上 headers参数可携带请求头

- Map参数和响应结果仅支持字符串
- get方法和post如果请求异常会返回 "” 字符串
- 文件下载失败则会抛出异常

---

### 文件操作方法 写入操作会自动创建父级文件夹和目标文件

`String readFileText(String path);`  //读取文件内的文本
`void writeTextToFile(String path, String text);`  //写文本到文件，覆盖写模式
`void writeTextAppendToFile(String path, String text);` //写文本到文件，追加写模式

`byte[] readFileBytes(String path);` //读取文件字节
`void writeBytesToFile(String path, byte[] bytes);` //写入字节到文件

---

## 悬浮窗可打开的菜单方法

### `String addItem(String Name, String CallbackName);`  // 添加一个菜单，将由模块显示在聊天窗口中。
`void addTemporaryItem(String ItemName, String CallbackName)`//添加一个临时菜单 在脚本菜单弹窗关闭会将会自动删除

### 上面两个方法 ： 方法参数1为显示的名字，参数2为回调方法的名字。

### 被模块调用的回调方法需提供3个参数 参数1为群号 参数2为QQ号  参数三私聊为1 群组为2

`*void r*emoveItem(String ItemID)`; //删除菜单

### 示例 (来自 “关键词” 脚本)

```java
     		addItem("开关加载提示", "加载提示", PluginID);
        public void 加载提示(String groupUin,String uin,int chatType)
        {
            if (getString("加载提示", "开关") == null) {
                putString("加载提示", "开关", "关");
                toast("已关闭加载提示");
            } else {
                putString("加载提示", "开关", null);
                toast("已开启加载提示");
            }
        }
        if (getString("加载提示", "开关") == null) toast("加载成功")
```

---

### 常规开关功能实现

```java
String configName = "开关";
addItem("开关","open");
public void open(String groupUin,String uin,int chatType)
{
    String configName = "开关";
    if (chatType != 2) {
        toast("不支持私聊开启");
        return;
    }
    if(getBoolean(configName,groupUin,false))
	{
	    putBoolean(configName,groupUin,false);
        toast("已关闭"+groupUin);
	} else{
	    putBoolean(configName,groupUin,true);
	    toast("已开启"+groupUin);
	}
}

public boolean isOpen(String groupUin){
    return getBoolean(configName,groupUin,false);
}

//监听进退群
void onTroopEvent(String groupUin, String userUin, int type){
    if (isOpen(groupUin)) {
        if (type == 2) {
            sendMsg(groupUin,"","有人加入:"+userUin);
        } 
        if (type == 1) {
            sendMsg(groupUin,"","有人退出:"+userUin);
        }
    }   
}
```

## 长按消息出现的菜单方法
`String addMenuItem(String Name, String CallbackName);` // 在长按消息的菜单中添加一个选项 参数一为选项名称 参数二为回调方法名称  回调方法只需要提供一个参数  被回调时会传入同`onMsg(MessageData msg)` 的msg参数，必须在`onCreateMenu(MessageData msg)`中使用

创建菜单的示例如下(演示如何创建一个仅在群内显示的菜单)

```java
void onCreateMenu(MessageData msg){
    if(msg.IsGroup){
        addMenuItem("仅群","showGroup");
    }
}
void showGroup(MessageData msg){
    toast("提示在"+msg.GroupUin);
}
```

---

### 关于当前窗口方法

### `int getChatType();`  获取当前聊天类型 1为私聊 2为群聊

### `String getCurrentGroupUin();` 获取当前聊天的群聊 如果私聊则返回空

### `String getCurrentFriendUin()；` 获取当前聊天的好友QQ 如果在群聊则返回空

---

一个基本的QStory Java脚本示例代码

```java
//接收到消息时QStory会调用此方法
public void onMsg(Object msg) {
    //消息内容
    String text = msg.MessageContent;
    //发送者qq
    String qq = msg.UserUin;
    //发送者群聊
    String qun = msg.GroupUin;

    if (text.equals("菜单") && qq.equals(myUin)) {

		String reply = "TG频道：https://t.me/QStoryPlugin\n交流群:979938489\n---------\n这是菜单 你可以发送下面的指令来进行测试  \n艾特我\n回复我\n私聊我";

        if (msg.IsGroup)
        {
            sendMsg(qun,"",reply);
        }
        else
        {
            sendMsg("",qq,reply);
        }
    }

    if (text.equals("艾特我") && msg.IsGroup && qq.equals(myUin)) {
        sendMsg(qun,"","[AtQQ="+qq+"] 嗯呐");
    }

    if (text.equals("回复我") && msg.IsGroup && qq.equals(myUin)) {
        sendReply(qun,msg,"好啦");
    }

    if (text.equals("私聊我")) {
        sendMsg(qun,qq,"我已经私聊你咯");
    }

    //正则表达式+解析时间格式来进行禁言 可以响应"禁言@xxx 1天"这样的消息
    //下面我写了三个匹配条件 并用&&相连 表示他们需要全部匹配才会发生
    if(msg.IsSend //是自己发送
    && msg.MessageContent.matches("禁言 ?@[\\s\\S]+[0-9]+(天|分|时|小时|分钟|秒)") //是"禁言@xxx 1天"这样的消息
    && msg.mAtList.size()>=1//艾特列表中 艾特人数至少有1个
    ) {
        int banTime = parseTimeBymessage(msg);
        if(banTime>=60*60*24*30+1) {
            sendMsg(msg.GroupUin,"","请控制在30天以内");
            return;
        } else {
            for(String atUin : msg.mAtList) {
                forbidden(msg.GroupUin,atUin,banTime);
            }
        }

    }

}

//将"禁言@xxx 1天"解析成 84600这样的秒格式
public int parseTimeBymessage(Object msg){
	int timeStartIndex = msg.MessageContent.lastIndexOf(" ");
	String date = msg.MessageContent.substring(timeStartIndex +1);
	date = date.trim();
	String t="";
	if(date != null && !"".equals(date)){
		for(int i=0;i<date.length();i++){
			if(date.charAt(i)>=48 && date.charAt(i)<=57){
				t +=date.charAt(i);
			}
		}
	}
    int time=Integer.parseInt(t);
	if(date.contains("天")){
		return time*60*60*24;
	}
	else if(date.contains("时") || date.contains("小时") ){
	 	return 60*60*time;
	}
	else if(date.contains("分") || date.contains("分钟") ){
		return 60*time;
    }
    return time;
}

//添加脚本悬浮窗菜单项
addItem("开关加载提示","加载提示");
//对应 "加载提示" 这个方法名
public void 加载提示(String s)
{
    //getString的参数分别是 配置文件名 Key键名
    if(getString("加载提示","开关")==null) {
	    putString("加载提示","开关","关");
        toast("已关闭加载提示");
    } else {
	    putString("加载提示","开关",null);
	    toast("已开启加载提示");
	}
}
if (getString("加载提示","开关")==null)
toast("发送 \"菜单\" 查看使用说明");
```

---