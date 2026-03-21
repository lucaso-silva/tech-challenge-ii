package br.com.fiap.tech_challenge_ii.restaurant.core.exception;

import lombok.Getter;

@Getter
public class SystemBaseException extends RuntimeException {

    private final String code;
    private final Integer httpStatus;

    public SystemBaseException(String code, String message, Integer httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
