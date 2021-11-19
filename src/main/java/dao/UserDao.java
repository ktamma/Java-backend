package dao;

import model.Order;
import model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public List<User> getUsers() {
        return em.createQuery("select u from User u").getResultList();
    }

    public User getUserByUserName(String userName) {
        TypedQuery<User> query =  em.createQuery("select u from User u where u.userName = :userName", User.class);
        query.setParameter("userName", userName);

        return query.getSingleResult();
    }


}
