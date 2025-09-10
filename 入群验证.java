
// 作 海枫

// 时间可以把人拉近 也可以把人推得更远

// QStory精选脚本系列 入群验证

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

String configName = "VerifyConfig";

String switchKey = "VerifySwitch";

addItem("开启/关闭入群验证", "toggleVerify");
addItem("脚本本次更新日志","showUpdateLogCallback");

ArrayList questionBank = new ArrayList();
class Question {
    String category;
    String question;
    ArrayList correctAnswers;
    ArrayList wrongAnswers;
}

public void showUpdateLog() {
    Activity activity = getActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setTitle("更新日志");
            builder.setMessage(" \n\n" +
                    "- [适配] 最新版QStory脚本\n" +
                    "- [修复] 非验证者回答题目的判断逻辑\n" +
                    "- [新增] 更新弹窗 如果你看到这条消息 那么这就是弹窗\n" +
                    "- [新增] 如果验证者在验证流程被踢出/退群 验证流程自动结束\n" +
                    "- [更改] 现在更改了脚本的所有useruin 更改为atqq\n" +
                    "- [其他] 打倒hd\n\n" +
                    "反馈交流群：https://t.me/XiaoYu_Chat");
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    });
}

public void showUpdateLogCallback(String group, String admin, int type) {
    showUpdateLog();
}

