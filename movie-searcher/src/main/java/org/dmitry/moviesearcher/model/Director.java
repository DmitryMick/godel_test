package org.dmitry.moviesearcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "director")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private Date date;

    @OneToMany(mappedBy = "director",cascade =  CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Film> films;
}

