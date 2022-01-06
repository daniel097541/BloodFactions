package crypto.factions.bloodfactions.commons.exceptions;

public class NoFactionForFactionLessException extends Exception{

    public NoFactionForFactionLessException() {
        super("There is no faction for faction less players in your system.yaml configuration file.");
    }
}
