package dev.iiDanto.iiHomes.menu;

import dev.iiDanto.iiHomes.cache.HomeCache;
import dev.iiDanto.iiHomes.objects.Home;
import dev.iiDanto.iiHomes.utils.ColorUtils;
import dev.iiDanto.iiHomes.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class HomeMenu {
    public HashMap<UUID, UUID> homehash = new HashMap<>() {};

    public void homeMenu(Player p, Player target){
        if (target != p){
            Inventory gui = Bukkit.createInventory(null, 27, ColorUtils.color("&#559effHomes &#ff0000(ADMIN) &8- &#559eff%d".replace("%d", target.getName())));
        }
        Inventory gui = Bukkit.createInventory(null, 27, ColorUtils.color("&#559effHomes &8- &#559eff%d".replace("%d", target.getName())));
        ItemStack bg = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta bgmeta = bg.getItemMeta();
        bgmeta.setDisplayName(ColorUtils.color("&7"));
        bg.setItemMeta(bgmeta);
        for (int i = 0; i < 27; i++){
            gui.setItem(i, bg);
        }
        Map<Integer, Home> homes = HomeCache.get(target.getUniqueId()).getHomes();

        for (int i = 1; i < 10; i++) {
            if (homes.get(i) != null && homes.get(i).isSet()) {
                ItemStack sethome = new ItemBuilder(Material.LIGHT_BLUE_BED)
                        .setName("&7Home " + i + ": &#559effSet")
                        .setLore(Arrays.asList("&7➥ &#559effLeft-Click &7to Teleport.", "&7➥ &#559effRight-Click &7to Delete"))
                        .build();
                gui.setItem(8 + i, sethome);
            } else {
                if (target.hasPermission("iihomes.home." + i)) {
                    ItemStack notsethome = new ItemBuilder(Material.WHITE_BED)
                            .setName("&7Home " + i + ": &#ff0000Not Set")
                            .setLore(Arrays.asList("&7➥ &#559effLeft-Click &7to set home."))
                            .build();
                    gui.setItem(8 + i, notsethome);
                } else {
                    ItemStack noperms = new ItemBuilder(Material.RED_BED)
                            .setName("&7Home " + i + ": &#ff0000Insufficient Permissions")
                            .build();
                    gui.setItem(8 + i, noperms);
                }
            }
        }
        homehash.put(p.getUniqueId(), target.getUniqueId());
        p.openInventory(gui);
    }
}
