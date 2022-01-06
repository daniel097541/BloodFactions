package crypto.factions.bloodfactions.commons.command.impl;

import crypto.factions.bloodfactions.backend.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.utils.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class ShowSubCommand extends FSubCommandImpl {

    @Inject
    public ShowSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.SHOW, langConfig);
    }

    private void showFaction(FPlayer player, Faction faction){
        int power = faction.getPower();
        Set<FPlayer> members = faction.getMembers();
        FPlayer owner = faction.getOwner();

        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("{faction_power}", String.valueOf(power));
        placeHolders.put("{faction_name}", faction.getName());
        placeHolders.put("{faction_members}", members.stream().map(FPlayer::getName).collect(Collectors.joining(", ")));
        placeHolders.put("{faction_owner}", owner.getName());

        String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_SHOW_SUCCESS);
        player.sms(StringUtils.replacePlaceHolders(message, placeHolders));
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
            this.showFaction(player, faction);
            return true;
        }

        // Show other.
        else {

            String factionName = args[1];
            Faction faction = NextGenFactionsAPI.getFactionByName(factionName);
            if(Objects.nonNull(faction)){
                this.showFaction(player, faction);
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
