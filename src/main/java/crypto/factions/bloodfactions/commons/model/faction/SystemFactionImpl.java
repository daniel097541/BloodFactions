package crypto.factions.bloodfactions.commons.model.faction;

import crypto.factions.bloodfactions.commons.annotation.db.ColumnName;
import crypto.factions.bloodfactions.commons.annotation.db.MemoryOnlyField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class SystemFactionImpl implements Faction {
    private final UUID id;

    private final String name;

    @MemoryOnlyField
    private final String color;

    @ColumnName("owner_id")
    private final UUID ownerId;

    @ColumnName("system_faction")
    private final boolean systemFaction = true;

    @Override
    public boolean isSystemFaction() {
        return systemFaction;
    }
}
