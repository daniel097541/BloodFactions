package crypto.anguita.nextgenfactions.backend.handler.data;

import crypto.anguita.nextgenfactions.backend.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.CheckIfFactionExistsByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionAtChunkEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.Objects;
import java.util.UUID;

public interface FactionsHandler extends DataHandler<Faction> {

    @Override
    FactionsDAO getDao();

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    default void handleGetFaction(GetFactionEvent getFactionEvent) {
        UUID id = getFactionEvent.getId();
        Faction faction = this.getById(id);
        getFactionEvent.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    default void handleGetFactionByName(GetFactionByNameEvent getFactionEvent) {
        String name = getFactionEvent.getName();
        Faction faction = this.getByName(name);
        getFactionEvent.setFaction(faction);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    default void handleFactionExistsByName(CheckIfFactionExistsByNameEvent event) {
        String name = event.getName();
        boolean exists = this.existsByName(name);
        event.setExists(exists);
    }

    default Faction getFactionLess() {
        String id = "00000000-0000-0000-0000-000000000000";
        return this.getById(UUID.fromString(id));
    }

    default Faction claimForFactionLess(FChunk chunk) {
        Faction faction = this.getFactionLess();
        this.getDao().claimForFaction(faction, chunk, Bukkit.getOfflinePlayer("BrutalFiestas").getUniqueId());
        return faction;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    default void handleGetFactionAtChunk(GetFactionAtChunkEvent event) {
        FChunk chunk = event.getChunk();
        Faction faction = this.getDao().getFactionAtChunk(chunk);

        if (Objects.isNull(faction)) {
            faction = this.claimForFactionLess(chunk);
        }

        event.setFaction(faction);
    }


}
