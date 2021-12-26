package crypto.anguita.nextgenfactions.commons.pipeline.impl;

import crypto.anguita.nextgenfactions.commons.events.PermissionEvent;
import crypto.anguita.nextgenfactions.commons.pipeline.PermissionsPipe;
import crypto.anguita.nextgenfactions.commons.pipeline.PermissionsPipeline;
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
