package dev.iiDanto.iiHomes.commands;

import dev.iiDanto.iiHomes.utils.ColorUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class iiHomesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)){
            sender.sendMessage("Only players may execute this command!");
            return false;
        }
        player.sendMessage(ColorUtils.color("&7-------------------------------------------"));
        player.sendMessage(ColorUtils.color("&#559eff&lIIHOMES &r&7- v1.2.4"));
        player.sendMessage(ColorUtils.color("&7"));
        player.sendMessage(ColorUtils.color("&7Author: &#559effiiDanto"));
        player.sendMessage(ColorUtils.color("&7Download: &#559effgithub.com/iiDanto/iiHomes/"));
        player.sendMessage(ColorUtils.color("&7Version: &#559effV1.1.3"));
        player.sendMessage(ColorUtils.color("&8"));
        player.sendMessage(ColorUtils.color("&7-------------------------------------------"));
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
        return true;
    }
}
