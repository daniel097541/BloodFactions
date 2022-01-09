package crypto.factions.bloodfactions.backend.manager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.dao.DAO;
import crypto.factions.bloodfactions.commons.model.NextGenFactionEntity;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface DataManager<T extends NextGenFactionEntity> extends Listener {

    DAO<T> getDAO();

    JavaPlugin getPlugin();

    LoadingCache<UUID, T> getCache();

    default LoadingCache<UUID, T> buildCache(long expireTime, TimeUnit timeUnit) {
        return CacheBuilder
                .newBuilder()
                .expireAfterAccess(expireTime, timeUnit)
                .build(new CacheLoader<UUID, T>() {
                    @Override
                    public @NotNull T load(@NotNull UUID key) throws NullPointerException {
                        return Objects.requireNonNull(getDAO().findById(key));
                    }
                });
    }

    default void autoRegister() {
        this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
        Bukkit.getConsoleSender().sendMessage("Successfully registered listener: " + this.getClass().getSimpleName());
    }

    @SneakyThrows
    default @Nullable T getById(@NotNull UUID id) {
        return this.getCache().get(id);
    }

    default @Nullable T getByName(@NotNull String name) {
        return this.getDAO().findByName(name);
    }

    default boolean existsById(@NotNull UUID id) {
        return this.getDAO().existsById(id);
    }

    default boolean existsByName(@NotNull String name) {
        return this.getDAO().existsByName(name);
    }

    default boolean deleteById(@NotNull UUID id) {
        this.getCache().invalidate(id);
        return this.getDAO().deleteById(id);
    }

    default T insert(T object) {
        T inserted = this.getDAO().insert(object);
        if (Objects.nonNull(inserted)) {
            this.getCache().put(inserted.getId(), inserted);
        }
        return inserted;
    }
}
