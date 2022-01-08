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
        if(!this.getPlayer().isOnline()){
            Logger.logInfo("Player power task cancelled for: " + this.getPlayer().getName());
            this.task.cancel();
        }
        else{
            Logger.logInfo("Incrementing power of: " + this.getPlayer().getName());
            this.getPlayer().updatePower(1);
        }
    }
}