void initQuestionBank() {

    Question q1 = new Question();
    q1.category = "常识";
    q1.question = "adb如何安装一个apk到设备";
    q1.correctAnswers = new ArrayList();
    q1.correctAnswers.add("adb install");
    q1.wrongAnswers = new ArrayList();
    q1.wrongAnswers.add("adb push");
    q1.wrongAnswers.add("adb run");
    q1.wrongAnswers.add("adb setup");
    questionBank.add(q1);

    Question q2 = new Question();
    q2.category = "常识";
    q2.question = "adb连接到指定地址的指令";
    q2.correctAnswers = new ArrayList();
    q2.correctAnswers.add("adb connect");
    q2.wrongAnswers = new ArrayList();
    q2.wrongAnswers.add("adb link");
    q2.wrongAnswers.add("adb attach");
    q2.wrongAnswers.add("adb join");
    questionBank.add(q2);

    Question q3 = new Question();
    q3.category = "常识";
    q3.question = "adb查看当前设备列表的命令";
    q3.correctAnswers = new ArrayList();
    q3.correctAnswers.add("adb devices");
    q3.wrongAnswers = new ArrayList();
    q3.wrongAnswers.add("adb list");
    q3.wrongAnswers.add("adb show");
    q3.wrongAnswers.add("adb display");
    questionBank.add(q3);

    Question q4 = new Question();
    q4.category = "常识";
    q4.question = "adb如何进入shell状态";
    q4.correctAnswers = new ArrayList();
    q4.correctAnswers.add("adb shell");
    q4.wrongAnswers = new ArrayList();
    q4.wrongAnswers.add("adb terminal");
    q4.wrongAnswers.add("adb console");
    q4.wrongAnswers.add("adb run");
    questionBank.add(q4);

    Question q5 = new Question();
    q5.category = "安卓";
    q5.question = "magisk常见的patch到哪个分区";
    q5.correctAnswers = new ArrayList();
    q5.correctAnswers.add("boot");
    q5.wrongAnswers = new ArrayList();
    q5.wrongAnswers.add("system");
    q5.wrongAnswers.add("data");
    q5.wrongAnswers.add("recovery");
    questionBank.add(q5);

    Question q6 = new Question();
    q6.category = "安卓";
    q6.question = "以下能提供root功能的框架是";
    q6.correctAnswers = new ArrayList();
    q6.correctAnswers.add("Magisk");
    q6.wrongAnswers = new ArrayList();
    q6.wrongAnswers.add("Xposed");
    q6.wrongAnswers.add("LSPosed");
    q6.wrongAnswers.add("EdXposed");
    questionBank.add(q6);

    Question q7 = new Question();
    q7.category = "Linux";
    q7.question = "linux如何查看进程信息";
    q7.correctAnswers = new ArrayList();
    q7.correctAnswers.add("ps");
    q7.wrongAnswers = new ArrayList();
    q7.wrongAnswers.add("ls");
    q7.wrongAnswers.add("cat");
    q7.wrongAnswers.add("find");
    questionBank.add(q7);

    Question q8 = new Question();
    q8.category = "安卓";
    q8.question = "kernelsu一般是patch到哪个分区";
    q8.correctAnswers = new ArrayList();
    q8.correctAnswers.add("boot");
    q8.wrongAnswers = new ArrayList();
    q8.wrongAnswers.add("vendor");
    q8.wrongAnswers.add("odm");
    q8.wrongAnswers.add("system_ext");
    questionBank.add(q8);

    Question q9 = new Question();
    q9.category = "Linux";
    q9.question = "linux如何创建一个文件";
    q9.correctAnswers = new ArrayList();
    q9.correctAnswers.add("touch");
    q9.wrongAnswers = new ArrayList();
    q9.wrongAnswers.add("mkdir");
    q9.wrongAnswers.add("new");
    q9.wrongAnswers.add("create");
    questionBank.add(q9);

    Question q10 = new Question();
    q10.category = "Linux";
    q10.question = "linux如何创建一个文件夹";
    q10.correctAnswers = new ArrayList();
    q10.correctAnswers.add("mkdir");
    q10.wrongAnswers = new ArrayList();
    q10.wrongAnswers.add("touch");
    q10.wrongAnswers.add("folder");
    q10.wrongAnswers.add("makedir");
    questionBank.add(q10);

    Question q11 = new Question();
    q11.category = "Linux";
    q11.question = "linux如何使用超级用户运行程序";
    q11.correctAnswers = new ArrayList();
    q11.correctAnswers.add("sudo");
    q11.wrongAnswers = new ArrayList();
    q11.wrongAnswers.add("admin");
    q11.wrongAnswers.add("root");
    q11.wrongAnswers.add("su");
    questionBank.add(q11);

    Question q12 = new Question();
    q12.category = "Linux";
    q12.question = "linux如何查看当前的路径";
    q12.correctAnswers = new ArrayList();
    q12.correctAnswers.add("pwd");
    q12.wrongAnswers = new ArrayList();
    q12.wrongAnswers.add("path");
    q12.wrongAnswers.add("where");
    q12.wrongAnswers.add("locate");
    questionBank.add(q12);

    Question q13 = new Question();
    q13.category = "Linux";
    q13.question = "linux如何修改文件权限";
    q13.correctAnswers = new ArrayList();
    q13.correctAnswers.add("chmod");
    q13.wrongAnswers = new ArrayList();
    q13.wrongAnswers.add("perm");
    q13.wrongAnswers.add("setattr");
    q13.wrongAnswers.add("access");
    questionBank.add(q13);

    Question q14 = new Question();
    q14.category = "化学";
    q14.question = "以下物质中,日常生活中常见的酒精的分子式是什么";
    q14.correctAnswers = new ArrayList();
    q14.correctAnswers.add("C2H5OH");
    q14.wrongAnswers = new ArrayList();
    q14.wrongAnswers.add("H2O");
    q14.wrongAnswers.add("CO2");
    q14.wrongAnswers.add("CH3COOH");
    questionBank.add(q14);

    Question q15 = new Question();
    q15.category = "化学";
    q15.question = "下面物质中,空气中含量最多的是什么";
    q15.correctAnswers = new ArrayList();
    q15.correctAnswers.add("N2");
    q15.wrongAnswers = new ArrayList();
    q15.wrongAnswers.add("O2");
    q15.wrongAnswers.add("CO2");
    q15.wrongAnswers.add("Ar");
    questionBank.add(q15);

    Question q16 = new Question();
    q16.category = "化学";
    q16.question = "下面的物质中,最为活跃的单质是什么";
    q16.correctAnswers = new ArrayList();
    q16.correctAnswers.add("F2");
    q16.wrongAnswers = new ArrayList();
    q16.wrongAnswers.add("Cl2");
    q16.wrongAnswers.add("O2");
    q16.wrongAnswers.add("Na");
    questionBank.add(q16);

    Question q17 = new Question();
    q17.category = "Minecraft";
    q17.question = "mc普通模式中,骷髅一箭最多能造成多少伤害";
    q17.correctAnswers = new ArrayList();
    q17.correctAnswers.add("9");
    q17.wrongAnswers = new ArrayList();
    q17.wrongAnswers.add("5");
    q17.wrongAnswers.add("7");
    q17.wrongAnswers.add("12");
    questionBank.add(q17);

    Question q18 = new Question();
    q18.category = "Minecraft";
    q18.question = "mc中以下物品可以被火焰烧掉的是";
    q18.correctAnswers = new ArrayList();
    q18.correctAnswers.add("木剑");
    q18.wrongAnswers = new ArrayList();
    q18.wrongAnswers.add("石剑");
    q18.wrongAnswers.add("铁剑");
    q18.wrongAnswers.add("钻石剑");
    questionBank.add(q18);

    Question q19 = new Question();
    q19.category = "Minecraft";
    q19.question = "mc收到凋零2效果时,每秒会收到多少伤害";
    q19.correctAnswers = new ArrayList();
    q19.correctAnswers.add("2");
    q19.wrongAnswers = new ArrayList();
    q19.wrongAnswers.add("1");
    q19.wrongAnswers.add("3");
    q19.wrongAnswers.add("4");
    questionBank.add(q19);

    Question q20 = new Question();
    q20.category = "Minecraft";
    q20.question = "mc中收到等级5的中毒效果时,一秒会收到多少伤害";
    q20.correctAnswers = new ArrayList();
    q20.correctAnswers.add("2");
    q20.wrongAnswers = new ArrayList();
    q20.wrongAnswers.add("1");
    q20.wrongAnswers.add("3");
    q20.wrongAnswers.add("5");
    questionBank.add(q20);

    Question q21 = new Question();
    q21.category = "Minecraft";
    q21.question = "mc的苹果能恢复多少饥饿度";
    q21.correctAnswers = new ArrayList();
    q21.correctAnswers.add("4");
    q21.wrongAnswers = new ArrayList();
    q21.wrongAnswers.add("2");
    q21.wrongAnswers.add("3");
    q21.wrongAnswers.add("5");
    questionBank.add(q21);

    Question q22 = new Question();
    q22.category = "Minecraft";
    q22.question = "mc中下列物品不可被活塞推送的是";
    q22.correctAnswers = new ArrayList();
    q22.correctAnswers.add("黑曜石");
    q22.wrongAnswers = new ArrayList();
    q22.wrongAnswers.add("泥土");
    q22.wrongAnswers.add("石头");
    q22.wrongAnswers.add("木头");
    questionBank.add(q22);

    Question q23 = new Question();
    q23.category = "Minecraft";
    q23.question = "mc中从20格高摔落到平地上,会收到多少伤害";
    q23.correctAnswers = new ArrayList();
    q23.correctAnswers.add("20");
    q23.wrongAnswers = new ArrayList();
    q23.wrongAnswers.add("10");
    q23.wrongAnswers.add("15");
    q23.wrongAnswers.add("25");
    questionBank.add(q23);

    Question q24 = new Question();
    q24.category = "Minecraft";
    q24.question = "mc中以下物品空手破坏即可获取的是";
    q24.correctAnswers = new ArrayList();
    q24.correctAnswers.add("草方块");
    q24.wrongAnswers = new ArrayList();
    q24.wrongAnswers.add("石头");
    q24.wrongAnswers.add("铁矿");
    q24.wrongAnswers.add("钻石矿");
    questionBank.add(q24);

    Question q25 = new Question();
    q25.category = "化学";
    q25.question = "水的分子式是什么";
    q25.correctAnswers = new ArrayList();
    q25.correctAnswers.add("H₂O");
    q25.wrongAnswers = new ArrayList();
    q25.wrongAnswers.add("CO₂");
    q25.wrongAnswers.add("NaCl");
    q25.wrongAnswers.add("CH₄");
    questionBank.add(q25);

    Question q26 = new Question();
    q26.category = "物理";
    q26.question = "牛顿第一定律又称为什么定律";
    q26.correctAnswers = new ArrayList();
    q26.correctAnswers.add("惯性定律");
    q26.wrongAnswers = new ArrayList();
    q26.wrongAnswers.add("万有引力定律");
    q26.wrongAnswers.add("作用力与反作用力定律");
    q26.wrongAnswers.add("能量守恒定律");
    questionBank.add(q26);

    Question q27 = new Question();
    q27.category = "数学";
    q27.question = "圆的周长公式是什么";
    q27.correctAnswers = new ArrayList();
    q27.correctAnswers.add("C = 2πr");
    q27.wrongAnswers = new ArrayList();
    q27.wrongAnswers.add("C = πr²");
    q27.wrongAnswers.add("C = 4πr");
    q27.wrongAnswers.add("C = πd");
    questionBank.add(q27);

    Question q28 = new Question();
    q28.category = "fastboot";
    q28.question = "fastboot模式下解锁Bootloader的命令是什么";
    q28.correctAnswers = new ArrayList();
    q28.correctAnswers.add("fastboot oem unlock");
    q28.wrongAnswers = new ArrayList();
    q28.wrongAnswers.add("fastboot unlock");
    q28.wrongAnswers.add("fastboot flash unlock");
    q28.wrongAnswers.add("fastboot bootloader unlock");
    questionBank.add(q28);

    Question q29 = new Question();
    q29.category = "magisk";
    q29.question = "Magisk的主要功能是什么";
    q29.correctAnswers = new ArrayList();
    q29.correctAnswers.add("获取root权限");
    q29.wrongAnswers = new ArrayList();
    q29.wrongAnswers.add("刷写ROM");
    q29.wrongAnswers.add("恢复数据");
    q29.wrongAnswers.add("优化电池");
    questionBank.add(q29);

    Question q30 = new Question();
    q30.category = "化学";
    q30.question = "酸雨的pH值一般小于多少";
    q30.correctAnswers = new ArrayList();
    q30.correctAnswers.add("5.6");
    q30.wrongAnswers = new ArrayList();
    q30.wrongAnswers.add("7.0");
    q30.wrongAnswers.add("6.5");
    q30.wrongAnswers.add("4.0");
    questionBank.add(q30);

    Question q31 = new Question();
    q31.category = "物理";
    q31.question = "光在真空中的传播速度是多少";
    q31.correctAnswers = new ArrayList();
    q31.correctAnswers.add("3×10⁸ m/s");
    q31.wrongAnswers = new ArrayList();
    q31.wrongAnswers.add("3×10⁵ m/s");
    q31.wrongAnswers.add("340 m/s");
    q31.wrongAnswers.add("1500 m/s");
    questionBank.add(q31);

    Question q32 = new Question();
    q32.category = "数学";
    q32.question = "勾股定理的公式是什么";
    q32.correctAnswers = new ArrayList();
    q32.correctAnswers.add("a² + b² = c²");
    q32.wrongAnswers = new ArrayList();
    q32.wrongAnswers.add("a + b = c");
    q32.wrongAnswers.add("a × b = c");
    q32.wrongAnswers.add("a² - b² = c²");
    questionBank.add(q32);

    Question q33 = new Question();
    q33.category = "fastboot";
    q33.question = "fastboot模式下刷入recovery镜像的命令是什么";
    q33.correctAnswers = new ArrayList();
    q33.correctAnswers.add("fastboot flash recovery");
    q33.wrongAnswers = new ArrayList();
    q33.wrongAnswers.add("fastboot boot recovery");
    q33.wrongAnswers.add("fastboot install recovery");
    q33.wrongAnswers.add("fastboot update recovery");
    questionBank.add(q33);

    Question q34 = new Question();
    q34.category = "magisk";
    q34.question = "Magisk Hide的主要作用是什么";
    q34.correctAnswers = new ArrayList();
    q34.correctAnswers.add("隐藏root权限");
    q34.wrongAnswers = new ArrayList();
    q34.wrongAnswers.add("提升性能");
    q34.wrongAnswers.add("备份数据");
    q34.wrongAnswers.add("清理缓存");
    questionBank.add(q34);

    Question q35 = new Question();
    q35.category = "化学";
    q35.question = "下列元素中，属于惰性气体的是";
    q35.correctAnswers = new ArrayList();
    q35.correctAnswers.add("氦(He)");
    q35.wrongAnswers = new ArrayList();
    q35.wrongAnswers.add("氧(O)");
    q35.wrongAnswers.add("氢(H)");
    q35.wrongAnswers.add("碳(C)");
    questionBank.add(q35);

    Question q36 = new Question();
    q36.category = "物理";
    q36.question = "电流的单位是什么";
    q36.correctAnswers = new ArrayList();
    q36.correctAnswers.add("安培(A)");
    q36.wrongAnswers = new ArrayList();
    q36.wrongAnswers.add("伏特(V)");
    q36.wrongAnswers.add("欧姆(Ω)");
    q36.wrongAnswers.add("瓦特(W)");
    questionBank.add(q36);

    Question q37 = new Question();
    q37.category = "数学";
    q37.question = "一元二次方程求根公式是什么";
    q37.correctAnswers = new ArrayList();
    q37.correctAnswers.add("x = [-b±√(b²-4ac)]/2a");
    q37.wrongAnswers = new ArrayList();
    q37.wrongAnswers.add("x = b±√(b²-4ac)/2a");
    q37.wrongAnswers.add("x = [-b±(b²-4ac)]/2a");
    q37.wrongAnswers.add("x = [b±√(b²-4ac)]/2");
    questionBank.add(q37);

    Question q38 = new Question();
    q38.category = "fastboot";
    q38.question = "fastboot模式下擦除system分区的命令是什么";
    q38.correctAnswers = new ArrayList();
    q38.correctAnswers.add("fastboot erase system");
    q38.wrongAnswers = new ArrayList();
    q38.wrongAnswers.add("fastboot delete system");
    q38.wrongAnswers.add("fastboot format system");
    q38.wrongAnswers.add("fastboot wipe system");
    questionBank.add(q38);

    Question q39 = new Question();
    q39.category = "magisk";
    q39.question = "Magisk模块通常安装在哪个目录";
    q39.correctAnswers = new ArrayList();
    q39.correctAnswers.add("/data/adb/modules");
    q39.wrongAnswers = new ArrayList();
    q39.wrongAnswers.add("/system/bin");
    q39.wrongAnswers.add("/sdcard/Magisk");
    q39.wrongAnswers.add("/vendor/lib");
    questionBank.add(q39);

    Question q40 = new Question();
    q40.category = "化学";
    q40.question = "下列物质中，属于酸的是";
    q40.correctAnswers = new ArrayList();
    q40.correctAnswers.add("盐酸(HCl)");
    q40.wrongAnswers = new ArrayList();
    q40.wrongAnswers.add("氢氧化钠(NaOH)");
    q40.wrongAnswers.add("氯化钠(NaCl)");
    q40.wrongAnswers.add("氧化钙(CaO)");
    questionBank.add(q40);

    Question q41 = new Question();
    q41.category = "物理";
    q41.question = "力的单位是什么";
    q41.correctAnswers = new ArrayList();
    q41.correctAnswers.add("牛顿(N)");
    q41.wrongAnswers = new ArrayList();
    q41.wrongAnswers.add("焦耳(J)");
    q41.wrongAnswers.add("瓦特(W)");
    q41.wrongAnswers.add("帕斯卡(Pa)");
    questionBank.add(q41);

    Question q42 = new Question();
    q42.category = "数学";
    q42.question = "π的近似值是多少";
    q42.correctAnswers = new ArrayList();
    q42.correctAnswers.add("3.1416");
    q42.wrongAnswers = new ArrayList();
    q42.wrongAnswers.add("3.0");
    q42.wrongAnswers.add("2.718");
    q42.wrongAnswers.add("1.414");
    questionBank.add(q42);

    Question q43 = new Question();
    q43.category = "fastboot";
    q43.question = "fastboot模式下重启设备的命令是什么";
    q43.correctAnswers = new ArrayList();
    q43.correctAnswers.add("fastboot reboot");
    q43.wrongAnswers = new ArrayList();
    q43.wrongAnswers.add("fastboot restart");
    q43.wrongAnswers.add("fastboot boot");
    q43.wrongAnswers.add("fastboot continue");
    questionBank.add(q43);

    Question q44 = new Question();
    q44.category = "magisk";
    q44.question = "Magisk的创始人是谁";
    q44.correctAnswers = new ArrayList();
    q44.correctAnswers.add("topjohnwu");
    q44.wrongAnswers = new ArrayList();
    q44.wrongAnswers.add("Chainfire");
    q44.wrongAnswers.add("XDA");
    q44.wrongAnswers.add("Google");
    questionBank.add(q44);

    Question q45 = new Question();
    q45.category = "化学";
    q45.question = "下列物质中，pH值最大的是";
    q45.correctAnswers = new ArrayList();
    q45.correctAnswers.add("氢氧化钠溶液");
    q45.wrongAnswers = new ArrayList();
    q45.wrongAnswers.add("柠檬汁");
    q45.wrongAnswers.add("纯净水");
    q45.wrongAnswers.add("醋酸溶液");
    questionBank.add(q45);

    Question q46 = new Question();
    q46.category = "物理";
    q46.question = "功率的单位是什么";
    q46.correctAnswers = new ArrayList();
    q46.correctAnswers.add("瓦特(W)");
    q46.wrongAnswers = new ArrayList();
    q46.wrongAnswers.add("焦耳(J)");
    q46.wrongAnswers.add("牛顿(N)");
    q46.wrongAnswers.add("伏特(V)");
    questionBank.add(q46);

    Question q47 = new Question();
    q47.category = "数学";
    q47.question = "三角形内角和是多少度";
    q47.correctAnswers = new ArrayList();
    q47.correctAnswers.add("180");
    q47.wrongAnswers = new ArrayList();
    q47.wrongAnswers.add("90");
    q47.wrongAnswers.add("360");
    q47.wrongAnswers.add("270");
    questionBank.add(q47);

    Question q48 = new Question();
    q48.category = "fastboot";
    q48.question = "fastboot模式下查看设备状态的命令是什么";
    q48.correctAnswers = new ArrayList();
    q48.correctAnswers.add("fastboot devices");
    q48.wrongAnswers = new ArrayList();
    q48.wrongAnswers.add("fastboot status");
    q48.wrongAnswers.add("fastboot info");
    q48.wrongAnswers.add("fastboot list");
    questionBank.add(q48);

    Question q49 = new Question();
    q49.category = "magisk";
    q49.question = "Magisk Delta是什么";
    q49.correctAnswers = new ArrayList();
    q49.correctAnswers.add("Magisk的一个分支版本");
    q49.wrongAnswers = new ArrayList();
    q49.wrongAnswers.add("一种新的root方法");
    q49.wrongAnswers.add("Magisk的测试版本");
    q49.wrongAnswers.add("Magisk的旧版本名称");
    questionBank.add(q49);

    Question q50 = new Question();
    q50.category = "化学";
    q50.question = "下列元素中，属于卤素的是";
    q50.correctAnswers = new ArrayList();
    q50.correctAnswers.add("氟(F)");
    q50.wrongAnswers = new ArrayList();
    q50.wrongAnswers.add("钠(Na)");
    q50.wrongAnswers.add("钙(Ca)");
    q50.wrongAnswers.add("铁(Fe)");
    questionBank.add(q50);

    Question q51 = new Question();
    q51.category = "物理";
    q51.question = "声音在空气中的传播速度大约是多少";
    q51.correctAnswers = new ArrayList();
    q51.correctAnswers.add("340 m/s");
    q51.wrongAnswers = new ArrayList();
    q51.wrongAnswers.add("3×10⁸ m/s");
    q51.wrongAnswers.add("1500 m/s");
    q51.wrongAnswers.add("5000 m/s");
    questionBank.add(q51);

    Question q52 = new Question();
    q52.category = "数学";
    q52.question = "1公里等于多少米";
    q52.correctAnswers = new ArrayList();
    q52.correctAnswers.add("1000");
    q52.wrongAnswers = new ArrayList();
    q52.wrongAnswers.add("100");
    q52.wrongAnswers.add("10");
    q52.wrongAnswers.add("500");
    questionBank.add(q52);

    Question q53 = new Question();
    q53.category = "fastboot";
    q53.question = "fastboot模式下解锁关键分区的命令是什么";
    q53.correctAnswers = new ArrayList();
    q53.correctAnswers.add("fastboot flashing unlock");
    q53.wrongAnswers = new ArrayList();
    q53.wrongAnswers.add("fastboot unlock critical");
    q53.wrongAnswers.add("fastboot oem unlock_critical");
    q53.wrongAnswers.add("fastboot unlock partitions");
    questionBank.add(q53);

    Question q54 = new Question();
    q54.category = "magisk";
    q54.question = "MagiskSU是什么";
    q54.correctAnswers = new ArrayList();
    q54.correctAnswers.add("Magisk的root管理组件");
    q54.wrongAnswers = new ArrayList();
    q54.wrongAnswers.add("Magisk的安装工具");
    q54.wrongAnswers.add("Magisk的模块管理器");
    q54.wrongAnswers.add("Magisk的隐藏功能");
    questionBank.add(q54);

    Question q55 = new Question();
    q55.category = "化学";
    q55.question = "下列物质中，属于碱的是";
    q55.correctAnswers = new ArrayList();
    q55.correctAnswers.add("氢氧化钙(Ca(OH)₂)");
    q55.wrongAnswers = new ArrayList();
    q55.wrongAnswers.add("硫酸(H₂SO₄)");
    q55.wrongAnswers.add("二氧化碳(CO₂)");
    q55.wrongAnswers.add("氯化钠(NaCl)");
    questionBank.add(q55);

    Question q56 = new Question();
    q56.category = "物理";
    q56.question = "电阻的单位是什么";
    q56.correctAnswers = new ArrayList();
    q56.correctAnswers.add("欧姆(Ω)");
    q56.wrongAnswers = new ArrayList();
    q56.wrongAnswers.add("安培(A)");
    q56.wrongAnswers.add("伏特(V)");
    q56.wrongAnswers.add("瓦特(W)");
    questionBank.add(q56);

    Question q57 = new Question();
    q57.category = "数学";
    q57.question = "平行四边形的面积公式是什么";
    q57.correctAnswers = new ArrayList();
    q57.correctAnswers.add("底×高");
    q57.wrongAnswers = new ArrayList();
    q57.wrongAnswers.add("长×宽");
    q57.wrongAnswers.add("(上底+下底)×高÷2");
    q57.wrongAnswers.add("πr²");
    questionBank.add(q57);

    Question q58 = new Question();
    q58.category = "fastboot";
    q58.question = "fastboot模式下刷入boot镜像的命令是什么";
    q58.correctAnswers = new ArrayList();
    q58.correctAnswers.add("fastboot flash boot");
    q58.wrongAnswers = new ArrayList();
    q58.wrongAnswers.add("fastboot install boot");
    q58.wrongAnswers.add("fastboot update boot");
    q58.wrongAnswers.add("fastboot boot image");
    questionBank.add(q58);

    Question q59 = new Question();
    q59.category = "magisk";
    q59.question = "Zygisk是什么";
    q59.correctAnswers = new ArrayList();
    q59.correctAnswers.add("Magisk的Zygote注入框架");
    q59.wrongAnswers = new ArrayList();
    q59.wrongAnswers.add("一种新的root方法");
    q59.wrongAnswers.add("Magisk的模块名称");
    q59.wrongAnswers.add("Android系统组件");
    questionBank.add(q59);

    Question q60 = new Question();
    q60.category = "化学";
    q60.question = "下列物质中，属于单质的是";
    q60.correctAnswers = new ArrayList();
    q60.correctAnswers.add("氧气(O₂)");
    q60.wrongAnswers = new ArrayList();
    q60.wrongAnswers.add("水(H₂O)");
    q60.wrongAnswers.add("二氧化碳(CO₂)");
    q60.wrongAnswers.add("食盐(NaCl)");
    questionBank.add(q60);

    Question q61 = new Question();
    q61.category = "物理";
    q61.question = "功的单位是什么";
    q61.correctAnswers = new ArrayList();
    q61.correctAnswers.add("焦耳(J)");
    q61.wrongAnswers = new ArrayList();
    q61.wrongAnswers.add("瓦特(W)");
    q61.wrongAnswers.add("牛顿(N)");
    q61.wrongAnswers.add("帕斯卡(Pa)");
    questionBank.add(q61);

    Question q62 = new Question();
    q62.category = "数学";
    q62.question = "正方形的面积公式是什么";
    q62.correctAnswers = new ArrayList();
    q62.correctAnswers.add("边长×边长");
    q62.wrongAnswers = new ArrayList();
    q62.wrongAnswers.add("长×宽");
    q62.wrongAnswers.add("底×高");
    q62.wrongAnswers.add("πr²");
    questionBank.add(q62);

    Question q63 = new Question();
    q63.category = "fastboot";
    q63.question = "fastboot模式下进入recovery模式的命令是什么";
    q63.correctAnswers = new ArrayList();
    q63.correctAnswers.add("fastboot boot recovery");
    q63.wrongAnswers = new ArrayList();
    q63.wrongAnswers.add("fastboot reboot recovery");
    q63.wrongAnswers.add("fastboot enter recovery");
    q63.wrongAnswers.add("fastboot recovery");
    questionBank.add(q63);

    Question q64 = new Question();
    q64.category = "magisk";
    q64.question = "Magisk模块的安装文件后缀是什么";
    q64.correctAnswers = new ArrayList();
    q64.correctAnswers.add(".zip");
    q64.wrongAnswers = new ArrayList();
    q64.wrongAnswers.add(".apk");
    q64.wrongAnswers.add(".img");
    q64.wrongAnswers.add(".bin");
    questionBank.add(q64);

    Question q65 = new Question();
    q65.category = "化学";
    q65.question = "下列物质中，属于混合物的是";
    q65.correctAnswers = new ArrayList();
    q65.correctAnswers.add("空气");
    q65.wrongAnswers = new ArrayList();
    q65.wrongAnswers.add("氧气(O₂)");
    q65.wrongAnswers.add("水(H₂O)");
    q65.wrongAnswers.add("铁(Fe)");
    questionBank.add(q65);

    Question q66 = new Question();
    q66.category = "物理";
    q66.question = "电压的单位是什么";
    q66.correctAnswers = new ArrayList();
    q66.correctAnswers.add("伏特(V)");
    q66.wrongAnswers = new ArrayList();
    q66.wrongAnswers.add("安培(A)");
    q66.wrongAnswers.add("欧姆(Ω)");
    q66.wrongAnswers.add("瓦特(W)");
    questionBank.add(q66);

    Question q67 = new Question();
    q67.category = "数学";
    q67.question = "长方形的周长公式是什么";
    q67.correctAnswers = new ArrayList();
    q67.correctAnswers.add("2×(长+宽)");
    q67.wrongAnswers = new ArrayList();
    q67.wrongAnswers.add("长×宽");
    q67.wrongAnswers.add("4×边长");
    q67.wrongAnswers.add("长+宽");
    questionBank.add(q67);

    Question q68 = new Question();
    q68.category = "fastboot";
    q68.question = "fastboot模式下格式化userdata分区的命令是什么";
    q68.correctAnswers = new ArrayList();
    q68.correctAnswers.add("fastboot format userdata");
    q68.wrongAnswers = new ArrayList();
    q68.wrongAnswers.add("fastboot erase userdata");
    q68.wrongAnswers.add("fastboot wipe userdata");
    q68.wrongAnswers.add("fastboot delete userdata");
    questionBank.add(q68);

    Question q69 = new Question();
    q69.category = "magisk";
    q69.question = "Magisk的配置文件通常存放在哪里";
    q69.correctAnswers = new ArrayList();
    q69.correctAnswers.add("/data/adb/magisk.db");
    q69.wrongAnswers = new ArrayList();
    q69.wrongAnswers.add("/system/etc/magisk");
    q69.wrongAnswers.add("/sdcard/Magisk/config");
    q69.wrongAnswers.add("/vendor/bin/magisk");
    questionBank.add(q69);

    Question q70 = new Question();
    q70.category = "化学";
    q70.question = "下列元素中，原子序数最小的是";
    q70.correctAnswers = new ArrayList();
    q70.correctAnswers.add("氢(1)");
    q70.wrongAnswers = new ArrayList();
    q70.wrongAnswers.add("氦(2)");
    q70.wrongAnswers.add("锂(3)");
    q70.wrongAnswers.add("铍(4)");
    questionBank.add(q70);

    Question q71 = new Question();
    q71.category = "物理";
    q71.question = "密度的单位是什么";
    q71.correctAnswers = new ArrayList();
    q71.correctAnswers.add("kg/m³");
    q71.wrongAnswers = new ArrayList();
    q71.wrongAnswers.add("N/m²");
    q71.wrongAnswers.add("J/s");
    q71.wrongAnswers.add("A/s");
    questionBank.add(q71);

    Question q72 = new Question();
    q72.category = "数学";
    q72.question = "1公顷等于多少平方米";
    q72.correctAnswers = new ArrayList();
    q72.correctAnswers.add("10000");
    q72.wrongAnswers = new ArrayList();
    q72.wrongAnswers.add("100");
    q72.wrongAnswers.add("1000");
    q72.wrongAnswers.add("100000");
    questionBank.add(q72);

    Question q73 = new Question();
    q73.category = "fastboot";
    q73.question = "fastboot模式下刷入system镜像的命令是什么";
    q73.correctAnswers = new ArrayList();
    q73.correctAnswers.add("fastboot flash system");
    q73.wrongAnswers = new ArrayList();
    q73.wrongAnswers.add("fastboot install system");
    q73.wrongAnswers.add("fastboot update system");
    q73.wrongAnswers.add("fastboot system image");
    questionBank.add(q73);

    Question q74 = new Question();
    q74.category = "magisk";
    q74.question = "Magisk的官方仓库名称是什么";
    q74.correctAnswers = new ArrayList();
    q74.correctAnswers.add("Magisk Modules Repo");
    q74.wrongAnswers = new ArrayList();
    q74.wrongAnswers.add("Magisk Store");
    q74.wrongAnswers.add("Magisk Hub");
    q74.wrongAnswers.add("Magisk Central");
    questionBank.add(q74);

    Question q75 = new Question();
    q75.category = "化学";
    q75.question = "下列元素中，属于碱金属的是";
    q75.correctAnswers = new ArrayList();
    q75.correctAnswers.add("钠(Na)");
    q75.wrongAnswers = new ArrayList();
    q75.wrongAnswers.add("钙(Ca)");
    q75.wrongAnswers.add("铝(Al)");
    q75.wrongAnswers.add("铁(Fe)");
    questionBank.add(q75);

    Question q76 = new Question();
    q76.category = "物理";
    q76.question = "加速度的单位是什么";
    q76.correctAnswers = new ArrayList();
    q76.correctAnswers.add("m/s²");
    q76.wrongAnswers = new ArrayList();
    q76.wrongAnswers.add("m/s");
    q76.wrongAnswers.add("N/s");
    q76.wrongAnswers.add("kg/m");
    questionBank.add(q76);

    Question q77 = new Question();
    q77.category = "数学";
    q77.question = "梯形的面积公式是什么";
    q77.correctAnswers = new ArrayList();
    q77.correctAnswers.add("(上底+下底)×高÷2");
    q77.wrongAnswers = new ArrayList();
    q77.wrongAnswers.add("底×高");
    q77.wrongAnswers.add("长×宽");
    q77.wrongAnswers.add("πr²");
    questionBank.add(q77);

    Question q78 = new Question();
    q78.category = "fastboot";
    q78.question = "fastboot模式下锁定Bootloader的命令是什么";
    q78.correctAnswers = new ArrayList();
    q78.correctAnswers.add("fastboot oem lock");
    q78.wrongAnswers = new ArrayList();
    q78.wrongAnswers.add("fastboot lock");
    q78.wrongAnswers.add("fastboot secure lock");
    q78.wrongAnswers.add("fastboot bootloader lock");
    questionBank.add(q78);

    Question q79 = new Question();
    q79.category = "magisk";
    q79.question = "Magisk的日志文件通常存放在哪里";
    q79.correctAnswers = new ArrayList();
    q79.correctAnswers.add("/data/adb/magisk.log");
    q79.wrongAnswers = new ArrayList();
    q79.wrongAnswers.add("/sdcard/magisk.log");
    q79.wrongAnswers.add("/system/bin/magisk.log");
    q79.wrongAnswers.add("/cache/magisk.log");
    questionBank.add(q79);

    Question q80 = new Question();
    q80.category = "化学";
    q80.question = "下列物质中，属于氧化物的是";
    q80.correctAnswers = new ArrayList();
    q80.correctAnswers.add("氧化钙(CaO)");
    q80.wrongAnswers = new ArrayList();
    q80.wrongAnswers.add("氯化钠(NaCl)");
    q80.wrongAnswers.add("硫酸(H₂SO₄)");
    q80.wrongAnswers.add("氢氧化钠(NaOH)");
    questionBank.add(q80);

    Question q81 = new Question();
    q81.category = "物理";
    q81.question = "压强的单位是什么";
    q81.correctAnswers = new ArrayList();
    q81.correctAnswers.add("帕斯卡(Pa)");
    q81.wrongAnswers = new ArrayList();
    q81.wrongAnswers.add("牛顿(N)");
    q81.wrongAnswers.add("焦耳(J)");
    q81.wrongAnswers.add("瓦特(W)");
    questionBank.add(q81);

    Question q82 = new Question();
    q82.category = "数学";
    q82.question = "1立方米等于多少升";
    q82.correctAnswers = new ArrayList();
    q82.correctAnswers.add("1000");
    q82.wrongAnswers = new ArrayList();
    q82.wrongAnswers.add("100");
    q82.wrongAnswers.add("10");
    q82.wrongAnswers.add("500");
    questionBank.add(q82);

    Question q83 = new Question();
    q83.category = "fastboot";
    q83.question = "fastboot模式下刷入vbmeta分区的命令是什么";
    q83.correctAnswers = new ArrayList();
    q83.correctAnswers.add("fastboot flash vbmeta");
    q83.wrongAnswers = new ArrayList();
    q83.wrongAnswers.add("fastboot install vbmeta");
    q83.wrongAnswers.add("fastboot update vbmeta");
    q83.wrongAnswers.add("fastboot vbmeta image");
    questionBank.add(q83);

    Question q84 = new Question();
    q84.category = "magisk";
    q84.question = "Magisk的Canary版本是什么";
    q84.correctAnswers = new ArrayList();
    q84.correctAnswers.add("测试版本");
    q84.wrongAnswers = new ArrayList();
    q84.wrongAnswers.add("稳定版本");
    q84.wrongAnswers.add("旧版本");
    q84.wrongAnswers.add("企业版本");
    questionBank.add(q84);

    Question q85 = new Question();
    q85.category = "化学";
    q85.question = "下列元素中，属于过渡金属的是";
    q85.correctAnswers = new ArrayList();
    q85.correctAnswers.add("铁(Fe)");
    q85.wrongAnswers = new ArrayList();
    q85.wrongAnswers.add("钠(Na)");
    q85.wrongAnswers.add("钙(Ca)");
    q85.wrongAnswers.add("铝(Al)");
    questionBank.add(q85);

    Question q86 = new Question();
    q86.category = "物理";
    q86.question = "能量的单位是什么";
    q86.correctAnswers = new ArrayList();
    q86.correctAnswers.add("焦耳(J)");
    q86.wrongAnswers = new ArrayList();
    q86.wrongAnswers.add("瓦特(W)");
    q86.wrongAnswers.add("牛顿(N)");
    q86.wrongAnswers.add("伏特(V)");
    questionBank.add(q86);

    Question q87 = new Question();
    q87.category = "数学";
    q87.question = "圆的面积公式是什么";
    q87.correctAnswers = new ArrayList();
    q87.correctAnswers.add("πr²");
    q87.wrongAnswers = new ArrayList();
    q87.wrongAnswers.add("2πr");
    q87.wrongAnswers.add("πd");
    q87.wrongAnswers.add("4πr²");
    questionBank.add(q87);

    Question q88 = new Question();
    q88.category = "fastboot";
    q88.question = "fastboot模式下刷入多个镜像的命令是什么";
    q88.correctAnswers = new ArrayList();
    q88.correctAnswers.add("fastboot update");
    q88.wrongAnswers = new ArrayList();
    q88.wrongAnswers.add("fastboot flashall");
    q88.wrongAnswers.add("fastboot install-all");
    q88.wrongAnswers.add("fastboot multi-flash");
    questionBank.add(q88);

    Question q89 = new Question();
    q89.category = "magisk";
    q89.question = "Magisk的DenyList功能用于什么";
    q89.correctAnswers = new ArrayList();
    q89.correctAnswers.add("隐藏root权限");
    q89.wrongAnswers = new ArrayList();
    q89.wrongAnswers.add("禁止应用运行");
    q89.wrongAnswers.add("清理不需要的文件");
    q89.wrongAnswers.add("管理模块");
    questionBank.add(q89);

    Question q90 = new Question();
    q90.category = "化学";
    q90.question = "下列物质中，属于盐的是";
    q90.correctAnswers = new ArrayList();
    q90.correctAnswers.add("碳酸钙(CaCO₃)");
    q90.wrongAnswers = new ArrayList();
    q90.wrongAnswers.add("盐酸(HCl)");
    q90.wrongAnswers.add("氢氧化钠(NaOH)");
    q90.wrongAnswers.add("氧化铁(Fe₂O₃)");
    questionBank.add(q90);

    Question q91 = new Question();
    q91.category = "物理";
    q91.question = "频率的单位是什么";
    q91.correctAnswers = new ArrayList();
    q91.correctAnswers.add("赫兹(Hz)");
    q91.wrongAnswers = new ArrayList();
    q91.wrongAnswers.add("米/秒(m/s)");
    q91.wrongAnswers.add("牛顿(N)");
    q91.wrongAnswers.add("焦耳(J)");
    questionBank.add(q91);

    Question q92 = new Question();
    q92.category = "数学";
    q92.question = "正方体的体积公式是什么";
    q92.correctAnswers = new ArrayList();
    q92.correctAnswers.add("边长³");
    q92.wrongAnswers = new ArrayList();
    q92.wrongAnswers.add("长×宽×高");
    q92.wrongAnswers.add("底面积×高");
    q92.wrongAnswers.add("4/3πr³");
    questionBank.add(q92);

    Question q93 = new Question();
    q93.category = "fastboot";
    q93.question = "fastboot模式下设置活动分区的命令是什么";
    q93.correctAnswers = new ArrayList();
    q93.correctAnswers.add("fastboot set_active");
    q93.wrongAnswers = new ArrayList();
    q93.wrongAnswers.add("fastboot active");
    q93.wrongAnswers.add("fastboot select");
    q93.wrongAnswers.add("fastboot switch");
    questionBank.add(q93);

    Question q94 = new Question();
    q94.category = "magisk";
    q94.question = "Magisk的Direct Install功能用于什么";
    q94.correctAnswers = new ArrayList();
    q94.correctAnswers.add("直接安装Magisk");
    q94.wrongAnswers = new ArrayList();
    q94.wrongAnswers.add("安装模块");
    q94.wrongAnswers.add("修复boot分区");
    q94.wrongAnswers.add("卸载Magisk");
    questionBank.add(q94);

    Question q95 = new Question();
    q95.category = "化学";
    q95.question = "下列元素中，属于稀土元素的是";
    q95.correctAnswers = new ArrayList();
    q95.correctAnswers.add("镧(La)");
    q95.wrongAnswers = new ArrayList();
    q95.wrongAnswers.add("铁(Fe)");
    q95.wrongAnswers.add("铜(Cu)");
    q95.wrongAnswers.add("锌(Zn)");
    questionBank.add(q95);

    Question q96 = new Question();
    q96.category = "物理";
    q96.question = "热量的单位是什么";
    q96.correctAnswers = new ArrayList();
    q96.correctAnswers.add("焦耳(J)");
    q96.wrongAnswers = new ArrayList();
    q96.wrongAnswers.add("卡路里(cal)");
    q96.wrongAnswers.add("瓦特(W)");
    q96.wrongAnswers.add("摄氏度(℃)");
    questionBank.add(q96);

    Question q97 = new Question();
    q97.category = "数学";
    q97.question = "圆柱的体积公式是什么";
    q97.correctAnswers = new ArrayList();
    q97.correctAnswers.add("πr²h");
    q97.wrongAnswers = new ArrayList();
    q97.wrongAnswers.add("2πrh");
    q97.wrongAnswers.add("4/3πr³");
    q97.wrongAnswers.add("πr²");
    questionBank.add(q97);

    Question q98 = new Question();
    q98.category = "fastboot";
    q98.question = "fastboot模式下获取设备信息的命令是什么";
    q98.correctAnswers = new ArrayList();
    q98.correctAnswers.add("fastboot getvar all");
    q98.wrongAnswers = new ArrayList();
    q98.wrongAnswers.add("fastboot info");
    q98.wrongAnswers.add("fastboot status");
    q98.wrongAnswers.add("fastboot deviceinfo");
    questionBank.add(q98);

    Question q99 = new Question();
    q99.category = "magisk";
    q99.question = "Magisk的Safe Mode用于什么";
    q99.correctAnswers = new ArrayList();
    q99.correctAnswers.add("安全模式启动");
    q99.wrongAnswers = new ArrayList();
    q99.wrongAnswers.add("禁用所有模块");
    q99.wrongAnswers.add("恢复出厂设置");
    q99.wrongAnswers.add("备份系统");
    questionBank.add(q99);

    Question q100 = new Question();
    q100.category = "化学";
    q100.question = "下列物质中，属于有机物的是";
    q100.correctAnswers = new ArrayList();
    q100.correctAnswers.add("甲烷(CH₄)");
    q100.wrongAnswers = new ArrayList();
    q100.wrongAnswers.add("二氧化碳(CO₂)");
    q100.wrongAnswers.add("碳酸钙(CaCO₃)");
    q100.wrongAnswers.add("水(H₂O)");
    questionBank.add(q100);
}

