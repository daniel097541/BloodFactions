package crypto.factions.bloodfactions.commons.command.impl;

import crypto.factions.bloodfactions.backend.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

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

            // Create role
            if (action.equalsIgnoreCase("create")) {

                // Create role with name.
                if (args.length == 3) {
                    String roleName = args[2];
                    FactionRank rank = faction.createRank(roleName, player);
                    return Objects.nonNull(rank);
                }

                // Send role creation formats.
                else {

                    return false;
                }

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
                    return faction.deleteRank(roleName, player);
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
