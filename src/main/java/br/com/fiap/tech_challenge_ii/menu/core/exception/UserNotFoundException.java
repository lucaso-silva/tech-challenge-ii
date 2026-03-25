package br.com.fiap.tech_challenge_ii.menu.core.exception;

public class UserNotFoundException extends NotFoundException {

    private static final String CODE = "user.userNotFound";
    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(CODE, MESSAGE);
    }

}
