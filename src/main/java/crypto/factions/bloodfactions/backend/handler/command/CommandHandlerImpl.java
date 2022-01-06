package crypto.factions.bloodfactions.backend.handler.command;

import crypto.factions.bloodfactions.commons.command.FCommand;
import lombok.Getter;
import org.bukkit.Bukkit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandHandlerImpl implements CommandHandler {

    @Getter
    private final FCommand command;

    @Inject
    public CommandHandlerImpl(FCommand command) {
        this.command = command;
        Bukkit.getConsoleSender().sendMessage("Command handler initialized.");
    }
}
