package crypto.anguita.nextgenfactionscommons.config;

public enum ConfigType {
    LANG("lang.yaml"),
    SYSTEM("system.yaml");

    private final String path;

    ConfigType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
