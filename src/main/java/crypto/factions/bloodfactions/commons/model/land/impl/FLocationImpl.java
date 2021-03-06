package crypto.factions.bloodfactions.commons.model.land.impl;

import crypto.factions.bloodfactions.commons.model.land.FLocation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class FLocationImpl implements FLocation {
    @NotNull
    private final UUID worldId;

    private final int x;
    private final int y;
    private final int z;

    public static @Nullable FLocation fromString(String id) {
        String[] split = id.split("_");

        String identifier = split[0];
        if (split.length == 5 && identifier.equals("FLOCATION")) {
            UUID worldId = UUID.fromString(split[1]);
            int x = Integer.getInteger(split[2]);
            int y = Integer.getInteger(split[3]);
            int z = Integer.getInteger(split[4]);

            return new FLocationImpl(worldId, x, y, z);
        }
        return null;
    }

    public static FLocation fromLocation(Location location) {
        return new FLocationImpl(Objects.requireNonNull(location.getWorld()).getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
