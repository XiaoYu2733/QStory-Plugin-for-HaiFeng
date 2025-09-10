final String PLUGIN_NAME = "金币系统";
final String DATA_DIR = appPath + "/金币系统/数据/";
final long WORK_COOLDOWN = 21600000;
final long ROB_COOLDOWN = 10800000;
final long TRANSFER_COOLDOWN = 3600000;
final long FISHING_COOLDOWN = 3600000;
final long ALMS_COOLDOWN = 86400000;
final long REDPACKET_COOLDOWN = 1800000;
final long DUNGEON_COOLDOWN = 43200000;
final long STOCK_COOLDOWN = 28800000;
final long PET_FEED_COOLDOWN = 43200000;
final long HOUSE_INCOME_COOLDOWN = 86400000;
final long ARENA_COOLDOWN = 21600000;
final long BANK_INTERVAL = 86400000;
final long MARKET_REFRESH = 3600000;
final long EQUIP_UPGRADE_COOLDOWN = 43200000;
final long PET_BATTLE_COOLDOWN = 21600000;
final long TEAM_DUNGEON_COOLDOWN = 172800000;
final long DAILY_TASK_RESET = 86400000;
final long LOTTERY_SHOP_COOLDOWN = 3600000;

java.io.File dataDir = new java.io.File(DATA_DIR);
if (!dataDir.exists()) {
    dataDir.mkdirs();
}

java.util.Map weapons = new java.util.HashMap();
{
    java.util.Map stick = new java.util.HashMap();
    stick.put("price", 300);
    stick.put("attack", 5);
    stick.put("defense", 0);
    stick.put("maxLevel", 5);
    weapons.put("木棍", stick);
    
    java.util.Map dagger = new java.util.HashMap();
    dagger.put("price", 800);
    dagger.put("attack", 15);
    dagger.put("defense", 5);
    dagger.put("maxLevel", 10);
    weapons.put("匕首", dagger);
    
    java.util.Map pistol = new java.util.HashMap();
    pistol.put("price", 2000);
    pistol.put("attack", 30);
    pistol.put("defense", 10);
    pistol.put("maxLevel", 15);
    weapons.put("手枪", pistol);
    
    java.util.Map shotgun = new java.util.HashMap();
    shotgun.put("price", 5000);
    shotgun.put("attack", 50);
    shotgun.put("defense", 15);
    shotgun.put("maxLevel", 20);
    weapons.put("散弹枪", shotgun);
    
    java.util.Map armor = new java.util.HashMap();
    armor.put("price", 3000);
    armor.put("attack", 0);
    armor.put("defense", 30);
    armor.put("maxLevel", 15);
    weapons.put("铠甲", armor);
    
    java.util.Map bulletproof = new java.util.HashMap();
    bulletproof.put("price", 4500);
    bulletproof.put("attack", 0);
    bulletproof.put("defense", 45);
    bulletproof.put("maxLevel", 20);
    weapons.put("防弹衣", bulletproof);
    
    java.util.Map lightsaber = new java.util.HashMap();
    lightsaber.put("price", 10000);
    lightsaber.put("attack", 70);
    lightsaber.put("defense", 25);
    lightsaber.put("maxLevel", 25);
    weapons.put("激光剑", lightsaber);
    
    java.util.Map bazooka = new java.util.HashMap();
    bazooka.put("price", 15000);
    bazooka.put("attack", 100);
    bazooka.put("defense", 10);
    bazooka.put("maxLevel", 30);
    weapons.put("火箭筒", bazooka);
    
    java.util.Map trident = new java.util.HashMap();
    trident.put("price", 25000);
    trident.put("attack", 120);
    trident.put("defense", 40);
    trident.put("maxLevel", 35);
    weapons.put("三叉戟", trident);
    
    java.util.Map dragonSword = new java.util.HashMap();
    dragonSword.put("price", 40000);
    dragonSword.put("attack", 180);
    dragonSword.put("defense", 60);
    dragonSword.put("maxLevel", 40);
    weapons.put("龙之剑", dragonSword);
    
    java.util.Map photonBlaster = new java.util.HashMap();
    photonBlaster.put("price", 60000);
    photonBlaster.put("attack", 250);
    photonBlaster.put("defense", 80);
    photonBlaster.put("maxLevel", 45);
    weapons.put("光子冲击炮", photonBlaster);
    
    java.util.Map titanShield = new java.util.HashMap();
    titanShield.put("price", 35000);
    titanShield.put("attack", 20);
    titanShield.put("defense", 150);
    titanShield.put("maxLevel", 40);
    weapons.put("泰坦之盾", titanShield);
    
    java.util.Map venomSpear = new java.util.HashMap();
    venomSpear.put("price", 30000);
    venomSpear.put("attack", 150);
    venomSpear.put("defense", 50);
    venomSpear.put("maxLevel", 35);
    weapons.put("毒液长矛", venomSpear);
    
    java.util.Map celestialBow = new java.util.HashMap();
    celestialBow.put("price", 45000);
    celestialBow.put("attack", 200);
    celestialBow.put("defense", 70);
    celestialBow.put("maxLevel", 40);
    weapons.put("天界神弓", celestialBow);
    
    java.util.Map necronomicon = new java.util.HashMap();
    necronomicon.put("price", 100000);
    necronomicon.put("attack", 300);
    necronomicon.put("defense", 100);
    necronomicon.put("maxLevel", 50);
    weapons.put("死灵之书", necronomicon);
    
    java.util.Map infinityGauntlet = new java.util.HashMap();
    infinityGauntlet.put("price", 200000);
    infinityGauntlet.put("attack", 500);
    infinityGauntlet.put("defense", 200);
    infinityGauntlet.put("maxLevel", 60);
    weapons.put("无限手套", infinityGauntlet);
}

java.util.Map items = new java.util.HashMap();
{
    java.util.Map expCard = new java.util.HashMap();
    expCard.put("price", 500);
    expCard.put("type", "buff");
    items.put("经验卡", expCard);
    
    java.util.Map protectShield = new java.util.HashMap();
    protectShield.put("price", 1000);
    protectShield.put("type", "shield");
    items.put("护盾", protectShield);
    
    java.util.Map fishingRod = new java.util.HashMap();
    fishingRod.put("price", 800);
    fishingRod.put("type", "tool");
    items.put("高级鱼竿", fishingRod);
    
    java.util.Map lottery = new java.util.HashMap();
    lottery.put("price", 200);
    lottery.put("type", "gamble");
    items.put("彩票", lottery);
    
    java.util.Map revivePotion = new java.util.HashMap();
    revivePotion.put("price", 1500);
    revivePotion.put("type", "consumable");
    items.put("复活药水", revivePotion);
    
    java.util.Map petFood = new java.util.HashMap();
    petFood.put("price", 300);
    petFood.put("type", "pet");
    items.put("宠物粮", petFood);
    
    java.util.Map upgradeStone = new java.util.HashMap();
    upgradeStone.put("price", 1000);
    upgradeStone.put("type", "upgrade");
    items.put("强化石", upgradeStone);
    
    java.util.Map treasureMap = new java.util.HashMap();
    treasureMap.put("price", 5000);
    treasureMap.put("type", "special");
    items.put("藏宝图", treasureMap);
    
    java.util.Map festivalToken = new java.util.HashMap();
    festivalToken.put("price", 200);
    festivalToken.put("type", "event");
    items.put("节日代币", festivalToken);
    
    java.util.Map houseDecor = new java.util.HashMap();
    houseDecor.put("price", 3000);
    houseDecor.put("type", "decoration");
    items.put("家园装饰", houseDecor);
    
    java.util.Map insurance = new java.util.HashMap();
    insurance.put("price", 5000);
    insurance.put("type", "bank");
    items.put("存款保险", insurance);
    
    java.util.Map blackMarketToken = new java.util.HashMap();
    blackMarketToken.put("price", 1000);
    blackMarketToken.put("type", "special");
    items.put("黑市令牌", blackMarketToken);
    
    java.util.Map dailyTaskReset = new java.util.HashMap();
    dailyTaskReset.put("price", 100);
    dailyTaskReset.put("type", "special");
    items.put("任务重置券", dailyTaskReset);
}

