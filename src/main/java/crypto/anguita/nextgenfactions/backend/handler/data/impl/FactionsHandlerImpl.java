package crypto.anguita.nextgenfactions.backend.handler.data.impl;

import crypto.anguita.nextgenfactions.backend.handler.dao.FactionsDAO;
import crypto.anguita.nextgenfactions.backend.handler.data.FactionsHandler;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Getter
public class FactionsHandlerImpl implements FactionsHandler {

    private final FactionsDAO dao;

    @Inject
    public FactionsHandlerImpl(FactionsDAO factionsDAO) {
        this.dao = factionsDAO;
    }
}
