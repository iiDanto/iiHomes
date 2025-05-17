package dev.iiDanto.iiHomes.storage;

import dev.iiDanto.iiHomes.objects.Home;
import dev.iiDanto.iiHomes.objects.HomeHolder;
import dev.iiDanto.iiHomes.utils.LocationUtils;
import org.bukkit.Location;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HomeDatabase implements DatabaseProvider<HomeHolder> {

    private final Connection connection;

    public HomeDatabase(Connection connection) {
        this.connection = connection;
        start();
    }

    @Override
    public void start() {
        try {
            try (Statement statement = connection.createStatement()){
                statement.execute("""
                CREATE TABLE IF NOT EXISTS homes (
                uuid TEXT PRIMARY KEY,
                number INTEGER NOT NULL,
                location TEXT NOT NULL)
        """);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(HomeHolder homeHolder) {
        for (Integer x : homeHolder.getHomes().keySet()) {
            Home home = homeHolder.getHomes().get(x);
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT OR REPLACE INTO homes (uuid, number, location) VALUES (?, ?, ?)
                    """)) {
                statement.setString(1, home.getUuid().toString());
                statement.setInt(2, home.getNumber());
                statement.setString(3, LocationUtils.encodeLocation(home.getLocation()));
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Optional<HomeHolder> get(UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT number, location
                FROM homes
                WHERE uuid = ?
                """)) {
            statement.setString(1, uuid.toString());
            final ResultSet set = statement.executeQuery();
            Map<Integer, Home> homes = new HashMap<>();

            while (set.next()) {
                final int id = set.getInt("number");
                final Location location = LocationUtils.decodeLocation(set.getString("location"));

                homes.put(id, new Home(uuid, id, location));
            }

            if(!homes.isEmpty()) {
                return Optional.of(new HomeHolder(uuid, homes));
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
