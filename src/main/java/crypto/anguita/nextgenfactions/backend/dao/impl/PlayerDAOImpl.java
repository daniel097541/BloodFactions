package crypto.anguita.nextgenfactions.backend.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.manager.DBManager;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayerImpl;
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
public class PlayerDAOImpl implements PlayerDAO {
    private final String tableName = "players";
    private final LoadingCache<UUID, FPlayer> cache = this.createCache(500, 5, TimeUnit.MINUTES);
    private final DBManager dbManager;

    @Override
    public @Nullable FPlayer fromResultSet(ResultSet rs) {
        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                int power = rs.getInt("power");
                return new FPlayerImpl(id, name, power);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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

    @Override
    public FPlayer insert(FPlayer player) {

        String sql = "INSERT INTO players (id, name, power) VALUES (?,?,?);";

        try(PreparedStatement statement = this.getPreparedStatement(sql)){

            statement.setString(1, player.getId().toString());
            statement.setString(2, player.getName());
            statement.setInt(3, player.getPower());

            statement.executeUpdate();

            return player;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
