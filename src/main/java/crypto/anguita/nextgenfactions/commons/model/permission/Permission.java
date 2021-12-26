package crypto.anguita.nextgenfactions.commons.model.permission;

public interface Permission {
    boolean isAllowed();

    Action getAction();
}
