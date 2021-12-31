package crypto.anguita.nextgenfactions.commons.model.faction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SystemFactionImpl implements Faction {
    private final UUID id;
    private final String name;

    @Override
    public boolean isSystemFaction() {
        return true;
    }
}
