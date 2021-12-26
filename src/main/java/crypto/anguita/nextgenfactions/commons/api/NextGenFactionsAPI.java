package crypto.anguita.nextgenfactions.commons.api;

import crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned.CreateFactionByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.CheckIfFactionExistsByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionAtChunkEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.land.callback.GetClaimsOfFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.player.unpermissioned.SavePlayerEvent;
import crypto.anguita.nextgenfactions.commons.events.player.callback.CheckIfPlayerHasFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.player.callback.GetPlayerByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.anguita.nextgenfactions.commons.events.player.callback.PlayerHasPermissionEvent;
import crypto.anguita.nextgenfactions.commons.events.shared.callback.GetFactionOfPlayerEvent;
import crypto.anguita.nextgenfactions.commons.events.shared.callback.GetPlayersInFactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.land.FLocation;
import crypto.anguita.nextgenfactions.commons.model.permission.Action;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class NextGenFactionsAPI {

    /**
     * Gets the Faction that owns this location.
     * @param location
     * @return
     */
    public static @Nullable Faction getFactionAtLocation(FLocation location) {
        FChunk chunk = location.getChunk();
        if (Objects.nonNull(chunk)) {
            return getFactionAtChunk(chunk);
        }
        return null;
    }

    /**
     * Gets the Faction that owns the given chunk.
     *
     * @param chunk
     * @return
     */
    public static @NotNull Faction getFactionAtChunk(@NotNull FChunk chunk) {
        GetFactionAtChunkEvent event = new GetFactionAtChunkEvent(chunk);
        return event.getFaction();
    }

    /**
     * Gets all players in a Faction.
     *
     * @param faction
     * @return
     */
    public static @NotNull Set<FPlayer> getPlayersInFaction(@NotNull Faction faction) {
        GetPlayersInFactionEvent event = new GetPlayersInFactionEvent(faction);
        return event.getPlayers();
    }

    /**
     * Saves a player in the system.
     *
     * @param player
     */
    public static void savePlayer(@NotNull FPlayer player) {
        new SavePlayerEvent(player);
    }

    /**
     * Creates a new Faction given a name.
     *
     * @param name
     * @return
     */
    public static @Nullable Faction createFaction(@NotNull String name) {
        CreateFactionByNameEvent event = new CreateFactionByNameEvent(name);
        return event.getFaction();
    }

    /**
     * Checks if the player has permission to perform an action.
     *
     * @param player
     * @param action
     * @return
     */
    public static boolean checkIfPlayerHasPermission(@NotNull FPlayer player, @NotNull Action action) {
        PlayerHasPermissionEvent event = new PlayerHasPermissionEvent(player, action);
        return event.isHasPermission();
    }

    /**
     * Checks if the player has a faction.
     *
     * @param player
     * @return
     */
    public static boolean checkIfPlayerHasFaction(@NotNull FPlayer player) {
        CheckIfPlayerHasFactionEvent event = new CheckIfPlayerHasFactionEvent(player);
        return event.isHasFaction();
    }

    /**
     * Gets the player by its id.
     *
     * @param id
     * @return
     */
    public static @Nullable FPlayer getPlayer(@NotNull UUID id) {
        GetPlayerEvent event = new GetPlayerEvent(id);
        return event.getPlayer();
    }

    /**
     * Gets the player by its name.
     *
     * @param name
     * @return
     */
    public static @Nullable FPlayer getPlayerByName(@NotNull String name) {
        GetPlayerByNameEvent event = new GetPlayerByNameEvent(name);
        return event.getPlayer();
    }

    /**
     * Gets a Faction by its id.
     *
     * @param id
     * @return
     */
    public static @Nullable Faction getFaction(@NotNull UUID id) {
        GetFactionEvent event = new GetFactionEvent(id);
        return event.getFaction();
    }

    /**
     * Checks if a Faction exists given its name.
     *
     * @param name
     * @return
     */
    public static boolean checkIfFactionExistsByName(@NotNull String name) {
        CheckIfFactionExistsByNameEvent event = new CheckIfFactionExistsByNameEvent(name);
        return event.isExists();
    }

    /**
     * Gets a Faction by its name.
     *
     * @param name
     * @return
     */
    public static @Nullable Faction getFactionByName(@NotNull String name) {
        GetFactionByNameEvent event = new GetFactionByNameEvent(name);
        return event.getFaction();
    }

    /**
     * Gets the faction of a player.
     *
     * @param player
     * @return
     */
    public static @NotNull Faction getFactionOfPlayer(@NotNull FPlayer player) {
        GetFactionOfPlayerEvent event = new GetFactionOfPlayerEvent(player);
        return event.getFaction();
    }

    public static @NotNull Set<FChunk> getAllClaims(@NotNull Faction faction){
        GetClaimsOfFactionEvent event = new GetClaimsOfFactionEvent(faction);
        return event.getChunks();
    }

}
