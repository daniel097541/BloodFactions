package crypto.factions.bloodfactions.backend.handler.data.impl;

import crypto.factions.bloodfactions.backend.dao.FactionsDAO;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.handler.data.FactionsHandler;
import crypto.factions.bloodfactions.commons.annotation.config.SystemConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
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
