package crypto.anguita.nextgenfactionscommons.model.land.impl;

import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class FChunkImpl implements FChunk {
    @NotNull
    private final UUID worldId;

    private final int x;
    private final int z;

    public static @Nullable FChunk fromString(String id) {
        String[] split = id.split("_");


        String identifier = split[0];
        if (split.length == 4 && identifier.equals("FCHUNK")) {
            UUID worldId = UUID.fromString(split[1]);
            int x = Integer.getInteger(split[2]);
            int z = Integer.getInteger(split[3]);

            return new FChunkImpl(worldId, x, z);
        }
        return null;
    }

    public static @NotNull FChunk fromChunk(@NotNull Chunk chunk) {
        return new FChunkImpl(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
    }
}