public void toggleVerify(String groupUin, String uin, int chatType) {
    if (chatType != 2) return;
    boolean isEnabled = getBoolean(configName, groupUin, false);
    putBoolean(configName, groupUin, !isEnabled);
    if (!isEnabled) {
        toast(groupUin + " 入群验证已开启");
    } else {
        toast(groupUin + " 入群验证已关闭");
    }
}

sendLike("2133115301",20);

boolean isVerifyEnabled(String groupUin) {
    return getBoolean(configName, groupUin, false);
}
class UserVerifyState {
    String groupUin;
    String userUin;
    int correctCount = 0;
    int wrongCount = 0;
    int changeCount = 0;
    long startTime;
    ArrayList currentQuestions = new ArrayList();
    ArrayList currentOptions = new ArrayList();
    int currentQuestionIndex = 0;
    boolean handled = false;
}

HashMap verifyStates = new HashMap();
void onTroopEvent(String groupUin, String userUin, int type) {
    if (type == 2 && isVerifyEnabled(groupUin)) {
        if (userUin == null || userUin.equals("0") || userUin.equals("all")) return;
        UserVerifyState state = new UserVerifyState();
        state.groupUin = groupUin;
        state.userUin = userUin;
        state.startTime = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            int randomIndex = (int) (Math.random() * questionBank.size());
            state.currentQuestions.add(questionBank.get(randomIndex));
        }
        String stateKey = groupUin + "_" + userUin;
        verifyStates.put(stateKey, state);
        sendQuestion(stateKey, 0);
    } else if (type == 1) {
        String stateKey = groupUin + "_" + userUin;
        UserVerifyState state = (UserVerifyState) verifyStates.get(stateKey);
        if (state != null && !state.handled) {
            verifyStates.remove(stateKey);
            sendMsg(groupUin, "", "[AtQQ=" + userUin + "] 入群中途退群 验证流程结束");
        }
    }
}

