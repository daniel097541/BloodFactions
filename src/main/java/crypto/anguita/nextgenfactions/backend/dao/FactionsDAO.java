package crypto.anguita.nextgenfactions.backend.dao;

import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.faction.FactionImpl;
import crypto.anguita.nextgenfactions.commons.model.faction.SystemFactionImpl;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface FactionsDAO extends DAO<Faction> {

    @Override
    default @NotNull Set<Faction> getSetFromResultSet(ResultSet rs) {
        Set<Faction> factions = new HashSet<>();
        try {
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                boolean isSystemFaction = rs.getBoolean("system_faction");

                // Returns normal faction.
                if (!isSystemFaction) {
                    factions.add(new FactionImpl(id, name));
                }
                // Returns sys faction.
                else {
                    factions.add(new SystemFactionImpl(id, name));
                    factions.add(new SystemFactionImpl(id, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factions;
    }

    default void insertChunkIfNotExists(FChunk chunk) {
        String chunkInsertion = "INSERT INTO claims (id, x, z, world_id) SELECT * FROM ( SELECT ?, ?, ?, ?) AS tmp " +
                "WHERE NOT EXISTS ( SELECT id FROM claims WHERE id = ?) LIMIT 1;";
        try (PreparedStatement statement = this.getPreparedStatement(chunkInsertion)) {
            statement.setString(1, chunk.getId());
            statement.setInt(2, chunk.getX());
            statement.setInt(3, chunk.getZ());
            statement.setString(4, chunk.getWorldId().toString());
            statement.setString(5, chunk.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void claimForFaction(Faction faction, FChunk chunk, UUID claimerId) {

        this.insertChunkIfNotExists(chunk);

        String sql = "INSERT INTO as_faction_claims (faction_id, claim_id, claimed_by) VALUES (?,?,?);";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, faction.getId().toString());
            statement.setString(2, faction.getName());
            statement.setString(3, claimerId.toString());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    default Faction getFactionAtChunk(FChunk chunk) {
        String sql = "SELECT * FROM as_faction_claims AS rel" +
                " JOIN factions AS f ON f.id = rel.faction_id " +
                " WHERE rel.claim_id = ?;";

        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {
            preparedStatement.setString(1, chunk.getId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return fromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    default @Nullable Faction fromResultSet(ResultSet rs) {
        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                boolean isSystemFaction = rs.getBoolean("system_faction");

                // Returns normal faction.
                if (!isSystemFaction) {
                    return new FactionImpl(id, name);
                }
                // Returns sys faction.
                else {
                    return new SystemFactionImpl(id, name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
