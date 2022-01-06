package crypto.factions.bloodfactions.frontend.listener;

import crypto.factions.bloodfactions.backend.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.impl.FChunkImpl;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public interface PlayerListener extends Listener {

    NGFConfig getLangConfig();

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
        } else {

            // Not wilderness.
            if (!factionAt.equals(playersFaction)) {
                event.setCancelled(true);
                String message = (String) this.getLangConfig().read(LangConfigItems.ACTIONS_BREAK_NOT_YOUR_FACTION.getPath());
                MessageContext messageContext = new MessageContextImpl(player, message);
                messageContext.setFaction(factionAt);
                player.sms(messageContext);
            }
        }
    }


}
