package pl.danowski.rafal.homelibraryserver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "books", schema = "home_library")
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

    @Column(name = "shelf_number")
    private Integer shelfNumber;

    @Column(name = "google_books_id")
    private String googleBooksId;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "favourite")
    private Boolean favourite;

    @Column(name = "read")
    private Boolean read;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


}