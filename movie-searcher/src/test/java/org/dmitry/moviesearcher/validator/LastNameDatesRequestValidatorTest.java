package org.dmitry.moviesearcher.validator;

import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.dmitry.moviesearcher.model.Film;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:validation.properties")
class LastNameDatesRequestValidatorTest {

    @Autowired
    private LastNameDatesRequestValidator validator;

    @Test
    void injectedComponentsAreNotNull() {
        assertNotNull(validator);
    }

    @Test
    void whenClassIsRequestDto_ThanOK() {
        assertTrue(validator.supports(LastNameDatesRequestDto.class));
    }

    @Test
    void whenClassIsRequestDto_ThanNotOK() {
        assertFalse(validator.supports(Film.class));
    }

    @Test
    void whenDatesAreCorrect_ThanNoErrors() {
        LastNameDatesRequestDto invalidDto = new LastNameDatesRequestDto();
        invalidDto.setFrom(Date.valueOf("1800-01-01"));
        invalidDto.setUntil(Date.valueOf("1900-01-01"));

        Errors errors = new BeanPropertyBindingResult(invalidDto, "invalidDto");
        validator.validate(invalidDto, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void whenDatesAreEquals_ThanNoErrors() {
        LastNameDatesRequestDto invalidDto = new LastNameDatesRequestDto();
        invalidDto.setFrom(Date.valueOf("1800-01-01"));
        invalidDto.setUntil(Date.valueOf("1800-01-01"));

        Errors errors = new BeanPropertyBindingResult(invalidDto, "invalidDto");
        validator.validate(invalidDto, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void whenFirstDateLessThanMinimumDate_ThanError() {
        LastNameDatesRequestDto invalidDto = new LastNameDatesRequestDto();
        invalidDto.setFrom(Date.valueOf("1800-01-01"));
        invalidDto.setUntil(Date.valueOf("1700-01-01"));

        Errors errors = new BeanPropertyBindingResult(invalidDto, "invalidDto");
        validator.validate(invalidDto, errors);

        assertTrue(errors.hasErrors());
    }
}