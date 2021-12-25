package crypto.anguita.nextgenfactionscommons.pipeline.impl;

import crypto.anguita.nextgenfactionscommons.model.permission.Action;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import crypto.anguita.nextgenfactionscommons.pipeline.PermissionsPipe;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class PermissionsPipeImpl implements PermissionsPipe {
    private final FPlayer player;
    private final Action action;
    private final String noPermissionMessage;
    private boolean success;
    private boolean noPermission;
}
