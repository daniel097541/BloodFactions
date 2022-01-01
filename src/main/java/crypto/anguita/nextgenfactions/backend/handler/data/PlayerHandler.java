package crypto.anguita.nextgenfactions.backend.handler.data;

import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.anguita.nextgenfactions.commons.events.shared.callback.GetPlayersInFactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayerImpl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public interface PlayerHandler extends DataHandler<FPlayer> {

    @Override
    PlayerDAO getDao();


    @EventHandler(priority = EventPriority.HIGHEST)
    default void getPlayer(GetPlayerEvent event) {
        UUID uuid = event.getId();
        FPlayer player = this.getById(uuid);

        if (Objects.isNull(player)) {

            // Create the player in DB.
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            player = new FPlayerImpl(offlinePlayer.getUniqueId(), offlinePlayer.getName(), 0);
            player = this.getDao().insert(player);

            // Add the player to the faction-less faction.
            Faction factionLessFaction = NextGenFactionsAPI.getFactionByName("Wilderness");
            this.getDao().addPlayerToFaction(player, factionLessFaction);
        }

        event.setPlayer(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void getPlayersInFaction(GetPlayersInFactionEvent event){
        Faction faction = event.getFaction();
        Set<FPlayer> playersInFaction = this.getDao().findPlayersInFaction(faction.getId());
        event.setPlayers(playersInFaction);
    }

}
