
public class DaytimeTask implements Runnable {
    private String qun;
    private List list;
    public TimerTask timerTask;
    public DaytimeTask(String GroupUin,List list1) {
        this.qun = GroupUin;
        this.list = list1;
        timerTask = new TimerTask() {
            public void run() {
                //天亮时间结束 开始投票时间

                DataList.ReplacementTime(TimeType.voting,qun);
            }
        };        
    }
    public void run() {
        //天亮时间开始
        //啥也不做
    }
}