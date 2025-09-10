// 定时全体禁言 定时全体解禁

// 作 海枫

// QQ交流群：1050252163

// 配置群聊类 群聊需要自己配置 注意 符号请采用英文符号
String[] targetGroups = {"114514","5201314","123456"};
int forbiddenTime = 31536000;

long lastClickTime = 0;

// 定时线程 别动！别动！别动！
new Thread(new Runnable(){
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Calendar now=Calendar.getInstance();
                checkAndExecute(now);
                Thread.sleep(60000);
            }catch(Exception e){
                toast("定时器错误:"+e.getMessage());
            }
        }
    }
    
    void checkAndExecute(Calendar now){
        int currentHour=now.get(Calendar.HOUR_OF_DAY);
        int currentMinute=now.get(Calendar.MINUTE);
        String today=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        
        // 可更改禁言时间 默认23:57
        if(currentHour==23&&currentMinute==57&&!today.equals(getString("ForbiddenTask","lastForbidDate"))){
            executeForbiddenAll();
            putString("ForbiddenTask","lastForbidDate",today);
            toast("已执行定时禁言");
        }
        
        // 可更改解禁时间 默认08:00
        if(currentHour==8&&currentMinute==0&&!today.equals(getString("ForbiddenTask","lastUnforbidDate"))){
            executeUnforbiddenAll();
            putString("ForbiddenTask","lastUnforbidDate",today);
            toast("已执行定时解禁");
        }
    }
}).start();

void executeForbiddenAll(){
    new Thread(new Runnable(){
        public void run(){
            for(String group:targetGroups){
                try{
                    forbidden(group, "", forbiddenTime);
                    Thread.sleep(3000);
                }catch(Exception e){
                    toast(group+"禁言失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

void executeUnforbiddenAll(){
    new Thread(new Runnable(){
        public void run(){
            for(String group:targetGroups){
                try{
                    forbidden(group, "", 0);
                    Thread.sleep(3000);
                }catch(Exception e){
                    toast(group+"解禁失败:"+e.getMessage());
                }
            }
        }
    }).start();
}

addItem("立即全员禁言","forbidNow");
addItem("立即全员解禁","unforbidNow");

public void forbidNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    executeForbiddenAll();
    toast("正在执行全员禁言");
}

public void unforbidNow(String g,String u,int t){
    long currentTime = System.currentTimeMillis();
    if(currentTime - lastClickTime < 60000){
        long remaining = (60000 - (currentTime - lastClickTime)) / 1000;
        toast("冷却中，请"+remaining+"秒后再试");
        return;
    }
    
    lastClickTime = currentTime;
    executeUnforbiddenAll();
    toast("正在执行全员解禁");
}

toast("脚本加载成功 欢迎试用定时禁言解禁重构版！");

// 制作不易 支持一下qwq
sendLike("2133115301",20);