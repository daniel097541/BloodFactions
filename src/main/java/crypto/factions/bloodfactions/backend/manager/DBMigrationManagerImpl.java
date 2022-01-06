package crypto.factions.bloodfactions.backend.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Singleton
public class DBMigrationManagerImpl implements DBMigrationManager{

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
                " power SMALLINT NOT NULL DEFAULT 0 " +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadFactionPlayersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_faction_players ( " +
                " faction_id VARCHAR[36], " +
                " player_id VARCHAR[36], " +
                " invited_by VARCHAR[36], " +
                " joined_date REAL DEFAULT (datetime('now', 'localtime')), " +
//                " UNIQUE (faction_id, player_id), " +
                " FOREIGN KEY (player_id) REFERENCES players(id), " +
                " FOREIGN KEY (faction_id) REFERENCES factions(id), " +
                " FOREIGN KEY (invited_by) REFERENCES players(id), " +
                " PRIMARY KEY(player_id, faction_id) " +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadClaimsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS claims ( " +
                " id VARCHAR[255] PRIMARY KEY, " +
                " world_id VARCHAR[36] NOT NULL, " +
                " x BIGINT NOT NULL, " +
                " z BIGINT NOT NULL " +
                ");";

        this.dbManager.executeUpdate(sql);
    }

    private void loadFactionClaimsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS as_faction_claims(" +
                " faction_id VARCHAR[36], " +
                " claim_id VARCHAR[255], " +
                " claimed_date REAL DEFAULT (datetime('now', 'localtime'))," +
                " claimed_by VARCHAR[36], " +
//                " UNIQUE (faction_id, claim_id), " +
                " FOREIGN KEY (faction_id) REFERENCES factions(id)," +
                " FOREIGN KEY (claim_id) REFERENCES claims(id)," +
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

    private void loadTeleportsTable(){

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

    private void load() {
        this.loadPlayersTable();
        this.loadFactionsTable();
        this.loadClaimsTable();
        this.loadFactionPlayersTable();
        this.loadFactionClaimsTable();
        this.loadPermissionsTable();
        this.loadRolesTable();
        this.loadPlayerPermissionsTable();
        this.loadRolesPermissionsTable();
        this.loadPlayerRoleTable();
        this.loadTeleportsTable();
    }
}
