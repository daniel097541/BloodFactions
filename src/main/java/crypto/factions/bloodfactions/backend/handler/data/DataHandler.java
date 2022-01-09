package crypto.factions.bloodfactions.backend.handler.data;

import crypto.factions.bloodfactions.backend.dao.DAO;
import crypto.factions.bloodfactions.backend.manager.DataManager;
import crypto.factions.bloodfactions.commons.model.NextGenFactionEntity;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface DataHandler<T extends NextGenFactionEntity> extends Listener {

    DataManager<T> getManager();

    JavaPlugin getPlugin();

    default void autoRegister() {
        this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
        Bukkit.getConsoleSender().sendMessage("Successfully registered listener: " + this.getClass().getSimpleName());
    }

    default @Nullable T getById(@NotNull UUID id) {
        return this.getManager().getById(id);
    }

    default @Nullable T getByName(@NotNull String name) {
        return this.getManager().getByName(name);
    }

    default boolean existsById(@NotNull UUID id) {
        return this.getManager().existsById(id);
    }

    default boolean existsByName(@NotNull String name) {
        return this.getManager().existsByName(name);
    }

    default boolean deleteById(@NotNull UUID id) {
        return this.getManager().deleteById(id);
    }

}
