package crypto.anguita.nextgenfactions.backend.handler.data;

import crypto.anguita.nextgenfactions.backend.dao.DAO;
import crypto.anguita.nextgenfactions.commons.model.NextGenFactionEntity;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface DataHandler<T extends NextGenFactionEntity> extends Listener {

    DAO<T> getDao();

    JavaPlugin getPlugin();

    default void autoRegister() {
        this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
        Bukkit.getConsoleSender().sendMessage("Successfully registered listener: " + this.getClass().getSimpleName());
    }

    default @Nullable T getById(@NotNull UUID id) {
        return this.getDao().findById(id);
    }

    default @Nullable T getByName(@NotNull String name) {
        return this.getDao().findByName(name);
    }

    default boolean existsById(@NotNull UUID id) {
        return this.getDao().existsById(id);
    }

    default boolean existsByName(@NotNull String name) {
        return this.getDao().existsByName(name);
    }

    default boolean deleteById(@NotNull UUID id) {
        return this.getDao().deleteById(id);
    }

    default boolean deleteByName(@NotNull String name) {
        return this.getDao().deleteByName(name);
    }
}
