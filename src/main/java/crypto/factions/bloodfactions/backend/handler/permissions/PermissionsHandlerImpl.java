package crypto.factions.bloodfactions.backend.handler.permissions;

import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.manager.PlayersManager;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Getter
public class PermissionsHandlerImpl implements PermissionsHandler {

    private final JavaPlugin plugin;
    private final RolesDAO rolesDAO;
    private final PlayersManager playerManager;
    private final NGFConfig langConfig;

    @Inject
    public PermissionsHandlerImpl(JavaPlugin plugin, RolesDAO rolesDAO, PlayersManager playersManager, @LangConfiguration NGFConfig langConfig) {
        this.plugin = plugin;
        this.rolesDAO = rolesDAO;
        this.playerManager = playersManager;
        this.langConfig = langConfig;
        this.autoRegister();
    }
}
