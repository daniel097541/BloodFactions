package crypto.anguita.nextgenfactions.backend.config.system;

import crypto.anguita.nextgenfactions.commons.config.ConfigItem;
import crypto.anguita.nextgenfactions.commons.config.impl.ConfigItemImpl;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;

import java.util.*;

public class SystemConfigItems {

    public static String defaultFactionsPath = "default-factions";
    public static String factionLessPath = defaultFactionsPath + ".faction-less";
    public static String warZonePath = defaultFactionsPath + ".war-zone";
    public static String peaceZonePath = defaultFactionsPath + ".peace-zone";

    public static String systemFactionIdSection = ".id";
    public static String systemFactionNameSection = ".name";
    public static String systemFactionBlocksPvPSection = ".blocks-pvp";
    public static String systemFactionAllowFlightSection = ".allow-flight";
    public static String systemFactionColorSection = ".color";
    public static String systemFactionDefaultFaction = ".is-for-faction-less";


    public static ConfigItem DEFAULT_FACTIONS_FACTION_LESS_ID = new ConfigItemImpl(factionLessPath + systemFactionIdSection, "00000000-0000-0000-0000-000000000000");
    public static ConfigItem DEFAULT_FACTIONS_FACTION_LESS_NAME = new ConfigItemImpl(factionLessPath + systemFactionNameSection, "Wilderness");
    public static ConfigItem DEFAULT_FACTIONS_FACTION_LESS_PVP = new ConfigItemImpl(factionLessPath + systemFactionBlocksPvPSection, false);
    public static ConfigItem DEFAULT_FACTIONS_FACTION_LESS_ALLOW_FLY = new ConfigItemImpl(factionLessPath + systemFactionAllowFlightSection, false);
    public static ConfigItem DEFAULT_FACTIONS_FACTION_LESS_COLOR = new ConfigItemImpl(factionLessPath + systemFactionColorSection, "&7");
    public static ConfigItem DEFAULT_FACTIONS_FACTION_IS_FOR_FACTION_LESS = new ConfigItemImpl(factionLessPath + systemFactionDefaultFaction, true);

    public static ConfigItem DEFAULT_FACTIONS_WAR_ZONE_ID = new ConfigItemImpl(warZonePath + systemFactionIdSection, "00000000-0000-0000-0000-000000000001");
    public static ConfigItem DEFAULT_FACTIONS_WAR_ZONE_NAME = new ConfigItemImpl(warZonePath + ".name", "WarZone");
    public static ConfigItem DEFAULT_FACTIONS_WAR_ZONE_PVP = new ConfigItemImpl(warZonePath + ".blocks-pvp", false);
    public static ConfigItem DEFAULT_FACTIONS_WAR_ZONE_ALLOW_FLY = new ConfigItemImpl(warZonePath + ".allow-flight", false);
    public static ConfigItem DEFAULT_FACTIONS_WAR_ZONE_COLOR = new ConfigItemImpl(warZonePath + ".color", "&c");
    public static ConfigItem DEFAULT_FACTIONS_WAR_ZONE_IS_FOR_FACTION_LESS = new ConfigItemImpl(warZonePath + systemFactionDefaultFaction, false);

    public static ConfigItem DEFAULT_FACTIONS_PEACE_ZONE_ID = new ConfigItemImpl(peaceZonePath + systemFactionIdSection, "00000000-0000-0000-0000-000000000002");
    public static ConfigItem DEFAULT_FACTIONS_PEACE_ZONE_NAME = new ConfigItemImpl(peaceZonePath + ".name", "PeaceZone");
    public static ConfigItem DEFAULT_FACTIONS_PEACE_ZONE_PVP = new ConfigItemImpl(peaceZonePath + ".blocks-pvp", true);
    public static ConfigItem DEFAULT_FACTIONS_PEACE_ZONE_ALLOW_FLY = new ConfigItemImpl(peaceZonePath + ".allow-flight", true);
    public static ConfigItem DEFAULT_FACTIONS_PEACE_ZONE_COLOR = new ConfigItemImpl(peaceZonePath + ".color", "&d");
    public static ConfigItem DEFAULT_FACTIONS_PEACE_ZONE_IS_FOR_FACTION_LESS = new ConfigItemImpl(peaceZonePath + systemFactionDefaultFaction, false);


