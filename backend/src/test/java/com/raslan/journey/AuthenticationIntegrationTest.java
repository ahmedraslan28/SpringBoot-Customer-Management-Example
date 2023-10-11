package com.raslan.journey;

import com.github.javafaker.Faker;
import com.raslan.customer.Gender;
import com.raslan.dto.CustomerDTO;
import com.raslan.dto.CustomerRegistrationRequestDTO;
import com.raslan.dto.CustomerTokenResponseDTO;
import com.raslan.dto.LoginRequestDTO;
import com.raslan.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthenticationIntegrationTest {
    private static final Faker faker = new Faker();

    private static final Random random = new Random();

    private static final String CUSTOMER_URL = "/api/v1/customers";

    private static final String LOGIN_URL = "/api/v1/auth/login";

    @Autowired
    private WebTestClient client;

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    void LoginWithValidCredentials() {
        String name = faker.name().fullName();
        String email = name + "@AuthIntegrationTest";
        String password = "123456";
        int age = faker.random().nextInt(18, 99);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequestDTO request = new CustomerRegistrationRequestDTO(
                name,
                email,
                password,
                age,
                gender
        );

        // post a new customer and get token
        String jwtToken = Objects.requireNonNull(client.post()
                        .uri(CUSTOMER_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(request), CustomerRegistrationRequestDTO.class)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody(CustomerTokenResponseDTO.class)
                        .returnResult()
                        .getResponseBody())
                .token();
        // create loginRequest object and send a request
        LoginRequestDTO loginRequest = new LoginRequestDTO(email, password);

        EntityExchangeResult<CustomerTokenResponseDTO> response = client.post()
                .uri(LOGIN_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(loginRequest), LoginRequestDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerTokenResponseDTO.class)
                .returnResult();

        CustomerTokenResponseDTO responseBody = response.getResponseBody();
        String token = Objects.requireNonNull(responseBody).token();

        CustomerDTO customer = Objects.requireNonNull(responseBody).customer();

        assertThat(jwtUtil.isTokenValid(token, customer.username())).isTrue();

        assertThat(customer.email()).isEqualTo(email);
        assertThat(customer.age()).isEqualTo(age);
        assertThat(customer.name()).isEqualTo(name);
        assertThat(customer.username()).isEqualTo(email);
        assertThat(customer.gender()).isEqualTo(gender);
        assertThat(customer.rules()).isEqualTo(List.of("ROLE_USER"));


    }

    @Test
    void LoginWithInvalidCredentials() {
        String username = "anyInvalidUserName@gmail.com";
        String password = "anyInvalidPassword";

        LoginRequestDTO loginRequest = new LoginRequestDTO(username, password);

        client.post()
                .uri(LOGIN_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(loginRequest), LoginRequestDTO.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }
}
