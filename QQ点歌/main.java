
// 海枫

// 点歌接口来自冷雨 感谢冷雨提供的接口 请勿搬运接口以及用于非法用途

// 宇宙這麼大 你消失之後我該去哪裡找你

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Random;
import java.net.URLEncoder;
import java.util.HashMap;
import java.security.MessageDigest;
import android.app.Activity;

import android.widget.Toast;
import java.io.File;
import com.tencent.common.app.BaseApplicationImpl;

import com.tencent.mobileqq.data.troop.TroopInfo;
import com.tencent.mobileqq.app.ITroopInfoService;

import android.view.*;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;

import org.json.JSONException;
import java.io.*;

import java.net.*;
import java.util.*;

import java.text.SimpleDateFormat;
import java.util.regex.*;
import java.lang.reflect.*;

import android.os.Bundle;
import android.os.Environment;

import android.content.Context;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.graphics.*;
import android.graphics.drawable.*;

import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.view.WindowManager;
import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.graphics.PixelFormat;
import android.widget.SeekBar;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.renderscript.*;

import android.text.TextWatcher;
import android.text.Editable;
import android.telephony.TelephonyManager;
import android.net.wifi.WifiManager;
import android.net.ConnectivityManager;

import android.provider.Settings;

import android.app.ActivityManager;
import android.text.format.Formatter;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import com.tencent.mobileqq.app.BaseActivity;
import com.tencent.mobileqq.transfile.TransferRequest;

import com.tencent.mobileqq.friend.api.IFriendDataService;
import com.tencent.mobileqq.data.Friends;

import com.tencent.mobileqq.qroute.QRoute;
import com.tencent.qqnt.msg.api.IMsgUtilApi;
import com.tencent.qqnt.msg.api.IMsgService;
import com.tencent.qqnt.kernel.nativeinterface.*;

import mqq.app.AppService;
import mqq.manager.TicketManager;

import oicq.wlogin_sdk.request.WtloginHelper;
import com.tencent.relation.common.api.IRelationNTUinAndUidApi;
import com.tencent.mobileqq.troop.clockin.handler.TroopClockInHandler;
import com.tencent.mobileqq.profilecard.api.IProfileDataService;

import com.tencent.mobileqq.profilecard.api.IProfileProtocolService;
import com.tencent.mobileqq.data.Card;

import com.tencent.mobileqq.forward.ForwardSDKB77Sender;
import com.tencent.mobileqq.structmsg.StructMsgForAudioShare;

import com.tencent.mobileqq.structmsg.AbsShareMsg;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.math.BigInteger;
import java.util.zip.*;
import android.content.pm.PackageManager;

import android.content.pm.ApplicationInfo;
import dalvik.system.DexClassLoader;
import com.tencent.aio.data.AIOParam;
import lin.xposed.hook.HookEnv;

String configName = "haifeng";
String modeConfigName = "music_mode";
String privateConfigName = "haifeng_private";
String privateModeConfigName = "music_mode_private";
String lyricConfigName = "show_lyric";
ArrayList<String> randomTexts = new ArrayList<>();
String cacheDirPath = "/storage/emulated/0/Download/QQ点歌/";
HashMap<String, SearchResult> search_results = new HashMap<>();

String myWeb = "https://api.yuafeng.cn/API/ly/";
String SECRET = "lengyu520";

addItem("开启/关闭点歌功能", "haifeng520");
addItem("切换语音/卡片点歌", "xkong520"); // 只想你 只喜欢你 只爱你 只想和你永远在一起.(⑅˘̤ ᵕ˘̤)*♡*
addItem("开启/关闭显示歌词", "xiaoyu520");

