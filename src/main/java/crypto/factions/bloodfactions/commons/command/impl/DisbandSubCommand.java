package crypto.factions.bloodfactions.commons.command.impl;

import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

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

        boolean hasFaction = player.hasFaction();

        if (!hasFaction) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_DISBAND_NO_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        Faction faction = player.getFaction();
        return faction.disband(player);
    }
}
