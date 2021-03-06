package crypto.factions.bloodfactions.commons.model.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ConsolePlayerImpl implements FPlayer {
    private final UUID id = UUID.fromString("cd2dce3d-dcae-4800-b7a8-d1aa57a0b4ba");
    private final String name;

    @Setter
    private boolean flying = false;

    @Setter
    private boolean autoFlying = false;


    public static FPlayer fromSender(CommandSender sender) {
        return new ConsolePlayerImpl(sender.getName());
    }

    @Override
    public int getPower() {
        return 0;
    }

    @Override
    public boolean hasBukkitPermission(String bukkitPermission) {
        // Console has all permissions.
        return true;
    }

    @Override
    public void setPower(int power) {

    }
}
