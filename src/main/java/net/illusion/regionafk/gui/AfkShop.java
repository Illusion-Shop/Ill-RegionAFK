package net.illusion.regionafk.gui;

import lombok.Getter;

import lombok.Setter;
import net.illusion.core.data.Config;
import net.illusion.regionafk.RegionAfkPlugin;
import net.illusion.regionafk.data.AfkShopActionType;
import net.illusion.regionafk.data.AfkShopType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class AfkShop implements InventoryHolder {

    @Getter
    private String name;

    private byte line;

    private Config config;

    @Getter
    private AfkShopType type;


    public AfkShop(String name) {
        this.name = name;
        this.config = new Config("shop/" + name);
        this.config.setPlugin(RegionAfkPlugin.getPlugin());
    }

    public void create(Player player) {
        Config folder = new Config("shop/");
        folder.setPlugin(RegionAfkPlugin.getPlugin());

        if (folder.fileListName().contains(name)) {
            player.sendMessage("같은 이름의 상점은 못만들엉~~");
            return;
        }

        config.setString("name", name);
        config.setByte("line", (byte) 6);
        config.getConfig().set("items", Collections.emptyList());
        config.saveConfig();

        player.sendMessage(RegionAfkPlugin.prefix + " 성공적으로 잠수 상점을 만들었습니다.");
    }

    public void open(Player player) {
        this.type = AfkShopType.OPEN;
        player.openInventory(getInventory());
    }

    public void delete(Player player) {

    }

    public void edit(Player player) {
        this.type = AfkShopType.EDIT;
        player.openInventory(getInventory());
    }

    /**
     * 상점을 저장하는 메소드 입니다.
     *
     * @param inv    저장할 상점 인벤토리.
     * @param player 닫은 플레이어
     */
    public void save(Player player, Inventory inv) {

        config.getConfig().set("items", inv.getContents());
        config.saveConfig();
    }


    public void setLine(byte line) {
        this.config.setByte("line", line);
    }

    public void rename(String newName) {

        config.renameFile(newName);
    }

    public int getLine() {
        return config.getInt("line");
    }


    /**
     * 가격을 설정할때에 설정한 아이템을 원래 아이템과 교체 시켜주는 메소드.
     *
     * @param slot 설정한 아이템 위치
     * @param itemStack 설정한 아이템
     */
    public void setItemStack(int slot, ItemStack itemStack) {
        List<ItemStack> items = (List<ItemStack>) config.getConfig().getList("items");
        items.set(slot, itemStack);

        config.getConfig().set("items", items);
        config.saveConfig();
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(this, getLine() * 9, name);

        List<ItemStack> items = (List<ItemStack>) config.getConfig().getList("items");

        inv.setContents(items.toArray(new ItemStack[items.size()]));
        return inv;
    }
}
