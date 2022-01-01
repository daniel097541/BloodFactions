package crypto.anguita.nextgenfactions.backend.handler.permissions;

import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.commons.events.PermissionEvent;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public interface PermissionsHandler extends Listener {


    JavaPlugin getPlugin();

    PlayerDAO getPlayerDao();

    default void autoRegister(){
        this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void checkPermissions(PermissionEvent event){

        FPlayer player = event.getPlayer();
        PermissionType permissionType = event.getPermissionType();

        boolean hasPermission = this.getPlayerDao().checkIfPlayerHasPermission(player, permissionType);
        event.setPermissionRestricted(!hasPermission);
        event.setFailureMessage("&cYou dont have permissions in your faction to perform that action!");
    }


}
