package crypto.factions.bloodfactions.frontend.command.impl;

import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.frontend.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Singleton
public class RanksSubCommand extends FSubCommandImpl {

    @Inject
    public RanksSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.RANK, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {
        boolean hasFaction = player.hasFaction();

        // Player is not in a faction.
        if (!hasFaction) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_NO_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        Faction faction = player.getFaction();

        // List roles
        if (args.length == 1) {
            player.listRoles(faction);
            return true;
        } else {

            String action = args[1];

            if (action.equalsIgnoreCase("set")) {

                if (args.length >= 4) {

                    String playerName = args[2];
                    String rankName = args[3];

                    FPlayer targetPlayer = ContextHandler.getPlayerByName(playerName);

                    if (Objects.nonNull(targetPlayer)) {
                        FactionRank targetRank = faction.getRankByName(rankName);

                        // Set the rank of the player.
                        if (Objects.nonNull(targetRank)) {
                            Logger.logInfo("Setting rank of " + playerName + " to " + rankName);
                            targetPlayer.setRank(targetRank, player);
                            return true;
                        }

                        // Set rank does not exist command.
                        String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_NOT_EXISTS);
                        MessageContext messageContext = new MessageContextImpl(player, successMessage);
                        player.sms(messageContext);
                        return false;
                    }

                    // Send does not exist command.
                    String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_PLAYER_NOT_EXISTS);
                    MessageContext messageContext = new MessageContextImpl(player, successMessage);
                    player.sms(messageContext);
                    return false;
                }

                // Send formats.
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_SET_FORMAT);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                player.sms(messageContext);
                return false;
            }

            // Create role
            if (action.equalsIgnoreCase("create")) {

                // Create role with name.
                if (args.length >= 3) {
                    String roleName = args[2];
                    Set<PermissionType> permissions = new HashSet<>();

                    if (args.length == 4) {
                        String permissionsStr = args[3];
                        String[] permissionsNames = permissionsStr.split(",");

                        for (String permName : permissionsNames) {
                            PermissionType type = PermissionType.fromName(permName);
                            if (Objects.nonNull(type)) {
                                permissions.add(type);
                            }
                        }
                    }

                    faction.createRank(roleName, player, permissions);
                    return true;
                }

                // Send creation format.
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_RANKS_CREATE_FORMAT);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                player.sms(messageContext);
                return false;
            }

            if (action.equalsIgnoreCase("list")) {
                player.listRoles(faction);
                return true;
            }

            if (action.equalsIgnoreCase("remove")
                    || action.equalsIgnoreCase("delete")
                    || action.equalsIgnoreCase("del")) {

                // Delete role with name.
                if (args.length == 3) {
                    String roleName = args[2];
                    faction.deleteRank(roleName, player);
                    return true;
                }

                // Send role deletion formats.
                else {

                    return false;
                }

            }
        }

        return true;
    }
}
