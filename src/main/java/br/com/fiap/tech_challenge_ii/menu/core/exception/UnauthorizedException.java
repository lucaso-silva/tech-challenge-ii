package br.com.fiap.tech_challenge_ii.menu.core.exception;

import br.com.fiap.tech_challenge_ii.starter.exception.SystemBaseException;

public class UnauthorizedException extends SystemBaseException {

    private static final String CODE = "user.actionNotAllowed";
    private static final String MESSAGE = "Action not allowed";
    private static final Integer HTTP_STATUS = 403;

    public UnauthorizedException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }

    public UnauthorizedException(String message) {
        super(CODE, message, HTTP_STATUS);
    }

}
