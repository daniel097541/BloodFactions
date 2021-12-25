package crypto.anguita.nextgenfactionscommons.config.impl;

import crypto.anguita.nextgenfactionscommons.config.ConfigType;
import crypto.anguita.nextgenfactionscommons.config.LangConfigItems;

import javax.inject.Singleton;

@Singleton
public class LangConfig extends NGFConfigImpl {

    public LangConfig() {
        super(LangConfigItems.asMap(), ConfigType.LANG);
    }
}
