package br.com.fiap.tech_challenge_ii.menu.core.exception;

public class ExistingMenuItemException extends SystemBaseException {

    private static final String CODE = "user.actionNotAllowed";
    private static final String MESSAGE = "Item with this name already exists";
    private static final Integer HTTP_STATUS = 400;

    public ExistingMenuItemException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }

}
