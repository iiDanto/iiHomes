package dev.iiDanto.iiHomes.objects;

import org.bukkit.Location;

import java.util.UUID;

public class Home {

    private final UUID uuid;
    private final int number;
    private final Location location;

    public Home(UUID uuid, int number, Location location) {
        this.uuid = uuid;
        this.number = number;
        this.location = location;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getNumber() {
        return number;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isSet() {
        return location != null;
    }
}
