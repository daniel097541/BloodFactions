package crypto.factions.bloodfactions.commons.tasks.manager;


import crypto.factions.bloodfactions.commons.tasks.model.PlayerTask;

import java.util.Map;
import java.util.UUID;

public interface TaskManager {

    Map<UUID, PlayerTask> getPowerTasks();


    default void addPowerTask(PlayerTask task) {
        this.getPowerTasks().put(task.getPlayer().getId(), task);
        task.schedule();
    }

    default void removePowerTask(UUID playerId){
        this.getPowerTasks().remove(playerId);
    }

}
