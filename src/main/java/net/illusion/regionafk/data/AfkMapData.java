package net.illusion.regionafk.data;

import net.illusion.regionafk.gui.AfkShop;
import net.illusion.regionafk.gui.AfkShopSetting;
import net.illusion.regionafk.scheduler.AfkDetect;

import java.util.HashMap;
import java.util.UUID;

public class AfkMapData {
    public static HashMap<UUID, AfkDetect> schedulerMap = new HashMap<>();

    public static HashMap<UUID, AfkShop> shopMap = new HashMap<>();

    public static HashMap<UUID, AfkShopSetting> shopSettingMap = new HashMap<>();
}
