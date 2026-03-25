package br.com.fiap.tech_challenge_ii.user.core.exception;

import br.com.fiap.tech_challenge_ii.starter.exception.SystemBaseException;

public class UserAlreadyExistsException extends SystemBaseException {

    private static final String CODE = "usuario.jaExistente";
    private static final String MESSAGE = "Usuário já existe com este email ";
    private static final Integer HTTP_STATUS = 400;

    public UserAlreadyExistsException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }

    public UserAlreadyExistsException(String email) {
        super(CODE, MESSAGE + email, HTTP_STATUS);
    }
}
