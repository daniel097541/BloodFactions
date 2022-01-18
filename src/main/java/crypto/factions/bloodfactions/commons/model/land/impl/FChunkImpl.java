package crypto.factions.bloodfactions.commons.model.land.impl;

import crypto.factions.bloodfactions.commons.model.land.FChunk;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
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

    public Set<FChunk> getChunksInRadius(int radius) {
        UUID worldId = this.getWorldId();
        int length = (radius * 2) + 1;
        Set<FChunk> chunks = new HashSet<>(length * length);
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                chunks.add(new FChunkImpl(worldId, x, z));
            }
        }
        return chunks;
    }

    public static @NotNull FChunk fromChunk(@NotNull Chunk chunk) {
        return new FChunkImpl(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
    }
}
