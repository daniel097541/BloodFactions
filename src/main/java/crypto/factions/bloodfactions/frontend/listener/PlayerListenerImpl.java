package crypto.factions.bloodfactions.frontend.listener;

import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Getter
public class PlayerListenerImpl implements PlayerListener {

    private final JavaPlugin plugin;
    private final NGFConfig langConfig;

    @Inject
    public PlayerListenerImpl(JavaPlugin plugin, @LangConfiguration NGFConfig langConfig) {
        this.plugin = plugin;
        this.langConfig = langConfig;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }
}
