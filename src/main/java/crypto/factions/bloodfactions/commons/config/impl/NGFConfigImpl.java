package crypto.factions.bloodfactions.commons.config.impl;

import crypto.factions.bloodfactions.commons.config.ConfigItem;
import crypto.factions.bloodfactions.commons.config.ConfigType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class NGFConfigImpl implements NGFConfig {
    private final JavaPlugin plugin;
    private final Map<String, ConfigItem> items;
    private final ConfigType configType;
    private File file;
    private YamlConfiguration yaml;
}
