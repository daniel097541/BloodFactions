package crypto.anguita.nextgenfactionscommons.model.faction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class FactionImpl implements Faction {
    private final UUID id;
    private final String name;
}
