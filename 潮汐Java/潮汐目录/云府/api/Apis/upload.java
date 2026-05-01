/*
由卑微萌新(QQ779412117)开发，使用请保留版权

QQ上传文件接口

群
sendFile(String qun,String filepath); 参数: 群号,文件路径

好友
sendFriendFile(String qq,String filepath); 参数: QQ号,文件路径

*/

import java.lang.*;
import com.tencent.mobileqq.filemanager.app.FileManagerEngine;
import com.tencent.common.app.BaseApplicationImpl;

FileManagerEngine file=new FileManagerEngine(BaseApplicationImpl.sApplication.getAppRuntime(myUin));

public void upload(String qun,String qq,String filepath)
{
if(qun==null||qun.equals(""))  sendFriendFile(qq,filepath);
else if(qun.contains("&")) sendGuildFile(qun.replaceAll(".*&(.*)","$1"),filepath);
else if(qq==null||qq.equals("")) sendFile(qun, filepath);
else sendSiLiaoFile(qun,qq,filepath);
}

public Object sendFile(String qun,String filepath)
{
    return file.a(filepath,qun,1,1);
}

public void sendGuildFile(String id,String filepath)
{
     file.e(filepath,id);
}

public Object sendFriendFile(String qq,String filepath)
{
return file.a(filepath, qq,qq,0,true);
}

public Object sendSiLiaoFile(String qun,String qq,String filepath)
{
return file.a(filepath, quncode(qun),qq,1000,true);
}

public String quncode(String str) {
        for(Object group:getGroupList()) {
if(str.equals(group.GroupUin)) return group.sourceInfo.troopcode;
 }
    return str;
    }

