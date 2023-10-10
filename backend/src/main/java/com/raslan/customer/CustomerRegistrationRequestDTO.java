package com.raslan.customer;

public record CustomerRegistrationRequestDTO(String name,
                                             String email,
                                             String password,
                                             Integer age,
                                             Gender gender) {
}