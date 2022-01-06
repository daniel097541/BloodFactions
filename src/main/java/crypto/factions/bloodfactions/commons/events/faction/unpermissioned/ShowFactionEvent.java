package crypto.factions.bloodfactions.commons.events.faction.unpermissioned;

import crypto.factions.bloodfactions.commons.events.shared.FactionPlayerEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import org.jetbrains.annotations.NotNull;

public class ShowFactionEvent extends FactionPlayerEvent {
    public ShowFactionEvent(@NotNull Faction faction, @NotNull FPlayer player) {
        super(faction, player);
        this.launch();
    }
}
