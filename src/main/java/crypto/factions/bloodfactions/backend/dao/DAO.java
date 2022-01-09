package crypto.factions.bloodfactions.backend.dao;

import crypto.factions.bloodfactions.backend.db.DBManager;
import crypto.factions.bloodfactions.commons.model.NextGenFactionEntity;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface DAO<T extends NextGenFactionEntity> {

    @NotNull DBManager getDbManager();

    @NotNull String getTableName();

    @Nullable T fromResultSet(ResultSet rs);

    @NotNull Set<T> getSetFromResultSet(ResultSet rs);

    default @NotNull Statement getStatement() {
        return this.getDbManager().getStatement();
    }


    default @NotNull PreparedStatement getPreparedStatement(String sql) {
        return this.getDbManager().getPreparedStatement(sql);
    }

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
                } else if (value instanceof Integer) {
                    statement.setInt(i, (int) value);
                } else if (value instanceof Boolean) {
                    statement.setBoolean(i, (boolean) value);
                } else if (value instanceof Float) {
                    statement.setFloat(i, (float) value);
                } else {
                    statement.setString(i, value.toString());
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
        return findByIdInDB(id);
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
        return this.existsByIdInDB(id);
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
            return deleted > 0;
        }
        // Error
        catch (Exception e) {
            e.printStackTrace();
        }

        // Error or not found.
        return false;
    }

    default boolean deleteById(@NotNull UUID id) {
        return this.deleteByIdInDB(id);
    }
}
