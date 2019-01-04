package pl.danowski.rafal.homelibraryserver.dto.gba;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Google Books Api Book Dto
 */
@Getter
@Setter
public class GBABookDto {

    @NotNull
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String publisher;

    @NotNull
    private String isbn13;

    private String publishedYear;
    private Integer pageCount;
    private String smallThumbnailURL;
    private String thumbnailURL;
    private String previewLinkURL;

}
