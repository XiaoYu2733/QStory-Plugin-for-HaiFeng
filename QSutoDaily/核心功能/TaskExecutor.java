
// 陪你走完这段路 我也变成你走过的路

void runFriendFireQueue(final Object[] 列表, final int 索引) {
    if (索引 >= 列表.length) {
        Toasts("好友续火任务已全部完成");
        return;
    }
    
    try {
        String 好友QQ = (String)列表[索引];
        int 随机索引 = (int)(Math.random() * 飘飘叶飘.size());
        String 续火语 = (String)飘飘叶飘.get(随机索引);
        sendMsg("", 好友QQ, 续火语);
        
        int 随机延迟 = 5000 + (int)(Math.random() * 25001);
        叶落飘花.postDelayed(new Runnable() {
            public void run() {
                runFriendFireQueue(列表, 索引 + 1);
            }
        }, 随机延迟);
    } catch (Exception e) {
        int 随机延迟 = 5000 + (int)(Math.random() * 25001);
        叶落飘花.postDelayed(new Runnable() {
            public void run() {
                runFriendFireQueue(列表, 索引 + 1);
            }
        }, 随机延迟);
    }
}

void runGroupFireQueue(final Object[] 列表, final int 索引) {
    if (索引 >= 列表.length) {
        Toasts("群组续火任务已全部完成");
        return;
    }
    
    try {
        String 群号 = (String)列表[索引];
        int 随机索引 = (int)(Math.random() * 叶落花落.size());
        String 续火语 = (String)叶落花落.get(随机索引);
        sendMsg(群号, "", 续火语);
        
        int 随机延迟 = 5000 + (int)(Math.random() * 25001);
        叶落飘花.postDelayed(new Runnable() {
            public void run() {
                runGroupFireQueue(列表, 索引 + 1);
            }
        }, 随机延迟);
    } catch (Exception e) {
        int 随机延迟 = 5000 + (int)(Math.random() * 25001);
        叶落飘花.postDelayed(new Runnable() {
            public void run() {
                runGroupFireQueue(列表, 索引 + 1);
            }
        }, 随机延迟);
    }
}

void executeLikeTask() {
    if (落叶叶子叶落子飘.isEmpty()) return;
    new Thread(new Runnable(){
        public void run(){
            Object[] 列表 = 落叶叶子叶落子飘.toArray();
            for(int i = 0; i < 列表.length; i++){
                try{
                    sendLike((String)列表[i], 20);
                }catch(Exception e){}
            }
            Toasts("点赞任务完成");
        }
    }).start();
}

void executeFriendFireTask(){
    if (落言花飘言落言.isEmpty() || 飘飘叶飘.isEmpty()) return;
    Object[] 列表 = 落言花飘言落言.toArray();
    runFriendFireQueue(列表, 0);
}

void executeGroupFireTask(){
    if (飘飘花言飘飘.isEmpty() || 叶落花落.isEmpty()) return;
    Object[] 列表 = 飘飘花言飘飘.toArray();
    runGroupFireQueue(列表, 0);
}

public void executeAllTasks() {
    if (落叶叶子叶落子飘.isEmpty() && 落言花飘言落言.isEmpty() && 飘飘花言飘飘.isEmpty()) {
        Toasts("未配置任何任务");
        return;
    }

    if (!落叶叶子叶落子飘.isEmpty()) executeLikeTask();
    if (!落言花飘言落言.isEmpty()) executeFriendFireTask();
    if (!飘飘花言飘飘.isEmpty()) executeGroupFireTask();
}