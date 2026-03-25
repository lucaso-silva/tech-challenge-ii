package br.com.fiap.tech_challenge_ii.user.infra.exception;

import java.io.Serial;

import br.com.fiap.tech_challenge_ii.starter.exception.SystemBaseException;

public class UserTypeNotFoundException extends SystemBaseException {
    @Serial
    private static final long serialVersionUID = 756435066530793530L;

    private static final String CODE = "usuario.tipoUsuarioNaoExiste";
    private static final String MESSAGE = "Tipo de usuário não existe";
    private static final Integer HTTP_STATUS = 422;

    public UserTypeNotFoundException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }

}
