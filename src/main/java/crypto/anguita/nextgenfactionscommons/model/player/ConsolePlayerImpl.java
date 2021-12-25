package crypto.anguita.nextgenfactionscommons.model.player;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ConsolePlayerImpl implements FPlayer {
    private final UUID id;
    private final String name;
}
