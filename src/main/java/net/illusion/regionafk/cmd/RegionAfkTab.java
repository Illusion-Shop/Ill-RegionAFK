package net.illusion.regionafk.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegionAfkTab implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> result = new ArrayList<>();
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (player.isOp()) {
                    result = List.of("구역", "포인트", "상점");
                    return result;
                }
                result = List.of("포인트", "상점");
            } else if (args.length == 2) {
                if (player.isOp()) {
                    switch (args[0]) {
                        case "상점":
                            result = List.of("생성", "제거", "편집", "줄", "제목", "목록", "리로드");
                            break;
                        case "구역":
                            result = List.of("생성", "제거", "편집");
                            break;
                    }


                    return result;
                }
                result = List.of("포인트", "상점");
            }
            return result;
        }
        return Collections.emptyList();
    }
}
