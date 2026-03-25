package br.com.fiap.tech_challenge_ii.restaurant.core.dto;

public record AddressDTO(Long id,
                         String street,
                         String number,
                         String neighborhood,
                         String city,
                         String zipCode) {
}
