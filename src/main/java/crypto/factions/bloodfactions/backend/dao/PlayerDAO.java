package crypto.factions.bloodfactions.backend.dao;

import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.player.FPlayerImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface PlayerDAO extends DAO<FPlayer> {

    @Override
    default @Nullable FPlayer fromResultSet(@NotNull ResultSet rs) {
        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                int power = rs.getInt("power");
                boolean isFlying = rs.getBoolean("flying");
                boolean isAutoFlying = rs.getBoolean("auto_flying");

                return new FPlayerImpl(id, name, isFlying, isAutoFlying, power);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    default @NotNull Set<FPlayer> getSetFromResultSet(@NotNull ResultSet rs) {
        Set<FPlayer> players = new HashSet<>();
        try {
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                int power = rs.getInt("power");
                boolean isFlying = rs.getBoolean("flying");
                boolean isAutoFlying = rs.getBoolean("auto_flying");
                FPlayer player = new FPlayerImpl(id, name, isFlying, isAutoFlying, power);
                players.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    default void removePlayerFromFaction(@NotNull FPlayer player) {

        String sql = "DELETE FROM as_faction_players WHERE player_id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {
            statement.setString(1, player.getId().toString());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a player to the faction.
     *
     * @param player
     * @param faction
     */
    default void addPlayerToFaction(@NotNull FPlayer player, @NotNull Faction faction, @NotNull FPlayer invitedBy) {

        // First remove any existing relationship of the player with another faction.
        this.removePlayerFromFaction(player);

        String sql = "INSERT INTO as_faction_players (faction_id, player_id, invited_by) VALUES (?,?,?);";

        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {

            preparedStatement.setString(1, faction.getId().toString());
            preparedStatement.setString(2, player.getId().toString());
            preparedStatement.setString(3, invitedBy.getId().toString());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all players in faction.
     *
     * @param factionId
     * @return
     */
    default @NotNull Set<FPlayer> findPlayersInFaction(@NotNull UUID factionId) {
        String sql = "SELECT * FROM as_faction_players AS rel " +
                " JOIN players AS p ON p.id = rel.player_id " +
                " WHERE rel.faction_id = ?;";
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {
            statement.setString(1, factionId.toString());
            try (ResultSet rs = statement.executeQuery()) {
                return this.getSetFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    /**
     * Checks whether a player has Faction.
     *
     * @param playerId
     * @return
     */
    default boolean checkIfPlayerHasFaction(@NotNull UUID playerId) {

        String sql = "SELECT count(*) AS count FROM as_faction_players AS rel " +
                " JOIN factions AS f ON f.id = rel.faction_id AND NOT f.system_faction " +
                " WHERE rel.player_id = ?;";
        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, playerId.toString());

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Kicks all players from the Faction.
     *
     * @param faction
     */
    default void removeAllPlayersFromFaction(Faction faction) {

        String sql = "DELETE FROM as_faction_players WHERE faction_id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {
            statement.setString(1, faction.getId().toString());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the player has permissions to perform the action.
     *
     * @param player
     * @param permissionType
     * @return
     */
    default boolean checkIfPlayerHasPermission(FPlayer player, PermissionType permissionType) {

        String sql = "SELECT count(*) AS count FROM as_player_permissions AS rel " +
                " WHERE rel.player_id = ? AND permission_id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, player.getId().toString());
            statement.setInt(1, permissionType.getId());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    default void updatePlayersFlightMode(UUID playerId, boolean flying) {

        String sql = "UPDATE players SET flying = ? WHERE id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setBoolean(1, flying);
            statement.setString(2, playerId.toString());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    default boolean updatePlayersPower(UUID id, int power) {

        String sql = "UPDATE players SET power = ? WHERE id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {
            statement.setInt(1, power);
            statement.setString(2, id.toString());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    default void updatePlayersAutoFly(UUID playerId, boolean autoFly) {

        String sql = "UPDATE players SET auto_flying = ? WHERE id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {
            statement.setBoolean(1, autoFly);
            statement.setString(2, playerId.toString());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
