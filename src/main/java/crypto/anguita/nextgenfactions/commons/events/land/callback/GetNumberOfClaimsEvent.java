package crypto.anguita.nextgenfactions.commons.events.land.callback;

import crypto.anguita.nextgenfactions.commons.events.faction.FactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class GetNumberOfClaimsEvent extends FactionEvent {
    private int numberOfClaims;

    public GetNumberOfClaimsEvent(@NotNull Faction faction) {
        super(faction);
    }
}
