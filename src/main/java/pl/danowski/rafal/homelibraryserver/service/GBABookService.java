package pl.danowski.rafal.homelibraryserver.service;

import org.springframework.stereotype.Service;
import pl.danowski.rafal.homelibraryserver.dto.gba.GBABookDto;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IGBABookService;

import java.util.List;

@Service
public class GBABookService implements IGBABookService {

    // TODO

    @Override
    public GBABookDto findGBABookById(String id) {
        return null;
    }

    @Override
    public List<GBABookDto> findGBABooksSearch(String search) {
        return null;
    }
}
