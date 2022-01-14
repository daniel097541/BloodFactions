package crypto.factions.bloodfactions.commons.api;

import crypto.factions.bloodfactions.commons.events.faction.callback.*;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.CreateFactionByNameEvent;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.ShowFactionEvent;
import crypto.factions.bloodfactions.commons.events.land.callback.GetClaimsOfFactionEvent;
import crypto.factions.bloodfactions.commons.events.land.callback.GetNumberOfClaimsEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.CheckIfPlayerHasFactionEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.GetPlayerByNameEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.factions.bloodfactions.commons.events.player.callback.PlayerHasPermissionEvent;
import crypto.factions.bloodfactions.commons.events.player.unpermissioned.*;
import crypto.factions.bloodfactions.commons.events.role.GetDefaultRoleOfFactionEvent;
import crypto.factions.bloodfactions.commons.events.role.GetRoleOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.role.GetRolesOfFactionEvent;
import crypto.factions.bloodfactions.commons.events.role.ListRolesEvent;
import crypto.factions.bloodfactions.commons.events.shared.callback.GetFactionOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.shared.callback.GetPlayersInFactionEvent;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.permission.Action;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class NextGenFactionsAPI {


    private static void logAction(long start, long end, APIAction action) {
        String message = "Time to perform action {action}: {time} ms";
        message = message
                .replace("{action}", action.name())
                .replace("{time}", String.valueOf(end - start));
        Logger.logInfo("&dAPI", message);
    }

    public static Set<FPlayer> getOnlinePlayers() {
        long start = System.currentTimeMillis();
        Set<FPlayer> onlinePlayers = Bukkit.getOnlinePlayers()
                .stream()
                .map(NextGenFactionsAPI::getPlayer)
                .collect(Collectors.toSet());
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_ONLINE_PLAYERS);
        return onlinePlayers;
    }

    /**
     * Gets the Faction that owns this location.
     *
     * @param location
     * @return
     */
    public static @Nullable Faction getFactionAtLocation(FLocation location) {
        long start = System.currentTimeMillis();
        Faction faction = null;
        FChunk chunk = location.getChunk();
        if (Objects.nonNull(chunk)) {
            faction = getFactionAtChunk(chunk);
        }
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_FACTION_AT_LOCATION);
        return faction;
    }

    /**
     * Gets the Faction that owns the given chunk.
     *
     * @param chunk
     * @return
     */
    public static @NotNull Faction getFactionAtChunk(@NotNull FChunk chunk) {
        long start = System.currentTimeMillis();
        GetFactionAtChunkEvent event = new GetFactionAtChunkEvent(chunk);
        Faction faction = event.getFaction();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_FACTION_AT_CHUNK);
        return faction;
    }

    /**
     * Gets all players in a Faction.
     *
     * @param faction
     * @return
     */
    public static @NotNull Set<FPlayer> getPlayersInFaction(@NotNull Faction faction) {
        long start = System.currentTimeMillis();
        GetPlayersInFactionEvent event = new GetPlayersInFactionEvent(faction);
        Set<FPlayer> players = event.getPlayers();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_PLAYERS_OF_FACTION);
        return players;
    }

    /**
     * Saves a player in the system.
     *
     * @param player
     */
    public static void savePlayer(@NotNull FPlayer player) {
        long start = System.currentTimeMillis();
        new SavePlayerEvent(player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.SAVE_PLAYER);
    }

    /**
     * Creates a new Faction given a name.
     *
     * @param name
     * @return
     */
    public static @Nullable Faction createFaction(@NotNull String name, @NotNull FPlayer player) {
        long start = System.currentTimeMillis();
        CreateFactionByNameEvent event = new CreateFactionByNameEvent(name, player);
        Faction faction = event.getFaction();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.CREATE_FACTION);
        return faction;
    }

    /**
     * Checks if the player has permission to perform an action.
     *
     * @param player
     * @param action
     * @return
     */
    public static boolean checkIfPlayerHasPermission(@NotNull FPlayer player, @NotNull Action action) {
        long start = System.currentTimeMillis();
        PlayerHasPermissionEvent event = new PlayerHasPermissionEvent(player, action);
        boolean hasPermission = event.isHasPermission();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.CHECK_IF_PLAYER_HAS_PERMISSION);
        return hasPermission;
    }

    /**
     * Checks if the player has a faction.
     *
     * @param player
     * @return
     */
    public static boolean checkIfPlayerHasFaction(@NotNull FPlayer player) {
        long start = System.currentTimeMillis();
        CheckIfPlayerHasFactionEvent event = new CheckIfPlayerHasFactionEvent(player);
        boolean hasFaction = event.isHasFaction();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.CHECK_IF_PLAYER_HAS_FACTION);
        return hasFaction;
    }

    /**
     * Gets the player by its id.
     *
     * @param id
     * @return
     */
    public static @Nullable FPlayer getPlayer(@NotNull UUID id) {
        long start = System.currentTimeMillis();
        GetPlayerEvent event = new GetPlayerEvent(id);
        FPlayer player = event.getPlayer();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_PLAYER);
        return player;
    }

    /**
     * Gets the player by its Bukkit player.
     *
     * @param player
     * @return
     */
    public static @Nullable FPlayer getPlayer(@NotNull Player player) {
        return getPlayer(player.getUniqueId());
    }

    /**
     * Gets the player by its name.
     *
     * @param name
     * @return
     */
    public static @Nullable FPlayer getPlayerByName(@NotNull String name) {
        long start = System.currentTimeMillis();
        GetPlayerByNameEvent event = new GetPlayerByNameEvent(name);
        FPlayer player = event.getPlayer();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_PLAYER_BY_NAME);
        return player;
    }

    /**
     * Gets a Faction by its id.
     *
     * @param id
     * @return
     */
    public static @Nullable Faction getFaction(@NotNull UUID id) {
        long start = System.currentTimeMillis();
        GetFactionEvent event = new GetFactionEvent(id);
        Faction faction = event.getFaction();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_PLAYER_BY_NAME);
        return faction;
    }

    /**
     * Checks if a Faction exists given its name.
     *
     * @param name
     * @return
     */
    public static boolean checkIfFactionExistsByName(@NotNull String name) {
        long start = System.currentTimeMillis();
        CheckIfFactionExistsByNameEvent event = new CheckIfFactionExistsByNameEvent(name);
        boolean exists = event.isExists();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.CHECK_IF_FACTION_EXISTS_BY_NAME);
        return exists;
    }

    /**
     * Gets a Faction less faction.
     *
     * @return
     */
    public static @NotNull Faction getFactionLessFaction() {
        long start = System.currentTimeMillis();
        GetFactionLessFactionEvent event = new GetFactionLessFactionEvent();
        Faction faction = event.getFaction();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_FACTION_BY_NAME);
        return faction;
    }

    /**
     * Gets a Faction by its name.
     *
     * @param name
     * @return
     */
    public static @Nullable Faction getFactionByName(@NotNull String name) {
        long start = System.currentTimeMillis();
        GetFactionByNameEvent event = new GetFactionByNameEvent(name);
        Faction faction = event.getFaction();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_FACTION_BY_NAME);
        return faction;
    }

    /**
     * Gets the faction of a player.
     *
     * @param player
     * @return
     */
    public static @NotNull Faction getFactionOfPlayer(@NotNull FPlayer player) {
        long start = System.currentTimeMillis();
        GetFactionOfPlayerEvent event = new GetFactionOfPlayerEvent(player);
        Faction faction = event.getFaction();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_FACTION_OF_PLAYER);
        return faction;
    }

    /**
     * Gets all claims from faction.
     *
     * @param faction
     * @return
     */
    public static @NotNull Set<FChunk> getAllClaims(@NotNull Faction faction) {
        long start = System.currentTimeMillis();
        GetClaimsOfFactionEvent event = new GetClaimsOfFactionEvent(faction);
        Set<FChunk> chunks = event.getChunks();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_CLAIMS_OF_FACTION);
        return chunks;
    }

    /**
     * Gets the role of a player.
     *
     * @param player
     * @return
     */
    public static @Nullable FactionRank getRoleOfPlayer(@NotNull FPlayer player) {
        long start = System.currentTimeMillis();
        FactionRank role = null;
        if (player.hasFaction()) {
            GetRoleOfPlayerEvent event = new GetRoleOfPlayerEvent(player);
            role = event.getRole();
        }
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_ROLE_OF_PLAYER);
        return role;
    }

    /**
     * Gets the default role.
     *
     * @param faction
     * @return
     */
    public static @Nullable FactionRank getDefaultRoleOfFaction(@NotNull Faction faction) {
        long start = System.currentTimeMillis();
        GetDefaultRoleOfFactionEvent event = new GetDefaultRoleOfFactionEvent(faction);
        FactionRank role = event.getDefaultRole();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_DEFAULT_ROLE_OF_FACTION);
        return role;
    }

    /**
     * Gets all the roles of a faction.
     *
     * @param faction
     * @return
     */
    public static @NotNull Set<FactionRank> getRolesOfFaction(@NotNull Faction faction) {
        long start = System.currentTimeMillis();
        GetRolesOfFactionEvent event = new GetRolesOfFactionEvent(faction);
        Set<FactionRank> roles = event.getRoles();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_ROLES_OF_FACTION);
        return roles;
    }

    /**
     * Gets the number of claims of a faction.
     *
     * @param faction
     * @return
     */
    public static int getNumberOfClaimsOfFaction(@NotNull Faction faction) {
        long start = System.currentTimeMillis();
        GetNumberOfClaimsEvent event = new GetNumberOfClaimsEvent(faction);
        int count = event.getNumberOfClaims();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_NUMBER_OF_CLAIMS);
        return count;
    }

    /**
     * Gets the home of a faction.
     *
     * @param faction
     * @return
     */
    public static FLocation getCoreOfFaction(Faction faction) {
        long start = System.currentTimeMillis();
        GetCoreEvent event = new GetCoreEvent(faction);
        FLocation core = event.getCore();
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.GET_FACTIONS_CORE);
        return core;
    }

    /**
     * Shows the faction info to the player.
     *
     * @param player
     * @param faction
     */
    public static void showFactionToPlayer(FPlayer player, Faction faction) {
        long start = System.currentTimeMillis();
        new ShowFactionEvent(faction, player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.SHOW_FACTION);
    }

    /**
     * Player changed land.
     *
     * @param player
     * @param from
     * @param to
     */
    public static void changedLand(FPlayer player, Faction from, Faction to) {
        long start = System.currentTimeMillis();
        new PlayerChangedLandEvent(player, from, to);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.CHANGED_LAND);
    }

    /**
     * Updates the power of the player.
     *
     * @param player
     * @param increment
     * @return
     */
    public static int updatePlayersPower(FPlayer player, int increment) {
        long start = System.currentTimeMillis();
        new PlayerPowerChangeEvent(player, increment);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.UPDATE_POWER);
        return player.getPower();
    }

    /**
     * Player logged in.
     *
     * @param player
     */
    public static void playerLoggedIn(FPlayer player) {
        long start = System.currentTimeMillis();
        new FPlayerLoginEvent(player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.LOGGED_IN);
    }

    /**
     * Player logged out.
     *
     * @param player
     */
    public static void playerLoggedOut(FPlayer player) {
        long start = System.currentTimeMillis();
        new FPlayerLogOutEvent(player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.LOGGED_OUT);
    }

    /**
     * Handles fall damage of player.
     *
     * @param player
     * @return
     */
    public static boolean handlePlayerFallDamage(FPlayer player) {
        long start = System.currentTimeMillis();
        FPlayerFallDamageEvent event = new FPlayerFallDamageEvent(player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.HANDLE_FALL_DAMAGE);
        return !event.isCancelled();
    }

    public static void listRoles(Faction faction, FPlayer player) {
        long start = System.currentTimeMillis();
        new ListRolesEvent(faction, player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.LIST_ROLES);
    }

    public static void playerDied(FPlayer player) {
        long start = System.currentTimeMillis();
        new FPlayerDiedEvent(player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.PLAYER_DIED);
    }

    public static void listInvitationsToFaction(FPlayer player, Faction faction) {
        long start = System.currentTimeMillis();
        new ListInvitationsToFactionEvent(faction, player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.LIST_INVITATIONS_TO_FACTION);
    }

    public static void listInvitationsToOtherFactions(FPlayer player) {
        long start = System.currentTimeMillis();
        new ListInvitationsToOtherFactionsEvent(player);
        long end = System.currentTimeMillis();
        logAction(start, end, APIAction.LIST_INVITATIONS_TO_OTHER_FACTIONS);
    }
}
