package crypto.factions.bloodfactions.commons.tasks.model.impl;

import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

@Getter
@Setter
public class PlayerPowerTask extends PlayerTaskImpl {

    private BukkitTask task;

    public PlayerPowerTask(FPlayer player, long time, JavaPlugin plugin) {
        super(player, time, plugin);
    }

    @Override
    public void run() {
        Logger.logInfo("Incrementing power of: " + this.getPlayer().getName());
        if(!this.getPlayer().isOnline()){
            this.task.cancel();
        }
        else{
            this.getPlayer().updatePower(1);
            Logger.logInfo("Incremented power of: " + this.getPlayer().getName());
        }
    }
}
