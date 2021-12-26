package crypto.anguita.nextgenfactions.commons.config.impl;

import crypto.anguita.nextgenfactions.commons.config.ConfigItem;
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
