package net.illusion.regionafk.cmd;

import com.sk89q.worldedit.IncompleteRegionException;
import net.illusion.core.util.text.StringUtil;
import net.illusion.regionafk.RegionAfkPlugin;
import net.illusion.regionafk.data.AfkArea;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionAfkCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 0) {
            return true;
        }

        Player player = (Player) sender;
        switch (args[0]) {
            case "상점":
                
                break;
            case "포인트":

                break;
            case "구역":
                String name;
                AfkArea afkArea;

                switch (args[1]) {
                    case "생성":
                        name = StringUtil.join(" ", args, 2);

                        if (lengthCheck(name, player)) {
                            afkArea = new AfkArea(name);

                            try {
                                afkArea.create(player);
                            } catch (IncompleteRegionException e) {
                                player.sendMessage(RegionAfkPlugin.prefix + "§c먼저 위치를 찍어주세요!");
                                return false;
                            }
                        }

                        break;

                    case "제거":
                        name = StringUtil.join(" ", args, 2);

                        if (lengthCheck(name, player)) {
                            afkArea = new AfkArea(name);
                            afkArea.remove(player);
                        }

                        break;

                    case "편집":
                        name = StringUtil.join(" ", args, 2);
                        
                        break;

                }
                break;
        }
        return false;
    }


    private boolean lengthCheck(String name, Player player) {
        if (name.length() > 256) {
            player.sendMessage(RegionAfkPlugin.prefix + "§c이름의 최대 길이는 256자 까지 가능합니다!");
            return false;
        }
        return true;
    }
}
