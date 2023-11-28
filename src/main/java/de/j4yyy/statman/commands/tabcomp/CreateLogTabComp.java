package de.j4yyy.statman.commands.tabcomp;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateLogTabComp implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            ArrayList<String> possibilities = new ArrayList<>(Arrays.asList(
                    "create",
                    "start",
                    "stop"
            ));
            return possibilities.stream().filter(text -> StringUtils.startsWithIgnoreCase(text, args[0])).collect(Collectors.toList());
        }
        return null;
    }
}