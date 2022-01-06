package crypto.factions.bloodfactions.commons.command.impl;

import crypto.factions.bloodfactions.backend.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
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
public class ClaimSubCommand extends FSubCommandImpl {

    @Inject
    public ClaimSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.CLAIM, langConfig);
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
        FChunk chunk = player.getChunk();
        Faction factionAt = Objects.requireNonNull(chunk).getFactionAt();

        // Already claimed this land.
        if (factionAt.equals(faction)) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_ALREADY_OWNED);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        // Need more pow
        if (!faction.canClaim() && !player.isOp()) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_NOT_ENOUGH_POWER);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        // Cannot over-claim faction
        if (!factionAt.canBeOverClaimed() && !player.isOp()) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_FACTION_IS_STRONG_TO_KEEP);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            messageContext.setFaction(factionAt);
            player.sms(messageContext);
            return false;
        }

        // Over-Claim
        if (!factionAt.isSystemFaction()) {
            faction.overClaim(chunk, player, factionAt);
        }
        // Simple claim
        else {
            faction.claim(chunk, player);
        }

        // Success
        String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_SUCCESS);
        MessageContext messageContext = new MessageContextImpl(player, successMessage);
        messageContext.setFaction(factionAt);
        player.sms(messageContext);
        return true;
    }
}
