package crypto.anguita.nextgenfactions.backend.handler.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.anguita.nextgenfactions.backend.handler.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.faction.FactionImpl;
import crypto.anguita.nextgenfactions.commons.model.faction.SystemFactionImpl;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class FactionsDAOImpl implements FactionsDAO {

    private final String tableName = "factions";
    private final LoadingCache<UUID, Faction> cache = this.createCache(500, 5, TimeUnit.MINUTES);

    @Override
    public @NotNull PreparedStatement getStatement() {
        return null;
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
}
