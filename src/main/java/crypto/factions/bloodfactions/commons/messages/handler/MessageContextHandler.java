package crypto.factions.bloodfactions.commons.messages.handler;

import crypto.factions.bloodfactions.commons.config.lang.LangConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessagePlaceholder;
import crypto.factions.bloodfactions.commons.messages.util.MessageUtils;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface MessageContextHandler {

    default void handle(MessageContext messageContext) {

        String message = messageContext.getMessage();
        Set<FPlayer> players = messageContext.getPlayers();
        Faction faction = messageContext.getFaction();
        FPlayer targetPlayer = messageContext.getTargetPlayer();
        Faction targetFaction = messageContext.getTargetFaction();
        FactionRank rank = messageContext.getRank();
        FChunk chunk = messageContext.getChunk();
        PermissionType permissionType = messageContext.getPermission();

        Map<String, String> placeHolders = new HashMap<>();

        if (message.contains(MessagePlaceholder.FACTION_NAME.getPlaceholder()) && Objects.nonNull(faction)) {
            placeHolders.put(MessagePlaceholder.FACTION_NAME.getPlaceholder(), faction.getName());
        }
        if (message.contains(MessagePlaceholder.TARGET_FACTION_NAME.getPlaceholder()) && Objects.nonNull(targetFaction)) {
            placeHolders.put(MessagePlaceholder.TARGET_FACTION_NAME.getPlaceholder(), targetFaction.getName());
        }
        if (message.contains(MessagePlaceholder.TARGET_PLAYER_NAME.getPlaceholder()) && Objects.nonNull(targetPlayer)) {
            placeHolders.put(MessagePlaceholder.TARGET_PLAYER_NAME.getPlaceholder(), targetPlayer.getName());
        }
        if (message.contains(MessagePlaceholder.CHUNK.getPlaceholder()) && Objects.nonNull(chunk)) {
            placeHolders.put(MessagePlaceholder.CHUNK.getPlaceholder(), chunk.chunkToString());
        }
        if (message.contains(MessagePlaceholder.RANK.getPlaceholder()) && Objects.nonNull(rank)) {
            placeHolders.put(MessagePlaceholder.RANK.getPlaceholder(), rank.getName());
        }
        if(message.contains(MessagePlaceholder.PERMISSION.getPlaceholder()) && Objects.nonNull(permissionType)){
            placeHolders.put(MessagePlaceholder.PERMISSION.getPlaceholder(), permissionType.name());
        }

        for (FPlayer player : players) {
            Player bukkitPlayer = player.getBukkitPlayer();
            // Send the message.
            if (Objects.nonNull(bukkitPlayer)) {
                String prefix = (String) LangConfig.getInstance().get(LangConfigItems.GLOBAL_PREFIX);
                MessageUtils.sendMessage(prefix + message, bukkitPlayer, placeHolders);
            }
        }
    }

}
