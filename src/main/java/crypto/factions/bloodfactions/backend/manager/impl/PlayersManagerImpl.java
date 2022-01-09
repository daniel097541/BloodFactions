package crypto.factions.bloodfactions.backend.manager.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.manager.PlayersManager;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class PlayersManagerImpl implements PlayersManager {

    private final PlayerDAO DAO;
    private final JavaPlugin plugin;
    private final LoadingCache<UUID, FPlayer> cache = this.buildCache(15, TimeUnit.MINUTES);

    private final LoadingCache<String, FPlayer> namePlayersCache = CacheBuilder
            .newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, FPlayer>() {
                @Override
                public @NotNull FPlayer load(@NotNull String key) throws NullPointerException {
                    return Objects.requireNonNull(getDAO().findByName(key));
                }
            });

    private final LoadingCache<UUID, FPlayer> playersCache = CacheBuilder
            .newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<UUID, FPlayer>() {
                @Override
                public @NotNull FPlayer load(@NotNull UUID key) throws NullPointerException {
                    return Objects.requireNonNull(getDAO().findById(key));
                }
            });


    @Inject
    public PlayersManagerImpl(PlayerDAO playerDAO, JavaPlugin plugin) {
        this.DAO = playerDAO;
        this.plugin = plugin;
    }
}
