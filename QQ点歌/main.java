
// 海枫

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Random;
import java.net.URLEncoder;

String configName = "haifeng";
String modeConfigName = "music_mode";
String privateConfigName = "haifeng_private";
String privateModeConfigName = "music_mode_private";
String lyricConfigName = "show_lyric";
ArrayList<String> randomTexts = new ArrayList<>();
String cacheDirPath = "/storage/emulated/0/Download/QQ点歌/";

addItem("开启/关闭群聊点歌", "haifeng520");
addItem("切换语音/卡片点歌", "xkong520");
addItem("开启/关闭显示歌词", "toggleLyric");

public void toggleLyric(String groupUin, String uin, int chatType) {
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

public void onMsg(Object msg) {
    String text = msg.MessageContent;
    String senderUin = msg.UserUin;
    String group = msg.GroupUin;
    String peerUin = msg.PeerUin;
    boolean isGroup = msg.IsGroup;
    boolean isSend = msg.IsSend;

    if (text.startsWith("QQ点歌")) {
        if (isGroup) {
            if (!isMusicOpen(group)) {
                return;
            }
        } else {
            String targetUin = peerUin;
            if (!isPrivateMusicOpen(targetUin)) {
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

        new Thread(() -> {
            try {
                Random rand = new Random();
                int apiChoice = rand.nextInt(2);
                String response = null;

                if (apiChoice == 0) {
                    String url = "https://hb.ley.wang/qq.php?word=" + URLEncoder.encode(songName, "UTF-8");
                    response = httpGet(url);
                } else {
                    String url = "https://api.iosxx.cn/API/qqmusic.php?name=" + URLEncoder.encode(songName, "UTF-8");
                    response = httpGet(url);
                }

                if (response == null || response.trim().isEmpty()) {
                    if (isGroup) {
                        sendMsg(group, "", "点歌失败，请稍后重试");
                    } else {
                        sendMsg("", peerUin, "点歌失败，请稍后重试");
                    }
                    return;
                }

                JSONObject json = new JSONObject(response);
                String title = "";
                String singer = "";
                String coverUrl = "";
                String musicUrl = "";
                String lyric = "";

                if (apiChoice == 0) {
                    if (json.getInt("code") != 200) {
                        if (isGroup) {
                            sendMsg(group, "", "点歌失败，请稍后重试");
                        } else {
                            sendMsg("", peerUin, "点歌失败，请稍后重试");
                        }
                        return;
                    }
                    title = json.getString("title");
                    singer = json.getString("singer");
                    coverUrl = json.getString("cover");
                    musicUrl = json.getString("music_url");
                    if (json.has("lyric")) {
                        lyric = json.getString("lyric");
                    }
                } else {
                    if (json.getInt("code") != 200) {
                        if (isGroup) {
                            sendMsg(group, "", "点歌失败，请稍后重试");
                        } else {
                            sendMsg("", peerUin, "点歌失败，请稍后重试");
                        }
                        return;
                    }
                    title = json.getString("title");
                    singer = json.getString("singer");
                    coverUrl = json.getString("cover");
                    musicUrl = json.getString("music_url");
                    if (json.has("lyric")) {
                        lyric = json.getString("lyric");
                    }
                }

                String randomText = "";
                if (randomTexts.size() > 0) {
                    randomText = "\n文案：" + randomTexts.get(rand.nextInt(randomTexts.size()));
                }

                String musicInfo = "歌曲：" + title + "\n歌手：" + singer + randomText;

                if (isGroup) {
                    if (isLyricOpen(group) && !lyric.isEmpty()) {
                        String cleanLyric = lyric.replace("\r\n", "\n");
                        sendMsg(group, "", "歌词：\n" + cleanLyric);
                    }
                } else {
                    String targetUin = peerUin;
                    if (isPrivateLyricOpen(targetUin) && !lyric.isEmpty()) {
                        String cleanLyric = lyric.replace("\r\n", "\n");
                        sendMsg("", targetUin, "歌词：\n" + cleanLyric);
                    }
                }

                if (isGroup) {
                    String mode = getString(modeConfigName, group, "voice");
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
                }
            } catch (Exception e) {
                error(e);
                if (isGroup) {
                    sendMsg(group, "", "点歌失败，请稍后重试");
                } else {
                    sendMsg("", peerUin, "点歌失败，请稍后重试");
                }
            }
        }).start();
    }
}

sendLike("2133115301",20);