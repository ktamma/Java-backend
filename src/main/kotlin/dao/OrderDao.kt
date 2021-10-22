package dao

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.jdbc.core.PreparedStatementSetter
import kotlin.Throws
import java.sql.SQLException
import java.sql.PreparedStatement
import org.springframework.jdbc.core.ResultSetExtractor
import java.sql.ResultSet
import item.Item
import order.Order
import org.springframework.stereotype.Repository
import java.util.ArrayList

@Repository
class OrderDao(private val template: JdbcTemplate) {
    val allOrders: List<Order>
        get() {
            val sql = """
        SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price, items.orderId
        FROM orders
        LEFT JOIN items ON orders.id = items.orderId order by 1
        """.trimIndent()
            return template.query(sql, OrdersMapper())
        }

    fun insertOrder(order: Order): Order {
        val data = BeanPropertySqlParameterSource(order)
        val id = SimpleJdbcInsert(template)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(data)
        order.id = id.toLong()
        if (order.orderRows != null) {
            for (item in order.orderRows!!) {
                insertItem(id.toLong(), item)
            }
        }
        return order
    }

    private fun insertItem(id: Long, item: Item) {
        val sql = "insert into items(orderId, itemName, quantity, price) values (?, ?, ?, ?)"
        template.update(sql, ItemMapper(id, item))
    }

    fun deleteOrderById(id: Long) {
        deleteItemById(id)
        val sql = "DELETE FROM orders WHERE orders.id=?"
        val args = arrayOf<Any>(id)
        template.update(sql, *args)
    }

    fun deleteItemById(id: Long) {
        val sql = "DELETE FROM items WHERE items.orderid=?;"
        val args = arrayOf<Any>(id)
        template.update(sql, *args)
    }

    private inner class ItemMapper(private val id: Long, private val item: Item) : PreparedStatementSetter {
        @Throws(SQLException::class)
        override fun setValues(ps: PreparedStatement) {
            ps.setLong(1, id)
            ps.setString(2, item.itemName)
            ps.setInt(3, item.quantity)
            ps.setInt(4, item.price)
        }
    }

    fun findOrderById(id: Long): Order? {
        val sql = """SELECT orders.id, orders.orderNumber, items.itemName, items.quantity, items.price
FROM orders
         LEFT JOIN items ON orders.id = items.orderId where orders.id =?"""
        return template.query(sql, arrayOf<Any>(id), OrderMapper())
    }

    private class OrderMapper : ResultSetExtractor<Order?> {
        @Throws(SQLException::class)
        override fun extractData(rs: ResultSet): Order? {
            val order = Order()
            if (rs.next()) {
                order.id = rs.getLong("id")
                order.orderNumber = rs.getString(ORDER_NUMBER)
                var item = Item(rs.getString("itemName"),
                        rs.getInt("price"),
                        rs.getInt("quantity"))
                order.orderRows = ArrayList()
                var items: MutableList<Item>? = order.orderRows
                items!!.add(item)
                order.orderRows = items
                while (rs.next()) {
                    item = Item(rs.getString("itemName"),
                            rs.getInt("price"),
                            rs.getInt("quantity"))
                    items = order.orderRows
                    items!!.add(item)
                    order.orderRows = items
                }
                println(order.orderNumber)
                return order
            }
            return null
        }
    }

    private class OrdersMapper : ResultSetExtractor<List<Order>> {
        @Throws(SQLException::class)
        override fun extractData(rs: ResultSet): List<Order> {
            val orders = ArrayList<Order>()
            while (rs.next()) {
                val order = Order()
                order.id = rs.getLong("id")
                order.orderNumber = rs.getString(ORDER_NUMBER)
                order.orderRows = ArrayList()
                val item = Item(rs.getString("itemName"),
                        rs.getInt("price"),
                        rs.getInt("quantity"))
                if (exists(orders, order)) {
                    val items = orders[orders.size - 1].orderRows
                    items!!.add(item)
                    orders[orders.size - 1].orderRows = items
                } else {
                    val items: MutableList<Item> = ArrayList()
                    items.add(item)
                    order.orderRows = items
                    orders.add(order)
                }
            }
            return orders
        }

        private fun exists(orders: ArrayList<Order>, order: Order): Boolean {
            for ((id) in orders) {
                if (order.id == id) {
                    return true
                }
            }
            return false
        }
    }

    companion object {
        private const val ORDER_NUMBER = "orderNumber"
    }
}