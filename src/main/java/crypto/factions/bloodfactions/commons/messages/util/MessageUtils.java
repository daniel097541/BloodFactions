package crypto.factions.bloodfactions.commons.messages.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;

public class MessageUtils {

    public static void sendMessage(String message, Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage(String message, Player player, Map<String, String> placeholders) {
        String finalMessage = message;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            finalMessage = finalMessage.replace(entry.getKey(), entry.getValue());
        }
        sendMessage(finalMessage, player);
    }

}
