package crypto.factions.bloodfactions.backend.handler.data.impl;

import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.handler.data.PlayerHandler;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.annotation.config.SystemConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Getter
public class PlayerHandlerImpl implements PlayerHandler {

    private final PlayerDAO dao;
    private final JavaPlugin plugin;
    private final RolesDAO rolesDAO;
    private final NGFConfig langConfig;
    private final NGFConfig sysConfig;

    @Inject
    public PlayerHandlerImpl(PlayerDAO playerDAO,
                             JavaPlugin plugin,
                             RolesDAO rolesDAO,
                             @LangConfiguration NGFConfig langConfig,
                             @SystemConfiguration NGFConfig systemConfig) {
        this.dao = playerDAO;
        this.plugin = plugin;
        this.rolesDAO = rolesDAO;
        this.langConfig = langConfig;
        this.sysConfig = systemConfig;
        this.autoRegister();
    }
}
