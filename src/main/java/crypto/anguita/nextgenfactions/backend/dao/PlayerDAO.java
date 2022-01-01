package crypto.anguita.nextgenfactions.backend.dao;

import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayerImpl;
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
                return new FPlayerImpl(id, name, power);
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
                FPlayer player = new FPlayerImpl(id, name, power);
                players.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    /**
     * Adds a player to the faction.
     *
     * @param player
     * @param faction
     */
    default void addPlayerToFaction(FPlayer player, Faction faction) {

        String sql = "INSERT INTO as_faction_players (faction_id, player_id, invited_by) VALUES (?,?,?);";

        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {

            preparedStatement.setString(1, faction.getId().toString());
            preparedStatement.setString(2, player.getId().toString());
            preparedStatement.setString(3, player.getId().toString());

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

}
