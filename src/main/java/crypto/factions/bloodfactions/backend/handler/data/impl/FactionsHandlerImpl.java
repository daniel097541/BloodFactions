package crypto.factions.bloodfactions.backend.handler.data.impl;

import crypto.factions.bloodfactions.backend.handler.data.FactionsHandler;
import crypto.factions.bloodfactions.backend.manager.FactionsManager;
import crypto.factions.bloodfactions.backend.manager.PlayersManager;
import crypto.factions.bloodfactions.backend.manager.RanksManager;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.annotation.config.SystemConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.tasks.handler.TasksHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Getter
public class FactionsHandlerImpl implements FactionsHandler {

    private final FactionsManager manager;
    private final RanksManager ranksManager;
    private final PlayersManager playersManager;
    private final JavaPlugin plugin;
    private final NGFConfig systemConfig;
    private final NGFConfig langConfig;
    private final TasksHandler tasksHandler;
    private final Map<UUID, FPlayer> unClaimingAllPlayers = new ConcurrentHashMap<>();

    @Inject
    public FactionsHandlerImpl(JavaPlugin plugin,
                               FactionsManager factionsManager,
                               RanksManager ranksManager, PlayersManager playersManager, @SystemConfiguration NGFConfig systemConfig,
                               @LangConfiguration NGFConfig langConfig,
                               TasksHandler tasksHandler) {
        this.manager = factionsManager;
        this.plugin = plugin;
        this.ranksManager = ranksManager;
        this.playersManager = playersManager;
        this.systemConfig = systemConfig;
        this.langConfig = langConfig;
        this.tasksHandler = tasksHandler;
        this.autoRegister();
        this.onLoad();
    }
}
