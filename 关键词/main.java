
// 临江踏雨不返 海枫

// 你总是担心失去谁 可谁又会担心失去你

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

String basePath = appPath + "/纵有相思零落处/";
String dataPath = basePath + "/也无旧时折枯枝/";
load(basePath + "秋诗.java");

public class JiangYun {
    public static String[][] HELP_MENU = {
        {"关键词", "◆关键词◆\n●添加方法●删除方法●\n●其他命令●其他说明●"},
        {"添加方法", "◆关键词添加方法◆\n以下是使用方法\n添加关键词+关键词+[处理方式列表]\n以下是支持的处理方式 : \n回复+文字,禁言+时间,\n全体禁言,全体解禁,\n撤回,踢出,踢黑,回+文字 \n\n延迟+毫秒时间 \n\n示范发送 : (仅别人可以触发)\n添加关键词 江 [回复雨,禁言60,撤回]\n然后发送包含\"江\"的消息就会进行上面的命令\n命令是顺序执行的 可以重复添加 要用\",\"分隔\n有两条相同的关键词新的处理方式会替代旧的处理方式"},
        {"删除方法", "◆关键词删除方法◆\n以下是删除方法\n删除关键词+关键词\n例如 : 删除关键词 江\n(可以发送清空关键词快速清空关键词)"},
        {"其他命令", "◆其他命令◆\n●查看关键词●清空关键词●\n●查看所有关键词●清空所有关键词●\n●正则表达式●查看支持的变量●"},
        {"查看支持的变量", "◆支持的动态参数◆\nAtQQ 在添加为回复内容时AtQQ会被替换为艾特发送人\nqun 在添加为回复内容时会被替换为本群群号\n敬请期待其他参数"},
        {"其他说明", "本jaja脚本的消息默认自己不触发自己发送的消息(为了防止错误添加导致自己触发自己刷屏)，\n但你可以发送\n \"接受消息\" 来关闭这个限制，发送\n \"屏蔽消息\" 来打开此限制\n\n在添加关键词时如添加了 [撤回] 则会把添加关键词的这条指令消息撤回，并且不会发出添加成功的消息，而是以提示的方法提醒添加成功，此限制会同步所有群"},
        {"正则表达式", "发送 \"开启正则\" 则会开启正则表达式匹配关键词，发送 \"关闭正则\" 则会关闭正则表达模式 该模式是单个群使用的，您最好在使用该模式前发送 \"详细正则说明\" 来查看与脚本有关的正则说明"},
        {"详细正则说明", "如果不会使用正则表达式可以去搜索引擎搜索 \"正则表达式\" 来学习正则表达式\n\n接下来会说明正则表达式对此脚本的影响\n1.使用正则表达式添加关键词时 \"[]\" 是可以正常匹配处理方式的 因为处理方式的substring方法\"[]\"会自动从文末端开始查找，不会与正则表达式符号\"[]\"产生冲突性\n2.即使你认为你不需要使用此模式，依然推荐你学习正则表达式，因为强大的正则表达式仍然可以帮你解决很多麻烦的匹配词\n另外欢迎加入我们的交流小组 ：634941583"}
    };
}

ConcurrentHashMap<String, JSONObject> keywordStore = new ConcurrentHashMap<>();

public void initKeywordStore() {
    try {
        File dataDir = new File(dataPath);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
            return;
        }
        File[] fileList = dataDir.listFiles();
        if (fileList == null) return;
        
        for (int i = 0; i < fileList.length; i++) {
            File file = fileList[i];
            if (file.getName().matches("\\d+\\.json") && file.isFile()) {
                String groupId = file.getName().substring(0, file.getName().lastIndexOf("."));
                try {
                    String content = QiuShi.readFile(file.getAbsolutePath());
                    if (content != null && !content.trim().isEmpty() && !content.equals("{}")) {
                        keywordStore.put(groupId, new JSONObject(content));
                    } else {
                        keywordStore.put(groupId, new JSONObject());
                    }
                } catch (Exception e) {
                    keywordStore.put(groupId, new JSONObject());
                }
            }
        }
    } catch (Exception e) {
    }
}

initKeywordStore();

