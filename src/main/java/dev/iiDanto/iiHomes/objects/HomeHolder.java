package dev.iiDanto.iiHomes.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeHolder {
    private final Map<Integer, Home> homes;
    private final UUID uuid;

    public HomeHolder(UUID uuid, Map<Integer, Home> homes) {
        this.homes = homes;
        this.uuid = uuid;
    }

    public Map<Integer, Home> getHomes() {
        return homes;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static HomeHolder newEmpty(UUID uuid) {
        return new HomeHolder(uuid, new HashMap<>());
    }
}
