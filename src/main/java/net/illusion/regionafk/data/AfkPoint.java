package net.illusion.regionafk.data;

import net.illusion.core.data.Config;
import net.illusion.regionafk.RegionAfkPlugin;
import org.bukkit.OfflinePlayer;

public class AfkPoint {

    private OfflinePlayer offlinePlayer;

    private Config config;

    public AfkPoint(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        config = new Config("data/" + offlinePlayer.getUniqueId());
        config.setPlugin(RegionAfkPlugin.getPlugin());
    }

    public void setPoint(long amount) {
        config.setLong("point", amount);
    }

    public boolean deposit(long amount){
        if(getPoint() - amount > 0){
            config.setLong("point", getPoint() - amount);
            return true;
        }
        return false;
    }
    public boolean withdraw(long amount){
        if(getPoint() - amount > 0){
            config.setLong("point", getPoint() - amount);
            return true;
        }
        return false;
    }

    public long getPoint() {
        return config.getLong("point");
    }
}
