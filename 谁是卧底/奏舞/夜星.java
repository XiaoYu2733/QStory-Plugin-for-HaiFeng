
//任务类 放了黑夜开始和结束的执行程序
public class NightTask implements Runnable {
    private String qun;
    private List list;
    public TimerTask timerTask;
    public NightTask(String GroupUin,List list1) {
        this.qun = GroupUin;
        this.list = list1;
        timerTask = new TimerTask() {
            public void run() { 
                ongoingGroup.get(qun).IsNight = false;
                for (String uin : list) {
                    sendMsg(qun,uin,"黑夜时间已结束");
                }
                forbidden(qun,"",0);
                String vote = DataList.CountingVotes(qun,TimeType.night);
                sendMsg(qun,"","天亮了 "+vote);
                if (DataList.isEnd(qun)[0]) {
                    DataList.ReplacementTime(TimeType.daytime,qun);
                } else {
                    DataList.gameOver(qun,DataList.isEnd(qun)[1]);
                }
            }
        };        
    }
    public void run() {
        sendMsg(qun,"","黑夜已降临");
        ongoingGroup.get(qun).IsNight = true;
        for (Object obj :  (List)ongoingGroup.get(qun).GameInformationList ) {
            if (obj.name.equals("侦探") && obj.survivalState) {
                ongoingGroup.get(qun).IsDetectiveTime[1]=true;
                sendMsg(qun,obj.uin,"你是侦探 已经到了黑夜 可以开始投票了 发送 查看+QQ号(如：看2376738)即可获取对方身份");
            }
            //是卧底且存活
            else if (obj.name.equals("卧底") && obj.survivalState) {
                forbidden(qun,"",1);
                list.add(obj.uin);
                sendMsg(qun,obj.uin,"你是卧底 已经到了黑夜 可以开始投票了 发送 投+QQ号(如：投2376738)即可投票");
            }
        }
        DataList.allowVoteNameList.put(qun,list);
    }    
}