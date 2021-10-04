package dao;

import connection_pool.ConnectionPool;
import item.Item;
import order.Order;

import java.sql.*;
import java.util.*;

public class OrderDao {

    private static final String ORDER_NUMBER = "orderNumber";


    private final ConnectionPool pool;

    public OrderDao(ConnectionPool pool) {
        this.pool = pool;
    }

    public Order findOrderById(Long id) {

        String sql = "SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price\n" +
                "FROM orders\n" +
                "         LEFT JOIN items ON orders.id = items.orderId where orders.id =?";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            Order order = new Order();

            if (rs.next()) {
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString(ORDER_NUMBER));
                Item item1 = new Item();
                item1.setItemName(rs.getString("itemName"));
                item1.setPrice(rs.getInt("price"));
                item1.setQuantity(rs.getInt("quantity"));
                order.setOrderRows(new ArrayList<>());
                order.addItem(item1);
                while (rs.next()) {
                    Item item = new Item();
                    item.setItemName(rs.getString("itemName"));
                    item.setPrice(rs.getInt("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    order.addItem(item);
                }
                return order;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Order> getOrders() {
        String sql = "SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price, items.orderId\n" +
                "FROM orders\n" +
                "LEFT JOIN items ON orders.id = items.orderId order by 1";

        try (Connection conn = pool.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);


            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString(ORDER_NUMBER));
                Item item = new Item();
                item.setItemName(rs.getString("itemName"));
                item.setPrice(rs.getInt("price"));
                item.setQuantity(rs.getInt("quantity"));
                if (orders.contains(order)) {
                    orders.get(orders.size() - 1).addItem(item);
                } else {
                    orders.add(order);
                    order.setOrderRows(new ArrayList<>());
                    order.addItem(item);
                }
            }
            return orders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Order findOrderByIdSlow(Long id) {

        String sql = "select id, orderNumber from orders where id =?";

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            Thread.sleep(1000);


            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            Order order = new Order();

            if (rs.next()) {
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString(ORDER_NUMBER));
                return order;
            }
            return null;

        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Order insertOrder(Order order) {

        String sql = "insert into orders(orderNumber) values (?)";
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

            if (order.getOrderRows() != null) {
                for (Item item : order.getOrderRows()) {
                    insertItem(item, id);
                }
            }

            return order;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void insertItem(Item item, Long orderId) {
        String sql = "insert into items(orderId, itemName, quantity, price) values (?, ?, ?, ?)";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"})) {

            ps.setLong(1, orderId);
            ps.setString(2, item.getItemName());
            ps.setInt(3, item.getQuantity());
            ps.setInt(4, item.getPrice());


            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (!(rs.next())) {
                throw new RuntimeException("Unexpected");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Order> getOrdersSlow() {
        String sql = "select id, orderNumber from orders";

        try (Connection conn = pool.getConnection();
             Statement stmt = conn.createStatement()) {

            Thread.sleep(1000);

            ResultSet rs = stmt.executeQuery(sql);


            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString(ORDER_NUMBER));
                orders.add(order);
            }
            return orders;

        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(long idToDelete) {

        String sql = "DELETE FROM items WHERE items.orderid=?;" +
                "DELETE FROM orders WHERE orders.id=?";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, idToDelete);
            ps.setLong(2, idToDelete);

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Long> insertOrdersBulk(List<Order> orders) {
        Map<Long, List<Item>> items = new HashMap<>();
        List<Long> keys = new ArrayList<>();
        String sql = "insert into orders(orderNumber) values (?)";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"})) {

            for (Order order : orders) {
                ps.setString(1, order.getOrderNumber());

                ps.addBatch();

                ps.clearParameters();

            }
            ps.executeBatch();
            ResultSet rs = ps.getGeneratedKeys();

            int i = 0;
            while (rs.next()) {
                keys.add(rs.getLong("id"));
                for (Item item : orders.get(i).getOrderRows()) {
                    if (items.containsKey(rs.getLong("id"))) {
                        items.get(rs.getLong("id")).add(item);
                    } else {
                        List<Item> newItems = new ArrayList<>();
                        newItems.add(item);
                        items.put(rs.getLong("id"), newItems);

                    }
                }
                i++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        insertItemsBulk(items);
        return keys;
    }

    public void insertItemsBulk(Map<Long, List<Item>> items) {
        String sql = "insert into items(orderId, itemName, quantity, price) values (?, ?, ?, ?)";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"})) {

            for (Long id : items.keySet()) {

                {
                    List<Item> itemList = items.get(id);
                    for (Item item : itemList) {
                        if (item != null) {
                            ps.setLong(1, id);
                            ps.setString(2, item.getItemName());
                            ps.setInt(3, item.getQuantity());
                            ps.setInt(4, item.getPrice());

                            ps.addBatch();
                        }
                        ps.clearParameters();
                    }
                }
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