    public static String defaultRolesPath = "default-roles";
    public static String memberPath = defaultRolesPath + ".member";
    public static String modPath = defaultRolesPath + ".mod";
    public static String adminPath = defaultRolesPath + ".admin";

    public static String roleIdSection = ".id";
    public static String roleNameSection = ".name";
    public static String rolePermissionsSection = ".permissions";
    public static String roleDefaultSection = ".default-role";
    public static String roleChatSymbolSection = ".chat-symbol";

    public static String[] memberPermissions = new String[]{};
    public static String[] modPermissions = new String[]{
            PermissionType.CLAIM.name(),
            PermissionType.MULTI_CLAIM.name(),
            PermissionType.INVITE.name()
    };
    public static String[] adminPermissions = new String[]{PermissionType.ALL.name()};

    public static ConfigItem DEFAULT_ROLES_MEMBER_ID = new ConfigItemImpl( memberPath + roleIdSection, "00000000-0000-0000-0000-000000000000");
    public static ConfigItem DEFAULT_ROLES_MEMBER_NAME = new ConfigItemImpl( memberPath + roleNameSection, "Member");
    public static ConfigItem DEFAULT_ROLES_MEMBER_PERMISSIONS = new ConfigItemImpl( memberPath + rolePermissionsSection, memberPermissions);
    public static ConfigItem DEFAULT_ROLES_MEMBER_DEFAULT = new ConfigItemImpl( memberPath + roleDefaultSection, true);
    public static ConfigItem DEFAULT_ROLES_MEMBER_CHAT_SYMBOL = new ConfigItemImpl( memberPath + roleChatSymbolSection, "+");

    public static ConfigItem DEFAULT_ROLES_MOD_ID = new ConfigItemImpl( modPath + roleIdSection, "00000000-0000-0000-0000-000000000001");
    public static ConfigItem DEFAULT_ROLES_MOD_NAME = new ConfigItemImpl( modPath + roleNameSection, "Mod");
    public static ConfigItem DEFAULT_ROLES_MOD_PERMISSIONS = new ConfigItemImpl( modPath + rolePermissionsSection, modPermissions);
    public static ConfigItem DEFAULT_ROLES_MOD_DEFAULT = new ConfigItemImpl( modPath + roleDefaultSection, false);
    public static ConfigItem DEFAULT_ROLES_MOD_CHAT_SYMBOL = new ConfigItemImpl( modPath + roleChatSymbolSection, "*");

    public static ConfigItem DEFAULT_ROLES_ADMIN_ID = new ConfigItemImpl( adminPath + roleIdSection, "00000000-0000-0000-0000-000000000002");
    public static ConfigItem DEFAULT_ROLES_ADMIN_NAME = new ConfigItemImpl( adminPath + roleNameSection, "Admin");
    public static ConfigItem DEFAULT_ROLES_ADMIN_PERMISSIONS = new ConfigItemImpl( adminPath + rolePermissionsSection, adminPermissions);
    public static ConfigItem DEFAULT_ROLES_ADMIN_DEFAULT = new ConfigItemImpl( adminPath + roleDefaultSection, false);
    public static ConfigItem DEFAULT_ROLES_ADMIN_CHAT_SYMBOL = new ConfigItemImpl( adminPath + roleChatSymbolSection, "**");

    public static Map<String, ConfigItem> asMap() {
        Map<String, ConfigItem> items = new HashMap<>();
        Arrays.asList(SystemConfigItems.class.getDeclaredFields())
                .forEach(field -> {
                    try {
                        if (field.getType().equals(ConfigItem.class)) {
                            ConfigItem item = (ConfigItem) field.get(null);
                            items.put(item.getPath(), item);
                        }
                    } catch (IllegalAccessException ignored) {
                    }

                });

        return items;
    }

}
