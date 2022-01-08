package crypto.factions.bloodfactions.commons.events.role;

import crypto.factions.bloodfactions.commons.events.faction.FactionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class GetRolesOfFactionEvent extends FactionEvent {

    @Getter
    @Setter
    private Set<FactionRank> roles;

    public GetRolesOfFactionEvent(@NotNull Faction faction) {
        super(faction);
        this.launch();
    }
}
