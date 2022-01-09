package crypto.factions.bloodfactions.backend.handler.data;

import crypto.factions.bloodfactions.backend.manager.FactionsManager;
import crypto.factions.bloodfactions.backend.manager.PlayersManager;
import crypto.factions.bloodfactions.backend.manager.RanksManager;
import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.config.system.SystemConfigItems;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.ShowFactionEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.CheckIfPlayerHasFactionEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.GetPlayerByNameEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.factions.bloodfactions.commons.events.player.permissioned.PlayerAutoFlyEvent;
import crypto.factions.bloodfactions.commons.events.player.permissioned.PlayerFlightEvent;
import crypto.factions.bloodfactions.commons.events.player.unpermissioned.*;
import crypto.factions.bloodfactions.commons.events.role.ChangeRankOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.role.GetRoleOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.shared.callback.GetPlayersInFactionEvent;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.player.FPlayerImpl;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import crypto.factions.bloodfactions.commons.tasks.handler.TasksHandler;
import crypto.factions.bloodfactions.commons.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.*;
import java.util.stream.Collectors;

public interface PlayerHandler extends DataHandler<FPlayer> {

    PlayersManager getManager();

    RanksManager getRanksManager();

    FactionsManager getFactionsManager();

    NGFConfig getLangConfig();

    NGFConfig getSysConfig();

    TasksHandler getTasksHandler();

    Map<UUID, FPlayer> getNoFallDamagePlayers();


