package crypto.anguita.nextgenfactions.commons.logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Logger {

    public static boolean DEBUG = true;

    public static void logInfo(@NotNull String level, @NotNull String message) {
        if (DEBUG) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + level + "&d>> &7" + message));
        }
    }

    public static void logInfo(String message) {
        logInfo("INFO", message);
    }

    public static void logError(String message, Exception... e) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4ERROR &d>> &7" + message));
        if (Objects.nonNull(e) && e.length > 0) {
            for (Exception exception : e) {
                exception.printStackTrace();
            }
        }
    }

}
