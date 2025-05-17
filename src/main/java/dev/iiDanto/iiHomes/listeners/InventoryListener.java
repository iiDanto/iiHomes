package dev.iiDanto.iiHomes.listeners;

import dev.iiDanto.iiHomes.IiHomes;
import dev.iiDanto.iiHomes.cache.HomeCache;
import dev.iiDanto.iiHomes.menu.HomeMenu;
import dev.iiDanto.iiHomes.objects.Home;
import dev.iiDanto.iiHomes.objects.HomeHolder;
import dev.iiDanto.iiHomes.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListener implements Listener {
    private final HomeMenu hm;
    private final IiHomes core;
    private final MovementListener movementListener;

    public InventoryListener(HomeMenu hm, IiHomes core, MovementListener movementListener) {
        this.hm = hm;
        this.core = core;
        this.movementListener = movementListener;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if (hm.homehash.containsKey(e.getPlayer().getUniqueId())){
            Player player = (Player) e.getPlayer();
            hm.homehash.remove(player);
        } else if (hm.homehash.containsKey(hm.homehash.get(e.getPlayer().getUniqueId()))){
            Player target = Bukkit.getPlayer(hm.homehash.get(e.getPlayer().getUniqueId()));
            hm.homehash.remove(target);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (hm.homehash.containsKey(e.getWhoClicked().getUniqueId())){
            if (e.getView().getTitle().contains(ColorUtils.color("&#559effHomes"))){
                Player target = Bukkit.getPlayer(hm.homehash.get(e.getWhoClicked().getUniqueId()));
                if (target == e.getWhoClicked()){
                    Player player = target;
                } else {
                    Player player = (Player) e.getWhoClicked();
                }
                e.setCancelled(true);
                int slot = e.getSlot();
                int home = slot - 8;
                if (home < 10 && home > 0){
                    Player player = (Player) e.getWhoClicked();
                    HomeHolder playerHomeHolder = HomeCache.get(target.getUniqueId());
                    if (e.getClick().isLeftClick()){
                        Home home1 = playerHomeHolder.getHomes().get(home);
                        if (home1 != null && home1.isSet()){
                            core.teleportingplayers.add(player.getUniqueId());
                            new BukkitRunnable() {
                                int countdown = 3;
                                @Override
                                public void run() {
                                    if (countdown > 0) {
                                        if (core.teleportingplayers.contains(player.getUniqueId())){
                                            player.sendMessage(ColorUtils.color("&7Teleporting in &#559eff" + countdown));
                                            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                                            countdown--;
                                        } else {
                                            cancel();
                                        }
                                    } else {
                                        player.teleport(home1.getLocation());
                                        player.sendMessage(ColorUtils.color("&7You have been teleported to &#559effhome %home%".replace("%home%", Integer.toString(home))));
                                        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                                        core.teleportingplayers.remove(player.getUniqueId());
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(core, 0L, 20L);

                        } else {
                            if (player.hasPermission("tlixmc.homes." + home)) {
                                playerHomeHolder.getHomes().put(home, new Home(player.getUniqueId(), home, player.getLocation()));
                                HomeCache.add(playerHomeHolder);
                                player.sendMessage(ColorUtils.color("&7Successfully set &#559effHome %home% &7to your current location.".replace("%home%", Integer.toString(home))));
                                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
                                hm.homeMenu(player, player);
                            } else {
                                player.sendMessage(ColorUtils.color("&#ff0000Insufficient Permissions!"));
                                player.playSound(player, Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f);
                            }

                        }
                    } else if (e.getClick().isRightClick()){
                        Home targetHome = playerHomeHolder.getHomes().get(home);
                        if (targetHome != null && targetHome.isSet()){
                            playerHomeHolder.getHomes().remove(home);
                            player.sendMessage(ColorUtils.color("&7Successfully &#ff0000DELETED &7home #%home%".replace("%home%", Integer.toString(home))));
                            player.playSound(player, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                            player.closeInventory();
                        }
                    }
                }
            }
        }
    }
}
