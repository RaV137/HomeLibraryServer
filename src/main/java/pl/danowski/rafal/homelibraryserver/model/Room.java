package pl.danowski.rafal.homelibraryserver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "rooms", schema = "home_library")
@Data
public class Room {

    @SequenceGenerator(name="seq_rooms", sequenceName="seq_rooms")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rooms")
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "colour")
    private String colour;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "shortName")
    private String shortName;

}
