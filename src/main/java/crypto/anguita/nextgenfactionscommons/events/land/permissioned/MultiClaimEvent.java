package crypto.anguita.nextgenfactionscommons.events.land.permissioned;

import crypto.anguita.nextgenfactionscommons.events.land.MultiLandEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
import crypto.anguita.nextgenfactionscommons.model.permission.PermissionType;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;

import java.util.Set;


public class MultiClaimEvent extends MultiLandEvent {
    public MultiClaimEvent(Faction faction, FPlayer player, Set<FChunk> chunks) {
        super(faction, player, PermissionType.MULTI_CLAIM, chunks);
        this.launch();
    }
}
