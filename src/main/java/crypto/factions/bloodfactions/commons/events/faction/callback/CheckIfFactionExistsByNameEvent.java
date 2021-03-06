package crypto.factions.bloodfactions.commons.events.faction.callback;

import crypto.factions.bloodfactions.commons.events.faction.FactionEvent;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class CheckIfFactionExistsByNameEvent extends FactionEvent {

    private final String name;
    private boolean exists;

    public CheckIfFactionExistsByNameEvent(@NotNull String name) {
        this.name = name;
        this.launch();
    }
}
