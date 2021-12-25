package crypto.anguita.nextgenfactionscommons.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import crypto.anguita.nextgenfactionscommons.annotation.command.CreateCommand;
import crypto.anguita.nextgenfactionscommons.annotation.config.LangConfiguration;
import crypto.anguita.nextgenfactionscommons.command.FCommand;
import crypto.anguita.nextgenfactionscommons.command.FSubCommand;
import crypto.anguita.nextgenfactionscommons.command.impl.CreateSubCommand;
import crypto.anguita.nextgenfactionscommons.command.impl.FCommandImpl;
import crypto.anguita.nextgenfactionscommons.config.NGFConfig;
import crypto.anguita.nextgenfactionscommons.config.impl.LangConfig;
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
    protected void configure() {
        // Bind plugin.
        this.bind(JavaPlugin.class).toInstance(this.plugin);

        // Bind configs.
        this.bind(NGFConfig.class).annotatedWith(LangConfiguration.class).to(LangConfig.class);

        // Bind commands.
        this.bind(FCommand.class).to(FCommandImpl.class);
        this.bind(FSubCommand.class).annotatedWith(CreateCommand.class).to(CreateSubCommand.class);
    }
}
