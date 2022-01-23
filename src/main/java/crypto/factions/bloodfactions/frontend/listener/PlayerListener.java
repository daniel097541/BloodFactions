package crypto.factions.bloodfactions.frontend.listener;

import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.land.impl.FChunkImpl;
import crypto.factions.bloodfactions.commons.model.land.impl.FLocationImpl;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.utils.BukkitLocationUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Objects;

public interface PlayerListener extends Listener {

    NGFConfig getLangConfig();

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleBlockPlace(BlockPlaceEvent event) {

        Player bukkitPlayer = event.getPlayer();
        Block bukkitBlock = event.getBlock();
        Location bukkitLocation = bukkitBlock.getLocation();

        FChunk chunk = FChunkImpl.fromChunk(bukkitLocation.getChunk());
        FPlayer player = ContextHandler.getPlayer(bukkitPlayer);
        Faction playersFaction = player.getFaction();
        Faction factionAt = chunk.getFactionAt();

        // Not a system faction.
        if (!factionAt.isSystemFaction()) {

            // Not same faction.
            if (!playersFaction.equals(factionAt)) {
                event.setCancelled(true);
                String message = (String) this.getLangConfig().read(LangConfigItems.ACTIONS_PLACE_NOT_YOUR_FACTION.getPath());
                MessageContext messageContext = new MessageContextImpl(player, message);
                messageContext.setFaction(factionAt);
                player.sms(messageContext);
            } else {
                boolean isAllowed = player.placeBlock(factionAt, FLocationImpl.fromLocation(bukkitLocation));
                if (!isAllowed) {
                    event.setCancelled(true);
                }
            }
        } else {
            // Not wilderness.
            if (!factionAt.isFactionLessFaction()) {
                event.setCancelled(true);
                String message = (String) this.getLangConfig().read(LangConfigItems.ACTIONS_PLACE_NOT_YOUR_FACTION.getPath());
                MessageContext messageContext = new MessageContextImpl(player, message);
                messageContext.setFaction(factionAt);
                player.sms(messageContext);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleBlockBreak(BlockBreakEvent event) {

        Player bukkitPlayer = event.getPlayer();
        Block bukkitBlock = event.getBlock();
        Location bukkitLocation = bukkitBlock.getLocation();

        FChunk chunk = FChunkImpl.fromChunk(bukkitLocation.getChunk());
        FPlayer player = ContextHandler.getPlayer(bukkitPlayer);
        Faction playersFaction = player.getFaction();
        Faction factionAt = chunk.getFactionAt();

        // Not a system faction.
        if (!factionAt.isSystemFaction()) {

            // Not same faction.
            if (!playersFaction.equals(factionAt)) {
                event.setCancelled(true);
                String message = (String) this.getLangConfig().read(LangConfigItems.ACTIONS_BREAK_NOT_YOUR_FACTION.getPath());
                MessageContext messageContext = new MessageContextImpl(player, message);
                messageContext.setFaction(factionAt);
                player.sms(messageContext);
            }
            // Same faction
            else {
                boolean isAllowed = player.breakBlock(factionAt, FLocationImpl.fromLocation(bukkitLocation));
                if (!isAllowed) {
                    event.setCancelled(true);
                }
            }
        } else {
            // Not wilderness.
            if (!factionAt.isFactionLessFaction()) {
                event.setCancelled(true);
                String message = (String) this.getLangConfig().read(LangConfigItems.ACTIONS_BREAK_NOT_YOUR_FACTION.getPath());
                MessageContext messageContext = new MessageContextImpl(player, message);
                messageContext.setFaction(factionAt);
                player.sms(messageContext);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerMovement(PlayerMoveEvent event) {
        Player bukkitPlayer = event.getPlayer();

        Location fromLocation = event.getFrom();
        Location toLocation = event.getTo();


        // Player effectively moved.
        if (Objects.nonNull(toLocation) && !BukkitLocationUtils.sameLocation(toLocation, fromLocation)) {

            FPlayer player = ContextHandler.getPlayer(bukkitPlayer);

            Objects.requireNonNull(player).proximityCheck();

            FLocation from = FLocationImpl.fromLocation(fromLocation);
            FChunk chunkFrom = from.getChunk();

            FLocation to = FLocationImpl.fromLocation(toLocation);
            FChunk chunkTo = to.getChunk();

            // Chunk changed
            if (!Objects.equals(chunkFrom, chunkTo)) {
                Faction factionFrom = from.getFactionAt();
                Faction factionTo = to.getFactionAt();

                // Changed faction
                if (!Objects.equals(factionTo, factionFrom)) {
                    Objects.requireNonNull(player).changedLand(factionFrom, factionTo);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FPlayer fPlayer = ContextHandler.getPlayer(player);
        Objects.requireNonNull(fPlayer).logIn();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleLogin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FPlayer fPlayer = ContextHandler.getPlayer(player);
        Objects.requireNonNull(fPlayer).logOut();
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player bukkitPlayer = (Player) event.getEntity();
            FPlayer player = ContextHandler.getPlayer(bukkitPlayer.getUniqueId());
            EntityDamageEvent.DamageCause cause = event.getCause();
            Logger.logInfo(cause.name());
            if (cause.equals(EntityDamageEvent.DamageCause.FALL)) {

                boolean getDamage = player.handleFallDamage();

                // Cancel event
                if (!getDamage) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handlePlayerDeath(PlayerDeathEvent event) {
        Player bukkitPlayer = event.getEntity();
        FPlayer player = ContextHandler.getPlayer(bukkitPlayer.getUniqueId());
        Objects.requireNonNull(player).died();
    }

    @EventHandler
    default void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void onPlayerHitOther(EntityDamageByEntityEvent event){

        Entity damaged = event.getEntity();
        Entity hitter = event.getDamager();

        if(damaged instanceof Player && hitter instanceof Player){

            FPlayer playerHitting = ContextHandler.getPlayer(hitter.getUniqueId());
            FPlayer playerDamaged = ContextHandler.getPlayer(damaged.getUniqueId());

            boolean isCancelled = Objects.requireNonNull(playerHitting).hit(playerDamaged);

            if(isCancelled){
                event.setCancelled(true);
            }

        }

    }

}
