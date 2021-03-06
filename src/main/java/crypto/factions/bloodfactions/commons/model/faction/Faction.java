package crypto.factions.bloodfactions.commons.model.faction;

import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.contex.PermissionContextHandler;
import crypto.factions.bloodfactions.commons.messages.handler.MessageContextHandler;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.model.NextGenFactionEntity;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface Faction extends NextGenFactionEntity {

    boolean isSystemFaction();

    UUID getOwnerId();

    default Set<FPlayer> getOnlineMembers() {
        return this.getMembers()
                .stream()
                .filter(FPlayer::isOnline)
                .collect(Collectors.toSet());
    }

    default void sms(MessageContext messageContext) {
        new MessageContextHandler() {
        }.handle(messageContext);
    }

    default void setCore(FPlayer player, FLocation location) {
        PermissionContextHandler.setCore(player, this, location);
    }

    default FLocation getCore() {
        return ContextHandler.getCoreOfFaction(this);
    }

    default boolean isFactionLessFaction() {
        Faction factionLessFaction = ContextHandler.getFactionLessFaction();
        return factionLessFaction.equals(this);
    }

    /**
     * Checks if is claim of Faction.
     *
     * @param chunk
     * @return
     */
    default boolean isClaim(FChunk chunk) {
        return chunk.getFactionAt().equals(this);
    }

    /**
     * Gets all the claims of a Faction.
     *
     * @return
     */
    default Set<FChunk> getAllAClaims() {
        return ContextHandler.getAllClaims(this);
    }

    /**
     * Gets the total amount of claims.
     *
     * @return
     */
    default int getCountOfClaims() {
        return ContextHandler.getNumberOfClaimsOfFaction(this);
    }

    /**
     * Un claims a set of chunks from the Faction.
     *
     * @param chunks
     * @param player
     * @return
     */
    default void multiUnClaim(Set<FChunk> chunks, FPlayer player) {
        PermissionContextHandler.multiUnClaim(this, chunks, player);
    }

    /**
     * Un claims a chunk from the faction.
     *
     * @param chunk
     * @param player
     * @return
     */
    default void unClaim(FChunk chunk, FPlayer player) {
        PermissionContextHandler.unClaim(this, chunk, player);
    }

    /**
     * Claims multiple chunks.
     *
     * @param chunks
     * @param player
     * @return
     */
    default void multiClaim(Set<FChunk> chunks, FPlayer player, int radius) {
        PermissionContextHandler.multiClaim(this, chunks, player, radius);
    }

    /**
     * Claims a chunk.
     *
     * @param chunk
     * @param player
     * @return
     */
    default void claim(FChunk chunk, FPlayer player) {
        PermissionContextHandler.claim(this, chunk, player);
    }

    /**
     * Over-Claims a chunk.
     *
     * @param chunk
     * @param player
     * @return
     */
    default void overClaim(FChunk chunk, FPlayer player, Faction otherFaction) {
        PermissionContextHandler.overClaim(this, otherFaction, chunk, player);
    }

    /**
     * Gets all the members of a Faction.
     *
     * @return
     */
    default Set<FPlayer> getMembers() {
        return ContextHandler.getPlayersInFaction(this);
    }

    /**
     * Kicks a player from the faction.
     *
     * @param kickedPlayer
     * @param playerKicking
     * @return
     */
    default void kickPlayer(FPlayer kickedPlayer, FPlayer playerKicking) {
        PermissionContextHandler.kickPlayerFromFaction(kickedPlayer, this, playerKicking);
    }

    /**
     * Invites a player to the faction.
     *
     * @param invitedPlayer
     * @param playerInviting
     * @return
     */
    default void invitePlayer(FPlayer invitedPlayer, FPlayer playerInviting) {
        PermissionContextHandler.invitePlayerToFaction(invitedPlayer, this, playerInviting);
    }

    /**
     * Disbands the faction.
     *
     * @param playerDisbanding
     * @return
     */
    default void disband(FPlayer playerDisbanding) {
        PermissionContextHandler.disbandFaction(this, playerDisbanding);
    }

    /**
     * Gets the default role of the faction.
     *
     * @return
     */
    default FactionRank getDefaultRole() {
        return ContextHandler.getDefaultRoleOfFaction(this);
    }

    /**
     * Gets the set of roles of the faction.
     *
     * @return
     */
    default Set<FactionRank> getRoles() {
        return ContextHandler.getRolesOfFaction(this);
    }

    /**
     * Checks if a faction can be over-claimed by other faction.
     *
     * @return
     */
    default boolean canBeOverClaimed() {
        if (this.isFactionLessFaction()) {
            return true;
        }
        int power = this.getPower();
        int claimsCount = this.getCountOfClaims();
        return claimsCount > power;
    }

    /**
     * Checks if the faction can claim.
     *
     * @return
     */
    default boolean canClaim() {
        int power = this.getPower();
        int claimsCount = this.getCountOfClaims();
        return claimsCount < power;
    }

    /**
     * Gets the total power of the faction.
     *
     * @return
     */
    default int getPower() {
        Set<FPlayer> players = this.getMembers();
        int power = 0;
        for (FPlayer member : players) {
            power += member.getPower();
        }
        return power;
    }


    default FPlayer getOwner() {
        return ContextHandler.getPlayer(this.getOwnerId());
    }

    default void createRank(String roleName, FPlayer player, Set<PermissionType> permissions) {
        PermissionContextHandler.createRank(roleName, player, this, permissions);
    }

    default void deleteRank(String roleName, FPlayer player) {
        PermissionContextHandler.deleteRank(roleName, player, this);
    }

    default @Nullable FactionRank getRankByName(@NotNull String rankName) {
        return this.getRoles()
                .stream()
                .filter(role -> role.getName().equals(rankName))
                .findFirst()
                .orElse(null);
    }

    default void unClaimAll(@NotNull FPlayer player, @NotNull Faction faction) {
        PermissionContextHandler.unClaimAll(player, faction);
    }

    default boolean allowsFlightNearPlayersOfOtherFaction(Faction otherFaction) {
        return this.equals(otherFaction);
    }

}
