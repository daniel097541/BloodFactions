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
public class DisbandFactionEvent extends PermissionEvent {

    private boolean disbanded;

    public DisbandFactionEvent(@NotNull FPlayer player, @NotNull Faction faction) {
        super(faction, player, PermissionType.DISBAND);
        this.launch();
    }
}
