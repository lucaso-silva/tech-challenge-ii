package br.com.fiap.tech_challenge_ii.restaurant.core.exception;

public class DomainException extends SystemBaseException {
    private static final String CODE = "restaurant.invalidRequest";
    private static final String MESSAGE = "Invalid request";
    private static final Integer HTTP_STATUS = 400;

    public DomainException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }

    public DomainException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
