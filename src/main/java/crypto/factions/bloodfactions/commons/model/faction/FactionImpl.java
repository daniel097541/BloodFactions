package crypto.factions.bloodfactions.commons.model.faction;

import crypto.factions.bloodfactions.commons.annotation.db.ColumnName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class FactionImpl implements Faction {
    private final UUID id;
    private final String name;

    @ColumnName("owner_id")
    private final UUID ownerId;

    @ColumnName("system_faction")
    private final boolean systemFaction = false;

    @Override
    public boolean isSystemFaction() {
        return systemFaction;
    }
}
