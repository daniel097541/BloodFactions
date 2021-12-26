package crypto.anguita.nextgenfactions.commons.events.faction.permissioned;

import crypto.anguita.nextgenfactions.commons.events.PermissionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class KickPlayerFromFactionEvent extends PermissionEvent {

    private final FPlayer kickedPlayer;
    private boolean kicked;

    public KickPlayerFromFactionEvent(@NotNull Faction faction, @NotNull FPlayer player, @NotNull FPlayer kickedPlayer) {
        super(faction, player, PermissionType.INVITE);
        this.kickedPlayer = kickedPlayer;
        this.launch();
    }
}