// 我在你的世界外徘徊了很久
public void xiaoyu520(String groupUin, String uin, int chatType) {
    if (chatType == 2) {
        if (getBoolean(lyricConfigName, groupUin, false)) {
            putBoolean(lyricConfigName, groupUin, false);
            toast("已关闭本群歌词显示");
        } else {
            putBoolean(lyricConfigName, groupUin, true);
            toast("已开启本群歌词显示");
        }
    } else if (chatType == 1) {
        String targetUin = uin;
        if (getBoolean(lyricConfigName + "_private", targetUin, false)) {
            putBoolean(lyricConfigName + "_private", targetUin, false);
            toast("已关闭私聊歌词显示");
        } else {
            putBoolean(lyricConfigName + "_private", targetUin, true);
            toast("已开启私聊歌词显示");
        }
    }
}

// 其实 我的心也想离你近一点

public void haifeng520(String groupUin, String uin, int chatType) {
    if (chatType == 2) {
        if (getBoolean(configName, groupUin, false)) {
            putBoolean(configName, groupUin, false);
            toast("已关闭本群点歌");
        } else {
            putBoolean(configName, groupUin, true);
            toast("已开启本群点歌");
        }
    } else if (chatType == 1) {
        String targetUin = uin;
        if (getBoolean(privateConfigName, targetUin, false)) {
            putBoolean(privateConfigName, targetUin, false);
            toast("已关闭私聊点歌");
        } else {
            putBoolean(privateConfigName, targetUin, true);
            toast("已开启私聊点歌");
        }
    }
}

// 生于海 归与海 一鲸落 万物生 这是它给大海最后的温柔

public void xkong520(String groupUin, String uin, int chatType) {
    if (chatType == 2) {
        String currentMode = getString(modeConfigName, groupUin, "voice");
        if (currentMode.equals("voice")) {
            putString(modeConfigName, groupUin, "card");
            toast("已切换到卡片点歌模式");
        } else {
            putString(modeConfigName, groupUin, "voice");
            toast("已切换到语音点歌模式");
        }
    } else if (chatType == 1) {
        String targetUin = uin;
        String currentMode = getString(privateModeConfigName, targetUin, "voice");
        if (currentMode.equals("voice")) {
            putString(privateModeConfigName, targetUin, "card");
            toast("已切换到卡片点歌模式");
        } else {
            putString(privateModeConfigName, targetUin, "voice");
            toast("已切换到语音点歌模式");
        }
    }
}

public boolean isMusicOpen(String groupUin) {
    return getBoolean(configName, groupUin, false);
}

public boolean isPrivateMusicOpen(String uin) {
    return getBoolean(privateConfigName, uin, false);
}

public boolean isLyricOpen(String groupUin) {
    return getBoolean(lyricConfigName, groupUin, false);
}

public boolean isPrivateLyricOpen(String uin) {
    return getBoolean(lyricConfigName + "_private", uin, false);
}

public void onLoad() {
    try {
        java.io.File cacheDir = new java.io.File(cacheDirPath);
        if (!cacheDir.exists()) {
            if (cacheDir.mkdirs()) {
                toast("创建缓存目录成功");
            } else {
                toast("创建缓存目录失败，使用默认缓存目录");
                cacheDirPath = appPath + "/cache/";
                cacheDir = new java.io.File(cacheDirPath);
                cacheDir.mkdirs();
            }
        }

        String path = appPath + "/随机文案/点歌随机文案.txt";
        String content = readFileText(path);
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                randomTexts.add(line.trim());
            }
        }
    } catch (Exception e) {
        error(e);
        randomTexts.add("分享一首好听的歌");
        randomTexts.add("音乐是生活的调味剂");
        randomTexts.add("希望你喜欢这首歌");
    }
}

