package com.raslan.journey;

import com.github.javafaker.Faker;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthenticationIntegrationTest {
    private static final Faker faker = new Faker();

    private static final Random random = new Random();

    private static final String CUSTOMER_URL = "/api/v1/customers";

    private static final String LOGIN_URL = "/api/v1/auth/login";




}
