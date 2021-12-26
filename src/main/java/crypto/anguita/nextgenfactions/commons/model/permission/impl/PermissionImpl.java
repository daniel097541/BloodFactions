package crypto.anguita.nextgenfactions.commons.model.permission.impl;

import crypto.anguita.nextgenfactions.commons.model.permission.Action;
import crypto.anguita.nextgenfactions.commons.model.permission.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PermissionImpl implements Permission {
    private final Action action;
    private final boolean allowed;
}
