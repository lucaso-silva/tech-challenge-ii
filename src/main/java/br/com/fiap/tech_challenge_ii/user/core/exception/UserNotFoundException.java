package br.com.fiap.tech_challenge_ii.user.core.exception;

import br.com.fiap.tech_challenge_ii.starter.exception.SystemBaseException;

public class UserNotFoundException extends SystemBaseException {
    private static final long serialVersionUID = -6678327325055715089L;

    private static final String CODE = "usuario.usuarioNaoEncontrado";
    private static final String MESSAGE = "Usuário não foi encontrado";
    private static final Integer HTTP_STATUS = 404;

    public UserNotFoundException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }

}
