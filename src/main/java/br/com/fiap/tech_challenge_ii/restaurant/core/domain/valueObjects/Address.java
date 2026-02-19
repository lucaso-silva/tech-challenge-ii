package br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects;

public record Address(String street, String number, String neighborhood, String city, String zipCode) {
    public static Address newAddress(final String street,
                                     final String number,
                                     final String neighborhood,
                                     final String city,
                                     final String zipCode){
        return new Address(street, number, neighborhood, city, zipCode);
    };
}
