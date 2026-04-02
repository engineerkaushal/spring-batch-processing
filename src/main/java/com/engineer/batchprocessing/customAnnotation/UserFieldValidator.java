package com.engineer.batchprocessing.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserFieldValidator implements ConstraintValidator<ValidateUserAnnotation, Object> {

    private String message;
    private int minLength;
    private long minValue;
    private long maxValue;

    @Override
    public void initialize (ValidateUserAnnotation constraintAnnotation) {
        // Initialization Logic (Optional but important)
        this.message = constraintAnnotation.message ();
        this.minLength = constraintAnnotation.minLength ();
        this.minValue = constraintAnnotation.minValue ();
        this.maxValue = constraintAnnotation.maxValue ();
    }

    @Override
    public boolean isValid (Object value, ConstraintValidatorContext context) {

        //null check for all types
        if ( value == null ) {
            addMessage (context);
            return false;
        }
        //String validation like : (name, email, password, etc...)
        if ( value instanceof String str ) {
            if ( str.trim ().isBlank () ) {
                addMessage (context);
                return false;
            }

            if ( minLength > 0  && str.length () < minLength) {
                addMessage (context);
                return false;
            }
        }

        if ( value instanceof Number age ) {
            long givenValue = age.longValue ();
            if ( givenValue <= minValue || givenValue >= maxValue ) {
                addMessage (context);
                return false;
            }
        }

        // For other types (Integer, Long, Boolean, etc..)
        // non-null is valid

        return true;
    }

    private void addMessage(ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate (message)
                .addConstraintViolation ();
    }
}
