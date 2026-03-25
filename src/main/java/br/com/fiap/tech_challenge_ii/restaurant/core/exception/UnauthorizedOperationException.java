package br.com.fiap.tech_challenge_ii.restaurant.core.exception;

import br.com.fiap.tech_challenge_ii.starter.exception.SystemBaseException;

public class UnauthorizedOperationException extends SystemBaseException {

    private static final String CODE = "restaurant.actionNotAllowed";
    private static final String MESSAGE = "Action not allowed";
    private static final Integer HTTP_STATUS = 403;

    public UnauthorizedOperationException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }

    public UnauthorizedOperationException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
