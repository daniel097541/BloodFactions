package crypto.factions.bloodfactions.commons.model.player;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class FPlayerImpl implements FPlayer {
    private final UUID id;
    private final String name;

    @Setter
    private boolean flying;

    @Setter
    private int power;

    public static FPlayer fromPlayer(Player player) {
        return new FPlayerImpl(player.getUniqueId(), player.getName(), false, 0);
    }
}
