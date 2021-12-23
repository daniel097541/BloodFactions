package crypto.anguita.nextgenfactionscommons.model.permission.impl;

import crypto.anguita.nextgenfactionscommons.model.permission.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActionImpl implements Action {
    private final String name;
}
