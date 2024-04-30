package ua.vholovetskyi.bookshop.order.exception;

import ua.vholovetskyi.onlinestore.commons.exception.ResourceNotFound;

public class OrderNotFoundException extends ResourceNotFound {
    public OrderNotFoundException(Long id) {
        super("Order with ID: %d not found!".formatted(id));
    }
}
