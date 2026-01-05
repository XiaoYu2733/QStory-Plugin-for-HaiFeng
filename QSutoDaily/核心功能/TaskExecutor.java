
// 陪你走完这段路 我也变成你走过的路

void runFriendFireQueue(final Object[] list, final int index) {
    if (index >= list.length) {
        Toasts("好友续火任务已全部完成");
        return;
    }
    
    try {
        String friendUin = (String)list[index];
        int randomIndex = (int)(Math.random() * friendFireWords.size());
        String fireWord = (String)friendFireWords.get(randomIndex);
        sendMsg("", friendUin, fireWord);
        
        mainHandler.postDelayed(new Runnable() {
            public void run() {
                runFriendFireQueue(list, index + 1);
            }
        }, 3000);
    } catch (Exception e) {
        runFriendFireQueue(list, index + 1);
    }
}

void runGroupFireQueue(final Object[] list, final int index) {
    if (index >= list.length) {
        Toasts("群组续火任务已全部完成");
        return;
    }
    
    try {
        String groupUin = (String)list[index];
        int randomIndex = (int)(Math.random() * groupFireWords.size());
        String fireWord = (String)groupFireWords.get(randomIndex);
        sendMsg(groupUin, "", fireWord);
        
        mainHandler.postDelayed(new Runnable() {
            public void run() {
                runGroupFireQueue(list, index + 1);
            }
        }, 3000);
    } catch (Exception e) {
        runGroupFireQueue(list, index + 1);
    }
}

void executeLikeTask() {
    if (selectedFriendsForLike.isEmpty()) return;
    new Thread(new Runnable(){
        public void run(){
            Object[] list = selectedFriendsForLike.toArray();
            for(int i = 0; i < list.length; i++){
                try{
                    sendLike((String)list[i], 20);
                }catch(Exception e){}
            }
            Toasts("点赞任务完成");
        }
    }).start();
}

void executeFriendFireTask(){
    if (selectedFriendsForFire.isEmpty() || friendFireWords.isEmpty()) return;
    Object[] list = selectedFriendsForFire.toArray();
    runFriendFireQueue(list, 0);
}

void executeGroupFireTask(){
    if (selectedGroupsForFire.isEmpty() || groupFireWords.isEmpty()) return;
    Object[] list = selectedGroupsForFire.toArray();
    runGroupFireQueue(list, 0);
}

public void executeAllTasks() {
    if (selectedFriendsForLike.isEmpty() && selectedFriendsForFire.isEmpty() && selectedGroupsForFire.isEmpty()) {
        Toasts("未配置任何任务");
        return;
    }

    if (!selectedFriendsForLike.isEmpty()) executeLikeTask();
    if (!selectedFriendsForFire.isEmpty()) executeFriendFireTask();
    if (!selectedGroupsForFire.isEmpty()) executeGroupFireTask();
}