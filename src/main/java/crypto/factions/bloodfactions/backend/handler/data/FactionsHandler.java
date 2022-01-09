package crypto.factions.bloodfactions.backend.handler.data;

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
import crypto.factions.bloodfactions.commons.events.land.callback.GetNumberOfClaimsEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.ClaimEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.OverClaimEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.UnClaimAllEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.UnClaimEvent;
import crypto.factions.bloodfactions.commons.events.role.*;
import crypto.factions.bloodfactions.commons.events.shared.callback.GetFactionOfPlayerEvent;
import crypto.factions.bloodfactions.commons.exceptions.NoFactionForFactionLessException;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.faction.FactionImpl;
import crypto.factions.bloodfactions.commons.model.faction.SystemFactionImpl;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import crypto.factions.bloodfactions.commons.model.role.FactionRoleImpl;
import crypto.factions.bloodfactions.commons.tasks.handler.TasksHandler;
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

    TasksHandler getTasksHandler();

    NGFConfig getSystemConfig();

    NGFConfig getLangConfig();

    LoadingCache<String, Faction> getChunkFactionsCache();

    LoadingCache<String, Faction> getNameFactionsCache();

    Map<UUID, FPlayer> getUnClaimingAllPlayers();

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

    default Map<FactionRank, Set<PermissionType>> getDefaultRoles(UUID factionId) {
        Set<String> rolesSections = this.getSystemConfig().getKeys(SystemConfigItems.defaultRolesPath);
        Map<FactionRank, Set<PermissionType>> factionRoles = new HashMap<>();
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
            Map<FactionRank, Set<PermissionType>> rolesPermissions = this.getDefaultRoles(faction.getId());
            for (Map.Entry<FactionRank, Set<PermissionType>> entry : rolesPermissions.entrySet()) {

                FactionRank role = entry.getKey();
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
        FactionRank role = this.getRolesDAO().getDefaultRole(faction.getId());
        event.setDefaultRole(role);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetRolesOfFaction(GetRolesOfFactionEvent event) {
        Faction faction = event.getFaction();
        Set<FactionRank> roles = this.getRolesDAO().getAllRolesOfFaction(faction.getId());
        event.setRoles(roles);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleClaim(ClaimEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();
        Faction factionAt = chunk.getFactionAt();

        Logger.logInfo("Player &d" + player.getName() + " &7is claiming for faction: &d" + faction.getName() + " &7at: &d" + chunk.getId());
        boolean claimed = this.getDao().claimForFaction(faction.getId(), chunk, player.getId());

        // If claimed update cache.
        if (claimed) {
            this.getChunkFactionsCache().put(chunk.getId(), faction);
            player.changedLand(factionAt, faction);
        }

        event.setSuccess(claimed);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleUnClaim(UnClaimEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();
        Faction factionAt = chunk.getFactionAt();

        Logger.logInfo("Player &d" + player.getName() + " &7is un-claiming for faction: &d" + faction.getName() + " &7at: &d" + chunk.getId());
        boolean unClaimed = this.getDao().removeClaim(faction.getId(), chunk.getId());

        // If unclaimed, invalidate chunk in cache.
        if (unClaimed) {
            this.getChunkFactionsCache().invalidate(chunk.getId());
            player.changedLand(factionAt, Objects.requireNonNull(player.getLocation()).getFactionAt());
        }

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

            if (claimed) {
                this.getChunkFactionsCache().put(chunk.getId(), faction);
            }

            event.setSuccess(claimed);
        } else {
            Logger.logInfo("Failed to un-claim.");
            event.setSuccess(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleRoleCreation(CreateRankEvent event) {

        String roleName = event.getRoleName();
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        Set<PermissionType> permissions = event.getPermissions();

        // Role does not exist by name.
        if (!this.getRolesDAO().roleExistsByName(roleName, faction.getId())) {
            FactionRank role = new FactionRoleImpl(UUID.randomUUID(), faction.getId(), roleName, false);
            this.getRolesDAO().insert(role);
            if (!permissions.isEmpty()) {
                for (PermissionType permissionType : permissions) {
                    this.getRolesDAO().addPermissionToRole(role, permissionType);
                }
            }
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_CREATE);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }

        // Role already exists.
        else {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_ALREADY_EXISTS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleListRoles(ListRolesEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();

        StringBuilder finalMessage = new StringBuilder((String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_LIST_HEADER));
        String bodyBlueprint = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_LIST_BODY);

        Set<FactionRank> roles = faction.getRoles();

        for (FactionRank role : roles) {
            Set<PermissionType> permissions = this.getRolesDAO().getRolePermissions(role.getId());
            String roleBody = bodyBlueprint.replace("{rank_name}", role.getName())
                    .replace("{permission_list}", permissions.stream().map(PermissionType::name).collect(Collectors.joining(", ")));
            finalMessage.append(roleBody);
        }

        MessageContext messageContext = new MessageContextImpl(player, finalMessage.toString());
        messageContext.setFaction(faction);
        player.sms(messageContext);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleRoleDeletion(DeleteRankEvent event) {

        String roleName = event.getRoleName();
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();

        // Role exists by name.
        if (this.getRolesDAO().roleExistsByName(roleName, faction.getId())) {

            // Delete
            this.getRolesDAO().deleteRoleByName(roleName, faction.getId());
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_DELETE);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }

        // Role does not exist.
        else {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_NOT_EXISTS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }
    }

    default void addUnClaimingAll(FPlayer player) {
        this.getUnClaimingAllPlayers().put(player.getId(), player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.getPlugin(), () -> this.getUnClaimingAllPlayers().remove(player.getId()), (30 * 1000) / 20);
    }

    default boolean isUnClaimingAll(FPlayer player) {
        return this.getUnClaimingAllPlayers().containsKey(player.getId());
    }

    default void removeUnClaimingAll(FPlayer player) {
        this.getUnClaimingAllPlayers().remove(player.getId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleUnClaimAll(UnClaimAllEvent event) {

        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        boolean isUnClaimingAll = this.isUnClaimingAll(player);

        if (!isUnClaimingAll) {
            this.addUnClaimingAll(player);
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_UN_CLAIM_ALL_CONFIRMATION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            event.setSuccess(false);
        }

        // Un-Claim all
        else {
            this.removeUnClaimingAll(player);
            boolean removed = this.getDao().removeAllClaimsOfFaction(faction);
            event.setSuccess(removed);
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_UN_CLAIM_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            messageContext.setFaction(faction);
            player.sms(messageContext);
        }
    }


}
