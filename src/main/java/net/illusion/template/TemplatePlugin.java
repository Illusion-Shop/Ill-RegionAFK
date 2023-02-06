package net.illusion.template;

import net.illusion.core.data.Config;
import net.illusion.template.cmd.TemplateCmd;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class TemplatePlugin extends JavaPlugin {

    private final Logger log = Bukkit.getLogger();
    private static TemplatePlugin plugin;
    public static Config config;

    @Override
    public void onEnable() {
        // DEPENDENCY
        if (getServer().getPluginManager().getPlugin("Ill-Core") != null) {
            log.warning("[ " + Bukkit.getName() + "] Ill-Core 플러그인이 적용되지 않아 비활성화 됩니다.");
            log.warning("[ " + Bukkit.getName() + "] 다운로드 링크: https://discord.gg/illusion-shop");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // CONFIG
        plugin = this;
        config = new Config("config");
        config.setPlugin(this);
        config.loadDefualtConfig();

        // EVENT
        // TODO

        // COMMAND
        getCommand("").setExecutor(new TemplateCmd()); // TODO
    }

    @Override
    public void onDisable() {
        // TODO
    }

    public static TemplatePlugin getPlugin() {
        return plugin;
    }
}
