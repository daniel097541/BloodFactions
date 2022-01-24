package crypto.factions.bloodfactions.backend.handler.data;

import crypto.factions.bloodfactions.backend.manager.FactionsManager;
import crypto.factions.bloodfactions.backend.manager.PlayersManager;
import crypto.factions.bloodfactions.backend.manager.RanksManager;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.config.system.SystemConfigItems;
import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.events.faction.SetCoreEvent;
import crypto.factions.bloodfactions.commons.events.faction.callback.*;
import crypto.factions.bloodfactions.commons.events.faction.permissioned.*;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.CreateFactionByNameEvent;
import crypto.factions.bloodfactions.commons.events.faction.unpermissioned.CreateFactionEvent;
import crypto.factions.bloodfactions.commons.events.land.callback.GetClaimsOfFactionEvent;
import crypto.factions.bloodfactions.commons.events.land.callback.GetNumberOfClaimsEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.*;
import crypto.factions.bloodfactions.commons.events.player.unpermissioned.PlayerChangedLandEvent;
import crypto.factions.bloodfactions.commons.events.role.*;
import crypto.factions.bloodfactions.commons.events.shared.callback.GetFactionOfPlayerEvent;
import crypto.factions.bloodfactions.commons.exceptions.NoFactionForFactionLessException;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.faction.FactionImpl;
import crypto.factions.bloodfactions.commons.model.faction.SystemFactionImpl;
import crypto.factions.bloodfactions.commons.model.invitation.FactionInvitation;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import crypto.factions.bloodfactions.commons.model.role.FactionRoleImpl;
import crypto.factions.bloodfactions.commons.tasks.handler.TasksHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public interface FactionsHandler extends DataHandler<Faction> {

    FactionsManager getManager();

    RanksManager getRanksManager();

    PlayersManager getPlayersManager();

    TasksHandler getTasksHandler();

    NGFConfig getSystemConfig();

    NGFConfig getLangConfig();

    Map<UUID, FPlayer> getUnClaimingAllPlayers();

    Map<UUID, FPlayer> getDisbandingPlayers();

    /**
     * Gets the faction-less faction.
     *
     * @param event
     * @throws NoFactionForFactionLessException
     */
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
                String color = (String) this.getSystemConfig().read(SystemConfigItems.defaultFactionsPath + "." + factionSection + SystemConfigItems.systemFactionColorSection);
                return new SystemFactionImpl(id, factionName, color, getDefaultOwnerId());
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
            String color = (String) this.getSystemConfig().read(SystemConfigItems.defaultFactionsPath + "." + factionSection + SystemConfigItems.systemFactionColorSection);
            Faction systemFaction = new SystemFactionImpl(id, factionName, color, getDefaultOwnerId());
            factions.add(systemFaction);
        }
        return factions;
    }

    default void onLoad() {

        Set<Faction> systemFactions = this.getSystemFactions();

        for (Faction faction : systemFactions) {
            UUID id = faction.getId();
            if (!this.getManager().existsById(id)) {
                this.getManager().insert(faction);
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
        faction = this.getManager().insert(faction);

        // Add player to faction
        if (Objects.nonNull(faction)) {

            // Add player to faction.
            this.getManager().addPlayerToFaction(player, faction, player);

            // Create roles
            Map<FactionRank, Set<PermissionType>> rolesPermissions = this.getDefaultRoles(faction.getId());
            for (Map.Entry<FactionRank, Set<PermissionType>> entry : rolesPermissions.entrySet()) {

                FactionRank role = entry.getKey();
                Set<PermissionType> permissions = entry.getValue();

                // Create role
                this.getRanksManager().insert(role);

                // Permissions
                for (PermissionType permissionType : permissions) {
                    this.getRanksManager().addPermissionToRole(role, permissionType);
                }
            }
        }
        return faction;
    }

    /**
     * Gets count of claims of a faction.
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetCountOfClaims(GetNumberOfClaimsEvent event) {
        Faction faction = event.getFaction();
        int count = this.getManager().getCountOfClaims(faction.getId());
        event.setNumberOfClaims(count);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerEnteredFaction(PlayerChangedLandEvent event) {

        Faction factionTo = event.getFactionTo();
        FPlayer player = event.getPlayer();
        Faction playersFaction = player.getFaction();

        String color = "&a";

        if (factionTo.isSystemFaction()) {
            SystemFactionImpl systemFaction = (SystemFactionImpl) factionTo;
            color = systemFaction.getColor();
        } else {
            if (!factionTo.equals(playersFaction)) {
                color = "&c";
            }
        }

        // Send title
        String title = ChatColor.translateAlternateColorCodes('&', color + factionTo.getName());
        player.sendTitle(title);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleDisbandFaction(DisbandFactionEvent event) {
        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        if (this.isDisbanding(player)) {

            // Send message
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_DISBAND_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(faction, successMessage);
            faction.sms(messageContext);

            Set<FPlayer> playersInFactionLand = faction.getOnlineMembers()
                    .stream()
                    .filter(FPlayer::isInHisLand)
                    .collect(Collectors.toSet());

            // Player changed land
            playersInFactionLand.forEach(p -> {
                try {
                    p.changedLand(faction, getFactionForFactionLess());
                } catch (NoFactionForFactionLessException e) {
                    e.printStackTrace();
                }
            });

            // Delete players
            this.getManager().removeAllPlayersFromFaction(faction);

            // Delete claims
            boolean deleted = this.getManager().removeAllClaimsOfFaction(faction);
            // Delete faction
            boolean disbanded = this.getManager().deleteById(faction.getId());

            event.setDisbanded(disbanded);

            // Disband
            this.removeDisbandingPlayer(player);
        }

        // Confirmation required.
        else {
            this.addDisbandingPlayers(player);
            event.setDisbanded(false);

            String confirmationMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_DISBAND_CONFIRMATION_REQUIRED);
            MessageContext confirmationMessageContext = new MessageContextImpl(player, confirmationMessage);
            player.sms(confirmationMessageContext);
        }

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
        Faction faction = this.getManager().getByName(name);
        getFactionEvent.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleFactionExistsByName(CheckIfFactionExistsByNameEvent event) {
        String name = event.getName();
        boolean exists = this.existsByName(name);
        event.setExists(exists);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleGetFactionOfPlayer(GetFactionOfPlayerEvent event) throws NoFactionForFactionLessException {
        FPlayer player = event.getPlayer();
        Faction faction = null;
        try {
            faction = this.getManager().getFactionOfPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Objects.isNull(faction)) {
            faction = this.getFactionForFactionLess();
        }

        event.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleGetFactionAtChunk(GetFactionAtChunkEvent event) throws NoFactionForFactionLessException {
        FChunk chunk = event.getChunk();
        Faction faction = null;
        try {
            faction = this.getManager().getFactionAtChunk(chunk);
        } catch (Exception ignored) {
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
        FactionRank role = this.getRanksManager().getDefaultRole(faction);
        event.setDefaultRole(role);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetRolesOfFaction(GetRolesOfFactionEvent event) {
        Faction faction = event.getFaction();
        Set<FactionRank> roles = this.getRanksManager().getAllRolesOfFaction(faction);
        event.setRoles(roles);
    }

    /**
     * Handles multi claim.
     *
     * @param event
     * @throws NoFactionForFactionLessException
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleMultiClaim(MultiClaimEvent event) throws NoFactionForFactionLessException {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        Set<FChunk> chunks = event.getChunks();

        Bukkit.getScheduler().runTaskAsynchronously(this.getPlugin(), () -> {

            long initClaim = System.currentTimeMillis();
            boolean multiClaimed = this.getManager().multiClaimForFaction(faction, chunks, player);
            long endClaim = System.currentTimeMillis();
            Logger.logInfo("Time to multi-claim asynchronously: " + (endClaim - initClaim) + " ms");

            if (multiClaimed) {
                Logger.logInfo("Multi Claimed successfully.");
                event.setSuccess(true);

                // Send change land to all players in the claimed chunks.
                Set<FPlayer> onlinePlayers = ContextHandler.getOnlinePlayers();

                Logger.logInfo("Warning " + onlinePlayers.size() + " players about multi-claim.");

                onlinePlayers
                        .stream()
                        .filter(p -> chunks.contains(p.getChunk()))
                        .map(p -> new AbstractMap.SimpleEntry<>(p, p.getChunk()))
                        .forEach(entry -> entry.getKey().changedLand(entry.getValue().getFactionAt(), faction));

            }
        });
        event.setSuccess(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleClaim(ClaimEvent event) {

        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();
        Faction factionAt = chunk.getFactionAt();

        // Already claimed this land.
        if (factionAt.equals(faction)) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_ALREADY_OWNED);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            event.setSuccess(false);
            return;
        }

        // Need more pow
        if (!faction.canClaim() && !player.isOp()) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_NOT_ENOUGH_POWER);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            event.setSuccess(false);
            return;
        }

        // Cannot over-claim faction
        if (!factionAt.canBeOverClaimed() && !player.isOp()) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_FACTION_IS_STRONG_TO_KEEP);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            messageContext.setFaction(factionAt);
            player.sms(messageContext);
            event.setSuccess(false);
            return;
        }

        boolean claimed;

        // Over-Claim
        if (!factionAt.isSystemFaction()) {
            claimed = faction.overClaim(chunk, player, factionAt);
        }
        // Simple claim
        else {
            claimed = this.getManager().claimForFaction(faction, chunk, player);
        }

        // Success
        if (claimed) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            messageContext.setFaction(factionAt);
            player.sms(messageContext);
        }
        // Failed
        else {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_FAIL);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
        }

        Logger.logInfo("Player &d" + player.getName() + " &7is claiming for faction: &d" + faction.getName() + " &7at: &d" + chunk.getId());

        if (claimed) {
            chunk
                    .getPlayersAtChunk()
                    .forEach(player1 -> player1.changedLand(factionAt, faction));
        }

        event.setSuccess(claimed);
    }

    default void unClaim(FPlayer player, FChunk chunk, Faction faction) {
        Logger.logInfo("Player &d" + player.getName() + " &7is un-claiming for faction: &d" + faction.getName() + " &7at: &d" + chunk.getId());
        boolean unClaimed = this.getManager().removeClaim(faction, chunk);

        if (unClaimed) {
            chunk
                    .getPlayersAtChunk()
                    .forEach(player1 -> {
                        try {
                            player1.changedLand(faction, this.getFactionForFactionLess());
                        } catch (NoFactionForFactionLessException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleUnClaim(UnClaimEvent event) throws NoFactionForFactionLessException {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();
        Bukkit.getScheduler().runTaskAsynchronously(this.getPlugin(), () -> this.unClaim(player, chunk, faction));
        event.setSuccess(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    default void handleSetCore(SetCoreEvent event) {
        FLocation core = event.getCore();
        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        // Set new home.
        boolean homeSet = this.getManager().setHome(faction, core, player);
        event.setSuccess(homeSet);
    }

    @EventHandler(priority = EventPriority.HIGH)
    default void handleGetCore(GetCoreEvent event) {
        Faction faction = event.getFaction();
        FLocation core = this.getManager().getHome(faction);
        event.setCore(core);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    default void handleOverClaim(OverClaimEvent event) {
        Faction faction = event.getFaction();
        Faction overClaimedFaction = event.getOverClaimedFaction();
        FPlayer player = event.getPlayer();
        FChunk chunk = event.getChunk();
        Logger.logInfo("Player &a" + player.getName() + " &7is over-claiming from faction: &a" + overClaimedFaction.getName() + " &7at: &2" + chunk.getId());

        boolean removed = this.getManager().removeClaim(overClaimedFaction, chunk);

        if (removed) {
            boolean claimed = this.getManager().claimForFaction(faction, chunk, player);

            chunk
                    .getPlayersAtChunk()
                    .forEach(player1 -> player1.changedLand(overClaimedFaction, faction));

            event.setSuccess(claimed);
        } else {
            Logger.logInfo("Failed to un-claim.");
            event.setSuccess(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleRoleCreation(CreateRankEvent event) {

        String roleName = event.getRoleName();
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        Set<PermissionType> permissions = event.getPermissions();

        // Role does not exist by name.
        if (!this.getRanksManager().roleExistsByName(roleName, faction)) {
            FactionRank role = new FactionRoleImpl(UUID.randomUUID(), faction.getId(), roleName, false);
            this.getRanksManager().insert(role);
            if (!permissions.isEmpty()) {
                for (PermissionType permissionType : permissions) {
                    this.getRanksManager().addPermissionToRole(role, permissionType);
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
            Set<PermissionType> permissions = this.getRanksManager().getRolePermissions(role);
            String roleBody = bodyBlueprint.replace("{rank_name}", role.getName())
                    .replace("{permission_list}", permissions.stream().map(PermissionType::name).collect(Collectors.joining(", ")));
            finalMessage.append(roleBody);
        }

        MessageContext messageContext = new MessageContextImpl(player, finalMessage.toString());
        messageContext.setFaction(faction);
        player.sms(messageContext);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleRoleDeletion(DeleteRankEvent event) {

        String roleName = event.getRoleName();
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FactionRank rank = this.getRanksManager().getRankByName(roleName, faction);

        // Role exists by name.
        if (Objects.nonNull(rank)) {

            // Delete
            this.getRanksManager().deleteById(rank.getId());
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

    default boolean isDisbanding(FPlayer player) {
        return this.getDisbandingPlayers().containsKey(player.getId());
    }

    default void removeDisbandingPlayer(FPlayer player) {
        this.getDisbandingPlayers().remove(player.getId());
    }

    default void addDisbandingPlayers(FPlayer player) {
        this.getDisbandingPlayers().put(player.getId(), player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.getPlugin(), () -> {
            this.removeDisbandingPlayer(player);
        }, 30000 / 20);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleGetAllClaimsOfFaction(GetClaimsOfFactionEvent event) {
        Faction faction = event.getFaction();
        Set<FChunk> allChunks = this.getManager().getAllClaims(faction);
        event.setChunks(allChunks);
    }

    default void sendExitLandToAllPlayers(Faction faction) throws NoFactionForFactionLessException {

        Set<FChunk> chunks = faction.getAllAClaims();
        Faction factionLess = this.getFactionForFactionLess();

        for (FPlayer player : ContextHandler.getOnlinePlayers()) {
            FChunk playerChunk = player.getChunk();
            if (chunks.contains(playerChunk)) {
                player.changedLand(faction, factionLess);
            }
        }
    }

    default void unClaimAll(Faction faction, FPlayer player) {
        try {
            this.sendExitLandToAllPlayers(faction);
        } catch (NoFactionForFactionLessException e) {
            e.printStackTrace();
        }
        this.removeUnClaimingAll(player);
        boolean removed = this.getManager().removeAllClaimsOfFaction(faction);
        if (removed) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_UN_CLAIM_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            messageContext.setFaction(faction);
            player.sms(messageContext);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleUnClaimAll(UnClaimAllEvent event) throws NoFactionForFactionLessException {

        FPlayer player = event.getPlayer();
        Faction faction = event.getFaction();

        int countOfClaims = faction.getCountOfClaims();

        if (countOfClaims == 0) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_UN_CLAIM_ALL_NO_CLAIMS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            event.setSuccess(false);
            return;
        }

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

            // All players from faction exit the land
            Bukkit.getScheduler().runTaskAsynchronously(this.getPlugin(), () -> {
                this.unClaimAll(faction, player);
            });


            event.setSuccess(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handlePlayerBreakBlockInFaction(PlayerBreakBlockInFactionEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FLocation location = event.getLocation();
        Block block = Objects.requireNonNull(location.getBukkitLocation()).getBlock();
        event.setSuccess(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handlePlayerPlaceBlockInFaction(PlayerPlaceBlockInFactionEvent event) {
        Faction faction = event.getFaction();
        FPlayer player = event.getPlayer();
        FLocation location = event.getLocation();

        Block block = Objects.requireNonNull(location.getBukkitLocation()).getBlock();
        event.setSuccess(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleInvitePlayerToFaction(InvitePlayerToFactionEvent event) {

        FPlayer invitedPlayer = event.getInvitedPlayer();
        FPlayer inviter = event.getPlayer();
        Faction faction = event.getFaction();
        boolean invited = false;

        // Player already in faction.
        if (invitedPlayer.isInFaction(faction)) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_ALREADY_IN_FACTION);
            MessageContext messageContext = new MessageContextImpl(inviter, successMessage);
            messageContext.setTargetPlayer(invitedPlayer);
            inviter.sms(messageContext);
        } else {
            boolean isPlayerInvitedAlready = this.getManager().isPlayerInvitedToFaction(invitedPlayer, faction);

            // Invite player
            if (!isPlayerInvitedAlready) {
                FactionInvitation invitation = this.getManager().invitePlayerToFaction(invitedPlayer, faction, inviter);
                invited = true;

                // Send success message
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_SUCCESS);
                MessageContext messageContext = new MessageContextImpl(inviter, successMessage);
                messageContext.setTargetPlayer(invitedPlayer);
                inviter.sms(messageContext);


                // Send invited message
                String invitedMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_INVITED_TO_FACTION);
                MessageContext invitedMessageContext = new MessageContextImpl(invitedPlayer, invitedMessage);
                invitedMessageContext.setTargetPlayer(inviter);
                invitedMessageContext.setFaction(faction);
                invitedPlayer.sms(invitedMessageContext);
            }

            // Already invited
            else {
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_ALREADY_INVITED);
                MessageContext messageContext = new MessageContextImpl(inviter, successMessage);
                messageContext.setTargetPlayer(invitedPlayer);
                inviter.sms(messageContext);
            }
        }
        event.setInvited(invited);
        event.setSuccess(event.isInvited());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleDeInvitePlayerFromFaction(DeInvitePlayerFromFactionEvent event) {

        FPlayer player = event.getDeInvitedPlayer();
        Faction faction = event.getFaction();

        boolean deInvited = this.getManager().removePlayerInvitation(player, faction);
        event.setDeInvited(deInvited);
        event.setSuccess(deInvited);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleKickPlayer(KickPlayerFromFactionEvent event) {

        FPlayer player = event.getPlayer();
        FPlayer kicked = event.getKickedPlayer();
        Faction faction = event.getFaction();

        boolean kickedSucceed = this.getManager().removePlayerFromFaction(kicked, faction);
        event.setKicked(kickedSucceed);

        if (kickedSucceed) {
            String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_KICK_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, message);
            messageContext.setTargetPlayer(kicked);
            player.sms(messageContext);

            String kickedMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_KICKED);
            MessageContext kickedMessageContext = new MessageContextImpl(kicked, kickedMessage);
            kickedMessageContext.setTargetPlayer(player);
            kickedMessageContext.setTargetFaction(faction);
            kicked.sms(kickedMessageContext);
        }

    }

}
