package crypto.anguita.nextgenfactions.commons.command;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public enum SubCommandType {

    UN_CLAIM(new String[]{"unclaim", "disclaim"}, "ngf.command.unclaim", "commands.f-unclaim", false, 2),
    CLAIM(new String[]{"claim"}, "ngf.command.claim", "commands.f-claim", false, 2),
    CREATE(new String[]{"create", "new"}, "ngf.command.create", "commands.f-create", true, 2),
    DISBAND(new String[]{"disband", "delete", "del", "remove"}, "ngf.command.disband", "commands.f-disband", false, 1),
    INVITE(new String[]{"invite", "add"}, "ngf.command.invite", "commands.f-invite", true, 2),
    KICK(new String[]{"kick"}, "ngf.command.kick", "commands.f-kick", true, 2);


    private final String[] aliases;
    private final String permission;
    private final String commandLangPath;
    private final boolean checkArgsLength;
    private final int requiredArgsLength;


    SubCommandType(String[] aliases, String permission, String commandLangPath, boolean checkArgsLength, int requiredArgsLength) {
        this.aliases = aliases;
        this.permission = permission;
        this.commandLangPath = commandLangPath;
        this.checkArgsLength = checkArgsLength;
        this.requiredArgsLength = requiredArgsLength;
    }

    public static @Nullable SubCommandType getByAlias(String alias) {
        for (SubCommandType subCommandType : SubCommandType.values()) {
            for (String subCommandAlias : subCommandType.getAliases()) {
                if (subCommandAlias.equalsIgnoreCase(alias)) {
                    return subCommandType;
                }
            }
        }

        return null;
    }
}
