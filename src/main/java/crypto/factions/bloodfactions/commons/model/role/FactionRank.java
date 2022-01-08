package crypto.factions.bloodfactions.commons.model.role;

import crypto.factions.bloodfactions.commons.model.NextGenFactionEntity;

import java.util.UUID;

public interface FactionRank extends NextGenFactionEntity {

    boolean isDefaultRole();

    UUID getFactionId();

}
