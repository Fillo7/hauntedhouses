package cz.muni.fi.pa165.hauntedhouses.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * this exception corresponds to all possible exceptions, which can be thrown from downstream layers
 *
 * Created by Ondro on 09-Nov-16.
 */
public class DataManipulationException extends DataAccessException {

    public DataManipulationException(String message){
        super(message);
    }

    public DataManipulationException(String message, Throwable cause){
        super(message, cause);
    }
}
