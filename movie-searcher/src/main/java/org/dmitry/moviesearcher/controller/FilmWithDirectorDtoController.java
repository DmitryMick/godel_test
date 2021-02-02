package org.dmitry.moviesearcher.controller;

import org.dmitry.moviesearcher.dto.FilmDirectorRespDto;
import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.dmitry.moviesearcher.service.FilmWithDirectorService;
import org.dmitry.moviesearcher.validator.LastNameDatesRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilmWithDirectorDtoController {
    private final FilmWithDirectorService service;
    private final LastNameDatesRequestValidator validator;

    @Autowired
    public FilmWithDirectorDtoController(FilmWithDirectorService service, LastNameDatesRequestValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @GetMapping()
    public ResponseEntity<List<FilmDirectorRespDto>> findByPrefixNameAndByDate(@RequestBody LastNameDatesRequestDto requestDto, BindingResult bindingResult) {
        validator.validate(requestDto, bindingResult);

        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest();
        }

        List<FilmDirectorRespDto> filmDtoList = service.findByLastNameAndByDateBetween(requestDto);

        return ResponseEntity.ok(filmDtoList);
    }
}
