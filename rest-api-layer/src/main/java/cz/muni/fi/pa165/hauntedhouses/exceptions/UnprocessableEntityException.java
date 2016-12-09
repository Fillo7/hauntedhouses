package cz.muni.fi.pa165.hauntedhouses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Ondrej Oravcok on 09-Dec-16.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Resource is invalid")
public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(Throwable cause){
        super(cause);
    }

    public UnprocessableEntityException(String message, Throwable cause){
        super(message, cause);
    }
}
