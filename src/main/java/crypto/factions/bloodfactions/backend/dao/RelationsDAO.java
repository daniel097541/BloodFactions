package crypto.factions.bloodfactions.backend.dao;

import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.relation.FactionRelation;
import crypto.factions.bloodfactions.commons.model.relation.FactionRelationImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface RelationsDAO extends DAO<FactionRelation> {

    @Override
    default @NotNull Set<FactionRelation> getSetFromResultSet(@NotNull ResultSet rs) {
        Set<FactionRelation> relations = new HashSet<>();
        try {
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                String color = rs.getString("color");

                relations.add(new FactionRelationImpl(id, name, color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relations;
    }

    @Override
    default @Nullable FactionRelation fromResultSet(@NotNull ResultSet rs) {
        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                String color = rs.getString("color");

                Logger.logInfo("Found role: " + name);

                return new FactionRelationImpl(id, name, color);
            } else {
                Logger.logInfo("Role not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    default boolean deleteRelationOfFactionWithOtherFaction(@NotNull UUID factionId, @NotNull UUID otherFactionId) {

        String sql = "DELETE FROM as_faction_relation WHERE faction_id = ? AND other_faction_id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, factionId.toString());
            statement.setString(2, otherFactionId.toString());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    default boolean setRelationOfFactionWithOtherFaction(@NotNull UUID factionId, @NotNull UUID otherFactionId, @NotNull UUID relationId) {

        String sql = "INSERT INTO as_faction_relations (faction_id, other_faction_id, relation_id) VALUES (?, ?, ?);";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, factionId.toString());
            statement.setString(2, otherFactionId.toString());
            statement.setString(3, relationId.toString());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    default @Nullable FactionRelation getRelationOfFactionWithOtherFaction(@NotNull UUID factionId, @NotNull UUID otherFactionId) {

        String sql = "SELECT * FROM as_faction_relations AS rel " +
                " JOIN faction_relations AS fr ON fr.id = rel.relation_id " +
                " WHERE rel.faction_id = ? AND rel.other_faction_id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, factionId.toString());
            statement.setString(2, otherFactionId.toString());

            try (ResultSet rs = statement.executeQuery()) {
                return this.fromResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
