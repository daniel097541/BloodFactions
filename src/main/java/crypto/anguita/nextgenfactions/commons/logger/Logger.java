package crypto.anguita.nextgenfactions.commons.logger;

import org.bukkit.Bukkit;

import java.util.Objects;

public class Logger {

    public static boolean DEBUG = true;

    public static void logInfo(String message) {
        if (DEBUG) {
            Bukkit.getConsoleSender().sendMessage("INFO >> " + message);
        }
    }

    public static void logError(String message, Exception... e) {
        Bukkit.getConsoleSender().sendMessage("ERROR >> " + message);
        if (Objects.nonNull(e) && e.length > 0) {
            for (Exception exception : e) {
                exception.printStackTrace();
            }
        }
    }

}
