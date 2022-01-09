package crypto.factions.bloodfactions.backend.manager.impl;

import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.manager.RanksManager;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class RanksManagerImpl implements RanksManager {

    private final RolesDAO DAO;

    private final JavaPlugin plugin;

    private final LoadingCache<UUID, FactionRank> cache = this.buildCache(2, TimeUnit.MINUTES);

    @Inject
    public RanksManagerImpl(RolesDAO dao, JavaPlugin plugin) {
        this.DAO = dao;
        this.plugin = plugin;
    }
}
