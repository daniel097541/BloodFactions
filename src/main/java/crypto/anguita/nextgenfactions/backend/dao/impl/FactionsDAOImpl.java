package crypto.anguita.nextgenfactions.backend.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.anguita.nextgenfactions.backend.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.backend.manager.DBManager;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class FactionsDAOImpl implements FactionsDAO {

    private final String tableName = "factions";
    private final LoadingCache<UUID, Faction> cache = this.createCache(500, 5, TimeUnit.MINUTES);
    private final DBManager dbManager;

    @Inject
    public FactionsDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public @NotNull Statement getStatement() {
        return this.dbManager.getStatement();
    }

    @Override
    public @NotNull PreparedStatement getPreparedStatement(String sql) {
        return this.dbManager.getPreparedStatement(sql);
    }
}
