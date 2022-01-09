package crypto.factions.bloodfactions.backend.handler.data.impl;

import crypto.factions.bloodfactions.backend.handler.data.PlayerHandler;
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
public class PlayerHandlerImpl implements PlayerHandler {

    private final PlayersManager manager;
    private final RanksManager ranksManager;
    private final FactionsManager factionsManager;
    private final JavaPlugin plugin;
    private final NGFConfig langConfig;
    private final NGFConfig sysConfig;
    private final TasksHandler tasksHandler;
    private final Map<UUID, FPlayer> noFallDamagePlayers = new ConcurrentHashMap<>();

    @Inject
    public PlayerHandlerImpl(PlayersManager manager,
                             JavaPlugin plugin,
                             RanksManager ranksManager,
                             FactionsManager factionsManager,
                             @LangConfiguration NGFConfig langConfig,
                             @SystemConfiguration NGFConfig systemConfig,
                             TasksHandler tasksHandler) {
        this.manager = manager;
        this.plugin = plugin;
        this.ranksManager = ranksManager;
        this.factionsManager = factionsManager;
        this.langConfig = langConfig;
        this.sysConfig = systemConfig;
        this.tasksHandler = tasksHandler;
        this.autoRegister();
    }
}
