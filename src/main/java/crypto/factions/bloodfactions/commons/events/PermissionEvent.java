package crypto.factions.bloodfactions.commons.events;

import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class PermissionEvent extends NextGenFactionsEvent {
    private final Faction faction;
    private final FPlayer player;
    private final PermissionType permissionType;
    private boolean permissionRestricted;
    private boolean success;
    private String failureMessage;
}
