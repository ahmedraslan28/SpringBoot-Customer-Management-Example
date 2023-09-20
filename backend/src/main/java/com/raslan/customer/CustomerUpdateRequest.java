package com.raslan.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}