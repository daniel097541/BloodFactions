package crypto.factions.bloodfactions.backend.db;

import lombok.SneakyThrows;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Singleton
public class DBManager {

    private final String URL = "jdbc:sqlite:blood_factions.db";
    private final Connection connection;

    @SneakyThrows
    public DBManager() {
        Class.forName("org.sqlite.JDBC").newInstance();

        this.connection = DriverManager.getConnection(this.URL);
//        this.connection.setAutoCommit(false);
    }

    @SneakyThrows
    public Statement getStatement() {
        return this.connection.createStatement();
    }

    @SneakyThrows
    public PreparedStatement getPreparedStatement(String sql) {
        return this.connection.prepareStatement(sql);
    }

    public boolean executeUpdate(String sql) {
        try (Statement statement = this.getStatement()) {
            return statement.executeUpdate(sql) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
