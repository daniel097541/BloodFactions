package crypto.anguita.nextgenfactionscommons.command.impl;

import crypto.anguita.nextgenfactionscommons.command.FCommand;
import crypto.anguita.nextgenfactionscommons.command.FSubCommand;
import crypto.anguita.nextgenfactionscommons.config.NGFConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Singleton
public class FCommandImpl implements FCommand {

    private final Set<FSubCommand> subCommands = new HashSet<>();
    private final NGFConfig langConfig;

    @Inject
    public FCommandImpl(JavaPlugin plugin, NGFConfig langConfig) {
        this.langConfig = langConfig;
        PluginCommand pluginCommand = plugin.getCommand("f");
        if (Objects.nonNull(pluginCommand)) {
            pluginCommand.setExecutor(this);
            Bukkit.getConsoleSender().sendMessage("Factions command registered successfully.");
        } else {
            Bukkit.getConsoleSender().sendMessage("No f command registered.");
        }

        // Create sub command.
        this.addSubCommand(new CreateSubCommand(langConfig));

    }
}