java.util.Map pets = new java.util.HashMap();
{
    java.util.Map dog = new java.util.HashMap();
    dog.put("price", 5000);
    dog.put("income", 50);
    dog.put("bonus", "打工");
    dog.put("attack", 20);
    dog.put("defense", 30);
    dog.put("hp", 100);
    pets.put("工作犬", dog);
    
    java.util.Map cat = new java.util.HashMap();
    cat.put("price", 3000);
    cat.put("income", 30);
    cat.put("bonus", "钓鱼");
    cat.put("attack", 15);
    cat.put("defense", 20);
    cat.put("hp", 80);
    pets.put("招财猫", cat);
    
    java.util.Map dragon = new java.util.HashMap();
    dragon.put("price", 10000);
    dragon.put("income", 100);
    dragon.put("bonus", "副本");
    dragon.put("attack", 50);
    dragon.put("defense", 60);
    dragon.put("hp", 200);
    pets.put("守护龙", dragon);
    
    java.util.Map rabbit = new java.util.HashMap();
    rabbit.put("price", 2000);
    rabbit.put("income", 25);
    rabbit.put("bonus", "签到");
    rabbit.put("attack", 10);
    rabbit.put("defense", 15);
    rabbit.put("hp", 60);
    pets.put("幸运兔", rabbit);
    
    java.util.Map phoenix = new java.util.HashMap();
    phoenix.put("price", 50000);
    phoenix.put("income", 200);
    phoenix.put("bonus", "战斗");
    phoenix.put("attack", 80);
    phoenix.put("defense", 70);
    phoenix.put("hp", 250);
    pets.put("凤凰", phoenix);
    
    java.util.Map unicorn = new java.util.HashMap();
    unicorn.put("price", 40000);
    unicorn.put("income", 150);
    unicorn.put("bonus", "市场");
    unicorn.put("attack", 60);
    unicorn.put("defense", 80);
    unicorn.put("hp", 220);
    pets.put("独角兽", unicorn);
}

java.util.Map houses = new java.util.HashMap();
{
    java.util.Map cottage = new java.util.HashMap();
    cottage.put("price", 10000);
    cottage.put("income", 100);
    cottage.put("slots", 3);
    houses.put("小木屋", cottage);
    
    java.util.Map villa = new java.util.HashMap();
    villa.put("price", 50000);
    villa.put("income", 500);
    villa.put("slots", 5);
    houses.put("豪华别墅", villa);
    
    java.util.Map castle = new java.util.HashMap();
    castle.put("price", 200000);
    castle.put("income", 2000);
    castle.put("slots", 8);
    houses.put("皇家城堡", castle);
    
    java.util.Map mansion = new java.util.HashMap();
    mansion.put("price", 150000);
    mansion.put("income", 1200);
    mansion.put("slots", 6);
    houses.put("海滨庄园", mansion);
    
    java.util.Map treehouse = new java.util.HashMap();
    treehouse.put("price", 80000);
    treehouse.put("income", 300);
    treehouse.put("slots", 4);
    houses.put("精灵树屋", treehouse);
}

java.util.Map careers = new java.util.HashMap();
{
    java.util.Map warrior = new java.util.HashMap();
    warrior.put("bonus", "打劫");
    warrior.put("effect", "打劫成功率提高20%");
    warrior.put("price", 5000);
    careers.put("战士", warrior);
    
    java.util.Map merchant = new java.util.HashMap();
    merchant.put("bonus", "打工");
    merchant.put("effect", "打工收入提高30%");
    merchant.put("price", 5000);
    careers.put("商人", merchant);
    
    java.util.Map fisherman = new java.util.HashMap();
    fisherman.put("bonus", "钓鱼");
    fisherman.put("effect", "钓鱼收益提高40%");
    fisherman.put("price", 5000);
    careers.put("渔夫", fisherman);
    
    java.util.Map banker = new java.util.HashMap();
    banker.put("bonus", "银行");
    banker.put("effect", "银行利息提高50%");
    banker.put("price", 10000);
    careers.put("银行家", banker);
    
    java.util.Map investor = new java.util.HashMap();
    investor.put("bonus", "股票");
    investor.put("effect", "股票收益下限提高");
    investor.put("price", 15000);
    careers.put("投资人", investor);
    
    java.util.Map loanShark = new java.util.HashMap();
    loanShark.put("bonus", "贷款");
    loanShark.put("effect", "贷款利率降低30%");
    loanShark.put("price", 20000);
    careers.put("高利贷", loanShark);
}

java.util.Map fishTypes = new java.util.HashMap();
{
    java.util.Map smallFish = new java.util.HashMap();
    smallFish.put("min", 10);
    smallFish.put("max", 30);
    fishTypes.put("小黄鱼", smallFish);
    
    java.util.Map ribbonFish = new java.util.HashMap();
    ribbonFish.put("min", 20);
    ribbonFish.put("max", 50);
    fishTypes.put("带鱼", ribbonFish);
    
    java.util.Map tuna = new java.util.HashMap();
    tuna.put("min", 50);
    tuna.put("max", 100);
    fishTypes.put("金枪鱼", tuna);
    
    java.util.Map shark = new java.util.HashMap();
    shark.put("min", 100);
    shark.put("max", 200);
    fishTypes.put("鲨鱼", shark);
    
    java.util.Map goldenFish = new java.util.HashMap();
    goldenFish.put("min", 200);
    goldenFish.put("max", 500);
    fishTypes.put("金龙鱼", goldenFish);
    
    java.util.Map lobster = new java.util.HashMap();
    lobster.put("min", 150);
    lobster.put("max", 300);
    fishTypes.put("龙虾", lobster);
    
    java.util.Map crab = new java.util.HashMap();
    crab.put("min", 40);
    crab.put("max", 80);
    fishTypes.put("帝王蟹", crab);
    
    java.util.Map octopus = new java.util.HashMap();
    octopus.put("min", 70);
    octopus.put("max", 150);
    fishTypes.put("章鱼", octopus);
    
    java.util.Map squid = new java.util.HashMap();
    squid.put("min", 60);
    squid.put("max", 120);
    fishTypes.put("鱿鱼", squid);
    
    java.util.Map turtle = new java.util.HashMap();
    turtle.put("min", 80);
    turtle.put("max", 180);
    fishTypes.put("海龟", turtle);
    
    java.util.Map whale = new java.util.HashMap();
    whale.put("min", 300);
    whale.put("max", 600);
    fishTypes.put("鲸鱼", whale);
    
    java.util.Map seahorse = new java.util.HashMap();
    seahorse.put("min", 15);
    seahorse.put("max", 40);
    fishTypes.put("海马", seahorse);
    
    java.util.Map jellyfish = new java.util.HashMap();
    jellyfish.put("min", 25);
    jellyfish.put("max", 60);
    fishTypes.put("水母", jellyfish);
    
    java.util.Map stingray = new java.util.HashMap();
    stingray.put("min", 90);
    stingray.put("max", 180);
    fishTypes.put("魔鬼鱼", stingray);
    
    java.util.Map blueWhale = new java.util.HashMap();
    blueWhale.put("min", 500);
    blueWhale.put("max", 1000);
    fishTypes.put("蓝鲸", blueWhale);
    
    java.util.Map swordfish = new java.util.HashMap();
    swordfish.put("min", 120);
    swordfish.put("max", 250);
    fishTypes.put("剑鱼", swordfish);
    
    java.util.Map anglerfish = new java.util.HashMap();
    anglerfish.put("min", 80);
    anglerfish.put("max", 160);
    fishTypes.put("灯笼鱼", anglerfish);
    
    java.util.Map pufferfish = new java.util.HashMap();
    pufferfish.put("min", 50);
    pufferfish.put("max", 100);
    fishTypes.put("河豚", pufferfish);
    
    java.util.Map narwhal = new java.util.HashMap();
    narwhal.put("min", 200);
    narwhal.put("max", 400);
    fishTypes.put("独角鲸", narwhal);
    
    java.util.Map giantSquid = new java.util.HashMap();
    giantSquid.put("min", 400);
    giantSquid.put("max", 800);
    fishTypes.put("巨型乌贼", giantSquid);
    
    java.util.Map mantaRay = new java.util.HashMap();
    mantaRay.put("min", 150);
    mantaRay.put("max", 300);
    fishTypes.put("蝠鲼", mantaRay);
    
    java.util.Map nautilus = new java.util.HashMap();
    nautilus.put("min", 100);
    nautilus.put("max", 200);
    fishTypes.put("鹦鹉螺", nautilus);
    
    java.util.Map seaDragon = new java.util.HashMap();
    seaDragon.put("min", 300);
    seaDragon.put("max", 600);
    fishTypes.put("海龙", seaDragon);
    
    java.util.Map coralReef = new java.util.HashMap();
    coralReef.put("min", 200);
    coralReef.put("max", 500);
    fishTypes.put("珊瑚礁", coralReef);
    
    java.util.Map deepSeaVent = new java.util.HashMap();
    deepSeaVent.put("min", 1000);
    deepSeaVent.put("max", 2000);
    fishTypes.put("深海热泉", deepSeaVent);
    
    java.util.Map yeqi = new java.util.HashMap();
    yeqi.put("min", -100);
    yeqi.put("max", -100);
    fishTypes.put("夜七", yeqi);
    
    java.util.Map linjiang = new java.util.HashMap();
    linjiang.put("min", 1000);
    linjiang.put("max", 1000);
    fishTypes.put("临江", linjiang);
}

