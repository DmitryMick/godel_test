package org.dmitry.moviesearcher.service;

import org.dmitry.moviesearcher.dto.FilmDirectorRespDto;
import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.dmitry.moviesearcher.model.Film;
import org.dmitry.moviesearcher.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:validation.properties")
public class FilmWithDirectorService {
    @Value("${Date.first}")
    private String firstDate;
    private final FilmRepository filmRepository;

    @Autowired
    public FilmWithDirectorService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<FilmDirectorRespDto> findByLastNameAndByDateBetween(LastNameDatesRequestDto requestDto) {
        String lastName = Objects.requireNonNullElse(requestDto.getLastName(), "");

        Date from = Objects.requireNonNullElse(requestDto.getFrom(), Date.valueOf(firstDate));
        Date until = requestDto.getUntil();

        List<Film> filmList;

        if (until == null) {
            filmList = filmRepository.findByDateAfterAndDirectorLastNameLike(from, lastName);
        } else {
            filmList = filmRepository.findByDateBetweenAndDirectorLastNameLike(from, until, lastName);
        }

        return getDtoFromFilms(filmList);
    }

    public List<FilmDirectorRespDto> getDtoFromFilms(List<Film> films) {
        List<FilmDirectorRespDto> dtoList = new ArrayList<>();
        films.forEach(f -> dtoList.add(getDtoResp(f)));

        return dtoList;
    }

    public FilmDirectorRespDto getDtoResp(Film film) {
        FilmDirectorRespDto responseDto = new FilmDirectorRespDto();

        responseDto.setDirectorFirstName(film.getDirector().getFirstName());
        responseDto.setDirectorLastName(film.getDirector().getLastName());
        responseDto.setBirthdate(film.getDirector().getDate());

        responseDto.setFilmName(film.getName());
        responseDto.setReleaseDate(film.getDate());
        responseDto.setGenre(film.getGenre());

        return responseDto;
    }
}
