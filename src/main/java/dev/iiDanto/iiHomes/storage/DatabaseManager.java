package dev.iiDanto.iiHomes.storage;

import dev.iiDanto.iiHomes.IiHomes;
import dev.iiDanto.iiHomes.objects.HomeHolder;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class DatabaseManager {

    private static DatabaseManager manager;
    private final Connection connection;

    private boolean isConnected = false;

    private Map<Class<?>, DatabaseProvider<?>> providers = new HashMap<>();

    public DatabaseManager(String path) {
        manager = this;
        File file = new File(path);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            isConnected = true;
            register();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T>CompletableFuture<Optional<T>> get(Class<T> clazz, UUID uuid) {
        if(!isConnected) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        return CompletableFuture.supplyAsync(() -> (Optional<T>) providers.get(clazz).get(uuid));
    }

    public <T>CompletableFuture<Void> save(Class<T> clazz, T t) {
        if(!isConnected) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
            ((DatabaseProvider<T>) providers.get(clazz)).save(t);
            return null;
        });
    }

    private void register() {
        providers.put(HomeHolder.class, new HomeDatabase(connection));
    }

    public static DatabaseManager getInstance() {
        if (manager == null) {
            new DatabaseManager(IiHomes.instance.getDataFolder().getAbsolutePath() + "/homes.db");
            return manager;
        }
        return manager;
    }
}
