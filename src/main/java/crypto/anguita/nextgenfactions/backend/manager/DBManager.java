package crypto.anguita.nextgenfactions.backend.manager;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBManager {

    private final String url = "jdbc:sqlite:C:/sqlite/db/next_gen_factions.db";
    private final Connection connection;

    @SneakyThrows
    public DBManager() {
        this.connection = DriverManager.getConnection(url);
        this.load();
    }

    @SneakyThrows
    public Statement getStatement() {
        return this.connection.createStatement();
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
                " creation_date DATE NOT NULL DEFAULT (datetime('now','localtime')), " +
                ");";

        this.executeUpdate(sql);
    }

    private void loadPlayersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS players ( " +
                " id VARCHAR[36] PRIMARY KEY, " +
                " name VARCHAR[50] NOT NULL, " +
                " creation_date DATE NOT NULL DEFAULT (datetime('now','localtime')), " +
                " power SMALLINT NOT NULL DEFAULT 0 " +
                ");";

        this.executeUpdate(sql);
    }

    private void loadFactionPlayersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_faction_players ( " +
                " faction_id VARCHAR[36] FOREIGN KEY REFERENCES factions(id), " +
                " player_id VARCHAR[36] FOREIGN KEY REFERENCES players(id), " +
                " PRIMARY KEY (faction_id, player_id), " +
                " invited_by VARCHAR[36] FOREIGN KEY REFERENCES players(id), " +
                " joined_date DATE NOT NULL DEFAULT (datetime('now','localtime'))" +
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
                " faction_id VARCHAR[36] FOREIGN KEY REFERENCES factions(id), " +
                " claim_id VARCHAR[255] FOREIGN KEY REFERENCES claims(id), " +
                " PRIMARY KEY (faction_id, claim_id), " +
                " claimed_date DATE NOT NULL DEFAULT (datetime('now', 'localtime'))," +
                " claimed_by VARCHAR[36] FOREIGN KEY REFERENCES players(id)" +
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