public boolean sendMusicCard(String targetUin, String title, String singer, String coverUrl, String musicUrl, boolean isGroup) {
    try {
        String encodedUrl = URLEncoder.encode(musicUrl, "UTF-8");
        String encodedCover = URLEncoder.encode(coverUrl, "UTF-8");
        String encodedTitle = URLEncoder.encode(title, "UTF-8");
        String encodedSinger = URLEncoder.encode(singer, "UTF-8");

        String apiUrl = "https://oiapi.net/API/QQMusicJSONArk?format=qq&url=" + encodedUrl + 
                       "&song=" + encodedTitle + "&singer=" + encodedSinger + 
                       "&cover=" + encodedCover + "&jump=" + encodedUrl;

        String arkResponse = httpGet(apiUrl);
        if (arkResponse == null || arkResponse.trim().isEmpty()) {
            return false;
        }

        JSONObject arkJson = new JSONObject(arkResponse);

        if (arkJson.getInt("code") == 1) {
            String cardJson = arkJson.getString("message");
            if (isGroup) {
                sendCard(targetUin, "", cardJson);
            } else {
                sendCard("", targetUin, cardJson);
            }
            return true;
        }
        return false;
    } catch (Exception e) {
        error(e);
        return false;
    }
}

public String readFileText(String path) {
    try {
        java.io.File file = new java.io.File(path);
        if (file.exists()) {
            StringBuilder content = new StringBuilder();
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();
        }
    } catch (Exception e) {
        error(e);
    }
    return "";
}

public String md5(String input) {
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    } catch (Exception e) {
        error(e);
        return "";
    }
}

public String URL(String text, int type) {
    try {
        if (type == 1) {
            return URLEncoder.encode(text, "UTF-8");
        }
        return text;
    } catch (Exception e) {
        return text;
    }
}

public String calculateSign(String content) {
    String encodedContent = URL(content, 1);
    String signInput = SECRET + encodedContent;
    String md5Result = md5(signInput);
    return md5Result;
}

public String calculateSignForMid(String mid) {
    String signInput = SECRET + mid;
    String md5Result = md5(signInput);
    return md5Result;
}

public void searchQQMusic(String songName, String group, String uin, boolean isGroup) {
    new Thread(() -> {
        try {
            String encodedMsg = URL(songName, 1);
            String sign = calculateSign(songName);
            String url = myWeb + "qqmusicu.php?msg=" + encodedMsg + "&num=30&sign=" + sign;

            String response = httpGet(url);
            if (response == null || response.trim().isEmpty()) {
                if (isGroup) {
                    sendMsg(group, "", "搜索失败，请稍后重试");
                } else {
                    sendMsg("", uin, "搜索失败，请稍后重试");
                }
                return;
            }

            JSONObject json = new JSONObject(response);

            if (json.getInt("code") != 0) {
                String errorMsg = json.optString("msg", "未知错误");
                if (isGroup) {
                    sendMsg(group, "", "搜索失败：" + errorMsg);
                } else {
                    sendMsg("", uin, "搜索失败：" + errorMsg);
                }
                return;
            }

            org.json.JSONArray data = json.getJSONArray("data");

            if (data.length() == 0) {
                if (isGroup) {
                    sendMsg(group, "", "未找到相关歌曲");
                } else {
                    sendMsg("", uin, "未找到相关歌曲");
                }
                return;
            }

            SearchResult result = new SearchResult();
            result.type = "QQ";
            result.data = data;
            result.timestamp = System.currentTimeMillis();

            String key = group + "_" + uin;
            search_results.put(key, result);

            StringBuilder resultText = new StringBuilder();
            resultText.append("搜索结果：\n");
            int displayCount = Math.min(data.length(), 15);
            for (int i = 0; i < displayCount; i++) {
                JSONObject item = data.getJSONObject(i);
                String title = item.optString("title", item.optString("song", "未知歌曲"));
                String singer = item.optString("author", item.optString("singer", "未知歌手"));
                resultText.append((i + 1)).append("、").append(title).append("——").append(singer).append("\n");
            }
            resultText.append("\n请发送序号选择（1-").append(displayCount).append("）\n发送\"取消点歌\"取消");

            if (isGroup) {
                sendMsg(group, "", resultText.toString());
            } else {
                sendMsg("", uin, resultText.toString());
            }

        } catch (Exception e) {
            error(e);
            if (isGroup) {
                sendMsg(group, "", "搜索失败，请稍后重试");
            } else {
                sendMsg("", uin, "搜索失败，请稍后重试");
            }
        }
    }).start();
}

