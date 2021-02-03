package org.dmitry.moviesearcher.validator;

import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.sql.Date;

@Component
@PropertySource("classpath:validation.properties")
public class LastNameDatesRequestValidator implements org.springframework.validation.Validator {
    @Value("${Date.first}")
    private String firstDate;

    @Override
    public boolean supports(Class<?> clazz) {
        return LastNameDatesRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LastNameDatesRequestDto requestDto = (LastNameDatesRequestDto) target;

        Date from = requestDto.getFrom();
        Date until = requestDto.getFrom();

        if (from != null && until != null) {
            Date minimum = Date.valueOf(firstDate);

            if (minimum.after(from)) {
                errors.reject("date", "Request.date.minimum");
            }

            if (from.after(until)) {
                errors.reject("date", "Request.date.between");
            }
        }
    }
}
