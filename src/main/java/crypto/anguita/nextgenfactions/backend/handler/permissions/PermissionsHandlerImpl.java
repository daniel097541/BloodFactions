package crypto.anguita.nextgenfactions.backend.handler.permissions;

import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.dao.RolesDAO;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Getter
public class PermissionsHandlerImpl implements PermissionsHandler {

    private final PlayerDAO playerDao;
    private final JavaPlugin plugin;
    private final RolesDAO rolesDAO;

    @Inject
    public PermissionsHandlerImpl(PlayerDAO playerDAO, JavaPlugin plugin, RolesDAO rolesDAO) {
        this.playerDao = playerDAO;
        this.plugin = plugin;
        this.rolesDAO = rolesDAO;
        this.autoRegister();
    }
}
