package ua.vholovetskyi.bookshop.customer.exception;

import ua.vholovetskyi.onlinestore.commons.exception.ResourceAlreadyExists;

public class EmailAlreadyExistsException extends ResourceAlreadyExists {
    public EmailAlreadyExistsException(String email) {
        super("Customer with email: [%s] already exists!".formatted(email));
    }
}
