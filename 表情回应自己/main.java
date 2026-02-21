
// 我待你和别人不同，但你始终不懂

import java.util.ArrayList;
import java.util.Random;

int totalEmojiCount = 200; // 别动这里
int randomCount = 10; // 随机个数 QQ表情上限最高26个 酌情修改 不是所有人都喜欢这样

void onMsg(Object msg) {
    if (msg.IsSend && msg.IsGroup) {
        ArrayList<String> emojiList = new ArrayList<>();
        for (int i = 1; i <= totalEmojiCount; i++) {
            emojiList.add(String.valueOf(i));
        }
        
        ArrayList<String> selectedEmojis = new ArrayList<>();
        Random rand = new Random();
        
        for (int i = 0; i < randomCount; i++) {
            if (emojiList.isEmpty()) break;
            int index = rand.nextInt(emojiList.size());
            selectedEmojis.add(emojiList.remove(index));
        }
        
        for (String emojiId : selectedEmojis) {
            replyEmoji(msg, emojiId);
        }
    }
}

// 你能看见这句话就说明你不简单😁