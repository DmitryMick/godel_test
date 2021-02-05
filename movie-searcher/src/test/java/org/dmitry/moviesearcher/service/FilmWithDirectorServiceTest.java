package org.dmitry.moviesearcher.service;

import org.dmitry.moviesearcher.dto.FilmDirectorRespDto;
import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.dmitry.moviesearcher.model.Director;
import org.dmitry.moviesearcher.model.Film;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class FilmWithDirectorServiceTest {
    @Autowired
    private FilmWithDirectorService service;

    @Test
    void injectedComponentsAreNotNull() {
        assertNotNull(service);
    }

    @Test
    void getDtoResp_CorrectConversionToDtoFromFilm_Test() {
        Long id = 11L;
        String name = "name1";
        Director director = new Director();
        Date release = Date.valueOf("1900-01-01");
        String genre = "genre";

        Film film = new Film(id, director, name, release, genre);

        FilmDirectorRespDto dto1 = service.getDtoResp(film);

        assertEquals(name, dto1.getFilmName());
        assertEquals(release, dto1.getReleaseDate());
        assertEquals(genre, dto1.getGenre());

        assertEquals(director.getFirstName(), dto1.getDirectorFirstName());
        assertEquals(director.getLastName(), dto1.getDirectorLastName());
        assertEquals(director.getDate(), dto1.getBirthdate());
    }

    @Test
    void getDtoFromFilms_GetSameNumberOfElements_Test() {
        int filmNumber = 100_000;

        List<Film> filmList = new ArrayList<>(filmNumber);
        for (long i = 0; i < filmNumber; i++) {
            Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
            Film film = new Film(i, new Director(), String.valueOf(i), date, String.valueOf(i));
            filmList.add(film);
        }
        List<FilmDirectorRespDto> dtoList = service.getDtoFromFilms(filmList);

        assertEquals(dtoList.size(), filmNumber);
    }

    @Test
    void getDtoFromFilms_WhenFilmListIsEmpty_GetNotNull() {
        List<Film> films = new ArrayList<>();
        List<FilmDirectorRespDto> dtoList = service.getDtoFromFilms(films);

        assertNotNull(dtoList);
        assertTrue(dtoList.isEmpty());
    }

    @Test
    void findByLastNameAndByDateBetween_WhereNameIsNull_ThenFindsByDates() {
        int allFilms = 8; //between 1900-01-01 and 2000-01-01
        LastNameDatesRequestDto dtoRequest = new LastNameDatesRequestDto();
        dtoRequest.setLastName(null);
        dtoRequest.setFrom(Date.valueOf("1900-01-01"));
        dtoRequest.setUntil(Date.valueOf("2000-01-01"));

        List<FilmDirectorRespDto> responseDtoList = service.findByLastNameAndByDateBetween(dtoRequest);

        assertEquals(responseDtoList.size(), allFilms);
    }

    @Test
    void findByLastNameAndByDateBetween_WhereNameIsEmpty_ThenFindsByDates() {
        int films = 1; //'Edward Scissorhands', '1990-09-14'
        LastNameDatesRequestDto dtoRequest = new LastNameDatesRequestDto();
        dtoRequest.setLastName("");
        dtoRequest.setFrom(Date.valueOf("1990-09-14"));
        dtoRequest.setUntil(Date.valueOf("1990-09-14"));

        List<FilmDirectorRespDto> responseDtoList = service.findByLastNameAndByDateBetween(dtoRequest);
        assertEquals(responseDtoList.size(), films);

        checkBurtonFilmFromDto(responseDtoList.get(0));
    }

    @Test
    void findByLastNameAndByDateBetween_WhereFirstDateIsNull_ThenFindsByNameAndSecondDate() {
        int BurtonFilms = 1; //'Edward Scissorhands', '1990-09-14'
        LastNameDatesRequestDto firstDateIsNullDto = new LastNameDatesRequestDto();
        firstDateIsNullDto.setLastName("Burton");
        firstDateIsNullDto.setFrom(Date.valueOf("1990-09-14"));
        firstDateIsNullDto.setUntil(null);

        List<FilmDirectorRespDto> firstResponseDtoList = service.findByLastNameAndByDateBetween(firstDateIsNullDto);
        assertEquals(firstResponseDtoList.size(), BurtonFilms);
        checkBurtonFilmFromDto(firstResponseDtoList.get(0));
    }
    @Test
    void findByLastNameAndByDateBetween_WhereSecondDateIsNull_ThenFindsByNameAndFirstDate() {
        int BurtonFilms = 1;
        LastNameDatesRequestDto secondDateIsNullDto = new LastNameDatesRequestDto();
        secondDateIsNullDto.setLastName("Burton");
        secondDateIsNullDto.setFrom(null);
        secondDateIsNullDto.setUntil(Date.valueOf("1990-09-14"));

        List<FilmDirectorRespDto> secondResponseDtoList = service.findByLastNameAndByDateBetween(secondDateIsNullDto);
        assertEquals(secondResponseDtoList.size(), BurtonFilms);
        checkBurtonFilmFromDto(secondResponseDtoList.get(0));
    }

    @Test
    void findByLastNameAndByDateBetween_WhereDatesAreNull_ThenFindsByNameOnly() {
        int BurtonFilms = 1; //'Edward Scissorhands', '1990-09-14'
        LastNameDatesRequestDto dtoRequest = new LastNameDatesRequestDto();
        dtoRequest.setLastName("Burton");
        dtoRequest.setFrom(null);
        dtoRequest.setUntil(null);

        List<FilmDirectorRespDto> responseDtoList = service.findByLastNameAndByDateBetween(dtoRequest);
        assertEquals(responseDtoList.size(), BurtonFilms);

        checkBurtonFilmFromDto(responseDtoList.get(0));
    }

    private void checkBurtonFilmFromDto(FilmDirectorRespDto dto) {
        //'Timothy''Burton' '1958-08-25' has film 'Edward Scissorhands', '1990-09-14', 'drama'
        assertEquals("Edward Scissorhands", dto.getFilmName());
        assertEquals(Date.valueOf("1990-09-14"), dto.getReleaseDate());
        assertEquals("drama", dto.getGenre());

        assertEquals("Timothy", dto.getDirectorFirstName());
        assertEquals("Burton", dto.getDirectorLastName());
        assertEquals(Date.valueOf("1958-08-25"), dto.getBirthdate());
    }
}