package pl.danowski.rafal.homelibraryserver.dao;

import org.springframework.stereotype.Repository;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IUserDao;
import pl.danowski.rafal.homelibraryserver.model.Room;
import pl.danowski.rafal.homelibraryserver.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDao implements IUserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getByLogin(String login) {
        final String query = "FROM User AS u WHERE lower(u.login) = lower(trim(:login))";
        return (User) em.createQuery(query)
                .setParameter("login", login)
                .getSingleResult();
    }

    @Override
    public User getByEmail(String email) {
        final String query = "FROM User AS u WHERE lower(u.email) = lower(trim(:email))";
        return (User) em.createQuery(query)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public User registerUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        em.merge(user);
        em.flush();
        return user;
    }

    @Override
    public User deleteUser(String login) {
        User user = getByLogin(login);
        em.remove(user);
        return user;
    }

    @Override
    public User findById(Integer id) {
        final String query = "FROM User AS u WHERE u.id = :id";
        return (User) em.createQuery(query)
                .setParameter("id", id)
                .getSingleResult();
    }

}
