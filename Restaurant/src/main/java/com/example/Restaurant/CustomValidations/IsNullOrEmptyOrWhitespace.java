package com.example.Restaurant.CustomValidations;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsNullOrEmptyOrWhitespaceValidator.class)
@Documented
public @interface IsNullOrEmptyOrWhitespace {
    String message() default "Data mandatory";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
