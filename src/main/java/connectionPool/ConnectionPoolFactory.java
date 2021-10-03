package connectionPool;

import util.ConfigUtil;
import util.ConnectionInfo;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectionPoolFactory {

    public ConnectionPool createConnectionPool() {
        ConnectionInfo connectionInfo = ConfigUtil.readConnectionInfo();

        ConnectionPool pool = new ConnectionPool();
        pool.setDriverClass("org.postgresql.Driver");
        pool.setUrl(connectionInfo.getUrl());
        pool.setUser(connectionInfo.getUser());
        pool.setPassword(connectionInfo.getPass());
        pool.setSize(2);

        pool.createPool();

        return pool;
    }

}
