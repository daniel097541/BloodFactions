package crypto.factions.bloodfactions.commons.model.player;

import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.contex.PermissionContextHandler;
import crypto.factions.bloodfactions.commons.messages.handler.MessageContextHandler;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.NextGenFactionEntity;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.invitation.FactionInvitation;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.land.impl.FChunkImpl;
import crypto.factions.bloodfactions.commons.model.land.impl.FLocationImpl;
import crypto.factions.bloodfactions.commons.model.permission.Action;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import crypto.factions.bloodfactions.commons.utils.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public interface FPlayer extends NextGenFactionEntity {

    int getPower();

    default boolean hit(FPlayer other) {
        return ContextHandler.playerHitOther(this, other);
    }

    default boolean isFlying() {
        return ContextHandler.isPlayerFlying(this);
    }

    default boolean isAutoFlying() {
        return ContextHandler.isPlayerAutoFlying(this);
    }

    default Set<FactionInvitation> getInvitations() {
        return ContextHandler.getInvitations(this);
    }

    default double distanceTo(FPlayer other) {

        FLocation location = this.getLocation();
        FLocation othersLocation = other.getLocation();

        if (this.isOnline() && other.isOnline() && Objects.nonNull(location) && Objects.nonNull(othersLocation)) {
            return location.getBukkitLocation().distance(othersLocation.getBukkitLocation());
        }
        return -1;
    }

    default boolean breakBlock(Faction faction, FLocation location) {
        return PermissionContextHandler.breakBlock(this, faction, location);
    }

    default boolean placeBlock(Faction faction, FLocation location) {
        return PermissionContextHandler.placeBlock(this, faction, location);
    }

    default boolean isInFaction(Faction faction) {
        Faction myFaction = this.getFaction();
        return myFaction.equals(faction);
    }

    default void sms(@NotNull MessageContext context) {
        new MessageContextHandler() {
        }.handle(context);
    }

    default void sms(@NotNull String message) {
        MessageContext messageContext = new MessageContextImpl(this, message);
        messageContext.setFaction(this.getFaction());
        this.sms(messageContext);
    }

    default void sms(String message, @NotNull Faction targetFaction) {
        MessageContext messageContext = new MessageContextImpl(this, message);
        messageContext.setFaction(this.getFaction());
        messageContext.setTargetFaction(targetFaction);
        this.sms(messageContext);
    }

    default void sms(String message, @NotNull FPlayer targetPlayer) {
        MessageContext messageContext = new MessageContextImpl(this, message);
        messageContext.setFaction(this.getFaction());
        messageContext.setTargetPlayer(targetPlayer);
        this.sms(messageContext);
    }

    default void sms(String message, @NotNull Faction targetFaction, @NotNull FPlayer targetPlayer) {
        MessageContext messageContext = new MessageContextImpl(this, message);
        messageContext.setFaction(this.getFaction());
        messageContext.setTargetPlayer(targetPlayer);
        messageContext.setTargetFaction(targetFaction);
        this.sms(messageContext);
    }

    /**
     * Gets the Bukkit offline player.
     *
     * @return
     */
    default @NotNull OfflinePlayer getBukkitOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.getId());
    }

    /**
     * Gets the Bukkit player if online.
     *
     * @return
     */
    default @Nullable Player getBukkitPlayer() {
        OfflinePlayer offlinePlayer = this.getBukkitOfflinePlayer();
        return offlinePlayer.getPlayer();
    }

    /**
     * Gets the Faction of the player.
     *
     * @return
     */
    default @NotNull Faction getFaction() {
        return ContextHandler.getFactionOfPlayer(this);
    }

    /**
     * Checks if the player has faction.
     *
     * @return
     */
    default boolean hasFaction() {
        return ContextHandler.checkIfPlayerHasFaction(this);
    }

    /**
     * Invites a player to the faction.
     *
     * @param player
     * @return
     */
    default boolean invitePlayerToFaction(@NotNull FPlayer player) {
        Faction faction = this.getFaction();
        return faction.invitePlayer(player, this);
    }

    /**
     * Kicks a player from the faction.
     *
     * @param player
     * @return
     */
    default boolean kickPlayerFromFaction(@NotNull FPlayer player) {
        Faction faction = this.getFaction();
        return faction.kickPlayer(player, this);
    }

    /**
     * Checks if the player has permission.
     *
     * @param action
     * @return
     */
    default boolean hasPermission(@NotNull Action action) {
        return ContextHandler.checkIfPlayerHasPermission(this, action);
    }

    /**
     * Checks if the player has the Bukkit permission.
     *
     * @param bukkitPermission
     * @return
     */
    default boolean hasBukkitPermission(String bukkitPermission) {
        Player player = this.getBukkitPlayer();
        return Objects.nonNull(player) && player.hasPermission(bukkitPermission);
    }

    /**
     * Gets the role of the player.
     *
     * @return
     */
    default @Nullable FactionRank getRole() {
        return ContextHandler.getRoleOfPlayer(this);
    }

    /**
     * Changes the role of a player.
     *
     * @param role
     * @param playerChangingTheRole
     * @return
     */
    default boolean changeRole(FactionRank role, FPlayer playerChangingTheRole) {
        return PermissionContextHandler.changeRankOfPlayer(this, role, playerChangingTheRole);
    }

    /**
     * Changes the role of other player.
     *
     * @param otherPlayer
     * @param role
     * @return
     */
    default boolean changeRoleOfPlayer(FPlayer otherPlayer, FactionRank role) {
        return otherPlayer.changeRole(role, this);
    }

    /**
     * Gets the chunk where the player is.
     *
     * @return
     */
    default @Nullable FChunk getChunk() {
        Player bukkitPlayer = this.getBukkitPlayer();

        if (Objects.nonNull(bukkitPlayer)) {
            Location location = bukkitPlayer.getLocation();
            Chunk chunk = location.getChunk();
            return FChunkImpl.fromChunk(chunk);
        }

        return null;
    }

    /**
     * Checks if the player is an operator.
     *
     * @return
     */
    default boolean isOp() {
        OfflinePlayer offlinePlayer = this.getBukkitOfflinePlayer();
        return offlinePlayer.isOp();
    }

    /**
     * Teleports player.
     *
     * @param location
     */
    default void teleport(@NotNull FLocation location) {
        Player bukkitPlayer = this.getBukkitPlayer();
        if (Objects.nonNull(bukkitPlayer)) {
            bukkitPlayer.teleport(Objects.requireNonNull(location.getBukkitLocation()));
        }
    }

    /**
     * Gets player location.
     *
     * @return
     */
    default @Nullable FLocation getLocation() {
        Player bukkitPlayer = this.getBukkitPlayer();
        if (Objects.nonNull(bukkitPlayer)) {
            return FLocationImpl.fromLocation(bukkitPlayer.getLocation());
        }
        return null;
    }

    /**
     * Shows the faction to the player.
     *
     * @param faction
     */
    default void showFaction(Faction faction) {
        ContextHandler.showFactionToPlayer(this, faction);
    }

    /**
     * Toggles flight mode.
     */
    default void toggleFly() {
        // If is flying, disable
        if (this.isFlying()) {
            this.disableFly();
        }
        // If is not flying, enable
        else {
            this.enableFly();
        }
    }

    default void disableFly() {
        PermissionContextHandler.toggleFlightMode(this, false);
    }

    default void enableFly() {
        PermissionContextHandler.toggleFlightMode(this, true);
    }

    /**
     * Enables bukkit flight.
     */
    default void enableBukkitFlight() {
        Player bukkitPlayer = this.getBukkitPlayer();
        if (Objects.nonNull(bukkitPlayer)) {
            bukkitPlayer.setAllowFlight(true);
            bukkitPlayer.setFlying(true);
        }
    }

    /**
     * Disables bukkit flight.
     */
    default void disableBukkitFlight() {
        Player bukkitPlayer = this.getBukkitPlayer();
        if (Objects.nonNull(bukkitPlayer)) {
            bukkitPlayer.setAllowFlight(false);
            bukkitPlayer.setFlying(false);
        }
    }

    /**
     * Toggles auto fly.
     */
    default void toggleAutoFly() {
        PermissionContextHandler.toggleAutoFly(this);
    }

    /**
     * Player changed land.
     *
     * @param from
     * @param to
     */
    default void changedLand(Faction from, Faction to) {
        ContextHandler.changedLand(this, from, to);
    }

    void setPower(int power);

    /**
     * Updates the power with increment.
     *
     * @param increment
     * @return
     */
    default void updatePower(int increment) {
        ContextHandler.updatePlayersPower(this, increment);
    }

    /**
     * Checks if the player is online.
     *
     * @return
     */
    default boolean isOnline() {
        return this.getBukkitOfflinePlayer().isOnline();
    }

    /**
     * Player logged in.
     */
    default void logIn() {
        ContextHandler.playerLoggedIn(this);
    }

    /**
     * Player logged out.
     */
    default void logOut() {
        ContextHandler.playerLoggedOut(this);
    }

    /**
     * Checks if a player is in his land.
     *
     * @return
     */
    default boolean isInHisLand() {
        Faction factionAt = this.getFactionAt();
        Faction faction = this.getFaction();
        return faction.equals(factionAt);
    }

    /**
     * Gets the faction at location.
     *
     * @return
     */
    default Faction getFactionAt() {
        return Objects.requireNonNull(this.getLocation()).getFactionAt();
    }

    /**
     * Handles fall damage.
     *
     * @return
     */
    default boolean handleFallDamage() {
        return ContextHandler.handlePlayerFallDamage(this);
    }

    default void listRoles(Faction faction) {
        ContextHandler.listRoles(faction, this);
    }

    default void died() {
        ContextHandler.playerDied(this);
    }

    default boolean setRank(@NotNull FactionRank targetRank, @NotNull FPlayer playerSettingTheRank) {
        return PermissionContextHandler.changeRankOfPlayer(this, targetRank, playerSettingTheRank);
    }

    default void sendTitle(String title) {
        StringUtils.sendTitle(this.getBukkitPlayer(), title, 250, 250, 250, ChatColor.AQUA);
    }

    default void listInvitationsOfMyFaction() {
        ContextHandler.listInvitationsToFaction(this, this.getFaction());
    }

    default void listInvitationsToOtherFactions() {
        ContextHandler.listInvitationsToOtherFactions(this);
    }

    default boolean acceptInvitation(Faction faction) {
        return ContextHandler.acceptFactionInvitation(this, faction);
    }

    default boolean declineInvitation(Faction faction) {
        return ContextHandler.declineFactionInvitation(this, faction);
    }

    default Set<FPlayer> getPlayersInRadius(int radius) {
        return ContextHandler.getPlayersInRadius(this, radius);
    }

    default void proximityCheck() {
        ContextHandler.proximityCheck(this);
    }

    default void playerIsNearOther(FPlayer other, int radius) {
        ContextHandler.playerIsNearOther(this, other, radius);
    }

    default boolean leaveFaction() {
        return ContextHandler.leaveFaction(this, this.getFaction());
    }

    default @NotNull Set<FChunk> getChunksInRadius(int radius) {
        if (this.isOnline()) {
            return Objects.requireNonNull(this.getChunk()).getChunksInRadius(radius);
        } else {
            return new HashSet<>();
        }
    }

    default boolean isOwner() {
        return this.getFaction().getOwner().equals(this);
    }

}
