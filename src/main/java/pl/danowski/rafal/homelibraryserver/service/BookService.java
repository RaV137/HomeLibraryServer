package pl.danowski.rafal.homelibraryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IBookDao;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IUserDao;
import pl.danowski.rafal.homelibraryserver.dto.gba.GBABookDto;
import pl.danowski.rafal.homelibraryserver.model.Book;
import pl.danowski.rafal.homelibraryserver.model.User;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IBookService;
import pl.danowski.rafal.homelibraryserver.gba.service.interfaces.IGBABookService;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookDao dao;

    @Autowired
    private IGBABookService service;

    @Autowired
    private IUserDao userDao;

    @Override
    public GBABookDto findGBABookById(String id) {
        return service.findGBABookById(id);
    }

    @Override
    public Book findBookById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public List<GBABookDto> findGBABooksBySearch(String search) {
        return service.findGBABooksSearch(search);
    }

    @Override
    public List<Book> findBooksByUserLogin(String login) {
        User user = userDao.getByLogin(login);
        return dao.findByUserId(user.getId());
    }

    @Override
    public Book addBook(Book book) {
        return dao.createBook(book);
    }

    @Override
    public Book modifyBook(Book book) {
        return dao.updateBook(book);
    }

    @Override
    public Book deleteBook(Integer id) {
        return dao.deleteBook(id);
    }
}
