package crypto.anguita.nextgenfactions.commons.events.role;

import crypto.anguita.nextgenfactions.commons.events.PermissionEvent;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleOfPlayerEvent extends PermissionEvent {

    private final FactionRole role;
    private boolean changed;
    private final FPlayer playerChangingTheRole;
    private final FPlayer playerToBeChanged;

    public ChangeRoleOfPlayerEvent(FPlayer player, FactionRole role, FPlayer playerChangingTheRole) {
        super(player.getFaction(), playerChangingTheRole, PermissionType.CHANGE_ROLES);
        this.playerToBeChanged = player;
        this.role = role;
        this.playerChangingTheRole = playerChangingTheRole;
        this.launch();
    }
}
