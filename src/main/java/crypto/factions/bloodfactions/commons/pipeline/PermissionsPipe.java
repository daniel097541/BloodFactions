package crypto.factions.bloodfactions.commons.pipeline;

import crypto.factions.bloodfactions.commons.model.permission.Action;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public interface PermissionsPipe {

    FPlayer getPlayer();

    Action getAction();

    String getNoPermissionMessage();

    boolean isSuccess();

    void setSuccess(boolean success);

    boolean isNoPermission();

    void setNoPermission(boolean noPermission);

    /**
     * Checks if the player has permissions.
     * @return
     */
    default boolean hasPermission() {
        FPlayer player = this.getPlayer();
        return player.hasPermission(this.getAction());
    }

    /**
     * Checks the pipeline.
     */
    default void check() {
        boolean hasPermission = this.hasPermission();
        if (!hasPermission) {
            this.setSuccess(false);
            this.setNoPermission(true);
        } else {
            this.setSuccess(true);
            this.setNoPermission(false);
        }
    }

}
