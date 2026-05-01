
import java.io.*;
import java.text.SimpleDateFormat;
public class Log {
    public static final String Path = appPath + "/Log/";
    public static final File LogPath = new File( Path + "log.txt");
    public static final File ExceptionPath = new File( Path + "ExceptionLog.txt");
    public static String getTime()
    {
        //日期格式
	    SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日"), 
	    df2 = new SimpleDateFormat("E", Locale.CHINA), 
	    df3 = new SimpleDateFormat("HH:mm:ss");
	    //日期实例
	    Calendar calendar = Calendar.getInstance();
	    //日期实例.getTime()为long 再通过格式化字符串
	    String TimeMsg1 = df1.format(calendar.getTime()), 
	    TimeMsg2 = df2.format(calendar.getTime()), 
	    TimeMsg3 = df3.format(calendar.getTime());
	    if (TimeMsg1.contains("年0"))
		    TimeMsg1 = TimeMsg1.replace("年0", "年");
	    if (TimeMsg1.contains("月0"))
		    TimeMsg1 = TimeMsg1.replace("月0", "月");
	    if (TimeMsg2.contains("周"))
		    TimeMsg2 = TimeMsg2.replace("周", "星期");
	    return TimeMsg1 + TimeMsg2 + TimeMsg3;
    }
    public static void r(String str) {
        if (!ExceptionPath.exists()) {
            if (new File(Path).exists())
                new File(Path).mkdirs();
            ExceptionPath.createNewFile();
        }
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        try {
            fileWriter = new FileWriter(LogPath,true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append("["+getTime()+"] " + str);
            bufferedWriter.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (fileWriter != null)
                    fileWriter.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void e(String str) {
        if (!ExceptionPath.exists()) {
            if (new File(Path).exists())
                new File(Path).mkdirs();
            ExceptionPath.createNewFile();
        }
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        try {
            fileWriter = new FileWriter(ExceptionPath,true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append("Exception ["+getTime()+"] \n" + str);
            bufferedWriter.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (fileWriter != null)
                    fileWriter.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}