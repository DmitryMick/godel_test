package org.dmitry.moviesearcher.exceptionhandler.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidDataRequestException extends Throwable {
    private BindingResult bindingResult;
}
