package crypto.anguita.nextgenfactions.backend.dao;

import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRoleImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface RolesDAO extends DAO<FactionRole> {


    @Override
    default @Nullable FactionRole fromResultSet(@NotNull ResultSet rs) {
        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                return new FactionRoleImpl(id, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    default @NotNull Set<FactionRole> getSetFromResultSet(@NotNull ResultSet rs) {
        final Set<FactionRole> roles = new HashSet<>();
        try {
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                FactionRole role = new FactionRoleImpl(id, name);
                roles.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    /**
     * Retrieves all the roles that a faction has.
     *
     * @param factionId
     * @return
     */
    default @NotNull Set<FactionRole> getAllRolesOfFaction(@NotNull UUID factionId) {
        final Set<FactionRole> roles = new HashSet<>();

        String sql = "SELECT * FROM as_faction_roles AS rel " +
                " JOIN roles AS r ON r.id = rel.role_id " +
                " WHERE rel.faction_id = ?;";

        try (PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, factionId.toString());

            try (ResultSet rs = statement.executeQuery()) {
                roles.addAll(this.getSetFromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }


}
