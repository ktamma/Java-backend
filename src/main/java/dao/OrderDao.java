package dao;

import exceptions.ValidationError;
import exceptions.ValidationErrors;
import item.Item;
import order.Order;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {


    private static final String ORDER_NUMBER = "orderNumber";


    private JdbcTemplate template;


    public OrderDao(JdbcTemplate template) {
        this.template = template;
    }


    public List<Order> getAllOrders() {
        String sql = "SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price, items.orderId\n" +
                "FROM orders\n" +
                "LEFT JOIN items ON orders.id = items.orderId order by 1";

        return template.query(sql, new OrdersMapper());
    }


    public Order insertOrder(Order order) {
        var data = new BeanPropertySqlParameterSource(order);

        Number id = new SimpleJdbcInsert(template)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(data);

        order.setId(id.longValue());

        if (order.getOrderRows() != null) {
            for (Item item : order.getOrderRows()) {
                insertItem(id.longValue(), item);
            }
        }
        return order;
    }

    private void insertItem(long id, Item item) {

        String sql = "insert into items(orderId, itemName, quantity, price) values (?, ?, ?, ?)";

        template.update(sql, new ItemMapper(id, item));
    }

    public void deleteOrderById(long id) {
        deleteItemById(id);
        String sql = "DELETE FROM orders WHERE orders.id=?";
        Object[] args = {id};
        template.update(sql, args);
    }
    public void deleteItemById(long id) {
        String sql = "DELETE FROM items WHERE items.orderid=?;";
        Object[] args = {id};

        template.update(sql, args);
    }

    public ValidationErrors validateOrder(Order order) {
        ValidationErrors errors = new ValidationErrors();
        List<ValidationError> errors1 = new ArrayList<>();
        if(order.getOrderNumber().length() < 2){
            ValidationError error = new ValidationError();
            error.setCode("too_short_number");
            errors1.add(error);
        }
        errors.setErrors(errors1);
        return errors;
    }


    private class ItemMapper implements PreparedStatementSetter {
        private long id;
        private Item item;

        public ItemMapper(long id, Item item) {
            this.id = id;
            this.item = item;
        }

        @Override
        public void setValues(PreparedStatement ps) throws SQLException {
            ps.setLong(1, id);
            ps.setString(2, item.getItemName());
            ps.setInt(3, item.getQuantity());
            ps.setInt(4, item.getPrice());
        }
    }

    public Order findOrderById(long id) {
        String sql = "SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price\n" +
                "FROM orders\n" +
                "         LEFT JOIN items ON orders.id = items.orderId where orders.id =?";
        return template.query(sql, new Object[]{id}, new OrderMapper());
    }

    private static class OrderMapper implements ResultSetExtractor<Order> {

        @Override
        public Order extractData(ResultSet rs) throws SQLException {
            Order order = new Order();

            if (rs.next()) {
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString(ORDER_NUMBER));
                Item item1 = Item.builder()
                        .itemName(rs.getString("itemName"))
                        .price(rs.getInt("price"))
                        .quantity(rs.getInt("quantity"))
                        .build();
                order.setOrderRows(new ArrayList<>());
                order.addItem(item1);
                while (rs.next()) {
                    Item item = Item.builder()
                            .itemName(rs.getString("itemName"))
                            .price(rs.getInt("price"))
                            .quantity(rs.getInt("quantity"))
                            .build();
                    order.addItem(item);
                }
                return order;
            }
            return null;
        }
    }

    private static class OrdersMapper implements ResultSetExtractor<List<Order>> {

        @Override
        public List<Order> extractData(ResultSet rs) throws SQLException {
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString(ORDER_NUMBER));
                order.setOrderRows(new ArrayList<>());
                Item item = Item.builder()
                        .itemName(rs.getString("itemName"))
                        .price(rs.getInt("price"))
                        .quantity(rs.getInt("quantity"))
                        .build();
                if (orders.contains(order)) {
                    orders.get(orders.size() - 1).addItem(item);
                } else {
                    orders.add(order);
                    order.setOrderRows(new ArrayList<>());
                    order.addItem(item);
                }
            }
            return orders;
        }
    }
}




