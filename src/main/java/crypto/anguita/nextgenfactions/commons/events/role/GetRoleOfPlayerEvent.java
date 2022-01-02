package crypto.anguita.nextgenfactions.commons.events.role;

import crypto.anguita.nextgenfactions.commons.events.player.PlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
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
