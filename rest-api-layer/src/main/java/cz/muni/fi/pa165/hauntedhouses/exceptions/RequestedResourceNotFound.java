package cz.muni.fi.pa165.hauntedhouses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * HTTP 404 not found
 *
 * Created by Ondrej Oravcok on 01-Dec-16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested resource does not exist")
public class RequestedResourceNotFound extends RuntimeException {

    public RequestedResourceNotFound(Throwable cause){
        super(cause);
    }

    public RequestedResourceNotFound(String message, Throwable cause){
        super(message, cause);
    }

}
