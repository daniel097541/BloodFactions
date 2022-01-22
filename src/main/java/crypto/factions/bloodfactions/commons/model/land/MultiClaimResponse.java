package crypto.factions.bloodfactions.commons.model.land;

import crypto.factions.bloodfactions.commons.model.faction.Faction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class MultiClaimResponse {

    private final Map<String, Faction> removedChunks;
    private final Map<String, Faction> claimedChunks;

}
