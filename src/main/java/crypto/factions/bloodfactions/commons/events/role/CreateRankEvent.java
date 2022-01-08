package crypto.factions.bloodfactions.commons.events.role;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRankEvent extends PermissionEvent {

    private final String roleName;
    private FactionRank role;

    public CreateRankEvent(Faction faction, FPlayer player, String roleName) {
        super(faction, player, PermissionType.CREATE_ROLE);
        this.roleName = roleName;
        this.launch();
    }
}