public void onMsg(Object msg) {
    if (msg == null) return;
    if (!msg.IsGroup) return;
    
    String groupId = msg.GroupUin;
    String content = msg.MessageContent;
    String userId = msg.UserUin;
    
    if (groupId == null || content == null || userId == null) return;
    
    if (userId.equals(myUin)) {
        for (int i = 0; i < JiangYun.HELP_MENU.length; i++) {
            if (content.equals(JiangYun.HELP_MENU[i][0])) {
                sendMsg(groupId, "", JiangYun.HELP_MENU[i][1]);
                return;
            }
        }
    }
    
    if (userId.equals(myUin)) {
        if (content.equals("接受消息") || content.equals("接收消息")) {
            if (getString("关键词", "己") == null) {
                putString("关键词", "己", "开");
                sendMsg(groupId, "", "已关闭自己不触发的限制");
            } else {
                sendMsg(groupId, "", "无需重复关闭此限制");
            }
            return;
        }
        
        if (content.equals("屏蔽消息")) {
            if (getString("关键词", "己") == null) {
                sendMsg(groupId, "", "当前已是不接受自身消息的状态");
            } else {
                putString("关键词", "己", null);
                sendMsg(groupId, "", "已开启不触发自己的消息");
            }
            return;
        }
        
        if (content.equals("开启正则")) {
            if (getString("正则表达式", groupId) == null) {
                putString("正则表达式", groupId, "开");
                sendMsg(groupId, "", "已开启本群的正则表达式模式");
            } else {
                sendMsg(groupId, "", "无需重复开启此模式");
            }
            return;
        }
        
        if (content.equals("关闭正则")) {
            if (getString("正则表达式", groupId) == null) {
                sendMsg(groupId, "", "当前已是普通匹配模式");
            } else {
                putString("正则表达式", groupId, null);
                sendMsg(groupId, "", "已关闭正则表达式匹配 切换为普通模式");
            }
            return;
        }
    }
    
    if (userId.equals(myUin)) {
        if (content.startsWith("添加关键词")) {
            addKeyword(groupId, content, msg);
            return;
        }
        
        if (content.startsWith("删除关键词")) {
            deleteKeyword(groupId, content);
            return;
        }
        
        if (content.equals("查看关键词")) {
            viewKeywords(groupId);
            return;
        }
        
        if (content.equals("查看所有关键词")) {
            viewAllKeywords(groupId);
            return;
        }
        
        if (content.equals("清空所有关键词")) {
            clearAllKeywords(groupId);
            return;
        }
        
        if (content.equals("清空关键词")) {
            clearKeywords(groupId);
            return;
        }
    }
    
    if ((!userId.equals(myUin) || getString("关键词", "己") != null)) {
        JSONObject groupKeywords = keywordStore.get(groupId);
        if (groupKeywords != null) {
            Iterator<String> keys = groupKeywords.keys();
            while (keys.hasNext()) {
                try {
                    String keyword = keys.next();
                    if (content.contains(keyword)) {
                        JSONArray actionGroup = groupKeywords.optJSONArray(keyword);
                        if (actionGroup != null) {
                            executeActions(groupId, userId, content, actionGroup, msg);
                        }
                    } else if (getString("正则表达式", groupId) != null && content.matches(keyword)) {
                        JSONArray actionGroup = groupKeywords.optJSONArray(keyword);
                        if (actionGroup != null) {
                            executeActions(groupId, userId, content, actionGroup, msg);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}

public void addKeyword(String groupId, String content, Object msgObj) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String processText = content.substring(5).trim();
                int startPos = processText.lastIndexOf("[");
                int endPos = processText.lastIndexOf("]");
                
                if (startPos == -1 || endPos == -1) {
                    sendMsg(groupId, "", "格式错误，请使用: 添加关键词 关键词 [处理方式]");
                    return;
                }
                
                String keyword = processText.substring(0, startPos).trim();
                String actionText = processText.substring(startPos + 1, endPos);
                
                JSONArray actionGroup = parseActions(actionText, keyword);
                if (actionGroup.length() == 0) {
                    sendMsg(groupId, "", "不包含任何处理方式");
                    return;
                }
                
                JSONObject groupKeywords = keywordStore.get(groupId);
                if (groupKeywords == null) {
                    groupKeywords = new JSONObject();
                }
                
                groupKeywords.put(keyword, actionGroup);
                keywordStore.put(groupId, groupKeywords);
                QiuShi.writeFile(dataPath + groupId + ".json", groupKeywords.toString());
               
                boolean shouldRevoke = false;
                for (int i = 0; i < actionGroup.length(); i++) {
                    if (actionGroup.get(i).toString().equals("撤回")) {
                        shouldRevoke = true;
                        break;
                    }
                }
                
                String resultText = "关键词【" + keyword + "】\n处理方式:\n" + actionText;
                if (shouldRevoke) {
                    revokeMsg(msgObj);
                    toast(resultText);
                } else {
                    sendMsg(groupId, "", resultText);
                }
                
            } catch (Exception e) {
                sendMsg(groupId, "", "添加失败，格式错误");
            }
        }
    }).start();
}

public JSONArray parseActions(String actionText, String keyword) {
    JSONArray actionGroup = new JSONArray();
    try {
        String pattern = "撤回|回复 ?[\\S\\s][^\\]，,]*|回 ?[\\S\\s][^\\]，,]*|踢|踢出|踢黑|禁言 ?\\d+|延迟 ?\\d+|全体禁言|全体解禁|全禁|全解";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(actionText);
        
        while (m.find()) {
            String actionType = actionText.substring(m.start(), m.end());
            
            if (actionType.startsWith("回复")) {
                String replyContent = actionType.substring(2).trim();
                if (replyContent.isEmpty()) {
                    replyContent = "江江大美女";
                }
                JSONObject actionContent = new JSONObject();
                actionContent.put("回复", replyContent);
                actionGroup.put(actionContent);
            }
            else if (actionType.startsWith("回")) {
                String replyContent = actionType.substring(1).trim();
                if (replyContent.isEmpty()) {
                    replyContent = "江江大美女";
                }
                JSONObject actionContent = new JSONObject();
                actionContent.put("回", replyContent);
                actionGroup.put(actionContent);
            }
            else if (actionType.startsWith("踢")) {
                JSONObject actionContent = new JSONObject();
                if (actionType.equals("踢黑")) {
                    actionContent.put("踢", true);
                } else {
                    actionContent.put("踢", false);
                }
                actionGroup.put(actionContent);
            }
            else if (actionType.startsWith("禁言")) {
                String timeText = actionType.substring(2).trim();
                int muteTime = Integer.parseInt(timeText);
                JSONObject actionContent = new JSONObject();
                actionContent.put("禁言", muteTime);
                actionGroup.put(actionContent);
            }
            else if (actionType.startsWith("延迟")) {
                String timeText = actionType.substring(2).trim();
                long delayTime = Long.parseLong(timeText);
                JSONObject actionContent = new JSONObject();
                actionContent.put("延迟", delayTime);
                actionGroup.put(actionContent);
            }
            else if (actionType.equals("撤回")) {
                actionGroup.put("撤回");
            }
            else if (actionType.equals("全体禁言") || actionType.equals("全禁")) {
                actionGroup.put("全体禁言");
            }
            else if (actionType.equals("全体解禁") || actionType.equals("全解")) {
                actionGroup.put("全体解禁");
            }
        }
    } catch (Exception e) {
    }
    return actionGroup;
}

public void deleteKeyword(String groupId, String content) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String keyword = content.substring(5).trim();
                
                JSONObject groupKeywords = keywordStore.get(groupId);
                if (groupKeywords == null || !groupKeywords.has(keyword)) {
                    sendMsg(groupId, "", "当前群聊不存在关键词\"" + keyword + "\"");
                    return;
                }
                
                groupKeywords.remove(keyword);
                keywordStore.put(groupId, groupKeywords);
                QiuShi.writeFile(dataPath + groupId + ".json", groupKeywords.toString());
                sendMsg(groupId, "", "已删除关键词\"" + keyword + "\"");
                
            } catch (Exception e) {
                sendMsg(groupId, "", "删除失败");
            }
        }
    }).start();
}

