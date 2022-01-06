package crypto.anguita.nextgenfactions.backend.handler.data;

import crypto.anguita.nextgenfactions.backend.config.system.SystemConfigItems;
import crypto.anguita.nextgenfactions.backend.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.dao.RolesDAO;
import crypto.anguita.nextgenfactions.commons.config.NGFConfig;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.*;
import crypto.anguita.nextgenfactions.commons.events.faction.permissioned.DisbandFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned.CreateFactionByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned.CreateFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.land.callback.GetNumberOfClaimsEvent;
import crypto.anguita.nextgenfactions.commons.events.land.permissioned.ClaimEvent;
import crypto.anguita.nextgenfactions.commons.events.land.permissioned.OverClaimEvent;
import crypto.anguita.nextgenfactions.commons.events.role.GetDefaultRoleOfFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.role.GetRolesOfFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.shared.callback.GetFactionOfPlayerEvent;
import crypto.anguita.nextgenfactions.commons.exceptions.NoFactionForFactionLessException;
import crypto.anguita.nextgenfactions.commons.logger.Logger;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.faction.FactionImpl;
import crypto.anguita.nextgenfactions.commons.model.faction.SystemFactionImpl;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRoleImpl;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public interface FactionsHandler extends DataHandler<Faction> {

    @Override
    FactionsDAO getDao();

    PlayerDAO getPlayerDAO();

    RolesDAO getRolesDAO();

    NGFConfig getSystemConfig();

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetFactionLessFaction(GetFactionLessFactionEvent event) throws NoFactionForFactionLessException {
        Faction factionLessFaction = this.getFactionForFactionLess();
        event.setFaction(factionLessFaction);
    }

    default @NotNull Faction getFactionForFactionLess() throws NoFactionForFactionLessException {
        Set<String> systemFactions = this.getSystemConfig().getKeys(SystemConfigItems.defaultFactionsPath);
        for (String factionSection : systemFactions) {

            boolean isForFactionLess = (boolean) this.getSystemConfig().read(SystemConfigItems.defaultFactionsPath + "." + factionSection + SystemConfigItems.systemFactionDefaultFaction);
            if (isForFactionLess) {
                UUID id = UUID.fromString((String) this.getSystemConfig().read(SystemConfigItems.defaultFactionsPath + "." + factionSection + SystemConfigItems.systemFactionIdSection));
                String factionName = (String) this.getSystemConfig().read(SystemConfigItems.defaultFactionsPath + "." + factionSection + SystemConfigItems.systemFactionNameSection);
                return new SystemFactionImpl(id, factionName, getDefaultOwnerId());
            }
        }
        throw new NoFactionForFactionLessException();
    }

    default UUID getDefaultOwnerId() {
        return Bukkit.getOfflinePlayer("BrutalFiestas").getUniqueId();
    }

    default Set<Faction> getSystemFactions() {
        Set<String> systemFactions = this.getSystemConfig().getKeys(SystemConfigItems.defaultFactionsPath);
        Set<Faction> factions = new HashSet<>();
        for (String factionSection : systemFactions) {
            UUID id = UUID.fromString((String) this.getSystemConfig().read(SystemConfigItems.defaultFactionsPath + "." + factionSection + SystemConfigItems.systemFactionIdSection));
            String factionName = (String) this.getSystemConfig().read(SystemConfigItems.defaultFactionsPath + "." + factionSection + SystemConfigItems.systemFactionNameSection);
            Faction systemFaction = new SystemFactionImpl(id, factionName, getDefaultOwnerId());
            factions.add(systemFaction);
        }
        return factions;
    }

    default void onLoad() {

        Set<Faction> systemFactions = this.getSystemFactions();

        for (Faction faction : systemFactions) {
            UUID id = faction.getId();
            if (!this.getDao().existsById(id)) {
                this.getDao().insert(faction);
            }
        }

    }

    default Map<FactionRole, Set<PermissionType>> getDefaultRoles(UUID factionId) {
        Set<String> rolesSections = this.getSystemConfig().getKeys(SystemConfigItems.defaultRolesPath);
        Map<FactionRole, Set<PermissionType>> factionRoles = new HashMap<>();
        for (String roleSection : rolesSections) {

            UUID id = UUID.randomUUID();//fromString((String) this.getSystemConfig().read(SystemConfigItems.defaultRolesPath + "." + roleSection + SystemConfigItems.roleIdSection));
            String name = (String) this.getSystemConfig().read(SystemConfigItems.defaultRolesPath + "." + roleSection + SystemConfigItems.roleNameSection);
            String[] permissions = (String[]) this.getSystemConfig().read(SystemConfigItems.defaultRolesPath + "." + roleSection + SystemConfigItems.rolePermissionsSection);
            boolean defaultRole = (boolean) this.getSystemConfig().read(SystemConfigItems.defaultRolesPath + "." + roleSection + SystemConfigItems.roleDefaultSection);

            factionRoles.put(new FactionRoleImpl(id, factionId, name, defaultRole), Arrays.stream(permissions).map(PermissionType::fromName).collect(Collectors.toSet()));
        }
        return factionRoles;
    }

    default Faction createFaction(Faction faction, FPlayer player) {

        // Insert in DB
        faction = this.getDao().insert(faction);

        // Add player to faction
        if (Objects.nonNull(faction)) {

            // Add player to faction.
            this.getPlayerDAO().addPlayerToFaction(player, faction, player);

            // Create roles
            Map<FactionRole, Set<PermissionType>> rolesPermissions = this.getDefaultRoles(faction.getId());
            for (Map.Entry<FactionRole, Set<PermissionType>> entry : rolesPermissions.entrySet()) {

                FactionRole role = entry.getKey();
                Set<PermissionType> permissions = entry.getValue();

                // Create role
                this.getRolesDAO().insert(role);

                // Permissions
                for (PermissionType permissionType : permissions) {
                    this.getRolesDAO().addPermissionToRole(role, permissionType);
                }
            }
        }
        return faction;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetCountOfClaims(GetNumberOfClaimsEvent event) {
        Faction faction = event.getFaction();
        int count = this.getDao().getCountOfClaims(faction.getId());
        event.setNumberOfClaims(count);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleDisbandFaction(DisbandFactionEvent event) {
        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        // Delete players
        this.getPlayerDAO().removeAllPlayersFromFaction(faction);

        // Delete claims
        this.getDao().removeAllClaimsOfFaction(faction);

        // Delete faction
        boolean disbanded = this.getDao().deleteById(faction.getId());

        event.setDisbanded(disbanded);
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleCreateFaction(CreateFactionByNameEvent event) {
        String factionName = event.getName();
        FPlayer player = event.getPlayer();
        Faction faction = new FactionImpl(UUID.randomUUID(), factionName, player.getId());
        faction = this.createFaction(faction, player);
        event.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleCreateFaction(CreateFactionEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        faction = this.createFaction(faction, player);
        event.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleGetFaction(GetFactionEvent getFactionEvent) {
        UUID id = getFactionEvent.getId();
        Faction faction = this.getById(id);
        getFactionEvent.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleGetFactionByName(GetFactionByNameEvent getFactionEvent) {
        String name = getFactionEvent.getName();
        Faction faction = this.getByName(name);
        getFactionEvent.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleFactionExistsByName(CheckIfFactionExistsByNameEvent event) {
        String name = event.getName();
        boolean exists = this.existsByName(name);
        event.setExists(exists);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleGetFactionOfPlayer(GetFactionOfPlayerEvent event) {
        FPlayer player = event.getPlayer();
        Faction faction = this.getDao().getFactionOfPlayer(player.getId());
        event.setFaction(faction);
    }

    default Faction getFactionLess() {
        String id = "00000000-0000-0000-0000-000000000000";
        return this.getById(UUID.fromString(id));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleGetFactionAtChunk(GetFactionAtChunkEvent event) {
        FChunk chunk = event.getChunk();
        Faction faction = this.getDao().getFactionAtChunk(chunk);

        // If the faction is null, then return faction less.
        if (Objects.isNull(faction)) {
            faction = this.getFactionLess();
        }

        event.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetDefaultRole(GetDefaultRoleOfFactionEvent event) {
        Faction faction = event.getFaction();
        FactionRole role = this.getRolesDAO().getDefaultRole(faction.getId());
        event.setDefaultRole(role);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetRolesOfFaction(GetRolesOfFactionEvent event) {
        Faction faction = event.getFaction();
        Set<FactionRole> roles = this.getRolesDAO().getAllRolesOfFaction(faction.getId());
        event.setRoles(roles);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleClaim(ClaimEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();

        Logger.logInfo("Player " + player.getName() + " is claiming for faction: " + faction.getName() + " at: " + chunk.getId());
        boolean claimed = this.getDao().claimForFaction(faction, chunk, player.getId());
        event.setSuccess(claimed);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleOverClaim(OverClaimEvent event) {
        Faction faction = event.getFaction();
        Faction overClaimedFaction = event.getOverClaimedFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();

        boolean removed = this.getDao().removeClaim(overClaimedFaction, chunk);

        if(removed) {
            Logger.logInfo("Player " + player.getName() + " is claiming for faction: " + faction.getName() + " at: " + chunk.getId());
            boolean claimed = this.getDao().claimForFaction(faction, chunk, player.getId());
            event.setSuccess(claimed);
        }
        else {
            Logger.logInfo("Failed to un-claim.");
            event.setSuccess(false);
        }
    }

}
