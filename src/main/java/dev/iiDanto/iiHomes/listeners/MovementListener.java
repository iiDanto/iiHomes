package dev.iiDanto.iiHomes.listeners;

import dev.iiDanto.iiHomes.IiHomes;
import dev.iiDanto.iiHomes.utils.ColorUtils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.imageio.stream.IIOByteBuffer;

public class MovementListener implements Listener {
    private final IiHomes core;

    public MovementListener(IiHomes core) {
        this.core = core;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (core.teleportingplayers.contains(e.getPlayer().getUniqueId())){
            if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
                e.getPlayer().sendMessage(ColorUtils.color("&7You have &#ff0000MOVED &7and your teleportation has been &#ff0000CANCELLED"));
                e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f);
                core.teleportingplayers.remove(e.getPlayer().getUniqueId());
            }
        }
    }}
