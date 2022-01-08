package crypto.factions.bloodfactions.commons.events.role;

import crypto.factions.bloodfactions.commons.events.faction.FactionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import lombok.Getter;
import lombok.Setter;

public class GetDefaultRoleOfFactionEvent extends FactionEvent {

    @Getter
    @Setter
    private FactionRank defaultRole;

    public GetDefaultRoleOfFactionEvent(Faction faction) {
        super(faction);
        this.launch();
    }
}
