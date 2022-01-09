package crypto.factions.bloodfactions.backend.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.dao.FactionsDAO;
import crypto.factions.bloodfactions.backend.db.DBManager;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class FactionsDAOImpl implements FactionsDAO {

    private final String tableName = "factions";
    private final DBManager dbManager;

    @Inject
    public FactionsDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }
}
