package cz.muni.fi.pa165.hauntedhouses.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalTime;

/**
 * Created by Ondro on 31-Oct-16.
 *
 * this code is from https://github.com/MartinStyk/pa165-activity-tracker repository
 */
public class SurviveConditionComparer implements ConstraintValidator<SurviveCondition, Object> {

    private SurviveCondition surviveCondition;

    @Override
    public void initialize(SurviveCondition surviveCondition) {
        this.surviveCondition = surviveCondition;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String[] members = surviveCondition.members();

        LocalTime startTime = LocalTime.MIN;

        for (String member : members) {
            Field field;
            try {
                field = o.getClass().getDeclaredField(member);
                field.setAccessible(true);

                LocalTime value = LocalTime.class.cast(field.get(o));
                if (value.isBefore(startTime)) {
                    return false;
                }
                startTime = value;
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Error while reading member " + member + " on class " +
                        o.getClass().getName());
            }

        }
        return true;
    }
}
