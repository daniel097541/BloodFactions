package crypto.anguita.nextgenfactions.commons.model.player;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class FPlayerImpl implements FPlayer {
    private final UUID id;
    private final String name;

    public static FPlayer fromPlayer(Player player) {
        return new FPlayerImpl(player.getUniqueId(), player.getName());
    }
}
