package crypto.factions.bloodfactions.commons.tasks.manager;

import com.google.inject.Singleton;
import crypto.factions.bloodfactions.commons.tasks.model.PlayerTask;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Singleton
public class TaskManagerImpl implements TaskManager {

    private final Map<UUID, PlayerTask> powerTasks = new HashMap<>();


}
