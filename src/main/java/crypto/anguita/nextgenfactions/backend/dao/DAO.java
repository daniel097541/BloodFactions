package crypto.anguita.nextgenfactions.backend.dao;

import com.google.common.cache.*;
import crypto.anguita.nextgenfactions.commons.model.NextGenFactionEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public interface DAO<T extends NextGenFactionEntity> {

    @NotNull LoadingCache<UUID, T> getCache();

    @NotNull String getTableName();

    @NotNull Statement getStatement();

    @NotNull PreparedStatement getPreparedStatement(String sql);

    @Nullable T fromResultSet(ResultSet rs);

    @NotNull Set<T> getSetFromResultSet(ResultSet rs);

    default @Nullable T insert(@NotNull T entity) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");

        sql.append(this.getTableName());
        sql.append(" (");

        Map<String, Object> map = entity.getAsMap();

        for (String key : map.keySet()) {
            sql.append(key).append(",");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1) + ") VALUES (");
        for (int i = 0; i < map.size(); i++) {
            sql.append("?,");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        sql.append(");");

        try (PreparedStatement statement = this.getPreparedStatement(sql.toString())) {

            int i = 1;

            for (Object value : map.values()) {
                if (value instanceof String) {
                    statement.setString(i, (String) value);
                }
                if (value instanceof Integer) {
                    statement.setInt(i, (int) value);
                }
                if (value instanceof Boolean) {
                    statement.setBoolean(i, (boolean) value);
                }
                if (value instanceof Float) {
                    statement.setFloat(i, (float) value);
                }
                i++;
            }
            statement.executeUpdate();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

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
                    if (Objects.nonNull(id) && cause.equals(RemovalCause.EXPLICIT)) {
                        deleteByIdInDB(id);
                    }
                })
                .build(
                        new CacheLoader<UUID, T>() {
                            @Override
                            public T load(@NotNull UUID key) {
                                return findByIdInDB(key);
                            }
                        });
    }

    default @Nullable T findByIdInDB(@NotNull UUID id) {
        String sql = "SELECT * FROM table_name WHERE id = ?;";

        sql = sql.replace("table_name", this.getTableName());

        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, id.toString());

            // Get from result set.
            try (ResultSet resultSet = statement.executeQuery()) {
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

    default @Nullable T findById(@NotNull UUID id) {
        try {
            return this.getCache().get(id);
        } catch (CacheLoader.InvalidCacheLoadException | ExecutionException ignored) {
        }
        return null;
    }

    default @Nullable T findByName(@NotNull String name) {
        String sql = "SELECT * FROM table_name WHERE name = ?;";

        sql = sql.replace("table_name", this.getTableName());

        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, name);

            // Get from result set.
            try (ResultSet resultSet = statement.executeQuery()) {
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

    default boolean existsByIdInDB(@NotNull UUID id) {
        String sql = "SELECT count(*) AS count FROM table_name WHERE id = ?;";

        sql = sql.replace("table_name", this.getTableName());


        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, id.toString());

            // Get from result set.
            try (ResultSet resultSet = statement.executeQuery()) {
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

    default boolean existsById(@NotNull UUID id) {
        return this.existsById(id, true);
    }

    default boolean existsById(@NotNull UUID id, boolean forceDBCheck) {
        if (forceDBCheck) {
            return this.existsByIdInDB(id);
        }
        return this.getCache().asMap().containsKey(id);
    }

    default boolean existsByName(@NotNull String name) {
        String sql = "SELECT count(*) AS count FROM table_name WHERE name = ?;";

        sql = sql.replace("table_name", this.getTableName());


        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, name);

            // Get from result set.
            try (ResultSet resultSet = statement.executeQuery()) {
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

    default boolean deleteByIdInDB(@NotNull UUID id) {
        String sql = "DELETE FROM table_name WHERE id = ?;";

        sql = sql.replace("table_name", this.getTableName());


        // Find in database.
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            // Set table name and id.
            statement.setString(1, id.toString());

            // Get from result set.
            int deleted = statement.executeUpdate();
            return deleted == 0;
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }

        // Error or not found.
        return false;
    }

    default boolean deleteById(@NotNull UUID id) {
        this.getCache().invalidate(id);
        return true;
    }
}
