package crypto.anguita.nextgenfactions.commons.model.faction;

import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.api.PermissionNextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.model.NextGenFactionEntity;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;

import java.util.Map;
import java.util.Set;

public interface Faction extends NextGenFactionEntity {

    boolean isSystemFaction();

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
    default FactionRole getDefaultRole() {
        return NextGenFactionsAPI.getDefaultRoleOfFaction(this);
    }

    /**
     * Gets the set of roles of the faction.
     *
     * @return
     */
    default Set<FactionRole> getRoles() {
        return NextGenFactionsAPI.getRolesOfFaction(this);
    }
}
