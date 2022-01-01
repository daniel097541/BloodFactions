package crypto.anguita.nextgenfactions.backend.manager;

import lombok.SneakyThrows;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Singleton
public class DBManager {

    private final String URL = "jdbc:sqlite:next_gen_factions.db";
    private final Connection connection;

    @SneakyThrows
    public DBManager() {
        Class.forName("org.sqlite.JDBC").newInstance();
        this.connection = DriverManager.getConnection(URL);
        this.load();
    }

    @SneakyThrows
    public Statement getStatement(){
        return this.connection.createStatement();
    }

    @SneakyThrows
    public PreparedStatement getPreparedStatement(String sql) {
        return this.connection.prepareStatement(sql);
    }

    public void executeUpdate(String sql) {
        try (Statement statement = this.getStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFactionsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS factions ( " +
                " id VARCHAR[36] PRIMARY KEY, " +
                " name VARCHAR[50] NOT NULL, " +
                " system_faction BOOLEAN DEFAULT false, " +
                " creation_date REAL DEFAULT (datetime('now', 'localtime')) " +
                ");";

        this.executeUpdate(sql);
    }

    private void loadPlayersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS players ( " +
                " id VARCHAR[36] PRIMARY KEY, " +
                " name VARCHAR[50] NOT NULL, " +
                " power SMALLINT NOT NULL DEFAULT 0 " +
                ");";

        this.executeUpdate(sql);
    }

    private void loadFactionPlayersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_faction_players ( " +
                " faction_id VARCHAR[36], " +
                " player_id VARCHAR[36], " +
                " invited_by VARCHAR[36], " +
                " joined_date REAL DEFAULT (datetime('now', 'localtime')), " +
                " UNIQUE (faction_id, player_id), " +
                " FOREIGN KEY (player_id) REFERENCES players(id), " +
                " FOREIGN KEY (faction_id) REFERENCES factions(id), " +
                " FOREIGN KEY (invited_by) REFERENCES players(id) " +
                ");";

        this.executeUpdate(sql);
    }

    private void loadClaimsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS claims ( " +
                " id VARCHAR[255] PRIMARY KEY, " +
                " world_id VARCHAR[36] NOT NULL, " +
                " x BIGINT NOT NULL, " +
                " z BIGINT NOT NULL " +
                ");";

        this.executeUpdate(sql);
    }

    private void loadFactionClaimsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_faction_claims(" +
                " faction_id VARCHAR[36], " +
                " claim_id VARCHAR[255], " +
                " claimed_date REAL DEFAULT (datetime('now', 'localtime'))," +
                " claimed_by VARCHAR[36], " +
                " UNIQUE (faction_id, claim_id), " +
                " FOREIGN KEY (faction_id) REFERENCES factions(id)," +
                " FOREIGN KEY (claim_id) REFERENCES claims(id)," +
                " FOREIGN KEY (claimed_by) REFERENCES players(id)" +
                ");";

        this.executeUpdate(sql);
    }

    private void load() {
        this.loadFactionsTable();
        this.loadPlayersTable();
        this.loadClaimsTable();
        this.loadFactionPlayersTable();
        this.loadFactionClaimsTable();
    }
}
