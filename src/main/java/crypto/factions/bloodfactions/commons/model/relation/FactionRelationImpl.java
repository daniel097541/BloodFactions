package crypto.factions.bloodfactions.commons.model.relation;

import crypto.factions.bloodfactions.commons.annotation.db.ColumnName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class FactionRelationImpl implements FactionRelation {

    private final UUID id;
    private final String name;

    @ColumnName("color")
    private final String relationColor;

}
