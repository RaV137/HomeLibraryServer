package pl.danowski.rafal.homelibraryserver.dto.book;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookDto {

    @NotNull
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private Integer shelfNumber;

    @NotNull
    private String googleBooksId;

    private Integer rating;

    @NotNull
    private Boolean favourite;

    @NotNull
    private Boolean read;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer roomId;

    private String smallImageUrl;

}
