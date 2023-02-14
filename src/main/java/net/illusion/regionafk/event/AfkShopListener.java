package net.illusion.regionafk.event;

import net.illusion.regionafk.RegionAfkPlugin;
import net.illusion.regionafk.data.AfkMapData;
import net.illusion.regionafk.data.AfkShopActionType;
import net.illusion.regionafk.gui.AfkShop;
import net.illusion.regionafk.data.AfkShopType;
import net.illusion.regionafk.gui.AfkShopSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class AfkShopListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {

        //만약, 클릭한 아이템이 null이라면, return을 시켜줌으로 아래 기능들을 실행 시키지 않도록 한다.
        if (event.getCurrentItem() == null) return;

        if (event.getWhoClicked() instanceof Player player) {
            //클릭한 인벤토리가 잠수 상점일 경우
            if (event.getInventory().getHolder() instanceof AfkShop) {
                AfkShop afkShop = AfkMapData.shopMap.get(player.getUniqueId());

                if (afkShop.getType() == AfkShopType.OPEN) { //열기 명령어를 톻해 상점을 열었는지 체크.
                    event.setCancelled(true);
                    return;
                }

                if (event.isShiftClick()) {
                    AfkShopSetting afkShopSetting = new AfkShopSetting(event.getCurrentItem());

                    afkShopSetting.setName(afkShop.getName()); //상점 생성자에 넣을 이름을 위해 상점 이름을 전해준다.
                    afkShopSetting.setSlot(event.getSlot()); //구매, 판매 가격을 다시 정해줄용으로 아이템 슬롯저장.

                    AfkMapData.shopSettingMap.put(player.getUniqueId(), afkShopSetting); //정적 변수에 클래스를 저장.

                    player.openInventory(afkShopSetting.getInventory());
                }
                return;
            }

            if (event.getInventory().getHolder() instanceof AfkShopSetting) {
                AfkShopSetting afkShopSetting = AfkMapData.shopSettingMap.get(player.getUniqueId());

                if (event.isRightClick()) {
                    afkShopSetting.setActionType(AfkShopActionType.BUY).price(player);
                    event.setCancelled(true);
                    return;
                }

                afkShopSetting.setActionType(AfkShopActionType.SELL).price(player);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (AfkMapData.shopSettingMap.containsKey(player.getUniqueId())) {

            AfkShopSetting afkShopSetting = AfkMapData.shopSettingMap.get(player.getUniqueId());

            try {
                long amount = Long.parseLong(event.getMessage());
                afkShopSetting.setPrice(amount);

            } catch (NumberFormatException e) {
                player.sendMessage("§c정수를 입력해 주세요!");
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (AfkMapData.shopMap.containsKey(player.getUniqueId())) {
                Inventory inv = event.getInventory();

                AfkShop afkShop = AfkMapData.shopMap.get(player.getUniqueId());
                afkShop.save(player, inv);

                AfkMapData.shopMap.remove(player.getUniqueId());
                return;
            }

            if (event.getInventory().getHolder() instanceof AfkShopSetting) {
                AfkShopSetting afkShopSetting = AfkMapData.shopSettingMap.get(player.getUniqueId());
                AfkShop afkShop = new AfkShop(afkShopSetting.getName());

                if (afkShopSetting.getActionType() == AfkShopActionType.BUY || afkShopSetting.getActionType() == AfkShopActionType.SELL) {
                    return;
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {

                        player.openInventory(afkShop.getInventory());

                        AfkMapData.shopMap.put(player.getUniqueId(), afkShop);
                        AfkMapData.shopSettingMap.remove(player.getUniqueId());
                        cancel();
                    }
                }.runTaskTimer(RegionAfkPlugin.getPlugin(), 0, 20);
            }
        }
    }
}
