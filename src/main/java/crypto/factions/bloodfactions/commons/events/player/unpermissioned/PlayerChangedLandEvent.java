package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class PlayerChangedLandEvent extends PlayerEvent {

    private final Faction factionFrom;
    private final Faction factionTo;

    public PlayerChangedLandEvent(FPlayer player, Faction factionFrom, Faction factionTo) {
        super(player);
        this.factionFrom = factionFrom;
        this.factionTo = factionTo;
        this.launch();
    }
}
