package crypto.factions.bloodfactions.commons.events.role;

import crypto.factions.bloodfactions.commons.events.shared.FactionPlayerEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class ListRolesEvent extends FactionPlayerEvent {
    public ListRolesEvent(Faction faction, FPlayer player) {
        super(faction, player);
        this.launch();
    }
}
