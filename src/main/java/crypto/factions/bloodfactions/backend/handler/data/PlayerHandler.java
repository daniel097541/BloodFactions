package crypto.factions.bloodfactions.backend.handler.data;

import crypto.factions.bloodfactions.backend.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.backend.config.system.SystemConfigItems;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.ShowFactionEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.CheckIfPlayerHasFactionEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.factions.bloodfactions.commons.events.player.permissioned.PlayerAutoFlyEvent;
import crypto.factions.bloodfactions.commons.events.player.permissioned.PlayerFlightEvent;
import crypto.factions.bloodfactions.commons.events.player.unpermissioned.*;
import crypto.factions.bloodfactions.commons.events.role.ChangeRoleOfPlayerEvent;
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

    @Override
    PlayerDAO getDao();

    RolesDAO getRolesDAO();

    NGFConfig getLangConfig();

    NGFConfig getSysConfig();

    TasksHandler getTasksHandler();

    Map<UUID, FPlayer> getNoFallDamagePlayers();


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
            player = new FPlayerImpl(offlinePlayer.getUniqueId(), offlinePlayer.getName(), false, false, 0);
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
        FactionRank role = this.getRolesDAO().getRoleOFPlayer(player.getId());
        event.setRole(role);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleChangeRoleOfPlayer(ChangeRoleOfPlayerEvent event) {
        FPlayer player = event.getPlayerToBeChanged();
        FactionRank role = event.getRole();

        this.getRolesDAO().setPlayersRole(player, role);
        event.setChanged(true);
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
        this.getDao().updatePlayersAutoFly(player.getId(), autoFly);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleToggleFlight(PlayerFlightEvent event) {

        FPlayer player = event.getPlayer();

        boolean flying = false;
        if (player.isFlying()) {
            // No fall damage
            this.addNoFallPlayer(player);

            // Disable flight
            player.disableBukkitFlight();

            // Send message
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_FLY_OFF);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        } else {
            player.enableBukkitFlight();
            flying = true;
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_FLY_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }

        event.setSuccess(flying);
        this.getDao().updatePlayersFlightMode(player.getId(), flying);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleIncrementPower(PlayerPowerChangeEvent event) {

        FPlayer player = event.getPlayer();
        int increment = event.getChange();

        int total = player.getPower() + increment;
        int maxPower = (int) this.getSysConfig().get(SystemConfigItems.SETTINGS_MAX_POWER);
        int minPower = (int) this.getSysConfig().get(SystemConfigItems.SETTINGS_MIN_POWER);

        if(total > 0) {
            if (total > maxPower) {
                total = maxPower;
            }
        }
        else{
            if(total < minPower){
                total = minPower;
            }
        }

        player.setPower(total);
        // Update power in db.
        this.getDao().updatePlayersPower(player.getId(), player.getPower());
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
    default void handleFallDamage(FPlayerFallDamageEvent event){
        FPlayer player = event.getPlayer();
        Logger.logInfo("Fall damage check for: " + player.getName());
        if(this.getNoFallDamagePlayers().containsKey(player.getId())){
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
    default void handlePlayerDied(FPlayerDiedEvent event){
        FPlayer player = event.getPlayer();
        int deathPowerDecrement = (int) this.getSysConfig().get(SystemConfigItems.DEATH_POWER_DECREMENT);
        player.updatePower(-deathPowerDecrement);
    }

}
