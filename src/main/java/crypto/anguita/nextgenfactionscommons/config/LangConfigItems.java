package crypto.anguita.nextgenfactionscommons.config;

import crypto.anguita.nextgenfactionscommons.config.impl.ConfigItemImpl;

public class LangConfigItems {


    public static ConfigItem COMMANDS_F_CREATE_FACTION_ALREADY_EXISTS = new ConfigItemImpl("commands.f-create.faction-already-exists", "&cThere is already a faction with that name.");
    public static ConfigItem COMMANDS_F_CREATE_FACTION_SUCCESS = new ConfigItemImpl("commands.f-create.success", "&7You successfully created a new faction called: &a{faction_name} &7!");
    public static ConfigItem COMMANDS_F_CREATE_FAIL = new ConfigItemImpl("commands.f-create.fail", "&cFaction creation failed.");


}
