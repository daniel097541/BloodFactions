package crypto.factions.bloodfactions.frontend.listener;

import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
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
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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
        FPlayer player = NextGenFactionsAPI.getPlayer(bukkitPlayer);
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
            }
            else{
                boolean isAllowed = player.placeBlock(factionAt, FLocationImpl.fromLocation(bukkitLocation));
                if(!isAllowed){
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
        FPlayer player = NextGenFactionsAPI.getPlayer(bukkitPlayer);
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

        FLocation from = FLocationImpl.fromLocation(event.getFrom());
        FChunk chunkFrom = from.getChunk();

        FLocation to = FLocationImpl.fromLocation(Objects.requireNonNull(event.getTo()));
        FChunk chunkTo = to.getChunk();

        if (!Objects.equals(chunkFrom, chunkTo)) {
            FPlayer player = NextGenFactionsAPI.getPlayer(bukkitPlayer);
            Faction factionFrom = from.getFactionAt();
            Faction factionTo = to.getFactionAt();

            // Changed faction
            if (!Objects.equals(factionTo, factionFrom)) {
                Objects.requireNonNull(player).changedLand(factionFrom, factionTo);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FPlayer fPlayer = NextGenFactionsAPI.getPlayer(player);
        Objects.requireNonNull(fPlayer).logIn();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleLogin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FPlayer fPlayer = NextGenFactionsAPI.getPlayer(player);
        Objects.requireNonNull(fPlayer).logOut();
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player bukkitPlayer = (Player) event.getEntity();
            FPlayer player = NextGenFactionsAPI.getPlayer(bukkitPlayer.getUniqueId());
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
        FPlayer player = NextGenFactionsAPI.getPlayer(bukkitPlayer.getUniqueId());

        player.died();
    }

    @EventHandler
    default void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

}
