package net.illusion.regionafk.data;

import net.illusion.regionafk.scheduler.AfkDetect;

import java.util.HashMap;
import java.util.UUID;

public class AfkMapData {
    public static HashMap<UUID, AfkDetect> scheduler = new HashMap<>();
}
