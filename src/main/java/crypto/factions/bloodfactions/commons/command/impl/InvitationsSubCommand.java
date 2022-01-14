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
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import java.util.Objects;

@Singleton
public class InvitationsSubCommand extends FSubCommandImpl {

    @Inject
    public InvitationsSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.INVITATIONS, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {

        String action = args[1];

        // List invitations.
        if (action.equals("list")) {

            // List invitations to my faction.
            if (player.hasFaction()) {
                player.listInvitationsOfMyFaction();
            }

            // List invitations from factions.
            else {
                player.listInvitationsToOtherFactions();
            }

            return true;
        }

        else if(action.equals("accept")){
            String factionName = args[2];
            Faction faction = NextGenFactionsAPI.getFactionByName(factionName);
            player.acceptInvitation(faction);
        }

        else if(action.equals("decline")){
            String factionName = args[2];
            Faction faction = NextGenFactionsAPI.getFactionByName(factionName);
            player.declineInvitation(faction);
        }

        // Add invitation.
        else if (action.equals("add")) {

            // Invite player
            if (args.length >= 3) {

                String playerName = args[2];
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
            // Missing player name.
            else {
                return false;
            }
        }

        // De-Invite
        else if (action.equals("remove")) {
            // Invite player
            if (args.length >= 3) {

                String playerName = args[2];
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
            // Missing player name.
            else {
                return false;
            }
        }

        return false;
    }
}
