package crypto.anguita.nextgenfactionscommons.config.impl;

import crypto.anguita.nextgenfactionscommons.config.ConfigItem;
import crypto.anguita.nextgenfactionscommons.config.ConfigType;
import crypto.anguita.nextgenfactionscommons.config.NGFConfig;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class NGFConfigImpl implements NGFConfig {
    private final Map<String, ConfigItem> items;
    private final ConfigType configType;
    private File file;
    private YamlConfiguration yaml;
}
