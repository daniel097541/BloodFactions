package crypto.factions.bloodfactions.backend.db;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Singleton
public class DBMigrationManagerImpl implements DBMigrationManager {

    private final DBManager dbManager;

    @Inject
    public DBMigrationManagerImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        this.load();
    }

    private void loadFactionsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS factions ( " +
                " id VARCHAR[36] PRIMARY KEY, " +
                " name VARCHAR[50] NOT NULL, " +
                " system_faction BOOLEAN DEFAULT false, " +
                " owner_id VARCHAR[36] NOT NULL," +
                " creation_date REAL DEFAULT (datetime('now', 'localtime')), " +
                " FOREIGN KEY (owner_id) REFERENCES players(id)" +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadPlayersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS players ( " +
                " id VARCHAR[36] PRIMARY KEY, " +
                " name VARCHAR[50] NOT NULL, " +
                " power SMALLINT NOT NULL DEFAULT 0, " +
                " flying BOOLEAN NOT NULL DEFAULT 0, " +
                " auto_flying BOOLEAN NOT NULL DEFAULT 0" +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadFactionPlayersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_faction_players ( " +
                " faction_id VARCHAR[36], " +
                " player_id VARCHAR[36], " +
                " invited_by VARCHAR[36], " +
                " joined_date REAL DEFAULT (datetime('now', 'localtime')), " +
                " FOREIGN KEY (player_id) REFERENCES players(id), " +
                " FOREIGN KEY (faction_id) REFERENCES factions(id), " +
                " FOREIGN KEY (invited_by) REFERENCES players(id), " +
                " PRIMARY KEY(player_id, faction_id) " +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadFactionClaimsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS faction_claims(" +
                " faction_id VARCHAR[36], " +
                " claim_id VARCHAR[255], " +
                " world_id VARCHAR[36] NOT NULL," +
                " x INTEGER NOT NULL," +
                " z INTEGER NOT NULL," +
                " claimed_date REAL DEFAULT (datetime('now', 'localtime'))," +
                " claimed_by VARCHAR[36], " +
                " FOREIGN KEY (faction_id) REFERENCES factions(id)," +
                " FOREIGN KEY (claimed_by) REFERENCES players(id), " +
                " PRIMARY KEY(faction_id, claim_id) " +
                ");";

        this.dbManager.executeUpdate(sql);
    }


    private void insertPermission(PermissionType permissionType) {
        String sql = "INSERT INTO ref_permissions (id, name) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = this.dbManager.getPreparedStatement(sql)) {
            preparedStatement.setInt(1, permissionType.getId());
            preparedStatement.setString(2, permissionType.name());
            preparedStatement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    private void loadPermissionsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS ref_permissions(" +
                " id INTEGER PRIMARY KEY, " +
                " name VARCHAR[255] UNIQUE NOT NULL " +
                ");";

        this.dbManager.executeUpdate(sql);

        for (PermissionType permissionType : PermissionType.values()) {
            this.insertPermission(permissionType);
        }

    }

    private void loadRolesTable() {

        String sql = "CREATE TABLE IF NOT EXISTS roles(" +
                " id VARCHAR[36] NOT NULL, " +
                " name VARCHAR[255] NOT NULL, " +
                " default_role BOOLEAN DEFAULT false, " +
                " faction_id VARCHAR[36] NOT NULL, " +
                " FOREIGN KEY (faction_id) REFERENCES factions(id)," +
                " UNIQUE (name, faction_id), " +
                " PRIMARY KEY (id) " +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadPlayerPermissionsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_player_permissions(" +
                " player_id VARCHAR[36], " +
                " permission_id INTEGER, " +
                " faction_id VARCHAR[36]," +
                " UNIQUE (player_id, permission_id), " +
                " FOREIGN KEY (permission_id) REFERENCES ref_permissions(id)," +
                " FOREIGN KEY (faction_id) REFERENCES factions(id)," +
                " FOREIGN KEY (player_id) REFERENCES players(id)" +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadRolesPermissionsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_role_permissions(" +
                " role_id VARCHAR[36], " +
                " permission_id INTEGER, " +
                " PRIMARY KEY (role_id, permission_id), " +
                " FOREIGN KEY (permission_id) REFERENCES ref_permissions(id)" +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadPlayerRoleTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_player_role(" +
                " role_id VARCHAR[36], " +
                " player_id VARCHAR[36] UNIQUE, " +
                " PRIMARY KEY (role_id, player_id), " +
                " FOREIGN KEY (player_id) REFERENCES players(id)," +
                " FOREIGN KEY (role_id) REFERENCES roles(id)" +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadTeleportsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS factions_tps(" +
                " faction_id VARCHAR[36] NOT NULL," +
                " world_id VARCHAR[36] NOT NULL," +
                " x INTEGER NOT NULL," +
                " y INTEGER NOT NULL," +
                " z INTEGER NOT NULL," +
                " created_by VARCHAR[36] NOT NULL," +
                " is_core BOOLEAN NOT NULL DEFAULT false," +
                " PRIMARY KEY (faction_id, world_id, x, y, z)," +
                " FOREIGN KEY (faction_id) REFERENCES factions(id)," +
                " FOREIGN KEY (created_by) REFERENCES players(id));";

        this.dbManager.executeUpdate(sql);
    }

    private void loadInvitationsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS faction_invitations(" +
                " faction_id VARCHAR[36] NOT NULL," +
                " player_id VARCHAR[36] NOT NULL," +
                " inviter_id VARCHAR[36] NOT NULL," +
                " date REAL DEFAULT (datetime('now', 'localtime'))," +
                " PRIMARY KEY (faction_id, player_id)," +
                " FOREIGN KEY (faction_id) REFERENCES factions(id)," +
                " FOREIGN KEY (player_id) REFERENCES players(id)," +
                " FOREIGN KEY (inviter_id) REFERENCES players(id));";

        this.dbManager.executeUpdate(sql);
    }

    private void loadRelationsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS faction_relations(" +
                " id VARCHAR[36] NOT NULL," +
                " name VARCHAR[255] NOT NULL," +
                " color VARCHAR[5] NOT NULL," +
                " default_relation BOOLEAN DEFAULT 0," +
                " PRIMARY KEY (id)," +
                " UNIQUE (name));";

        this.dbManager.executeUpdate(sql);
    }

    private void loadAsFactionRelationsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS as_faction_relations(" +
                " faction_id VARCHAR[36] NOT NULL," +
                " other_faction_id VARCHAR[36] NOT NULL, " +
                " relation_id VARCHAR[36] NOT NULL, " +
                " PRIMARY KEY (faction_id, other_faction_id, relation_id)," +
                " FOREIGN KEY (faction_id) REFERENCES factions(id), " +
                " FOREIGN KEY (other_faction_id) REFERENCES factions(id)," +
                " FOREIGN KEY (relation_id) REFERENCES faction_relations(id));";
        boolean created = this.dbManager.executeUpdate(sql);
    }

    private void loadVersion() {
        String sql = "SELECT sqlite_version() AS version;";
        try (Statement statement = this.dbManager.getStatement()) {
            try (ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) {
                    String version = rs.getString("version");
                    Logger.logInfo("SQLite version: " + version);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load() {
        this.loadVersion();
        this.loadPlayersTable();
        this.loadFactionsTable();
        this.loadFactionPlayersTable();
        this.loadFactionClaimsTable();
        this.loadPermissionsTable();
        this.loadRolesTable();
        this.loadPlayerPermissionsTable();
        this.loadRolesPermissionsTable();
        this.loadPlayerRoleTable();
        this.loadTeleportsTable();
        this.loadInvitationsTable();
        this.loadAsFactionRelationsTable();
    }
}
