package crypto.anguita.nextgenfactions;

import com.google.inject.Injector;
import crypto.anguita.nextgenfactions.commons.injection.NGFBinder;
import org.bukkit.plugin.java.JavaPlugin;

public class NextGenFactionsPlugin extends JavaPlugin {


    private static NextGenFactionsPlugin instance;

    private final NGFBinder binder = new NGFBinder(this);


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
        // Bind
        this.binder.configure();

        // Inject
        Injector injector = this.binder.createInjector();
        injector.injectMembers(this);
    }
}
