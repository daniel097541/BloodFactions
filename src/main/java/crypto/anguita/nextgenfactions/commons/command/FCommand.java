package crypto.anguita.nextgenfactions.commons.command;

import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.model.player.ConsolePlayerImpl;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

public interface FCommand extends CommandExecutor {

    @NotNull Set<FSubCommand> getSubCommands();

    default void addSubCommand(@NotNull FSubCommand subCommand) {
        this.getSubCommands().add(subCommand);
        Bukkit.getConsoleSender().sendMessage("Sub command: " + subCommand.getSubCommandType().name() + " was registered successfully.");
    }

    default @Nullable FSubCommand getSubCommand(@NotNull String subCommandLabel) {
        SubCommandType subCommandType = SubCommandType.getByAlias(subCommandLabel);
        if (Objects.nonNull(subCommandType)) {
            return this.getSubCommands()
                    .stream()
                    .filter(fSubCommand -> fSubCommand.getSubCommandType().equals(subCommandType))
                    .findFirst()
                    .orElse(null); // Not found
        }

        // Not found
        return null;
    }

    @Override
    default boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        FPlayer player;

        // The sender is a player.
        if (sender instanceof Player) {
            player = NextGenFactionsAPI.getPlayer((Player) sender);
        }

        // The sender is the console.
        else {
            player = ConsolePlayerImpl.fromSender(sender);
        }

        // No args = help command
        if (args.length == 0) {
            args = new String[]{"help"};
        }

        // Run sub command if found.
        FSubCommand subCommand = this.getSubCommand(args[0]);
        if (Objects.nonNull(subCommand)) {
            return subCommand.run(args, player);
        }

        // Send not found message.
        String notFound = "";
        player.sms(notFound);

        // If not found, return false.
        return false;
    }
}
