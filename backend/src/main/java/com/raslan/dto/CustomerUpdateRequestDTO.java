package com.raslan.dto;

public record CustomerUpdateRequestDTO(
        String name,
        String email,
        Integer age
) {
}