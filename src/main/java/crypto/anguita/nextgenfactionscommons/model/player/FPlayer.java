package crypto.anguita.nextgenfactionscommons.model.player;

import crypto.anguita.nextgenfactionscommons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactionscommons.messages.handler.MessageContextHandler;
import crypto.anguita.nextgenfactionscommons.messages.model.MessageContext;
import crypto.anguita.nextgenfactionscommons.messages.model.MessageContextImpl;
import crypto.anguita.nextgenfactionscommons.model.NextGenFactionEntity;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.permission.Action;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FPlayer extends NextGenFactionEntity {

    default void sms(MessageContext context){
        new MessageContextHandler(){}.handle(context);
    }

    default void sms(String message){
        MessageContext messageContext = new MessageContextImpl(this, message);
        messageContext.setFaction(this.getFaction());
        this.sms(messageContext);
    }

    default void sms(String message, @NotNull Faction targetFaction){
        MessageContext messageContext = new MessageContextImpl(this, message);
        messageContext.setFaction(this.getFaction());
        messageContext.setTargetFaction(targetFaction);
        this.sms(messageContext);
    }

    default void sms(String message, @NotNull FPlayer targetPlayer){
        MessageContext messageContext = new MessageContextImpl(this, message);
        messageContext.setFaction(this.getFaction());
        messageContext.setTargetPlayer(targetPlayer);
        this.sms(messageContext);
    }

    default void sms(String message, @NotNull Faction targetFaction, @NotNull FPlayer targetPlayer){
        MessageContext messageContext = new MessageContextImpl(this, message);
        messageContext.setFaction(this.getFaction());
        messageContext.setTargetPlayer(targetPlayer);
        messageContext.setTargetFaction(targetFaction);
        this.sms(messageContext);
    }

    /**
     * Gets the Bukkit offline player.
     * @return
     */
    default @NotNull OfflinePlayer getBukkitOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.getId());
    }

    /**
     * Gets the Bukkit player if online.
     * @return
     */
    default @Nullable Player getBukkitPlayer() {
        OfflinePlayer offlinePlayer = this.getBukkitOfflinePlayer();
        return offlinePlayer.getPlayer();
    }

    /**
     * Gets the Faction of the player.
     * @return
     */
    default @NotNull Faction getFaction() {
        return NextGenFactionsAPI.getFactionOfPlayer(this);
    }

    /**
     * Checks if the player has faction.
     * @return
     */
    default boolean hasFaction() {
        return NextGenFactionsAPI.checkIfPlayerHasFaction(this);
    }

    /**
     * Invites a player to the faction.
     * @param player
     * @return
     */
    default boolean invitePlayerToFaction(@NotNull FPlayer player) {
        Faction faction = this.getFaction();
        return faction.invitePlayer(player, this);
    }

    /**
     * Kicks a player from the faction.
     * @param player
     * @return
     */
    default boolean kickPlayerFromFaction(@NotNull FPlayer player) {
        Faction faction = this.getFaction();
        return faction.kickPlayer(player, this);
    }

    /**
     * Checks if the player has permission.
     * @param action
     * @return
     */
    default boolean hasPermission(@NotNull Action action) {
        return NextGenFactionsAPI.checkIfPlayerHasPermission(this, action);
    }

}
