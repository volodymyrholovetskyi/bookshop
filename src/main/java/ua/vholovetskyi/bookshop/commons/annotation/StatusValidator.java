package ua.vholovetskyi.bookshop.commons.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class StatusValidator implements ConstraintValidator<StatusValidation, String> {
    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintContext) {
        if (status == null) {
            return false;
        }

        var statuses = Set.of("NEW", "PAID", "CANCELED", "SHIPPED");
        if (!statuses.contains(status)) {
            constraintContext.buildConstraintViolationWithTemplate("Status is not valid!").addConstraintViolation();
            return false;
        }
        return true;
    }
}
