package crypto.factions.bloodfactions.commons.config.lang;

import crypto.factions.bloodfactions.commons.config.ConfigItem;
import crypto.factions.bloodfactions.commons.config.impl.ConfigItemImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LangConfigItems {

    public static ConfigItem GLOBAL_PREFIX = new ConfigItemImpl("settings.messages-prefix", "&cBlood&7Factions &d>> &7");

    // Alerts broadcasting
    public static ConfigItem ALERT_PREFIX = new ConfigItemImpl("alerts.prefix", "&dALERT &7>> ");
    public static ConfigItem ALERT_OVER_CLAIMED = new ConfigItemImpl("alerts.over-claimed", "&c{faction_name} &eis over claiming you at: &7{chunk} &e!");

    // Actions messages
    public static ConfigItem ACTIONS_BREAK_NOT_YOUR_FACTION = new ConfigItemImpl("actions.block-break.not-your-faction", "&7This land is claimed by: &c{faction_name}&7, you cannot break blocks here!");
    public static ConfigItem ACTIONS_PLACE_NOT_YOUR_FACTION = new ConfigItemImpl("actions.block-place.not-your-faction", "&7This land is claimed by: &c{faction_name}&7, you cannot place blocks here!");
    public static ConfigItem ACTIONS_CANNOT_HIT_PLAYERS_IN_YOUR_FACTION = new ConfigItemImpl("actions.cannot-hit-players-of-your-faction", "&cYou cannot damage players from your own faction!");

    public static ConfigItem FACTION_PERMISSIONS_NO_PERMISSION = new ConfigItemImpl("f-permissions.no-permission", "&cYour role doesn't have the required permission: &7{permission_name}");

    // No permission for commands.
    public static ConfigItem COMMANDS_NO_PERMISSION = new ConfigItemImpl("commands.messages.no-permission", "&cYou dont have permission to perform this command.");

    // Create command
    public static ConfigItem COMMANDS_F_CREATE_FACTION_ALREADY_EXISTS = new ConfigItemImpl("commands.f-create.faction-already-exists", "&cThere is already a faction with that name.");
    public static ConfigItem COMMANDS_F_CREATE_FACTION_SUCCESS = new ConfigItemImpl("commands.f-create.success", "&7You successfully created a new faction called: &a{faction_name} &7!");
    public static ConfigItem COMMANDS_F_CREATE_FAIL = new ConfigItemImpl("commands.f-create.fail", "&cFaction creation failed.");
    public static ConfigItem COMMANDS_F_CREATE_PLAYER_ALREADY_HAS_FACTION = new ConfigItemImpl("commands.f-create.player-already-has-faction", "&cYou already have a faction!");

    // Disband command
    public static ConfigItem COMMANDS_F_DISBAND_NO_FACTION = new ConfigItemImpl("commands.f-disband.no-faction", "&cYou are not in a faction.");
    public static ConfigItem COMMANDS_F_DISBAND_SUCCESS = new ConfigItemImpl("commands.f-disband.success", "&eYour faction was &cdisbanded");
    public static ConfigItem COMMANDS_F_DISBAND_CONFIRMATION_REQUIRED = new ConfigItemImpl("commands.f-disband.confirmation-required", "&eTo confirm the disband, type again: &7/f disband");

    // Claim command
    public static ConfigItem COMMANDS_F_CLAIM_ALREADY_OWNED = new ConfigItemImpl("commands.f-claim.already-owned", "&7Your faction already owns this land.");
    public static ConfigItem COMMANDS_F_CLAIM_NO_FACTION = new ConfigItemImpl("commands.f-claim.no-faction", "&cYou are not in a faction.");
    public static ConfigItem COMMANDS_F_CLAIM_NOT_ENOUGH_POWER = new ConfigItemImpl("commands.f-claim.not-enough-power", "&cYour faction has not enough power to claim this land.");
    public static ConfigItem COMMANDS_F_CLAIM_FACTION_IS_STRONG_TO_KEEP = new ConfigItemImpl("commands.f-claim.faction-is-strong-to-keep", "&7The faction &c{faction_name} &7is strong enough to keep this land.");
    public static ConfigItem COMMANDS_F_CLAIM_SUCCESS = new ConfigItemImpl("commands.f-claim.success", "&aYou successfully claimed this chunk from &7{faction_name}.");
    public static ConfigItem COMMANDS_F_CLAIM_FAIL = new ConfigItemImpl("commands.f-claim.fail", "&cFailed to claim this chunk.");
    public static ConfigItem COMMANDS_F_CLAIM_MAX_RADIUS = new ConfigItemImpl("commands.f-claim.max-radius", "&cThe max radius for multi-claim is: &7{radius}");


    // Un-Claim command
    public static ConfigItem COMMANDS_F_UN_CLAIM_NOT_YOUR_LAND = new ConfigItemImpl("commands.f-unclaim.not-your-land", "&cThis chunk is not claimed by your faction.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_NO_FACTION = new ConfigItemImpl("commands.f-unclaim.no-faction", "&cYou are not in a faction.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_SUCCESS = new ConfigItemImpl("commands.f-unclaim.success", "&aYou successfully un-claimed this chunk of &7{faction_name}.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_FAIL = new ConfigItemImpl("commands.f-unclaim.fail", "&cFailed to un-claim this chunk.");

    // Un-Claim all command
    public static ConfigItem COMMANDS_F_UN_CLAIM_ALL_NO_FACTION = new ConfigItemImpl("commands.f-unclaimall.no-faction", "&cYou are not in a faction.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_ALL_SUCCESS = new ConfigItemImpl("commands.f-unclaimall.success", "&aYou successfully un-claimed all the claims from &7{faction_name}.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_ALL_FAIL = new ConfigItemImpl("commands.f-unclaimall.fail", "&cFailed to un-claim all.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_ALL_CONFIRMATION = new ConfigItemImpl("commands.f-unclaimall.confirmation", "&eUn-claim all command requires confirmation, type the command again before 30s.");
    public static ConfigItem COMMANDS_F_UN_CLAIM_ALL_NO_CLAIMS= new ConfigItemImpl("commands.f-unclaimall.no-claims", "&cYour faction does not have any claim.");

    // Home
    public static ConfigItem COMMANDS_F_HOME_NO_FACTION = new ConfigItemImpl("commands.f-home.no-faction", "&cYou need a faction to do that.");
    public static ConfigItem COMMANDS_F_HOME_SUCCESS = new ConfigItemImpl("commands.f-home.success", "&aTeleported to your home!");
    public static ConfigItem COMMANDS_F_HOME_FAIL = new ConfigItemImpl("commands.f-home.fail", "&cFaction home failed.");
    public static ConfigItem COMMANDS_F_HOME_NOT_SET = new ConfigItemImpl("commands.f-home.home-is-not-set", "&cYour faction does not have a home, set it with: &7/f home set");
    public static ConfigItem COMMANDS_F_HOME_SET_SUCCESS = new ConfigItemImpl("commands.f-home.set-success", "&aYou successfully set the home of your faction!");
    public static ConfigItem COMMANDS_F_HOME_NOT_YOUR_LAND = new ConfigItemImpl("commands.f-home.not-your-land", "&cThis chunk is not claimed by your faction.");


    // Show
    public static String showBluePrint = "\n&7----------- &e{faction_name} &7-----------\n" +
            "\n" +
            "   &d- &7Faction can be over-claimed: &d{can_be_over_claimed} \n" +
            "   &d- &7Faction power: &e{faction_power} \n" +
            "   &d- &7Faction claims: &e{faction_claims} \n" +
            "   &d- &7Faction members: &e{faction_members} \n" +
            "   &d- &7Owner: &e{faction_owner} \n";

    public static ConfigItem COMMANDS_F_SHOW_NO_FACTION = new ConfigItemImpl("commands.f-show.no-faction", "&cYou need a faction to do that.");
    public static ConfigItem COMMANDS_F_SHOW_SUCCESS = new ConfigItemImpl("commands.f-show.success", showBluePrint);
    public static ConfigItem COMMANDS_F_SHOW_FACTION_DOES_NOT_EXIST = new ConfigItemImpl("commands.f-show.fail", "&cThere is no faction with that name.");

    // Fly
    public static ConfigItem COMMANDS_F_FLY_NO_FACTION = new ConfigItemImpl("commands.f-fly.no-faction", "&cYou need a faction to do that.");
    public static ConfigItem COMMANDS_F_FLY_SUCCESS = new ConfigItemImpl("commands.f-fly.success", "&aFlight mode activated.");
    public static ConfigItem COMMANDS_F_FLY_OFF = new ConfigItemImpl("commands.f-fly.disabled", "&cFlight mode disabled.");
    public static ConfigItem COMMANDS_F_FLY_FAIL = new ConfigItemImpl("commands.f-fly.fail", "&cError activating flight.");
    public static ConfigItem COMMANDS_F_FLY_NOT_IN_YOUR_FACTION = new ConfigItemImpl("commands.f-fly.not-in-your-faction", "&cYou are not in your factions land.");


    // Auto Fly
    public static ConfigItem COMMANDS_F_AUTO_FLY_NO_FACTION = new ConfigItemImpl("commands.f-auto-fly.no-faction", "&cYou need a faction to do that.");
    public static ConfigItem COMMANDS_F_AUTO_FLY_SUCCESS = new ConfigItemImpl("commands.f-auto-fly.success", "&aAuto-fly mode activated.");
    public static ConfigItem COMMANDS_F_AUTO_FLY_OFF = new ConfigItemImpl("commands.f-auto-fly.disabled", "&cAuto-fly mode disabled.");
    public static ConfigItem COMMANDS_F_AUTO_FLY_FAIL = new ConfigItemImpl("commands.f-auto-fly.fail", "&cError activating auto-fly.");

    // Roles
    public static String rolesListHeaderBlueprint = "\n&7----------------- &a{faction_name}'s &dRanks &7-----------------\n";
    public static String rolesListBlueprint =
            "&7-------------------------------------------- \n" +
                    "&7|   &d- &7Rank name: &e{rank_name} \n" +
                    "&7|   &d- &7Rank permissions: &c{permission_list} \n" +
                    "&7-------------------------------------------- \n";
    public static ConfigItem COMMANDS_F_RANKS_NO_FACTION = new ConfigItemImpl("commands.f-ranks.no-faction", "&cYou need a faction to do that.");
    public static ConfigItem COMMANDS_F_RANKS_CREATE = new ConfigItemImpl("commands.f-ranks.create", "&aRank successfully created.");
    public static ConfigItem COMMANDS_F_RANKS_DELETE = new ConfigItemImpl("commands.f-ranks.delete", "&aRank successfully deleted.");
    public static ConfigItem COMMANDS_F_RANKS_NOT_EXISTS = new ConfigItemImpl("commands.f-ranks.not-exists", "&cThere is no rank with that name.");
    public static ConfigItem COMMANDS_F_RANKS_ALREADY_EXISTS = new ConfigItemImpl("commands.f-ranks.already-exists", "&cThere is already a rank with that name.");
    public static ConfigItem COMMANDS_F_RANKS_LIST_HEADER = new ConfigItemImpl("commands.f-ranks.list-header", rolesListHeaderBlueprint);
    public static ConfigItem COMMANDS_F_RANKS_LIST_BODY = new ConfigItemImpl("commands.f-ranks.list-body", rolesListBlueprint);
    public static ConfigItem COMMANDS_F_RANKS_PLAYER_NOT_EXISTS = new ConfigItemImpl("commands.f-ranks.player-not-exists", "&cThere is no player with that name.");
    public static ConfigItem COMMANDS_F_RANKS_PLAYER_ALREADY_IS_RANK = new ConfigItemImpl("commands.f-ranks.player-already-has-that-rank", "&cThe player already has that rank.");
    public static ConfigItem COMMANDS_F_RANKS_PLAYER_CHANGED_RANK = new ConfigItemImpl("commands.f-ranks.players-rank-changed", "&eYou successfully set the rank of &a{target_player_name} &eto &d{rank_name}");
    public static ConfigItem COMMANDS_F_RANKS_YOUR_RANK_CHANGED = new ConfigItemImpl("commands.f-ranks.your-rank-changed", "&a{target_player_name} &ehas changed your rank to: &d{rank_name}");
    public static ConfigItem COMMANDS_F_RANKS_SET_FORMAT = new ConfigItemImpl("commands.f-ranks.set-format", "&7Command usage: &e/f ranks set <player_name> <rank_name>");
    public static ConfigItem COMMANDS_F_RANKS_CREATE_FORMAT = new ConfigItemImpl("commands.f-ranks.create-format", "&7Rank creation command: \n" +
            "\n&7-----------------------------------------------" +
            "\n&7|    &7Ussage: &e/f ranks create &7<rank_name> &d<rank_permissions>" +
            "\n&7|" +
            "\n&7|    &7Example: &e/f ranks create &7Moderator &dINVITE,KICK" +
            "\n&7-----------------------------------------------");


    // Invite

    public static String listToMyFactionHeaderBluePrint = "\n&7----------------- &a{faction_name} &eInvitations &7-----------------\n";
    public static String listToOtherFactions = "\n&7----------------- &eMy invitations &7-----------------\n";

    public static String listItemBlueprint =
            "&7-----------------------------------------------\n" +
                    "   &d- &7Faction inviting: &e{faction_name}\n" +
                    "   &d- &7Player invited: &e{target_player_name}\n" +
                    "   &d- &7Invited by: &e{inviter_name}\n" +
                    "   &d- &7Invited on: &e{invitation_date} \n" +
                    "&7-----------------------------------------------\n";

    public static ConfigItem COMMANDS_F_INVITE_PLAYER_DOES_NOT_EXIST = new ConfigItemImpl("commands.f-invite.player-does-not-exist", "&cThe player is offline or does not exist.");
    public static ConfigItem COMMANDS_F_INVITE_SUCCESS = new ConfigItemImpl("commands.f-invite.success", "&aYou successfully invited &7{target_player_name} &ato your faction.");
    public static ConfigItem COMMANDS_F_INVITE_FAILED = new ConfigItemImpl("commands.f-invite.failed", "&cThe command failed.");
    public static ConfigItem COMMANDS_F_INVITE_INVITED_TO_FACTION = new ConfigItemImpl("commands.f-invite.you-were-invited", "&d{target_player_name} &7just invited you to: &e{faction_name}");
    public static ConfigItem COMMANDS_F_INVITE_ALREADY_INVITED = new ConfigItemImpl("commands.f-invite.already-invited", "&7{target_player_name} &cis already invited to your faction.");
    public static ConfigItem COMMANDS_F_INVITE_ALREADY_IN_FACTION = new ConfigItemImpl("commands.f-invite.already-in-faction", "&7{target_player_name} &cis already in your faction.");
    public static ConfigItem COMMANDS_F_INVITE_LIST_TO_MY_FACTION_HEADER = new ConfigItemImpl("commands.f-invite.list-invitations-of-my-faction-header", listToMyFactionHeaderBluePrint);
    public static ConfigItem COMMANDS_F_INVITE_LIST_TO_OTHER_FACTIONS_HEADER = new ConfigItemImpl("commands.f-invite.list-my-invitations", listToOtherFactions);
    public static ConfigItem COMMANDS_F_INVITE_LIST_ITEM = new ConfigItemImpl("commands.f-invite.list-item", listItemBlueprint);
    public static ConfigItem COMMANDS_F_INVITE_NOT_INVITED_BY_FACTION = new ConfigItemImpl("commands.f-invite.not-invited-by-faction", "&cYou dont have any invitation of: &7{faction_name}");
    public static ConfigItem COMMANDS_F_INVITE_ACCEPTED = new ConfigItemImpl("commands.f-invite.accepted-invitation", "&7You successfully joined: &a{faction_name}");
    public static ConfigItem COMMANDS_F_INVITE_DECLINED = new ConfigItemImpl("commands.f-invite.declined-invitation", "&7You declined an invitation from &c{faction_name}");
    public static ConfigItem COMMANDS_F_INVITATION_DECLINED = new ConfigItemImpl("commands.f-invite.player-declined-invitation", "&7The player &c{target_player_name} &7declined the invitation to your faction.");
    public static ConfigItem COMMANDS_F_INVITATION_ACCEPTED = new ConfigItemImpl("commands.f-invite.player-accepted-invitation", "&aThe player &e{target_player_name} &ahas joined to your faction!");


    public static ConfigItem COMMANDS_F_KICK_NO_FACTION = new ConfigItemImpl("commands.f-kick.no-faction", "&cYou need a faction to do that!");
    public static ConfigItem COMMANDS_F_KICK_SUCCESS = new ConfigItemImpl("commands.f-kick.success", "&aYou successfully kicked: &c{target_player_name}");
    public static ConfigItem COMMANDS_F_KICK_NOT_IN_YOUR_FACTION = new ConfigItemImpl("commands.f-kick.player-not-in-your-faction", "&cThe player is not in your faction.");
    public static ConfigItem COMMANDS_F_KICK_CANNOT_KICK_PLAYER = new ConfigItemImpl("commands.f-kick.cannot-kick-player", "&cYou cannot kick that player.");
    public static ConfigItem COMMANDS_F_KICK_NO_PLAYER = new ConfigItemImpl("commands.f-kick.player-does-not-exist", "&cThe player does not exist.");
    public static ConfigItem COMMANDS_F_KICKED = new ConfigItemImpl("commands.f-kick.you-were-kicked", "&7You have been kicked from &c{target_faction_name} &7by &c{target_player_name}");


    public static ConfigItem COMMANDS_F_LEAVE_NO_FACTION = new ConfigItemImpl("commands.f-leave.no-faction", "&cYou need a faction to do that!");
    public static ConfigItem COMMANDS_F_LEAVE_SUCCESS = new ConfigItemImpl("commands.f-leave.success", "&aYou successfully left your faction!");
    public static ConfigItem COMMANDS_F_PLAYER_LEFT = new ConfigItemImpl("commands.f-leave.player-left", "&7The player &c{target_player_name} &7has left your faction!");
    public static ConfigItem COMMANDS_F_LEAVE_OWNER_CANNOT_LEAVE = new ConfigItemImpl("commands.f-leave.you-are-the-owner", "&cYou are the owner of the faction, you cannot leave it!");


    public static Map<String, ConfigItem> asMap() {
        Map<String, ConfigItem> items = new HashMap<>();
        Arrays.asList(LangConfigItems.class.getDeclaredFields())
                .forEach(field -> {

                    try {
                        ConfigItem item = (ConfigItem) field.get(null);
                        items.put(item.getPath(), item);
                    } catch (Exception ignored) {
                    }

                });

        return items;
    }

}
