package crypto.factions.bloodfactions.commons.events.role;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleOfPlayerEvent extends PermissionEvent {

    private final FactionRank role;
    private boolean changed;
    private final FPlayer playerChangingTheRole;
    private final FPlayer playerToBeChanged;

    public ChangeRoleOfPlayerEvent(FPlayer player, FactionRank role, FPlayer playerChangingTheRole) {
        super(player.getFaction(), playerChangingTheRole, PermissionType.CHANGE_ROLES);
        this.playerToBeChanged = player;
        this.role = role;
        this.playerChangingTheRole = playerChangingTheRole;
        this.launch();
    }
}
