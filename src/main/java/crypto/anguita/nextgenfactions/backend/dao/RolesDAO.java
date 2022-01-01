package crypto.anguita.nextgenfactions.backend.dao;

import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
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

    /**
     * Gets the permissions of the role.
     * @param roleId
     * @return
     */
    default @NotNull Set<PermissionType> getRolePermissions(@NotNull UUID roleId) {
        final String sql = "SELECT * FROM as_role_permissions AS rel " +
                " WHERE rel.role_id = ?;";

        final Set<PermissionType> permissions = new HashSet<>();

        try (final PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, roleId.toString());

            try (final ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("permission_id");
                    PermissionType permissionType = PermissionType.fromId(id);
                    permissions.add(permissionType);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return permissions;
    }

    /**
     * Adds a permission to the given role.
     *
     * @param role
     * @param permissionType
     */
    default void addPermissionToRole(@NotNull FactionRole role, @NotNull PermissionType permissionType) {
        final String sql = "INSERT INTO as_role_permissions (role_id, permission_id) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {
            preparedStatement.setString(1, role.getId().toString());
            preparedStatement.setInt(2, permissionType.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a permission from the given role.
     *
     * @param role
     * @param permissionType
     */
    default void removePermissionFromRole(@NotNull FactionRole role, @NotNull PermissionType permissionType) {
        final String sql = "DELETE FROM as_role_permissions WHERE role_id = ? AND permission_id = ?;";
        try (final PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {
            preparedStatement.setString(1, role.getId().toString());
            preparedStatement.setInt(2, permissionType.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a role has the permission.
     *
     * @param role
     * @param permissionType
     * @return
     */
    default boolean roleHasPermission(@NotNull FactionRole role, @NotNull PermissionType permissionType) {

        final String sql = "SELECT count(*) AS count FROM as_role_permissions AS rel " +
                " WHERE rel.role_id = ? AND permission_id = ?;";

        try (final PreparedStatement statement = this.getPreparedStatement(sql)) {

            statement.setString(1, role.getId().toString());
            statement.setInt(2, permissionType.getId());

            try (final ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0;
                } else {
                    return false;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Gets the role of a player in a faction.
     *
     * @param playerId
     * @return
     */
    default @Nullable FactionRole getRoleOFPlayerInFaction(@NotNull UUID playerId, @NotNull UUID factionId) {
        final String sql = "SELECT * FROM as_player_role AS rel " +
                " JOIN roles AS r ON r.id = rel.role_id " +
                " WHERE rel.player_id = ? AND rel.faction_id = ?;";

        try (final PreparedStatement statement = this.getPreparedStatement(sql)) {
            statement.setString(1, playerId.toString());
            statement.setString(2, factionId.toString());
            try (final ResultSet rs = statement.executeQuery()) {
                return this.fromResultSet(rs);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
