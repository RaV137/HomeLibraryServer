package pl.danowski.rafal.homelibraryserver.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.danowski.rafal.homelibraryserver.dto.gba.GBABookDto;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IGBABookService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GBABookService implements IGBABookService {

    @Value("${gba.key}")
    private String API_KEY;

    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private Books books;

    private void initializeBooks() {
        if (books == null) {
            try {
                String APPLICATION_NAME = "HomeLibraryDev-HomeLibraryServer/1.0";
                books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                        .setApplicationName(APPLICATION_NAME)
                        .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
                        .build();
            } catch (Exception ignored) {
            }
        }
    }


    @Override
    public GBABookDto findGBABookById(String id) {
        initializeBooks();
        GBABookDto book = null;

        try {
            Books.Volumes.Get get = books.volumes().get(id);
            Volume volume = get.execute();
            book = getBookFromVolume(volume);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return book;
    }

    @Override
    public List<GBABookDto> findGBABooksSearch(String query) {
        initializeBooks();
        List<GBABookDto> booksList = new ArrayList<>();

        try {
            Books.Volumes.List volumesList = books.volumes().list(query);
            volumesList.setMaxResults(20L);

            Volumes volumes = volumesList.execute();
//            if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
//                System.out.println("No matches found.");
//            }

            for (Volume volume : volumes.getItems()) {
                GBABookDto book = getBookFromVolume(volume);
                if(book != null) {
                    booksList.add(book);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return booksList;
    }

    private GBABookDto getBookFromVolume(Volume volume){
        GBABookDto book = new GBABookDto();
        Volume.VolumeInfo info = volume.getVolumeInfo();

        book.setId(volume.getId());
        book.setTitle(info.getTitle());
        book.setAuthor(listContentToString(info.getAuthors()));
        book.setPublisher(info.getPublisher());
        book.setPublishedYear(getYearFromDateString(info.getPublishedDate()));
        book.setIsbn13(getISBN13FromIndustryIdentifiers(info.getIndustryIdentifiers()));
        book.setPageCount(info.getPageCount());

        Volume.VolumeInfo.ImageLinks imageLinks = info.getImageLinks();
        if(imageLinks != null) {
            book.setSmallThumbnailURL(imageLinks.getSmallThumbnail());
            book.setThumbnailURL(imageLinks.getThumbnail());
        } else {
            book.setSmallThumbnailURL(null);
            book.setThumbnailURL(null);
        }

        book.setPreviewLinkURL(info.getPreviewLink());

        if(!isBookValid(book)) {
            return null;
        }

        return book;
    }

    private boolean isBookValid(GBABookDto book) {
        return book.getIsbn13() != null
                && book.getPublishedYear() != null
                && book.getAuthor() != null;
    }

    private String getISBN13FromIndustryIdentifiers(List<Volume.VolumeInfo.IndustryIdentifiers> identifiers) {
        for (Volume.VolumeInfo.IndustryIdentifiers identifier : identifiers) {
            String ISBN_13 = "ISBN_13";
            if (identifier.getType().equals(ISBN_13)) {
                return identifier.getIdentifier();
            }
        }
        return null;
    }

    private String getYearFromDateString(String date) {
        if (date.length() == 4) {
            return date;
        } else if (date.length() > 4) {
            return date.substring(0, 4);
        } else {
            return null;
        }
    }

    private String listContentToString(List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(", ");
        }
        int length = sb.length();
        sb.delete(length - 2, length);
        return sb.toString();
    }

}
