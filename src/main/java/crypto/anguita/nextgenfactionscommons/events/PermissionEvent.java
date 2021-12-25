package crypto.anguita.nextgenfactionscommons.events;

import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.permission.PermissionType;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
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
    private String noPermissionMessage;
}
