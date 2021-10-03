package dao;

import connection_pool.ConnectionPool;
import order.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {


    private final ConnectionPool pool;

    public OrderDao(ConnectionPool pool) {
        this.pool = pool;
    }

    public Order findOrderById(Long id) {

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

    public List<Order> getOrders() {
        String sql = "select id, orderNumber from \"order\"";

        try (Connection conn = pool.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);


            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString("orderNumber"));
                orders.add(order);
            }
            return orders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Order findOrderByIdSlow(Long id) {

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

    public List<Order> getOrdersSlow() {
        String sql = "select id, orderNumber from \"order\"";

        try (Connection conn = pool.getConnection();
             Statement stmt = conn.createStatement()) {

            Thread.sleep(1000);

            ResultSet rs = stmt.executeQuery(sql);


            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString("orderNumber"));
                orders.add(order);
            }
            return orders;

        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
