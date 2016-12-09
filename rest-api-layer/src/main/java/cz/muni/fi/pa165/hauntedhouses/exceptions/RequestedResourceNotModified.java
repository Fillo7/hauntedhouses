package cz.muni.fi.pa165.hauntedhouses.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by User on 09-Dec-16.
 */
@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "Requested resource was not modified")
public class RequestedResourceNotModified extends RuntimeException {

    public RequestedResourceNotModified(Throwable cause){
        super(cause);
    }

    public RequestedResourceNotModified(String message, Throwable cause){
        super(message, cause);
    }

}
