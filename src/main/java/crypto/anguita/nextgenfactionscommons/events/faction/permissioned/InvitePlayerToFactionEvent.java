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
public class InvitePlayerToFactionEvent extends PermissionEvent {

    private final FPlayer invitedPlayer;
    private boolean invited;

    public InvitePlayerToFactionEvent(@NotNull Faction faction, @NotNull FPlayer player, @NotNull FPlayer invited) {
        super(faction, player, PermissionType.INVITE);
        this.invitedPlayer = invited;
        this.launch();
    }
}
