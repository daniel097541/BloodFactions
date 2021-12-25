package crypto.anguita.nextgenfactionscommons.pipeline;

import crypto.anguita.nextgenfactionscommons.events.PermissionEvent;

import java.util.LinkedList;

public interface PermissionsPipeline {

    PermissionEvent getEvent();

    LinkedList<PermissionsPipe> getPipes();

    /**
     * Checks all pipes to check players permissions.
     */
    default void check() {

        for (PermissionsPipe pipe : this.getPipes()) {

            // Run check
            pipe.check();

            // Succeed?
            boolean success = pipe.isSuccess();

            // Pipe failed.
            if (!success) {

                // Check if the failure is due to no permissions.
                boolean noPermission = pipe.isNoPermission();
                if (noPermission) {

                    // Cancel event and set the failure message.
                    this.getEvent().setCancelled(true);
                    this.getEvent().setPermissionRestricted(true);
                    this.getEvent().setFailureMessage(pipe.getNoPermissionMessage());
                }

                // Stop pipeline.
                break;
            }
        }
    }

}
