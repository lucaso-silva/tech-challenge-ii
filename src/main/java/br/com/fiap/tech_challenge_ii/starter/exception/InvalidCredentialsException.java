package br.com.fiap.tech_challenge_ii.starter.exception;

public class InvalidCredentialsException extends SystemBaseException {

    public InvalidCredentialsException() {
        super("INVALID_CREDENTIALS", "Invalid login or password", 401);
    }
}
