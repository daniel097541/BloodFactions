package crypto.anguita.nextgenfactions.frontend.listener;

import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.impl.FChunkImpl;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayerListenerImpl implements PlayerListener {

    private final JavaPlugin plugin;

    @Inject
    public PlayerListenerImpl(JavaPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FPlayer fPlayer = NextGenFactionsAPI.getPlayer(player);
        fPlayer.sms("&aHey!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChunkLoad(ChunkLoadEvent event) {
        Faction factionAt = NextGenFactionsAPI.getFactionAtChunk(FChunkImpl.fromChunk(event.getChunk()));
        Bukkit.getConsoleSender().sendMessage(factionAt.getName());
    }
}
