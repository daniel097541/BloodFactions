package crypto.factions.bloodfactions.backend.handler.data.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.backend.handler.data.AlertsHandler;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
@Getter
public class AlertsHandlerImpl implements AlertsHandler {

    private final JavaPlugin plugin;
    private final NGFConfig langConfig;

    @Inject
    public AlertsHandlerImpl(JavaPlugin plugin, @LangConfiguration NGFConfig langConfig) {
        this.plugin = plugin;
        this.langConfig = langConfig;
        this.autoRegister();
    }
}
