package cz.muni.fi.pa165.hauntedhouses.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Ondro on 31-Oct-16.
 *
 * it compares all given attributes, so they must satisfy condition n1 <= n2 <= n3 <= ...
 *
 * this code is from https://github.com/MartinStyk/pa165-activity-tracker repository
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SurviveConditionComparer.class)
@Documented
public @interface SurviveCondition {
    String message() default "These attributes has strict order.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] members() default {};
}
