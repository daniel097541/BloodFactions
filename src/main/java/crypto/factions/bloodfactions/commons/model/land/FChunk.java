package crypto.factions.bloodfactions.commons.model.land;

import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.impl.FChunkImpl;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface FChunk {

    Set<FChunk> getChunksInRadius(int radius);

    default @NotNull String getId() {
        return "FCHUNK_" + this.getWorldId().toString() + "_" + this.getX() + "_" + this.getZ();
    }

    default @NotNull String chunkToString() {
        Chunk bukkitChunk = this.getBukkitChunk();

        if (Objects.nonNull(bukkitChunk)) {

            World world = bukkitChunk.getWorld();
            String name = world.getName();
            int x = this.getX();
            int z = this.getZ();
            return "World: " + name + ", X: " + x + ", Z: " + z;
        }
        return "";
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

    default @NotNull Set<FPlayer> getPlayersAtChunk() {
        return NextGenFactionsAPI
                .getOnlinePlayers()
                .stream()
                .filter(player -> Objects.equals(player.getChunk(), this))
                .collect(Collectors.toSet());
    }
}
