package crypto.anguita.nextgenfactions.commons.model.land;

import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.impl.FChunkImpl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public interface FLocation {

    default @NotNull String getId() {
        return "FLOCATION_" + this.getWorldId().toString() + "_" + this.getX() + "_" + this.getY() + "_" + this.getZ();
    }

    UUID getWorldId();

    int getX();

    int getY();

    int getZ();

    default @Nullable Location getBukkitLocation() {
        World world = Bukkit.getWorld(this.getWorldId());
        if (Objects.nonNull(world)) {
            return world.getBlockAt(this.getX(), this.getY(), this.getZ()).getLocation();
        }
        return null;
    }

    default @Nullable FChunk getChunk(){
        Location location = this.getBukkitLocation();
        if (Objects.nonNull(location)) {
            Chunk chunk = location.getChunk();
            return FChunkImpl.fromChunk(chunk);
        }
        return null;
    }

    default @Nullable Faction getFactionAt(){
        FChunk chunk = this.getChunk();
        if (Objects.nonNull(chunk)){
            return chunk.getFactionAt();
        }
        return null;
    }

}
