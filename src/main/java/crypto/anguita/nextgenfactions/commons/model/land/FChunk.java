package crypto.anguita.nextgenfactions.commons.model.land;

import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public interface FChunk {

    default @NotNull String getId() {
        return "FCHUNK_" + this.getWorldId().toString() + "_" + this.getX() + "_" + this.getZ();
    }

    UUID getWorldId();

    int getX();

    int getZ();

    default @Nullable Chunk getBukkitChunk() {
        World world = Bukkit.getWorld(this.getWorldId());
        if (Objects.nonNull(world)) {
            return world.getChunkAt(this.getX(), this.getZ());
        }
        return null;
    }

    default @NotNull Faction getFactionAt() {
        return NextGenFactionsAPI.getFactionAtChunk(this);
    }
}
