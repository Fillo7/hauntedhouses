package cz.muni.fi.pa165.hauntedhouses.exceptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation, which cause translating downstream-layers exceptions
 *
 * Created by Ondrej Oravcok on 21-Nov-16.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceExceptionTranslate {
}
