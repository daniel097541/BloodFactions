package crypto.factions.bloodfactions.commons.utils;

import org.bukkit.ChatColor;

import java.util.Map;

public class StringUtils {

    public static String replacePlaceHolders(String msg, Map<String, String> placeHolders) {

        for (Map.Entry<String, String> entry : placeHolders.entrySet()) {
            msg = msg.replace(entry.getKey(), entry.getValue());
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
