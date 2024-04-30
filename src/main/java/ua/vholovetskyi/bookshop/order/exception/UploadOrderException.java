package ua.vholovetskyi.bookshop.order.exception;

import ua.vholovetskyi.onlinestore.commons.exception.BusinessException;

public class UploadOrderException extends BusinessException {
    public UploadOrderException(Throwable cause) {
        super("An error occurred while uploading the file!", cause);
    }
}
