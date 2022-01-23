package crypto.factions.bloodfactions.backend.handler.data;

import crypto.factions.bloodfactions.backend.manager.FactionsManager;
import crypto.factions.bloodfactions.backend.manager.PlayersManager;
import crypto.factions.bloodfactions.backend.manager.RanksManager;
import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.config.system.SystemConfigItems;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.ShowFactionEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.*;
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
import crypto.factions.bloodfactions.commons.model.invitation.FactionInvitation;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
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
    default void handleGetPlayer(GetPlayerEvent event) {
        UUID playerId = event.getId();
        FPlayer player = null;

        try {
            player = this.getById(playerId);
        } catch (Exception ignored) {
        }

        if (Objects.isNull(player)) {
            Logger.logInfo("Creating player: " + playerId.toString());

            // Create the player in DB.
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);
            player = new FPlayerImpl(offlinePlayer.getUniqueId(), offlinePlayer.getName(), 0);
            player = this.getManager().insert(player);

            if (Objects.nonNull(player)) {
                Logger.logInfo("New player inserted");
                // Add the player to the faction-less faction.
                Faction factionLessFaction = ContextHandler.getFactionLessFaction();
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
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
            player.disableFly();
        }

        // Enable fly if auto flying
        if (player.isAutoFlying() && !player.isFlying() && factionTo.equals(playersFaction) && !factionTo.isSystemFaction()) {
            player.enableFly();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
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

            // Enable flight mode.
            if (player.hasFaction() && player.isInHisLand() && !player.isFlying()) {
                player.enableFly();
            }
        }

        // Update auto-fly
        boolean updated = this.getManager().updatePlayersAutoFly(player, autoFly);
        if (updated) {
            event.setAutoFlying(autoFly);
        }

        event.setSuccess(updated);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleToggleFlight(PlayerFlightEvent event) {

        FPlayer player = event.getPlayer();

        boolean flying = false;
        boolean toggle = event.isToggle();
        boolean updated = false;
        String successMessage;

        // Toggle on
        if (toggle) {

            player.enableBukkitFlight();
            flying = true;

            // Send message
            successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_FLY_SUCCESS);

            updated = this.getManager().updatePlayersFlightMode(player, true);

        }

        // Toggle off
        else {
            // No fall damage
            this.addNoFallPlayer(player);

            // Disable flight
            player.disableBukkitFlight();

            // Send message
            successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_FLY_OFF);

            updated = this.getManager().updatePlayersFlightMode(player, false);

        }

        // Message context send
        MessageContext messageContext = new MessageContextImpl(player, successMessage);
        player.sms(messageContext);

        // Set flying event
        event.setFlying(flying);
        event.setSuccess(updated);
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

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleChangeLand(PlayerChangedLandEvent event) {
        FPlayer player = event.getPlayer();
        Faction factionTo = event.getFactionTo();

        if (player.isInFaction(factionTo) && player.isAutoFlying() && !player.isFlying()) {
            player.enableFly();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleListInvitationsFromOtherFactions(ListInvitationsToOtherFactionsEvent event) {

        FPlayer player = event.getPlayer();

        Set<FactionInvitation> invitations = this.getFactionsManager().getInvitationsOfPlayer(player);

        String header = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_LIST_TO_OTHER_FACTIONS_HEADER);
        String body = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_LIST_ITEM);
        StringBuilder finalMessage = new StringBuilder(header);

        for (FactionInvitation invitation : invitations) {

            FPlayer invitedBy = invitation.getInviter();
            FPlayer invited = invitation.getPlayer();
            String date = invitation.getDate();
            Faction faction = invitation.getFaction();

            String finalBody = body
                    .replace("{inviter_name}", invitedBy.getName())
                    .replace("{invitation_date}", date)
                    .replace("{target_player_name}", invited.getName())
                    .replace("{faction_name}", faction.getName());

            finalMessage.append(finalBody);
        }

        MessageContext messageContext = new MessageContextImpl(player, finalMessage.toString());
        player.sms(messageContext);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleListFactionInvitations(ListInvitationsToFactionEvent event) {

        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        Set<FactionInvitation> invitations = this.getFactionsManager().getInvitationsOfFaction(faction);

        String header = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_LIST_TO_MY_FACTION_HEADER);
        String body = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_LIST_ITEM);
        StringBuilder finalMessage = new StringBuilder(header);

        for (FactionInvitation invitation : invitations) {

            FPlayer invitedBy = invitation.getInviter();
            FPlayer invited = invitation.getPlayer();
            String date = invitation.getDate();

            String finalBody = body
                    .replace("{inviter_name}", invitedBy.getName())
                    .replace("{invitation_date}", date)
                    .replace("{target_player_name}", invited.getName())
                    .replace("{faction_name}", faction.getName());

            finalMessage.append(finalBody);
        }

        MessageContext messageContext = new MessageContextImpl(player, finalMessage.toString());
        messageContext.setFaction(faction);
        player.sms(messageContext);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleDeclineInvitationEvent(DeclineFactionInvitationEvent event) {

        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        FactionInvitation invitation = this.getFactionsManager().getInvitation(player, faction);

        // Player is invited
        if (Objects.nonNull(invitation)) {

            // Remove
            this.getFactionsManager().removePlayerInvitation(player, faction);

            String notInvited = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_DECLINED);
            MessageContext messageContext = new MessageContextImpl(player, notInvited);
            messageContext.setFaction(faction);
            player.sms(messageContext);

            String playerDeclined = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITATION_DECLINED);
            MessageContext declinedMessage = new MessageContextImpl(faction, playerDeclined);
            declinedMessage.setTargetPlayer(invitation.getPlayer());
            faction.sms(declinedMessage);
        }

        // Player is not invited
        else {

            String notInvited = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_NOT_INVITED_BY_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, notInvited);
            messageContext.setFaction(faction);
            player.sms(messageContext);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleAcceptInvitationEvent(AcceptFactionInvitationEvent event) {

        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        FactionInvitation invitation = this.getFactionsManager().getInvitation(player, faction);
        if (Objects.nonNull(invitation)) {

            // Remove invitation and add player.
            this.getFactionsManager().removePlayerInvitation(player, faction);
            this.getFactionsManager().addPlayerToFaction(invitation.getPlayer(), invitation.getFaction(), invitation.getInviter());

            String accepted = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_ACCEPTED);
            MessageContext messageContext = new MessageContextImpl(player, accepted);
            messageContext.setFaction(faction);
            player.sms(messageContext);

            String joined = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITATION_ACCEPTED);
            MessageContext joinedMessage = new MessageContextImpl(faction, joined);
            joinedMessage.setTargetPlayer(player);
            faction.sms(joinedMessage);

        } else {
            String notInvited = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_NOT_INVITED_BY_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, notInvited);
            messageContext.setFaction(faction);
            player.sms(messageContext);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleGetPlayersInRadius(FindPlayersInRadiusEvent event) {

        FLocation location = event.getLocation();
        int radius = event.getRadius();


        Set<FPlayer> onlinePlayersInRadius = ContextHandler.getOnlinePlayers()
                .stream()
                .filter(player -> {
                    FLocation playersLocation = player.getLocation();
                    double distance = Objects.requireNonNull(location.getBukkitLocation()).distance(Objects.requireNonNull(Objects.requireNonNull(playersLocation).getBukkitLocation()));
                    Logger.logInfo("Distance " + distance);
                    return distance < radius;
                })
                .collect(Collectors.toSet());
        Logger.logInfo("Found " + onlinePlayersInRadius.size() + " players in a radius of: " + radius);
        event.setNearPlayers(onlinePlayersInRadius);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handlePlayerIsNearOther(PlayerIsNearOtherEvent event) {

        FPlayer player = event.getPlayer();
        FPlayer otherPlayer = event.getOtherPlayer();

        Faction factionOfPlayer = player.getFaction();
        Faction factionOfTheOtherPlayer = otherPlayer.getFaction();

        // If the faction does not allow flight near players of the other faction.
        if (!factionOfPlayer.allowsFlightNearPlayersOfOtherFaction(factionOfTheOtherPlayer)) {

            // Cancel fly if flying.
            if (player.isFlying()) {
                player.disableFly();
            }

            // Cancel fly if flying.
            if (otherPlayer.isFlying()) {
                otherPlayer.disableFly();
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleProximityCheck(PlayerProximityCheckEvent event) {

        FPlayer player = event.getPlayer();
        Set<FPlayer> players = player.getPlayersInRadius(32);

        players.forEach(p -> {
            if (!p.equals(player)) {
                double distance = p.distanceTo(player);
                player.playerIsNearOther(p, (int) distance);
            }
        });

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void checkPlayerFlying(CheckPlayerFlyingEvent event) {
        FPlayer player = event.getPlayer();
        boolean isFlying = this.getManager().checkIfPlayerIsFlying(player);
        event.setFlying(isFlying);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void checkPlayerAutoFlying(CheckPlayerAutoFlyingEvent event) {
        FPlayer player = event.getPlayer();
        boolean isFlying = this.getManager().checkIfPlayerIsAutoFlying(player);
        event.setAutoFlying(isFlying);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handlePlayerHitOther(PlayerHitOtherEvent event) {
        FPlayer player = event.getPlayer();
        FPlayer damaged = event.getOtherPlayer();

        if (player.isInFaction(damaged.getFaction())) {
            event.setCancelled(true);
            String message = (String) this.getLangConfig().get(LangConfigItems.ACTIONS_CANNOT_HIT_PLAYERS_IN_YOUR_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, message);
            player.sms(messageContext);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetInvitationsOfPlayer(GetInvitationsOfPlayerEvent event){
        FPlayer player = event.getPlayer();
        Set<FactionInvitation> invitations = this.getFactionsManager().getInvitationsOfPlayer(player);
        event.setInvitations(invitations);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerLeftFaction(PlayerLeftFactionEvent event){
        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        boolean left = this.getFactionsManager().removePlayerFromFaction(player, faction);

        if(left) {
            String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_LEAVE_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, message);
            player.sms(messageContext);

            String playerLeft = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_PLAYER_LEFT);
            MessageContext playerLeftContext = new MessageContextImpl(faction, playerLeft);
            playerLeftContext.setTargetPlayer(player);
            faction.sms(playerLeftContext);
        }



        event.setLeft(left);
    }


}
