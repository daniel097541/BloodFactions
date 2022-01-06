package crypto.factions.bloodfactions.commons.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EventLoop {

    public static void launchEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

}
