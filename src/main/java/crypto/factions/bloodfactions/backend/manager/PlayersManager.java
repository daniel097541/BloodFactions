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

    default void updatePlayersAutoFly(FPlayer player, boolean autoFly) {

    }

    default void updatePlayersFlightMode(FPlayer player, boolean flying) {

    }

    default void updatePlayersPower(FPlayer player) {

    }

    default Set<FPlayer> findPlayersInFaction(Faction faction) {
        return getDAO().findPlayersInFaction(faction.getId());
    }
}
