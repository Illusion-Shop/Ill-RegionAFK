package net.illusion.regionafk.data;

import net.illusion.core.data.Config;
import net.illusion.regionafk.RegionAfkPlugin;
import org.bukkit.entity.Player;

public class AfkShop {

    private String name;

    private byte line;

    private Config config;

    public AfkShop(String name) {
        this.name = name;
        this.config = new Config("shop/" + name);
        this.config.setPlugin(RegionAfkPlugin.getPlugin());
    }

    public void create(Player player) {

    }

    public void delete(Player player){

    }

    public void edit(Player player){

    }


    public void setLine(byte line) {
        this.config.setByte("line", line);
    }

    public void rename(String newName){

    }

    public int getLIne() {
        return config.getInt("line");
    }
}
