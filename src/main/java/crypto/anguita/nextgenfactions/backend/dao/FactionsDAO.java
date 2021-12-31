package crypto.anguita.nextgenfactions.backend.dao;

import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;

import java.util.UUID;

public interface FactionsDAO extends DAO<Faction> {

    Faction getFactionAtChunk(FChunk chunk);

    void claimForFaction(Faction faction, FChunk chunk, UUID claimerId);

    Faction insert(Faction faction);
}
