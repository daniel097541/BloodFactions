package crypto.factions.bloodfactions.backend.dao;

import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import crypto.factions.bloodfactions.commons.model.role.FactionRoleImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface RolesDAO extends DAO<FactionRank> {


    @Override
    default @Nullable FactionRank fromResultSet(@NotNull ResultSet rs) {
        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                boolean isDefaultRole = rs.getBoolean("default_role");
                UUID factionId = UUID.fromString(rs.getString("faction_id"));
                return new FactionRoleImpl(id, factionId, name, isDefaultRole);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    default @NotNull Set<FactionRank> getSetFromResultSet(@NotNull ResultSet rs) {
        final Set<FactionRank> roles = new HashSet<>();
        try {
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                boolean isDefaultRole = rs.getBoolean("default_role");
                UUID factionId = UUID.fromString(rs.getString("faction_id"));
                FactionRank role = new FactionRoleImpl(id, factionId, name, isDefaultRole);
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
    default @NotNull Set<FactionRank> getAllRolesOfFaction(@NotNull UUID factionId) {
        final Set<FactionRank> roles = new HashSet<>();

        String sql = "SELECT * FROM roles AS r WHERE r.faction_id = ?;";

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
     *
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
    default void addPermissionToRole(@NotNull FactionRank role, @NotNull PermissionType permissionType) {
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
    default void removePermissionFromRole(@NotNull FactionRank role, @NotNull PermissionType permissionType) {
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
    default boolean roleHasPermission(@NotNull FactionRank role, @NotNull PermissionType permissionType) {

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
    default @Nullable FactionRank getRoleOFPlayer(@NotNull UUID playerId) {
        final String sql = "SELECT * FROM as_player_role AS rel " +
                " JOIN roles AS r ON r.id = rel.role_id " +
                " WHERE rel.player_id = ?;";

        try (final PreparedStatement statement = this.getPreparedStatement(sql)) {
            statement.setString(1, playerId.toString());
            try (final ResultSet rs = statement.executeQuery()) {
                return this.fromResultSet(rs);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the default role of the faction.
     * @param factionId
     * @return
     */
    default @Nullable FactionRank getDefaultRole(@NotNull UUID factionId){
        final String sql = "SELECT * FROM roles AS r " +
                " WHERE r.faction_id = ? AND default_role = true;";
        try(final PreparedStatement statement = this.getPreparedStatement(sql)){
            statement.setString(1, factionId.toString());
            try(final ResultSet rs = statement.executeQuery()){
                return this.fromResultSet(rs);
            }
        }
        catch (final Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets the role of the player.
     * @param player
     * @param role
     * @return
     */
    default boolean setPlayersRole(@NotNull FPlayer player, @NotNull FactionRank role){
        UUID playerId = player.getId();
        UUID roleId = role.getId();

        final String sql = "INSERT INTO as_player_role (player_id, role_id) VALUES (?,?);";

        try(final PreparedStatement statement = this.getPreparedStatement(sql)){
            statement.setString(1, playerId.toString());
            statement.setString(2, roleId.toString());
            statement.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    default boolean deleteRoleByName(String name, UUID factionId){

        String sql = "DELETE FROM roles WHERE name = ? AND faction_id = ?;";

        try(PreparedStatement statement = this.getPreparedStatement(sql)){

            statement.setString(1, name);
            statement.setString(2, factionId.toString());

            int deleted = statement.executeUpdate();
            return deleted > 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    default boolean roleExistsByName(String name, UUID factionId){

        String sql = "SELECT count(*) AS count FROM roles AS r WHERE r.name = ? AND r.faction_id = ?;";

        try(PreparedStatement statement = this.getPreparedStatement(sql)){

            statement.setString(1, name);
            statement.setString(2, factionId.toString());

            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    int count = rs.getInt("count");
                    return count > 0;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

}
