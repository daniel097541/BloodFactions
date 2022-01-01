package crypto.anguita.nextgenfactions.backend.handler.data;

import crypto.anguita.nextgenfactions.backend.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.CheckIfFactionExistsByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionAtChunkEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.callback.GetFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned.CreateFactionByNameEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned.CreateFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.shared.callback.GetFactionOfPlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.faction.FactionImpl;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.Objects;
import java.util.UUID;

public interface FactionsHandler extends DataHandler<Faction> {

    @Override
    FactionsDAO getDao();

    PlayerDAO getPlayerDAO();

    default Faction createFaction(Faction faction, FPlayer player) {
        faction = this.getDao().insert(faction);
        if(Objects.nonNull(faction)) {
            this.getPlayerDAO().addPlayerToFaction(player, faction, player);
        }
        return faction;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    default void handleCreateFaction(CreateFactionByNameEvent event) {
        String factionName = event.getName();
        FPlayer player = event.getPlayer();
        Faction faction = new FactionImpl(UUID.randomUUID(), factionName);
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

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    default void handleGetFactionAtChunk(GetFactionAtChunkEvent event) {
        FChunk chunk = event.getChunk();
        Faction faction = this.getDao().getFactionAtChunk(chunk);

        // If the faction is null, then return faction less.
        if (Objects.isNull(faction)) {
            faction = this.getFactionLess();
        }

        event.setFaction(faction);
    }


}
