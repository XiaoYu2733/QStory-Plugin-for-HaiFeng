
// 作 海枫

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

String configName = "随机歌曲";
String haifengConfigName = "haifeng";
String modeConfigName = "music_mode";
String privateConfigName = "haifeng_private";
String privateModeConfigName = "music_mode_private";
String lyricConfigName = "haifeng_lyric";
String privateLyricConfigName = "haifeng_lyric_private";
ArrayList<String> randomTexts = new ArrayList<>();
String cacheDirPath = "/storage/emulated/0/Download/QQ点歌/";

HashMap neteaseCooldownMap = new HashMap();
HashMap lastNetEaseSongIdMap = new HashMap();
long cooldownTime = 5000;

addItem("开启/关闭随机音乐", "qstory");
addItem("开启/关闭热评功能", "xiaoyu520");
addItem("开启/关闭群聊点歌", "haifeng520");
addItem("切换语音/卡片点歌", "xkong520");
addItem("开启/关闭本群点歌歌词", "lengbai520");

public void qstory(String groupUin, String uin, int chatType) {
    String contextKey = (chatType == 2) ? groupUin : uin;
    String contextType = (chatType == 2) ? "群聊" : "私聊";

    boolean isOpen = getBoolean(configName + "总开关", contextKey, false);
    putBoolean(configName + "总开关", contextKey, !isOpen);

    String status = isOpen ? "已关闭" : "已开启";
    String symbol = isOpen ? "❌" : "✅";

    String msg = symbol + contextType + "随机音乐功能" + status;

    toast(msg);
}

public boolean isMusicEnabled(String contextKey) {
    return getBoolean(configName + "总开关", contextKey, false);
}

public void xiaoyu520(String groupUin, String uin, int chatType) {
    String contextKey = (chatType == 2) ? groupUin : uin;
    String contextType = (chatType == 2) ? "群聊" : "私聊";

    boolean isCommentOpen = getBoolean(configName + "热评开关", contextKey, true);
    putBoolean(configName + "热评开关", contextKey, !isCommentOpen);

    String status = isCommentOpen ? "已关闭" : "已开启";
    String symbol = isCommentOpen ? "❌" : "✅";

    String msg = symbol + contextType + "热评功能" + status;

    toast(msg);
}

