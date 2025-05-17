package dev.iiDanto.iiHomes.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
    public static String encodeLocation(Location location) {
        if (location == null || location.getWorld() == null) {
            return null;
        }

        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return worldName + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
    }

    public static Location decodeLocation(String encodedLocation) {
        if (encodedLocation == null || encodedLocation.isEmpty()) {
            return null;
        }

        String[] parts = encodedLocation.split(":");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid location format.");
        }

        String worldName = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalArgumentException("World not found: " + worldName);
        }
        return new Location(world, x, y, z, yaw, pitch);
    }
}
