package crypto.factions.bloodfactions.commons.config;

import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public interface NGFConfig {

    JavaPlugin getPlugin();

    ConfigType getConfigType();

    Map<String, ConfigItem> getItems();

    YamlConfiguration getYaml();

    File getFile();

    void setFile(File file);

    void setYaml(YamlConfiguration yaml);

    default Object get(ConfigItem configItem) {
        return this.read(configItem.getPath());
    }

    @SneakyThrows
    default void init() {
        String folder = this.getPlugin().getDataFolder().getPath();

        File file = new File(folder + "/" + this.getConfigType().getPath());
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        // Sets file.
        this.setFile(file);

        // Sets yaml.
        this.setYaml(yamlConfiguration);

        // Loads defaults.
        this.loadDefaults();
    }

    default @NotNull Set<String> getKeys(@NotNull String configSection) {
        ConfigurationSection cs = this.getYaml().getConfigurationSection(configSection);
        if (Objects.nonNull(cs)) {
            return cs.getKeys(false);
        } else {
            return new HashSet<>();
        }
    }

    default @NotNull Map<String, Object> getChildren(@NotNull String configurationSection) {
        ConfigurationSection cs = this.getYaml().getConfigurationSection(configurationSection);
        if (Objects.nonNull(cs)) {
            return cs.getValues(true);
        } else {
            return new HashMap<>();
        }
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
                .filter(configItem -> !this.exists(configItem.getPath()))
                .forEach(configItem -> this.write(configItem.getPath(), configItem.getDefaultValue()));

        // Save after writing defaults.
        this.save();
    }

    @SneakyThrows
    default void save() {
        this.getYaml().save(this.getFile());
    }
}
