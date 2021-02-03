package org.dmitry.moviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastNameDatesRequestDto {

    @Pattern(regexp = "^[a-zA-z-']*$", message = "The director's last name must be in latin letters with \"-\" and \"'\" as delimiters.")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date from;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date until;
}
