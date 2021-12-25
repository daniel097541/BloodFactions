package crypto.anguita.nextgenfactionscommons.config;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public interface NGFConfig {

    ConfigType getConfigType();

    Map<String, ConfigItem> getItems();

    YamlConfiguration getYaml();

    File getFile();

    void setFile(File file);

    void setYaml(YamlConfiguration yaml);

    default Object get(ConfigItem configItem){
        return this.read(configItem.getPath());
    }

    @SneakyThrows
    default void init() {

        File file = new File(this.getConfigType().getPath());
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.load(file);

        // Sets file.
        this.setFile(file);

        // Sets yaml.
        this.setYaml(yamlConfiguration);

        // Loads defaults.
        this.loadDefaults();
    }

    default void write(String path, Object value) {
        this.getYaml().set(path, value);
    }

    default Object read(String path) {
        return this.getYaml().get(path);
    }

    default boolean exists(String path) {
        return this.getYaml().isConfigurationSection(path);
    }

    default void loadDefaults() {
        // Write all defaults if they don't exist.
        this.getItems()
                .values()
                .stream()
                .filter(configItem -> this.exists(configItem.getPath()))
                .forEach(configItem -> this.write(configItem.getPath(), configItem.getDefaultValue()));

        // Save after writing defaults.
        this.save();
    }

    @SneakyThrows
    default void save() {
        this.getYaml().save(this.getFile());
    }
}
