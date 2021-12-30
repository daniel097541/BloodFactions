package crypto.anguita.nextgenfactions.backend.dao;

import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;

public interface FactionsDAO extends DAO<Faction> {

    Faction getFactionAtChunk(FChunk chunk);
}
