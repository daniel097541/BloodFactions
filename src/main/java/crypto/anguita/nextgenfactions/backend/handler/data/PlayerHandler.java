package crypto.anguita.nextgenfactions.backend.handler.data;

import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.dao.RolesDAO;
import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.events.player.callback.CheckIfPlayerHasFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.anguita.nextgenfactions.commons.events.role.ChangeRoleOfPlayerEvent;
import crypto.anguita.nextgenfactions.commons.events.role.GetRoleOfPlayerEvent;
import crypto.anguita.nextgenfactions.commons.events.shared.callback.GetPlayersInFactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayerImpl;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
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

    RolesDAO getRolesDAO();


    @EventHandler(priority = EventPriority.HIGHEST)
    default void checkIfPlayerHasFaction(CheckIfPlayerHasFactionEvent event) {
        FPlayer player = event.getPlayer();
        boolean hasFaction = this.getDao().checkIfPlayerHasFaction(player.getId());
        event.setHasFaction(hasFaction);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void getPlayer(GetPlayerEvent event) {
        UUID uuid = event.getId();
        FPlayer player = this.getById(uuid);

        if (Objects.isNull(player)) {

            // Create the player in DB.
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            player = new FPlayerImpl(offlinePlayer.getUniqueId(), offlinePlayer.getName(), 0);
            player = this.getDao().insert(player);

            if (Objects.nonNull(player)) {
                // Add the player to the faction-less faction.
                Faction factionLessFaction = NextGenFactionsAPI.getFactionLessFaction();
                this.getDao().addPlayerToFaction(player, factionLessFaction, player);
            }
        }

        event.setPlayer(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void getPlayersInFaction(GetPlayersInFactionEvent event) {
        Faction faction = event.getFaction();
        Set<FPlayer> playersInFaction = this.getDao().findPlayersInFaction(faction.getId());
        event.setPlayers(playersInFaction);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetRoleOfPlayer(GetRoleOfPlayerEvent event) {
        FPlayer player = event.getPlayer();
        FactionRole role = this.getRolesDAO().getRoleOFPlayer(player.getId());
        event.setRole(role);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleChangeRoleOfPlayer(ChangeRoleOfPlayerEvent event) {
        FPlayer player = event.getPlayerToBeChanged();
        FactionRole role = event.getRole();

        this.getRolesDAO().setPlayersRole(player, role);
        event.setChanged(true);
    }

}
