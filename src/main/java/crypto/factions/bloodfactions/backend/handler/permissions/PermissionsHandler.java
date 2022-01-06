package crypto.factions.bloodfactions.backend.handler.permissions;

import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRole;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public interface PermissionsHandler extends Listener {


    JavaPlugin getPlugin();

    PlayerDAO getPlayerDao();

    RolesDAO getRolesDAO();

    default void autoRegister() {
        this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
    }

    default boolean checkPlayerPermissions(FPlayer player, PermissionType permissionType) {
        boolean hasAllPermissions = this.getPlayerDao().checkIfPlayerHasPermission(player, PermissionType.ALL);
        boolean hasPermission = this.getPlayerDao().checkIfPlayerHasPermission(player, permissionType);

        return hasAllPermissions && hasPermission;
    }

    default boolean checkPlayersRolePermissions(FPlayer player, PermissionType permissionType) {
        FactionRole role = this.getRolesDAO().getRoleOFPlayer(player.getId());
        if (Objects.nonNull(role)) {
            return this.getRolesDAO().roleHasPermission(role, permissionType);
        }
        return false;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    default void checkPermissions(PermissionEvent event) {

        FPlayer player = event.getPlayer();
        PermissionType permissionType = event.getPermissionType();

        // If player is OP, has all permissions.
        if (!player.isOp()) {

            // Check concrete permission and ALL permission.
            boolean playerPermissions = this.checkPlayerPermissions(player, permissionType);
            boolean rolePermissions = this.checkPlayersRolePermissions(player, permissionType);

            // Role has no permission either.
            if (!playerPermissions && !rolePermissions) {
                event.setCancelled(true);
                event.setFailureMessage("&cYou dont have permissions in your faction to perform that action!");
                event.setPermissionRestricted(true);
                Logger.logInfo("Player " + player.getName() + " has NOT permissions to perform: " + permissionType.name());
            }
        }

        Logger.logInfo("Player " + player.getName() + " has permissions to perform: " + permissionType.name());
    }
}