public void viewKeywords(String groupId) {
    new Thread(new Runnable() {
        public void run() {
            try {
                JSONObject groupKeywords = keywordStore.get(groupId);
                if (groupKeywords == null || groupKeywords.length() == 0) {
                    sendMsg(groupId, "", "当前群暂无关键词");
                    return;
                }
                
                StringBuilder result = new StringBuilder("当前群的关键词列表:\n");
                Iterator<String> keys = groupKeywords.keys();
                while (keys.hasNext()) {
                    String keyword = keys.next();
                    result.append(keyword).append(" 处理方式:\n");
                    JSONArray actionGroup = groupKeywords.getJSONArray(keyword);
                    for (int i = 0; i < actionGroup.length(); i++) {
                        result.append(actionGroup.get(i).toString()).append("\n");
                    }
                    result.append("\n");
                }
                
                sendMsg(groupId, "", result.toString());
                
            } catch (Exception e) {
                sendMsg(groupId, "", "查看失败");
            }
        }
    }).start();
}

public void viewAllKeywords(String groupId) {
    new Thread(new Runnable() {
        public void run() {
            try {
                StringBuilder result = new StringBuilder();
                File dataDir = new File(dataPath);
                
                if (dataDir.exists() && dataDir.listFiles() != null) {
                    File[] files = dataDir.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        File file = files[i];
                        if (file.getName().matches("\\d+\\.json") && file.isFile()) {
                            String fileGroupId = file.getName().replace(".json", "");
                            String fileContent = QiuShi.readFile(file.getAbsolutePath());
                            if (fileContent == null || fileContent.equals("{}")) continue;
                            
                            JSONObject groupKeywords = new JSONObject(fileContent);
                            result.append("群 ").append(fileGroupId).append(":\n");
                            
                            Iterator<String> keys = groupKeywords.keys();
                            while (keys.hasNext()) {
                                String keyword = keys.next();
                                result.append(keyword).append(" 处理方式:\n");
                                JSONArray actionGroup = groupKeywords.getJSONArray(keyword);
                                for (int j = 0; j < actionGroup.length(); j++) {
                                    result.append(actionGroup.get(j).toString()).append("\n");
                                }
                            }
                            result.append("\n");
                        }
                    }
                }
                
                if (result.length() == 0) {
                    result.append("暂无任何群的关键词");
                }
                
                sendMsg(groupId, "", result.toString());
                
            } catch (Exception e) {
                sendMsg(groupId, "", "查看失败");
            }
        }
    }).start();
}

