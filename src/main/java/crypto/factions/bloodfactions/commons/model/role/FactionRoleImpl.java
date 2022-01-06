package crypto.factions.bloodfactions.commons.model.role;

import crypto.factions.bloodfactions.commons.annotation.db.ColumnName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FactionRoleImpl implements FactionRole {
    private final UUID id;

    @ColumnName("faction_id")
    private final UUID factionId;

    private String name;

    @ColumnName("default_role")
    private boolean defaultRole;
}
