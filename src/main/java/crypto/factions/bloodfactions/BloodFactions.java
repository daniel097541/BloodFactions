package crypto.factions.bloodfactions;

import com.google.inject.Inject;
import com.google.inject.Injector;
import crypto.factions.bloodfactions.backend.handler.command.CommandHandler;
import crypto.factions.bloodfactions.backend.handler.data.FactionsHandler;
import crypto.factions.bloodfactions.backend.handler.data.PlayerHandler;
import crypto.factions.bloodfactions.backend.handler.permissions.PermissionsHandler;
import crypto.factions.bloodfactions.backend.db.DBMigrationManager;
import crypto.factions.bloodfactions.frontend.listener.PlayerListener;
import crypto.factions.bloodfactions.injection.NGFBinder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class BloodFactions extends JavaPlugin {

    @Getter
    private static BloodFactions instance;

    private final NGFBinder binder = new NGFBinder(this);

    @Inject
    private DBMigrationManager dbMigrationManager;

    @Inject
    private CommandHandler commandHandler;

    @Inject
    private FactionsHandler factionsHandler;

    @Inject
    private PermissionsHandler permissionsHandler;

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
