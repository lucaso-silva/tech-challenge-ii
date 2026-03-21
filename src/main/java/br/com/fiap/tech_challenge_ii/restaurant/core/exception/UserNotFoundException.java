package br.com.fiap.tech_challenge_ii.restaurant.core.exception;

public class UserNotFoundException extends NotFoundException {
    private static final String CODE = "user.userNotFound";

    public UserNotFoundException(String message) {
        super(CODE, message);
    }

}
