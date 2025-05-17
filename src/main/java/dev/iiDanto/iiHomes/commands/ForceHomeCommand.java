package dev.iiDanto.iiHomes.commands;

import dev.iiDanto.iiHomes.cache.HomeCache;
import dev.iiDanto.iiHomes.objects.Home;
import dev.iiDanto.iiHomes.objects.HomeHolder;
import dev.iiDanto.iiHomes.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class ForceHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) return false;
        if (player.hasPermission("iihomes.admin")){
            if (args.length != 2){
                player.sendMessage(ColorUtils.color("&#ff0000Invalid Arguments! &7/forcehome (player) (home)"));
                player.playSound(player, Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f);
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null){
                HomeHolder homeHolder = HomeCache.get(target.getUniqueId());
                int home = Integer.valueOf(args[1]);
                Home home1 = homeHolder.getHomes().get(home);
                if (home1 != null){
                    player.teleport(home1.getLocation());
                    player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                    player.sendMessage(ColorUtils.color("&7Successfully teleported to &#559effHome %home% &7of &#559eff%player%".replace("%home%", String.valueOf(home)).replace("%player%", target.getName())));
                } else {
                    player.sendMessage(ColorUtils.color("&7The target player &#559eff%target% &7does not have &#559effHome %home% &7set.".replace("%target%", target.getName()).replace("%home%", Integer.toString(home))));
                }

            } else {
                player.sendMessage(ColorUtils.color("&7The player &#ff0000%target% &7does not exist!".replace("%target%", target.getName())));
                player.playSound(player, Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f);
            }
        } else {
            player.sendMessage(ColorUtils.color("&#ff0000Insufficient Permissions!"));
            player.playSound(player, Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f);
        }
        return true;
    }
}
