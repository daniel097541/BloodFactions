package crypto.anguita.nextgenfactions.backend.handler.data.impl;

import crypto.anguita.nextgenfactions.backend.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.backend.handler.data.FactionsHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Getter
public class FactionsHandlerImpl implements FactionsHandler {

    private final FactionsDAO dao;
    private final JavaPlugin plugin;

    @Inject
    public FactionsHandlerImpl(JavaPlugin plugin, FactionsDAO factionsDAO) {
        this.dao = factionsDAO;
        this.plugin = plugin;
        this.autoRegister();
    }
}
