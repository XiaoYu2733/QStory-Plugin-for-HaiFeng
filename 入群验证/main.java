
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

ArrayList questionBank = new ArrayList();

public void showUpdateLogCallback(String group, String admin, int type) {
    showUpdateLog();
}

void initQuestionBank() {
    HashMap q1 = new HashMap();
    q1.put("category", "常识");
    q1.put("question", "adb如何安装一个apk到设备");
    ArrayList correct1 = new ArrayList();
    correct1.add("adb install");
    ArrayList wrong1 = new ArrayList();
    wrong1.add("adb push");
    wrong1.add("adb run");
    wrong1.add("adb setup");
    q1.put("correctAnswers", correct1);
    q1.put("wrongAnswers", wrong1);
    questionBank.add(q1);

    HashMap q2 = new HashMap();
    q2.put("category", "常识");
    q2.put("question", "adb连接到指定地址的指令");
    ArrayList correct2 = new ArrayList();
    correct2.add("adb connect");
    ArrayList wrong2 = new ArrayList();
    wrong2.add("adb link");
    wrong2.add("adb attach");
    wrong2.add("adb join");
    q2.put("correctAnswers", correct2);
    q2.put("wrongAnswers", wrong2);
    questionBank.add(q2);

    HashMap q3 = new HashMap();
    q3.put("category", "常识");
    q3.put("question", "adb查看当前设备列表的命令");
    ArrayList correct3 = new ArrayList();
    correct3.add("adb devices");
    ArrayList wrong3 = new ArrayList();
    wrong3.add("adb list");
    wrong3.add("adb show");
    wrong3.add("adb display");
    q3.put("correctAnswers", correct3);
    q3.put("wrongAnswers", wrong3);
    questionBank.add(q3);

    HashMap q4 = new HashMap();
    q4.put("category", "常识");
    q4.put("question", "adb如何进入shell状态");
    ArrayList correct4 = new ArrayList();
    correct4.add("adb shell");
    ArrayList wrong4 = new ArrayList();
    wrong4.add("adb terminal");
    wrong4.add("adb console");
    wrong4.add("adb run");
    q4.put("correctAnswers", correct4);
    q4.put("wrongAnswers", wrong4);
    questionBank.add(q4);

    HashMap q5 = new HashMap();
    q5.put("category", "安卓");
    q5.put("question", "magisk常见的patch到哪个分区");
    ArrayList correct5 = new ArrayList();
    correct5.add("boot");
    ArrayList wrong5 = new ArrayList();
    wrong5.add("system");
    wrong5.add("data");
    wrong5.add("recovery");
    q5.put("correctAnswers", correct5);
    q5.put("wrongAnswers", wrong5);
    questionBank.add(q5);

    HashMap q6 = new HashMap();
    q6.put("category", "安卓");
    q6.put("question", "以下能提供root功能的框架是");
    ArrayList correct6 = new ArrayList();
    correct6.add("Magisk");
    ArrayList wrong6 = new ArrayList();
    wrong6.add("Xposed");
    wrong6.add("LSPosed");
    wrong6.add("EdXposed");
    q6.put("correctAnswers", correct6);
    q6.put("wrongAnswers", wrong6);
    questionBank.add(q6);

    HashMap q7 = new HashMap();
    q7.put("category", "Linux");
    q7.put("question", "linux如何查看进程信息");
    ArrayList correct7 = new ArrayList();
    correct7.add("ps");
    ArrayList wrong7 = new ArrayList();
    wrong7.add("ls");
    wrong7.add("cat");
    wrong7.add("find");
    q7.put("correctAnswers", correct7);
    q7.put("wrongAnswers", wrong7);
    questionBank.add(q7);

    HashMap q8 = new HashMap();
    q8.put("category", "安卓");
    q8.put("question", "kernelsu一般是patch到哪个分区");
    ArrayList correct8 = new ArrayList();
    correct8.add("boot");
    ArrayList wrong8 = new ArrayList();
    wrong8.add("vendor");
    wrong8.add("odm");
    wrong8.add("system_ext");
    q8.put("correctAnswers", correct8);
    q8.put("wrongAnswers", wrong8);
    questionBank.add(q8);

    HashMap q9 = new HashMap();
    q9.put("category", "Linux");
    q9.put("question", "linux如何创建一个文件");
    ArrayList correct9 = new ArrayList();
    correct9.add("touch");
    ArrayList wrong9 = new ArrayList();
    wrong9.add("mkdir");
    wrong9.add("new");
    wrong9.add("create");
    q9.put("correctAnswers", correct9);
    q9.put("wrongAnswers", wrong9);
    questionBank.add(q9);

    HashMap q10 = new HashMap();
    q10.put("category", "Linux");
    q10.put("question", "linux如何创建一个文件夹");
    ArrayList correct10 = new ArrayList();
    correct10.add("mkdir");
    ArrayList wrong10 = new ArrayList();
    wrong10.add("touch");
    wrong10.add("folder");
    wrong10.add("makedir");
    q10.put("correctAnswers", correct10);
    q10.put("wrongAnswers", wrong10);
    questionBank.add(q10);

    HashMap q11 = new HashMap();
    q11.put("category", "Linux");
    q11.put("question", "linux如何使用超级用户运行程序");
    ArrayList correct11 = new ArrayList();
    correct11.add("sudo");
    ArrayList wrong11 = new ArrayList();
    wrong11.add("admin");
    wrong11.add("root");
    wrong11.add("su");
    q11.put("correctAnswers", correct11);
    q11.put("wrongAnswers", wrong11);
    questionBank.add(q11);

    HashMap q12 = new HashMap();
    q12.put("category", "Linux");
    q12.put("question", "linux如何查看当前的路径");
    ArrayList correct12 = new ArrayList();
    correct12.add("pwd");
    ArrayList wrong12 = new ArrayList();
    wrong12.add("path");
    wrong12.add("where");
    wrong12.add("locate");
    q12.put("correctAnswers", correct12);
    q12.put("wrongAnswers", wrong12);
    questionBank.add(q12);

    HashMap q13 = new HashMap();
    q13.put("category", "Linux");
    q13.put("question", "linux如何修改文件权限");
    ArrayList correct13 = new ArrayList();
    correct13.add("chmod");
    ArrayList wrong13 = new ArrayList();
    wrong13.add("perm");
    wrong13.add("setattr");
    wrong13.add("access");
    q13.put("correctAnswers", correct13);
    q13.put("wrongAnswers", wrong13);
    questionBank.add(q13);

    HashMap q14 = new HashMap();
    q14.put("category", "化学");
    q14.put("question", "以下物质中,日常生活中常见的酒精的分子式是什么");
    ArrayList correct14 = new ArrayList();
    correct14.add("C2H5OH");
    ArrayList wrong14 = new ArrayList();
    wrong14.add("H2O");
    wrong14.add("CO2");
    wrong14.add("CH3COOH");
    q14.put("correctAnswers", correct14);
    q14.put("wrongAnswers", wrong14);
    questionBank.add(q14);

    HashMap q15 = new HashMap();
    q15.put("category", "化学");
    q15.put("question", "下面物质中,空气中含量最多的是什么");
    ArrayList correct15 = new ArrayList();
    correct15.add("N2");
    ArrayList wrong15 = new ArrayList();
    wrong15.add("O2");
    wrong15.add("CO2");
    wrong15.add("Ar");
    q15.put("correctAnswers", correct15);
    q15.put("wrongAnswers", wrong15);
    questionBank.add(q15);

    HashMap q16 = new HashMap();
    q16.put("category", "化学");
    q16.put("question", "下面的物质中,最为活跃的单质是什么");
    ArrayList correct16 = new ArrayList();
    correct16.add("F2");
    ArrayList wrong16 = new ArrayList();
    wrong16.add("Cl2");
    wrong16.add("O2");
    wrong16.add("Na");
    q16.put("correctAnswers", correct16);
    q16.put("wrongAnswers", wrong16);
    questionBank.add(q16);

    HashMap q17 = new HashMap();
    q17.put("category", "Minecraft");
    q17.put("question", "mc普通模式中,骷髅一箭最多能造成多少伤害");
    ArrayList correct17 = new ArrayList();
    correct17.add("9");
    ArrayList wrong17 = new ArrayList();
    wrong17.add("5");
    wrong17.add("7");
    wrong17.add("12");
    q17.put("correctAnswers", correct17);
    q17.put("wrongAnswers", wrong17);
    questionBank.add(q17);

    HashMap q18 = new HashMap();
    q18.put("category", "Minecraft");
    q18.put("question", "mc中以下物品可以被火焰烧掉的是");
    ArrayList correct18 = new ArrayList();
    correct18.add("木剑");
    ArrayList wrong18 = new ArrayList();
    wrong18.add("石剑");
    wrong18.add("铁剑");
    wrong18.add("钻石剑");
    q18.put("correctAnswers", correct18);
    q18.put("wrongAnswers", wrong18);
    questionBank.add(q18);

    HashMap q19 = new HashMap();
    q19.put("category", "Minecraft");
    q19.put("question", "mc收到凋零2效果时,每秒会收到多少伤害");
    ArrayList correct19 = new ArrayList();
    correct19.add("2");
    ArrayList wrong19 = new ArrayList();
    wrong19.add("1");
    wrong19.add("3");
    wrong19.add("4");
    q19.put("correctAnswers", correct19);
    q19.put("wrongAnswers", wrong19);
    questionBank.add(q19);

    HashMap q20 = new HashMap();
    q20.put("category", "Minecraft");
    q20.put("question", "mc中收到等级5的中毒效果时,一秒会收到多少伤害");
    ArrayList correct20 = new ArrayList();
    correct20.add("2");
    ArrayList wrong20 = new ArrayList();
    wrong20.add("1");
    wrong20.add("3");
    wrong20.add("5");
    q20.put("correctAnswers", correct20);
    q20.put("wrongAnswers", wrong20);
    questionBank.add(q20);

    HashMap q21 = new HashMap();
    q21.put("category", "Minecraft");
    q21.put("question", "mc的苹果能恢复多少饥饿度");
    ArrayList correct21 = new ArrayList();
    correct21.add("4");
    ArrayList wrong21 = new ArrayList();
    wrong21.add("2");
    wrong21.add("3");
    wrong21.add("5");
    q21.put("correctAnswers", correct21);
    q21.put("wrongAnswers", wrong21);
    questionBank.add(q21);

    HashMap q22 = new HashMap();
    q22.put("category", "Minecraft");
    q22.put("question", "mc中下列物品不可被活塞推送的是");
    ArrayList correct22 = new ArrayList();
    correct22.add("黑曜石");
    ArrayList wrong22 = new ArrayList();
    wrong22.add("泥土");
    wrong22.add("石头");
    wrong22.add("木头");
    q22.put("correctAnswers", correct22);
    q22.put("wrongAnswers", wrong22);
    questionBank.add(q22);

    HashMap q23 = new HashMap();
    q23.put("category", "Minecraft");
    q23.put("question", "mc中从20格高摔落到平地上,会收到多少伤害");
    ArrayList correct23 = new ArrayList();
    correct23.add("20");
    ArrayList wrong23 = new ArrayList();
    wrong23.add("10");
    wrong23.add("15");
    wrong23.add("25");
    q23.put("correctAnswers", correct23);
    q23.put("wrongAnswers", wrong23);
    questionBank.add(q23);

    HashMap q24 = new HashMap();
    q24.put("category", "Minecraft");
    q24.put("question", "mc中以下物品空手破坏即可获取的是");
    ArrayList correct24 = new ArrayList();
    correct24.add("草方块");
    ArrayList wrong24 = new ArrayList();
    wrong24.add("石头");
    wrong24.add("铁矿");
    wrong24.add("钻石矿");
    q24.put("correctAnswers", correct24);
    q24.put("wrongAnswers", wrong24);
    questionBank.add(q24);

    HashMap q25 = new HashMap();
    q25.put("category", "化学");
    q25.put("question", "水的分子式是什么");
    ArrayList correct25 = new ArrayList();
    correct25.add("H₂O");
    ArrayList wrong25 = new ArrayList();
    wrong25.add("CO₂");
    wrong25.add("NaCl");
    wrong25.add("CH₄");
    q25.put("correctAnswers", correct25);
    q25.put("wrongAnswers", wrong25);
    questionBank.add(q25);

    HashMap q26 = new HashMap();
    q26.put("category", "物理");
    q26.put("question", "牛顿第一定律又称为什么定律");
    ArrayList correct26 = new ArrayList();
    correct26.add("惯性定律");
    ArrayList wrong26 = new ArrayList();
    wrong26.add("万有引力定律");
    wrong26.add("作用力与反作用力定律");
    wrong26.add("能量守恒定律");
    q26.put("correctAnswers", correct26);
    q26.put("wrongAnswers", wrong26);
    questionBank.add(q26);

    HashMap q27 = new HashMap();
    q27.put("category", "数学");
    q27.put("question", "圆的周长公式是什么");
    ArrayList correct27 = new ArrayList();
    correct27.add("C = 2πr");
    ArrayList wrong27 = new ArrayList();
    wrong27.add("C = πr²");
    wrong27.add("C = 4πr");
    wrong27.add("C = πd");
    q27.put("correctAnswers", correct27);
    q27.put("wrongAnswers", wrong27);
    questionBank.add(q27);

    HashMap q28 = new HashMap();
    q28.put("category", "fastboot");
    q28.put("question", "fastboot模式下解锁Bootloader的命令是什么");
    ArrayList correct28 = new ArrayList();
    correct28.add("fastboot oem unlock");
    ArrayList wrong28 = new ArrayList();
    wrong28.add("fastboot unlock");
    wrong28.add("fastboot flash unlock");
    wrong28.add("fastboot bootloader unlock");
    q28.put("correctAnswers", correct28);
    q28.put("wrongAnswers", wrong28);
    questionBank.add(q28);

    HashMap q29 = new HashMap();
    q29.put("category", "magisk");
    q29.put("question", "Magisk的主要功能是什么");
    ArrayList correct29 = new ArrayList();
    correct29.add("获取root权限");
    ArrayList wrong29 = new ArrayList();
    wrong29.add("刷写ROM");
    wrong29.add("恢复数据");
    wrong29.add("优化电池");
    q29.put("correctAnswers", correct29);
    q29.put("wrongAnswers", wrong29);
    questionBank.add(q29);

    HashMap q30 = new HashMap();
    q30.put("category", "化学");
    q30.put("question", "酸雨的pH值一般小于多少");
    ArrayList correct30 = new ArrayList();
    correct30.add("5.6");
    ArrayList wrong30 = new ArrayList();
    wrong30.add("7.0");
    wrong30.add("6.5");
    wrong30.add("4.0");
    q30.put("correctAnswers", correct30);
    q30.put("wrongAnswers", wrong30);
    questionBank.add(q30);

    HashMap q31 = new HashMap();
    q31.put("category", "物理");
    q31.put("question", "光在真空中的传播速度是多少");
    ArrayList correct31 = new ArrayList();
    correct31.add("3×10⁸ m/s");
    ArrayList wrong31 = new ArrayList();
    wrong31.add("3×10⁵ m/s");
    wrong31.add("340 m/s");
    wrong31.add("1500 m/s");
    q31.put("correctAnswers", correct31);
    q31.put("wrongAnswers", wrong31);
    questionBank.add(q31);

    HashMap q32 = new HashMap();
    q32.put("category", "数学");
    q32.put("question", "勾股定理的公式是什么");
    ArrayList correct32 = new ArrayList();
    correct32.add("a² + b² = c²");
    ArrayList wrong32 = new ArrayList();
    wrong32.add("a + b = c");
    wrong32.add("a × b = c");
    wrong32.add("a² - b² = c²");
    q32.put("correctAnswers", correct32);
    q32.put("wrongAnswers", wrong32);
    questionBank.add(q32);

    HashMap q33 = new HashMap();
    q33.put("category", "fastboot");
    q33.put("question", "fastboot模式下刷入recovery镜像的命令是什么");
    ArrayList correct33 = new ArrayList();
    correct33.add("fastboot flash recovery");
    ArrayList wrong33 = new ArrayList();
    wrong33.add("fastboot boot recovery");
    wrong33.add("fastboot install recovery");
    wrong33.add("fastboot update recovery");
    q33.put("correctAnswers", correct33);
    q33.put("wrongAnswers", wrong33);
    questionBank.add(q33);

    HashMap q34 = new HashMap();
    q34.put("category", "magisk");
    q34.put("question", "Magisk Hide的主要作用是什么");
    ArrayList correct34 = new ArrayList();
    correct34.add("隐藏root权限");
    ArrayList wrong34 = new ArrayList();
    wrong34.add("提升性能");
    wrong34.add("备份数据");
    wrong34.add("清理缓存");
    q34.put("correctAnswers", correct34);
    q34.put("wrongAnswers", wrong34);
    questionBank.add(q34);

    HashMap q35 = new HashMap();
    q35.put("category", "化学");
    q35.put("question", "下列元素中，属于惰性气体的是");
    ArrayList correct35 = new ArrayList();
    correct35.add("氦(He)");
    ArrayList wrong35 = new ArrayList();
    wrong35.add("氧(O)");
    wrong35.add("氢(H)");
    wrong35.add("碳(C)");
    q35.put("correctAnswers", correct35);
    q35.put("wrongAnswers", wrong35);
    questionBank.add(q35);

    HashMap q36 = new HashMap();
    q36.put("category", "物理");
    q36.put("question", "电流的单位是什么");
    ArrayList correct36 = new ArrayList();
    correct36.add("安培(A)");
    ArrayList wrong36 = new ArrayList();
    wrong36.add("伏特(V)");
    wrong36.add("欧姆(Ω)");
    wrong36.add("瓦特(W)");
    q36.put("correctAnswers", correct36);
    q36.put("wrongAnswers", wrong36);
    questionBank.add(q36);

    HashMap q37 = new HashMap();
    q37.put("category", "数学");
    q37.put("question", "一元二次方程求根公式是什么");
    ArrayList correct37 = new ArrayList();
    correct37.add("x = [-b±√(b²-4ac)]/2a");
    ArrayList wrong37 = new ArrayList();
    wrong37.add("x = b±√(b²-4ac)/2a");
    wrong37.add("x = [-b±(b²-4ac)]/2a");
    wrong37.add("x = [b±√(b²-4ac)]/2");
    q37.put("correctAnswers", correct37);
    q37.put("wrongAnswers", wrong37);
    questionBank.add(q37);

    HashMap q38 = new HashMap();
    q38.put("category", "fastboot");
    q38.put("question", "fastboot模式下擦除system分区的命令是什么");
    ArrayList correct38 = new ArrayList();
    correct38.add("fastboot erase system");
    ArrayList wrong38 = new ArrayList();
    wrong38.add("fastboot delete system");
    wrong38.add("fastboot format system");
    wrong38.add("fastboot wipe system");
    q38.put("correctAnswers", correct38);
    q38.put("wrongAnswers", wrong38);
    questionBank.add(q38);

    HashMap q39 = new HashMap();
    q39.put("category", "magisk");
    q39.put("question", "Magisk模块通常安装在哪个目录");
    ArrayList correct39 = new ArrayList();
    correct39.add("/data/adb/modules");
    ArrayList wrong39 = new ArrayList();
    wrong39.add("/system/bin");
    wrong39.add("/sdcard/Magisk");
    wrong39.add("/vendor/lib");
    q39.put("correctAnswers", correct39);
    q39.put("wrongAnswers", wrong39);
    questionBank.add(q39);

    HashMap q40 = new HashMap();
    q40.put("category", "化学");
    q40.put("question", "下列物质中，属于酸的是");
    ArrayList correct40 = new ArrayList();
    correct40.add("盐酸(HCl)");
    ArrayList wrong40 = new ArrayList();
    wrong40.add("氢氧化钠(NaOH)");
    wrong40.add("氯化钠(NaCl)");
    wrong40.add("氧化钙(CaO)");
    q40.put("correctAnswers", correct40);
    q40.put("wrongAnswers", wrong40);
    questionBank.add(q40);

    HashMap q41 = new HashMap();
    q41.put("category", "物理");
    q41.put("question", "力的单位是什么");
    ArrayList correct41 = new ArrayList();
    correct41.add("牛顿(N)");
    ArrayList wrong41 = new ArrayList();
    wrong41.add("焦耳(J)");
    wrong41.add("瓦特(W)");
    wrong41.add("帕斯卡(Pa)");
    q41.put("correctAnswers", correct41);
    q41.put("wrongAnswers", wrong41);
    questionBank.add(q41);

    HashMap q42 = new HashMap();
    q42.put("category", "数学");
    q42.put("question", "π的近似值是多少");
    ArrayList correct42 = new ArrayList();
    correct42.add("3.1416");
    ArrayList wrong42 = new ArrayList();
    wrong42.add("3.0");
    wrong42.add("2.718");
    wrong42.add("1.414");
    q42.put("correctAnswers", correct42);
    q42.put("wrongAnswers", wrong42);
    questionBank.add(q42);

    HashMap q43 = new HashMap();
    q43.put("category", "fastboot");
    q43.put("question", "fastboot模式下重启设备的命令是什么");
    ArrayList correct43 = new ArrayList();
    correct43.add("fastboot reboot");
    ArrayList wrong43 = new ArrayList();
    wrong43.add("fastboot restart");
    wrong43.add("fastboot boot");
    wrong43.add("fastboot continue");
    q43.put("correctAnswers", correct43);
    q43.put("wrongAnswers", wrong43);
    questionBank.add(q43);

    HashMap q44 = new HashMap();
    q44.put("category", "magisk");
    q44.put("question", "Magisk的创始人是谁");
    ArrayList correct44 = new ArrayList();
    correct44.add("topjohnwu");
    ArrayList wrong44 = new ArrayList();
    wrong44.add("Chainfire");
    wrong44.add("XDA");
    wrong44.add("Google");
    q44.put("correctAnswers", correct44);
    q44.put("wrongAnswers", wrong44);
    questionBank.add(q44);

    HashMap q45 = new HashMap();
    q45.put("category", "化学");
    q45.put("question", "下列物质中，pH值最大的是");
    ArrayList correct45 = new ArrayList();
    correct45.add("氢氧化钠溶液");
    ArrayList wrong45 = new ArrayList();
    wrong45.add("柠檬汁");
    wrong45.add("纯净水");
    wrong45.add("醋酸溶液");
    q45.put("correctAnswers", correct45);
    q45.put("wrongAnswers", wrong45);
    questionBank.add(q45);

    HashMap q46 = new HashMap();
    q46.put("category", "物理");
    q46.put("question", "功率的单位是什么");
    ArrayList correct46 = new ArrayList();
    correct46.add("瓦特(W)");
    ArrayList wrong46 = new ArrayList();
    wrong46.add("焦耳(J)");
    wrong46.add("牛顿(N)");
    wrong46.add("伏特(V)");
    q46.put("correctAnswers", correct46);
    q46.put("wrongAnswers", wrong46);
    questionBank.add(q46);

    HashMap q47 = new HashMap();
    q47.put("category", "数学");
    q47.put("question", "三角形内角和是多少度");
    ArrayList correct47 = new ArrayList();
    correct47.add("180");
    ArrayList wrong47 = new ArrayList();
    wrong47.add("90");
    wrong47.add("360");
    wrong47.add("270");
    q47.put("correctAnswers", correct47);
    q47.put("wrongAnswers", wrong47);
    questionBank.add(q47);

    HashMap q48 = new HashMap();
    q48.put("category", "fastboot");
    q48.put("question", "fastboot模式下查看设备状态的命令是什么");
    ArrayList correct48 = new ArrayList();
    correct48.add("fastboot devices");
    ArrayList wrong48 = new ArrayList();
    wrong48.add("fastboot status");
    wrong48.add("fastboot info");
    wrong48.add("fastboot list");
    q48.put("correctAnswers", correct48);
    q48.put("wrongAnswers", wrong48);
    questionBank.add(q48);

    HashMap q49 = new HashMap();
    q49.put("category", "magisk");
    q49.put("question", "Magisk Delta是什么");
    ArrayList correct49 = new ArrayList();
    correct49.add("Magisk的一个分支版本");
    ArrayList wrong49 = new ArrayList();
    wrong49.add("一种新的root方法");
    wrong49.add("Magisk的测试版本");
    wrong49.add("Magisk的旧版本名称");
    q49.put("correctAnswers", correct49);
    q49.put("wrongAnswers", wrong49);
    questionBank.add(q49);

    HashMap q50 = new HashMap();
    q50.put("category", "化学");
    q50.put("question", "下列元素中，属于卤素的是");
    ArrayList correct50 = new ArrayList();
    correct50.add("氟(F)");
    ArrayList wrong50 = new ArrayList();
    wrong50.add("钠(Na)");
    wrong50.add("钙(Ca)");
    wrong50.add("铁(Fe)");
    q50.put("correctAnswers", correct50);
    q50.put("wrongAnswers", wrong50);
    questionBank.add(q50);

    HashMap q51 = new HashMap();
    q51.put("category", "物理");
    q51.put("question", "声音在空气中的传播速度大约是多少");
    ArrayList correct51 = new ArrayList();
    correct51.add("340 m/s");
    ArrayList wrong51 = new ArrayList();
    wrong51.add("3×10⁸ m/s");
    wrong51.add("1500 m/s");
    wrong51.add("5000 m/s");
    q51.put("correctAnswers", correct51);
    q51.put("wrongAnswers", wrong51);
    questionBank.add(q51);

    HashMap q52 = new HashMap();
    q52.put("category", "数学");
    q52.put("question", "1公里等于多少米");
    ArrayList correct52 = new ArrayList();
    correct52.add("1000");
    ArrayList wrong52 = new ArrayList();
    wrong52.add("100");
    wrong52.add("10");
    wrong52.add("500");
    q52.put("correctAnswers", correct52);
    q52.put("wrongAnswers", wrong52);
    questionBank.add(q52);

    HashMap q53 = new HashMap();
    q53.put("category", "fastboot");
    q53.put("question", "fastboot模式下解锁关键分区的命令是什么");
    ArrayList correct53 = new ArrayList();
    correct53.add("fastboot flashing unlock");
    ArrayList wrong53 = new ArrayList();
    wrong53.add("fastboot unlock critical");
    wrong53.add("fastboot oem unlock_critical");
    wrong53.add("fastboot unlock partitions");
    q53.put("correctAnswers", correct53);
    q53.put("wrongAnswers", wrong53);
    questionBank.add(q53);

    HashMap q54 = new HashMap();
    q54.put("category", "magisk");
    q54.put("question", "MagiskSU是什么");
    ArrayList correct54 = new ArrayList();
    correct54.add("Magisk的root管理组件");
    ArrayList wrong54 = new ArrayList();
    wrong54.add("Magisk的安装工具");
    wrong54.add("Magisk的模块管理器");
    wrong54.add("Magisk的隐藏功能");
    q54.put("correctAnswers", correct54);
    q54.put("wrongAnswers", wrong54);
    questionBank.add(q54);

    HashMap q55 = new HashMap();
    q55.put("category", "化学");
    q55.put("question", "下列物质中，属于碱的是");
    ArrayList correct55 = new ArrayList();
    correct55.add("氢氧化钙(Ca(OH)₂)");
    ArrayList wrong55 = new ArrayList();
    wrong55.add("硫酸(H₂SO₄)");
    wrong55.add("二氧化碳(CO₂)");
    wrong55.add("氯化钠(NaCl)");
    q55.put("correctAnswers", correct55);
    q55.put("wrongAnswers", wrong55);
    questionBank.add(q55);

    HashMap q56 = new HashMap();
    q56.put("category", "物理");
    q56.put("question", "电阻的单位是什么");
    ArrayList correct56 = new ArrayList();
    correct56.add("欧姆(Ω)");
    ArrayList wrong56 = new ArrayList();
    wrong56.add("安培(A)");
    wrong56.add("伏特(V)");
    wrong56.add("瓦特(W)");
    q56.put("correctAnswers", correct56);
    q56.put("wrongAnswers", wrong56);
    questionBank.add(q56);

    HashMap q57 = new HashMap();
    q57.put("category", "数学");
    q57.put("question", "平行四边形的面积公式是什么");
    ArrayList correct57 = new ArrayList();
    correct57.add("底×高");
    ArrayList wrong57 = new ArrayList();
    wrong57.add("长×宽");
    wrong57.add("(上底+下底)×高÷2");
    wrong57.add("πr²");
    q57.put("correctAnswers", correct57);
    q57.put("wrongAnswers", wrong57);
    questionBank.add(q57);

    HashMap q58 = new HashMap();
    q58.put("category", "fastboot");
    q58.put("question", "fastboot模式下刷入boot镜像的命令是什么");
    ArrayList correct58 = new ArrayList();
    correct58.add("fastboot flash boot");
    ArrayList wrong58 = new ArrayList();
    wrong58.add("fastboot install boot");
    wrong58.add("fastboot update boot");
    wrong58.add("fastboot boot image");
    q58.put("correctAnswers", correct58);
    q58.put("wrongAnswers", wrong58);
    questionBank.add(q58);

    HashMap q59 = new HashMap();
    q59.put("category", "magisk");
    q59.put("question", "Zygisk是什么");
    ArrayList correct59 = new ArrayList();
    correct59.add("Magisk的Zygote注入框架");
    ArrayList wrong59 = new ArrayList();
    wrong59.add("一种新的root方法");
    wrong59.add("Magisk的模块名称");
    wrong59.add("Android系统组件");
    q59.put("correctAnswers", correct59);
    q59.put("wrongAnswers", wrong59);
    questionBank.add(q59);

    HashMap q60 = new HashMap();
    q60.put("category", "化学");
    q60.put("question", "下列物质中，属于单质的是");
    ArrayList correct60 = new ArrayList();
    correct60.add("氧气(O₂)");
    ArrayList wrong60 = new ArrayList();
    wrong60.add("水(H₂O)");
    wrong60.add("二氧化碳(CO₂)");
    wrong60.add("食盐(NaCl)");
    q60.put("correctAnswers", correct60);
    q60.put("wrongAnswers", wrong60);
    questionBank.add(q60);

    HashMap q61 = new HashMap();
    q61.put("category", "物理");
    q61.put("question", "功的单位是什么");
    ArrayList correct61 = new ArrayList();
    correct61.add("焦耳(J)");
    ArrayList wrong61 = new ArrayList();
    wrong61.add("瓦特(W)");
    wrong61.add("牛顿(N)");
    wrong61.add("帕斯卡(Pa)");
    q61.put("correctAnswers", correct61);
    q61.put("wrongAnswers", wrong61);
    questionBank.add(q61);

    HashMap q62 = new HashMap();
    q62.put("category", "数学");
    q62.put("question", "正方形的面积公式是什么");
    ArrayList correct62 = new ArrayList();
    correct62.add("边长×边长");
    ArrayList wrong62 = new ArrayList();
    wrong62.add("长×宽");
    wrong62.add("底×高");
    wrong62.add("πr²");
    q62.put("correctAnswers", correct62);
    q62.put("wrongAnswers", wrong62);
    questionBank.add(q62);

    HashMap q63 = new HashMap();
    q63.put("category", "fastboot");
    q63.put("question", "fastboot模式下进入recovery模式的命令是什么");
    ArrayList correct63 = new ArrayList();
    correct63.add("fastboot boot recovery");
    ArrayList wrong63 = new ArrayList();
    wrong63.add("fastboot reboot recovery");
    wrong63.add("fastboot enter recovery");
    wrong63.add("fastboot recovery");
    q63.put("correctAnswers", correct63);
    q63.put("wrongAnswers", wrong63);
    questionBank.add(q63);

    HashMap q64 = new HashMap();
    q64.put("category", "magisk");
    q64.put("question", "Magisk模块的安装文件后缀是什么");
    ArrayList correct64 = new ArrayList();
    correct64.add(".zip");
    ArrayList wrong64 = new ArrayList();
    wrong64.add(".apk");
    wrong64.add(".img");
    wrong64.add(".bin");
    q64.put("correctAnswers", correct64);
    q64.put("wrongAnswers", wrong64);
    questionBank.add(q64);

    HashMap q65 = new HashMap();
    q65.put("category", "化学");
    q65.put("question", "下列物质中，属于混合物的是");
    ArrayList correct65 = new ArrayList();
    correct65.add("空气");
    ArrayList wrong65 = new ArrayList();
    wrong65.add("氧气(O₂)");
    wrong65.add("水(H₂O)");
    wrong65.add("铁(Fe)");
    q65.put("correctAnswers", correct65);
    q65.put("wrongAnswers", wrong65);
    questionBank.add(q65);

    HashMap q66 = new HashMap();
    q66.put("category", "物理");
    q66.put("question", "电压的单位是什么");
    ArrayList correct66 = new ArrayList();
    correct66.add("伏特(V)");
    ArrayList wrong66 = new ArrayList();
    wrong66.add("安培(A)");
    wrong66.add("欧姆(Ω)");
    wrong66.add("瓦特(W)");
    q66.put("correctAnswers", correct66);
    q66.put("wrongAnswers", wrong66);
    questionBank.add(q66);

    HashMap q67 = new HashMap();
    q67.put("category", "数学");
    q67.put("question", "长方形的周长公式是什么");
    ArrayList correct67 = new ArrayList();
    correct67.add("2×(长+宽)");
    ArrayList wrong67 = new ArrayList();
    wrong67.add("长×宽");
    wrong67.add("4×边长");
    wrong67.add("长+宽");
    q67.put("correctAnswers", correct67);
    q67.put("wrongAnswers", wrong67);
    questionBank.add(q67);

    HashMap q68 = new HashMap();
    q68.put("category", "fastboot");
    q68.put("question", "fastboot模式下格式化userdata分区的命令是什么");
    ArrayList correct68 = new ArrayList();
    correct68.add("fastboot format userdata");
    ArrayList wrong68 = new ArrayList();
    wrong68.add("fastboot erase userdata");
    wrong68.add("fastboot wipe userdata");
    wrong68.add("fastboot delete userdata");
    q68.put("correctAnswers", correct68);
    q68.put("wrongAnswers", wrong68);
    questionBank.add(q68);

    HashMap q69 = new HashMap();
    q69.put("category", "magisk");
    q69.put("question", "Magisk的配置文件通常存放在哪里");
    ArrayList correct69 = new ArrayList();
    correct69.add("/data/adb/magisk.db");
    ArrayList wrong69 = new ArrayList();
    wrong69.add("/system/etc/magisk");
    wrong69.add("/sdcard/Magisk/config");
    wrong69.add("/vendor/bin/magisk");
    q69.put("correctAnswers", correct69);
    q69.put("wrongAnswers", wrong69);
    questionBank.add(q69);

    HashMap q70 = new HashMap();
    q70.put("category", "化学");
    q70.put("question", "下列元素中，原子序数最小的是");
    ArrayList correct70 = new ArrayList();
    correct70.add("氢(1)");
    ArrayList wrong70 = new ArrayList();
    wrong70.add("氦(2)");
    wrong70.add("锂(3)");
    wrong70.add("铍(4)");
    q70.put("correctAnswers", correct70);
    q70.put("wrongAnswers", wrong70);
    questionBank.add(q70);

    HashMap q71 = new HashMap();
    q71.put("category", "物理");
    q71.put("question", "密度的单位是什么");
    ArrayList correct71 = new ArrayList();
    correct71.add("kg/m³");
    ArrayList wrong71 = new ArrayList();
    wrong71.add("N/m²");
    wrong71.add("J/s");
    wrong71.add("A/s");
    q71.put("correctAnswers", correct71);
    q71.put("wrongAnswers", wrong71);
    questionBank.add(q71);

    HashMap q72 = new HashMap();
    q72.put("category", "数学");
    q72.put("question", "1公顷等于多少平方米");
    ArrayList correct72 = new ArrayList();
    correct72.add("10000");
    ArrayList wrong72 = new ArrayList();
    wrong72.add("100");
    wrong72.add("1000");
    wrong72.add("100000");
    q72.put("correctAnswers", correct72);
    q72.put("wrongAnswers", wrong72);
    questionBank.add(q72);

    HashMap q73 = new HashMap();
    q73.put("category", "fastboot");
    q73.put("question", "fastboot模式下刷入system镜像的命令是什么");
    ArrayList correct73 = new ArrayList();
    correct73.add("fastboot flash system");
    ArrayList wrong73 = new ArrayList();
    wrong73.add("fastboot install system");
    wrong73.add("fastboot update system");
    wrong73.add("fastboot system image");
    q73.put("correctAnswers", correct73);
    q73.put("wrongAnswers", wrong73);
    questionBank.add(q73);

    HashMap q74 = new HashMap();
    q74.put("category", "magisk");
    q74.put("question", "Magisk的官方仓库名称是什么");
    ArrayList correct74 = new ArrayList();
    correct74.add("Magisk Modules Repo");
    ArrayList wrong74 = new ArrayList();
    wrong74.add("Magisk Store");
    wrong74.add("Magisk Hub");
    wrong74.add("Magisk Central");
    q74.put("correctAnswers", correct74);
    q74.put("wrongAnswers", wrong74);
    questionBank.add(q74);

    HashMap q75 = new HashMap();
    q75.put("category", "化学");
    q75.put("question", "下列元素中，属于碱金属的是");
    ArrayList correct75 = new ArrayList();
    correct75.add("钠(Na)");
    ArrayList wrong75 = new ArrayList();
    wrong75.add("钙(Ca)");
    wrong75.add("铝(Al)");
    wrong75.add("铁(Fe)");
    q75.put("correctAnswers", correct75);
    q75.put("wrongAnswers", wrong75);
    questionBank.add(q75);

    HashMap q76 = new HashMap();
    q76.put("category", "物理");
    q76.put("question", "加速度的单位是什么");
    ArrayList correct76 = new ArrayList();
    correct76.add("m/s²");
    ArrayList wrong76 = new ArrayList();
    wrong76.add("m/s");
    wrong76.add("N/s");
    wrong76.add("kg/m");
    q76.put("correctAnswers", correct76);
    q76.put("wrongAnswers", wrong76);
    questionBank.add(q76);

    HashMap q77 = new HashMap();
    q77.put("category", "数学");
    q77.put("question", "梯形的面积公式是什么");
    ArrayList correct77 = new ArrayList();
    correct77.add("(上底+下底)×高÷2");
    ArrayList wrong77 = new ArrayList();
    wrong77.add("底×高");
    wrong77.add("长×宽");
    wrong77.add("πr²");
    q77.put("correctAnswers", correct77);
    q77.put("wrongAnswers", wrong77);
    questionBank.add(q77);

    HashMap q78 = new HashMap();
    q78.put("category", "fastboot");
    q78.put("question", "fastboot模式下锁定Bootloader的命令是什么");
    ArrayList correct78 = new ArrayList();
    correct78.add("fastboot oem lock");
    ArrayList wrong78 = new ArrayList();
    wrong78.add("fastboot lock");
    wrong78.add("fastboot secure lock");
    wrong78.add("fastboot bootloader lock");
    q78.put("correctAnswers", correct78);
    q78.put("wrongAnswers", wrong78);
    questionBank.add(q78);

    HashMap q79 = new HashMap();
    q79.put("category", "magisk");
    q79.put("question", "Magisk的日志文件通常存放在哪里");
    ArrayList correct79 = new ArrayList();
    correct79.add("/data/adb/magisk.log");
    ArrayList wrong79 = new ArrayList();
    wrong79.add("/sdcard/magisk.log");
    wrong79.add("/system/bin/magisk.log");
    wrong79.add("/cache/magisk.log");
    q79.put("correctAnswers", correct79);
    q79.put("wrongAnswers", wrong79);
    questionBank.add(q79);

    HashMap q80 = new HashMap();
    q80.put("category", "化学");
    q80.put("question", "下列物质中，属于氧化物的是");
    ArrayList correct80 = new ArrayList();
    correct80.add("氧化钙(CaO)");
    ArrayList wrong80 = new ArrayList();
    wrong80.add("氯化钠(NaCl)");
    wrong80.add("硫酸(H₂SO₄)");
    wrong80.add("氢氧化钠(NaOH)");
    q80.put("correctAnswers", correct80);
    q80.put("wrongAnswers", wrong80);
    questionBank.add(q80);

    HashMap q81 = new HashMap();
    q81.put("category", "物理");
    q81.put("question", "压强的单位是什么");
    ArrayList correct81 = new ArrayList();
    correct81.add("帕斯卡(Pa)");
    ArrayList wrong81 = new ArrayList();
    wrong81.add("牛顿(N)");
    wrong81.add("焦耳(J)");
    wrong81.add("瓦特(W)");
    q81.put("correctAnswers", correct81);
    q81.put("wrongAnswers", wrong81);
    questionBank.add(q81);

    HashMap q82 = new HashMap();
    q82.put("category", "数学");
    q82.put("question", "1立方米等于多少升");
    ArrayList correct82 = new ArrayList();
    correct82.add("1000");
    ArrayList wrong82 = new ArrayList();
    wrong82.add("100");
    wrong82.add("10");
    wrong82.add("500");
    q82.put("correctAnswers", correct82);
    q82.put("wrongAnswers", wrong82);
    questionBank.add(q82);

    HashMap q83 = new HashMap();
    q83.put("category", "fastboot");
    q83.put("question", "fastboot模式下刷入vbmeta分区的命令是什么");
    ArrayList correct83 = new ArrayList();
    correct83.add("fastboot flash vbmeta");
    ArrayList wrong83 = new ArrayList();
    wrong83.add("fastboot install vbmeta");
    wrong83.add("fastboot update vbmeta");
    wrong83.add("fastboot vbmeta image");
    q83.put("correctAnswers", correct83);
    q83.put("wrongAnswers", wrong83);
    questionBank.add(q83);

    HashMap q84 = new HashMap();
    q84.put("category", "magisk");
    q84.put("question", "Magisk的Canary版本是什么");
    ArrayList correct84 = new ArrayList();
    correct84.add("测试版本");
    ArrayList wrong84 = new ArrayList();
    wrong84.add("稳定版本");
    wrong84.add("旧版本");
    wrong84.add("企业版本");
    q84.put("correctAnswers", correct84);
    q84.put("wrongAnswers", wrong84);
    questionBank.add(q84);

    HashMap q85 = new HashMap();
    q85.put("category", "化学");
    q85.put("question", "下列元素中，属于过渡金属的是");
    ArrayList correct85 = new ArrayList();
    correct85.add("铁(Fe)");
    ArrayList wrong85 = new ArrayList();
    wrong85.add("钠(Na)");
    wrong85.add("钙(Ca)");
    wrong85.add("铝(Al)");
    q85.put("correctAnswers", correct85);
    q85.put("wrongAnswers", wrong85);
    questionBank.add(q85);

    HashMap q86 = new HashMap();
    q86.put("category", "物理");
    q86.put("question", "能量的单位是什么");
    ArrayList correct86 = new ArrayList();
    correct86.add("焦耳(J)");
    ArrayList wrong86 = new ArrayList();
    wrong86.add("瓦特(W)");
    wrong86.add("牛顿(N)");
    wrong86.add("伏特(V)");
    q86.put("correctAnswers", correct86);
    q86.put("wrongAnswers", wrong86);
    questionBank.add(q86);

    HashMap q87 = new HashMap();
    q87.put("category", "数学");
    q87.put("question", "圆的面积公式是什么");
    ArrayList correct87 = new ArrayList();
    correct87.add("πr²");
    ArrayList wrong87 = new ArrayList();
    wrong87.add("2πr");
    wrong87.add("πd");
    wrong87.add("4πr²");
    q87.put("correctAnswers", correct87);
    q87.put("wrongAnswers", wrong87);
    questionBank.add(q87);

    HashMap q88 = new HashMap();
    q88.put("category", "fastboot");
    q88.put("question", "fastboot模式下刷入多个镜像的命令是什么");
    ArrayList correct88 = new ArrayList();
    correct88.add("fastboot update");
    ArrayList wrong88 = new ArrayList();
    wrong88.add("fastboot flashall");
    wrong88.add("fastboot install-all");
    wrong88.add("fastboot multi-flash");
    q88.put("correctAnswers", correct88);
    q88.put("wrongAnswers", wrong88);
    questionBank.add(q88);

    HashMap q89 = new HashMap();
    q89.put("category", "magisk");
    q89.put("question", "Magisk的DenyList功能用于什么");
    ArrayList correct89 = new ArrayList();
    correct89.add("隐藏root权限");
    ArrayList wrong89 = new ArrayList();
    wrong89.add("禁止应用运行");
    wrong89.add("清理不需要的文件");
    wrong89.add("管理模块");
    q89.put("correctAnswers", correct89);
    q89.put("wrongAnswers", wrong89);
    questionBank.add(q89);

    HashMap q90 = new HashMap();
    q90.put("category", "化学");
    q90.put("question", "下列物质中，属于盐的是");
    ArrayList correct90 = new ArrayList();
    correct90.add("碳酸钙(CaCO₃)");
    ArrayList wrong90 = new ArrayList();
    wrong90.add("盐酸(HCl)");
    wrong90.add("氢氧化钠(NaOH)");
    wrong90.add("氧化铁(Fe₂O₃)");
    q90.put("correctAnswers", correct90);
    q90.put("wrongAnswers", wrong90);
    questionBank.add(q90);

    HashMap q91 = new HashMap();
    q91.put("category", "物理");
    q91.put("question", "频率的单位是什么");
    ArrayList correct91 = new ArrayList();
    correct91.add("赫兹(Hz)");
    ArrayList wrong91 = new ArrayList();
    wrong91.add("米/秒(m/s)");
    wrong91.add("牛顿(N)");
    wrong91.add("焦耳(J)");
    q91.put("correctAnswers", correct91);
    q91.put("wrongAnswers", wrong91);
    questionBank.add(q91);

    HashMap q92 = new HashMap();
    q92.put("category", "数学");
    q92.put("question", "正方体的体积公式是什么");
    ArrayList correct92 = new ArrayList();
    correct92.add("边长³");
    ArrayList wrong92 = new ArrayList();
    wrong92.add("长×宽×高");
    wrong92.add("底面积×高");
    wrong92.add("4/3πr³");
    q92.put("correctAnswers", correct92);
    q92.put("wrongAnswers", wrong92);
    questionBank.add(q92);

    HashMap q93 = new HashMap();
    q93.put("category", "fastboot");
    q93.put("question", "fastboot模式下设置活动分区的命令是什么");
    ArrayList correct93 = new ArrayList();
    correct93.add("fastboot set_active");
    ArrayList wrong93 = new ArrayList();
    wrong93.add("fastboot active");
    wrong93.add("fastboot select");
    wrong93.add("fastboot switch");
    q93.put("correctAnswers", correct93);
    q93.put("wrongAnswers", wrong93);
    questionBank.add(q93);

    HashMap q94 = new HashMap();
    q94.put("category", "magisk");
    q94.put("question", "Magisk的Direct Install功能用于什么");
    ArrayList correct94 = new ArrayList();
    correct94.add("直接安装Magisk");
    ArrayList wrong94 = new ArrayList();
    wrong94.add("安装模块");
    wrong94.add("修复boot分区");
    wrong94.add("卸载Magisk");
    q94.put("correctAnswers", correct94);
    q94.put("wrongAnswers", wrong94);
    questionBank.add(q94);

    HashMap q95 = new HashMap();
    q95.put("category", "化学");
    q95.put("question", "下列元素中，属于稀土元素的是");
    ArrayList correct95 = new ArrayList();
    correct95.add("镧(La)");
    ArrayList wrong95 = new ArrayList();
    wrong95.add("铁(Fe)");
    wrong95.add("铜(Cu)");
    wrong95.add("锌(Zn)");
    q95.put("correctAnswers", correct95);
    q95.put("wrongAnswers", wrong95);
    questionBank.add(q95);

    HashMap q96 = new HashMap();
    q96.put("category", "物理");
    q96.put("question", "热量的单位是什么");
    ArrayList correct96 = new ArrayList();
    correct96.add("焦耳(J)");
    ArrayList wrong96 = new ArrayList();
    wrong96.add("卡路里(cal)");
    wrong96.add("瓦特(W)");
    wrong96.add("摄氏度(℃)");
    q96.put("correctAnswers", correct96);
    q96.put("wrongAnswers", wrong96);
    questionBank.add(q96);

    HashMap q97 = new HashMap();
    q97.put("category", "数学");
    q97.put("question", "圆柱的体积公式是什么");
    ArrayList correct97 = new ArrayList();
    correct97.add("πr²h");
    ArrayList wrong97 = new ArrayList();
    wrong97.add("2πrh");
    wrong97.add("4/3πr³");
    wrong97.add("πr²");
    q97.put("correctAnswers", correct97);
    q97.put("wrongAnswers", wrong97);
    questionBank.add(q97);

    HashMap q98 = new HashMap();
    q98.put("category", "fastboot");
    q98.put("question", "fastboot模式下获取设备信息的命令是什么");
    ArrayList correct98 = new ArrayList();
    correct98.add("fastboot getvar all");
    ArrayList wrong98 = new ArrayList();
    wrong98.add("fastboot info");
    wrong98.add("fastboot status");
    wrong98.add("fastboot deviceinfo");
    q98.put("correctAnswers", correct98);
    q98.put("wrongAnswers", wrong98);
    questionBank.add(q98);

    HashMap q99 = new HashMap();
    q99.put("category", "magisk");
    q99.put("question", "Magisk的Safe Mode用于什么");
    ArrayList correct99 = new ArrayList();
    correct99.add("安全模式启动");
    ArrayList wrong99 = new ArrayList();
    wrong99.add("禁用所有模块");
    wrong99.add("恢复出厂设置");
    wrong99.add("备份系统");
    q99.put("correctAnswers", correct99);
    q99.put("wrongAnswers", wrong99);
    questionBank.add(q99);

    HashMap q100 = new HashMap();
    q100.put("category", "化学");
    q100.put("question", "下列物质中，属于有机物的是");
    ArrayList correct100 = new ArrayList();
    correct100.add("甲烷(CH₄)");
    ArrayList wrong100 = new ArrayList();
    wrong100.add("二氧化碳(CO₂)");
    wrong100.add("碳酸钙(CaCO₃)");
    wrong100.add("水(H₂O)");
    q100.put("correctAnswers", correct100);
    q100.put("wrongAnswers", wrong100);
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

HashMap verifyStates = new HashMap();

void onTroopEvent(String groupUin, String userUin, int type) {
    if (type == 2 && isVerifyEnabled(groupUin)) {
        if (userUin == null || userUin.equals("0") || userUin.equals("all")) return;
        HashMap state = new HashMap();
        state.put("groupUin", groupUin);
        state.put("userUin", userUin);
        state.put("correctCount", 0);
        state.put("wrongCount", 0);
        state.put("changeCount", 0);
        state.put("startTime", System.currentTimeMillis());
        state.put("handled", false);
        ArrayList currentQuestions = new ArrayList();
        for (int i = 0; i < 5; i++) {
            int randomIndex = (int) (Math.random() * questionBank.size());
            currentQuestions.add(questionBank.get(randomIndex));
        }
        state.put("currentQuestions", currentQuestions);
        String stateKey = groupUin + "_" + userUin;
        verifyStates.put(stateKey, state);
        sendQuestion(stateKey, 0);
    } else if (type == 1) {
        String stateKey = groupUin + "_" + userUin;
        HashMap state = (HashMap) verifyStates.get(stateKey);
        if (state != null && !(Boolean)state.get("handled")) {
            verifyStates.remove(stateKey);
            sendMsg(groupUin, "", "[AtQQ=" + userUin + "] 入群中途退群 验证流程结束");
        }
    }
}

void sendQuestion(String stateKey, int questionIndex) {
    HashMap state = (HashMap) verifyStates.get(stateKey);
    if (state == null) return;
    state.put("currentQuestionIndex", questionIndex);
    ArrayList currentQuestions = (ArrayList) state.get("currentQuestions");
    if (questionIndex >= currentQuestions.size()) {
        checkVerificationResult(stateKey);
        return;
    }
    HashMap q = (HashMap) currentQuestions.get(questionIndex);
    StringBuilder message = new StringBuilder();
    message.append("[AtQQ=").append(state.get("userUin")).append("] 欢迎 请在300s内回答以下问题,验证过期永久封禁,回答错误永久封禁[").append(questionIndex + 1).append("/2]\n\n");
    message.append(q.get("question")).append("\n\n");
    ArrayList allOptions = new ArrayList();
    allOptions.addAll((ArrayList)q.get("correctAnswers"));
    allOptions.addAll((ArrayList)q.get("wrongAnswers"));
    for (int i = 0; i < allOptions.size(); i++) {
        int randomPos = (int) (Math.random() * allOptions.size());
        Object temp = allOptions.get(i);
        allOptions.set(i, allOptions.get(randomPos));
        allOptions.set(randomPos, temp);
    }
    state.put("currentOptions", allOptions);
    char optionChar = 'A';
    for (Object option : allOptions) {
        message.append(optionChar).append(". ").append(option).append("\n");
        optionChar++;
    }
    long elapsed = System.currentTimeMillis() - (Long)state.get("startTime");
    long remaining = 300000 - elapsed;
    int remainingSec = (int)(remaining / 1000);
    message.append("\n非验证者回答会被禁言\n题目太难可擅用DeepSeekChatGPT\n题目太难可以发送换题更换题目[换题机会剩余");
    message.append(2 - (Integer)state.get("changeCount")).append("次]");
    message.append("\n验证过期时间：").append(remainingSec).append("s");
    sendMsg((String)state.get("groupUin"), "", message.toString());
}

void processAnswer(String stateKey, String answer) {
    HashMap state = (HashMap) verifyStates.get(stateKey);
    if (state == null) return;
    long elapsed = System.currentTimeMillis() - (Long)state.get("startTime");
    if (elapsed > 300000) {
        handleVerificationFailure(stateKey, "验证过期");
        return;
    }
    if ("换题".equals(answer)) {
        int changeCount = (Integer)state.get("changeCount");
        if (changeCount < 2) {
            state.put("changeCount", changeCount + 1);
            int currentIndex = (Integer)state.get("currentQuestionIndex");
            int randomIndex = (int) (Math.random() * questionBank.size());
            ArrayList currentQuestions = (ArrayList) state.get("currentQuestions");
            currentQuestions.set(currentIndex, questionBank.get(randomIndex));
            sendQuestion(stateKey, currentIndex);
        } else {
            sendMsg((String)state.get("groupUin"), "", "[AtQQ=" + state.get("userUin") + "] 换题次数已达上限，请回答问题");
        }
        return;
    }
    if (answer.length() != 1 || !Character.isLetter(answer.charAt(0))) {
        sendMsg((String)state.get("groupUin"), "", "[AtQQ=" + state.get("userUin") + "] 请及时完成入群验证，超时永久封禁");
        return;
    }
    int optionIndex = Character.toUpperCase(answer.charAt(0)) - 'A';
    int questionIndex = (Integer)state.get("currentQuestionIndex");
    ArrayList currentQuestions = (ArrayList) state.get("currentQuestions");
    HashMap q = (HashMap) currentQuestions.get(questionIndex);
    String selectedAnswer = "";
    ArrayList currentOptions = (ArrayList) state.get("currentOptions");
    if (optionIndex >= 0 && optionIndex < currentOptions.size()) {
        selectedAnswer = ((String) currentOptions.get(optionIndex)).toLowerCase().replaceAll("\\s+", "");
    } else {
        sendMsg((String)state.get("groupUin"), "", "[AtQQ=" + state.get("userUin") + "] 选项无效，请重新选择");
        return;
    }
    boolean isCorrect = false;
    for (Object correctObj : (ArrayList)q.get("correctAnswers")) {
        String correctAnswer = ((String) correctObj).toLowerCase().replaceAll("\\s+", "");
        if (selectedAnswer.equals(correctAnswer)) {
            isCorrect = true;
            break;
        }
    }
    if (isCorrect) {
        state.put("correctCount", (Integer)state.get("correctCount") + 1);
        sendMsg((String)state.get("groupUin"), "", "[AtQQ=" + state.get("userUin") + "] 回答正确");
        if ((Integer)state.get("correctCount") >= 2) {
            checkVerificationResult(stateKey);
        } else {
            sendQuestion(stateKey, questionIndex + 1);
        }
    } else {
        state.put("wrongCount", (Integer)state.get("wrongCount") + 1);
        sendMsg((String)state.get("groupUin"), "", "[AtQQ=" + state.get("userUin") + "] 回答错误");
        if ((Integer)state.get("wrongCount") >= 2) {
            handleVerificationFailure(stateKey, "验证错误");
        } else {
            sendQuestion(stateKey, questionIndex);
        }
    }
}

void checkVerificationResult(String stateKey) {
    HashMap state = (HashMap) verifyStates.get(stateKey);
    if (state == null) return;
    if ((Boolean)state.get("handled")) return;
    state.put("handled", true);
    if ((Integer)state.get("correctCount") >= 2) {
        sendMsg((String)state.get("groupUin"), "", "[AtQQ=" + state.get("userUin") + "] 验证通过 欢迎加入群聊");
    } else {
        handleVerificationFailure(stateKey, "未通过验证");
    }
    verifyStates.remove(stateKey);
}

void handleVerificationFailure(String stateKey, String reason) {
    HashMap state = (HashMap) verifyStates.get(stateKey);
    if (state == null) return;
    if ((Boolean)state.get("handled")) return;
    state.put("handled", true);
    try {
        kick((String)state.get("groupUin"), (String)state.get("userUin"), true);
        sendMsg((String)state.get("groupUin"), "", "[AtQQ=" + state.get("userUin") + "] 入群中途被踢出 验证流程结束");
    } catch (Exception e) {
        sendMsg((String)state.get("groupUin"), "", "[AtQQ=" + state.get("userUin") + "] 由于" + reason + "，未完成入群验证，已被永久封禁。");
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
                HashMap state = (HashMap) verifyStates.get(stateKey);
                if (state == null) continue;

                if (!userUin.equals(state.get("userUin")) && (content.matches("^[A-Da-d]$") || "换题".equals(content))) {
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
                    HashMap state = (HashMap) verifyStates.get(stateKey);
                    if (state == null) continue;
                    if ((Boolean)state.get("handled")) continue;
                    long elapsed = currentTime - (Long)state.get("startTime");
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