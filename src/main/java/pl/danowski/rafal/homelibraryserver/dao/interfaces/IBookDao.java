package pl.danowski.rafal.homelibraryserver.dao.interfaces;

import pl.danowski.rafal.homelibraryserver.model.Book;

import java.util.List;

public interface IBookDao {

    List<Book> findByUserId(Integer id);

    Book getById(Integer id);

    Book createBook(Book book);

    Book updateBook(Book book);

    Book deleteBook(Integer id);
}
