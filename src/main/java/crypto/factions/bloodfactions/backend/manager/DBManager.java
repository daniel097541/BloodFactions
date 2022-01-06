package crypto.factions.bloodfactions.backend.manager;

import lombok.SneakyThrows;

import javax.inject.Singleton;
import java.sql.*;

@Singleton
public class DBManager {

    private final String URL = "jdbc:sqlite:next_gen_factions.db";
    private final Connection connection;

    @SneakyThrows
    public DBManager() {
        Class.forName("org.sqlite.JDBC").newInstance();
        this.connection = DriverManager.getConnection(URL);
    }

    @SneakyThrows
    public Statement getStatement() {
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


}
