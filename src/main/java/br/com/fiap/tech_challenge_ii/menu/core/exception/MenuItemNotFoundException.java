package br.com.fiap.tech_challenge_ii.menu.core.exception;

public class MenuItemNotFoundException extends NotFoundException {

    private static final String CODE = "menuItem.menuItemNotFound";
    private static final String MESSAGE = "Menu Item not found";

    public MenuItemNotFoundException() {
        super(CODE, MESSAGE);
    }
}
