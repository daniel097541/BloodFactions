package crypto.factions.bloodfactions.commons.model.invitation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class FactionInvitationImpl implements FactionInvitation {

    private final UUID factionId;
    private final UUID playerId;
    private final UUID inviterId;
    private final Date date;

}
