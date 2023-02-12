package net.illusion.regionafk.cmd;

import com.sk89q.worldedit.IncompleteRegionException;
import net.illusion.core.util.text.StringUtil;
import net.illusion.regionafk.RegionAfkPlugin;
import net.illusion.regionafk.data.AfkArea;
import net.illusion.regionafk.data.AfkShop;
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

        String name;

        AfkShop afkShop;

        switch (args[0]) {
            case "상점":
                switch (args[1]) {
                    case "생성":
                        name = StringUtil.join(" ", args, 2);

                        afkShop = new AfkShop(name);
                        afkShop.create(player);

                        break;
                    case "제거":
                        name = StringUtil.join(" ", args, 2);

                        afkShop = new AfkShop(name);
                        afkShop.delete(player);
                        break;
                    case "편집":
                        name = StringUtil.join(" ", args, 2);
                        afkShop = new AfkShop(name);

                        afkShop.edit(player);
                        break;
                    case "줄":
                        byte line = Byte.parseByte(args[2]);

                        name = StringUtil.join(" ", args, 3);
                        afkShop = new AfkShop(name);

                        afkShop.setLine(line);

                        break;
                    case "제목":
                        name = StringUtil.join(" ", args, 2);

                        String newName = StringUtil.join(" ", args, name.length());
                        afkShop = new AfkShop(name);

                        afkShop.rename(newName);
                        break;
                    case "목록":

                        break;

                    case "리로드":

                        break;

                }

                break;

            case "포인트":

                break;
            case "구역":

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
