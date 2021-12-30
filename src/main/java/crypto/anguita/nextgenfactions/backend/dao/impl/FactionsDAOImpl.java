package crypto.anguita.nextgenfactions.backend.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.anguita.nextgenfactions.backend.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.backend.manager.DBManager;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.faction.FactionImpl;
import crypto.anguita.nextgenfactions.commons.model.faction.SystemFactionImpl;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @Override
    public @Nullable Faction fromResultSet(ResultSet rs) {
        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                boolean isSystemFaction = rs.getBoolean("system_faction");

                // Returns normal faction.
                if (!isSystemFaction) {
                    return new FactionImpl(id, name);
                }
                // Returns sys faction.
                else {
                    return new SystemFactionImpl(id, name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Faction getFactionAtChunk(FChunk chunk) {
        String sql = "SELECT (f.id AS id, f.name AS name, f.system_faction AS system_faction) FROM as_faction_claims AS rel" +
                " JOIN factions AS f ON f.id = rel.faction_id " +
                " WHERE rel.claim_id = ?";

        try (PreparedStatement preparedStatement = this.getPreparedStatement(sql)) {
            preparedStatement.setString(1, chunk.getId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return fromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
