package crypto.factions.bloodfactions.backend.handler.data;

import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.backend.config.system.SystemConfigItems;
import crypto.factions.bloodfactions.backend.dao.FactionsDAO;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.events.faction.SetCoreEvent;
import crypto.factions.bloodfactions.commons.events.faction.callback.*;
import crypto.factions.bloodfactions.commons.events.faction.permissioned.DisbandFactionEvent;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.CreateFactionByNameEvent;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.CreateFactionEvent;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.ShowFactionEvent;
import crypto.factions.bloodfactions.commons.events.land.callback.GetNumberOfClaimsEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.ClaimEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.OverClaimEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.UnClaimEvent;
import crypto.factions.bloodfactions.commons.events.role.GetDefaultRoleOfFactionEvent;
import crypto.factions.bloodfactions.commons.events.role.GetRolesOfFactionEvent;
import crypto.factions.bloodfactions.commons.events.shared.callback.GetFactionOfPlayerEvent;
import crypto.factions.bloodfactions.commons.exceptions.NoFactionForFactionLessException;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.faction.FactionImpl;
import crypto.factions.bloodfactions.commons.model.faction.SystemFactionImpl;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRole;
import crypto.factions.bloodfactions.commons.model.role.FactionRoleImpl;
import crypto.factions.bloodfactions.commons.utils.StringUtils;
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

    NGFConfig getLangConfig();

    LoadingCache<String, Faction> getChunkFactionsCache();

    LoadingCache<String, Faction> getNameFactionsCache();

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
        Faction faction;
        try {
            faction = this.getNameFactionsCache().get(name);
        } catch (Exception e) {
            faction = null;
        }
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleGetFactionAtChunk(GetFactionAtChunkEvent event) throws NoFactionForFactionLessException {
        FChunk chunk = event.getChunk();
        Faction faction;

        try {
            faction = this.getChunkFactionsCache().get(chunk.getId());
        } catch (Exception e) {
            faction = null;
        }

        // If the faction is null, then return faction less.
        if (Objects.isNull(faction)) {
            faction = this.getFactionForFactionLess();
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

        Logger.logInfo("Player &d" + player.getName() + " &7is claiming for faction: &d" + faction.getName() + " &7at: &d" + chunk.getId());
        boolean claimed = this.getDao().claimForFaction(faction.getId(), chunk, player.getId());
        event.setSuccess(claimed);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleUnClaim(UnClaimEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();

        Logger.logInfo("Player &d" + player.getName() + " &7is un-claiming for faction: &d" + faction.getName() + " &7at: &d" + chunk.getId());
        boolean unClaimed = this.getDao().removeClaim(faction.getId(), chunk.getId());
        event.setSuccess(unClaimed);
    }

    @EventHandler(priority = EventPriority.HIGH)
    default void handleSetCore(SetCoreEvent event) {
        FLocation core = event.getCore();
        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();
        // Remove previous core.
        this.getDao().removeCore(faction.getId());

        // Set new core.
        this.getDao().setCore(faction.getId(), player.getId(), core);
    }

    @EventHandler(priority = EventPriority.HIGH)
    default void handleGetCore(GetCoreEvent event) {
        Faction faction = event.getFaction();
        FLocation core = this.getDao().getCore(faction.getId());
        event.setCore(core);
    }

    @EventHandler(priority = EventPriority.HIGH)
    default void handleOverClaim(OverClaimEvent event) {
        Faction faction = event.getFaction();
        Faction overClaimedFaction = event.getOverClaimedFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();
        Logger.logInfo("Player &a" + player.getName() + " &7is over-claiming from faction: &a" + overClaimedFaction.getName() + " &7at: &2" + chunk.getId());

        boolean removed = this.getDao().removeClaim(overClaimedFaction.getId(), chunk.getId());

        if (removed) {
            boolean claimed = this.getDao().claimForFaction(faction.getId(), chunk, player.getId());
            event.setSuccess(claimed);
        } else {
            Logger.logInfo("Failed to un-claim.");
            event.setSuccess(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleShowFaction(ShowFactionEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        int power = faction.getPower();
        Set<FPlayer> members = faction.getMembers();
        FPlayer owner = faction.getOwner();

        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("{faction_power}", String.valueOf(power));
        placeHolders.put("{faction_name}", faction.getName());
        placeHolders.put("{faction_members}", members.stream().map(FPlayer::getName).collect(Collectors.joining(", ")));
        placeHolders.put("{faction_owner}", owner.getName());

        String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_SHOW_SUCCESS);
        player.sms(StringUtils.replacePlaceHolders(message, placeHolders));
    }

}
