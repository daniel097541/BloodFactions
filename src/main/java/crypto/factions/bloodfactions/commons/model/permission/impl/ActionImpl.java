package crypto.factions.bloodfactions.commons.model.permission.impl;

import crypto.factions.bloodfactions.commons.model.permission.Action;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActionImpl implements Action {
    private final PermissionType type;
}
