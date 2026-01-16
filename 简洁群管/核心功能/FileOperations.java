
// 你总以为机会无限 所以从不珍惜眼前人 山与山不见面 再见容易再见难

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public synchronized void 简写(File ff, String a) {
    FileWriter f = null;
    BufferedWriter f1 = null;
    try {
        f = new FileWriter(ff, true);
        f1 = new BufferedWriter(f);
        f1.append(a);
        f1.newLine();
    } catch (Exception e) {
    } finally {
        try {
            if (f1 != null) f1.close();
            if (f != null) f.close();
        } catch (Exception e) {}
    }
}

public synchronized ArrayList 简取(File ff) {
    if(!ff.exists()){
        return new ArrayList(); 
    }
    FileReader fr = null;
    BufferedReader f2 = null;
    ArrayList list1 = new ArrayList();
    try {
        fr = new FileReader(ff);  
        f2 = new BufferedReader(fr);
        String a;
        while ((a = f2.readLine()) != null) {
            if (a != null && !a.trim().isEmpty()) {
                list1.add(a.trim());
            }
        }
    } catch (Exception e) {
    } finally {
        try {
            if (fr != null) fr.close();
            if (f2 != null) f2.close();
        } catch (Exception e) {}
    }
    return list1;
}

public boolean jiandu(String a,ArrayList l1){
    if (a == null || l1 == null) return false;
    boolean x=false;
    ArrayList l1Copy = safeCopyList(l1);
    for(int i=0;i<l1Copy.size();i++){
        Object item = l1Copy.get(i);
        if(item != null && a.equals(item.toString())){
            x=true; break;
        }
    }
    return x;
}

public synchronized void 全弃(File ff) {
    FileWriter f = null;
    BufferedWriter f1 = null;
    try {
        f = new FileWriter(ff);
        f1 = new BufferedWriter(f);
        f1.write("");
    } catch (Exception e) {
    } finally {
        try {
            if (f1 != null) f1.close();
            if (f != null) f.close();
        } catch (Exception e) {}
    }
}

public int 度(String a){
    if (a == null) return 0;
    return a.length();
}

public synchronized void 简弃(File ff,String a) {
    if (a == null) return;
    ArrayList l1= new ArrayList();
    try {
        l1.addAll(简取(ff));
        if(l1.contains(a)){
            l1.remove(a);
        }
        FileWriter f = new FileWriter(ff);
        BufferedWriter f1 = new BufferedWriter(f);
        f1.write("");
        f1.close();
        f.close();
        
        for(int i=0;i<l1.size();i++){
            Object item = l1.get(i);
            if (item != null) {
                简写(ff,item.toString());
            }
        }
    } catch (Exception e) {
    }
}

public File 获取代管文件() {
    String 代管目录 = appPath + "/代管列表/";
    File 代管文件夹 = new File(代管目录);
    if (!代管文件夹.exists()) {
        代管文件夹.mkdirs();
    }
    File 代管文件 = new File(代管目录, "代管.txt");
    return 代管文件;
}

public File 创建代管文件() {
    File 代管文件 = 获取代管文件();
    if (!代管文件.exists()) {
        try {
            代管文件.createNewFile();
        } catch (Exception e) {}
    }
    return 代管文件;
}

public boolean 是代管(String groupUin, String userUin) {
    if (userUin == null) return false;
    if (userUin.equals(myUin)) return true;
    
    try {
        File 代管文件 = 获取代管文件();
        if (!代管文件.exists()) {
            return false;
        }
        ArrayList 代管列表 = 简取(代管文件);
        return 代管列表.contains(userUin);
    } catch (Exception e) {
        return false;
    }
}

public boolean 有权限操作(String groupUin, String operatorUin, String targetUin) {
    if (operatorUin == null || targetUin == null) return false;
    if (operatorUin.equals(myUin)) {
        return true;
    }
    if (是代管(groupUin, operatorUin)) {
        if (targetUin.equals(myUin)) {
            return false;
        }
        if (是代管(groupUin, targetUin)) {
            return false;
        }
        return true;
    }
    return false;
}

public boolean 检查代管保护(String groupUin, String targetUin, String operation) {
    if (是代管(groupUin, targetUin)) {
        sendMsg(groupUin, "", "检测到QQ号 " + targetUin + " 为代管，无法被" + operation);
        return true;
    }
    return false;
}

public File 获取黑名单文件(String 群号) {
    if (群号 == null || 群号.isEmpty()) {
        return null;
    }
    File 文件 = new File(退群拉黑目录 + 群号 + ".txt");
    if (!文件.exists()) {
        try {
            文件.createNewFile();
        } catch (Exception e) {
        }
    }
    return 文件;
}

public void 添加黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        return;
    }
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 == null) return;
    try {
        ArrayList 当前名单 = 简取(黑名单文件);
        if (!当前名单.contains(QQ号)) {
            简写(黑名单文件, QQ号);
        }
    } catch (Exception e) {}
}

public void 移除黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        return;
    }
    File 黑名单文件 = 获取黑名单文件(群号);
    if (黑名单文件 != null && 黑名单文件.exists()) {
        try {
            简弃(黑名单文件, QQ号);
        } catch (Exception e) {}
    }
}

public boolean 检查黑名单(String 群号, String QQ号) {
    if (群号 == null || 群号.isEmpty()) {
        return false;
    }
    try {
        File 黑名单文件 = 获取黑名单文件(群号);
        if (黑名单文件 == null || !黑名单文件.exists()) return false;
        ArrayList 名单 = 简取(黑名单文件);
        return 名单.contains(QQ号);
    } catch (Exception e) {
        return false;
    }
}

public ArrayList 获取黑名单列表(String 群号) {
    if (群号 == null || 群号.isEmpty()) {
        return new ArrayList();
    }
    try {
        return 简取(获取黑名单文件(群号));
    } catch (Exception e) {
        return new ArrayList();
    }
}