package crypto.factions.bloodfactions.commons.model.faction;

import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.api.PermissionNextGenFactionsAPI;
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

public interface Faction extends NextGenFactionEntity {

    boolean isSystemFaction();

    UUID getOwnerId();

    default boolean setCore(FPlayer player, FLocation location) {
        return PermissionNextGenFactionsAPI.setCore(player, this, location);
    }

    default FLocation getCore() {
        return NextGenFactionsAPI.getCoreOfFaction(this);
    }

    default boolean isFactionLessFaction() {
        Faction factionLessFaction = NextGenFactionsAPI.getFactionLessFaction();
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
        return NextGenFactionsAPI.getAllClaims(this);
    }

    /**
     * Gets the total amount of claims.
     *
     * @return
     */
    default int getAmountOfClaims() {
        return NextGenFactionsAPI.getNumberOfClaimsOfFaction(this);
    }

    /**
     * Un claims a set of chunks from the Faction.
     *
     * @param chunks
     * @param player
     * @return
     */
    default Map<FChunk, Boolean> multiUnClaim(Set<FChunk> chunks, FPlayer player) {
        return PermissionNextGenFactionsAPI.multiUnClaim(this, chunks, player);
    }

    /**
     * Un claims a chunk from the faction.
     *
     * @param chunk
     * @param player
     * @return
     */
    default boolean unClaim(FChunk chunk, FPlayer player) {
        return PermissionNextGenFactionsAPI.unClaim(this, chunk, player);
    }

    /**
     * Claims multiple chunks.
     *
     * @param chunks
     * @param player
     * @return
     */
    default Map<FChunk, Boolean> multiClaim(Set<FChunk> chunks, FPlayer player) {
        return PermissionNextGenFactionsAPI.multiClaim(this, chunks, player);
    }

    /**
     * Claims a chunk.
     *
     * @param chunk
     * @param player
     * @return
     */
    default boolean claim(FChunk chunk, FPlayer player) {
        return PermissionNextGenFactionsAPI.claim(this, chunk, player);
    }

    /**
     * Over-Claims a chunk.
     *
     * @param chunk
     * @param player
     * @return
     */
    default boolean overClaim(FChunk chunk, FPlayer player, Faction otherFaction) {
        return PermissionNextGenFactionsAPI.overClaim(this, otherFaction, chunk, player);
    }

    /**
     * Gets all the members of a Faction.
     *
     * @return
     */
    default Set<FPlayer> getMembers() {
        return NextGenFactionsAPI.getPlayersInFaction(this);
    }

    /**
     * Kicks a player from the faction.
     *
     * @param kickedPlayer
     * @param playerKicking
     * @return
     */
    default boolean kickPlayer(FPlayer kickedPlayer, FPlayer playerKicking) {
        return PermissionNextGenFactionsAPI.kickPlayerFromFaction(kickedPlayer, this, playerKicking);
    }

    /**
     * Invites a player to the faction.
     *
     * @param invitedPlayer
     * @param playerInviting
     * @return
     */
    default boolean invitePlayer(FPlayer invitedPlayer, FPlayer playerInviting) {
        return PermissionNextGenFactionsAPI.invitePlayerToFaction(invitedPlayer, this, playerInviting);
    }

    /**
     * Disbands the faction.
     *
     * @param playerDisbanding
     * @return
     */
    default boolean disband(FPlayer playerDisbanding) {
        return PermissionNextGenFactionsAPI.disbandFaction(this, playerDisbanding);
    }

    /**
     * Gets the default role of the faction.
     *
     * @return
     */
    default FactionRank getDefaultRole() {
        return NextGenFactionsAPI.getDefaultRoleOfFaction(this);
    }

    /**
     * Gets the set of roles of the faction.
     *
     * @return
     */
    default Set<FactionRank> getRoles() {
        return NextGenFactionsAPI.getRolesOfFaction(this);
    }

    /**
     * Checks if a faction can be over-claimed by other faction.
     *
     * @return
     */
    default boolean canBeOverClaimed() {
        int power = this.getPower();
        int claimsCount = this.getAmountOfClaims();
        return claimsCount > power;
    }

    /**
     * Checks if the faction can claim.
     *
     * @return
     */
    default boolean canClaim() {
        int power = this.getPower();
        int claimsCount = this.getAmountOfClaims();
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
        return NextGenFactionsAPI.getPlayer(this.getOwnerId());
    }

    default FactionRank createRank(String roleName, FPlayer player, Set<PermissionType> permissions) {
        return PermissionNextGenFactionsAPI.createRank(roleName, player, this, permissions);
    }

    default boolean deleteRank(String roleName, FPlayer player) {
        return PermissionNextGenFactionsAPI.deleteRank(roleName, player, this);
    }

    default @Nullable FactionRank getRankByName(@NotNull String rankName) {
        return this.getRoles()
                .stream()
                .filter(role -> role.getName().equals(rankName))
                .findFirst()
                .orElse(null);
    }

}