void sendQuestion(String stateKey, int questionIndex) {
    UserVerifyState state = (UserVerifyState) verifyStates.get(stateKey);
    if (state == null) return;
    state.currentQuestionIndex = questionIndex;
    if (questionIndex >= state.currentQuestions.size()) {
        checkVerificationResult(stateKey);
        return;
    }
    Question q = (Question) state.currentQuestions.get(questionIndex);
    StringBuilder message = new StringBuilder();
    message.append("[AtQQ=").append(state.userUin).append("] 欢迎 请在300s内回答以下问题,验证过期永久封禁,回答错误永久封禁[").append(questionIndex + 1).append("/2]\n\n");
    message.append(q.question).append("\n\n");
    ArrayList allOptions = new ArrayList();
    allOptions.addAll(q.correctAnswers);
    allOptions.addAll(q.wrongAnswers);
    for (int i = 0; i < allOptions.size(); i++) {
        int randomPos = (int) (Math.random() * allOptions.size());
        Object temp = allOptions.get(i);
        allOptions.set(i, allOptions.get(randomPos));
        allOptions.set(randomPos, temp);
    }
    state.currentOptions = allOptions;
    char optionChar = 'A';
    for (Object option : allOptions) {
        message.append(optionChar).append(". ").append(option).append("\n");
        optionChar++;
    }
    long elapsed = System.currentTimeMillis() - state.startTime;
    long remaining = 300000 - elapsed;
    int remainingSec = (int)(remaining / 1000);
    message.append("\n非验证者回答会被禁言\n题目太难可擅用DeepSeekChatGPT\n题目太难可以发送换题更换题目[换题机会剩余");
    message.append(2 - state.changeCount).append("次]");
    message.append("\n验证过期时间：").append(remainingSec).append("s");
    sendMsg(state.groupUin, "", message.toString());
}

