package connection_pool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

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

    private List<Connection> pool = new ArrayList<>();



    public int getActive() {
        return size - pool.size();
    }

    public int getIdle() {
        return pool.size();
    }

    public void createPool() {
        try {

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
            throw new RuntimeException(e);
        }
    }

    private Object getProxy(Connection conn) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{Connection.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //Determine whether it is the close method to recover the connection
                        if (method.getName().equals("close")) {
                            synchronized (pool) {
                                pool.add((Connection) proxy);
                                pool.notify();
                                return null;
                            }
                        } else {
                            //If the call is not the close method, just let it go
                            return method.invoke(conn, args);
                        }
                    }
                });
    }

    public Connection getConnection() {
        synchronized (pool) {
            if (pool.isEmpty()) {

                try {
                    pool.wait();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return pool.remove(pool.size() - 1);
        }
    }
}