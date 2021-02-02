package org.dmitry.moviesearcher.repository;

import org.dmitry.moviesearcher.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findByDateBetween(Date from, Date until);

    @Query("SELECT f FROM Film f JOIN Director d ON f.director.id = d.id WHERE d.lastName LIKE ?3% AND f.date BETWEEN ?1 AND ?2")
    List<Film> findByDateBetweenAndDirectorLastNameLike(@Param("from") Date from, @Param("until") Date until, String lastName);
}
