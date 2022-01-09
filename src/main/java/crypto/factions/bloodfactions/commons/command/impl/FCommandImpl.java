package crypto.factions.bloodfactions.commons.command.impl;

import crypto.factions.bloodfactions.commons.annotation.command.*;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.command.FCommand;
import crypto.factions.bloodfactions.commons.command.FSubCommand;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
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
    public FCommandImpl(JavaPlugin plugin,
                        @LangConfiguration NGFConfig langConfig,
                        @CreateCommand FSubCommand createSubCommand,
                        @DisbandCommand FSubCommand disbandSubCommand,
                        @ClaimCommand FSubCommand claimCommand,
                        @UnClaimCommand FSubCommand unClaimSubCommand,
                        @HomeCommand FSubCommand homeSubCommand,
                        @ShowCommand FSubCommand showSubCommand,
                        @FlyCommand FSubCommand flySubCommand,
                        @AutoFlyCommand FSubCommand autoFlySubCommand,
                        @RolesCommand FSubCommand rolesSubCommand,
                        @UnClaimAllCommand FSubCommand unClaimAllSubCommand
                        ) {

        this.langConfig = langConfig;
        PluginCommand pluginCommand = plugin.getCommand("f");
        if (Objects.nonNull(pluginCommand)) {
            pluginCommand.setExecutor(this);
            Bukkit.getConsoleSender().sendMessage("Factions command registered successfully.");
        } else {
            Bukkit.getConsoleSender().sendMessage("No f command registered.");
        }

        // Create sub command.
        this.addSubCommand(createSubCommand);
        this.addSubCommand(disbandSubCommand);
        this.addSubCommand(claimCommand);
        this.addSubCommand(unClaimSubCommand);
        this.addSubCommand(homeSubCommand);
        this.addSubCommand(showSubCommand);
        this.addSubCommand(flySubCommand);
        this.addSubCommand(autoFlySubCommand);
        this.addSubCommand(rolesSubCommand);
        this.addSubCommand(unClaimAllSubCommand);
    }
}
