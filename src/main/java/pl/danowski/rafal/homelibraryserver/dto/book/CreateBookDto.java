package pl.danowski.rafal.homelibraryserver.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookDto {
    private String title;
    private String author;
    private Integer shelfNumber;
    private String googleBooksId;
    private Integer rating;
    private Boolean favourite;
    private Boolean read;
    private Integer userId;
    private Integer roomId;
}
