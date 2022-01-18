package crypto.factions.bloodfactions.commons.events.player.callback;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.invitation.FactionInvitation;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GetInvitationsOfPlayerEvent extends PlayerEvent {
    private Set<FactionInvitation> invitations;

    public GetInvitationsOfPlayerEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
