package crypto.factions.bloodfactions.commons.messages.model;

import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface MessageContext {

    @NotNull String getMessage();

    @NotNull Set<FPlayer> getPlayers();

    @Nullable Faction getFaction();

    @Nullable FPlayer getTargetPlayer();

    @Nullable FPlayer getOtherPlayer();

    @Nullable Faction getTargetFaction();

    @Nullable FChunk getChunk();

    @Nullable FactionRank getRank();

    void setRank(FactionRank rank);

    void setFaction(@NotNull Faction faction);

    void setTargetFaction(@NotNull Faction targetFaction);

    void setTargetPlayer(@NotNull FPlayer targetPlayer);

    void setOtherPlayer(@NotNull FPlayer otherPlayer);

    void setChunk(@NotNull FChunk chunk);

    default void send() {
        for (FPlayer player : this.getPlayers()) {
            player.sms(this);
        }
    }

}
