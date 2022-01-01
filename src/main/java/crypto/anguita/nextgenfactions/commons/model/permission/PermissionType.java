package crypto.anguita.nextgenfactions.commons.model.permission;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public enum PermissionType {
    DISBAND(1),
    INVITE(2),
    KICK(3),
    BAN(4),
    CLAIM(5),
    UN_CLAIM(6),
    MULTI_CLAIM(7),
    MULTI_UN_CLAIM(8);

    @Getter
    private final int id;

    PermissionType(int id) {
        this.id = id;
    }

    public static @Nullable PermissionType fromId(int id) {
        for (PermissionType permissionType : PermissionType.values()) {
            if (permissionType.id == id) {
                return permissionType;
            }
        }
        return null;
    }

}
