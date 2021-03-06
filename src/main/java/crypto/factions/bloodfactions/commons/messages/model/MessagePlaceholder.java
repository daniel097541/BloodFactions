package crypto.factions.bloodfactions.commons.messages.model;

import lombok.Getter;

public enum MessagePlaceholder {

    PERMISSION("{permission_name}"),
    RANK("{rank_name}"),
    CHUNK("{chunk}"),
    FACTION_NAME("{faction_name}"),
    PLAYER_NAME("{player_name}"),
    TARGET_FACTION_NAME("{target_faction_name}"),
    TARGET_PLAYER_NAME("{target_player_name}");

    @Getter
    private final String placeholder;

    MessagePlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

}
