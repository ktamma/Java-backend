package connectionPool;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolExample {

    public static void main(String[] args) throws SQLException {

        ConnectionPool pool = new ConnectionPoolFactory().createConnectionPool();
        System.out.println("!!!!");
        printPoolInfo(pool);

        Connection c1 = pool.getConnection();
        Connection c2 = pool.getConnection();
        Connection c3 = pool.getConnection();

        printPoolInfo(pool);

        c1.close();

        printPoolInfo(pool);
    }

    private static void printPoolInfo(ConnectionPool dataSource) {
        System.out.println("!!!");
        ConnectionPool pool = (ConnectionPool) dataSource;

        System.out.printf("{active: %s; idle: %s}",
                pool.getActive(), pool.getIdle());
    }

}
