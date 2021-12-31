package crypto.anguita.nextgenfactions.backend.config.lang;

import crypto.anguita.nextgenfactions.commons.config.ConfigType;
import crypto.anguita.nextgenfactions.commons.config.impl.NGFConfigImpl;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LangConfig extends NGFConfigImpl {

    @Inject
    public LangConfig(JavaPlugin plugin) {
        super(plugin, LangConfigItems.asMap(), ConfigType.LANG);
        this.init();
    }
}