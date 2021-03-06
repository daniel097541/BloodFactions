package crypto.factions.bloodfactions.backend.manager;

import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.dao.FactionsDAO;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.invitation.FactionInvitation;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface FactionsManager extends DataManager<Faction> {

    FactionsDAO getDAO();

    PlayerDAO getPlayerDAO();

    RolesDAO getRolesDAO();

//    LoadingCache<String, Faction> getChunkFactionsCache();

    LoadingCache<String, Faction> getNameFactionsCache();

    LoadingCache<UUID, Faction> getPlayerFactionCache();


    default int getCountOfClaims(@NotNull UUID factionId) {
        return this.getDAO().getCountOfClaims(factionId);
    }

    default boolean removeClaim(@NotNull Faction faction, @NotNull FChunk chunk) {
        boolean unClaimed = this.getDAO().removeClaim(faction.getId(), chunk.getId());
//        if (unClaimed) {
//            this.getChunkFactionsCache().invalidate(chunk.getId());
//        }
        return unClaimed;
    }

    default boolean claimForFaction(@NotNull Faction faction, @NotNull FChunk chunk, @NotNull FPlayer player) {
        boolean claimed = this.getDAO().claimForFaction(faction.getId(), chunk, player.getId());
//        if (claimed) {
//            this.getChunkFactionsCache().put(chunk.getId(), faction);
//        }
        return claimed;
    }

    default boolean setHome(@NotNull Faction faction, @NotNull FLocation location, @NotNull FPlayer player) {
        this.removeHome(faction);
        return this.getDAO().setCore(faction.getId(), player.getId(), location);
    }

    default boolean removeHome(@NotNull Faction faction) {
        return this.getDAO().removeCore(faction.getId());
    }

    default boolean removeAllClaimsOfFaction(@NotNull Faction faction) {
        // Invalidate all cache keys.
//        Set<String> keys = this.getChunkFactionsCache()
//                .asMap()
//                .entrySet()
//                .stream()
//                .filter(entry -> entry.getValue().getId().equals(faction.getId()))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toSet());
//
//        this.getChunkFactionsCache().invalidateAll(keys);

        // Remove from db.
        return this.getDAO().removeAllClaimsOfFaction(faction.getId());
    }

    default @Nullable FLocation getHome(@NotNull Faction faction) {
        return this.getDAO().getCore(faction.getId());
    }

    default @Nullable Faction getFactionAtChunk(@NotNull FChunk chunk) throws Exception {
//        return this.getChunkFactionsCache().get(chunk.getId());
        return this.getDAO().getFactionAtChunk(chunk.getId());
    }

    default boolean checkIfPlayerHasFaction(@NotNull FPlayer player) {
        return this.getPlayerDAO().checkIfPlayerHasFaction(player.getId());
    }

    default void addPlayerToFaction(@NotNull FPlayer player, @NotNull Faction faction, @NotNull FPlayer invitedBy) {
        this.getPlayerDAO().addPlayerToFaction(player, faction, invitedBy);
    }

    default void removeAllPlayersFromFaction(@NotNull Faction faction) {
        this.getPlayerDAO().removeAllPlayersFromFaction(faction.getId());
    }

    default @NotNull Faction getFactionOfPlayer(@NotNull FPlayer player) {
        return this.getDAO().getFactionOfPlayer(player.getId());
    }

    default @NotNull Set<FChunk> getAllClaims(@NotNull Faction faction) {
        Set<FChunk> claims = this.getDAO().getAllClaimsOfFaction(faction.getId());
//        claims.forEach(c -> this.getChunkFactionsCache().put(c.getId(), faction));
        return claims;
    }

    default @Nullable FactionInvitation invitePlayerToFaction(@NotNull FPlayer player, @NotNull Faction faction, @NotNull FPlayer inviter) {
        return this.getDAO().createInvitation(faction.getId(), player.getId(), inviter.getId());
    }

    default boolean isPlayerInvitedToFaction(@NotNull FPlayer player, @NotNull Faction faction) {
        return this.getDAO().existsInvitation(faction.getId(), player.getId());
    }

    default boolean removePlayerInvitation(@NotNull FPlayer player, @NotNull Faction faction) {
        return this.getDAO().removeInvitation(faction.getId(), player.getId());
    }

    default @NotNull Set<FactionInvitation> getInvitationsOfFaction(@NotNull Faction faction) {
        return this.getDAO().getInvitationsOfFaction(faction.getId());
    }

    default @NotNull Set<FactionInvitation> getInvitationsOfPlayer(@NotNull FPlayer player) {
        return this.getDAO().getInvitationsOfPlayer(player.getId());
    }

    default @Nullable FactionInvitation getInvitation(@NotNull FPlayer player, @NotNull Faction faction) {
        return this.getDAO().getInvitation(player.getId(), faction.getId());
    }

    default boolean removePlayerFromFaction(@NotNull FPlayer kicked, @NotNull Faction faction) {
        return this.getDAO().removePlayerFromFaction(kicked.getId(), faction.getId());
    }

    default boolean multiClaimForFaction(Faction faction, Set<FChunk> chunks, FPlayer player) {

        Map<FChunk, Faction> chunksThatCanBeClaimed = chunks
                .stream()
                .filter(c -> c.getFactionAt().canBeOverClaimed())
                .map(c -> new AbstractMap.SimpleEntry<>(c, c.getFactionAt()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        int removed = 0;
        for (Map.Entry<FChunk, Faction> entry : chunksThatCanBeClaimed.entrySet()) {
            Faction fAt = entry.getValue();
            FChunk chunk = entry.getKey();

            if (!fAt.isFactionLessFaction()) {
                this.getDAO().removeClaim(fAt.getId(), chunk.getId());
                removed++;
            }
        }

        Logger.logInfo("Removed: " + removed + " claims from other factions.");

        Logger.logInfo("Claiming chunks.");
        boolean response = this.getDAO().multiClaimForFaction(faction.getId(), chunksThatCanBeClaimed, player.getId());
        if (response) {
            Logger.logInfo("Success multi-claim response from DAO, claimed: " + chunksThatCanBeClaimed.size());
            return true;
        } else {
            return false;
        }
    }
}
