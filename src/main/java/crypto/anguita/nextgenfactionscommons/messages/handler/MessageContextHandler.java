package crypto.anguita.nextgenfactionscommons.messages.handler;

import crypto.anguita.nextgenfactionscommons.messages.model.MessageContext;
import crypto.anguita.nextgenfactionscommons.messages.model.MessagePlaceholder;
import crypto.anguita.nextgenfactionscommons.messages.util.MessageUtils;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

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

        @Nullable Player bukkitPlayer = player.getBukkitPlayer();
        if (Objects.nonNull(bukkitPlayer)) {
            MessageUtils.sendMessage(message, bukkitPlayer, placeHolders);
        }
    }

}
