package pl.danowski.rafal.homelibraryserver.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.danowski.rafal.homelibraryserver.dto.book.BookDto;
import pl.danowski.rafal.homelibraryserver.dto.book.CreateBookDto;
import pl.danowski.rafal.homelibraryserver.dto.gba.GBABookDto;
import pl.danowski.rafal.homelibraryserver.model.Book;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IBookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    @Autowired
    private final IBookService service;

    @Autowired
    private final MapperFacade mapper;

    @GetMapping("/{id}")
    @ApiOperation("Find book from Google Books Api by its id")
    public ResponseEntity<GBABookDto> findBookByGBAId(@PathVariable("id") String id) {
        GBABookDto book = service.findGBABookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/user/{login}")
    @ApiOperation("Finds all books for particular user")
    public ResponseEntity<List<BookDto>> getAllForUser(@PathVariable("login") String login) {
        List<Book> books = service.findBooksByUserLogin(login);
        return new ResponseEntity<>(books.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create new book")
    public ResponseEntity<BookDto> createNewBook(@Valid @RequestBody CreateBookDto createBook) {
        Book book = convertFromDto(createBook);
        Book registeredBook = service.addBook(book);
        return new ResponseEntity<>(convertToDto(registeredBook), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Update book")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Integer id, @Valid @RequestBody BookDto bookDto) {
        Book bookToUpdate = service.findBookById(id);
        mapper.map(bookDto, bookToUpdate);
        Book updatedBook = service.modifyBook(bookToUpdate);
        return new ResponseEntity<>(convertToDto(updatedBook), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete book")
    public ResponseEntity<BookDto> deleteBook(@PathVariable("id") Integer id) {
        Book book = service.deleteBook(id);
        return new ResponseEntity<>(convertToDto(book), HttpStatus.OK);
    }

    private BookDto convertToDto(Book book) {
        return mapper.map(book, BookDto.class);
    }

    private Book convertFromDto(CreateBookDto dto) {
        return mapper.map(dto, Book.class);
    }

}
