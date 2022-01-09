package crypto.factions.bloodfactions.backend.manager;

import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import java.util.Set;
import java.util.UUID;

public interface PlayersManager extends DataManager<FPlayer> {

    PlayerDAO getDAO();

    LoadingCache<String, FPlayer> getNamePlayersCache();

    LoadingCache<UUID, FPlayer> getPlayersCache();

    default boolean updatePlayersAutoFly(FPlayer player, boolean autoFly) {
        return this.getDAO().updatePlayersAutoFly(player.getId(), autoFly);
    }

    default boolean updatePlayersFlightMode(FPlayer player, boolean flying) {
        return this.getDAO().updatePlayersFlightMode(player.getId(), flying);
    }

    default void updatePlayersPower(FPlayer player) {
        this.getDAO().updatePlayersPower(player.getId(), player.getPower());
    }

    default Set<FPlayer> findPlayersInFaction(Faction faction) {
        return getDAO().findPlayersInFaction(faction.getId());
    }
}
