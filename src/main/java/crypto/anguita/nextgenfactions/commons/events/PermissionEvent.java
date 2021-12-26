package crypto.anguita.nextgenfactions.commons.events;

import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
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
