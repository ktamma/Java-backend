package dao;

import util.ConfigUtil;
import util.ConnectionInfo;
import util.FileUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class CreateDatabase
{
    private static Connection getConnection(ConnectionInfo connectionInfo) throws SQLException {
        return DriverManager.getConnection(
                connectionInfo.getUrl(),
                connectionInfo.getUser(),
                connectionInfo.getPass());
    }

    public static void createSchema() throws SQLException {
        ConnectionInfo connectionInfo = ConfigUtil.readConnectionInfo();

        Connection conn = getConnection(connectionInfo);

        try (conn; Statement stmt = conn.createStatement()) {

            String sql1 = FileUtil.readFileFromClasspath("schema.sql");

            stmt.executeUpdate(sql1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
