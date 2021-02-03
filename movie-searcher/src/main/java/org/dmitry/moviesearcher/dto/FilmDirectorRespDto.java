package org.dmitry.moviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmDirectorRespDto {
    private String directorFirstName;

    private String directorLastName;

    private Date birthdate;

    private String filmName;

    private Date releaseDate;

    private String genre;
}
