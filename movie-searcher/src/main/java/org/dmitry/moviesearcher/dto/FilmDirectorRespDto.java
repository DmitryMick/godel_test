package org.dmitry.moviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmDirectorRespDto{
    private String directorFirstName;

    private String directorLastName;

    private Date birthdate;

    private String filmName;

    private Date releaseDate;

    private String genre;
}
