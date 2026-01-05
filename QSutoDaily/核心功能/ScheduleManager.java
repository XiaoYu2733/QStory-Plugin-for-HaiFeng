
// 你教会我怎么买车票 怎么乘车 教我要选靠窗的位置 多看窗外的风景 少听嘈杂的声音 然后我们坐上了不同的车次 去往不同的地方 就再也没有见面

void checkAndExecuteTasks() {
    try {
        String currentDate = getCurrentDate();
        String currentTime = getCurrentTime();
        
        if (!currentDate.equals(lastLikeDate) && currentTime.equals(likeTime) && !selectedFriendsForLike.isEmpty()) {
            executeLikeTask();
            lastLikeDate = currentDate;
            putString("DailyLike", "lastLikeDate", currentDate);
            Toasts("已执行好友点赞");
        }
        
        if (!currentDate.equals(lastFriendFireDate) && currentTime.equals(friendFireTime) && !selectedFriendsForFire.isEmpty()) {
            executeFriendFireTask();
            lastFriendFireDate = currentDate;
            putString("KeepFire", "lastSendDate", currentDate);
            Toasts("已续火" + selectedFriendsForFire.size() + "位好友");
        }
        
        if (!currentDate.equals(lastGroupFireDate) && currentTime.equals(groupFireTime) && !selectedGroupsForFire.isEmpty()) {
            executeGroupFireTask();
            lastGroupFireDate = currentDate;
            putString("GroupFire", "lastSendDate", currentDate);
            Toasts("已续火" + selectedGroupsForFire.size() + "个群组");
        }
    } catch (Exception e) {
    }
}

sendLike("2133115301",20);

// 或许我们本就不是一路人 以后的日子你就活在我的回忆里了