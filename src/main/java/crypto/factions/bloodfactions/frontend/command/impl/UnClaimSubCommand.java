package crypto.factions.bloodfactions.frontend.command.impl;

import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.frontend.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class UnClaimSubCommand extends FSubCommandImpl {

    @Inject
    public UnClaimSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.UN_CLAIM, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {
        boolean hasFaction = player.hasFaction();

        // Player is not in a faction.
        if (!hasFaction) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_UN_CLAIM_NO_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        Faction faction = player.getFaction();
        FChunk chunk = player.getChunk();
        Faction factionAt = Objects.requireNonNull(chunk).getFactionAt();

        // Already claimed this land.
        if (!factionAt.equals(faction)) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_UN_CLAIM_NOT_YOUR_LAND);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        boolean unClaimed = faction.unClaim(chunk, player);


        // Success
        if (unClaimed) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_UN_CLAIM_SUCCESS);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            messageContext.setFaction(factionAt);
            player.sms(messageContext);
            return true;
        }
        // Failed
        else {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_UN_CLAIM_FAIL);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }
    }
}
