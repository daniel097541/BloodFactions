package crypto.anguita.nextgenfactions.frontend.listener;

import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

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

        Faction faction = fPlayer.getFaction();

        fPlayer.sms("&aHey! " + faction.getName());


        Set<FPlayer> players = NextGenFactionsAPI.getPlayersInFaction(fPlayer.getFaction());

    }
}
