package dev.iiDanto.iiHomes.cache;

import dev.iiDanto.iiHomes.objects.HomeHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeCache {

    private final static Map<UUID, HomeHolder> homeCache = new HashMap<>();

    public static HomeHolder get(UUID uuid) {
        return homeCache.computeIfAbsent(uuid, uuid1 -> HomeHolder.newEmpty(uuid));
    }

    public static void add(HomeHolder homeHolder) {
        homeCache.put(homeHolder.getUuid(), homeHolder);
    }

}
