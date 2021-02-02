package org.dmitry.moviesearcher.model;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "film")
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch =  FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;

    @Column(name = "name")
    private String name;

    @Column(name = "release_date")
    private Date date;

    @Column(name = "genre")
    private String genre;
}