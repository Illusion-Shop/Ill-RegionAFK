package net.illusion.regionafk.scheduler;

import lombok.Getter;
import net.illusion.core.util.location.Region;
import net.illusion.core.util.time.Time;
import net.illusion.regionafk.data.AfkArea;
import net.illusion.regionafk.data.AfkMapData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AfkDetect extends BukkitRunnable {

    private Time time;

    private Player player;

    @Getter
    private AfkArea afkArea;

    public AfkDetect(Player player, AfkArea afkArea) {
        this.afkArea = afkArea;
        time = new Time(0);
        this.player = player;
    }

    @Override
    public void run() {
        time.addSeconds(1);

        Region region = new Region(afkArea.getLocations().get(0), afkArea.getLocations().get(1));
        if (region.locationIsInRegion(player.getLocation())) {
            if (time.getMillSeconds() == afkArea.getStartTime().getMillSeconds()) {
                AfkMapData.scheduler.put(player.getUniqueId(), this);
                player.sendMessage("잠수가 시작됩니다.");
            }
        } else {
            afkArea.removePlayer(player);
            AfkMapData.scheduler.remove(player.getUniqueId());
            cancel();
        }

    }
}
