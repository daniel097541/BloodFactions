package crypto.anguita.nextgenfactionscommons.pipeline.impl;

import crypto.anguita.nextgenfactionscommons.events.PermissionEvent;
import crypto.anguita.nextgenfactionscommons.pipeline.PermissionsPipe;
import crypto.anguita.nextgenfactionscommons.pipeline.PermissionsPipeline;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class PermissionsPipelineImpl implements PermissionsPipeline {
    private final PermissionEvent event;
    private final LinkedList<PermissionsPipe> pipes;
}
