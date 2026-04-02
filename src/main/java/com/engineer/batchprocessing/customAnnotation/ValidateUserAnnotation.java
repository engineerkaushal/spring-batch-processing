package com.engineer.batchprocessing.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.FIELD)
@Constraint (validatedBy = UserFieldValidator.class)
public @interface ValidateUserAnnotation {

    String message() default "Incorrect input";
    int minLength() default 0;

    long minValue() default Long.MIN_VALUE;
    long maxValue() default Long.MAX_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
