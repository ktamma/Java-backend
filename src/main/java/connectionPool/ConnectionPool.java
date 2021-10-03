package connectionPool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.ConfigUtil;
import util.ConnectionInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.Properties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConnectionPool {
    private String url;
    private String user;
    private String password;
    private String driverClass;
    private int size;

    private LinkedList<Connection> pool = new LinkedList<>();


    public int getActive() {
        return size - pool.size();
    }

    public int getIdle() {
        return pool.size();
    }

    public void createPool() {
        try {
            Properties properties = new Properties();

            Class.forName(driverClass);
            for (int i = 0; i < size; i++) {

                final Connection conn = DriverManager.getConnection(
                        url,
                        user,
                        password);


                Object connProxy = getProxy(conn);
                pool.add((Connection) connProxy);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Object getProxy(Connection conn) {
        Object connProxy = Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(),
                new Class[]{Connection.class},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //Determine whether it is the close method to recover the connection
                        if (method.getName().equals("close")) {
                            synchronized (pool) {
                                pool.addLast((Connection) proxy);
                                pool.notify();
                                return null;
                            }
                        } else {
                            //If the call is not the close method, just let it go
                            return method.invoke(conn, args);
                        }
                    }
                });
        return connProxy;
    }

    public Connection getConnection() {
        synchronized (pool) {
            if (pool.size() == 0) {

                try {
                    pool.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            Connection connection = pool.removeFirst();
            return connection;
        }
    }
}