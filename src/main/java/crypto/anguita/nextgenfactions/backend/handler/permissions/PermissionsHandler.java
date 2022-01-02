package crypto.anguita.nextgenfactions.backend.handler.permissions;

import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.dao.RolesDAO;
import crypto.anguita.nextgenfactions.commons.events.PermissionEvent;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
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

    @EventHandler(priority = EventPriority.HIGHEST)
    default void checkPermissions(PermissionEvent event) {

        FPlayer player = event.getPlayer();
        PermissionType permissionType = event.getPermissionType();

        boolean hasPermission = this.getPlayerDao().checkIfPlayerHasPermission(player, permissionType);

        // Player has no permission, check its role.
        if (!hasPermission) {
            FactionRole role = this.getRolesDAO().getRoleOFPlayer(player.getId());
            if (Objects.nonNull(role)) {
                hasPermission = this.getRolesDAO().roleHasPermission(role, permissionType);
            }
        }

        // Role has no permission either.
        if(!hasPermission){
            event.setCancelled(true);
            event.setFailureMessage("&cYou dont have permissions in your faction to perform that action!");
            event.setPermissionRestricted(true);
        }

    }


}
