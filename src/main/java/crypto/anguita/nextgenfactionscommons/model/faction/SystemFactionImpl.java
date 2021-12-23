package crypto.anguita.nextgenfactionscommons.model.faction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SystemFactionImpl implements Faction {
    private final UUID id;
    private final String name;
}
