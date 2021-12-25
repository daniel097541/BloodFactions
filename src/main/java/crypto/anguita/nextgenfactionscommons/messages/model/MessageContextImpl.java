package crypto.anguita.nextgenfactionscommons.messages.model;

import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class MessageContextImpl implements MessageContext {


    @NotNull
    private final FPlayer player;

    @NotNull
    private final String message;

    @Nullable
    private Faction faction;

    @Nullable
    private Faction targetFaction;

    @Nullable
    private FPlayer targetPlayer;

    @Nullable
    private FPlayer otherPlayer;

}
