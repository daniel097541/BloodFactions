package crypto.factions.bloodfactions.backend.handler.data;

import crypto.factions.bloodfactions.backend.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.config.ConfigItem;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.events.land.permissioned.OverClaimEvent;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public interface AlertsHandler extends Listener {

    JavaPlugin getPlugin();

    NGFConfig getLangConfig();

    default void autoRegister(){
        JavaPlugin plugin = this.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    default String getAlert(ConfigItem item){
        String prefix = (String) this.getLangConfig().read(LangConfigItems.ALERT_PREFIX.getPath());
        return prefix + this.getLangConfig().read(item.getPath());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void handleOverClaimedAlert(OverClaimEvent event){

        Faction overClaimedFaction = event.getOverClaimedFaction();
        FChunk chunk = event.getChunk();
        Faction claimingFaction = event.getFaction();

        String message = this.getAlert(LangConfigItems.ALERT_OVER_CLAIMED);
        MessageContext messageContext = new MessageContextImpl(overClaimedFaction.getMembers(), message);
        messageContext.setFaction(claimingFaction);
        messageContext.setChunk(chunk);
        messageContext.send();
    }

}
