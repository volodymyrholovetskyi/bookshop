package ua.vholovetskyi.bookshop.commons.exception;

import java.time.Instant;

public record ErrorResponse(String status, int statusCode, String message, Instant timestamp) {

}
