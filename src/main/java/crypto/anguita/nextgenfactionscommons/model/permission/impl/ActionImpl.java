package crypto.anguita.nextgenfactionscommons.model.permission.impl;

import crypto.anguita.nextgenfactionscommons.model.permission.Action;
import crypto.anguita.nextgenfactionscommons.model.permission.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActionImpl implements Action {
    private final PermissionType type;
}
