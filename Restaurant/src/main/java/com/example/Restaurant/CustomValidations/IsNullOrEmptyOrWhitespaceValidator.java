package com.example.Restaurant.CustomValidations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsNullOrEmptyOrWhitespaceValidator implements ConstraintValidator<IsNullOrEmptyOrWhitespace, String> {
    public IsNullOrEmptyOrWhitespaceValidator() {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.trim().length() == 0)
            return false;
        return true;
    }
}
