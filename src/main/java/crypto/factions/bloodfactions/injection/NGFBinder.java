package crypto.factions.bloodfactions.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import crypto.factions.bloodfactions.backend.config.lang.LangConfig;
import crypto.factions.bloodfactions.backend.config.system.SystemConfig;
import crypto.factions.bloodfactions.backend.dao.FactionsDAO;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.dao.impl.FactionsDAOImpl;
import crypto.factions.bloodfactions.backend.dao.impl.PlayerDAOImpl;
import crypto.factions.bloodfactions.backend.dao.impl.RolesDAOImpl;
import crypto.factions.bloodfactions.backend.handler.command.CommandHandler;
import crypto.factions.bloodfactions.backend.handler.command.CommandHandlerImpl;
import crypto.factions.bloodfactions.backend.handler.data.AlertsHandler;
import crypto.factions.bloodfactions.backend.handler.data.FactionsHandler;
import crypto.factions.bloodfactions.backend.handler.data.PlayerHandler;
import crypto.factions.bloodfactions.backend.handler.data.impl.AlertsHandlerImpl;
import crypto.factions.bloodfactions.backend.handler.data.impl.FactionsHandlerImpl;
import crypto.factions.bloodfactions.backend.handler.data.impl.PlayerHandlerImpl;
import crypto.factions.bloodfactions.backend.handler.permissions.PermissionsHandler;
import crypto.factions.bloodfactions.backend.handler.permissions.PermissionsHandlerImpl;
import crypto.factions.bloodfactions.backend.manager.DBManager;
import crypto.factions.bloodfactions.backend.manager.DBMigrationManager;
import crypto.factions.bloodfactions.backend.manager.DBMigrationManagerImpl;
import crypto.factions.bloodfactions.commons.annotation.command.*;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.annotation.config.SystemConfiguration;
import crypto.factions.bloodfactions.commons.command.FCommand;
import crypto.factions.bloodfactions.commons.command.FSubCommand;
import crypto.factions.bloodfactions.commons.command.impl.*;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.tasks.handler.TasksHandler;
import crypto.factions.bloodfactions.commons.tasks.handler.TasksHandlerImpl;
import crypto.factions.bloodfactions.commons.tasks.manager.TaskManager;
import crypto.factions.bloodfactions.commons.tasks.manager.TaskManagerImpl;
import crypto.factions.bloodfactions.frontend.listener.PlayerListener;
import crypto.factions.bloodfactions.frontend.listener.PlayerListenerImpl;
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
        this.bind(NGFConfig.class).annotatedWith(SystemConfiguration.class).to(SystemConfig.class);

        // Bind commands.
        this.bind(FCommand.class).to(FCommandImpl.class);
        this.bind(FSubCommand.class).annotatedWith(CreateCommand.class).to(CreateSubCommand.class);
        this.bind(FSubCommand.class).annotatedWith(DisbandCommand.class).to(DisbandSubCommand.class);
        this.bind(FSubCommand.class).annotatedWith(ClaimCommand.class).to(ClaimSubCommand.class);
        this.bind(FSubCommand.class).annotatedWith(UnClaimCommand.class).to(UnClaimSubCommand.class);
        this.bind(FSubCommand.class).annotatedWith(HomeCommand.class).to(HomeSubCommand.class);
        this.bind(FSubCommand.class).annotatedWith(ShowCommand.class).to(ShowSubCommand.class);
        this.bind(FSubCommand.class).annotatedWith(FlyCommand.class).to(FlySubCommand.class);

        // Bind db manager
        this.bind(DBManager.class).toInstance(new DBManager());
        this.bind(DBMigrationManager.class).to(DBMigrationManagerImpl.class);

        // Bind DAOs
        this.bind(FactionsDAO.class).to(FactionsDAOImpl.class);
        this.bind(PlayerDAO.class).to(PlayerDAOImpl.class);
        this.bind(RolesDAO.class).to(RolesDAOImpl.class);

        // Tasks
        this.bind(TaskManager.class).to(TaskManagerImpl.class);
        this.bind(TasksHandler.class).to(TasksHandlerImpl.class);

        // Bind frontend listeners
        this.bind(PlayerListener.class).to(PlayerListenerImpl.class);

        // Bind handlers
        this.bind(CommandHandler.class).to(CommandHandlerImpl.class);
        this.bind(FactionsHandler.class).to(FactionsHandlerImpl.class);
        this.bind(PlayerHandler.class).to(PlayerHandlerImpl.class);
        this.bind(PermissionsHandler.class).to(PermissionsHandlerImpl.class);
        this.bind(AlertsHandler.class).to(AlertsHandlerImpl.class);

    }
}
