package crypto.anguita.nextgenfactions.commons.events.shared.callback;

import crypto.anguita.nextgenfactions.commons.events.shared.FactionPlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class GetFactionOfPlayerEvent extends FactionPlayerEvent {
    public GetFactionOfPlayerEvent(FPlayer player) {
        super(null, player);
        this.launch();
    }
}
