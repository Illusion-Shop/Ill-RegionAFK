package net.illusion.regionafk.data;

import net.illusion.core.data.Config;
import net.illusion.regionafk.RegionAfkPlugin;

public class AfkShop {

    private String name;

    private Config config;

    public AfkShop(String name) {
        this.name = name;
        this.config = new Config("shop/" + name);
        this.config.setPlugin(RegionAfkPlugin.getPlugin());
    }

    public void create() {
        
    }
}
