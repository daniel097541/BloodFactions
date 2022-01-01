package crypto.anguita.nextgenfactions.backend.handler.data.impl;

import crypto.anguita.nextgenfactions.backend.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.backend.handler.data.FactionsHandler;
import crypto.anguita.nextgenfactions.commons.model.faction.SystemFactionImpl;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
@Getter
public class FactionsHandlerImpl implements FactionsHandler {

    private final FactionsDAO dao;
    private final PlayerDAO playerDAO;
    private final JavaPlugin plugin;

    @Inject
    public FactionsHandlerImpl(JavaPlugin plugin, FactionsDAO factionsDAO, PlayerDAO playerDAO) {
        this.dao = factionsDAO;
        this.plugin = plugin;
        this.playerDAO = playerDAO;
        this.autoRegister();
        this.onLoad();
    }

    private void onLoad() {

        UUID factionLessId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        String factionLessName = "Wilderness";
        UUID warZoneId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        String warZoneName = "WarZone";
        UUID peaceZoneId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        String peaceZoneName = "PeaceZone";

        if (!dao.existsById(factionLessId)) {
            this.dao.insert(new SystemFactionImpl(factionLessId, factionLessName));
        }
        if(!dao.existsById(warZoneId)) {
            this.dao.insert(new SystemFactionImpl(warZoneId, warZoneName));
        }
        if(!dao.existsById(peaceZoneId)) {
            this.dao.insert(new SystemFactionImpl(peaceZoneId, peaceZoneName));
        }

    }

}
