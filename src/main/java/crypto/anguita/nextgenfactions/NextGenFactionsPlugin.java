package crypto.anguita.nextgenfactions;

import com.google.inject.Inject;
import com.google.inject.Injector;
import crypto.anguita.nextgenfactions.backend.handler.command.CommandHandler;
import crypto.anguita.nextgenfactions.backend.handler.data.FactionsHandler;
import crypto.anguita.nextgenfactions.backend.handler.data.PlayerHandler;
import crypto.anguita.nextgenfactions.frontend.listener.PlayerListener;
import crypto.anguita.nextgenfactions.injection.NGFBinder;
import org.bukkit.plugin.java.JavaPlugin;

public class NextGenFactionsPlugin extends JavaPlugin {

    private static NextGenFactionsPlugin instance;

    private final NGFBinder binder = new NGFBinder(this);

    @Inject
    private CommandHandler commandHandler;

    @Inject
    private FactionsHandler factionsHandler;

    @Inject
    private PlayerHandler playerHandler;

    @Inject
    private PlayerListener playerListener;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        instance = this;

        this.inject();
    }

    private void inject() {
        // Inject
        Injector injector = this.binder.createInjector();
        injector.injectMembers(this);
    }
}
