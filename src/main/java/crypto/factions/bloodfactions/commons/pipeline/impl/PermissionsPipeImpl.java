package crypto.factions.bloodfactions.commons.pipeline.impl;

import crypto.factions.bloodfactions.commons.model.permission.Action;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.pipeline.PermissionsPipe;
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
