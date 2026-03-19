package br.com.fiap.tech_challenge_ii.menu.core.exception;

import lombok.Getter;

@Getter
public class SystemBaseException extends RuntimeException {

    private final String code;
    private final String message;
    private final Integer httpStatus;

    public SystemBaseException(String code, String message, Integer httpStatus) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
