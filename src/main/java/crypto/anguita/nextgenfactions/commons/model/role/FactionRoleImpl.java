package crypto.anguita.nextgenfactions.commons.model.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FactionRoleImpl implements FactionRole {
    private final UUID id;
    private String name;
    private boolean defaultRole;
}
