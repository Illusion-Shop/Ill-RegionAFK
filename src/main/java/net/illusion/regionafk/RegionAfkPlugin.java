package net.illusion.regionafk;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.illusion.core.data.Config;
import net.illusion.regionafk.cmd.RegionAfkCmd;
import net.illusion.regionafk.cmd.RegionAfkTab;
import net.illusion.regionafk.data.AfkArea;
import net.illusion.regionafk.event.AfkShopListener;
import net.illusion.regionafk.event.RegionAfkListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class RegionAfkPlugin extends JavaPlugin {

    private final Logger log = Bukkit.getLogger();
    private static RegionAfkPlugin plugin;
    public static Config config;

    public static WorldEditPlugin WorldEdit;

    public static Config folder;

    public static String prefix;

    @Override
    public void onEnable() {
        // DEPENDENCY
        if (getServer().getPluginManager().getPlugin("Ill-Core") != null) {
            log.warning("[ " + Bukkit.getName() + "] Ill-Core 플러그인이 적용되지 않아 비활성화 됩니다.");
            log.warning("[ " + Bukkit.getName() + "] 다운로드 링크: https://discord.gg/illusion-shop");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") != null) {
            WorldEdit = getWorldEditPlugin();
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " WorldEdit 플러그인이 존재하지 않습니다. 일부 기능이 작동 하지 않을 수 있습니다.");
        }

        // CONFIG
        plugin = this;

        config = new Config("config");
        config.setPlugin(this);
        config.loadDefualtConfig();

        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix"));

        folder = new Config("area/");
        folder.setPlugin(RegionAfkPlugin.getPlugin());

        // EVENT
        // TODO
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new RegionAfkListener(), this);
        pm.registerEvents(new AfkShopListener(), this);

        // COMMAND
        getCommand("잠수").setExecutor(new RegionAfkCmd()); // TODO
        getCommand("잠수").setTabCompleter(new RegionAfkTab()); // TODO
    }

    @Override
    public void onDisable() {
        // TODO
        for (String name : folder.fileListName()) { // 영역 안에 있을시, 모두 삭제 처리
            AfkArea afkArea = new AfkArea(name);
            afkArea.removePlayers();
        }
    }


    public static WorldEditPlugin getWorldEditPlugin() {
        Plugin worldedit = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldedit instanceof WorldEditPlugin) return (WorldEditPlugin) worldedit;
        else return null;
    }


    public static RegionAfkPlugin getPlugin() {
        return plugin;
    }
}
