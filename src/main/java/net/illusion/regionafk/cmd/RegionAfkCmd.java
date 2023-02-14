package net.illusion.regionafk.cmd;

import com.sk89q.worldedit.IncompleteRegionException;
import net.illusion.core.data.Config;
import net.illusion.core.util.text.StringUtil;
import net.illusion.regionafk.RegionAfkPlugin;
import net.illusion.regionafk.data.AfkArea;
import net.illusion.regionafk.data.AfkMapData;
import net.illusion.regionafk.gui.AfkShop;
import org.bukkit.ChatColor;
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
                if (player.isOp()) {
                    switch (args[1]) {
                        case "생성":
                            name = StringUtil.join(" ", args, 2);

                            if (!compareLength(name, player)) return false;

                            if (!compare(name, player)) return false;

                            afkShop = new AfkShop(name);
                            afkShop.create(player);

                            break;

                        case "제거":
                            name = StringUtil.join(" ", args, 2);

                            if (!compare(name, player)) return false;

                            afkShop = new AfkShop(name);
                            afkShop.delete(player);
                            break;

                        case "편집":
                            name = StringUtil.join(" ", args, 2);

                            if (!compare(name, player)) return false;

                            afkShop = new AfkShop(name);
                            afkShop.edit(player);

                            AfkMapData.shopMap.put(player.getUniqueId(), afkShop);

                            break;

                        case "줄":
                            byte line = Byte.parseByte(args[2]);
                            name = StringUtil.join(" ", args, 3);

                            if (line > 6 || line < 1) {
                                player.sendMessage(RegionAfkPlugin.prefix + " §c1~6 줄 단위로 입력해 주세요!");
                                return false;
                            }

                            if (!compare(name, player)) return false;

                            afkShop = new AfkShop(name);
                            afkShop.setLine(line);

                            player.sendMessage(RegionAfkPlugin.prefix + " §a성공적으로 §r" + name + " §a상점의 줄을 §f" + line + " §a칸으로 설정하였습니다.");

                            break;

                        case "제목":
                            name = StringUtil.join(" ", args, 2);
                            String newName = StringUtil.join(" ", args, name.length());

                            if (!compareLength(newName, player)) return false;

                            if (!compare(newName, player)) return false;

                            afkShop = new AfkShop(name);
                            afkShop.rename(newName);
                            break;

                        case "목록":
                            Config folder = new Config("shop/");
                            folder.setPlugin(RegionAfkPlugin.getPlugin());

                            if (folder.fileListName().isEmpty()) {
                                player.sendMessage(RegionAfkPlugin.prefix + ChatColor.RED + " 상점이 존재하지 않습니다.");
                                return false;
                            }

                            player.sendMessage(String.join("\n", folder.fileListName().toArray(new String[0])));

                            break;

                        case "리로드":

                            break;
                    }
                    return true;
                }

                if ("열기".equalsIgnoreCase(args[1])) {
                    name = StringUtil.join(" ", args, 2);

                    if (!compare(name, player)) return false;

                    afkShop = new AfkShop(name);
                    afkShop.open(player);
                    AfkMapData.shopMap.put(player.getUniqueId(), afkShop);
                }


                break;

            case "포인트":

                break;
            case "구역":

                AfkArea afkArea;

                switch (args[1]) {
                    case "생성":
                        name = StringUtil.join(" ", args, 2);

                        if (!compare(name, player)) return false;

                        if (compareLength(name, player)) {
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

                        if (!compare(name, player)) return false;

                        if (compareLength(name, player)) {
                            afkArea = new AfkArea(name);
                            afkArea.remove(player);
                        }

                        break;

                    case "편집":
                        name = StringUtil.join(" ", args, 2);

                        if (!compare(name, player)) return false;

                        if (compareLength(name, player)) {
                            afkArea = new AfkArea(name);

                        }

                        break;

                }
                break;
        }
        return false;
    }

    /**
     * 이름을 특정 형식에 맞춰 입력했는지 비교하는 메소드 입니다.
     *
     * @param name   비교할 이름
     * @param player 플레이어
     * @return 만약 입력한 이름이 잘못된 형식이면, false를, 반대 경우엔 true를 반환합니다. 따라서
     *  <p>if (!compare(name, player)) return false; 한줄로 비교가 가능해집니다.</p>
     */
    private boolean compare(String name, Player player) {
        if (name.length() == 0) {
            player.sendMessage(RegionAfkPlugin.prefix + "§c상점 이름을 입력해 주세요!");
            return false;
        }

        if (StringUtil.containsSpecialChar(name)) {
            player.sendMessage(RegionAfkPlugin.prefix + "§c상점 이름에 특수문자는 들어갈 수 없습니다!");
            return false;
        }
        return true;
    }


    /**
     * 윈도우10 기준, 파일 이름의 최대 길이는 256자 입니다.
     * 즉, 이름이 256자를 넘었는지 확인하는 메소드 입니다.
     * @param name 비교할 이름
     * @param player 커맨드를 입력한 플레이어
     * @return 256자가 넘으면 false를, 넘지 않으면 true를 반환합니다.
     */
    private boolean compareLength(String name, Player player) {
        if (name.length() > 256) {
            player.sendMessage(RegionAfkPlugin.prefix + "§c이름의 최대 길이는 256자 까지 가능합니다!");
            return false;
        }
        return true;
    }
}
