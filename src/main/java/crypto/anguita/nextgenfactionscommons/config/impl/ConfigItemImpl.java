package crypto.anguita.nextgenfactionscommons.config.impl;

import crypto.anguita.nextgenfactionscommons.config.ConfigItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ConfigItemImpl implements ConfigItem {
    private final String path;
    private final Object defaultValue;
}
