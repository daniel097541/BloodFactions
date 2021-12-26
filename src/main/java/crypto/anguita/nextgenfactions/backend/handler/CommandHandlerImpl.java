package crypto.anguita.nextgenfactions.backend.handler;

import crypto.anguita.nextgenfactions.commons.command.FCommand;
import lombok.Getter;
import org.bukkit.Bukkit;

import javax.inject.Inject;

public class CommandHandlerImpl implements CommandHandler {

    @Getter
    private final FCommand command;

    @Inject
    public CommandHandlerImpl(FCommand command) {
        this.command = command;
        Bukkit.getConsoleSender().sendMessage("Command handler initialized.");
    }
}
