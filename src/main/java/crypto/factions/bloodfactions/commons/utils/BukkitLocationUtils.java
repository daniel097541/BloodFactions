package crypto.factions.bloodfactions.commons.utils;

import org.bukkit.Location;

import java.util.Objects;

public class BukkitLocationUtils {

    public static boolean sameLocation(Location oneLocation, Location otherLocation){
        return Objects.equals(oneLocation.getWorld(), oneLocation.getWorld())
                && oneLocation.getX() == otherLocation.getX()
                && oneLocation.getY() == otherLocation.getY()
                && oneLocation.getZ() == otherLocation.getZ();
    }

}
