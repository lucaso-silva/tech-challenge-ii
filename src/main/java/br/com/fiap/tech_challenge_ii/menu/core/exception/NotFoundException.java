package br.com.fiap.tech_challenge_ii.menu.core.exception;

public class NotFoundException extends SystemBaseException {

    private static final Integer HTTP_STATUS = 404;

    public NotFoundException(String code, String message) {
        super(code, message, HTTP_STATUS);
    }

}
