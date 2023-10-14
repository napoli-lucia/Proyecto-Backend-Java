package metcamp.backend.eventsmanager.validation;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidateEnum {

    Class<? extends Enum<?>> enumClass();
    String message() default "value is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
