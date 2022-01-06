package crypto.factions.bloodfactions.backend.config.lang;

import crypto.factions.bloodfactions.commons.config.ConfigItem;
import crypto.factions.bloodfactions.commons.config.impl.ConfigItemImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LangConfigItems {

    // Alerts broadcasting
    public static ConfigItem ALERT_PREFIX = new ConfigItemImpl("alerts.prefix", "&dALERT &7>> ");
    public static ConfigItem ALERT_OVER_CLAIMED = new ConfigItemImpl("alerts.over-claimed", "&c{faction_name} &eis over claiming you at: &7{chunk} &e!");

    // Actions messages
    public static ConfigItem ACTIONS_BREAK_NOT_YOUR_FACTION = new ConfigItemImpl("actions.block-break.not-your-faction", "&7This land is claimed by: &c{faction_name}&7, you cannot break blocks here!");

    // No permission for commands.
    public static ConfigItem COMMANDS_NO_PERMISSION = new ConfigItemImpl("commands.messages.no-permission", "&cYou dont have permission to perform this command.");

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


    // Un-Claim command
    public static ConfigItem COMMANDS_F_UN_CLAIM_NOT_YOUR_LAND = new ConfigItemImpl("commands.f-unclaim.not-your-land", "&cThis chunk is not claimed by your faction.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_NO_FACTION = new ConfigItemImpl("commands.f-unclaim.no-faction", "&cYou are not in a faction.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_SUCCESS = new ConfigItemImpl("commands.f-unclaim.success", "&aYou successfully un-claimed this chunk from &7{faction_name}.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_FAIL = new ConfigItemImpl("commands.f-unclaim.fail", "&cFailed to un-claim this chunk.");


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
