package crypto.anguita.nextgenfactionscommons.model.permission.impl;

import crypto.anguita.nextgenfactionscommons.model.permission.Action;
import crypto.anguita.nextgenfactionscommons.model.permission.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PermissionImpl implements Permission {
    private final Action action;
    private final boolean allowed;
}
