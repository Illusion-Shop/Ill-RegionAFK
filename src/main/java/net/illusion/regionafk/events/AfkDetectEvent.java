package net.illusion.regionafk.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AfkDetectEvent extends Event {

    private Player player;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public AfkDetectEvent(Player player) {
        this.player = player;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }
}
