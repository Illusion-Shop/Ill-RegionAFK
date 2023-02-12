package net.illusion.regionafk.event;

import net.illusion.core.util.location.Region;
import net.illusion.regionafk.RegionAfkPlugin;
import net.illusion.regionafk.data.AfkArea;

import net.illusion.regionafk.data.AfkMapData;
import net.illusion.regionafk.events.AfkDetectEvent;
import net.illusion.regionafk.scheduler.AfkDetect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;


public class RegionAfkListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (AfkMapData.scheduler.containsKey(player.getUniqueId())) {
            AfkDetectEvent afkDetectEvent = new AfkDetectEvent(player);
            Bukkit.getPluginManager().callEvent(afkDetectEvent);
            return;
        }

        for (String name : RegionAfkPlugin.folder.fileListName()) {
            AfkArea afkArea = new AfkArea(name);

            Region region = new Region(afkArea.getLocations().get(0), afkArea.getLocations().get(1));

            if (!region.locationIsInRegion(player.getLocation())) {
                if (!afkArea.contains(player)) { // afk 안에 플레이어가 없을 시에 계속 진행한다.
                    continue;
                }
            }

            if (afkArea.addPlayer(player)) {
                AfkDetect afkDetect = new AfkDetect(player, afkArea);

                afkDetect.runTaskTimer(RegionAfkPlugin.getPlugin(), 0, 20);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player player) {
            System.out.println(AfkMapData.scheduler.containsKey(player.getUniqueId()));
            if (AfkMapData.scheduler.containsKey(player.getUniqueId())) {

                AfkDetectEvent afkDetectEvent = new AfkDetectEvent(player);
                Bukkit.getPluginManager().callEvent(afkDetectEvent);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        if (AfkMapData.scheduler.containsKey(player.getUniqueId())) {

            AfkDetectEvent afkDetectEvent = new AfkDetectEvent(player);
            Bukkit.getPluginManager().callEvent(afkDetectEvent);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (AfkMapData.scheduler.containsKey(player.getUniqueId())) {

            AfkDetectEvent afkDetectEvent = new AfkDetectEvent(player);
            Bukkit.getPluginManager().callEvent(afkDetectEvent);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (AfkMapData.scheduler.containsKey(player.getUniqueId())) {

            AfkDetectEvent afkDetectEvent = new AfkDetectEvent(player);
            Bukkit.getPluginManager().callEvent(afkDetectEvent);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void interactEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (AfkMapData.scheduler.containsKey(player.getUniqueId())) {

            AfkDetectEvent afkDetectEvent = new AfkDetectEvent(player);
            Bukkit.getPluginManager().callEvent(afkDetectEvent);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if (AfkMapData.scheduler.containsKey(player.getUniqueId())) {
            AfkDetectEvent afkDetectEvent = new AfkDetectEvent(player);
            Bukkit.getPluginManager().callEvent(afkDetectEvent);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        if (AfkMapData.scheduler.containsKey(player.getUniqueId())) {
            AfkDetectEvent afkDetectEvent = new AfkDetectEvent(player);
            Bukkit.getPluginManager().callEvent(afkDetectEvent);
        }
    }

    @EventHandler
    public void afkDetectEvent(AfkDetectEvent event){
        Player player = event.getPlayer();

        AfkDetect afkDetect = AfkMapData.scheduler.get(player.getUniqueId());

        afkDetect.cancel();
        afkDetect.getAfkArea().removePlayer(player);

        player.sendMessage("잠수가 끝남");
        AfkMapData.scheduler.remove(player.getUniqueId());
    }
}
