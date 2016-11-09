package cz.muni.fi.pa165.hauntedhouses.exceptions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.dao.DataAccessException;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

/**
 * Every exception from DAO is presented as DataManipulationException
 * when translation between exceptions fails, this exception is thrown
 *
 * Created by Ondro on 09-Nov-16.
 */
public class TranslateAspectException {

    @Around("execution(public * cz.fi.muni.pa165.hauntedhouses.service..*(..))")
    public Object translateDataAccessException(ProceedingJoinPoint pjp) throws Throwable{
        try{
            return pjp.proceed();
        } catch(NullPointerException | IllegalArgumentException | ConstraintViolationException | PersistenceException
                | DataAccessException exception){
            throw new DataManipulationException("Exception thrown while accessing data layer on " + pjp.toShortString(), exception);
        }
    }
}
