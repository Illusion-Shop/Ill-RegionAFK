package net.illusion.regionafk.data;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.math.BlockVector3;
import net.illusion.core.data.Config;

import net.illusion.core.util.time.Time;
import net.illusion.regionafk.RegionAfkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AfkArea {

    private Config config;

    private String name;


    public AfkArea(String name) {
        this.name = name;
        config = new Config("area/" + name);
        config.setPlugin(RegionAfkPlugin.getPlugin());
    }

    public void create(Player player) throws IncompleteRegionException {
        Config file = new Config("area/");
        file.setPlugin(RegionAfkPlugin.getPlugin());

        if (file.fileListName().contains(name)) {
            player.sendMessage(RegionAfkPlugin.prefix + "§c해당 이름의 구역은 이미 존재합니다");
            return;
        }

        BlockVector3 pos1 = RegionAfkPlugin.WorldEdit.getSession(player).getSelection().getBoundingBox().getPos1();
        BlockVector3 pos2 = RegionAfkPlugin.WorldEdit.getSession(player).getSelection().getBoundingBox().getPos2();

        Location location = new Location(player.getWorld(), pos1.getX(), pos1.getY(), pos1.getZ());
        Location location2 = new Location(player.getWorld(), pos2.getX(), pos2.getY(), pos2.getZ());

        config.getConfig().set("pos1", location);
        config.getConfig().set("pos2", location2);

        config.getConfig().set("startTime", 5000); // 잠수 시작 시간은 기본 5초로 설정됨.
        config.getConfig().set("stepTime", 5000); // 잠수 시작 시간은 기본 5초로 설정됨.

        config.getConfig().set("players", Collections.emptyList());

        config.getConfig().set("point", 100);

        config.getConfig().set("message.start", "{prefix} {name} &7구역에서 잠수가 시작됩니다.");
        config.getConfig().set("message.cancel", "{prefix} {name} &7구역에서 잠수가 취소되었습니다.");
        config.getConfig().set("message.get", "{prefix} {name} &7잠수 포인트 {point}를 얻었습니다.");
        config.saveConfig();

        player.sendMessage(RegionAfkPlugin.prefix + "§a성공적으로 §r" + name + " §a의 구역을 생성 하였습니다!");
    }

    public void remove(Player player) {
        Config file = new Config("area/");
        file.setPlugin(RegionAfkPlugin.getPlugin());

        if (file.fileListName().contains(name)) {
            config.deleteFile();
            player.sendMessage("성공적으로 구역을 지움");
            return;
        }

        player.sendMessage("해당 구역의 이름은 없음 ㅅㄱ");
    }

    public boolean contains(Player player) {
        List<Player> players = (List<Player>) config.getConfig().getList("players");
        return players.contains(player);
    }

    public void removePlayers() {
        List<Player> players = (List<Player>) config.getConfig().getList("players");
        players.clear();
        config.getConfig().set("players", players);
        config.saveConfig();
    }

    public boolean removePlayer(Player player) {
        List<Player> players = (List<Player>) config.getConfig().getList("players");
        if (players.contains(player)) {
            players.remove(player);
            config.getConfig().set("players", players);
            config.saveConfig();
            return true;
        }
        return false;
    }

    public boolean addPlayer(Player player) {
        List<Player> players = (List<Player>) config.getConfig().getList("players");

        if (!players.contains(player)) {
            players.add(player);
            config.getConfig().set("players", players);
            config.saveConfig();

            return true;
        }
        return false;
    }

    /**
     * 좌표들을 불러오는 메소들입니다.
     *
     * @return <p> getLocations().get(0) = pos1 </p>
     * <p>getLocations().get(1) = pos2</p>
     */
    public List<Location> getLocations() {
        List<Location> result = new ArrayList<>();

        if (config.getConfig().getLocation("pos1") == null && config.getConfig().getLocation("pos2") == null)
            return Collections.emptyList();

        result.add(config.getConfig().getLocation("pos1"));
        result.add(config.getConfig().getLocation("pos2"));
        return result;
    }


    public Time getStepTime() {
        return new Time(config.getLong("stepTime"));
    }

    public Time getStartTime() {
        return new Time(config.getLong("startTime"));
    }

    public String getStartMessage() {
        String result = config.getString("message.start");

        result = result.replaceAll("\\{prefix\\}", RegionAfkPlugin.config.getString("prefix"));
        result = result.replaceAll("\\{name\\}", name);
        return ChatColor.translateAlternateColorCodes('&', result);
    }

    public String getName() {
        return name;
    }
}
