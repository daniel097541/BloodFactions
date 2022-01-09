package crypto.factions.bloodfactions.backend.manager;

import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;

import java.util.Set;

public interface RanksManager extends DataManager<FactionRank> {

    RolesDAO getDAO();

    default void addPermissionToRole(FactionRank role, PermissionType permissionType) {
        this.getDAO().addPermissionToRole(role.getId(), permissionType);
    }

    default FactionRank getDefaultRole(Faction faction) {
        return this.getDAO().getDefaultRole(faction.getId());
    }

    default Set<FactionRank> getAllRolesOfFaction(Faction faction) {
        return this.getDAO().getAllRolesOfFaction(faction.getId());
    }

    default boolean roleExistsByName(String roleName, Faction faction) {
        return this.getDAO().roleExistsByName(roleName, faction.getId());
    }

    default Set<PermissionType> getRolePermissions(FactionRank rank) {
        return this.getDAO().getRolePermissions(rank.getId());
    }

    default FactionRank getRoleOFPlayer(FPlayer player) {
        return this.getDAO().getRoleOFPlayer(player.getId());
    }

    default boolean setPlayersRole(FPlayer player, FactionRank rank) {
        return this.getDAO().setPlayersRole(player, rank);
    }

    default FactionRank getRankByName(String roleName, Faction faction) {
        return this.getDAO().getRankByName(roleName, faction.getId());
    }
}
