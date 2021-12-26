package crypto.anguita.nextgenfactions.backend.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import crypto.anguita.nextgenfactions.backend.config.lang.LangConfig;
import crypto.anguita.nextgenfactions.backend.handler.CommandHandler;
import crypto.anguita.nextgenfactions.backend.handler.CommandHandlerImpl;
import crypto.anguita.nextgenfactions.backend.manager.DBManager;
import crypto.anguita.nextgenfactions.commons.annotation.command.CreateCommand;
import crypto.anguita.nextgenfactions.commons.annotation.config.LangConfiguration;
import crypto.anguita.nextgenfactions.commons.command.FCommand;
import crypto.anguita.nextgenfactions.commons.command.FSubCommand;
import crypto.anguita.nextgenfactions.commons.command.impl.CreateSubCommand;
import crypto.anguita.nextgenfactions.commons.command.impl.FCommandImpl;
import crypto.anguita.nextgenfactions.commons.config.NGFConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class NGFBinder extends AbstractModule {

    private final JavaPlugin plugin;

    public NGFBinder(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        // Bind plugin.
        this.bind(JavaPlugin.class).toInstance(this.plugin);

        // Bind configs.
        this.bind(NGFConfig.class).annotatedWith(LangConfiguration.class).to(LangConfig.class);

        // Bind commands.
        this.bind(FCommand.class).to(FCommandImpl.class);
        this.bind(FSubCommand.class).annotatedWith(CreateCommand.class).to(CreateSubCommand.class);

        // Bind handlers
        this.bind(CommandHandler.class).to(CommandHandlerImpl.class);

        // Bind db manager
        this.bind(DBManager.class).toInstance(new DBManager());
    }
}
