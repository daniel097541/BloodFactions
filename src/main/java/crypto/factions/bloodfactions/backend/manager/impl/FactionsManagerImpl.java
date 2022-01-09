package crypto.factions.bloodfactions.backend.manager.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.backend.dao.FactionsDAO;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.manager.FactionsManager;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class FactionsManagerImpl implements FactionsManager {


    private final RolesDAO rolesDAO;
    private final FactionsDAO DAO;
    private final PlayerDAO playerDAO;
    private final JavaPlugin plugin;
    private final LoadingCache<UUID, Faction> cache = this.buildCache(5, TimeUnit.MINUTES);

    private final LoadingCache<UUID, Faction> playerFactionCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build(new CacheLoader<UUID, Faction>() {
                @Override
                public @NotNull Faction load(@NotNull UUID key) throws NullPointerException {
                    return getDAO().getFactionOfPlayer(key);
                }
            });

    private final LoadingCache<String, Faction> nameFactionsCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build(new CacheLoader<String, Faction>() {
                @Override
                public @NotNull Faction load(@NotNull String key) throws NullPointerException {
                    return Objects.requireNonNull(getDAO().findByName(key));
                }
            });

    private final LoadingCache<String, Faction> chunkFactionsCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .maximumSize(10000)
            .build(new CacheLoader<String, Faction>() {
                @Override
                public @NotNull Faction load(@NotNull String key) {
                    return getDAO().getFactionAtChunk(key);
                }
            });

    @Inject
    public FactionsManagerImpl(RolesDAO rolesDAO, FactionsDAO factionsDAO, PlayerDAO playerDAO, JavaPlugin plugin) {
        this.rolesDAO = rolesDAO;
        this.DAO = factionsDAO;
        this.playerDAO = playerDAO;
        this.plugin = plugin;
    }
}
