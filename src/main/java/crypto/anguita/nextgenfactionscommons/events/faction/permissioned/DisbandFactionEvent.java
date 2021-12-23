package crypto.anguita.nextgenfactionscommons.events.faction.permissioned;

import crypto.anguita.nextgenfactionscommons.events.PermissionEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.permission.PermissionType;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class DisbandFactionEvent extends PermissionEvent {

    private boolean disbanded;

    public DisbandFactionEvent(@NotNull FPlayer player, @NotNull Faction faction) {
        super(faction, player, PermissionType.DISBAND);
        this.launch();
    }
}
