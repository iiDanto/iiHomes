package dev.iiDanto.iiHomes.commands;

import dev.iiDanto.iiHomes.menu.HomeMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {
    private final HomeMenu homeMenu;

    public HomeCommand(HomeMenu homeMenu) {
        this.homeMenu = homeMenu;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) return false;
        homeMenu.homeMenu(player, player);
        return true;
    }
}
