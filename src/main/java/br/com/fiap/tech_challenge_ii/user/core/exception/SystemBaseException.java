package br.com.fiap.tech_challenge_ii.user.core.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemBaseException extends RuntimeException {
	@Serial
    private static final long serialVersionUID = 443911183945646720L;

	private final String code;
	private final String message;
	private final Integer httpStatus;
}
