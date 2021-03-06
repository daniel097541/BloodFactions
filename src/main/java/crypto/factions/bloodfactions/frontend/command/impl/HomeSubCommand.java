package crypto.factions.bloodfactions.frontend.command.impl;

import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.frontend.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class HomeSubCommand extends FSubCommandImpl {

    @Inject
    public HomeSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.HOME, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {
        boolean hasFaction = player.hasFaction();

        // Player is not in a faction.
        if (!hasFaction) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_HOME_NO_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }


        Faction faction = player.getFaction();
        FLocation home = faction.getCore();

        if (args.length == 1) {

            if (Objects.nonNull(home)) {
                player.teleport(home);
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_HOME_SUCCESS);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                player.sms(messageContext);
                return true;
            } else {
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_HOME_NOT_SET);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                player.sms(messageContext);
                return false;
            }
        } else {
            String secondArg = args[1];

            if (secondArg.equalsIgnoreCase("set")) {
                FLocation location = player.getLocation();

                if (Objects.equals(Objects.requireNonNull(location).getFactionAt(), faction)){
                    faction.setCore(player, location);
                    return true;
                }

                // Not claimed
                else{
                    String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_HOME_NOT_YOUR_LAND);
                    MessageContext messageContext = new MessageContextImpl(player, successMessage);
                    player.sms(messageContext);
                    return false;
                }
            }
        }

        return false;
    }

}
