package crypto.factions.bloodfactions.backend.config.lang;

import crypto.factions.bloodfactions.commons.config.ConfigItem;
import crypto.factions.bloodfactions.commons.config.ConfigType;
import crypto.factions.bloodfactions.commons.config.impl.NGFConfigImpl;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class LangConfig extends NGFConfigImpl {

    @Getter
    public static LangConfig instance;

    @Inject
    public LangConfig(JavaPlugin plugin) {
        super(plugin, LangConfigItems.asMap(), ConfigType.LANG);
        this.init();
        instance = this;
    }
}
