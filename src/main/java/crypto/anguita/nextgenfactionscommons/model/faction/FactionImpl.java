package crypto.anguita.nextgenfactionscommons.model.faction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FactionImpl implements Faction {
    private final UUID id;
    private final String name;
}