java.util.Map decorations = new java.util.HashMap();
{
    java.util.Map fountain = new java.util.HashMap();
    fountain.put("price", 5000);
    fountain.put("incomeBonus", 10);
    decorations.put("喷泉", fountain);
    
    java.util.Map garden = new java.util.HashMap();
    garden.put("price", 8000);
    garden.put("incomeBonus", 15);
    decorations.put("花园", garden);
    
    java.util.Map statue = new java.util.HashMap();
    statue.put("price", 12000);
    statue.put("incomeBonus", 20);
    decorations.put("雕像", statue);
    
    java.util.Map pool = new java.util.HashMap();
    pool.put("price", 15000);
    pool.put("incomeBonus", 25);
    decorations.put("游泳池", pool);
    
    java.util.Map observatory = new java.util.HashMap();
    observatory.put("price", 20000);
    observatory.put("incomeBonus", 30);
    decorations.put("天文台", observatory);
}

java.util.Map marketItems = new java.util.HashMap();
java.util.Map marketPrices = new java.util.HashMap();
long lastMarketRefresh = 0;

java.util.Map dailyTasks = new java.util.HashMap();
{
    java.util.Map workTask = new java.util.HashMap();
    workTask.put("name", "打工达人");
    workTask.put("goal", 3);
    workTask.put("reward", 500);
    dailyTasks.put("work", workTask);
    
    java.util.Map robTask = new java.util.HashMap();
    robTask.put("name", "打劫高手");
    robTask.put("goal", 2);
    robTask.put("reward", 800);
    dailyTasks.put("rob", robTask);
    
    java.util.Map fishTask = new java.util.HashMap();
    fishTask.put("name", "钓鱼大师");
    fishTask.put("goal", 5);
    fishTask.put("reward", 1000);
    dailyTasks.put("fish", fishTask);
    
    java.util.Map signTask = new java.util.HashMap();
    signTask.put("name", "签到打卡");
    signTask.put("goal", 1);
    signTask.put("reward", 300);
    dailyTasks.put("sign", signTask);
}

void refreshMarket() {
    long now = System.currentTimeMillis();
    if (now - lastMarketRefresh < MARKET_REFRESH) return;
    
    marketItems.clear();
    marketPrices.clear();
    
    java.util.Random rand = new java.util.Random();
    java.util.List weaponNames = new java.util.ArrayList(weapons.keySet());
    java.util.Collections.shuffle(weaponNames);
    
    for (int i = 0; i < 5; i++) {
        String weapon = (String) weaponNames.get(i);
        java.util.Map weaponData = (java.util.Map) weapons.get(weapon);
        int basePrice = (Integer) weaponData.get("price");
        int marketPrice = basePrice * (70 + rand.nextInt(61)) / 100;
        marketItems.put(weapon, "weapon");
        marketPrices.put(weapon, marketPrice);
    }
    
    java.util.List itemNames = new java.util.ArrayList(items.keySet());
    java.util.Collections.shuffle(itemNames);
    
    for (int i = 0; i < 5; i++) {
        String item = (String) itemNames.get(i);
        java.util.Map itemData = (java.util.Map) items.get(item);
        int basePrice = (Integer) itemData.get("price");
        int marketPrice = basePrice * (60 + rand.nextInt(81)) / 100;
        marketItems.put(item, "item");
        marketPrices.put(item, marketPrice);
    }
    
    lastMarketRefresh = now;
}

void handleWork(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastWork = getLongValue(group, "workTime_" + uin, 0L);
    String cooldownKey = "cd_work_" + uin;
    
    if (now - lastWork < WORK_COOLDOWN) {
        long remaining = lastWork + WORK_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "打工冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    int baseEarn = 100;
    if (hasBuff(group, uin, "经验卡")) {
        baseEarn *= 2;
    }
    
    String career = getCareer(group, uin);
    if ("商人".equals(career)) {
        baseEarn = (int)(baseEarn * 1.3);
    }
    
    int earned = baseEarn + (int)(Math.random() * 201);
    
    String pet = getPet(group, uin);
    if (pet != null && pet.equals("工作犬")) {
        earned += getPetIncome(group, uin);
    }
    
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + earned);
    setLongValue(group, "workTime_" + uin, now);
    setBooleanValue(group, cooldownKey, false);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 打工赚了" + earned + "金币，总金币:" + (currentGold + earned));
    
    updateDailyTaskProgress(group, uin, "work");
    checkAchievement(group, uin, "work", earned);
}

