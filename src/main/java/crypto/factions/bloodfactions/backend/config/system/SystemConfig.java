package crypto.factions.bloodfactions.backend.config.system;

import crypto.factions.bloodfactions.commons.config.ConfigType;
import crypto.factions.bloodfactions.commons.config.impl.NGFConfigImpl;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SystemConfig extends NGFConfigImpl {

    @Inject
    public SystemConfig(JavaPlugin plugin) {
        super(plugin, SystemConfigItems.asMap(), ConfigType.SYSTEM);
        this.init();
    }
}
