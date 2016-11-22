package cz.muni.fi.pa165.hauntedhouses.exception;

/**
 * When there is no entity, this exception is thrown on facade layer
 *
 * Created by Ondrej Oravcok on 21-Nov-16.
 */
public class NoEntityException extends RuntimeException {

    public NoEntityException(String message){
        super(message);
    }

    public NoEntityException(String message, Throwable cause){
        super(message, cause);
    }
}
