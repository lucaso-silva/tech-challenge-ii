package br.com.fiap.tech_challenge_ii.restaurant.core.exception;

public class SystemBaseException extends RuntimeException {
    public SystemBaseException(String message) {
        this(message, null);
    }

    public SystemBaseException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
