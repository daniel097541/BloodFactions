package crypto.anguita.nextgenfactionscommons.pipeline;

import crypto.anguita.nextgenfactionscommons.events.PermissionEvent;
import crypto.anguita.nextgenfactionscommons.model.permission.Action;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;

public interface PermissionsPipe {

    PermissionEvent getEvent();

    Action getAction();

    String getNoPermissionMessage();

    default boolean hasPermission() {
        FPlayer player = this.getEvent().getPlayer();
        return player.hasPermission(this.getAction());
    }

    default void cancel() {
        this.getEvent().setCancelled(true);
        this.getEvent().setPermissionRestricted(true);
        this.getEvent().setNoPermissionMessage(this.getNoPermissionMessage());
    }

    default void checkPipe(){
        boolean hasPermission = this.hasPermission();
        if (!hasPermission){
            this.cancel();
        }
    }

}
