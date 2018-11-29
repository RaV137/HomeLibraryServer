package pl.danowski.rafal.homelibraryserver.exception.model;

public class BookNotFoundException extends EntityNotFoundException {

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFoundException(Throwable cause) {
        super(cause);
    }
}

