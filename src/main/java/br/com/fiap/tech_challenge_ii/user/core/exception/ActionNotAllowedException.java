package br.com.fiap.tech_challenge_ii.user.core.exception;


import java.io.Serial;

public class ActionNotAllowedException extends SystemBaseException {
	@Serial
    private static final long serialVersionUID = -6678327325055715089L;
	
	private static final String CODE = "usuario.acaoNaoPermitida";
	private static final String MESSAGE = "Ação não permitida";
	private static final Integer HTTP_STATUS = 403;
	
	public ActionNotAllowedException() {
		super(CODE, MESSAGE, HTTP_STATUS);
	}
	
}
