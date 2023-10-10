package com.raslan.dto;

import com.raslan.customer.Gender;

import java.util.List;

public record CustomerDTO(Integer id,
                          String name,
                          String email,
                          Gender gender,
                          Integer age,
                          List<String> rules,
                          String username) {
}
