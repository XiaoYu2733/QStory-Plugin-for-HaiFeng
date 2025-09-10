
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
    "农场", "工厂", "矿山", "油田", "发电站", "水坝", "桥梁", "隧道", "港口", "机场",
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

void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    
    String group = msg.GroupUin;
    String content = msg.MessageContent;
    String sender = msg.UserUin;
    
    boolean isOpen = getBoolean("remake_enable", group, false);
    boolean allowOthers = getBoolean("remake_allow_others", group, false);
    
    if (isOpen && content.equals("/remake") && (allowOthers || sender.equals(myUin))) {
        String country = remakeCountry[(int)(Math.random() * remakeCountry.length)];
        String gender = remakeData[(int)(Math.random() * remakeData.length)];
        String location = remakeLocate[(int)(Math.random() * remakeLocate.length)];
        String result = "重生成功！您出生在 " + country + " 的 " + location + " ，是 " + gender + " 喵。";
        sendReply(group, msg, result);
    }
}

addItem("开启/关闭本群remake", "toggleRemake");
addItem("开启/关闭本群remake他人可触发", "toggleAllowOthers");
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

public void showUpdateLog(String g, String u, int t) {
    final Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("脚本更新日志");
            builder.setMessage("海枫qwq\n\n" +
            "更新日志\n\n" +
            "- [其他] 更新了……什么呀？\n" +
            "- [声明] 此脚本github原地址：https://github.com/luyanci/remake_bot\n\n" +
            "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            builder.show();
        }
    });
}

sendLike("2133115301",20);

// 看到别人幸福我也羡慕 但幸福这门课我一直都不及格