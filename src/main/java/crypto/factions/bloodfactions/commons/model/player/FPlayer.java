package crypto.factions.bloodfactions.commons.model.player;

import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.api.PermissionNextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.messages.handler.MessageContextHandler;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.NextGenFactionEntity;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.impl.FChunkImpl;
import crypto.factions.bloodfactions.commons.model.permission.Action;
import crypto.factions.bloodfactions.commons.model.role.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface FPlayer extends NextGenFactionEntity {

    int getPower();

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
        return NextGenFactionsAPI.getFactionOfPlayer(this);
    }

    /**
     * Checks if the player has faction.
     *
     * @return
     */
    default boolean hasFaction() {
        return NextGenFactionsAPI.checkIfPlayerHasFaction(this);
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
        return NextGenFactionsAPI.checkIfPlayerHasPermission(this, action);
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
     * @return
     */
    default @Nullable FactionRole getRole(){
        return NextGenFactionsAPI.getRoleOfPlayer(this);
    }

    /**
     * Changes the role of a player.
     * @param role
     * @param playerChangingTheRole
     * @return
     */
    default boolean changeRole(FactionRole role, FPlayer playerChangingTheRole){
        return PermissionNextGenFactionsAPI.changeRoleOfPlayer(this, role, playerChangingTheRole);
    }

    /**
     * Changes the role of other player.
     * @param otherPlayer
     * @param role
     * @return
     */
    default boolean changeRoleOfPlayer(FPlayer otherPlayer, FactionRole role){
        return otherPlayer.changeRole(role, this);
    }

    default @Nullable FChunk getChunk(){
        Player bukkitPlayer = this.getBukkitPlayer();

        if(Objects.nonNull(bukkitPlayer)) {
            Location location = bukkitPlayer.getLocation();
            Chunk chunk = location.getChunk();
            return FChunkImpl.fromChunk(chunk);
        }

        return null;
    }

    /**
     * Checks if the player is an operator.
     * @return
     */
    default boolean isOp(){
        OfflinePlayer offlinePlayer = this.getBukkitOfflinePlayer();
        return offlinePlayer.isOp();
    }

}
