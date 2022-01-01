package crypto.anguita.nextgenfactions.commons.command.impl;

import crypto.anguita.nextgenfactions.backend.config.lang.LangConfigItems;
import crypto.anguita.nextgenfactions.commons.annotation.config.LangConfiguration;
import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.command.SubCommandType;
import crypto.anguita.nextgenfactions.commons.config.NGFConfig;
import crypto.anguita.nextgenfactions.commons.messages.model.MessageContext;
import crypto.anguita.nextgenfactions.commons.messages.model.MessageContextImpl;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class DisbandSubCommand extends FSubCommandImpl {

    @Inject
    public DisbandSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.DISBAND, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {

        boolean hasFaction = player.hasFaction();

        if(!hasFaction){
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_DISBAND_NO_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }


        Faction faction = player.getFaction();
        boolean disbanded = faction.disband(player);

        if(disbanded) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_DISBAND_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return true;
        }
        else{
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_DISBAND_FAIL);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }
    }
}
