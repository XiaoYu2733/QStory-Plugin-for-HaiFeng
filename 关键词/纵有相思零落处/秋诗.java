
// 计划了很多事 其实根本没有那一天

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class QiuShi {

    private static ConcurrentHashMap<String, Object> fileLocks = new ConcurrentHashMap<>();
    
    private static Object getFileLock(String filePath) {
        return fileLocks.computeIfAbsent(filePath, k -> new Object());
    }
    
    public static void writeFile(String filePath, String content) {
        Object lock = getFileLock(filePath);
        synchronized (lock) {
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    file.createNewFile();
                }
                fileWriter = new FileWriter(file);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
                bufferedWriter.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (bufferedWriter != null)
                       bufferedWriter.close();
                    if (fileWriter != null)
                       fileWriter.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }   
    
    public static String readFile(String filePath) {
        Object lock = getFileLock(filePath);
        synchronized (lock) {
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            StringBuilder contentBuilder = new StringBuilder();
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    file.createNewFile();
                    writeFile(file.getAbsolutePath(), "{}");
                }
                String line;
                fileReader = new FileReader(file);  
                bufferedReader = new BufferedReader(fileReader);            
                while ((line = bufferedReader.readLine()) != null) {
                    contentBuilder.append(line);
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null)
                       bufferedReader.close();
                    if (fileReader != null)
                       fileReader.close();
                } catch(Exception e2) {
                    e2.printStackTrace();
                }
            }
            return contentBuilder.toString();
        }
    }
}

// 我那么喜欢你 你要是丢下我 我会哭的