package crypto.anguita.nextgenfactions.commons.model.permission.impl;

import crypto.anguita.nextgenfactions.commons.model.permission.Action;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActionImpl implements Action {
    private final PermissionType type;
}
