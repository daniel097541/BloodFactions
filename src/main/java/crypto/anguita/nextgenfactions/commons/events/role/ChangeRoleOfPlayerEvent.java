package crypto.anguita.nextgenfactions.commons.events.role;

import crypto.anguita.nextgenfactions.commons.events.player.PlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleOfPlayerEvent extends PlayerEvent {

    private final FactionRole role;
    private boolean changed;
    private final FPlayer playerChangingTheRole;

    public ChangeRoleOfPlayerEvent(FPlayer player, FactionRole role, FPlayer playerChangingTheRole) {
        super(player);
        this.role = role;
        this.playerChangingTheRole = playerChangingTheRole;
        this.launch();
    }
}
