package ua.vholovetskyi.bookshop.commons.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidator.class)
@Target(FIELD)
public @interface StatusValidation {

    public String message() default "Unknown status.";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
