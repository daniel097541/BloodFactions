package crypto.factions.bloodfactions.backend.handler.permissions;

import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.manager.PlayersManager;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public interface PermissionsHandler extends Listener {


    JavaPlugin getPlugin();

    PlayersManager getPlayerManager();

    RolesDAO getRolesDAO();

    NGFConfig getLangConfig();

    default void autoRegister() {
        this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
    }

    default boolean checkPlayerPermissions(FPlayer player, PermissionType permissionType) {
        boolean hasAllPermissions = this.getPlayerManager().checkIfPlayerHasPermission(player, PermissionType.ALL);
        boolean hasPermission = this.getPlayerManager().checkIfPlayerHasPermission(player, permissionType);

        return hasAllPermissions && hasPermission;
    }

    default boolean checkPlayersRolePermissions(FPlayer player, PermissionType permissionType) {
        FactionRank role = this.getRolesDAO().getRoleOFPlayer(player.getId());

        if (Objects.nonNull(role)) {
            Logger.logInfo("Checking if role " + role.getName() + " has permission to: " + permissionType.name());
            return this.getRolesDAO().roleHasPermission(role, permissionType);
        }
        return false;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    default void checkPermissions(PermissionEvent event) {

        FPlayer player = event.getPlayer();
        PermissionType permissionType = event.getPermissionType();

        // If player is OP, has all permissions.
        if (player.isOp()) {

            // Check concrete permission and ALL permission.
            boolean playerPermissions = this.checkPlayerPermissions(player, permissionType);
            boolean rolePermissions = this.checkPlayersRolePermissions(player, permissionType);

            // Role has no permission either.
            if (!playerPermissions && !rolePermissions) {
                event.setCancelled(true);

                String noPermissionMessage = (String) this.getLangConfig().get(LangConfigItems.FACTION_PERMISSIONS_NO_PERMISSION);
                MessageContext messageContext = new MessageContextImpl(player, noPermissionMessage);
                messageContext.setPermission(permissionType);
                player.sms(messageContext);
                event.setPermissionRestricted(true);
                Logger.logInfo("Player " + player.getName() + " has NOT permissions to perform: " + permissionType.name());
            }
        }

        Logger.logInfo("Player " + player.getName() + " has permissions to perform: " + permissionType.name());
    }
}
