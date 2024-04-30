package ua.vholovetskyi.bookshop.order.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderJsonValidator {
    private final Validator validator;

    public boolean isValid(OrderJson orderJson) {
        Set<ConstraintViolation<OrderJson>> validate = validator.validate(orderJson);
        return validate.isEmpty();
    }
}
