package org.dmitry.moviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastNameDatesRequestDto {
    private String lastName;
    private Date from;
    private Date until;
}
