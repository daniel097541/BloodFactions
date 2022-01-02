package crypto.anguita.nextgenfactions.backend.handler.data.impl;

import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.dao.RolesDAO;
import crypto.anguita.nextgenfactions.backend.handler.data.PlayerHandler;
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

    @Inject
    public PlayerHandlerImpl(PlayerDAO playerDAO, JavaPlugin plugin, RolesDAO rolesDAO) {
        this.dao = playerDAO;
        this.plugin = plugin;
        this.rolesDAO = rolesDAO;
        this.autoRegister();
    }
}