void handleRob(Object msg) {
    String robberUin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    if (hasShield(group, msg.mAtList)) {
        sendMsg(group, "", "对方有护盾保护，无法打劫！");
        return;
    }
    
    long now = System.currentTimeMillis();
    long lastRob = getLongValue(group, "robTime_" + robberUin, 0L);
    String cooldownKey = "cd_rob_" + robberUin;
    
    if (now - lastRob < ROB_COOLDOWN) {
        long remaining = lastRob + ROB_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "打劫冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要打劫的人");
        return;
    }
    
    String victimUin = (String) msg.mAtList.get(0);
    if (victimUin.equals(robberUin)) {
        sendMsg(group, "", "不能打劫自己");
        return;
    }
    
    int victimGold = getGold(group, victimUin);
    if (victimGold <= 0) {
        String victimName = getSafeMemberName(group, victimUin);
        sendMsg(group, "", victimName + " 没有金币，不值得打劫");
        return;
    }
    
    String robberWeaponName = getStringValue(group, "weapon_" + robberUin, "");
    String victimWeaponName = getStringValue(group, "weapon_" + victimUin, "");
    
    int robberAttack = 0;
    int victimDefense = 0;
    if (!robberWeaponName.isEmpty()) {
        java.util.Map robberWeapon = (java.util.Map) weapons.get(robberWeaponName);
        robberAttack = (Integer) robberWeapon.get("attack");
    }
    if (!victimWeaponName.isEmpty()) {
        java.util.Map victimWeapon = (java.util.Map) weapons.get(victimWeaponName);
        victimDefense = (Integer) victimWeapon.get("defense");
    }
    
    int baseRob = 25 + (int)(Math.random() * 46);
    
    String career = getCareer(group, robberUin);
    if ("战士".equals(career)) {
        baseRob = (int)(baseRob * 1.2);
    }
    
    int robbed = baseRob + robberAttack - victimDefense;
    if (robbed < 0) robbed = 0;
    
    boolean criticalHit = false;
    if (!robberWeaponName.isEmpty() && (robberWeaponName.equals("匕首") || 
        robberWeaponName.equals("手枪") || robberWeaponName.equals("散弹枪") || 
        robberWeaponName.equals("激光剑") || robberWeaponName.equals("火箭筒") ||
        robberWeaponName.equals("三叉戟") || robberWeaponName.equals("龙之剑") ||
        robberWeaponName.equals("光子冲击炮") || robberWeaponName.equals("毒液长矛") ||
        robberWeaponName.equals("天界神弓") || robberWeaponName.equals("死灵之书") ||
        robberWeaponName.equals("无限手套"))) {
        
        double criticalChance = 0.0;
        if (robberWeaponName.equals("匕首")) criticalChance = 0.1;
        else if (robberWeaponName.equals("手枪")) criticalChance = 0.2;
        else if (robberWeaponName.equals("散弹枪")) criticalChance = 0.3;
        else if (robberWeaponName.equals("激光剑")) criticalChance = 0.4;
        else if (robberWeaponName.equals("火箭筒")) criticalChance = 0.5;
        else if (robberWeaponName.equals("三叉戟")) criticalChance = 0.6;
        else if (robberWeaponName.equals("龙之剑")) criticalChance = 0.65;
        else if (robberWeaponName.equals("光子冲击炮")) criticalChance = 0.7;
        else if (robberWeaponName.equals("毒液长矛")) criticalChance = 0.55;
        else if (robberWeaponName.equals("天界神弓")) criticalChance = 0.75;
        else if (robberWeaponName.equals("死灵之书")) criticalChance = 0.8;
        else if (robberWeaponName.equals("无限手套")) criticalChance = 1.0;
        
        if (Math.random() < criticalChance) {
            robbed *= 2;
            criticalHit = true;
        }
    }
    
    boolean counterAttack = false;
    int counterLoss = 0;
    if (robberWeaponName.isEmpty() && !victimWeaponName.isEmpty()) {
        counterAttack = true;
        counterLoss = baseRob / 2;
    }
    
    int robberGold = getGold(group, robberUin);
    if (counterAttack) {
        if (robberGold < counterLoss) counterLoss = robberGold;
        setGold(group, robberUin, robberGold - counterLoss);
        setGold(group, victimUin, victimGold + counterLoss);
    } else {
        if (robbed > victimGold) robbed = victimGold;
        setGold(group, robberUin, robberGold + robbed);
        setGold(group, victimUin, victimGold - robbed);
    }
    
    setLongValue(group, "robTime_" + robberUin, now);
    setBooleanValue(group, cooldownKey, false);
    
    String robberName = getSafeMemberName(group, robberUin);
    String victimName = getSafeMemberName(group, victimUin);
    
    StringBuilder result = new StringBuilder();
    String[] battleEmojis = {"⚔️", "🔫", "💥", "✨", "🔥", "💢", "🛡️"};
    String emoji = battleEmojis[(int)(Math.random() * battleEmojis.length)];
    
    result.append(emoji).append(" ");
    if (counterAttack) {
        result.append(robberName).append(" 试图打劫 ")
              .append(victimName).append("，但被反击\n");
        result.append(victimName).append(" 使用 ").append(victimWeaponName)
              .append(" 反抢了 ").append(counterLoss).append("金币");
    } else {
        result.append(robberName);
        if (!robberWeaponName.isEmpty()) {
            result.append(" 使用 ").append(robberWeaponName);
        }
        result.append(" 打劫了 ").append(victimName);
        if (!victimWeaponName.isEmpty()) {
            result.append("(").append(victimWeaponName).append(")");
        }
        result.append("，抢走").append(robbed).append("金币");
        
        if (criticalHit) {
            result.append("\n暴击伤害");
        }
    }
    result.append(" ").append(emoji);
    
    sendMsg(group, "", result.toString());
    updateDailyTaskProgress(group, robberUin, "rob");
    checkAchievement(group, robberUin, "rob", robbed);
}

void handleFishing(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastFishing = getLongValue(group, "fishingTime_" + uin, 0L);
    String cooldownKey = "cd_fish_" + uin;
    
    if (now - lastFishing < FISHING_COOLDOWN) {
        long remaining = lastFishing + FISHING_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "钓鱼冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    Object[] fishNames = fishTypes.keySet().toArray();
    String caughtFish = (String) fishNames[(int)(Math.random() * fishNames.length)];
    java.util.Map fish = (java.util.Map) fishTypes.get(caughtFish);
    
    int minValue = (Integer) fish.get("min");
    int maxValue = (Integer) fish.get("max");
    int fishValue = minValue + (int)(Math.random() * (maxValue - minValue + 1));
    
    if (hasItem(group, uin, "高级鱼竿")) {
        fishValue = (int)(fishValue * 1.5);
    }
    
    String career = getCareer(group, uin);
    if ("渔夫".equals(career)) {
        fishValue = (int)(fishValue * 1.4);
    }
    
    String pet = getPet(group, uin);
    if (pet != null && pet.equals("招财猫")) {
        fishValue += getPetIncome(group, uin);
    }
    
    int currentGold = getGold(group, uin);
    
    if (caughtFish.equals("夜七")) {
        fishValue = -100;
        setGold(group, uin, currentGold + fishValue);
    } else if (caughtFish.equals("临江")) {
        fishValue = 1000;
        setGold(group, uin, currentGold + fishValue);
    } else {
        setGold(group, uin, currentGold + fishValue);
    }
    
    setLongValue(group, "fishingTime_" + uin, now);
    setBooleanValue(group, cooldownKey, false);
    
    String name = getSafeMemberName(group, uin);
    
    StringBuilder result = new StringBuilder();
    result.append(name).append(" 钓到了 ");
    
    if (caughtFish.equals("夜七")) {
        result.append("夜七！被罚款100金币 😭");
    } else if (caughtFish.equals("临江")) {
        result.append("临江！获得1000金币大奖 🎉");
    } else {
        result.append(caughtFish).append("，卖出获得").append(fishValue).append("金币");
    }
    
    if (caughtFish.equals("鲸鱼") || caughtFish.equals("蓝鲸")) {
        result.append("\n鲸鱼，超级大奖");
    } else if (caughtFish.equals("金龙鱼")) {
        result.append("\n金龙鱼，太幸运了");
    } else if (caughtFish.equals("深海热泉")) {
        result.append("\n发现深海宝藏！");
    } else if (caughtFish.equals("巨型乌贼")) {
        result.append("\n深海巨怪！");
    } else if (caughtFish.equals("独角鲸")) {
        result.append("\n稀有独角兽般的生物！");
    } else if (caughtFish.equals("海龙")) {
        result.append("\n传说中的海龙！");
    }
    
    sendMsg(group, "", result.toString());
    updateDailyTaskProgress(group, uin, "fish");
    checkAchievement(group, uin, "fish", fishValue);
}

void handleDungeon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastDungeon = getLongValue(group, "dungeonTime_" + uin, 0L);
    String cooldownKey = "cd_dungeon_" + uin;
    
    if (now - lastDungeon < DUNGEON_COOLDOWN) {
        long remaining = lastDungeon + DUNGEON_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "副本冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    String[] dungeons = {"巨龙巢穴", "海盗宝藏", "亡灵古墓", "天空之城", "深渊地牢"};
    String dungeon = dungeons[(int)(Math.random() * dungeons.length)];
    
    int baseReward = 500;
    int risk = (int)(Math.random() * 100);
    int result = 0;
    
    String pet = getPet(group, uin);
    if (pet != null && pet.equals("守护龙")) {
        baseReward += getPetIncome(group, uin);
    }
    
    boolean hasRevive = hasItem(group, uin, "复活药水");
    
    if (risk < 40) {
        result = -(int)(baseReward * 0.7);
        if (hasRevive) {
            result = (int)(result * 0.5);
            removeItem(group, uin, "复活药水");
        }
    } else {
        result = baseReward + risk * 10;
    }
    
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + result);
    setLongValue(group, "dungeonTime_" + uin, now);
    setBooleanValue(group, cooldownKey, false);
    
    String name = getSafeMemberName(group, uin);
    String outcome = result > 0 ? "成功挑战" : "挑战失败";
    
    StringBuilder sb = new StringBuilder();
    sb.append(name).append(" 探索[").append(dungeon).append("] ").append(outcome).append("，");
    
    if (result > 0) {
        sb.append("获得").append(result).append("金币");
    } else {
        sb.append("损失").append(-result).append("金币");
        if (hasRevive) {
            sb.append(" (复活药水减少了一半损失)");
        }
    }
    
    sendMsg(group, "", sb.toString());
    checkAchievement(group, uin, "dungeon", result);
}

