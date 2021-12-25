package crypto.anguita.nextgenfactionscommons.command.impl;

import crypto.anguita.nextgenfactionscommons.command.FSubCommand;
import crypto.anguita.nextgenfactionscommons.command.SubCommandType;
import crypto.anguita.nextgenfactionscommons.config.NGFConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class FSubCommandImpl implements FSubCommand {
    private final SubCommandType subCommandType;
    private final NGFConfig langConfig;
}
