package crypto.anguita.nextgenfactions.backend.config.lang;

import crypto.anguita.nextgenfactions.commons.config.ConfigItem;
import crypto.anguita.nextgenfactions.commons.config.impl.ConfigItemImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LangConfigItems {


    public static ConfigItem COMMANDS_F_CREATE_FACTION_ALREADY_EXISTS = new ConfigItemImpl("commands.f-create.faction-already-exists", "&cThere is already a faction with that name.");
    public static ConfigItem COMMANDS_F_CREATE_FACTION_SUCCESS = new ConfigItemImpl("commands.f-create.success", "&7You successfully created a new faction called: &a{faction_name} &7!");
    public static ConfigItem COMMANDS_F_CREATE_FAIL = new ConfigItemImpl("commands.f-create.fail", "&cFaction creation failed.");
    public static ConfigItem COMMANDS_F_CREATE_PLAYER_ALREADY_HAS_FACTION = new ConfigItemImpl("commands.f-create.player-already-has-faction", "&cYou already have a faction!");

    public static ConfigItem COMMANDS_F_DISBAND_NO_FACTION = new ConfigItemImpl("commands.f-disband.no-faction", "&cYou are not in a faction.");
    public static ConfigItem COMMANDS_F_DISBAND_SUCCESS = new ConfigItemImpl("commands.f-disband.success", "&aFaction successfully disbanded.");
    public static ConfigItem COMMANDS_F_DISBAND_FAIL = new ConfigItemImpl("commands.f-disband.fail", "&cFailed to disband faction.");

    public static Map<String, ConfigItem> asMap() {
        Map<String, ConfigItem> items = new HashMap<>();
        Arrays.asList(LangConfigItems.class.getDeclaredFields())
                .forEach(field -> {

                    try {
                        ConfigItem item = (ConfigItem) field.get(null);
                        items.put(item.getPath(), item);
                    } catch (IllegalAccessException ignored) {
                    }

                });

        return items;
    }

}
