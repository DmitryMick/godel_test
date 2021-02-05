package org.dmitry.moviesearcher.repository;

import org.dmitry.moviesearcher.model.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertNotNull(filmRepository);
    }

    @Test
    void whenInitializedByFlywayV1_Init_thenGetAllFilms() {
        int filmsInMigrationFile = 8;

        List<Film> filmList = filmRepository.findAll();
        assertNotNull(filmList);

        assertEquals(filmList.size(), filmsInMigrationFile);
    }

    @Test
    void whenWasFiledByFlywayV2_Add_thenContainsAllNames() {
        List<String> filmNames = Arrays.asList(
                "Dial M for Murder",
                "Rear Window",
                "Vertigo",
                "Psycho",
                "From Dusk Till Dawn",
                "Four Rooms",
                "Pulp Fiction",
                "Edward Scissorhands"
        );

        List<Film> filmList = filmRepository.findAll();

        for (Film film : filmList) {
            Assertions.assertTrue(filmNames.contains(film.getName()));
        }
    }

    @Test
    void findByDateBetweenAndDirectorLastNameLike_Test() {
        //'Timothy''Burton' '1958-08-25' has film 'Edward Scissorhands', '1990-09-14', 'drama'

        String lastName = "bur"; //first letters, case insensitive
        Date from = (Date.valueOf("1990-01-01"));
        Date until = (Date.valueOf("1990-12-31"));

        List<Film> Burtonfilms = filmRepository.findByDateBetweenAndDirectorLastNameLike(from, until, lastName);
        assertEquals(Burtonfilms.size(), 1);

        Film film = Burtonfilms.get(0);
        assertEquals("Edward Scissorhands", film.getName());
        assertEquals(Date.valueOf("1990-09-14"), film.getDate());
        assertEquals("drama", film.getGenre());
    }

    @Test
    void findByDateAfterAndDirectorLastNameLike_Test() {
        //'Timothy''Burton' '1958-08-25' has film 'Edward Scissorhands', '1990-09-14', 'drama'

        String lastName = "bur"; //first letters, case insensitive
        Date from = (Date.valueOf("1990-01-01"));

        List<Film> Burtonfilms = filmRepository.findByDateAfterAndDirectorLastNameLike(from, lastName);
        assertEquals(Burtonfilms.size(), 1);

        Film film = Burtonfilms.get(0);
        assertEquals("Edward Scissorhands", film.getName());
        assertEquals(Date.valueOf("1990-09-14"), film.getDate());
        assertEquals("drama", film.getGenre());
    }
}