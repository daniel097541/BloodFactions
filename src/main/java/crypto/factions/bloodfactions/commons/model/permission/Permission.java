package crypto.factions.bloodfactions.commons.model.permission;

public interface Permission {
    boolean isAllowed();

    Action getAction();
}
