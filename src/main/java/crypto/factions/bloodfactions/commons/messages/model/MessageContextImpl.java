package crypto.factions.bloodfactions.commons.messages.model;

import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class MessageContextImpl implements MessageContext {

    public MessageContextImpl(@NotNull FPlayer player, @NotNull String message) {
        this.players = new HashSet<>();
        this.players.add(player);
        this.message = message;
    }

    public MessageContextImpl(@NotNull Faction faction, @NotNull String message){
        this.faction = faction;
        this.players = faction.getOnlineMembers();
        this.message = message;
    }

    @NotNull
    private final Set<FPlayer> players;

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

    @Nullable
    private FChunk chunk;

    @Nullable
    private FactionRank rank;

    @Nullable
    private PermissionType permission;

}