void handleStock(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastStock = getLongValue(group, "stockTime_" + uin, 0L);
    String cooldownKey = "cd_stock_" + uin;
    
    if (now - lastStock < STOCK_COOLDOWN) {
        long remaining = lastStock + STOCK_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "股票冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    String[] stocks = {"金币矿业", "鱼市集团", "武器科技", "冒险公会", "魔法商店"};
    String stock = stocks[(int)(Math.random() * stocks.length)];
    
    double change = (Math.random() * 40) - 20;
    int investment = 1000;
    
    int profit = (int)(investment * change / 100);
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + profit);
    setLongValue(group, "stockTime_" + uin, now);
    setBooleanValue(group, cooldownKey, false);
    
    String name = getSafeMemberName(group, uin);
    String trend = profit > 0 ? "上涨" : "下跌";
    
    sendMsg(group, "", name + " 投资了" + stock + "，股票" + trend + Math.abs(change) + "%，" +
            (profit > 0 ? "赚取" : "亏损") + Math.abs(profit) + "金币");
    checkAchievement(group, uin, "stock", profit);
}

void handleArenaChallenge(Object msg) {
    String group = msg.GroupUin;
    String uin = msg.UserUin;
    
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastArena = getLongValue(group, "arena_time_" + uin, 0L);
    String cooldownKey = "cd_arena_" + uin;
    
    if (now - lastArena < ARENA_COOLDOWN) {
        long remaining = lastArena + ARENA_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "竞技场冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要挑战的对手");
        return;
    }
    
    String opponentUin = (String) msg.mAtList.get(0);
    if (opponentUin.equals(uin)) {
        sendMsg(group, "", "不能挑战自己");
        return;
    }
    
    int challengerPower = calculateCombatPower(group, uin);
    int opponentPower = calculateCombatPower(group, opponentUin);
    
    int winChance = 50;
    if (challengerPower > opponentPower) {
        winChance += (challengerPower - opponentPower) / 10;
    } else {
        winChance -= (opponentPower - challengerPower) / 10;
    }
    
    if (winChance < 10) winChance = 10;
    if (winChance > 90) winChance = 90;
    
    boolean win = (int)(Math.random() * 100) < winChance;
    int reward = 500 + (int)(Math.random() * 501);
    
    String challengerName = getSafeMemberName(group, uin);
    String opponentName = getSafeMemberName(group, opponentUin);
    
    StringBuilder result = new StringBuilder();
    result.append("⚔️ 竞技场挑战 ⚔️\n");
    result.append(challengerName).append(" 挑战 ").append(opponentName).append("\n");
    result.append("战力对比: ").append(challengerPower).append(" vs ").append(opponentPower).append("\n");
    
    if (win) {
        result.append(challengerName).append(" 获胜！获得").append(reward).append("金币");
        int currentGold = getGold(group, uin);
        setGold(group, uin, currentGold + reward);
        checkAchievement(group, uin, "arena", reward);
    } else {
        result.append(challengerName).append(" 失败！下次再接再厉");
    }
    
    setLongValue(group, "arena_time_" + uin, now);
    setBooleanValue(group, cooldownKey, false);
    sendMsg(group, "", result.toString());
}

