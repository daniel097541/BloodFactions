package crypto.anguita.nextgenfactions.backend.config.system;

import crypto.anguita.nextgenfactions.commons.config.ConfigType;
import crypto.anguita.nextgenfactions.commons.config.impl.NGFConfigImpl;
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
