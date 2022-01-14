package crypto.factions.bloodfactions.commons.model.invitation;

import java.util.Date;
import java.util.UUID;

public interface FactionInvitation {

    UUID getFactionId();

    UUID getPlayerId();

    UUID getInviterId();

    Date getDate();
}
