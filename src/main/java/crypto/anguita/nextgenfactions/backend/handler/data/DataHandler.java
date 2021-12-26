package crypto.anguita.nextgenfactions.backend.handler.data;

import crypto.anguita.nextgenfactions.backend.handler.dao.DAO;
import crypto.anguita.nextgenfactions.commons.model.NextGenFactionEntity;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface DataHandler<T extends NextGenFactionEntity> extends Listener {

    DAO<T> getDao();

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
