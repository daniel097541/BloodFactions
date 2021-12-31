package crypto.anguita.nextgenfactions.backend.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.manager.DBManager;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
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
public class PlayerDAOImpl implements PlayerDAO {
    private final String tableName = "players";
    private final LoadingCache<UUID, FPlayer> cache = this.createCache(500, 5, TimeUnit.MINUTES);
    private final DBManager dbManager;

    @Inject
    public PlayerDAOImpl(DBManager dbManager) {
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
