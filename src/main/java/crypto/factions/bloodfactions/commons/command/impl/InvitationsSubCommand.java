package crypto.factions.bloodfactions.commons.command.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.invitation.FactionInvitation;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import java.util.Objects;

@Singleton
public class InvitationsSubCommand extends FSubCommandImpl {

    @Inject
    public InvitationsSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.INVITATIONS, langConfig);
    }

    private boolean declineInvitation(String factionName, FPlayer player) {
        Faction faction = ContextHandler.getFactionByName(factionName);
        return player.declineInvitation(faction);
    }

    private boolean acceptInvitation(String factionName, FPlayer player) {
        Faction faction = ContextHandler.getFactionByName(factionName);
        return player.acceptInvitation(faction);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {

        String subCommandName = args[0];

        if (subCommandName.equalsIgnoreCase("join")) {

            // Accept invitation by faction name.
            if (args.length > 2) {
                return this.acceptInvitation(args[2], player);
            }

            // Accept latest invitation.
            else {
                FactionInvitation invitation = player.getInvitations().stream().findFirst().orElse(null);
                if (Objects.nonNull(invitation)) {
                    return this.acceptInvitation(invitation.getFaction().getName(), player);
                }
            }

        } else if (subCommandName.equalsIgnoreCase("decline")) {

            // Decline invitation by faction name.
            if (args.length > 2) {
                return this.declineInvitation(args[2], player);
            }
            // Decline latest invitation.
            else {
                FactionInvitation invitation = player.getInvitations().stream().findFirst().orElse(null);
                if (Objects.nonNull(invitation)) {
                    return this.declineInvitation(invitation.getFaction().getName(), player);
                }

                // No invitation
                else{

                    return true;
                }
            }

        }

        String actionOrPlayer = args[1];
        FPlayer targetPlayer = ContextHandler.getPlayerByName(actionOrPlayer);

        // Invite player if first parameter is a player name.
        if (Objects.nonNull(targetPlayer)) {
            return player.invitePlayerToFaction(targetPlayer);
        }

        // List invitations.
        if (actionOrPlayer.equals("list")) {

            // List invitations to my faction.
            if (player.hasFaction()) {
                player.listInvitationsOfMyFaction();
            }

            // List invitations from factions.
            else {
                player.listInvitationsToOtherFactions();
            }

            return true;
        } else if (actionOrPlayer.equals("accept")) {
            return acceptInvitation(args[2], player);
        } else if (actionOrPlayer.equals("decline")) {
            return declineInvitation(args[2], player);
        }

        // Add invitation.
        else if (actionOrPlayer.equals("add")) {

            // Invite player
            if (args.length >= 3) {

                String playerName = args[2];
                FPlayer invitedPlayer = ContextHandler.getPlayerByName(playerName);

                if (Objects.nonNull(invitedPlayer)) {
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
        else if (actionOrPlayer.equals("remove")) {
            // Invite player
            if (args.length >= 3) {

                String playerName = args[2];
                FPlayer invitedPlayer = ContextHandler.getPlayerByName(playerName);

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
