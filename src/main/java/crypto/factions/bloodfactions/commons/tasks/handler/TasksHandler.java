package crypto.factions.bloodfactions.commons.tasks.handler;

import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.tasks.manager.TaskManager;
import crypto.factions.bloodfactions.commons.tasks.model.impl.PlayerPowerTask;
import org.bukkit.plugin.java.JavaPlugin;

public interface TasksHandler {

    TaskManager getTaskManager();

    JavaPlugin getPlugin();

    default void addPowerTask(FPlayer player){
        Logger.logInfo("New player power task for: " + player.getName());
        this.getTaskManager().addPowerTask(new PlayerPowerTask(player, 60000, this.getPlugin()));
    }

    default void removePowerTask(FPlayer player){
        Logger.logInfo("Removing player power task for: " + player.getName());
        this.getTaskManager().removePowerTask(player.getId());
    }
}
