package crypto.factions.bloodfactions.commons.command.impl;

import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class ShowSubCommand extends FSubCommandImpl {

    @Inject
    public ShowSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.SHOW, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {

        // Show own faction.
        if (args.length == 1) {
            boolean hasFaction = player.hasFaction();

            // Player is not in a faction.
            if (!hasFaction) {
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_SHOW_NO_FACTION);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                player.sms(messageContext);
                return false;
            }

            Faction faction = player.getFaction();
            player.showFaction(faction);
            return true;
        }

        // Show other.
        else {

            String factionName = args[1];
            Faction faction = ContextHandler.getFactionByName(factionName);
            if(Objects.nonNull(faction)){
                player.showFaction(faction);
                return true;
            }
            else{
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_SHOW_FACTION_DOES_NOT_EXIST);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                player.sms(messageContext);
            }
        }
        return false;
    }
}
