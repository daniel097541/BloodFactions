package crypto.factions.bloodfactions.commons.events.role;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRole;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class GetRoleOfPlayerEvent extends PlayerEvent {

    @Getter
    @Setter
    private FactionRole role;

    public GetRoleOfPlayerEvent(@NotNull FPlayer player) {
        super(player);
        this.launch();
    }

}
