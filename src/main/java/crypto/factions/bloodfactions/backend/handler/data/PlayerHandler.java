package crypto.factions.bloodfactions.backend.handler.data;

import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.events.player.callback.CheckIfPlayerHasFactionEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.factions.bloodfactions.commons.events.role.ChangeRoleOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.role.GetRoleOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.shared.callback.GetPlayersInFactionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.player.FPlayerImpl;
import crypto.factions.bloodfactions.commons.model.role.FactionRole;
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
