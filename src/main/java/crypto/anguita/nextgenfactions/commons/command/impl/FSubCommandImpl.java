package crypto.anguita.nextgenfactions.commons.command.impl;

import crypto.anguita.nextgenfactions.commons.command.FSubCommand;
import crypto.anguita.nextgenfactions.commons.command.SubCommandType;
import crypto.anguita.nextgenfactions.commons.config.NGFConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class FSubCommandImpl implements FSubCommand {
    private final SubCommandType subCommandType;
    private final NGFConfig langConfig;
}
