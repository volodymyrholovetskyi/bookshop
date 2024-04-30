package ua.vholovetskyi.bookshop.order.exception;

import ua.vholovetskyi.onlinestore.commons.exception.BusinessException;

public class UpdateStatusException extends BusinessException {
    public UpdateStatusException(String status) {
        super("Unknown status: [%s]".formatted(status));
    }
}