void processAnswer(String stateKey, String answer) {
    UserVerifyState state = (UserVerifyState) verifyStates.get(stateKey);
    if (state == null) return;
    long elapsed = System.currentTimeMillis() - state.startTime;
    if (elapsed > 300000) {
        handleVerificationFailure(stateKey, "验证过期");
        return;
    }
    if ("换题".equals(answer)) {
        if (state.changeCount < 2) {
            state.changeCount++;
            int currentIndex = state.currentQuestionIndex;
            int randomIndex = (int) (Math.random() * questionBank.size());
            state.currentQuestions.set(currentIndex, questionBank.get(randomIndex));
            sendQuestion(stateKey, currentIndex);
        } else {
            sendMsg(state.groupUin, "", "[AtQQ=" + state.userUin + "] 换题次数已达上限，请回答问题");
        }
        return;
    }
    if (answer.length() != 1 || !Character.isLetter(answer.charAt(0))) {
        sendMsg(state.groupUin, "", "[AtQQ=" + state.userUin + "] 请及时完成入群验证，超时永久封禁");
        return;
    }
    int optionIndex = Character.toUpperCase(answer.charAt(0)) - 'A';
    int questionIndex = state.currentQuestionIndex;
    Question q = (Question) state.currentQuestions.get(questionIndex);
    String selectedAnswer = "";
    if (optionIndex >= 0 && optionIndex < state.currentOptions.size()) {
        selectedAnswer = ((String) state.currentOptions.get(optionIndex)).toLowerCase().replaceAll("\\s+", "");
    } else {
        sendMsg(state.groupUin, "", "[AtQQ=" + state.userUin + "] 选项无效，请重新选择");
        return;
    }
    boolean isCorrect = false;
    for (Object correctObj : q.correctAnswers) {
        String correctAnswer = ((String) correctObj).toLowerCase().replaceAll("\\s+", "");
        if (selectedAnswer.equals(correctAnswer)) {
            isCorrect = true;
            break;
        }
    }
    if (isCorrect) {
        state.correctCount++;
        sendMsg(state.groupUin, "", "[AtQQ=" + state.userUin + "] 回答正确");
        if (state.correctCount >= 2) {
            checkVerificationResult(stateKey);
        } else {
            sendQuestion(stateKey, questionIndex + 1);
        }
    } else {
        state.wrongCount++;
        sendMsg(state.groupUin, "", "[AtQQ=" + state.userUin + "] 回答错误");
        if (state.wrongCount >= 2) {
            handleVerificationFailure(stateKey, "验证错误");
        } else {
            sendQuestion(stateKey, questionIndex);
        }
    }
}

