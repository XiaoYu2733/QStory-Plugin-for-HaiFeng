
// 海枫

// 如果你也害怕失去我就好了

addMenuItem("发送语音", "sendVoiceMenu");

// String voiceUrl替换为自己的音乐链接即可
public void sendVoiceMenu(Object msg) {
    String voiceUrl = "https://multimedia.nt.qq.com.cn/download?appid=1403&fileid=EhTnNL1F-E2HD20tLoDxIxYWcdW6sBiunT8g-woose6Q-NOKkAMyBHByb2RQgPUkWhAzEkyvr2c_81ZYUqB33QDKegI0poIBAm5q&rkey=CAISqAGKPkFJeHAjcitYsknjMrnLAVq7yqoB6sYr4CoBOBOSTbyirHIWCbl1nSGj0Ddn2ZTppF9pxEFLhuN-MOcpdjBHgNJubAs2UHQ0Mczjfb9MhC6EC5VrYnu1u4Z_IZF96rnmWOK5f2s-XYXVe8UdnU3MrAn_iFDdHEsXBzsNKjchWFgH8TFd82_rOY85tMAOaU1OJu0PtCd9qdJPYzTNhIbll7M6y4P7Z9E";
    
    // 我凌晨的心事全是你 你在睡觉 我在想你
    if (msg.IsGroup) {
        sendVoice(msg.GroupUin, "", voiceUrl);
        toast("已发送语音到群聊");
    } else {
        sendVoice("", msg.PeerUin, voiceUrl);
        toast("已发送语音到私聊");
    }
}

// 我始终做不到说走就走

sendLike("2133115301",20);