public void haifeng520(String groupUin, String uin, int chatType) {
    if (chatType == 2) {
        if (getBoolean(haifengConfigName, groupUin, false)) {
            putBoolean(haifengConfigName, groupUin, false);
            toast("已关闭本群点歌");
        } else {
            putBoolean(haifengConfigName, groupUin, true);
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

public void lengbai520(String groupUin, String uin, int chatType) {
    if (chatType == 2) {
        if (getBoolean(lyricConfigName, groupUin, false)) {
            putBoolean(lyricConfigName, groupUin, false);
            toast("已关闭本群点歌歌词");
        } else {
            putBoolean(lyricConfigName, groupUin, true);
            toast("已开启本群点歌歌词");
        }
    } else if (chatType == 1) {
        String targetUin = uin;
        if (getBoolean(privateLyricConfigName, targetUin, false)) {
            putBoolean(privateLyricConfigName, targetUin, false);
            toast("已关闭私聊点歌歌词");
        } else {
            putBoolean(privateLyricConfigName, targetUin, true);
            toast("已开启私聊点歌歌词");
        }
    }
}

public boolean isMusicOpen(String groupUin) {
    return getBoolean(haifengConfigName, groupUin, false);
}

public boolean isPrivateMusicOpen(String uin) {
    return getBoolean(privateConfigName, uin, false);
}

public boolean isLyricOpen(String groupUin) {
    return getBoolean(lyricConfigName, groupUin, false);
}

public boolean isPrivateLyricOpen(String uin) {
    return getBoolean(privateLyricConfigName, uin, false);
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

String getRedirectUrl(String originalUrl) {
    try {
        URL url = new URL(originalUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || 
            responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
            String location = conn.getHeaderField("Location");
            if (location != null && !location.isEmpty()) {
                return location;
            }
        }

        return originalUrl;
    } catch (Exception e) {
        error(e);
        return originalUrl;
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

void onMsg(Object msg) {
    if (msg.IsChannel) return;

    String groupUin = msg.GroupUin;
    String userUin = msg.UserUin;
    String content = msg.MessageContent.trim();
    String peerUin = msg.PeerUin;
    boolean isGroup = msg.IsGroup;

    if (content.equals("随机音乐")) {
        handleNetEaseMusic(msg, groupUin, userUin, content);
    }

    if (content.startsWith("QQ点歌")) {
        if (isGroup) {
            if (!isMusicOpen(groupUin)) {
                return;
            }
        } else {
            String targetUin = peerUin;
            if (!isPrivateMusicOpen(targetUin)) {
                return;
            }
        }

        String songName = content.substring(4).trim();
        if (songName.isEmpty()) {
            if (isGroup) {
                sendMsg(groupUin, "", "请输入歌名");
            } else {
                sendMsg("", peerUin, "请输入歌名");
            }
            return;
        }

        new Thread(() -> {
            try {
                String url = "https://hb.ley.wang/qq.php?word=" + URLEncoder.encode(songName, "UTF-8") + "&n=1";
                String response = httpGet(url);

                if (response == null || response.trim().isEmpty()) {
                    if (isGroup) {
                        sendMsg(groupUin, "", "点歌失败，请稍后重试");
                    } else {
                        sendMsg("", peerUin, "点歌失败，请稍后重试");
                    }
                    return;
                }

                JSONObject json = new JSONObject(response);
                if (json.getInt("code") != 200) {
                    if (isGroup) {
                        sendMsg(groupUin, "", "点歌失败，请稍后重试");
                    } else {
                        sendMsg("", peerUin, "点歌失败，请稍后重试");
                    }
                    return;
                }

                String title = json.getString("title");
                String singer = json.getString("singer");
                String coverUrl = json.getString("cover");
                String musicUrl = json.getString("music_url");
                String lyric = json.optString("lyric", "");

                String randomText = "";
                if (randomTexts.size() > 0) {
                    Random rand = new Random();
                    randomText = "\n文案：" + randomTexts.get(rand.nextInt(randomTexts.size()));
                }

                String musicInfo = "歌曲：" + title + "\n歌手：" + singer + randomText;

                if (isGroup) {
                    String mode = getString(modeConfigName, groupUin, "voice");
                    if (mode.equals("card")) {
                        boolean success = sendMusicCard(groupUin, title, singer, coverUrl, musicUrl, true);
                        if (!success) {
                            sendMsg(groupUin, "", musicInfo);
                            String musicPath = cacheDirPath + System.currentTimeMillis() + ".mp3";
                            httpDownload(musicUrl, musicPath);
                            sendVoice(groupUin, "", musicPath);
                            new java.io.File(musicPath).delete();
                        } else {
                            sendMsg(groupUin, "", "[PicUrl=" + coverUrl + "]" + musicInfo);
                        }
                    } else {
                        sendMsg(groupUin, "", "[PicUrl=" + coverUrl + "]" + musicInfo);
                        String musicPath = cacheDirPath + System.currentTimeMillis() + ".mp3";
                        httpDownload(musicUrl, musicPath);
                        sendVoice(groupUin, "", musicPath);
                        new java.io.File(musicPath).delete();
                    }
                    
                    if (isLyricOpen(groupUin) && !lyric.trim().isEmpty()) {
                        String cleanLyric = lyric.replace("\r\n", "\n");
                        sendMsg(groupUin, "", "歌词：\n" + cleanLyric);
                    }
                } else {
                    String targetUin = peerUin;
                    String mode = getString(privateModeConfigName, targetUin, "voice");
                    if (mode.equals("card")) {
                        boolean success = sendMusicCard(targetUin, title, singer, coverUrl, musicUrl, false);
                        if (!success) {
                            sendMsg("", targetUin, musicInfo);
                            String musicPath = cacheDirPath + System.currentTimeMillis() + ".mp3";
                            httpDownload(musicUrl, musicPath);
                            sendVoice("", targetUin, musicPath);
                            new java.io.File(musicPath).delete();
                        } else {
                            sendMsg("", targetUin, "[PicUrl=" + coverUrl + "]" + musicInfo);
                        }
                    } else {
                        sendMsg("", targetUin, "[PicUrl=" + coverUrl + "]" + musicInfo);
                        String musicPath = cacheDirPath + System.currentTimeMillis() + ".mp3";
                        httpDownload(musicUrl, musicPath);
                        sendVoice("", targetUin, musicPath);
                        new java.io.File(musicPath).delete();
                    }
                    
                    if (isPrivateLyricOpen(targetUin) && !lyric.trim().isEmpty()) {
                        String cleanLyric = lyric.replace("\r\n", "\n");
                        sendMsg("", targetUin, "歌词：\n" + cleanLyric);
                    }
                }
            } catch (Exception e) {
                error(e);
                if (isGroup) {
                    sendMsg(groupUin, "", "点歌失败，请稍后重试");
                } else {
                    sendMsg("", peerUin, "点歌失败，请稍后重试");
                }
            }
        }).start();
    }
}

void handleNetEaseMusic(Object msg, String groupUin, String userUin, String content) {
    String contextKey;
    if (msg.IsGroup) {
        contextKey = groupUin;
    } else {
        contextKey = getCurrentFriendUin();
        if (contextKey == null || contextKey.isEmpty()) {
            contextKey = userUin;
        }
    }

    if (!isMusicEnabled(contextKey)) {
        return; 
    }

    long currentTime = System.currentTimeMillis();
    Object lastSendTimeObj = neteaseCooldownMap.get(contextKey);
    Long lastSendTime = null;

    if (lastSendTimeObj != null) {
        lastSendTime = (Long) lastSendTimeObj;
    }

    if (lastSendTime != null && (currentTime - lastSendTime) < cooldownTime) {
        String remainingTime = String.format("%.1f", (cooldownTime - (currentTime - lastSendTime)) / 1000.0);
        toast("⏳ 冷却中，请等待 " + remainingTime + " 秒后再试");
        return;
    }

    boolean isCardMode = getBoolean(configName + "卡片模式", contextKey, false);
    boolean isCommentOpen = getBoolean(configName + "热评开关", contextKey, true);

    try {
        Object lastSongIdObj = lastNetEaseSongIdMap.get(contextKey);
        String lastSongId = lastSongIdObj != null ? (String) lastSongIdObj : "";


        String normalApiUrl = "http://qs.java.xrvi.top/wyy?t=" + System.currentTimeMillis();
        String normalResponse = httpGet(normalApiUrl);

        if (normalResponse == null || normalResponse.isEmpty()) {
            toast("🎵 获取音乐失败: 网络请求无响应");
            return;
        }

        JSONObject normalJson = new JSONObject(normalResponse);

        if (normalJson.getInt("code") != 1) {
            toast("🎵 获取音乐失败: " + normalJson.getString("message"));
            return;
        }

        JSONObject normalData = normalJson.getJSONObject("data");
        String songId = normalData.getString("id");
        String songName = normalData.getString("song");
        String singerName = normalData.getString("singer");
        String coverUrl = normalData.getString("cover");
        String commentContent = normalData.getString("content");
        JSONObject author = normalData.getJSONObject("author");
        String authorName = author.getString("nick");
        String authorAvatar = author.getString("avatarUrl");

        String originalUrl = normalData.getString("url");
        String audioUrl = getRedirectUrl(originalUrl);

        if (songId.equals(lastSongId)) {
            toast("🎵 暂时没有新歌曲，请稍后再试");
            return;
        }

        if (isCardMode) {

            String cardApiUrl = "http://qs.java.xrvi.top/wyy/?ka=1&song_id=" + songId + "&t=" + System.currentTimeMillis();
            String cardResponse = httpGet(cardApiUrl);

            boolean cardSuccess = false;

            if (cardResponse != null && !cardResponse.isEmpty()) {
                try {
                    JSONObject cardJson = new JSONObject(cardResponse);
                    if (cardJson.getInt("code") == 1) {
                        JSONObject cardData = cardJson.getJSONObject("data");

                        String cardJsonStr = cardData.toString();
                        if (msg.IsGroup) {
                            sendCard(groupUin, "", cardJsonStr);
                        } else {
                            sendCard("", contextKey, cardJsonStr);
                        }

                        if (isCommentOpen) {
                            String commentMsg = "💬 热评: " + commentContent + "\n" +
                                              "👤 评论者: " + authorName;

                            if (msg.IsGroup) {
                                sendMsg(groupUin, "", commentMsg);
                            } else {
                                sendMsg("", contextKey, commentMsg);
                            }
                        }
                        cardSuccess = true;
                    }
                } catch (Exception e) {
                    error(e);
                    toast("❌ 卡片数据解析失败，使用普通模式");
                    sendNormalMusic(msg, groupUin, contextKey, normalData, audioUrl, isCommentOpen);
                    cardSuccess = true;
                }
            } else {
                toast("❌ 卡片模式请求失败，使用普通模式");
                sendNormalMusic(msg, groupUin, contextKey, normalData, audioUrl, isCommentOpen);
                cardSuccess = true;
            }

            if (cardSuccess) {
                neteaseCooldownMap.put(contextKey, currentTime);
                lastNetEaseSongIdMap.put(contextKey, songId);
                return;
            }
        }

        sendNormalMusic(msg, groupUin, contextKey, normalData, audioUrl, isCommentOpen);

        neteaseCooldownMap.put(contextKey, currentTime);
        lastNetEaseSongIdMap.put(contextKey, songId);

    } catch (Exception e) {
        error(e);
        toast("❌ 获取音乐时出现错误: " + e.getMessage());
    }
}

void sendNormalMusic(Object msg, String groupUin, String targetUin, JSONObject data, String audioUrl, boolean isCommentOpen) {
    try {
        String songName = data.getString("song");
        String singerName = data.getString("singer");
        String coverUrl = data.getString("cover");
        String commentContent = data.getString("content");
        JSONObject author = data.getJSONObject("author");
        String authorName = author.getString("nick");

        String infoMsg = "🎶 歌曲: " + songName + "\n" +
                        "🎤 歌手: " + singerName + "\n";

        if (isCommentOpen) {
            infoMsg += "💬 热评: " + commentContent + "\n" +
                      "👤 评论者: " + authorName + "\n";
        }

        infoMsg += "🖼️ [PicUrl=" + coverUrl + "]";

        if (msg.IsGroup) {
            sendMsg(groupUin, "", infoMsg);
            sendVoice(groupUin, "", audioUrl);
        } else {
            sendMsg("", targetUin, infoMsg);
            sendVoice("", targetUin, audioUrl);
        }
    } catch (Exception e) {
        error(e);
        toast("❌ 发送音乐信息时出现错误");
    }
}