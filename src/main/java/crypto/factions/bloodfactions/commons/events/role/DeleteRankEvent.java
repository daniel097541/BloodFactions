package crypto.factions.bloodfactions.commons.events.role;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRankEvent extends PermissionEvent {

    private final String roleName;

    public DeleteRankEvent(Faction faction, FPlayer player, String roleName) {
        super(faction, player, PermissionType.DELETE_ROLE);
        this.roleName = roleName;
        this.launch();
    }
}
