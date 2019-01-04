package pl.danowski.rafal.homelibraryserver.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms", schema = "hls")
@Data
public class Room {

    @SequenceGenerator(name = "seq_rooms", sequenceName = "seq_rooms", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rooms")
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "colour")
    private String colour;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "shortName")
    private String shortName;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Book> books;
}
