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
public class DisbandFactionEvent extends PermissionEvent {

    private boolean disbanded;

    public DisbandFactionEvent(@NotNull FPlayer player, @NotNull Faction faction) {
        super(faction, player, PermissionType.DISBAND);
        this.launch();
    }
}
