package crypto.factions.bloodfactions.commons.command.impl;

import crypto.factions.bloodfactions.commons.command.FSubCommand;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class FSubCommandImpl implements FSubCommand {
    private final SubCommandType subCommandType;
    private final NGFConfig langConfig;
}
