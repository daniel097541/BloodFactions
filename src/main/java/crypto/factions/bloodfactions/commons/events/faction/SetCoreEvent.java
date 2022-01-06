package crypto.factions.bloodfactions.commons.events.faction;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCoreEvent extends PermissionEvent {

    private final FLocation core;
    private boolean success;


    public SetCoreEvent(Faction faction, FPlayer player, FLocation core) {
        super(faction, player, PermissionType.SET_CORE);
        this.core = core;
        this.launch();
    }
}