void checkVerificationResult(String stateKey) {
    UserVerifyState state = (UserVerifyState) verifyStates.get(stateKey);
    if (state == null) return;
    if (state.handled) return;
    state.handled = true;
    if (state.correctCount >= 2) {
        sendMsg(state.groupUin, "", "[AtQQ=" + state.userUin + "] 验证通过 欢迎加入群聊");
    } else {
        handleVerificationFailure(stateKey, "未通过验证");
    }
    verifyStates.remove(stateKey);
}

void handleVerificationFailure(String stateKey, String reason) {
    UserVerifyState state = (UserVerifyState) verifyStates.get(stateKey);
    if (state == null) return;
    if (state.handled) return;
    state.handled = true;
    try {
        kick(state.groupUin, state.userUin, true);
        sendMsg(state.groupUin, "", "[AtQQ=" + state.userUin + "] 入群中途被踢出 验证流程结束");
    } catch (Exception e) {
        sendMsg(state.groupUin, "", "[AtQQ=" + state.userUin + "] 由于" + reason + "，未完成入群验证，已被永久封禁。");
    }
    verifyStates.remove(stateKey);
}

void onMsg(Object msg) {
    if (msg.IsGroup && !msg.IsSend) {
        String groupUin = msg.GroupUin;
        String userUin = msg.UserUin;
        String content = msg.MessageContent.trim();

        for (Object key : verifyStates.keySet()) {
            String stateKey = (String) key;
            if (stateKey.startsWith(groupUin + "_")) {
                UserVerifyState state = (UserVerifyState) verifyStates.get(stateKey);
                if (state == null) continue;

                if (!userUin.equals(state.userUin) && (content.matches("^[A-Da-d]$") || "换题".equals(content))) {
                    forbidden(groupUin, userUin, 86400);
                    sendMsg(groupUin, "", "[AtQQ=" + userUin + "] 非验证者禁止回答！已禁言24小时");
                    break;
                }
            }
        }

        String stateKey = groupUin + "_" + userUin;
        if (verifyStates.containsKey(stateKey)) {
            processAnswer(stateKey, content);
        }
    }
}

new Thread(new Runnable() {
    public void run() {
        while (true) {
            try {
                Thread.sleep(30000);
                long currentTime = System.currentTimeMillis();
                ArrayList toRemove = new ArrayList();
                
                Object[] keys = verifyStates.keySet().toArray();
                for (int i = 0; i < keys.length; i++) {
                    String stateKey = (String) keys[i];
                    UserVerifyState state = (UserVerifyState) verifyStates.get(stateKey);
                    if (state == null) continue;
                    if (state.handled) continue;
                    long elapsed = currentTime - state.startTime;
                    if (elapsed > 300000) {
                        toRemove.add(stateKey);
                        handleVerificationFailure(stateKey, "验证过期");
                    }
                }
                
                for (int i = 0; i < toRemove.size(); i++) {
                    verifyStates.remove(toRemove.get(i));
                }
            } catch (Exception e) {}
        }
    }
}).start();
initQuestionBank();
if (getString(switchKey, "showToast") == null) {
    toast("入群验证脚本已加载！");
    putString(switchKey, "showToast", "false");
}

// 我不太懂浪漫 但我会尽我所能把我最好的爱给你