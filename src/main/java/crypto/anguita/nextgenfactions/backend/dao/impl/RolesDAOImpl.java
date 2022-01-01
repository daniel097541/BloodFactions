package crypto.anguita.nextgenfactions.backend.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.anguita.nextgenfactions.backend.dao.RolesDAO;
import crypto.anguita.nextgenfactions.backend.manager.DBManager;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
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
