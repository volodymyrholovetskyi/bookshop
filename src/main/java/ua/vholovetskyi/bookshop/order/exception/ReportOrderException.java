package ua.vholovetskyi.bookshop.order.exception;

import ua.vholovetskyi.onlinestore.commons.exception.BusinessException;

public class ReportOrderException extends BusinessException {
    public ReportOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
