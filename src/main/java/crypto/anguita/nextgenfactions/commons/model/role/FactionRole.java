package crypto.anguita.nextgenfactions.commons.model.role;

import crypto.anguita.nextgenfactions.commons.model.NextGenFactionEntity;

import java.util.UUID;

public interface FactionRole extends NextGenFactionEntity {

    boolean isDefaultRole();

    UUID getFactionId();

}
