package crypto.anguita.nextgenfactions.commons.command;

import crypto.anguita.nextgenfactions.commons.config.NGFConfig;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;

public interface FSubCommand {

    NGFConfig getLangConfig();

    SubCommandType getSubCommandType();

    default String getFormatMessage() {
        return (String) this.getLangConfig().read(this.getSubCommandType().getCommandLangPath() + ".format");
    }

    default String getNoPermissionMessage() {
        return (String) this.getLangConfig().read(this.getSubCommandType().getCommandLangPath() + ".no-permission");
    }

    default boolean run(String[] args, FPlayer player) {

        // Check validity.
        boolean valid = this.check(args, player);

        // Validation passed.
        if (valid) {
            return this.execute(args, player);
        }

        // Invalid command.
        else {
            return false;
        }
    }

    boolean execute(String[] args, FPlayer player);

    default boolean check(String[] args, FPlayer player) {

        // Check args length.
        boolean checkArgsLength = this.getSubCommandType().isCheckArgsLength();
        if (checkArgsLength) {
            int requiredLength = this.getSubCommandType().getRequiredArgsLength();

            // Args length is not correct.
            if (args.length < requiredLength) {
                String formatMessage = this.getFormatMessage();
                player.sms(formatMessage);
                return false;
            }
        }

        // Check permissions.
        boolean hasPermission = player.hasBukkitPermission(this.getSubCommandType().getPermission());
        if (!hasPermission) {
            String noPermissionMessage = this.getNoPermissionMessage();
            player.sms(noPermissionMessage);
            return false;
        } else {
            return true;
        }
    }
}
