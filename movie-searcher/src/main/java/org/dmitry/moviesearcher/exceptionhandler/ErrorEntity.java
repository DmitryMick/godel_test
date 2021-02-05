package org.dmitry.moviesearcher.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEntity {
    private String fieldName;
    private Object rejectedValue;
    private String messageError;
}
