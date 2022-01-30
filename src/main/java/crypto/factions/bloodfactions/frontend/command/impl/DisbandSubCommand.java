package crypto.factions.bloodfactions.frontend.command.impl;

import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.frontend.command.SubCommandType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DisbandSubCommand extends FSubCommandImpl {

    @Inject
    public DisbandSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.DISBAND, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {
        Faction faction = player.getFaction();
        faction.disband(player);
        return true;
    }
}
