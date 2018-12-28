package pl.danowski.rafal.homelibraryserver.dao;

import org.springframework.stereotype.Repository;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IBookDao;
import pl.danowski.rafal.homelibraryserver.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("ALL")
@Repository
@Transactional
public class BookDao implements IBookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findByUserId(Integer id) {
        final String query = "FROM Book AS b WHERE b.user.id = :id";
        return (List<Book>) em.createQuery(query)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public Book getById(Integer id) {
        final String query = "FROM Book AS b WHERE b.id = :id";
        return (Book) em.createQuery(query)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Book createBook(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        em.merge(book);
        em.flush();
        return book;
    }

    @Override
    public Book deleteBook(Integer id) {
        Book book = getById(id);
        em.remove(book);
        return book;
    }

}
