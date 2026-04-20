
// 你要问我哪天最开心 我会说和你在一起的每一天

void checkAndExecuteTasks() {
    try {
        String currentDate = getCurrentDate();
        String currentTime = getCurrentTime();
        
        if (!currentDate.equals(lastLikeDate) && currentTime.equals(likeTime) && !likeFriendList.isEmpty()) {
            executeLikeTask();
            lastLikeDate = currentDate;
            putString("DailyLike", "lastLikeDate", currentDate);
            Toasts("已执行好友点赞");
        }
        
        if (!currentDate.equals(lastFireFriendDate) && currentTime.equals(fireFriendTime) && !fireFriendList.isEmpty()) {
            executeFriendFireTask();
            lastFireFriendDate = currentDate;
            putString("KeepFire", "lastSendDate", currentDate);
            Toasts("已续火" + fireFriendList.size() + "位好友");
        }
        
        if (!currentDate.equals(lastFireGroupDate) && currentTime.equals(fireGroupTime) && !fireGroupList.isEmpty()) {
            executeGroupFireTask();
            lastFireGroupDate = currentDate;
            putString("GroupFire", "lastSendDate", currentDate);
            Toasts("已续火" + fireGroupList.size() + "个群组");
        }
    } catch (Exception e) {
    }
}