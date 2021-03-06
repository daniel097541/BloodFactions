package crypto.factions.bloodfactions.commons.events.faction.permissioned;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class InvitePlayerToFactionEvent extends PermissionEvent {

    private final FPlayer invitedPlayer;
    private boolean invited;

    public InvitePlayerToFactionEvent(@NotNull Faction faction, @NotNull FPlayer player, @NotNull FPlayer invited) {
        super(faction, player, PermissionType.INVITE);
        this.invitedPlayer = invited;
        this.launch();
    }
}
