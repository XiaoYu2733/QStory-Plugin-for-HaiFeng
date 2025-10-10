
// 计划了很多事 其实根本没有那一天

import java.io.*;
import java.lang.StringBuilder;

public class QiuShi {

    private static FileWriter fileWriter = null;
    private static BufferedWriter bufferedWriter = null;
    private static FileReader fileReader = null;
    private static BufferedReader bufferedReader = null;
    
    public static void writeFile(String filePath, String content) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
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
    
    public static String readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder contentBuilder = new StringBuilder();
        try {
            if (!file.exists()) {
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