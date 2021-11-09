package dao;

import model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Order save(Order order){
        em.persist(order);
        return order;
    }

    public List<Order> getAllOrders() {
        return em.createQuery("select o from Order o").getResultList();
    }

    public Order findOrderById(Long id) {
        TypedQuery<Order> query =  em.createQuery("select o from Order o where o.id = :id", Order.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    public void deleteOrderById(Long id) {
        Query query = em.createQuery("delete from Order o where o.id = :id");
        query.setParameter("id", id);
    }
}
