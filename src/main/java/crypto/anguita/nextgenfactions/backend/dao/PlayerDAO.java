package crypto.anguita.nextgenfactions.backend.dao;

import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;

public interface PlayerDAO extends DAO<FPlayer> {

    FPlayer insert(FPlayer player);

}
