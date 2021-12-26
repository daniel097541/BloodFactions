package crypto.anguita.nextgenfactions.commons.messages.handler;

import crypto.anguita.nextgenfactions.commons.messages.model.MessageContext;
import crypto.anguita.nextgenfactions.commons.messages.model.MessagePlaceholder;
import crypto.anguita.nextgenfactions.commons.messages.util.MessageUtils;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface MessageContextHandler {

    default void handle(MessageContext messageContext) {

        String message = messageContext.getMessage();
        FPlayer player = messageContext.getPlayer();
        Faction faction = messageContext.getFaction();
        FPlayer targetPlayer = messageContext.getTargetPlayer();
        Faction targetFaction = messageContext.getTargetFaction();

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

        Player bukkitPlayer = player.getBukkitPlayer();

        // Send the message.
        if (Objects.nonNull(bukkitPlayer)) {
            MessageUtils.sendMessage(message, bukkitPlayer, placeHolders);
        }
    }

}
