
//任务类 放了黑夜开始和结束的执行程序
public class VoteTask implements Runnable {
    private String qun;
    private List list;
    public TimerTask timerTask;
    public VoteTask(String GroupUin,List list1) {
        this.qun = GroupUin;
        this.list = list1;
        timerTask = new TimerTask() {
            public void run() {
                //计算投票
                String vote = DataList.CountingVotes(qun,TimeType.daytime);
                sendMsg(qun,"",vote);
                if (DataList.isEnd(qun)[0]) 
                    DataList.ReplacementTime(TimeType.night,qun);
                else {
                    DataList.gameOver(qun,DataList.isEnd(qun)[1]);
                }
            }
        };        
    }
    public void run() {
        for (Object obj :  (List)ongoingGroup.get(qun).GameInformationList ) {
            //put可投票名单 条件 存活
            if (obj.getSurvivalState()) {
                list.add(obj.getUin());
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("通知\n-------我是分割线qwq--------\n可以开始投票了 允许投票的玩家如下\n");
        for(String str : list) {
            sb.append(at(str))
            .append("(")
            .append(getQQ(str))
            .append(")");
            sb.append("\n");
        }
        sb.append("发送 投@ 即可投票");
        sendMsg(qun,"",sb.toString());
        DataList.allowVoteNameList.put(qun,list);
    }    
}