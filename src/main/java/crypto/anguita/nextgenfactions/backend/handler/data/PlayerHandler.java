package crypto.anguita.nextgenfactions.backend.handler.data;

import crypto.anguita.nextgenfactions.backend.dao.PlayerDAO;
import crypto.anguita.nextgenfactions.commons.events.player.callback.GetPlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayerImpl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.Objects;
import java.util.UUID;

public interface PlayerHandler extends DataHandler<FPlayer> {

    @Override
    PlayerDAO getDao();


    @EventHandler(priority = EventPriority.HIGHEST)
    default void getPlayer(GetPlayerEvent event){
        UUID uuid = event.getId();
        FPlayer player = this.getById(uuid);

        if(Objects.isNull(player)){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            player = new FPlayerImpl(offlinePlayer.getUniqueId(), offlinePlayer.getName(), 0);
            player = this.getDao().insert(player);
        }

        event.setPlayer(player);
    }

}
