package com.raslan.dto;

import com.raslan.customer.Gender;

public record CustomerRegistrationRequestDTO(String name,
                                             String email,
                                             String password,
                                             Integer age,
                                             Gender gender) {
}