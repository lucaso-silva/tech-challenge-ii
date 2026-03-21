package br.com.fiap.tech_challenge_ii.restaurant.core.exception;

import br.com.fiap.tech_challenge_ii.starter.exception.SystemBaseException;

public class NotFoundException extends SystemBaseException {
    private static final Integer HTTP_STATUS = 404;

    public NotFoundException(String code, String message) {
        super(code, message, HTTP_STATUS);
    }
}
