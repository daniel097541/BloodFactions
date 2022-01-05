package crypto.anguita.nextgenfactions.backend.config.lang;

import crypto.anguita.nextgenfactions.commons.config.ConfigItem;
import crypto.anguita.nextgenfactions.commons.config.impl.ConfigItemImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LangConfigItems {

    // Create command
    public static ConfigItem COMMANDS_F_CREATE_FACTION_ALREADY_EXISTS = new ConfigItemImpl("commands.f-create.faction-already-exists", "&cThere is already a faction with that name.");
    public static ConfigItem COMMANDS_F_CREATE_FACTION_SUCCESS = new ConfigItemImpl("commands.f-create.success", "&7You successfully created a new faction called: &a{faction_name} &7!");
    public static ConfigItem COMMANDS_F_CREATE_FAIL = new ConfigItemImpl("commands.f-create.fail", "&cFaction creation failed.");
    public static ConfigItem COMMANDS_F_CREATE_PLAYER_ALREADY_HAS_FACTION = new ConfigItemImpl("commands.f-create.player-already-has-faction", "&cYou already have a faction!");

    // Disband command
    public static ConfigItem COMMANDS_F_DISBAND_NO_FACTION = new ConfigItemImpl("commands.f-disband.no-faction", "&cYou are not in a faction.");
    public static ConfigItem COMMANDS_F_DISBAND_SUCCESS = new ConfigItemImpl("commands.f-disband.success", "&aFaction successfully disbanded.");
    public static ConfigItem COMMANDS_F_DISBAND_FAIL = new ConfigItemImpl("commands.f-disband.fail", "&cFailed to disband faction.");

    // Claim command
    public static ConfigItem COMMANDS_F_CLAIM_ALREADY_OWNED = new ConfigItemImpl("commands.f-claim.already-owned", "&7Your faction already owns this land.");
    public static ConfigItem COMMANDS_F_CLAIM_NO_FACTION = new ConfigItemImpl("commands.f-claim.no-faction", "&cYou are not in a faction.");
    public static ConfigItem COMMANDS_F_CLAIM_NOT_ENOUGH_POWER = new ConfigItemImpl("commands.f-claim.not-enough-power", "&cYour faction has not enough power to claim this land.");
    public static ConfigItem COMMANDS_F_CLAIM_FACTION_IS_STRONG_TO_KEEP = new ConfigItemImpl("commands.f-claim.faction-is-strong-to-keep", "&7The faction &c{faction_name} &7is strong enough to keep this land.");
    public static ConfigItem COMMANDS_F_CLAIM_SUCCESS = new ConfigItemImpl("commands.f-claim.success", "&aYou successfully claimed this chunk from &7{faction_name}.");
    public static ConfigItem COMMANDS_F_CLAIM_FAIL = new ConfigItemImpl("commands.f-claim.fail", "&cFailed to claim this chunk.");


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
