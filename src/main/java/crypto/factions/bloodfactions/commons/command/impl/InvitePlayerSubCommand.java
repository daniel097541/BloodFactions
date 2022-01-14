package crypto.factions.bloodfactions.commons.command.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import java.util.Objects;

@Singleton
public class InvitePlayerSubCommand extends FSubCommandImpl {

    @Inject
    public InvitePlayerSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.INVITE, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {

        String playerName = args[1];
        FPlayer invitedPlayer = NextGenFactionsAPI.getPlayerByName(playerName);

        if (Objects.nonNull(invitedPlayer) && invitedPlayer.isOnline()) {
            return player.invitePlayerToFaction(invitedPlayer);
        } else {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_INVITE_PLAYER_DOES_NOT_EXIST);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }
    }
}
