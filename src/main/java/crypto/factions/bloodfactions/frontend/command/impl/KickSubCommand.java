package crypto.factions.bloodfactions.frontend.command.impl;

import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.frontend.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class KickSubCommand extends FSubCommandImpl {

    @Inject
    public KickSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.KICK, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {
        boolean hasFaction = player.hasFaction();

        // Player is not in a faction.
        if (!hasFaction) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_KICK_NO_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        Faction faction = player.getFaction();

        if(args.length >= 2){
            String playerName = args[1];
            FPlayer kicked = ContextHandler.getPlayerByName(playerName);

            if(Objects.nonNull(kicked)) {
                faction.kickPlayer(kicked, player);
            }
            else{
                String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_KICK_NO_PLAYER);
                MessageContext messageContext = new MessageContextImpl(player, message);
                player.sms(messageContext);
            }
        }


        return true;
    }
}
