package br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects;

public record Address(Long id, String street, String number, String neighborhood, String city, String zipCode) {
    public static Address newAddress(final Long id,
                                     final String street,
                                     final String number,
                                     final String neighborhood,
                                     final String city,
                                     final String zipCode){
        return new Address(id, street, number, neighborhood, city, zipCode);
    };
}
