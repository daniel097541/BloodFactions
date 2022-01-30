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
        Faction faction = player.getFaction();
        FChunk chunk = player.getChunk();
        faction.unClaim(chunk, player);
        return true;
    }
}
