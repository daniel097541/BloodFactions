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

    default void schedule() {
        long time = (this.getTime() * 1000) / 20;
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(this.getPlugin(), this, time, time);
        this.setTask(task);
    }

}
