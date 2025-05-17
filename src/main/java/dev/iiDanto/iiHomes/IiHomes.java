package dev.iiDanto.iiHomes;

import dev.iiDanto.iiHomes.commands.ForceHomeCommand;
import dev.iiDanto.iiHomes.commands.HomeAdminCommand;
import dev.iiDanto.iiHomes.commands.HomeCommand;
import dev.iiDanto.iiHomes.commands.iiHomesCommand;
import dev.iiDanto.iiHomes.listeners.InventoryListener;
import dev.iiDanto.iiHomes.listeners.MovementListener;
import dev.iiDanto.iiHomes.menu.HomeMenu;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class IiHomes extends JavaPlugin {
    private HomeMenu homeMenu;
    public static IiHomes instance;
    public List<UUID> teleportingplayers = new ArrayList<>();

    @Override
    public void onEnable() {
        this.homeMenu = new HomeMenu();
        instance = this;
        getCommand("home").setExecutor(new HomeCommand(homeMenu));
        getCommand("forcehome").setExecutor(new ForceHomeCommand());
        getCommand("homeadmin").setExecutor(new HomeAdminCommand(homeMenu));
        getCommand("iihomes").setExecutor(new iiHomesCommand());
        getServer().getPluginManager().registerEvents(new MovementListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(homeMenu, this, new MovementListener(this)), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
