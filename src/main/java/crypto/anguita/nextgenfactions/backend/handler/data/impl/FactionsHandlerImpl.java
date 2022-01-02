package crypto.anguita.nextgenfactions.backend.handler.data.impl;

import crypto.anguita.nextgenfactions.backend.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.dao.RolesDAO;
import crypto.anguita.nextgenfactions.backend.handler.data.FactionsHandler;
import crypto.anguita.nextgenfactions.commons.annotation.config.SystemConfiguration;
import crypto.anguita.nextgenfactions.commons.config.NGFConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Getter
public class FactionsHandlerImpl implements FactionsHandler {

    private final FactionsDAO dao;
    private final PlayerDAO playerDAO;
    private final RolesDAO rolesDAO;
    private final JavaPlugin plugin;
    private final NGFConfig systemConfig;

    @Inject
    public FactionsHandlerImpl(JavaPlugin plugin,
                               FactionsDAO factionsDAO,
                               PlayerDAO playerDAO,
                               RolesDAO rolesDAO,
                               @SystemConfiguration NGFConfig systemConfig) {
        this.dao = factionsDAO;
        this.plugin = plugin;
        this.playerDAO = playerDAO;
        this.rolesDAO = rolesDAO;
        this.systemConfig = systemConfig;
        this.autoRegister();
        this.onLoad();
    }
}
