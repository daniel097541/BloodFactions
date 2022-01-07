package crypto.factions.bloodfactions.commons.tasks.model;

import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public interface PlayerTask extends Runnable {

    FPlayer getPlayer();

    long getTime();

    JavaPlugin getPlugin();

    BukkitTask getTask();

    void setTask(BukkitTask task);

    default void schedule(){
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(this.getPlugin(), this, this.getTime(), this.getTime());
        this.setTask(task);
    }

}
