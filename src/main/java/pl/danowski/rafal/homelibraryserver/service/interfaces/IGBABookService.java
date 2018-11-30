package pl.danowski.rafal.homelibraryserver.service.interfaces;

import pl.danowski.rafal.homelibraryserver.dto.gba.GBABookDto;

import java.util.List;

public interface IGBABookService {

    GBABookDto findGBABookById(String id);

    List<GBABookDto> findGBABooksSearch(String search);

}
