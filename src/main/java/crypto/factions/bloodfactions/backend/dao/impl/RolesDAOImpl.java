package crypto.factions.bloodfactions.backend.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.manager.DBManager;
import crypto.factions.bloodfactions.commons.model.role.FactionRole;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Singleton
public class RolesDAOImpl implements RolesDAO {
    private final String tableName = "roles";
    private final LoadingCache<UUID, FactionRole> cache = this.createCache(500, 5, TimeUnit.MINUTES);
    private final DBManager dbManager;

    @Inject
    public RolesDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }


}
