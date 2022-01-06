package crypto.factions.bloodfactions.commons.pipeline.impl;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.pipeline.PermissionsPipe;
import crypto.factions.bloodfactions.commons.pipeline.PermissionsPipeline;
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
