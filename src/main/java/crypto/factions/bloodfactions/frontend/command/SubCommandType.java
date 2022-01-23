package crypto.factions.bloodfactions.frontend.command;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public enum SubCommandType {

    RANK(new String[]{"role", "roles", "ranks", "rank"}, "ngf.command.ranks", "commands.f-ranks", false, 2),
    AUTO_FLY(new String[]{"autofly", "autoflight"}, "ngf.command.autofly", "commands.f-auto-fly", false, 2),
    FLY(new String[]{"fly", "flight"}, "ngf.command.fly", "commands.f-fly", false, 2),
    SHOW(new String[]{"show", "info"}, "ngf.command.show", "commands.f-show", false, 2),
    HOME(new String[]{"core", "home"}, "ngf.command.home", "commands.f-home", false, 2),
    UN_CLAIM_ALL(new String[]{"unclaimall", "disclaimall"}, "ngf.command.unclaimall", "commands.f-unclaimall", false, 2),
    UN_CLAIM(new String[]{"unclaim", "disclaim"}, "ngf.command.unclaim", "commands.f-unclaim", false, 2),
    CLAIM(new String[]{"claim"}, "ngf.command.claim", "commands.f-claim", false, 2),
    CREATE(new String[]{"create", "new"}, "ngf.command.create", "commands.f-create", true, 2),
    DISBAND(new String[]{"disband", "delete", "del", "remove"}, "ngf.command.disband", "commands.f-disband", false, 1),
    INVITATIONS(new String[]{"invitations", "invite", "join", "decline"}, "ngf.command.invitations", "commands.f-invite", false, 2),
    LEAVE(new String[]{"leave"}, "ngf.command.leave", "commands.f-leave", false, 2),
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