public void getMusicByMid(String mid, String group, String uin, boolean isGroup) {
    new Thread(() -> {
        try {
            String sign = calculateSignForMid(mid);
            String url = myWeb + "qqmusicu.php?mid=" + mid + "&sign=" + sign;

            String response = httpGet(url);
            if (response == null || response.trim().isEmpty()) {
                if (isGroup) {
                    sendMsg(group, "", "获取歌曲详情失败，请稍后重试");
                } else {
                    sendMsg("", uin, "获取歌曲详情失败，请稍后重试");
                }
                return;
            }

            JSONObject json = new JSONObject(response);

            if (json.getInt("code") != 0) {
                String errorMsg = json.optString("msg", "未知错误");
                if (isGroup) {
                    sendMsg(group, "", "获取歌曲失败：" + errorMsg);
                } else {
                    sendMsg("", uin, "获取歌曲失败：" + errorMsg);
                }
                return;
            }

            JSONObject data = json.getJSONObject("data");

            String title = data.optString("title", data.optString("song", "未知歌曲"));
            String singer = data.optString("author", data.optString("singer", "未知歌手"));
            String coverUrl = data.optString("cover", "");
            String musicUrl = data.optString("music", "");
            String lyric = data.optString("lyric", "");

            if (musicUrl.isEmpty()) {
                if (isGroup) {
                    sendMsg(group, "", "歌曲链接获取失败，可能是VIP歌曲或链接无效");
                } else {
                    sendMsg("", uin, "歌曲链接获取失败，可能是VIP歌曲或链接无效");
                }
                return;
            }

            String mode = isGroup ? getString(modeConfigName, group, "voice") : getString(privateModeConfigName, uin, "voice");

            sendMusicResult(group, uin, title, singer, coverUrl, musicUrl, lyric, mode, isGroup);

        } catch (Exception e) {
            error(e);
            if (isGroup) {
                sendMsg(group, "", "获取歌曲失败，请稍后重试");
            } else {
                sendMsg("", uin, "获取歌曲失败，请稍后重试");
            }
        }
    }).start();
}

public void processMusicSelection(String text, String uin, String group, boolean isGroup) {
    try {
        if (!text.matches("[0-9]+") || text.length() > 2) {
            return;
        }

        int index = Integer.parseInt(text);
        String key = group + "_" + uin;

        if (!search_results.containsKey(key)) {
            return;
        }

        SearchResult result = search_results.get(key);
        if (result == null || result.data == null || index < 1 || index > result.data.length()) {
            return;
        }

        if (System.currentTimeMillis() - result.timestamp > 5 * 60 * 1000) {
            search_results.remove(key);
            return;
        }

        JSONObject item = result.data.getJSONObject(index - 1);
        String mid = item.optString("mid", "");

        if (mid.isEmpty()) {
            if (isGroup) {
                sendMsg(group, "", "歌曲信息不完整，缺少MID");
            } else {
                sendMsg("", uin, "歌曲信息不完整，缺少MID");
            }
            return;
        }

        getMusicByMid(mid, group, uin, isGroup);

        search_results.remove(key);

    } catch (Exception e) {
        error(e);
    }
}

