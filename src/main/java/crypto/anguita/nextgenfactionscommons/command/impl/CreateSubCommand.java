package crypto.anguita.nextgenfactionscommons.command.impl;

import crypto.anguita.nextgenfactionscommons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactionscommons.command.SubCommandType;
import crypto.anguita.nextgenfactionscommons.config.LangConfigItems;
import crypto.anguita.nextgenfactionscommons.config.NGFConfig;
import crypto.anguita.nextgenfactionscommons.messages.model.MessageContext;
import crypto.anguita.nextgenfactionscommons.messages.model.MessageContextImpl;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;

import java.util.Objects;

public class CreateSubCommand extends FSubCommandImpl {

    public CreateSubCommand(NGFConfig langConfig) {
        super(SubCommandType.CREATE, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {
        String name = args[1];

        boolean exists = NextGenFactionsAPI.checkIfFactionExistsByName(name);

        // No faction with name.
        if (!exists) {
            Faction faction = NextGenFactionsAPI.createFaction(name);

            // Faction created successfully.
            if (Objects.nonNull(faction)) {
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CREATE_FACTION_SUCCESS);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                messageContext.setFaction(faction);
                player.sms(messageContext);
                return true;
            }

            // Creation failed.
            else {
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CREATE_FAIL);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                player.sms(messageContext);
                return false;
            }
        }
        // Already exists.
        else {
            String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CREATE_FACTION_ALREADY_EXISTS);
            player.sms(message);
            return false;
        }
    }
}
