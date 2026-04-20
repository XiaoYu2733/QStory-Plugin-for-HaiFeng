
// 我受过伤，所以更渴望美丽的飞翔

void runFriendFireQueue(final Object[] list, final int index) {
    if (index >= list.length) {
        Toasts("好友续火任务已全部完成");
        return;
    }
    
    try {
        String friendQQ = (String) list[index];
        int randomIndex = (int)(Math.random() * friendFireWords.size());
        String fireWord = (String) friendFireWords.get(randomIndex);
        sendMsg("", friendQQ, fireWord);
        
        int randomDelay = 5000 + (int)(Math.random() * 25001);
        mainHandler.postDelayed(new Runnable() {
            public void run() {
                runFriendFireQueue(list, index + 1);
            }
        }, randomDelay);
    } catch (Exception e) {
        int randomDelay = 5000 + (int)(Math.random() * 25001);
        mainHandler.postDelayed(new Runnable() {
            public void run() {
                runFriendFireQueue(list, index + 1);
            }
        }, randomDelay);
    }
}

void runGroupFireQueue(final Object[] list, final int index) {
    if (index >= list.length) {
        Toasts("群组续火任务已全部完成");
        return;
    }
    
    try {
        String groupUin = (String) list[index];
        int randomIndex = (int)(Math.random() * groupFireWords.size());
        String fireWord = (String) groupFireWords.get(randomIndex);
        sendMsg(groupUin, "", fireWord);
        
        int randomDelay = 5000 + (int)(Math.random() * 25001);
        mainHandler.postDelayed(new Runnable() {
            public void run() {
                runGroupFireQueue(list, index + 1);
            }
        }, randomDelay);
    } catch (Exception e) {
        int randomDelay = 5000 + (int)(Math.random() * 25001);
        mainHandler.postDelayed(new Runnable() {
            public void run() {
                runGroupFireQueue(list, index + 1);
            }
        }, randomDelay);
    }
}

void executeLikeTask() {
    if (likeFriendList.isEmpty()) return;
    new Thread(new Runnable(){
        public void run(){
            Object[] list = likeFriendList.toArray();
            for(int i = 0; i < list.length; i++){
                try{
                    sendLike((String) list[i], 20);
                }catch(Exception e){}
            }
            Toasts("点赞任务完成");
        }
    }).start();
}

void executeFriendFireTask(){
    if (fireFriendList.isEmpty() || friendFireWords.isEmpty()) return;
    Object[] list = fireFriendList.toArray();
    runFriendFireQueue(list, 0);
}

void executeGroupFireTask(){
    if (fireGroupList.isEmpty() || groupFireWords.isEmpty()) return;
    Object[] list = fireGroupList.toArray();
    runGroupFireQueue(list, 0);
}

public void executeAllTasks() {
    if (likeFriendList.isEmpty() && fireFriendList.isEmpty() && fireGroupList.isEmpty()) {
        Toasts("未配置任何任务");
        return;
    }

    if (!likeFriendList.isEmpty()) executeLikeTask();
    if (!fireFriendList.isEmpty()) executeFriendFireTask();
    if (!fireGroupList.isEmpty()) executeGroupFireTask();
}

sendLike("2133115301",20);