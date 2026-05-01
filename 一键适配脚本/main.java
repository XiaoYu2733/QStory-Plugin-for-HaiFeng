import java.io.*;
import java.util.*;

{
    String basePath = "/storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/Plugin/";
    File pluginDir = new File(basePath);
    
    //检查目录
    if (!pluginDir.exists() || !pluginDir.isDirectory()) {
        toast("脚本目录不存在: " + basePath);
        return;
    }
    
    File[] allFiles = pluginDir.listFiles();
    if (allFiles == null || allFiles.length == 0) {
        toast("未找到任何脚本文件夹");
        return;
    }
    
    int processedCount = 0;
    int modifiedCount = 0;
    
    for (File item : allFiles) {
        if (!item.isDirectory()) {
            continue;
        }
        
        File infoFile = new File(item, "info.prop");
        
        if (!infoFile.exists() || !infoFile.isFile()) {
            continue;
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(infoFile));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            
            boolean hasDateLine = false;
            boolean hasTagsLine = false;
            
            for (String lineStr : lines) {
                String trimmedLine = lineStr.trim();
                if (trimmedLine.startsWith("date")) {
                    hasDateLine = true;
                }
                if (trimmedLine.startsWith("tags")) {
                    hasTagsLine = true;
                }
            }
            
            List<String> newLines = new ArrayList<>();
            boolean fileModified = false;
            
            for (String lineStr : lines) {
                String trimmedLine = lineStr.trim();
                
                if (trimmedLine.startsWith("time")) {
                    if (hasDateLine) {
                        fileModified = true;
                        continue;
                    } else {
                        //时间可以自己改
                        newLines.add("date = 2025-12-4");
                        fileModified = true;
                        hasDateLine = true;
                        continue;
                    }
                }
                
                newLines.add(lineStr);
            }
            
            //tags可以自己改
            if (!hasTagsLine) {
                newLines.add("tags = 综合脚本");
                fileModified = true;
            }
            
            if (fileModified) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(infoFile));
                for (String newLine : newLines) {
                    writer.write(newLine);
                    writer.newLine();
                }
                writer.close();
                modifiedCount++;
            }
            
            processedCount++;
            
        } catch (IOException e) {
            log("处理文件失败: " + infoFile.getAbsolutePath());
        }
    }
    
    toast("脚本信息更新完成: 共检查 " + processedCount + " 个脚本，修改了 " + modifiedCount + " 个文件");
}