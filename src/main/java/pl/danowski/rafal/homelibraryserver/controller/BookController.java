package pl.danowski.rafal.homelibraryserver.controller;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.danowski.rafal.homelibraryserver.dto.book.BookDto;
import pl.danowski.rafal.homelibraryserver.dto.book.CreateBookDto;
import pl.danowski.rafal.homelibraryserver.model.Book;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IBookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    @Autowired
    private final IBookService service;

    @Autowired
    private final MapperFacade mapper;


    // TODO: pobierz konkretną książkę (GPA id), pobierz wszystkie książki, dodaj książkę, zmodyfikuj książkę, usuń książkę

    private BookDto convertToDto(Book book) {
        return mapper.map(book, BookDto.class);
    }

    private Book convertFromDto(CreateBookDto dto) {
        return mapper.map(dto, Book.class);
    }

}
