package crypto.anguita.nextgenfactions.commons.events.role;

import crypto.anguita.nextgenfactions.commons.events.faction.FactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
import lombok.Getter;
import lombok.Setter;

public class GetDefaultRoleOfFactionEvent extends FactionEvent {

    @Getter
    @Setter
    private FactionRole defaultRole;

    public GetDefaultRoleOfFactionEvent(Faction faction) {
        super(faction);
        this.launch();
    }
}
