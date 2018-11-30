package pl.danowski.rafal.homelibraryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IBookDao;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IBookService;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IGBABookService;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookDao dao;

    @Autowired
    private IGBABookService service;

}
