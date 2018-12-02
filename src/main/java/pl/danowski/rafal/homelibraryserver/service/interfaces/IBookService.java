package pl.danowski.rafal.homelibraryserver.service.interfaces;

import pl.danowski.rafal.homelibraryserver.dto.gba.GBABookDto;
import pl.danowski.rafal.homelibraryserver.model.Book;

import java.util.List;

public interface IBookService {

    GBABookDto findGBABookById(String id);

    Book findBookById(Integer id);

    List<GBABookDto> findGBABooksBySearch(String search);

    List<Book> findBooksByUserLogin(String login);

    Book addBook(Book book);

    Book modifyBook(Book book);

    Book deleteBook(Integer id);

}
