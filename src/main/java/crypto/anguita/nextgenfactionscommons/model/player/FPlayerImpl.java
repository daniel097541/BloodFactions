package crypto.anguita.nextgenfactionscommons.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FPlayerImpl implements FPlayer {
    private final UUID id;
    private final String name;
}
