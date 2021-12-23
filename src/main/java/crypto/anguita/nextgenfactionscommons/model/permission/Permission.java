package crypto.anguita.nextgenfactionscommons.model.permission;

public interface Permission {
    boolean isAllowed();

    Action getAction();
}
