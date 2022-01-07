package crypto.factions.bloodfactions.commons.events.land.callback;

import crypto.factions.bloodfactions.commons.events.faction.FactionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class GetNumberOfClaimsEvent extends FactionEvent {
    private int numberOfClaims;

    public GetNumberOfClaimsEvent(@NotNull Faction faction) {
        super(faction);
        this.launch();
    }
}
