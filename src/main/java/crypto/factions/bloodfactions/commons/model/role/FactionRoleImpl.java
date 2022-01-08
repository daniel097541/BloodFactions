package crypto.factions.bloodfactions.commons.model.role;

import crypto.factions.bloodfactions.commons.annotation.db.ColumnName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class FactionRoleImpl implements FactionRank {
    private final UUID id;

    @ColumnName("faction_id")
    private final UUID factionId;

    private String name;

    @ColumnName("default_role")
    private boolean defaultRole;
}
