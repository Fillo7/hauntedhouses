package cz.muni.fi.pa165.hauntedhouses.exceptions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;

import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

/**
 * Every exception from DAO is presented as DataManipulationException
 * when translation between exceptions fails, this exception is thrown
 *
 * Created by Ondro on 09-Nov-16.
 */
@Aspect
@Named
public class ServiceExceptionTranslateAspect {

    @Around("(@annotation(ServiceExceptionTranslate) || @within(ServiceExceptionTranslate)) && execution(public * *(..))")
    public Object translate(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch(ValidationException | PersistenceException | DataAccessException | UnsupportedOperationException exception) {
            throw new DataManipulationException("Exception thrown while accessing data layer on " + pjp.toShortString(), exception);
        }
    }
}
