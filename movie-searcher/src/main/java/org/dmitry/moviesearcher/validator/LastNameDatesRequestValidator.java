package org.dmitry.moviesearcher.validator;

import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;

@Component
@PropertySource("classpath:validation.properties")
public class LastNameDatesRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LastNameDatesRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LastNameDatesRequestDto requestDto = (LastNameDatesRequestDto) target;

        Date from = requestDto.getFrom();
        Date until = requestDto.getUntil();

        if (from != null && until != null ) {

            if (from.after(until)) {
                errors.reject("date", "Request.date.between");
            }
        }
    }
}
