package crypto.factions.bloodfactions.backend.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.backend.dao.RelationsDAO;
import crypto.factions.bloodfactions.backend.db.DBManager;
import lombok.Getter;

@Singleton
@Getter
public class FactionRelationDAOImpl implements RelationsDAO {

    private final String tableName = "faction_relations";
    private final DBManager dbManager;


    @Inject
    public FactionRelationDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }
}
