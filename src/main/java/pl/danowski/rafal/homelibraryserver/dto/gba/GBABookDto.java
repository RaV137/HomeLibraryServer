package pl.danowski.rafal.homelibraryserver.dto.gba;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *  Google Books Api Book Dto
 */
@Getter
@Setter
public class GBABookDto {

    @NotNull
    private String id;

    @NotNull
    private String title;
    private String subtitle;

    @NotNull
    private String author;

    @NotNull
    private String publisher;

    @NotNull
    private Date publishedDate;
    private String description;

    @NotNull
    private String isbn13;

    @NotNull
    private Integer pageCount;
    private String smallThumbnailURL;
    private String thumbnailURL;
    private String previewLinkURL;

}
