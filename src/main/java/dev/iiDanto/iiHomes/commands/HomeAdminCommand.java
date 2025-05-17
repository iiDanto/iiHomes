package dev.iiDanto.iiHomes.commands;

import dev.iiDanto.iiHomes.menu.HomeMenu;
import dev.iiDanto.iiHomes.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeAdminCommand implements CommandExecutor {
    private final HomeMenu homeMenu;

    public HomeAdminCommand(HomeMenu homeMenu) {
        this.homeMenu = homeMenu;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)){
            sender.sendMessage("Only players may execute this command!");
            return false;
        }
        if (args.length != 1){
            player.sendMessage(ColorUtils.color("&#ff0000Invalid Arguments! &7/homeadmin (player)"));
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null && target.isOnline()){
            homeMenu.homeMenu(player, target);
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
        } else {
            player.sendMessage(ColorUtils.color("&7The target player &#ff0000%player% &7does not exist!".replace("%player%", args[0])));
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
        return true;
    }
}
