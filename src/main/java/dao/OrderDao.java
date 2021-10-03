package dao;

import connectionPool.ConnectionPool;
import order.Order;
import util.ConnectionInfo;

import java.sql.*;

public class OrderDao {


    private final ConnectionPool pool;

    public OrderDao(ConnectionPool pool) {
        this.pool = pool;
    }

    public Order findOrderById2(Long id) {

        String sql = "select id, orderNumber from \"order\" where id =?";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {




            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            Order order = new Order();

            if (rs.next()) {
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString("orderNumber"));
                return order;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Order findOrderById(Long id) {

        String sql = "select id, orderNumber from \"order\" where id =?";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            Thread.sleep(1000);


            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            Order order = new Order();

            if (rs.next()) {
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString("orderNumber"));
                return order;
            }
            return null;

        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Order findPersonById(Long id) {
        String s = "select id, name, age from person where id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(s)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Order();
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order insertOrder(Order order) {

        String sql = "insert into \"order\"(orderNumber) values (?)";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"})) {

            ps.setString(1, order.getOrderNumber());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (!(rs.next())) {
                throw new RuntimeException("Unexpected");
            }
            Long id = rs.getLong("id");
            order.setId(id);
            return order;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