    @EventHandler(priority = EventPriority.HIGHEST)
    default void checkIfPlayerHasFaction(CheckIfPlayerHasFactionEvent event) {
        FPlayer player = event.getPlayer();
        boolean hasFaction = this.getFactionsManager().checkIfPlayerHasFaction(player);
        event.setHasFaction(hasFaction);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void getPlayer(GetPlayerEvent event) {
        UUID uuid = event.getId();
        FPlayer player = this.getById(uuid);

        if (Objects.isNull(player)) {

            // Create the player in DB.
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            player = new FPlayerImpl(offlinePlayer.getUniqueId(), offlinePlayer.getName(), false, false, 0);
            player = this.getManager().insert(player);

            if (Objects.nonNull(player)) {
                // Add the player to the faction-less faction.
                Faction factionLessFaction = NextGenFactionsAPI.getFactionLessFaction();
                this.getFactionsManager().addPlayerToFaction(player, factionLessFaction, player);
            }
        }

        event.setPlayer(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void getPlayersInFaction(GetPlayersInFactionEvent event) {
        Faction faction = event.getFaction();
        Set<FPlayer> playersInFaction = this.getManager().findPlayersInFaction(faction);
        event.setPlayers(playersInFaction);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetRoleOfPlayer(GetRoleOfPlayerEvent event) {
        FPlayer player = event.getPlayer();
        FactionRank role = this.getRanksManager().getRoleOFPlayer(player);
        event.setRole(role);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleChangeRoleOfPlayer(ChangeRankOfPlayerEvent event) {
        FPlayer player = event.getPlayerToBeChanged();
        FactionRank rank = event.getRole();
        FPlayer playerChangingTheRank = event.getPlayerChangingTheRole();

        FactionRank currentRank = player.getRole();

        // Same rank
        if (rank.equals(currentRank)) {
            String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_PLAYER_ALREADY_IS_RANK);
            MessageContext messageContext = new MessageContextImpl(player, message);
            player.sms(messageContext);
            event.setChanged(false);
        }

        // Not same rank, change it.
        else {
            // Send rank changed to player changing the rank.
            String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_PLAYER_CHANGED_RANK);
            MessageContext messageContext = new MessageContextImpl(player, message);
            messageContext.setTargetPlayer(player);
            messageContext.setRank(rank);
            player.sms(messageContext);

            // Send rank changed to target player
            String targetMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_YOUR_RANK_CHANGED);
            MessageContext targetMessageContext = new MessageContextImpl(player, targetMessage);
            targetMessageContext.setTargetPlayer(playerChangingTheRank);
            targetMessageContext.setRank(rank);
            player.sms(targetMessageContext);

            // Change event
            event.setChanged(false);
            boolean changed = this.getRanksManager().setPlayersRole(player, rank);
            event.setChanged(changed);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerChangedLandEvent(PlayerChangedLandEvent event) {

        FPlayer player = event.getPlayer();
        Faction playersFaction = player.getFaction();
        Faction factionFrom = event.getFactionFrom();
        Faction factionTo = event.getFactionTo();

        // Cancel flight
        if (player.isFlying() && factionFrom.equals(playersFaction)) {
            player.toggleFly();
        }

        // Enable fly if auto flying
        if (player.isAutoFlying() && !player.isFlying() && factionTo.equals(playersFaction)) {
            player.toggleFly();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleAutoFly(PlayerAutoFlyEvent event) {

        FPlayer player = event.getPlayer();

        boolean autoFly = false;

        // Disable auto-fly
        if (player.isAutoFlying()) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_AUTO_FLY_OFF);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }

        // Auto fly
        else {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_AUTO_FLY_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            autoFly = true;
        }

        // Update auto-fly
        this.getManager().updatePlayersAutoFly(player, autoFly);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleToggleFlight(PlayerFlightEvent event) {

        FPlayer player = event.getPlayer();

        boolean flying = false;

        // Disable flight.
        if (player.isFlying()) {
            // No fall damage
            this.addNoFallPlayer(player);

            // Disable flight
            player.disableBukkitFlight();

            // Send message
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_FLY_OFF);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }

        // Enable flight.
        else {
            player.enableBukkitFlight();
            flying = true;
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_FLY_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }

        event.setSuccess(flying);
        this.getManager().updatePlayersFlightMode(player, flying);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleIncrementPower(PlayerPowerChangeEvent event) {

        FPlayer player = event.getPlayer();
        int increment = event.getChange();

        int total = player.getPower() + increment;
        int maxPower = (int) this.getSysConfig().get(SystemConfigItems.SETTINGS_MAX_POWER);
        int minPower = (int) this.getSysConfig().get(SystemConfigItems.SETTINGS_MIN_POWER);

        // Upper bounds. Can't handle more power.
        if (total > 0) {
            if (total > maxPower) {
                total = maxPower;
            }
        }

        // Lower bounds. Can't handle less power.
        else {
            if (total < minPower) {
                total = minPower;
            }
        }

        player.setPower(total);
        // Update power in db.
        this.getManager().updatePlayersPower(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleShowFaction(ShowFactionEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        int power = faction.getPower();
        Set<FPlayer> members = faction.getMembers();
        FPlayer owner = faction.getOwner();

        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("{faction_power}", String.valueOf(power));
        placeHolders.put("{faction_name}", faction.getName());
        placeHolders.put("{faction_members}", members.stream().map(FPlayer::getName).collect(Collectors.joining(", ")));
        placeHolders.put("{faction_owner}", owner.getName());
        placeHolders.put("{faction_claims}", String.valueOf(faction.getAmountOfClaims()));
        placeHolders.put("{can_be_over_claimed}", String.valueOf(faction.canBeOverClaimed()).toUpperCase(Locale.ROOT));

        String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_SHOW_SUCCESS);
        player.sms(StringUtils.replacePlaceHolders(message, placeHolders));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerLogin(FPlayerLoginEvent event) {
        FPlayer player = event.getPlayer();
        this.getTasksHandler().addPowerTask(player);

        // If player was flying, and he remains in his land, enable flight.
        if (player.isFlying() && player.isInHisLand()) {
            player.enableBukkitFlight();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerLogOut(FPlayerLogOutEvent event) {
        FPlayer player = event.getPlayer();
        this.getTasksHandler().removePowerTask(player);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleFallDamage(FPlayerFallDamageEvent event) {
        FPlayer player = event.getPlayer();
        Logger.logInfo("Fall damage check for: " + player.getName());

        // No fall damage.
        if (this.getNoFallDamagePlayers().containsKey(player.getId())) {
            Logger.logInfo("Player is fall damage protected: " + player.getName());
            event.setCancelled(true);
        }
    }

    default void addNoFallPlayer(FPlayer player) {
        long noFallDamageTime = 10;
        this.getNoFallDamagePlayers().put(player.getId(), player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.getPlugin(), () -> getNoFallDamagePlayers().remove(player.getId()), (noFallDamageTime * 1000) / 20);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerDied(FPlayerDiedEvent event) {
        FPlayer player = event.getPlayer();
        int deathPowerDecrement = (int) this.getSysConfig().get(SystemConfigItems.DEATH_POWER_DECREMENT);
        player.updatePower(-deathPowerDecrement);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetPlayerByName(GetPlayerByNameEvent event) {
        String playerName = event.getName();
        FPlayer player = this.getManager().getByName(playerName);
        event.setPlayer(player);
    }
}
