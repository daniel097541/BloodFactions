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
public class DeInvitePlayerFromFactionEvent extends PermissionEvent {

    private final FPlayer deInvitedPlayer;
    private boolean deInvited;

    public DeInvitePlayerFromFactionEvent(@NotNull Faction faction, @NotNull FPlayer player, @NotNull FPlayer invited) {
        super(faction, player, PermissionType.INVITE);
        this.deInvitedPlayer = invited;
        this.launch();
    }
}