public void clearAllKeywords(String groupId) {
    new Thread(new Runnable() {
        public void run() {
            try {
                File dataDir = new File(dataPath);
                if (dataDir.exists() && dataDir.listFiles() != null) {
                    File[] files = dataDir.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        File file = files[i];
                        if (file.getName().matches("\\d+\\.json") && file.isFile()) {
                            QiuShi.writeFile(file.getAbsolutePath(), "{}");
                        }
                    }
                }
                keywordStore.clear();
                initKeywordStore();
                sendMsg(groupId, "", "所有群关键词已清空");
                
            } catch (Exception e) {
                sendMsg(groupId, "", "清空失败");
            }
        }
    }).start();
}

public void clearKeywords(String groupId) {
    new Thread(new Runnable() {
        public void run() {
            try {
                keywordStore.put(groupId, new JSONObject());
                QiuShi.writeFile(dataPath + groupId + ".json", "{}");
                sendMsg(groupId, "", "本群所有关键词已清空");
                
            } catch (Exception e) {
                sendMsg(groupId, "", "清空失败");
            }
        }
    }).start();
}

public void executeActions(String groupId, String userId, String content, JSONArray actionGroup, Object msgObj) {
    new Thread(new Runnable() {
        public void run() {
            try {
                if (actionGroup == null) return;
                
                for (int i = 0; i < actionGroup.length(); i++) {
                    Object action = actionGroup.get(i);
                    
                    if (action instanceof JSONObject) {
                        JSONObject actionObj = (JSONObject)action;
                        
                        if (actionObj.has("回复")) {
                            String replyContent = actionObj.getString("回复");
                            replyContent = replyContent.replace("AtQQ", "[AtQQ=" + userId + "]");
                            replyContent = replyContent.replace("qun", groupId);
                            sendMsg(groupId, "", replyContent);
                        }
                        
                        if (actionObj.has("回")) {
                            String replyContent = actionObj.getString("回");
                            replyContent = replyContent.replace("AtQQ", "[AtQQ=" + userId + "]");
                            replyContent = replyContent.replace("qun", groupId);
                            sendMsg(groupId, "", replyContent);
                        }
                        
                        if (actionObj.has("禁言")) {
                            forbidden(groupId, userId, actionObj.getInt("禁言"));
                        }
                        
                        if (actionObj.has("延迟")) {
                            Thread.sleep(actionObj.getLong("延迟"));
                        }
                        
                        if (actionObj.has("踢")) {
                            kick(groupId, userId, actionObj.getBoolean("踢"));
                        }
                    }
                    
                    if (action instanceof String) {
                        String actionStr = (String)action;
                        
                        if (actionStr.equals("撤回")) {
                            revokeMsg(msgObj);
                        }
                        
                        if (actionStr.equals("全体禁言")) {
                            forbidden(groupId, "", 1);
                        }
                        
                        if (actionStr.equals("全体解禁")) {
                            forbidden(groupId, "", 0);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }).start();
}

addItem("检测自己", "toggleSelfTrigger");
public void toggleSelfTrigger(String groupUin, String uin, int chatType) {
    if (getString("关键词", "己") == null) {
        putString("关键词", "己", "开");
        toast("已关闭屏蔽自己 会检测自己发送的关键词");
    } else {
        putString("关键词", "己", null);
        toast("已开启屏蔽自己 不会检测自己发送的关键词");
    }
}

addItem("正则表达式", "toggleRegexMode");
public void toggleRegexMode(String groupUin, String uin, int chatType) {
    if (getString("正则表达式", groupUin) == null) {
        putString("正则表达式", groupUin, "开");
        toast("已开启本群的正则表达式模式");
    } else {
        putString("正则表达式", groupUin, null);
        toast("已关闭本群的正则表达式模式");
    }
}

addItem("开关加载提示", "toggleLoadPrompt");
public void toggleLoadPrompt(String groupUin, String uin, int chatType) {
    if (getString("加载提示", "开关") == null) {
        putString("加载提示", "开关", "关");
        toast("已关闭加载提示");
    } else {
        putString("加载提示", "开关", null);
        toast("已开启加载提示");
    }
}

if (getString("加载提示", "开关") == null)
    toast("发送 \"关键词\" 查看使用说明");

// 希望有人懂你的言外之意 更懂你的欲言而止.
// 我始终做不到说走就走.