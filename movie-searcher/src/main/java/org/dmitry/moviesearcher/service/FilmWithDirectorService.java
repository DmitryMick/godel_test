package org.dmitry.moviesearcher.service;

import org.dmitry.moviesearcher.dto.FilmDirectorRespDto;
import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.dmitry.moviesearcher.model.Film;
import org.dmitry.moviesearcher.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmWithDirectorService {
    private final FilmRepository filmRepository;

    @Autowired
    public FilmWithDirectorService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<FilmDirectorRespDto> findByLastNameAndByDateBetween(LastNameDatesRequestDto requestDto) {
        String lastName = requestDto.getLastName();
        Date from = requestDto.getFrom();
        Date until = requestDto.getUntil();

        List<Film> filmList;

        if (!lastName.isEmpty()) {
            filmList = filmRepository.findByDateBetweenAndDirectorLastNameLike(from, until, lastName);
        } else {
            filmList = filmRepository.findByDateBetween(from, until);
        }

        return getDtoFromFilms(filmList);
    }

    List<FilmDirectorRespDto> getDtoFromFilms(List<Film> films) {
        List<FilmDirectorRespDto> dtoList = new ArrayList<>();
        films.parallelStream().forEach(f -> dtoList.add(new FilmDirectorRespDto(f)));

        return dtoList;
    }
}
