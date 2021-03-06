package crypto.factions.bloodfactions.commons.model.permission;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public enum PermissionType {
    ALL(0),
    DISBAND(1),
    INVITE(2),
    KICK(3),
    BAN(4),
    CLAIM(5),
    UN_CLAIM(6),
    MULTI_CLAIM(7),
    MULTI_UN_CLAIM(8),
    CHANGE_ROLES(9),
    SET_CORE(10),
    FLY(11),
    DELETE_ROLE(12),
    CREATE_ROLE(13),
    UN_CLAIM_ALL(14),
    PLACE_BLOCKS(15),
    BREAK_BLOCKS(16);

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

    public static @Nullable PermissionType fromName(String name) {
        for (PermissionType permissionType : PermissionType.values()) {
            if (permissionType.name().equals(name)) {
                return permissionType;
            }
        }
        return null;
    }
}
