package crypto.factions.bloodfactions.commons.tasks.model.impl;

import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.tasks.model.PlayerTask;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public abstract class PlayerTaskImpl implements PlayerTask {

    private final FPlayer player;
    private final long time;
    private final JavaPlugin plugin;

    public PlayerTaskImpl(FPlayer player, long time, JavaPlugin plugin) {
        this.player = player;
        this.time = time;
        this.plugin = plugin;
    }
}
