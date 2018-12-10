package pl.danowski.rafal.homelibraryserver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "books", schema = "hls")
@Data
public class Book {

    @SequenceGenerator(name="seq_books", sequenceName="seq_books")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_books")
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "shelf")
    private Integer shelfNumber;

    @Column(name = "id_google_books_api")
    private String googleBooksId;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "favourite")
    private Boolean favourite;

    @Column(name = "is_read")
    private Boolean read;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;


}
