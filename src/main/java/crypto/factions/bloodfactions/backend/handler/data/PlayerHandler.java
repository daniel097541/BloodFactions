package crypto.factions.bloodfactions.backend.handler.data;

import crypto.factions.bloodfactions.backend.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.events.player.callback.CheckIfPlayerHasFactionEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.factions.bloodfactions.commons.events.player.permissioned.PlayerFlightEvent;
import crypto.factions.bloodfactions.commons.events.player.unpermissioned.PlayerChangedLandEvent;
import crypto.factions.bloodfactions.commons.events.role.ChangeRoleOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.role.GetRoleOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.shared.callback.GetPlayersInFactionEvent;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.player.FPlayerImpl;
import crypto.factions.bloodfactions.commons.model.role.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public interface PlayerHandler extends DataHandler<FPlayer> {

    @Override
    PlayerDAO getDao();

    RolesDAO getRolesDAO();

    NGFConfig getLangConfig();


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
            player = new FPlayerImpl(offlinePlayer.getUniqueId(), offlinePlayer.getName(), false, 0);
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

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerChangedLandEvent(PlayerChangedLandEvent event) {

        FPlayer player = event.getPlayer();
        Faction factionFrom = event.getFactionFrom();
        Faction factionTo = event.getFactionTo();

        // Cancel flight
        if (player.isFlying() && factionFrom.equals(player.getFaction())) {
            player.toggleFly();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleToggleFlight(PlayerFlightEvent event){

        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();
        Player bukkitPlayer = player.getBukkitPlayer();

        boolean flying = false;
        if(player.isFlying()){
            Objects.requireNonNull(bukkitPlayer).setAllowFlight(false);
            bukkitPlayer.setFlying(false);
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_FLY_OFF);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }
        else{
            Objects.requireNonNull(bukkitPlayer).setAllowFlight(true);
            bukkitPlayer.setFlying(true);
            flying = true;
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_FLY_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }

        event.setSuccess(flying);
        this.getDao().updatePlayersFlightMode(player.getId(), flying);
    }

}
