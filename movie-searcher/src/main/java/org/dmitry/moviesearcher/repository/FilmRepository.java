package org.dmitry.moviesearcher.repository;

import org.dmitry.moviesearcher.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT f FROM Film f JOIN Director d ON f.director.id = d.id WHERE LOWER(d.lastName) LIKE LOWER(CONCAT(:name,'%')) AND f.date >= :after")
    List<Film> findByDateAfterAndDirectorLastNameLike(@Param("after") Date after, @Param("name") String lastName);

    @Query("SELECT f FROM Film f JOIN Director d ON f.director.id = d.id WHERE LOWER(d.lastName) LIKE LOWER(CONCAT(:name,'%')) AND f.date BETWEEN :from AND :until")
    List<Film> findByDateBetweenAndDirectorLastNameLike(@Param("from") Date from, @Param("until") Date until, @Param("name") String lastName);
}
