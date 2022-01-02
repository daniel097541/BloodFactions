package crypto.anguita.nextgenfactions.commons.events.role;

import crypto.anguita.nextgenfactions.commons.events.faction.FactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class GetRolesOfFactionEvent extends FactionEvent {

    @Getter
    @Setter
    private Set<FactionRole> roles;

    public GetRolesOfFactionEvent(@NotNull Faction faction) {
        super(faction);
        this.launch();
    }
}
