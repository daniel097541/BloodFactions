package crypto.anguita.nextgenfactions.backend.handler.dao;

import com.google.common.cache.*;
import crypto.anguita.nextgenfactions.commons.model.NextGenFactionEntity;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface DAO<T extends NextGenFactionEntity> {

    @NotNull LoadingCache<UUID, Faction> getCache();

    @NotNull String getTableName();

    @NotNull Statement getStatement();

    @NotNull PreparedStatement getPreparedStatement(String sql);

    @Nullable T fromResultSet(ResultSet rs);

    /**
     * Creates the cache.
     *
     * @param size
     * @param duration
     * @param timeUnit
     * @return
     */
    default @NotNull LoadingCache<UUID, T> createCache(int size, int duration, TimeUnit timeUnit) {
        return CacheBuilder.newBuilder()
                .maximumSize(size)
                .expireAfterAccess(duration, timeUnit)
                .removalListener((RemovalListener<UUID, T>) notification -> {
                    UUID id = notification.getKey();
                    RemovalCause cause = notification.getCause();
                    // Explicit delete, means remove from database.
                    if (Objects.nonNull(id) && cause.equals(RemovalCause.EXPLICIT)){
                        deleteById(id);
                    }
                })
                .build(
                        new CacheLoader<UUID, T>() {
                            @Override
                            public T load(@NotNull UUID key) {
                                return findById(key);
                            }
                        });
    }

    default @Nullable T findById(@NotNull UUID id) {
        String sql = "SELECT * FROM ? WHERE id = ?;";

        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, this.getTableName());
            statement.setString(2, id.toString());

            // Get from result set.
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                @Nullable T entity = this.fromResultSet(resultSet);
                return entity;
            }
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }

        // Error or not found.
        return null;
    }

    default @Nullable T findByName(@NotNull String name) {
        String sql = "SELECT * FROM ? WHERE name = ?;";

        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, this.getTableName());
            statement.setString(2, name);

            // Get from result set.
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                @Nullable T entity = this.fromResultSet(resultSet);
                return entity;
            }
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }

        // Error or not found.
        return null;
    }

    default boolean existsById(@NotNull UUID id) {
        String sql = "SELECT count(*) AS count FROM ? WHERE id = ?;";

        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, this.getTableName());
            statement.setString(2, id.toString());

            // Get from result set.
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }

        // Error or not found.
        return false;
    }

    default boolean existsByName(@NotNull String name) {
        String sql = "SELECT count(*) AS count FROM ? WHERE name = ?;";

        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, this.getTableName());
            statement.setString(2, name);

            // Get from result set.
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }

        // Error or not found.
        return false;
    }

    default boolean deleteById(@NotNull UUID id) {
        String sql = "DELETE FROM ? WHERE id = ?;";

        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, this.getTableName());
            statement.setString(2, id.toString());

            // Get from result set.
            int deleted = statement.executeUpdate(sql);
            return deleted == 0;
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }

        // Error or not found.
        return false;
    }

    default boolean deleteByName(@NotNull String name) {
        String sql = "DELETE FROM ? WHERE name = ?;";

        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, this.getTableName());
            statement.setString(2, name);

            // Get from result set.
            int deleted = statement.executeUpdate(sql);
            return deleted == 0;
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }

        // Error or not found.
        return false;
    }

}