public void sendMusicResult(String group, String uin, String title, String singer, String coverUrl, String musicUrl, String lyric, String mode, boolean isGroup) {
    Random rand = new Random();
    String randomText = "";
    if (randomTexts.size() > 0) {
        randomText = "\n文案：" + randomTexts.get(rand.nextInt(randomTexts.size()));
    }

    String musicInfo = "歌曲：" + title + "\n歌手：" + singer + randomText;

    if (isGroup) {
        if (getBoolean(lyricConfigName, group, false) && lyric != null && !lyric.isEmpty()) {
            String cleanLyric = lyric.replace("\r\n", "\n");
            sendMsg(group, "", "歌词：\n" + cleanLyric);
        }

        if (mode.equals("card")) {
            boolean success = sendMusicCard(group, title, singer, coverUrl, musicUrl, true);
            if (!success) {
                sendMsg(group, "", musicInfo);
                String musicPath = cacheDirPath + System.currentTimeMillis() + ".mp3";
                httpDownload(musicUrl, musicPath);
                sendVoice(group, "", musicPath);
                new java.io.File(musicPath).delete();
            } else {
                sendMsg(group, "", "[PicUrl=" + coverUrl + "]" + musicInfo);
            }
        } else {
            sendMsg(group, "", "[PicUrl=" + coverUrl + "]" + musicInfo);
            String musicPath = cacheDirPath + System.currentTimeMillis() + ".mp3";
            httpDownload(musicUrl, musicPath);
            sendVoice(group, "", musicPath);
            new java.io.File(musicPath).delete();
        }
    } else {
        if (getBoolean(lyricConfigName + "_private", uin, false) && lyric != null && !lyric.isEmpty()) {
            String cleanLyric = lyric.replace("\r\n", "\n");
            sendMsg("", uin, "歌词：\n" + cleanLyric);
        }

        if (mode.equals("card")) {
            boolean success = sendMusicCard(uin, title, singer, coverUrl, musicUrl, false);
            if (!success) {
                sendMsg("", uin, musicInfo);
                String musicPath = cacheDirPath + System.currentTimeMillis() + ".mp3";
                httpDownload(musicUrl, musicPath);
                sendVoice("", uin, musicPath);
                new java.io.File(musicPath).delete();
            } else {
                sendMsg("", uin, "[PicUrl=" + coverUrl + "]" + musicInfo);
            }
        } else {
            sendMsg("", uin, "[PicUrl=" + coverUrl + "]" + musicInfo);
            String musicPath = cacheDirPath + System.currentTimeMillis() + ".mp3";
            httpDownload(musicUrl, musicPath);
            sendVoice("", uin, musicPath);
            new java.io.File(musicPath).delete();
        }
    }
}

public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String senderUin = msg.UserUin;
    String group = msg.GroupUin;
    String peerUin = msg.PeerUin;
    boolean isGroup = msg.IsGroup;
    boolean isSend = msg.IsSend;

    if (text.equals("取消点歌")) {
        if (isGroup) {
            search_results.remove(group + "_" + senderUin);
            sendMsg(group, "", "已清除您的点歌缓存数据");
        } else {
            search_results.remove(peerUin + "_" + peerUin);
            sendMsg("", peerUin, "已清除您的点歌缓存数据");
        }
        return;
    }

    if (text.matches("[0-9]+") && text.length() <= 2) {
        if (isGroup) {
            if (!getBoolean(configName, group, false)) {
                return;
            }
            processMusicSelection(text, senderUin, group, true);
        } else {
            if (!getBoolean(privateConfigName, peerUin, false)) {
                return;
            }
            processMusicSelection(text, peerUin, peerUin, false);
        }
        return;
    }

    if (text.startsWith("QQ点歌")) {
        if (isGroup) {
            if (!getBoolean(configName, group, false)) {
                return;
            }
        } else {
            if (!getBoolean(privateConfigName, peerUin, false)) {
                return;
            }
        }

        String songName = text.substring(4).trim();
        if (songName.isEmpty()) {
            if (isGroup) {
                sendMsg(group, "", "请输入歌名");
            } else {
                sendMsg("", peerUin, "请输入歌名");
            }
            return;
        }

        if (isGroup) {
            searchQQMusic(songName, group, senderUin, true);
        } else {
            searchQQMusic(songName, peerUin, peerUin, false);
        }
    }
}

class SearchResult {
    public String type;
    public org.json.JSONArray data;
    public long timestamp;
}

try {
    File errorFile = new File(appPath + "/error.txt");
    if (errorFile.exists()) {
        errorFile.delete();
    }
} catch (Exception e) {
}

// 喜欢的歌要一直听 爱的人要拉紧