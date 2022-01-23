package crypto.factions.bloodfactions.api;

import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.impl.FChunkImpl;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BloodFactionsAPI {


    public static Faction getFaction(String factionName) {
        return ContextHandler.getFactionByName(factionName);
    }

    public static Faction getFaction(UUID id) {
        return ContextHandler.getFaction(id);
    }

    public static Faction getFactionAtLocation(Location location) {
        return getFactionAtChunk(location.getChunk());
    }

    public static Faction getFactionAtChunk(Chunk chunk) {
        FChunk fChunk = FChunkImpl.fromChunk(chunk);
        return ContextHandler.getFactionAtChunk(fChunk);
    }

    public static FPlayer getPlayerByName(String playerName){
        return ContextHandler.getPlayerByName(playerName);
    }

    public static FPlayer getPlayer(Player player){
        return ContextHandler.getPlayer(player);
    }


}
