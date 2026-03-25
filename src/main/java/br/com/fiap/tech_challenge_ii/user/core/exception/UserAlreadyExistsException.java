package br.com.fiap.tech_challenge_ii.user.core.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("Usuário já existe com este email");
    }

    public UserAlreadyExistsException(String email) {
        super("Usuário já existe com email: " + email);
    }
}