package crypto.anguita.nextgenfactions.backend.handler.data.impl;

import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
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


    @Inject
    public PlayerHandlerImpl(PlayerDAO playerDAO, JavaPlugin plugin) {
        this.dao = playerDAO;
        this.plugin = plugin;
        this.autoRegister();
    }
}