void handleFeedPet(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String pet = getPet(group, uin);
    if (pet == null) {
        sendMsg(group, "", "你还没有宠物，无法喂养");
        return;
    }
    
    long now = System.currentTimeMillis();
    long lastFeed = getLongValue(group, "pet_feed_time_" + uin, 0L);
    String cooldownKey = "cd_petfeed_" + uin;
    
    if (now - lastFeed < PET_FEED_COOLDOWN) {
        long remaining = lastFeed + PET_FEED_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "宠物喂养冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    int foodCount = getIntValue(group, "item_宠物粮_" + uin, 0);
    if (foodCount <= 0) {
        sendMsg(group, "", "你没有宠物粮，无法喂养");
        return;
    }
    
    setIntValue(group, "item_宠物粮_" + uin, foodCount - 1);
    setIntValue(group, "pet_hunger_" + uin, 100);
    setLongValue(group, "pet_feed_time_" + uin, now);
    setBooleanValue(group, cooldownKey, false);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 喂养了 " + pet + "，宠物饱食度恢复");
}

void handleCollectHouseIncome(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    String house = getHouse(group, uin);
    if (house == null || house.isEmpty()) {
        sendMsg(group, "", "你还没有家园，无法收取收益");
        return;
    }
    
    if (!houses.containsKey(house)) {
        sendMsg(group, "", "你的家园配置有误，请联系管理员");
        return;
    }
    
    long now = System.currentTimeMillis();
    long lastCollect = getLongValue(group, "house_income_time_" + uin, 0L);
    String cooldownKey = "cd_house_" + uin;
    
    if (now - lastCollect < HOUSE_INCOME_COOLDOWN) {
        long remaining = lastCollect + HOUSE_INCOME_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "家园收益冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    java.util.Map houseData = (java.util.Map) houses.get(house);
    int income = (Integer) houseData.get("income");
    
    int decorBonus = getIntValue(group, "decor_bonus_" + uin, 0);
    income = (int)(income * (1 + decorBonus / 100.0));
    
    int currentGold = getGold(group, uin);
    setGold(group, uin, currentGold + income);
    setLongValue(group, "house_income_time_" + uin, now);
    setBooleanValue(group, cooldownKey, false);
    
    String name = getSafeMemberName(group, uin);
    sendMsg(group, "", name + " 收取了 " + house + " 的收益 " + income + "金币");
}

void handleUpgradeWeapon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastUpgrade = getLongValue(group, "upgrade_time_" + uin, 0L);
    String cooldownKey = "cd_upgrade_" + uin;
    
    if (now - lastUpgrade < EQUIP_UPGRADE_COOLDOWN) {
        long remaining = lastUpgrade + EQUIP_UPGRADE_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "强化冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    String weaponName = getStringValue(group, "weapon_" + uin, "");
    if (weaponName.isEmpty()) {
        sendMsg(group, "", "你还没有武器，无法强化");
        return;
    }
    
    int stoneCount = getIntValue(group, "item_强化石_" + uin, 0);
    if (stoneCount < 1) {
        sendMsg(group, "", "需要强化石x1");
        return;
    }
    
    java.util.Map weapon = (java.util.Map) weapons.get(weaponName);
    int currentLevel = getIntValue(group, "weapon_level_" + uin, 1);
    int maxLevel = (Integer) weapon.get("maxLevel");
    
    if (currentLevel >= maxLevel) {
        sendMsg(group, "", "武器已达到最高强化等级");
        return;
    }
    
    double successRate = 1.0 - (currentLevel * 0.05);
    if (successRate < 0.3) successRate = 0.3;
    
    boolean success = Math.random() < successRate;
    
    removeItem(group, uin, "强化石");
    setLongValue(group, "upgrade_time_" + uin, now);
    setBooleanValue(group, cooldownKey, false);
    
    String name = getSafeMemberName(group, uin);
    
    if (success) {
        setIntValue(group, "weapon_level_" + uin, currentLevel + 1);
        
        int successes = getIntValue(group, "upgrade_success_" + uin, 0);
        setIntValue(group, "upgrade_success_" + uin, successes + 1);
        
        sendMsg(group, "", name + " 成功将 " + weaponName + " 强化到 +" + (currentLevel + 1) + "!");
    } else {
        if (currentLevel > 1) {
            setIntValue(group, "weapon_level_" + uin, currentLevel - 1);
            sendMsg(group, "", name + " 强化失败！" + weaponName + " 降级到 +" + (currentLevel - 1) + " 😭");
        } else {
            sendMsg(group, "", name + " 强化失败！幸运的是武器没有降级");
        }
    }
}

void handlePetBattle(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastBattle = getLongValue(group, "pet_battle_time_" + uin, 0L);
    String cooldownKey = "cd_petbattle_" + uin;
    
    if (now - lastBattle < PET_BATTLE_COOLDOWN) {
        long remaining = lastBattle + PET_BATTLE_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "宠物对战冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    String pet = getPet(group, uin);
    if (pet == null || pet.isEmpty() || !pets.containsKey(pet)) {
        sendMsg(group, "", "你还没有宠物，无法对战");
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.isEmpty()) {
        sendMsg(group, "", "请@要对战的玩家");
        return;
    }
    
    String opponentUin = (String) msg.mAtList.get(0);
    String opponentPet = getPet(group, opponentUin);
    if (opponentPet == null || opponentPet.isEmpty() || !pets.containsKey(opponentPet)) {
        sendMsg(group, "", "对方没有宠物");
        return;
    }
    
    java.util.Map yourPet = (java.util.Map) pets.get(pet);
    java.util.Map oppPet = (java.util.Map) pets.get(opponentPet);
    if (yourPet == null || oppPet == null) {
        sendMsg(group, "", "宠物数据错误，无法对战");
        return;
    }
    
    int yourLevel = getIntValue(group, "pet_level_" + uin, 1);
    int oppLevel = getIntValue(group, "pet_level_" + opponentUin, 1);
    
    int yourHP = (Integer) yourPet.get("hp") * yourLevel;
    int yourAttack = (Integer) yourPet.get("attack") * yourLevel;
    int yourDefense = (Integer) yourPet.get("defense") * yourLevel;
    
    int oppHP = (Integer) oppPet.get("hp") * oppLevel;
    int oppAttack = (Integer) oppPet.get("attack") * oppLevel;
    int oppDefense = (Integer) oppPet.get("defense") * oppLevel;
    
    StringBuilder battleLog = new StringBuilder();
    battleLog.append("🐾 宠物对战开始 🐾\n");
    battleLog.append(getSafeMemberName(group, uin)).append(" 的 ").append(pet).append(" Lv.").append(yourLevel)
             .append(" vs ")
             .append(getSafeMemberName(group, opponentUin)).append(" 的 ").append(opponentPet).append(" Lv.").append(oppLevel).append("\n");
    
    int round = 1;
    while (yourHP > 0 && oppHP > 0 && round <= 10) {
        int yourDamage = yourAttack - oppDefense;
        if (yourDamage < 1) yourDamage = 1;
        oppHP -= yourDamage;
        
        int oppDamage = oppAttack - yourDefense;
        if (oppDamage < 1) oppDamage = 1;
        yourHP -= oppDamage;
        
        battleLog.append("回合").append(round).append(": ")
                 .append(pet).append(" 造成 ").append(yourDamage).append(" 伤害, ")
                 .append(opponentPet).append(" 造成 ").append(oppDamage).append(" 伤害\n");
        round++;
    }
    
    boolean youWin = oppHP <= 0;
    int reward = 300 + (int)(Math.random() * 201);
    
    if (youWin) {
        battleLog.append(pet).append(" 获胜！");
        setGold(group, uin, getGold(group, uin) + reward);
        battleLog.append(getSafeMemberName(group, uin)).append(" 获得 ").append(reward).append("金币");
    } else {
        battleLog.append(opponentPet).append(" 获胜！");
        setGold(group, opponentUin, getGold(group, opponentUin) + reward);
        battleLog.append(getSafeMemberName(group, opponentUin)).append(" 获得 ").append(reward).append("金币");
    }
    
    setLongValue(group, "pet_battle_time_" + uin, now);
    setLongValue(group, "pet_battle_time_" + opponentUin, now);
    setBooleanValue(group, cooldownKey, false);
    sendMsg(group, "", battleLog.toString());
}

void handleTeamDungeon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastDungeon = getLongValue(group, "team_dungeon_time_" + uin, 0L);
    String cooldownKey = "cd_teamdungeon_" + uin;
    
    if (now - lastDungeon < TEAM_DUNGEON_COOLDOWN) {
        long remaining = lastDungeon + TEAM_DUNGEON_COOLDOWN - now;
        if (!getBooleanValue(group, cooldownKey, false)) {
            sendMsg(group, "", "团队副本冷却中，请等待" + formatCoolDown(remaining));
            setBooleanValue(group, cooldownKey, true);
        }
        return;
    }
    
    if (msg.mAtList == null || msg.mAtList.size() < 2) {
        sendMsg(group, "", "请@至少2名队友");
        return;
    }
    
    java.util.List team = new java.util.ArrayList();
    team.add(uin);
    team.addAll(msg.mAtList);
    
    if (team.size() > 5) {
        sendMsg(group, "", "团队最多5人");
        return;
    }
    
    int totalPower = 0;
    for (Object memberUinObj : team) {
        String memberUin = (String) memberUinObj;
        totalPower += calculateCombatPower(group, memberUin);
    }
    
    String[] dungeons = {"巨龙巢穴", "亡灵要塞", "深渊魔窟", "天空圣殿"};
    String dungeon = dungeons[(int)(Math.random() * dungeons.length)];
    
    int baseReward = 2000;
    int difficulty = (int)(Math.random() * 100) + 1;
    boolean success = totalPower > difficulty * 50;
    
    StringBuilder result = new StringBuilder();
    result.append("👥 团队副本挑战: ").append(dungeon).append("\n");
    result.append("团队成员: ");
    for (int i = 0; i < team.size(); i++) {
        String memberUin = (String) team.get(i);
        result.append(getSafeMemberName(group, memberUin));
        if (i < team.size() - 1) {
            result.append(", ");
        }
    }
    result.append("\n团队总战力: ").append(totalPower).append("\n");
    
    if (success) {
        int reward = baseReward + totalPower;
        result.append("挑战成功！每人获得").append(reward).append("金币");
        
        for (Object memberUinObj : team) {
            String memberUin = (String) memberUinObj;
            setGold(group, memberUin, getGold(group, memberUin) + reward);
            setLongValue(group, "team_dungeon_time_" + memberUin, now);
            setBooleanValue(group, "cd_teamdungeon_" + memberUin, false);
            
            int count = getIntValue(group, "team_dungeon_" + memberUin, 0);
            setIntValue(group, "team_dungeon_" + memberUin, count + 1);
        }
    } else {
        result.append("挑战失败！下次再接再厉");
        for (Object memberUinObj : team) {
            String memberUin = (String) memberUinObj;
            setLongValue(group, "team_dungeon_time_" + memberUin, now);
            setBooleanValue(group, "cd_teamdungeon_" + memberUin, false);
        }
    }
    
    sendMsg(group, "", result.toString());
}
    
int calculateCombatPower(String group, String uin) {
    int power = 0;
    
    String weapon = getStringValue(group, "weapon_" + uin, "");
    if (!weapon.isEmpty()) {
        java.util.Map weaponData = (java.util.Map) weapons.get(weapon);
        int level = getIntValue(group, "weapon_level_" + uin, 1);
        power += (Integer) weaponData.get("attack") * level;
        power += (Integer) weaponData.get("defense") * level;
    }
    
    String career = getCareer(group, uin);
    if (career != null) {
        power += 50;
    }
    
    String pet = getPet(group, uin);
    if (pet != null && pets.containsKey(pet)) {
        int petLevel = getIntValue(group, "pet_level_" + uin, 1);
        java.util.Map petData = (java.util.Map) pets.get(pet);
        power += (Integer) petData.get("attack") * petLevel;
        power += (Integer) petData.get("defense") * petLevel;
    }
    
    String house = getHouse(group, uin);
    if (house != null) {
        power += 20;
    }
    
    int decorBonus = getIntValue(group, "decor_bonus_" + uin, 0);
    power += decorBonus;
    
    return power;
}

void handleMyWeapon(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    String weaponName = getStringValue(group, "weapon_" + uin, "");
    int weaponLevel = getIntValue(group, "weapon_level_" + uin, 1);
    
    if (weaponName.isEmpty()) {
        sendMsg(group, "", "你还没有武器，发送「武器商店」购买武器");
        return;
    }
    
    java.util.Map weapon = (java.util.Map) weapons.get(weaponName);
    int baseAttack = (Integer) weapon.get("attack");
    int baseDefense = (Integer) weapon.get("defense");
    int maxLevel = (Integer) weapon.get("maxLevel");
    
    int currentAttack = baseAttack * weaponLevel;
    int currentDefense = baseDefense * weaponLevel;
    
    StringBuilder sb = new StringBuilder();
    sb.append("你的武器: ").append(weaponName).append(" +").append(weaponLevel).append("\n");
    sb.append("攻击力: ").append(currentAttack).append(" (基础").append(baseAttack).append(")\n");
    sb.append("防御力: ").append(currentDefense).append(" (基础").append(baseDefense).append(")\n");
    sb.append("强化上限: ").append(maxLevel).append("级\n");
    sb.append("特殊效果: ");
    
    if (weaponName.equals("匕首") || weaponName.equals("手枪") || 
        weaponName.equals("散弹枪") || weaponName.equals("激光剑") || 
        weaponName.equals("火箭筒") || weaponName.equals("三叉戟") || 
        weaponName.equals("龙之剑") || weaponName.equals("光子冲击炮") || 
        weaponName.equals("毒液长矛") || weaponName.equals("天界神弓") || 
        weaponName.equals("死灵之书") || weaponName.equals("无限手套")) {
        sb.append("攻击时有概率暴击(双倍伤害)");
    } else if (weaponName.equals("铠甲") || weaponName.equals("防弹衣") || 
               weaponName.equals("泰坦之盾")) {
        sb.append("减少受到的伤害");
    } else {
        sb.append("无");
    }
    
    sendMsg(group, "", sb.toString());
}

void handleProfile(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    
    int gold = getGold(group, uin);
    int deposit = getIntValue(group, "deposit_" + uin, 0);
    String weaponName = getStringValue(group, "weapon_" + uin, "");
    int weaponLevel = getIntValue(group, "weapon_level_" + uin, 1);
    int defense = 0;
    int attack = 0;
    
    if (!weaponName.isEmpty()) {
        java.util.Map weapon = (java.util.Map) weapons.get(weaponName);
        defense = (Integer) weapon.get("defense") * weaponLevel;
        attack = (Integer) weapon.get("attack") * weaponLevel;
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append("个人信息\n");
    sb.append("金币: ").append(gold).append("\n");
    sb.append("存款: ").append(deposit).append("\n");
    sb.append("武器: ").append(weaponName.isEmpty() ? "无" : weaponName + "+" + weaponLevel).append("\n");
    sb.append("攻击: ").append(attack).append(" | 防御: ").append(defense).append("\n");
    
    String career = getCareer(group, uin);
    if (career != null) {
        sb.append("职业: ").append(career).append("\n");
    }
    
    String pet = getPet(group, uin);
    if (pet != null && pets.containsKey(pet)) {
        int hunger = getIntValue(group, "pet_hunger_" + uin, 100);
        int petLevel = getIntValue(group, "pet_level_" + uin, 1);
        sb.append("宠物: ").append(pet).append(" Lv.").append(petLevel)
          .append(" | 饱食度: ").append(hunger).append("\n");
    }
    
    String house = getHouse(group, uin);
    if (house != null) {
        long lastCollect = getLongValue(group, "house_income_time_" + uin, 0L);
        long nextCollect = lastCollect + HOUSE_INCOME_COOLDOWN;
        long remain = nextCollect - System.currentTimeMillis();
        
        if (remain > 0) {
            sb.append("家园: ").append(house).append(" | 收益冷却: ").append(formatCoolDown(remain)).append("\n");
        } else {
            sb.append("家园: ").append(house).append(" | 可收取收益\n");
        }
        
        int decorCount = getIntValue(group, "decor_count_" + uin, 0);
        int decorBonus = getIntValue(group, "decor_bonus_" + uin, 0);
        sb.append("装饰: ").append(decorCount).append("个 | 收益加成: ").append(decorBonus).append("%\n");
    }
    
    if (hasBuff(group, uin, "经验卡")) {
        sb.append("经验卡: 生效中\n");
    }
    
    if (hasShield(group, uin)) {
        sb.append("护盾: 生效中\n");
    }
    
    String achievements = getStringValue(group, "achievements_" + uin, "");
    if (!achievements.isEmpty()) {
        sb.append("成就: ").append(achievements).append("\n");
    }
    
    String title = getStringValue(group, "title_" + uin, "");
    if (!title.isEmpty()) {
        sb.append("称号: ").append(title).append("\n");
    }
    
    int consecutiveDays = getIntValue(group, "consecutiveDays_" + uin, 0);
    sb.append("连续签到: ").append(consecutiveDays).append("天\n");
    
    int power = calculateCombatPower(group, uin);
    sb.append("综合战力: ").append(power);
    
    sendMsg(group, "", sb.toString());
}

void handleAlms(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastAlms = getLongValue(group, "almsTime_" + uin, 0L);
    
    if (now - lastAlms < ALMS_COOLDOWN) {
        sendMsg(group, "", "施舍冷却中，请等待" + formatCoolDown(lastAlms + ALMS_COOLDOWN - now));
        return;
    }
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    
    try {
        if (parts.length > 1) {
            amount = Integer.parseInt(parts[1]);
        }
    } catch (Exception e) {
        sendMsg(group, "", "请指定有效的金币数量");
        return;
    }
    
    if (amount <= 0) {
        sendMsg(group, "", "金币数量必须大于0");
        return;
    }
    
    int senderGold = getGold(group, uin);
    if (senderGold < amount) {
        sendMsg(group, "", "你的金币不足");
        return;
    }
    
    java.util.List members = getGroupMemberList(group);
    if (members == null || members.size() < 3) {
        sendMsg(group, "", "群成员不足，无法施舍");
        return;
    }
    
    java.util.List receivers = new java.util.ArrayList();
    java.util.Random random = new java.util.Random();
    
    while (receivers.size() < 3) {
        int index = random.nextInt(members.size());
        Object member = members.get(index);
        String receiverUin = (String) member.UserUin;
        if (!receiverUin.equals(uin)) {
            receivers.add(receiverUin);
        }
    }
    
    int perPerson = amount / 3;
    if (perPerson < 1) {
        sendMsg(group, "", "施舍金额太少，每人至少1金币");
        return;
    }
    
    setGold(group, uin, senderGold - amount);
    
    for (Object receiverUinObj : receivers) {
        String receiverUin = (String) receiverUinObj;
        int currentGold = getGold(group, receiverUin);
        setGold(group, receiverUin, currentGold + perPerson);
    }
    
    setLongValue(group, "almsTime_" + uin, now);
    
    String senderName = getSafeMemberName(group, uin);
    StringBuilder result = new StringBuilder();
    result.append(senderName).append(" 施舍了 ").append(amount).append("金币\n");
    result.append("受赠者: ");
    
    for (int i = 0; i < receivers.size(); i++) {
        String receiverUin = (String) receivers.get(i);
        String receiverName = getSafeMemberName(group, receiverUin);
        result.append(receiverName);
        if (i < receivers.size() - 1) {
            result.append(", ");
        }
    }
    
    result.append("\n每人获得: ").append(perPerson).append("金币");
    
    sendMsg(group, "", result.toString());
    checkAchievement(group, uin, "alms", amount);
}

void handleRedPacket(Object msg) {
    String uin = msg.UserUin;
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    long now = System.currentTimeMillis();
    long lastRedPacket = getLongValue(group, "redPacketTime_" + uin, 0L);
    
    if (now - lastRedPacket < REDPACKET_COOLDOWN) {
        sendMsg(group, "", "发红包冷却中，请等待" + formatCoolDown(lastRedPacket + REDPACKET_COOLDOWN - now));
        return;
    }
    
    String text = msg.MessageContent.trim();
    String[] parts = text.split(" ");
    int amount = 0;
    int count = 0;
    
    try {
        if (parts.length > 1) amount = Integer.parseInt(parts[1]);
        if (parts.length > 2) count = Integer.parseInt(parts[2]);
    } catch (Exception e) {
        sendMsg(group, "", "请使用「发红包 金额 人数」格式");
        return;
    }
    
    if (amount <= 0 || count <= 0) {
        sendMsg(group, "", "金额和人数必须大于0");
        return;
    }
    
    if (count < 2 || count > 10) {
        sendMsg(group, "", "红包人数必须在2-10人之间");
        return;
    }
    
    int senderGold = getGold(group, uin);
    if (senderGold < amount) {
        sendMsg(group, "", "你的金币不足");
        return;
    }
    
    java.util.List members = getGroupMemberList(group);
    if (members == null || members.size() < count) {
        sendMsg(group, "", "群成员不足");
        return;
    }
    
    java.util.List receivers = new java.util.ArrayList();
    java.util.Random random = new java.util.Random();
    
    while (receivers.size() < count) {
        int index = random.nextInt(members.size());
        Object member = members.get(index);
        String receiverUin = (String) member.UserUin;
        if (!receiverUin.equals(uin)) {
            receivers.add(receiverUin);
        }
    }
    
    int[] amounts = new int[count];
    int remaining = amount;
    
    for (int i = 0; i < count - 1; i++) {
        int max = remaining - (count - i - 1);
        int min = 1;
        if (max <= min) max = min + 1;
        
        int part = min + random.nextInt(max - min);
        amounts[i] = part;
        remaining -= part;
    }
    amounts[count - 1] = remaining;
    
    setGold(group, uin, senderGold - amount);
    
    StringBuilder result = new StringBuilder();
    result.append(getSafeMemberName(group, uin)).append(" 发送了红包\n");
    result.append("总金额: ").append(amount).append("金币 | 份马 数: ").append(count).append("\n");
    
    for (int i = 0; i < count; i++) {
        String receiverUin = (String) receivers.get(i);
        int gold = amounts[i];
        int currentGold = getGold(group, receiverUin);
        setGold(group, receiverUin, currentGold + gold);
        
        String receiverName = getSafeMemberName(group, receiverUin);
        result.append(receiverName).append(" 获得 ").append(gold).append("金币\n");
    }
    
    setLongValue(group, "redPacketTime_" + uin, now);
    sendMsg(group, "", result.toString());
}

void handleRank(Object msg) {
    String group = msg.GroupUin;
    if (!isEnabled(group)) return;
    
    java.util.Properties props = loadGroupData(group);
    java.util.Map goldMap = new java.util.HashMap();
    
    for (Object keyObj : props.keySet()) {
        String key = (String) keyObj;
        if (key.startsWith("gold_")) {
            String uin = key.substring(5);
            try {
                int gold = Integer.parseInt(props.getProperty(key));
                if (gold > 0) {
                    goldMap.put(uin, gold);
                }
            } catch (Exception e) {
            }
        }
    }
    
    if (goldMap.isEmpty()) {
        sendMsg(group, "", "暂无排行榜数据");
        return;
    }
    
    java.util.List list = new java.util.ArrayList(goldMap.entrySet());
    
    java.util.Collections.sort(list, new java.util.Comparator() {
        public int compare(Object o1, Object o2) {
            java.util.Map.Entry e1 = (java.util.Map.Entry) o1;
            java.util.Map.Entry e2 = (java.util.Map.Entry) o2;
            int v1 = (Integer) e1.getValue();
            int v2 = (Integer) e2.getValue();
            return v2 - v1;
        }
    });
    
    StringBuilder sb = new StringBuilder();
    sb.append("金币排行榜\n");
    int rank = 1;
    
    for (Object entryObj : list) {
        if (rank > 10) break;
        java.util.Map.Entry entry = (java.util.Map.Entry) entryObj;
        String uin = (String) entry.getKey();
        int gold = (Integer) entry.getValue();
        String name = getSafeMemberName(group, uin);
        
        String weaponName = getStringValue(group, "weapon_" + uin, "");
        String weaponInfo = weaponName.isEmpty() ? "" : "[" + weaponName + "]";
        
        sb.append(rank).append(". ")
          .append(name).append(weaponInfo).append("(").append(uin).append(") : ")
          .append(gold).append("金币\n");
        rank++;
    }
    
    sendMsg(group, "", sb.toString());
}

void checkAchievement(String group, String uin, String type, int value) {
    java.util.Map achievements = new java.util.HashMap();
    achievements.put("rich", "大富翁");
    achievements.put("robber", "江洋大盗");
    achievements.put("fisher", "钓鱼大师");
    achievements.put("giver", "慈善家");
    achievements.put("worker", "打工人");
    achievements.put("homeowner", "房产大亨");
    achievements.put("petlover", "宠物达人");
    achievements.put("arena", "竞技之王");
    achievements.put("banker", "金融巨鳄");
    achievements.put("trader", "市场专家");
    achievements.put("blacksmith", "锻造大师");
    achievements.put("teamplayer", "团队之星");
    
    int gold = getGold(group, uin);
    int deposit = getIntValue(group, "deposit_" + uin, 0);
    int totalWealth = gold + deposit;
    String currentAchs = getStringValue(group, "achievements_" + uin, "");
    
    if (totalWealth >= 100000 && currentAchs.indexOf("大富翁") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "大富翁";
        setStringValue(group, "title_" + uin, "大富豪");
        sendMsg(group, "", "成就解锁: 大富翁！获得称号「大富豪」");
    }
    
    if (type.equals("rob") && value >= 500 && currentAchs.indexOf("江洋大盗") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "江洋大盗";
        setStringValue(group, "title_" + uin, "江洋大盗");
        sendMsg(group, "", "成就解锁: 江洋大盗！获得称号「江洋大盗」");
    }
    
    if (type.equals("fish") && value >= 500 && currentAchs.indexOf("钓鱼大师") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "钓鱼大师";
        setStringValue(group, "title_" + uin, "钓鱼宗师");
        sendMsg(group, "", "成就解锁: 钓鱼大师！获得称号「钓鱼宗师」");
    }
    
    if (type.equals("alms") && value >= 1000 && currentAchs.indexOf("慈善家") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "慈善家";
        setStringValue(group, "title_" + uin, "慈善大使");
        sendMsg(group, "", "成就解锁: 慈善家！获得称号「慈善大使」");
    }
    
    if (type.equals("work") && value >= 250 && currentAchs.indexOf("打工人") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "打工人";
        setStringValue(group, "title_" + uin, "勤劳标兵");
        sendMsg(group, "", "成就解锁: 打工人！获得称号「勤劳标兵」");
    }
    
    if (getHouse(group, uin) != null && currentAchs.indexOf("房产大亨") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "房产大亨";
        setStringValue(group, "title_" + uin, "地产大亨");
        sendMsg(group, "", "成就解锁: 房产大亨！获得称号「地产大亨」");
    }
    
    if (getPet(group, uin) != null && currentAchs.indexOf("宠物达人") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "宠物达人";
        setStringValue(group, "title_" + uin, "宠物专家");
        sendMsg(group, "", "成就解锁: 宠物达人！获得称号「宠物专家」");
    }
    
    if (type.equals("arena") && value >= 1000 && currentAchs.indexOf("竞技之王") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "竞技之王";
        setStringValue(group, "title_" + uin, "竞技场冠军");
        sendMsg(group, "", "成就解锁: 竞技之王！获得称号「竞技场冠军」");
    }
    
    if (deposit >= 50000 && currentAchs.indexOf("金融巨鳄") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "金融巨鳄";
        setStringValue(group, "title_" + uin, "金融巨头");
        sendMsg(group, "", "成就解锁: 金融巨鳄！获得称号「金融巨头」");
    }
    
    if (getIntValue(group, "market_sales_" + uin, 0) >= 10 && currentAchs.indexOf("市场专家") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "市场专家";
        setStringValue(group, "title_" + uin, "贸易大师");
        sendMsg(group, "", "成就解锁: 市场专家！获得称号「贸易大师」");
    }
    
    if (getIntValue(group, "upgrade_success_" + uin, 0) >= 20 && currentAchs.indexOf("锻造大师") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "锻造大师";
        setStringValue(group, "title_" + uin, "神匠");
        sendMsg(group, "", "成就解锁: 锻造大师！获得称号「神匠」");
    }
    
    if (getIntValue(group, "team_dungeon_" + uin, 0) >= 5 && currentAchs.indexOf("团队之星") == -1) {
        currentAchs += (currentAchs.isEmpty() ? "" : ",") + "团队之星";
        setStringValue(group, "title_" + uin, "团队领袖");
        sendMsg(group, "", "成就解锁: 团队之星！获得称号「团队领袖」");
    }
    
    if (!currentAchs.equals(getStringValue(group, "achievements_" + uin, ""))) {
        setStringValue(group, "achievements_" + uin, currentAchs);
    }
}

boolean hasBuff(String group, String uin, String buff) {
    long expire = getLongValue(group, "buff_" + buff + "_" + uin, 0L);
    return expire > System.currentTimeMillis();
}

boolean hasShield(String group, String uin) {
    long expire = getLongValue(group, "shield_" + uin, 0L);
    return expire > System.currentTimeMillis();
}