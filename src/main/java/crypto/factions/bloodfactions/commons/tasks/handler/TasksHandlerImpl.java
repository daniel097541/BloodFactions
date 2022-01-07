package crypto.factions.bloodfactions.commons.tasks.handler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.commons.tasks.manager.TaskManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
@Getter
public class TasksHandlerImpl implements TasksHandler {

    private final TaskManager taskManager;
    private final JavaPlugin plugin;

    @Inject
    public TasksHandlerImpl(TaskManager taskManager, JavaPlugin plugin) {
        this.taskManager = taskManager;
        this.plugin = plugin;
    }
}
