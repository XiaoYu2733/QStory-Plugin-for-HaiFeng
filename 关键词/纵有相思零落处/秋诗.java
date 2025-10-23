
// 计划了很多事 其实根本没有那一天
// 你是我心里最特别的人 也是我最不想失去的人

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

// 无法见面的日子 你要照顾好自己 我很想你

public class QiuShi {
    private static ConcurrentHashMap<String, Object> fileLocks = new ConcurrentHashMap<>();
    
    private static Object getFileLock(String filePath) {
        return fileLocks.computeIfAbsent(filePath, k -> new Object());
    }
    
    public static void writeFile(String filePath, String content) {
        Object lock = getFileLock(filePath);
        synchronized (lock) {
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
                bufferedWriter.flush();
                bufferedWriter.close();
                fileWriter.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }   
    
    public static String readFile(String filePath) {
        Object lock = getFileLock(filePath);
        synchronized (lock) {
            StringBuilder contentBuilder = new StringBuilder();
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    return "{}";
                }
                FileReader fileReader = new FileReader(file);  
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    contentBuilder.append(line);
                }
                bufferedReader.close();
                fileReader.close();
            } catch(Exception e) {
                return "{}";
            }
            String result = contentBuilder.toString();
            return result.isEmpty() ? "{}" : result;
        }
    }
}

// 我那么喜欢你 你要是丢下我 我会哭的