package crypto.factions.bloodfactions.backend.handler.data.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.dao.FactionsDAO;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.handler.data.FactionsHandler;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.annotation.config.SystemConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.tasks.handler.TasksHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class FactionsHandlerImpl implements FactionsHandler {

    private final FactionsDAO dao;
    private final PlayerDAO playerDAO;
    private final RolesDAO rolesDAO;
    private final JavaPlugin plugin;
    private final NGFConfig systemConfig;
    private final NGFConfig langConfig;
    private final TasksHandler tasksHandler;
    private final Map<UUID, FPlayer> unClaimingAllPlayers = new ConcurrentHashMap<>();

    private final LoadingCache<String, Faction> chunkFactionsCache = CacheBuilder
            .newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .recordStats()
            .build(new CacheLoader<String, Faction>() {
                @Override
                public @NotNull Faction load(@NotNull String key) throws Exception {
                    Faction faction = dao.getFactionAtChunk(key);
                    if (Objects.isNull(faction)) {
                        faction = getFactionForFactionLess();
                    }
                    return faction;
                }
            });

    private final LoadingCache<String, Faction> nameFactionsCache = CacheBuilder
            .newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Faction>() {
                @Override
                public @NotNull Faction load(@NotNull String key) throws Exception {
                    return Objects.requireNonNull(dao.findByName(key));
                }
            });

    private final LoadingCache<UUID, Faction> playerFactionsCache = CacheBuilder
            .newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<UUID, Faction>() {
                @Override
                public @NotNull Faction load(@NotNull UUID key) throws Exception {
                    return dao.getFactionOfPlayer(key);
                }
            });

    @Inject
    public FactionsHandlerImpl(JavaPlugin plugin,
                               FactionsDAO factionsDAO,
                               PlayerDAO playerDAO,
                               RolesDAO rolesDAO,
                               @SystemConfiguration NGFConfig systemConfig,
                               @LangConfiguration NGFConfig langConfig,
                               TasksHandler tasksHandler) {
        this.dao = factionsDAO;
        this.plugin = plugin;
        this.playerDAO = playerDAO;
        this.rolesDAO = rolesDAO;
        this.systemConfig = systemConfig;
        this.langConfig = langConfig;
        this.tasksHandler = tasksHandler;
        this.autoRegister();
        this.onLoad();
    }
}
