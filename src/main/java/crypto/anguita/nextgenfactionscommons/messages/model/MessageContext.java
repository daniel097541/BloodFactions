package crypto.anguita.nextgenfactionscommons.messages.model;

import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MessageContext {

    @NotNull String getMessage();

    @NotNull FPlayer getPlayer();

    @Nullable Faction getFaction();

    @Nullable FPlayer getTargetPlayer();

    @Nullable Faction getTargetFaction();

    void setFaction(@NotNull Faction faction);

    void setTargetFaction(@NotNull Faction targetFaction);

    void setTargetPlayer(@NotNull FPlayer targetPlayer);

}
