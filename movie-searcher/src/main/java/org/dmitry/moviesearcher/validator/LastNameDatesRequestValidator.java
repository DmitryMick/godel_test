package org.dmitry.moviesearcher.validator;

import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.sql.Date;
import java.time.LocalDate;

@Component
@PropertySource("classpath:validation.properties")
public class LastNameDatesRequestValidator implements org.springframework.validation.Validator {
    @Value("${Request.date.minimum}")
    private String minYear;

    @Value("${Request.lastname.default}")
    private String defaultName;

    @Value("${Request.lastname.format}")
    private String nameFormat;

    @Override
    public boolean supports(Class<?> clazz) {
        return LastNameDatesRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LastNameDatesRequestDto requestDto = (LastNameDatesRequestDto) target;

        String lastName = requestDto.getLastName();

        if (lastName == null) {
            requestDto.setLastName(defaultName);
        } else if (!lastName.matches(nameFormat)) {
            errors.reject("lastname", "Request.lastname.letters");
        }

        if (requestDto.getFrom() == null) {
            requestDto.setFrom(Date.valueOf(minYear));
        }
        if (requestDto.getUntil() == null) {
            requestDto.setUntil(Date.valueOf(LocalDate.now()));
        }

        if (requestDto.getFrom().after(requestDto.getUntil())) {
            errors.reject("date", "Request.date.between");
        }
    }
}
