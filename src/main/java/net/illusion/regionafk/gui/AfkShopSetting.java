package net.illusion.regionafk.gui;

import lombok.Getter;
import lombok.Setter;
import net.illusion.core.util.item.ItemBuilder;
import net.illusion.core.util.item.PDCData;
import net.illusion.regionafk.RegionAfkPlugin;
import net.illusion.regionafk.data.AfkShopActionType;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AfkShopSetting implements InventoryHolder {


    @Getter
    private ItemStack itemStack;

    @Getter
    private AfkShopActionType actionType;

    @Getter
    private AfkShop afkShop;


    @Getter
    @Setter
    private int slot;

    @Getter
    private String name;

    public AfkShopSetting(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setName(String name) {
        this.name = name;
        this.afkShop = new AfkShop(name);
    }

    public AfkShopSetting setActionType(AfkShopActionType afkShopActionType) {
        this.actionType = afkShopActionType;
        return this;
    }

    public void price(Player player) {
        if (actionType == AfkShopActionType.BUY) {
            player.sendMessage("구매 가격을 입력해 주세요!");
            player.closeInventory();
            return;
        }

        player.sendMessage("판매 가격을 입력해 주세요!");
        player.closeInventory();
    }

    public void setPrice(long amount) {
        /**
         * NBT 특성상, meta 값을 저장해버리면 모두 동기화가 되는 현상이 있다.
         * 그래서 먼저, setLong을 통해 값을 초기화 하기 전에, 다른 값을 먼저 setLong을 통해 초기화 시켜주고,
         * 남은 값을 setLong을 시켜주면 값 동기화 문제를 해결 할 수 있다.
         */

        PDCData storage = new PDCData(RegionAfkPlugin.getPlugin());

        if (actionType == AfkShopActionType.BUY) {

            if (storage.getLong(itemStack, "sell") != -1)
                storage.setLong(itemStack, "sell", storage.getLong(itemStack, "sell"));

            storage.setLong(itemStack, "buy", amount);
            afkShop.setItemStack(slot, itemStack);
            return;
        }

        if (storage.getLong(itemStack, "buy") != -1)
            storage.setLong(itemStack, "buy", storage.getLong(itemStack, "buy"));

        storage.setLong(itemStack, "sell", amount);
        afkShop.setItemStack(slot, itemStack);
    }



    @NotNull
    @Override
    public Inventory getInventory() {
        //Owner 값을 this로 지정함으로 AfkShopSetting().getInventory() 의 InventoryHolder값을 지정해준다.
        Inventory inv = Bukkit.createInventory(this, 54, "잠수 포인트 설정");
        ItemBuilder.newItemStack("", itemStack.getType(), 1, List.of(), 1, inv);

        return inv;
    }
}
