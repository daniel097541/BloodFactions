package crypto.factions.bloodfactions.commons.utils;

import crypto.factions.bloodfactions.BloodFactions;
import org.bukkit.Bukkit;

public class ThreadUtils {

    public static void runAsync(Runnable runnable){
        Bukkit.getScheduler().runTask(BloodFactions.getInstance(), runnable);
    }

}
